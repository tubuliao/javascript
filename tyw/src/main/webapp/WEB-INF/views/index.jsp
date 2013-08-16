<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>天佑网后台</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/easyui/themes/icon.css">
	<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
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
    </style>
	<script>
		$(function(){
			var tabPanel=$("#main");
			$("#segment-window").window("close");//初始化关闭
			$("#form-window").window("close");//初始化关闭
			$("#file-window").window("close");//初始化关闭
			$("#image-window").window("close");
			$("#video-window").window("close");
			$("#source-window").window("close");
			$("#ppt-window").window("close");
			$("#question-window").window("close");
			/*菜单*/
			$(".easyui-accordion").find("a[link]").each(function(){
                   $(this).click(function(){
                        var url=$(this).attr("link");
                        var title=$(this).html();
				        if(url!=="#"&&url!==""){
								/*没有就添加,有就选中*/
					            if(!tabPanel.tabs('exists',title)){
							        tabPanel.tabs('add',{
							             title:title,
							             content:'<iframe src="'+url+'" style="padding:0;margin:0;border:0;width:100%;height:100%;"></iframe>',
							              closable:true});
							        bindfresh(title);
						        }else{
							        tabPanel.tabs('select',title);
							        fresh(title);
						        }
					        }
				      });
                  });
					
		    $.extend($.fn.validatebox.defaults.rules, {   
			    /*必须和某个字段相等*/
			    equalTo: { 
			        validator:function(value,param){ 
			            return $(param[0]).val() == value; 
			        }, 
			        message:'字段不匹配'
			    }
		    });  
		    
		  
		});
		
		function approveSegment(status){
			var row = $('#grid').datagrid('getSelected');
			$.post('segment/approve/'+row.id,{status:status},function(result){
							if (result.success){
								$.messager.confirm('确认', '审批完成是否关闭当前页面并刷新列表?', function(r){
									if (r){
										$('#grid').datagrid('reload');	// 重新加载数据
										segmentWin.window('close');
									}
								});
							} else {
								$.messager.show({	// 显示错误信息
									title: '错误',
									msg: result.msg
								});
							}
						},'json');
		}
		
		/*双击刷新TAB选项卡*/
		function bindfresh(title){
			$(".tabs-inner").dblclick(function(){
				var _ctab=$('#main').tabs('getTab',title);
				var html=_ctab.html();
				_ctab.html(html);
			});
        }
        
		/*刷新*/
        function fresh(title){
        	var _ctab=$('#main').tabs('getTab',title);
			var html=_ctab.html();
			_ctab.html(html);
        }
        
        
        function logout(){
        	$.messager.confirm('确认', '你确定要退出当前系统?', function(r){
				if (r){
					window.location="/logout.do";
				}
			});
        }
        
        function changepwd(){
        	//$('#fm').form('clear');
        	$('#dlg').dialog('open').dialog('setTitle','修改密码');
			$('#originalPwd').focus();
        }
        
        
       	function save(){
			if($('#originalMsg').html() != '') {
				alert('请输入正确的当前密码！');
				return;
			}
       		var validation = {'p1':$('#password').val(),'p2':$('#check_password').val()};
       		validation.p1Length = validation.p1.length;
       		validation.p2Length = validation.p2.length;
           	if(validation.p1Length==0){
               	alert("新密码不能为空！");
               	return false;
           	}
           	if(validation.p1!=validation.p2){
				alert("新密码与重复密码不一致！");
				return false;
            }
            if(!(6<=validation.p1Length<=20)){
					alert("密码必须介于6和20之间！");
					return false;
            }
       		$('#fm').form('submit',{
				url: '/user/changepwd',
				onSubmit: function(){
					return $(this).form('validate');
				},
				success: function(result){
					var result = eval('('+result+')');
					if (result.success){
						$('#dlg').dialog('close');		
						$.messager.alert('消息','恭喜你密码修改成功,请记住新密码,下次请使用新密码登陆!','info');
						$('#originalPwd').val('');
						$('#password').val('');
						$('#check_password').val('');
					} else {
						$.messager.show({
							title: '错误',
							msg: result.msg
						});
					}
				}
			});
       	}

		/*预览图片等比缩放*/
		function resizeImg(obj){
			 var imgW=obj.width;
			 var imgH=obj.height; 
			 //alert('W:' + imgW + '  H:' + imgH) ;
			  obj.width = '1024';
			  obj.height = imgH*1024/imgW;
			  
		}

		function checkOriginalPwd() {
			originalPwd=$('#originalPwd').val();
			//alert(originalPwd);
			if(originalPwd != '') {
				$.get('/user/CheckOriginalPwd', { originalPwd: originalPwd}, function(r) {
					if(!r.success) {
						$('#originalMsg').html('<font color="red">当前密码输入错误！</font>');
					} else if(r.success) {
						$('#originalMsg').html('');
					} else if(r.success == 'error') {
						$.messager.alert('错误：', r.msg);
					}
				}, 'json');
			} else {
				$('#originalMsg').html('<font color="red">请输入正确的当前密码！</font>');
			}
		}

		function clearMsg(){
			$('#originalPwd').val('');
			$('#password').val('');
			$('#check_password').val('');
			$('#originalMsg').html('');
		}
       	
	</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',split:false" title="" style="height:65px;">
		<div class="easyui-panel" data-options="fit:true,border:false" style="background:url(/images/logo.gif) no-repeat left center #e5eeff">
			
			<div style="float:right;">【${user.aliasname}】 欢迎 登录天佑网后台管理系统
			<a href="#" onClick="changepwd()" class="easyui-linkbutton" plain="true" icon="icon-undo" >修改密码</a>
			<a href="#" onClick="logout()" class="easyui-linkbutton" plain="true" icon="icon-cancel" >退出系统</a>
			</div>
		</div>
	</div>
	<div data-options="region:'south',split:false" title="" style="background-color:#AED7E5;height:25px;padding:0px;">
		<div >状态栏</div>
	</div>
	<div data-options="region:'west',split:false" title="菜单项" style="width:180px;padding1:1px;overflow:hidden;">
		<div class="easyui-accordion" data-options="fit:true,border:false">
		<sec:authorize ifAllGranted="KNOWLEDGE_MANAGEMENT">
			<div title="知识管理" style="padding:10px">
				<ul class="easyui-tree">
					<sec:authorize ifAllGranted="KNOWLEDGE_SEGMENTLIST">
						<li><span><a link="segment">知识切片列表</a></span></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="KNOWLEDGE_FORMLIST">
				 		<li><span><a link="form">表格列表</a></span></li>
				 	</sec:authorize>
				 	<sec:authorize ifAllGranted="KNOWLEDGE_IMAGELIST">
						<li><span><a link="image">图片列表</a></span></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="KNOWLEDGE_VIDEOLIST">
						<li><span><a link="video">视频列表</a></span></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="KNOWLEDGE_WORDLIST">
						<li><span><a link="files">Word文件列表</a></span></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="KNOWLEDGE_PPTLIST">
						<li><span><a link="ppt">PPT文件列表</a></span></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_QUANLIST">
						<li><span><a link="question">问答列表</a></span></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_SOURCELIST">
						<li><span><a link="source">来源列表</a></span></li>
					</sec:authorize>
					<sec:authorize ifAllGranted="ROLE_BATTAG">
						<li><span><a link="batchApproval">批量标签</a></span></li>
					</sec:authorize>

					<!--
					<li><span><a link="editor">文本编辑器示例</a></span></li>
					<li><span><a link="uploader">文件上传示例</a></span></li>
					-->
				</ul>
			</div>
		</sec:authorize>
		<sec:authorize ifAllGranted="TAG_MANAGEMENT">
			<div title="标签管理" style="padding:10px;">
				<ul class="easyui-tree">
					<li><span><a link="tag">标签列表</a></span></li>
				</ul>
			</div>
		</sec:authorize>
		<!-- 
		<sec:authorize ifAllGranted="CHANNEL_MANAGEMENT">
			<div title="渠道商家园"  style="padding:10px;">
				<ul class="easyui-tree">
					<li><span><a link="discountlist">优惠通道</a></span></li>
				</ul>
			</div>
		</sec:authorize>
		 -->
		<sec:authorize ifAllGranted="PRODUCT_MANAGEMENT">
			<div title="产品管理"  style="padding:10px;">
				<ul class="easyui-tree">
					<!--
					<li><span><a link="cardTypelist">产品类型管理</a></span></li>
					<li><span><a link="paymentlist">订单管理</a></span></li>
					-->
					<li><span><a link="cardlist">卡管理</a></span></li>
					<li><span><a link="licensePaymentlist">订单管理</a></span></li>
					<li><span><a link="receiptList">发票管理</a></span></li>
				</ul>
			</div>
		</sec:authorize>
			<!-- 
			<div title="统计分析" style="padding:10px;">
				<ul class="easyui-tree">
					<li><span>会员分析</span></li>
					<li><span>天佑卡分析</span></li>
					<li><span>项目信息统计</span></li>
					<li><span>知识热度统计</span></li>
					<li>
						<span>操作日志查询</span>
						<ul>
							<li><span>系统日志</span></li>
							<li><span>使用日志</span></li>
						</ul>
					</li>
				</ul>
			</div>
			 -->
		<sec:authorize ifAllGranted="USER_MANAGEMENT">
			<div title="用户管理" data-options="selected:false" style="padding:10px;overflow:auto;">
				<ul id="tt1" class="easyui-tree" data-options="animate:true,dnd:true">
					<li>
						<span>用户管理</span>
						<ul>
							<li><span><a link="user">用户管理</a></span></li>
							<!--
							<li>查看会员消费记录</li>
							<li>查看会员操作记录</li>
							-->
						</ul>
					</li>
					<li>
						<span>权限管理</span>
						<ul>
							<li><span><a link="role">权限管理</a></span></li>
						</ul>
					</li>
					<li>
						<span>公告信息维护</span>
						<ul>
							<li><span><a link="announcelist">公告信息列表</a></span></li>
						</ul>
					</li>
				</ul>
			</div>
			</sec:authorize>
			<sec:authorize ifAllGranted="LICENSE_MANAGEMENT">
			<div title="序列号管理"  style="padding:10px;">
				<ul class="easyui-tree">
					<li><span><a link="licenselist">license管理</a></span></li>
				</ul>
			</div>
			</sec:authorize>
			<sec:authorize ifAllGranted="USEROPINION_MANAGEMENT">
			<div title="用户意见管理"  style="padding:10px;">
				<ul class="easyui-tree">
					<li><span><a link="suggestList">意见管理</a></span></li>
				</ul>
			</div>
			</sec:authorize>
			<sec:authorize ifAllGranted="LEXICON_MANAGEMENT">
			<div title="词库管理"  style="padding:10px;">
				<ul class="easyui-tree">
						<li><span><a link="thesaures">词库列表</a></span></li>
				</ul>
			</div>
			</sec:authorize>
		<sec:authorize ifAllGranted="STATISTICANALYSIS_MANAGEMENT">
		<div title="统计分析"  style="padding:10px;">
				<ul class="easyui-tree">
					<li><span><a link="sourceviewlist">知识来源统计</a></span></li>
					<li><span><a link="formviewlist">表格同步统计</a></span></li>
					<li><span><a link="knowledgeSortList">知识分类统计</a></span></li>
					<li><span><a link="dataContrastList">数据对比统计</a></span></li>
				</ul>
		</div>
		</sec:authorize>
		</div>
	</div>
	<div data-options="region:'center'" title="" style="overflow:hidden;">
		<div id="main" class="easyui-tabs" data-options="fit:true,border:false">
			<div title="功能说明" style="padding:20px;overflow:hidden;"> 
				<div style="margin-top:20px;">
					
				</div>
			</div>
		</div>
	</div>
	
	
	<div id="dlg" class="easyui-dialog" style="width:350px;height:220px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons" data-options="modal:true">
		<div class="ftitle">修改密码</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>当前密码：</label>
				<input name="originalPwd" id="originalPwd" type="password" style="width:120px;" onblur="checkOriginalPwd();"/><br />
				<label></label><span id="originalMsg"></span>
			</div>
			<div class="fitem">
				<label>新密码:</label>
				<input name="password" id="password" type="password" style="width:120px;" validType="length[6,20]" class="easyui-validatebox"/>
				<input name="id" value='${user.id}' type="hidden" />
				<input name="username" value='${user.username}' type="hidden" />
			</div>
			<div class="fitem">
				<label>重复密码:</label>
				<input name="check_password" id="check_password" type="password" style="width:120px;" class="easyui-validatebox" validType="equalTo['#password']" invalidMessage="两次输入密码不匹配"/>
			</div>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onClick="save()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onClick="javascript:$('#dlg').dialog('close');clearMsg();">取消</a>
	</div>
	
	
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
                <a class="easyui-linkbutton"  data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:$('#segment-window').window('close')">关闭</a>  
            </div>  
        </div>  
    </div>  
    
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
                <a class="easyui-linkbutton"  data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:$('#form-window').window('close')">关闭</a>  
            </div>  
        </div>  
    </div>  

	<div id="file-window" class="easyui-window" title="预览" data-options="iconCls:'icon-tip'" maximized="true">  
        <div class="easyui-layout" data-options="fit:true">  
            <div data-options="region:'east',split:true" style="width:150px;padding:5px;">
            	<div id="fileTitle"></div>
            	<div id="fileSource"></div>
            	<div id="fileCreateDate"></div>
            	<div id="fileinsertName"></div>
            	<div id="filetags"></div>
            </div>  
            <div data-options="region:'center'" style="padding:10px;">  
                <!-- <div id="formContent" style="margin-left:10px"></div> -->
                <div id="fileContent" class="easyui-tabs" style="" fit=true>
                	<div id="fileCatalog" title="目录" selected="true"></div>
                	<div id="fileContentDetail" title="文件说明" ></div>
                </div>
            </div>   
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">  
                <a class="easyui-linkbutton"  data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:$('#file-window').window('close')">关闭</a>  
            </div>  
        </div>  
    </div>  


	<div id="image-window" class="easyui-window" title="预览" data-options="iconCls:'icon-tip'" maximized="true">  
        <div class="easyui-layout" data-options="fit:true">  
            <div data-options="region:'east',split:true" style="width:150px;padding:5px;">
            	<div id="imageTitle"></div>
            	<div id="imageSource"></div>
            	<div id="imageCreateDate"></div>
            	<div id="imageInsertName"></div>
            	<div id="imageTags"></div>
            </div>  
            <div data-options="region:'center'" style="padding:10px;">  
                <div id="imagePreview" class="easyui-tabs" style="" fit=true>
                	<div id="imageUrl" title="图片文件" selected="true"></div>
                	<div id="imageContent" title="说明文件" ></div>
                </div>
            </div>  
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">  
                <a class="easyui-linkbutton"  data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:$('#image-window').window('close')">关闭</a>  
            </div>  
        </div>  
    </div>  

	<div id="video-window" class="easyui-window" title="预览" data-options="iconCls:'icon-tip'" maximized="true">  
        <div class="easyui-layout" data-options="fit:true">  
            <div data-options="region:'east',split:true" style="width:150px;padding:5px;">
            	<div id="videoTitle"></div>
            	<div id="videoSource"></div>
            	<div id="videoCreateDate"></div>
            	<div id="videoInsertName"></div>
            	<div id="videoTags"></div>
            </div>  
            <div data-options="region:'center'" style="padding:10px;">  
                <div id="videoPreview" class="easyui-tabs" style="" fit=true>
                	<div id="videoUrl" title="视频文件" selected="true"></div>
                	<div id="videoContent" title="说明文件" ></div>
                </div>
            </div>  
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">  
                <a class="easyui-linkbutton"  data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:$('#video-window').window('close')">关闭</a>  
            </div>  
        </div>  
    </div>  
    
    <div id="source-window" class="easyui-window" title="预览" data-options="iconCls:'icon-tip'" maximized="true">  
        <div class="easyui-layout" data-options="fit:true">  
            <div data-options="region:'east',split:true" style="width:150px;padding:5px;">
            	<div id="sourceStandardName"></div>
            	<div id="sourceStandardType"></div>
            	<div id="sourceStandardNo"></div>
            	<div id="sourceExecuteDate"></div>
            </div>  
            <div data-options="region:'center'" style="padding:10px;">  
                <div id="sourcePreview" class="easyui-tabs" style="" fit=true>
                	<div id="sourceCatalog" title="标准目次" selected="true"></div>
                	<div id="sourceDescription" title="标准介绍" ></div>
                </div>
            </div>  
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">  
                <a class="easyui-linkbutton"  data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:$('#source-window').window('close')">关闭</a>  
            </div>  
        </div>  
    </div>  
    
    
    
    <div id="ppt-window" class="easyui-window" title="预览" data-options="iconCls:'icon-tip'" maximized="true">  
        <div class="easyui-layout" data-options="fit:true">  
            <div data-options="region:'east',split:true" style="width:150px;padding:5px;">
            	<div id="pptTitle"></div>
            	<div id="pptSource"></div>
            	<div id="pptCreateDate"></div>
            	<div id="pptInsertName"></div>
            	<div id="pptTags"></div>
            </div>  
            <div data-options="region:'center'" style="padding:10px;">  
                <div id="pptContent" style="margin-left:10px"></div>
            </div>   
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">  
                <a class="easyui-linkbutton"  data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:$('#ppt-window').window('close')">关闭</a>  
            </div>  
        </div>  
    </div>
    
    
    <div id="question-window" class="easyui-window" title="预览" data-options="iconCls:'icon-tip'" maximized="true">  
        <div class="easyui-layout" data-options="fit:true">  
            <div data-options="region:'east',split:true" style="width:150px;padding:5px;">
            	<div id="questionTitle"></div>
            	<div id="questionSource"></div>
            	<div id="questionCreateDate"></div>
            	<div id="questionInsertName"></div>
            	<div id="questionTags"></div>
            </div>  
            <div data-options="region:'center'" style="padding:10px;">  
                <div id="questionContent" style="margin-left:10px"></div>
            </div>   
            <div data-options="region:'south',border:false" style="text-align:right;padding:5px 0 0;">  
                <a class="easyui-linkbutton"  data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onClick="javascript:$('#question-window').window('close')">关闭</a>  
            </div>  
        </div>  
    </div>


</body>
</html>