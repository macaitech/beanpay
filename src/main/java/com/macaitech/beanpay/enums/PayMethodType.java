/**
 * 
 */
package com.macaitech.beanpay.enums;


/**
 * @author yuhui.tang
 * paychannel 转 paymethod 
 * paymethod 设计的目的是兼容，是个累赘
 */
public enum PayMethodType {
	PayMethod_ALIPAY("alipay",1),
	PayMethod_WXPAY("wxpay",2),
	PayMethod_JDPAY("jdpay",3);
	
	private String key;
	private int value;

	private PayMethodType(String key, int value) {
		this.key = key;
		this.value = value;
	}
	
	
	public static PayMethodType getPayMethodType(String key){
		for(PayMethodType type :PayMethodType.values()){
			if(type.getKey().equalsIgnoreCase(key)){
				return type;
			}
		}
		return null;
	}
	
	public static PayMethodType getPayMethodType(int value){
		for(PayMethodType type :PayMethodType.values()){
			if(type.getValue() == value){
				return type;
			}
		}
		return null;
	}
	
	public String getKey() {
		return key;
	}

	public int getValue() {
		return value;
	}
}
