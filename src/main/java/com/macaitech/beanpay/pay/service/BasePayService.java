/**
 * 
 */
package com.macaitech.beanpay.pay.service;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macaitech.beanpay.common.ResultCodes;
import com.macaitech.beanpay.db.entity.MerchantApp;
import com.macaitech.beanpay.db.entity.Order;
import com.macaitech.beanpay.db.service.MerchantAppService;
import com.macaitech.beanpay.exception.PayException;
import com.macaitech.beanpay.model.OrderResult;
import com.macaitech.beanpay.model.request.OrderRequest;
import com.macaitech.beanpay.model.response.PayResponse;
import com.macaitech.beanpay.signature.SignatureUtil;
import com.macaitech.beanpay.util.DateUtils;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.MapUtils;
import com.macaitech.beanpay.util.PlatformProperties;


/**
 * @author yuhui.tang
 * 基础Service
 */
@Service
public class BasePayService extends BaseService {
	@Autowired
	private MerchantAppService merchantAppService;
	/**
	 * 老单，封装返回
	 * @param order
	 * @return
	 */
	public PayResponse extractExistedOrder(Order order) {
		PayResponse response = new PayResponse();
		OrderResult orderResult = new OrderResult();
		orderResult.setMerchantOrderNo(order.getMerchantOrderNo());//商户订单号
		orderResult.setOrderUrl(order.getTradeOrderUrl());
		orderResult.setPrepay(order.getTradePrepay());
		orderResult.setPayChannel(order.getPayChannel());
		orderResult.setPayWay(order.getPayWay());
		orderResult.setPayStatus(order.getPayStatus());
		orderResult.setPaymethod(order.getPayMethod());
		response.setData(orderResult);
		try {
			//生成签名串
			Map<String, Object> map = (Map<String, Object>) MapUtils.objectToMap(orderResult);
			String sign = this.signatureData(map, order.getAppId(),String.valueOf(order.getMerchantId()));
			response.setSign(sign);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return response;
	}
	
	/**
	 * 封装订单
	 * @param orderRequest
	 * @return
	 */
	public Order toOrder(MerchantApp merchantApp,OrderRequest orderRequest) {
		Order order = new Order();
		order.setAppId(orderRequest.getAppId());
		order.setClientIp(orderRequest.getClientIp());
		
		order.setExpireSecond(orderRequest.getExpireSecond());
		order.setMerchantId(Integer.parseInt(orderRequest.getMerchantId()));
		order.setNotifyUrl(orderRequest.getNotifyUrl());
		order.setOrderDesc(orderRequest.getOrderDesc());
		order.setOrderMoney(orderRequest.getOrderMoney());
		order.setOrderName(orderRequest.getOrderName());
		order.setMerchantOrderNo(orderRequest.getMerchantOrderNo());
		//确定我方订单流失号，向支付渠道请求的我方订单号
		String orderNo = merchantApp.getOrderPrefix()+orderRequest.getMerchantOrderNo();
		order.setOrderNo(orderNo);
		order.setPayChannel(orderRequest.getPayChannel());
		//order.setPayMethod(orderRequest.getPaymethod());
		order.setPayWay(orderRequest.getPayWay());
		
		order.setRequestTime(DateUtils.parseDate(orderRequest.getRequestTime()));
		order.setReturnUrl(orderRequest.getReturnUrl());
		return order;
	}
	
	public void validRequst(OrderRequest orderRequest) {
		if(StringUtils.isEmpty(orderRequest.getAppId()) 
				|| StringUtils.isEmpty(orderRequest.getMerchantId())
				|| StringUtils.isEmpty(orderRequest.getMerchantOrderNo()) 
						|| StringUtils.isEmpty(orderRequest.getMerchantOrderNo())
								|| StringUtils.isEmpty(orderRequest.getMerchantOrderNo() )
								) {
			throw new PayException(ResultCodes.Result_Code_50001, ResultCodes.Result_Message_50001);
		}
	}
	
	/**
	 * 获取商户
	 * @param appId
	 * @return
	 */
	public MerchantApp getMerchantApp(String appId,String merchantId) {
		logger.info("pay->getMerchantApp, appId:"+appId);
		MerchantApp merchantApp = this.merchantAppService.getByAppId(appId);
		if(merchantApp == null || !String.valueOf(merchantApp.getMerchantId()).equals(merchantId)) {
			logger.info("商户id或appId不存在"+" "+appId+" "+merchantId);
			throw new PayException(ResultCodes.Result_Code_50001, "商户id或appId不存在");
		}
		else {
			return merchantApp;
		}
	}
	
	/**
	 * 验证签名
	 * @param map
	 * @param merchantApp
	 * @return
	 */
	public void validSignature(Map<String, Object> map,MerchantApp merchantApp) {
		String signatureText = this.signatureData(map, merchantApp);
		logger.info("pay->validSignature:"+signatureText);
		String enableSignature = PlatformProperties.getProperty("enable.signature");
		if(!StringUtils.isEmpty(enableSignature) && !enableSignature.equals("false")) { 
			//是否需要验证签名，默认需要
			Object requestSign = map.get("sign");
			boolean isSign =   (requestSign!=null && requestSign.equals(signatureText));
			if(isSign == false) {
				logger.info("pay->validSignature:签名验证失败");
				throw new PayException(ResultCodes.Result_Code_50001, "签名验证失败");
			}
		}
	}
	
	/**
	 * 获得签名字符串
	 * @param map
	 * @param appId
	 * @return
	 */
	public String signatureData(Map<String, Object> map,String appId,String merchantId) {
		MerchantApp merchantApp = this.getMerchantApp(appId,merchantId);
		return this.signatureData(map, merchantApp);
	}
	
	/**
	 * 获得签名字符串
	 * @param map
	 * @param merchantApp
	 * @return
	 */
	public String signatureData(Map<String, Object> map,MerchantApp merchantApp) {
		String sign = null;
		try {
			sign = SignatureUtil.generateSignature(map,merchantApp.getAppKey());
		} catch (Exception e) {
			logger.error("pay->validSignature:生成签名验证失败:"+JsonUtil.toString(map));
			logger.error(e.getMessage(),e);
			throw new PayException(ResultCodes.Result_Code_50003, "生成签名验证失败");
		}
		return sign;
	}

}
