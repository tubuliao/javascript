<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%response.setHeader("progma","no-cache");%>
<%response.setHeader("Cache-Control","no-cache"); %>   
<%response.setDateHeader("Expires",0); %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>录入信息</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/css/project.css" rel="stylesheet" type="text/css" />

<script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>
</head>

<body>
<div class="content bg1">
	<div class="project_submit">
   	  <div class="logo_box">
        	<img class="left" src="/images/search_03.png" />
        <div class="left">
            	<h3>项目部计划</h3>
                <p>专业的建筑信息服务平台</p>
        </div>
            <div class="clear"></div>
	  </div>
      <div class="liucheng"><img src="/images/project/lc1_06.png" /></div>
      <form action="/addProjectInfo" method="post" id="projectInfo" name="projectInfo" onsubmit="return check();">
        <div class=" shang10">
          <div class="project_login_input_box left">
          <input type="hidden" id="id" name="id" value="${lp.id}"/>
          <input name="proName" type="text" class="project_login_input" id="proName" value="请输入项目组名称" onfocus="if(this.value=='请输入项目组名称')this.value=''" onblur="if(this.value=='')this.value='请输入项目组名称'" />
          <input name="proAddress" type="text" class="project_login_input2" id="proAddress" value="请输入项目组地址" onfocus="if(this.value=='请输入项目组地址')this.value=''" onblur="if(this.value=='')this.value='请输入项目组地址'" />
          </div>
          
          <input class="project_login_submit left" type="submit" name="button" id="button" value="保存" />
          
        </div>
        </form>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
	function check(){
		var name = document.getElementById("proName").value;
		var addr = document.getElementById("proAddress").value;
		if(name.length==""||name=="请输入项目组名称"){
			alert("请填写项目组名称！");
			document.getElementById("proName").focus();
			return false;
		}
		if(addr.length==""||addr=="请输入项目组地址"){
			alert("请填写项目组地址！");
			document.getElementById("proAddress").focus();
			return false;
		}
		if(name.length>100){
			alert("你输入的项目组名称过长，请控制在100字以内！");
			document.getElementById("proName").focus();
			return false;
		}
		if(addr.length>100){
			alert("你输入的项目组地址过长，请控制在100字以内！");
			document.getElementById("proAddress").focus();
			return false;
		}
	}
	
	window.onload=function(){
		
		var pro_Name = "${lp.proName}";
		var pro_Address = "${lp.proAddress}";
		
		if(pro_Name!=""){
			$("#proName").val(pro_Name);
		}
		if(pro_Address!=""){
			$("#proAddress").val(pro_Address);
		}
	}
</script>
