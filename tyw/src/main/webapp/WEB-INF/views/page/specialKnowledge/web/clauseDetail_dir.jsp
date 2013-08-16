<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>



        	<h3>书籍目录</h3>
        	<ul>
            	<c:forEach items="${segList}" var="knowledge" varStatus="status">
                    	<li class="cover">
                    		<a  title="${knowledge.title}" href="javascript:void(0)"   id="${knowledge.id}">
                    		<span>${knowledge.segItem}</span><c:out value="${knowledge.title}"/>
                    		</a>
                    	</li>
           		</c:forEach>
            </ul>
