<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${files.title}</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link rel=stylesheet type=text/css href="/css/search.css" />
 <style type="text/css">
	.highlight{
	background:#FF0;
 	}  
    </style>
<!-- link href="/css/pagination.css" rel="stylesheet" type="text/css" /-->
<script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.pagination.js" type="text/javascript" />

<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxbase.js"  type="text/javascript"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-ui-1.10.2/themes/base/jquery-ui.css" />
<!-- script src="${pageContext.request.contextPath}/js/js.js"></script-->
<script src="${pageContext.request.contextPath}/js/detailCommon.js" type="text/javascript"></script>
 
<!--[if lt IE 7]>
        <script type="text/javascript" src="unitpngfix.js"></script>
<![endif]-->
<script type="text/javascript">
window.onload=function(){
    map_width=document.body.clientWidth;//获取body宽度
    map_height=document.documentElement.clientHeight-65;//获取页面可见高度
    //alert(map_width);
    //alert(map_height);
    //下面两行最后的"px"必须加上,折腾了半天老是不显示添加的style,最后就是错在这了
    document.getElementById("windowfix").style.height=map_height+"px";
    
}
</script>
<script language="javascript" type="text/javascript">

function BrowseFolder(url,filename){
	$.post('/addDownload/',{url:url,title:filename},function(result){
		
	},'json');
window.location.href=encodeURI("${pageContext.request.contextPath}/downloadhttp?downLoadPath="+url+"&&fileName="+filename);
	 }  

function check(){
	var userId = $("#userId").val();
	var fileId = "${files.id}";
	if(userId == null || userId == ''){
		$("#loginDiv").fadeIn(1000);
   		$("#loginDiv").fadeOut(1000);
   	    setTimeout("location.href='/login.jsp'",2000);
	}else{
		$.post('/addLikeCount/',{userId:userId,fileId:fileId},function(result){
			if(result.success){
			  $("#likeImg").fadeIn(800).slideUp();
       		  $("#likeImg").fadeOut(800);
       		  var addLike = parseInt($("#likeCountId").text())+1;
       		  $("#likeCountId").html(addLike);
			}else if(result.fail){
				$("#likeDiv").fadeIn(1000);
	       		$("#likeDiv").fadeOut(1000);
			}
		},'json');
	}
}


function stos(url){
	 location.href=url;
}

 var total = 0;
 var ajq = 0;
 
 $(window).load(function(){    
  	 $("#NavigationSearch").css("display","none");
	  $("#keyword").css("display","none");
	  $("#search_btn").css("display","none");
	  $("#konwledgeType7").css("display","none");
	  $("#search_pd_shuru").css("display","block");
	  $("#pdsousuo").css("display","block");
	  $("#dropDownList1").css("display","block");
	  $("#searchCondition").css("display","none");
	  $("#pdss").html("页内搜索");
	 $("#pdsousuo").val("页内搜索");
	 
 	   var catalog = $("#muluid").text();
	   if(catalog == ''){
		   $('.detail').css({'width':'994px'});
		   $('#main').css({'width':'938px'});
	   }
		getDataList(page_index);
	   $("a[name='filesUrl']").each(function(){
		   $(this).click(function(){
			
			   var gUrl = $(this).attr('href');
  			   $.post('/changePageNum/',{gUrl:gUrl},function(result){
				   if(result.success){
					   var changeId = result.changeId;
					   var changeNum = result.changeNum;
					   ajq = total+1;
						$('#Pagination').pagination(total, {
								'items_per_page'      : items_per_page,
								'num_display_entries' : 5,
								'num_edge_entries'    : 2,
								'current_page'		  : changeNum-1,   
								'prev_text'           : "上一页",
								'next_text'           : "下一页",
								'callback'            : pageselectCallback
 							});
					  
			 			$("#filecontent").load('/openFile',{fileId:changeId,pageNum:changeNum});
			 			
 	 			 		$("#filecontent").ajaxStop(function(){
 	 						 $("#search_pd_shuru").click();
 	 						 var aPoint = result.aPoint;
  	 			 		 //初始化目录
  	 				 		$("#windowfix").height($("#filecontent").height());
	 	 			 		 var apointLength=$("a[name="+aPoint.replace("#","")+"]").offset().top-30;
	  	 			 		 window.scrollTo(0,apointLength);
	  	 			 		 $(this).unbind();
 	 				 	});
 				   }
			   },'json');
			   return false;
		   }); 
	   });   
	}); 
 	
 	var items_per_page = 1;
	var page_index = 0;
	
	function getDataList(index){
		var pageIndex = index;
		var fileId = "${files.id}";
		$.ajax({
			type: "get",
			url: "${pageContext.request.contextPath}/bigFilesPagination",
			data: "fileId="+fileId,
			dataType: 'json',
			contentType: "text/html; charset=utf-8",
			//contentType: "application/x-www-form-urlencoded",
			success: function(msg){
				 total = msg.total;
				 ajq = total;
				 
				//分页-只初始化一次
				if($("#Pagination").html().length == 0){
					
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
		
		var pageNum = page_index+1;
		var fileId = "${files.id}";
		getDataList(page_index);
		if(ajq==total){
			$.post('/searchUrl/',{pageNum:pageNum,fileId:fileId},function(result){
				if(result.success){
					 var aPoint = result.aPoint;
 		 			$("#filecontent").load('/openFile',{fileId:$("#fileid").val(),pageNum:pageNum});
 		 			$("#filecontent").ajaxStop(function(){
 	 		 			try{
	 	 		 			 $("#search_pd_shuru").focus().blur();
	 	 				 	 $("#windowfix").height($("#filecontent").height());
		 			 		 var apointLength=$("a[name="+aPoint.replace("#","")+"]").offset().top-30;
	 	 			 		 window.scrollTo(0,apointLength);
	 	 			 		 $(this).unbind();
 	 	 		 			}catch(error){
 	 		 			
 	 	 		 			}
 					
 				 	});
					//$("#main").attr("src","/openFile?fileId="+fileId+"&pageNum="+pageNum+"");
				}
			 },'json');
		}
	}
 
        /***
        *功能：隐藏和显示div
        *参数divDisplay：html标签id
        ***/
        function click_a(divDisplay)
        {
            if(document.getElementById(divDisplay).style.display != "block")
            {
                document.getElementById(divDisplay).style.display = "block";
            }
            else
            {
                document.getElementById(divDisplay).style.display = "none";
            }
        }
    /* 
        function collectBookmark() {
    		// 收藏地址
    		var url = window.location.href ;
    		var title = $('#title02').text() ;
    		//alert(url + '**********' + title) ;
    		
    		$.get('${pageContext.request.contextPath}/collectBookmark', { bUrl: url, bTitle: title}, function(result) {
    			if(result.success) {
    				$("#collectImg").fadeIn(800).slideUp();
          		    $("#collectImg").fadeOut(800);
    			} else {
    				$("#collectDiv").fadeIn(1000);
    	       		$("#collectDiv").fadeOut(1000);
    			}
    		}, 'json') ;
    		
    	}

       */   

       $(document).ready(function(){
    		 init();
    	 });

    		function init(){
    			var url = window.location.href;

    			var visitId = "${files.id}";

    			var title = "${files.title}";
    			
    			$.post('/addVisit/',{url:url,title:title,visitId:visitId},function(result){
    							
    						},'json');
    		}  
        
        
        
        
        

</script> 
   
     
</head>

<body>
<div class="body">
 <input value="detail" id="isindex" type="hidden"/>
  <input value="${files.id}" id="fileid" type="hidden"/>
<div>
   <jsp:include page="${pageContext.request.contextPath}/top.jsp" />
  <jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />
  </div>
 <div class="search_up2"></div>   
  <div class="search_content_box">
    <div class="detail_book">
   <div class="weizhi">
          <div class="weizhi_shouye left"><a class="" href="${pageContext.request.contextPath}/jump.jsp">首页</a></div>
          
          	<input id="userId" name="userId" type="hidden" value="${user.id}"/>
          	<input id="kId" name="kId" type="hidden" value="${files.id }" />
          <div class="weizhi_qita left">
          		<span class="xjt">></span><a href="${TagPath[1]}" target="_blank">${TagPath[0]}</a>
          </div>
    	</div>
  <div class="search_content">
  <c:if test="${files.catalog != ''}">
   <div class="search_right left">
  <div class="detail_mulu" >
    <div class="detail_mulu_left" id="windowfix">
        	<h3>书籍目录</h3>
        	<span id="muluid">
        	${files.catalog}
        	</span>
        </div>
        </div>
  	  <div class="clear"></div>
      <!-- a href="#"><img class="tiwen" src="/images/cs_25.gif" /></a--></div>
  </c:if>
 
  	<div class="detail left">
   
    	
   	  <div class="detail_title" id="title02">${files.title}</div>
        <div class="title2"> 发布时间：${files.begincreateDate} </div>
         <div class="wenzi" id="filecontent">  </div>

    	  <div  id="main"    scrolling="no">
       </div>  
          <div class="scott">
			<div id="Pagination" class="pagination" style="margin-left:45px;"></div>
		</div>
		<div class="clear"></div>
		
		
        <div class="btn">
        <a class="shoucang" style="positon:relative" id="collect" href="javascript:void(0);" onclick="collectBookmark();">
        <img id="collectImg" src="/images/like107.png" style="display:none; position:absolute; margin-top:-30px; margin-left:-10px"/>
                    收藏 (<span id="collectCountId">${collectCount}</span>)
        </a>
		
        <a class="zan" style="positon:relative" id="like" href="javascript:void(0);" onclick="check();">
        <img id="likeImg" src="/images/like107.png" style="display:none; position:absolute; margin-top:-30px; margin-left:-10px"/>
                    赞 (<span id="likeCountId">${likeCount}</span>)
        </a>
      <!-- <c:if test="${not empty files.url}">
	         <a class="xiazai"  id="download" onclick="BrowseFolder('${files.url}','${files.title}')"  >
	        <img id="likeImg" src="/images/new/xia_03.gif" style="display:none; position:absolute; margin-top:-30px; margin-left:-10px"/>
	                    下载 
	        </a>
        </c:if>
         -->  
        <!-- a href="#"><img src="/images/detail_11.gif" width="84" height="30" /></a--></div>
        <c:if test="${not empty listFiles}">
        <div align="center" class='xia20'><img src="/images/detail_16.gif" width="694" height="16" /></div>
            <h2 class="left" style="margin:0; margin-left:30px; line-height:25px;">附件下载：</h2>
            <ul class="left" style="font-size:14px; line-height:25px;">
       			 <c:forEach items="${listFiles}" var="filesdown" varStatus="index">
 		                   	<li> 
 		                   		<a style="color:blue" href="javascript:BrowseFolder('${filesdown.url}','${filesdown.title}(${index.index+1})')"  >${filesdown.title}(${index.index+1})${filesdown.suffix} </a> 
 		                 	</li>
 				 </c:forEach>
 		   </ul>
 		   </c:if>
 		   <div class="clear"></div>
      <div class="gjztj zuo20 shang10">
          	<div class="gjztj_title">相关搜索:</div>
    				<ul class="gjz" id="stos">
                <c:forEach items="${listtag}" var="segTag">
 		                   	<li>
		                   		<a href="${pageContext.request.contextPath}/goPageList?keyword=${segTag.tagName}&typeId=&pageNo=1&tag_name=&onex=konwledgeType0" title="${segTag.tagName}"   id="">${segTag.tagName}  </a> 
		                 	</li>
 				    </c:forEach>
                    </ul>
      </div>
        </div>
   
          
          <div class="clear"></div>
  </div>
  </div>
  </div>
    <div class="search_down"></div>
    <div class="foot">
<!-- 联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号  -->
<jsp:include page="${pageContext.request.contextPath}/bottom.jsp" />
</div></div>
<div class="fixed">
	<div class="fixed_box">
		<div class="shangxiaye">
		    <div class="glgjc_box">
			    <a id="hotup"><img title="关键字高亮" class="shang10" src="/images/detail/gjc_03.gif" /></a>
			    <a class="glgjc" id="glgjc" href="javascript:void(0)">关键词</a>
			    <a id="hotnext"  href="javascript:void(0)"><img title="关键字不高亮" src="/images/detail/gjc_06.gif"/></a>
		    </div>
		</div>
	</div>
</div>
<div id="likeDiv" class="ding" style="display:none;background:none">
<div class="tishi">
<div  class="tishi_box" style="margin-left:200px;width:200px; height:30px; line-height:30px; font-size:16px; text-align:center;border:3px solid #eeeeee; background:#f98d05; color:#fff;">
您已经赞过此文章！
</div>
</div>
</div>
<div id="collectDiv" class="ding" style="display:none;background:none">
<div class="tishi">
<div  class="tishi_box" style="margin-left:200px;width:200px; height:30px; line-height:30px; font-size:16px; text-align:center;border:3px solid #eeeeee; background:#f98d05; color:#fff;">
您已经收藏过此文章！
</div>
</div>
</div>
<div id="loginDiv" class="ding" style="display:none;background:none">
<div class="tishi">
<div  class="tishi_box" style="margin-left:200px;width:200px; height:30px; line-height:30px; font-size:16px; text-align:center;border:3px solid #eeeeee; background:#f98d05; color:#fff;">
请先登录！
</div>
</div>
</div>
<div class="guding">
<div class="guding2">
<!-- div class="zuofu">
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
</div-->
<div class="btn_fd">
	<a href="#"></a>
	<a href="#top"><img title="返回顶部" class="shang20" src="/images/btnfd_13.gif" /></a>
</div>
</div></div>
 
</body>
 
<script src="${pageContext.request.contextPath}/js/jquery.highlight-3.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/myhighlight.js" type="text/javascript"></script>
</html>