<%@ page isELIgnored="false" contentType="text/html; charset=utf-8" language="java" 
import="com.isoftstone.tyw.entity.auths.User,com.isoftstone.tyw.entity.auths.Additional,java.sql.*,com.isoftstone.tyw.util.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
PropertiesReader pu = PropertiesReader.getInstance();
String path = pu.getProperties("fdfs.HttpAddress");
//虽然使用了session但是不影响数据即时性展示问题，因为每次都是最新的放入session中
session.setAttribute("httpAddress", path);

User user = (User)session.getAttribute("user");
if(user == null){
	user = new User();
	Additional additional = new Additional();
	user.setAdditional(additional);
}
String url = "";
if(user.getHeadUrl() == "" || user.getHeadUrl() == null){
	url = request.getContextPath()+"/images/personal/person_07.gif";
}else{
	url = path+user.getHeadUrl();
}

String gradeimg = "";
if(user.getGrade()==1){
	gradeimg = "images/personal/vip_09.png";
}else if(user.getGrade()==2){
	gradeimg = "images/personal/vip_11.png";
}else if(user.getGrade()==3){ 
	gradeimg = "images/personal/vip_13.png";
}else{
	gradeimg = "images/personal/vip_07.png";
}
 

request.setAttribute("url", url);
%>
 
    	<div class="person_info_s">
        <div class="person_info_touxiang">
          <a href="#"><img id="imgHead" src="${url}" width="89" height="89" /></a> </div>
       	  <div class="person_info_shot">
    <div><a href="#">${user.username }</a></div>
    <div class="" style="display:inline">
	    <img src="../images/personal/wb_03.png" width="16" height="16" /><img src="../images/personal/wbh_05.png" width="16" height="16" /><img src="../images/personal/dj_05.png" width="40" height="17" /> 
	    <img src="../<%=gradeimg %>" width="42" height="17" />
    </div>
  </div>
        	<div class="person_info_caozuo">
            	<span><a href="/personCentertoModifyPassword">修改密码&nbsp;</a>  | 
            		<!-- 
            	 <a href="#">&nbsp;购卡&nbsp;</a>  |
            	 -->
            	 <a href="/personCenter1">个人资料</a></span>
            	 <span class="zuo10 hui">完整度</span> 
            	 <span class="anhong zihao14">${sumTotal}%</span>
            	 <div class="person_info_qk">
   					 <ul>
     					 <li 
							 <%
     					 	if(user.getAdditional().getEmail()!=null&&user.getAdditional().getEmail().length()>0){
     					 		out.print("class='dui'");
     					 	}else{
     					 		out.print("class='cuo'");
     					 	}
     					  %> 
							>邮箱</li>
     					
     					 <li 
     					 <%
     					 	if(user.getAdditional().getMobile()!=null&&user.getAdditional().getMobile().length()>0){
     					 		out.print("class='dui'");
     					 	}else{
     					 		out.print("class='cuo'");
     					 	}
     					  %> 
     					 	>手机</li>
     					  
      					 <li
      					 <%
     					 	if("/images/personal/person_07.gif".equals(url)){
     					 		out.print("class='cuo'");
     					 	}else{
     					 		out.print("class='dui'");
     					 	}
     					  %> 
							>头像</li>
   				 	</ul>
    			 <div class="clear"></div>
  			</div>
            	 
            </div>
        </div>
 