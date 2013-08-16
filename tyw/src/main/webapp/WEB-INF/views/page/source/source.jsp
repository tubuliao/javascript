<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="${pageContext.request.contextPath}/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
    
     <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/source.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js" type="text/javascript" ></script>
    <script src="${pageContext.request.contextPath}/uploadify/jquery.uploadify-3.1.js" type="text/javascript"></script>
    <script type="text/javascript">
    	$(function(){
    		$("#uploadify").uploadify({
    			'swf':'${pageContext.request.contextPath}/uploadify/uploadify.swf',
    			'uploader':'${pageContext.request.contextPath}/source/upload',
    			'queueID':'fileQueue',
    			'buttonText': '选择文件',  
    			'height': 20,   
    			'multi':false,
        		'width': 80, 
        		'fileTypeExts':'*.xls;*.xlsx',
        		'fileTypeDesc':'Excel文件',
    			'onUploadSuccess':function(file,data,response){
    				if('success' == data) {
    					$.messager.alert('提示', '文件上传成功！') ;
    				} else {
    					$.messager.alert('提示', '文件上传失败！') ;	 
    				}
    			},
    			'onUploadError':function(file,errorCode,errorMsg,errorString){
    				$.messager.alert('错误','文件上传出错 ,错误代码:'+errorCode); 
    			}
    		});
			
    	});
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
		.qitem label{
			display:inline-block;
			width:60px;
			margin-left:90px;
		}
		.hidden{
			display:none;
		}
    </style>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<sec:authorize ifAllGranted="BACKGROUND_ONLYREAD">
		<input type="hidden" id="backGround_read" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_ONLYREAD">
		<input type="hidden" id="backGround_read" value=""/>
	</sec:authorize>    
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden">
       <div class="easyui-layout" id="subWrap" fit="true" style="width:100%;height:100%;">
        <div data-options="region:'north',title:'查 询',split:false" style="height:145px;background:#fafafa;">
		      <div id="p" style="padding:10px;">   
		      	<table>
				<tr>
					<td>
						<span class="qitem">
							<label>标准名称:</label>
							<input id="standardName" name="standardName" style="width:100px"/>
						</span>
					</td>
					<td>
				      	<span class="qitem" >
							<label>标准编号:</label>
							<input id="standardNo" name="standardNo" style="width:100px"/>
						</span>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>
						<span class="qitem" >
							<label>主编单位:</label>
							<input id="editDepartment" name="editDepartment" style="width:150px"/>
						</span>
					</td>
					<td>
						<span class="qitem" >
							<label>批准部门:</label>
							<input id="approveDepartment" name="approveDepartment" style="width:150px"/>
						</span>
					</td>
					<td>
						<span class="qitem">
							<label>标准类型:</label>
							<input id="standardType" name="standardType" style="width:150px"/>
						</span>
					</td> 
				</tr>
			</table>  
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()" style="margin-top:5px;margin-left:900px;" >查询</a>
			</div> 
		</div>  
		<div data-options="region:'center'" style="padding:5px;background:#eee;">
    	      <div id="grid"  > </div>
		</div> 
	  </div>
    </div>
    
    
    <div id="dlg-xls" class="easyui-dialog" style="width:400px;height:200px;padding:10px 20px"
			closed="true" buttons="#dlg-xls-buttons" data-options="modal:true">
		<div class="ftitle">选择要导入的Excel文件</div>
			<div class="fitem">
				<div id="fileQueue"></div>
				<input type="file" name="uploadify" id="uploadify"/>
			</div>
	</div>
	<div id="dlg-xls-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg-xls').dialog('close')">取消</a>
	</div>
    <div id="dlg" class="easyui-dialog" style="width:500px;height:450px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons" data-options="modal:true">
		<div class="ftitle">来源信息</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>标准类型:</label>
				<input name="standardType" class="easyui-validatebox" required="true">
				<input name="id" type="hidden">
			</div>
			<div class="fitem">
				<label>标准名称:</label>
				<input name="standardName" class="easyui-validatebox" required="true" validType="validateUser[5,18]" >
			</div>
			<div class="fitem">
				<label>标准编号:</label>
				<input name="standardNo" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>被替标准编号:</label>
				<input name="changeNo" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>英文名称:</label>
				<input name="englishName" class="easyui-validatebox" required="true" />
			</div>
			<div class="fitem" id="ck_pass">
				<label>主编单位:</label>
				<input name="editDepartment" class="easyui-validatebox" validType="validateLen[25]" />
			</div>
			<div class="fitem">
				<label>批准部门:</label>
				<input name="approveDepartment" class="easyui-validatebox" validType="validateLen[25]" />
			</div>
			<div class="fitem">
				<label>实施日期:</label>
				<input name="executeDate" class="easyui-validatebox" />
			</div>
			<div class="fitem">
				<label>标准目次:</label>
				<input name="catalog" class="easyui-validatebox" />
			</div>
			<div class="fitem">
				<label>标准目的:</label>
				<input name="goal" class="easyui-validatebox" />
			</div>
			<div class="fitem">
				<label>适用范围:</label>
				<input name="scope" class="easyui-validatebox" />
			</div>
			<div class="fitem">
				<label>标准介绍:</label>
				<input name="description" class="easyui-validatebox" />
			</div>
			<div class="fitem">
				<label>切片数量:</label>
				<input name="segmentCount">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</body>
</html>
