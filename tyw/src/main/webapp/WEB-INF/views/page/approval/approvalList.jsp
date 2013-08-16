<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
  
    <script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js"></script>
	<script src="${pageContext.request.contextPath}/js/approval.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function() {
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
			width: 100px;
		}
		.fitem label{
			display:inline-block;
			width:80px;
		}
		.qitem label{
			display:inline-block;
			margin-left:10px;
		}
		.hidden{
			display:none;
		}
    </style>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<!-- 批量同意删除权限start -->
	<sec:authorize ifAllGranted="BACKGROUND_BAT_DELETE">
		<input type="hidden" id="backGround_bat_delete" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_BAT_DELETE">
		<input type="hidden" id="backGround_bat_delete" value=""/>
	</sec:authorize>
<!-- 批量同意删除权限end -->
	<div region="west" split="true" title="标签树" style="width: 180px;" id="west">
        <ul id="tree">
        </ul>
    </div>
   <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden">
	    <div class="easyui-layout" id="subWrap" fit="true" style="width:110%;height:100%;">
       	 	<div data-options="region:'north',title:'查 询',split:false" style="height:120px;;background:#fafafa;">
		    	<div style="padding:10px;">  
		    		<span class="qitem" >
						<label>知识类型：</label>
						<select id="serachInfoType">
							<option value="0">全部</option>
							<option value="1">文件</option>
							<option value="2">切片</option>
							<option value="3">视频</option>
							<option value="4">表格</option>
							<option value="5">图片</option>
							<option value="6">PPT</option>
						</select>
					</span>
					<span class="qitem" >
						<label>申请删除：</label>
						<select id="serachApplyDetele">
							<option value="">全部</option>
							<option value="1">已申请</option>
							<option value="0">未申请</option>
							
						</select>
					</span>
					<span class="qitem" >
						<label>标题：</label>
						<input type="text" id="serachTitle" name="serachTitle" />
					</span>
					<span class="qitem" >
						<label>录入人：</label>
						<input type="text" id="serachInsertName" name="searchInsertName" style="width:60px;"/>
					</span>
					<span class="qitem" >
						<label>来源：</label>
						<input type="text" id="serachSource" name="serachSource" />
					</span>
				</div> 
				<div style="padding:10px;">
					<span class="qitem" >
						<label>录入时间</label>
						From：<input type="text" id="searchCreateDateFrom" name="searchCreateDateFrom" class="Wdate" onClick="WdatePicker()" style="width:100px;" />&nbsp;&nbsp;
						To：<input type="text" id="searchCreateDateTo" name="searchCreateDateTo" class="Wdate" onClick="WdatePicker()" style="width:100px;" />
					</span>
					<span class="qitem">
						<label>审批状态：</label>
						<select id="searchState">
							<option value="">全部</option>
							<option value="0">未审批</option>
							<option value="1">审批通过 </option>
							<option value="2">驳回</option>
							<option value="3">已二次审批</option>
						</select>
					</span>
					<span class="qitem">
						<label>标题重复：</label>
						<input type="checkbox" id="titleRepeat" />
					</span>
					<span class="qitem">
						<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="query();" style="margin-left:100px;">查询</a> 
					</span>
				</div>
		    </div>
		     
		    <div data-options="region:'center'" style="padding:5px;background:#eee;">
		        <div id="gridcenter" >
		        </div>
		    </div>
	    </div>
	 </div>
	 <!-- 修改来源 -->
	 
	  <div id="source_dialog" class="easyui-dialog" style="width:400px;height:200px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons" iconcls="icon-edit" data-options="modal:true">
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<div class="ftitle">来源: </div>
				<div class="fitem" >
					<input id="newSource" name="newSource" class="easyui-validatebox" style="width:300px;" />
				</div>
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveNewSource()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#source_dialog').dialog('close')">取消</a>
	</div>
</body>
</html>
