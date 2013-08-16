var listUrl='/formviewlist/data';
var grid;
 

function query(){
	var name=$("#name").val();
	grid.datagrid({ url: listUrl, queryParams: {name:name} });
}

//分页方法
function pageUrl(pageSize,pageNo){
	return listUrl+"?page.page="+pageNo+"&page.size="+pageSize;
} 

$(function () {
	grid=$('#grid').datagrid({
				url: listUrl,
				loadMsg:'正在处理，请稍待。。。',
				title: '来源统计列表',
		        iconCls: 'icon-user',
		        methord: 'post',
		        pageSize:20, 
			    pageList:[20,40,60,80,100],//可以设置每页记录条数的列表
		        columns: [[
			        		 {field:'name',title:'地区',width:25},  
			        		 {field:'formType',title:'类型',width:25},
			        		 {field:'allCount',title:'总数',width:25}, 
			        		 {field:'empCount',title:'表格XLS/PDF数',width:25},
			        		 {field:'picCount',title:'表格JPG数',width:25},
			        		 {field:'docCount',title:'填写说明DOC数',width:25},
			        		 {field:'htmlCount',title:'填写说明HTML数',width:25}  
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
			 		pageSize = pageSize == 10 ?60:pageSize; 
					$(this).datagrid('options').url=pageUrl(pageSize,pageNo);
				}
			});
			
			$('#grid').datagrid('getPager').pagination({
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
