<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title></title>
	<script type="text/javascript" charset="utf-8" src="../../ueditor/editor_config.js"></script>
    <!--使用版-->
    <script type="text/javascript" charset="utf-8" src="../../ueditor/editor_all.js"></script>
    <link href="../../easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="../../easyui/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="../../easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="../../easyui/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="../../easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var editor_a;
    	var treeKeys=[];
    	$(function(){
    		$('#tags').combotree({
				url:'/tag/000',
				required:true,
				methord:'get',
				onBeforeExpand: function (node, param) {
		            $('#tags').combotree('tree').tree('options').url = "/tag/" + node.id; // change the url                       
		        },onCheck:function(node,checked){
		        	var nodes=$('#tags').combotree('tree').tree('getChecked');
		        	if(nodes.length>0){
		        		treeKeys=[];
		        		$('#tagContainer').empty(); //清空默认列表
		        		for(var i=0;i<nodes.length;i++){
		        			$('#tagContainer').append('<span style="margin-left:5px;"></span>').append('<img src="../../images/attach.gif">').append(nodes[i].text);
		        		}
		        	}
		        }
			});
			

			editor_a = new baidu.editor.ui.Editor();
	        //渲染编辑器
	        editor_a.render('myEditor');
			
			//初始化已选标签列表
			var treeValues=[];
			$('#tagContainer span').each(function(){
				treeValues.push($(this).text());
				treeKeys.push($(this).find('img').attr('alt'));
			});
			
			$('#tags').combotree('setValues',treeValues);
    	});
    	function saveAnnounce(){
			$('#content').val(editor_a.getContent());//赋值
			$('#fm').form('submit',{
				url: 'addAnnounce',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');
					if (result.success){
						location.href='announcelist';
					} else {
						$.messager.show({
							title: '错误',
							msg: result.msg
						});
					}
				}
			});
    	}
	function returnBack (){
		location.href='../../announcelist';
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
			<div class="ftitle">公告信息</div>
			<div class="fitem">
				<label>公告标题:</label>
				<input name="id" type="hidden"  value="${announce.id}">
				<input name="title" class="easyui-validatebox" required="true" value="${announce.title}" readonly="true">
			</div>
			<div class="fitem">
				<label>发布者:</label>
				<input id="level" name="issuedBy" class="easyui-validatebox" readonly="true" value="${announce.issuedBy}">
			</div>
			
			<div class="ftitle">公告内容</div>
			<div class="fitem">
				<label></label>
				<textarea id="myEditor" readonly="true">${announce.content}</textarea>
				<input id="content" name="content" type="hidden">
			</div>
		</form>

<a href="#" class="easyui-linkbutton" iconCls="icon-back" onclick="returnBack()">返回</a>
</body>
</html>
