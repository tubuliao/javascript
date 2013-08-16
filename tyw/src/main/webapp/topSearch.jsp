<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>


</head>

<body>
<div class="head_top"><a href="/jump.jsp" target="_parent"><img src="${pageContext.request.contextPath}/images/home/logo_home_03.png" width="222" height="81" /></a>
   <form method="post" action="${pageContext.request.contextPath}/goPageList" name="searchform" id="searchform" onsubmit="return issub()">
<div class="whitetxt2">
<div id="one0" onclick="setTabs('one',0,6)"  class="hover" >全部</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="one1" onclick="setTabs('one',1,6)"   >知识</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="one2" onclick="setTabs('one',2,6)"  >表格</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="one3" onclick="setTabs('one',3,6)" >图片</div>
  <div  style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="one4" onclick="setTabs('one',4,6)" >视频</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC">|</div>
  <div id="one5" onclick="setTabs('one',5,6)" >文档</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="one6" onclick="setTabs('one',6,6)" >课件</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
</div>
<div class="clear"></div>

    
            
          <div class="search_input2">
   
			<input id="typeId"   type="hidden"   name="typeId"/>
			<input id="onex"   type="hidden"   name="onex"  />
          <input id="tag_name" value="" type="hidden" name="tag_name" />
           <input id="pageNo" value="1" type="hidden" name="pageNo" />
          <input class="shurukuang2" id="keyword" value="${keyword}" size="40" type="text" name="keyword" 
          	x-webkit-speech="" x-webkit-grammar="builtin:translate" 
          			onfocus="onkeyWordVal(this.value)" onkeydown="return onkeyword(event)"
					onblur="this.value=(this.value=='') ? '输入要查找的内容' : this.value;"/>
          <input class="search_btn" id="search_btn"  value="搜索"  type="submit"  onclick="isclick()" style="vertical-align:middle" />
        </div>
  </form>
  
  
 
    <div class="clear"></div>
</div>
</body>
</html>
