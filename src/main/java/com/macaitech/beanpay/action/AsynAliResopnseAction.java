/**
 * 
 */
package com.macaitech.beanpay.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.macaitech.beanpay.common.ResultData;
import com.macaitech.beanpay.pay.service.alipay.NotifyReceiveAliService;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.UuidUtil;

/**
 * @author yuhui.tang
 * 支付宝支付，异步通知接收接口
 */
@Controller
@RequestMapping(value="/order/notify")
public class AsynAliResopnseAction  extends BaseAction{
	@Autowired
	private NotifyReceiveAliService notifyReceiveAliService;
	
	
	/**
	 * 支付宝回调
	 * @param body
	 * @return
	 */
	@RequestMapping(value="alipay",method=RequestMethod.POST, produces = {"text/html; charset=UTF-8"})
	public void asyncNotifyFromAli(HttpServletRequest request, HttpServletResponse response) {
		String uuid = UuidUtil.getUUID();
		long start = System.currentTimeMillis();
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		logger.info("notify->from->ali begin("+uuid+"):"+JsonUtil.toString(params));
		ResultData resultData = this.notifyReceiveAliService.doNotify(uuid,params);
		logger.info("notify->from->ali result("+uuid+"):"+JsonUtil.toString(resultData));
		PrintWriter out = null;
		try {
			String resultString = resultData.getCode()==200?"SUCCESS":"FAIL";
			response.setContentType("text/html;charset=utf-8"); 
			out = response.getWriter();
			out.println("<html><head></head><body>"+resultString+"</body></html>");
			out.flush();
		} catch (IOException e) {
			logger.error("notify->from->ali error("+uuid+"):"+e.getMessage(),e);
		}
		finally {
			if(out!=null) {
				out.close();
			}
			logger.info("notify->from->ali end("+uuid+"):"+(System.currentTimeMillis()-start));
		}
	}
	
}
