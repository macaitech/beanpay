package com.macaitech.beanpay.db.dao;


import org.apache.ibatis.annotations.Param;

import com.macaitech.beanpay.db.entity.Order;

/**
 * 订单DAO
 * @author yuhui.tang
 *
 */
public interface OrderDao {
	/**
	 * 根据主键id获取
	 * @param id
	 * @return
	 */
	public Order getById(long id);

	/**
	 * 根据商户订单号查询
	 * @param appId
	 * @param merchantOrderNo
	 * @return
	 */
	public Order getByMerchantOrderNo(@Param("appId")String appId,@Param("merchantOrderNo") String merchantOrderNo) ;
	
	/**
	 * 根据appId，orderNo获取
	 * @param appId
	 * @param orderNo
	 * @return
	 */
	public Order getByOrderNo(@Param("orderNo") String orderNo);
	
	/**
	 * 根据appId，orderNo获取
	 * @param appId
	 * @param tradeNo
	 * @return
	 */
	public Order getByTradeNo(@Param("appId")String appId,@Param("tradeNo") String tradeNo);
	
	/**
	 * 保存订单
	 * @param order
	 */
	public int insert(Order order);
	
	/**
	 * 更新订单
	 * @param order
	 */
	public int updatePayResult(Order order);
	/**
	 * 更新订单
	 * @param order
	 */
	public int updateOrderResponse(Order order);
	
	/**
	 * 重新发起支付前更新，只有未支付或支付中的可以更新
	 * @param order
	 * @return
	 */
	public int updateTryAgain(Order order);
}
