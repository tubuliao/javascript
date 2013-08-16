<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人信息</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/person.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/js/Jquery.L.Message.js"></script>
<script type="text/javascript">
$(function() {
		$("#modifyPasswordForm").validate({
	        rules: {
	    			originalPassword: {
					    required: true, 
					    remote:{ //密码是否正确
					    	　　 type:"POST",
					    	　　 url:"validatePassword", 
					    	　　 data:{
						    	　　 password:function(){
						    		 	return $("#originalPassword").val();
						    		}
					    	　　 } 
					    } 
				    },
				    password: {
					    required: true ,
					    equalTo:'#confirmPassword'						 
				    },
				    confirmPassword: {
					    required: true,
						rangelength:[6,20],
						remote:{
							type:"POST",
							url:"passwordRepeat",
							data:{
								originalPassword:function(){
									return $("#originalPassword").val();
								},
								newPassword:function() {
									return $("#confirmPassword").val();
								}
							}
						}
				    }  
	 			 },
	        messages: { 
				    originalPassword: {
					    required: "请填写原始密码",
					    remote:'填写原始密码不正确'
				   } ,
				   password: {
					   required: "请填写确认密码",
					   equalTo:"密码不一致"
					   
				    },
				    confirmPassword: {
				    	 required: "请填新密码",
						 rangelength: '密码长度必须介于6和20之间',
						 remote: '新密码不能与当前密码相同'
				    }  
	  			}
	    });
		
	$("#sub").click(function(){
		$("#modifyPasswordForm").submit();
		//location.href = '/logout.do';
		//$('#hello').messagebox('修改密码成功！<br/>3秒后跳转到登陆页面！<br/>', '/login.jsp', 1, 3000);
		
	});
	$("#reset").click(function(){
		document.getElementById("modifyPasswordForm").reset();
	});
});
 
	</script>
</head>
<body>
  <div class="search_up"></div>
  <div class="search_content_box">
  <div class="search_content">
    <div class="person_nav2">
    	
        	<a href="/personCenter1">个人资料补全</a>
            <a class="present" href="/personCentertoModifyPassword">修改密码</a>
             <!-- 
            <a href="/personCentertoValidatePhone">密保手机</a>
            <a href="/personCentertoValidateEmail">密保邮箱</a>
            <a href="#">我的积分</a>
            <a href="#">我的等级</a>
            <a href="#">安全中心</a-->
        </ul>
  </div>
<div class="person_left left">
    	<div class="order_title">修改密码<span class="order_title_en"> / change code</span> </div>
    	<form id="modifyPasswordForm" name="modifyPasswordForm" method="post" action="/personalCenterModifyPassword">
    	  <!-- div class="state">
<div>
  <div class="wzd left">您的账户安全等级：<font size="4" class="anhong"> 低 </font></div>
  <div class="progress left">
    <div class="progress_grows"></div>
  </div>
  <a href="#"><img class="stateimg" src="../images/personal/xf_03.gif" /></a>
  <p class="clear">您的帐号未设置任何密码保护措施，当您忘记密码时，将无法找回密码！</p>
</div>
          </div-->
    	  <table width="700" border="0" cellpadding="0" cellspacing="0" class="code">
            <tr>
              <td width="95" height="40" align="right">当前密码：</td>
              <td width="217" height="40">
              	<input class="login_input" name="originalPassword" type="password" id="originalPassword" size="30" />
              	<input type="hidden" name="pwd" id="pwd" value="${user.password}" />
              </td>
              <td width="386" height="40" class="login_tishi">
			  	<label for="originalPassword" class="error"></label>
			  </td>
            </tr>
            <tr>
              <td height="40" align="right">新密码：</td>
              <td height="40"><input class="login_input" name="confirmPassword" type="password" id="confirmPassword" size="30" /></td>
              <td height="40" class="login_tishi"><label for="confirmPassword" class="error"></label></td>
            </tr>
            <tr>
              <td height="40" align="right">确认密码：</td>
              <td height="40"><input class="login_input" name="password" type="password" id="password" size="30" /></td>
              <td height="40" class="login_tishi"><label for="password" class="error"></label></td>
            </tr>

            <tr>
              <td align="right">&nbsp;</td>
              <td height="40" colspan="2">
               <input id="sub" type="image" src="../images/personal/save_13.gif" name="button2" id="button2" value="提交" />
               <input id="reset" type="image" src="../images/personal/save_15.gif" name="button3" id="button3" value="提交" />
              </td>
            </tr>
          </table>
    	</form>
    </div>
     <div id="hello"></div>
     <div class="person_right right">
    	<jsp:include page="right.jsp" ></jsp:include>
        <div class="info_youhui">
       	  <!-- div class="info_youhui_title">继续完善可以获得以下优惠或特权：</div>
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
        </div -->
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