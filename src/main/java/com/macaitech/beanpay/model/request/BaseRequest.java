/**
 * 
 */
package com.macaitech.beanpay.model.request;

import java.io.Serializable;
import java.util.UUID;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author sz
 *
 */
public class BaseRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(name="appId",value="应用ID",required=true)
	private String appId;
	@ApiModelProperty(name="merchantId",value="商户ID",required=true)
	private String merchantId;
	@ApiModelProperty(name="version",value="接口版本，目前默认为：1.0",required=true)
	private String version = "1.0";
	@ApiModelProperty(name="sign",value="签名",required=true)
	private String sign;
	
	public String getUuid() {
		String uuid =  UUID.randomUUID().toString();
		uuid = uuid.replaceAll("-", "");
		return uuid;
	}
	
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}


}
