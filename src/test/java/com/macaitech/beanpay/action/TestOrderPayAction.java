package com.macaitech.beanpay.action;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;

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
import org.springframework.web.context.WebApplicationContext;

import com.macaitech.beanpay.model.request.OrderRequest;
import com.macaitech.beanpay.model.response.PayResponse;
import com.macaitech.beanpay.util.DateUtils;
import com.macaitech.beanpay.util.JsonMapper;
import com.macaitech.beanpay.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value="src/main/webapp")
@ContextConfiguration({"classpath*:/spring/springmvc.xml" })
public class TestOrderPayAction extends BaseTestOrderPayAction {
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private OrderPayAction orderPayAction;
	
	@Before  
    public void setUp() {  
        mockMvc = MockMvcBuilders.standaloneSetup(orderPayAction).build() ;  

    }  
	
	/**
	 * 测试商户不存在
	 */
	//@Test
	public void testNotExistMerchant() {
        OrderRequest orderRequest = new OrderRequest();
		orderRequest.setNotifyUrl("http://www.0606.com.cn");
		orderRequest.setClientIp("172.10.2.2");
		try {
			orderRequest.setMerchantOrderNo("2018041010101002");
			orderRequest.setOrderName("金钩量化");
			orderRequest.setPayChannel("wxpay");
			orderRequest.setPayWay("h5");
			orderRequest.setOrderMoney(1);
			MvcResult mvcResult = this.mockMvc.perform(
					MockMvcRequestBuilders.post("/order/pay-json").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
					.content(JsonUtil.toString(orderRequest).getBytes())).andReturn();
			String content = mvcResult.getResponse().getContentAsString();
			PayResponse payResponse = JsonMapper.getInstance().fromJson(content, PayResponse.class);
			Assert.assertNotEquals(payResponse.getCode(), 200);
			Assert.assertEquals(payResponse.getMessage(), "商户id或appId不存在");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			fail("Not yet implemented");
		}
	}
	/**
	 * 测试微信新订单
	 */
	//@Test
	public void testNewOrderToWxPay() {
        OrderRequest orderRequest = new OrderRequest();
		orderRequest.setNotifyUrl("http://www.0606.com.cn");
		orderRequest.setClientIp("172.10.2.2");
		try {
			String oderNo = DateUtils.formatDate(new Date(), "yyyyMMdd")+"001";
			orderRequest.setAppId("23dsddsas23213dasdsd");
			orderRequest.setMerchantId("1018040101");
			orderRequest.setMerchantOrderNo(oderNo);
			orderRequest.setOrderName("微信量化");
			orderRequest.setPayChannel("wxpay");
			orderRequest.setPayWay("h5");
			orderRequest.setOrderMoney(1);
			MvcResult mvcResult = this.mockMvc.perform(
					MockMvcRequestBuilders.post("/order/pay-json").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
					.content(JsonUtil.toString(orderRequest).getBytes())).andReturn();
			String content = mvcResult.getResponse().getContentAsString();
			System.out.println(content);
			PayResponse payResponse = JsonMapper.getInstance().fromJson(content, PayResponse.class);
			Assert.assertEquals(payResponse.getCode(), 200);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			fail("Not yet implemented");
		}
	}
	/**
	 * 测试支付宝新订单
	 */
	//@Test
	public void testNewOrderToAliPayH5() {
        OrderRequest orderRequest = new OrderRequest();
		orderRequest.setNotifyUrl("http://www.0606.com.cn");
		orderRequest.setClientIp("172.10.2.2");
		try {
			String oderNo = DateUtils.formatDate(new Date(), "yyyyMMdd")+"003";
			orderRequest.setAppId("23dsddsas23213dasdsd");
			orderRequest.setMerchantId("1018040101");
			orderRequest.setMerchantOrderNo(oderNo);
			orderRequest.setOrderName("支付宝量化");
			orderRequest.setPayChannel("alipay");
			orderRequest.setPayWay("h5");
			orderRequest.setOrderMoney(1);
			MvcResult mvcResult = this.mockMvc.perform(
					MockMvcRequestBuilders.post("/order/pay-json").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
					.content(JsonUtil.toString(orderRequest).getBytes())).andReturn();
			String content = mvcResult.getResponse().getContentAsString();
			System.out.println(content);
			PayResponse payResponse = JsonMapper.getInstance().fromJson(content, PayResponse.class);
			Assert.assertEquals(payResponse.getCode(), 200);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			fail("Not yet implemented");
		}
	}
	
	@Test
	public void testNewOrderToWxPayApp() {
        OrderRequest orderRequest = new OrderRequest();
		orderRequest.setNotifyUrl("http://www.0606.com.cn");
		orderRequest.setClientIp("172.10.2.2");
		try {
			String oderNo = DateUtils.formatDate(new Date(), "yyyyMMdd")+"007";
			orderRequest.setAppId("23dsddsas23213dasdsd");
			orderRequest.setMerchantId("1018040101");
			orderRequest.setMerchantOrderNo(oderNo);
			orderRequest.setOrderName("支付宝量化");
			orderRequest.setPayChannel("wxpay");
			orderRequest.setPayWay("app");
			orderRequest.setOrderMoney(1);
			MvcResult mvcResult = this.mockMvc.perform(
					MockMvcRequestBuilders.post("/order/pay-json").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
					.content(JsonUtil.toString(orderRequest).getBytes())).andReturn();
			String content = mvcResult.getResponse().getContentAsString();
			System.out.println(content);
			PayResponse payResponse = JsonMapper.getInstance().fromJson(content, PayResponse.class);
			Assert.assertEquals(payResponse.getCode(), 200);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			fail("Not yet implemented");
		}
	}
}
