<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>支付宝回调</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<!-- 
total_amount=2.00&buyer_id=2088102116773037&body=大乐透2.1&
trade_no=2016071921001003030200089909&refund_fee=0.00&xnotify_time=2016-07-19 14:10:49
&subject=大乐透2.1&sign_type=RSA2&charset=utf-8&xnotify_type=trade_status_sync
&out_trade_no=0719141034-6418&gmt_close=2016-07-19 14:10:46&gmt_payment=2016-07-19 14:10:47
&trade_status=TRADE_SUCCESS&version=1.0
&sign=kPbQIjX+xQc8F0/A6/AocELIjhhZnGbcBN6G4MM/HmfWL4ZiHM6fWl5NQhzXJusaklZ1LFuMo+lHQUELAYeugH8LYFvxnNajOvZhuxNFbN2LhF0l/KL8ANtj8oyPM4NN7Qft2kWJTDJUpQOzCzNnV9hDxh5AaT9FPqRS6ZKxnzM=
&gmt_create=2016-07-19 14:10:44&app_id=2015102700040153
&seller_id=2088102119685838&xnotify_id=4a91b7a78a503640467525113fb7d8bg8e
	 -->
	 <h4>支付宝回调</h4>
	<form action="./mock/alinotify" method="post">
		
		<div class="control-group">
			<label class="control-label">回调内容：</label>
			<div class="controls">
				<textarea name="notifyText"  rows="40" cols="100">
total_amount=2.00&buyer_id=2088102116773037&body=大乐透2.1&
trade_no=2016071921001003030200089909&refund_fee=0.00
&xnotify_time=2016-07-19 14:10:49&subject=大乐透2.1&sign_type=RSA2&xnotify_type=trade_status_sync&charset=utf-8
&out_trade_no=0719141034-6418&gmt_close=2016-07-19 14:10:46&gmt_payment=2016-07-19 14:10:47
&trade_status=TRADE_SUCCESS&version=1.0
&sign=kPbQIjX+xQc8F0/A6/AocELIjhhZnGbcBN6G4MM/HmfWL4ZiHM6fWl5NQhzXJusaklZ1LFuMo+lHQUELAYeugH8LYFvxnNajOvZhuxNFbN2LhF0l/KL8ANtj8oyPM4NN7Qft2kWJTDJUpQOzCzNnV9hDxh5AaT9FPqRS6ZKxnzM=
&gmt_create=2016-07-19 14:10:44&app_id=2015102700040153
&seller_id=2088102119685838&xnotify_id=4a91b7a78a503640467525113fb7d8bg8e
				</textarea>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>