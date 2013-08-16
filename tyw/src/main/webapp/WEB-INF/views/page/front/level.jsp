<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的等级</title>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../css/person.css" rel="stylesheet" type="text/css" />

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
</head>

<body>


  <div class="search_up"></div>

    <div class="search_content_box">
  <div class="search_content">
<div class="person_nav2"> <a href="/person/accountInfo">我的账户</a> <!--<a href="order.html">我的订单</a> <a href="nopay.html">未支付订单</a> <a href="card.html">套餐选择</a> <a href="pay.html">购物车</a><a href="experience.html">我的经验</a>--><a class="present" href="/person/Level">我的等级</a>
   
</div>
<div class="person_left left">
    	<div class="order_title">我的等级<span class="order_title_en"> / my level</span> </div>
    	<form id="form2" name="form2" method="post" action="">
    	  <div class="state">
    	    <div>
              <div class="wzd left"><span>您的账户安全等级：</span><font size="4" class="anhong"> 低 </font></div>
    	      <div class="progress left">
                <div class="progress_grows"></div>
  	        </div>
    	      <p class="clear">您的帐号未设置任何密码保护措施，当您忘记密码时，将无法找回密码！</p>
  	      </div>
    	    <div class="card_box">
            	<div class="mail_bm"><span><img src="../images/personal/zh_10.png" width="16" height="13" /></span> 您已经设置好了保密邮箱： <span class="mail_address">l*****e@gmail.com</span></font>&nbsp;<font size="3"><a href="#"></a></font>   <font color="#a6a6a6" size="2">设置保密邮箱，增加您的账户安全性</font></div>
                <div class="mail_bm"><img src="../images/personal/zh_13.png" width="16" height="19" /> 您尚未设置保密手机：<span class="lan"><a href="#">修改保密手机</a></span>    <font color="#a6a6a6" size="2">设置保密邮箱，增加您的账户安全性</font></div>
            </div>
    	    <div class="clear"></div>
   	      </div>
          <div class="state">
            <div>
            	<div class="mail_bm"><img src="../images/personal/zh_19.png" width="16" height="16" /> 我的等级：<img src="../images/personal/vip_07.png" width="42" height="17" /><font color="#a6a6a6" size="2"> 距离下一等级还剩 <span class="anhong">400</span> 天<a href="#"> 如何升级等级?</a></font><font color="#a6a6a6" size="2"> &nbsp;等不及了</font> <img class="stateimg2" src="../images/personal/gmsj.gif" /></div>
              <div class="mail_bm"> 天佑网等级划分：<img class="you10" src="../images/personal/vip_07.png" width="42" height="17" /><img class="you10"  src="../images/personal/vip_09.png" width="40" height="17" /><img class="you10"  src="../images/personal/vip_11.png" width="43" height="17" /><img src="../images/personal/vip_13.png" width="44" height="17" /></div>
            </div>
            <div class="clear"></div>
   	      </div>
          <div class="state">
            <div>
            	<div class="mail_bm lan"><span class="lan">2013-03-27 得分： 1活跃天</span> </div>
                <div class="mail_bm">
                	<ul>
                    	<li><span class="left">在线时长2小时以上</span>       <span class="right">获得 1 活跃天 </span></li>
                    	<li><span class="left">在线时长2小时以上</span>       <span class="right">获得 1 活跃天 </span></li>
                        <li><span class="left">在线时长2小时以上</span>       <span class="right">获得 1 活跃天 </span></li>
                        <li><span class="left">在线时长2小时以上</span>       <span class="right">获得 1 活跃天 </span></li>
                    </ul>
                </div>
            </div>
            <div class="clear"></div>
   	      </div>
    	</form>
        </div>
<div class="person_right right">
<jsp:include page="right.jsp" ></jsp:include>
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
            	<li><a href="#"><img src="../images/personal/person_11.gif" width="53" height="64" /></a></li>
              <li><a href="#"><img src="../images/personal/person_11.gif" width="53" height="64" /></a></li>
                <li><a href="#"><img src="../images/personal/person_11.gif" width="53" height="64" /></a></li>
            </ul>
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
	<a href="#top"><img title="返回顶部" class="shang20" src="../images/btnfd_13.gif" /></a>
</div>
</div></div>

</body>
</html>
