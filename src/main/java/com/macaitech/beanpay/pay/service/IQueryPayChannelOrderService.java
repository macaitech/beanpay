/**
 * 
 */
package com.macaitech.beanpay.pay.service;

import java.text.ParseException;

import com.macaitech.beanpay.db.entity.Order;
import com.macaitech.beanpay.model.response.QueryResponse;

/**
 * @author yuhui.tang
 * 向支付渠道查询订单
 */
public interface IQueryPayChannelOrderService {
	/**
	 * 调用渠道查询订单
	 * @param order
	 * @return
	 */
	public QueryResponse invokeChannelQueryOrder(Order order)  throws ParseException;
}
