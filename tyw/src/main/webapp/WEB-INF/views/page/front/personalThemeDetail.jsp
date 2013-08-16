<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>专题详细信息</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/css/person.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery.pagination.js" type="text/javascript" />    
<script src="${ctx}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/editor_all.js"></script>
<link href="${pageContext.request.contextPath}/jquery-ui-1.10.2/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
<script type="text/javascript">
var editor_a;
var items_per_page = 5;
var page_index = 0;
var classification_id = "" ;
var curtableId = "";
var timepool="";
$(function(){
	var size = ${size};
	
	for(var i = 0;i<size;i++){
		timepool+=i+",";
	}
	$('#editorDailog').dialog({
		autoOpen : false,
		width : 650,
		height: 550,
		title:'自定义文本',
		center:true,
		modal : true
	}); 
	$('#editorHTML').dialog({
		autoOpen : false,
		width : 650,
		height: 550,
		title:'自定义文本',
		center:true,
		modal : true
	}); 
	editor_a =  new baidu.editor.ui.Editor({
	     id: 'myEditor',
	     initialFrameWidth:500,
	     initialFrameHeight:300
	     });

    //渲染编辑器
    editor_a.render('myEditor');
    
    $("#editorDailog").parent().css("position", "fixed");
	$("#editorDailog").parent().css("top",$(window).height()/2+"px");
	displayContent('all');
});
//写一个展示已经存在内容
function displayContent(op){
	 var temp = timepool.split(",");
	 for(var i = 0;i<temp.length;i++){
		 if(temp[i]==""){continue;}
		 var type = $("#"+temp[i]).parent().parent().parent().parent().attr("type");
		 if(op != type && op != 'all'){continue;}
		 if(type == 'knowledge'){
			 
			 $.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/theme/findThemeDetailContentlist?"+Math.random(),
					data : {
						"content":$("#"+temp[i]).val(),
						"sort":temp[i]
					},
					dataType : 'json',
					success : function(msg) {
						var html = '<tr>';
						var count = 0;
						 $.each(msg.result,function(i,y){
							 count++;
							 var xt ='<a id="ak_'+msg.index+'_'+y.id+'" href="/detail/' + y.infoType+ '/'+y.id+'" target="_blank"> ' + y.title + ' </a>';
							    html += '<td width="44%" height="38" bgcolor="#FFFFFF" class="zuoju">'+xt+'</td>';
			                	html += '<td width="6%" align="center" bgcolor="#FFFFFF"><a id="img_ak_'+msg.index+'_'+y.id+'" title="" href="javascript:deleteknowledge(\'ak_'+msg.index+'_'+y.id+'\')"><img title="删除此条" src="../images/personal/data_btn_20.png" width="13" height="12" /></a></td>';
			                	if((i+1)%2==0){
			                		html +="</tr><tr>";
			                	}
						 });
						 if(count%2==1){
							 html += '<td width="44%" height="38" bgcolor="#FFFFFF" class="zuoju">'+'</td>';
				         	 html += '<td width="6%" align="center" bgcolor="#FFFFFF"></td>';
						 }
						 html+="</tr>";
						 $("#"+msg.index).parent().parent().parent().parent().append(html); 
					}
				});
		 }
		 if(type == 'detail'){
			 $.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/theme/findThemeDetailContentlist?"+Math.random(),
					data : {
						"content":$("#"+temp[i]).val(),
						"sort":temp[i]
					},
					dataType : 'json',
					success : function(msg) {
						var html = '';
						 $.each(msg.result,function(i,n){
							    html +='<a id="detail_'+n.id+'" href="/detail/' + n.infoType+ '/'+n.id+'" target="_blank"> ' + n.title + ' </a>';
						 });
						 $("#detailContent_"+msg.index).html(html); 
					}
				});
		 }
	 }
}
function  deleteknowledge(op){
	var tm = op.replace("ak_","");
	var index = tm.indexOf("_");
	var tt = tm.substring(0,index);
	var value = tm.substring(index+1);
	var allvalue = $("#"+tt).val();
	var finalvalue = allvalue.replace(value+";","");
	$("#"+tt).val(finalvalue);
	$("#img_"+op).remove();
	$("#"+op).remove();
}
function getDataList(index, cId){
	var pageIndex = index;
	$.ajax({
		type: "get",
		url: "${pageContext.request.contextPath}/pagination",
		data: "pageIndex="+pageIndex+'&items_per_page='+items_per_page+'&classification_id='+cId+'&'+Math.random(),
		dataType: 'json',
		contentType: "text/html; charset=utf-8",
		success: function(msg){
			var total = msg.total;
			var html = '' ;
			$.each(msg.result,function(i,n){
				var type = $("#"+curtableId).attr("type");
				var xml = '';
				if(type=="detail"){
					xml = '<input type="radio" name="radioname" id="radiobox" value="' + n.knowledgeId + '"/>';
				}
				if(type=="knowledge"){
					xml = '<input type="checkbox" name="checkname" id="checkbox" value="' + n.knowledgeId + '"/>';
				}
				
				html +=  '<li> ' + 
						  '<div class="data_xuan left"> '+xml+' </div> ' +
						  ' <div class="data_list_word left"> ' +
						  '	<div class="data_list_title"><a id="xxxx_'+n.knowledgeId+'" href="' + n.url+ '" target="_blank"> ' + n.title + ' </a></div> ' +
						  '	<div class="data_list_info">收藏时间：' + n.createDate + ' &nbsp;&nbsp;&nbsp;&nbsp;类型：' + (n.classification != null ? n.classification.title : '暂未分类')  + ' </div> ' +
						  ' </div> ' +
					     '</li>'
			});
			$('#Searchresult').html(html);
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
	getDataList(page_index, classification_id);
}
</script>
</head>
<body>
	<div class="search_up"></div>
	<div class="search_content_box">
		<div class="search_content">
			 <div class="person_nav2">
				<a class="present" href="/person/goThemeDetail?themeId=${personalTheme.id }">专题详细</a>
			</div>
			<div class="person_left left">
				<div class="order_title">
					专题详细<span class="order_title_en"> / theme detail</span>
				</div>
				<form id="form2" name="form2" method="post" action="">
					<div id="editorDailog" style="display:none">
					    <table>
							<tr>
								<td>类型名称：</td>
								<td>
									<select name="classification" id="classification" onChange="listForCid(this.value);">
										<option value="">-全部-</option>
										<c:forEach items="${cList}" var="cl">
											<option value="${cl.id}">${cl.title}</option>
										</c:forEach>
									 </select>
							    </td>
							</tr>
						</table>
				 	
						<ul id='Searchresult' class="data_list">
				        </ul>
						<div class="scott">
							<div id="Pagination" class="pagination"></div>
						</div>
						<input type="button" class="order_btn" name="check_btn" id="check_btn" value="勾选好了，确认" />
					</div>
					
					
					<div style='display:none' id="editorHTML">
						<textarea id="myEditor"></textarea>
						<input type="button" class="order_btn" name="check_btn" id="check_btn" value="勾选好了，确认" />
					</div>
				 
				</form>
				<div class="state">
        <div class="my_zt">
        	<img class="myzt_btimg" src="../images/personal/zt/myzt_03.gif" />
            <h3>${personalTheme.title }</h3>
            <img class="myzt_btimg" src="../images/personal/zt/myzt_06.gif" />
            <p>
            	${personalTheme.content }
            </p>
            <div class="myzt_add">
               	  <div class="my_zt_kongjian">
	                  <div class="left">
		                  	<span>选择模块类型: </span>
		                 	<select name="select" id="select">
		                  	  <option id="knowledgelist" value="knowledge">知识列表</option>
		                  	  <option id="articledetail" value="detail">文章详细</option>
		                  	  <option id="richtext" value="rich">个性文本</option>
		             	    </select>
		                  <input class="my_zt_add" type="button" name="addmodule" id="addmodule" value="" />
	                  </div>
	                  <div class="clear"></div>
                  </div> 
            </div>
            <div id="modulelist">
            	 <c:forEach  var="detail"  items="${detaillist}" varStatus="vs">
            	 	 <c:if test="${detail.type == 0 }">
            	 	 	 <table id="tbrichtext_${vs.index }" time="${vs.index }" type="rich" width="99%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E8E8E8">
				                 <tr>
				                    <td height="36" colspan="2" background="../images/mapbg_03.png" bgcolor="#FFFFFF" class="zuoju"><input class="my_zt_tjnr"  type="button" name="btnricheditor" id="button4" value="添加内容" />
				                    <input class="my_zt_scmk" type="button" name="delmodule" id="button5" value="删除此模块" />
				                    <input name="${vs.index }" id="${vs.index }" type="hidden"  value=""/>
				                    </td>
				                   </tr>
				                    <tr>
				                      <td height="36" background="../images/personal/zt/myzt_20.gif" class="zuoju"><strong>标题：
				                        <input type="text" style="width:400px;" name="title_${vs.index }" id="title_${vs.index }" value="${detail.title }" />
				                      </strong></td>
				                    </tr>
				                    <tr>
				                      <td id="readtext_${vs.index }" width="8%" height="54" bgcolor="#FFFFFF" class="zuoju my_zt_doc">
				                      	${detail.value }
				                      </td>
				                    </tr>
				         </table>
				         </c:if>
            	 	  <c:if test="${detail.type == 1 }">
            	 	    <table id="tbknowledgelist_${vs.index }" time="${vs.index }" type="knowledge" width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E8E8E8">
                  <tr>
                    <td height="36" colspan="4" background="../images/mapbg_03.png" bgcolor="#FFFFFF" class="zuoju"><input class="my_zt_tjnr"  type="button" name="btneditor" id="button4" value="添加内容" />
                     
                    <input class="my_zt_scmk" type="button" name="delmodule"  id="button5" value="删除此模块" /></td>
                    </tr>
                    <tr>
                      <td height="36" colspan="4" background="../images/personal/zt/myzt_20.gif" class="zuoju"><strong>列表名称：</strong>
					<input name="${vs.index }" id="${vs.index }" type="hidden"  value="${detail.value }"/>
                      <input type="text" name="knowledgetitle_${vs.index }" style="width:400px;" id="knowledgetitle_${vs.index }" value="${detail.title }" />
                    </tr>
                      
                  </table>
            	 	 </c:if>
            	 	  <c:if test="${detail.type == 2 }">
						 <table id="tbarticledetail_${vs.index }" time="${vs.index }" type="detail"  width="99%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E8E8E8">
			                 <tr>
			                    <td height="36" colspan="2" background="../images/mapbg_03.png" bgcolor="#FFFFFF" class="zuoju"><input class="my_zt_tjnr"  type="button" name="btneditor" id="button4" value="添加内容" />
			                    	<input name="${vs.index }" id="${vs.index }" type="hidden"  value="${detail.value }"/>
			                    <input class="my_zt_scmk" type="button" name="delmodule" id="button5"  value="删除此模块" /></td>
			                   </tr>
			                    <tr>
			                      <td height="36" background="../images/personal/zt/myzt_20.gif" class="zuoju"><strong>标题：
			                     <input type="text" style="width:400px;" name="title_${vs.index }" id="title_${vs.index }" value="${detail.title }" />
			                      </strong></td>
			                    </tr>
			                    <tr>
			                      <td id="detailContent_${vs.index }" width="8%" height="54" bgcolor="#FFFFFF" class="zuoju"></td>
			                    </tr>
		           		 </table>
            	 	 </c:if>
            	 </c:forEach>
            </div>
            <br/>
            <input type="button" class="order_btn" name="save_btn" id="save_btn" value="填好了，保存" />
          </div>
        </div>
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
	<script type="text/javascript">
$(function(){
	$("input[name='delmodule']").click(function(){
		var temp = $(this).parent().parent().parent().parent().attr("time");
		$(this).parent().parent().parent().parent().remove();
		timepool = timepool.replace(temp+",","");
	});
	$("input[name='btneditor']").click(function(){
		curtableId =$(this).parent().parent().parent().parent().attr("id");	
		$("#editorDailog").dialog("open");
		getDataList(page_index, classification_id);
	});
	$("input[name='btnricheditor']").click(function(){
		   curtableId =$(this).parent().parent().parent().parent().attr("id");
	       $("#editorHTML").dialog("open");
	   });
	$("input[name='check_btn']").click(function(){
		var type = $("#"+curtableId).attr("type");
		
		if(type=="knowledge"){
			 var html = '<tr>';
			 var count = 0;
			 var hid = '';
			 $('input[type="checkbox"][name="checkname"]:checked').each(
		                function(i) {
		                	count++;
		                	html += '<td width="44%" height="38" bgcolor="#FFFFFF" class="zuoju">'+$("#xxxx_"+$(this).val()).text()+'</td>';
		                	html += '<td width="6%" align="center" bgcolor="#FFFFFF"><a title="" href="#"><img title="删除此条" src="../images/personal/data_btn_20.png" width="13" height="12" /></a></td>';
		                	if((i+1)%2==0){
		                		html +="</tr><tr>";
		                	}
		                	hid +=$(this).val()+';';
		                	 
		                }
		      );
			 if(count%2==1){
				 html += '<td width="44%" height="38" bgcolor="#FFFFFF" class="zuoju">'+'</td>';
	         	 html += '<td width="6%" align="center" bgcolor="#FFFFFF"></td>';
			 }
			 html+="</tr>";
			 var time = curtableId.replace("tbknowledgelist_","");
			 $("#"+time).val(hid);
			 $("#"+curtableId).append(html);
			 $("#editorDailog").dialog("close"); 
		}
		if(type=="detail"){
			var time = curtableId.replace("tbarticledetail_","");
			$("#"+time).val($("input[name='radioname']:checked").val());
			$("#title_"+time).val($("#xxxx_"+$("input[name='radioname']:checked").val()).text());
			$("#detailContent_"+time).html("这里需要ajax请求具体数据");
			displayContent('detail');
			 $("#editorDailog").dialog("close"); 
		}
		if(type=="rich"){
			var time = curtableId.replace("tbrichtext_","");
			$("#"+time).val(editor_a.getContent());
			$("#readtext_"+time).html(editor_a.getContent());
			 $("#editorHTML").dialog("close"); 
		}
		
	});
	
	$("#addmodule").click(function(){
		var timestamp = Date.parse(new Date());
		timepool += timestamp+",";
		var selectvalue = $("#select").val();
		var html = '';
		if(selectvalue == 'knowledge'){
			
			html += '<table id="tbknowledgelist_'+timestamp+'" time="'+timestamp+'" type="knowledge"  width="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E8E8E8">';
			html += '<tr>';
			html += ' <td height="36" colspan="4" background="../images/mapbg_03.png" bgcolor="#FFFFFF" class="zuoju"><input class="my_zt_tjnr"  type="button" name="btneditor"  id="button4" value="添加内容" />';
			html += '<input id="'+timestamp+'" name="'+timestamp+'" type="hidden"/>';
			html += ' <input class="my_zt_scmk" type="button" name="delmodule" id="button5"  value="删除此模块" /></td>';
			html += '</tr>';
			html += ' <tr>';
			html += '<td height="36" colspan="4" background="../images/personal/zt/myzt_20.gif" class="zuoju"><strong>列表名称：</strong>';
			html += '<input type="text" style="width:400px;" name="knowledgetitle_'+timestamp+'" id="knowledgetitle_'+timestamp+'" /></td>';
			html += '  </tr>';
			html +='</table>';
		}
		if(selectvalue == 'detail'){
			html += '<table  id="tbarticledetail_'+timestamp+'" time="'+timestamp+'" type="detail" width="99%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E8E8E8">';
			html += ' <tr>';
			html += '  <td height="36" colspan="2" background="../images/mapbg_03.png" bgcolor="#FFFFFF" class="zuoju"><input class="my_zt_tjnr"  type="button" name="btneditor"  value="添加内容" />';
			html += '<input id="'+timestamp+'" name="'+timestamp+'" type="hidden"/>';
			html += '<input class="my_zt_scmk" type="button" name="delmodule" id="button5" value="删除此模块" /></td>';
			html += '</tr>';
			html += '<tr>';
			html += '  <td height="36" background="../images/personal/zt/myzt_20.gif" class="zuoju"><strong>标题：';
			html += ' <input type="text" style="width:400px;" name="title_'+timestamp+'" id="title_'+timestamp+'" />';
			html += ' </strong></td>';
			html += '</tr>';
			html += '  <tr>';
			html += '  <td id="detailContent_'+timestamp+'" width="8%" height="54" bgcolor="#FFFFFF" class="zuoju"></td>';
			html += '</tr>  </table>';  
			
		}
		if(selectvalue == 'rich'){
			html += '<table id="tbrichtext_'+timestamp+'" time="'+timestamp+'" type="rich" width="99%" border="0" cellpadding="0" cellspacing="1" bgcolor="#E8E8E8">';
			html += '<tr>';
			html += '<td height="36" colspan="2" background="../images/mapbg_03.png" bgcolor="#FFFFFF" class="zuoju"><input class="my_zt_tjnr"  type="button" name="btnricheditor"  id="button4" value="添加内容" />';
			html += '<input id="'+timestamp+'" name="'+timestamp+'" type="hidden"/>';
			html += ' <input class="my_zt_scmk" type="button" name="delmodule" id="button5" value="删除此模块" /></td>';
			html += '   </tr>';
			html += ' <tr>';
			html += ' <td height="36" background="../images/personal/zt/myzt_20.gif" class="zuoju"><strong>标题：';
			html += ' <input type="text" style="width:400px;" name="title_'+timestamp+'" id="title_'+timestamp+'" />';
			html += ' </strong></td>';
			html += ' </tr>';
			html += ' <tr>';
			html += ' <td id="readtext_'+timestamp+'" width="8%" height="54" bgcolor="#FFFFFF" class="zuoju my_zt_doc">';
			html += '</td>';
			html += ' </tr>';
			html += ' </table>';
		}
		$("#modulelist").append(html);
		
		$("input[name='delmodule']").click(function(){
			var temp = $(this).parent().parent().parent().parent().attr("time");
			$(this).parent().parent().parent().parent().remove();
			timepool = timepool.replace(temp+",","");
		});
	   $("input[name='btneditor']").click(function(){
		   curtableId =$(this).parent().parent().parent().parent().attr("id");
	       $("#editorDailog").dialog("open");
	       getDataList(page_index, classification_id);
	    });
	   $("input[name='btnricheditor']").click(function(){
		   curtableId =$(this).parent().parent().parent().parent().attr("id");
	       $("#editorHTML").dialog("open");
	   });
	});
	
	$("#save_btn").click(function(){
		 var temp = timepool.split(",");
		 var themeId = '${personalTheme.id }';
		  
		 var titles = '';
		 var values = '';
		 var types  = '';
		 
		 for(var i = 0;i<temp.length;i++){
			 if(temp[i]==""){continue;}
			 var type = $("#"+temp[i]).parent().parent().parent().parent().attr("type");
			 
			 var value = $("#"+temp[i]).val();
			 var title = '';
			 var typenum = 0 ;
			 if(type == 'knowledge'){
				 title = $("#knowledgetitle_"+temp[i]).val();
				 typenum = 1;
			 }
			 if(type == 'detail'){
				  title =  $("#title_"+temp[i]).val();
				  typenum = 2;
			 }
			 if(type == 'rich'){
				  title = $("#title_"+temp[i]).val();
				  typenum=0;
				  value = $("#readtext_"+temp[i]).html();
			 }
			 titles += title+"@#@";
			 types += typenum+"@#@";
			 values += value+"@#@";
		 }
		
		 $.ajax({
				type : "POST",
				url : "${pageContext.request.contextPath}/theme/saveThemeDetail?"+Math.random(),
				data : {
					"themeId":themeId,
					"titles":titles,
					"values":values,
					 "types":types
				},
				dataType : 'json',
				success : function(msg) {
					if(msg){
						alert("保存成功");
					}else{
						alert("保存失败");
					}	  
				}
			});
	});
});

function listForCid(cId) {
	classification_id = cId ;
	$("#Pagination").html('');
	getDataList(page_index, classification_id) ;
} 
</script>
</body>

</html>