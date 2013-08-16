<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课件类</title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/kejian.css"/>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/person.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/pagination.css" rel="stylesheet" type="text/css" />
<style type="text/css">
   
   
	.highlight{
	background:#FF0;
 	color:#E00;
	}  
    </style>

<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.pagination.js" type="text/javascript" />
<script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>

<script type="text/javascript" >
	var items_per_page = 5;
	var page_index = 0;
	function getDataList(index){
		var pageIndex = index;
		$.ajax({
			type: "get",
			url: "${pageContext.request.contextPath}/getEducationPackageList",
			data: "page.page="+(pageIndex+1)+"&page.size="+items_per_page+"&"+Math.random(),
			dataType: 'json',
			contentType: "text/html; charset=utf-8",
			success: function(msg){
				var total = msg.total;
				var html = '' ;	
				$.each(msg.result,function(i,n){	 
					html += "<li>";
						html+= "<div class='kejian_title'>";
							html+= "<a target='_blank' href='${pageContext.request.contextPath}/toEduSourceList?packageId="+n.id+"'>"+n.title+"</a>";
						html+="</div>";
						html+= "<div class='kejian_info'>";
							html+= "类型："+n.postName;
						html+="</div>";
						
						html+= "<div class='kejian_jianjie'>";
							html+= "描述："+n.description;
						html+="</div>";
					html += "</li>"; 
					html += "</br>";
				});
				$('#searchResultList').html(html);
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
		getDataList(page_index);
	});
</script>
</head>

<body>
<div class="body">
 		<div>
			<jsp:include page="${pageContext.request.contextPath}/top.jsp" />
            <jsp:include page="/menu.jsp"></jsp:include>
			<jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />
		</div>
 <div class=" new_box">
 	<div class="new_body">
   		<jsp:include page="/summary.jsp" ></jsp:include>
        <div class="new_right">
          <div class="new_search" style="display:none">
         
          
        </div>
        <div class="new_right_mulu">
          <div class="new_right_book_title">
            <div class="left">专题列表</div>
            <div class="clear"></div>
          </div>
          <div class="mulu_list">
          <ul id="searchResultList">
          
            </ul>
          <div class="scott">
          <div id="Pagination" class="pagination"></div>
          </div>
          </div>
          </div>
        </div>
        <div class="kejian_right">
        	<!-- div class="kejian_top10">
        		<h3><div class="left">最受欢迎的课件</div><div class="right"></div></h3>
                <div class="clear"></div>
                <ul>
                	<li><span>[施工]</span> <a href="#">个性化的获取自己需要的信</a></li>
                    <li><span>[施工]</span> <a href="#">个性化的获取自己需要的信</a></li>
                </ul>
        	</div -->
        </div>
        <div class="clear"></div>
 </div> 	
 </div>  
  <div class="clear"></div>
  <div class="foot">
<!-- 联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号  -->
<jsp:include page="${pageContext.request.contextPath}/bottom.jsp" />

</div></div>

<div class="guding">
<div class="guding2">
<div class="zuofu">
	<ul>
    	<li class="present"><a href="#">资讯</a></li>
        <li><a href="#">文件</a></li>
        <li><a href="#">核心</a></li>
        <li><a href="#">施工</a></li>
        <li><a href="#">验收</a></li>
        <li><a href="#">表格</a></li>
        <li><a href="#">监管</a></li>
        <li><a href="#">管理</a></li>
        <li><a href="#">职考</a></li>
        <li><a href="#">应用</a></li>
    </ul>
</div>
<div class="btn_fd">
	<a href="#"></a>
	<a href="#top"><img title="返回顶部" class="shang20" src="../images/btnfd_13.gif" /></a>
</div>
</div></div>
<div class="fixed"><div class="fixed_box">
<div class="shangxiaye">
	<a class="shangyiye" href="javascript:void(0)" style="display: none;" onclick="last();" id="shangyiye"></a>
    <a class="xiayiye" href="javascript:void(0)" style="display: none;" onclick="next();" id="xiayiye"></a>
    <div class="glgjc_box">
	    <a id="hotup"><img title="关键字高亮" class="shang10" src="/images/detail/gjc_03.gif" /></a>
	    <a class="glgjc" id="glgjc" href="javascript:void(0)">关键词</a>
	    <a id="hotnext"  href="javascript:void(0)"><img title="关键字不高亮" src="/images/detail/gjc_06.gif"/></a>
    </div>
</div>
</div></div>
</body>
</html>

