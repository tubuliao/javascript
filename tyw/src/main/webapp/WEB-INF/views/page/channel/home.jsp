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
		      <li><a href="/channel/web/home" class="active"><span>主页</span></a></li>
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
	<!-- END Logo + Search + Navigation -->
  <!-- Header -->
  <div id="page" class="shell">
  <div class="channel_positon">
  <div class="left"><img src="/images/channel/channel_07.gif" /> 首页 <span class="hui"> > </span> 个人信息</div>
  <div class="right">快乐分享：
  <!-- Baidu Button BEGIN -->
<div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare" style="float:right; margin-top:8px;">
<a class="bds_qzone"></a>
<a class="bds_tsina"></a>
<a class="bds_tqq"></a>
<a class="bds_renren"></a>
<a class="bds_t163"></a>
</div>
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
<!-- END Header -->
  <!-- Main -->
<div id="main">
		<!-- Three Column Content -->
		<div class="cols three-cols">
			<div class="cl">&nbsp;</div>
			<div class="col">
				<h2>我的账户</h2>
			  <div class="channel_xs">交费金额总数：${totalMoney}</div>
				<p><a href="#" class="more">更多&gt;&gt;</a></p>
			</div>
		  <div class="cl">&nbsp;</div>
		</div>
		<!-- END Three Column Content -->
		<!-- Two Column Content -->
		<!--div class="cols two-cols">
          <h2>我的动态</h2>
		  <div class="col">
            <div class="channel_news">
              <ul>
                <li><span class="left"><a href="#">人民日报连续4天批苹果：无与伦比地耀武扬威</a></span><span class="right">[2012-11-22]</span></li>
                <li><span class="left"><a href="#">人民日报连续4天批苹果：无与伦比地耀武扬威</a></span><span class="right">[2012-11-22]</span></li>
                <li><span class="left"><a href="#">人民日报连续4天批苹果：无与伦比地耀武扬威</a></span><span class="right">[2012-11-22]</span></li>
                <li><span class="left"><a href="#">人民日报连续4天批苹果：无与伦比地耀武扬威</a></span><span class="right">[2012-11-22]</span></li>
                <li><span class="left"><a href="#">人民日报连续4天批苹果：无与伦比地耀武扬威</a></span><span class="right">[2012-11-22]</span></li>
                <li><span class="left"><a href="#">人民日报连续4天批苹果：无与伦比地耀武扬威</a></span><span class="right">[2012-11-22]</span></li>
                <li><span class="left"><a href="#">人民日报连续4天批苹果：无与伦比地耀武扬威</a></span><span class="right">[2012-11-22]</span></li>
                <li><span class="left"><a href="#">人民日报连续4天批苹果：无与伦比地耀武扬威</a></span><span class="right">[2012-11-22]</span></li>
                <li><span class="left"><a href="#">人民日报连续4天批苹果：无与伦比地耀武扬威</a></span><span class="right">[2012-11-22]</span></li>
              </ul>
              <div class="clear"></div>
            </div>
		    <p><a href="#" class="more">更多&gt;&gt;</a></p>
	      </div>
          <div class="cl">&nbsp;</div>
    </div-->
		<!-- END Two Column Content -->
	</div>
	<!-- END Main -->
	<!-- Footer -->

</div>
<div id="footer">
	<div class="footer">
	  <div class="left">联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客 京ICP898893233</div>  <div class="right">©天佑网版权所有</div>/</div>
      </div>
</body>
</html>