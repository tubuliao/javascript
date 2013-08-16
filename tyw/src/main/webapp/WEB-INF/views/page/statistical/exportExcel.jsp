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
				<td colspan="10" style="text-align:center"> <h3>${result.heading}</h3> </td>
			</tr>
			<tr style="text-align:center">
				<td>${result.num}</td>
				<td>${result.title}</td>
				<td>${result.source}</td>
				<td>${result.createDate}</td>
				<td>${result.insertName}</td>
				<td>${result.modifyDate}</td>
				<td>${result.modifyName}</td>
				<td>${result.checkDate}</td>
				<td>${result.checkName}</td>
				<td>${result.state}</td>
			</tr>
			<c:forEach items="${dataList }" var="d" varStatus="idx">
				<tr>
					<td style="text-align:center">${idx.index + 1}</td>
					<td>${d.title}</td>
					<td>${d.source}</td>
					<td><fmt:formatDate value="${d.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${d.insertName}</td>
					<td><fmt:formatDate value="${d.modifyDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${d.modifyName}</td>
					<td><fmt:formatDate value="${d.checkDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${d.checkName}</td>
					<td>${d.state == 0 ? result.state0 : (d.state == 1 ? result.state1 : (d.state == 2 ? result.state2 : result.state3))}</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</body>
</html>
