<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人信息</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/css/person.css" rel="stylesheet" type="text/css" />

<script src="${pageContext.request.contextPath}/js/jquery.pagination.js" type="text/javascript" />    
<script src="${ctx}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<link href="${pageContext.request.contextPath}/jquery-ui-1.10.2/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/editor_all.js"></script>
<script type="text/javascript">

var mytheme = function(){
	var items_per_page = 5;
	var page_index = 0;
	return {
		addTheme:function(){
			$("#title").val('');
			$("#content").val('');
			$('#addTheme').dialog("open");
		},
		radioCheckAll:function(){
			if($("#checkAll").attr("checked") == 'checked' || $("#checkAll").attr("checked") == 'true'){
				$("input[name='checkname']").each(function(){
					$(this).attr("checked",'checked');
				});
			}else{
				$("input[name='checkname']").each(function(){
					$(this).removeAttr("checked");
				});
			}
		},
		publish:function(){
			var tempid = '';
			$("input[name='checkname']:checked").each(function(){
				 tempid += $(this).val()+',';
			});
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/theme/publishTheme?"+Math.random(),
				data : {
					"ids":tempid
				},
				dataType : 'json',
				success : function(msg) {
					if(msg == true || msg == 'true'){
						window.location = "${pageContext.request.contextPath}/person/myThemes";
					}else{
						alert("发布失败");
					}
				}
			});
		},
		deleteAllTheme:function(){
			var tempid = '';
			$("input[name='checkname']:checked").each(function(){
				 tempid += $(this).val()+',';
			});
			alert(tempid);
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/theme/deleteThemelist?"+Math.random(),
				data : {
					"ids":tempid
				},
				dataType : 'json',
				success : function(msg) {
					 
					if(msg == true || msg == 'true'){
						window.location = "${pageContext.request.contextPath}/person/myThemes";
					}else{
						alert("删除失败");
					}
					
				}
			});
		},
		manager:function(op){
			window.location="${pageContext.request.contextPath}/person/goThemeDetail?themeId="+op;
		},
		deleteTheme:function(op){
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/theme/deleteTheme?"+Math.random(),
				data : {
					"id":op
				},
				dataType : 'json',
				success : function(msg) {
					if(msg){
						$("#Pagination").html('');
						mytheme.findMyThemes(0);
					}else{
						alert("删除失败");
					}
				}
			});
		},
		findMyThemes:function(index){
			var pageIndex = index;
			$.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/theme/findMyThemes?"+Math.random(),
				data : {
					"items_per_page" : items_per_page,
					"pageIndex":pageIndex
				},
				dataType : 'json',
				success : function(msg) {
					var total = msg.total;
					var html = '' ;
					$.each(msg.result,function(i,n){	
						var date = new Date(n.createDate);
						
						html +=  '<li> ' + 
								  '<div class="data_xuan left"> <input type="checkbox" name="checkname" id="checkbox" value=" ' + n.id + ' "/> </div> ' +
								  ' <div class="data_list_word left"> ' +
								  '	<div class="data_list_title"><a href="#" target="_blank"> ' + n.title + ' </a></div> ' +
								  '	<div class="data_list_info">创建时间：' + date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate()+' '+date.getHours()+':'+date.getMinutes() +':'+date.getSeconds()+ ' &nbsp;&nbsp;&nbsp;&nbsp;状态：' + (n.status == 0 ? '未发布' : '发布')  + ' </div> ' +
								  ' </div> ' +
								  ' <div class="data_btn_position"> ' +
								  ' <div class="data_btn_box right"> ' +
									' <div class="data_btn"> ' + 
										' <img src="../images/personal/data_btn_18.png" width="10" height="12" /> <a href="#" onClick="mytheme.manager(\'' + n.id + '\');">管理</a> ' + // id为字符串，会默认为一个变量
										' <img src="../images/personal/data_btn_20.png" width="13" height="12" /> <a href="#" onClick="mytheme.deleteTheme(\'' + n.id + '\');">删除</a> ' +
									' </div> ' +
									' </div> ' +
								  ' </div>' +
							     '</li>'
																							         
					});
					$('#Searchresult').html(html);
					//分页-只初始化一次
					if($("#Pagination").html().length == 0){
						$('#Pagination').pagination(total, {
							'items_per_page'      : items_per_page,
							'num_display_entries' : 5,
							'num_edge_entries'    : 2,
							'prev_text'           : "上一页",
							'next_text'           : "下一页",
							'callback'            : callback
						});
					}
				}
			});
		}
	}; 
}();

function callback (page_index, jq){
	mytheme.findMyThemes(page_index);
}

var editor_a;
$(function(){
	$('#addTheme').dialog({
		autoOpen : false,
		width : 300,
		height: 250,
		center:true,
		modal : true
	});
	$("#checkAll").click(mytheme.radioCheckAll);
	//刷新列表
	mytheme.findMyThemes(0);
	//editor_a =  new baidu.editor.ui.Editor({
	//     id: 'myEditor',
	//     initialFrameWidth:500,
	//     initialFrameHeight:400
	//     });

    //渲染编辑器
    //editor_a.render('myEditor');
    $("#submit").click(function(){
    	var title = $("#title").val();
    	var content = $("#content").html();
    	$.ajax({
			type : "POST",
			url : "${pageContext.request.contextPath}/theme/saveMyTheme",
			data : {
				"title" : title,
				"content":content
			},
			dataType : 'json',
			success : function(msg) {
				$('#addTheme').dialog("close");
				if(msg){//成功
					//刷新列表
					$("#Pagination").html('');
					mytheme.findMyThemes();
				}else{//失败
					//提示失败原因
					alert("添加失败");
				}
			}
    	});
    });
})
 

</script>
</head>
<body>
	<div class="search_up"></div>
	<div id="addTheme" class="fcontrol" title="专题" style="display: none" >
		<table>
			<tr>
				<td>标题：</td><td><input name="title" style="width:200px;" id="title" type="text" value=""/></td>			
			</tr>
			<tr>
				<td>介绍：</td><td><textarea style="width:200px;" rows="8" id="content" name="content"></textarea></td>	
			</tr>
			<tr><td colspan="2" align="center"><input type="button" id="submit" name="submit" value="提交"/></td></tr>
		</table>
		
	</div>
	<div class="search_content_box">
		<div class="search_content">
			<div class="person_nav2">
				<a class="present" href="/person/myThemes">我的专题</a>
			</div>
			<div class="person_left left">
				<div class="order_title">
					我的专题<span class="order_title_en"> / my themes</span>
				</div>
				<form id="form2" name="form2" method="post" action="">
					<div class="data_title">
						<div class="data_title_left left">
							<input type="checkbox" id="checkAll" />全选
						</div>
						<div class="data_title_right right">
							<div id="xx" class="fcontrol" title="专题" style="display: none" />
						</div>
						<img src="../images/personal/data_btn_05.png" width="16" height="16" /> 
						<a href="#" onclick="mytheme.publish()">发布</a> 
						<img src="../images/personal/data_btn_05.png" width="16" height="16" /> 
						<a href="#" onclick="mytheme.addTheme()">增加</a> 
						<img src="../images/personal/data_btn_07.png" width="16" height="16" />
						<a href="#" onclick="mytheme.deleteAllTheme();">删除</a>
					</div>
			</div>
			<ul id='Searchresult' class="data_list">
			</ul>
			<div class="scott">
				<div id="Pagination" class="pagination"></div>
			</div>

			</form>
		</div>
		<div class="person_right right">
			<jsp:include page="right.jsp"></jsp:include>
		</div>
		<div class="clear"></div>
	</div>
	</div>
	<div class="search_down"></div>
	<div class="clear"></div>
	<div class="foot">联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客 天佑网版权所有
		©1997-2013 粤ICP备20090191号</div>
	</div>
</body>
</html>