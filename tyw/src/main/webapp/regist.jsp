<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>邮箱注册页</title>
<link href="../css/person.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript src="homesearch.js"></SCRIPT>
<!--[if lt IE 7]>
        <script type="text/javascript" src="../unitpngfix.js"></script>
<![endif]-->
<script type="text/javascript" >
function $$$$$(_sId){
 return document.getElementById(_sId);
 }
function hide(_sId)
 {$$$$$(_sId).style.display = $$$$$(_sId).style.display == "none" ? "" : "none";}
function pick(v) {
 document.getElementById('am').value=v;
hide('HMF-1')
}
function bgcolor(id){
 document.getElementById(id).style.background="#F7FFFA";
 document.getElementById(id).style.color="#000";
}
function nocolor(id){
 document.getElementById(id).style.background="";
 document.getElementById(id).style.color="#788F72";
}
</script>
<script type="text/javascript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}
//-->
</script>
<script>
<!--
/*第一种形式 第二种形式 更换显示样式*/
function setTab(name,cursel,n){
for(i=1;i<=n;i++){
var menu=document.getElementById(name+i);
var con=document.getElementById("con_"+name+"_"+i);
menu.className=i==cursel?"hover":"";
con.style.display=i==cursel?"block":"none";
}
}
//-->
</script>
</head>

<body>
<div class="cloud"></div>
<div class="body">
<div class="login_top">
  	<a href="../index.html"><img class=" zuo10 left" src="../images/search_03.png" /></a>
  	<div class="right"><a href="#">返回网站首页</a>|<a href="#">收藏本站</a>|<a href="#">帮助中心</a></div>
  	<div class="clear"></div>
  </div>
    <div class="search_content_box">
  <div class="search_content">
  	<div class="regist_left left">
    	<div class="regist">
    	  <div class="regist_choose">
          	<div class="reg_xuan"><a id="one1" onclick="setTab('one',1,2)" class="hover" href="#">邮箱注册</a><a id="one2" onclick="setTab('one',2,2)" href="#">手机注册</a></div>
    	   
          <div class="clear"></div>
          </div>
          
    	</div>
       <form id="form1" name="form1" method="post" action="/regists">
        <table id="con_one_1" class="login_table" width="650" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="114" align="right">电子邮箱<span class="must"> *&nbsp;&nbsp;</span> </td>
            <td width="247">
            	<input name="mail" type="text" class="login_input" id="mail" size="35" />
            	<input type="hidden" name="userType" value="1"/>
            	<input type="hidden" name="additional.sex" value="1"/>
            </td>
            <td width="289" height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td align="right">密码<span class="must"> *&nbsp;&nbsp;</span></td>
            <td><input name="password" type="password" class="login_input" id="password1" size="35" /></td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td align="right">确认密码<span class="must"> *&nbsp;&nbsp;</span></td>
            <td><input name="confirm_password" type="password" class="login_input" id="confirm_password1" size="35" /></td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td height="76" align="right">&nbsp;</td>
            <td colspan="2"><input type="button" class="regist_btn " disabled_with="提交中。。。。。" name="tijiao" id="tijiao" value="注册" onclick="return dosubmit()"/></td>
          </tr>
        </table>
       </form>
       <form id="form2" name="form2" method="post" action="/regists">
        <table id="con_one_2" class="login_table" style="display:none" width="650" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="114" align="right">手机号码<span class="must"> *&nbsp;&nbsp;</span> </td>
            <td width="247">
            	<input name="phone" type="text" class="login_input" id="phone" size="35" />
            	<input type="hidden" name="userType" value="1"/>
            	<input type="hidden" name="additional.sex" value="1"/>
            </td>
            <td width="289" height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td align="right">密码<span class="must"> *&nbsp;&nbsp;</span></td>
            <td><input name="password" type="password" class="login_input" id="password2" size="35" /></td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td align="right">确认密码<span class="must"> *&nbsp;&nbsp;</span></td>
            <td><input name="confirm_password" type="password" class="login_input" id="confirm_password2" size="35" /></td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td height="76" align="right">&nbsp;</td>
            <td colspan="2"><input type="button" class="regist_btn" name="tijiao2" id="tijiao2" value="注册" onclick="return dosubmit2();"/></td>
          </tr>
        </table>
      </form>
    </div>
    <div class="login_right right">
    	<div class="login_zhuce">
    	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="45" colspan="2" align="center" class="yanhei_hui">已注册用户？请直接登陆</td>
            </tr>
            <tr>
              <td height="83" colspan="2" align="center"><a href="/login.jsp"><img src="../images/regist/regist_17.gif" width="130" height="40" /></a></td>
            </tr>
            <tr>
              <td width="13%" height="124">&nbsp;</td>
              <td width="87%"><div class="info_youhui">
                <div class="info_youhui_title">完善资料可以获得以下优惠或特权：</div>
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
联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号</div>
</div>

<div class="guding">
<div class="guding2">

<div class="btn_fd">
	<a href="#"></a>
	<a href="#top"><img title="返回顶部" class="shang20" src="../images/btnfd_13.gif" /></a></div>
</div>
</div>
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/formValidator-4.1.1.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/regist.js" type="text/javascript"></script>
</body>
</html>
