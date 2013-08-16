<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>套餐页</title>
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

function setTab(name,cursel,n){
	for(i=1;i<=n;i++){
	var menu=document.getElementById(name+i);
	var con=document.getElementById("con_"+name+"_"+i);
	menu.className=i==cursel?"hover":"";
	con.style.display=i==cursel?"block":"none";
	}
	}
</script>
</head>

<body>

<div class="cloud"></div>
<div class="body">
  <div class="head">
	<img class="logo left" src="../images/search_03.png" />
	<div class="top">
      <div class="fav2 right"><a href="#">加入收藏</a> | <a href="#">帮助中心</a></div>
	  <div class="search2">
        <form id="form1" name="form1" method="post" action="">
          <div class="shuru left">
            <input onclick="hide('HMF-1')" type="text" value="新闻" id="am" class="am" />
            <div id="HMF-1" style="display: none " class="bm"> <span id="a1" onclick="pick('新闻')" onmouseover="bgcolor('a1')" onmouseout="nocolor('a1')" class="cur">新闻</span> <span id="a2" onclick="pick('文档')" onmouseover="bgcolor('a2')" onmouseout="nocolor('a2')" class="cur">文档</span> <span id="a3" onclick="pick('图片')" onmouseover="bgcolor('a3')" onmouseout="nocolor('a3')" class="cur">图片</span> </div>
            <input type="text" name="textfield" id="textfield" />
          </div>
          <input type="image" class="" src="../images/btn_04.gif" name="button" id="button" value=" " />
        </form>
      </div>
    </div>
  </div>
    
	<div class="nav2">
		<ul>
            <li class="navlist_person"><a href="/personCentermyStatus">我的主页</a></li>
       		<li class="navlist_person"><a href="/tocardInfo">账户信息</a></li>
         	<li class="navlist_person"><a href="#">我的资料</a></li>
            <li class="navlist_person"><a href="/personCenter1">个人信息</a></li>
            <li id="navlist2" class="navlist_person"><a href="#">注销</a></li>
            <li id="navlist2"><a href="#">网站首页</a></li>
		</ul>
        </div>
    <div class="search_up"></div>

    <div class="search_content_box">
  <div class="search_content">
    <div class="person_nav2">
    	
            <a class="present" href="#">服务购买信息</a>
            <a href="#">我的提醒</a>
            <a href="#">我的服务</a>
            <a href="#">我的记录</a>
        </ul>
  </div>
  
  <div class="person_left left">
    	
    	<div class="person_info">
        <ul>
        	<li id="one1" onclick="setTab('one',1,5)"  class="hover">点卡选购</li>
        	<li id="one2" onclick="setTab('one',2,5)">我的购物车</li>
            <li id="one3" onclick="setTab('one',3,5)">我的订单</li>
            <li id="one4" onclick="setTab('one',4,5)">我已购买</li>
            <li id="one5" onclick="setTab('one',5,5)">充值</li>
        </ul>
        </div>
      <div class="content_box">
<form id="shopping" name="userInfo" method="get" action="/addShoppingCard">
<input type="hidden" id="accountTag" name="accountTag" value="${hover}" />
		<br/>
        <table id="con_one_1" width="700" border="0" cellpadding="0" cellspacing="0" class="order_list hover">
          <tr>
            <td align="center" class="order_list_title">天佑卡名称</td>
            <td align="center" class="order_list_title">简介</td>
            <td align="center" class="order_list_title">单价</td>
            <td align="center" class="order_list_title">详细</td>
            <td align="center" class="order_list_title">数量</td>
            <td align="center" class="order_list_title">操作
            <input type="hidden" id="shoppingAmount" name="shoppingAmount"/>
        	<input type="hidden" id="shoppingId" name="shoppingId"/>
        	<input type="hidden" id="sds" name="sds" value="${addReturn}"/>
            </td>
          </tr>
        <c:forEach  var="CT"  items="${cardTypelist}">
			<tr>
	         <td height="35" align="center" bgcolor="#fafafa" class="order_list_info"><c:out value="${CT.title}"/></td>
	         <td height="35" align="center" class="order_list_info"><c:out value="${CT.content}"/></td>
	         <td height="35" align="center" class="order_list_info"><c:out value="${CT.price}"/></td>
	         <td height="35" align="center" class="order_list_info"><a href="/addShoppingCards/${CT.id}/javascript:1">查看详细</a></td>
	         <td height="35" align="center" class="order_list_info"><select name="amount" size="1" id="${CT.id}">
	           <option value="1" selected="selected">1</option>
	           <option value="2" >2</option>
	           <option value="3" >3</option>
	         </select>            </td>
	         <td align="center" class="order_list_info"><a href="#" onclick="gwc('${CT.id}');" >添加到购物车</a>
	         </td>
	       </tr>
 		</c:forEach>
        </table>
        <br/>
</form>
<form id="formProduceOrder" name="formProduceOrder" method="get" action="/produceOrder">
        <table id="con_one_2" style="display: none" width="700" border="0" cellpadding="0" cellspacing="0" class="order_list">
          	<tr>
            <td align="center" class="order_list_title">天佑卡名称</td>
            <td align="center" class="order_list_title">简介</td>
            <td align="center" class="order_list_title">单价</td>
            <td align="center" class="order_list_title">数量</td>
            <td align="center" class="order_list_title">操作
            </td>
          </tr>
        <c:forEach  var="PI"  items="${PaymentItemList}">
			<tr>
	         <td height="35" align="center" bgcolor="#fafafa" class="order_list_info"><c:out value="${PI.title}"/></td>
	         <td height="35" align="center" class="order_list_info"><c:out value="${PI.price}"/></td>
	         <td height="35" align="center" class="order_list_info"><c:out value="${PI.price}"/></td>
	         <td height="35" align="center" class="order_list_info"><c:out value="${PI.amount}"/></td>
	         <td align="center" class="order_list_info"><a href="#" onclick="deleteInfo('${PI.id}');" >删除</a>
	         </td>
	       </tr>
 		</c:forEach>
 		<tr class="person_info_add hover">
            <td height="40" align="right">订单备注：</td>
            <td height="40"><input type="text" name="remark" id="remark" /></td>
            <td height="40"><span class="login_tishi"></span></td>
         </tr>
         <tr>
            <td height="40" align="right">发票抬头：</td>
            <td height="40"><input type="text" name="billHead" id="billHead" /></td>
            <td height="40">
            	<input type=hidden name="payMoney" id="payMoney" value="${price}"/>
            </td>
        </tr>
 		<tr>
            <td align="right">&nbsp;</td>
            <td height="80" colspan="2"><input type="image" src="images/personal/person_14.gif" name="produceOrders" id="produceOrders" value="提交" onclick="produceOrder();"/></td>
          </tr>
        </table>
</form>
<form id="toPayMoney" name="toPayMoney" method="get" action="/payMoney">
		<table id="con_one_3" style="display:none" width="700" border="0" cellpadding="0" cellspacing="0" class="order_list">
			<tr>
            <td align="center" class="order_list_title">订单编号</td>
            <td align="center" class="order_list_title">缴费金额</td>
            <td align="center" class="order_list_title">订单备注</td>
            <td align="center" class="order_list_title">下单时间</td>
            <td align="center" class="order_list_title">缴费时间</td>
            <td align="center" class="order_list_title">缴费确认时间</td>
            <td align="center" class="order_list_title">发票状态
            	<input type="hidden" id="orderId" name="orderId" />
            </td>
          </tr>
        <c:forEach  var="PL"  items="${PaymentList}">
			<tr>
	         <td height="35" align="center" bgcolor="#fafafa" class="order_list_info"><c:out value="${PL.orderCode}"/></td>
	         <td height="35" align="center" class="order_list_info"><c:out value="${PL.payMoney}"/></td>
	         <td height="35" align="center" class="order_list_info"><c:out value="${PL.remark}"/></td>
	         <td height="35" align="center" class="order_list_info"><fmt:formatDate  value="${PL.createdAt}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	         <td height="35" align="center" class="order_list_info">
	         	<c:if test="${!empty PL.payTime}">
	         		<fmt:formatDate  value="${PL.payTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" />
	         	</c:if>
	         	<c:if test="${empty PL.payTime}">
	         		<a href="#" onclick="payMoney('${PL.id}');">去支付</a>
	         	</c:if>
	         </td>
	         <td height="35" align="center" class="order_list_info"><fmt:formatDate  value="${PL.confirmTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	         <td height="35" align="center" class="order_list_info"><fmt:formatDate  value="${PL.billStatus}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	       </tr>
 		</c:forEach>
        </table>
</form>
<form id="formPro" name="formPro" method="post" action="/formProjectInfo">
        <table id="con_one_4" style="display:none" width="700" border="0" cellpadding="0" cellspacing="0" class="order_list">
        	<tr>
            <td align="center" class="order_list_title">卡号</td>
            <td align="center" class="order_list_title">创建日期</td>
            <td align="center" class="order_list_title">激活状态</td>
            <td align="center" class="order_list_title">激活日期</td>
          </tr>
        <c:forEach  var="CL"  items="${CardList}">
			<tr>
	         <td height="35" align="center" bgcolor="#fafafa" class="order_list_info"><a href="#" onclick="chongzhi(this.value);" value="${CL.cardNo}"><c:out value="${CL.cardNo}"/></a></td>
	         <td height="35" align="center" class="order_list_info"><fmt:formatDate  value="${CL.createDate}" type="both" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	         <td height="35" align="center" class="order_list_info">
	         	<c:if test="${!empty CL.activeTime}">
	         		<c:out value="已激活"/>
	         	</c:if>
	         	<c:if test="${empty CL.activeTime}">
	         		<c:out value="未激活"/>
	         	</c:if>
	         </td>
	         <td height="35" align="center" class="order_list_info"><c:out value="${CL.activeTime}"/></td>
	       </tr>
 		</c:forEach>
        </table>
</form>
<form id="formPay" name="formPay" method="get" action="/payCard">
<input type="hidden" id="beCardNo" name="beCardNo" value="${beCardNo}" />
<input type="hidden" id="beActive" name="beActive" value="${beActive}" />
        <table id="con_one_5" style="display:none" width="700" border="0" cellpadding="0" cellspacing="0" class="order_list">
        	<tr>
	         <td align="center" height="35" bgcolor="#fafafa" >充值卡号：<input type="text" id="cardNo" name="cardNo" size=33 maxlength=32/>
	         <button type="buttion" onclick="chongzhitijiao();">充     值</button></td>
	        </tr>
	        <tr>
	         <td align="center" height="35" bgcolor="#fafafa" >
	         <div id="isCardNo" name="isCardNo"><font color="red">此卡号不存在！</font></div> <div id="isActive" name="isActive"><font color="red">此卡号已经注册不能再用！</font></div>
	         </td>
	        </tr>
        </table>
</form>
	</div>
	
	</div>
<div class="person_right right">
    	<div class="person_info_s">
        
  <div class="person_info_jifen">
            	
                	<ul>
                    	<li>
                        	<div class="person_info_jifen_shuzi">
                            	200                            </div>
                            <div class="person_info_jifen_wenzi">
                              <a href="#">优惠券</a> </div>
                        </li>
                        <li class="xuxian">
                        	<div class="person_info_jifen_shuzi">
                            	200                            </div>
                            <div class="person_info_jifen_wenzi">
                              <a href="#">优惠券</a> </div>
                        </li>
                    </ul>
          </div>
        	<div class="person_info_caozuo">
            	<span><a href="#">修改密码</a>  |  <a href="#">充值</a>  | <a href="#">个人资料</a></span><span class="zuo10 hui">完整度</span> <span class="anhong zihao14">40%</span></div>
                <div class="person_info_touxiang">
          <a href="#"><img src="../images/personal/person_03.gif" width="89" height="89" /></a> </div>
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
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/formValidator-4.1.1.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/webCard.js" type="text/javascript"></script>
</body>
</html>