<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
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
    	var resultsX = '' ;
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
			
			

			$("#uploadify").uploadify({
    			'swf':'${pageContext.request.contextPath}/uploadify/uploadify.swf',
    			'uploader':'${pageContext.request.contextPath}/ppt/upload/fdfs',
    			'queueID':'fileQueue',
    			'buttonText': '添加文件',  
    			'height': 20,   
				'auto': false,
    			'multi':true,
        		'width': 80, 
				//'cancelImg' :  getRootPath()+'/resources/js/uploadify/uploadify-cancel.png',
				
    			'onUploadSuccess':function(file,data,response){
				//alert(data);
		        	$('#pptContainer').append('<img src="${pageContext.request.contextPath}/images/attach.gif">').append(data);  //.append('The file ' + file.name + ' was successfully uploaded with a response of ' + response ); 
					resultsX += ';' + data;
					//resultsX += file.name.substring(0, file.name.indexOf('.')) + data + "|";
    				$('#urls').val(resultsX);
					//alert("results -- " + results) ;
					//alert('The file ' + file.name + ' was successfully uploaded with a response of ' + response + ':' + data) ;
    			},
    			'onUploadError':function(file,errorCode,errorMsg,errorString){
    				if(errorCode != "-280") {  // errorCode = "-280" 取消上传
    					$.messager.alert('错误','文件上传出错 ,错误代码:'+errorCode);
    				} 
    			}
    		});
    	
       	});
    	
    	function saveQuestion(){
    		var quesContent = document.getElementById("title").value;
    		if(quesContent.length==0){
    			alert("问题不能为空！");
    			document.getElementById("title").focus();
    			return false;
    		}
    		if(quesContent.length>120){
    			alert("问题内容不能大于120字！");
    			document.getElementById("title").focus();
    			return false;
    		}
			//$('#content').val(editor_a.getContent());//赋值
			setTagIds();
			$('#fm').form('submit',{
				url: '${pageContext.request.contextPath}/question/save',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');
					if (result.success){
						if (result.success){
							location.href='${pageContext.request.contextPath}/question';
						}
						//$('#pptContainer').empty();   // 清空默认列表
					} else {
						$.messager.show({
							title: '错误',
							msg: result.msg
						});
					}
				}
			});
    	}
    	
    	function startUpload(){
    		
			$('#uploadify').uploadify('upload','*');
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
				<label>问题内容:</label>
				<textarea id="title" name="title" rows="10" cols="60"></textarea>
			</div>
			
			
			
			
			<div class="ftitle">标签信息</div>
			<div class="fitem">
				<div id="tagList" title="标签列表" class="easyui-datagrid" style="width:650px;height:200px"></div>
				<input id="tags" name="tags" type="hidden" /> 
			</div>
			<div class="ftitle"></div>
		
		</form>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveQuestion()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-back" onclick="javascript:window.location.href='/question'">返回</a>
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
