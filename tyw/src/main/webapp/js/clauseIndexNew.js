//查询按钮ajax查询
function sub(){
	//进度条开始
	/*$.messager.progress({
		title : '提示：' ,
		text : '正在查询……' ,
		interval : 1000
	});*/
	var sour = $("#bookSource").val();//来源
	var title = $("#title").val();//标题
	var standard = $("#bookStandard").val();//标准
	var tags = "分部分项"//标签
	if($("span.path_info a").length>0){
		tags = $("span.path_info a").last().text().split(' ')[2];
	}
	//alert(sour+"ss"+title+"ss"+tags);
	//doSourceListQueryajax
	getSourceListtext(0,tags,sour,title,standard);
	getKnowledgeListFilter(tags,sour,title,standard);
	//进度条结束
	/*$.messager.progress('close'); */
}
//$(document).ready(function(){
//$(document).ready(function(){
//	getSourceList('核心条文 分部分项');
//});
var pageDate=0;
var keyword="";
function pageselectCallback(page_index, jq){
   	if(page_index!=pageDate){
 		getSourceListtext(page_index, keyword);
	}
	pageDate=page_index;
}

$(document).ready(function(){
	keyword="核心条文 分部分项";
	getSourceListtext(0, '分部分项');
	//fenbufenxiang();
});	
var hiderowPageData=0;
var keysource="";
function pageselectContentCallback(page_index, jq){
	
     	if(hiderowPageData!=page_index){
     		
    		getKnowledgeList(page_index, keysource);
	}
   	hiderowPageData=page_index;
}

$(document).ready(function(){
$("#bookcontent a").live("click", function(){
	//$("#bookSource").val($(this).text());
	keysource=$(this).text();
	getKnowledgeList(0,$(this).text().trim());
	//联动目录结构ajax
		/*var sj_first = $("ul.know li").eq(0).text();
		if(sj_first==null){
			sj_first = $("ul.know li").eq(1).text();
		}
		getKnowledgeList(0, sj_first);*/
		//
});
});	

		/**
		 * 异步加载知识列表数据
		 */
		function getKnowledgeList(index,source){
			//显示等待图片
			  tywBeforeSend("","");
			  
				if(source==null||source=='undefined'||source==undefined)source="";
				source = source.trim();
				//alert(source);
				$("#new_right_mulu").load("/doKnowledgeListajax",{keyword:"",typeId:"",source:source,pageNo:1},function(data){
					//结束等待图片
					tywAjaxComplete("","");
				});
		}

		/**
		 * 异步加载来源列表数据
		 */ 
		function getSourceListtext(index,tags,source,title,standard){
			  //显示等待图片
			  tywBeforeSend("","");
			
			  if(tags==null||tags=='undefined'||tags==undefined)tags="";
			  if(source==null||source=='undefined'||source==undefined)source="";
			  if(title==null||title=='undefined'||title==undefined)title="";
			  if(standard==null||standard=='standard'||standard==undefined)standard="";
			 
			  //$(this).css({'font-weight':'normal', 'color':'#FFFFFF'});
			  tags=tags.trim();source=source.trim();title=title.trim();standard=standard.trim();
			  // /doSourceListajax
 				$("#bookcontent").load("/doSourceListQueryDBajax",{source:source,title:title,standard:standard,tag_name:tags,pageNum:0},function(data){
 					//联动目录结构ajax
 	 				var sj_first = $("ul.know li").eq(0).text().trim();
 	 				if(sj_first==null){
 	 					sj_first = $("ul.know li").eq(1).text().trim();
 	 				}
 	 				//alert(sj_first);
 	 				getKnowledgeList(0, sj_first);
				});
 				
 				//隐藏等待图片
 				//tywAjaxComplete("","");
		}
		
		function getKnowledgeListFilter(tags,source,title,standard){
			 if(tags==null||tags=='undefined'||tags==undefined)tags="";
			  if(source==null||source=='undefined'||source==undefined)source="";
			  if(title==null||title=='undefined'||title==undefined)title="";
			  if(standard==null||standard=='standard'||standard==undefined)standard="";
			$("#new_right_mulu_filters").load("/doknowledgeListQueryDBajaxs",{source:source,title:title,standard:standard,tag_name:tags,pageNo:1},function(data){
				//alert();
			});
		}
		
		function getSourceList(tags){
			 //alert($(this).text()+"s"+tags);
				$("#bookcontent").load("/doSourceListajax",{keyword:"",typeId:"",tag_name:tags,pageNo:1},function(data){
				
				});
		}
		
		
		function getTagListByParentId(code,level){
			var result = '';
			htmlobj=$.get("/doTagListajax",{code:code,level:level,pdCode:'40501000000000000'},
						function(data,textStatus){
							
							var list = data;//eval(data);
							for(var i=0;i<list.length;i++){
								var tag = list[i];
								var tags = "核心条文  "+tag.name;
								var on = "onclick='getSourceList("+tags+")' ";
								alert(on);
								/*var s =	'<li '+on+'><a href="#" class="click" level='+tag.level+' data='+tag.code+'>'+tag.name+'</a></li>';*/
								var s =	'<li><a href="#" class="click" level='+tag.level+' data='+tag.code+'>'+tag.name+'</a></li>';
								result+=s;
								//alert(tag.code);
							}
							alert(result);
						}
					);
			//alert(result);
		}
		//
		
//});
		$(function(){
			/*$("ul.now li").live("click",function(){
				//alert($(this).text());
				$("#BQItem").val($(this).text());
				getSourceListtext(0,$(this).text());
				});*/
			//书籍来源联动ajax获取
			$(".a_clicks").live("click",function(){
				$(".a_clicks").css({ 'color':'#333333'});
				$(this).css({ 'color':'#F00'});
				$("#BQItem").val($(this).attr("data"));
				getSourceListtext(0,$(this).attr("data"));
			});
			//目录结构ajax获取
			$("ul.know li").live("click",function(){
				$("ul.know li").attr("class","");
				$(this).attr("class","hover");
				//alert($(this).text());
				//getKnowledgeList($(this).text());
				});
			
			//标签的点击效果
			$(".a_click").live("click",function(){
				//alert("m");
				var parent = $(this).parents("ul").first();
				var se_text = $(this).attr('text');
				if(parent.attr('ajax_status')=="1"){
					//alert("x");
				}else{
					//alert("b");
					parent.attr('ajax_status',"1");
					var father_tag_id = parent.attr('data');
					var now_id = $(this).attr('data');
					var level = $(this).attr('level');
					//alert(now_id+':'+level);
					var html_string = "";
					var show_text = $(this).attr("text");
					var that = this;
					//数据处理获取一个ul next，TODO获取这个ul
					//$("ul.bak_next").eq(0).attr("data",father_tag_id);
					$.get("/doTagListajax",{code:now_id,level:level,pdCode:'40501000000000000'},function(data){
	//					$(that).attr("ajax_status","true");
						if(data.length >0){
							for(var i=0;i<data.length;i++){
								if(data[i].count==0){
									html_string = html_string + "<li >"+data[i].name+"&nbsp;&nbsp;<span style='color:green'>("+data[i].count+")</span></li>";
								}else{
									html_string = html_string + "<li ><a href='javascript:void(0)' class='a_clicks' data='"+data[i].name+"'>"+data[i].name+"&nbsp;&nbsp;<span style='color:green'>("+data[i].count+")</span></a>";
									if(data[i].leaf==0){
										html_string = html_string +"<a href='javascript:void(0)' style='display:inline-block;float:right' class='a_click' level='"+data[i].level+"'  data='"+data[i].code+"' text='"+data[i].name+"'><img  class='right ' src='/images/new/new_17.gif' /></a>";
									}
									html_string += '</li>';
								}
							}
							$("ul.bak_next").clone(true).attr('data',now_id).attr('ajax_status','0').removeClass("bak_next").addClass('next').html(html_string).appendTo("#left_banner");
							$(".now").first().clone(true).attr('ajax_status','0').removeClass('now').css("left","214px").css('position','relative').appendTo("#bak_ul");
							//路径添加
							var index = $(".path_info a").length+1;
							var a_link = $("#html_bak a.path").eq(0).clone(true).attr('data',now_id).attr('index',index).text(' > '+show_text);
							$(".path_info").first().append(a_link);
							
							//动画处理 TODO
							$(that).parents("ul").remove();
							$("ul.next").first().removeClass("next").addClass('now');
							$('ul.now').eq(0).animate({left:'0'}, 1000,function(){});
						}else{
							alert('该项目无子分类');
							//ShowPop();
						}
						getSourceListtext(0,se_text );
						parent.attr('ajax_status',"0");
					});
					
				}
			});
			
			
			//路径标签的点击效果
			$("a.path").live("click",function(){
				var data_id = $(this).attr("data");
				var data_index = $(this).attr('index');
				var max = $(".path_info a").length;
				if (data_index == max) {
				}else{
					$("#bak_ul ul").each(function(index){
						var temp_id = $(this).attr('data');
						//var temp_index = $(this).attr('index');
						if (data_id == temp_id) {
							$(this).clone(true).attr('ajax_status',"0").appendTo("#left_banner").addClass('next');
							$("ul.now").eq(0).removeClass("now").remove();
							$("ul.next").first().removeClass("next").addClass('now');
							$('ul.now').eq(0).animate({left:'0'}, 1000,function(){});
							
						}
					});
					$(".path").each(function(){
						var temp_index = $(this).attr('index');
						if (temp_index > data_index) {
							$(this).remove();
						}
					});
					//联动获取书籍来源
					$("#BQItem").val($(this).text());
					tags = $(this).text().split(' ')[2];
					getSourceListtext(0,tags);
				}

			});
			//prev的点击效果实现
			$(".back_link").click(function(){
				
				var max = $(".path_info a").length;
				var data_index = max -1;
				var data_id = $(".path").eq(data_index).attr("data");
				if (max>1){
					$("#bak_ul ul").each(function(index){
						var temp_id = $(this).attr('data');
						if (data_id == temp_id) {
							$(this).clone(true).attr('ajax_status',"0").appendTo("#left_banner").addClass('next');
							$("ul.now").eq(0).removeClass("now").remove();
							$("ul.next").first().removeClass("next").addClass('now');
							$('ul.now').eq(0).animate({left:'0'}, 1000,function(){});
							
						}
					});
					$(".path").each(function(){
						var temp_index = $(this).attr('index');
						if (temp_index > data_index) {
							$(this).remove();
						}
					});
					//联动获取书籍来源
					$("#BQItem").val($(".path").eq(data_index).text());
					getSourceListtext(0,$(".path").eq(data_index).text());
				}else if(max == 1)
				{
					$("#bak_ul ul").eq(0).clone(true).appendTo("#left_banner").addClass('next');
					$("ul.now").eq(0).removeClass("now").remove();
					$("ul.next").first().removeClass("next").addClass('now');
					$('ul.now').eq(0).animate({left:'0'}, 1000,function(){});
					$(".path").each(function(){
						var temp_index = $(this).attr('index');
						if (temp_index > data_index) {
							$(this).remove();
						}
					});
					getSourceListtext(0, '分部分项');
				}
				
			});
		});
		
	function fenbufenxiang(){
		getSourceListtext(0, '分部分项');
		
		if($("#bak_ul ul").length>0){
		    $("ul.now").first().clone(true).attr('ajax_status','0').removeClass('now').css("left","214px").css('position','relative').appendTo("#bak_ul");
		    $("ul.now").first().remove();
		    $("#bak_ul ul").eq(0).clone(true).attr('ajax_status',"0").appendTo("#left_banner").addClass('now');
		    $('ul.now').eq(0).animate({left:'0'}, 1000,function(){});

		    $(".path_info").text('');
		    }
	}
	
	function setabFilter(name,cursel,n){
		for(var i=1;i<=n;i++){
			var menu=document.getElementById(name+i);
			var con=document.getElementById("con_"+name+"_"+i);
			menu.className=i==cursel?"hover":"";
			con.style.display=i==cursel?"block":"none";
		}
	}
	
	String.prototype.trim= function(){  
	    // 用正则表达式将前后空格  
	    // 用空字符串替代。  
	    return this.replace(/(^\s*)|(\s*$)/g, "");  
	};

	/**
	 * 省份选择触发的事件
	 */
	function getDataView(){
		fenbufenxiang();
		//location.reload();
	}