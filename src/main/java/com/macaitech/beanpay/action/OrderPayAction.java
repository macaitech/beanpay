/**
 * 
 */
package com.macaitech.beanpay.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.macaitech.beanpay.common.ResultCodes;
import com.macaitech.beanpay.exception.PayException;
import com.macaitech.beanpay.model.request.OrderRequest;
import com.macaitech.beanpay.model.response.PayResponse;
import com.macaitech.beanpay.pay.service.AbstractOrderPayTemplate;
import com.macaitech.beanpay.pay.service.PayFactory;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.MapUtils;

/**
 * @author yuhui.tang
 * 订单支付
 */
@Controller
@RequestMapping(value="/order")
public class OrderPayAction extends BaseAction{
	@Autowired
	private PayFactory payFactory;
//	@ModelAttribute
//	public OrderRequest get() {
//		return new OrderRequest();
//	}
	/**
	 * 下单支付，支持query或form表单
	 * @param orderRequest
	 * @return
	 */
	@RequestMapping(value="pay",method=RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public PayResponse orderPay(@ModelAttribute OrderRequest orderRequest) {
		return this.doOrderPay(orderRequest);
	}
	/**
	 * 下单支付，支持json格式入参
	 * @param orderRequest
	 * @return
	 */
	@RequestMapping(value="pay-json",method=RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public PayResponse orderPayJson(@RequestBody OrderRequest orderRequest) {
		return this.doOrderPay(orderRequest);
	}

	/**
	 * 下单
	 * @param orderRequest
	 * @return
	 */
	public PayResponse doOrderPay(OrderRequest orderRequest) {
		long start = System.currentTimeMillis();
		try {
			logger.info("orderpay->begin("+orderRequest.getUuid()+"):"+JsonUtil.toString(orderRequest));
			AbstractOrderPayTemplate orderPayService = this.payFactory.getPayService(orderRequest.getPayChannel());
			//TODO delete
//			Map<String, Object> map = (Map<String, Object>) MapUtils.objectToMap(orderRequest);
//			String sign = orderPayService.signatureData(map, orderRequest.getAppId(),orderRequest.getMerchantId());
//			orderRequest.setSign(sign);
			//invoke service
			PayResponse response= orderPayService.doOrderPay(orderRequest);
			return response;
		}
		catch(Exception e) {
			PayResponse response = new PayResponse();
			response.setCode(ResultCodes.Result_Code_50003);
			if(e instanceof PayException) {
				response.setCode(((PayException) e).getCode());
				response.setMessage(e.getMessage());
			}
			else {
				logger.error("orderpay->error:"+e.getMessage(),e);
				response.setMessage(ResultCodes.Result_Message_50003);
			}
			return response;
		}
		finally {
			logger.info("orderpay->end("+orderRequest.getUuid()+"):"+(System.currentTimeMillis()-start));
		}
	}

}
