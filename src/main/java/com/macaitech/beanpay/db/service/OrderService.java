/**
 * 
 */
package com.macaitech.beanpay.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.macaitech.beanpay.db.dao.OrderDao;
import com.macaitech.beanpay.db.entity.Order;

/**
 * @author yuhui.tang 
 * 订单Service
 */
@Service
public class OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
	/**
	 * 根据主键id获取
	 * @param id
	 * @return
	 */
	public Order getById(long id) {
		return this.orderDao.getById(id);
	}
	/**
	 * 根据商户订单号查询
	 * @param appId
	 * @param merchantOrderNo
	 * @return
	 */
	public Order getByMerchantOrderNo(String appId,String merchantOrderNo) {
		return this.orderDao.getByMerchantOrderNo(appId, merchantOrderNo);
	}
	
	/**
	 * 根据appId，orderNo获取
	 * @param appId
	 * @param orderNo
	 * @return
	 */
	public Order getByOrderNo(String orderNo) {
		return this.orderDao.getByOrderNo(orderNo);
	}
	
	/**
	 * 根据appId，orderNo获取
	 * @param appId
	 * @param tradeNo
	 * @return
	 */
	public Order getByTradeNo(String appId,String tradeNo) {
		return this.orderDao.getByTradeNo(appId, tradeNo);
	}
	
	/**
	 * 保存订单
	 * @param order
	 */
	@Transactional
	public int insert(Order order) {
		return this.orderDao.insert(order);
	}
	
	/**
	 * 更新订单
	 * @param order
	 */
	@Transactional
	public int updatePayResult(Order order) {
		return this.orderDao.updatePayResult(order);
	}
	/**
	 * 更新订单
	 * @param order
	 */
	@Transactional
	public int updateOrderResponse(Order order) {
		return this.orderDao.updateOrderResponse(order);
	}
	/**
	 * 重新发起支付前更新，只有未支付或支付中的可以更新
	 * @param order
	 * @return
	 */
	public int updateTryAgain(Order order) {
		return this.orderDao.updateTryAgain(order);
	}
}
