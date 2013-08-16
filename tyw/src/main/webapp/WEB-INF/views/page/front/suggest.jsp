<%@ page contentType="text/html; charset=utf-8" language="java" 
import="com.isoftstone.tyw.entity.auths.User,com.isoftstone.tyw.entity.auths.Additional,java.sql.*,com.isoftstone.tyw.util.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>您的意见</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/person.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/jquery-ui-1.10.2/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />

<script src="homesearch.js" language=javascript></script>
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
<script src="${ctx}/js/persondialogs.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/uploadify/jquery.uploadify-3.1.min.js" type="text/javascript"></script>
    
	
</head>

<body> 
 <div class="search_up"></div>
  <div class="search_content_box">
  <div class="search_content">
    <div class="person_nav2">
    	
        	
  </div>
<div class="person_left left">
    	<div class="person_info">
        
        </div>
      <div class="content_box">
	<form id="addSuggest" name="addSuggest" method="post" action="/addSuggest" onsubmit="return check();">
		
		
        <table id="con_one_1" width="700" border="0" cellpadding="0" cellspacing="0" class="person_info_add hover">
         
          <tr>
            <td height="40" align="right">姓&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
            <td height="40"><input type="text" name="name" id="name" value="" /></td>
            
          </tr>
          <tr>
            <td height="40" align="right">邮&nbsp;&nbsp;&nbsp;&nbsp;箱：</td>
            <td height="40"><input type="text" name="email" id="email" value="" /></td>
          </tr>
          
          <tr>
            <td height="40" align="right">手&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
            <td height="40"><input type="text" name="phone" id="phone" value="" /></td>
          </tr>
          <tr>
            <td height="40" align="right">Q&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Q：</td>
            <td height="40"><input type="text" name="qq" id="qq" value="" /></td>
          </tr>
          
          <tr>
            <td height="40" align="right">标&nbsp;&nbsp;&nbsp;&nbsp;题：</td>
            <td height="40"><input type="text" name="title" id="title" value="" /></td>
          </tr>
          
          <tr>
            <td height="40" align="right">您的意见：</td>
            <td height="40" colspan="2"><textarea name="content" id="content" cols="45" rows="5"></textarea></td>
            
          </tr>
          <tr>
            <td align="right">&nbsp;</td>
            <td height="80" colspan="2"><input type="submit" value="提交"/></td>
          </tr> 
        </table>
</form>
        </div>
    </div>
     
    <div class="clear"></div>
    </div>
  </div>
    <div class="search_down"></div>
    <div class="clear"></div>
    <div class="foot">
		<!-- 联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号  -->
		<jsp:include page="${pageContext.request.contextPath}/bottom.jsp" />
	</div>
<div class="btn_fd">
	<a href="#"></a>
	<a href="#top"><img title="返回顶部" class="shang20" src="images/btnfd_13.gif" /></a>
</div>
</body>
<script type="text/javascript">
	function check(){
		var content = document.getElementById("content").value;
		if(content.length==0){
			alert("请填写意见内容");
			document.getElementById("content").focus();
			return false;
		}
		if(/^\s+$/gi.test(content)){
			alert("内容不能全为空格！");
			document.getElementById("content").focus();
			return false;
		}
		if(content.length>500){
			alert("内容不能超过500字！");
			document.getElementById("content").focus();
			return false;
		}
	}

</script>
</html>
 
