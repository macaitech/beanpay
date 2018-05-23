/**
 * 
 */
package com.macaitech.beanpay.action;

import static org.junit.Assert.fail;

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

import com.macaitech.beanpay.model.response.QueryResponse;
import com.macaitech.beanpay.util.JsonMapper;
import com.macaitech.beanpay.util.JsonUtil;

/**
 * @author yuhui
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value="src/main/webapp")
@ContextConfiguration({"classpath*:/spring/springmvc.xml" })
public class TestAsynAliResopnseAction extends BaseTestOrderPayAction {
	private MockMvc mockMvc;
	
	@Autowired
	private AsynAliResopnseAction asynAliResopnseAction;
	
	@Before  
    public void setUp() {  
        mockMvc = MockMvcBuilders.standaloneSetup(asynAliResopnseAction).build() ;  

    }  
	
	@Test
	public void testAsyncNotifyFromAli() {
		String notifyText  ="total_amount=2.00&buyer_id=2088102116773037&body=大乐透2.1&trade_no=2016071921001003030200089909"
				+ "&refund_fee=0.00&notify_time=2016-07-19 14:10:49&subject=大乐透2.1&sign_type=RSA2&charset=utf-8"
				+ "&notify_type=trade_status_sync&out_trade_no=0719141034-6418&gmt_close=2016-07-19 14:10:46&gmt_payment=2016-07-19 14:10:47"
				+ "&trade_status=TRADE_SUCCESS&version=1.0"
				+ "&sign=kPbQIjX+xQc8F0/A6/AocELIjhhZnGbcBN6G4MMHmfWL4ZiHM6fWl5NQhzXJusaklZ1LFuMo+lHQUELAYeugH8LYFvxnNajOvZhuxNFbN2LhF0l/KL8ANtj8oyPM4NN7Qft2kWJTDJUpQOzCzNnV9hDxh5AaT9FPqRS6ZKxnzM="
				+ "&gmt_create=2016-07-19 14:10:44&app_id=2015102700040153&seller_id=2088102119685838&notify_id=4a91b7a78a503640467525113fb7d8bg8e";
		try {
			MvcResult mvcResult = this.mockMvc.perform(
					MockMvcRequestBuilders.post("/order/notify/alipay")
					.content(notifyText).contentType(MediaType.APPLICATION_FORM_URLENCODED)).andReturn();
			String content = mvcResult.getResponse().getContentAsString();
			System.out.println(content);
			Assert.assertEquals(200, 200);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			fail("Not yet implemented");
		}
	}
}
