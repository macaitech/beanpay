/**
 * 
 */
package com.macaitech.beanpay.action;

import java.text.ParseException;
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
import com.macaitech.beanpay.model.request.QueryRequest;
import com.macaitech.beanpay.model.response.QueryResponse;
import com.macaitech.beanpay.pay.service.OrderQueryService;
import com.macaitech.beanpay.util.JsonUtil;
import com.macaitech.beanpay.util.MapUtils;

/**
 * @author yuhui.tang
 * 订单查询
 */
@Controller
@RequestMapping(value="/query")
public class OrderQueryAction extends BaseAction{
	
	@Autowired
	private OrderQueryService orderQueryService;
	
	/**
	 * 订单查询
	 * @param queryRequest
	 * @return
	 */
	@RequestMapping(value="order",method=RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public QueryResponse orderQuery(@ModelAttribute QueryRequest queryRequest) {
		return doOrderQuery(queryRequest);
	}
	
	/**
	 * 订单查询
	 * @param queryRequest
	 * @return
	 */
	@RequestMapping(value="order-json",method=RequestMethod.POST, produces = {"application/json; charset=UTF-8"})
	@ResponseBody
	public QueryResponse orderQueryJson(@RequestBody QueryRequest queryRequest) {
		return doOrderQuery(queryRequest);
	}
	

	public QueryResponse doOrderQuery(QueryRequest queryRequest) {
		long start = System.currentTimeMillis();
		try {
			//TODO DELETE
//			Map<String, Object> map = (Map<String, Object>) MapUtils.objectToMap(queryRequest);
//			String sign = this.orderQueryService.signatureData(map, queryRequest.getAppId(),queryRequest.getMerchantId());
//			queryRequest.setSign(sign);
//			logger.info("sign="+sign);
			
			logger.info("query->action begin("+queryRequest.getUuid()+"):"+JsonUtil.toString(queryRequest));
			QueryResponse response =  this.orderQueryService.doQueryOrder(queryRequest);
			logger.info("query->doOrderQuery result("+queryRequest.getUuid()+"):"+JsonUtil.toString(response));
			return response;
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
			QueryResponse response = new QueryResponse();
			response.setCode(ResultCodes.Result_Code_50003);
			response.setMessage("时间解码错误");
			return response;
		} catch (PayException e) {
			logger.error(e.getMessage(),e);
			QueryResponse response = new QueryResponse();
			response.setCode(e.getCode());
			response.setMessage(e.getMessage());
			return response;
		}
		finally {
			logger.info("query->action end("+queryRequest.getUuid()+"):"+(System.currentTimeMillis()-start));
		}
	}
}
