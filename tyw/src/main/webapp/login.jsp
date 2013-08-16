<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:wb="http://open.weibo.com/wb">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录页</title>
<link href="../css/person.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../css/regist.css" rel="stylesheet" type="text/css" />

<link rel="Bookmark"  type="image/x-icon"  href="favicon.ico"/>
<link rel="shortcut icon" href="favicon.ico" />
<link rel="icon" href="favicon.ico" type="image/gif"/>

<script language=javascript src="homesearch.js"></script>
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=3495616062" type="text/javascript" charset="utf-8"></script>
<!--[if lt IE 7]>
        <script type="text/javascript" src="../unitpngfix.js"></script>
<![endif]-->

</head>

<body onkeypress="if(event.keyCode==13){dosubmit();}">
<div class="cloud"></div>
<div class="body">
  <div class="login_top">
  	<a href="../index.jsp"><img class="left" src="../images/search_03.png" /></a>
  	<div class="right"><a href="/index.jsp">返回网站首页</a>|<a href="#"><span style="CURSOR: hand" onclick="javascript:window.external.AddFavorite('http://www.tianyouwang.net','一个专业的建筑网站---天佑网')" title="网址">收藏本站</span></a><!-- a href="#">帮助中心</a--></div>
    <div class="clear"></div>
  </div>
  <div class="login_body">
  	<div class="login_content">
   	  <div class="login_box">
       	<div class="login_box_title">
          <ul>
   			  <li> 现在登录</li>
			  <li class="present"><a href="/regist.jsp">注册</a></li>
          </ul>
            </div>
           <form id="form2" name="form2" method="post" action="login.do">
           	<input type="hidden" name="currUrl" value='<%=request.getHeader("Referer")%>'/>
            <table width="340" border="0" align="left" cellpadding="0" cellspacing="0" class=" shang10">
              <tr>
                <td height="50" align="right"><img class="you10" src="../images/regist/btn_03.gif" width="20" height="18" /></td>
                <td colspan="2"><input name="j_username" type="text" class="login_box_input" id="j_username" size="36" style="width:240px"
            		onfocus="this.value=(this.value=='手机号/邮箱/用户名') ? '' : this.value;"
					onblur="this.value=(this.value=='') ? '手机号/邮箱/用户名' : this.value;" value="手机号/邮箱/用户名" /><input type="hidden" name="userType" value="1"/></td>
              </tr>
              <tr>
                <td height="50" align="right"><img src="../images/regist/btn_06.gif" width="20" height="24" class="you10" /></td>
                <td colspan="2"><input name="j_password" type="password" class="login_box_input" id="j_password" size="36" style="width:240px"/></td>
              </tr>

              <tr>
                <td height="40" align="right">&nbsp;</td>
                <td width="148" align="left"><input type="checkbox" name="_spring_security_remember_me" id="remember" />
                  记住用户名、密码&nbsp;</td>
                <!-- td width="138" align="left"><a href="#">忘记密码？</a></td-->
              </tr>
               <tr>
	            <td width="24%" height="20"><span class="STYLE9"></span></td>
	            <td width="76%"><c:if test='${param.authentication_error=="true"}'><font color="red">用户名或密码错误</font></c:if></td>
          	   </tr>
              <tr>
                <td height="60" align="right">&nbsp;</td>
                 <td colspan="2"><input type="button" class="login_btn" name="button2" id="button2" value="马上登录" onclick="dosubmit();"/></td>
                </tr>
              <tr>
                <td height="50" align="right">&nbsp;</td>
                <!-- td colspan="2">
                
                <a href="https://api.weibo.com/oauth2/authorize?client_id=<App Key>&redirect_uri=<Redirect URI>&response_type=code">
                <wb:login-button type="3,2" onlogin="login" onlogout="logout" ></wb:login-button>   
                </a>
                
                <img class="weiboimg" src="../images/regist/share_13.gif" width="16" height="16" /> <a href="#">腾讯微博账号登陆</a></td-->
              </tr>
            </table>
           </form>
    	</div>
    </div>
  
  </div>
    <div class="search_down"></div>
    <div class="clear"></div>
    <div class="foot">
<!-- 联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号  -->
<jsp:include page="${pageContext.request.contextPath}/bottom.jsp" />

</div>
</div>
<script type="text/javascript">


function getCookie(c_name){　　　　
	if (document.cookie.length>0){　　
		c_start=document.cookie.indexOf(c_name + "=");
	if (c_start!=-1){　　　　　　　　
		c_start=c_start + c_name.length+1;　
		c_end=document.cookie.indexOf(";",c_start)　
		if (c_end==-1) c_end=document.cookie.length　　　　　　　
			return unescape(document.cookie.substring(c_start,c_end))　;
		}
	}　
	return ""　
}　　

function setCookie(c_name, value, expiredays){
	var exdate=new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	document.cookie=""+c_name+ "=" + escape(value) + ((expiredays==null) ? "" : ";expires="+exdate.toGMTString());
}
var u = getCookie('tywUsername');
var p = getCookie('tywPassword');
if(''!=u&&null!=u){
	$('#j_username').val(u);
	$('#remember').attr("checked", true);
}
if(''!=p&&null!=p){
	$('#j_password').val(p);
}
function dosubmit(){
	if($('#remember').is(":checked")){
		var un = $('#j_username').val();
		var pw = $('#j_password').val();
		setCookie('tywUsername',un,30);
		setCookie('tywPassword',pw,30);
	}else{
		setCookie('tywUsername','');
		setCookie('tywPassword','');
	}
    $('#form2').submit();  
}
$("#button2").focus(); 
//新浪微博登陆
/* WB2.anyWhere(function(W){
    W.widget.connectButton({
        id: "wb_connect_btn",	
        type:"3,2",
        callback : {
            login:function(o){	//登录后的回调函数
            },	
            logout:function(){	//退出后的回调函数
            }
        }
    });
}); */
</script>
</body>
</html>
