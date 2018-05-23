/**
 * 
 */
package com.macaitech.beanpay.db.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.macaitech.beanpay.db.dao.NotifyDao;
import com.macaitech.beanpay.db.entity.Notify;

/**
 * @author yuhui.tang
 *	回调服务
 */
@Service
public class NotifyService {
	@Autowired
	private NotifyDao notifyDao; 
	
	/**
	 * 查询
	 * @param lePlanTime
	 * @param status
	 * @param limitSize
	 * @return
	 */
	public List<Notify> findNeedNotifyList(Date lePlanTime,int status,int limitSize){
		return this.notifyDao.findNeedNotifyList(lePlanTime, status, limitSize);
	}
	
	/**
	 * 获得单个notify
	 * @param merchantId
	 * @param appId
	 * @param merchantOrderNo
	 * @param status
	 * @return
	 */
	public Notify getNeedNotify(String merchantId,String appId,String merchantOrderNo,Integer status){
		return this.notifyDao.getNeedNotify(merchantId, appId, merchantOrderNo, status);
	}
	
	/**
	 * 插入
	 * @param notify
	 * @return
	 */
	@Transactional
	public int insert(Notify notify) {
		return this.notifyDao.insert(notify);
	}
	/**
	 * 更新
	 * @param notify
	 * @return
	 */
	@Transactional
	public int update(Notify notify) {
		return this.update(notify);
	}
	
	/**
	 * 回调成功-更新
	 * @param id
	 * @return
	 */
	@Transactional
	public int updateSuccess(long id) {
		return this.notifyDao.updateSuccess(id);
	}
}
