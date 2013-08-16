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
    <script src="${pageContext.request.contextPath}/js/sourceviewlist.js" type="text/javascript"></script>
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
    <div region="north" title="查 询" style="height: 120px;background:#fafafa;" id="west">
    	<div id="p" style="padding:10px;">  
			<table>
				<tr>
					<td>
						<span class="qitem" >
							<label>知识类型:</label>
							<select id="knowledgeType" name="knowledgeType">
								<option value="全部">全部</option>
								<option value="文件">文件</option>
								<option value="切片">切片</option>
								<option value="视频">视频</option>
								<option value="表格">表格</option>
								<option value="图片">图片</option>
								<option value="PPT">PPT</option>
							</select>
						</span>
					</td>
					<td>
						<span class="qitem" >
							<label>来源类型:</label>
							<input id="sourceType" name="sourceType" style="width:100px"/>
						</span>
					</td>
				</tr>
				<tr>
					<td>
						<span class="qitem" >
							<label>来源:</label>
							<input id="sources" name="sources" style="width:300px"/>
						</span>
					</td>
					<td>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()" style="padding-left:200px;">查询</a> 
					</td>
				</tr>
			</table> 
		</div> 
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden">
        <div id="grid" >
        </div>
    </div>
</body>
</html>
