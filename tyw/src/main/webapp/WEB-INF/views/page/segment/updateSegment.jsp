<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%-- <%@ include file="/include/common.jsp" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/editor_config.js"></script>
    <!--使用版-->
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/editor_all.js"></script>
    
    <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js"></script>
    <script src="${pageContext.request.contextPath}/js/tagListPlus.js" type="text/javascript"></script>
    <script type="text/javascript"u>
    	var editor_a;
    	var appendObjs=new Array();
    	$(function(){
			editor_a = new baidu.editor.ui.Editor();
	        //渲染编辑器
	        editor_a.render('myEditor');
			
			//初始化已选标签列表
			$('#tagList').datagrid({  
	    	    url:'${pageContext.request.contextPath}/segment/tags/${segment.id}', 
	    	    rownumbers:true, 
	    	    toolbar: [
	    	    {iconCls: 'icon-edit',text: '选择标签',handler: function(){$('#tag-window').window('open');initTags();}},
	    	    '-',
	    	    {iconCls: 'icon-remove',text: '删除标签',handler: function(){var selected=$('#tagList').datagrid('getSelections'); $.each(selected,function(i){ deletetRow(selected[i]); });}
				}],
	    	    columns:[[  
	    	        {field:'id',title:'id',width:100,hidden:true},  
	    	        {field:'name',title:'标签名',width:100},
	    	        {field:'manager',title:'操作',width:50}
	    	    ]]  
	    	});
	        $("#tag-window").window("close");//初始化关闭
    	});
    	
    	
    	function saveSegment(){
    		var index = $("#index").val();
			$('#content').val(editor_a.getContent());//赋值
			setTagIds();
			$('#fm').form('submit',{
				url: '${pageContext.request.contextPath}/segment/save',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');
					if (result.success){
						//location.href='${pageContext.request.contextPath}/segment';
						window.close();
						//opener.refreshRow(index);
						//window.opener.rowRefresh();
					} else {
						$.messager.show({
							title: '错误',
							msg: result.msg
						});
					}
				}
			});
    	}
    </script>
    <style type="text/css">
        #fm{
			margin:0;
			padding:10px 30px;
		}
		.ftitle{
			font-size:14px;
			font-weight:bold;
			color:#666;
			padding:5px 0;
			margin-bottom:10px;
			border-bottom:1px solid #ccc;
		}
		.fitem{
			margin-bottom:5px;
		}
		.fitem label{
			display:inline-block;
			width:80px;
		}
		#myEditor{
            width: 500px;
            height: 300px;
        }
    </style>
</head>

<body>
		<form id="fm" method="post" novalidate>
			<div class="ftitle">切片基础信息</div>
			<div class="fitem">
				<label>标题:</label>
				<input name="title" class="easyui-validatebox" required="true" value="${segment.title}" style="width:400px" />
				<input name="id" type="hidden"  value="${segment.id}" style="width:400px" />
				<input id="index" type="hidden" value="${index}" />
				<!--
				<input name="insertId" type="hidden" value="${segment.insertId}" />
				<input name="insertName" type="hidden" value="${segment.insertName}" />
				<input name="createDate02" type="hidden" value="${segment.createDate}" />
				-->
				<input name="checkId" type="hidden" value="${segment.checkId}" />
				<input name="checkName" type="hidden" value="${segment.checkName}" />
				<input name="checkDate02" type="hidden" value="${segment.checkDate}" />
				<input name="state" type="hidden" value="${segment.state}" />
				<input name="weighing" type="hidden" value="${segment.weighing }" />
			</div>
			<div class="fitem">
				<label>简标题:</label>
				<input name="shortTitle" class="easyui-validatebox" style="width:300px" value="${segment.shortTitle}" />
			</div>
			<div class="fitem">
				<label>条目:</label>
				<input type="text" name="segItem" id="segItem" style="width:200px" value="${segment.segItem }"/>
			</div>
			<div class="fitem">
				<label>摘要:</label>
				<textarea name="summary" style="width:550px;height:100px">${segment.summary}</textarea>
			</div>
			<div class="fitem">
				<label>发布时间:</label>
				<input type="text" id="begincreateDate02" name="begincreateDate02" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate  value="${segment.begincreateDate}" type="both" pattern="yyyy-MM-dd " />' />
			</div>
			<div class="fitem">
				<label>来源:</label>
				<input type="text" id="source" name="source" style="width:400px" value="${segment.source}" style="width:400px" />
			</div>
			<c:if test="${segment.state == 2}">
				<div class="fitem">
					<label><font>驳回原因:</font></label>
					<input type="text" id="auditInfo" name="auditInfo" style="width:400px; color:red;" value="${segment.auditInfo}" readonly/>
				</div>
			</c:if>
			
			<div class="ftitle">标签信息</div>
			<div class="fitem">
				<div id="tagList" title="标签列表" class="easyui-datagrid" style="width:650px;height:200px"></div>
				<input id="tags" name="tags" type="hidden" /> 
			</div>
			<div class="ftitle">切片内容信息</div>
			<div class="fitem">
				<label></label>
				<textarea id="myEditor">${segment.content}</textarea>
				<input id="content" name="content" type="hidden">
			</div>
		</form>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveSegment()">保存</a>
		<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-back" onclick="javascript:history.go(-1)">返回</a> -->
		<a href="#" class="easyui-linkbutton" iconCls="icon-back" onclick="javascript:window.close();">返回</a>
	</div>

   
	<div id="tag-window" class="easyui-window" style="width:600px;height:400px" title="标签" data-options="iconCls:'icon-tag',modal:true">  
        <div class="easyui-layout" data-options="fit:true">  
            <div data-options="region:'east',split:true" style="width:150px;padding:5px;">
            	<div id="formTitle"></div>
            </div>  
            <div data-options="region:'center'" style="padding:10px;">  
                <div id="formContent" style="margin-left:10px">
              		
                </div>
                 <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">  
              	  <a class="easyui-linkbutton"   icon="icon-back"   href="javascript:getHisMsg()">返回</a>  
            	</div> 
            </div>  
            
          
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">  
                <a class="easyui-linkbutton"  data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:$('#tag-window').window('close')">关闭</a>  
            </div>  
           
        </div>  
    </div>  
    
    <input value="" id="parentId" type="hidden"/>
</body>
</html>
