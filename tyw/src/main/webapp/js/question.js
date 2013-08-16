var ansUrl='/answerlist';
function pageansUrl(pageSize,pageNo){
	return ansUrl+"?page.page="+pageNo+"&page.size="+pageSize;
}
function removeQuestion(){
	var row = $('#grid').datagrid('getSelected');
	if (row){
		$.messager.confirm('提示','您确定要删吗?',function(r){
			if (r){
				$.post('/question/delete/'+row.id,{id:row.id},function(result){
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

function addQuestion() {
	location.href = "/question/add" ;
}

function query(){
	var searchInsertName=$("#searchInsertName").val();
	var searchSource=$("#searchSource").val();
	var searchStatus=$("#searchStatus").val();
	var searchTitle=$("#searchTitle").val();
	var searchSynStatus = $('#synAttachment').val() ;
	var tagId = $('#tree').tree('getSelected') == null ? '' : $('#tree').tree('getSelected').id ;
	grid.datagrid('clearSelections');
	grid.datagrid({ url: listUrl, queryParams: { insertName: searchInsertName,source:searchSource,state:searchStatus,title:searchTitle, synStatus:searchSynStatus, tagId: tagId} });
}



//分页方法
function pageUrl(pageSize,pageNo){
	return listUrl+"?page.page="+pageNo+"&page.size="+pageSize;
}

var $parent = self.parent.$ ;

function showQuestion(){
	//$('#pptContainer').empty(); // 清空URL
	var row = $('#grid').datagrid('getSelected');
	if (row){
		$.get('/question/previewQuestion?c='+Math.random(), { id: row.id}, function(result) {
			if(result.success) {
				var ansHtml='';
				var f = result.question;
				var ansList = result.answerPageList;
				
				for(var j=0;j<ansList.length;j++){
					if(ansList[j] != null && ansList[j] != "") {
						ansHtml += (j+1)+"、"+ansList[j].content+"</br></br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" ;
					}
				}
				
				$parent("#questionContent").html("问题："+f.title+"</br></br></br>"+"答案："+ansHtml);
				$parent("#questionCreateDate").html("创建时间："+f.createDate);
				$parent("#questionInsertName").html(f.insertName==null?"录入人：(无)":"录入人："+f.insertName);
				$parent("#questionId").val(f.id);
				var tagNames=[];
				for(var i=0;i<f.tags.length;i++){
					tagNames.push(f.tags[i].name);
				}
				$parent("#questionTags").html("标签："+tagNames.join(","))
				questionWin.window('open');
			} else {
				$.messager.alert('错误：', result.msg) ;
			}
		}, 'json') ;
		
		
	}else {
        Msgshow('请先选择要查看的记录。');
    }
}



$(function () {
	backGround = $('#backGround_read').val();
	bar={};
	bar1=[{
		            text: '新增问题',
		            iconCls: 'icon-add',
		            handler: addQuestion
		        }, '-', {
		            text: '修改',
		            iconCls: 'icon-edit',
		            handler: editQuestion
		        }, '-', {
		            text: '删除',
		            iconCls: 'icon-remove',
		            handler: removeQuestion
		        }, '-', {
		            text: '预览',
		            iconCls: 'icon-tip',
		            handler: showQuestion
		        }, '-', {
		            text: '回答问题',
		            iconCls: 'icon-add',
		            handler: ansQuestion
		        }, '-', {
		            text: '审核',
		            iconCls: 'icon-ok',
		            handler: okOrBreak
		        }];
	
       
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
		        pageSize: 50 ,
		        columns: [[
							{ field: 'title', title: '问题内容', width: 70 },
							{ field: 'insertName', title: '录入人', align: 'center',width: 20 },
							{ field: 'createDate', title: '创建时间', align: 'center',width: 45 },
							{ field: 'state', title: '状态',align: 'center', width: 25 ,formatter:function(value,row,index){if(value==0){return '<font color="blue">未审批</font>';}else if(value==1){return '<font color="#53FF53;">已审批</font>';}}},
							
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
					showQuestion();
				}
			});
			
			questionWin=$parent('#question-window').window({
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

var listUrl='/questionlist';
var url;
var tree;
var grid;
var ansgrid;
var searchWin;
var questionWin;

function editQuestion() {
    var row = grid.datagrid('getSelected');
    if(row){
    	location.href='/question/edit/'+row.id;
    }else{
    	 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
        return;
    }
}


function ansQuestion(){ 
	var row = grid.datagrid('getSelected');
    if(row){
    	$('#fm').form('clear');
    	$('#dlg').dialog('open').dialog('setTitle','回答问题');
    	$('#quesId').val(row.id);
    	
    	ansgrid = $('#ansgrid').datagrid({
			url: ansUrl,
			loadMsg:'正在处理，请稍待。。。',
			title: '答案列表',
	        iconCls: 'icon-user',
	        methord: 'post',
	        title:false,
	        sortName: 'ID',
	        sortOrder: 'desc',
	        idField: 'ID',
	        pageSize: 10 ,
	        queryParams: { ansId:row.id},
	        columns: [[
						 {field:'id',title:'id',hidden:true},  
		        		 {field:'content',title:'答案',width:25},  
		        		 {field:'formatCreateDate', title: '回答时间', align: 'center',width: 35} 
		        		
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
				$(this).datagrid('options').url=pageansUrl(pageSize,pageNo);
			}
		});
    	
    	$('#ansgrid').datagrid('getPager').pagination({
    		displayMsg:'当前显示从{from}到{to}共{total}记录',
    		beforePageText:'当前第',
    		afterPageText:'页,共{pages}页',
    		onBeforeRefresh:function(pageNumber, pageSize){
     			$(this).pagination('loading');
     			$(this).pagination('loaded');
    	}});
    	
    }else{
    	 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
        return;
    }
}

function saveAns(){
	var ansContent = document.getElementById("content").value;
	if(ansContent.length==0){
		alert("请输入您的答案！");
		document.getElementById("content").focus();
		return false;
	}
	
	if(ansContent.length>1000){
		alert("答案的字数不能大于1000字！");
		document.getElementById("content").focus();
		return false;
	}
	
	$('#fm').form('submit',{
		url: '/saveAnswer',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(result){
			var result = eval('('+result+')');
			if (result.success){
				//$('#ansgrid').datagrid('reload');	
				$('#dlg').dialog('close');		
			} else {
				$.messager.show({
					title: '错误',
					msg: result.msg
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

function okOrBreak() {
	var row = grid.datagrid('getSelected');
    if(row){
		window.open('/question/audit/' + row.id, "_blank", "toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=yes, width=1024, height=800");
    }else{
    	 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
        return;
    }
}