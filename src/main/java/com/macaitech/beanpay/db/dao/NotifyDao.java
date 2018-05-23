/**
 * 
 */
package com.macaitech.beanpay.db.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.macaitech.beanpay.db.entity.Notify;

/**
 * @author yuhui.tang
 * 回调DAO
 */
public interface NotifyDao {
	
	/**
	 * 查询
	 * @param lePlanTime
	 * @param status
	 * @param limitSize
	 * @return
	 */
	public List<Notify> findNeedNotifyList(@Param("lePlanTime")Date lePlanTime,@Param("status") int status,@Param("limitSize") int limitSize);
	
	/**
	 * 获得单个notify
	 * @param merchantId
	 * @param appId
	 * @param merchantOrderNo
	 * @param status
	 * @return
	 */
	public Notify getNeedNotify(@Param("merchantId")String merchantId,@Param("appId")String appId,
			@Param("merchantOrderNo")String merchantOrderNo,@Param("status") Integer status);
	
	/**
	 * 插入
	 * @param notify
	 * @return
	 */
	public int insert(Notify notify);
	/**
	 * 更新
	 * @param notify
	 * @return
	 */
	public int update(Notify notify);
	
	/**
	 * 回调成功-更新
	 * @param id
	 * @return
	 */
	public int updateSuccess(@Param("id") long id);
}
