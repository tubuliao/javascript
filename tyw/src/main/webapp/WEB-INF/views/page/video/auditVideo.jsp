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
    	$(function(){

			//初始化已选标签列表
			$('#tagList').datagrid({  
	    	    url:'${pageContext.request.contextPath}/video/tags/${video.id}', 
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
						//location.href='${pageContext.request.contextPath}/video';
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
    	
		
    	function delVideo(vId, spanId) {
			$.messager.confirm('提示：', '确定删除此视频？', function(r) {
				if(r) {
					$.get('/video/delVideo', { id: vId}, function(result) {
							if(result.success) {
								$.messager.alert('提示：', '删除视频文件成功！', 'info', function() {
									//location.href = '/video/edit/' + vId;
									$('#'+spanId).remove();
								}) ;
							} else {
								$.messager.alert('错误：', result.msg) ;
							}
					}, 'json') ;
				}
			}) ;
		}

		function saveAndAuditPass(){
    		var id = '${video.id}';
    		var state = '${video.state}';
    		
    		if(state==3){
				 $.messager.show({
					title: '提示：',
					msg: '该记录已通过二次审批不能重复审批!'
				 });
				 return;
			}
    		
    		if(state == 0 || state == 2) {
    			$.messager.confirm('确认', '你确认保存并审批通过该视频吗?', function(r){
        			if (r){
        				// 保存	
        				saveVideo();
        				
        				// 审批通过
        				$.post('${pageContext.request.contextPath}/video/approve/'+id,{status:'1'},function(result){
        					if (result.success){
        						window.close();
        					} else {
        						$.messager.show({	// 显示错误信息
        							title: '错误',
        							msg: result.msg
        						});
        					}
        				},'json');
        			}
        		});
    		}
    		
    		if(state == 1) {
    			var weightVal = $('#weighingVal').val();
    			var weightType = $('input:checked').val();
    			var baseSource = '${video.source}';
    			$.messager.confirm('确认', '该记录已经通过审批，是否进行二次审批？', function(r){
        			if (r){
        				// 保存	
        				saveVideo();
        				
        				// 审批通过
        				$.post('${pageContext.request.contextPath}/video/approve/'+id,{status:'3', weightType: weightType, weightVal: weightVal, baseSource: baseSource},function(result){
        					if (result.success){
        						window.close();
        					} else {
        						$.messager.show({	// 显示错误信息
        							title: '错误',
        							msg: result.msg
        						});
        					}
        				},'json');
        			}
        		});
    		}
    		
    		
    		
    	}
    	
    	function reject(){
    		var id = '${video.id}';
    		var state = '${video.state}';
    		
    		if(state==2){
    			$.messager.show({
    				title: '提示',
    				msg: '该记录已被驳回不能重复驳回!'
    			});
    			return;
    		}
    		$.messager.prompt('驳回原因', '请填写驳回原因:<br><font color="red">注意：必须填写驳回原因！</font>', function(r){
    			if (r){
    					$.post('${pageContext.request.contextPath}/video/approve/'+id,{status:'2',auditInfo:r},function(result){
    					if (result.success){
    						window.close();
    					} else {
    						$.messager.show({	// 显示错误信息
    							title: '错误',
    							msg: result.msg
    						});
    					}
    				},'json');
    			} 
    		});
    	}
    	
    	function checkWeighingVal(v) {
			if(v != '') {
				if(!/^[1-9]$/.test(v) && v != '10') {
					//$('#saveBtn').hide();
					$.messager.alert('错误：', '权重值只能是1至10范围内的整数！', 'error', function() {
						$('#weighingVal').focus();
					});
					return;
				} else {
					//$('#saveBtn').show();
				}
			} 
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
				<input name="title" class="easyui-validatebox" required="true" style="width:400px;" value="${video.title}" />
				<input name="id" type="hidden" value="${video.id}" />
				<input name="checkId" type="hidden" value="${video.checkId}" />
				<input name="checkName" type="hidden" value="${video.checkName}" />
				<input name="checkDate02" type="hidden" value="${video.checkDate}" />
				<input name="state" type="hidden" value="${video.state}" />
				<input name="weighing" type="hidden" value="${video.weighing }" />
			</div>
			<div class="fitem">
				<label>简标题:</label>
				<input name="shortTitle" class="easyui-validatebox" style="width:200px;" value="${video.shortTitle}" />
			</div>
			<div class="fitem">
				<label>摘要:</label>
				<textarea name="summary" style="width:550px;height:100px">${video.summary}</textarea>
			</div>
			<div class="fitem">
				<label>发布时间:</label>
				<input type="text" id="begincreateDate02" name="begincreateDate02" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate  value="${video.begincreateDate}" type="both" pattern="yyyy-MM-dd " />' />
			</div>
			<div class="fitem">
				<label>来源:</label>
				<input type="text" id="source" name="source" style="width:400px" value="${video.source}" />
			</div>
			<c:if test="${video.state == 2}">
				<div class="fitem">
					<label><font>驳回原因:</font></label>
					<input type="text" id="auditInfo" name="auditInfo" style="width:400px; color:red;" value="${video.auditInfo}" readonly/>
				</div>
			</c:if>
			<!-- 设置权重 -->
			<c:if test="${video.state == 1 || video.state == 3}">
				<br />
				<div class="fitem">
					<label class="ftitle">权重设置</label><br />
					<input type="radio" name="weight" value="title" checked/> 标题 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="weight" value="source" /> 来源<br /><br />
					权重值：<input type="text" name="weighingVal" id="weighingVal" value="${video.weighing }" style="width:40px;" onblur="checkWeighingVal(value);" />
				</div>
				<br />
			</c:if>
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
				<div id="videoContainer">
					<c:if test="${ video.url != null && video.url != ''}">
						<%-- <span style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" />${video.url}&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" onclick="delVideo('${video.id}');">删除</a></span> --%>
						<span id="${fn:substring(video.url, 38, 67 )}" style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" />${video.url}&nbsp;&nbsp;<input type="button" value="删除" onclick="delVideo('${video.id}', '${fn:substring(video.url, 38, 67 )}');" /></span>
					</c:if>
				</div> 
				<input id="url" name="url" type="hidden" value="${video.url}"/>
				<input type="file" name="uploadifyVideo" id="uploadifyVideo"/>
			</div>
			
			<!-- 视频说明  -->
			<br />
			<div class="ftitle">视频说明</div>
			<div class="fitem">
				<label></label>
				<textarea id="myEditor">${video.content}</textarea>
				<input id="content" name="content" type="hidden" />
			</div>
			 
		</form>
	
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
    <div id="" style="padding:20px;">
		<c:if test="${video.state == 0 || video.state == 2 }">
			<span id="saveBtn"><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:saveAndAuditPass();">保存/审批通过</a></span>&nbsp;&nbsp;
		</c:if>
		<c:if test="${video.state == 1 }">
			<span id="saveBtn"><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:saveAndAuditPass();">保存/二次审批通过</a></span>&nbsp;&nbsp;
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo" onclick="javascript:reject();">驳回</a>&nbsp;&nbsp;
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:window.close();">关闭</a>
	</div>
</body>
</html>
