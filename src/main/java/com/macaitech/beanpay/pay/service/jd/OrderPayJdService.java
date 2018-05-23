/**
 * 
 */
package com.macaitech.beanpay.pay.service.jd;


import com.macaitech.beanpay.model.request.OrderRequest;
import com.macaitech.beanpay.pay.service.BaseService;

/**
 * @author yuhui.tang
 * 下单支付——京东
 */
public class OrderPayJdService extends BaseService{
	public static void main(String[] args) {
//		OrderPayJdService payService = new OrderPayJdService();
//		OrderRequest orderRequest = new OrderRequest();
//		orderRequest.setMerchantId(JdPayConfig.Merchant);
//		payService.orderPayBySdk(orderRequest);
	}
	
	/**
	 * 订单支付
	 * @param orderRequest
	 */
	public void orderPayBySdk(OrderRequest orderRequest) {
//		BasePayOrderInfo order = this.constructOrder(orderRequest);
//		try {
//			String tradeXml = JdPayUtil.genReqXml(order, JdPayConfig.RsaPrivateKey, JdPayConfig.DesKey);
//			logger.info("request xml:" + tradeXml);
//			String resultJsonData = HttpsClientUtil.sendRequest(JdPayConfig.UniorderURL, tradeXml, "application/xml");
//			logger.info("resultJsonData:" + resultJsonData);
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
	}
	
//	public BasePayOrderInfo constructOrder(OrderRequest orderRequest) {
//			BasePayOrderInfo order = new BasePayOrderInfo();
//			order.setVersion(orderRequest.getVersion());
//			order.setMerchant(orderRequest.getMerchantId());
//			//order.setDevice(request.getParameter("device"));
//			order.setTradeNum(orderRequest.getOderNo());
//			order.setTradeName(orderRequest.getOrderName());
//			order.setTradeDesc(orderRequest.getOrderDesc());
//			order.setTradeTime(orderRequest.getRequestTime());
//			order.setAmount(String.valueOf(orderRequest.getOrderMoney()));
//			//order.setCurrency(request.getParameter("currency"));
//			//order.setNote(request.getParameter("note"));
//			order.setCallbackUrl(orderRequest.getCallbackUrl());
//			order.setNotifyUrl(orderRequest.getNotifyUrl());
//			//order.setIp(request.getParameter("ip"));
//			//order.setUserType(request.getParameter("userType"));
//			//order.setUserId(request.getParameter("userId"));
//			order.setExpireTime(String.valueOf(orderRequest.getExpireSecond()));
//			//order.setOrderType(request.getParameter("orderType"));
//			//order.setIndustryCategoryCode(request.getParameter("industryCategoryCode"));
//			//order.setSpecCardNo(request.getParameter("specCardNo"));
//			//order.setSpecId(request.getParameter("specId"));
//			//order.setSpecName(request.getParameter("specName"));
//			//order.setPayChannel(request.getParameter("payChannel"));
//
//			//order.setVendorId(request.getParameter("vendorId"));
//			//order.setGoodsInfo(request.getParameter("goodsInfo"));
//			//order.setOrderGoodsNum(request.getParameter("orderGoodsNum"));
//			//order.setTermInfo(request.getParameter("termInfo"));
//			//order.setReceiverInfo(request.getParameter("receiverInfo"));
//			//order.setRiskInfo(request.getParameter("riskInfo"));
//			filterCharProcess(order);
//
//			String priKey = PropertyUtils.getProperty("wepay.merchant.rsaPrivateKey");
//			String desKey = PropertyUtils.getProperty("wepay.merchant.desKey");
//
//			String cert = CertUtil.getCert();
//			System.out.println("00000000000000000000000000002");
//			if ((cert != null) && (!cert.equals(""))) {
//				order.setCert(cert);
//			}
//			List<String> unSignedKeyList = new ArrayList<String>();
//			unSignedKeyList.add("sign");
//
//			order.setSign(SignUtil.signRemoveSelectedKeys(order, priKey, unSignedKeyList));
//
//			byte[] key = BASE64.decode(desKey);
//			if (StringUtils.isNotBlank(order.getDevice())) {
//				order.setDevice(ThreeDesUtil.encrypt2HexStr(key, order.getDevice()));
//			}
//			order.setTradeNum(ThreeDesUtil.encrypt2HexStr(key, order.getTradeNum()));
//			if (StringUtils.isNotBlank(order.getTradeName())) {
//				order.setTradeName(ThreeDesUtil.encrypt2HexStr(key, order.getTradeName()));
//			}
//			if (StringUtils.isNotBlank(order.getTradeDesc())) {
//				order.setTradeDesc(ThreeDesUtil.encrypt2HexStr(key, order.getTradeDesc()));
//			}
//			order.setTradeTime(ThreeDesUtil.encrypt2HexStr(key, order.getTradeTime()));
//
//			order.setAmount(ThreeDesUtil.encrypt2HexStr(key, order.getAmount()));
//
//			order.setCurrency(ThreeDesUtil.encrypt2HexStr(key, order.getCurrency()));
//			if (StringUtils.isNotBlank(order.getNote())) {
//				order.setNote(ThreeDesUtil.encrypt2HexStr(key, order.getNote()));
//			}
//			order.setCallbackUrl(ThreeDesUtil.encrypt2HexStr(key, order.getCallbackUrl()));
//
//			order.setNotifyUrl(ThreeDesUtil.encrypt2HexStr(key, order.getNotifyUrl()));
//
//			order.setIp(ThreeDesUtil.encrypt2HexStr(key, order.getIp()));
//			if (StringUtils.isNotBlank(order.getUserType())) {
//				order.setUserType(ThreeDesUtil.encrypt2HexStr(key, order.getUserType()));
//			}
//			if (StringUtils.isNotBlank(order.getUserId())) {
//				order.setUserId(ThreeDesUtil.encrypt2HexStr(key, order.getUserId()));
//			}
//			if (StringUtils.isNotBlank(order.getExpireTime())) {
//				order.setExpireTime(ThreeDesUtil.encrypt2HexStr(key, order.getExpireTime()));
//			}
//			if (StringUtils.isNotBlank(order.getOrderType())) {
//				order.setOrderType(ThreeDesUtil.encrypt2HexStr(key, order.getOrderType()));
//			}
//			if (StringUtils.isNotBlank(order.getIndustryCategoryCode())) {
//				order.setIndustryCategoryCode(ThreeDesUtil.encrypt2HexStr(key, order.getIndustryCategoryCode()));
//			}
//			if (StringUtils.isNotBlank(order.getSpecCardNo())) {
//				order.setSpecCardNo(ThreeDesUtil.encrypt2HexStr(key, order.getSpecCardNo()));
//			}
//			if (StringUtils.isNotBlank(order.getSpecId())) {
//				order.setSpecId(ThreeDesUtil.encrypt2HexStr(key, order.getSpecId()));
//			}
//			if (StringUtils.isNotBlank(order.getSpecName())) {
//				order.setSpecName(ThreeDesUtil.encrypt2HexStr(key, order.getSpecName()));
//			}
//			if (StringUtils.isNotBlank(order.getPayChannel())) {
//				order.setPayChannel(ThreeDesUtil.encrypt2HexStr(key, order.getPayChannel()));
//			}
//			if (StringUtils.isNotBlank(order.getVendorId())) {
//				order.setVendorId(ThreeDesUtil.encrypt2HexStr(key, order.getVendorId()));
//			}
//			if (StringUtils.isNotBlank(order.getGoodsInfo())) {
//				order.setGoodsInfo(ThreeDesUtil.encrypt2HexStr(key, order.getGoodsInfo()));
//			}
//			if (StringUtils.isNotBlank(order.getOrderGoodsNum())) {
//				order.setOrderGoodsNum(ThreeDesUtil.encrypt2HexStr(key, order.getOrderGoodsNum()));
//			}
//			if (StringUtils.isNotBlank(order.getTermInfo())) {
//				order.setTermInfo(ThreeDesUtil.encrypt2HexStr(key, order.getTermInfo()));
//			}
//			if (StringUtils.isNotBlank(order.getReceiverInfo())) {
//				order.setReceiverInfo(ThreeDesUtil.encrypt2HexStr(key, order.getReceiverInfo()));
//			}
//			if (StringUtils.isNotBlank(order.getRiskInfo())) {
//				order.setRiskInfo(ThreeDesUtil.encrypt2HexStr(key, order.getRiskInfo()));
//			}
//			if (StringUtils.isNotBlank(order.getCert())) {
//				order.setCert(ThreeDesUtil.encrypt2HexStr(key, order.getCert()));
//			}
//			return order;
//		}
//	
//		private void filterCharProcess(BasePayOrderInfo basePayOrderInfo) {
//			basePayOrderInfo.setVersion(doFilterCharProcess(basePayOrderInfo.getVersion()));
//			basePayOrderInfo.setMerchant(doFilterCharProcess(basePayOrderInfo.getMerchant()));
//			basePayOrderInfo.setDevice(doFilterCharProcess(basePayOrderInfo.getDevice()));
//			basePayOrderInfo.setTradeNum(doFilterCharProcess(basePayOrderInfo.getTradeNum()));
//			basePayOrderInfo.setTradeName(doFilterCharProcess(basePayOrderInfo.getTradeName()));
//			basePayOrderInfo.setTradeDesc(doFilterCharProcess(basePayOrderInfo.getTradeDesc()));
//			basePayOrderInfo.setTradeTime(doFilterCharProcess(basePayOrderInfo.getTradeTime()));
//			basePayOrderInfo.setAmount(doFilterCharProcess(basePayOrderInfo.getAmount()));
//			basePayOrderInfo.setCurrency(doFilterCharProcess(basePayOrderInfo.getCurrency()));
//			basePayOrderInfo.setNote(doFilterCharProcess(basePayOrderInfo.getNote()));
//			basePayOrderInfo.setCallbackUrl(doFilterCharProcess(basePayOrderInfo.getCallbackUrl()));
//			basePayOrderInfo.setNotifyUrl(doFilterCharProcess(basePayOrderInfo.getNotifyUrl()));
//			basePayOrderInfo.setIp(doFilterCharProcess(basePayOrderInfo.getIp()));
//			basePayOrderInfo.setUserType(doFilterCharProcess(basePayOrderInfo.getUserType()));
//			basePayOrderInfo.setUserId(doFilterCharProcess(basePayOrderInfo.getUserId()));
//			basePayOrderInfo.setExpireTime(doFilterCharProcess(basePayOrderInfo.getExpireTime()));
//			basePayOrderInfo.setOrderType(doFilterCharProcess(basePayOrderInfo.getOrderType()));
//			basePayOrderInfo.setIndustryCategoryCode(doFilterCharProcess(basePayOrderInfo.getIndustryCategoryCode()));
//			basePayOrderInfo.setSpecCardNo(doFilterCharProcess(basePayOrderInfo.getSpecCardNo()));
//			basePayOrderInfo.setSpecId(doFilterCharProcess(basePayOrderInfo.getSpecId()));
//			basePayOrderInfo.setSpecName(doFilterCharProcess(basePayOrderInfo.getSpecName()));
//	
//			basePayOrderInfo.setVendorId(doFilterCharProcess(basePayOrderInfo.getVendorId()));
//			basePayOrderInfo.setGoodsInfo(doFilterCharProcess(basePayOrderInfo.getGoodsInfo()));
//			basePayOrderInfo.setOrderGoodsNum(doFilterCharProcess(basePayOrderInfo.getOrderGoodsNum()));
//			basePayOrderInfo.setTermInfo(doFilterCharProcess(basePayOrderInfo.getTermInfo()));
//			basePayOrderInfo.setReceiverInfo(doFilterCharProcess(basePayOrderInfo.getReceiverInfo()));
//		}
//
//		private String doFilterCharProcess(String param) {
//			if ((param == null) || (param.equals(""))) {
//				return param;
//			}
//			return StringEscape.htmlSecurityEscape(param);
//		}
}
