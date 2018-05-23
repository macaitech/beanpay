/**
 * 
 */
package com.macaitech.beanpay.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import com.macaitech.beanpay.util.PlatformProperties;

/**
 * @author yuhui.tang
 * 支付渠道配置
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "payment")
public class PayConfig {
	
	private String appid;
	private String merchantId;
	private String partner;
	private String seller;
	private String key;
	private String notify_url;
	private String public_key;
	private String private_key;
	private String sandbox;
	private String signtype;
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getNotify_url() {
		if(!StringUtils.isEmpty(this.notify_url) && !this.notify_url.startsWith("http")) {
			return PlatformProperties.getProperty("pay.notify.domain")+this.notify_url;
		}
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	
	public String getPublic_key() {
		return public_key;
	}
	public void setPublic_key(String public_key) {
		this.public_key = public_key;
	}
	
	public String getPrivate_key() {
		return private_key;
	}
	public void setPrivate_key(String private_key) {
		this.private_key = private_key;
	}
	public String getSandbox() {
		return sandbox;
	}
	public void setSandbox(String sandbox) {
		this.sandbox = sandbox;
	}
	public String getSigntype() {
		return signtype;
	}
	public void setSigntype(String signtype) {
		this.signtype = signtype;
	}
	
	
}
