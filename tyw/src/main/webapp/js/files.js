function removeFile(){
	var row = $('#grid').datagrid('getSelected');
	if (row){
		$.messager.confirm('提示','您确定要删吗?',function(r){
			if (r){
				$.post('files/delete/'+row.id,{id:row.id},function(result){
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

function addFiles() {
	location.href = "/files/add" ;
}
/*
function addFileOld(){
	$('#fm').form('clear');
	if($("#primaryId").val()!=""){
	  	//showToolBar();
	  	extlistUrl="/formextlist/"+$("#primaryId").val();
  	}else{
  		//hideToolBar();
  		extlistUrl="/formextlist/0";
  	}
	$('#dlg').dialog('open').dialog('setTitle','新增文件信息');
	$('#fileContainer').empty(); //清空默认列表
}
*/
function query(){
	var searchInsertName=$("#searchInsertName").val();
	var searchSource=$("#searchSource").val();
	var searchStatus=$("#searchStatus").val();
	var searchTitle=$("#searchTitle").val();
	var searchSynStatus = $('#synAttachment').val() ;
	
	// 知识等级
	var searchLevel = $("#searchLevel").val();
	// 发布日期
	var searchBegincreateDate = $("#searchBegincreateDate").val();
	
	var tagId = $('#tree').tree('getSelected') == null ? '' : $('#tree').tree('getSelected').id ;
	grid.datagrid('clearSelections');
	grid.datagrid({ url: listUrl, queryParams: { insertName: searchInsertName,source:searchSource,state:searchStatus,title:searchTitle, synStatus:searchSynStatus, tagId: tagId, level: searchLevel, begincreateDate: searchBegincreateDate} });
}
function okFile(){
	var row = $('#grid').datagrid('getSelected');
	if(row){
		if(row.state==1){
			 Msgslide('该记录已通过审批不能重复审批!');
			 return;
		}
		$.messager.confirm('确认', '你确认审批通过该文件吗?', function(r){
			if (r){
				$.post('files/approve/'+row.id,{status:'1'},function(result){
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
        Msgshow('请先选择要审批记录。');
    }
}


function breakFile(){
	var row = $('#grid').datagrid('getSelected');
	if(row){
		if(row.state==2){
			 Msgslide('该记录已被驳回不能重复驳回!');
			 return;
		}
		$.messager.prompt('驳回原因', '请填写驳回原因:<br><font color="red">注意：必须填写驳回原因！</font>', function(r){
			if (r){
					$.post('files/approve/'+row.id,{status:'2',auditInfo:r},function(result){
					if (result.success){
						$('#grid').datagrid('reload');	// 重新加载数据
					} else {
						$.messager.show({	// 显示错误信息
							title: '错误',
							msg: result.msg
						});
					}
				},'json');
			} /*else {
				Msgslide("必须填写驳回原因！");
				return ;
			}*/
		});
	}else {
        Msgshow('请先选择要驳回的记录。');
    }
}

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

var $parent = self.parent.$ ;

function showFile02(){
	$('#fileContainer').empty(); // 清空URL
	var row = $('#grid').datagrid('getSelected');
	if (row){
		$.get('/files/previewFiles', { id: row.id}, function(result) {
			if(result.success) {
				var f = result.files
				//$parent("#fileContent").html(f.content);
				$parent("#fileCatalog").html(f.catalog);
				$parent("#fileContentDetail").html(f.content);
				$parent("#fileTitle").html("标题："+f.title);
				$parent("#fileSource").html("来源："+ (f.source==null?"(无)":f.source)) ;
				$parent("#fileCreateDate").html("创建时间："+f.createDate);
				$parent("#fileinsertName").html(f.insertName==null?"录入人：(无)":"录入人："+f.insertName);
				$parent("#fileId").val(f.id);
				var tagNames=[];
				for(var i=0;i<f.tags.length;i++){
					tagNames.push(f.tags[i].name);
				}
				$parent("#filetags").html("标签："+tagNames.join(","))
				fileWin.window('open');
			} else {
				$.messager.alert('错误：', result.msg) ;
			}
		}, 'json') ;
		
		
	}else {
        Msgshow('请先选择要查看的记录。');
    }
}


function showFile(){
	$('#fileContainer').empty(); // 清空URL
	var row = $('#grid').datagrid('getSelected');
	if (row){
		window.open('/detail/1/' + row.id, "_blank", "toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=yes, width=1024, height=800");
	}else {
        Msgshow('请先选择要查看的记录。');
    }
}



$(function () {
	//backGround = $('#backGround_read').val();
	bar=[];
	bar1=[{
		            text: '新增新文件',
		            iconCls: 'icon-add',
		            handler: addFiles
		        }, '-', {
		            text: '修改',
		            iconCls: 'icon-edit',
		            handler: editFile
		        }, '-', {
		            text: '删除',
		            iconCls: 'icon-remove',
		            handler: removeFile
		        }, '-', {
		            text: '预览',
		            iconCls: 'icon-tip',
		            handler: showFile
		        }/*, '-', {
		            text: '通过审批',
		            iconCls: 'icon-ok',
		            handler: okFile
		        }, '-', {
		            text: '驳回',
		            iconCls: 'icon-redo',
		            handler: breakFile
		        }*/, '-', {
		        	text: 'Excel导入',
		        	iconCls: 'icon-excel',
		        	handler: importData
		        }, '-', {
		        	text : '同步附件' ,
		        	iconCls : 'icon-ok' ,
		        	handler : synFileAttachment
		        }, '-', {
		            text: '通过/驳回',
		            iconCls: 'icon-ok',
		            handler: okOrBreak
		        }, '-',{
		            text: '导出excel',
		            iconCls: 'icon-redo',
		            handler: exportExcel
		        } ];
	var bar2=[{
		            text: '预览',
		            iconCls: 'icon-tip',
		            handler: showFile
		        }];
       
	//if(backGround=='1'){bar=bar2;}else{bar=bar1;};
	
	//按钮权限控制start
	//获取页面对权限控制的控件
	var backGround_add = $('#backGround_add').val();
	var backGround_modify = $('#backGround_modify').val();
	var backGround_remove = $('#backGround_remove').val();
	var backGround_summary = $('#backGround_summary').val();
	var BACKGROUND_PREVIEW = $('#backGround_preview').val();
	var backGround_importing = $('#backGround_importing').val();
	var backGround_approval_reject = $('#backGround_approval_reject').val();
	var backGround_exprot = $('#backGround_exprot').val();
	
	var backGround_synchro = $('#backGround_synchro').val();
	
	if(backGround_add=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
		            text: '新增新文件',
		            iconCls: 'icon-add',
		            handler: addFiles
		        });
	}
	if(backGround_modify=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
		            text: '修改',
		            iconCls: 'icon-edit',
		            handler: editFile
		        });
	}
	if(backGround_remove=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
		            text: '删除',
		            iconCls: 'icon-remove',
		            handler: removeFile
		        });
	}
	if(BACKGROUND_PREVIEW=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
		            text: '预览',
		            iconCls: 'icon-tip',
		            handler: showFile
		        });
	}
	if(backGround_importing=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
		        	text: 'Excel导入',
		        	iconCls: 'icon-excel',
		        	handler: importData
		        });
	}
	if(backGround_synchro=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
		        	text : '同步附件' ,
		        	iconCls : 'icon-ok' ,
		        	handler : synFileAttachment
		        });
	}
	if(backGround_approval_reject=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
		            text: '通过/驳回',
		            iconCls: 'icon-ok',
		            handler: okOrBreak
		        });
	}
	if(backGround_exprot=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
		            text: '导出excel',
		            iconCls: 'icon-redo',
		            handler: exportExcel
		        });
	}
	//按钮权限控制end

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
		        pageSize: 50 ,
		        columns: [[
							{ field: 'title', title: '标题', width: 70 },
							{ field: 'source', title: '来源', width: 100},
							{ field: 'insertName', title: '录入人', align: 'center',width: 20 },
							{ field: 'createDate', title: '创建时间', align: 'center',width: 45 },
							{ field: 'state', title: '状态',align: 'center', width: 25 ,formatter:function(value,row,index){if(value==0){return '<font color="blue">未审批</font>';}else if(value==1){return '<font color="#53FF53;">已审批</font>';}else if(value==2){return '<font color="red">已驳回</font>';}else if(value==3){return '<font color="green">已二次审批</font>';}}},
							{ field: 'url', title: '同步状态', align: 'center', width: 25, formatter:function(value, row, index) {if(value != null && value != "") {return '<font color="green">已同步</font>' ;} else {return '<font color="red">未同步</font>' ;}}	},
							{ field: 'weighing', title: '知识等级', align: 'center', width: 10}
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
					showFile();
				}
			});
			
			fileWin=$parent('#file-window').window({
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

var listUrl='/fileslist';
var url;
var tree;
var grid;
var searchWin;
var fileWin;

function editFile() {
    var row = grid.datagrid('getSelected');
    if(row){
//    	location.href='/file/edit/'+row.id;
    	window.open('/file/edit/'+row.id, "_blank", "toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=yes, width=1024, height=800");
    	// 被编辑数据行标题着色
    	showSelectedRow(row);
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
	grid.datagrid({ url: listUrl, queryParams: { name:'',tagId:''} });
}
function OpensearchWin() {
	$("#s_title").val('');
    searchWin.window('open');
}


function SearchOK() {
    var s_title = $("#s_title").val();
    searchWin.window('close');
    grid.datagrid({ url: listUrl, queryParams: { title: s_title} });
}
function closeSearchWindow() {
    searchWin.window('close');
}

function importData(){
	$('#dlg-xls').dialog('open').dialog('setTitle','导入来源');
	 //Msgshow('暂不支持');
}
 
function synFileAttachment() {
	//Msgshow('暂不支持') ;
	
	//location.href = "/files/synFileAttachment" ;
	
	$.messager.progress({
		title : '提示' ,
		text : '正在同步……' ,
		interval : 1000	
	})
	$.get('/files/synFileAttachmentOriginal', function(result) {
		if(result.success) {
//			$.messager.progress('close') ;
			$.messager.alert('提示：' , '同步成功！') ;
			$('#grid').datagrid('reload');	// 重新加载数据
		} else if(result.fail) {
//			$.messager.progress('close') ;
			$.messager.alert("错误：", result.msg, 'error');
		}
	}, 'json')
	.complete(function() { $.messager.progress('close'); });
}

function okOrBreak() {
	var row = grid.datagrid('getSelected');
    if(row){
		window.open('/file/audit/' + row.id, "_blank", "toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=yes, width=1024, height=800");
		// 被编辑数据行标题着色
    	showSelectedRow(row);
    }else{
    	 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
        return;
    }
}



var xlsUrl = "/export";
function exportExcel() {
	// 标签
	var tagId = $('#tree').tree('getSelected') == null ? '' : $('#tree').tree('getSelected').id ;
	// 录入人
	var searchInsertName=$("#searchInsertName").val();
	// 来源
	var searchSource=$("#searchSource").val();
	// 审批状态
	var searchStatus=$("#searchStatus").val();
	// 标题
	var searchTitle=$("#searchTitle").val();
	// 发布日期
	var searchBegincreateDate = $("#searchBegincreateDate").val();
	// 知识等级
	var searchLevel = $("#searchLevel").val();
	
	window.location.href="/export?title=" + searchTitle + 
						"&insertName=" + searchInsertName + 
						"&source=" + searchSource + 
						"&state=" + searchStatus + 
						"&tagId=" + tagId + 
						"&infoType=" + "1" +
						"&begincreateDate=" + searchBegincreateDate +
						"&level=" + searchLevel;
}

function showSelectedRow(row) {
	var index=grid.datagrid('getRowIndex',row);
	grid.datagrid('updateRow',{
		index: index,
		row: {
			title: "<font style='background:#FFC78E'>" + row.title + "</font>"
		}
	});
//	grid.datagrid({
//		rowStyler: function(idx, row) {
//			if(idx == index) {
//				return  'background-color:#FFC78E;';	
//			}
//		}
//	});
}