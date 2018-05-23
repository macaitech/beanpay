package com.macaitech.beanpay.pay.service.weixin;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.macaitech.beanpay.common.ResultCodes;
import com.macaitech.beanpay.common.ResultData;
import com.macaitech.beanpay.enums.PayStatus;
import com.macaitech.beanpay.exception.PayException;
import com.macaitech.beanpay.model.request.NotifyRequest;
import com.macaitech.beanpay.pay.service.BaseNotifyService;
import com.macaitech.beanpay.sdk.wxpay.WXPayConfig;
import com.macaitech.beanpay.sdk.wxpay.WXPayUtil;
import com.macaitech.beanpay.util.DateUtils;

/**
 * 
 * @author yuhui.tang 回调-微信接收
 */
@Service
public class NotifyReceiveWxService extends BaseNotifyService {

	/**
	 * 处理回调
	 * @param bodyText
	 * @return
	 */
	@Transactional
	public ResultData doNotify(String uuid,String bodyText) {
		ResultData resultData = new ResultData();
		resultData.setCode(ResultCodes.Result_Code_50001);
		NotifyRequest notifyRequest;
		try {
			notifyRequest = this.parseNotifyRequestForWx(uuid,bodyText);
		} catch (ParseException e) {
			logger.error("notify->from->wx error("+uuid+"):时间转换失败", e);
			logger.error(e.getMessage(),e);
			resultData.setMessage("时间转换失败");
			return resultData;
		}
		return doNotifyOrder(resultData,notifyRequest);
	}

	/**
	 * 微信参数解码
	 * @param content
	 * @return
	 * @throws ParseException 
	 */
	private NotifyRequest parseNotifyRequestForWx(String uuid,String bodyText) throws ParseException {
		Map<String, String> map;
		boolean flag = false;
		try {
			map = WXPayUtil.xmlToMap(bodyText);
		} catch (Exception e) {
			logger.error("notify->from->wx error("+uuid+"):参数格式校验错误", e);
			throw new PayException(ResultCodes.Result_Code_50001, "参数格式校验错误");
		}
		try {
			WXPayConfig wxPayConfig  =new WeixinPayConfig();
			flag = WXPayUtil.isSignatureValid(map, wxPayConfig.getKey());
		} catch (Exception e1) {
			logger.error("notify-from->wx error("+uuid+"):验签失败", e1);
			throw new PayException(ResultCodes.Result_Code_50001, "验签失败");
		}
		if(flag==false) {
			throw new PayException(ResultCodes.Result_Code_50001, "验签失败");
		}
		String returnCode = map.get("return_code");
		returnCode = returnCode != null ? returnCode : "";
		
		if(StringUtils.isEmpty(returnCode) || !returnCode.equalsIgnoreCase("SUCCESS")) {
			return null;
		}
		else {
			return toNotifyRequest(bodyText, map);
		}
	}

	/**
	 * 成功时封装
	 * @param bodyText
	 * @param map
	 * @return
	 * @throws ParseException
	 */
	private NotifyRequest toNotifyRequest(String bodyText, Map<String, String> map) throws ParseException {
		NotifyRequest notifyRequest = new NotifyRequest();

		String resultCode = map.get("result_code");
		resultCode = resultCode != null ? resultCode : "";
		notifyRequest.setResultCode(resultCode);
		notifyRequest.setPayStatus(this.toPayStatus(resultCode));// 转换为支付状态
				
		notifyRequest.setAppId(map.get("appid"));
		notifyRequest.setMerchantId(map.get("mch_id"));
		notifyRequest.setSignType(map.get("sign_type"));
		
		notifyRequest.setOrderNo(map.get("out_trade_no"));
		notifyRequest.setTradeNo(map.get("transaction_id"));
		
		String totalFee = map.get("total_fee");//微信是分
		if(!StringUtils.isEmpty(totalFee)) {
			BigDecimal bigDecimal = new BigDecimal(totalFee);
			notifyRequest.setTotalAmount(bigDecimal.intValue());//订单金额,元转换为分
		}
		
		String cashFee = map.get("cash_fee");//微信是分
		if(!StringUtils.isEmpty(cashFee)) {
			BigDecimal bigDecimal = new BigDecimal(cashFee);
			notifyRequest.setReceiptAmount(bigDecimal.intValue());//实际金额,元转换为分
		}
		
		Date payTime = DateUtils.parseDate(map.get("time_end"), DateUtils.parsePatterns);
		notifyRequest.setPayTime(payTime);
		
		notifyRequest.setNotifyText(bodyText);
		
		notifyRequest.setErrorCode(map.get("err_code"));
		notifyRequest.setErrorMsg(map.get("err_code_des"));
		
		//String device_info = map.get("device_info");
		
		return notifyRequest;
	}
	/**
	 * 转换为支付状态
	 * @param resultCode
	 * @return
	 */
	private PayStatus toPayStatus(String resultCode) {
		if(!StringUtils.isEmpty(resultCode) && resultCode.equalsIgnoreCase("SUCCESS")) {
			return PayStatus.PayStatus_OK;
		}
		else if(resultCode.equalsIgnoreCase("FAIL")) {
			return PayStatus.PayStatus_Fail;
		}
		return PayStatus.PayStatus_UnPay;
	}
}
