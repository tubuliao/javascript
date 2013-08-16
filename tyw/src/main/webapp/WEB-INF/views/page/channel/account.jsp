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
<link href="/css/pagination.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js"></script>
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.pagination.js" type="text/javascript" />
<script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-ui-1.10.2/themes/base/jquery-ui.css" />
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
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
            <li><a href="/channel/web/home"><span>主页</span></a></li>
            <li><a href="/channel/web/info"><span>我的信息</span></a></li>
            <li><a href="/channel/web/account" class="active"><span>我的账户</span></a></li>
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
  <div class="cols two-cols">
    <h2>我的账户</h2>
    <div class="col">
      <div class="channel_news">
        <form>
          <table class="tab" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
              
      			<td><strong>创建日期：
   			      <input type="text" class="Wdate id_input" name="createDate" id="createDate" onclick="WdatePicker()" />
      			</strong></td>
      			<td><strong>交费日期：
   			      <input type="text" class="Wdate id_input" name="activateDate" id="activateDate" onclick="WdatePicker()" />
      			</strong></td>
      			<td><strong>批次号：
   			      <input class="id_input" type="text" name="batchCode" id="batchCode"/>
      			</strong></td>
    <td><strong>
      				状态：
      				    <select class="id_input" name="licenseStatus" id="licenseStatus">
      				      <option value="">全部</option>
      				      <option value="1">未付费</option>
      				      <option value="2">已付费</option>
			                </select>
      			</strong></td>
      			<td><strong>项目组名称：
   			      <input class="id_input" type="text" name="proName" id="proName"/>
      			</strong></td>
      			<td><input type="button" class="chaxun" onclick="javascript:query();javascript:getLicenseDataList(0)" name="查询" value="点击查询"/></td>
            </tr>
          </table>
          <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#EEEEEE" class="channel_table">
            <tr>
              <td width="15%" height="40" align="center" background="../images/bg/bg2.gif" bgcolor="#FFFFFF"><strong>license</strong></td>
              <td width="15%" height="40" align="center" background="../images/bg/bg2.gif" bgcolor="#FFFFFF"><strong>批次号</strong></td>
              <td width="15%" height="40" align="center" background="../images/bg/bg2.gif" bgcolor="#FFFFFF"><strong>批次号创建时间</strong></td>
              <td width="20%" align="center" background="../images/bg/bg2.gif" bgcolor="#FFFFFF"><strong>项目组名称</strong></td>
              <td width="8%" align="center" background="../images/bg/bg2.gif" bgcolor="#FFFFFF"><strong>类型</strong></td>
              <td width="15%" align="center" background="../images/bg/bg2.gif" bgcolor="#FFFFFF"><strong>交费时间</strong></td>
              <td width="15%" align="center" background="../images/bg/bg2.gif" bgcolor="#FFFFFF"><strong>交费金额</strong></td>
            </tr>
            <ul id="SearchLicenseresult"></ul>
            
          </table>
          <div class="scott">
			<ul id="licensePagination" class="pagination"></ul>
			</div>
          <h1>&nbsp;</h1>
        </form>
        <div class="clear"></div>
      </div>
    </div>
    <div class="cl">&nbsp;</div>
  </div>
  <!-- END Logo + Search + Navigation -->
	<!-- Header -->
  <!-- END Header -->
  <!-- Main -->
<div id="main">
		<!-- Three Column Content -->
        <div class=""></div>
        <h1></h1>
        <!-- END Three Column Content -->
		<!-- Two Column Content -->
    <!-- END Two Column Content -->
  </div>
<!-- END Main -->
	<!-- Footer -->
	
	<!-- END Footer -->

</div>

</div>
<div id="footer">
	<div class="footer">
	  <div class="left">联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客 京ICP898893233</div>  <div class="right">©天佑网版权所有</div>/</div>
</body>
</html>
<script type="text/javascript">

var items_per_page = 6;
var page_index = 0;


function getLicenseDataList(index){
	var createDate = $("#createDate").val();
	var activateDate = $("#activateDate").val();
	var batchCode = $("#batchCode").val();
	var licenseStatus = $("#licenseStatus").val();
	var proName = $("#proName").val();
	var pageIndex = index;
	$.ajax({
		type: "get",
		url: "${pageContext.request.contextPath}/licensePagination",
		data: "page.page="+(pageIndex+1)+'&page.size='+items_per_page+'&createDate='+createDate+'&activateDate='+activateDate+'&batchCode='+batchCode+'&licenseStatus='+licenseStatus+'&proName='+proName+'&'+Math.random(),
		dataType: 'json',
		contentType: "text/html; charset=utf-8",
		//contentType: "application/x-www-form-urlencoded",
		success: function(msg){
			
			var total = msg.total;
			var html = '' ;	
			$.each(msg.result,function(i,n){					
				var name = n.proName
				if(name==null){
					name="";
				}
				var activateDate = n.activateDate
				if(activateDate==null){
					activateDate="";
				}
				var payAmount = n.payAmount
				if(payAmount==null){
					payAmount="";
				}
				html += '<tr><td height="40" align="center" bgcolor="#FFFFFF">'+n.licenseNum+'</td><td height="40" align="center" bgcolor="#FFFFFF">'+n.batchCode+'</td><td height="40" align="center" bgcolor="#FFFFFF">'+n.createDate+'</td><td align="center" bgcolor="#FFFFFF">'+name+'</td><td align="center" bgcolor="#FFFFFF">'+n.licenseType+'</td><td align="center" bgcolor="#FFFFFF">'+activateDate+'</td><td align="center" bgcolor="#FFFFFF">'+payAmount+'</td></tr>'
			
			});
			$('#SearchLicenseresult').html(html);
			
			//分页-只初始化一次
			if($("#licensePagination").html().length == 0){
				$('#licensePagination').pagination(total, {
					
					'items_per_page'      : items_per_page,
					'num_display_entries' : 5,
					'num_edge_entries'    : 2,
					'prev_text'           : "上一页",
					'next_text'           : "下一页",
					'callback'            : licensePageselectCallback
				});
			}
		}
	});
}

function licensePageselectCallback(page_index, jq){
	getLicenseDataList(page_index);
}

$(document).ready(function(){
	
	getLicenseDataList(page_index);
});

function query(){
	$("#licensePagination").html("");
}
</script>