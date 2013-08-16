<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>首页</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/css/project.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div class="content">
	<div class="project_login">
   	  <div class="logo_box">
        	<img class="left" src="/images/search_03.png" />
        <div class="left">
            	<h3>项目部计划</h3>
                <p>专业的建筑信息服务平台</p>
        </div>
            <div class="clear"></div>
	  </div>
        <form action="/licenseLogin" method="post" id="licenseLogin" name="licenseLogin" onsubmit="return check();">
        <div class=" shang20">
          <input name="licenseNum" type="text" class="project_login_input" id="licenseNum" value="请输入验证码" onfocus="if(this.value=='请输入验证码')this.value=''" onblur="if(this.value=='')this.value='请输入验证码'" />
          <input class="project_login_button" type="submit" name="button" id="button" value="提交" />
          </div>
        </form>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
function check(){
	var num = document.getElementById("licenseNum").value;
	if(num.length==""||num=="请输入验证码"){
		alert("请输入序列号！");
		document.getElementById("licenseNum").focus();
		return false;
	}
}
</script>
