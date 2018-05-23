/**
 * 
 */
package com.macaitech.beanpay.pay.service.alipay;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.macaitech.beanpay.model.PayConfig;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.PayConfigUtil;

/**
 * @author yuhui.tang
 *
 */
public class AlipayConfig {
	public static final Logger logger = LoggerFactory.getLogger(AlipayConfig.class);
	
	public final static String PAY_GATEWAY = "https://openapi.alipay.com/gateway.do";
	
	public final static String PAY_GATEWAY_SANDBOX = "https://openapi.alipaydev.com/gateway.do";
	
	public static int TIME_OUT = 2*60;
	
	public static String CHARSET = "UTF-8";
	
	public static String FORMAT = "json";
			
	private static AlipayClient _AlipayClient = null;
	
	private static PayConfig _PayConfig = null;
	
	public static String getAppId() {
		return _PayConfig.getAppid();
	}
	/**
	 * 应用私钥
	 * @return
	 */
	public static String getPrivateKey() {
		return _PayConfig.getPrivate_key();
	}
	
	/**
	 * 支付渠道的公钥，非应用公钥
	 * @return
	 */
	public static String getPublicKey() {
		return _PayConfig.getPublic_key();
	}
	/**
	 * 是否沙箱环境
	 * @return
	 */
	public static boolean isSandBox() {
		String sandbox = _PayConfig.getSandbox();
		if(!StringUtils.isEmpty(sandbox) && sandbox.equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}
	public static String getSignType() {
		return _PayConfig.getSigntype();
	}
	
	public static String getPayChannelURL() {
		if(isSandBox()) {
			return PAY_GATEWAY_SANDBOX;
		}
		else {
			return PAY_GATEWAY;
		}
	}
	
	static {
		_PayConfig = PayConfigUtil.getAliPayConfig();
		if(_PayConfig == null) {
			throw new RuntimeException("支付宝渠道配置加载失败");
		}
		else {
			logger.info("load pay config:"+JsonUtil.toString(_PayConfig));
		}
	}
	
	public static AlipayClient getAlipayClient() {
		if(_AlipayClient==null) {
			_AlipayClient = new DefaultAlipayClient(AlipayConfig.getPayChannelURL(),
					_PayConfig.getAppid(), _PayConfig.getPrivate_key(), "json", AlipayConfig.CHARSET, _PayConfig.getPublic_key(), _PayConfig.getSigntype());
		}
		return _AlipayClient;
	}
	/**
	 * 返回支付渠道配置
	 * @return
	 */
	public static PayConfig getPayConfig() {
		return _PayConfig;
	}
}
