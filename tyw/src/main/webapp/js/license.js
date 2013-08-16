var listUrl='/licensebatchlist/data';
var qdsUrl='/qdslist/data';
var licenseUrl = '/licenselist/data';
var grid;
var qdsgrid;
var scgrid;

function query(){
	var licenseBatch=$("#licenseBatch").val();
	var agentId=$("#agentId").val();
	grid.datagrid({ url: listUrl, queryParams: {licenseBatch:licenseBatch,agentId:agentId } });
}

//显示新增标签对话框
function add(){ 
	$('#fm').form('clear');
	$('#dlg').dialog('open').dialog('setTitle','生成序列号');
}
function save(){
	$('#fm').form('submit',{
		url: '/license/save',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(result){
			var result = eval('('+result+')');
			if (result.success){
				$('#dlg').dialog('close');		
				$('#grid').datagrid('reload');	
			} else {
				$.messager.show({
					title: '错误',
					msg: result.msg
				});
			}
		}
	});
}

//分页方法
function pageUrl(pageSize,pageNo){
	return listUrl+"?page.page="+pageNo+"&page.size="+pageSize;
} 
function pageqdsUrl(pageSize,pageNo){
	return qdsUrl+"?page.page="+pageNo+"&page.size="+pageSize;
}
function pagelicenseUrl(batchCode,pageSize,pageNo){
	return licenseUrl+"?page.page="+pageNo+"&page.size="+pageSize+"&batchCode="+batchCode;
}
function formatDate (date, format)
    { 
	return (format || '%y-%m-%d %h:%n:%s').replace(/%(?:%|(\d*)([ymdhns]))/g, function(a, b, c) {
		//var a;
		switch (c) {
		case 'y':
		a = date.getFullYear();
		break;
		case 'm':
		a = date.getMonth() + 1;
		break;
		case 'd':
		a = date.getDate();
		break;
		case 'h':
		a = date.getHours();
		break;
		case 'n':
		a = date.getMinutes();
		break;
		case 's':
		a = date.getSeconds();
		break;
		default:
		return '%';
		}
		 
		if (b) {
		b = +b;
		a += '';
		if (a.length < b) {
		do a = '0' + a;
		while (a.length < b);
		}
		else a = a.slice(-b);
		}
		return a;
		});
    }
	
	// 转换时间戳
	function getLocalTime(nS) {  
		return  formatDate(new Date(nS),"");
	} 
$(function () {
	backGround = $('#backGround_read').val();
	bar={};
	bar1=[{
        text: '新增',
        iconCls: 'icon-add',
        handler: add
    }];
	var bar2=[];
       
	if(backGround=='1'){bar=bar2;}else{bar=bar1;};
	grid=$('#grid').datagrid({
				url: listUrl,
				loadMsg:'正在处理，请稍待。。。',
				title: '批次号列表',
		        iconCls: 'icon-user',
		        methord: 'post',
		        sortName: 'ID',
		        sortOrder: 'desc',
		        idField: 'ID',
		        pageSize: 10 ,
		        columns: [[
							 {field:'id',title:'id',hidden:true},  
			        		 {field:'agent',title:'渠道商',width:25,formatter:function(value,row,index){
			        		    return value.username;
			        		 }},  
			        		 {field:'batchCode',title:'批次号',width:25}, 
			        		 {field:'licenseTotal',title:'license数量',width:25},
			        		 {field:'createDate',title:'创建时间',width:25,formatter:function(value,row,index){
			        		    return getLocalTime(value);
			        		 }}  
						]],
		        fit:true,
		        pagination: true,
		        rownumbers: true,
		        fitColumns: true,
		        singleSelect: true,
		        toolbar: bar,
		        onHeaderContextMenu: function (e, field) {
		            e.preventDefault();
		            if (!$('#tmenu').length) {
		              
		            }
		            $('#tmenu').menu('show', {
		                left: e.pageX,
		                top: e.pageY
		            });
		        },
				onBeforeLoad: function(param){
					var pageNo=$(this).datagrid('getPager').pagination('options').pageNumber;
					var pageSize=$(this).datagrid('getPager').pagination('options').pageSize;
					//使用查询参数有问题
					$(this).datagrid('options').url=pageUrl(pageSize,pageNo);
				},
				onDblClickRow:function(rowIndex, rowData){
					//初始化一个grid来装载license列表
		 var licensegrid = $('#licensegrid').datagrid({
				url: licenseUrl,
				loadMsg:'正在处理，请稍待。。。',
				title: false,
		        iconCls: 'icon-user',
		        methord: 'post',
		        title:false,
		        sortName: 'ID',
		        sortOrder: 'desc',
		        idField: 'ID',
		        pageSize: 10 ,
		        columns: [[
							 {field:'id',title:'id',hidden:true},  
			        		 {field:'batchCode',title:'批次号',width:25},  
			        		 {field:'licenseNum',title:'序列号',width:25},
			        		 {field:'licenseType',title:'类型',width:25}, 
			        		 {field:'licenseStatus',title:'状态',width:25,formatter:function(value,row,index){
			        		 	var result = '未知';
			        		 	if(value == 0){
			        		 		result = '未登录';
			        		 	}else if(value ==1){
			        		 		result = '已登录，未缴费';
			        		 	}else if(value ==2){
			        		 		result = '已缴费';
			        		 	}
			        		    return result;
			        		 }}
						]],
		        fit:true,
		        pagination: true,
		        rownumbers: true,
		        fitColumns: true,
		        singleSelect: true,
		        onHeaderContextMenu: function (e, field) {
		            e.preventDefault();
		            if (!$('#tmenu').length) {
		              
		            }
		            $('#tmenu').menu('show', {
		                left: e.pageX,
		                top: e.pageY
		            });
		        },
				onBeforeLoad: function(param){
					var pageNo=$(this).datagrid('getPager').pagination('options').pageNumber;
					var pageSize=$(this).datagrid('getPager').pagination('options').pageSize;
					//使用查询参数有问题
					$(this).datagrid('options').url=pagelicenseUrl(rowData.batchCode,pageSize,pageNo);
				} 
			});
	    	
	    	$('#qdsgrid').datagrid('getPager').pagination({
	    		displayMsg:'当前显示从{from}到{to}共{total}记录',
	    		beforePageText:'当前第',
	    		afterPageText:'页,共{pages}页',
	    		onBeforeRefresh:function(pageNumber, pageSize){
	     			$(this).pagination('loading');
	     			$(this).pagination('loaded');
	    	}});	
	    	
					$("#licenselist").dialog("open");
				}
			});
			
			$('#licensegrid').datagrid('getPager').pagination({
	    		displayMsg:'当前显示从{from}到{to}共{total}记录',
	    		beforePageText:'当前第',
	    		afterPageText:'页,共{pages}页',
	    		onBeforeRefresh:function(pageNumber, pageSize){
	     			$(this).pagination('loading');
	     			$(this).pagination('loaded');
	    	}});
	    qdsgrid = $('#qdsgrid').datagrid({
				url: qdsUrl,
				loadMsg:'正在处理，请稍待。。。',
				title: '渠道商列表',
		        iconCls: 'icon-user',
		        methord: 'post',
		        title:false,
		        sortName: 'ID',
		        sortOrder: 'desc',
		        idField: 'ID',
		        pageSize: 10 ,
		        columns: [[
							 {field:'id',title:'id',hidden:true},  
			        		 {field:'username',title:'渠道商',width:25},  
			        		 {field:'phone',title:'手机号',width:25}, 
			        		 {field:'mail',title:'邮件',width:25}
						]],
		        fit:true,
		        pagination: true,
		        rownumbers: true,
		        fitColumns: true,
		        singleSelect: true,
		        onHeaderContextMenu: function (e, field) {
		            e.preventDefault();
		            if (!$('#tmenu').length) {
		              
		            }
		            $('#tmenu').menu('show', {
		                left: e.pageX,
		                top: e.pageY
		            });
		        },
				onBeforeLoad: function(param){
					var pageNo=$(this).datagrid('getPager').pagination('options').pageNumber;
					var pageSize=$(this).datagrid('getPager').pagination('options').pageSize;
					//使用查询参数有问题
					$(this).datagrid('options').url=pageqdsUrl(pageSize,pageNo);
				},
				onDblClickRow:function(rowIndex, rowData){
					$("#agentId").val(rowData.id);
					$("#agentName").val(rowData.username);
					$('#qdscx').dialog('close');
				}
			});
	    	
	    	$('#qdsgrid').datagrid('getPager').pagination({
	    		displayMsg:'当前显示从{from}到{to}共{total}记录',
	    		beforePageText:'当前第',
	    		afterPageText:'页,共{pages}页',
	    		onBeforeRefresh:function(pageNumber, pageSize){
	     			$(this).pagination('loading');
	     			$(this).pagination('loaded');
	    	}});	
	    	
	    	
	    	 scgrid = $('#scgrid').datagrid({
				url: qdsUrl,
				loadMsg:'正在处理，请稍待。。。',
				title: '渠道商列表',
		        iconCls: 'icon-user',
		        methord: 'post',
		        title:false,
		        sortName: 'ID',
		        sortOrder: 'desc',
		        idField: 'ID',
		        pageSize: 10 ,
		        columns: [[
							 {field:'id',title:'id',hidden:true},  
			        		 {field:'username',title:'渠道商',width:25},  
			        		 {field:'phone',title:'手机号',width:25}, 
			        		 {field:'mail',title:'邮件',width:25}
						]],
		        pagination: true,
		        rownumbers: true,
		        fitColumns: true,
		        singleSelect: true,
		        onHeaderContextMenu: function (e, field) {
		            e.preventDefault();
		            if (!$('#tmenu').length) {
		              
		            }
		            $('#tmenu').menu('show', {
		                left: e.pageX,
		                top: e.pageY
		            });
		        },
				onBeforeLoad: function(param){
					var pageNo=$(this).datagrid('getPager').pagination('options').pageNumber;
					var pageSize=$(this).datagrid('getPager').pagination('options').pageSize;
					//使用查询参数有问题
					$(this).datagrid('options').url=pageqdsUrl(pageSize,pageNo);
				},
				onDblClickRow:function(rowIndex, rowData){
				 $("#agentNames").val(rowData.username);
				 $("#agentIds").val(rowData.id);
				 
				}
			});
	    	
	    	$('#scgrid').datagrid('getPager').pagination({
	    		displayMsg:'当前显示从{from}到{to}共{total}记录',
	    		beforePageText:'当前第',
	    		afterPageText:'页,共{pages}页',
	    		onBeforeRefresh:function(pageNumber, pageSize){
	     			$(this).pagination('loading');
	     			$(this).pagination('loaded');
	    	}});
	    		
    $('body').layout();
});

 

function roleLoad(row){
	var roleIds=[];
	for(var i=0;i<row.roles.length;i++){
		roleIds.push(row.roles[i].id);
	}
	return roleIds;
}

function Msgshow(msg) {
    $.messager.show({
        title: '提示',
        msg: msg,
        showType: 'show'
    });
}
function Msgslide(msg) {
    $.messager.show({
        title: '提示',
        msg: msg,
        timeout: 3000,
        showType: 'slide'
    });
}
function Msgfade(msg) {
    $.messager.show({
        title: '提示',
        msg: msg,
        timeout: 3000,
        showType: 'fade'
    });
}
