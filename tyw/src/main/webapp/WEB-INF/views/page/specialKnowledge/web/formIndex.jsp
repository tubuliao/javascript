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
<LINK rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/new.css">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<LINK rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/search.css">
<SCRIPT language=javascript src="${pageContext.request.contextPath}/homesearch.js"></SCRIPT>
<!--[if lt IE 7]>
        <script type="text/javascript" src="unitpngfix.js"></script>
<![endif]-->
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>

<script src="${pageContext.request.contextPath}/js/endless_scroll_min.js"></script>
<script src="${pageContext.request.contextPath}/js/nav.js"></script>
</head>

<body>
<div class="body">
<div class="clear">
<jsp:include page="${pageContext.request.contextPath}/top.jsp" />
  <jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />
  <jsp:include page="nav.jsp" />
</div>
 <div class="search_up2"></div>   
  <div class="search_content_box">
  <div class="search_content">
  	<div class="detail left">
  	
  	<div class="city">
  <div class="city_selected left">当前省市：北京</div>
  <div class=" zuo10 left">
  <a class="zuofan" href="#"></a>
  <div class="city_select" id="city_select">
  			<a href="#">北京</a><a href="#">天津</a><a href="#">广州</a><a href="#">上海</a>
  			<a href="#">重庆</a><a href="#">河北</a><a href="#">河南</a><a href="#">云南</a>
  			<a href="#">辽宁</a><a href="#">黑龙江</a><a href="#">湖南</a><a href="#">安徽</a>
  			<a href="#">山东</a><a href="#">新疆</a><a href="#">江苏</a><a href="#">浙江</a>
  			<a href="#">江西</a><a href="#">湖北</a><a href="#">广西</a><a href="#">甘肃</a>
  			<a href="#">山西</a><a href="#">内蒙古</a><a href="#">陕西</a><a href="#">吉林</a>
  			<a href="#">福建</a><a href="#">贵州</a><a href="#">广州</a><a href="#">青海</a>
  			<a href="#">西藏</a><a href="#">四川</a><a href="#">宁夏</a><a href="#">海南</a>
  </div>
  <a class="youfan" href="#"></a>
  <a class="map" onclick="x.style.display='block';" ></a>
  </div>
  <div class="clear"></div>
  </div>
  	
  	
    	<div class="biaoge_box">
<div class="tiaowen_bt">
  <div class="left">施工表格</div>
  <div class="right"><a href="/specialKnowledge/web/sourceList/Tags:核心条文  AND Source:GB" target="_blank">更多</a></div>
</div>
<div class="clear"></div>
            <div class="book_list">
            	<ul>
                	<c:forEach items="${GBSource}" var="source" varStatus="status">
                    	<li id="one${status.count }" onclick="setTab('one',${status.count },28)" >
                    	<a  title="${source.name}">
			                ${source.name}
						</a></li>
                    </c:forEach>
                </ul>
          </div>
          <c:forEach items="${GBknowledge}" var="knowledgeList" varStatus="status">
	            <div id="con_one_${status.count }" style="display:none" class="book_content">
	            <div class="">
	                <div class="one">
		                <ul>
			                <c:forEach items="${knowledgeList}" var="knowledge" >
			                	<li>
			                		<div class="title">
			                			<a href="/specialDetail/${knowledge.infoType}/${knowledge.id}" target="_blank">${knowledge.title}</a>
		                				<%-- <span>
		                					<fmt:formatDate value="${knowledge.createDate}" pattern="yyyy-MM-dd" />
		                				</span> --%>
			                		</div>
			                	</li>
			                </c:forEach>
		              	</ul>
	              	</div>
	            </div>
	            </div>
           </c:forEach>
           
           
           
           
            
            
            <div class="clear"></div>
        </div>
        <div class="tiaowen_box">
          <div class="tiaowen_bt">
            <div class="left">安全表格</div>
            <div class="right"><a href="/specialKnowledge/web/sourceList/Tags:核心条文  AND Source:DB" target="_blank">更多</a></div>
          </div>
          <div class="clear"></div>
            <div class="book_list">
            	<ul>
                    <c:forEach items="${DBSource}" var="source" varStatus="status">
                    	<li id="two${status.count }" onclick="setTab('two',${status.count },28)" >
                    	<a  title="${source.name}">
							${source.name}
						</a></li>
                    </c:forEach>
                </ul>
          </div>
            <c:forEach items="${DBknowledge}" var="knowledgeList" varStatus="status">
	            <div id="con_two_${status.count }" style="display:none" class="book_content">
	            <div class="">
	                <div class="one">
		                <ul>
			                <c:forEach items="${knowledgeList}" var="knowledge" >
			                	<li>
			                		<div class="title">
			                			<a href="/detail/${knowledge.infoType}/${knowledge.id}" target="_blank">${knowledge.title}</a>
		                				<%-- <span>
		                					<fmt:formatDate value="${knowledge.createDate}" pattern="yyyy-MM-dd" />
		                				</span> --%>
			                		</div>
			                	</li>
			                </c:forEach>
		              	</ul>
	              	</div>
	            </div>
	            </div>
           </c:forEach>
           
          <div class="clear"></div>
        </div>
        	
    </div>
  
  
  
  
    <div class="search_right right">
      <div class="top10 left">
        <div class="top10_title">推荐表格</div>
        <ul>
          <c:forEach items="${listForm}" var="listForm" varStatus="status">
		         <li >
		         	<em>${status.count }</em>
		         	<a href="${pageContext.request.contextPath}/detail/${listForm.infoType}/${listForm.id}" target="_blank">${listForm.title}</a>
		          </li>
		  </c:forEach>
        </ul>
      </div>
      <div class="top10_pic left">
        <div class="top10_pic_title">推荐图片</div>
        <ul>
          <li><a href="${pageContext.request.contextPath}/detail/5/2c9f850e3e4ab964013e4ff777bb04d7" target="_blank"><img src="http://211.141.29.47/group1/M00/00/C4/rB8FjlF865PqfYtuAAGRzDD2ey8706.jpg" /></a><a href="${pageContext.request.contextPath}/detail/5/2c9f850e3e4ab964013e4ff777bb04d7" target="_blank">北京当代十大建筑评选结果</a></li>
          <%-- <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li> --%>
          
        </ul>
      </div>
      <div class="top10_video left">
        <div class="top10_video_title">推荐视频</div>
        <ul>
          <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">上下水知识讲解</a></li>
          <%-- <li><a href="#"><img src="${pageContext.request.contextPath}/images/search/pic/fafa.jpg" /></a><a href="#">个性化的获取自己需要的信息</a></li> --%>
          
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
<script src="${pageContext.request.contextPath}/js/clauseIndex.js" type="text/javascript"></script>
<script type="text/javascript">
    $(window).load(function () {
        //$("#s1").endlessScroll({ width: '100%', height: '100px', steps: -2, speed: 40, mousestop: true });
        $("#city_select").endlessScroll({ width: '700px', height: '26px', steps: -2, speed: 40, mousestop: true });
    });
</script>
</body>
</html>
						