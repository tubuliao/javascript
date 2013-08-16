<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title> 
	<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/editor_config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor/editor_all.js"></script>
	 <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>
     
	 <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
	<!--<script src="${pageContext.request.contextPath}/js/source.js" type="text/javascript"></script> -->
    <script src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js" type="text/javascript" ></script>
	<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js"></script>
	
    
    <script type="text/javascript">
		var editor_a;
		var editor_b;

		$(function(){
			//alert('xx') ;
			editor_a = new baidu.editor.ui.Editor();
			editor_b = new baidu.editor.ui.Editor() ;

	        editor_a.render('myEditor01');
			editor_b.render('myEditor02');
		});

		function saveSource() {
			
			$('#catalog').val(editor_b.getContent()) ;
			$('#description').val(editor_a.getContent()) ;
			//alert('xxx') ;
			$('#fm').form('submit', {
				url: '${pageContent.request.contextPath}/source/saveSource',
				onSubmit: function() {
					return $(this).form('validate') ;
				},
				success: function(result) {
					var result = eval('('+result+')');
					if(result.success) {
						window.location.href = '${pageContent.request.contextPath}/source' ;
					} else {
						$.messager.alert('错误：', '保存失败！', 'error') ;
					}
				}
			}) ;
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
			width:100px;
		}
		.qitem label{
			display:inline-block;
			width:60px;
			margin-left:90px;
		}
		.hidden{
			display:none;
		}
		#myEditor01{
            width: 500px;
            height: 300px;
        }
		#myEditor02{
            width: 500px;
            height: 300px;
        }
    </style>
</head>
<body>
		<form id="fm" method="post" novalidate>
			<div class="ftitle">来源信息</div>
			<div class="fitem">
				<label>标准类型:</label>
				<input name="standardType" class="easyui-validatebox" required="true" value="${source.standardType}" style="width:500px;" />
				<input name="id" value="${source.id}" type="hidden" />
			</div>
			<br />
			<div class="fitem">
				<label>标准名称:</label>
				<input name="standardName" class="easyui-validatebox" required="true" validType="validateUser[5,18]" value="${source.standardName}" style="width:500px;"/>
			</div>
			<br />
			<div class="fitem">
				<label>标准编号:</label>
				<input name="standardNo" class="easyui-validatebox" required="true" value="${source.standardNo}" />
			</div>
			<br />
			<div class="fitem">
				<label>被替标准编号:</label>
				<input name="changeNo" class="easyui-validatebox" required="true" value="${source.changeNo}" />
			</div>
			<br />
			<div class="fitem">
				<label>英文名称:</label>
				<input name="englishName" class="easyui-validatebox" required="true" value="${source.englishName}" style="width:500px;"/>
			</div>
			<br />
			<div class="fitem">
				<label>主编单位:</label>
				<input name="editDepartment" class="easyui-validatebox" validType="validateLen[25]" value="${source.editDepartment}" style="width:500px;" />
			</div>
			<br />
			<div class="fitem">
				<label>批准部门:</label>
				<input name="approveDepartment" class="easyui-validatebox" validType="validateLen[25]" value="${source.approveDepartment}" style="width:500px;" />
			</div>
			<br />
			<div class="fitem">
				<label>实施日期:</label>
				<input type="text" class="Wdate" name="executeDate02" id="executeDate02" onClick="WdatePicker()" value="${source.executeDate}" style="width:200px;"/>
			</div>
			<br />
			<div class="ftitle">标准目次:</div>
			<div class="fitem">
				<label></label>
				<textarea id="myEditor02">${source.catalog}</textarea>
				<input id="catalog" name="catalog" type="hidden" />
			</div>
			<br />
			<div class="fitem">
				<label>标准目的:</label>
				<input name="goal" class="easyui-validatebox" value="${source.goal}" style="width:500px;" />
			</div>
			<br />
			<div class="fitem">
				<label>适用范围:</label>
				<input name="scope" class="easyui-validatebox" value="${source.scope}" style="width:500px;" />
			</div>
			<br />
			<div class="ftitle">标准介绍</div>
			<div class="fitem">
				<textarea id="myEditor01">${source.description}</textarea>
				<input id="description" name="description" type="hidden" />
			</div>
			<br />
			<div class="ftitle">
				<label>切片数量:</label>
				<input name="segmentCount" value="${source.segmentCount}" />
			</div>
		</form>
	
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveSource()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:history.go(-1)">取消</a>
	</div>

</body>
</html>
