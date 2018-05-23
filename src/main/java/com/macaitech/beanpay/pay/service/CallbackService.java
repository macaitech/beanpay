/**
 * 
 */
package com.macaitech.beanpay.pay.service;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macaitech.beanpay.db.entity.MerchantApp;
import com.macaitech.beanpay.db.entity.Notify;
import com.macaitech.beanpay.db.entity.Order;
import com.macaitech.beanpay.db.service.MerchantAppService;
import com.macaitech.beanpay.db.service.NotifyService;
import com.macaitech.beanpay.model.QueryResult;
import com.macaitech.beanpay.model.request.QueryRequest;
import com.macaitech.beanpay.util.HttpPoolManage;
import com.macaitech.beanpay.util.HttpUtil;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.MapUtils;

/**
 * @author yuhui.tang
 * 回调服务
 */
@Service
public class CallbackService extends BaseService{
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private MerchantAppService merchantAppService;
	
	private final int _TimeOut = 20*1000;
	
	/**
	 * 回调
	 * @param notify
	 * @throws IOException 
	 */
	public void callback(Notify notify) throws IOException {
		String url = notify.getOrder().getNotifyUrl();
		QueryResult queryResult = this.convert(notify);
		Map<String, Object> params =  MapUtils.objectToMap(queryResult);
		String queryString = HttpUtil.buildQuery(params, "UTF-8");
		String responseText = HttpPoolManage.sendPost(url, queryString);
		Map<String,String> queryResultMap =(Map<String,String>) JsonUtil.readJsonMap(responseText);
		if(queryResultMap!=null && !StringUtils.isEmpty(queryResultMap.get("code")) && queryResultMap.get("code").equals("200")) {
			//成功，则更新
			this.notifyService.updateSuccess(notify.getId());
		}
	}
	/**
	 * 回调
	 * @param queryRequest
	 * @throws IOException 
	 */
	public boolean callback(QueryRequest queryRequest) throws IOException {
		MerchantApp merchantApp = this.merchantAppService.getByAppId(queryRequest.getAppId());
		if(merchantApp!=null && queryRequest.getMerchantId().equals(String.valueOf(merchantApp.getMerchantId()))) {
			Notify notify = this.notifyService.getNeedNotify(queryRequest.getMerchantId(), queryRequest.getAppId(),
					queryRequest.getMerchantOrderNo(), null);
			if(notify!=null) {
				this.callback(notify);
			}
			else {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * 封装成QueryResult
	 * @param notify
	 * @return
	 */
	private QueryResult convert(Notify notify) {
		QueryResult queryResult = new QueryResult();
		Order order = notify.getOrder();
		queryResult.setMerchantId(String.valueOf(order.getMerchantId()));
		queryResult.setAppId(order.getAppId());

		queryResult.setMerchantOrderNo(order.getMerchantOrderNo());
		queryResult.setPayChannel(order.getPayChannel());
		queryResult.setPayWay(order.getPayWay());
		
		queryResult.setTradeNo(order.getTradeNo());

		queryResult.setPaystatus(order.getPayStatus());
		queryResult.setPayTime(order.getPayTime());
		queryResult.setPaymsg(order.getPayMsg());
		
		queryResult.setOrderMoney(order.getOrderMoney());
		queryResult.setOrderName(order.getOrderName());
		
		queryResult.setTradeStatus(order.getTradeStatus());
		queryResult.setTradeMsg(order.getTradeMsg());
		return queryResult;
	}
	
}
