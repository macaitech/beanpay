<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<title>回调用户</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<h4>回调用户</h4>
	<form action="./mock/calluser" method="post">
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
				<input name="merchantOrderNo"  value ="20180418007" size="50"/>
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