<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="css/mainstyle.css" />
<link type="text/css" rel="stylesheet" href="css/jsandcss/happysysLaw.css" />
<!-- 日期控件 -->
<script type="text/javascript" src="../../common/datepicker/WdatePicker.js"></script>

<title>会员</title>
<script type="text/javascript" language="javascript">
function getRadio(userType){
 var ifrm = document.getElementById("iframe");
 if(ifrm){
   if(ifrm.style.display=='none'){
	   ifrm.style.display='block';
   }
 }
 //document.getElementById("iframe").style.display="";//显示

  if(userType==1){
  	ifrm.src="/user_common";
  }else if(userType==2){
  	ifrm.src="/user_company";
  }else if(userType==3){
  	ifrm.src="/user_business";
  }else if(userType==4){
  	ifrm.src="/user_admin";
  }
  
}
</script>
</head>

<body>
<table class="barTable" style="width:96%;">
    
  <tr> 
    <td>新增会员</td>
    </tr>
</table>
<form name="user" method="POST" modelAttribute="user" >

  <table width="80%"  class="form-table"  style="width: 96%;" align="center">

    <tr> 
      <td class="form-table-label">姓名:</td>
      <td colspan="3"><label> 
        <input type="text" name="username">
        </label></td>
    </tr>
    <tr> 
      <td class="form-table-label">登录名:</td>
      <td colspan="3"><label> 
        <input type="text" name="aliasname">
        </label></td>
    </tr>
    <tr> 
      <td class="form-table-label">登录口令:</td>
      <td colspan="3"><label> </label>
        <input type="text" name="password"></td>
    </tr>
    <!--<tr>
      <td class="form-table-label">账户角色</td>
      <td colspan="3"><label>
        <select name="roles">
          <option selected value="admin1">管理员</option>
          <option value="role1">角色1</option>
          <option value="role2">角色2</option>
        </select>
      </label></td>
    </tr>
-->
    <tr> 
      <td class="form-table-label">是否管理员:</td>
      <td colspan="3"><label> 
        <input type="radio" name="admin" value="1">
        是 
        <input type="radio" name="admin" value="0">
        否 </label></td>
    </tr>
	<tr> 
      <td class="form-table-label">账户是否可用:</td>
      <td colspan="3"><label> 
        <input type="radio" name="enable" value="1">
        是 
        <input type="radio" name="enable" value="0">
        否 </label></td>
    </tr>
	<tr> 
      <td class="form-table-label">帐户是否锁定:</td>
      <td colspan="3"><label> 
        <input type="radio" name="nonLocked" value="1">
        是 
        <input type="radio" name="nonLocked" value="0">
        否 </label></td>
    </tr>
	<tr> 
      <td class="form-table-label">账户类型:</td>
      <td colspan="3"><label> 
        <input type="radio" name="userType" value="1"  onclick="getRadio('1')">
        个人会员 
        <input type="radio" name="userType" value="2"  onclick="getRadio('2')">
        企业会员
		 <input type="radio" name="userType" value="3"  onclick="getRadio('3')">
        渠道商 
        <input type="radio" name="userType" value="4"  onclick="getRadio('4')">
        管理员 </label></td>
    </tr>
  </table>
   	<div align="center" style="float:center;overflow:auto;width:100%;height:150px" >
	 	<iframe id="iframe" width="80%"  style="display:none;overflow:hidden;height:350px;" frameborder="0" scrolling="auto">
	 	</iframe>
	 	   
 	</div>
  
  <table class="form-table">
    <tr>
      <td height="17" align="center">
	    <button class="form-table-button"  style="width: 80px;" type="submit">保存&nbsp;&nbsp;</button>	   
	    <!--<button class="form-table-button"  style="width: 80px;" type="button" onclick="javascript::location.href='/userlist'">返回</button> -->  
		
    </tr>
</table>

</form>

</body>
</html>
