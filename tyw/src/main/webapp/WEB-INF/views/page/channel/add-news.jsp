<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>渠道商主页</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/css/chan.css" rel="stylesheet" type="text/css" />
<link href="/css/channel.css" rel="stylesheet" type="text/css" />
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
	<!-- END Logo + Search + Navigation -->
  <!-- Header -->
  <div class="channel_shot"> <img class="channel_head left you10" src="${httpAddress}${user.headUrl}" />
      <div class=" left">
        <h3>${agent.name}</h3>
      </div>
      <div class="clear"></div>
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
			  <div class="cols two-cols">
                <h2>添加动态</h2>
			    <div class="col">
                  <div class="channel_news">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="person_info_add">
                      <!-- tr class="person_info_add">
                        <td width="81" height="40" align="right" nowrap="nowrap">编辑：</td>
                        <td width="639"><input class="login_input" name="textfield2" type="text" id="textfield" size="30" /></td>
                      </tr-->
                      <tr class="person_info_add">
                        <td height="40" align="right" nowrap="nowrap">新闻标题：</td>
                        <td><input class="login_input" name="textfield2" type="text" id="textfield2" size="60" />
                            <font color="#ff0000">*</font> </td>
                      </tr>
                      <tr class="person_info_add">
                        <td height="40" align="right" nowrap="nowrap">新闻内容：</td>
                        <td><textarea class="login_input2" name="textarea" id="textarea" cols="80" rows="9"></textarea>
                          <font color="#ff0000">*</font>  <br />
                          请限制在5000字以内 </td>
                      </tr>
                      <tr class="person_info_add">
                        <td height="40" align="right" nowrap="nowrap">上传文件：</td>
                        <td><input class="login_input" 
            onfocus="javascript:getFocus('content');" size="38" type="file" 
            name="content" />
                          &nbsp;<!--font size="2" 
            color="#ff0000">文件必须为WinRAR、WinZIP文件或JPG图片</font> </td>
                      </tr>
                      <tr class="person_info_add">
                        <td height="40" align="right" nowrap="nowrap">图片上传：</td>
                        <td><input class="login_input" 
            onfocus="javascript:getFocus('miniature');" size="38" type="file" 
            name="miniature" />
                          &nbsp;<font size="2" color="#ff0000">上传优质图片的稿件，编辑会优先审阅</font> </td>
                      </tr>
                      <tr class="person_info_add">
                        <td height="40" align="right" nowrap="nowrap">&nbsp;</td>
                        <td><input value="1" checked="checked" type="checkbox" 
            name="move_to_bbs" />
                          如果内容不适合资料库收录，允许转发到论坛</td>
                      </tr>
                      <tr class="person_info_add">
                        <td height="40" align="right" nowrap="nowrap">&nbsp;</td>
                        <td><input value="1" checked="checked" type="checkbox" 
        name="disp_uploader" />
                          公开投稿人</td>
                      </tr-->
                      <tr class="person_info_add">
                        <td height="40" align="right" nowrap="nowrap">&nbsp;</td>
                        <td height="80"><span class="person_info_add hover">
                          <input type="button" class="order_btn" name="button2" id="button2" value="填好了，保存" />
                        </span></td>
                      </tr>
                    </table>
                    <div class="clear"></div>
                  </div>
		        </div>
              </div>
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