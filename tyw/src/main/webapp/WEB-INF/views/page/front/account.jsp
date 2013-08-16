<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的账户</title>
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
<div class="person_nav2"> <a class="present" href="/person/accountInfo">账户信息</a><!-- <a href="order.html">我的订单</a> <a href="nopay.html">未支付订单</a> <a href="card.html">套餐选择</a> <a href="pay.html">购物车</a><a href="experience.html">我的经验</a><a href="/person/Level">我的等级</a>-->
   
</div>
<div class="person_left left">
    	<div class="order_title">我的账户<span class="order_title_en"> / my account</span> </div>
    	<form id="form2" name="form2" method="post" action="">
    	  <div class="state">
    	   <div>
            	<div class="mail_bm"><img src="../images/personal/zh_19.png" width="16" height="16" /> 我的等级：
            	<img src="../${gradeimg}" width="44" height="17" />
            	</div>
              <div class="mail_bm"> 天佑网等级划分：<img class="you10" src="../images/personal/vip_07.png" width="42" height="17" /><img class="you10"  src="../images/personal/vip_09.png" width="40" height="17" /><img class="you10"  src="../images/personal/vip_11.png" width="43" height="17" /><img src="../images/personal/vip_13.png" width="44" height="17" /></div>
            </div>
    	    
    	    <div class="clear"></div>
   	      </div>
          <div class="state">
            <div class="left">
            	<div class="mail_bm"><img src="../images/personal/zh_15.png" width="16" height="18" /> 我购买的服务：<span class="lan">${title} </span></div>
                <div class="mail_bm"><img src="../images/personal/zh_17.png" width="16" height="18" /> 套餐到期时间：<span class="lan">${endDate}</span></div>
            </div>
            <span class="card_box"><img class="card zuo10 shang10 left" src="../${img }" /></span>
            <div class="clear"></div>
   	      </div>
         
		  <div class="info_youhui_title zuo20 xia20 shang20"><strong>天佑网会员权限说明：</strong></div>
    	  <table class="zuo20 hui xia20" width="693" border="0" cellpadding="0" cellspacing="1" bgcolor="#E8E8E8">
         
            <tr>
              <td height="50" colspan="2" background="../images/bg2.gif" bgcolor="#FAFAFA"><p align="center"><strong>服务内容\会员等级</strong> </p></td>
              <td width="103" height="50" background="../images/bg2.gif" bgcolor="#FAFAFA"><p align="center"><strong>钻石VIP会员 </strong></p></td>
              <td width="109" height="50" background="../images/bg2.gif" bgcolor="#FAFAFA"><p align="center"><strong>白金VIP会员</strong> </p></td>
              <td width="108" height="50" background="../images/bg2.gif" bgcolor="#FAFAFA"><p align="center"><strong>VIP</strong><strong>会员</strong> </p></td>
              <td width="103" height="50" background="../images/bg2.gif" bgcolor="#FAFAFA"><p align="center"><strong>普通会员</strong> </p></td>
              <td width="82" height="50" background="../images/bg2.gif" bgcolor="#FAFAFA"><p align="center"><strong>备注</strong> </p></td>
            </tr>
            <tr>
              <td width="91" bgcolor="#FFFFFF"><p align="center"><strong>体验服务 </strong></p></td>
              <td width="89" bgcolor="#FFFFFF"><p align="center">受限查询 </p></td>
              <td width="103" valign="top" bgcolor="#FFFFFF"><p align="center">&nbsp;</p></td>
              <td width="109" bgcolor="#FFFFFF"></td>
              <td width="108" bgcolor="#FFFFFF"></td>
              <td width="103" bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">已开通 </p></td>
            </tr>
            <tr>
              <td width="91" rowspan="6" bgcolor="#FFFFFF"><p align="center"><strong>基础服务 </strong></p></td>
              <td width="89" bgcolor="#FFFFFF"><p align="center">知识查询 </p></td>
              <td width="103" bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="109" bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="108" bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="103" bgcolor="#FFFFFF"></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">已开通 </p></td>
            </tr>
            <tr>
              <td width="89" bgcolor="#FFFFFF"><p align="center">知识收藏 </p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="103" bgcolor="#FFFFFF"></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">已开通 </p></td>
            </tr>
            <tr>
              <td width="89" bgcolor="#FFFFFF"><p align="center">知识问答 </p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="103" bgcolor="#FFFFFF"></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">即将推出  </p></td>
            </tr>
            <tr>
              <td width="89" bgcolor="#FFFFFF"><p align="center">知识订阅 </p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="103" bgcolor="#FFFFFF"></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">即将推出  </p></td>
            </tr>
            <tr>
              <td width="89" bgcolor="#FFFFFF"><p align="center">电子期刊 </p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="103" bgcolor="#FFFFFF"></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">即将推出 </p></td>
            </tr>
            <tr>
              <td width="89" bgcolor="#FFFFFF"><p align="center">积分换礼 </p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="103" bgcolor="#FFFFFF"></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">即将推出  </p> </td>
            </tr>
            <tr>
              <td width="91" rowspan="5" bgcolor="#FFFFFF"><p align="center"><strong>增值体验 </strong></p></td>
              <td width="89" bgcolor="#FFFFFF"><p align="center">知识下载 </p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="108" bgcolor="#FFFFFF"></td>
              <td width="103" bgcolor="#FFFFFF"></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">即将推出  </p></td>
            </tr>
            <tr>
              <td width="89" bgcolor="#FFFFFF"><p align="center">视频教学 </p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="108" bgcolor="#FFFFFF"></td>
              <td width="103" bgcolor="#FFFFFF"><p align="center">　 </p></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">即将推出  </p></td>
            </tr>
            <tr>
              <td width="89" bgcolor="#FFFFFF"><p align="center">电话热线 </p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="108" bgcolor="#FFFFFF"></td>
              <td width="103" bgcolor="#FFFFFF"><p align="center">　 </p></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">即将推出 </p></td>
            </tr>
            <tr>
              <td width="89" bgcolor="#FFFFFF"><p align="center">VIP专场培训 </p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="108" bgcolor="#FFFFFF"></td>
              <td width="103" bgcolor="#FFFFFF"><p align="center">　 </p></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">即将推出 </p></td>
            </tr>
            <tr>
              <td width="89" bgcolor="#FFFFFF"><p align="center">线下活动 </p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="108" bgcolor="#FFFFFF"></td>
              <td width="103" bgcolor="#FFFFFF"><p align="center">　 </p></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">即将推出 </p></td>
            </tr>
            <tr>
              <td width="91" rowspan="2" bgcolor="#FFFFFF"><p align="center"><strong>尊贵独享 </strong></p></td>
              <td width="89" bgcolor="#FFFFFF"><p align="center">发布知识 </p></td>
              <td width="103" bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="109" bgcolor="#FFFFFF"></td>
              <td width="108" bgcolor="#FFFFFF"><p align="center">　 </p></td>
              <td width="103" bgcolor="#FFFFFF"><p align="center">　 </p></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">即将推出  </p></td>
            </tr>
            <tr>
              <td width="89" bgcolor="#FFFFFF"><p align="center">个人推荐展示 </p></td>
              <td width="103" bgcolor="#FFFFFF"><p align="center"><img src="../images/personal/zh_03.png" width="15" height="14" /></p></td>
              <td width="109" bgcolor="#FFFFFF"></td>
              <td width="108" bgcolor="#FFFFFF"><p align="center">　 </p></td>
              <td width="103" bgcolor="#FFFFFF"><p align="center">　 </p></td>
              <td width="82" height="40" bgcolor="#FFFFFF"><p align="center">即将推出  </p></td>
            </tr>
          </table>
    	</form>
        </div>
<div class="person_right right">
<jsp:include page="right.jsp"></jsp:include>
<!-- div class="info_youhui">
       	  <div class="info_youhui_title">继续完善可以获得以下优惠或特权：</div>
            <ul>
           	  <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
            </ul>
        </div -->
        <!-- div class="new_youhui">
        	<div class="new_youhui_title">最新优惠<span class="new_youhui_title_en"> / latest preferential</span> </div>
            <ul>
            	<li><a href="#"><img src="../images/personal/person_11.gif" width="53" height="64" /></a></li>
              <li><a href="#"><img src="../images/personal/person_11.gif" width="53" height="64" /></a></li>
                <li><a href="#"><img src="../images/personal/person_11.gif" width="53" height="64" /></a></li>
            </ul>
        </div -->
    </div>
    <div class="clear"></div>
    </div>
  </div>
  <div class="search_down"></div>
    <div class="clear"></div>
    <div class="foot">
联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客| <a target="_blank" href="/map.jsp">网站地图</a>  天佑网版权所有　©1997-2013　粤ICP备20090191号</div>
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
