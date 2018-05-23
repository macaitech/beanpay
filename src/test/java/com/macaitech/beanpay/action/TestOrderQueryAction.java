/**
 * 
 */
package com.macaitech.beanpay.action;

import static org.junit.Assert.fail;

import java.util.Date;

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

import com.macaitech.beanpay.model.request.QueryRequest;
import com.macaitech.beanpay.model.response.PayResponse;
import com.macaitech.beanpay.model.response.QueryResponse;
import com.macaitech.beanpay.util.DateUtils;
import com.macaitech.beanpay.util.JsonMapper;
import com.macaitech.beanpay.util.JsonUtil;

/**
 * @author yuhui.tang
 * 订单查询测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value="src/main/webapp")
@ContextConfiguration({"classpath*:/spring/springmvc.xml" })
public class TestOrderQueryAction extends BaseTestOrderPayAction {
	private MockMvc mockMvc;
	
	@Autowired
	private OrderQueryAction orderQueryAction;
	
	@Before  
    public void setUp() {  
        mockMvc = MockMvcBuilders.standaloneSetup(orderQueryAction).build() ;  

    }  
	
	@Test
	public void testQueryOrderWx() {
		QueryRequest queryRequest = new QueryRequest();
		String oderNo = DateUtils.formatDate(new Date(), "yyyyMMdd")+"007";
		queryRequest.setAppId("23dsddsas23213dasdsd");
		queryRequest.setMerchantId("1018040101");
		queryRequest.setMerchantOrderNo(oderNo);
		try {
			MvcResult mvcResult = this.mockMvc.perform(
					MockMvcRequestBuilders.post("/query/order-json").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
					.content(JsonUtil.toString(queryRequest).getBytes())).andReturn();
			String content = mvcResult.getResponse().getContentAsString();
			QueryResponse queryResponse = JsonMapper.getInstance().fromJson(content, QueryResponse.class);
			Assert.assertEquals(queryResponse.getCode(), 200);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			fail("Not yet implemented");
		}
	}
}
