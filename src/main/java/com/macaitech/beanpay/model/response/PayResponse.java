/**
 * 
 */
package com.macaitech.beanpay.model.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.macaitech.beanpay.common.ResultCodes;
import com.macaitech.beanpay.model.OrderResult;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yuhui.tang
 * 响应结果Model
 */
public class PayResponse implements Serializable {
	@JsonIgnore
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(name="code",value="结果编码",required=true)
	private int code;	// 状态码
	@ApiModelProperty(name="message",value="结果信息",required=true)
	private String message = "成功";
	@ApiModelProperty(name="sign",value="签名",required=true)
	private String sign = "";
	@ApiModelProperty(name="data",value="数据对象",required=true)
	private OrderResult data = new OrderResult();	
	
	public PayResponse() {
		code = ResultCodes.Result_Code_200;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public OrderResult getData() {
		return data;
	}
	public void setData(OrderResult data) {
		this.data = data;
	}

}
