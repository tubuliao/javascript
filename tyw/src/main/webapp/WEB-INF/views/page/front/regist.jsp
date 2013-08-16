<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>天佑网首页</title>
<link href="../css/person.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript src="homesearch.js"></SCRIPT>
<!--[if lt IE 7]>
        <script type="text/javascript" src="unitpngfix.js"></script>
<![endif]-->
<script type="text/javascript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
</head>

<body>
<div class="cloud"></div>
<div class="body">
<div class="head"> <img class="logo left" src="../images/search_03.png" />
    <div class="top">
      <div class="fav2 right"><a href="#">加入收藏</a> | <a href="#">帮助中心</a></div>
      <div class="search2">
        <form id="form0" name="form0" method="post" action="">
          <div class="shuru left">
            <div class="DivSelect left">
              <select name="select" class="SelectList">
                <option>新闻</option>
                <option>技术</option>
                <option>文档</option>
              </select>
            </div>
            <input type="text" name="textfield" id="textfield" />
          </div>
          <input type="image" class="" src="../images/btn_04.gif" name="button" id="button" value=" " />
        </form>
      </div>
    </div>
</div>
<div class="nav">
		<ul>
			<li><a class="" href="#">首页</a></li>
       		<li class="navlist"><a href="#">分部分项</a></li>
            <li class="navlist"><a href="#">强制性条文</a></li>
            <li class="navlist"><a href="#">核心条文</a></li>
            <li class="navlist"><a href="#">法律法规</a></li>
            <li class="navlist"><a href="#">标准规范</a></li>
		</ul>

    </div>
    <div class="search_up"></div>
    <div class="search_content_box">
  <div class="search_content">
  	<div class="regist_left left">
    	<div class="regist">
    	  <div class="regist_choose"><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image7','','../images/regist/regist_03.gif',0)"><img src="../images/regist/regist_phone_03.gif" name="Image7" width="112" height="35" border="0" id="Image7" /></a><img src="../images/regist/regist_phone_05.gif" width="112" height="35" /></div>
    	</div>
      <form id="form1" name="form1" method="post" action="/regists">
        <table class="login_table" width="650" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="114" align="right">电子邮箱<span class="must"> *&nbsp;&nbsp;</span> </td>
            <td width="251"><input name="mail" type="text" class="login_input" id="mail" size="35" /><input type="hidden" name="userType" value="1"/></td>
            <td width="285" height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td align="right">用户名<span class="must"> *&nbsp;&nbsp;</span></td>
            <td><input name="username" type="text" class="login_input" id="username" size="35" /></td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td align="right">密码<span class="must"> *&nbsp;&nbsp;</span></td>
            <td><input name="password" type="password" class="login_input" id="password" size="35" /></td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td align="right">确认密码<span class="must"> *&nbsp;&nbsp;</span></td>
            <td><input name="confirm_password" type="password" class="login_input" id="confirm_password" size="35" /></td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td align="right">验证码<span class="must"> *&nbsp;&nbsp;</span></td>
            <td><input type="text" name="validateCode" id="validateCode" /></td>
            <td height="40"></td>
          </tr>
          <tr>
            <td align="right"><span class="must"> *&nbsp;&nbsp;</span></td>
            <td><img id="yzm" src="/validateCode" onclick="this.src=this.src+'?'+(new Date()).getTime()" /><a href="#" onclick="yanzhengma();">看不清，换一张</a></td>
            <td height="40"></td>
          </tr>
          <tr>
            <td align="right">&nbsp;</td>
            <td><input type="checkbox" name="_spring_security_remember_me" id="_spring_security_remember_me" />
            下次自动登陆</td>
            <td height="35">&nbsp;</td>
          </tr>
          <tr>
            <td height="76" align="right">&nbsp;</td>
            <td colspan="2"><input type="image" src="../images/regist/regist_27.gif" name="button2" id="button2" value="提交" onclick="dosubmit()"/></td>
          </tr>
        </table>
      </form>
    </div>
    <div class="login_right right">
    	<div class="login_zhuce">
    	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="45" colspan="2" align="center"><img src="../images/regist/jk_13.gif" width="242" height="22" /></td>
            </tr>
            <tr>
              <td height="83" colspan="2" align="center"><a href="/loginView"><img src="../images/regist/regist_17.gif" width="130" height="40" /></a></td>
            </tr>
            <tr>
              <td width="13%" height="124">&nbsp;</td>
              <td width="87%"><div class="info_youhui">
                <div class="info_youhui_title">继续完善可以获得以下优惠或特权：</div>
                <ul>
                  <li>1.个性化的获取自己需要的信息</li>
                  <li>1.个性化的获取自己需要的信息</li>
                  <li>1.个性化的获取自己需要的信息</li>
                  <li>1.个性化的获取自己需要的信息</li>
                  <li>1.个性化的获取自己需要的信息</li>
                  </ul>
              </div></td>
            </tr>
          </table>
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
</div>
<script src="easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="js/formValidator-4.1.1.js" type="text/javascript"></script>
<script src="js/regist.js" type="text/javascript"></script>
</body>
</html>