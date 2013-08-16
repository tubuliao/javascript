<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
     <script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js"></script>
    <script src="${pageContext.request.contextPath}/js/tagListPlus.js" type="text/javascript"></script>
    <script type="text/javascript">
    	var editor_a;
		var editor_b;
    	var appendObjs=new Array();
		var segmentWin;

    	$(function(){

			//初始化已选标签列表
			$('#tagList').datagrid({  
	    	    url:'${pageContext.request.contextPath}/segment/tags/${segment.id}', 
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

			//初始化关闭
			$("#tag-window").window('close');
			
			// 隐藏可修改部分
			//$('#revisability').hide();
	
			//初始化已选标签列表 显示
			/*
			$('#tagList02').datagrid({  
	    	    url:'${pageContext.request.contextPath}/segment/tags/${segment.id}', 
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
			// 显示
			editor_b = new baidu.editor.ui.Editor();
	        //渲染编辑器
	        editor_b.render('myEditor02');
			*/

			segmentWin = $('#segment-window').window({
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

			$("#segment-window").window('close');

    	});
    	/*
    	function saveSegment(){
			$('#content').val(editor_a.getContent());//赋值
			setTagIds();
			$('#fm').form('submit',{
				url: '${pageContext.request.contextPath}/segment/save',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');
					if (result.success){
						location.href='${pageContext.request.contextPath}/segment';
					} else {
						$.messager.show({
							title: '错误',
							msg: result.msg
						});
					}
				}
			});
    	}
		*/

		function auditEditSegment() {
			//初始化已选标签列表
			$('#tagList').datagrid({  
	    	    url:'${pageContext.request.contextPath}/segment/tags/${segment.id}', 
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
			$('#revisability').show();
			$('#display').hide();
		}

		function showSegment() {
			//$.messager.alert('提示：', '暂不支持！');
			$("#segmentContent").html(editor_a.getContent());
			$("#segmentTitle").html("标题："+'${segment.title}');
			$("#segmentSource").html("来源："+ ('${segment.source}'==null&&'${segment.source}'==''?"(无)":'${segment.source}'));
			$("#segmentCreateDate").html("创建时间："+'${segment.createDate}');
			$("#segmentinsertName").html('${segment.insertName}'==null || '${segment.insertName}'=='' ? "录入人：(无)":"录入人："+'${segment.insertName}');
			//alert($('#tagList02').length);
			/*
			var tagArr = [];
			for(var i = 0; i < $('#tagList02').length; i++) {
				//tagArr.push($('#tagList02')[i].name);
				//alert($('#tagList02')[i].name);
			}
			$("#segmenttags").html("标签：" + tagArr.join(','));
			*/
			segmentWin.window('open');

		}

		function auditSegmentPass() {
			var id = '${segment.id}';
			//$.messager.confirm('确认', '你确认审批通过该切片吗?', function(r){
				//if (r){
					$.post('${pageContext.request.contextPath}/segment/approve/'+id,{status:'1'},function(result){
								if (result.success){
									// 重新加载数据
									//location.href = '${pageContext.request.contextPath}/segment';
									window.close();
								} else {
									$.messager.show({	// 显示错误信息
										title: '错误',
										msg: result.msg
									});
								}
					},'json');
				//}
		//	});
		}
		
		function reAuditSegmentPass() {
			var id = '${segment.id}';
			var weightVal = $('#weighingVal').val();
			var weightType = $('input:checked').val();
			var baseSource = '${segment.source}';
			//alert(weightType + '  **  ' + weightVal + '  **  ' + baseSource);
			//return;
			$.post('${pageContext.request.contextPath}/segment/approve/'+id,{status:'3', weightType: weightType, weightVal: weightVal, baseSource: baseSource},function(result){
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

		function auditSegmentNoPass() {
			var state = '${segment.state}';
			var id = '${segment.id}';
			if(state==2){
				 $.messager.show({
					title: '提示：',
					msg: '该记录已被驳回不能重复驳回!'
				 });
				 return;
			}
			$.messager.prompt('驳回原因', '请填写驳回原因:</br></br><font color="red">注意：必须填写驳回原因！</font>', function(r){
				//alert(typeof r);
				if(r) {
					$.post('${pageContext.request.contextPath}/segment/approve/'+id,{status:'2',auditInfo:r},function(result){
						if(result.success){
							//location.href = '${pageContext.request.contextPath}/segment';
							window.close();
						} else {
							$.messager.show({	// 显示错误信息
								title: '错误',
								msg: result.msg
							});
						}
					},'json');
				} /*else if() {
					$.messager.show({
						title: '提示：',
						msg: '必须填写驳回原因！'
					});
					return ;
				} */
			});
		}

		function saveAndPass() {
			var state = '${segment.state}';
			if(state==3){
				 $.messager.show({
					title: '提示：',
					msg: '该记录已通过二次审批不能重复审批!'
				 });
				 return;
			}
			if(state == 1) {
				$.messager.confirm('提示：', '该记录已经通过审批，是否进行二次审批？', function(r) {
					if(r) {
						//alert('123');
						//return;
						// 保存
						$('#content').val(editor_a.getContent());//赋值
						setTagIds();
						$('#fm').form('submit',{
							url: '${pageContext.request.contextPath}/segment/save',
							onSubmit: function(){
								return $(this).form('validate');
							},
							success: function(result){
								var result = eval('('+result+')');
								if (result.success){
									// 审批通过
									reAuditSegmentPass() ;

								} else {
									$.messager.show({
										title: '错误',
										msg: result.msg
									});
								}
							}
						});
					}
				});
			}
			if( state == 0 || state == 2) {
				$.messager.confirm('确认', '你确认审批通过该切片吗?',function(r){
					if(r) {
						// 保存
						$('#content').val(editor_a.getContent());//赋值
						setTagIds();
						$('#fm').form('submit',{
							url: '${pageContext.request.contextPath}/segment/save',
							onSubmit: function(){
								return $(this).form('validate');
							},
							success: function(result){
								var result = eval('('+result+')');
								if (result.success){
									// 审批通过
									auditSegmentPass() ;
			
								} else {
									$.messager.show({
										title: '错误',
										msg: result.msg
									});
								}
							}
						});
					}
				});
			}
			
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
	<div id="revisability">
		<form id="fm" method="post" novalidate>
			<div class="ftitle">切片基础信息</div>
			<div class="fitem">
				<label>标题:</label>
				<input name="title" class="easyui-validatebox" required="true" value="${segment.title}" style="width:400px" /> 
				<input name="id" type="hidden"  value="${segment.id}" style="width:400px" />
				<!--
				<input name="insertId" type="hidden" value="${segment.insertId}" />
				<input name="insertName" type="hidden" value="${segment.insertName}" />
				<input name="createDate02" type="hidden" value="${segment.createDate}" />
				-->
				<input name="checkId" type="hidden" value="${segment.checkId}" />
				<input name="checkName" type="hidden" value="${segment.checkName}" />
				<input name="checkDate02" type="hidden" value="${segment.checkDate}" />
				<input name="weighing" type="hidden" value="${segment.weighing }" />
			</div>
			<div class="fitem">
				<label>简标题:</label>
				<input name="shortTitle" class="easyui-validatebox" style="width:300px" value="${segment.shortTitle}" />
			</div>
			<div class="fitem">
				<label>条目:</label>
				<input type="text" name="segItem" id="segItem" style="width:200px" value="${segment.segItem }"/>
			</div>
			<div class="fitem">
				<label>摘要:</label>
				<textarea name="summary" style="width:550px;height:100px">${segment.summary}</textarea>
			</div>
			<div class="fitem">
				<label>发布时间:</label>
				<input type="text" id="begincreateDate02" name="begincreateDate02" class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate  value="${segment.begincreateDate}" type="both" pattern="yyyy-MM-dd " />' />
			</div>
			
			<div class="fitem">
				<label>来源:</label>
				<input type="text" id="source" name="source" style="width:400px" value="${segment.source}" style="width:400px" />
			</div>
			<c:if test="${segment.state == 2}">
				<div class="fitem">
					<label><font>驳回原因:</font></label>
					<input type="text" id="auditInfo" name="auditInfo" style="width:400px; color:red;" value="${segment.auditInfo}" readonly/>
				</div>
			</c:if>
			<!-- 设置权重 -->
			<c:if test="${segment.state == 1 || segment.state == 3}">
				<br />
				<div class="fitem">
					<label class="ftitle">权重设置</label><br />
					<input type="radio" name="weight" value="title" checked/> 标题 &nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="weight" value="source" /> 来源<br /><br />
					权重值：<input type="text" name="weighingVal" id="weighingVal" value="${segment.weighing }" style="width:40px;" onblur="checkWeighingVal(value);" />
				</div>
				<br />
			</c:if>
			
			<div class="ftitle">标签信息</div>
			<div class="fitem">
				<div id="tagList" title="标签列表" class="easyui-datagrid" style="width:650px;height:200px"></div>
				<input id="tags" name="tags" type="hidden" /> 
			</div>
			<div class="ftitle">切片内容信息</div>
			<div class="fitem">
				<label></label>
				<textarea id="myEditor">${segment.content}</textarea>
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
	</div>

	<div id="" style="padding:30px;">
		<c:if test="${segment.state == 0 || segment.state == 2}">
			<span id="saveBtn"><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:saveAndPass();">保存/审批通过</a></span>&nbsp;&nbsp;
		</c:if>
		<c:if test="${segment.state == 1 }">
			<span id="saveBtn"><a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-ok" onclick="javascript:saveAndPass();">保存/二次审批通过</a></span>&nbsp;&nbsp;
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo" onclick="javascript:auditSegmentNoPass();">驳回</a>&nbsp;&nbsp;
		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:window.close();">关闭</a>
	</div>
	<!-- 预览 -->
	<div id="segment-window" class="easyui-window" title="预览" data-options="iconCls:'icon-tip'" maximized="true">  
        <div class="easyui-layout" data-options="fit:true">  
            <div data-options="region:'east',split:true" style="width:150px;padding:5px;">
            	<div id="segmentTitle"></div>
            	<div id="segmentSource"></div>
            	<div id="segmentCreateDate"></div>
            	<div id="segmentinsertName"></div>
            	<div id="segmenttags"></div>
            </div>  
            <div data-options="region:'center'" style="padding:10px;">  
                <div id="segmentContent" style="margin-left:10px"></div>
            </div>  
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">  
                <a class="easyui-linkbutton"  data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:$('#segment-window').window('close');">关闭</a>  
            </div>  
        </div>  
    </div>  
</body>
</html>
