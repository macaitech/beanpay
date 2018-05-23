package com.macaitech.beanpay.db.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单entity
 * @author yuhui.tang
 *
 */
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	private long id;// '主键，自增'
	private String orderNo;// '我方订单编号'
	private String merchantOrderNo ;//商户订单编号
	private int merchantId;// '商户Id'
	private String appId; // '应用Id hn_app.appid'
	private String payChannel;// '支付渠道'
	private int payMethod;// '支付渠道alipay:支付宝支付，wxpay:微信支付，jdpay:京东支付'
	private String payWay;//
	private String orderName;// '订单名称或描述'
	private int orderMoney = 0;// '订单金额'
	private String clientIp;// '客户端ip'
	private int expireSecond = 0;// '订单过期时间'
	private int payStatus;// '支付结果状态 200:交易成功，300:交易未支付，400:交易失败，500:交易关闭'
	private String payMsg;// '支付返回消息'
	private Date payTime ;// 支付系统支付完成时间
	private String tradeNo;// '支付渠道交易流水号'
	private String tradeStatus;// '支付渠道返回码'
	private String tradeMsg;// '支付渠道返回信息'
	private String orderDesc;// '订单描述'
	private String notifyUrl;// '异步回调地址'
	private String returnUrl;// '页面返回地址'
	private Date requestTime;// '订单请求发起时间'
	private Date createTime;// '创建时间'
	private Date updateTime;// '更新时间'
	private String tradeOrderUrl;//支付渠道OrderUrl
	private String tradePrepay;//支付渠道的Prepay
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}
	
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public int getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
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
	public int getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(int orderMoney) {
		this.orderMoney = orderMoney;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public int getExpireSecond() {
		return expireSecond;
	}
	public void setExpireSecond(int expireSecond) {
		this.expireSecond = expireSecond;
	}
	public int getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayMsg() {
		return payMsg;
	}
	public void setPayMsg(String payMsg) {
		this.payMsg = payMsg;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
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
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getTradeOrderUrl() {
		return tradeOrderUrl;
	}
	public void setTradeOrderUrl(String tradeOrderUrl) {
		this.tradeOrderUrl = tradeOrderUrl;
	}
	public String getTradePrepay() {
		return tradePrepay;
	}
	public void setTradePrepay(String tradePrepay) {
		this.tradePrepay = tradePrepay;
	}

}
