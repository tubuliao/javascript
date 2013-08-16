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
				<td colspan="12" style="text-align:center"> <h3>${r.heading}</h3> </td>
			</tr>
			<tr style="text-align:center">
				<td>${r.num}</td>
				<td>${r.agentName}</td>
				<td>${r.batchCode}</td>
				<td>${r.receiptTitle}</td>
				<td>${r.receiptAddress}</td>
				<td>${r.receiptAmount}</td>
				<td>${r.vMoneytype}</td>
				<td>${r.receiptPerson}</td>
				<td>${r.postCode}</td>
				<td>${r.receiptPhone}</td>
				<td>${r.licenseNumber}</td>
				<td>${r.status}</td>
			</tr>
			<c:forEach items="${receiptList }" var="re" varStatus="idx">
				<tr>
					<td style="text-align:center">${idx.index + 1}</td>
					<td>${re.agentName}</td>
					<td>${re.batchCode}</td>
					<td>${re.receiptTitle}</td>
					<td>${re.receiptAddress}</td>
					<td>${re.receiptAmount}</td>
					<td>${re.vMoneytype == "0" ? r.vMoneytype01 : (re.vMoneytype == "1" ? r.vMoneytype02 : r.vMoneytype03)}</td>
					<td>${re.receiptPerson}</td>
					<td>${re.postCode}</td>
					<td>${re.receiptPhone}</td>
					<td>${re.licenseNumber}</td>
					<td>${re.status == "0" ? r.status01 : (re.status == "1" ? r.status02 : r.status03) }</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</body>
</html>
