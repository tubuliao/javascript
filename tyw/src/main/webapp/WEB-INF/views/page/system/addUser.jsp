<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="easyui/themes/default/datagrid.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
<!-- 日期控件 -->
<script type="text/javascript" src="common/datepicker/WdatePicker.js"></script>

<title>会员</title>
<script type="text/javascript" language="javascript">
function getRadio(userType){
 for(var i=1;i<4;i++){
 	document.getElementById('div'+i).style.display="none";
 }
 var obj = document.getElementById('div'+userType);
 if(obj){
   if(obj.style.display=='none'){
	   obj.style.display='block';
   }
 }

 //document.getElementById("iframe").style.display="";//显示

 /* if(userType==1){
  	ifrm.src="/user_common";
  }else if(userType==2){
  	ifrm.src="/user_company";
  }else if(userType==3){
  	ifrm.src="/user_business";
  }else if(userType==4){
   
  	document.getElementById("iframe").style.display="none";
  }*/
  
}
function checkUser(){
 
    var re = /^[a-zA-z]\w{5,17}$/;
    var name=document.getElementById("username").value;
	if(name==""){
		alert("请输入登录名！");
		document.user.username.focus(); 
		return  false;
	}
	if(document.getElementById("aliasname").value==""){
		alert("请输入用户显示名称！");
		document.user.aliasname.focus(); 
		return  false;
	}
	if(document.getElementById("password").value==""){
		alert("请输入密码！");
		document.user.password.focus(); 
		return  false;
	}
	if(document.all("check_password").value==""){
		alert("请输入确认密码！");
		document.all("check_password").focus(); 
		return  false;
	}
    if(!re.test(name)){
        alert("用户名不合法！");
		document.user.username.focus(); 
		return  false; 
    }  
	//alert(document.getElementById("password").value.length);
	if(document.getElementById("password").value.length>16 || document.getElementById("password").value.length<6) 
	{ 
		alert("密码长度不能小于6大于16！");		document.user.password.focus();  
		return false; 
	} 
	
	if  (document.user.password.value  !=  document.user.check_password.value)  {   
		alert("您两次输入的密码不一样！请重新输入."); 
		document.user.password.value="";
		document.user.check_password.value="";
		document.user.password.focus(); 
		return  false; 
	}

	var resualt=false;
	var userType;
	for(var i=0;i<document.user.userType.length;i++)
	{
		if(document.user.userType[i].checked)
		{
		  resualt=true;
		  userType=document.user.userType[i].value;
		  break;
		}
	}
	if(!resualt)
	{
		alert("请选择账户类型！");
		return false;
	}
	
	if(userType=="1"){  //个人会员
		if(!isNull(document.all("additional.email").value)){
		  if(!checkEmail(document.all("additional.email").value)){
		  	document.all("additional.email").focus(); 
			return  false; 
		  }
		}
		
		if(!isNull(document.all("additional.mobile").value)){
			if(!checkMobil(document.all("additional.mobile").value)){
		  	document.all("additional.mobile").focus(); 
			return  false; 
		  }
		}
	}
	if(userType=="2"){  //企业会员
		if(!isNull(document.all("firm.phone").value)){
		  if(!checkPhone(document.all("firm.phone").value)){
		  	document.all("firm.phone").focus(); 
			return  false; 
		  }
		}
		
		if(!isNull(document.all("firm.fax").value)){
			if(!checkPhone(document.all("firm.fax").value)){
		  	document.all("firm.fax").focus(); 
			return  false; 
		  }
		}
		if(!isNull(document.all("firm.zip").value)){
			if(!CheckPostCode(document.all("firm.zip").value)){
		  	document.all("firm.zip").focus(); 
			return  false; 
		  }
		}
		if(!isNull(document.all("firm.email").value)){
			if(!checkEmail(document.all("firm.email").value)){
		  	document.all("firm.email").focus(); 
			return  false; 
		  }
		}
	
	}
	if(userType=="3"){  //渠道商
		if(!isNull(document.all("agent.phone").value)){
		  if(!checkPhone(document.all("agent.phone").value)){
		  	document.all("agent.phone").focus(); 
			return  false; 
		  }
		}
		
		if(!isNull(document.all("agent.fax").value)){
			if(!checkPhone(document.all("agent.fax").value)){
		  	document.all("agent.fax").focus(); 
			return  false; 
		  }
		}
		if(!isNull(document.all("agent.zip").value)){
			if(!CheckPostCode(document.all("agent.zip").value)){
		  	document.all("agent.zip").focus(); 
			return  false; 
		  }
		}
		if(!isNull(document.all("agent.email").value)){
			if(!checkEmail(document.all("agent.email").value)){
		  	document.all("agent.email").focus(); 
			return  false; 
		  }
		}
	}
	

    document.forms[0].submit();
  
}
	function isNull( str ){ 
		if ( str == "" ) return true; 
		var regu = "^[ ]+$"; 
		var re = new RegExp(regu); 
		return re.test(str); 
	} 
	function checkEmail(s){
			var emailRe =/^[-_A-Za-z0-9]+@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/; 
		 if(!emailRe.test(s)){
			 alert("请输入正确的邮箱地址！");
			 //document.all("additional.email").focus(); 
			 return  false; 
        }
		return true;
	}
    function checkMobil(s){
		var regu =/^1\d{10}/; 
		if (!regu.test(s)) { 
			 alert("手机号码不符合规则！");
			 //document.all("additional.mobile").focus(); 
			 return  false; 
		}
		return true;
	}
	
	function checkPhone(str){
    var re = /^0\d{2,3}-?\d{7,8}$/;
    if(!re.test(str)){
        alert("电话号码不正确！");
    	return  false; 
	}
		return true;
	}
	function CheckPostCode (str){
		var reg = /^\d{6}$/  ;  
		if (!reg.test(str)){
        alert("邮编格式不正确！");
    	return  false; 
	}
		return true; 

	}
	function returnBack (){
		location.href='userlist';
	}

</script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>

<body>

<form name="user" method="POST" modelAttribute="user" >
  <table width="80%" class="datagrid-header" align="center">
    <tr>
    <td height="25" colspan="4" align="center" >新增会员</td>
  	</tr>
    <tr> 
      <td  class="title2" width="24%">&nbsp;&nbsp;登录名:</td>
      <td width="76%"><label> 
        <input type="text" name="username" id="username">
        </label><span class="STYLE1">* 6~18个字符，可使用字母、数字、下划线，需以字母开头</span></td>
    </tr>
    <tr> 
      <td  class="title2">&nbsp;&nbsp;显示名:</td>
      <td >
        <input type="text" name="aliasname" id="aliasname">
        <span class="STYLE1">*</span></td>
    </tr>
    <tr> 
      <td  class="title2">&nbsp;&nbsp;登录口令:</td>
      <td >
        <input type="password" name="password" id="password">
        <span class="STYLE1">*  6~16个字符，区分大小写</span></td>
    </tr> 
    <tr> 
      <td  class="title2">&nbsp;&nbsp;确认口令:</td>
      <td >
        <input type="password" name="check_password" id="check_password">
        <span class="STYLE1">* 请再次填写密码 </span></td>
    </tr>
    <!--<tr>
      <td class="title2">账户角色</td>
      <td ><label>
        <select name="roles">
          <option selected value="admin1">管理员</option>
          <option value="role1">角色1</option>
          <option value="role2">角色2</option>
        </select>
      </label></td>
    </tr>
-->

	<tr> 
      <td class="title2">&nbsp;&nbsp;账户是否可用:</td>
      <td ><label> 
        <input type="radio" name="enable" value="1" checked="checked">
        是 
        <input type="radio" name="enable" value="0">
        否 </label></td>
    </tr>
	<tr> 
      <td class="title2">&nbsp;&nbsp;帐户是否锁定:</td>
      <td ><label> 
        <input type="radio" name="nonLocked" value="0" checked="checked">
        是 
        <input type="radio" name="nonLocked" value="1">
        否 </label></td>
    </tr>
	<tr> 
      <td class="title2">&nbsp;&nbsp;账户类型:</td>
      <td ><label> 
        <input type="radio" name="userType" value="1"  onclick="getRadio('1')">
        个人会员 
        <input type="radio" name="userType" value="2"  onclick="getRadio('2')">
        企业会员
		 <input type="radio" name="userType" value="3"  onclick="getRadio('3')">
        渠道商 
        <input type="radio" name="userType" value="4"  onclick="getRadio('4')">
        管理员 <span class="STYLE1">*</span></label></td>
    </tr>
  </table>
   	<div  style="overflow:auto;display:none" id="div1">
	 	<!--<iframe id="iframe" width="80%"  style="display:none;overflow:hidden;" frameborder="0" scrolling="auto">
	 	</iframe>-->
	 	  <table width="80%"   align="center" class="datagrid-header">
			<tr> 
			  <td class="title2" width="24%">&nbsp;&nbsp;邮箱:</td>
			  <td width="76%"><label> 
				<input type="text" name="additional.email">
				</label></td>
			</tr>
			<tr> 
			  <td class="title2">&nbsp;&nbsp;手机号:</td>
			  <td ><label> 
				<input type="text" name="additional.mobile">
				</label></td>
			</tr>
			<tr> 
			  <td class="title2">&nbsp;&nbsp;性别:</td>
			  <td ><label> </label>
				
				 <input type="radio" name="additional.sex" value="1" >
				男 
				<input type="radio" name="additional.sex" value="0">
				女
				</td>
			</tr>
			
			<tr> 
			  <td class="title2">&nbsp;&nbsp;住址:</td>
			  <td ><label>
			  <input type="text" name="additional.location" />
			  </label></td>
			</tr>
			<tr> 
			  <td class="title2">&nbsp;&nbsp;生日:</td>
			  <td >
			  <input name="str_birthday" type="text" class="Wdate" onfocus="WdatePicker({skin:'blue'})">
			  </td>
			</tr>
		  </table>  
 	 </div>
    <div  style="float:center;overflow:auto;display:none" id="div2">
			<table width="80%"  align="center" class="datagrid-header">
				<tr> 
				  <td width="24%" class="title2">&nbsp;&nbsp;企业名称:</td>
				  <td width="76%" ><label> 
					<input type="text" name="firm.name">
				  </label></td>
				</tr>
				<tr> 
				  <td class="title2">&nbsp;&nbsp;地址:</td>
				  <td ><label> 
					<input type="text" name="firm.addr">
					</label></td>
				</tr>
				<tr> 
				  <td class="title2">&nbsp;&nbsp;法人代表:</td>
				  <td ><label> </label>
					<input type="text" name="firm.linkman"></td>
				</tr>
			   
				<tr> 
				  <td class="title2">&nbsp;&nbsp;联系电话:</td>
				  <td ><label>
				  <input type="text" name="firm.phone" />
				  </label></td>
				</tr>
				<tr> 
				  <td class="title2">&nbsp;&nbsp;传真:</td>
				  <td ><label>
				  <input type="text" name="firm.fax" />
				  </label></td>
				</tr>
				<tr> 
				  <td class="title2">&nbsp;&nbsp;邮编:</td>
				  <td ><label>
				  <input type="text" name="firm.zip" />
				  </label></td>
				</tr>
				<tr> 
				  <td class="title2">&nbsp;&nbsp;电子邮箱:</td>
				  <td ><label>
				  <input type="text" name="firm.email" />
				  </label></td>
				</tr>
			  </table> 
 	 </div>
    <div  style="float:center;overflow:auto;display:none" id="div3">
			<table width="80%"  align="center" class="datagrid-header">
				<tr> 
				  <td width="24%" class="title2">&nbsp;&nbsp;渠道商名称:</td>
					   <td width="76%" ><label> 
					<input type="text" name="agent.name">
				  </label></td>
				</tr>
				<tr> 
				  <td class="title2">&nbsp;&nbsp;地址:</td>
				  <td ><label> 
					<input type="text" name="agent.addr">
					</label></td>
				</tr>
				<tr> 
				  <td class="title2">&nbsp;&nbsp;法人代表:</td>
				  <td >
					<input type="text" name="agent.linkman"></td>
				</tr>
			   
				<tr> 
				  <td class="title2">&nbsp;&nbsp;联系电话:</td>
				  <td ><label>
				  <input type="text" name="agent.phone" />
				  </label></td>
				</tr>
				<tr> 
				  <td class="title2">&nbsp;&nbsp;传真:</td>
				  <td ><label>
				  <input type="text" name="agent.fax" />
				  </label></td>
				</tr>
				<tr> 
				  <td class="title2">&nbsp;&nbsp;邮编:</td>
				  <td ><label>
				  <input type="text" name="agent.zip" />
				  </label></td>
				</tr>
				<tr> 
				  <td class="title2">&nbsp;&nbsp;电子邮箱:</td>
				  <td ><label>
				  <input type="text" name="agent.email" />
				  </label></td>
				</tr>
				<tr> 
				  <td class="title2">&nbsp;&nbsp;默认优惠折扣:</td>
				  <td ><label>
				  <input type="text" name="agent.discountValue" value="0.9" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/> &nbsp;&nbsp; 只能输入数字
				  </label></td>
				</tr>
			  </table>
 	 </div>
  <table width="80%" align="center">
    <tr>
      <td height="17" align="center">
	    
	    <button class="form-table-button"  style="width: 80px;" type="button" onclick="checkUser();">保存&nbsp;&nbsp;</button>	   
		<button class="form-table-button"  style="width: 80px;" type="button" onclick="returnBack();">返回&nbsp;&nbsp;</button>
    </tr>
</table>

</form>

</body>
</html>
