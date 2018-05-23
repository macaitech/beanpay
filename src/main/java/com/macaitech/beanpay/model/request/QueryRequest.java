/**
 * 
 */
package com.macaitech.beanpay.model.request;


import io.swagger.annotations.ApiModelProperty;

/**
 * @author yuhui.tang
 *	支付查询请求Model
 */
public class QueryRequest  extends BaseRequest {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(name="merchantOrderNo",value="订单号,商户系统内部的订单号,32个字符内、可包含字母",required=true)
	private String merchantOrderNo;
	
	@ApiModelProperty(name="tradeNo",value="支付系统交易流水号",required=true)
	private String tradeNo;
	
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
	
}
