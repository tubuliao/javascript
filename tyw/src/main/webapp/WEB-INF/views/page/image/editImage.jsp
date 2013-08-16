<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %> 
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
    	var appendObjs=new Array();
		var urlStr = '' ;
		var fileName = '' ;
    	$(function(){

			//初始化已选标签列表
			$('#tagList').datagrid({  
	    	    url:'${pageContext.request.contextPath}/image/tags/${image.id}', 
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
  
    
    	$("#uploadifyImage").uploadify({
			'swf':'${pageContext.request.contextPath}/uploadify/uploadify.swf',
			'uploader':'${pageContext.request.contextPath}/image/upload/fdfs',
			'queueID':'imageQueue',
			'buttonText': '添加图片文件',  
			'height': 20,   
			'multi':true,
			'auto': false,
    		'width': 80, 
    		'fileTypeDesc' : '图片文件',
    		'fileTypeExts': '*.jpg;*.gif;*.png;*.bmp',
    		//'fileTypeDesc':'swf文档',
			'onUploadSuccess':function(file,data,response){
				//$('#imageContainer').empty(); //清空默认列表
	        	$('#imageContainer').append('</br><img src="${pageContext.request.contextPath}/images/attach.gif">').append(data);
				urlStr += data + ';' ;
				$('#url').val(urlStr);
				// 保存源文件名
				fileName += file.name.substring(0, file.name.indexOf('.')) + ';'	// 去除文件名后缀
				$('#description').val(fileName) ;
			},
			'onUploadError':function(file,errorCode,errorMsg,errorString){
				if(errorCode != -280) {
					$.messager.alert('错误','文件上传出错 ,错误代码:'+errorCode); 
				}
			}
		});
    	
    	$("#uploadifyImageUrl").uploadify({
			'swf':'${pageContext.request.contextPath}/uploadify/uploadify.swf',
			'uploader':'${pageContext.request.contextPath}/image/upload/fdfs',
			'queueID':'imageUrlQueue',
			'buttonText': '添加图片附件',  
			'height': 20,   
			'multi':false,
			'auto': true,
    		'width': 80, 
    		'fileTypeDesc' : '图片附件',
    		'fileTypeExts': '*.rar',
    		//'fileTypeDesc':'swf文档',
			'onUploadSuccess':function(file,data,response){
				//alert(fileName) ;
				//alert('file:' + file.name + '  data:' + data + '  response:' + response) ;
				$('#imageUrlContainer').empty(); //清空默认列表
	        	$('#imageUrlContainer').append('</br><img src="${pageContext.request.contextPath}/images/attach.gif">').append(data);
				$('#imgUrl').val(data);
				// 保存源文件名
				//fileName += file.name.substring(0, file.name.indexOf('.')) + ';'	// 去除文件名后缀
				//$('#description').val(fileName) ;
			},
			'onUploadError':function(file,errorCode,errorMsg,errorString){
				if(errorCode != -280) {
					$.messager.alert('错误','文件上传出错 ,错误代码:'+errorCode); 
				}
			}
		});
    	
      });
    	
    	function saveImage(){
			$('#content').val(editor_a.getContent());//赋值
			setTagIds();
			$('#fm').form('submit',{
				url: '${pageContext.request.contextPath}/image/save',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');
					if (result.success){
						//location.href='${pageContext.request.contextPath}/image';
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
    		$("#uploadifyImage").uploadify("upload", "*") ;
    	}
    	
    	function deleteImg(imgUrl, id, spanId) {
    		//alert('imgUrl:' + imgUrl + ' *********** id:' + id) ;
    		var url = imgUrl.replace(new RegExp('/', 'gm'), '&') ;  // url格式转换( '/' -> '&' )
    		$.messager.confirm('提示：', '确认删除此图片？', function(r) {
    			if(r) {
    				$.get('/img/deleteImg', { imgUrl: url, imgId: id}, function(result) {
    					if(result.success) {
    						$.messager.alert('提示：', '删除成功！', 'info', function() {
    							/* location.href='/image/edit/' + id; */
    							$('#'+spanId).remove();
    						}) ;
    					} else {
    						$.messager.alert('错误：', result.msg) ;
    					}
    				}, 'json') ;
    			} 
    		}) ;
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
				<input name="title" class="easyui-validatebox" required="true" style="width:400px;" value="${image.title}" />
				<input name="id" type="hidden" value="${image.id}" />
				<input name="checkId" type="hidden" value="${image.checkId}" />
				<input name="checkName" type="hidden" value="${image.checkName}" />
				<input name="checkDate02" type="hidden" value="${image.checkDate}" />
				<input name="state" type="hidden" value="${image.state}" />
				<input name="weighing" type="hidden" value="${image.weighing }" />
			</div>
			<div class="fitem">
				<label>简标题:</label>
				<input name="shortTitle" class="easyui-validatebox" style="width:200px;" value="${image.shortTitle}" />
			</div>
			<div class="fitem">
				<label>摘要:</label>
				<textarea name="summary" style="width:550px;height:100px">${image.summary}</textarea>
			</div>
			<div class="fitem">
				<label>发布时间:</label>
				<input type="text" id="begincreateDate02" name="begincreateDate02" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate  value="${image.begincreateDate}" type="both" pattern="yyyy-MM-dd " />' />
			</div>
			<div class="fitem">
				<label>来源:</label>
				<input type="text" id="source" name="source" style="width:400px" value="${image.source}" />
			</div>
			<c:if test="${image.state == 2}">
				<div class="fitem">
					<label><font>驳回原因:</font></label>
					<input type="text" id="auditInfo" name="auditInfo" style="width:400px; color:red;" value="${image.auditInfo}" readonly/>
				</div>
			</c:if>
			<div class="ftitle">标签信息</div>
			<div class="fitem">
				<div id="tagList" title="标签列表" class="easyui-datagrid" style="width:650px;height:200px"></div>
				<input id="tags" name="tags" type="hidden" /> 
			</div>
			<!-- 图片文件 -->
			<br />
			<div class="ftitle">图片文件</div>
			<div class="fitem">
				<div id="imageQueue"></div>
				<div id="imageContainer">
					<c:forEach items="${urlList}" var="url">
						<%-- <span id="" style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" />${url} &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="javascript:deleteImg('${url}', '${image.id}')">删除</a></span><br /> --%>
						<span id="${fn:substring(url, 38, 67 )}" style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" />${url} &nbsp;&nbsp;<input type="button" value='删除' onclick="javascript:deleteImg('${url}', '${image.id}', '${fn:substring(url, 38, 67 )}')"></span><br />
					</c:forEach>
				</div> 
				<input id="url" name="url" type="hidden" value=""/>
				<input id="description" name="description" type="hidden" />
				<input type="file" name="uploadifyImage" id="uploadifyImage"/>

				<a href="javascript:void(0)" onclick="javascript:$('#uploadifyImage').uploadify('upload', '*')">开始上传</a>
				<a href="javascript:void(0)" onclick="javascript:$('#uploadifyImage').uploadify('cancel', '*')">取消上传</a>
			</div>
			<!-- 图片附件 -->
			<br />
			<div class="ftitle">图片附件</div>
			<div class="fitem">
				<div id="imageUrlQueue"></div>
				<div id="imageUrlContainer">
					<span style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" />${image.imgUrl}</span>
				</div> 
				<input id="imgUrl" name="imgUrl" type="hidden" value="${image.imgUrl}"/>
				<!-- <input id="imgUrlDescription" name="imgUrlDescription" type="hidden" /> -->
				<input type="file" name="uploadifyImageUrl" id="uploadifyImageUrl"/>
			</div>
			<!-- 图片说明  -->
			<br />
			<div class="ftitle">图片说明</div>
			<div class="fitem">
				<label></label>
				<textarea id="myEditor">${image.content}</textarea>
				<input id="content" name="content" type="hidden" />
			</div>
			 
		</form>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveImage()">保存</a>
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
