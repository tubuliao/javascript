<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/person.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript src="homesearch.js"></SCRIPT>

<title>修改状态</title> 
</head>
<body>
	<div class="search_up"></div>
	    <div class="search_content_box">
	    <div class="search_content">
	    <div class="person_nav2">
	    	
	        	<a href="/personCenter1">个人资料补全</a>
	            <a href="/personCentertoModifyPassword">修改密码</a>
	            <!-- 
	            <a class="present" href="/personCentertoValidatePhone">密保手机</a>
                <a href="/personCentertoValidateEmail">密保邮箱</a>
            <a href="#">我的积分</a>
            <a href="#">我的等级</a>
            <a href="#">安全中心</a-->
	        </ul>
	    </div>

		<div class="person_left left">
    	<div class="order_title">保密手机<span class="order_title_en"> / secret phone</span> </div>
    	<form id="form2" name="form2" method="post" action="">
    	  <table width="700" border="0" cellpadding="0" cellspacing="0" class="code">
            <tr>
              <td height="40" colspan="3" align="left" valign="bottom"> <span class="yanhei_hui zuo20">忘记密码时可通过保密手机重置密码！</span></td>
            </tr>
            <tr>
              <td height="60" colspan="3" align="left">
              <div class="regist_liucheng">
        <ul>
          <li class="present">1.<span>填写信息</span></li>
          <img src="../images/regist/regist_07.gif" />
          <li>2.<span>验证信息</span></li>
          <img src="../images/regist/regist_07.gif" />
          <li>3.<span>设置成功</span></li>
        </ul>
        </div>
              </td>
            </tr>
            <tr>
              <td width="85" height="40" align="right">手机号：</td>
              <td width="217" height="40"><input class="login_input" name="textfield3" type="text" id="textfield3" size="30" /></td>
              <td width="396" height="40" class="login_tishi">请输入您的手机号码</td>
            </tr>
            <tr>
              <td width="85" height="40" align="right">登陆密码：</td>
              <td width="217" height="40"><input class="login_input" name="textfield2" type="text" id="textfield2" size="30" /></td>
              <td width="396" height="40" class="login_tishi">密码长度6-15为，由字母数字下划线组成</td>
            </tr>
            <tr>
              <td align="right">&nbsp;</td>
              <td height="40" colspan="2"><input type="image" src="../images/personal/save_13.gif" name="button2" id="button2" value="提交" />
                  <input type="image" src="../images/personal/save_15.gif" name="button3" id="button3" value="提交" /></td>
            </tr>
          </table>
    	</form>
    </div>
    
     <div class="person_right right">
    	<div class="person_info_s">
        <div class="person_info_touxiang">
          <a href="#"><img src="${httpAddress}${user.headUrl}" width="89" height="89" /></a> </div>
       	  <div class="person_info_shot">
    <div><a href="#">${user.username }</a></div>
    <div class="" style="display:inline">
	    <img src="../images/personal/wb_03.png" width="16" height="16" /><img src="../images/personal/wbh_05.png" width="16" height="16" /><img src="../images/personal/dj_05.png" width="40" height="17" /> 
	    <img src="../images/personal/vip_07.png" width="42" height="17" />
    </div>
  </div>
        	<div class="person_info_caozuo">
            	<span><a href="#">修改密码&nbsp;</a>  | 
            	 <a href="#">&nbsp;购卡&nbsp;</a>  |
            	 <a href="#">个人资料</a></span>
            	 <span class="zuo10 hui">完整度</span> 
            	 <span class="anhong zihao14">40%</span>
            	 <div class="person_info_qk">
   					 <ul>
     					 <li class="dui">邮箱</li>
     					 <li class="cuo">手机</li>
      					 <li class="dui">头像</li>
   				 	</ul>
    			 <div class="clear"></div>
  			</div>
            	 
            </div>
        </div>
        <div class="info_youhui">
       	  <div class="info_youhui_title">继续完善可以获得以下优惠或特权：</div>
            <ul>
           	  <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
            </ul>
        </div>
        <div class="new_youhui">
        	<div class="new_youhui_title">最新优惠<span class="new_youhui_title_en"> / latest preferential</span> </div>
            <ul>
            	<li><a href="#"><img src="images/personal/person_11.gif" width="53" height="64" /></a></li>
                <li><a href="#"><img src="images/personal/person_11.gif" width="53" height="64" /></a></li>
                <li><a href="#"><img src="images/personal/person_11.gif" width="53" height="64" /></a></li>
            </ul>
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
<div class="btn_fd">
	<a href="#"></a>
	<a href="#top"><img title="返回顶部" class="shang20" src="images/btnfd_13.gif" /></a>
</div>
</body>
</html>