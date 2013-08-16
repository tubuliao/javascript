<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"   %>
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
    	var treeKeys=[];
    	var appendObjs=new Array();
    	var empHiPicUrlx = "" ;
    	var empLowPicUrlx = "" ;
    	var sampHiPicUrlx = "" ;
    	var sampLowPicUrlx = "" ;
		var formWin;

	
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
			
			// 可修改部分默认隐藏
			//$('#revisability').hide();
			$('#display').hide();


			// 初始化预览的已选标签列表
			/*
			$('#tagList02').datagrid({  
	    	    url:'${pageContext.request.contextPath}/form/tags/${form.id}', 
	    	    rownumbers:true, 
	    	    columns:[[  
	    	        {field:'id',title:'id',width:100,hidden:true},  
	    	        {field:'name',title:'标签名',width:100}
	    	    ]]  
	    	});
			*/
    		
			editor_a = new baidu.editor.ui.Editor();
	        //渲染编辑器
	        editor_a.render('myEditor');

			/*
			editor_b = new baidu.editor.ui.Editor();
	        //渲染编辑器
	        editor_b.render('myEditor02');
			*/

     
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
    		//'fileTypeDesc':'swf文档',
			'onUploadSuccess':function(file,data,response){
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

		formWin=$('#form-window').window({
		        closed: true,
		        modal: true,
		        left:(screen.width-700)/2 ,
		        top:(screen.height-700)/2,
		        resizable:true,
		        minimizable:false,
		        maximizable:true,
		        width:700,
		        height:600
			 });
		$('#form-window').window('close');
    	 
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
						location.href='${pageContext.request.contextPath}/form';
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

		
		function showForm() {
			
			//alert('${form.id}'); 
			var html = "" ;
			//alert('${form.empHiPicUrl}');
			var empHiPicUrlArr = "" ;
			if('${form.empHiPicUrl}' != "" && '${form.empHiPicUrl}' != null) {
				empHiPicUrlArr = '${form.empHiPicUrl}'.split(";") ;
				//alert(row.empHiPicUrl) ;
				for(var i = 0 ; i < empHiPicUrlArr.length ; i++) {
					if(empHiPicUrlArr[i] != null && empHiPicUrlArr[i] != "") {
						html += "<img src='" + empHiPicUrlArr[i] + "' onload=\"resizeImg(this);\" />" ;
					}
				}
			}
			$("#formContentPicture").html(html) ;
			$("#formConentDesc").html(editor_a.getContent()) ;
			$("#formTitle").html("标题："+'${form.title}');
			$("#formSource").html("来源："+ ('${form.source}'==null?"(无)":'${form.source}')) ;
			$("#formCreateDate").html("创建时间："+'${row.createDate}');
			$("#forminsertName").html('${form.insertName}'==null?"录入人：(无)":"录入人："+'${form.insertName}');
			$("#formId").val('${form.id}');
			
			formWin.window('open');
		}

		function auditEdit() {
			// 初始化已选择标签
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
			// 显示可修改部分
			$('#revisability').show();
			// 隐藏预览部分
			$('#display').hide();
		}

		function auditPass() {
			var id = '${form.id}';
			$.post('${pageContext.request.contextPath}/form/approve/'+id,{status:'1'},function(result){
						if (result.success){
							//location.href="/form";	// 重新加载数据
							window.close();
						} else {
							$.messager.show({	// 显示错误信息
								title: '错误',
								msg: result.msg
							});
						}
			},'json');
		} 
		
		function reAuditPass() {
			var id = '${form.id}';
			var weightVal = $('#weighingVal').val();
			var weightType = $('input:checked').val();
			var baseSource = '${form.source}';
			//alert(weightType + '  **  ' + weightVal + '  **  ' + baseSource);
			//return;
			$.post('${pageContext.request.contextPath}/form/approve/'+id,{status:'3', weightVal: weightVal, weightType: weightType, baseSource: baseSource},function(result){
				if (result.success){
					//location.href="/form";	// 重新加载数据
					window.close();
				} else {
					$.messager.show({	// 显示错误信息
						title: '错误',
						msg: result.msg
					});
				}
			},'json');
		} 

		function auditNoPass() {
			var state = '${form.state}';
			var id = '${form.id}';
			if(state==2){
				 $.messager.show({
					title: '提示',
					msg: '该记录已被驳回不能重复驳回!'
				 });
				 return;
			}
			$.messager.prompt('驳回原因', '请填写驳回原因:<br><font color="red">注意：必须填写驳回原因！</font>', function(r){
				if (r){
						$.post('${pageContext.request.contextPath}/form/approve/'+id,{status:'2',auditInfo:r},function(result){
						if (result.success){
							//location.href = '/form'	// 重新加载数据
							window.close();
						} else {
							$.messager.show({	// 显示错误信息
								title: '错误',
								msg: result.msg
							});
						}
					},'json');
				} /*else {
					$.messager.show({
						title: '提示',
						msg: '必须填写驳回原因!'
					 });
					return ;
				}*/
			});
		}

		function saveAndAuditPass() {
			var id = '${form.id}';
			var state = '${form.state}';
			//alert(id);
			if(state==3){	// 已二次审批
				 $.messager.alert('提示：', '该记录已通过二次审批不能重复审批!');
				 return;
			}
			
			if(state == 1) {	// 已审批
				$.messager.confirm('提示：', '该记录已经通过审批，是否进行二次审批？', function(r) {
					if(r) {
						// 保存
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
									// 审批
									reAuditPass();
								} else {
									$.messager.show({
										title: '错误',
										msg: result.msg
									}); 
								}
							}
						});	
					}
				})
			}
			
			if(state == 0 || state == 2) {	// 未审批或驳回
				$.messager.confirm('确认', '你确认保存并审批通过该表格吗?', function(r){
					if (r){	
						// 保存
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
							success: function(r){
								var value = '('+r+')';
								var result = eval(value);
								if(result.success) {
									// 审批
									auditPass();
								} else {
									$.messager.alert("错误：", "若要初审通过，源文件、WEB图片和mobile图片不能为空！", "error");
								} 
							}
						});
					}
				});
			}
			// 保存
			/* 
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


					} else {
						$.messager.show({
							title: '错误',
							msg: result.msg
						});
					}
				}
			});				
 */
		}

		function goBack() {
			$('#revisability').hide();
			$('#display').show();
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
		#myEditor02{
            width: 500px;
            height: 300px;
        }
    </style>
</head>

<body>
	<!--
	<div id="" style="padding:20px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-tip" onclick="javascript:showForm();">预览</a>&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="javascript:auditEdit()">审批修改</a>&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="">保存/审批通过</a>&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" onclick="">驳回</a>&nbsp;&nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-back" onclick="javascript:history.go(-1)">返回</a>
	</div>
	-->
	<!-- 审批修改 -->
	<div id="revisability">
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
				<input name="weighing" type="hidden" value="${form.weighing }" />
				<!--  保存类型：审批时保存，需要校验：源文件、WEB图片、mobile图片 -->			
				<input name="saveType" type="hidden" value="approve" />

			</div>
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
			<!-- 设置权重 -->
			<c:if test="${form.state == 1 || form.state == 3}">
				<br />
				<div class="fitem">
					<label class="ftitle">权重设置</label><br />
					<input type="radio" name="weight" value="title" checked /> 标题 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="weight" value="source" /> 来源<br /><br />
					权重值：<input type="text" name="weighingVal" id="weighingVal" value="${form.weighing }" style="width:40px;" onblur="checkWeighingVal(value);" />
				</div>
				<br />
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
	   		<c:if test="${form.state == 0 || form.state == 2 }">
	   			<span id="saveBtn"><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:saveAndAuditPass();">保存/审批通过</a></span>&nbsp;&nbsp;
	   		</c:if>
	   		<c:if test="${form.state == 1 }">
	   			<span id="saveBtn"><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:saveAndAuditPass();">保存/二次审批通过</a></span>&nbsp;&nbsp;
	   		</c:if>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo" onclick="javascript:auditNoPass();">驳回</a>&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:window.close();">关闭</a>
		</div>
	</div>


	<!-- 显示 -->
	<div id="display" style="padding:30px;">
		<div id="" style="padding:0px 0px 30px 0px">
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-tip" onclick="javascript:showForm();">预览</a>&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" onclick="javascript:auditEdit()">审批修改</a>&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:auditPass();">审批通过</a>&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo" onclick="javascript:auditNoPass();">驳回</a>&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-back" onclick="javascript:window.close();">返回</a>
		</div>
			<div class="ftitle">基础信息</div>
			<div class="fitem">
				<label>标题:</label>
				${form.title}

			</div>
			<div class="fitem">
				<label>简标题:</label>
				${form.shortTitle}
			</div>
			<div class="fitem">
				<label>摘要:</label>
				${form.summary }
			</div>
			<div class="fitem">
				<label>来源:</label>
				${form.source }
			</div>
			<div class="ftitle">标签信息</div>
			<div class="fitem">
				<div id="tagList02" title="标签列表" class="easyui-datagrid" style="width:450px;height:200px"></div>
			</div>
			<!-- 空表/样表 文件 -->
			<br />
			<div class="ftitle">空表/样表 源文件</div>
			<div class="fitem">
				<div id="fileQueue"></div>
				<div id="fileContainer">
					<span style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" />${form.empUrl }</span>
				</div> 
			</div>
			<br />
			<!-- 空表/样表 Web图片 -->
			<div class="ftitle">空表/样表 Web图片</div>
			<div class="fitem">
				<div id="empHiPicQueue"></div>
				<div id="empHiPicContainer">
					<!-- <span style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" />${form.empHiPicUrl }</span> -->
					<c:forEach items="${hiPicUrlSet}" var="hiPicUrl">
						<span style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" /><a href='${hiPicUrl}' alt='查看附件' onclick="window.open(this.href, '_blank', '');return false">${hiPicUrl}</a></span></br>
					</c:forEach>

				</div>
				
			</div>
			
			<br />
			<!-- 空表/样表 Mobile图片 -->
			<div class="ftitle">空表/样表 Mobile图片</div>
			<div class="fitem">
				<div id="empLowPicQueue"></div>
				<div id="empLowPicContainer">
					<!-- <span style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" />${form.empLowPicUrl }</span> -->
					<c:forEach items="${lowPicUrlSet}" var="lowPicUrl">
						<span style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" /><a href='${lowPicUrl}' onclick="window.open(this.href, '_blank', '');return false">${lowPicUrl}</a></span></br>
					</c:forEach>
				</div>
			</div>
			
			<br />
			<!-- 填表说明源文件 -->
			<div class="ftitle">填表说明源文件</div>
			<div class="fitem">
				<div id="descContentQueue"></div>
				<div id="descContentContainer">
					<span style="margin-left:5px;"><img src="${pageContext.request.contextPath}/images/attach.gif" />${form.descUrl }</span>
				</div>
			</div>
			
			<!-- 填表说明文件 -->
			<br />
			<div class="ftitle">填表说明</div>
			<div class="fitem">
				<label></label>
				<textarea id="myEditor02">${form.descContent }</textarea>
			</div>
	</div>
	

	<!-- 预览 -->
	 <div id="form-window" class="easyui-window" title="预览" data-options="iconCls:'icon-tip'" maximized="true">  
        <div class="easyui-layout" data-options="fit:true">  
            <div data-options="region:'east',split:true" style="width:150px;padding:5px;">
            	<div id="formTitle"></div>
            	<div id="formSource"></div>
            	<div id="formCreateDate"></div>
            	<div id="forminsertName"></div>
            	<div id="formtags"></div>
            </div>  
            <div data-options="region:'center'" style="padding:10px;">  
                <!-- <div id="formContent" style="margin-left:10px"></div> -->
                <div id="formContent" class="easyui-tabs" style="" fit=true>
                	<div id="formContentPicture" title="空表/样表WEB图片" selected="true"></div>
                	<div id="formConentDesc" title="填表说明文件" ></div>
                </div>
            </div>  
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">  
                <a class="easyui-linkbutton"  data-options="iconCls:'icon-cancel'" href="javascript:void(0);" onClick="javascript:$('#form-window').window('close');">关闭</a>  
            </div>  
        </div>  
    </div>
</body>
</html>
