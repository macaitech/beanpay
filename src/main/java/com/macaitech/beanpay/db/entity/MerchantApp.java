/**
 * 
 */
package com.macaitech.beanpay.db.entity;

import java.io.Serializable;

/**
 * @author yuhui.tang
 * 商户App 
 */
public class MerchantApp implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id ;
	private int merchantId;
	private String appId;
	private String appName;
	private String merchantName;
	private String appKey;
	private String orderPrefix;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(int merchantId) {
		this.merchantId = merchantId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getOrderPrefix() {
		return orderPrefix;
	}
	public void setOrderPrefix(String orderPrefix) {
		this.orderPrefix = orderPrefix;
	}
	
}
