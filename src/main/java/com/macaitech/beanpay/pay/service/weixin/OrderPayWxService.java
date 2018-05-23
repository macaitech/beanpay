/**
 * 
 */
package com.macaitech.beanpay.pay.service.weixin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.macaitech.beanpay.common.ResultCodes;
import com.macaitech.beanpay.db.entity.Order;
import com.macaitech.beanpay.model.OrderResult;
import com.macaitech.beanpay.model.response.PayResponse;
import com.macaitech.beanpay.pay.service.AbstractOrderPayTemplate;
import com.macaitech.beanpay.sdk.wxpay.WXPay;
import com.macaitech.beanpay.sdk.wxpay.WXPayConfig;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.MapUtils;


/**
 * @author yuhui.tang
 * 下单支付——微信
 */
@Service
public class OrderPayWxService extends AbstractOrderPayTemplate{
	
	/**
	 * 下单支付
	 * @param order
	 */
	@Override
	public PayResponse invokePayChannel(String uuid,Order order) {
		WXPayConfig wxPayConfig  =new WeixinPayConfig();
		WXPay wxPay = new WXPay(wxPayConfig,false,false);
		logger.info("pay->invokePayChannel("+uuid+"),wx order+"+JsonUtil.toString(order));
		Map<String, String> reqData = this.enboxOrder(wxPayConfig,order);
		PayResponse response = null;
		try {
			logger.info("pay->invokePayChannel("+uuid+"),wx request+"+JsonUtil.toString(reqData));
			Map<String, String> resultMap = wxPay.unifiedOrder(reqData);
			response = enboxResponse(order, resultMap);
			logger.info("pay->invokePayChannel("+uuid+"),wx response+"+JsonUtil.toString(response));
		} catch (Exception e) {
			logger.error("pay->invokePayChannel("+uuid+"),wx error+"+JsonUtil.toString(order));
			logger.error(e.getMessage(),e);
		}
		return response;
	}
	
	/**
	 * 封装请求参数
	 * @param wxPayConfig
	 * @param order
	 * @return
	 */
	public Map<String, String> enboxOrder(WXPayConfig wxPayConfig,Order order){
		Map<String, String> reqData = new HashMap<String, String>();
		reqData.put("body", order.getOrderName());
		reqData.put("out_trade_no", order.getOrderNo());//我方订单号
		reqData.put("total_fee", String.valueOf(order.getOrderMoney()));
		reqData.put("spbill_create_ip",order.getClientIp());
		// 设置异步通知地址,通知到hipay，然后再由hipay通知到商户
		reqData.put("notify_url", wxPayConfig.getPayConfig().getNotify_url());
		reqData.put("trade_type", WxTradeType.getTradeType(order.getPayWay().toLowerCase()).getValue());
		return reqData;
	}
	/**
	 * 
	 * @param orderRequest
	 * @param resultMap
	 * @return
	 */
	private PayResponse enboxResponse(Order order,Map<String, String> resultMap) {
		PayResponse response = new PayResponse();
		if(!WxResponseUtil.isSuccessed(resultMap)) {//不成功
			response.setCode(ResultCodes.Result_Code_50001);
			response.setMessage(WxResponseUtil.getReturnMsg(resultMap));
		}
		else {
			OrderResult orderResult = new OrderResult();
			orderResult.setMerchantOrderNo(order.getMerchantOrderNo());
			orderResult.setPaymethod(order.getPayMethod());
			orderResult.setPayChannel(order.getPayChannel());
			orderResult.setPayWay(order.getPayWay());
			orderResult.setOrderUrl(WxResponseUtil.getMwebUrl(resultMap));
			orderResult.setPrepay(WxResponseUtil.getPrepayId(resultMap));
			response.setData(orderResult);
			try {
				//生成签名串
				Map<String, Object> map = (Map<String, Object>) MapUtils.objectToMap(orderResult);
				String sign = super.signatureData(map, order.getAppId(),String.valueOf(order.getMerchantId()));
				response.setSign(sign);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		return response;
	}
}
