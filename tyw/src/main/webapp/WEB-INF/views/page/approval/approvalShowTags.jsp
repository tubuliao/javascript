<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>批量标签</title>
	<link href="${pageContext.request.contextPath }/css/tablecloth.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
     
    <script src="${pageContext.request.contextPath}/js/tagListPlus.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/approval.js" type="text/javascript"></script>
    
    <script type="text/javascript">
    		 
    $(function() { 

    		
    	})	
    </script>
    <style>
	    body{
			margin:0;
			padding:0;
			background:#f1f1f1;
			font:70% Arial, Helvetica, sans-serif; 
			color:#555;
			line-height:150%;
			text-align:left;
		}
		
		form{
			margin:1em 0;
			padding:.2em 20px;
			background:#eee;
		}
		h1{
			font-size:140%;
			margin:0 20px;
			line-height:80px;	
		}
		.btn02 {  
		    display: inline-block;  
		    zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */  
		    *display: inline;  
		    vertical-align: baseline;  
		    margin: 0 2px;  
		    outline: none;  
		    cursor: pointer;  
		    text-align: center;  
		    text-decoration: none;  
		    font: 14px/100% Arial, Helvetica, sans-serif;  
		    padding: .5em 2em .55em;  
		    text-shadow: 0 1px 1px rgba(0,0,0,.3);  
		    -webkit-border-radius: .5em;   
		    -moz-border-radius: .5em;  
		    border-radius: .5em;  
		    -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.2);  
		    -moz-box-shadow: 0 1px 2px rgba(0,0,0,.2);  
		    box-shadow: 0 1px 2px rgba(0,0,0,.2);  
		    background-color: #009393;
		    font-weight:bold;
		}
		
    </style>
   
</head>

<body>
	<form id="fm">
		<input type="hidden" id="baseId"' value="${idStr }" />
		<table width="100%" border="1" cellspacing="0" cellpadding="0" >
			<caption style="font-size:30px;"><h1>批量标签<h1/></caption>
			<tr>
				<td colspan="6" style="text-align:right" >
					<input class='btn02' type="button" value="选择标签" onclick="chooseTag();"/>
				</td>
			</tr>
			<tr>
				<th style="text-align:center">编号</th>
				<th>标题</th>
				<th>分部分项</th>
				<th>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区</th>
				<th>人员分类</th>
				<th>知识性质</th>
			</tr>
			<c:forEach items="${allList}" var="oneDetail" varStatus="idx">
				<tr id="${oneDetail.baseData.id }">
					<!-- 编号 -->
					<td style="width:40px;text-align:center">${idx.index + 1 }</td>
					
					<!-- 标题 -->
					<td style="width:100px;">${oneDetail.baseData.title}</td>
					
					<!-- 分部分项 -->
					<td>
						<c:forEach items="${oneDetail.subitemTagList }" var="subitemTag">
							<span id="${oneDetail.baseData.id}${fn:substring(subitemTag.id, 0, 7)}">${subitemTag.name }&nbsp;<a href="javascript:void(0);" onclick="delTag('${oneDetail.baseData.id}','${subitemTag.id}', '${oneDetail.baseData.id}${fn:substring(subitemTag.id, 0, 7)}');"><img src="${pageContext.request.contextPath }/images/delTag.gif" width="8" height="8"/></a></span><br />
						</c:forEach>
					</td>
					
					<!-- 地区 -->
					<td>
						<c:forEach items="${oneDetail.areaTagList }" var="areaTag">
							<!--  <span id="${oneDetail.baseData.id}${fn:substring(areaTag.id, 0, 7)}">${areaTag.name }<input type="button" value="删除" onclick="delTag('${oneDetail.baseData.id}','${areaTag.id}', '${oneDetail.baseData.id}${fn:substring(areaTag.id, 0, 7)}');" /></span><br />-->
							<span id="${oneDetail.baseData.id}${fn:substring(areaTag.id, 0, 7)}">${areaTag.name }&nbsp;<a href="javascript:void(0);" onclick="delTag('${oneDetail.baseData.id}','${areaTag.id}', '${oneDetail.baseData.id}${fn:substring(areaTag.id, 0, 7)}');"><img src="${pageContext.request.contextPath }/images/delTag.gif" width="8" height="8"/></a></span><br />
						</c:forEach>
					</td>
					
					<!-- 人员分类 -->
					<td>
						<c:forEach items="${oneDetail.personnelTagList }" var="personnelTag">
							<!--  <span id="${oneDetail.baseData.id}${fn:substring(personnelTag.id, 0, 7)}">${personnelTag.name }<input type="button" value="删除" onclick="delTag('${oneDetail.baseData.id}','${personnelTag.id}', '${oneDetail.baseData.id}${fn:substring(personnelTag.id, 0, 7)}');" /></span><br /> -->
							<span id="${oneDetail.baseData.id}${fn:substring(personnelTag.id, 0, 7)}">${personnelTag.name }&nbsp;<a href="javascript:void(0);" onclick="delTag('${oneDetail.baseData.id}','${personnelTag.id}', '${oneDetail.baseData.id}${fn:substring(personnelTag.id, 0, 7)}');"><img src="${pageContext.request.contextPath }/images/delTag.gif" width="8" height="8"/></a></span><br />
						</c:forEach>
					</td>
					
					<!-- 知识性质 -->
					<td>
						<c:forEach items="${oneDetail.architectonicTagList }" var="architectonicTag">
							<!-- <span id="${oneDetail.baseData.id}${fn:substring(architectonicTag.id, 0, 7)}">${architectonicTag.name }<input type="button" value="删除" onclick="delTag('${oneDetail.baseData.id}','${architectonicTag.id}', '${oneDetail.baseData.id}${fn:substring(architectonicTag.id, 0, 7)}');" /></span><br /> -->
							<span id="${oneDetail.baseData.id}${fn:substring(architectonicTag.id, 0, 7)}">${architectonicTag.name }&nbsp;<a href="javascript:void(0);" onclick="delTag('${oneDetail.baseData.id}','${architectonicTag.id}', '${oneDetail.baseData.id}${fn:substring(architectonicTag.id, 0, 7)}');"><img src="${pageContext.request.contextPath }/images/delTag.gif" width="8" height="8"/></a></span><br />
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="6" style="text-align:right">
					<input type="button" class="btn02" value="批量审批通过" id="" onclick="approvalAudit();"/>
					<input type="button" class="btn02" value="关闭" id="cancel" onclick="javascript:window.close();"/>
				</td>
			</tr>
		</table>
	</form>
	<div id="tag-window" class="easyui-window" style="width:600px;height:400px" title="标签" data-options="iconCls:'icon-tag',modal:true, closed:true">  
        <div class="easyui-layout" data-options="fit:true">  
            <div data-options="region:'east',split:true" style="width:150px;padding:5px;">
            	<div id="formTitle"></div>
            </div>  
            <div data-options="region:'center'" style="padding:10px;">  
                <div id="formContent" style="margin-left:10px">
              		
                </div>
                 <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">  
              	  <a class="easyui-linkbutton"   icon="icon-back"   href="javascript:getHisMsg()">返回</a>  
            	</div> 
            </div>  
            
          
            <div data-options="region:'south',border:false" style="text-align:right;padding:15px;">
            	<a class="easyui-linkbutton"  data-options="iconCls:'icon-save'" href="javascript:void(0)" onClick="saveBaseAndTag();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;   
                <a class="easyui-linkbutton"  data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:$('#tag-window').window('close')">关闭</a>  
            </div>  
        </div>  
    </div>  
     <input value="" id="parentId" type="hidden"/>
</body>
</html>
