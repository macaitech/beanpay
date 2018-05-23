package com.macaitech.beanpay.exception;

/**
 * 自定义业务异常
 *
 */
public class PayException extends RuntimeException {
	
	private static final long serialVersionUID = -7535678064045845850L;
	
	private int code;


	public PayException() {
	}

	public PayException(int code, String message) {
		super(message);
		this.code = code;
	}
	
	
	public PayException(int code, String message,Throwable e) {
		super(message,e);
		this.code = code;
	}
	
	

	public PayException(String msg) {
		super(msg);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
