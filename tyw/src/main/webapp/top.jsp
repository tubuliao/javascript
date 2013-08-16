<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 

 <title>天佑网</title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/search.css"/>

<link rel="Bookmark"  type="image/x-icon"  href="${pageContext.request.contextPath}/favicon.ico"/>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/gif"/>
 <body>
<div class="home_top">
    <div class="home_top_box"><div style="float:left">
    	<input type="hidden" id="isFirstLogin" value="<c:out value="${firstLogin}"/>">
	    <c:if test="${!empty user}">
	    	您好,&nbsp;&nbsp;<c:out value="${user.username}"/>
	    	<c:if test="${!empty user.aliasname}"> 【 <c:out value="${user.aliasname}"/> 】</c:if>
	    	
	    	<sec:authorize ifAllGranted="ROLE_PERSON">
		                    &nbsp;<a href="${pageContext.request.contextPath}/personCentermyStatus">个人中心</a>
	        </sec:authorize>
	     	<sec:authorize ifAllGranted="ROLE_CHANNEL">
		                    &nbsp;<a href="${pageContext.request.contextPath}/channel/web/home">渠道商中心</a>
	        </sec:authorize>
	     	<sec:authorize ifAllGranted="ROLE_ENTERPRISE">
		                    &nbsp;<a href="${pageContext.request.contextPath}/tywViewer?rootCode=40102000000000000">企业中心</a>
	        </sec:authorize>
	    	<sec:authorize ifAllGranted="ROLE_BACKGROUND">
		                    &nbsp;<a href="${pageContext.request.contextPath}/toBackGround">进入后台</a>
	        </sec:authorize>
							&nbsp;<a href="/logout.do">登&nbsp;出</a><br />
	    </c:if>
	    <c:if test="${empty user}">
	    	 请 <a id="userLogin" href="${pageContext.request.contextPath}/login.jsp" target="_parent">登录</a> | <a href="${pageContext.request.contextPath}/toLicenseLogin" target="_parent">项目许可入口</a><%--  | <a href="${pageContext.request.contextPath}/regist.jsp" target="_parent">注册</a>  --%>
	    </c:if>
    </div>
    
    
    
    
    
    
    <div class="ggao left zuo20">
    <div class="left"><img class="ggaoimg" src="/images/home/speaker_03.png" /> 公告：</div>
    
    <c:choose>  
  			<c:when test="${!empty user}">
  			<marquee class="left" id="announce" scrollAmount=2 width=240 onmouseover=stop() onmouseout=start()></marquee>
   			</c:when>  
   			<c:otherwise>
   			<marquee class="left" id="announce" scrollAmount=2 width=240 onmouseover=stop() onmouseout=start()></marquee>  
   			</c:otherwise>
	</c:choose>
    
     
    </div>       
		<div class="right">
		<c:if test="${!empty user}">
        <a href="http://116.213.192.72/sourceware/tyw/content.htm" target="_blank"><img src="${pageContext.request.contextPath}/images/home/film.gif" />使用说明</a> | 
        </c:if>
        <a href="javascript:void(0);" class="hrefs" onclick="SetHome(this,'http://www.tianyouwang.net');">设为首页</a> | <a href="javascript:void(0);"><span style="CURSOR: hand" onclick="AddFavorite('http://www.tianyouwang.net','一个专业的建筑网站---天佑网')" title="网址">收藏本站</span></a></div>
       
    </div>
  </div>
  <!-- 第一次登陆欢迎提示start -->
  
  
  
  
  
  
  
  <div class="ding" id="welcome" style="z-index:99999;display:none;"> 

<div class="welcome" >
<div class="huanying">
<img src="${pageContext.request.contextPath}/images/home/hy_03.gif" />
<form id="form1" name="form1" method="post" action="">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="5%">&nbsp;</td>
      <td height="35" colspan="4"><img src="${pageContext.request.contextPath}/images/home/film.gif" /><a href="http://116.213.192.72/sourceware/tyw/content.htm" target="_blank"><img src="${pageContext.request.contextPath}/images/home/hulj_07.gif" width="115" height="20" /></a></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td width="21%"><img src="${pageContext.request.contextPath}/images/home/hy_07.gif" width="172" height="20" /></td>
      <td width="16%">400-8796-123</td>
      <td width="10%"><img src="${pageContext.request.contextPath}/images/home/hy_09.gif" width="83" height="20" /></td>
      <td width="48%" height="35"><a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=2945694238&amp;site=qq&amp;menu=yes">2945694238</a></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td height="30" colspan="4"><label><input type="checkbox" name="nextAlert" id="nextAlert" onclick="nextAlert1();"/>下次不再显示</label></td>
    </tr>
  </table>
</form>
</div>
<a onclick="welcome.style.display='none'" ><img class="welcome_close" src="${pageContext.request.contextPath}/images/home/hygb_03.png" /></a> </div>

</div>
 <!-- 第一次登陆欢迎提示end -->  
  
</body>
<script type="text/javascript">
function SetHome(obj,url){
	 
    try{
 
        obj.style.behavior='url(#default#homepage)';
 
       obj.setHomePage(url);
 
   }catch(e){
 
       if(window.netscape){
 
          try{
 
              netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
 
         }catch(e){
 
              alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
 
          }
 
       }else{
 
        alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【"+url+"】设置为首页。");
 
       }
 
  }
 
}




function AddFavorite(url, title) {
	  try {
	 
	      window.external.addFavorite(url, title);
	 
	  }
	 
	catch (e) {
	 
	     try {
	 
	       window.sidebar.addPanel(url, title, "");
	 
	    }
	 
	     catch (e) {
	 
	         alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加");
	 
	     }
	 
	  }
	 
	}
//欢迎提示JS代码
var isFirstLogin = 1;
isFirstLogin = document.getElementById("isFirstLogin").value;
if(isFirstLogin=="0"){
	document.getElementById("welcome").style.display="";
	<%session.removeAttribute("firstLogin");%>
}

function nextAlert1(){
	var xmlhttp;
	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	objName= document.getElementById("nextAlert");
	if (objName.type=="checkbox" && objName.checked){ //选中，下次不再提示   
        
        xmlhttp.open("GET","/web/welcome?nextAlert=1&t=" + Math.random(),true);
        xmlhttp.send();  
        //alert('你选中了');          
    }else{//未选中下次提示
    	
    	xmlhttp.open("GET","/web/welcome?nextAlert=0&t=" + Math.random(),true);
        xmlhttp.send(); 
    	//alert('您取消了')  
    }
	/* xmlhttp.onreadystatechange = function(){
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200){
			element.innerHTML = xmlhttp.responseText;
		}
	}
	xmlhttp.send(null); */
}

$(window).load(function(){
 	var html="";
 	var html1="";
 	var html2="";
 	$.post("/announcelist/data",function(result){
 		var total = result.total;
 		 $.each(result.rows,function(i,t){
 			if(i == 0){
 				html1= "<a href='/announcetotallist?annId="+t.id+"' target='_blank'><img src='/images/home/new.gif' />"+(i+1) + "." + t.title + "</a>&nbsp;&nbsp;&nbsp;&nbsp;";
 			 }else if(i == 1){
 				html2= "<a href='/announcetotallist?annId="+t.id+"' target='_blank'><img src='/images/home/new.gif' />"+(i+1) + "." + t.title + "</a>&nbsp;&nbsp;&nbsp;&nbsp;"; 
 			 }else{
 				html+= "<a href='/announcetotallist?annId="+t.id+"' target='_blank'>"+(i+1) + "." + t.title + "</a>&nbsp;&nbsp;&nbsp;&nbsp;"; 
 			 }
 		 });
 		 html = html1 + html2 + html;
 		 $("#announce").html(html);
   },"json");
})
</script>
 