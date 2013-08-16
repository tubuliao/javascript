<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人主页</title>

<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../css/person.css" rel="stylesheet" type="text/css" />
<link href="../css/pagination.css" rel="stylesheet" type="text/css" />


<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.pagination.js" type="text/javascript" />
<script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-ui-1.10.2/themes/base/jquery-ui.css" />
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>

<!--<SCRIPT language=javascript src="homesearch.js"></SCRIPT>-->

<script type="text/javascript" >
	var items_per_page = 6;
	var page_index = 0;

	function getDataList(index){
		var pageIndex = index;
		$.ajax({
			type: "get",
			url: "${pageContext.request.contextPath}/pagination",
			data: "pageIndex="+pageIndex+'&items_per_page='+items_per_page+'&'+Math.random(),
			dataType: 'json',
			contentType: "text/html; charset=utf-8",
			//contentType: "application/x-www-form-urlencoded",
			success: function(msg){
				var total = msg.total;
				var html = '' ;
				$.each(msg.result,function(i,n){	
					//html += '<li><div><a href="' + n[5] + '">' + n[3] + '</a></div><span><input type="button" value="取消收藏" onClick="deleteBookmark( ' + n[0] + ' );" /></span></li>'
					//html += '<li><div><a href="' + n[5] + '">' + n[3] + '</a></div><span>收藏时间： ' + getLocalTime(n[7]) + ' &nbsp;&nbsp; 类型：建筑 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onClick="deleteBookmark( ' + n[0] + ' );">【取消收藏</a></span></li>'
					html += '<li><div><a href="' + n.url + '">' + n.title + '</a></div><span>收藏时间： ' + n.createDate + ' &nbsp;&nbsp; 类型：' + (n.classification != null ? n.classification.title : '暂未分类') + ' &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></li>'
																						           
				
				});
				$('#Searchresult').html(html);
				
				//分页-只初始化一次
				if($("#Pagination").html().length == ''){
			
					$('#Pagination').pagination(total, {
						'items_per_page'      : items_per_page,
						'num_display_entries' : 5,
						'num_edge_entries'    : 2,
						'prev_text'           : "上一页",
						'next_text'           : "下一页",
						'callback'            : pageselectCallback
					});
				}
			}
		});
	}

	function pageselectCallback(page_index, jq){
		getDataList(page_index);
	}

	$(document).ready(function(){
		//alert("pagination") ;
		getDataList(page_index);
	});
	
	function deleteBookmark(bId) {
		//alert('xx') ;
		var r = window.confirm('确定取消该收藏？') ;
		if(r) {
			$.get('/deleteBookmark/' + bId, function(result) {
				if(result.success) {
					getDataList(page_index);
					window.alert('取消收藏成功！') ;
				} else {
					alert(result.msg) ;
				}
			}) ;
		}
		
	}



	function getVisitDataList(index){
		var pageIndex = index;
		$.ajax({
			type: "get",
			url: "${pageContext.request.contextPath}/visitPagination",
			data: "pageIndex="+pageIndex+'&items_per_page='+items_per_page+'&'+Math.random(),
			dataType: 'json',
			contentType: "text/html; charset=utf-8",
			//contentType: "application/x-www-form-urlencoded",
			success: function(msg){
				
				var total = msg.total;
				var html = '' ;	
				$.each(msg.result,function(i,n){					
					
					html += "<li><div><a target='_blank' href='" + n.url + "'>" + n.title + "</a></div><span>访问时间： " + n.visitDate + " </span></li>"
				
				});
				$('#SearchVisitresult').html(html);
				
				//分页-只初始化一次
				if($("#visitPagination").html().length == ''){
			
					$('#visitPagination').pagination(total, {
						
						'items_per_page'      : items_per_page,
						'num_display_entries' : 5,
						'num_edge_entries'    : 2,
						'prev_text'           : "上一页",
						'next_text'           : "下一页",
						'callback'            : visitPageselectCallback
					});
				}
			}
		});
	}

	function visitPageselectCallback(page_index, jq){
		getVisitDataList(page_index);
		if(page_index>0){
			$('html,body').animate({ scrollTop: document.body.clientHeight }, "fast");
		}
	}

	$(document).ready(function(){
		
		getVisitDataList(page_index);
	});


	$(function() {
		
		$( "#progressbar" ).progressbar({
			value: ${sumTotal}
		});
	});

	

</script>

</head>

<body>

 
    <div class="search_up"></div>

    <div class="search_content_box">
  <div class="search_content">
    <div class="person_nav2">
        	<a class="present" href="#">个人空间</a> 
        </ul>
  </div>
  	<div class="person_left left">
    	<div class="order_title">我的状态<span class="order_title_en"> / my state</span> </div>
        <div class="state">
        	<div class="name">${user.aliasname}</div>
       	  <div class="wzd left">您的资料完整度：<font size="4" class="anhong">${sumTotal}%</font></div>
		  <input type="hidden" id="sumTotal" name="sumTotal" value="${sumTotal}"/>
        	<div class="progress left"><div id="progressbar"></div></div>
        	  <a href="/personCenter1"><img class="stateimg" src="../images/personal/ws_03.gif" /></a>

       	  <p class="clear">完善资料可以获得更多优惠或积分</p>
              <div class="card_box">
              <img class="card shang10 left" src="../${img }" />
              <div class="account_shot left">
              <div>
              		经验：<img src="../images/personal/dj_05.png" width="40" height="17" /> 
              		${user.additional.experience}   &nbsp;&nbsp;&nbsp;
              		等级：             <img src="../${gradeimg }" width="42" height="17" /> 
              		${user.additional.grade}&nbsp;&nbsp;&nbsp;
              	 
              </div>
				<div>购买的服务：<span class="lan">${title} </span>&nbsp;&nbsp;&nbsp;套餐到期时间：<span class="lan">${endDate}</span></div>      
              
              </div>
              </div>
              <div class="clear"></div>
          <!-- div>
       	    <div class="wzd left">您的账户安全等级：<font size="4" class="anhong"> 低 </font></div>
        	  <div class="progress left"><div class="progress_grows"></div></div>
        	  <a href="#"><img class="stateimg" src="../images/personal/xf_03.gif" /></a>
              <p class="clear">您的帐号未设置任何密码保护措施，当您忘记密码时，将无法找回密码！</p>
       	  </div -->
        </div>


        <div class="order_title">我的收藏<span class="order_title_en"> / my bookmark</span> </div>
		<div class="person_qiepian">
        	<ul id="Searchresult">
            </ul>
        </div>
			<div class="scott" style="margin-left:20px;">
			<div id="Pagination" class="pagination"></div>
			</div>



 <div class="order_title">最近访问<span class="order_title_en"> / recent visit</span> </div>
		<div class="person_qiepian">
        	<ul id="SearchVisitresult">
		
			
			
			
				<br style="clear:both;" />

                <div class="clear"></div>
            </ul>
       
        </div>
			<div class="scott" style="margin-left:20px;">
			<div id="visitPagination" class="pagination"></div>
			</div>


   	  </div>
    <div class="person_right right">
    	<jsp:include page="right.jsp" ></jsp:include>
        <!--  div class="info_youhui">
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
            	<li><a href="#"><img src="images/personal/person_11.gif" width="53" height="64" /></a></li>
                <li><a href="#"><img src="images/personal/person_11.gif" width="53" height="64" /></a></li>
                <li><a href="#"><img src="images/personal/person_11.gif" width="53" height="64" /></a></li>
            </ul>
        </div>
        <div class="new_youhui">
          <div class="new_youhui_title">活跃用户<span class="new_youhui_title_en"> / latest preferential</span> </div>
          <ul>
            <li><a href="http://www.baidu.com"><img src="../images/personal/person_07.gif" width="150" height="150" /></a><span><a href="#">盛开的荷花</a></span></li>
            <li><a href="#"><img src="../images/personal/person_07.gif" width="150" height="150" /></a><span><a href="#">盛开的荷花</a></span></li>
            <li><a href="#"><img src="../images/personal/person_07.gif" width="150" height="150" /></a><span><a href="#">盛开的荷花</a></span></li>
            <li><a href="#"><img src="../images/personal/person_07.gif" width="150" height="150" /></a><span><a href="#">盛开的荷花</a></span></li>
            <li><a href="#"><img src="../images/personal/person_07.gif" width="150" height="150" /></a><span><a href="#">盛开的荷花</a></span></li>
          </ul>
          <div class="clear"></div>
        </div-->
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
  <div class="btn_fd"><a href="/toSuggest" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image17','','../images/btnfd_10.gif',1)"><img src="../images/message.gif" name="Image17" width="34" height="34" border="0" id="Image17" /></a><a href="#" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image18','','../images/btnfd_13.gif',1)"><img src="../images/top.gif" name="Image18" width="34" height="34" border="0" class="shang20" id="Image18" /></a></div>
</div></div>

</body>
</html>
