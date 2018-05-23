/**
 * 
 */
package com.macaitech.beanpay.action;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

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

import com.alipay.api.internal.util.XmlUtils;
import com.macaitech.beanpay.model.WxNotifyRequest;
import com.macaitech.beanpay.model.response.QueryResponse;
import com.macaitech.beanpay.util.JsonMapper;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.XmlTestUtil;

/**
 * @author yuhui
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value="src/main/webapp")
@ContextConfiguration({"classpath*:/spring/springmvc.xml" })
public class TestAsynWxResopnseAction extends BaseTestOrderPayAction {
	private MockMvc mockMvc;
	
	@Autowired
	private AsynWxResopnseAction asynWxResopnseAction;
	
	@Before  
    public void setUp() {  
        mockMvc = MockMvcBuilders.standaloneSetup(asynWxResopnseAction).build() ;  

    }  
	
	@Test
	public void testAsyncNotifyFromAli() {
		WxNotifyRequest notifyRequest = new WxNotifyRequest();
		String xmlText = XmlTestUtil.convertToXml(notifyRequest);
		try {
			MvcResult mvcResult = this.mockMvc.perform(
					MockMvcRequestBuilders.post("/order/notify/wxpay")
					.content(xmlText).contentType(MediaType.APPLICATION_XML)).andReturn();
			String content = mvcResult.getResponse().getContentAsString();
			System.out.println(content);
			Assert.assertEquals(200, 200);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			fail("Not yet implemented");
		}
	}
}
