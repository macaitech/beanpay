/**
 * 
 */
package com.macaitech.beanpay.enums;


/**
 * @author yuhui.tang
 * 支付状态
 */
public enum PayStatus {
	PayStatus_OK(200,"交易成功"),
	PayStatus_UnPay(300,"交易未支付"),
	PayStatus_Fail(400,"交易失败"),
	PayStatus_Closed(500,"交易关闭"),
	PayStatus_Finished(600,"交易结束"),
	PayStatus_Refund(700,"交易退款"),
	PayStatus__Paying(800,"交易支付中");
	
	private int key;
	private String value;

	private PayStatus(int key, String value) {
		this.key = key;
		this.value = value;
	}
	
	
	public static PayStatus getPayStatus(int key){
		for(PayStatus payStatus :PayStatus.values()){
			if(payStatus.getKey() == key){
				return payStatus;
			}
		}
		return null;
	}
	
	public int getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
