<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><c:out value="${segment.title}"/></title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />

<link rel=stylesheet type=text/css href="css/search.css" />
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxbase.js"  type="text/javascript"></script>
<!-- <script src="${pageContext.request.contextPath}/js/js.js" type="text/javascript"></script> -->

<%-- <script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
<link href="${pageContext.request.contextPath}/jquery-ui-1.10.2/themes/base/jquery-ui.css" hrel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/ajaxbase.js" type="text/javascript"></script> --%>
<style type="text/css">
	.highlight{
	    background:#FF0;
 		padding:2px;
	}  
</style>
		
 

</head>

<body>
<div class="body">
 <input id="isindex" value="detail" type="hidden"/>
<div class="clear"></div>
  <div>
  <jsp:include page="${pageContext.request.contextPath}/top.jsp" />
  <jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />
  </div>
 <div class="search_up2"></div>   
  <div class="search_content_box">
  <div class="detail_book">
  <div class="weizhi">
          <div class="weizhi_shouye left"><a class="" href="${pageContext.request.contextPath}/jump.jsp" target="_blank">首页</a></div>
          <div class="weizhi_qita left">
          	<span class="xjt">></span><a href="${TagPath[1]}" target="_blank">${TagPath[0]}</a>
          	 <input type="hidden" id="schidden" value="" /> 
          	 <input type="hidden" id="isbtn" value="" /> 
          	  <input type="hidden" id="issearch" value="" /> 
                      
          </div>
 </div>
 <!-- 
 <div class="detail_search fix2 shang10">
               <input class="detail_search_input " type="text" name="textfield" id="textfield" value="文内检索" onfocus="if(this.value=='文内检索')this.value=''" onblur="if(this.value=='')this.value='文内检索'" />
               <input title="搜索" class="detail_search_btn" type="submit" name="button" id="button" value="" />
              <a href="#" title="上一个关键词"><img src="/images/detail/wnjs_03.jpg" /></a>
              <a href="#" title="下一个关键词"><img src="/images/detail/wnjs_05.jpg" /></a>
              <input title="关键词高亮开关，点击可切换" class="detail_search_light" type="submit" name="button" id="button" value="关键词高亮" />
  </div>
  -->
        <div class="clear"></div>
        <div class="search_content">
  <div class="search_right left" id="hideLeft">
  <input type="hidden" id="this_segment" value="${segment.id}"/>
  <input type="hidden" id="this_source" value="${segment.source}"/>
  <input id="userId" name="userId" type="hidden" value="${user.id}"/>
  <div class="detail_mulu" >
  <div class="detail_mulu_left " id="windowfix">
  
  </div>
  </div>
  	  <div class="clear"></div>

      <!-- a href="#"><img class="tiwen" src="${pageContext.request.contextPath}/images/cs_25.gif" /></a-->
      </div>


  	<div class="detail left" id="contentright">
    
    <div class="knowledge_context">
   	  
   	</div>
        
   	    
        <div align="center"><img src="${pageContext.request.contextPath}/images/detail_16.gif" width="694" height="16" /></div>
      	
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

<div class="fixed"><div class="fixed_box">
<div class="shangxiaye">
	<a class="shangyiye" href="javascript:void(0)" onclick="last();" id="shangyiye"></a>
    <a class="xiayiye" href="javascript:void(0)" onclick="next();" id="xiayiye"></a>
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
	<a href="#top"><img title="返回顶部" class="shang20" src="${pageContext.request.contextPath}/images/btnfd_13.gif" /></a>
</div>
</div></div>
 
<script type="text/javascript">
var segmentId = $("#this_segment").val();
var source = $("#this_source").val();

function getDir(source){
	var currentDate = new Date().toLocaleString();
	$(".detail_mulu_left").load("/knowledge_Dir/"+encodeURIComponent(source)+"?date='"+encodeURIComponent(currentDate)+"'",function(data){
		//alert($('#windowfix li').length<=1);
		if($('#windowfix li').length<=1){
			 $('#shangyiye').attr('class','shangyiye2');
			 $('#xiayiye').attr('class','xiayiye2');
			$('.detail_mulu').hide();
			$('#xiayiye').hide();
			$('#shangyiye').hide();
			$('#hideLeft').hide();	
			$('.detail').css({'width':'992px'});
			
		}
	});
}

if(source==""){//当来源为空时隐藏目录列表和上一页和下一页
	$('.detail_mulu').hide();
	$('#xiayiye').hide();
	$('#shangyiye').hide();
}



function check(){
	var userId = $("#userId").val();
	var fileId = $("#visitId").val();
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


this.getContextClick(segmentId);
	//$(".knowledge_context").load("/knowledge_Detail/2/"+segmentId,function(data){});
this.getDir(source);
$('#windowfix li a').unbind("click");

var lens = $('#windowfix li').length;//目录长度

if(lens==0){//目录长度为0时隐藏目录和上一页和下一页
	//$('.detail_mulu').hide();
	//$('#xiayiye').hide();
	//$('#shangyiye').hide();
}
/**
 * 点击目录事件的时候调用的函数
 */
function getContextClick(id){
	 $('#windowfix li a').unbind("click");
	var len = $('#windowfix li').length;
	for(var i=0;i<len;i++){
		vid = $('#windowfix li').eq(i).children('a').eq(0).attr('id');
		if(vid==id){
			$('#shangyiye').attr('class','shangyiye');
			$('#xiayiye').attr('class','xiayiye');
			if(i==len-1){
				$('#xiayiye').attr('class','xiayiye2');
			}
			if(i==0){
				$('#shangyiye').attr('class','shangyiye2');
			}
			break;
		}
	}
	getContext(id);
 
}
	
var nowId = $('#this_segment').val();
function getContext(id){
	var currentDate = new Date().toLocaleString();
	$(".knowledge_context").load("/knowledge_Detail/2/"+id+"?date='"+encodeURIComponent(currentDate)+"'",function(data){});
	//隐藏等待图片
	//tywAjaxComplete("","");
	
	nowId = id;
}



/**
 * 点击下一页事件调用的函数
 */
function next(){
	//如果为灰色的就返回
	 $('#hotup').unbind("click"); 
	$('#hotnext').unbind("click"); 
	  $("#isbtn").val("");
	if($('#xiayiye').attr('class')=='xiayiye2'){
		return;
	}
	var len = $('#windowfix li').length;
	var nextId = '';
	var nextIndex = 0;
	var vid = ''; 
	for(var i=0;i<len;i++){
		vid = $('#windowfix li').eq(i).children('a').eq(0).attr('id');
		if(vid==nowId){
			nextIndex = i+1;
			nowId=$('#windowfix li').eq(nextIndex).children('a').eq(0).attr('id');
			$('#xiayiye').attr('class','xiayiye');
			if(i==len-2){
				$('#xiayiye').attr('class','xiayiye2');
			}
			if(i!=1){
				$('#shangyiye').attr('class','shangyiye');
			}
			break;
		}
	}
 	this.getContext(nowId)
}
function hotnext(){
	//如果为灰色的就返回
	 
	if($('#xiayiye').attr('class')=='xiayiye2'){
		return;
	}
	var len = $('#windowfix li').length;
	var nextId = '';
	var nextIndex = 0;
	var vid = ''; 
	for(var i=0;i<len;i++){
		vid = $('#windowfix li').eq(i).children('a').eq(0).attr('id');
		if(vid==nowId){
			nextIndex = i+1;
			nowId=$('#windowfix li').eq(nextIndex).children('a').eq(0).attr('id');
			$('#xiayiye').attr('class','xiayiye');
			if(i==len-2){
				$('#xiayiye').attr('class','xiayiye2');
			}
			if(i!=1){
				$('#shangyiye').attr('class','shangyiye');
			}
			break;
		}
	}
	 
 
  	this.getContext(nowId)
}
/**
 * 点击上一页事件调用的函数
 */
function last(){
	 $('#hotup').unbind("click"); 
	  $('#hotnext').unbind("click"); 
	$("#isbtn").val("");
 	if($('#shangyiye').attr('class')=='shangyiye2'){
		return;
	}
	var len = $('#windowfix li').length;
	var nextId = '';
	var nextIndex = 0;
	var vid = ''; 
	for(var i=0;i<len;i++){
		vid = $('#windowfix li').eq(i).children('a').eq(0).attr('id');
		if(vid==nowId){
			nextIndex = i-1;
			nowId=$('#windowfix li').eq(nextIndex).children('a').eq(0).attr('id');
			$('#shangyiye').attr('class','shangyiye');
			if(i==1){
				$('#shangyiye').attr('class','shangyiye2');
			}
			if(i!=len-2){
				$('#xiayiye').attr('class','xiayiye');
			}
			break;
		}
		
	}
	this.getContext(nowId)
}
$("#pdsousuo").click(function(){
	$("#pdsousuo").click();
	   //$("#search_pd_shuru").focus().blur();
});
function hotup(){
	 $('#hotup').unbind("click"); 
	  $('#hotnext').unbind("click"); 
 	if($('#shangyiye').attr('class')=='shangyiye2'){
		return;
	}
	var len = $('#windowfix li').length;
	var nextId = '';
	var nextIndex = 0;
	var vid = ''; 
	for(var i=0;i<len;i++){
		vid = $('#windowfix li').eq(i).children('a').eq(0).attr('id');
		if(vid==nowId){
			nextIndex = i-1;
			nowId=$('#windowfix li').eq(nextIndex).children('a').eq(0).attr('id');
			$('#shangyiye').attr('class','shangyiye');
			if(i==1){
				$('#shangyiye').attr('class','shangyiye2');
			}
			if(i!=len-2){
				$('#xiayiye').attr('class','xiayiye');
			}
			break;
		}
		
	}
	this.getContext(nowId)
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
/**********相关搜索********************/
function stos(url){
	 window.open(url);
	// location.href=url;
}

/* window.onload=function(){
    map_width=document.body.clientWidth;//获取body宽度
    map_height=document.documentElement.clientHeight-80;//获取页面可见高度
    //alert(map_width);
    //alert(map_height);
    //下面两行最后的"px"必须加上,折腾了半天老是不显示添加的style,最后就是错在这了
    document.getElementById("windowfix").style.height=map_height+"px";
    
} */

function collectBookmark() {
	var userId = $("#userId").val();
	var kId = $('#visitId').val();
	//alert(kId);
	//return ;
	// 收藏地址
	var url = window.location.href ;
	// 获取隐藏的变动的知识ID拼接成新的url
	url = url.substring(0, url.lastIndexOf("/")+1) + kId;
	//alert(url);
	//return;
	var title = $('#title02').val() ;
	//alert(url + '******' + title + '******' + kId) ;
	//return;
	
	if(userId == null || userId == ''){
		$("#loginDiv").fadeIn(1000);
   		$("#loginDiv").fadeOut(1000);
   	    setTimeout("location.href='/login.jsp'",2000);
	}else{
		$.post('${pageContext.request.contextPath}/collectBookmark', { bUrl: url, bTitle: title, kId: kId}, function(result) {
			if(result.success) {
				$("#collectImg").fadeIn(800).slideUp();
      		    $("#collectImg").fadeOut(800);
      		  var addCollect = parseInt($("#collectCountId").text())+1;
     		    $("#collectCountId").html(addCollect);
			} else if(result.fail) {
				$("#collectDiv").fadeIn(1000);
	       		$("#collectDiv").fadeOut(1000);
			}
		}, 'json') ;
	}
	
}
function showheight(){
	var scval=$("#search_pd_shuru").val();
		$("#issearch").val(0);
		if($.trim(scval)!=""){
			
			 $(".highlight").removeClass("highlight").removeAttr("style");
			 if(scval.indexOf(" ")>=0){
  			 var scvals=$.trim(scval).split(" ");
 			 for(var q=0;q<scvals.length;q++){
					if($.trim(scvals[q])!=""){
 						$(".wenzi").highlight($.trim(scvals[q]));
						}
			 }
			 }
			if(scval.indexOf("+")>=0){
 			var scvalsum=$.trim(scval).split("+");
		    for(var jq=0;jq<scvalsum.length;jq++){
		    	if($.trim(scvalsum[jq])!=""){
 					$(".wenzi").highlight($.trim(scvalsum[jq]));
		    	}
		    }
			}else{
 				  $(".wenzi").highlight(scval);
			}
		 	 $(".glgjc_box").show();
	} 
 
}

$(document).ready(function(){
	
	 $('#windowfix li a').live("click",function(){
		   getContextClick(this.id)
		   $("#issearch").val(1);
		   $('#hotnext').unbind("click"); 
       	   $('#xiayiye').unbind("click");
		   $('#windowfix li a').unbind("click");
		   $(".knowledge_context").ajaxStop(function(){
 			  showheight();
			 //  $("#search_pd_shuru").focus().blur();
		 	});
			
	})
	
	 var lilength=$("#stos>li").length;
 	 for(var i=15;i<lilength;i++){
 		   $("#stos li:eq("+i+")").hide();
  	 }
  	 //初始化目录
 	map_width=document.body.clientWidth;//获取body宽度
    map_height=document.documentElement.clientHeight-65;//获取页面可见高度
    document.getElementById("windowfix").style.height=map_height+"px";

	/**如果关键字为‘’空的话，就隐藏关键字高亮的DIV**/
    if($("#keyword").val()==''||$("#keyword").val()==null){
		$(".glgjc_box").hide();
    }
})

</script>
</body>

</html>
