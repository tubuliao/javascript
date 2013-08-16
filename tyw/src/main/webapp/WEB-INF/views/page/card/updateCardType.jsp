<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../../css/body.css" rel="stylesheet" type="text/css" />

<!-- 日期控件 -->
<script type="text/javascript" src="common/datepicker/WdatePicker.js"></script>

<title>新增产品</title>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
<script type="text/javascript">
var rownum = 0; 
function addTable() 
{ 
//行号是从0开始
    rownum=$("#table2 tr").length-1;
	//alert(rownum);
    var chk="<input type='checkbox' id='chk_"+rownum+"' name='chk_"+rownum+"'/>";
    var text="<input type='text' id='cardBizs["+rownum+"].value' name='cardBizs["+rownum+"].value' width='30px'/>";
    var sel="<select name='cardBizs["+rownum+"].bizType' ><option value='1'>年份</option><option value='0'>点数</option></select>";
    var row="<tr><td>"+chk+"</td><td  class='title2' width='10%'>&nbsp;&nbsp;业务类型:</td><td>"+sel+"</td>";
	    row=row+"<td  class='title2'>&nbsp;&nbsp;业务值:</td><td>"+text+"</td></tr>";
    $(row).insertAfter($("#table2 tr:eq("+rownum+")"));   
}
function btnDeleteRow()
{
   $("#table2 tr").each(function(i){
       var chk=$(this).find("input[type='checkbox']");
       if(chk.attr("id")!="checkall")//不能删除标题行
       {
		   if(chk.attr("checked"))
		   {
			 //alert($(this).html);
			  $(this).remove();
		   }
       }
	   
    });

}
function check(){
	//document.forms[0].action="placeInfo_view.do?info.policeStation=<s:property value='info.policeStation' />";
	//var   reg   =   RegExp( '/^\d+$/ '); 
    // reg.test(document.getElementById( 'discountValue ').value);  
	if(document.all("title").value==""){
		alert("请输入标题！");
		document.all("title").focus(); 
		return  false;
	}
	if(document.all("content").value==""){
		alert("请输入业务内容！");
		document.all("content").focus(); 
		return  false;
	}
	if(document.all("price").value==""){
		alert("请输入单价！");
		document.all("price").focus(); 
		return  false;
	}
	



	  document.forms[0].submit();
}
	function returnBack (){
		location.href='../../cardTypelist';
	}
</script>
</head>

<body>
  <table width="80%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolorlight="#185AB4" bordercolordark="#FFFFFF" id="table1">
    <tr>
    <td height="25" colspan="10" align="center" class="title1">修改产品类型</td>
  	</tr>
    <tr> 
      <td  class="title2" width="24%">&nbsp;&nbsp;标题:</td>
      <td width="76%"><label> 
        <input type="text" name="title"  value="${cardType.title}">
        <span class="STYLE1">*</span></label></td>
    </tr>
    <tr> 
      <td  class="title2">&nbsp;&nbsp;业务内容:</td>
      <td >
        <input type="text" name="content"  value="${cardType.content}">
        <span class="STYLE1">*</span></td>
    </tr>
        <tr> 
      <td  class="title2">&nbsp;&nbsp;单价:</td>
      <td >
        <input type="text" name="price"  value="${cardType.price}">
        <span class="STYLE1">*</span></td>
    </tr>
    <tr> 
      <td  class="title2">&nbsp;&nbsp;状态:</td>
      <td >&nbsp;<input type="radio" name="status" value="1">
        有效
        <input type="radio" name="status" value="0">
        无效</td>
    </tr>
	
	    <tr> 
      <td  class="title2">&nbsp;&nbsp;备注:</td>
      <td >
        <input type="text" name="summary"  value="${cardType.summary}"></td>
    </tr>
	
  </table>

 <table width="80%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolorlight="#185AB4" bordercolordark="#FFFFFF">
    <tr>
     <td height="25" colspan="13" align="center" class="title1">产品功能列表</td>
    </tr>
    <tr> 

      <td class="title2" align="center">序号</td>
      <td  class="title2" align="center"> 业务类型</td>
      <td  class="title2" align="center">业务值</td>
	  <td  class="title2" align="center">状态</td>
    </tr>
	<c:forEach items="${cardType.cardBizs}" var="cardBizList" varStatus="xh">
    <tr class="list-table-content1"> 
      <td align="center">${xh.count}</td>
      <td align="center">&nbsp;
	  	<c:if test="${cardBizList.bizType==0}">
			访问时长
		</c:if>
		<c:if test="${cardBizList.bizType==1}">
			下载
		</c:if>
		<c:if test="${cardBizList.bizType==2}">
			视频
		</c:if>
	  </td>
      <td align="center">&nbsp;${cardBizList.value}</td>
      <td align="center">&nbsp;
	  	<c:if test="${cardBizList.status==0}">
			无效
		</c:if>
		<c:if test="${cardBizList.status==1}">
			有效
		</c:if>
	  </td>
    </tr>
   </c:forEach>

  </table> 
    <table width="80%" align="center">
    <tr>
      <td height="17" align="center">
	    
	    <button class="form-table-button"  style="width: 80px;" type="button" onclick="check();">保存&nbsp;&nbsp;</button>	   
		<button class="form-table-button"  style="width: 80px;" type="button" onclick="returnBack();">返回&nbsp;&nbsp;</button>
		
    </tr>
</table>
</body>
</html>
