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
    <script src="${pageContext.request.contextPath}/js/suggest.js" type="text/javascript"></script>
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
        <div id="grid" >
        </div>
    </div>
     
</body>
</html>
