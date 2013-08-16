function removeTag(){
	var row = $('#grid').datagrid('getSelected');
	if (row){
		$.messager.confirm('提示','您确定要删除该标签?',function(r){
			if (r){
				$.post('/tag/delete/'+row.id,{id:row.id},function(result){
					if (result.success){
						$('#grid').datagrid('reload');	// 重新加载数据
						//刷新树
						reloadTree();
					} else {
						$.messager.show({	// 显示错误信息
							title: '错误',
							msg: result.msg
						});
					}
				},'json');
			}
		});
	}else {
        Msgshow('请先选择要删除的记录。');
    }
}

function reloadTree(){
	var node=tree.tree('getSelected');
	//此刷新有局限性,他只能刷新选中节点的子节点不刷新其他。
	if(node){
		if($('#level').val()==1){
			tree.tree('options').url = "/tagtree/tag/0";
			tree.tree('reload');
		}else{
			tree.tree('reload',node.target);
		}
	}else{
		tree.tree('options').url = "/tagtree/tag/0";
		tree.tree('reload');
	}
}

var url='/tag/save';
//显示新增标签对话框
function newTag(){
	$('#fm').form('clear');
	var node=tree.tree('getSelected');
	var parentId=0;
	if(node!=null){
		$('#dlg').dialog('open').dialog('setTitle','添加子标签');
		$('#parentId').val(node.id);
		//请求单个节点的全部信息 
		$.post('/tag/details/'+node.id+'?ran='+Math.random(),{id:node.id},function(result){
			$('#level').val(result.level+1);
			$('#parentCode').val(result.code);
		},'json');
		parentId=node.id;
	}else{
		$('#dlg').dialog('open').dialog('setTitle','添加根标签');
		$('#level').val(1);
	}
	$.post('/tag/sortNo/'+parentId+'?ran='+Math.random(),{id:''},function(result){
			if(result!=null){
				$('#sortNo').val(result.sortNo+1);
			}else{
				$('#sortNo').val(1);
			}
	},'json');
}
function newRootTag(){
	$('#fm').form('clear');
	$('#dlg').dialog('open').dialog('setTitle','添加根标签');
	$('#level').val(1);
	$.post('/tag/sortNo/0?ran='+Math.random(),{id:''},function(result){
			if(result!=null){
				$('#sortNo').val(result.sortNo+1);
			}else{
				$('#sortNo').val(1);
			}
	},'json');
}
function saveTag(){
	$('#fm').form('submit',{
		url: url,
		onSubmit: function(){
			if($('#level').val()>9){
				Msgshow('非法操作,标签最大支持到9级！');
				return false;
			}
			return $(this).form('validate');
		},
		success: function(result){
			var result = eval('('+result+')');
			if (result.success){
				$('#dlg').dialog('close');		
				$('#grid').datagrid('reload');	
				//刷新树
				reloadTree();
			} else {
				$.messager.show({
					title: '错误',
					msg: result.msg
				});
			}
		}
	});
}
//导出数据
function exportTag(){
	var total=$('#grid').datagrid('getPager').pagination('options').total;
	var queryParams=$("#grid").datagrid('options').queryParams;
	var exportUrl='/tag/export?page.size='+total;
	//alert("total:" + total + "queryParams:" + queryParams + "exportUrl:" + exportUrl) ;
	$.messager.progress({title:'数据处理进度',msg: '数据整理中...',interval: 1000}); 
	$.get(exportUrl,queryParams,function(data){
		
		if(data.success){
			$.messager.progress('close');
			location.href='/tag/download';
		}else{
			$.messager.progress('close');
			alert(data.msg);
		}
	});
}
//分页方法
function pageUrl(pageSize,pageNo){
	return listUrl+"?page.page="+pageNo+"&page.size="+pageSize;
}


function query(){
	var name=$("#name").val();
	var tagNo=$("#tagNo").val();
	grid.datagrid('clearSelections');
	grid.datagrid({ url: listUrl, queryParams: { name: name,tagNo:tagNo} });
}

var listUrl='/taglist';







$(function () {
	backGround = $('#backGround_read').val();
	bar={};
	bar1=[{
        text: '增加',
        iconCls: 'icon-add',
        handler: newTag
    }, '-',{
        text: '增加根标签',
        iconCls: 'icon-add',
        handler: newRootTag
    }, '-',{
        text: '修改',
        iconCls: 'icon-edit',
        handler: editTag
    }, '-', {
        text: '删除',
        iconCls: 'icon-remove',
        handler: removeTag
    }, '-',{
        text: '导出',
        iconCls: 'icon-excel',
        handler: exportTag
    } ];
	var bar2=[{
        text: '导出',
        iconCls: 'icon-excel',
        handler: exportTag
    } ];
       
	if(backGround=='1'){bar=bar2;}else{bar=bar1;};

	$(this).bind('contextmenu', function(e) {            
		// 你弹出右键的函数           
		return false;        
	});

    tree = $('#tree').tree({
        checkbox: false,
        methord:'get',
        url: '/tagtree/tag/0?ran='+Math.random(),
        onBeforeExpand: function (node, param) {
            $('#tree').tree('options').url = "/tagtree/tag/" + node.id;                   
        },
        onClick: function (node) {
            grid.datagrid({ url: listUrl, queryParams: { parentId: node.id} });
            grid.datagrid('clearSelections');
        }
    });
    
        $('#btn-search,#btn-search-cancel').linkbutton();
	    searchWin = $('#search-window').window({
	        closed: true,
	        modal: true,
	        resizable:false,
	        minimizable:false,
	        maximizable:false
	    });

	grid=$('#grid').datagrid({
				url: listUrl,
				loadMsg:'正在处理，请稍待。。。',
				title: '标签列表',
		        iconCls: 'icon-tag',
		        methord: 'post',
		        sortName: 'ID',
		        sortOrder: 'desc',
		        idField: 'ID',
		        pageSize: 10 ,
		        columns: [[
		        			{ field: 'name', title: '标签名称', width: 40 },
							{ field: 'level', title: '层级', width: 20 },
							{ field: 'tagNo', title: '首字母', width: 20 },
							{ field: 'sortNo', title: '序号', width: 20 },
							{ field: 'dataDate', title: '更新时间', width: 50 },
							{ field: 'summary', title: '备注', width: 50 }
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
		                createColumnMenu();
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
					//var queryParams={'page.page':pageNo,'page.size':pageSize};
					//$(this).datagrid('options').queryParams=queryParams;
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

function createColumnMenu() {
    var tmenu = $('<div id="tmenu" style="width:100px;"></div>').appendTo('body');
    var fields = grid.datagrid('getColumnFields');
    for (var i = 0; i < fields.length; i++) {
        $('<div iconCls="icon-ok"/>').html(fields[i]).appendTo(tmenu);
    }
    tmenu.menu({
        onClick: function (item) {
            if (item.iconCls == 'icon-ok') {
                grid.datagrid('hideColumn', item.text);
                tmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-empty'
                });
            } else {
                grid.datagrid('showColumn', item.text);
                tmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-ok'
                });
            }
        }
    });
}

var tree;
var grid;
var searchWin;


function editTag() {
    var row = grid.datagrid('getSelected');
    if(row){
    	$('#dlg').dialog('open').dialog('setTitle','编辑标签');
		$('#fm').form('load',row);
    }else{
    	 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
        return;
    }
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

function showAll() {
    grid.datagrid({ url: listUrl, queryParams: { name:'',parentId:''} });
}
function OpensearchWin() {
	$("#s_title").val('');
    searchWin.window('open');
}


function SearchOK() {
    var s_title = $("#s_title").val();
    searchWin.window('close');
    grid.datagrid({ url: listUrl, queryParams: { name: s_title} });
}
function closeSearchWindow() {
    searchWin.window('close');
}
 