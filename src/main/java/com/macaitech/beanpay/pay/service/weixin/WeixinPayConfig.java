/**
 * 
 */
package com.macaitech.beanpay.pay.service.weixin;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.macaitech.beanpay.model.PayConfig;
import com.macaitech.beanpay.sdk.wxpay.IWXPayDomain;
import com.macaitech.beanpay.sdk.wxpay.WXPayConfig;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.PayConfigUtil;

/**
 * @author sz
 *
 */
public class WeixinPayConfig extends WXPayConfig {
	
	public static final Logger logger = LoggerFactory.getLogger(WeixinPayConfig.class);
	
	private static PayConfig _PayConfig = null;
	
	static {
		_PayConfig = PayConfigUtil.getWxPayConfig();
		if(_PayConfig == null) {
			throw new RuntimeException("微信支付渠道配置加载失败");
		}
		else {
			logger.info("load pay config:"+JsonUtil.toString(_PayConfig));
		}
	}

	/* (non-Javadoc)
	 * @see com.macaitech.beanpay.sdk.wxpay.WXPayConfig#getAppID()
	 */
	@Override
	public String getAppID() {
		//return "wxc664595f0bba74c8";
		return _PayConfig.getAppid();
	}

	/* (non-Javadoc)
	 * @see com.macaitech.beanpay.sdk.wxpay.WXPayConfig#getMchID()
	 */
	@Override
	public String getMchID() {
		//return "1460973902";
		return _PayConfig.getMerchantId();
	}

	/* (non-Javadoc)
	 * @see com.macaitech.beanpay.sdk.wxpay.WXPayConfig#getKey()
	 */
	@Override
	public String getKey() {
		//return "Z9sbfKtEd801dHbfztmi6k12o9GCTtrp";
		return _PayConfig.getKey();
	}

	/* (non-Javadoc)
	 * @see com.macaitech.beanpay.sdk.wxpay.WXPayConfig#getCertStream()
	 */
	@Override
	public InputStream getCertStream() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.macaitech.beanpay.sdk.wxpay.WXPayConfig#getWXPayDomain()
	 */
	@Override
	public IWXPayDomain getWXPayDomain() {
		return new WxDomain();
	}
	
	/**
	 * 返回支付渠道配置
	 * @return
	 */
	@Override
	public PayConfig getPayConfig() {
		return _PayConfig;
	}

}
