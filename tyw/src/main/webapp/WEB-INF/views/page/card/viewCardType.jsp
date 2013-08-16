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
<!--
	function returnBack (){
		location.href='../../cardTypelist';
	}
</script>
</head>

<body>
  <table width="80%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolorlight="#185AB4" bordercolordark="#FFFFFF" id="table1">
    <tr>
    <td height="25" colspan="10" align="center" class="title1">查看产品类型</td>
  	</tr>
    <tr> 
      <td  class="title2" width="24%">&nbsp;&nbsp;标题:</td>
      <td width="76%"><label> 
        <input type="text" name="title" readonly="true" value="${cardType.title}">
        <span class="STYLE1">*</span></label></td>
    </tr>
    <tr> 
      <td  class="title2">&nbsp;&nbsp;业务内容:</td>
      <td >
        <input type="text" name="content" readonly="true" value="${cardType.content}">
        <span class="STYLE1">*</span></td>
    </tr>
        <tr> 
      <td  class="title2">&nbsp;&nbsp;单价:</td>
      <td >
        <input type="text" name="price" readonly="true" value="${cardType.price}">
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
        <input type="text" name="summary" readonly="true" value="${cardType.summary}"></td>
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
	   <button class="form-table-button"  style="width: 80px;" type="button" onclick="returnBack();">返回&nbsp;&nbsp;</button>  
    </tr>
</table>

</body>
</html>
