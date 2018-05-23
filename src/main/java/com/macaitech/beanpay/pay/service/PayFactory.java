/**
 * 
 */
package com.macaitech.beanpay.pay.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macaitech.beanpay.enums.PayChannelType;
import com.macaitech.beanpay.pay.service.alipay.OrderPayAliService;
import com.macaitech.beanpay.pay.service.alipay.OrderQueryAliService;
import com.macaitech.beanpay.pay.service.weixin.OrderPayWxService;
import com.macaitech.beanpay.pay.service.weixin.OrderQueryWxService;

/**
 * @author yuhui.tang
 * 支付工厂
 */
@Service
public class PayFactory {
		
	@Autowired
	private OrderPayAliService orderPayAliService;
	
	@Autowired
	private OrderPayWxService orderPayWxService;
	
	@Autowired
	private OrderQueryAliService orderQueryAliService;
	
	@Autowired
	private OrderQueryWxService orderQueryWxService;
	
	/**
	 * 获取OrderPayService
	 * @param payChannel 支付渠道
	 * @return
	 */
	public AbstractOrderPayTemplate getPayService(String payChannel) {
		if(StringUtils.isEmpty(payChannel)) {
			return null;
		}
		else if(payChannel.equalsIgnoreCase(PayChannelType.PayChannel_ALIPAY.getKey())) {
			return this.orderPayAliService;
		}
		else if(payChannel.equalsIgnoreCase(PayChannelType.PayChannel_WXPAY.getKey())) {
			return this.orderPayWxService;
		}
		return null;
	}
	
	/**
	 * 获取OrderQueryInterface
	 * @param payChannel 支付渠道
	 * @return
	 */
	public IQueryPayChannelOrderService getQueryService(String payChannel) {
		if(StringUtils.isEmpty(payChannel)) {
			return null;
		}
		else if(payChannel.equalsIgnoreCase(PayChannelType.PayChannel_ALIPAY.getKey())) {
			return this.orderQueryAliService;
		}
		else if(payChannel.equalsIgnoreCase(PayChannelType.PayChannel_WXPAY.getKey())) {
			return this.orderQueryWxService;
		}
		return null;
	}

}
