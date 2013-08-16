<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
		
		
		<input type="hidden" id="visitId" name="visitId" value="${segment.id}"/>
		<input type="hidden" id="title02" name="title02" value="${segment.title}"/>
		<div class="detail_title">
			<div class="left"><a href="javascript:void(0);" onclick="bookDetail();"><span id="sourceDetail">${segment.source}</span></a></div>
			<p class="left">
				<c:if test="${!empty segment.begincreateDate}">
					 颁布时间：<fmt:formatDate value="${segment.begincreateDate}" type="date" dateStyle="long"  />
				</c:if>
			</p>
			<div class="clear"></div>
		</div>
		<div id="dialog-confirm" title="提示" style="display: none">
			<p><span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"></span>本页已查找完毕是否进入下一页查找?</p>
		</div>
      <div class="detail_title_detail" style="font-weight:bold"><c:out value="${segment.segItem}"/>&nbsp;&nbsp;&nbsp;<c:out value="${segment.title}"/><img src="${pageContext.request.contextPath}/images/detail/yl_07.gif" width="34" height="18" /></div>
        
    	<div class="wenzi">${segment.content}</div>
    	
    	<div class="clear"></div>
        <div class="btn">
        <a class="shoucang" style="positon:relative" id="collect" href="javascript:void(0);" onclick="collectBookmark();">
        <img id="collectImg" src="/images/like107.png" style="display:none; position:absolute; margin-top:-30px; margin-left:-10px"/>
                    收藏 (<span id="collectCountId">${collectCount}</span>)
        </a>
        <a class="zan" style="positon:relative" id="like" href="javascript:void(0);" onclick="check();">
        <img id="likeImg" src="/images/like107.png" style="display:none; position:absolute; margin-top:-30px; margin-left:-10px"/>
                    赞 (<span id="likeCountId">${likeCount}</span>)
        </a>
        <!-- a href="#"><img src="/images/detail_11.gif" width="84" height="30" /></a--></div>
 
<LINK rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/apprise.css">

<script src="${pageContext.request.contextPath}/js/apprise-1.5.full.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.highlight-3.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/myhighlight.js" type="text/javascript"></script>


<script type="text/javascript" >
 function bookDetail() {
		var sourceCompose = $('#sourceDetail').html();
		window.open("${pageContext.request.contextPath}/knowledge/web/bookdetail/" + encodeURIComponent(encodeURIComponent(sourceCompose)), '_blank');
	}
	
/*  window.onload=function(){
		init();
	} */

 $(document).ready(function(){
	 init();
	 
	 $(".knowledge_context .wenzi>p>a").live("mouseover",function(){
 		 $(this).attr("href",$(this).attr("href")+"_#")
 		 $(this).unbind();
		})
	
	$(".knowledge_context .wenzi>p>a").live("mouseout",function(){
 		 $(this).attr("href",$(this).attr("href").replace("_#",""))
 		 $(this).unbind();
		})
	
	$(".knowledge_context .wenzi>p>a").live("click",function(){
		var url = $(this).attr("href").replace("_#","");
		var title = $("#title02").val();
		$.post('/addDownload/',{url:url,title:title},function(result){
			
		},'json');
		
		$(this).attr("href",$(this).attr("href").replace("_#",""));
		})
	 
		$("#windowfix").height($("#contentright").height());
 });
  
	function init(){
		var url = window.location.href;

		var visitId = $("#visitId").val();

		var title = $("#title02").val();
		
		$.post('/addVisit/',{url:url,title:title,visitId:visitId},function(result){
						
					},'json');
	}
	
	
</script>