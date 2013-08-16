<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
     
    <script src="${pageContext.request.contextPath}/uploadify/jquery.uploadify-3.1.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/receipt.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var extlistUrl='${pageContext.request.contextPath}/formextlist/0';
    
    	$(function(){
			
    	});
    	
    	//分页方法
		function pageExtUrl(pageSize,pageNo){
			return extlistUrl+"?page.page="+pageNo+"&page.size="+pageSize;
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
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<jsp:include page="${pageContext.request.contextPath}/authorize.jsp" />
    
     <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden">
       <div class="easyui-layout" id="subWrap" fit="true" style="width:100%;height:100%;">
        <div data-options="region:'north',title:'查 询',split:false" style="height:90px;;background:#fafafa;">
		      <div id="p" style="padding:10px;">  
				<span class="qitem">
					<label>序列号：</label>
					<input id="searchLicenseNumber" name="searchLicenseNumber" style="width:200px"/>&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
				<span class="qitem" >
					<label>发票状态：</label>
					<select id="searchStatus">
						<option value="">全部</option>
						<option value="0">未开票</option>
						<option value="1">已开票</option>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;
				</span>
				<span class="qitem" >
					<label>渠道商：</label>
					<select id="searchAgentName">
						<option value="">全部</option>
						<c:forEach items="${agentList}" var="a">
							<option value="${a.id }">${a.aliasname}</option>
						</c:forEach>
					</select>&nbsp;&nbsp;&nbsp;&nbsp;
				</span> 
				<span class="qitem" >
					<label>批次号：</label>
					<input id="searchBatchCode" name="searchBatchCode" style="width:100px"/>&nbsp;&nbsp;&nbsp;&nbsp;
				</span> 
				 
				<span><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()" style="padding-left:40px;">查询</a></span>
				 
			</div> 
		</div>  
		<div data-options="region:'center'" style="padding:5px;background:#eee;">
    	      <div id="grid"  > </div>
		</div> 
	 </div>
    </div>
    <!-- 批量状态 -->
 <div id="status_dialog" class="easyui-dialog" style="width:300px;height:180px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons" iconcls="icon-edit" data-options="modal:true">
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<div class="">发票状态：
					<select id="newStatus">
						<option value="0">未开票</option>
						<option value="1">已开票</option>
					</select>
				</div>
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveNewStatus()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#status_dialog').dialog('close')">取消</a>
	</div>
</body>
</html>
