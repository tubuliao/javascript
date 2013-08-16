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
    	var editor_a;
    	var appendObjs=new Array();
    	$(function(){
			 $('#tagList').datagrid({  
		    	    url:'', 
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
  
    
    	$("#uploadifyVideo").uploadify({
			'swf':'${pageContext.request.contextPath}/uploadify/uploadify.swf',
			'uploader':'${pageContext.request.contextPath}/video/upload/fdfs',
			'queueID':'videoQueue',
			'buttonText': '添加视频文件',  
			'height': 20,   
			'multi':false,
    		'width': 80, 
    		'fileTypeDesc' : '视频文件',
    		'fileTypeExts': '*.*',
    		//'fileTypeDesc':'swf文档',
			'onUploadSuccess':function(file,data,response){
				$('#videoContainer').empty(); //清空默认列表
	        	$('#videoContainer').append('</br><img src="${pageContext.request.contextPath}/images/attach.gif">').append(data);
				$('#url').val(data);
			},
			'onUploadError':function(file,errorCode,errorMsg,errorString){
				$.messager.alert('错误','文件上传出错 ,错误代码:'+errorCode); 
			}
		});
    	
    	;
    	
      });
    	
    	function saveVideo(){
			$('#content').val(editor_a.getContent());//赋值
			setTagIds();
			$('#fm').form('submit',{
				url: '${pageContext.request.contextPath}/video/save',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');
					if (result.success){
						location.href='${pageContext.request.contextPath}/video';
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
				<input name="title" class="easyui-validatebox" required="true" style="width:400px;"/>
			</div>
			<div class="fitem">
				<label>简标题:</label>
				<input name="shortTitle" class="easyui-validatebox" style="width:200px;"/>
			</div>
			<div class="fitem">
				<label>摘要:</label>
				<textarea name="summary" style="width:550px;height:100px"></textarea>
			</div>
			<div class="fitem">
				<label>发布时间:</label>
				<input type="text" id="begincreateDate02" name="begincreateDate02" class="Wdate" onClick="WdatePicker()" />
			</div>
			<div class="fitem">
				<label>来源:</label>
				<input type="text" id="source" name="source" style="width:400px" />
			</div>
			<div class="ftitle">标签信息</div>
			<div class="fitem">
				<div id="tagList" title="标签列表" class="easyui-datagrid" style="width:650px;height:200px"></div>
				<input id="tags" name="tags" type="hidden" /> 
			</div>
			<!-- 视频文件 -->
			<br />
			<div class="ftitle">视频文件</div>
			<div class="fitem">
				<div id="videoQueue"></div>
				<div id="videoContainer"></div> 
				<input id="url" name="url" type="hidden" />
				<input type="file" name="uploadifyVideo" id="uploadifyVideo"/>
			</div>
			
			<!-- 视频说明  -->
			<br />
			<div class="ftitle">视频说明</div>
			<div class="fitem">
				<label></label>
				<textarea id="myEditor"></textarea>
				<input id="content" name="content" type="hidden" />
			</div>
			 
		</form>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveVideo()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-back" onclick="javascript:history.go(-1)">返回</a>
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
