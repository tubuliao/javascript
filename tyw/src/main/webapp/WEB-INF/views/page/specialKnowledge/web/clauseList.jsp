<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>列表页</title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<LINK rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/search.css">
<LINK rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/new.css">
<!--[if lt IE 7]>
        <script type="text/javascript" src="unitpngfix.js"></script>
<![endif]-->
<script src="${pageContext.request.contextPath}/js/nav.js"></script>
</head>

<body>
<div class="body">
<div class="clear"></div>
<jsp:include page="${pageContext.request.contextPath}/top.jsp" />
  <jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />
  <jsp:include page="nav.jsp" />
 <div class="search_up2"></div>   
  <div class="search_content_box">
  <div class="search_content">
  	<div class="detail left">
    <div class="weizhi">
          <div class="weizhi_shouye left"><a class="" href="#">首页</a></div>
          <div class="weizhi_qita left"><span class="xjt">></span><a href="#">门户级管理</a><span class="xjt">&gt;</span><a href="#">个人办公</a><span class="xjt">&gt;</span><a href="#">代办事件</a><span class="xjt">&gt;</span><a href="#">列表</a> </div>
    	</div>
    <div class="liebiao">
    <h3>${title }列表</h3>
    <ul>
    	<c:forEach var="LIST" items="${source}" >
    		<li><a href="#">${LIST}</a></li>
    	</c:forEach>
    </ul> 
    </div>
    <div class="scott"><a href="#?page=2"> 首页</a><a href="#?page=2">上一页</a><span class="disabled">&lt; </span><span class="current">1</span><a href="#?page=2">2</a><a href="#?page=3">3</a><a href="#?page=4">4</a><a href="#?page=5">5</a><a href="#?page=6">6</a><a href="#?page=7">7</a>...<a href="#?page=199">199</a><a href="#?page=200">200</a><a href="#?page=2">下一页 </a></div>               
    </div>
  	<div class="search_right right">
      <div class="top10 left">
        <div class="top10_title">推荐表格</div>
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
      <div class="top10_pic left">
        <div class="top10_pic_title">推荐图片</div>
        <ul>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
        </ul>
      </div>
      <div class="top10_video left">
        <div class="top10_video_title">推荐视频</div>
        <ul>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li>
        </ul>
      </div>
      <div class="clear"></div>
      </div>
    <div class="clear"></div>
  </div>
  </div>
    <div class="search_down"></div>
    <div class="foot">
<!-- 联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号  -->
<jsp:include page="${pageContext.request.contextPath}/bottom.jsp" />

</div></div>

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
</body>
</html>
