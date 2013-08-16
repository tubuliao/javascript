//分页方法
function pageUrl(pageSize,pageNo){
	return listUrl+"?page.page="+pageNo+"&page.size="+pageSize;
}



$(function () {
	backGround = $('#backGround_read').val();
	bar={};
	bar1=[{
		            text: '查看',
		            iconCls: 'icon-edit',
		            handler: editSuggest
		        }];
	
       
	if(backGround=='1'){bar=bar2;}else{bar=bar1;};

    
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
				title: '用户意见列表',
		        iconCls: 'icon-table',
		        methord: 'post',
		        sortName: 'ID',
		        sortOrder: 'desc',
		        idField: 'ID',
		        pageSize: 50 ,
		        columns: [[
							{ field: 'title', title: '标题', width: 70 },
							{ field: 'name', title: '姓名', width: 20 },
							{ field: 'email', title: '邮箱', width: 20 },
							{ field: 'qq', title: 'QQ号', width: 20 },
							{ field: 'phone', title: '手机号', width: 20 },
							{ field: 'formatCreateDate', title: '创建时间', align: 'center',width: 45 }
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
					editSuggest();
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

var listUrl='/suggestlist';
var url;
var tree;
var grid;
var searchWin;

function editSuggest() {
    var row = grid.datagrid('getSelected');
    if(row){
    	location.href='/suggest/edit/'+row.id;
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