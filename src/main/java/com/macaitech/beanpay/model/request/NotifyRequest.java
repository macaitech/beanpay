/**
 * 
 */
package com.macaitech.beanpay.model.request;

import java.io.Serializable;
import java.util.Date;

import com.macaitech.beanpay.enums.PayStatus;
/**
 * @author yuhui.tang
 * 回调请求
 */
public class NotifyRequest  extends BaseRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String signType;
	
	private String tradeNo;//支付渠道交易流水号
	
	private String orderNo;//商户订单号
	
	private String resultCode ;//结果码

	private String tradeStatus;//业务结果
	
	private PayStatus payStatus; //支付状态
	
	private String sellerId;
	
	private int totalAmount;//订单金额
	
	private int receiptAmount;//实际支付金额
	
	private Date createTime;//订单创建时间
	
	private Date payTime;//订单支付时间
	
	private Date closeTime;//订单关闭时间
	
	private String errorCode;//失败错误码
	
	private String errorMsg ;//失败错误信息
	
	private String notifyText;//回调通知文本

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public int getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(int receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getNotifyText() {
		return notifyText;
	}

	public void setNotifyText(String notifyText) {
		this.notifyText = notifyText;
	}

	public PayStatus getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(PayStatus payStatus) {
		this.payStatus = payStatus;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}	
	
}
