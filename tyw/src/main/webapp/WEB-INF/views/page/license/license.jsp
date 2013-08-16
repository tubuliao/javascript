<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>license登录</title>
</head>
<body>
<form action="/licenseLogin" id="licenseLogin" name="licenseLogin" onsubmit="return check();">
			
<input type="text" id="licenseNum" name="licenseNum"/>
			
<input type="submit" value="登录"/>
			
</form>
</body>
</html>
<script type="text/javascript">
function check(){
	var num = document.getElementById("licenseNum").value;
	if(num.length==""){
		alert("请输入序列号！");
		document.getElementById("licenseNum").focus();
		return false;
	}
}
</script>