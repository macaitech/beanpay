/**
 * 
 */
package com.macaitech.beanpay.db.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yuhui.tang
 * 回调pojo
 */
public class Notify implements Serializable {

	private static final long serialVersionUID = -7489333296806182635L;
	
	  private long id ;//'自增主键',
	  private long orderId;//'订单id,hn_order.id',
	  private int notifyNum;// '回调次数',
	  private Date planNotifyTime;// '计划/预期回调时间',
	  private Date nextNotifyTime;// '下一次回调时间',
	  private int status;// '回调启用状态，1:需要回调 2：所有回调完成',
	  private Date createTime ;// '创建时间',
	  private Date updateTime ;//'更新时间',
	  private String notifyCode ;// '回调响应状态',
	  private String notifyMsg;// '回调响应消息',
	  private Order order; 
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public int getNotifyNum() {
		return notifyNum;
	}
	public void setNotifyNum(int notifyNum) {
		this.notifyNum = notifyNum;
	}
	public Date getPlanNotifyTime() {
		return planNotifyTime;
	}
	public void setPlanNotifyTime(Date planNotifyTime) {
		this.planNotifyTime = planNotifyTime;
	}
	public Date getNextNotifyTime() {
		return nextNotifyTime;
	}
	public void setNextNotifyTime(Date nextNotifyTime) {
		this.nextNotifyTime = nextNotifyTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getNotifyCode() {
		return notifyCode;
	}
	public void setNotifyCode(String notifyCode) {
		this.notifyCode = notifyCode;
	}
	public String getNotifyMsg() {
		return notifyMsg;
	}
	public void setNotifyMsg(String notifyMsg) {
		this.notifyMsg = notifyMsg;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
}
