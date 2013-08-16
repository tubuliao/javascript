var listUrl='/sourcelist/data';
var url='/source/save';
var grid;
var sourceWin;

var $parent = self.parent.$ ;

function query(){
	var standardName=$("#standardName").val();
	var standardNo=$("#standardNo").val();
	var standardType=$("#standardType").val();
	var editDepartment=$("#editDepartment").val();
	var approveDepartment=$("#approveDepartment").val();
	grid.datagrid({ url: listUrl, queryParams: {standardName:standardName,standardNo:standardNo,standardType:standardType,editDepartment:editDepartment,approveDepartment:approveDepartment} });
	
}

function remove(){
	var row = $('#grid').datagrid('getSelected');
	if (row){
		$.messager.confirm('提示','您确定要删除该来源?',function(r){
			if (r){
				$.post('/source/delete/'+row.id,{id:row.id},function(result){
					if (result.success){
						$('#grid').datagrid('reload');	// 重新加载数据
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

function modSegMenetCount(){
	 
	var row = $('#grid').datagrid('getSelected');
	var name_no="《"+row.standardName+"》"+row.standardNo;
	
	if (row){
				$.post('/modifySegMentCount',{name_no:name_no},function(result){
					if (result.success){
						$('#grid').datagrid('reload');	// 重新加载数据
					} else {
						$.messager.show({	// 显示错误信息
							title: '错误',
							msg: result.msg
						});
					}
				},'json');
	}else {
		alert('');
        Msgshow('请先选择要删除的记录。');
    }
}

function beachModSegMenetCount(){
	$.post('/modifySegMentCount',{name_no:""},function(result){
		if (result.success){
			$('#grid').datagrid('reload');	// 重新加载数据
		} else {
			$.messager.show({	// 显示错误信息
				title: '错误',
				msg: result.msg
			});
		}
	},'json');
}

 
function save(){
	
}

//分页方法
function pageUrl(pageSize,pageNo){
	return listUrl+"?page.page="+pageNo+"&page.size="+pageSize;
}


$(function () {
	backGround = $('#backGround_read').val();
	bar={};
	bar1=[{
        text: '新增',
        iconCls: 'icon-add',
        handler: addSource
    }, '-', {
        text: '修改',
        iconCls: 'icon-edit',
        handler: editSource
    },'-', {
        text: '删除',
        iconCls: 'icon-remove',
        handler: remove
    },'-',{
    	text: '预览',
    	iconCls: 'icon-tip',
    	handler: showSource
    },'-', {
        text: '同步',
        iconCls: 'icon-ok',
        handler: modSegMenetCount
    },'-', {
        text: '批量同步',
        iconCls: 'icon-ok',
        handler: beachModSegMenetCount
    },'-', {
        text: 'Excel导入',
        iconCls: 'icon-excel',
        handler: importData
    }];
	var bar2=[{
    	text: '预览',
    	iconCls: 'icon-tip',
    	handler: showSource
    }];
       
	if(backGround=='1'){bar=bar2;}else{bar=bar1;};
    $.extend($.fn.validatebox.defaults.rules, {   
	    /*必须和某个字段相等*/
	    equalTo: { 
	        validator:function(value,param){ 
	            return $(param[0]).val() == value; 
	        }, 
	        message:'字段不匹配'
	    },
	    validateUser : {     
            validator: function(value,param){     
                
                if(value.length >= param[0] && param[1] >= value.length)  
                {  
                    if (!/^[\w]+$/.test(value)) {   
	                    param[2]= '用户名只能英文字母、数字及下划线的组合！';   
	                    return false;   
	                }
                }else{  
                    param[2] = "请输入"+param[0]+"-"+param[1]+"位字符.";  
                    return false;  
                } 
                return true;
            },     
            message: "{2}"   
        },
        
        validateLen : {
        	validator : function(value, param) {
        		if(value.length > param[0]) {
        			return false ;
        		} 
        		return true ;
        	},
        	message : "请输入不超过{0}位长度的字符！"
        }
    });  
    

	grid=$('#grid').datagrid({
				url: listUrl,
				loadMsg:'正在处理，请稍待。。。',
				title: '来源列表',
		        iconCls: 'icon-source',
		        methord: 'post',
		        //sortName: 'ID',
		        //sortOrder: 'desc',
		        //idField: 'ID',
		        pageSize: 50 ,
		        columns: [[
							 {field:'id',title:'id',hidden:true},  
							 {field:'standardType',title:'标准类型',width:25},
			        		 {field:'standardName',title:'标准名称',width:40},  
			        		 {field:'standardNo',title:'标准编号',width:25}, 
			        		 {field:'changeNo', title:'被替标准编号',width:25},
			        		 {field:'englishName', title:'英文名称', width:25},
			        		 {field:'editDepartment',title:'主编单位',width:25},
			        		 {field:'approveDepartment',title:'批准部门',width:25},
			        		 {field:'executeDate', title:'实施日期', width:25},
			        		 {field:'segmentCount',title:'切片数量',width:10}
			        		 
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
				},
				onDblClickRow:function(rowIndex, rowData){
					showSource() ;
				}
			});

			sourceWin=$parent('#source-window').window({
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


function editSource() {
   var row = $('#grid').datagrid('getSelected') ;
   if(row) {
   		location.href = '/source/editSource/' + row.id ;
   } else  {
   		$.messager.show({
   			title: '提示：',
   			msg: '请选择一条记录进行操作！'
   		})
   }
}

function importData(){
	$('#dlg-xls').dialog('open').dialog('setTitle','导入来源');
	 //Msgshow('暂不支持');
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

function addSource() {
	window.location.href = '/source/addSource' ;
}


function showSource(){
	var row = $('#grid').datagrid('getSelected');
	if (row){
		$.get('/source/preview', { sourceId: row.id}, function(result) {
			if(result.success) {
				var source = result.sourceDetail ;
				
				$parent("#sourceCatalog").html(source.catalog);
				$parent("#sourceDescription").html(source.description);
			
				$parent("#sourceStandardName").html("标准名称：" + source.standardName);
				$parent("#sourceStandardType").html("标准类型："+source.standardType);
				$parent("#sourceStandardNo").html("标准编号："+ source.standardNo) ;
				$parent("#sourceExecuteDate").html('实施时间：' + (source.executeDate == null ? '无' : source.executeDate));
				
				sourceWin.window('open');
			} else {
				$.messager.alert('错误', result.msg) ;
			}
		}, 'json') ;
	}else {
        Msgshow('请先选择要查看的记录。');
    }
}
 