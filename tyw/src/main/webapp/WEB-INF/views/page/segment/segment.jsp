<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%-- <%@ include file="/include/common.jsp" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
    <title></title>
    
     <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/datagrid-detailview.js" type="text/javascript"></script>
  
    <script src="${pageContext.request.contextPath}/js/segment.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js" type="text/javascript" ></script>
    <script src="${pageContext.request.contextPath}/uploadify/jquery.uploadify-3.1.js" type="text/javascript"></script>
    <link href="${pageContext.request.contextPath}/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
     <script type="text/javascript">
    	$(function(){
    		$("#uploadify").uploadify({
    			'swf':'${pageContext.request.contextPath}/uploadify/uploadify.swf',
    			'uploader':'${pageContext.request.contextPath}/segment/upload',
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
			margin-left:10px;
		} 
    </style>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<jsp:include page="${pageContext.request.contextPath}/authorize.jsp" />
	
    <div region="west" split="false" title="标签树" style="width:180px;" id="west">
        <ul id="tree">
        </ul>
    </div>
   <div region="center"  style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden">
   	 <div class="easyui-layout" id="subWrap" fit="true" style="width:100%;height:100%;">
   	 	<div data-options="region:'north',title:'查 询',split:false" style="height:120px;;background:#fafafa;">
		      <div id="p" style="padding:10px;">  
				<span class="qitem">
					<label>来源：</label>
					<input id="searchSource" name="searchSource" style="width:200px"/>
				</span>
				<span class="qitem" >
					<label>标题：</label>
					<input id="searchTitle" name="searchTitle" style="width:100px"/>
				</span>
				<span class="qitem" >
					<label>录入人：</label>
					<input id="searchInsertName" name="searchInsertName" style="width:100px"/>
				</span>
				<span class="qitem">
					<label>状态：</label>
					<select id="searchStatus" name="searchStatus">
						<option  value="10000">全部</option>
						<option value="1">已审批</option>
						<option value="0">未审批</option>
						<option value="2">已驳回</option>
						<option value="3">已二次审批</option>
					</select>
				</span>
			</div> 
			<div id="p" style="padding:10px;">  
				<span class="qitem">
					<label>发布日期：</label>
					<input type="text" id="searchBegincreateDate" name="searchBegincreateDate" class="Wdate" onClick="WdatePicker()" style="width:100px;" />
					<font style="color:blue;">（若未设置，系统默认为"1970-01-01 00:00:00"）</font>&nbsp;&nbsp;
				</span>
				<span class="qitem">
					<label>知识等级：</label>
					<select id="searchLevel" name="searchLevel">
						<option value="10000">全部</option>
						<c:forEach items="${kLevel }" var="kl">
							<option value="${kl}">${kl}级</option>
						</c:forEach>
					</select>
					<font style="color:blue;">（系统默认为"7"级）</font>
				</span>
				<span><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()" style="margin-left:100px;">查询</a></span>
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
    <!--
    <div id="segment-window" class="easyui-window" title="切片预览" data-options="iconCls:'icon-tip'">  
        <div class="easyui-layout" data-options="fit:true">  
            <div data-options="region:'east',split:true" style="width:150px;padding:5px;">
            	<div id="segmentTitle"></div>
            	<div id="segmentCreateDate"></div>
            	<div id="segmentinsertName"></div>
            	<div id="segmenttags"></div>
            </div>  
            <div data-options="region:'center'" style="padding:10px;">  
                <div id="segmentContent" style="margin-left:10px"></div>
            </div>  
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">  
                <a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="approveSegment('1')">通过</a>  
                <a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="approveSegment('2')">驳回</a>  
            </div>  
        </div>  
    </div>  
    -->
    
    <div id="search-window" title="查询窗口" style="width: 350px; height: 200px;">
        <div style="padding: 20px 20px 40px 80px;">
            <table>
                <tr>
                    <td>
                      	 标题：
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
