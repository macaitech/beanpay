package com.macaitech.beanpay.pay.service;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.macaitech.beanpay.common.ResultCodes;
import com.macaitech.beanpay.db.entity.MerchantApp;
import com.macaitech.beanpay.db.entity.Order;
import com.macaitech.beanpay.db.service.OrderService;
import com.macaitech.beanpay.enums.PayStatus;
import com.macaitech.beanpay.exception.PayException;
import com.macaitech.beanpay.model.OrderResult;
import com.macaitech.beanpay.model.QueryResult;
import com.macaitech.beanpay.model.request.OrderRequest;
import com.macaitech.beanpay.model.response.PayResponse;
import com.macaitech.beanpay.model.response.QueryResponse;
import com.macaitech.beanpay.util.DateUtils;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.MapUtils;

/**
 * 
 * @author yuhui.tang
 *  下单抽象类
 */
@Service
public abstract class AbstractOrderPayTemplate extends BasePayService {
	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderQueryService orderQueryService;
	/**
	 * 下单
	 * @param orderRequest
	 * @return
	 */
	@Transactional
	public PayResponse doOrderPay(OrderRequest orderRequest) {
		//TODO
		//验证参数
		logger.info("pay->doOrderPay begin("+orderRequest.getUuid()+"):"+JsonUtil.toString(orderRequest));
		//查询商户
		MerchantApp merchantApp = this.getMerchantApp(orderRequest.getAppId(),orderRequest.getMerchantId());
		
		//验证签名
		Map<String, Object> map = (Map<String, Object>) MapUtils.objectToMap(orderRequest);
		this.validSignature(map,merchantApp);
		
		Order order = this.orderService.getByMerchantOrderNo(orderRequest.getAppId(), orderRequest.getMerchantOrderNo());
		if(order==null) {//订单不存在，则第一次发起
			return this.doSubmitNewOrder(merchantApp,orderRequest);			
		}
		else if(order.getPayStatus() == PayStatus.PayStatus_OK.getKey()
				|| order.getPayStatus() == PayStatus.PayStatus_Fail.getKey()
				|| order.getPayStatus() == PayStatus.PayStatus_Finished.getKey()
				|| order.getPayStatus() == PayStatus.PayStatus_Refund.getKey()) {
			//订单已支付、失败、结束、退款，封装订单状态返回
			logger.info("pay->doOrderPay("+orderRequest.getUuid()+") order done,orderid=:"+order.getId());
			PayResponse response = this.extractExistedOrder(order);
			response.setCode(ResultCodes.Result_Code_200);
			response.setMessage(PayStatus.getPayStatus(order.getPayStatus()).getValue());
			return response;
		}
		else {
			//先向支付渠道查询
			logger.info("pay->doOrderPay("+orderRequest.getUuid()+") invoke paychannel :"+JsonUtil.toString(order));
			try {
				QueryResponse response = this.orderQueryService.queryPayChannelAndUpdateDone(orderRequest.getUuid(),order);
				if(response!=null) {
					QueryResult queryResult =  response.getData();
					if(queryResult!=null && (
							queryResult.getPaystatus()==PayStatus.PayStatus_UnPay.getKey()
							|| queryResult.getPaystatus()==PayStatus.PayStatus__Paying.getKey())) {
						//订单未支付，则先更新订单，再发起支付请求
						return this.doSubmitOrderAgain(orderRequest, order);
					}
				}
				//已支付或已结束订单返回
				return this.extractExistedOrder(order);
			} catch (ParseException e) {
				logger.error(e.getMessage(),e);
				PayResponse response = new PayResponse();
				response.setCode(ResultCodes.Result_Code_50003);
				response.setMessage("时间解码错误");
				return response;
			}
		}
	}
	/**
	 * 新单，发起支付
	 * @param orderRequest
	 * @return
	 */
	private PayResponse doSubmitNewOrder(MerchantApp merchantApp,OrderRequest orderRequest) {
		Order order;
		order = this.toOrder(merchantApp,orderRequest);
		logger.info("pay->doSubmitNewOrder("+orderRequest.getUuid()+"),insert:merchantOrderNo="+order.getMerchantOrderNo());
		this.orderService.insert(order);
		//调用支付
		PayResponse response = this.invokePayChannel(orderRequest.getUuid(),order);
		
		//后更新调用支付渠道后返回的结果
		OrderResult orderResult = response.getData();
		order.setTradeOrderUrl(orderResult.getOrderUrl());
		order.setTradePrepay(orderResult.getPrepay());
		logger.info("pay->doSubmitNewOrder("+orderRequest.getUuid()+"),updateOrderResponse:merchantOrderNo="+order.getMerchantOrderNo());
		this.orderService.updateOrderResponse(order);
		return response;
	}
	/**
	 * 再次发起请求
	 * @param merchantApp
	 * @param orderRequest
	 * @param orderInDb
	 * @return
	 */
	private PayResponse doSubmitOrderAgain(OrderRequest orderRequest,Order orderInDb) { 
		orderInDb = this.enboxOrder(orderRequest,orderInDb);
		logger.info("pay->doSubmitOrderAgain("+orderRequest.getUuid()+"),update:merchantOrderNo="+orderInDb.getMerchantOrderNo());
		this.orderService.updateTryAgain(orderInDb);
		//调用支付
		PayResponse response = this.invokePayChannel(orderRequest.getUuid(),orderInDb);
		
		//后更新调用支付渠道后返回的结果
		OrderResult orderResult = response.getData();
		orderInDb.setTradeOrderUrl(orderResult.getOrderUrl());
		orderInDb.setTradePrepay(orderResult.getPrepay());
		logger.info("pay->doSubmitOrderAgain("+orderRequest.getUuid()+"),updateOrderResponse:merchantOrderNo="+orderInDb.getMerchantOrderNo());
		this.orderService.updateOrderResponse(orderInDb);
		return response;
	} 
	/**
	 * 封装订单
	 * @param orderRequest
	 * @param orderInDb
	 * @return
	 */
	private Order enboxOrder(OrderRequest orderRequest,Order orderInDb) {
		if(orderRequest.getAppId().equals(orderInDb.getAppId()) 
				&& orderRequest.getMerchantId().equals(String.valueOf(orderInDb.getMerchantId()))
				&& orderRequest.getMerchantOrderNo().equals(orderInDb.getMerchantOrderNo())) {
			//订单关键项目必须一致
			orderInDb.setClientIp(orderRequest.getClientIp());
			orderInDb.setExpireSecond(orderRequest.getExpireSecond());
			orderInDb.setNotifyUrl(orderRequest.getNotifyUrl());
			orderInDb.setOrderDesc(orderRequest.getOrderDesc());
			orderInDb.setOrderMoney(orderRequest.getOrderMoney());
			orderInDb.setOrderName(orderRequest.getOrderName());
			orderInDb.setPayChannel(orderRequest.getPayChannel());
			orderInDb.setPayWay(orderRequest.getPayWay());
			orderInDb.setPayStatus(PayStatus.PayStatus_UnPay.getKey());
			orderInDb.setPayTime(null);
			orderInDb.setRequestTime(DateUtils.parseDate(orderRequest.getRequestTime()));
		}
		else {
			throw new PayException(ResultCodes.Result_Code_50001, "原订单["+orderInDb.getMerchantOrderNo()+"]发起再支付请求时，参数不一致");
		}
		return orderInDb;
	}
	
	/**
	 * 抽象方法，子类实现下单具体逻辑
	 * @param orderRequest
	 * @return
	 */
	public abstract PayResponse invokePayChannel(String uuid,Order order) ;
	
	
}
