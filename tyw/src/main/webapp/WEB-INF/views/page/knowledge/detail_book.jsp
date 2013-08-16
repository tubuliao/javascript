<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>书籍详细</title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type=text/css href="${pageContext.request.contextPath}/css/search.css"/>
 <style type="text/css">
	.highlight{
	background:#FF0;
 	color:#E00;
	}  
    </style>
<!--[if lt IE 7]>
        <script type="text/javascript" src="unitpngfix.js"></script>
<![endif]-->
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>

 <script language="javascript" type="text/javascript">
        /***
        *功能：隐藏和显示div
        *参数divDisplay：html标签id
        ***/
        function click_a(divDisplay)
        {
            if(document.getElementById(divDisplay).style.display != "block")
            {
                document.getElementById(divDisplay).style.display = "block";
            }
            else
            {
                document.getElementById(divDisplay).style.display = "none";
            }
        }

		function bookDetailLink(sourceCompose) {
			//alert(sourceCompose);
			//location.href="${pageContext.request.contextPath}/knowledge/web/bookdetail/" + encodeURIComponent(encodeURIComponent(sourceCompose));
			window.open("${pageContext.request.contextPath}/knowledge/web/bookdetail/" + encodeURIComponent(encodeURIComponent(sourceCompose)), '_blank');
		}
	
    </script>
</head>

<body>
<div class="body">
 <input id="isindex" value="detail" type="hidden"/>
<div class="clear"></div>
  <div>
  <jsp:include page="${pageContext.request.contextPath}/top.jsp" />
  <jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />
  </div>

 <div class="search_up2"></div>   
  <div class="search_content_box">
  <div class="search_content">
  	<div class="detail_book">
	
    <div class="weizhi">
	
      <div class="weizhi_shouye left"></div>
      <div class="weizhi_qita left"><span class="xjt"></span><span class="xjt"></span><span class="xjt"></span><span class="xjt"></span></div>
	 
    </div>
	
    <div class="mulu_list wenzi">
	<!-- start -->
	<c:if test="${not empty book}">
    	<div class="detail_book_title"><img src="/images/new/book_03.gif" />书籍介绍：</div>
        <div class="detail_book_book"><p>${book.standardName}</p><span>${book.editDepartment}&nbsp;&nbsp;编著</span></div>
    <div class="detail_book_info">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="17%" height="32" class="detail_book_info_m">标准类型: </td>
          <td width="83%" class="detail_book_info_n"><strong>${book.standardType}</strong></td>
        </tr>
        <tr>
          <td height="32" class="detail_book_info_m">标准名称: </td>
          <td class="detail_book_info_n"><strong>${book.standardName}</strong></td>
        </tr>
        <tr>
          <td height="32" class="detail_book_info_m">标准编号:</td>
          <td class="detail_book_info_n"><strong>${book.standardNo}</strong></td>
        </tr>
        <tr>
          <td height="32" class="detail_book_info_m">被替标准编号: </td>
		  <td class="detail_book_info_n"><strong>${book.changeNo == "" || book.changeNo == null ? "无" : book.changeNo} </strong></td>
        </tr>
        <tr>
          <td height="32" class="detail_book_info_m">英文名称: </td>
          <td class="detail_book_info_n"><strong>${book.englishName == "" || book.englishName == null ? "无" : book.englishName}</strong></td>
        </tr>
        <tr>
          <td height="32" class="detail_book_info_m">主编单位: </td>
          <td class="detail_book_info_n"><strong>${book.editDepartment == "" || book.editDepartment == null ? "无" : book.editDepartment} </strong></td>
        </tr>
        <tr>
          <td height="32" class="detail_book_info_m">批准部门: </td>
          <td class="detail_book_info_n"><strong>${book.approveDepartment == "" || book.approveDepartment == null ? "无" : book.approveDepartment}</strong></td>
        </tr>
        <tr>
          <td height="32" class="detail_book_info_m">实施日期:</td>
          <td class="detail_book_info_n"><strong>${book.executeDate == "" || book.executeDate == null ? "无" : book.executeDate}</strong></td>
        </tr>
        <tr>
          <td height="32" class="detail_book_info_m">标准目的: </td>
          <td class="detail_book_info_n"><strong>${book.goal == "" || book.goal == null ? "无" : book.goal}</strong></td>
        </tr>
        <tr>
          <td height="32" class="detail_book_info_m">适用范围: </td>
          <td class="detail_book_info_n"><strong>${book.scope == "" || book.scope == null ? "无" : book.scope}</strong></td>
        </tr>
      </table>
    </div>
    <div class="clear"></div>

	<div class="detail_book_list">
		<h3>书籍简介：</h3>
			<p>&nbsp;&nbsp;&nbsp;&nbsp;${book.description == "" || book.description == null ? "<li><p>无</p></li>" : book.description }</p> 
	</div>

<div class="detail_book_list">
 <h3>书籍目录：</h3>
<ul>
	<c:choose>
		<c:when test="${not empty catalogList}">
			<c:forEach items="${catalogList}" var="c">
				<li><p>${c}</p></li>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<li><p>无</p></li>
		</c:otherwise>
	</c:choose>
</ul>

</div>

    <div class="shang20 xia10"><div class="clear"></div></div>

    <div class="detail_book_con">
<h3>相关书籍：</h3>
<c:choose >
	<c:when test="${not empty otherList}">
		<ul>
			<c:forEach items="${otherList}" var="other">
				<li><p><a href="javascript:void(0);" onclick="bookDetailLink('《'+'${other.standardName}'+'》'+'${other.standardNo}');">${fn:substring(other.standardName, 0, 14)}</a></p></li>
			</c:forEach>
		</ul>
	</c:when>
	<c:otherwise>
		&nbsp;&nbsp;&nbsp;&nbsp;暂无！
	</c:otherwise>
</c:choose>
<div class="clear"></div>
</div>
</div>
 
</div>
<div class="clear"></div>
</div>
</div>
<div class="search_down"></div>
</c:if>
<!-- end -->

<c:if test="${ empty book}">
	<div id="noMesssager">
		暂时没有该书籍的相关信息！
	</div>
</c:if>
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
<a href="#top"><img title="返回顶部" class="shang20" src="/images/btnfd_13.gif" /></a>
</div>
</div></div>
<div class="fixed"><div class="fixed_box">
<div class="shangxiaye">
	<a class="shangyiye" href="javascript:void(0)" style="display:none" onclick="last();" id="shangyiye"></a>
    <a class="xiayiye" href="javascript:void(0)" style="display:none"  onclick="next();" id="xiayiye"></a>
    <div class="glgjc_box">
	    <a id="hotup"><img title="关键字高亮" class="shang10" src="/images/detail/gjc_03.gif" /></a>
	    <a class="glgjc" id="glgjc" href="javascript:void(0)">关键词</a>
	    <a id="hotnext"  href="javascript:void(0)"><img title="关键字不高亮" src="/images/detail/gjc_06.gif"/></a>
    </div>
</div>
</div>
</body>
<LINK rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/apprise.css">

<script src="${pageContext.request.contextPath}/js/apprise-1.5.full.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.highlight-3.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/myhighlight.js" type="text/javascript"></script>

</html>