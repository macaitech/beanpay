/**
 * 
 */
package com.macaitech.beanpay.pay.service.alipay;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alipay.api.internal.util.AlipaySignature;
import com.macaitech.beanpay.common.ResultCodes;
import com.macaitech.beanpay.common.ResultData;
import com.macaitech.beanpay.model.request.NotifyRequest;
import com.macaitech.beanpay.pay.service.BaseNotifyService;
import com.macaitech.beanpay.util.JsonUtil;


/**
 * @author yuhui.tang
 * 支付宝支付，回调接收
 */
@Service
public class NotifyReceiveAliService extends BaseNotifyService{
	/**
	 * 处理回调
	 * @param params
	 */
	public ResultData doNotify(String uuid,Map<String,String> map) {
		ResultData resultData = new ResultData();
		resultData.setCode(ResultCodes.Result_Code_50001);
		boolean verifyResult = false;
		try {
			verifyResult = AlipaySignature.rsaCheckV1(map, AlipayConfig.getPublicKey(), AlipayConfig.CHARSET, AlipayConfig.getSignType());
		} catch (Exception e) {
			logger.error("notify->from->ali error("+uuid+"):验签失败", e);
			logger.error(e.getMessage(),e);logger.error(e.getMessage(),e);
			resultData.setMessage("验签失败");
			return resultData;
		}
		if(verifyResult) { 
			NotifyRequest notifyRequest;
			try {
				notifyRequest = this.parseNotifyRequest(map);
			} catch (ParseException e) {
				logger.error("notify->from->ali error("+uuid+"):时间转换失败", e);
				logger.error(e.getMessage(),e);
				resultData.setMessage("时间转换失败");
				return resultData;
			}
			//处理订单
			return super.doNotifyOrder(resultData, notifyRequest);
		}
		resultData.setMessage("回调处理失败");
		return resultData;
	}
	/**
	 * 解码参数，封装为NotifyRequest
	 * @param map
	 * @return
	 * @throws ParseException 
	 */
	private NotifyRequest parseNotifyRequest(Map<String,String> map) throws ParseException {
		NotifyRequest notifyRequest = new NotifyRequest();
		notifyRequest.setAppId(map.get("app_id"));
		notifyRequest.setSignType(map.get("sign_type"));
		
		notifyRequest.setOrderNo(map.get("out_trade_no"));
		notifyRequest.setTradeNo(map.get("trade_no"));

		String totalAmount = map.get("total_amount") ;
		if(!StringUtils.isEmpty(totalAmount)) {
			BigDecimal bigDecimal = new BigDecimal(totalAmount);
			bigDecimal = bigDecimal.multiply(new BigDecimal(100));
			notifyRequest.setTotalAmount(bigDecimal.intValue());//订单金额,元转换为分
		}
		
		String receiptAmount = map.get("receipt_amount");
		if(!StringUtils.isEmpty(receiptAmount)) {
			BigDecimal bigDecimal = new BigDecimal(receiptAmount);
			bigDecimal = bigDecimal.multiply(new BigDecimal(100));
			notifyRequest.setReceiptAmount(bigDecimal.intValue());///实际金额,元转换为分
		}
		
		//判断和转换支付状态
		String tradeStatus = map.get("trade_status");
		notifyRequest.setPayStatus(AliResponseUtil.toPayStatus(tradeStatus));
		
		notifyRequest.setPayTime(AliResponseUtil.getPayTime(notifyRequest.getPayStatus(), map));
		
		//保存回调原始参数
		notifyRequest.setNotifyText(JsonUtil.toString(map));		
		return notifyRequest;
	}

}
