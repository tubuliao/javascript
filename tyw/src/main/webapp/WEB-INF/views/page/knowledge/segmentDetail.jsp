<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>

<title>${segment.title}</title>
 
  <style type="text/css">
   
	.highlight{
	    background:#FF0;
 		padding:2px;
	}  
    </style>
 <!--[if lt IE 7]>
 

 [if lt IE 7]><![endif]-->
 
<script src="${pageContext.request.contextPath}/js/detailCommon.js" type="text/javascript"></script>
<script type="text/javascript" src="unitpngfix.js"></script>
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.highlight-3.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/myhighlight.js" type="text/javascript"></script>

 <script type="text/javascript" >
	 
function check(){
	var userId = $("#userId").val();
	var fileId = "${segment.id}";
	if(userId == null || userId == ''){
		$("#loginDiv").fadeIn(1000);
   		$("#loginDiv").fadeOut(1000);
   	    setTimeout("location.href='/login.jsp'",2000);
	}else{
		$.post('/addLikeCount/',{userId:userId,fileId:fileId},function(result){
			if(result.success){
			  $("#likeImg").fadeIn(800).slideUp();
       		  $("#likeImg").fadeOut(800);
       		var addLike = parseInt($("#likeCountId").text())+1;
     		  $("#likeCountId").html(addLike);
			}else if(result.fail){
				$("#likeDiv").fadeIn(1000);
	       		$("#likeDiv").fadeOut(1000);
			}
		},'json');
	}
}
	

$(document).ready(function(){
	 init();
});

	function init(){
		var url = window.location.href;

		var visitId = "${segment.id}";

		var title = "${segment.title}";
		
		$.post('/addVisit/',{url:url,title:title,visitId:visitId},function(result){
						
					},'json');
	}  


	 function stos(url){
		window.open(url);
		//location.href=url;
	}

	$(document).ready(function(){
		var lilength=$("#stos>li").length;
	 	 for(var i=15;i<lilength;i++){
	 		   $("#stos li:eq("+i+")").hide();
	  	  }
		})
	</script>	
</head>

<body>
<div class="body">
 
<div class="clear"></div>
  <div>
  <jsp:include page="${pageContext.request.contextPath}/top.jsp" />
  <jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />
  </div>
  
 <div class="search_up2" ></div>   
  <div class="search_content_box">
  <div class="search_content">
  	<div class="detail left">
  	<input id="userId" name="userId" type="hidden" value="${user.id}"/>
  	<input id="kId" name="kId" type="hidden" value="${segment.id }" />
   <!--  <div class="weizhi">
          <div class="weizhi_shouye left"><a class="" href="#">首页</a></div>
          <div class="weizhi_qita left"><span class="xjt">></span><a href="#">门户级管理</a><span class="xjt">&gt;</span><a href="#">个人办公</a><span class="xjt">&gt;</span><a href="#">代办事件</a><span class="xjt">&gt;</span><a href="#"><c:out value="${segment.knowledgeTag}"/></a> </div>
    	</div> -->
   	  <div class="detail_title" id="title02"><c:out value="${segment.title}"/></div>
	  <input type="hidden" id="visitId" name="visitId" value="${segment.id}"/>
        <div class="title2">
        				发布时间：<fmt:formatDate value="${segment.createDate}" type="both" dateStyle="long" timeStyle="long" />&nbsp;&nbsp;&nbsp;&nbsp;
						数据来源：<a href="javascript:void(0);" onclick="bookDetail();"><span id="sourceDetail"><c:out value="${segment.source}"/></span></a>
		</div>
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
        <div align="center"><img src="${pageContext.request.contextPath}/images/detail_16.gif" width="694" height="16" /></div>
      <div class="gjztj zuo20 shang10">
       
                 	<div class="gjztj_title">相关搜索</div>
            	<ul class="gjz" id="stos">
	            	<c:forEach items="${listtag}" var="segTag">
 		                   	<li>
		                   		<a href="${pageContext.request.contextPath}/goPageList?keyword=${segTag.tagName}&typeId=&pageNo=1&tag_name=&onex=konwledgeType0" title="${segTag.tagName}"   id="">${segTag.tagName}  </a> 
		                 	</li>
 				    </c:forEach>
               </ul>
               <br/>
      </div>
       
        <div class="pinglun">
     <!--   	<div class="pinglun_title">网友评论</div>
        	<c:forEach items="${allComments.content}" var="base">
        		<div class="pinglun_box">
            		<a href="#"><img src="${pageContext.request.contextPath}/images/detail_20.gif"></a>
       	  			<div class="left">
                		<div class="pinglun_ren">
                			<a href="#"><c:out value="${base.issuedBy}" /></a>
	                		<span class="pinglun_shijian">
	                      		<fmt:formatDate value="${base.createDate}" type="both" dateStyle="long" timeStyle="long" />
	                     	</span>                    
                     	</div>
	          			<div class="pinglun_neirong">
	                   		<c:out value="${base.content}" />
	                    </div>
                	</div>
                	<div class="clear"></div>
            	</div>
        	</c:forEach>
        	
   	  <div class="pinglun_tongji">
            	<a href="#">55 </a>人参与 <a href="#"> 33 </a>条评论（查看）          </div>
      <div class="pinglun_tianxie">
                <input type="hidden" id="authsUserId" name="authsUserId" value="${user.id}" /> 
                <input type="hidden" id="infoBaseId" name="infoBaseId" value="${segment.id}" />
				<textarea class="zuo10" name="content1" id="content1" cols="79" rows="5"
						onfocus="this.value=(this.value=='文明上网，登陆评论！') ? '' : this.value;"
						onblur="this.value=(this.value=='') ? '文明上网，登陆评论！' : this.value;">文明上网，登陆评论！</textarea>
                <div class="shengming left">所有评论仅代表网友意见</div>
           	    <input class="right shang10 you10" type="image" src="${pageContext.request.contextPath}/images/detail_31.gif" name="button3" id="button3" value="提交" onclick="addComments();"/>
            </div>-->
        
        </div>
         
       
    </div>
  
  
  
    <div class="search_right right">
<div class="top10 left">
  <div class="top10_title" style="width: 160px;">相关推荐</div>
  <ul>
  	<c:forEach items="${listSource}" var="listSource">
  	   <sec:authorize ifAllGranted="KNOWLEDGE_LOOK">
                   	<li ><font size="2"><a href="${pageContext.request.contextPath}/detail/${listSource.infoType}/${listSource.id}" target="_blank">
                  		  ${listSource.title}
                   </a></font> </li>
	      </sec:authorize>
    </c:forEach>
  </ul>
</div>
<div class="top10 left">
  <div class="top10_title" style="width: 160px;">相关表格</div>
  <ul>
    <c:forEach items="${listForm}" var="listForm">
      <sec:authorize ifAllGranted="KNOWLEDGE_LOOK">
                   	<li ><a style="font-size:12px;" href="${pageContext.request.contextPath}/detail/${listForm.infoType}/${listForm.id}" target="_blank">
                  		  ${listForm.title}
                   </a> </li>
	      </sec:authorize>
     </c:forEach>

  </ul>
</div>
<!-- 
<div class="top10 left">
  <div class="top10_title">网友都在看什么</div>
  <ul>
    <li><a href="#">个性化的获取自己需要的信息</a></li>
    <li><a href="#">个性化的获取自己需要的信息</a></li>
    <li><a href="#">个性化的获取自己需要的信息</a></li>
    <li><a href="#">个性化的获取自己需要的信息</a></li>
    <li><a href="#">个性化的获取自己需要的信息</a></li>
    <li><a href="#">个性化的获取自己需要的信息</a></li>
    <li><a href="#">个性化的获取自己需要的信息</a></li>
  </ul>
</div>
 -->
<div class="clear"></div>
       <!-- a href="#"><img class="tiwen" src="${pageContext.request.contextPath}/images/cs_25.gif" /></a--></div>     
          
          <div class="clear"></div>
  </div>
  </div>
    <div class="search_down"></div>
    <div class="foot">
<!-- 联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号  -->
<jsp:include page="${pageContext.request.contextPath}/bottom.jsp" />

</div></div>
<div id="likeDiv" class="ding" style="display:none;background:none">
<div class="tishi">
<div  class="tishi_box" style="margin-left:200px;width:200px; height:30px; line-height:30px; font-size:16px; text-align:center;border:3px solid #eeeeee; background:#f98d05; color:#fff;">
您已经赞过此文章！
</div>
</div>
</div>
<div id="collectDiv" class="ding" style="display:none;background:none">
<div class="tishi">
<div  class="tishi_box" style="margin-left:200px;width:200px; height:30px; line-height:30px; font-size:16px; text-align:center;border:3px solid #eeeeee; background:#f98d05; color:#fff;">
您已经收藏过此文章！
</div>
</div>
</div>
<div id="loginDiv" class="ding" style="display:none;background:none">
<div class="tishi">
<div  class="tishi_box" style="margin-left:200px;width:200px; height:30px; line-height:30px; font-size:16px; text-align:center;border:3px solid #eeeeee; background:#f98d05; color:#fff;">
请先登录！
</div>
</div>
</div>
<div class="fixed"><div class="fixed_box">
<div class="shangxiaye">
	<a class="shangyiye" href="#"></a>
    <a class="xiayiye" href="#"></a>
    <div class="glgjc_box">
    <a id="hotup"><img title="关键字高亮" class="shang10" src="/images/detail/gjc_03.gif" /></a>
    <a class="glgjc" id="glgjc" href="#">关键词</a>
    <a id="hotnext"><img title="关键字不高亮" src="/images/detail/gjc_06.gif"/></a>
    </div>
    </div>
</div>
</div>
<div class="guding">
<div class="guding2">
<!-- <div class="zuofu">
	<ul>
    	<li class="present"><a href="#">资讯</a></li>
        <li><a href="#">文件</a></li>
        <li><a href="#">核心</a></li>
        <li><a href="#">施工</a></li>
        <li><a href="#">验收</a></li>
        <li><a href="#">表格</a></li>
        <li><a href="#">监管</a></li>
        <li><a href="#">管理</a></li>
        <li><a href="#">职考</a></li>
        <li><a href="#">应用</a></li>
    </ul>
</div>
-->
<div class="btn_fd">
	<a href="#"></a>
	<a href="#top"><img title="返回顶部" class="shang20" src="${pageContext.request.contextPath}/images/btnfd_13.gif" /></a>
</div>
</div></div>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/google.js"></script>
 <script src="${pageContext.request.contextPath}/js/segmentDetail.js" type="text/javascript"></script>
</body>
</html>
<script type="text/javascript">

window.onload=function(){
	init();
}

function init(){
	var url = window.location.href;

	var visitId = $("#visitId").val();

	var title = $("#detail").html();

	$.post('/addVisit/',{url:url,title:title,visitId:visitId},function(result){
					
				},'json');
}

</script>