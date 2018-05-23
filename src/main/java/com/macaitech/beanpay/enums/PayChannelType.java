/**
 * 
 */
package com.macaitech.beanpay.enums;


/**
 * @author sz
 *
 */
public enum PayChannelType {
	PayChannel_ALIPAY("alipay","支付宝支付"),
	PayChannel_WXPAY("wxpay","微信支付"),
	PayChannel_JDPAY("jdpay","京东支付");
	
	private String key;
	private String value;

	private PayChannelType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	
	public static PayChannelType getTradeType(String key){
		for(PayChannelType tradeType :PayChannelType.values()){
			if(tradeType.getKey().equalsIgnoreCase(key)){
				return tradeType;
			}
		}
		return null;
	}
	
	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
