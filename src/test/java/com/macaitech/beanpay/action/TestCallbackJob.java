/**
 * 
 */
package com.macaitech.beanpay.action;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.macaitech.beanpay.db.entity.Notify;
import com.macaitech.beanpay.db.service.NotifyService;
import com.macaitech.beanpay.util.DateUtils;
import com.macaitech.beanpay.util.JsonUtil;

/**
 * @author yuhui.tang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value="src/main/webapp")
@ContextConfiguration({"classpath*:/spring/springmvc.xml" })
public class TestCallbackJob extends BaseTestOrderPayAction {
	
	private MockMvc mockMvc;
	
	@Autowired
	private MockRecevieCallbackAction callbackAction;
	
	@Autowired
	private NotifyService notifyService;
	
	@Before  
    public void setUp() {  
        mockMvc = MockMvcBuilders.standaloneSetup(callbackAction).build() ;  

    }  
	
	@Test
	public void testCallback() {
		List<Notify> notifyList = this.notifyService.findNeedNotifyList(DateUtils.addMinutes(new Date(), -100), 1, 1);
		String callbackText = "";
		try {
			MvcResult mvcResult = this.mockMvc.perform(
					MockMvcRequestBuilders.post("/mock/callback")
					.content(callbackText).contentType(MediaType.APPLICATION_FORM_URLENCODED)).andReturn();
			String content = mvcResult.getResponse().getContentAsString();
			System.out.println(content);
			Map<String,String> map =(Map<String,String>) JsonUtil.readJsonMap(content);
			int updateResult = 0;
			if(map!=null && !StringUtils.isEmpty(map.get("code")) && map.get("code").equals("200")) {
				for (Notify notify : notifyList) {
					updateResult = this.notifyService.updateSuccess(notify.getId());
				}
			}
			if(notifyList.size()==0) updateResult = 1;
			Assert.assertEquals(updateResult, 1);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			fail("Not yet implemented");
		}
	}
}
