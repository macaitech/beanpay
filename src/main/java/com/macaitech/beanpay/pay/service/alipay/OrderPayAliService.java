/**
 * 
 */
package com.macaitech.beanpay.pay.service.alipay;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.macaitech.beanpay.common.ResultCodes;
import com.macaitech.beanpay.db.entity.Order;
import com.macaitech.beanpay.model.OrderResult;
import com.macaitech.beanpay.model.response.PayResponse;
import com.macaitech.beanpay.pay.service.AbstractOrderPayTemplate;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.MapUtils;

/**
 * @author yuhui.tang
 * 下单支付——支付宝
 */
@Service
public class OrderPayAliService extends AbstractOrderPayTemplate{
	
	@Override
	public PayResponse invokePayChannel(String uuid,Order order) {
		AlipayResponse response = null;
		try {
			//调用
			if(order.getPayWay() == null || order.getPayWay().equalsIgnoreCase("h5")) {
				logger.info("pay->ali-wap("+uuid+"):"+order.getMerchantOrderNo());
				response = this.payOrderByWap(uuid,order);
			}
			else {
				logger.info("pay->ali-sdk("+uuid+"):"+order.getMerchantOrderNo());
				response = this.payOrderBySdk(uuid,order);
			}
			System.out.println(response.getBody());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return exbox(order, response);
	}
	/**
	 * APP支付
	 * @param order
	 * @return
	 */
	private AlipayResponse payOrderBySdk(String uuid,Order order) {
		
		AlipayClient alipayClient = AlipayConfig.getAlipayClient();
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		  // 封装请求支付信息
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
	    model.setOutTradeNo(order.getOrderNo());
	    model.setSubject(order.getOrderName());
	    model.setTotalAmount(String.valueOf(order.getOrderMoney()));
	    model.setBody(order.getOrderDesc());
	    model.setTimeoutExpress(String.valueOf(AlipayConfig.TIME_OUT));
	    model.setProductCode(order.getOrderName());
	    request.setBizModel(model);
	    // 设置异步通知地址,通知到hipay，然后再由hipay通知到商户
	    request.setNotifyUrl(AlipayConfig.getPayConfig().getNotify_url());
	    // 设置同步地址
	    request.setReturnUrl(order.getReturnUrl());   
	    try {
	        //这里和普通的接口调用不同，使用的是sdkExecute
	    		logger.info("pay->ali-sdk request("+uuid+"):"+JsonUtil.toString(request));
	        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
	        logger.info("pay->ali-sdk response("+uuid+"):"+JsonUtil.toString(response));
	        return response;
	    } catch (AlipayApiException e) {
	    		logger.error("pay->ali-sdk error("+uuid+"):"+JsonUtil.toString(order));
	    		logger.error(e.getMessage(),e);
	        return new AlipayTradeAppPayResponse() ;
	    }
	}
	/**
	 * wap手机网站支付
	 * @param order
	 * @return
	 */
	private AlipayResponse payOrderByWap(String uuid,Order order) {
		
		AlipayClient alipayClient = AlipayConfig.getAlipayClient();
		
		AlipayTradeWapPayRequest request=new AlipayTradeWapPayRequest();
	    // 封装请求支付信息
	    AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
	    model.setOutTradeNo(order.getOrderNo());
	    model.setSubject(order.getOrderName());
	    model.setTotalAmount(String.valueOf(order.getOrderMoney()));
	    model.setBody(order.getOrderDesc());
	    model.setTimeoutExpress(String.valueOf(AlipayConfig.TIME_OUT));
	    model.setProductCode(order.getOrderName());
	    request.setBizModel(model);
	    // 设置异步通知地址
	    request.setNotifyUrl(order.getNotifyUrl());
	    // 设置同步地址
	    request.setReturnUrl(order.getReturnUrl());   
	    try {
	        logger.info("pay->ali-wap request("+uuid+"):"+JsonUtil.toString(request));
	        AlipayTradeWapPayResponse response = alipayClient.pageExecute(request);
	        logger.info("pay->ali-wap response("+uuid+"):"+JsonUtil.toString(response));
	        return response;
	    } catch (AlipayApiException e) {
	    		logger.error("pay->ali-wap error("+uuid+"):"+JsonUtil.toString(order));
	    		logger.error(e.getMessage(),e);
	        return new AlipayTradeWapPayResponse();
	    }
	}
	
	/**
	 * 封装参数
	 * @param order
	 * @param alipayResponse
	 * @return
	 */
	private PayResponse exbox(Order order,AlipayResponse alipayResponse) {
		PayResponse response = new PayResponse();
		if(!alipayResponse.isSuccess()) {//不成功
			response.setCode(ResultCodes.Result_Code_50001);
			response.setMessage(alipayResponse.getMsg()!=null?alipayResponse.getMsg():"");
		}
		else {
			OrderResult orderResult = new OrderResult();
			orderResult.setMerchantOrderNo(order.getMerchantOrderNo());
			orderResult.setPayChannel(order.getPayChannel());
			orderResult.setPayWay(order.getPayWay());
			orderResult.setPaymethod(order.getPayMethod());
			orderResult.setOrderUrl("");
			orderResult.setPrepay(alipayResponse.getBody());
			response.setData(orderResult);
			//生成签名串
			try {
				Map<String, Object> map = (Map<String, Object>) MapUtils.objectToMap(orderResult);
				String sign = this.signatureData(map, order.getAppId(),String.valueOf(order.getMerchantId()));
				response.setSign(sign);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		return response;
	}

}
