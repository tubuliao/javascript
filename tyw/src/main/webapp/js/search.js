$(window).load(function(){
		var endPage="";
 		var typeId=$("#typeId").val();
 		//初始化搜索条件
 		var tagSearchArrayval=getCookie("tagSearchArray");
   		//初始化高级搜索框
 		$("#tagSearch").dialog({
 			autoOpen : false,
 			width : 800,
 			modal : true
 		});
 		 $("#konwledgeType7").live("click",function(){
  	 				$("#tagSearch").dialog('open');
 	 			
 	 				//gettagSearch();
 		 });
 	     gettagSearch(1,1);
  		if(tagSearchArrayval!=null&&$.trim(tagSearchArrayval)!=""){
 	 		$("#searchCondition").html(tagSearchArrayval);
 	 		var searchTagId="";
  	 		for(var d=1;d<$("#searchCondition>a").length-1;d++){
  	 			var searchid=$("#searchCondition>a:eq("+d+")").attr("id");
 	 			searchTagId=$("#searchCondition>a:eq("+d+")").attr("id").substring(3,searchid.length);
  	 			$("#search_"+searchTagId).attr("href","javascript:void(0)");
 	 		}
  	 		
  	 		var taglenVal="";
  	 	 	for(var taglen=0;taglen<$("#searchCondition a").length;taglen++){
  	 	 		if(taglenVal==""&&$("#searchCondition a:eq("+taglen+")").text()!=""){
  	 	 	 		taglenVal=$("#searchCondition a:eq("+taglen+")").text();
  	 	 	 		continue;
  	 	 	 	 }if(taglenVal!=""&&$("#searchCondition a:eq("+taglen+")").text()!=""){
  		  	 	 		taglenVal=taglenVal+"OR"+$("#searchCondition a:eq("+taglen+")").text();
  	 	 	 	 }
  			}
  		  	$("#tag_name").val(taglenVal);
 		}
  		//$(document).ready(function(){
			getDataList(0, classification_id);
//	});		
			//排序
			$("#search_sort").change(function(){
				 var sortField=$("#sortField").val();
		 	     var sortOrder=$("#sortOrder").val();
		 	     if(sortOrder=="null"&&sortField!="null"){
 		 	    	 $("#sortOrder option:eq(1)").attr("selected","selected");
		 	     }
		 	    if(sortOrder!="null"&&sortField=="null"){
 		 	    	$("#sortField option:eq(0)").attr("selected","selected");
 		 	    	$("#sortOrder option:eq(0)").attr("selected","selected");
		 	    }
		 	  
				getDataList(0, classification_id);
			})
			/**
			 * 异步加载列表数据
			 */ 

				var items_per_page = 10;
				var page_index = 1;
				var classification_id = "" ;
				var ciid="";
				function getDataList(index, cId){
    				var keyword=$('#keyword').val().replace(/[ ]/g,"");
	  				  var pageIndex = index;
	  				  var typeId=$("#typeId").val();
 	  				  var tag_name=$("#Havesel").text(); 
	  				  var tag_name="";
			 	      var total=0; 
			 	      var sortField=$("#sortField").val();
			 	      var sortOrder=$("#sortOrder").val();
 			 	 	 var hidAddreval=$("#hidAddre").val(); 
					 var hidpersonval=$("#hidperson").val(); 
					 var hidknoleval=$("#hidknole").val(); 
					 var hidsupartval=$("#hidsupart").val(); 
					 var HaveselVal=$("#Havesel").text().replace("清除",""); 

 					//tag_name="("+hidAddreval+")AND("+hidpersonval+")AND("+hidknoleval+")AND("+hidsupartval+")";高级查询
					tag_name=$("#tag_name").val();
					if(keyword=='输入要找的内容'||keyword==""||keyword=="?"||keyword=="*"||keyword=="?*"){
						keyword="";
					}
					 if(tag_name==null){
						  tag_name=""; 
					 }
					 
 			 	    /**/
 	 				$("#search_result").load("/doListajax",{keyword:keyword,typeId:typeId,tag_name:tag_name,sortField:sortField,sortOrder:sortOrder,pageNo:pageIndex+1},function(data){
  	 					total= data.match(/id=\"hidrowCount\" value=\"(\d+)\"/)[1];
	 					if(total==0){
	 						 if($("#goLuxurySearch").val()!=1){
	 				  	  		 var keyval=(keyword+HaveselVal).replace("/[]/g","").replace("输入要找的内容","");
	 				  	  		 if(keyval==""){
	 				  	 			 $("#errorMsg").html("请输入您要查找的内容！");
	 				  	  		 }else{
	 				 				 $("#errorMsg").html("抱歉，没有找到与“<font color=\"red\">"+keyval+"</font>”相关的网页内容！");
	 				  	  		 }
	 						 }
	 				  		$("#goLuxurySearch").val(2);
		 						$("#Pagination").html("");
		 						return false;
	 						}
      	 					if(total!=$("#hiderow_Count").val()){
	 	  						$('#Pagination').pagination(total, {
	 								'items_per_page'      : items_per_page,
	 								'num_display_entries' : 5,
	 								'num_edge_entries'    : 2,
	 								'current_page'		  : pageIndex,   
	 								'prev_text'           : "上一页",
	 								'next_text'           : "下一页",
	 								'callback'            : pageselectCallback
	 							});
	 						}
	 					 	$("#hiderow_Count").val(total);
  					});
 	 			
				}

				function pageselectCallback(page_index, jq){
					getDataList(page_index, classification_id);
				 
				}

				 
 				 //异步提交
				 $("#search_btn").click(function(){
						$("#hiderow_Count").val("");
						getDataList(0, classification_id);
					//	btn_hideDiv("filter");高级查询
				});
				 $(".shaixuan").click(function(){
						getDataList(0, classification_id);
					//	btn_hideDiv("filter");高级查询
				});
				 $("#keyword").keydown(function (event) {
		                if (event.keyCode == 13) {
	 	                	 $("#search_btn").click();
		                	 event.preventDefault();//阻止form提交
		                }
		            });
				 
					//初始化地区查询
				 	var provinceVal = getCookie("provinceVal");
				     if(provinceVal!=null){
						$("#province").html(provinceVal); 
					}else{
				 		var citys=null;
						if($.trim(ILData[2])==""){
				    			$("head").append("<script type=\"text/javascript\" src=\"${pageContext.request.contextPath}/js/geoip.js\"><\/script>");
				  			$.post("/index/getpoint",{x:geoip_latitude(),y:geoip_longitude()},function(data){
								cityVal=$.trim(data.long_name).length==2?$.trim(data.long_name)+"市":$.trim(data.long_name);
				   	 			citys=cityVal==""?'全国':cityVal;
					 		//	getProvince(citys);
					   			searchCityVal=citys;
								setCookie("provinceVal", citys,1);
								$("#province").html(citys); 
							});
						}else{
				   			searchCityVal=ILData[2];
							setCookie("provinceVal", ILData[2],1);
							$("#province").html(ILData[2]); 
						}
					}
				 
				  	//初始化省份DIV
						$("#provinceArea").dialog({
							autoOpen : false,
							width : 600,
							center:true,
							dialogClass : "my-dialog",
							modal : true
				 		});
						$("#provinceArea").parent().css("position", "fixed");
				 		$("#provinceArea").parent().css("top",$(window).height()/2+"px");
						$("#province").click(function() {
							try{
								var provinceVal = getCookie("provinceVal");
				 				if(provinceVal!=null){
									getProvince(provinceVal);
								}else{
									getProvince(searchCityVal);
								}
				 				$("#provinceArea").dialog('open');
							}catch(error){
							}
				 		});
						function getProvince(cityVals) {
 						 	//这里去加载标签树
							var areaurl = '/tag/getProvinceTag';
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
						
						
 //菜单切换
//$(".whitetxt2").ready(function(){
			try{
				var onexVal=null==$("#onex").val()?"konwledgeType0":$("#onex").val();
		  		var i=onexVal.replace("konwledgeType","");
		 		var menu=null;
		 		for(var j=0;j<=5;j++){
		  			if(j==i){
		  				if(i==1){
 		  					$(".type_choose").css("display","block");
 			  				 var typeId=$("#typeId").val();
 			  				var radioId=$(".type_choose label input[value='"+typeId+"']").attr("id");
 			  				$("#"+radioId).attr("checked","checked");
  			  				 
		  				}
		  				menu=document.getElementById(onexVal);
		 				menu.className="hover";
		 			}else{
		  				menu=document.getElementById("konwledgeType"+j);
		 				menu.className="";
		 			}
		  		} 
			}catch(error){
				
			}
//});
 				/* $('body').mousewheel(function(event, delta,deltX,deltaY) {
 					 var wLength= $(document).scrollTop();//滚动条高度
 					 var asLength=$("#advance_search").offset().top;
 					 var flength=$("#filter").height();
   					 var filter=$("#filter").css("display");
 					 var isShow=wLength+asLength;
 					 var isShowFilter=wLength+flength;
 					   if(delta==-1&&isShow>130&filter=="none"){
 						 $("#fixed").css("display","block");
					   }if(delta==-1&&isShowFilter>1400&filter=="block"){
  						 $("#fixed").css("display","block");
					   }if(delta==1&&isShowFilter<=1600&filter=="block"){
						  $("#fixed").css("display","none");
					   }if(delta==1&&isShow<=325){
 						  $("#fixed").css("display","none");
					   }
					});*/
 
	});
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
function setTab(name,cursel,n){
	 
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
function goLuxurySearch(divDisplay,goLuxurySearch)
{
 	if(goLuxurySearch==1){
		 document.getElementById(divDisplay).style.display = "block";
	}
		
}

//操作条件框
var tagSearchArray=new Array();
	var tagSearchArrayval=getCookie("tagSearchArray");
	var hidAddre="";
function addtagSearchValue(op) {
 	if(tabTag==1){
			var si=$("#searchCondition a").length;
		if(5>si){
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
	 	 	 	if(taglenVal==""){
	 	 	 		taglenVal=$("#searchCondition a:eq("+taglen+")").text();
	 	 	 		 continue;
	 	 	 	 }else{
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
 	 	 	if(taglenVal==""){
 	 	 		taglenVal=$("#searchCondition a:eq("+taglen+")").text();
 	 	 		continue;
 	 	 	 }else{
  	 	 		taglenVal=taglenVal+"OR"+$("#searchCondition a:eq("+taglen+")").text();
 	 	 	 }
	}
  	$("#search_" + op).attr("href","javascript:addtagSearchValue(\""+op+"\")");
  	$("#tag_name").val(taglenVal);
 	setCookie("tagSearchArray", $("#searchCondition").html(),1);
}

var tabTag=1;
//查询标签
function gettagSearch(istag,n) {
	tabTag=n;
 	var areaurl = '/tag/findsearchhotword';
   	$.post(areaurl,{"istag":istag-1},function(result) {
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
	 	});
 	}
}

function hotTag(name,cursel,n){
	for(i=1;i<=n;i++){
  		var menu=document.getElementById(name+"_"+i);
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
//热词切换
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

function setCookie(name, value, expireday) {
      var exp = new Date();
    exp.setTime(exp.getTime() + expireday*24*60*60*1000); //设置cookie的期限
  //	document.cookie = name+"="+escape(value)+"; expires"+"="+exp.toGMTString();//创建cookie,带失效周期的
    document.cookie = name+"="+escape(value)+";path=/";//创建cookie
  }



function getCookie(c_name) {
    if (document.cookie.length>0)
    {
     c_start=document.cookie.indexOf(c_name + "=");
     if (c_start!=-1)
     { 
      c_start=c_start + c_name.length+1 ;
      c_end=document.cookie.indexOf(";",c_start);
      if (c_end==-1) c_end=document.cookie.length;
      return unescape(document.cookie.substring(c_start,c_end));
     } 
    }
    return "";
   }


