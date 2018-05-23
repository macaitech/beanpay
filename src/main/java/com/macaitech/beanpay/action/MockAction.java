/**
 * 
 */
package com.macaitech.beanpay.action;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.macaitech.beanpay.model.request.OrderRequest;
import com.macaitech.beanpay.model.request.QueryRequest;
import com.macaitech.beanpay.model.response.PayResponse;
import com.macaitech.beanpay.model.response.QueryResponse;
import com.macaitech.beanpay.pay.service.CallbackService;
import com.macaitech.beanpay.pay.service.alipay.AlipayConfig;
import com.macaitech.beanpay.util.HttpUtil;
import com.macaitech.beanpay.util.PayConfigUtil;

/**
 * @author yuhui.tang
 *
 */
@Controller
@RequestMapping(value="/mock")
public class MockAction  extends BaseAction{
	
	@Autowired
	private OrderPayAction orderPayAction;
	
	@Autowired
	private OrderQueryAction orderQueryAction;
	
	@Autowired
	private CallbackService callbackService;
	
	@RequestMapping(value = "pay",method=RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public PayResponse pay(@ModelAttribute OrderRequest orderRequest) {
//		Map<String, Object> map = (Map<String, Object>) MapUtils.objectToMap(orderRequest);
//		String sign = basePayService.signatureData(map, orderRequest.getAppId(),orderRequest.getMerchantId());
//		orderRequest.setSign(sign);
//		logger.info("sign="+sign);
		return this.orderPayAction.orderPay(orderRequest);
	}
	
	@RequestMapping(value="testquery",method=RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public QueryResponse query(@ModelAttribute QueryRequest queryRequest) {
//		Map<String, Object> map = (Map<String, Object>) MapUtils.objectToMap(queryRequest);
//		String sign = basePayService.signatureData(map, queryRequest.getAppId(),queryRequest.getMerchantId());
//		queryRequest.setSign(sign);
//		logger.info("sign="+sign);
		return this.orderQueryAction.doOrderQuery(queryRequest);
	}
	
	@RequestMapping(value = "alinotify",method=RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public String aliNotify(HttpServletRequest request, HttpServletResponse response) {
		String notifyText = request.getParameter("notifyText");
		Map<String,Object> map = new HashMap<>();
		if(!StringUtils.isEmpty(notifyText)) {
			String[] params= notifyText.split("&");
			for (String paramString : params) {
				String[] data  = paramString.split("=");
				if(data.length>1) {
					String key = data[0].replace("xnotify", "notify");
					map.put(key, data[1]);
				}
			}
		}
		System.out.println(notifyText);
		String url = AlipayConfig.getPayConfig().getNotify_url();
		try {
			String queryString = HttpUtil.buildQuery(map, "UTF-8");
			url = url +"?"+queryString;
			String content = HttpUtil.doPost(url, map);
			return content ;
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			return "alinotify";
		}
	}
	
	@RequestMapping(value = "wxnotify",method=RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public String wxNotify(HttpServletRequest request, HttpServletResponse response) {
		String notifyText = request.getParameter("notifyText");
		
		System.out.println(notifyText);
		String url = PayConfigUtil.getWxPayConfig().getNotify_url();
		try {
			byte[] content = HttpUtil.post(url, notifyText.getBytes());
			String retText =  new String(content) ;
			return retText;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return "wxnotify";
		}
	}
	
	@RequestMapping(value="calluser",method=RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public String calluser(@ModelAttribute QueryRequest queryRequest) {
//		Map<String, Object> map = (Map<String, Object>) MapUtils.objectToMap(queryRequest);
//		String sign = basePayService.signatureData(map, queryRequest.getAppId(),queryRequest.getMerchantId());
//		queryRequest.setSign(sign);
//		logger.info("sign="+sign);
		try {
			boolean flag = this.callbackService.callback(queryRequest);
			
			return (flag==true ? "成功":"失败");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);;
			return  "失败";
		}
	}
	
}
