/**
 * 
 */
package com.macaitech.beanpay.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.macaitech.beanpay.util.JsonUtil;

/**
 * @author yuhui.tang
 * 模拟回调信息返回
 */
@Controller
@RequestMapping(value="/mock")
public class MockRecevieCallbackAction extends BaseAction{
	
	@RequestMapping(value="callback",method=RequestMethod.POST, produces = {"text/html; charset=UTF-8"})
	@ResponseBody
	public String doCallback() {
		Map<String,String> map = new HashMap<>();
		logger.info("pay->callback");
		map.put("code", "200");
		map.put("message", "成功");
		return JsonUtil.toString(map);
	}
	
}
