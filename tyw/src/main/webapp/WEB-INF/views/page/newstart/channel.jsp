<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新频道测试</title>
  		<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
		 		 <script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
		 <script  type="text/javascript" src="${pageContext.request.contextPath}/js/seleted.js" ></script>
	 	 <script  type="text/javascript" src="${pageContext.request.contextPath}/js/cookie.js" ></script>
   	 	<script  type="text/javascript" src="${pageContext.request.contextPath}/js/top_search_load.js" ></script>
   	 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.pagination.js"  ></script>
   	 	
 		 <link href="${pageContext.request.contextPath}/newstart/css/basic.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/css/pagination.css" rel="stylesheet" type="text/css" />
 		<link rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/home.css" />
        <link rel=stylesheet type=text/css href="${pageContext.request.contextPath}/jquery-ui-1.10.2/themes/base/jquery.ui.all.css" />
       <script type="text/javascript" src="http://counter.sina.com.cn/ip/" charset="gb2312"></script> 
       
       <style>
       	   #tagSearch h4{ margin:0;}
           #tagSearch h4 a{ margin:0; color:#999999;font-size:14px; font-family:"microsoft yahei";margin-right:10px; }
	       #tagSearch .hover{
	       		color:green; background:#; border-bottom:1px solid green; 
	       }
      </style>  
</head>
 <script type="text/javascript" >

 $(window).load(function(){
  	$("#keyword").autocomplete({
		source: function(request,response){
			$.ajax({
	            type: "POST",
	            contentType: "application/json; charset=utf-8",
				url: encodeURI("${pageContext.request.contextPath}/list/suggest/"+$("#keyword").val()),
                dataType: "json",
                data: request,
                global: false,//屏蔽全局事件
                success: function(data) {
                     response(data);
                }
           })
		}
	});
});
 $(document).ready(function(){

  	     gettagSearch(1,1);
		var inputlen="";
		var isindex="";
		try
		{
			inputlen=$(".new_search .table2 input[type='text']").length
			isindex=$("#isindex").val();
			if(isindex=="isindex"){
  				$(".search_qz").css("display","block");
				return;
			}if(isindex=="detail"){
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
  			}else{
 		 		  $("#NavigationSearch").css("display","none");
				  $("#keyword").css("display","none");
				  $("#search_btn").css("display","none");
				  $("#konwledgeType7").css("display","none");
				  $("#search_pd_shuru").css("display","block");
				  $("#pdsousuo").css("display","block");
				  $("#dropDownList1").css("display","block");
				  $("#searchCondition").css("display","none");
				 if(inputlen>1){
	 				 $("#pdgaoji").live("click",function(){
	 	 					$("#searchCombination").dialog({
	 	 						autoOpen : true,
	 	 						width : 600,
	  	 						modal : true,
	  	 					    close: function() { 
		  	 					     $(".new_search").html($("#searchCombination").formhtml());
									 $("#searchCombination").html("");
	  	 					    } 
	 	 			 		});
	 	 					$("#searchCombination").parent().css("position", "fixed");
	 	 			 		$("#searchCombination").parent().css("top",$(window).height()/2+"px");
							$("#searchCombination").html($(".new_search").formhtml());
							$(".new_search").html("");
							$("#new_chaxun").click(function(){
								 $("#searchCombination").dialog('close');
								 sub();
							});
	 	 			 });
					 $("#pdgaoji").css("display","block");
				 }else{
	 				  $("#pdgaoji").css("display","none");
				}
			}
		}catch(err){
 			$(".search_qz").css("display","block");
		};
		 $("#search_pd_shuru").live("keydown",function(e){
				if($("#search_pd_shuru").val()!=""){
					var keynum = window.event ? e.keyCode : e.which;
					if(keynum==13){
							$("#pdsousuo").click();
						}
						 
				}
			});
})
   //全站和频道搜索切换
function changesearch(station){
   	if(station=="全站搜索"){
 		  $("#pdgaoji").css("display","none");
		  $("#konwledgeType7").css("display","block");
		  $("#keyword").css("display","block");
		  $("#search_btn").css("display","block");
		  $("#search_pd_shuru").css("display","none");
		  $("#pdsousuo").css("display","none");
		  $("#NavigationSearch").css("display","block");
		  $("#searchCondition").css("display","block");
		  $("#keyword").val($("#search_pd_shuru").val());
  	}if(station=="频道搜索"){
		var inputlen=$(".new_search .table2 input[type='text']").length
	  	  if(inputlen>1){
		  	  $("#pdgaoji").css("display","block");
	  	  }
		  $("#searchCondition").css("display","none");
	  	  $("#NavigationSearch").css("display","none");
		  $("#konwledgeType7").css("display","none");
 		  $("#keyword").css("display","none");
		  $("#search_btn").css("display","none");
		  $("#search_pd_shuru").css("display","block");
		  $("#pdsousuo").css("display","block");
		  $("#search_pd_shuru").val($("#keyword").val());
 	}if(station=="页内搜索"){
 		  $("#search_btn").val("页内搜索");
		  $("#searchCondition").css("display","none");
	  	  $("#NavigationSearch").css("display","none");
		  $("#konwledgeType7").css("display","none");
 		  $("#keyword").css("display","none");
		  $("#search_btn").css("display","none");
		  $("#search_pd_shuru").css("display","block");
		  $("#pdsousuo").css("display","block");
		  $("#search_pd_shuru").val($("#keyword").val());
	}
}

var tabTag=1;
//查询标签
function gettagSearch(istag,n) {
	tabTag=n;
  	var areaurl = '${ctx}/tag/findsearchhotword';
   	$.post(areaurl,{"istag":istag-1}, function(result) {
		var html = '';
 		html += "<table >";
		html += "<tr>";
		$.each(result, function(i) {
			if (i % 5 == 0) {
				html += "</tr>";
			}
			if (i % 5 == 0) {
				html += "<tr>";
			}
			if(this.keyword!=null){
	  			html += "<td style='width:200px;'>";
				html += "<a id=search_" + this.id+ " href='javascript:addtagSearchValue(\""+this.id+"\")'>" + this.keyword + "</a>";
				html += "</td>";
			}
		});
		html += "</tr>";
		html += "</table>";
 		$("#tagSearch_"+n).html(html);
 	}, 'json');
 	if(n==1){
 	 	$("#tagSearch_1").ajaxStop(function(){
  	 		 for(var js=0;js<$("#searchCondition>a").length;js++){
				for(var jq=js;jq<$("#tagSearch_1 a").length;jq++){
 					if($("#searchCondition>a:eq("+js+") span").html()==$("#tagSearch_1 a:eq("+jq+")").html()){
 				 	 	$("#tagSearch_1 a:eq("+jq+")").attr("href","javascript:void(0)");
			 		}
				}
		 	 }
		 	 $("#tagSearch_1").unbind();
	 	})
 	}
}
 


//操作条件框
var tagSearchArray=new Array();
var hidAddre="";
function addtagSearchValue(op) {
 		if(tabTag==1){
			var si=$("#searchCondition a").length;
			if(5>si){//最多选择5个
				 $("#search_" + op).attr("href","javascript:void(0)");
					var addTagval=$("#search_" + op).text();
					var taglenVal="";
					tagSearchArray[tagSearchArray.length]=addTagval;
					hidAddre=$("#searchCondition").html();
 				 		if(hidAddre==""){
				 			hidAddre="<a  id='op_"+op+"' onclick='javascript:deltagSearchValue(\""+addTagval+"\",\""+op+"\")'><span>"+addTagval+"</span></a>";
						}else{
							hidAddre=hidAddre+"<a id='op_"+op+"' onclick='javascript:deltagSearchValue(\""+addTagval+"\",\""+op+"\")'><span>"+addTagval+"</span></a>";
						}
						
			   		$("#searchCondition").html(hidAddre);
				 	for(var taglen=0;taglen<$("#searchCondition a").length;taglen++){
				 	 		if(taglenVal==""&&$("#searchCondition a:eq("+taglen+")").text()!=""){
				 	 	 		taglenVal=$("#searchCondition a:eq("+taglen+")").text();
				 	 	 		continue;
				 	 	 	 }if(taglenVal!=""&&$("#searchCondition a:eq("+taglen+")").text()!=""){
			 	  	 	 		taglenVal=taglenVal+"OR"+$("#searchCondition a:eq("+taglen+")").text();
				 	 	 	 }
					}
		 	 	  	$("#tag_name").val(taglenVal);
			  		 	setCookie("tagSearchArray",  $("#searchCondition").html(),1);
			}  
		}if(tabTag==2){
			$("#keyword").val($("#search_" + op).text());
		}
   }
 //删除条件搜索
 var delhidAddre="";
function deltagSearchValue(tsas,op){
	var taglenVal="";
 	    tagSearchArray.splice($.inArray(tsas,tagSearchArray),1);//删除元素
		if(delhidAddre==""){
			delhidAddre="<a href=\"#\" id='op_"+op+"' onclick='javascript:deltagSearchValue(\""+tsas+"\",\""+op+"\")'><span>"+tsas+"</span></a>";
		}else{
			delhidAddre=delhidAddre+"<a href=\"#\" id='op_"+op+"' onclick='javascript:deltagSearchValue(\""+tsas+"\",\""+op+"\")'><span>"+tsas+"</span></a>";
		}
		 
	   	$("#op_"+op).remove();
	   	hidAddre=$("#searchCondition").html();
	   
	 	for(var taglen=0;taglen<$("#searchCondition a").length;taglen++){
 	 	 	 	if(taglenVal==""&&$("#searchCondition a:eq("+taglen+")").text()!=""){
	 	 	 		taglenVal=$("#searchCondition a:eq("+taglen+")").text();
	 	 	 		continue;
	 	 	 	 }if(taglenVal!=""&&$("#searchCondition a:eq("+taglen+")").text()!=""){
	  	 	 		taglenVal=taglenVal+"OR"+$("#searchCondition a:eq("+taglen+")").text();
	 	 	 	 }
 		}
 	$("#search_" + op).attr("href","javascript:addtagSearchValue(\""+op+"\")");
  	$("#tag_name").val(taglenVal);
  	setCookie("tagSearchArray",null,1);
  	setCookie("tagSearchArray", $("#searchCondition").html(),1);
}
 
function getProvince(cityVals) {
 	//这里去加载标签树
	var areaurl = '${ctx}/tag/getProvinceTag';
	var pv="";
 	$.post(areaurl, function(result) {
		var html = '';
 		html += "<table>";
		html += "<tr>";
		$.each(result, function(i) {
			if (i % 5 == 0) {
				html += "</tr>";
			}
			if (i % 5 == 0) {
				html += "<tr>";
			}
			if(this.name==cityVals){
				pv="<font color='red'>"+this.name+"</font>";
			}else{
				pv=this.name;
			}
			html += "<td style='width:120px;'>";
			html += "<a id='pid_" + this.id
					+ "' href='javascript:getProvinceValue(\""+this.id+"\")'>" + pv + "</a>";
			html += "</td>";
		});
		html += "</tr>";
		html += "</table>";
		$("#provinceArea").html(html);
	}, 'json');
}
function getProvinceValue(op) {
  	//$.cookie("provinceVal", $("#pid_" + op).text());
 	setCookie("provinceVal", $("#pid_" + op).text(),1);
  	$("#province").html($("#pid_" + op).text());
	$('#provinceArea').dialog('close');
	try
	{
		getDataView();
	}catch(err){
		
	}
}

function goLuxurySearch(){
 		location.href='${pageContext.request.contextPath}/goLuxurySearch?typeId='+typeId+'';
}
function onkeyWordVal(valkeyword){
		if(valkeyword=="输入要查找的内容"){
		$("#keyword").val("");
	}
}
 

var isclickform=true;
var iskdform=true;
function isclick(){
 	var keyVal=$("#keyword").val();
 	if(keyVal=="输入要查找的内容"||keyVal=="?"||keyVal=="*"||keyVal=="?*"){
		$("#keyword").val("输入要查找的内容");
		isclickform=false;
	}
}
//回车
function onkeyword(e){
	if($("#keyword").val()==""){
		var keynum = window.event ? e.keyCode : e.which;
		if(keynum==13)
			iskdform=false;
	}
}

function issub(){
	if(isclickform==false||iskdform==false){
		 isclickform=true;
		 iskdform=true;
		 return false;
	}
}
function setTabs(name,cursel,n){
 	for(var i=0;i<=n;i++){
				var menu=document.getElementById(name+i);
				con=document.getElementById("con_"+name+"_"+i);
				menu.className=i==cursel?"hover":"";
				if(menu.className=="hover"){
					if(i==0){//全部
						$("#typeId").val(0);
						$(".type_choose").css("display","none");
					}
					if(i==1){//文库
						$(".type_choose").css("display","block");
 							$("#typeId").val(100);
					}
					if(i==2){//表格
						$("#typeId").val(4);
						$(".type_choose").css("display","none");
					}
					if(i==3){//图片
						$("#typeId").val(5);
						$(".type_choose").css("display","none");
					}
					if(i==4){//视频
						$("#typeId").val(3);
						$(".type_choose").css("display","none");
					}
					//if(i==5){//ppt
						//$("#typeId").val(6);
						//$(".type_choose").css("display","none");
				//	}	
					
					//if(i==6){//ppt
						//$("#typeId").val(6);
					//}
 					$("#onex").val(name+i);
				}
		}
}

 
 $(".type_choose label input").live("click",function(){
	 var radioId=this.id;
   	if($("#"+radioId).attr("checked")=="checked"){
    		$("#typeId").val($("#"+radioId).val());
	}
 })
function hotTag(name,cursel,n){
		for(i=1;i<=n;i++){
	  		var menu=document.getElementById("hotTagword"+i);
	 		var con=document.getElementById(name+"_"+i);
	 		menu.className=i==cursel?"hover":"";
 	 		con.style.display=i==cursel?"block":"none";
 		}
		  if(cursel==1){
 			  gettagSearch(1,1);
	 	  }else if(cursel == 2){
 	 		 gettagSearch(2,2);
		 } 
	}
 
	var items_per_page = 15;
	var page_index = 0;
	var flag = 1;
 $(document).ready(function(){
	  $("#menufbfx span a").mouseover(function(){
	 		var menuname=$(this).attr("name");
			var contentfbfx=$("#contentfbfx div[name='"+menuname+"']").html();
			  $(this).parent().parent().parent().next().find('.subitem').html(contentfbfx);
	 });
	  $("#menufbfx span a").mouseover(function(){
			var menuname=$(this).attr("name");
			var contentfbfx=$("#contentfbfx div[name='"+menuname+"']").html();
			$(this).parent().parent().parent().next().find('.subitem').html(contentfbfx);
	}); 
	  $("#menufbfx span a").click(function(){
			var menuname=$(this).attr("name");
			var contentfbfx=$("#contentfbfx div[name='"+menuname+"']").html();
			//alert($(this).attr("data"));
		  $.post('/goChanneltagname',{tag_name:$(this).attr("data")},function(result){
			    var resultVal=result.substring(8,result.length-1);
 			    $("#lastpath").val(resultVal);
 			});
		  	getDataList(1);
			$(this).parent().parent().parent().next().find('.subitem').html(contentfbfx);
	});
	  getDataList(page_index);
 });
 	 var province ="";
	function getDataList(index) {
  		var title = $("#title").val();
		var pageIndex = index;
		province=getCookie("provinceVal");
		var zsxzpath = $("#zsxzpath").val();
   		$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/dolistBaseBy",
					data : {
						"pageIndex" : (pageIndex + 1),
						"items_per_page" : items_per_page,
						"fbfx" : $("#lastpath").val(),
						"title" : title,
						"province" : province,
						"zsxzpath" : zsxzpath,
						"infoType":"${type}"
					},
					dataType : 'json',
				 //	beforeSend:tywBeforeSend,                    
				//	complete: tywAjaxComplete,
					success : function(msg) {
						var total = msg.total; 
						var html = "";
  						$.each(msg.result,function(i, n) {
 										//	var dAccess = $('#dAccess').val();//访问权限

html+="<li><div class=\"list-content\"><img class=\"left\" src=\"${pageContext.request.contextPath}/newstart/images/newstart_61.gif\" /><div class=\"list-detail\">";
html+="<h4>";
html+="<div class=\"left\">";
if(n.infoType == 1){
	html += "<img src='${pageContext.request.contextPath}/images/bg/bigfile.gif' />";
}
if(n.infoType == 2){
	html += "<img src='${pageContext.request.contextPath}/images/bg/segment.gif' />";
}
if(n.infoType == 3){
	html += "<img src='${pageContext.request.contextPath}/images/bg/video.gif' />";
}
if(n.infoType == 4){
	html += "<img src='${pageContext.request.contextPath}/images/bg/table.gif' />";
}
if(n.infoType == 5){
	html += "<img src='${pageContext.request.contextPath}/images/bg/picture.gif' />";
}
html += "&nbsp;<a href='javascript:void()' title='"+n.title+"'>"+ n.title + "</a>";
html +="</div>";
html +="<div class=\"right\"><img src=\"${pageContext.request.contextPath}/newstart/images/newstart_67.gif\" /> " +n.modifyDate+"</div>";
html +="</h4>";
html+="<p>12.11.1 应按出厂使用说明书要求接通切割机的电源，并应做好保护接地或接零。 12.11.2 作业前，应先空运转，检查并确认氧乙炔和加装的仿形样板配合无误后，开始切割作业。 12.11.3 作业后，应清理保养设备，整理并保管好氧气缆线。</p>";
html+="<div class=\"left\">来源:"+n.source+"</div>";
html+="<div class=\"right\"><img src=\"${pageContext.request.contextPath}/newstart/images/newstart_78.gif\" />3232<img src=\"${pageContext.request.contextPath}/newstart/images/newstart_75.gif\" />223<img src=\"${pageContext.request.contextPath}/newstart/images/newstart_72.gif\" />99004</div>";
html+="<div class=\"clear\"></div>";
html+=" </div></div></div><img src=\"${pageContext.request.contextPath}/newstart/images/newstart_90.gif\" />";
html+="</div></li>"
 					
						//分页-只初始化一次
						 if ($("#Pagination").html().length == '') {
							$('#Pagination').pagination(total, {
								'items_per_page' : items_per_page,
								'num_display_entries' : 5,
								'num_edge_entries' : 2,
								'prev_text' : "上一页",
								'next_text' : "下一页",
								'callback' : pageselectCallback
							});
						} 
					 
				});
 						$('#searchResultList').html(html);
	}
 		})}
	function pageselectCallback(page_index, jq) {
		if (page_index == 0 && flag == 1) {
			flag = 0;
		} else {
			getDataList(page_index);
		}
	}
 </script>
<body>
<a name="top" id="top"></a>
<input class="xmm_input_s width90" type="hidden" name="title" id="title" /> 
<input class="xmm_input_s width90" type="hidden" name="lastpath" id="lastpath" /> 
<input class="xmm_input_s width90" type="hidden" name="zsxzpath" id="zsxzpath" value="${rootCode}" />
<div class="top-all">
 
 <jsp:include page="${pageContext.request.contextPath}/top_new.jsp" />    
</div>
<div class="search-all">
	<div class="search-box">
    	<img class="logo" src="${pageContext.request.contextPath}/newstart/images/newstart_06.gif" />
        <div class="page-info">
        	<p>分部分项</p>
            <span>专业的建筑服务平台</span>
        </div>
        <div class="location"> <a id="province"><span>dd</span></a> </div>
          <div class="now_good left">
      <h3>
      </h3> 
         <div id="tagSearch" class="fcontrol" title="选词助手：最多选择5个词" style="font:12px;width: 600px; height:800px; display: none;cursor:help;line-height:30px;">
			    	<h4 class="front"><a id="hotTagword1" onClick="hotTag('tagSearch',1,2)"  class="hover" href="#">热标签</a> <a id="hotTagword2"  onclick="hotTag('tagSearch',2,2)" href="#">热词</a> </h4>
			        <ul id="tagSearch_1">
			        	 
			        </ul>
			         <ul id="tagSearch_2" >
			        	 
			        </ul>
		 </div>
        
         
     	<div id="provinceArea" class="fcontrol" title="省份"
											style="font:15px宋体;width: 250px; height:350px; display: none;cursor:help;line-height:30px;"></div>
 </div>
        <div class="search-area">
        
        
      
        
       <form method="get" action="${pageContext.request.contextPath}/goPageList" name="searchform" id="searchform" onSubmit="return issub()">
 <div class="search_qz"  >
<div class="whitetxt2">
<span id="NavigationSearch">
<div id="konwledgeType0" onClick="setTabs('konwledgeType',0,4)"  class="hover" ><img src="${pageContext.request.contextPath}/newstart/images/newstart_12.gif" />全部</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType1" onClick="setTabs('konwledgeType',1,4)"   ><img src="${pageContext.request.contextPath}/newstart/images/newstart_09.gif" />文库</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType2" onClick="setTabs('konwledgeType',2,4)"  ><img src="${pageContext.request.contextPath}/newstart/images/newstart_15.gif" />表格</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType3" onClick="setTabs('konwledgeType',3,4)" ><img src="${pageContext.request.contextPath}/newstart/images/newstart_17.gif" />图片</div>
  <div  style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType4" onClick="setTabs('konwledgeType',4,4)" ><img src="${pageContext.request.contextPath}/newstart/images/newstart_20.gif" />视频</div>
  <!--  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC">|</div>
 <div id="konwledgeType5" onclick="setTabs('konwledgeType',5,6)" >文档</div> 
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>-->
   <!--<div id="konwledgeType5" onclick="setTabs('konwledgeType',5,5)" >PPT</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
   <div id="konwledgeType5" onClick="setTabs('konwledgeType',5,5)" >PPT</div>-->
  <!--<div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>-->
    </span>
</div>
<div class="clear"></div>
          <div class="search_input2">
            <input id="onex"  type="hidden"  value="konwledgeType0" name="onex"/>
			<input id="typeId"   type="hidden"  name="typeId"/>
          <input id="tag_name" value="" type="hidden" name="tag_name" />
           <input id="pageNo" value="1" type="hidden" name="pageNo" />
          <input class="shurukuang2" id="keyword" value="${keyword}" size="40" type="text" name="keyword" 
          	x-webkit-speech="" x-webkit-grammar="builtin:translate" 
          			onfocus="onkeyWordVal(this.value)" onKeyDown="return onkeyword(event)"
					onblur="this.value=(this.value=='') ? '输入要查找的内容' : this.value;"/>
					
		  <input style="display: none;" class="shurukuang2" id="search_pd_shuru" value="${keyword}" size="40" type="text" name="keyword" 
          	x-webkit-speech="" x-webkit-grammar="builtin:translate"/>
					
		<!-- <div id="dropDownList1" class="dropdown pd_search" style="display: none;z-index: 999999999999999999" >
        <select id="selectpq" style="display:none;" >
			<option value="pdss" id="pdssval">频道搜索</option>
			<option value="qzss">全站搜索</option>
		</select>
		<span value="pdss" id="pdss"> </span>
		
           <ul>
          </ul>
        </div> -->
<div class="selectBox" id="dropDownList1"  style="display:none; " >
	<a href="javascript:void(0);" id="selectpq" >请选择类别</a><p>
    	<a href="javascript:void(0)" class="current" value="" >请选择类别</a>
    	<a href="javascript:void(0)" id="pdssval" value="频道搜索">搜频道</a>
        <a href="javascript:void(0)" value="全站搜索">搜全站</a>
    </p><input type="hidden" name="category" id="category" value="no" />
</div>
        
          <input class="search_btn" id="search_btn"  value="搜全站"  type="submit"  onclick="isclick()" style="vertical-align:middle;" />
          <input class="search_btn" id="pdsousuo"   value="搜频道"  type="button"   style="vertical-align:middle;display: none" />
         
          <input class="search_gaoji" type="button" name="button" id="konwledgeType7" value="高级" />
          <input class="search_gaoji" type="button" name="button" id="pdgaoji" style="display:none" value=" " />
          
        </div>
        
        		<!--  <div class="tansuo"><a  href="${pageContext.request.contextPath}/goLuxurySearch?typeId=0&onex=konwledgeType0" >探索？</a></div> --> 
  </div>
  
 <div class="search_pd left" style="display: none;" id="search_pd">
 <!--  <input class="search_pd_shuru" type="text" name="textfield" id="search_pd_shuru" />
  <input class="search_pd_sousuo" type="button" name="button" id="pdsousuo" value=" " />  
  <input class="search_pd_gaoji" type="button" name="button" id="pdgaoji" style="display:none" value=" " /> -->

</div>
 <div class="type_choose" style="display: none">	
          <label>
            <input type="radio" name="RadioGroup1" checked="checked" value="100" id="RadioGroup1_0" />
            全部</label>
          <label>
            <input type="radio" name="RadioGroup1" value="2" id="RadioGroup1_1" />
        TXT</label>
          <label>
            <input type="radio" name="RadioGroup1" value="1" id="RadioGroup1_2" />
         WORD</label>
  		<label>
            <input type="radio" name="RadioGroup1" value="6" id="RadioGroup1_3" />
          PPT</label>
      </div>
  </form>



        </div>
        <div class="clear"></div>
          
    </div>
    
</div>
<div class="search-tag"><div class="sousuo_xia" id="searchCondition"></div></div>
<div class="nav">
	<div class="nav-box">
    	<ul>
        	<li class="present">	首页  </li><li>分部分项</li>	<li>	法律法规  </li>	<li>	规范信息</li>	<li>  	图集信息 </li>	<li> 	培训课程  </li>	<li>	执业考试</li>	<li> 	 	施工专题 </li>	<li> 	 视频•图片  	 </li>	<li> 文化论文  	</li>	<li>  专利技术 </li>	<li> 	  学生专区
</li>
        </ul>
    </div>
</div>

<div class="content">
	<div class="content-left">
    	<div class="stepup">
        	<img src="${pageContext.request.contextPath}/newstart/images/newstart_37.gif" />
           <jsp:include page="${pageContext.request.contextPath}/channel_menu.jsp" />
         
 
        </div>
        <div class="step-other">
            <jsp:include page="${pageContext.request.contextPath}/zsxz_menu.jsp" />
 
        </div>
	</div>
    <div class="content-right">
    	<div class="zsxz">
        	<h3>资料类型检索 <span>(非必选项)</span></h3>
            <ul>
     			<li><a  class="present"  href="/goChannel?rootCode=40201000000000000">强制性条文</a></li>
               <li><a    href="/goChannel?rootCode=40302000000000000">施工方案</a></li>
	    	  <li><a class="hover"    href="/goChannel?rootCode=40502000000000000">质量控制</a></li>
		      <li><a class="hover"  href="/goChannel?rootCode=40603000000000000">新规范表格</a></li>
		      <li><a class="hover"  href="#">规范条文</a></li>
		      <li><a  href="/goChannel?rootCode=40303000000000000">技术交底</a></li>
		      <li><a class="hover"    href="/goChannel?rootCode=40402000000000000">经验技巧</a></li>
		      <li><a class="hover"  href="/goChannel?rootCode=40501000000000000">质量检验</a></li>
		      <li><a class="hover"  href="/goChannel?rootCode=40503000000000000">质量通病</a></li>
		      <li><a class="hover"  href="/goChannel?rootCode=40601000000000000">工程资料</a></li>

               
                <div class="clear"></div>
            </ul>
        </div>
        
        <div class="thelist">
        	<h3>
        	  <div class="left">对结果进行排序 <img src="${pageContext.request.contextPath}/newstart/images/newstart_52.gif" /></div>
        	  <div class="right"><img src="${pageContext.request.contextPath}/newstart/images/newstart_55.gif" />按浏览量<img src="${pageContext.request.contextPath}/newstart/images/newstart_49.gif" />按更新时间</div>
            </h3>
        	<ul id="searchResultList">
             
            </ul>

			<div class="scott">
			<div id="Pagination" ></div>
			</div>
        </div>
    </div>
    <div class="clear"></div>
</div>

<div class="your-position" style="display: none">
	<div class=" your-positon-box"><img src="${pageContext.request.contextPath}/newstart/images/fhx_102.png" /> 已选择的分部分项：地基与基础工程 > 土方工程 > 土石方爆破 > 爆破工程监测</div> 
</div>
<div class="back2top"><a href="#top"><img src="${pageContext.request.contextPath}/newstart/images/top_94.gif" /></a></div>
<div class="foot">
<div class="foot-box">
<div class="left"><img src="${pageContext.request.contextPath}/newstart/images/qq_112.gif" /> 联系我们 | 移动客户端 | 网站地图 | 您的意见</div>
<div class="right">天佑网版权所有　:copyright:2013　京ICP备13028869-1号 版本号 V1.8</div>
</div>
</div>

</body>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/geoip.js" charset="gb2312"></script> 
  <script src="${pageContext.request.contextPath}/js/apprise-1.5.full.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/jquery.highlight-3.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/channel_highlight.js" type="text/javascript"></script>
 <script type="text/javascript">
(function($) {
    var oldHTML = $.fn.html;
    $.fn.formhtml = function() {
        if (arguments.length) return oldHTML.apply(this,arguments);
        $("input,textarea,button", this).each(function() {
            this.setAttribute('value',this.value);
        });
        $(":radio,:checkbox", this).each(function() {
            if (this.checked) this.setAttribute('checked', 'checked');
            else this.removeAttribute('checked');
        });
        $("option", this).each(function() {
            if (this.selected) this.setAttribute('selected', 'selected');
            else this.removeAttribute('selected');
        });
        return oldHTML.apply(this);
    };
})(jQuery);
 
</script>
</html>
