<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>订单测试</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	
	<form action="./mock/pay" method="post">
		<div class="control-group">
			<label class="control-label">appId：</label>
			<div class="controls">
				<input name="appId"  value ="23dsddsas23213dasdsd" size="50"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">merchantId：</label>
			<div class="controls">
				<input name="merchantId"  value ="1018040101" size="50"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">商户订单号：</label>
			<div class="controls">
				<input name="merchantOrderNo"  value ="20180420001" size="50"/>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">订单名称：</label>
			<div class="controls">
				<input name="orderName"  value ="沧海上天" size="50"/>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">订单金额：</label>
			<div class="controls">
				<input name="orderMoney"  value ="1" size="50"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">支付渠道：</label>
			<div class="controls">
				<input name="payChannel"  value ="alipay" size="50"/>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">支付方式：</label>
			<div class="controls">
				<input name="payWay"  value ="app" size="50"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">终端用户ip：</label>
			<div class="controls">
				<input name="clientIp"  value ="10.0.0.2" size="50"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">商户回调地址：</label>
			<div class="controls">
				<input name="notifyUrl"  value ="./mock/callback" size="50"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">签名：</label>
			<div class="controls">
				<input name="sign"  value ="" size="50"/>
			</div>
		</div>
		
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form>
</body>
</html>