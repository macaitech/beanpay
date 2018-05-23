package com.macaitech.beanpay.job;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.macaitech.beanpay.db.entity.Notify;
import com.macaitech.beanpay.db.service.NotifyService;
import com.macaitech.beanpay.pay.service.CallbackService;
import com.macaitech.beanpay.util.JsonUtil;

/**
 * 回调商户任务
 * @author yuhui.tang
 *
 */
@Service
public class NotifyMerchantJob implements BaseJob{
	
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private CallbackService callbackService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("job->excute start :"+this.getClass().getName());
		long start = System.currentTimeMillis();
		Date lePlanTime = new Date();
		Map<String,String> map = new HashMap<>();
		List<Notify> notifyList = this.notifyService.findNeedNotifyList(lePlanTime, 1, 1000);
		for (Notify notify : notifyList) {
			String merchantOrderNo = notify.getOrder().getMerchantOrderNo();
			try {
				this.callbackService.callback(notify);
				if(!map.containsKey(merchantOrderNo)) {
					map.put(merchantOrderNo,merchantOrderNo);
				}
			} catch (IOException e) {
				logger.error("job->notify fail: notifyid ="+notify.getId()+",merchantOrderNo="+merchantOrderNo);
				logger.error(e.getMessage(),e);
			}
		}
		if(map.size()>0) {
			logger.info("job->excute merchantOrderNos:"+JsonUtil.toString(map.keySet()));
		}
		logger.info("job->excute end :"+(System.currentTimeMillis()-start));
	}
}
