/**
 * 
 */
package com.macaitech.beanpay.pay.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macaitech.beanpay.common.ResultCodes;
import com.macaitech.beanpay.common.ResultData;
import com.macaitech.beanpay.db.entity.Notify;
import com.macaitech.beanpay.db.entity.Order;
import com.macaitech.beanpay.db.service.NotifyService;
import com.macaitech.beanpay.db.service.OrderService;
import com.macaitech.beanpay.enums.PayStatus;
import com.macaitech.beanpay.model.request.NotifyRequest;
import com.macaitech.beanpay.util.DateUtils;
import com.macaitech.beanpay.util.JsonUtil;

/**
 * @author yuhui.tang
 * 基础回调服务
 */
@Service
public class BaseNotifyService extends BaseService {
	@Autowired
	private OrderService orderService;
	@Autowired
	private NotifyService notifyService;
	
	/**
	 * 处理订单回调
	 * @param resultData
	 * @param notifyRequest
	 * @return
	 */
	public ResultData doNotifyOrder(ResultData resultData,NotifyRequest notifyRequest) {
		resultData.setCode(ResultCodes.Result_Code_50001);
		if(notifyRequest==null) {
			logger.info("notify->doNotifyOrder notifyRequest is null");
			resultData.setMessage("回调参数封装失败");
			return resultData;
		}
		logger.info("notify->doNotifyOrder("+notifyRequest.getUuid()+"):"+JsonUtil.toString(notifyRequest));
		Order order = this.orderService.getByOrderNo(notifyRequest.getOrderNo());
		if (order == null) {// 订单不存在
			logger.info("notify->doNotifyOrder("+notifyRequest.getUuid()+") 订单不存在:orderNo="+notifyRequest.getOrderNo());
			resultData.setMessage("订单不存在");
			return resultData;
		} else {
			
			order.setTradeNo(notifyRequest.getTradeNo());
			order.setTradeStatus(notifyRequest.getTradeStatus());
			order.setPayTime(notifyRequest.getPayTime());
			
			if(notifyRequest.getPayStatus() == PayStatus.PayStatus_UnPay ) {
				//未支付
				logger.info("notify->doNotifyOrder("+notifyRequest.getUuid()+"):orderNo=" + order.getOrderNo() + ",payStatus=" + notifyRequest.getPayStatus().getValue());
			}
			else {				
				//支付成功,支付失败，支付关闭，支付完成
				order.setTradeMsg("本次支付状态由" + PayStatus.getPayStatus(order.getPayStatus()).getValue() + "到"+notifyRequest.getPayStatus().getValue());
				logger.info("notify->doNotifyOrder updatePayResult("+notifyRequest.getUuid()+"):" +notifyRequest.getPayStatus().getKey()+","+ order.getTradeNo() + "," + order.getTradeMsg());
				
				order.setPayStatus(notifyRequest.getPayStatus().getKey());
				this.orderService.updatePayResult(order);
				//订单更新成功后，需要回调商户
				this.notify(notifyRequest.getUuid(),order);
			}
			resultData.setCode(ResultCodes.Result_Code_200);
			resultData.setMessage("成功");
			return resultData;
		}
	}
	/**
	 * 回调
	 * @param order
	 */
	private void notify(String uuid,Order order) {
		try {
			Notify notify = new Notify();
			notify.setNotifyNum(1);
			notify.setOrderId(order.getId());
			notify.setPlanNotifyTime(DateUtils.addSeconds(new Date(), 60));
			notify.setNextNotifyTime(DateUtils.addSeconds(new Date(), 120));
			notify.setStatus(1);//需要回调
			logger.info("notify->doNotifyOrder 插入回调数据("+uuid+")：notifyId="+notify.getId()+",orderNo="+notify.getOrder().getOrderNo());
			this.notifyService.insert(notify);
		}catch(Exception e) {
			logger.error(uuid+" "+e.getMessage(),e);
		}
	}
}
