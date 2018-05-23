package com.macaitech.beanpay.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.macaitech.beanpay.pay.service.BaseService;

/**
 * 
 * @author tangyuhui
 * job基类
 */
public interface BaseJob extends Job{
	
	public static final Logger logger = LoggerFactory.getLogger(BaseJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException;
}

