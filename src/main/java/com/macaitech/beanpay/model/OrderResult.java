package com.macaitech.beanpay.model;

import java.io.Serializable;

import com.macaitech.beanpay.enums.PayStatus;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author yuhui.tang
 * 订单结果
 */
public class OrderResult implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(name="merchantOrderNo",value="应用或商户订单号",required=true)
	private String merchantOrderNo;
	private int paymethod;
	private String payChannel;
	private String payWay;
	
	private String orderUrl;
	private String prepay;
	
	private int payStatus = PayStatus.PayStatus_UnPay.getKey();//初始化为未支付
	
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	public int getPaymethod() {
		return paymethod;
	}
	public void setPaymethod(int paymethod) {
		this.paymethod = paymethod;
	}
	
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getOrderUrl() {
		return orderUrl;
	}
	public void setOrderUrl(String orderUrl) {
		this.orderUrl = orderUrl;
	}
	public String getPrepay() {
		return prepay;
	}
	public void setPrepay(String prepay) {
		this.prepay = prepay;
	}
	
	public int getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayStatusDesc() {
		if(PayStatus.getPayStatus(this.payStatus) !=null) {		
			return PayStatus.getPayStatus(this.payStatus).getValue();
		}
		return PayStatus.PayStatus_UnPay.getValue();
	}
	
}
