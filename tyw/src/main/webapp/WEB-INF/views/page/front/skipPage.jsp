<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>跳转页</title>
</head>
<body>
    
	<div style="text-align:center;padding-top:80px;">
		<%
			response.setHeader("refresh","2;URL=login.jsp") ;
		%>
		<h2><font color="green">密码修改成功，请用新密码重新登录！<br /><br />2秒后跳转到登陆页面……</font></h2>
	</div>

</body>
</html>