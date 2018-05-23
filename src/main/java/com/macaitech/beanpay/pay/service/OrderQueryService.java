/**
 * 
 */
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
import com.macaitech.beanpay.enums.PayMethodType;
import com.macaitech.beanpay.enums.PayStatus;
import com.macaitech.beanpay.model.QueryResult;
import com.macaitech.beanpay.model.request.QueryRequest;
import com.macaitech.beanpay.model.response.QueryResponse;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.MapUtils;

/**
 * @author yuhui.tang 订单查询
 */
@Service
public class OrderQueryService extends BasePayService {
	@Autowired
	private OrderService orderService;
	@Autowired
	private PayFactory payFactory;

	@Transactional
	public QueryResponse doQueryOrder(QueryRequest queryRequest) throws ParseException {
		// TODO
		// 验证参数
		// 查询商户
		logger.info("query->doOrderPay begin("+queryRequest.getUuid()+"):" + JsonUtil.toString(queryRequest));
		MerchantApp merchantApp = this.getMerchantApp(queryRequest.getAppId(),queryRequest.getMerchantId());
		// 验证签名
		Map<String, Object> map = (Map<String, Object>) MapUtils.objectToMap(queryRequest);
		this.validSignature(map, merchantApp);

		Order order = this.orderService.getByMerchantOrderNo(queryRequest.getAppId(),
				queryRequest.getMerchantOrderNo());
		if (order == null) {
			logger.info("query->doOrderPay order not existed("+queryRequest.getUuid()+"):merchantOrderNo=" + queryRequest.getMerchantOrderNo());
			QueryResponse response = new QueryResponse();
			response.setCode(ResultCodes.Result_Code_50001);
			response.setMessage("订单不存在");
			return response;
		} else {
			if (order.getPayStatus() == PayStatus.PayStatus_UnPay.getKey()
					|| order.getPayStatus() == PayStatus.PayStatus__Paying.getKey()) {
				//未支付，或支付中的，发起向支付渠道查询
				return this.queryPayChannelAndUpdateDone(queryRequest.getUuid(),order);
			} else {
				// 其他状态，直接封装订单信息返回
				return this.toResponse(order);
			}
		}
	}

	/**
	 * 转换为响应结果
	 * @param order
	 * @return
	 */
	public QueryResponse toResponse(Order order) {
		QueryResponse response = new QueryResponse();
		QueryResult result = new QueryResult();
		result.setMerchantId(String.valueOf(order.getMerchantId()));
		result.setAppId(order.getAppId());

		result.setMerchantOrderNo(order.getMerchantOrderNo());
		result.setPayChannel(order.getPayChannel());
		result.setPaymethod(PayMethodType.getPayMethodType(order.getPayChannel()).getValue());
		result.setPayWay(order.getPayWay());

		result.setOrderMoney(order.getOrderMoney());
		result.setOrderName(order.getOrderName());

		result.setTradeNo(order.getTradeNo());

		result.setPaystatus(order.getPayStatus());
		result.setPaymsg(order.getPayMsg());

		result.setTradeStatus(order.getTradeStatus());
		result.setTradeMsg(order.getTradeMsg());
		response.setData(result);
		return response;
	}
	
	/**
	 * 查询支付渠道，如果订单完结的更新订单记录
	 * @param order
	 * @return
	 * @throws ParseException
	 */
	public QueryResponse queryPayChannelAndUpdateDone(String uuid,Order order) throws ParseException {
		logger.info("query->doOrderPay("+uuid+") order unpay or paying:orderId=" + order.getId());
		// 未支付 或 支付中，进入向支付渠道查询
		// #-----------------------向支付渠道查询--------------------
		IQueryPayChannelOrderService queryPayChannelOrderService = this.payFactory
				.getQueryService(order.getPayChannel());
		logger.info("query->doOrderPay invoke paychannel("+uuid+") :orderId=" + order.getId());
		QueryResponse response = queryPayChannelOrderService.invokeChannelQueryOrder(order);
		logger.info("query->doOrderPay invoke paychannel("+uuid+") :" + JsonUtil.toString(response));
		QueryResult result = response.getData();
		if (result != null && (result.getPaystatus() == PayStatus.PayStatus_OK.getKey()
				|| result.getPaystatus() == PayStatus.PayStatus_Closed.getKey()
				|| result.getPaystatus() == PayStatus.PayStatus_Fail.getKey()
				|| result.getPaystatus() == PayStatus.PayStatus_Finished.getKey()
				|| result.getPaystatus() == PayStatus.PayStatus_Refund.getKey())) {
			// 支付结束的，更新订单
			order.setPayStatus(result.getPaystatus());
			order.setPayTime(result.getPayTime());
			order.setTradeNo(result.getTradeNo());
			order.setPayMsg(result.getPaymsg());
			order.setTradeStatus(result.getTradeStatus());
			order.setTradeMsg(result.getTradeMsg());
			logger.info("query->updatePayResult("+uuid+") 有最终支付结果状态则更新:" + JsonUtil.toString(result));
			this.orderService.updatePayResult(order);
		}
		return response;
	}
}
