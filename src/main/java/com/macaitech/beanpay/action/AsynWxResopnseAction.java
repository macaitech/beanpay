/**
 * 
 */
package com.macaitech.beanpay.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.macaitech.beanpay.common.ResultData;
import com.macaitech.beanpay.exception.PayException;
import com.macaitech.beanpay.pay.service.weixin.NotifyReceiveWxService;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.UuidUtil;

/**
 * @author yuhui.tang
 * 微信支付，异步通知接收接口
 */
@Controller
@RequestMapping(value="/order/notify")
public class AsynWxResopnseAction  extends BaseAction{
	
	@Autowired
	private NotifyReceiveWxService notifyReceiveWxService;
	
	/**
	 * 微信回调
	 * @param body
	 * @return
	 */
	@RequestMapping(value="wxpay",method=RequestMethod.POST, produces = {"application/xml; charset=UTF-8"})
	public void asyncNotifyFromWx(HttpServletRequest request,HttpServletResponse response) {
		String uuid = UuidUtil.getUUID();
		long start = System.currentTimeMillis();
		int retunCode = 0;
		String msg = "";
		StringBuilder sb = new StringBuilder();
	    BufferedReader reader = null;
		try {
			reader = request.getReader();
			String str = "";
			while((str = reader.readLine()) != null){
				sb.append(str);
			}
			logger.info("notify->from->wx begin("+uuid+"):"+sb.toString());
			ResultData resultData = this.notifyReceiveWxService.doNotify(uuid,sb.toString());
			logger.info("notify->from->wx result("+uuid+"):"+JsonUtil.toString(resultData));
			retunCode = resultData.getCode();
			msg = resultData.getMessage();
		} catch (Exception e) {
			msg ="回调处理失败";
			if(!(e instanceof PayException)) {
				logger.error("notify->from->wx error("+uuid+"):"+e.getMessage(),e);
			}
		}
		//------返回
		PrintWriter out = null;
		try {
			String returnCode = retunCode==200 ?"SUCCESS":"FAIL";
			sb = new StringBuilder();
			response.setContentType("text/xml;charset=utf-8"); 
			out = response.getWriter();
			sb.append("<xml>\n");
			sb.append("  <return_code><![CDATA["+returnCode+"]]></return_code>\n");
			sb.append("	<return_msg><![CDATA["+msg+"]]></return_msg>\n");
			sb.append("</xml>\n");
			out.flush();
		} catch (IOException e) {
			logger.error("notify->from->wx error("+uuid+"):"+e.getMessage(),e);
		}
		finally {
			if(reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
			}
			if(out!=null) {
				out.close();
			}
			logger.info("notify->from->wx end("+uuid+"):"+(System.currentTimeMillis()-start));
		}
	}
}
