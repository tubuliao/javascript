<%@ page contentType="application/x-msdownload" pageEncoding="UTF-8" %>
<%-- <%@ page contentType="application/vnd.ms-excel" pageEncoding="GBK" %>  --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<html>
<head>
    <title></title>
</head>
<body>
	<form id="">
		<table border="1">
			<tr>
				<td colspan="11" style="text-align:center"> <h3>${result.heading}</h3> </td>
			</tr>
			<tr style="text-align:center">
				<td>${result.num}</td>
				<td>${result.agent}</td>
				<td>${result.batch}</td>
				<td>${result.user}</td>
				<td>${result.payStatus}</td>
				<td>${result.payAmount}</td>
				<td>${result.payDate}</td>
				<td>${result.vOid}</td>
				<td>${result.vPmode}</td>
				<td>${result.vPstring}</td>
				<td>${result.vMoneytype}</td>
			</tr>
			<c:forEach items="${paymentList }" var="p" varStatus="idx">
				<tr>
					<td style="text-align:center">${idx.index + 1}</td>
					<td>${p.name}</td>
					<td>${p.batchCode}</td>
					<td>${p.aliasname}</td>
					<td>${p.payStatus == "1" ? result.payStatus01 : (p.payStatus == "20" ? result.payStatus02 : (p.payStatus == "30" ? result.payStatus03 : result.payStatus04))}</td>
					<td>${p.payAmount}</td>
					<td><fmt:formatDate value="${p.payDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${p.vOid}</td>
					<td>${p.vPmode}</td>
					<td>${p.vPstring}</td>
					<td>${p.vMoneytype == "0" ? result.vMoneytype01 : (p.vMoneytype == "1" ? result.vMoneytype02 : result.vMoneytype03)}</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</body>
</html>
