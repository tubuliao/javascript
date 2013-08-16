<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<sitemesh:head/>
<style>
  /**去除jquery ui close按钮**/
  .my-dialog .ui-dialog-titlebar-close{
    display: none;
  }
.search_content_box{ width:1000px; background:url(${pageContext.request.contextPath}/images/bgall_09.png) repeat-y; margin:0 auto; position:relative;
-moz-box-shadow:0px 0px 0px #b9b9b9;               /* For Firefox3.6+ */
-webkit-box-shadow:0px 0px 0px #b9b9b9;          /* For Chrome5+, Safari5+ */
box-shadow:0px 0px 0px #b9b9b9;                    /* For Latest Opera */
}
</style> 
</head>

<body >
<div class="cloud"></div>
<div class="body">
  <div class="head">
	<img class="logo left" src="../images/search_03.png" />
	<div class="top">
      <!-- div class="fav2 right"><a href="#">加入收藏</a> | <a href="#">帮助中心</a></div-->
	  <div class="search2">
        <form id="form1" name="form1" method="post" onSubmit="return false;" action="">
          <input type="hidden" id="personTag" name="personTag" value=${hover} />
        </form>
      </div>
    </div>
  </div>
    
	<div class="nav2">
		<ul>
			<li class="navlist_person"><a href="/personCentermyStatus">我的主页</a></li>
         	<li class="navlist_person"><a href="/personCenterMyBookmark">我的文库</a></li>
         	<li class="navlist_person"><a href="/person/myThemes">我的专题</a></li>
            <li class="navlist_person"><a href="/personCenter1">我的信息</a></li>
       		<li class="navlist_person"><a href="/person/myAccount">我的账户</a></li>
            <li id="navlist2" class="navlist_person"><a href="/logout.do">注销</a></li>
            <li id="navlist2"><a href="/">网站首页</a></li>
		</ul>
        </div>

   
	
	<sitemesh:body/>  
	
		
   

</body>
</html>