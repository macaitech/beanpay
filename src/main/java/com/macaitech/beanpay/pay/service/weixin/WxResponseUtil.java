/**
 * 
 */
package com.macaitech.beanpay.pay.service.weixin;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.macaitech.beanpay.enums.PayStatus;
import com.macaitech.beanpay.enums.WxTradeState;

/**
 * @author yuhui.tang
 * 微信响应工具
 */
public class WxResponseUtil {
	
	/**
	 * 是否成功返回
	 * @param resultMap
	 * @return
	 */
	public static boolean isSuccessed(Map<String, String> resultMap) {
		String resultCode = resultMap.get("result_code");
		if(!StringUtils.isEmpty(resultCode) && resultCode.equalsIgnoreCase("SUCCESS")) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取返回信息
	 * @param resultMap
	 * @return
	 */
	public static String getReturnMsg(Map<String, String> resultMap) {
		String value = resultMap.get("err_code_des");
		if(StringUtils.isEmpty(value)) {
			value = resultMap.get("return_msg");
		}
		return value != null?value.toString():"";
	}
	
	/**
	 * 获取返回码
	 * @param resultMap
	 * @return
	 */
	public static String getReturnCode(Map<String, String> resultMap) {
		String value = resultMap.get("err_code");
		if(StringUtils.isEmpty(value)) {
			value = resultMap.get("result_code");
		}
		return value != null?value.toString():"";
	}
	
	/**
	 * 支付跳转链接
	 * @param resultMap
	 * @return
	 */
	public static String getMwebUrl(Map<String, String> resultMap) {
		String value = resultMap.get("mweb_url");
		return value != null?value.toString():"";
	}
	
	/**
	 * 预支付交易会话标识
	 * @param resultMap
	 * @return
	 */
	public static String getPrepayId(Map<String, String> resultMap) {
		String value = resultMap.get("prepay_id");
		return value != null?value.toString():"";
	}
	
	/**
	 * 商户网站唯一订单号
	 * @param resultMap
	 * @return
	 */
	public static String getOutTradeNo(Map<String, String> resultMap) {
		Object value = resultMap.get("out_trade_no");
		return value != null?value.toString():"";
	}
	
	/**
	 * 微信支付订单号
	 * @param resultMap
	 * @return
	 */
	public static String getTradeNo(Map<String, String> resultMap) {
		Object value = resultMap.get("transaction_id");
		return value != null?value.toString():"";
	}
	/**
	 * 该笔订单的资金总额,单位为分
	 * @param resultMap
	 * @return
	 */
	public static int getTotalAmount(Map<String, String> resultMap) {
		Object value = resultMap.get("total_fee");
		if(value==null) {
			return 0;
		}
		else {
			return Integer.parseInt(value.toString());
		}
	}
	/**
	 * 获取支付状态
	 * @param resultMap
	 * @return
	 */
	public static PayStatus getPayStatus(Map<String, String> resultMap) {
		String tradeState = getTradeState(resultMap);
		if(!StringUtils.isEmpty(tradeState) && WxTradeState.getWxTradeState(tradeState)!=null) {
			return PayStatus.getPayStatus(WxTradeState.getWxTradeState(tradeState).getValue());
		}
		return PayStatus.PayStatus_UnPay;
	}
	
	/**
	 * 交易状态
	 * @param resultMap
	 * @return
	 */
	public static String getTradeState(Map<String, String> resultMap) {
		Object value = resultMap.get("trade_state");
		return value != null?value.toString():"";
	}
	
	public static String getPayTime(Map<String, String> resultMap) {
		Object value = resultMap.get("time_end");
		return value != null?value.toString():"";
	}
	
	
	/**
	 * 交易状态描述
	 * @param resultMap
	 * @return
	 */
	public static String getTradeStateDesc(Map<String, String> resultMap) {
		Object value = resultMap.get("trade_state_desc");
		return value != null?value.toString():"";
	}
}
