<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
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
     
    <link href="${pageContext.request.contextPath}/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/uploadify/jquery.uploadify-3.1.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/tagListPlus.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js"type="text/javascript"></script>
    <script type="text/javascript">
    	$(function(){
  
    	
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

<body>
		<form id="fm" method="post" novalidate>
			<div class="ftitle">基础信息</div>
			<div class="fitem">
				<label>名称:</label>
				<input name="name" class="easyui-validatebox" required="true" validType="validName" style="width:200px;" value="${thesaures.name}"/>
				<input name="id" type="hidden" value="${thesaures.id}" />
			</div>
			<div class="fitem">
				<label>长度:</label>
				<input name="len" class="easyui-validatebox" required="true" validType="validLen" style="width:200px;" value="${thesaures.len}"/>
			</div>
		</form>
	<div id="dlg-buttons" style="padding:30px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveThesaures()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-back" onclick="javascript:history.go(-1)">返回</a>
	</div>
	
</body>
<script>
function saveThesaures(){
	$('#fm').form('submit',{
		url: '${pageContext.request.contextPath}/saveThesaures',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(r){
			var result = eval('('+r+')');
			if (result.success){
				location.href='${pageContext.request.contextPath}/thesaures';
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
</html>
