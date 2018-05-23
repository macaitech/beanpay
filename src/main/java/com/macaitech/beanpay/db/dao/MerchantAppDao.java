/**
 * 
 */
package com.macaitech.beanpay.db.dao;

import com.macaitech.beanpay.db.entity.MerchantApp;

/**
 * @author yuhui.tang
 * 商户App DAO
 */
public interface MerchantAppDao {
	
	public MerchantApp getByAppId(String appId);
}
