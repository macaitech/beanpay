/**
 * 
 */
package com.macaitech.beanpay.util;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import com.macaitech.beanpay.model.PayConfig;

/**
 * @author yuhui.tang
 *  支付配置工具
 */
public class PayConfigUtil {
	
	public static final Logger logger = LoggerFactory.getLogger(PayConfigUtil.class);

	public static void main(String[] args) {
		PayConfig payConfig = PayConfigUtil.getAliPayConfig();
		System.out.println(JsonUtil.toString(payConfig));
	}
	/**
	 * 阿里支付配置
	 * @return
	 */
	public static PayConfig getAliPayConfig() {
		try {
			String xmlPath = "classpath:pay/pay-ali.xml";
			return getPayConfig(xmlPath);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	
	/**
	 * 微信支付配置
	 * @return
	 */
	public static PayConfig getWxPayConfig() {
		try {
			String xmlPath = "classpath:pay/pay-wx.xml";
			return getPayConfig(xmlPath);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	
	public static PayConfig getPayConfig(String xmlFile) {
		String xmlPath;
		try {
			xmlPath = ResourceUtils.getFile(xmlFile).getAbsolutePath();
			PayConfig payConfig =(PayConfig) XmlTestUtil.convertXmlFileToObject(PayConfig.class, xmlPath);
			return payConfig;
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	
}
