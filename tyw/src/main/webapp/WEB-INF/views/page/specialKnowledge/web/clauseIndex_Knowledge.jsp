<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
          <ul>
            <c:forEach items="${page}" var="knowledge" varStatus="status">
                    	<li >
                    		<c:if test="${empty DetailAccess}">
	                    		<a  title="${knowledge.title}" href="javascript:tishi('')">
	                    		<span>${knowledge.segItem}</span>${knowledge.title}
	                    		</a>
                    		</c:if>
                    		<c:if test="${!empty DetailAccess}">
                    			<sec:authorize ifAllGranted="ADMINISTRATOR">
                    				<a  title="${knowledge.title}" href="/detail/2/${knowledge.id}" target="_blank">
		                    		<span>${knowledge.segItem}</span>${knowledge.title}
		                    		</a>
                    			</sec:authorize>
                    			<sec:authorize ifNotGranted="ADMINISTRATOR">
                    				<c:if test="${DetailAccess==1 }">
			                    		<a  title="${knowledge.title}" href="/detail/2/${knowledge.id}" target="_blank">
			                    		<span>${knowledge.segItem}</span>${knowledge.title}
			                    		</a>
	                    			</c:if>
	                    			<c:if test="${DetailAccess==0 }">
			                    		<a  title="${knowledge.title}" href="javascript:alert('您的服务已到期，请购买服务！')">
			                    		<span>${knowledge.segItem}</span>${knowledge.title}
		                    			</a>
	                    			</c:if>
	                    			<c:if test="${DetailAccess==2 }">
			                    		<a  title="${knowledge.title}" href="javascript:alert('您需要购买服务！')">
			                    		<span>${knowledge.segItem}</span>${knowledge.title}
		                    			</a>
	                    			</c:if>
                    			</sec:authorize>
	                    		
                    		</c:if>
                    	</li>
           	</c:forEach>
          </ul>
