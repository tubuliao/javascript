var listUrl='/thesauresList';
var url;
var tree;
var grid;
var searchWin;
var fileWin;


$(function () {
	backGround = $('#backGround_read').val();
	bar={};
	bar1=[{
        text: '添加',
        iconCls: 'icon-add',
        handler: addThesaures
    }, '-', {
        text: '修改',
        iconCls: 'icon-edit',
        handler: editThesaures
    }, '-', {
        text: '删除',
        iconCls: 'icon-remove',
        handler: deleteThesaures
    }, '-', {
    	text: 'Excel导入',
    	iconCls: 'icon-excel',
    	handler: importData 
    }];
	var bar2=[];
       
	if(backGround=='1'){bar=bar2;}else{bar=bar1;};

    tree = $('#tree').tree({
        checkbox: false,
        methord:'get',
        url: '/tagtree/form/0?ran='+Math.random(),
        onBeforeExpand: function (node, param) {
            $('#tree').tree('options').url = "/tagtree/form/" + node.id;                   
        },
        onClick: function (node) {
            grid.datagrid({ url: listUrl, queryParams: { tagId: node.id} });
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
				title: '文件列表',
		        iconCls: 'icon-table',
		        methord: 'post',
		        sortName: 'ID',
		        sortOrder: 'desc',
		        idField: 'ID',
		        pageList: [50, 100, 150, 200] ,
		        columns: [[
							{ field: 'id', title: 'id', width: 70, hidden: true },
							{ field: 'name', title: '名称', width: 100}
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
					$(this).datagrid('options').url=pageUrl(pageSize,pageNo);
				},onDblClickRow:function(rowIndex, rowData){

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
			
			$.extend($.fn.validatebox.defaults.rules, {
				validName: {
					validator: function(value, param) {
						alert("asd");
						var msg=$.ajax({
									url:"/validThesauresName",
									dataType:"json",
									data:{name: value},
									async:false,
									cache:false,
									type:"post"
								}).responseText;
						if("false" == msg) {
							return false;
						}
					},
					message: "已存在同名的关键词，请重新输入！"
				},
				validLen: {
					validator: function(value, param) {
						if(!"/^\d+$/".test(value)) {
							return false;
						}
					},
					message: "长度必须为数字！"
				}
			});
	    		
    $('body').layout();
});


function addThesaures() {
	window.location.href = '/addThesaures' ;
}


function deleteThesaures() {
	var row = $('#grid').datagrid('getSelected') ;
	if(row) {
		$.messager.confirm('提示：', '确认删除此记录？', function(r) {
			if(r) {
				$.get('/deleteThesaures/'+row.id, {}, function(result) {
					if(result.success) {
						$.messager.alert('提示：', '删除成功！') ;
						$('#grid').datagrid('reload') ;
					} else if(result.fail){
						$.messager.show({
							title: '错误：',
							msg: result.msg,
							showType: 'show'
						}) ;
					}
				}, 'json') ;
			} 
		})
	} else {
		$.messager.show({
			title: '提示：',
			msg: '请选择需要删除的记录！',
			showType: 'slide',
			timeout: 3000
		}) ;
	}
}

function editThesaures() {
    var row = grid.datagrid('getSelected');
    if(row){
    	location.href='/editThesaures/'+row.id;
    }else{
    	 $.messager.show({
    	 	title: '提示',
    	 	msg: '请选择一条记录进行操作',
    	 	showType: 'show'
    	 })
        return;
    }
}

function okVideo(){
	var row = $('#grid').datagrid('getSelected');
	if(row){
		if(row.state==1){
			 Msgslide('该记录已通过审批不能重复审批!');
			 return;
		}
		$.messager.confirm('提示：', '你确认审批通过该视频吗?', function(r){
			if (r){
//				$.post('/video/approve/'+row.id,{status:'1'},function(result){
//							if (result.success){
//								$('#grid').datagrid('reload');	// 重新加载数据
//							} else {
//								$.messager.show({	// 显示错误信息
//									title: '错误',
//									msg: result.msg
//								});
//							}
//				},'json');
			}
		});
		
	}else {
        $.messager.show({
        	title: '提示：',
        	msg: '请选择需要审批的记录！'
        }) ;
    }
}


function breakVideo(){
	var row = $('#grid').datagrid('getSelected');
	if(row){
		if(row.state==2){
			 Msgslide('该记录已被驳回不能重复驳回!');
			 return;
		}
		$.messager.prompt('驳回原因', '请填写驳回原因:<br><font color="red">注意：必须填写驳回原因！</font>', function(r){
			if (r){
//					$.post('/video/approve/'+row.id,{status:'2',auditInfo:r},function(result){
//					if (result.success){
//						$('#grid').datagrid('reload');	// 重新加载数据
//					} else {
//						$.messager.show({	// 显示错误信息
//							title: '错误',
//							msg: result.msg
//						});
//					}
//				},'json');
			} /*else {
				Msgslide("必须填写驳回原因！");
				return ;
			}*/
		});
	}else {
        Msgshow('请先选择要驳回的记录。');
    }
}


var $parent = self.parent.$ ;



function query(){
	var searchName=$("#searchName").val();
	grid.datagrid('clearSelections');
	grid.datagrid({ url: listUrl, queryParams: { name: searchName} });
}



/****************************************************************/





//导出数据
function exportSegment(){
	var total=$('#grid').datagrid('getPager').pagination('options').total;
	location.href='segment/export/2?page.size='+total;
	$.messager.progress({title:'数据处理进度',msg: '数据整理中...',
        interval: 1000}); 
  	//$.messager.progress('close');
}
//分页方法
function pageUrl(pageSize,pageNo){
	return listUrl+"?page.page="+pageNo+"&page.size="+pageSize;
}



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




function importData(){
	$('#dlg-xls').dialog('open').dialog('setTitle','词库导入');
	 //Msgshow('暂不支持');
}

