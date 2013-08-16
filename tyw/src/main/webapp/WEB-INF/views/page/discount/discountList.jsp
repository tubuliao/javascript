<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/discount.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js" type="text/javascript" ></script>
	
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
		.STYLE1 {color: #FF0000}
    </style>

</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	
    <div region="north" title="查 询" style="height: 110px;background:#fafafa;" id="west">
    	<div id="p" style="padding:10px;">  
			<span class="qitem">
				<label>通道名称:</label>
				<input id="discountName" name="discountName">
			</span>
			<span class="qitem">
				<label>优惠编号:</label>
				<input id="discountCode" name="discountCode">
			</span>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()" style="margin-top:5px;margin-left:900px;">查询</a> 
		</div> 
    </div>


   <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden">
        <div id="grid" >
        </div>
    </div>
    <div id="dlg" class="easyui-dialog" style="width:500px;height:500px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons" data-options="modal:true">
		<div class="ftitle">编辑优惠通道</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>通道名称:</label>
				<input name="discountName" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>优惠编号:</label>
				<input name="discountCode" class="easyui-validatebox" required="true">
				
			</div>
			<div class="fitem">
				<label>优惠折扣:</label>
				<input name="discountValue" class="easyui-validatebox" required="true" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
				<span class="STYLE1">* &nbsp;&nbsp;只能输入数字.</span>
			</div>
			<div class="fitem">
				<label>优惠说明:</label>
				<input name="remark" class="easyui-validatebox" >
			</div>
			<div class="fitem">
				<label>有效期始:</label>
				<input name="strValidStartDate" id="strValidStartDate" class="easyui-datebox">
			</div>
			<div class="fitem">
				<label>有效期至:</label>
				<input name="strValidEndDate" id="strValidEndDate"  class="easyui-datebox">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	
	 <div id="dlg_view" class="easyui-dialog" style="width:500px;height:500px;padding:10px 20px"
			closed="true" buttons="#view-buttons" data-options="modal:true">
		<div class="ftitle">查看优惠通道</div>
		<form id="fm1" method="post" novalidate>
			<div class="fitem">
				<label>通道名称:</label>
				<input name="discountName" class="easyui-validatebox" readonly="true">
			</div>
			<div class="fitem">
				<label>优惠编号:</label>
				<input name="discountCode" class="easyui-validatebox" readonly="true">
				
			</div>
			<div class="fitem">
				<label>优惠折扣:</label>
				<input name="discountValue" class="easyui-validatebox" readonly="true">
			</div>
			<div class="fitem">
				<label>优惠说明:</label>
				<input name="remark" class="easyui-validatebox" readonly="true">
			</div>
			<div class="fitem">
				<label>有效期始:</label>
				<input name="strValidStartDate" id="strValidStartDate1" class="easyui-datebox" disabled="true">
			</div>
			<div class="fitem">
				<label>有效期至:</label>
				<input name="strValidEndDate" id="strValidEndDate1"  class="easyui-datebox" disabled="true">
			</div>
		</form>
	</div>
	<div id="view-buttons">
		
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_view').dialog('close')">取消</a>
	</div>
	
</body>
</html>
