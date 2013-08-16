<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%-- <%@ include file="/include/common.jsp" %> --%>
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
    
    <script src="${pageContext.request.contextPath}/uploadify/jquery.uploadify-3.1.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/question.js" type="text/javascript"></script>
    <script type="text/javascript">
    
    	$(function(){
			$("#excelUploadify").uploadify({
    			'swf':'${pageContext.request.contextPath}/uploadify/uploadify.swf',
    			'uploader':'${pageContext.request.contextPath}/ppt/excelUpload',
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
    					$('#grid').datagrid('reload') ;
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
		#myEditor{
            width: 500px;
            height: 300px;
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
    <div region="west" split="false" title="标签树" style="width: 180px;" id="west">
        <ul id="tree">
        </ul>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden">
       <div class="easyui-layout" id="subWrap" fit="true" style="width:100%;height:100%;">
        <div data-options="region:'north',title:'查 询',split:false" style="height:120px;background:#fafafa;">
		      <div id="p" style="padding:10px;">  
		      	
		      	<span class="qitem" >
					<label>问题内容:</label>
					<input id="searchTitle" name="searchTitle" style="width:100px"/>
				</span>
				<span class="qitem" >
					<label>录入人:</label>
					<input id="searchInsertName" name="searchInsertName" style="width:100px"/>
				</span>
			
			</div> 
			<div id="p02" style="padding:10px;">
				<span class="qitem" style="padding-left:10px;">
					<label>审批状态:</label>
					<select id="searchStatus" name="searchStatus">
						<option  value="10000">全部</option>
						<option value="1">已审批</option>
						<option value="0">未审批</option>
					</select>
				</span>
				
				<span class="qitem">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()" style="padding-left: 30px;" >查询</a>
				</span>
			</div>
		</div>  
		<div data-options="region:'center'" style="padding:5px;background:#eee;">
    	      <div id="grid"  > </div>
		</div>
	 </div>
    </div>
    
 
   <div id="dlg" class="easyui-dialog" style="width:700px;height:440px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons" data-options="modal:true">
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				答案:<textarea id="content" name="content" rows="10" cols="30"></textarea>
				<input type="hidden" name="quesId" id="quesId" value=""/>
			</div>
		</form>
	    <table id="ansgrid" class="easyui-datagrid" style="width:630px;height:220px;">
	    </table> 
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveAns()">保存答案</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</body>
</html>
