<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:include page="${pageContext.request.contextPath}/top.jsp" />
<jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>详细页</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<LINK rel=stylesheet type=text/css href="css/search.css">
<SCRIPT language=javascript src="homesearch.js"></SCRIPT>
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/detailCommon.js" type="text/javascript"></script>

<!--[if lt IE 7]>
        <script type="text/javascript" src="unitpngfix.js"></script>
<![endif]-->
<script src="js/nav.js"></script>

<script type="text/javascript" >

function check(){
	var userId = $("#userId").val();
	var fileId = "${files.id}";
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

		var visitId = "${files.id}";

		var title = "${files.title}";
		
		$.post('/addVisit/',{url:url,title:title,visitId:visitId},function(result){
						
					},'json');
	}  


/* 	
function collectBookmark() {
	// 收藏地址
	var url = window.location.href ;
	var title = $('#title02').text() ;
	//alert(url + '**********' + title) ;
	
	$.get('${pageContext.request.contextPath}/collectBookmark', { bUrl: url, bTitle: title}, function(result) {
		if(result.success) {
			$("#collectImg").fadeIn(800).slideUp();
  		    $("#collectImg").fadeOut(800);
		} else {
			$("#collectDiv").fadeIn(1000);
       		$("#collectDiv").fadeOut(1000);
		}
	}, 'json') ;
	
}

	function bookDetail() {
		var sourceCompose = $('#sourceDetail').html();
		//var sourceCompose = $('#sourceDetail').html().replace(new RegExp('/', 'g'), '&');
		//alert(sourceCompose);
		//location.href="${pageContext.request.contextPath}/knowledge/web/bookdetail/" + encodeURIComponent(encodeURIComponent(sourceCompose));	// 两次编码 java在后台会自动的进行一次解码 但结果不对
		window.open("${pageContext.request.contextPath}/knowledge/web/bookdetail/" + encodeURIComponent(encodeURIComponent(sourceCompose)), '_blank');
	}
 */	 
	</script>	
</head>

<body>
<div class="body">
 
<div class="clear"></div>
  
 <div class="search_up2" style="margin-top:130px"></div>   
  <div class="search_content_box">
  <div class="search_content">
  	<div class="detail left">
    <div class="weizhi">
          <div class="weizhi_shouye left"><a class="" href="${pageContext.request.contextPath}/jump.jsp">首页</a></div>
          <input id="userId" name="userId" type="hidden" value="${user.id}"/>
  		  <input id="kId" name="kId" type="hidden" value="${files.id }" />
          <div class="weizhi_qita left">
          		<c:forEach items="${TagPath}" var = "path" varStatus="status">
          			<span class="xjt">></span>${path}
          		</c:forEach>
          </div>
    	</div>
   	  <div class="detail_title" id="title02"><c:out value="${files.title}"/></div>
        <div class="title2">
        				发布时间：<fmt:formatDate value="${files.begincreateDate}" type="both" dateStyle="long" timeStyle="long" />&nbsp;&nbsp;&nbsp;&nbsp;
						数据来源：<a href="javascript:void(0);" onclick="bookDetail();"><span id="sourceDetail"><c:out value="${files.source}"/></span></a>
		</div>
    	<div class="wenzi">${files.content}</div>
    	
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
          	<div class="gjztj_title">相关搜索关键字推荐</div>
            	<ul class="gjz">
                	<li><a class="first" href="#">土石方工程</a></li>
                    <li><a href="#">土石方工程</a></li>
                    <li><a href="#">土石方工程</a></li>
                    <li><a href="#">土石方工程</a></li>
                    <li><a href="#">土石方工程</a></li>
                    <li><a href="#">土石方工程</a></li>
                
            </ul>
                <ul class="xinxi5">
                	<li><a href="#">京沪高速铁路工程某络线特大桥下部工程上部工程及附属工程(实施)施工组织设计</a> </li>
                    <li><a href="#">京沪高速铁路工程某络线特大桥下部工程上部工程及附属工程(实施)施工组织设计</a> </li>
                    <li><a href="#">京沪高速铁路工程某络线特大桥下部工程上部工程及附属工程(实施)施工组织设计</a> </li>
                    <li><a href="#">京沪高速铁路工程某络线特大桥下部工程上部工程及附属工程(实施)施工组织设计</a> </li>
                    <li>京沪高速铁路工程某络线特大桥下部工程上部工程及附属工程(实施)施工组织设计 </li>
                </ul>
             
      </div>
        <div class="pinglun">
        	<div class="pinglun_title">网友评论</div>
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
                <input type="hidden" id="infoBaseId" name="infoBaseId" value="${files.id}" />
				<textarea class="zuo10" name="content1" id="content1" cols="79" rows="5"
						onfocus="this.value=(this.value=='文明上网，登陆评论！') ? '' : this.value;"
						onblur="this.value=(this.value=='') ? '文明上网，登陆评论！' : this.value;">文明上网，登陆评论！</textarea>
                <div class="shengming left">所有评论仅代表网友意见</div>
           	    <input class="right shang10 you10" type="image" src="${pageContext.request.contextPath}/images/detail_31.gif" name="button3" id="button3" value="提交" onclick="addComments();"/>
            </div>
        
        </div>
        
    </div>
  
  
  
  
    <div class="search_right right">
<div class="top10 left">
  <div class="top10_title">最新内荐</div>
  <ul>
    <li><em>1</em><a href="#">个性化的获取自己需要的信息</a></li>
    <li><em>2</em><a href="#">个性化的获取自己需要的信息</a></li>
    <li><em>3</em><a href="#">个性化的获取自己需要的信息</a></li>
    <li><em>4</em><a href="#">个性化的获取自己需要的信息</a></li>
    <li><em>5</em><a href="#">个性化的获取自己需要的信息</a></li>
    <li><em>6</em><a href="#">个性化的获取自己需要的信息</a></li>
    <li><em>7</em><a href="#">个性化的获取自己需要的信息</a></li>
    <li><em>8</em><a href="#">个性化的获取自己需要的信息</a></li>
    <li><em>9</em><a href="#">个性化的获取自己需要的信息</a></li>
    <li><em>10</em><a href="#">个性化的获取自己需要的信息</a></li>
  </ul>
</div>
<div class="top10 left">
  <div class="top10_title">大家爱看的内容</div>
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
<div class="guding">
<div class="guding2">
<div class="zuofu">
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
<div class="btn_fd">
	<a href="#"></a>
	<a href="#top"><img title="返回顶部" class="shang20" src="${pageContext.request.contextPath}/images/btnfd_13.gif" /></a>
</div>
</div></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/google.js"></script>
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js"	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/filesDetail.js" type="text/javascript"></script>
</body>
</html>
