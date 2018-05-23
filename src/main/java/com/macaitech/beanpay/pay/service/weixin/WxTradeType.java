/**
 * 
 */
package com.macaitech.beanpay.pay.service.weixin;

/**
 * @author yuhui.tang
 * 微信交易类型
 */
public enum WxTradeType {
	//JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，MICROPAY--刷卡支付,MWEB--H5支付
	TradeType_App("app","APP"),
	TradeType_H5("h5","MWEB");
	
	private String key;
	private String value;

	private WxTradeType(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	
	public static WxTradeType getTradeType(String key){
		for(WxTradeType tradeType :WxTradeType.values()){
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
