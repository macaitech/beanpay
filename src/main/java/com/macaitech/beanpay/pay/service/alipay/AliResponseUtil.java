/**
 * 
 */
package com.macaitech.beanpay.pay.service.alipay;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.macaitech.beanpay.enums.PayStatus;
import com.macaitech.beanpay.util.DateUtils;

/**
 * @author yuhui.tang
 * 支付宝响应工具
 */
public class AliResponseUtil {
	
	/**
	 * 是否成功返回
	 * @param resultMap
	 * @return
	 */
	public static boolean isSuccessed(Map<String, Object> resultMap) {
		Object value = getResponseCode(resultMap);
		if(value!=null && value.toString().equalsIgnoreCase("10000")) {
			return true;
		}
		return false;
	}
	/**
	 * 业务处理失败的订单
	 * @param resultMap
	 * @return
	 */
	public static boolean isFailOrder(Map<String, Object> resultMap) {
		Object value = getResponseCode(resultMap);
		if(value!=null && value.toString().equalsIgnoreCase("40004")) {
			return true;
		}
		return false;
	}
	
	public static String getResponseCode(Map<String, Object> resultMap) {
		Object value  = resultMap.get("code");
		return value != null?value.toString():"";
	}
	
	/**
	 * 获取返回信息
	 * @param resultMap
	 * @return
	 */
	public static String getMsg(Map<String, Object> resultMap) {
		Object value = resultMap.get("sub_msg");
		if(value==null) {
			value = resultMap.get("msg");
		}
		return value != null?value.toString():"";
	}
	
	/**
	 * 获取返回码
	 * @param resultMap
	 * @return
	 */
	public static String getMsgCode(Map<String, Object> resultMap) {
		Object value = resultMap.get("sub_code");
		if(value==null) {
			value = resultMap.get("code");
		}
		return value != null?value.toString():"";
	}
	
	/**
	 * 商户网站唯一订单号
	 * @param resultMap
	 * @return
	 */
	public static String getOutTradeNo(Map<String, Object> resultMap) {
		Object value = resultMap.get("out_trade_no");
		return value != null?value.toString():"";
	}
	
	/**
	 * 该交易在支付宝系统中的交易流水号。最长64位
	 * @param resultMap
	 * @return
	 */
	public static String getTradeNo(Map<String, Object> resultMap) {
		Object value = resultMap.get("trade_no");
		return value != null?value.toString():"";
	}
	/**
	 * 支付状态
	 * @param resultMap
	 * @return
	 */
	public static PayStatus getPayStatus(Map<String, Object> resultMap) {
		Object value = getTradeStatus(resultMap);
		return toPayStatus(value.toString());
	}
	
	/**
	 * 交易状态
	 * @param resultMap
	 * @return
	 */
	public static String getTradeStatus(Map<String, Object> resultMap) {
		Object value = resultMap.get("trade_status");
		return value != null?value.toString():"";
	}
	
	/**
	 * 该笔订单的资金总额，支付宝单位为元，带小数点
	 * 本方法统一转换为分
	 * @param resultMap
	 * @return
	 */
	public static int getTotalAmount(Map<String, Object> resultMap) {
		Object value = resultMap.get("total_amount");
		BigDecimal decimal = new BigDecimal(value!=null?value.toString():"-1");
		decimal = decimal.multiply(new BigDecimal(100));
		return decimal.intValue();
	}
	
	/**
	 * 转换交易结果
	 * @param tradeStatus
	 * @return
	 */
	public static PayStatus toPayStatus(String tradeStatus) {
		//WAIT_BUYER_PAY	交易创建，等待买家付款
		//TRADE_CLOSED	未付款交易超时关闭，或支付完成后全额退款
		//TRADE_SUCCESS	交易支付成功
		//TRADE_FINISHED 交易结束，不可退款
		
		if(StringUtils.isEmpty(tradeStatus) || tradeStatus.equalsIgnoreCase("WAIT_BUYER_PAY")) {
			return PayStatus.PayStatus_UnPay;
		}
		else if (tradeStatus.equalsIgnoreCase("TRADE_CLOSED")) {
			return PayStatus.PayStatus_Closed;
		}
		else if (tradeStatus.equalsIgnoreCase("TRADE_SUCCESS")) {
			return PayStatus.PayStatus_OK;
		}
		else if (tradeStatus.equalsIgnoreCase("TRADE_FINISHED")) {
			//交易结束是什么鬼，为什么没有交易失败
			return PayStatus.PayStatus_Finished;
		}
		return PayStatus.PayStatus_UnPay;
	}
	
	/**
	 * 返回支付时间
	 * @param payStatus
	 * @param map
	 * @return
	 * @throws ParseException 
	 */
	public static Date getPayTime(PayStatus payStatus,Map<String,String> map) throws ParseException {
		Date payTime = null;
		//交易关闭时间
		if(payStatus == PayStatus.PayStatus_Closed  && !StringUtils.isEmpty(map.get("gmt_close"))) {
			payTime = DateUtils.parseDate(map.get("gmt_close"), DateUtils.parsePatterns);
		}
		//交易付款时间
		if(!StringUtils.isEmpty(map.get("gmt_payment"))) {
			payTime = DateUtils.parseDate(map.get("gmt_payment"), DateUtils.parsePatterns);
		}
		return payTime;
	}
	
	public static Map<String,String> convertMap(Map<String,Object> map){
		Map<String,String> tempMap = new HashMap<>();
		Iterator<String> itr = map.keySet().iterator();
		while(itr.hasNext()) {
			String key = itr.next();
			tempMap.put(key, map.get(key).toString());
		}
		return tempMap;
	}
}
