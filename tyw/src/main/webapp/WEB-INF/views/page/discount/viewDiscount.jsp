<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../../easyui/themes/default/datagrid.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../../easyui/themes/default/easyui.css">
<!-- 日期控件 -->
<script type="text/javascript" src="../../common/datepicker/WdatePicker.js"></script>

<title>新增优惠通道</title>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<script type="text/javascript">
<!--



function check(){
	//document.forms[0].action="placeInfo_view.do?info.policeStation=<s:property value='info.policeStation' />";
	//var   reg   =   RegExp( '/^\d+$/ '); 
    // reg.test(document.getElementById( 'discountValue ').value);  
	if(document.all("discountName").value==""){
		alert("请输入通道名称！");
		document.all("discountName").focus(); 
		return  false;
	}
	if(document.all("discountCode").value==""){
		alert("请输入优惠编码！");
		document.all("discountCode").focus(); 
		return  false;
	}
	if(document.all("discountValue").value==""){
		alert("请输入优惠折扣！");
		document.all("discountValue").focus(); 
		return  false;
	}
	if(document.all("strValidStartDate").value==""){
		alert("请选择有效期开始日期！");
		document.all("strValidStartDate").focus(); 
		return  false;
	}
	if(document.all("strValidEndDate").value==""){
		alert("请选择有效期结束日期！");
		document.all("strValidEndDate").focus(); 
		return  false;
	}
	var begin=discount.strValidStartDate.value;//开始日期
	var over=discount.strValidEndDate.value;//结束日期

	var ass,aD,aS;
	var bss,bD,bS;
	ass=begin.split("-");//以"-"分割字符串，返回数组；
	aD=new Date(ass[0],ass[1]-1,ass[2]);//格式化为Date对像;
	aS=aD.getTime(); 
	bss=over.split("-");
	bD=new Date(bss[0],bss[1]-1,bss[2]);
	bS=bD.getTime();
	if(aS>bS){
		alert("有效期结束日期不能小于有效期起始日期");
		return false;
	}


	  document.forms[0].submit();
}
	function returnBack (){
		location.href='../../discountlist';
	}
</script>

</head>

<body>

<form name="discount" method="POST" modelAttribute="discount" >

  <table width="80%" class="datagrid-header" align="center">
    <tr>
    <td height="25" colspan="4" align="center" class="title1">新增优惠通道</td>
  	</tr>
    <tr> 
      <td  class="title2" width="24%">&nbsp;&nbsp;通道名称:</td>
      <td width="76%"><label> 
        <input type="text" name="discountName" value="${discount.discountName}" readonly="true">
        <span class="STYLE1">*</span></label></td>
    </tr>
    <tr> 
      <td  class="title2">&nbsp;&nbsp;优惠编号:</td>
      <td >
        <input type="text" name="discountCode" value="${discount.discountCode}">
        <span class="STYLE1">*</span></td>
    </tr>
        <tr> 
      <td  class="title2">&nbsp;&nbsp;优惠折扣:</td>
      <td >
        <input type="text" name="discountValue" readonly="true" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" value="${discount.discountValue}"> 
        <span class="STYLE1">* &nbsp;&nbsp;只能输入数字.</span></td>
    </tr>
    <tr> 
      <td  class="title2">&nbsp;&nbsp;优惠说明:</td>
      <td >
        <input type="text" name="remark" readonly="true" value="${discount.remark}"></td>
    </tr>
	<tr> 
      <td  class="title2">&nbsp;&nbsp;有效期始:</td>
      <td >
		<input name="strValidStartDate" readonly="true" type="text" class="Wdate" onfocus="WdatePicker({skin:'blue'})" value="${discount.validStartDate}">
        <span class="STYLE1">*</span></td>
    </tr>
    <tr> 
      <td  class="title2">&nbsp;&nbsp;有效期至:</td>
      <td >
		<input name="strValidEndDate"  readonly="true" type="text" class="Wdate" onfocus="WdatePicker({skin:'blue'})" value="${discount.validEndDate}">
        <span class="STYLE1">*</span></td>
    </tr>
  </table>
  <table width="80%" align="center">
    <tr>
      <td height="17" align="center">

		<button class="form-table-button"  style="width: 80px;" type="button" onclick="returnBack();">返回&nbsp;&nbsp;</button>   
    </tr>
</table>

</form>

</body>
</html>
