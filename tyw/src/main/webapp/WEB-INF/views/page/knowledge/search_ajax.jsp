<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<script src="${pageContext.request.contextPath}/js/Jquery.L.Message.js"	type="text/javascript"></script>
	<script type="text/javascript" >
 $(document).ready(function(){
	 	 var showIndex=$("#hidshowIndex").val();
		 var hidPageNum=$("#hidPageNum").val();
		 var hidrowCount=$("#hidrowCount").val();
		 var keywordVal=$("#keyword").val();
		 var HaveselVal=$("#Havesel").text().replace("清除","");
		 if(showIndex==0){
			 showIndex=1;
		 }
  		 var pageMsg="跳到<input name=\"pageNo\" id=\"goPageContent\"  style=\"width:30px;\" value="+hidPageNum+"></input>页 <a id=\"goPage\" href=\"javascript:void(0)\">go</a>[当前第:"+hidPageNum+"/"+showIndex+"页,共"+$("#hidrowCount").val()+"记录]";
		 $("#pageNum").html(pageMsg); 
		
  		 if($("#goLuxurySearch").val()!=1){
  	  		 var keyval=$.trim((keywordVal+HaveselVal)).replace("输入要找的内容","");
  	  		 if(keyval==""){
  	 			 $("#errorMsg").html("请输入您要查找的内容！");
  	  		 }else{
 				 $("#errorMsg").html("抱歉，没有找到与“<font color=\"red\">"+keyval+"</font>”相关的网页内容！");
  	  		 }
		 }
  		$("#goLuxurySearch").val(2);

  		
});
</script>
<body>
  
 				<div id="hello" ></div>
 				<c:choose>
 					<c:when test="${not empty pager.result}">
              <c:forEach items="${pager.result}" var="base">
                <div class="search_jieguo">
                  <div class="search_xinxi">
                    <div class='${base.infoType==1?"bigfile":(base.infoType==6?"ppts":(base.infoType==5?"picture":(base.infoType==2?"segment":(base.infoType==3?"video":(base.infoType==4?"table":"word")))))}'>
	                    
	                    	<c:if test="${empty DetailAccess}">
	                    		<a  id="url"    href="javascript:void()" onclick="tishi('');">	
			                  		<font size="3">	${base.title} </font>
		                  	 	</a>
                    		</c:if>
                    		<c:if test="${!empty DetailAccess}">
                    			<sec:authorize ifAllGranted="ADMINISTRATOR">
                    				<a id="url"   onclick="javascript:window.open('${pageContext.request.contextPath}/detail/${base.infoType}/${base.id}?keyword=${keyword}') " >
				                   		<font size="3"> ${base.title}</font>
				                    </a>
                    			</sec:authorize>
                    			<sec:authorize ifNotGranted="ADMINISTRATOR">
                    				<c:if test="${DetailAccess==1 }">
			                    		<a id="url"   onclick="javascript:window.open('${pageContext.request.contextPath}/detail/${base.infoType}/${base.id}?keyword=${keyword}') " >
				                   			<font size="3"> ${base.title}</font>
				                    	</a>
	                    			</c:if>
	                    			<c:if test="${DetailAccess==0 }">
		                    			<a  id="url"   href="javascript:alert('您的服务已到期，请购买服务！')" onclick="">	
			                  				<font size="3">	${base.title} </font>
		                  	 			</a>
	                    			</c:if>
	                    			<c:if test="${DetailAccess==2 }">
		                    			<a  id="url"   href="javascript:alert('您需要购买服务！')" onclick="">	
			                  				<font size="3">	${base.title} </font>
		                  	 			</a>
	                    			</c:if>
                    			</sec:authorize>
                    		</c:if>
	                    
                    </div>
                    <div class="search_jianjie">${base.content}</div>
                    <div class="search_leibie"><div class="left">来源： ${base.source}</div><div class="right">日期:<fmt:formatDate value="${base.createDate}" type="both"   />
                    <img src="images/personal/data_btn_25.png" width="11" height="10" />赞(${base.likeCount})<img src="images/personal/data_btn_13.png" width="12" height="12" />收藏(${base.favCount}) </div></div>
                  <div class="clear"></div>
                  </div>
                 </div>
               </c:forEach>
              </c:when>
 			  <c:otherwise>
 					<div id="errorMsg" style="margin: 20px;">
 						
 					</div>


 			 </c:otherwise>
 		</c:choose>
           
 
       	 	<input type="hidden" id="hidrowCount" value="${pager.rowCount}"/>
     
</body>
<script src="${pageContext.request.contextPath}/js/search_ajax.js" type="text/javascript"></script>
 </html>
