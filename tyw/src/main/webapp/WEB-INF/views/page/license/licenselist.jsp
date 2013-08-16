<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/license.js" type="text/javascript"></script>
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
    <script type="text/javascript">
    	$(function(){
    		$('#qdscx').dialog('close');
    		$("#btn_qdscx").click(function(){
    			$('#qdscx').dialog('open');
    		});
    		$("#btn_qdsdel").click(function(){
    			$("#agentId").val("");
    			$("#agentName").val("");
    		});
    		$("#licenselist").dialog("close");
    	});
    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<sec:authorize ifAllGranted="BACKGROUND_ONLYREAD">
		<input type="hidden" id="backGround_read" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_ONLYREAD">
		<input type="hidden" id="backGround_read" value=""/>
	</sec:authorize>
    <div region="north" title="查 询" style="height: 140px;background:#fafafa;" id="west">
    	<div id="p" style="padding:10px;">  
			<table>
				<tr>
					<td>
						<span class="qitem" >
							<label>批次号:</label>
							<input id="licenseBatch" name="licenseBatch" style="width:100px"/>
						</span>
					</td>
					<td>
						<span class="qitem">
							<label>渠道商:</label>
							<input id="agentName" name="agentName" readonly style="width:100px"/> 
							<input id="agentId" name="agentId" type="hidden" value=""/> 
							<a id="btn_qdscx" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'"></a> 
							<a id="btn_qdsdel" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'"></a> 
						</span>
					</td>
				</tr>
			</table> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()" style="margin-top:5px;margin-left:900px;">查询</a> 
		</div> 
    </div>
    <div id="qdscx" class="easyui-dialog" title="渠道商列表" data-options="modal:true" 
    		style="width:600px;height:400px;padding:10px">  
         <div style="margin:10px 0;"></div>  
    <table id="qdsgrid" class="easyui-datagrid" style="width:500px;height:auto">  
         
    </table>  
    </div>  
    <div id="licenselist" class="easyui-dialog" title="序列号列表" data-options="modal:true" 
    		style="width:600px;height:400px;padding:10px">  
          
       <table id="licensegrid" class="easyui-datagrid" style="width:500px;height:auto">  
         
   	   </table>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden">
        <div id="grid" >
        </div>
    </div>
     <div id="dlg" class="easyui-dialog" style="width:550px;height:440px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons" data-options="modal:true">
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>渠道商:</label>
				<input id="agentNames" name="agentName" class="easyui-validatebox" required="true" readonly  />
				<input id="agentIds" name="agentId" type="hidden" value="" />
			</div>
			<div class="fitem">
				<label>15(人/包):</label>
				<input name="licenseFifTotal" class="easyui-numberbox" required="true" max="300"  />
			</div>
			<div class="fitem">
				<label>10(人/包):</label>
				<input name="licenseTenTotal" class="easyui-numberbox " required="true"  max="300"  />	
			</div>
		</form>
	    <table id="scgrid" class="easyui-datagrid" style="width:500px;height:220px;">  
	    </table> 
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">生成</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</body>
</html>
