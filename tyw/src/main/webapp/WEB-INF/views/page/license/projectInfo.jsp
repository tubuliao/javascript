<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>项目信息</title>
</head>
<body>
<form action="/addProjectInfo" id="projectInfo" name="projectInfo" onsubmit="return check();">
			
<td>项目组名称：<input type="text" id="proName" name="proName"/></td></br>
<td>项目组地址：<input type="text" id="proAddress" name="proAddress"/></td></br>
			
<input type="submit" value="提交"/>
			
</form>
</body>
</html>
<script type="text/javascript">
	function check(){
		var name = document.getElementById("proName").value;
		var addr = document.getElementById("proAddress").value;
		if(name.length==""){
			alert("请填写项目组名称！");
			document.getElementById("proName").focus();
			return false;
		}
		if(addr.length==""){
			alert("请填写项目组地址！");
			document.getElementById("proAddress").focus();
			return false;
		}
	}
</script>