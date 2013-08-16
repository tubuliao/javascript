<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>渠道商主页</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/css/chan.css" rel="stylesheet" type="text/css" />
<link href="/css/channel.css" rel="stylesheet" type="text/css" />
</head>
	<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
	<link href="${pageContext.request.contextPath}/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/uploadify/jquery.uploadify-3.1.min.js" type="text/javascript"></script>
<body>

	<!-- Logo + Search + Navigation -->
	<div class="top_box">
      <div id="top">
        <div class="com_top">
          <div class="left">你好，欢迎光临天佑网！</div>
          <!-- div class="right"><img src="/images/conmpany/share_11.gif" width="16" height="16" /> <a href="#">新浪微博</a> <img src="/images/conmpany/share_13.gif" width="16" height="16" /> <a href="#">腾讯微博</a> | <a href="#">加入收藏</a> | <a href="#">帮助中心</a></div-->
        </div>
        <div class="clear"></div>
        <h1 id="logo"><a href="/">MONDAYS</a></h1>
	  <form action="" method="post" id="search">
        <!-- input type="submit" class="button right" value="Search" />
        <div class="field-holder">
          <input type="text" class="field" value="搜索" onfocus="if(this.value=='搜索')this.value=''" onblur="if(this.value=='')this.value='搜索'" />
        </div-->
        <div class="cl">&nbsp;</div>
      </form>
        <div class="cl">&nbsp;</div>
        <div id="navigation">
          <ul>
            <li><a href="/channel/web/home"><span>主页</span></a></li>
            <li><a href="/channel/web/info" class="active"><span>我的信息</span></a></li>
            <li><a href="/channel/web/account"><span>我的账户</span></a></li>
            <!--li><a href="/channel/web/channel"><span>优惠通道</span></a></li>
            <li><a href="/channel/web/id" class="active"><span>企业账号</span></a></li>
            <li><a href="/channel/web/news"><span>我的动态</span></a></li-->
            <li style=" float:right"><a href="/logout.do"><span>注销</span></a></li>
            <li style=" float:right"><a href="/index.jsp"><span>网站首页</span></a></li>
          </ul>
        </div>
      </div>
</div>
	<div id="page" class="shell">
	<div class="channel_positon">
      <div class="left"><img src="/images/channel/channel_07.gif" /> 首页 <span class="hui"> &gt; </span> 个人信息</div>
      <div class="right">快乐分享：
        <!-- Baidu Button BEGIN -->
          <div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare" style="float:right; margin-top:8px;"> <a class="bds_qzone"></a> <a class="bds_tsina"></a> <a class="bds_tqq"></a> <a class="bds_renren"></a> <a class="bds_t163"></a> </div>
        <script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=6653508" ></script>
          <script type="text/javascript" id="bdshell_js"></script>
          <script type="text/javascript">
document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
      </script>
          <!-- Baidu Button END -->
      </div>
    </div>
<div class="channel_shot">
			<c:choose>  
  				<c:when test="${user.headUrl==null}">
     				<img class="channel_head left you10" src="/images/channel/default.gif" />
   				</c:when>  
   				<c:otherwise>  
   					<img class="channel_head left you10" src="${httpAddress}${user.headUrl}" />
   				</c:otherwise>
			</c:choose> 
      <div class=" left">
          <h3>${agent.name}</h3>
          </div>
      <div class="clear"></div>
  </div>
	<!-- END Logo + Search + Navigation -->
	<!-- Header -->
  <!-- END Header -->
  <!-- Main -->
<div id="main">
		<!-- Three Column Content -->
        <form id="agentInfo" name="agentInfo" method="post" action="/updateAgentInfo" onsubmit="return check();">
        <div class="cols two-cols">
        <h2>我的信息</h2>
	  <div class="col">
              
		  <div class="channel_news">
		    <div class="clear">
		      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="channel_table">
                <tr>
                  <td colspan="3" valign="top">&nbsp;</td>
                </tr>
                <tr>
                  <td width="100" height="40" align="right" valign="top">企业logo：</td>
                  <td width="199" height="40"><a href="#">
                  <c:choose>  
  				<c:when test="${user.headUrl==null}">
     				<img id="person_info_head" class="person_info_head" src="/images/channel/default.gif" width="150" height="150" />
   				</c:when>  
   				<c:otherwise>  
   					<img id="person_info_head" class="person_info_head" src="${httpAddress}${user.headUrl}" width="150" height="150" />
   				</c:otherwise>
				</c:choose>
                  </a>
                      <input name='headUrl' type='hidden' id="headUrl" value="${user.headUrl}" />
                      <input type="file" name="uploadify" id="uploadify"/>
                  </td>
                  <td></br></td>
                  <td></td>
                  <td width="575" height="40" valign="top" class="login_tishi"></td>
                </tr>
                <tr>
                  <td height="40" align="right">渠道商名称：</td>
                  <td height="40"><input type="hidden" name ="id" value="${user.id}" />
                      <input class="login_input" name="name" type="text" id="name" size="30" value="${agent.name}"/>
                    <font color="red">*</font></td>
                </tr>
                <tr>
                  <td height="40" align="right">联系地址：</td>
                  <td height="40"><input class="login_input" name="addr" type="text" id="addr" size="30" value="${agent.addr}"/>
                      <font color="red">*</font></td>
                  <td height="40">&nbsp;</td>
                </tr>
                <tr>
                  <td height="40" align="right">联系电话：</td>
                  <td height="40"><input class="login_input" name="phone" type="text" id="phone" size="30" value="${agent.phone}" maxlength="11"/>
                      <font color="red">*</font></td>
                  <td height="40" class="login_tishi"></td>
                </tr>
                <tr>
                  <td height="40" align="right">邮箱：</td>
                  <td height="40"><input class="login_input" name="email" type="text" id="email" size="30" value="${agent.email}"/>
                      <font color="red">*</font></td>
                  <td height="40">&nbsp;</td>
                </tr>
                <tr>
                  <td height="40" align="right">联系人：</td>
                  <td height="40"><input class="login_input" name="linkman" type="text" id="linkman" size="30" value="${agent.linkman}"/></td>
                  <td height="40">&nbsp;</td>
                </tr>
                <tr>
                  <td height="40" align="right">传真：</td>
                  <td height="40"><input class="login_input" name="fax" type="text" id="fax" size="30" value="${agent.fax}"/></td>
                  <td height="40" class="login_tishi"></td>
                </tr>
                <tr>
                  <td height="40" align="right">邮编：</td>
                  <td height="40"><input class="login_input" maxlength="6" name="zip" type="text" id="zip" size="30" value="${agent.zip}"/></td>
                  <td height="40" class="login_tishi"></td>
                </tr>
                <tr>
                  <td height="40" align="right">默认优惠折扣：</td>
                  <td height="40"><input class="login_input" name="discountValue" type="text" id="discountValue" size="30" value="${agent.discountValue}" maxlength="3" onkeyup="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^ +| +$/g,'')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
                </tr>
                <tr>
                  <td align="right">&nbsp;</td>
                  <td height="80" colspan="2"><input class="channel_btn" type="submit" name="button2" id="button2" value="保存" /></td>
                </tr>
              </table>
		    </div>
            </div>
		  </div>
		  <div class="cl">&nbsp;</div>
	</div>
    </form>
        <!-- END Three Column Content -->
		<!-- Two Column Content -->
    <!-- END Two Column Content -->
  </div>
<!-- END Main -->
	<!-- Footer -->
	
	<!-- END Footer -->

</div>
<div id="footer">
	<div class="footer">
	  <div class="left">联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客 京ICP898893233</div>  <div class="right">©天佑网版权所有</div>/</div>
</div>
</body>
</html>
 <script  type="text/javascript">
function startUpload(){
	$('#uploadify').uploadify('upload','*');
}
$(function(){
 	$("#uploadify").uploadify({
			'swf':'${pageContext.request.contextPath}/uploadify/uploadify.swf',
			'uploader':'${pageContext.request.contextPath}/form/upload/fdfs',
			'queueID':'fileQueue',
			'buttonText': '选择头像',  
			'height': 20,   
			'multi':false,
    		'width': 100, 
    		'fileTypeDesc' : '图片文件',
    		'fileTypeExts':'*.jpg;*.gif;*.png;',
			'onUploadSuccess':function(file,data,response){
	        	$('#person_info_head').attr("src",data);
				$('#headUrl').val( data.replace("${httpAddress}",""));
			},
			'onUploadError':function(file,errorCode,errorMsg,errorString){
				$.messager.alert('错误','头像上传出错 ,错误代码:'+errorCode); 
			}
		});
    	
});

function check(){

	if(document.all("name").value==""){
		alert("请输入渠道商名称！");
		document.all("name").focus(); 
		return  false;
	}

	if(document.all("addr").value==""){
		alert("请输入联系地址！");
		document.all("addr").focus(); 
		return  false;
	}

	if(document.all("phone").value==""){
		alert("请输入联系电话！");
		document.all("phone").focus(); 
		return  false;
	}

	var pattern =/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	var fax = document.getElementById("fax").value;
	if(fax.length>0){
		if(!pattern.test(fax)){
		alert("请输入正确的传真号码！");
		document.getElementById("fax").focus();
		return false;
	}
	}

	/*var re= /^[0-9][0-9]{5}$/
	var zip = document.getElementById("zip").value;
	if(zip.length>0){
		if(!re.test(zip)){
		alert("请输入正确的邮编");
		document.getElementById("zip").focus();
		return false;
		}
	}*/

	if(document.all("email").value==""){
		alert("请输入邮箱！");
		document.all("email").focus(); 
		return  false;
	}

	var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	if (!emailReg.test(document.all("email").value)){
		alert("电子邮箱地址输入错误，请重新输入！");
		document.all("email").focus(); 
		return false;
	}
}

</script>