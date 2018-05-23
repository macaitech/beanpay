/**
 * 
 */
package com.macaitech.beanpay.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macaitech.beanpay.db.dao.MerchantAppDao;
import com.macaitech.beanpay.db.entity.MerchantApp;

/**
 * @author yuhui.tang
 *
 */
@Service
public class MerchantAppService {
	
	@Autowired
	private MerchantAppDao merchantAppDao;
	
	public MerchantApp getByAppId(String appId) {
		
		return this.merchantAppDao.getByAppId(appId);
	}
}
