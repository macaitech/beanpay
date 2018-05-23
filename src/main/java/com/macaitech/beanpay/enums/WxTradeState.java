/**
 * 
 */
package com.macaitech.beanpay.enums;

/**
 * @author yuhui.tang
 * 微信支付结果状态
 */
public enum WxTradeState {
	WxTradeState_OK("SUCCESS",200),
	WxTradeState_UnPay("NOTPAY",300),
	WxTradeState_Fail("PAYERROR",400),
	WxTradeState_Closed("CLOSED",500),
	WxTradeState_Finished("FINISHED",600),
	WxTradeState_Refund("REFUND",700),
	WxTradeState_Paying("USERPAYING",800);
	
	private String key;
	private int value;

	private WxTradeState(String key, int value) {
		this.key = key;
		this.value = value;
	}
	
	
	public static WxTradeState getWxTradeState(String key){
		for(WxTradeState type :WxTradeState.values()){
			if(type.getKey() == key){
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
