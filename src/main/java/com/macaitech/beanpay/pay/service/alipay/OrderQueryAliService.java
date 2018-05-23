/**
 * 
 */
package com.macaitech.beanpay.pay.service.alipay;

import java.text.ParseException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.macaitech.beanpay.common.ResultCodes;
import com.macaitech.beanpay.db.entity.Order;
import com.macaitech.beanpay.enums.PayChannelType;
import com.macaitech.beanpay.enums.PayStatus;
import com.macaitech.beanpay.model.response.QueryResponse;
import com.macaitech.beanpay.model.QueryResult;
import com.macaitech.beanpay.pay.service.BasePayService;
import com.macaitech.beanpay.pay.service.IQueryPayChannelOrderService;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.MapUtils;

/**
 * @author yuhui.tang
 * 订单查询
 * 2018-04-11
 */
@Service
public class OrderQueryAliService extends BasePayService implements IQueryPayChannelOrderService{
	
	public static void main(String[] args) {
		OrderQueryAliService aliService = new OrderQueryAliService();
		Order order = new Order();
		order.setOrderNo("10180420180423008");
		order.setMerchantOrderNo("20180423008");
		try {
			aliService.invokeChannelQueryOrder(order);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 查询订单
	 * @throws ParseException 
	 */
	@Override
	public QueryResponse invokeChannelQueryOrder(Order order) throws ParseException {
		
		AlipayClient alipayClient = AlipayConfig.getAlipayClient();
		AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

		AlipayTradeQueryModel model=new AlipayTradeQueryModel();
	     model.setOutTradeNo(order.getOrderNo());
	     model.setTradeNo(order.getTradeNo());
	     alipayRequest.setBizModel(model);
	     
	     QueryResponse response = null ;
		try {
			AlipayTradeQueryResponse alipay_response = alipayClient.execute(alipayRequest);
			logger.info(alipay_response.getBody());

			JSONObject jsonObject = JSONObject.parseObject(alipay_response.getBody());
			JSONObject queryResponse = jsonObject.getJSONObject("alipay_trade_query_response");
			Map<String, Object> resultMap = (Map<String, Object>) JsonUtil.readJsonMap(queryResponse.toJSONString());
			response = this.enboxResponse(order, resultMap);
		} catch (AlipayApiException e) {
			logger.error(e.getMessage(),e);
		}
		return response;
	}
	
	/**
	 * 封装响应
	 * @param order
	 * @param resultMap
	 * @return
	 * @throws ParseException 
	 */
	private QueryResponse enboxResponse(Order order,Map<String, Object> resultMap) throws ParseException {
		QueryResponse response = new QueryResponse();
		QueryResult result = new QueryResult();
		result.setTradeStatus(AliResponseUtil.getMsgCode(resultMap));
		result.setTradeMsg(AliResponseUtil.getMsg(resultMap));
		//获取支付状态
		PayStatus payStatus = AliResponseUtil.getPayStatus(resultMap);
		result.setPaystatus(payStatus.getKey());
		
		if(!AliResponseUtil.isSuccessed(resultMap)) {//不成功
			response.setCode(ResultCodes.Result_Code_50001);
			response.setMessage(AliResponseUtil.getMsg(resultMap));
			response.setData(result);
		}
		else {
			result.setMerchantId(String.valueOf(order.getMerchantId()));
			result.setAppId(order.getAppId());

			result.setMerchantOrderNo(order.getMerchantOrderNo());
			result.setPayChannel(PayChannelType.PayChannel_ALIPAY.getKey());
			result.setPayWay(order.getPayWay());
			
			result.setTradeNo(AliResponseUtil.getTradeNo(resultMap));

			result.setPayTime(AliResponseUtil.getPayTime(payStatus, AliResponseUtil.convertMap(resultMap)));
			result.setPaymsg(AliResponseUtil.getTradeStatus(resultMap));
			
			result.setOrderMoney(AliResponseUtil.getTotalAmount(resultMap));
			result.setOrderName(order.getOrderName());
			response.setData(result);
			try {
				//生成签名串
				Map<String, Object> map = (Map<String, Object>) MapUtils.objectToMap(result);
				String sign = this.signatureData(map, order.getAppId(),String.valueOf(order.getMerchantId()));
				response.setSign(sign);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		return response;
	}

}
