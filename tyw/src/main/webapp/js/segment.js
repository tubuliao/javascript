function removeSegment(){
	var row = $('#grid').datagrid('getSelected');
	if (row){
		$.messager.confirm('提示','您确定要删除该切片?',function(r){
			if (r){
				$.post('segment/delete/'+row.id,{id:row.id},function(result){
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


function showSummary(){
	var row = $('#grid').datagrid('getSelected');
	if (row){
		$.messager.alert('摘要内容',row.summary==null?'(空)':row.summary,'info');
	}else {
        Msgshow('请先选择要查看的记录。');
    }
}

function okSegment(){
	var row = $('#grid').datagrid('getSelected');
	if(row){
		if(row.state==1){
			 Msgslide('该记录已通过审批不能重复审批!');
			 return;
		}
		$.messager.confirm('确认', '你确认审批通过该切片吗?', function(r){
			if (r){
				$.post('segment/approve/'+row.id,{status:'1'},function(result){
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

function breakSegment(){
	var row = $('#grid').datagrid('getSelected');
	if(row){
		if(row.state==2){
			 Msgslide('该记录已被驳回不能重复驳回!');
			 return;
		}
		$.messager.prompt('驳回原因', '请填写驳回原因:', function(r){
			if (r){
					$.post('segment/approve/'+row.id,{status:'2',auditInfo:r},function(result){
					if (result.success){
						$('#grid').datagrid('reload');	// 重新加载数据
					} else {
						$.messager.show({	// 显示错误信息
							title: '错误',
							msg: result.msg
						});
					}
				},'json');
			} else {
				Msgslide("必须填写驳回原因！");
				return ;
			}
		});
	}else {
        Msgshow('请先选择要驳回的记录。');
    }
}


var $parent = self.parent.$

function showSegment02(){
	var row = $('#grid').datagrid('getSelected');
	if (row){
		$.get('/segment/previewSegment/'+row.id, {}, function(result) {
			if(result.success) {
				var seg = result.segment ;
				$parent("#segmentContent").html(seg.content);
				$parent("#segmentTitle").html("标题："+seg.title);
				$parent("#segmentSource").html("来源："+ (seg.source==null?"(无)":seg.source));
				$parent("#segmentCreateDate").html("创建时间："+seg.createDate);
				$parent("#segmentinsertName").html(seg.insertName==null?"录入人：(无)":"录入人："+seg.insertName);
				$parent("#segmentId").val(seg.id);
				var tagNames=[];
				for(var i=0;i<seg.tags.length;i++){
					tagNames.push(seg.tags[i].name);
				}
				$parent("#segmenttags").html("标签："+tagNames.join(","))
				segmentWin.window('open');
			} else {
				$.messager('错误：', result.msg) ;
			}
		}, 'json') ;
	}else {
        Msgshow('请先选择要查看的记录。');
    }
}

function showSegment(){
	var row = $('#grid').datagrid('getSelected');
	if (row){
		window.open('/detail/2/' + row.id, "_blank", "toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=yes, width=1024, height=800");
	}else {
        Msgshow('请先选择要查看的记录。');
    }
}


var url;

function addPage(){
	location.href='segment/add';
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

var listUrl='segmentlist';


$(function () {
	//backGround = $('#backGround_read').val();
	bar=[];
	bar1=[/*{
		            text: '导出切片',
		            iconCls: 'icon-redo',
		            handler: exportSegment
		        }, '-', */{
		            text: '新增',
		            iconCls: 'icon-add',
		            handler: addPage
		        }, '-', {
		            text: '修改',
		            iconCls: 'icon-edit',
		            handler: editPage
		        }, '-', {
		            text: '删除',
		            iconCls: 'icon-remove',
		            handler: removeSegment
		        }, '-', {
		            text: '摘要',
		            iconCls: 'icon-tip',
		            handler: showSummary
		        }, '-', {
		            text: '预览切片',
		            iconCls: 'icon-tip',
		            handler: showSegment
		        }, '-', {
		        	text: 'Excel导入',
		        	iconCls: 'icon-excel',
		        	handler: importData
		        }, '-', {
					text: '审批/驳回',
					iconCls: 'icon-ok',
					handler: auditSegment
				}, '-',{
		            text: '导出excel',
		            iconCls: 'icon-redo',
		            handler: exportExcel
		        }];
	var bar2=[ {
		            text: '摘要',
		            iconCls: 'icon-tip',
		            handler: showSummary
		        }, '-', {
		            text: '预览切片',
		            iconCls: 'icon-tip',
		            handler: showSegment
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
	
	if(backGround_add=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
		            text: '新增',
		            iconCls: 'icon-add',
		            handler: addPage
		        });
	}
	if(backGround_modify=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
		            text: '修改',
		            iconCls: 'icon-edit',
		            handler: editPage
		        });
	}
	if(backGround_remove=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
		            text: '删除',
		            iconCls: 'icon-remove',
		            handler: removeSegment
		        });
	}
	if(backGround_summary=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
		            text: '摘要',
		            iconCls: 'icon-tip',
		            handler: showSummary
		        });
	}
	if(BACKGROUND_PREVIEW=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
		            text: '预览切片',
		            iconCls: 'icon-tip',
		            handler: showSegment
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
	if(backGround_approval_reject=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
					text: '审批/驳回',
					iconCls: 'icon-ok',
					handler: auditSegment
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
        url: '/tagtree/segment/0?ran='+Math.random(),
        onBeforeExpand: function (node, param) {
            $('#tree').tree('options').url = "/tagtree/segment/" + node.id;                   
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
    
    segmentWin=$parent('#segment-window').window({
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

	grid=$('#grid').datagrid({
				url: listUrl,
				loadMsg:'正在处理，请稍待。。。',
				title: '知识切片列表',
		        iconCls: 'icon-book',
		        methord: 'post',
		        url: 'newslistdemo',
//		        view: detailview,
		        sortName: 'ID',
		        sortOrder: 'desc',
		        idField: 'ID',
		        pageSize: 50 ,
		        columns: [[
							{ field: 'title', title: '标题',width: 70/*, formatter:function(value, rowData, rowIndex) { return "<font color='yellow'>" + value + "</font>";}*/},
							{ field: 'segItem', title: '条目', width: 20},
							{ field: 'source', title: '来源', align: 'left', width: 100},
							{ field: 'insertName', title: '录入人',align: 'center', width: 20 },
							//{ field: 'createDate', title: '创建时间',align: 'center', width: 45 },
							{ field: 'modifyDate', title: '修改时间',align: 'center', width: 45 },
							{ field: 'state', title: '状态', align: 'center',width: 25 ,formatter:function(value,row,index){if(value==0){return '<font color="#0098bd">未审批</font>';}else if(value==1){return '<font color="#53FF53;">已审批</font>';}else if(value==2){return '<font color="red">已驳回</font>';}else if(value==3){return '<font color="green">已二次审批</font>';}}},
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
					showSegment();
				}/*,
				detailFormatter: function(rowIndex, rowData){
					return '<table><tr>' +
							'<td rowspan=2 style="border:0"><img src="images/' + rowData.itemid + '.png" style="height:50px;"></td>' +
							'<td style="border:0">' +
							'<p>Attribute: ' + rowData.attr1 + '</p>' +
							'<p>Status: ' + rowData.status + '</p>' +
							'</td>' +
							'</tr></table>';
				}*/
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

function importData(){
	$('#dlg-xls').dialog('open').dialog('setTitle','导入来源');
	 //Msgshow('暂不支持');
}


function query(){
	var tagId = $('#tree').tree('getSelected') == null ? '' : $('#tree').tree('getSelected').id ;
//	alert(tagId);
//	return;
	var searchInsertName=$("#searchInsertName").val();
	var searchSource=$("#searchSource").val();
	var searchStatus=$("#searchStatus").val();
	var searchTitle=$("#searchTitle").val();
	// 知识等级
	var searchLevel = $("#searchLevel").val();
	// 发布日期
	var searchBegincreateDate = $("#searchBegincreateDate").val();
	grid.datagrid('clearSelections');
	grid.datagrid({ url: listUrl, queryParams: { insertName: searchInsertName,source:searchSource,state:searchStatus,title:searchTitle, tagId: tagId, level: searchLevel, begincreateDate: searchBegincreateDate} });
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

var tree;
var grid;
var searchWin;
var segmentWin;


function editPage() {
    var row = grid.datagrid('getSelected');
    var index=grid.datagrid('getRowIndex',row);
    if(row){
//    	location.href='segment/update/'+row.id;
    	window.open('segment/update/'+row.id + '/' + index, "_blank", "toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=yes, width=1024, height=800");
    	
//    	var index=grid.datagrid('getRowIndex',row);
//    	grid.datagrid('updateRow',{
//    		index: index,
//    		row: {
//    			title: "<font style='background:#FFC78E'>" + row.title + "</font>"
//    		}
//    	});
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
 
function auditSegment() {
	var row = $('#grid').datagrid('getSelected');
	if(row) {
		/*
		var searchInsertName=$("#searchInsertName").val() != '' ? searchInsertName : '%';
		var searchSource=$("#searchSource").val() != '' ? searchSource : '%';
		var searchStatus=$("#searchStatus").val();
		var searchTitle=$("#searchTitle").val() != '' ? searchTitle : '%';
		//alert(searchTitle);
		*/
		//location.href = '/segment/auditSegment/' + row.id + '/' + searchInsertName + '/' + searchSource + '/' + searchStatus + '/' + searchTitle;
		window.open('/segment/auditSegment/' + row.id, "_blank", "toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=yes, width=1024, height=800");
    	// 被编辑数据行标题着色
    	showSelectedRow(row);
	} else {
		$.messager.show({
			title: '提示：',
			msg: '请选择一条记录进行处理！'
		});
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
						"&infoType=" + "2" +
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

function refreshRow(index) {
	//alert(index);
	grid.datagrid('refreshRow', index);
}


function rowRefresh() {
	grid.pagination("refresh");
}
