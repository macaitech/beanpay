/**
 * 
 */
package com.macaitech.beanpay.pay.service.weixin;

import com.macaitech.beanpay.sdk.wxpay.IWXPayDomain;
import com.macaitech.beanpay.sdk.wxpay.WXPayConfig;
import com.macaitech.beanpay.sdk.wxpay.WXPayConstants;

/**
 * @author sz
 *
 */
public class WxDomain implements IWXPayDomain {

	/* (non-Javadoc)
	 * @see com.macaitech.beanpay.sdk.wxpay.IWXPayDomain#report(java.lang.String, long, java.lang.Exception)
	 */
	@Override
	public void report(String domain, long elapsedTimeMillis, Exception ex) {


	}

	/* (non-Javadoc)
	 * @see com.macaitech.beanpay.sdk.wxpay.IWXPayDomain#getDomain(com.macaitech.beanpay.sdk.wxpay.WXPayConfig)
	 */
	@Override
	public DomainInfo getDomain(WXPayConfig config) {
		DomainInfo domainInfo = new DomainInfo(WXPayConstants.DOMAIN_API, true);
		return domainInfo;
	}

}
