<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>渠道商主页</title>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../css/chan.css" rel="stylesheet" type="text/css" />
<link href="../css/channel.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="page" class="shell">
	<!-- Logo + Search + Navigation -->
	<div id="top">
	  <h1 id="logo"><a href="#">MONDAYS</a></h1>
  <form action="" method="post" id="search">
			<div class="field-holder">
				<input type="text" class="field" value="搜索" onfocus="if(this.value=='搜索')this.value=''" onblur="if(this.value=='')this.value='搜索'" />
			</div>
<input type="submit" class="button" value="Search" />
			<div class="cl">&nbsp;</div>
		</form>
	  <div class="cl">&nbsp;</div>
		<div id="navigation">
			<ul>
		      <li><a href="/channel/web/home"><span>主页</span></a></li>
		      <li><a href="/channel/web/info"><span>我的信息</span></a></li>
              <li><a href="/channel/web/account"><span>我的账户</span></a></li>
		      <li><a href="/channel/web/channel"><span>优惠通道</span></a></li>
		      <li><a href="/channel/web/id"><span>企业账号</span></a></li>
		      <li><a class="active" href="/channel/web/news"><span>我的动态</span></a></li>
              <li style=" float:right"><a href="/logout.do"><span>注销</span></a></li>
              <li style=" float:right"><a href="/index.jsp"><span>网站首页</span></a></li>
		  </ul>
		</div>	
  </div>
	<div class="channel_positon">我现在的位置：首页 &gt; 渠道商主页 &gt; 我的动态 &gt; 发布动态</div>
	<!-- END Logo + Search + Navigation -->
  <!-- Header -->
<div class="channel_shot">
    
  </div>
  <!-- END Header -->
  <!-- Main -->
<div id="main">
		<!-- Three Column Content -->
	<!-- END Three Column Content -->
	<!-- Two Column Content -->
<div class="cols two-cols">
			<div class="cl">&nbsp;</div>
			<form id="form1" name="form1" method="post" action="">
			  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="person_info_add">

                

                
                <tr class="person_info_add">
                  <td height="40" align="right" nowrap="nowrap">新闻标题：</td>
                  <td><input class="login_input" name="textfield" type="text" id="textfield12" size="60" />
                      <font color="#ff0000">*</font> </td>
                </tr>

                <tr class="person_info_add">
                  <td height="40" align="right" nowrap="nowrap">新闻内容：</td>
                  <td><textarea class="login_input2" name="textarea2" id="textarea3" cols="80" rows="9"></textarea>
				  <font color="#ff0000">*</font>
                      <br />
                    请限制在5000字以内 </td>
                </tr>
				<!--
                <tr class="person_info_add">
                  <td height="40" align="right" nowrap="nowrap">内容上传：</td>
                  <td><input class="login_input" 
            onfocus="javascript:getFocus('content');" size="38" type="file" 
            name="content2" />
                    &nbsp;<font size="2" 
            color="#ff0000">文件必须为WinRAR、WinZIP文件或JPG图片</font> </td>
                </tr>
                <tr class="person_info_add">
                  <td height="40" align="right" nowrap="nowrap">图片上传：</td>
                  <td><input class="login_input" 
            onfocus="javascript:getFocus('miniature');" size="38" type="file" 
            name="miniature2" />
                    &nbsp;<font size="2" color="#ff0000">上传优质图片的稿件，编辑会优先审阅</font> </td>
                </tr>
				
                <tr class="person_info_add">
                  <td height="40" align="right" nowrap="nowrap">&nbsp;</td>
                  <td><input value="1" checked="checked" type="checkbox" 
            name="move_to_bbs2" />
                    如果内容不适合资料库收录，允许转发到论坛</td>
                </tr>
                <tr class="person_info_add">
                  <td height="40" align="right" nowrap="nowrap">&nbsp;</td>
                  <td><input value="1" checked="checked" type="checkbox" 
        name="disp_uploader2" />
                    公开投稿人</td>
                </tr>
				-->
                <tr class="person_info_add">
                  <td height="40" align="right" nowrap="nowrap">&nbsp;</td>
                  <td height="80"><input type="submit" name="button" id="button" value="提交" />
                  <input type="reset" name="button2" id="button2" value="重置" /></td>
                </tr>
              </table>
      </form>
	  <div class="cl">&nbsp;</div>
		</div>
		<!-- END Two Column Content -->
	</div>
<!-- END Main -->
	<!-- Footer -->
	<div id="footer">
		<p class="right">&copy; 2010 - CompanyName</p>
	  联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20</div>
	<!-- END Footer -->
	<br />
</div>
</body>
</html>