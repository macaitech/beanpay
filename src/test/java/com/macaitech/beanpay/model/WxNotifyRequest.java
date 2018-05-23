/**
 * 
 */
package com.macaitech.beanpay.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author yuhui.tang
 * 微信回调请求
 */ 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class WxNotifyRequest {
	 private String appid = "wxc664595f0bba74c8";
	  private String attach = "支付测试";
	  private String bank_type = "CFT";
	  private String fee_type = "CNY";
	  private String is_subscribe = "Y";
	  private String mch_id = "10000100";
	  private String nonce_str = "5d2b6c2a8db53831f7eda20af46e531c";
	  private String openid = "oUpF8uMEb4qRXf22hE3X68TekukE";
	  private String out_trade_no = "10180420180418007";
	  private String result_code = "SUCCESS";
	  private String return_code = "SUCCESS";
	  private String sign = "B552ED6B279343CB493C5DD0D78AB241";
	  private String sub_mch_id = "10000100";
	  private String time_end = "20140903131540";
	  private String total_fee = "1";
	  private String coupon_count = "1";
	  private String coupon_type = "CASH";
	  private String coupon_id = "10000";
	  private String coupon_fee = "100";
	  private String trade_type = "JSAPI";
	  private String transaction_id = "1004400740201409030005092168";
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getBank_type() {
		return bank_type;
	}
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}
	public String getFee_type() {
		return fee_type;
	}
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	public String getIs_subscribe() {
		return is_subscribe;
	}
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getReturn_code() {
		return return_code;
	}
	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSub_mch_id() {
		return sub_mch_id;
	}
	public void setSub_mch_id(String sub_mch_id) {
		this.sub_mch_id = sub_mch_id;
	}
	public String getTime_end() {
		return time_end;
	}
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
	public String getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	public String getCoupon_count() {
		return coupon_count;
	}
	public void setCoupon_count(String coupon_count) {
		this.coupon_count = coupon_count;
	}
	public String getCoupon_type() {
		return coupon_type;
	}
	public void setCoupon_type(String coupon_type) {
		this.coupon_type = coupon_type;
	}
	public String getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(String coupon_id) {
		this.coupon_id = coupon_id;
	}
	public String getCoupon_fee() {
		return coupon_fee;
	}
	public void setCoupon_fee(String coupon_fee) {
		this.coupon_fee = coupon_fee;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	  
	  
	  
}
