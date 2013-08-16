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
    <script src="${pageContext.request.contextPath}/js/tag.js" type="text/javascript"></script>
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
    </style>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<sec:authorize ifAllGranted="BACKGROUND_ONLYREAD">
		<input type="hidden" id="backGround_read" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_ONLYREAD">
		<input type="hidden" id="backGround_read" value=""/>
	</sec:authorize>
    <div region="west" split="true" title="标签树" style="width: 180px;" id="west">
        <ul id="tree">
        </ul>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden">
       <div class="easyui-layout" id="subWrap" fit="true" style="width:100%;height:100%;">
        <div data-options="region:'north',title:'查 询',split:false " style="height:110px;background:#fafafa;">
		      <div id="p" style="padding:10px;">  
				<span class="qitem" >
					<label>标签名称:</label>
					<input id="name" name="name" style="width:100px"/>
				</span>
				<span class="qitem">
					<label>首字母:</label>
					<input id="tagNo" name="tagNo" style="width:200px"/>
				</span>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()" style="margin-top:5px;margin-left:500px;" >查询</a>
			</div> 
		</div>  
		<div data-options="region:'center'" style="padding:5px;background:#eee;">
    	      <div id="grid"  > </div>
		</div>
	 </div> 
    </div>
    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons" data-options="modal:true">
		<div class="ftitle">标签信息</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>序号:</label>
				<input id="sortNo" name="sortNo">
			</div>
			<div class="fitem">
				<input type="hidden" id="level" name="level">
				<input type="hidden"  id="parentCode" name="parentCode">
				<input type="hidden"  name="code" >
				<input type="hidden" id="parentId" name="parentId">
				<input type="hidden"  name="leaf">
				<input type="hidden"  id="id" name="id" >
				<label>标签名称:</label>
				<input name="name" class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>备注:</label>
				<input name="summary">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveTag()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
    <div id="search-window" title="查询窗口" style="width: 350px; height: 200px;">
        <div style="padding: 20px 20px 40px 80px;">
            <table>
                <tr>
                    <td>
                      	  标签名称：
                    </td>
                    <td>
                        <input name="s_title" id="s_title" style="width: 150px;" />
                    </td>
                </tr>
            </table>
        </div>
        <div style="text-align: center; padding: 5px;">
            <a href="javascript:void(0)" onclick="SearchOK()" id="btn-search" icon="icon-ok">确定</a>
            <a href="javascript:void(0)" onclick="closeSearchWindow()" id="btn-search-cancel" icon="icon-cancel">
                取消</a>
        </div>
    </div>
</body>
</html>
