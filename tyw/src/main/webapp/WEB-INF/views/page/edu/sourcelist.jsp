<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>课件类</title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/kejian.css">
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/person.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/pagination.css" rel="stylesheet" type="text/css" />


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
			url: "${pageContext.request.contextPath}/getEducationSourceList",
			data: "pageNo="+(pageIndex+1)+"&pageSize="+items_per_page+"&packageId="+$("#packageId").val()+"&"+Math.random(),
			dataType: 'json',
			contentType: "text/html; charset=utf-8",
			success: function(msg){
				var total = msg.total;
				var html = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>" ;	
				html += "<tr>";
				html+="<td width='7%' height='40' align='center' class='kejian_table_title'>";
					html+="序号";
				html+="</td>";
				html+="<td width='53%' height='40' align='center' class='kejian_table_title'>";
					html+="课程名称";
				html+="</td>";
				
				html+="<td width='13%' height='40' align='center' class='kejian_table_title'>";
					html+="专业类型";
				html+="</td>";
			
				html+="<td width='10%' height='40' align='center' class='kejian_table_title'>";
					html+="课节数";
				html+="</td>";
				
				html+="<td width='12%' height='40' align='center' class='kejian_table_title'>";
					html+="主讲老师";
				html+="</td>";
			  html += "</tr>";
			  
				$.each(msg.result,function(i,n){	
					
						var count = pageIndex*items_per_page+i+1;
							html += "<tr>";
								html+="<td height='30' align='center' class='kejian_table_xuhao'>";
									html+=""+count;
								html+="</td>";
								html+="<td height='30'>";
									html+="<a href='/toSourceLeafList?sourceId="+n.id+"&packageId=${pack.id}'>"+n.sourceName+"</a>";
								html+="</td>";
								
								html+="<td height='30' align='center'>";
									html+=n.postName;
								html+="</td>";
							
								html+="<td height='30' align='center'>";
									html+=n.sourceWareCount;
								html+="</td>";
								
								html+="<td height='30' align='center'>";
									html+=n.teacher;
								html+="</td>";
							html += "</tr>"; 
				});
				html+="</table>";
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
<input id="packageId" name="packageId" value="${pack.id }" type="hidden" />

 <div class=" new_box">
 	<div class="new_body">
        <div class="new_right">
         <div class="new_search" style="display:none"></div>
         <div class="new_position"><img src="../images/new/new_08.gif" />位置：<a href="/toPackageList">专题列表</a>><a href="/toEduSourceList?packageId=${pack.id}">${pack.title }</a>> </div>
         <div class="new_right_mulu">
           <div class="new_right_book_title">
            <div class="left">课程介绍</div>
            <div class="clear"></div>
          </div>
          <div class="mulu_list">
          <p>
         	${pack.description }
          </p> 
          
          <div class="kejian_table">
          <div id="searchResultList" >
          </div>
          </div>
 
          <div class="scott">
          <div id="Pagination" class="pagination"></div>
          </div>
          </div>
          </div>
        </div>
        <div class="kejian_right">
        	<!--div class="kejian_top10">
        		<h3><div class="left">最受欢迎的课件</div><div class="right"></div></h3>
                <div class="clear"></div>
                <ul>
                	<li><span>[施工]</span> <a href="#">个性化的获取自己需要的信</a></li>
                    <li><span>[施工]</span> <a href="#">个性化的获取自己需要的信</a></li>
                </ul>
        	</div-->
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
</body>
</html>

