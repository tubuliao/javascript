<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../../css/body.css" rel="stylesheet" type="text/css" />

<!-- 日期控件 -->
<script type="text/javascript" src="common/datepicker/WdatePicker.js"></script>

<title>新闻资讯</title>
</head>

<body>

<form name="news" method="POST" modelAttribute="news" >

  <table width="80%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolorlight="#185AB4" bordercolordark="#FFFFFF">
    <tr>
    <td height="25" colspan="4" align="center" class="title1">属性信息 </td>
  	</tr>
    <tr> 
    	<input type="hidden" name="id" value="${news.id}">
      <td  class="title2" width="24%">&nbsp;&nbsp;标题:</td>
      <td width="76%"><label> 
        ${news.title}
        </label></td>
    </tr>
    <tr> 
      <td  class="title2">&nbsp;&nbsp;内容:</td>
      <td >
        ${news.content}
        <span class="STYLE1"></span></td>
    </tr>
    
  </table>
  
    <table width="80%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolorlight="#185AB4" bordercolordark="#FFFFFF">
    <tr>
    <td height="25" colspan="4" align="center" class="title1">标签信息 </td>
  	</tr>
    <tr> 
      <td  class="title2" width="24%">&nbsp;&nbsp;标签列表:</td>
      <td width="76%"><label> 
      	<c:if test="${empty news.tags}">
      		<span style="margin-left:5px">未指定标签</span>
      	</c:if>
      	<c:forEach items="${news.tags}" var="tag" varStatus="xh">
		<span style="color:red ;margin-left:5px">${tag.name}</span>
		</c:forEach>
        </label></td>
    </tr>    
  </table>
  <table width="80%" align="center">
    <tr>
      <td height="17" align="center">
	    <button class="form-table-button"  style="width: 80px;" type="submit">审批&nbsp;&nbsp;</button>	   
    </tr>
</table>

</form>

</body>
</html>
