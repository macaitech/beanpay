package com.macaitech.beanpay.common;

import java.io.Serializable;

import com.macaitech.beanpay.util.JsonUtil;


public class ResultData implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code;	// 状态码
	private String message = "成功";
	private Object data;	
	
	public ResultData() {
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public String toJson() {
		return JsonUtil.toString(this);
	}

}
