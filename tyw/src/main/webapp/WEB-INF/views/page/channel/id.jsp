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

<link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
 <script src="${pageContext.request.contextPath}/uploadify/jquery.uploadify-3.1.js" type="text/javascript"></script>
 <link href="${pageContext.request.contextPath}/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
</head>
<body>

	<!-- Logo + Search + Navigation -->
	<div class="top_box">
      <div id="top">
        <div class="com_top">
          <div class="left">你好，欢迎光临天佑网！</div>
          <div class="right"><img src="/images/conmpany/share_11.gif" width="16" height="16" /> <a href="#">新浪微博</a> <img src="/images/conmpany/share_13.gif" width="16" height="16" /> <a href="#">腾讯微博</a> | <a href="#">加入收藏</a> | <a href="#">帮助中心</a></div>
        </div>
        <div class="clear"></div>
        <h1 id="logo"><a href="#">MONDAYS</a></h1>
        <form action="" method="post" id="search">
          <input type="submit" class="button right" value="Search" />
          <div class="field-holder">
            <input type="text" class="field" value="搜索" onfocus="if(this.value=='搜索')this.value=''" onblur="if(this.value=='')this.value='搜索'" />
          </div>
          <div class="cl">&nbsp;</div>
        </form>
        <div class="cl">&nbsp;</div>
        <div id="navigation">
          <ul>
            <li><a href="/channel/web/home"><span>主页</span></a></li>
            <li><a href="/channel/web/info"><span>我的信息</span></a></li>
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
<div class="channel_shot"> <img class="channel_head left you10" src="${httpAddress}${user.headUrl}" />
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
        <form id="firmInfo" name="firmInfo" method="post" action="/saveFirmInfo" onsubmit="return check();">
        <div class="cols two-cols">
        <h2>企业账号</h2>
		  <div class="col">
              
			  <div class="channel_news">
              	<div class=""><a class="channel_btn2 left" href="#" onclick="add.style.display='block';" >新增企业</a><a class="channel_btn2 left zuo10" href="javascript:void(0);" onclick="importData();">导入</a><a class="channel_btn2 left zuo10" href="javascript:void(0);" onclick="">导出</a><a class="channel_btn2 left zuo10" href="/upload/firm.xlsx">模板下载</a></div>
                <div class="clear"></div>
                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="channel_table">
                  <tr>
                    <td width="7%" height="40" align="center" bgcolor="#F7F7F7"><strong>全选</strong></td>
                    <td width="20%" height="40" align="center" bgcolor="#F7F7F7"><strong>账号</strong></td>
                    <td width="15%" height="40" align="center" bgcolor="#F7F7F7"><strong>企业名称</strong></td>
                    <td width="20%" align="center" bgcolor="#F7F7F7"><strong>联系人</strong></td>
                    <td width="16%" align="center" bgcolor="#F7F7F7"><strong>邮箱</strong></td>
                    <td width="17%" align="center" bgcolor="#F7F7F7"><strong>电话</strong></td>
                    <td width="16%" align="center" bgcolor="#F7F7F7"><strong>功能</strong></td>
                  </tr>
                  <c:forEach items="${list}" var="l">
                    <tr>
                      <td height="40" align="center"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                      <td height="40" align="center">${l.user.username}</td>
                      <td height="40" align="center">${l.name}</td>
                      <td align="center">${l.linkman}</td>
                      <td align="center">${l.email}</td>
                      <td align="center">${l.phone}</td>
                      <td align="center"><a href="javascript:void(0);" onclick="deleteFirm('${l.id}');">删除</a></td>
                    </tr>
                  </c:forEach>
                </table>
                <div class="clear"></div>
            </div>
		  </div>
		  <div class="cl">&nbsp;</div>
		</div>
          <div id="add">
<div class="close">
   	<a href="#" onclick="add.style.display='none';">关闭</a></div>
    
  <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
    <tr class="channel_table">
      <td width="21%" height="40" align="center">账号</td>
	  <input type="hidden" name="firm.agentId" value="${user.id}">
      <td width="79%" height="40"><input type="text" name="username" id="username" /></td>
      </tr>
    <tr>
      <td height="30" align="center">企业名称</td>
      <td height="30"><input type="text" name="firm.name" id="name" /></td>
      </tr>
    <tr>
      <td height="30" align="center">联系人</td>
      <td height="30"><input type="text" name="firm.linkman" id="linkman" /></td>
      </tr>
    <tr>
      <td height="30" align="center">邮箱</td>
      <td height="30"><input type="text" name="firm.email" id="email" /></td>
      </tr>
    <tr>
      <td height="30" align="center">电话</td>
      <td height="30"><input type="text" name="firm.phone" id="phone" maxlength ="11"/></td>
      </tr>
    <tr>
      <td height="30" colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="8%">&nbsp;</td>
          <td width="92%"><input class="channel_btn2 left" type="submit" name="button3" id="button3" value="提交" />
              <input class="channel_btn2 left zuo10" type="reset" name="button5" id="button5" value="重置" /></td>
        </tr>
      </table></td>
      </tr>
  </table>
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
<div id="dlg-xls" class="easyui-dialog" style="width:400px;height:200px;padding:10px 20px"
			closed="true" buttons="#dlg-xls-buttons" data-options="modal:true">
		<div class="ftitle">选择要导入的Excel文件</div>
			<div class="fitem">
				<div id="fileQueue"></div>
				<input type="file" name="excelUploadify" id="excelUploadify"/>
			</div>
	</div>
	<div id="dlg-xls-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg-xls').dialog('close')">取消</a>
	</div>
</body>
</html>
<script type="text/javascript">
function deleteFirm(firmId){
			$.messager.confirm('提示','确定要删除?',function(r){
			if (r){
				$.post('/firm/delete/'+firmId,{id:firmId},function(result){
					if (result.success){
						location.href='/channel/web/id';
					} else {
						$.messager.show({	// 显示错误信息
							title: '错误',
							msg: result.msg
						});
					}
				},'json');
			}
		});
		}

function importData(){
	$('#dlg-xls').dialog('open').dialog('setTitle','导入来源');
}


$(function(){
 	$("#excelUploadify").uploadify({
			'swf':'${pageContext.request.contextPath}/uploadify/uploadify.swf',
			'uploader':'${pageContext.request.contextPath}/firm/excelFirmUpload',
			'queueID':'fileQueue',
			'buttonText': '选择文件',  
			'height': 20,   
			'multi':false,
    		'width': 100, 
    		'fileTypeDesc' : 'Excel文件',
    		'fileTypeExts':'*.xlsx;*.xls',
			'onUploadSuccess':function(file,data,response){
    				if('success' == data) {
    					$.messager.alert('提示', '文件上传成功！') ;
    					location.href='/channel/web/id';
    				} else {
    					$.messager.alert('提示', '文件上传失败！') ;	 
    				}
    			},
    			'onUploadError':function(file,errorCode,errorMsg,errorString){
    				$.messager.alert('错误','文件上传出错 ,错误代码:'+errorCode); 
    			}
		});
    	
});

function check(){
var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
if(document.all("username").value==""){
		alert("请输入账号！");
		document.all("username").focus(); 
		return  false;
	}
	if(document.all("name").value==""){
		alert("请输入企业名称！");
		document.all("name").focus(); 
		return  false;
	}
	if(document.all("linkman").value==""){
		alert("请输入企业联系人！");
		document.all("linkman").focus(); 
		return  false;
	}
	if(document.all("email").value==""){
		alert("请输入邮箱！");
		document.all("email").focus(); 
		return  false;
	}
	if (!emailReg.test(document.all("email").value)){
		alert("电子邮箱地址输入错误，请重新输入！");
		document.all("email").focus(); 
		return false;
	}
	if(document.all("phone").value==""){
		alert("请输入电话号码！");
		document.all("phone").focus(); 
		return  false;
	}
	if( document.all("phone").value.length!=11){
	    alert("手机号码必须为11位");
	    document.all("phone").focus(); 
	   	return false;
	}
	var isMobile = /^(?:13\d|15[0-9]|18[6|7|8|9])-?\d{5}(\d{3}|\*{3})$/;
	if(!isMobile.test(document.all("phone").value)){
		alert("您输入的手机号码不合法，请输入正确的手机号码");    
        document.all("phone").focus(); 
        return false;   
	}
	alert("新增企业成功!");
}

</script>