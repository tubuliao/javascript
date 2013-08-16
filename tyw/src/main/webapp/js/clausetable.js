
$(document).ready(function(){
	/**
	 * 异步加载来源列表数据
	 */ 

		
		function getSourceList(){
				  
				$("#new_right_book").load("/doSourceListajax",{keyword:"",typeId:"",tag_name:"",pageNo:1},function(data){
					/*total= data.match(/id=\"hidrowCount\" value=\"(\d+)\"/)[1];
					if(total==0){
 						$("#Pagination").html("");
 						return false;
						}
 					if(total!=	$("#hiderow_Count").val()){
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
						
					 	$("#hiderow_Count").val(total);*/
				});
			
		}
		
		function getTagListByParentId(code,level,pdCode){
			var result = '';
			htmlobj=$.get("/doTagListajax",{code:code,level:level,pdCode:pdCode},
						function(data,textStatus){
							var list = data;//eval(data);
							for(var i=0;i<list.length;i++){
								var tag = list[i];
								var s =	'<li><a href="#" class="click" data='+tag.code+'>'+tag.name+'</a></li>';
								result+=s;
							}
						}
					);
			//alert(result);
		}
		//
		
//});
		var pdCode = $("#zsxzpath").val();
		
		//alert(pdCode);
		$(function(){
			$(".a_clicks").live("click",function(){
				$(".a_clicks").css({ 'color':'#333333'});
				$(this).css({ 'color':'#F00'});
				$("#lastpath").val($(this).attr("data"));
				$("#Pagination").html('');
				getDataList(page_index);
			});
			//标签的点击效果
			$(".a_click").live("click",function(){
				
				var parent = $(this).parents("ul").first();
				
				if(parent.attr('ajax_status')=="1"){
					
				}else{
					parent.attr('ajax_status',"1");
					var father_tag_id = parent.attr('data');
					var now_id = $(this).attr('data');
					var html_string = "";
					var show_text = $(this).attr("text");
					var level = $(this).attr("level");
					 $("#lastpath").val(now_id);
					var that = this;
					
					//alert(pdCode);
					//数据处理获取一个ul next，TODO获取这个ul
					//$("ul.bak_next").eq(0).attr("data",father_tag_id);
					$.get("/doTagListajax",{code:now_id,level:level,pdCode:pdCode},function(data){
						if(data.length >0){
							for(var i=0;i<data.length;i++){
								html_string = html_string + "<li onclick='getSourceList()'><span class='left'><a href='javascript:void(0)' name='a_fbfxs' class='a_clicks' level='"+data[i].level+"' data='"+data[i].code+"'>"+data[i].name+"&nbsp;&nbsp;<span style='color:green'>("+data[i].count+")</span></a></span>";
								if(data[i].leaf==0){
									html_string = html_string +"<a href='javascript:void(0)' style='display:inline-block;float:right' name='a_fbfx' class='a_click' data='"+data[i].code+"' level='"+data[i].level+"'   text='"+data[i].name+"'><img  class='right ' src='/images/new/new_17.gif' /></a>";
								}
								html_string = html_string + "</li>";
							}
							$("ul.bak_next").clone(true).attr('data',now_id).attr('ajax_status','0').removeClass("bak_next").addClass('next').html(html_string).appendTo("#left_banner");
							$(".now").first().clone(true).attr('ajax_status','0').removeClass('now').css("left","214px").css('position','relative').appendTo("#bak_ul");
							//路径添加
							var index = $(".path_info a").length+1;
							var a_link = $("#html_bak a.path").eq(0).clone(true).attr('data',now_id).attr('index',index).text(show_text+' > ');
							$(".path_info").first().append(a_link);
							//动画处理 TODO
							$(that).parents("ul").remove();
							$("ul.next").first().removeClass("next").addClass('now');
							$('ul.now').eq(0).animate({left:'0'}, 1000,function(){});
							$("#Pagination").html('');
							getDataList(page_index);
						}else{
							alert('该项目无子分类');
						}
						parent.attr('ajax_status',"0");
					});
				}
			});
			
			
			//路径标签的点击效果
			$("a.path").live("click",function(){
				var data_id = $(this).attr("data");
				var data_index = $(this).attr('index');
				$("#lastpath").val(data_id);
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
					})
				}
				$("#Pagination").html('');
				getDataList(page_index);
			})
			//prev的点击效果实现
			$(".back_link").click(function(){
				
				var max = $(".path_info a").length;
				var data_index = max -1;
				var data_id = $(".path").eq(data_index).attr("data");
				//$("#lastpath").val($(".path").text().replace(">",""));
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
						if (temp_index == data_index) {
							$("#lastpath").val($(this).attr("data"));
						}
					});
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
						if (temp_index == data_index) {
							$("#lastpath").val("10000000000000000");
						}
					})
				}
				$("#Pagination").html('');
				getDataList(page_index);
			})
		})
});