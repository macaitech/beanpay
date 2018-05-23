/**
 * 
 */
package com.macaitech.beanpay.pay.service.weixin;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.macaitech.beanpay.common.ResultCodes;
import com.macaitech.beanpay.db.entity.Order;
import com.macaitech.beanpay.enums.PayChannelType;
import com.macaitech.beanpay.enums.PayMethodType;
import com.macaitech.beanpay.enums.PayStatus;
import com.macaitech.beanpay.model.response.QueryResponse;
import com.macaitech.beanpay.model.QueryResult;
import com.macaitech.beanpay.pay.service.BasePayService;
import com.macaitech.beanpay.pay.service.IQueryPayChannelOrderService;
import com.macaitech.beanpay.sdk.wxpay.WXPay;
import com.macaitech.beanpay.sdk.wxpay.WXPayConfig;
import com.macaitech.beanpay.util.DateUtils;
import com.macaitech.beanpay.util.MapUtils;

/**
 * @author yuhui.tang
 * 订单查询
 */
@Service
public class OrderQueryWxService extends BasePayService implements IQueryPayChannelOrderService{
	

	@Override
	public QueryResponse invokeChannelQueryOrder(Order order) throws ParseException {
		QueryResponse response = null;
		WXPayConfig wxPayConfig  =new WeixinPayConfig();
		WXPay wxPay = new WXPay(wxPayConfig,false,false);
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("out_trade_no", order.getOrderNo());
		reqData.put("transaction_id", order.getTradeNo());
		Map<String, String> resultMap = null ;
		try {
			resultMap = wxPay.orderQuery(reqData);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		if(resultMap!=null) {
			response = this.enboxResponse(order, resultMap);
			return response;
		}
		return null;
	}
	
	/**
	 * 封装响应结果
	 * @param queryRequest
	 * @param resultMap
	 * @return
	 * @throws ParseException 
	 */
	private QueryResponse enboxResponse(Order order,Map<String, String> resultMap) throws ParseException {
		QueryResponse response = new QueryResponse();
		QueryResult queryResult = new QueryResult();
		queryResult.setTradeStatus(WxResponseUtil.getTradeState(resultMap));
		queryResult.setTradeMsg(WxResponseUtil.getTradeStateDesc(resultMap));
		//获取支付状态
		PayStatus payStatus = WxResponseUtil.getPayStatus(resultMap);
		queryResult.setPaystatus(payStatus.getKey());
		
		if(!WxResponseUtil.isSuccessed(resultMap)) {//不成功
			response.setCode(ResultCodes.Result_Code_50001);
			response.setMessage(WxResponseUtil.getReturnMsg(resultMap));
		}
		else {
			queryResult.setMerchantId(String.valueOf(order.getMerchantId()));
			queryResult.setAppId(order.getAppId());
			
			queryResult.setMerchantOrderNo(order.getMerchantOrderNo());
			queryResult.setPayChannel(PayChannelType.PayChannel_WXPAY.getKey());
			queryResult.setPaymethod(PayMethodType.getPayMethodType(queryResult.getPayChannel()).getValue());
			
			queryResult.setTradeNo(WxResponseUtil.getTradeNo(resultMap));
			
			String payTime = WxResponseUtil.getPayTime(resultMap);
			if(!StringUtils.isEmpty(payTime)) {
				queryResult.setPayTime(DateUtils.parseDate(payTime, DateUtils.parsePatterns));
			}
			
			queryResult.setPaymsg(WxResponseUtil.getTradeStateDesc(resultMap));//渠道支付描述
			
			queryResult.setOrderMoney(WxResponseUtil.getTotalAmount(resultMap));
			response.setData(queryResult);
			try {
				//生成签名串
				Map<String, Object> map = (Map<String, Object>) MapUtils.objectToMap(queryResult);
				String sign = this.signatureData(map, order.getAppId(),String.valueOf(order.getMerchantId()));
				response.setSign(sign);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		return response;
	}

}
