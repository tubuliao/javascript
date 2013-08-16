<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>条文类</title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<LINK rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/search.css">
<LINK rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/new.css">
 	<script src="${pageContext.request.contextPath}/js/jquery.pagination.js" type="text/javascript" />

</head>

<body>

       
          <ul class="know">
             <c:forEach items="${name}" var="source" varStatus="status">
             			<c:if test="${status.first}"><li class="hover" ></c:if>
                    	<c:if test="${!status.first}"><li ></c:if>
                    		<a title="${source.source}" href="javascript:void(0)" >
                    			${source.source}
                    		</a>
                    	</li>
           	</c:forEach> 
           	 <input type="hidden" id="hidrowCount"  value="${rowCount}"/>
          </ul>
          
</body>

</html>
