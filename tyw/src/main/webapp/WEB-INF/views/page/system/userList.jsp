<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
   <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<script type="text/javascript" src="easyui/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<scirpt type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
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
	<script type="text/javascript">
	    //删除用户
		function removeUser(){
			var row = $('#dg').datagrid('getSelected');
			if (row){
				$.messager.confirm('提示','您确定要删除该用户?',function(r){
					if (r){
						$.post('user/delete/'+row.id,{id:row.id},function(result){
						   // alert(row.id);
							if (result.success){
								$('#dg').datagrid('reload');	// 重新加载数据
							} else {
								$.messager.show({	// 显示错误信息
									title: '错误',
									msg: result.msg
								});
							}
						},'json');
					}
				});
			}else{
				 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
				return;
			}
		}
		var url;
	

		//分页方法
		function pageUrl(pageSize,pageNo){
			return listUrl+"?page.page="+pageNo+"&page.size="+pageSize;
		}
		
		var listUrl='userlist/data';
		
		$(function(){
			$('#dg').datagrid({
				url: listUrl,
				loadMsg:'正在处理，请稍待。。。',
				 columns:[[  
			        {field:'id',title:'id',hidden:true},  
			        {field:'username',title:'用户名',width:25},  
			        {field:'aliasname',title:'用户显示名',width:25}, 
			        {field:'userType',title:'会员类型',width:20,
			        	formatter: function(value,row,index){
							if (value==1){
								return '个人会员';
							} else if(value==2) {
								return '企业会员';
							}else if(value==3) {
								return '渠道商';
							}else if(value==4) {
								return '管理员';
							}
						}
			        	
			        },
			        {field:'nonLocked',title:'是否锁定',width:15,
			        	formatter: function(value,row,index){
							if (value){
								return '是';
							} else  {
								'否';
							}
						}
			        },
			        {field:'enable',title:'是否可用',width:15,
			        	formatter: function(value,row,index){
							if (value){
								return '是';
							} else  {
								'否';
							}
						}
			        
			        }
			    ]], 
				onBeforeLoad: function(param){
					var pageNo=$(this).datagrid('getPager').pagination('options').pageNumber;
					var pageSize=$(this).datagrid('getPager').pagination('options').pageSize;
					//使用查询参数有问题
					//var queryParams={'page.page':pageNo,'page.size':pageSize};
					//$(this).datagrid('options').queryParams=queryParams;
					$(this).datagrid('options').url=pageUrl(pageSize,pageNo);
				}
			});
			
			$('#dg').datagrid('getPager').pagination({
	    		displayMsg:'当前显示从{from}到{to}共{total}记录',
	    		beforePageText:'当前第',
	    		afterPageText:'页,共{pages}页',
	    		onBeforeRefresh:function(pageNumber, pageSize){
	     			$(this).pagination('loading');
	     			$(this).pagination('loaded');
	    		},});
	    		
	    	$('#mm').menu({  
		    	onClick:function(item){  
       	 			newTag();
    			}  
			});	
		});
		
		function addUser(){
			location.href='user';
		}
        function updateUser(){ 
		    var row = $('#dg').datagrid('getSelected');
			if (row){
			location.href='user/update/'+row.id;
			}else{
				 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
				return;
			}
		}
		function viewUser(){ 
		    var row = $('#dg').datagrid('getSelected');
			if (row){
			location.href='seeuser?id='+row.id;
			}else{
				 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
				return;
			}
		}
		function Msgslide(msg) {
			$.messager.show({
				title: '提示',
				msg: msg,
				timeout: 3000,
				showType: 'slide'
		});
}
	</script>
</head>
<body>
	
	<table id="dg" title="用户列表" class="easyui-datagrid" style="width:880px;height:400px"
			toolbar="#toolbar" pagination="true"
			rownumbers="true" fitColumns="true" singleSelect="true">
		
        
	</table>
	<div id="toolbar">
	    <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addUser()">新增用户</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUser()">删除用户</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateUser()">编辑用户</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="viewUser()">查看用户</a>
	</div>
	

</body>
</html>
