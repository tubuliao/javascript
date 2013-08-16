<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"   %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%-- <%@ include file="/include/common.jsp" %> --%>
<%-- <jsp:include page="${pageContext.request.contextPath}/include/common.jsp" /> --%>
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
    	var editor_a;
    	var treeKeys=[];
    	var appendObjs=new Array();
    	var empHiPicUrlx = "" ;
    	var empLowPicUrlx = "" ;
    	var sampHiPicUrlx = "" ;
    	var sampLowPicUrlx = "" ;
    	$(function(){
    		
    		//初始化已选标签列表
			$('#tagList').datagrid({  
	    	    url:'${pageContext.request.contextPath}/form/tags/${form.id}', 
	    	    rownumbers:true, 
	    	    toolbar: [
	    	    {iconCls: 'icon-edit',text: '选择标签',handler: function(){$('#tag-window').window('open');initTags();}},
	    	    '-',
	    	    {iconCls: 'icon-remove',text: '删除标签',handler: function(){var selected=$('#tagList').datagrid('getSelections'); $.each(selected,function(i){ deletetRow(selected[i]); });}
				}],
	    	    columns:[[  
	    	        {field:'id',title:'id',width:100,hidden:true},  
	    	        {field:'name',title:'标签名',width:100},
	    	        {field:'manager',title:'操作',width:50}
	    	    ]]  
	    	});
	        $("#tag-window").window("close");//初始化关闭

    		
			editor_a = new baidu.editor.ui.Editor();
	        //渲染编辑器
	        editor_a.render('myEditor');
     
    	$("#uploadify").uploadify({
			'swf':'${pageContext.request.contextPath}/uploadify/uploadify.swf',
			'uploader':'${pageContext.request.contextPath}/form/upload/fdfs',
			'queueID':'fileQueue',
			'buttonText': '空表/样表 源文件',  
			'height': 20,   
			'multi':false,
    		'width': 100, 
    		'fileTypeDesc' : 'Excle或PDF或Word文件',
    		'fileTypeExts':'*.xls;*.xlsx;*.pdf;*.doc;*.docx',
			'onUploadSuccess':function(file,data,response){
				/* 
				var originalUrl = $('#empUrl').val();
				if(originalUrl != '') {	// 源文件替换
					originalUrl = $('#empUrl').val().replace(new RegExp('/', 'gm'), '&');
					$.get('${pageContext.request.contextPath}/form/fdfsDel', { url: originalUrl}, function(r) {
						alert('sd');
						if(r.success) {
							$('#fileContainer').empty(); //清空默认列表
				        	$('#fileContainer').append('<img src="${pageContext.request.contextPath}/images/attach.gif">').append(data);
							$('#empUrl').val(data);
							$.messager.show({
								title: '提示：',
								msg: '更新源文件成功！'
							});
						} else {
							$.messager.show({
								title: '错误：',
								msg: r.msg
							});
						}
					}, 'json');
				} else {
					$('#fileContainer').empty(); //清空默认列表
		        	$('#fileContainer').append('<img src="${pageContext.request.contextPath}/images/attach.gif">').append(data);
					$('#empUrl').val(data);
				}
				 */
				$('#fileContainer').empty(); //清空默认列表
	        	$('#fileContainer').append('<img src="${pageContext.request.contextPath}/images/attach.gif">').append(data);
				$('#empUrl').val(data);
			},
			'onUploadError':function(file,errorCode,errorMsg,errorString){
				$.messager.alert('错误','文件上传出错 ,错误代码:'+errorCode); 
			}
		});
    	
    	$("#empHiPicUrlUploadify").uploadify({
			'swf' : '${pageContext.request.contextPath}/uploadify/uploadify.swf' ,
			'uploader' : '${pageContext.request.contextPath}/form/upload/fdfs' ,
			'queueID' : 'empHiPicQueue' ,
			'buttonText' : '添加空表/样表 Web图片' ,
			'height' : 20 ,
			'width' : 160 ,
			'multi' : true ,
			'auto' : false ,
			'fileTypeExts' : '*.jpg;*.gif;*.png;*.bmp' ,
			'fileTypeDesc' : '图片文件' ,
			'onUploadSuccess' : function(file, data, response) {
				//$('#empHiPicContainer').empty() ;
				// $('#empHiPicContainer').append('<span style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" /><a href="${hiPicUrl}" alt="查看附件" onclick="window.open(this.href, '_blank', '');return false">').append(data).append('</a>&nbsp;&nbsp; <input type="button" value="删除" onClick="deletePic(\"').append(data).append('\", \"${form.id}\", \"hi\");"></span></br>') ; 
				$('#empHiPicContainer').append('<img src="${pageContext.request.contextPath}/images/attach.gif">').append(data) ;
				empHiPicUrlx += ';' + data ;
				$('#empHiPicUrl').val(empHiPicUrlx) ;
			},
			'onUploadError':function(file,errorCode,errorMsg,errorString){
				$.messager.alert('错误','文件上传出错 ,错误代码:'+errorCode); 
			}
    	}) ;
    	
    	$("#empLowPicUrlUploadify").uploadify({
    		'swf' : '${pageContext.request.contextPath}/uploadify/uploadify.swf' ,
    		'uploader' : '${pageContext.request.contextPath}/form/upload/fdfs' ,
    		'queueID' : 'empLowPicQueue' ,
    		'buttonText' : '添加空表/样表 Mobile图片' ,
    		'height' : 20 ,
    		'width' : 160 ,
    		'multi' : true ,
    		'auto' : false ,
    		'fileTypeExts' : '*.jpg;*.gif;*.png;*.bmp' ,
    		'fileTypeDesc' : '图片文件',
    		'onUploadSuccess' : function(file, data, reponse) {
    			$('#empLowPicContainer').append('<img src="${pageContext.request.contextPath}/images/attach.gif">').append(data) ;
    			empLowPicUrlx += ';' + data ;
    			$('#empLowPicUrl').val(empLowPicUrlx) ;
    		},
    		'onUploadError':function(file,errorCode,errorMsg,errorString){
				$.messager.alert('错误','文件上传出错 ,错误代码:'+errorCode); 
			}
    	}) ;
 
    	$("#descContentUploadify").uploadify({
    		'swf':'${pageContext.request.contextPath}/uploadify/uploadify.swf',
			'uploader':'${pageContext.request.contextPath}/form/upload/fdfs',
			'queueID' : 'descContentnQueue' ,
			'buttonText' : '填表说明源文件' ,
			'height' : 20 ,
			'width' : 100 ,
			'fileTypeExts' : '*.doc;*.docx' ,
    		'fileTypeDesc' : 'Word 文件',
    		'onUploadSuccess' : function(file, data, response) {
    			$("#descContentContainer").empty() ;
    			$("#descContentContainer").append('<img src="${pageContext.request.contextPath}/images/attach.gif">').append(data) ;
    			//alert("descUrl : " + data ) ;
    			$('#descUrl').val(data) ;
    		}
    	}) ;
    	 
      });
    	
    	function saveForm(){
			$('#descContent').val(editor_a.getContent());//赋值
			setTagIds();
			if(treeKeys.length>0){ //如果数据没有被修改赋值
				$('#tags').combotree('setValues',treeKeys);
			}
			$('#fm').form('submit',{
				url: '${pageContext.request.contextPath}/form/save',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');
					if (result.success){
						//location.href='${pageContext.request.contextPath}/form';
						window.close();
					} else {
						$.messager.show({
							title: '错误',
							msg: result.msg
						});
					}
				}
			});
    	}
    	
    	function startUpload() {
    		$("#simpleuploadify").uploadify("upload", "*") ;
    	}
    	
    	function returnList() {
    		location.href = "/formlist" ;
    	}
    	
    	function startUploadEmpHiPicUrl() {
    		$("#empHiPicUrlUploadify").uploadify("upload", "*") ;
    	}
    	
    	function startUploadEmpLowPicUrl() {
    		$("#empLowPicUrlUploadify").uploadify("upload", "*") ;
    	} 
    	/* 
    	function startUploadSampHiPicUrl() {
    		$("#sampHiPicUrlUploadify").uploadify("upload", "*") ;
    	}
    	
    	function startUploadSampLowPicUrl() {
    		$("#sampLowPicUrlUploadify").uploadify("upload", "*") ;
    	}
    	 */

		function deletePic(picUrl, id, type, spanId) {
			//alert(spanId);
			var url = picUrl.replace(new RegExp('/', 'gm'), '&');
			$.messager.confirm('提示：', '确认删除此图片？', function(r) {
				if(r) {
					$.get("/form/deletePic/"+url+"/"+id+"/"+type, function(result) {
 						if(result.success) {
							/*
							$.messager.alert('提示：', '图片删除成功！');
							*/
							$.messager.alert('提示：', '图片删除成功！', '', function() {
								$('#'+spanId).remove();
							}) ;
							
						} else {
							$.messager.alert('错误：', result.msg) ;
						}
					}, 'json')
				}
			})
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

<body>
		<form id="fm" method="post" novalidate>
			<div class="ftitle">基础信息</div>
			<div class="fitem">
				<label>标题:</label>
				<input name="title" class="easyui-validatebox" required="true" value="${form.title }" style="width:400px"/>
				<input name="id" id="id" value="${form.id }" type="hidden" />
				<!--
				<input name="insertId" type="hidden" value="${form.insertId}" />
				<input name="insertName" type="hidden" value="${form.insertName}" />
				<input name="createDate02" type="hidden" value="${form.createDate}" />
				-->
				<input name="checkId" type="hidden" value="${form.checkId}" />
				<input name="checkName" type="hidden" value="${form.checkName}" />
				<input name="checkDate02" type="hidden" value="${form.checkDate}" />
				<input name="state" type="hidden" value="${form.state}" />
				<input name="weighing" type="hidden" value="${form.weighing }" />		
			<div class="fitem">
				<label>简标题:</label>
				<input name="shortTitle" class="easyui-validatebox" style="width:200px" value="${form.shortTitle}" />
			</div>
			<div class="fitem">
				<label>摘要:</label>
				<textarea name="summary" style="width:550px;height:100px">${form.summary }</textarea>
			</div>
			<div class="fitem">
				<label>发布时间:</label>
				<input type="text" id="begincreateDate02" name="begincreateDate02" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate  value="${form.begincreateDate}" type="both" pattern="yyyy-MM-dd " />' />
			</div>
			<div class="fitem">
				<label>来源:</label>
				<input type="text" id="source" name="source" style="width:400px" value="${form.source }" />
			</div>
			<c:if test="${form.state == 2}">
				<div class="fitem">
					<label><font>驳回原因:</font></label>
					<input type="text" id="auditInfo" name="auditInfo" style="width:400px; color:red;" value="${form.auditInfo}" readonly/>
				</div>
			</c:if>
			<div class="ftitle">标签信息</div>
			<div class="fitem">
				<div id="tagList" title="标签列表" class="easyui-datagrid" style="width:650px;height:200px"></div>
				<input id="tags" name="tags" type="hidden" /> 
			</div>
			<!-- 空表/样表 文件 -->
			<br />
			<div class="ftitle">空表/样表 源文件</div>
			<div class="fitem">
				<div id="fileQueue"></div>
				<div id="fileContainer">
					<span style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" />${form.empUrl }</span>
				</div> 
				<input id="empUrl" name="empUrl" type="hidden" value="${form.empUrl }" />
				<input type="file" name="uploadify" id="uploadify"/>
			</div>
			<br />
			<!-- 空表/样表 Web图片 -->
			<div class="ftitle">空表/样表 Web图片</div>
			<div class="fitem">
				<div id="empHiPicQueue"></div>
				<div id="empHiPicContainer">
					<!-- <span style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" />${form.empHiPicUrl }</span> -->
					<c:forEach items="${hiPicUrlSet}" var="hiPicUrl">
						<span id="${fn:substring(hiPicUrl, 38, 67 )}" style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" /><a href='${hiPicUrl}' alt='查看附件' onclick="window.open(this.href, '_blank', '');return false">${hiPicUrl}</a>&nbsp;&nbsp; <input type="button" value='删除' id='${i}' onClick='deletePic("${hiPicUrl}", "${form.id}", "hi", "${fn:substring(hiPicUrl, 38, 67 )}");'></span></br>
					</c:forEach>

				</div>
				<input id="empHiPicUrl" name="empHiPicUrl" type="hidden" />
				<input id="deletedEmpHiPicUrl" name="deletedEmpHiPicUrl" type="hidden" />
				<input type="file" name="empHiPicUrlUploadify" id="empHiPicUrlUploadify" />
				
				<a href="javascript:void(0)" onclick="startUploadEmpHiPicUrl()"  >开始上传</a>
				<a href="javascript:$(#empHiPicUrlUploadify).uploadify('cancel', '*')">取消上传</a>
			</div>
			
			<br />
			<!-- 空表/样表 Mobile图片 -->
			<div class="ftitle">空表/样表 Mobile图片</div>
			<div class="fitem">
				<div id="empLowPicQueue"></div>
				<div id="empLowPicContainer">
					<!-- <span style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" />${form.empLowPicUrl }</span> -->
					<c:forEach items="${lowPicUrlSet}" var="lowPicUrl">
						<span id="${fn:substring(lowPicUrl, 38, 67 )}" style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" /><a href='${lowPicUrl}' onclick="window.open(this.href, '_blank', '');return false">${lowPicUrl}</a>&nbsp;&nbsp; <input type="button" value='删除' onClick='deletePic("${lowPicUrl}", "${form.id}", "low", "${fn:substring(lowPicUrl, 38, 67 )}")'></span></br>
					</c:forEach>
				</div>
				<input id="empLowPicUrl" name="empLowPicUrl" type="hidden" />
				<input id="deletedEmpLowPicUrl" name="deletedEmpLowPicUrl" type="hidden" />
				<input type="file" name="empLowPicUrlUploadify" id="empLowPicUrlUploadify" />
				
				<a href="javascript:void(0)" onclick="startUploadEmpLowPicUrl();">开始上传</a>
				<a href="javascript:$(#empLowPicUrlUploadify).uploadify('cancel', '*')">取消上传</a>
			</div>
		
			<br />
			<!-- 填表说明源文件 -->
			<div class="ftitle">填表说明源文件</div>
			<div class="fitem">
				<div id="descContentQueue"></div>
				<div id="descContentContainer">
					<span style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" />${form.descUrl }</span>
				</div>
				<input id="descUrl" name="descUrl" type="hidden" value="${form.descUrl }"/>
				<input type="file" name="descContentUploadify" id="descContentUploadify" />
			</div>
			
			<!-- 填表说明文件 -->
			<br />
			<div class="ftitle">填表说明</div>
			<div class="fitem">
				<label></label>
				<textarea id="myEditor">${form.descContent }</textarea>
				<input id="descContent" name="descContent" type="hidden" />
			</div>
		</form>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveForm()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-back" onclick="javascript:window.close();">返回</a>
	</div>
	<div id="tag-window" class="easyui-window" style="width:600px;height:400px" title="标签" data-options="iconCls:'icon-tag',modal:true">  
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
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">  
                <a class="easyui-linkbutton"  data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:$('#tag-window').window('close')">关闭</a>  
            </div>  
        </div>  
    </div>  
   <input value="" id="parentId" type="hidden"/> 
</body>
</html>
