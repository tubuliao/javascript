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
</head>

<body>
<div class="cloud"></div>
<div class="body">
<div class="head"> <img class="logo left" src="../images/search_03.png" />
    <div class="top">
      <div class="fav2 right"><a href="#">加入收藏</a> | <a href="#">帮助中心</a></div>
      <div class="search2">
        <form id="form1" name="form1" method="post" action="">
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
  	<div class="login_left left">
    	<img class="login_pic" src="../images/regist/login_03.gif" />
      <form id="form2" name="form2" method="post" action="login.do">
        <table class="login_table" width="650" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="109" align="right">用户名：</td>
            <td colspan="2"><input name="j_username" type="text" class="login_input" id="j_username" size="35" /><input type="hidden" name="userType" value="1"/></td>
            <td width="335" height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td align="right">密码：</td>
            <td colspan="2"><input name="j_password" type="password" class="login_input" id="j_password" size="35" /></td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td align="right">验证码：</td>
            <td colspan="2"><input name="validateCode" type="text" class="login_input" id="validateCode" size="30" /></td>
            <td height="40"></td>
          </tr>
          <tr>
            <td align="right"><span class="must"></span></td>
            <td colspan="2"><img id="yzm" src="/validateCode" onclick="this.src=this.src+'?'+(new Date()).getTime()" /><a href="#" onclick="yanzhengma();">看不清，换一张</a></td>
            <td height="40"></td>
          </tr>
          <tr>
            <td align="right">&nbsp;</td>
            <td width="106"><input type='checkbox' name='_spring_security_remember_me' id="_spring_security_remember_me" tabindex="3" value="true"/>
            下次自动登陆&nbsp;</td>
            <td width="100"><a href="#">忘记密码？</a></td>
            <td height="40">&nbsp;</td>
          </tr>
          <tr>
            <td height="76" align="right">&nbsp;</td>
            <td colspan="3"><input type="image" src="../images/regist/login_11.gif" name="button2" id="button2" value="提交" onclick="dosubmit();"/></td>
          </tr>
        </table>
      </form>
    </div>
    <div class="login_right right">
    	<div class="login_zhuce">
    	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td height="45" colspan="2" align="center"><img src="../images/regist/login_05.gif" width="264" height="26" /></td>
            </tr>
            <tr>
              <td height="83" colspan="2" align="center"><a href="/regist"><img src="../images/regist/login_07.gif" width="130" height="40" /></a></td>
            </tr>
            <tr>
              <td width="11%" height="124">&nbsp;</td>
              <td width="89%"><div class="info_youhui">
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
<script src="js/login.js" type="text/javascript"></script>
</body>
</html>