<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${pageContext.request.contextPath}/css/body.css" rel="stylesheet" type="text/css" />
<title>缴费记录明细</title>
<style type="text/css">
input[type=text] {
	border-top: 1px solid #BBBBBB;
	border-right: 1px solid #BBBBBB;
	border-bottom: 1px solid #BBBBBB;
	border-left: 1px solid #BBBBBB;
	height: 18px;
	border-bottom-color: #BBBBBB;
}

.STYLE1 {color: #CC0000}
</style>
<script type="text/javascript">
<!--
	function returnBack (){
		location.href='../../paymentlist';
	}
</script>
</head>

<body>

<form name="payment" method="POST" modelAttribute="payment" >
    <h2 align="center" class="STYLE1">缴费记录明细</h2>
  <table width="85%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolorlight="#9B9BFF" bordercolordark="#C9E0F5">

	<tr> 
      <td >&nbsp;&nbsp;&nbsp;&nbsp;订单编号:</td>
      <td width="81%" colspan="3" ><label> 
        <input name="orderCode" type="text" value="${payment.orderCode}" size="50" readonly="true" >
      </label></td>
	  
    </tr>

    <tr>
	  <td   width="19%">&nbsp;&nbsp;&nbsp;&nbsp;下单时间:</td>
      <td width="81%" colspan="3" ><label> 
        <input type="text" name="createdAt" readonly="true" value="${payment.createdAt}" size="30">
      </label></td>
    </tr>
    <tr> 
      <td  >&nbsp;&nbsp;&nbsp;&nbsp;优惠编号:</td>
      <td colspan="3" >
        <input type="text" name="discountCode" readonly="true" value="${payment.discountCode}" size="30"></td>
	</tr>

	
		<tr> 
      <td  colspan="4">&nbsp;&nbsp;&nbsp;&nbsp;已购产品：
	  	<table width="72%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolorlight="#185AB4" bordercolordark="#FFFFFF">

	

    <tr> 
     <!-- <td width="6%" class="title2"><input type="checkbox" onClick="selectall(this)">
      全选</td>-->
      <td width="16%" align="center" class="title2">序号</td>
      <td width="38%" align="center"  class="title2">标题</td>
      <td width="20%" align="center"  class="title2">单价</td>
      <td width="26%" align="center"  class="title2">数量</td>
    </tr>

	<c:forEach var="entry" items="${payment.paymentItems}" varStatus="xh">


    <tr class="list-table-content1"> 
      <!--<td align="center"><input type="checkbox" name="id" id="id" value="${card.id}"></td>-->
      <td align="center" height="20">${xh.count}</td>
      <td align="center">&nbsp;${entry.title}</td>
      <td align="center">&nbsp;${entry.price}</td>
      <td align="center">&nbsp;${entry.amount}</td>
    </tr>

   </c:forEach>
  </table> 
	  </td>
    </tr>
	<tr> 
      <td  width="19%">&nbsp;&nbsp;&nbsp;&nbsp;缴费用户:</td>
      <td width="81%" colspan="3" ><label> 
        <input type="text" name="name" readonly="true" value="${payment.name}" size="30">
      </label></td>
    </tr>
    <tr>
		<td >&nbsp;&nbsp;&nbsp;&nbsp;缴费金额:</td>
        <td colspan="3" >
        <input type="text" name="payMoney" readonly="true" value="${payment.payMoney}"></td>
    </tr>
	<tr> 
      <td   >&nbsp;&nbsp;&nbsp;&nbsp;支付方式:</td>
      <td colspan="3">
        <input type="text" name="payWay" readonly="true" value="${payment.payWay}"></td>
	</tr>
        <tr> 
      <td  >&nbsp;&nbsp;&nbsp;&nbsp;缴费时间:</td>
      <td >
        <input type="text" name="payTime" readonly="true" value="${payment.payTime}"></td>

		 <td  >&nbsp;&nbsp;&nbsp;&nbsp;缴费确认时间:</td>

         <td >
        <input type="text" name="confirmTime" readonly="true" value="${payment.confirmTime}"></td>
    </tr>
    	
	<tr> 
      <td  >&nbsp;&nbsp;&nbsp;&nbsp;发票抬头:</td>
      <td colspan="3">
        <input type="text" name="billHead" readonly="true" value="${payment.billHead}" size="80"></td>
	</tr>
    <tr>
		<td  >&nbsp;&nbsp;&nbsp;&nbsp;发票状态:</td>
        <td colspan="3">
        <input type="text" name="billStatus" readonly="true" value="${payment.billStatus}"></td>
    </tr>

	<tr> 
      <td  >&nbsp;&nbsp;&nbsp;&nbsp;发票受理时间:</td>
      <td >
        <input type="text" name="acceptTime" readonly="true" value="${payment.acceptTime}"></td>

		<td  >&nbsp;&nbsp;&nbsp;&nbsp;发票受理人:</td>
        <td >
        <input type="text" name="acceptMan" readonly="true" value="${payment.acceptMan}"></td>
    </tr>
	    <tr>
		<td  >&nbsp;&nbsp;&nbsp;&nbsp;订单备注:</td>
      <td colspan="3" >
        <input type="text" name="remark" readonly="true" value="${payment.remark}" size="100"></td>
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
