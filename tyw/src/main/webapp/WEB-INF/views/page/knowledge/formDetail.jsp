<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${form.title}</title>
<link href="${pageContext.request.contextPath}/jquery-ui-1.10.2/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/pagination.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/detailCommon.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/formDetail.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.pagination.js" type="text/javascript"></script>
 
<script src="${pageContext.request.contextPath}/js/myhighlight.js" type="text/javascript"></script>
<!--[if lt IE 7]>
        <script type="text/javascript" src="unitpngfix.js"></script>
<![endif]-->
<script src="js/nav.js"></script>
</head>
  <style type="text/css">
	.highlight{
	background:#FF0;
 	color:#E00;
	}  
    </style>
 <script type="text/javascript" >
	 
 function check(){
		var userId = $("#userId").val();
		var fileId = "${form.id}";
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
 function download(url){
	 window.location.href=url;
}
 
 $(document).ready(function(){
	 init();
 });

	function init(){
		var url = window.location.href;

		var visitId = "${form.id}";

		var title = "${form.title}";
		
		$.post('/addVisit/',{url:url,title:title,visitId:visitId},function(result){
						
					},'json');
	}  
 
	 function stos(url){
		 location.href=url;
	}

 /* 
	 function collectBookmark() {
		// 收藏地址
		var url = window.location.href ;
		var title = $('#title02').text() ;
		//alert(url + '**********' + title) ;
		
		$.get('${pageContext.request.contextPath}/collectBookmark', { bUrl: url, bTitle: title}, function(result) {
			if(result.success) {
				alert('收藏添加成功！') ;
			} else {
				alert(result.msg) ;
			}
		}, 'json') ;
	} 
  */

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
 

 		var lilength=$("#stos>li").length;
	 	 for(var i=15;i<lilength;i++){
	 		   $("#stos li:eq("+i+")").hide();
	  	  }
 })
 
 /* 
	function bookDetail() {
		var sourceCompose = $('#sourceDetail').html();
		//var sourceCompose = $('#sourceDetail').html().replace(new RegExp('/', 'g'), '&');
		//alert(sourceCompose);
		//location.href="${pageContext.request.contextPath}/knowledge/web/bookdetail/" + encodeURIComponent(encodeURIComponent(sourceCompose));	// 两次编码 java在后台会自动的进行一次解码 但结果不对
		window.open("${pageContext.request.contextPath}/knowledge/web/bookdetail/" + encodeURIComponent(encodeURIComponent(sourceCompose)), '_blank');
	}
  */
 	var items_per_page = 1;
	var page_index = 0;
	var id = "${id}";
	var flag = '1';
	function BrowseFolder(url,filename){
		$.post('/addDownload/',{url:url,title:filename},function(result){
			
		},'json');
 		window.location.href=encodeURI("${pageContext.request.contextPath}/downloadhttp?downLoadPath="+url+"&&fileName="+filename);
			 }  
	function getDataList(index){
		var pageIndex = index;
		$.ajax({
			type: "get",
			url: "${pageContext.request.contextPath}/pagination/exampleForm",
			data: "pageIndex=" + pageIndex + '&itemsPerPage=' + items_per_page + '&id=' + id + '&flag=' + flag + '&' + Math.random(),
			dataType: 'json',
			contentType: "text/html; charset=utf-8",
			//contentType: "application/x-www-form-urlencoded",
			success: function(r){
				var total = r.total;
				var html = '' ;
				var sampleTable="";
				$.each(r.result,function(i,n){	
 					if(n.empHiPicUrl == 'no' && flag == '1') {
						html += '暂无该地区相关联的样表！ 可以<a href="javascript:void(0);" onclick="referOther();" style="color:blue;font-weight:bold;font-size:15px;"> < 参考 > </a>其他地区的样表……';
					} else if(n.empHiPicUrl == 'no' && flag == '0') {
						html += '哎呀！其他地区貌似也没有相关的样表啊……'
					} else if(flag == '1'){
						html += '<img src="' + n.empHiPicUrl + '" /><br /><br />';
					} else if(flag == '0'){	// 其他地区的样表 显示来源
						html += '<img src="' + n.empHiPicUrl + '" /><br /><span class="title2" style="position:absolute;right:235px;">来源：' + n.source + '</span><br /><br />';
					}
				});
				  $.each(r.sampleTable,function(j,k){	
 					  sampleTable+="<li><a style=\"color:blue\" href=\"${pageContext.request.contextPath}/downloadhttp?downLoadPath="+k.empUrl+"&&fileName="+k.title+"(样表)"+"\">"+k.title+"(样表)"+k.suffix+ "</a></li>"
 					});
  	 			$("#sampleTable").html(sampleTable);
				$('#searchresult').html(html);
				try{
				 if(n.empHiPicUrl == 'no' ){
					return;
				 }
				}catch(error){}
				//分页
				if($("#pagination").html().length == ''){
					$('#pagination').pagination(total, {
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

	function referOther() {
		flag = '0';
		$("#pagination").html('');
		getDataList('0');
	}
	
	
	</script>	
<body>
<div class="body">
 <input value="detail" id="isindex" type="hidden"/>
<div class="clear"></div>
  <div>
  <jsp:include page="${pageContext.request.contextPath}/top.jsp" />
  <jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />
  </div>
  <input id="userId" name="userId" type="hidden" value="${user.id}"/>
  <input id="kId" name="kId" type="hidden" value="${form.id }" />
 <div class="search_up2"  ></div>   
  <div class="search_content_box">
  
  		<div class="weizhi" style=" padding-top:10px">
          <div class="weizhi_shouye left"><a class="" href="${pageContext.request.contextPath}/jump.jsp" target="_blank">首页</a></div>
          <div class="weizhi_qita left">
          	<span class="xjt">></span><a href="${TagPath[1]}" target="_blank">${TagPath[0]}</a>
          </div>
    	</div>
        <div class="clear"></div>
  
  <div class="search_content">
  	<div class="detail left">
   	  <div class="detail_title" id='title02'><c:out value="${form.title}"/></div>
        <div class="title2">
        				发布时间：<fmt:formatDate value="${form.begincreateDate}" type="both" dateStyle="long" timeStyle="long" />&nbsp;&nbsp;&nbsp;&nbsp;
						数据来源：<a href="javascript:void(0);" onclick="bookDetail();"><span id="sourceDetail"><c:out value="${form.source}"/></span></a>
		</div>
    	
    	<div class="table_hd">
        		<ul>
                	<li id="onef1" onclick="setTab('onef',1,3)" class="hover">空表</li>
                    <li id="onef2" onclick="setTab('onef',2,3)">填写说明</li>
					<li id="onef3" onclick="setTab('onef',3,3)">样表</li>
                </ul>
        </div>
		<!--空表-->
        <div class="table_box hover" id="con_onef_1">
        	<div class="pic" align="center" id="empForm">
        		<c:forEach items="${picUrls}" var="PICURL" varStatus="idx">
        			<img src="${PICURL}"/><br/><br/>
        		</c:forEach>
		      </div>
        </div>
		<!--填表说明-->
        <div class="table_box" id="con_onef_2" style="display:none" >
    		<div class="wenzi">${form.descContent}</div>
        </div>
 		<!--样表-->
		<!--
		 <div class="table_box" id="con_onef_3" style="display:none">
			<div class="pic" align="center">
				<c:forEach items="${allExpUrlList}" var="aeul" varStatus="idx">
					<c:forEach items="${aeul}" var="eu">
						<img src="${eu}" /><br /><br />
        			</c:forEach>	
        		</c:forEach>
			  </div>
		 </div>
		-->
		 <!-- 样表（分页） -->
		 <div class="table_box" id="con_onef_3" style="display:none">
			<div id='searchresult' class="pic" align="center">
			</div>

			<div class="scott">
				<div id="pagination" class="pagination" style="margin-left:42%;left:-width/2;padding:0 0 30px 0"></div>
			</div>
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
        
        <!-- a href="#"><img src="/images/detail_11.gif" width="84" height="30" /></a--></div>
        <c:if test="${not empty listFormdown}">
         <div align="center" class='xia20'><img src="/images/detail_16.gif" width="694" height="16" /></div>
            <h2 class="left" style="margin:0; margin-left:30px; line-height:25px;">附件下载：</h2>
            <div  class="left" >
              <ul style="font-size:14px; line-height:25px;"  >
       			 <c:forEach items="${listFormdown}" var="listFormdown" varStatus="index">
 		                   	<li> 
 		                   		<a style="color:blue" href="javascript:BrowseFolder('${listFormdown.empUrl}','${listFormdown.title}(空表)')"  >${listFormdown.title}(空表)${listFormdown.suffix} </a> 
 		                 	</li>
 				 </c:forEach>
 		   </ul>
 			 <ul  style="font-size:14px; line-height:25px;" id="sampleTable">
 		    </ul>
            </div>
 		 </c:if>
 		   <div class="clear"></div>
     
      <div class="gjztj zuo20 shang10">
          
                 	<div class="gjztj_title">相关搜索</div>
            	<ul class="gjz" id="stos">
	            	<c:forEach items="${listtag}" var="segTag">
 		                   	<li>
		                   		<a href="${pageContext.request.contextPath}/goPageList?keyword=${segTag.tagName}&typeId=&pageNo=1&tag_name=&onex=konwledgeType0" title="${segTag.tagName}"   id="">${segTag.tagName}  </a> 
		                 	</li>
 				    </c:forEach>
               </ul>
               <br/>
             
      </div>
      <!-- 
        <div class="pinglun">
        	<div class="pinglun_title">网友评论</div>
        	<c:forEach items="${allComments.content}" var="base">
        		<div class="pinglun_box">
            		<a href="#"><img src="${pageContext.request.contextPath}/images/detail_20.gif"></a>
       	  			<div class="left">
                		<div class="pinglun_ren">
                			<a href="#"><c:out value="${base.issuedBy}" /></a>
	                		<span class="pinglun_shijian">
	                      		<fmt:formatDate value="${base.createDate}" type="both" dateStyle="long" timeStyle="long" />
	                     	</span>                    
                     	</div>
	          			<div class="pinglun_neirong">
	                   		<c:out value="${base.content}" />
	                    </div>
                	</div>
                	<div class="clear"></div>
            	</div>
        	</c:forEach>
        	
   	  <div class="pinglun_tongji">
            	<a href="#">55 </a>人参与 <a href="#"> 33 </a>条评论（查看）          </div>
      <div class="pinglun_tianxie">
                <input type="hidden" id="authsUserId" name="authsUserId" value="${user.id}" /> 
                <input type="hidden" id="infoBaseId" name="infoBaseId" value="${form.id}" />
				<textarea class="zuo10" name="content1" id="content1" cols="79" rows="5"
						onfocus="this.value=(this.value=='文明上网，登陆评论！') ? '' : this.value;"
						onblur="this.value=(this.value=='') ? '文明上网，登陆评论！' : this.value;">文明上网，登陆评论！</textarea>
                <div class="shengming left">所有评论仅代表网友意见</div>
           	    <input class="right shang10 you10" type="image" src="${pageContext.request.contextPath}/images/detail_31.gif" name="button3" id="button3" value="提交" onclick="addComments();"/>
            </div>
        
        </div>
         -->
    </div>
  
  
  
  
    <div class="search_right right">
 
<div class="top10 left">
  <div class="top10_title" style="width: 150px">相关表格</div>
  <ul>
    <c:forEach items="${listForm}" var="listForm">
      <sec:authorize ifAllGranted="KNOWLEDGE_LOOK">
                   	<li ><a title=${listForm.title} href="${pageContext.request.contextPath}/detail/${listForm.infoType}/${listForm.id}" target="_blank">
                  		  ${listForm.title}
                   </a> </li>
	      </sec:authorize>
     </c:forEach>
  </ul>
</div>
 
<div class="clear"></div>
       <!-- a href="#"><img class="tiwen" src="${pageContext.request.contextPath}/images/cs_25.gif" /></a--></div>     
          
          <div class="clear"></div>
  </div>
  </div>
    <div class="search_down"></div>
    <div class="foot">
<!-- 联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号  -->
<jsp:include page="${pageContext.request.contextPath}/bottom.jsp" />

</div></div>
<div class="fixed"><div class="fixed_box">
<div class="shangxiaye">
 
    <div class="glgjc_box">
	    <a id="hotup"><img title="关键字高亮" class="shang10" src="/images/detail/gjc_03.gif" /></a>
	    <a class="glgjc" id="glgjc" href="javascript:void(0)">关键词</a>
	    <a id="hotnext"  href="javascript:void(0)"><img title="关键字不高亮" src="/images/detail/gjc_06.gif"/></a>
    </div>
</div>
</div></div>
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
<div class="fixed"><div class="fixed_box">
<div class="shangxiaye">
	<!-- <a class="shangyiye" href="#"></a>
    <a class="xiayiye" href="#"></a> -->
    <div class="glgjc_box">
    <!-- <a href="#"><img title="关键字高亮" class="shang10" src="/images/detail/gjc_03.gif" /></a>
    <a class="glgjc" href="#">关键词</a>
    <a href="#"><img title="关键字不高亮" src="/images/detail/gjc_06.gif"/></a> -->
    </div>
    </div>
</div>
</div>
<div class="guding">
<!-- 
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
 -->
<div class="btn_fd">
	<a href="#"></a>
	<a href="#top"><img title="返回顶部" class="shang20" src="${pageContext.request.contextPath}/images/btnfd_13.gif" /></a>
</div>
</div></div>
</body>
</html>
