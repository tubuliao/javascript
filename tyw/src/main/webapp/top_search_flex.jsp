<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
   <link rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/home.css" />
       <link rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/seleted.css" />
       <style>
       	   #tagSearch h4{ margin:0;}
           #tagSearch h4 a{ margin:0; color:#999999;font-size:14px; font-family:"microsoft yahei";margin-right:10px; }
	       #tagSearch .hover{
	       		color:green; background:#; border-bottom:1px solid green; 
	       }
      </style>  
   
     <link rel=stylesheet type=text/css href="${pageContext.request.contextPath}/jquery-ui-1.10.2/themes/base/jquery.ui.all.css" />
 <script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
   <script  type="text/javascript" src="${pageContext.request.contextPath}/js/cookie.js" ></script>
    <script  type="text/javascript" src="${pageContext.request.contextPath}/js/top_search_load.js" ></script>
   <script type="text/javascript" src="http://counter.sina.com.cn/ip/" charset="gb2312"></script> 
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
		 
})
  
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
	</script>	
	<body>
	
	
<div class="head_top"><a class="left" href="/jump.jsp" target="_parent"><img src="${pageContext.request.contextPath}/images/home/logo_home_03.png" width="183" height="66" /></a>
<div class="now_good left">
      <h3>
      <a id="province"><span></span></a><img src="${pageContext.request.contextPath}/images/home/xl_03.gif"/>
      </h3> 
         <h4>${tagName}</h4>
           
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
   <form method="get" action="${pageContext.request.contextPath}/goPageList" name="searchform" id="searchform" onSubmit="return issub()">
 <div class="search_qz"  >
<div class="whitetxt2">
<span id="NavigationSearch">
<div id="konwledgeType0" onClick="setTabs('konwledgeType',0,5)"  class="hover" >全部</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType1" onClick="setTabs('konwledgeType',1,5)"   >文库</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType2" onClick="setTabs('konwledgeType',2,5)"  >表格</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType3" onClick="setTabs('konwledgeType',3,5)" >图片</div>
  <div  style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType4" onClick="setTabs('konwledgeType',4,5)" >视频</div>
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
    	<a href="javascript:void(0)" id="pdssval" value="频道搜索">频道搜索</a>
        <a href="javascript:void(0)" value="全站搜索">全站搜索</a>
    </p><input type="hidden" name="category" id="category" value="no" />
</div>
        
          <input class="search_btn" id="search_btn"  value="全站搜索"  type="submit"  onclick="isclick()" style="vertical-align:middle;" />
        <!--  <input class="search_btn" id="pdsousuo"   value="频道搜索"  type="button"   style="vertical-align:middle;display: none" /> --> 
         
          <input class="search_gaoji" type="button" name="button" id="konwledgeType7" value="" />
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
  <div class="clear"></div>
  <div class="sousuo_xia" id="searchCondition"></div>
    <div class="clear"></div>
</div>


</body>
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/geoip.js" charset="gb2312"></script> 
 <!-- <script src="${pageContext.request.contextPath}/js/apprise-1.5.full.js" type="text/javascript"></script>
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
 
</script> --> 