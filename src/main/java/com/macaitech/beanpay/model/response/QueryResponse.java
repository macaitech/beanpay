/**
 * 
 */
package com.macaitech.beanpay.model.response;

import java.io.Serializable;

import com.macaitech.beanpay.common.ResultCodes;
import com.macaitech.beanpay.model.QueryResult;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yuhui.tang
 * 响应结果Model
 */
public class QueryResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(name="code",value="结果编码",required=true)
	private int code;	// 状态码
	@ApiModelProperty(name="message",value="结果信息",required=true)
	private String message = "成功";
	@ApiModelProperty(name="sign",value="签名",required=true)
	private String sign = "";
	@ApiModelProperty(name="data",value="数据对象",required=true)
	private QueryResult data = new QueryResult();	
	
	public QueryResponse() {
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
	public QueryResult getData() {
		return data;
	}
	public void setData(QueryResult data) {
		this.data = data;
	}

}
