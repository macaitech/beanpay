/**
 * 
 */
package com.macaitech.beanpay.model.request;


import org.apache.commons.lang.StringUtils;

import com.macaitech.beanpay.enums.PayChannelType;
import com.macaitech.beanpay.enums.PayMethodType;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yuhui.tang
 *	下单支付请求Model
 */
public class OrderRequest  extends BaseRequest {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(name="clientIp",value="客户端IP",required=true)
	private String clientIp;
	
	@ApiModelProperty(name="payChannel",value="支付渠道,支付宝:alipay,微信支付:wxpay,京东支付:jdpay",required=true)
	private String payChannel;
//	@ApiModelProperty(name="paymethod",value="支付渠道,支付宝:1,微信支付:2,京东支付:3",required=true)
//	private int paymethod;
	@ApiModelProperty(name="payWay",value="支付方式,调起移动端:app,调起H5页面:h5,调起PC端web:pc,后端支付:server",required=true)
	private String payWay = "app";//默认app
	@ApiModelProperty(name="requestTime",value="请求发送的时间，格式yyyy-MM-dd HH:mm:ss",required=true)
	private String requestTime;
	@ApiModelProperty(name="expireSecond",value="订单的失效时长,单位：秒,默认时效时间为2小时",required=false)
	private int expireSecond = 2*3600;
	
	@ApiModelProperty(name="merchantOrderNo",value="订单号,商户系统内部的订单号,32个字符内、可包含字母",required=true)
	private String merchantOrderNo;
	@ApiModelProperty(name="orderName",value="订单名",required=false)
	private String orderName;
	@ApiModelProperty(name="orderDesc",value="订单描述",required=false)
	private String orderDesc;
	@ApiModelProperty(name="orderMoney",value="订单金额，单位分",required=true)
	private int orderMoney = 0;
	
	@ApiModelProperty(name="notifyUrl",value="异步通知地址，最多3次回调",required=true)
	private String notifyUrl;
	@ApiModelProperty(name="callbackUrl",value="支付成功后跳转的URL",required=true)
	private String returnUrl;
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
//		if(!StringUtils.isEmpty(this.payChannel)) {
//			if(PayMethodType.getPayMethodType(this.payChannel)!=null && this.paymethod==0) {
//				this.paymethod = PayMethodType.getPayMethodType(this.payChannel).getValue();
//			}
//		}
	}
	
//	public int getPaymethod() {
//		return paymethod;
//	}
//	public void setPaymethod(int paymethod) {
//		this.paymethod = paymethod;
//		if(this.paymethod>0) {
//			if(PayMethodType.getPayMethodType(this.paymethod)!=null) {
//				this.payChannel = PayMethodType.getPayMethodType(this.paymethod).getKey();
//			}
//		}
//	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public int getExpireSecond() {
		return expireSecond;
	}
	public void setExpireSecond(int expireSecond) {
		this.expireSecond = expireSecond;
	}
	
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public int getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(int orderMoney) {
		this.orderMoney = orderMoney;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

}
