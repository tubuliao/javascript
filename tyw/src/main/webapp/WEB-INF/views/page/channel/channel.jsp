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
<script src="/js/Calendar4.js" type="text/javascript"></script>



<link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/default/easyui.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/icon.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/easyui-1.3.2/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js"></script>
</head>
<body>
<script type="text/javascript">
function deleteDiscount(discountId){
			
			$.messager.confirm('提示','确定要删除?',function(r){
			if (r){
				$.post('/discount/delete/'+discountId,{id:discountId},function(result){
					if (result.success){
						location.href='/channel/web/channel';
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

function query(discountId){
		$.post('/discount/view/'+discountId,{id:discountId},function(result){
					$("#discountName").val(result.discountName);
					$("#discountCode").val(result.discountCode);
					$("#discountValue").val(result.discountValue);
					$("#validStartDate").val(result.validStartDate);
					$("#validEndDate").val(result.validEndDate);
					$("#sid").val(result.id);
				},'json');
}

$(document).ready(function(){      
	   $("#cid").click(function(){   
		   $("#discountName").val("");
			$("#discountCode").val("");
			$("#discountValue").val("");
			$("#validStartDate").val("");
			$("#validEndDate").val("");
			$("#sid").val("");
	    });   
	}); 


function check(){

	if(document.all("discountName").value==""){
		alert("请输入通道名称！");
		document.all("discountName").focus(); 
		return  false;
	}
	if(document.all("discountCode").value==""){
		alert("请输入优惠编号！");
		document.all("discountCode").focus(); 
		return  false;
	}
	if(document.all("discountValue").value==""){
		alert("请输入优惠折扣！");
		document.all("discountValue").focus(); 
		return  false;
	}
	if(document.all("strValidStartDate").value==""){
		alert("请选择有效期开始日期！");
		document.all("strValidStartDate").focus(); 
		return  false;
	}
	if(document.all("strValidEndDate").value==""){
		alert("请选择有效期结束日期！");
		document.all("strValidEndDate").focus(); 
		return  false;
	}
	alert("保存优惠通道成功!");
}

</script>

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
        <form id="discountInfo" name="discountInfo" method="post" action="/addDiscount" onsubmit="return check();">
        <div class="cols two-cols">
        <h2>优惠通道<a class="channel_btn2 right" id="cid" href="#" onclick="add.style.display='block';" >新增通道</a></h2>
		  <div class="col">
              
			  <div class="channel_news">
			    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="channel_table">
                  <tr>
                    <td width="8%" height="40" align="center" background="/images/bg/bg2.gif" bgcolor="#F7F7F7"><strong>全选</strong></td>
                    <td width="15%" height="40" align="center" background="/images/bg/bg2.gif" bgcolor="#F7F7F7"><strong>通道名称</strong></td>
                    <td width="15%" height="40" align="center" background="/images/bg/bg2.gif" bgcolor="#F7F7F7"><strong>优惠编号</strong></td>
                    <td width="15%" height="40" align="center" background="/images/bg/bg2.gif" bgcolor="#F7F7F7"><strong>优惠折扣</strong></td>
                    <td width="21%" align="center" background="/images/bg/bg2.gif" bgcolor="#F7F7F7"><strong>有效时间</strong></td>
                    <td width="16%" align="center" background="/images/bg/bg2.gif" bgcolor="#F7F7F7"><strong>用户数</strong></td>
                    <td width="17%" align="center" background="/images/bg/bg2.gif" bgcolor="#F7F7F7"><strong>功能</strong></td>
                  </tr>
                  <c:forEach items="${list}" var="l">
                    <tr>
                      <td height="40" align="center"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                      <td height="40" align="center">${l.discountName}</td>
                      <td height="40" align="center">${l.discountCode}</td>
                      <td height="40" align="center">${l.discountValue}折</td>
                      <td align="center">${l.validStartDate}——${l.validEndDate}</td>
                      <td align="center">89889</td>
                      <td align="center"><a href="#" onclick="add.style.display='block';query('${l.id}');">编辑</a> <a href="javascript:void(0);" onclick="deleteDiscount('${l.id}');">删除</a></td>
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
      <td width="21%" height="40" align="center">通道名称</td>
	  <input type="hidden" id="sid" name="id" value="">
      <td width="79%" height="40"><input type="text" name="discountName" id="discountName"/></td>
      </tr>
    <tr>
	<tr class="channel_table">
      <td width="21%" height="40" align="center">优惠编号</td>
      <td width="79%" height="40"><input type="text" name="discountCode" id="discountCode"/></td>
      </tr>
    <tr>
      <td height="30" align="center">优惠折扣</td>
      <td height="30"><input type="text" name="discountValue" id="discountValue" maxlength="3"
	  onkeyup="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^ +| +$/g,'')" onafterpaste="if(isNaN(value))execCommand('undo')"
	  /></td>
      </tr>
    <tr>
      <td height="30" align="center">有效期</td>
      <td height="30"><input type="text" class="Wdate" name="strValidStartDate" id="validStartDate" onclick="WdatePicker()" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'validEndDate\')}'})"/>-<input type="text" class="Wdate" name="strValidEndDate" id="validEndDate" onclick="WdatePicker()" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'validStartDate\')}'})"/></td>
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
</body>
</html>