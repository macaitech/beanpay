/**
 * 
 */
package com.macaitech.beanpay.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.macaitech.beanpay.enums.PayStatus;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author sz
 *
 */
public class QueryResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(name="appId",value="应用ID",required=true)
	private String appId;
	@ApiModelProperty(name="merchantId",value="商户ID",required=true)
	private String merchantId;
	
	private int paymethod;
	private String payChannel;
	private String payWay;

	@ApiModelProperty(name="merchantOrderNo",value="应用或商户订单号",required=true)
	private String merchantOrderNo;
	
	@ApiModelProperty(name="tradeNo",value="支付系统交易流水号",required=true)
	private String tradeNo;
	
	private String orderName;// '订单名称或描述'

	@ApiModelProperty(name="orderMoney",value="订单金额，单位分",required=true)
	private int orderMoney;
	
	
	@ApiModelProperty(name="paystatus",value="支付结果状态",required=true)
	private int paystatus ;//不可初始化
	@ApiModelProperty(name="paymsg",value="支付结果信息",required=true)
	private String paymsg;
	private Date payTime ;// 支付系统支付完成时间
	
	private String tradeStatus;// '支付渠道返回码'
	private String tradeMsg;// '支付渠道返回信息'
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public int getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(int paystatus) {
		this.paystatus = paystatus;
	}

	public String getPaymsg() {
		if(StringUtils.isEmpty(this.paymsg)) {
			PayStatus payStatus = PayStatus.getPayStatus(this.paystatus);
			if(payStatus!=null) return payStatus.getValue();
		}
		return paymsg;
	}

	public void setPaymsg(String paymsg) {
		this.paymsg = paymsg;
	}

	public int getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(int orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getTradeMsg() {
		return tradeMsg;
	}

	public void setTradeMsg(String tradeMsg) {
		this.tradeMsg = tradeMsg;
	}
	
	
}
