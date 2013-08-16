
var listUrl = '/allList';
var grid;
var tree;
var segmentWin;
// 批量选中的id
//var idStr = '';
 	
$(function () {
	bar=[{
		   text: '批量标签',
		   iconCls: 'icon-ok',
		   handler: doBatch
	   }, '-', {
	       text: '预览切片',
	       iconCls: 'icon-tip',
	       handler: showSegment
	   }, '-', {
		   text: '批量来源',
		   iconCls: 'icon-edit',
		   handler: editSource
	   }, '-', {
		   text: '批量申请删除',
		   iconCls: 'icon-edit',
		   handler: applyDetele
	   }, '-', {
		   text: '批量取消申请删除',
		   iconCls: 'icon-edit',
		   handler: applyCancelDetele
	   }, '-', {
		   text: '当页全选',
		   iconCls: 'icon-add',
		   handler: seleceAll
	   }, '-', {
		   text: '取消选择',
		   iconCls: 'icon-remove',
		   handler: unselectAll
	   }];
    
    
    var backGround_bat_delete = $('#backGround_bat_delete').val();
    if(backGround_bat_delete=='1'){
		if(bar.length!=0){
			bar.push('-');
		}
        bar.push({
     	   text: '批量同意删除',
    	   iconCls: 'icon-remove',
    	   handler: agreeDetele
        		});
	}

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
    
	grid=$('#gridcenter').datagrid({
				url: listUrl,
				loadMsg:'正在处理，请稍待。。。',
				title: '知识列表',
		        iconCls: 'icon-table',
		        methord: 'post',
		        sortName: 'id',
		        sortOrder: 'desc',
		        idField: 'id',
		        pageSize: 50 ,
				pageList: [10, 50, 100, 150],
		        columns: [[
		                    { field: 'id', title:'ID', align: 'center', hidden:true},
		                    { field: 'infoType', title:'类型', align: 'center', width: 20, formatter: function(value, row, index) { if(value == 1) { return '文件'; } else if(value == 2) { return '切片'; } else if(value == 3) { return '视频'; } else if(value == 4) { return '表格' ;} else if(value == 5) { return '图片'; }else if(value == 6) { return 'PPT'; }  }},
							{ field: 'title', title: '标题',width: 70 },
							{ field: 'source', title: '来源', width: 100},
							{ field: 'createDate', title: '创建时间',align: 'center', width: 45 },
							{ field: 'insertName', title: '录入人', align: 'center',width: 20 },
							{ field: 'modifyDate', title: '修改时间',align: 'center', width: 45 },
							{ field: 'modifyName', title: '修改人', align: 'center',width: 20, formatter:function(value, row, index) { if(value == '' || value == null) { return '无'; } else { return value; } } },
							{ field: 'state', title: '状态',align: 'center', width: 25 ,formatter:function(value,row,index){if(value==0){return '<font color="blue">未审批</font>';}else if(value==1){return '<font color="#53FF53;">已审批</font>';}else if(value==2){return '<font color="red">已驳回</font>';}else if(value==3){return '<font color="green">已二次审批</font>';}}},
							{ field: 'applyDetele', title: '申请删除',align: 'center', width: 25,formatter:function(value,row,index){if(value==1){return '<font color="red">已申请</font>';}else if(value==0){return '<font color="blue">未申请</font>';}}}
						]],
		        fit:true,
		        pagination: true,
		        rownumbers: true,
		        fitColumns: true,
		        checkOnSelect:true,
//		        singleSelect: true,
		        toolbar: bar,
				onBeforeLoad: function(param){
					var pageNo=$(this).datagrid('getPager').pagination('options').pageNumber;
					var pageSize=$(this).datagrid('getPager').pagination('options').pageSize;
					$(this).datagrid('options').url=pageUrl(pageSize,pageNo);
				},
				onDblClickRow:function(rowIndex, rowData){
//					showSegment();
				}
			});
			
			
			$('#gridcenter').datagrid('getPager').pagination({
	    		displayMsg:'当前显示从{from}到{to}共{total}记录',
	    		beforePageText:'当前第',
	    		afterPageText:'页,共{pages}页',
	    		onBeforeRefresh:function(pageNumber, pageSize){
	     			$(this).pagination('loading');
	     			$(this).pagination('loaded');
	    		}});
			
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
	
    $('body').layout();
});

//分页方法
function pageUrl(pageSize,pageNo){
	return listUrl+"?page.page="+pageNo+"&page.size="+pageSize;
}

function query() {
	var searchInfoType = $('select option:selected').val();
	var searchInsertName = $('#serachInsertName').val();
	var searchDateFrom = $('#searchCreateDateFrom').val();
	var searchDateTo = $('#searchCreateDateTo').val();
	var serachTitle = $('#serachTitle').val();
	var searchState = $("#searchState").val();
	var serachApplyDetele = $("#serachApplyDetele").val();
	//alert(searchInfoType + ' ** ' + searchInsertName + ' ** ' + searchDateFrom + ' ** ' + searchDateTo);
	var tagId = $('#tree').tree('getSelected') == null ? '' : $('#tree').tree('getSelected').id ;
	// 标题重复
	var searchRepeat = ($("#titleRepeat").attr("checked") == undefined ? "0" : "1");
	//来源
	var serachSource = $("#serachSource").val();
	
	grid.datagrid('clearSelections');
	grid.datagrid({
		url: listUrl,
		queryParams: {
			infoType: searchInfoType,
			insertName: searchInsertName,
			dateFrom: searchDateFrom,
			dateTo: searchDateTo,
			tagId: tagId,
			title: serachTitle,
			state: searchState,
			applyDetele:serachApplyDetele,
			titleRepeat: searchRepeat,
			source: serachSource
		}
	});
}

function doBatch() {
	var idStr = '';
	var rowArr = $('#gridcenter').datagrid('getSelections');
	for(var i = 0; i < rowArr.length; i++) {
		idStr += rowArr[i].id + '&';
	}
	grid.datagrid('clearSelections');
	if(idStr != '') {
		window.open('/showTags/' + idStr, "_blank", "toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=yes, width=1024, height=800");
	} else {
		$.messager.show({
			title: '提示：',
			msg: '请选择相应的记录进行操作！'
		});
	}
}

function applyDetele() {
	var idStr = '';
	var rowArr = $('#gridcenter').datagrid('getSelections');
	for(var i = 0; i < rowArr.length; i++) {
		idStr += rowArr[i].id + '&';
	}
	grid.datagrid('clearSelections');
	if(idStr != '') {
		$.messager.confirm('提示','您确定要申请删除这些知识?',function(r){
			if (r){
				$.post('/applyDetele/'+idStr,{},function(result){
					if (result.success){
						query();
					} else {
						$.messager.show({	// 显示错误信息
							title: '错误',
							msg: result.msg
						});
					}
				},'json');
			}
		});
	} else {
		$.messager.show({
			title: '提示：',
			msg: '请选择相应的记录进行操作！'
		});
	}
}

function applyCancelDetele() {
	var idStr = '';
	var rowArr = $('#gridcenter').datagrid('getSelections');
	for(var i = 0; i < rowArr.length; i++) {
		idStr += rowArr[i].id + '&';
	}
	grid.datagrid('clearSelections');
	if(idStr != '') {
		$.messager.confirm('提示','您确定要取消申请删除这些知识?',function(r){
			if (r){
				$.post('/applyCancelDetele/'+idStr,{},function(result){
					if (result.success){
						query();
					} else {
						$.messager.show({	// 显示错误信息
							title: '错误',
							msg: result.msg
						});
					}
				},'json');
			}
		});
	} else {
		$.messager.show({
			title: '提示：',
			msg: '请选择相应的记录进行操作！'
		});
	}
}

function agreeDetele() {
	var idStr = '';
	var rowArr = $('#gridcenter').datagrid('getSelections');
	for(var i = 0; i < rowArr.length; i++) {
		idStr += rowArr[i].id + '&';
	}
	grid.datagrid('clearSelections');
	if(idStr != '') {
		$.messager.confirm('提示','您确定要同意删除这些知识?',function(r){
			if (r){
				$.post('/agreeDetele/'+idStr,{},function(result){
					if (result.success){
						query();
					} else {
						$.messager.show({	// 显示错误信息
							title: '错误',
							msg: result.msg
						});
					}
				},'json');
			}
		});
	} else {
		$.messager.show({
			title: '提示：',
			msg: '请选择相应的记录进行操作！'
		});
	}
}

function delTag(baseId, tagId, spanId) {
	$.messager.confirm('提示：', '确定删除该标签？', function(r) {
		if(r) {
			//alert('ok');
			$.get('/approval/delTag', {baseId: baseId, tagId: tagId}, function(re) {
				if(re.success) {
					// tagId 会有相同的
					$('#' + spanId).remove(); 
				} else if(re.fail) {
					$.messager.alert('错误：', re.msg, 'error');
				}
			}, 'json');
		} else {
			//alert('no');
		}
	})
}

function chooseTag() {
	$('#tag-window').window('open');
	initTags();
}

var appendObjs=new Array();
var tagIdArr = new Array();
var idx = 0;
var flag;

function delTagId(tagId) {
	//alert('ss');
	for(var i = 0; i < tagIdArr.length; i++) {
		if(tagIdArr[i] == tagId) {
			flag = i ;
			break;
		}
	}
	delete tagIdArr[flag];
	//alert(tagIdArr);
}

function saveBaseAndTag() {
	var baseIdStr = $('#baseId').val();
	var tagIdStr = tagIdArr.join('&');
	//alert(tagIdStr);
	if(tagIdStr != '') {
		$.messager.progress({
			title : '提示：' ,
			text : '批量更新中……' ,
			interval : 500
		});
		$.post('/approval/doSave', {baseIdStr: baseIdStr, tagIdStr: tagIdStr}, function(r) {
			if(r.success) {
				$('#tag-window').window('close');
				//window.close();
				$.messager.alert('提示：', '批量标签成功！', 'info', function() {
					window.close();
					window.open('/showTags/' + baseIdStr, "_blank", "toolbar=no, location=yes, directories=no, status=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=yes, width=1024, height=800");
				})
			} else {
				$('#tag-window').window('close');
				$.messager.show({
					title: '错误：',
					msg: r.msg
				})
			}
		}, 'json')
		.complete(function() { $.messager.progress('close'); });;
	} else {
		$('#tag-window').window('close');
	}
}

function initTags(){
	 $.post("/combotree/tag/0",{},function(data){
		// var rows=$('#tagList').datagrid('getRows');  //获取已选标签列表
		$("#formContent").empty(); //清空之前内容
		$("#parentId").val("0");
		for(var i=0;i<data.length;i++){
			var obj={type:"checkbox",val:data[i].id,id:data[i].id,name:data[i].id,checked:false}
//			for(var j=0;j<rows.length;j++){
//				if(rows[j].id==data[i].id){
//					obj.checked=true;
//					break;
//				}
//			}
			var html="<input>";
			$(html,obj).appendTo("#formContent");
			var s="input[name='"+data[i].id+"']";
			$(s).data("tag",data[i]);
			$("<label for='"+data[i].id+"'>"+data[i].name+"</label>&nbsp;<a href='javascript:void(0)' id='"+data[i].id+"'>(NEXT)</a>").insertAfter(s);
		}
		
		//为生成的checkbox绑定单击事件
		$("input:checkbox","#formContent").on("click",{id:10}, function(event){
			if($(this).attr("checked")){
				//insertRow($(this).data("tag);
				tagIdArr.push($(this).data("tag").id);
				//alert(tagIdArr);
			}else{
				//deletetRow($(this).data("tag"));
				delTagId($(this).data("tag").id);
			}
		});
		
		//为超链接绑定事件
		$("#formContent > a").on("click", function(){
			var id=$(this).attr("id");
			$("#parentId").val(id);
			upData(id,data);
			var s="input[name='"+id+"']";
			queryTags($(s).data("tag"));
		});
		
		upData("0",data);
	 },"json");
}


/***************************************/

//插入行
//function insertRow(obj){
//	var rows=$('#tagList').datagrid('getRows');
//	var isHas=false;
//	for(var i=0;i<rows.length;i++){
//		if(rows[i].id==obj.id){
//			isHas=true;
//		}
//	}
//	if(!isHas){
//		$('#tagList').datagrid('appendRow',{id:obj.id,name:obj.name,manager:"<a href='javascript:void(0)' id='"+obj.id+"' onclick='deletetRowById(this.id)'>删除</a>"});
//	}
//}

//删除行根据对象
//function deletetRow(obj){
//	var rows=$('#tagList').datagrid('getRows');
//	var index=0;
//	for(var i=0;i<rows.length;i++){
//		if(rows[i].id==obj.id){
//			index=i;
//		}
//	}
//	$('#tagList').datagrid('deleteRow',index);
//}

//删除行根据ID
//function deletetRowById(id){
//	$.get('/tag/delAreaTag', { tagId: id}, function(r) {
//		if(r.success) {	// 删除地区标签时弹出提示
//			$.messager.confirm('提示：', '确定删除此地区标签？', function(v) {
//				if(v) {
//					var rows=$('#tagList').datagrid('getRows');
//					var index=0;
//					for(var i=0;i<rows.length;i++){
//						if(rows[i].id==id){
//							index=i;
//						}
//					}
//					$('#tagList').datagrid('deleteRow',index);
//				} else {
//					return ;
//				}
//			}) ;
//		} else {
//			var rows=$('#tagList').datagrid('getRows');
//			var index=0;
//			for(var i=0;i<rows.length;i++){
//				if(rows[i].id==id){
//					index=i;
//				}
//			}
//			$('#tagList').datagrid('deleteRow',index);
//		}
//	}, 'json') ;
//}

function queryTags(obj){
	var prientId=obj.id;
	appendPath(obj);
	$.post("/combotree/tag/"+obj.id,{},function(data){
		if(data.length==0){
			alert("无下级标签");
		}else{
			$("#parentId").val(prientId);
			$("#formContent").empty(); //清空之前内容
			//var rows=$('#tagList').datagrid('getRows');  //获取已选标签列表
			for(var i=0;i<data.length;i++){
	      		var obj={type:"checkbox",val:data[i].id,id:data[i].id,name:data[i].id,checked:false}
	      		for(var j=0;j<tagIdArr.length;j++){
	      			if(tagIdArr[j]==data[i].id){
	      				obj.checked=true;
	      				break;
	      			}
	      		}
	      		var html="<input>";
	      		$(html,obj).appendTo("#formContent");
	      		var s="input[name='"+data[i].id+"']";
	      		$(s).data("tag",data[i]);
	      		$("<label for='"+data[i].id+"'>"+data[i].name+"</label>&nbsp;<a href='javascript:void(0)' id='"+data[i].id+"'>(NEXT)</a>").insertAfter(s);
  		}
  		//为生成的checkbox绑定单击事件
      	$("input:checkbox","#formContent").on("click",{id:10}, function(event){
      		var myObj= $(this);
      		if($(this).attr("checked")){
      			//insertRow($(this).data("tag"));
      			if($(this).data("tag").code.toString().indexOf("2")==0){	// 修改地区标签
      			 	$.messager.confirm('提示：', '确定修改此地区标签？', function(v) {
							if(v) {	
								tagIdArr.push(myObj.data("tag").id);
								//alert(tagIdArr);
							}else{
							   myObj.attr("checked",false) ;
							}
						});
      			} else {
      				tagIdArr.push(myObj.data("tag").id);
    				//alert(tagIdArr);
      			}
      		}else{
      			if($(this).data("tag").code.toString().indexOf("2")==0){	// 修改地区标签
      			 	$.messager.confirm('提示：', '确定修改此地区标签？', function(v) {
							if(v) {	
//								deletetRow(myObj.data("tag"));
								delTagId(myObj.data("tag").id);
							}else{
							   myObj.attr("checked",true) ;
							}
						});
      			} else {
//      				deletetRow(myObj.data("tag"));
      				delTagId(myObj.data("tag").id);
      			}
      		}
      	});
      	//为超链接绑定事件
      	$("#formContent > a").on("click", function(){
	  		var id=$(this).attr("id");
	  		$("#parentId").val(id);
	  		upData(id,data);
			var s="input[name='"+id+"']";
			queryTags($(s).data("tag"));
		});
	}
  },"json");
}

//添加路径导航
function appendPath(obj){
	
	$("#formTitle").empty(); //清空所有元素
	//如果是第一次加载,同时加载根标签
	if(appendObjs.length==0&&obj.id!=0){
		var rootObj={id:0,name:'根标签'};
	 
		appendObjs.push(rootObj);
		appendObjs.push(obj);
	}else{
	 
		var isHas=false;
		for(var i=0;i<appendObjs.length;i++){
		 
			if(appendObjs[i].id==obj.id){
				isHas=true;
			}
		}
		if(!isHas){
			appendObjs.push(obj);
		}
	}
	
	
	for(var key in appendObjs){
		$("#formTitle").append("<a id='"+appendObjs[key].id+"' href='javascript:void(0)'>"+appendObjs[key].name+"</a>").append("<br>");
	}
	
	
	//为超链接绑定事件
	$("#formTitle > a").bind("click", function(){
		var id=$(this).attr("id");
		
		for(var i=0;i<appendObjs.length;i++){
			if(appendObjs[i].id==id){
				tag=appendObjs[i];
				break;
			}
		}
		queryTags(tag);
	});
}
//缓存
function upData(keyData,valData)
{ 
	$("div").data(keyData, valData);  
}


//取历史缓存
function getHisMsg(){
	var copyDataId=$("#parentId").val();
	data=$("div").data(copyDataId);
//	var rows=$('#tagList').datagrid('getRows');  //获取已选标签列表
  	$("#formContent").empty(); //清空之前内容
   	for(var i=0;i<data.length;i++){
  		var obj={type:"checkbox",val:data[i].id,id:data[i].id,name:data[i].id,checked:false}
	  		for(var j=0;j<tagIdArr.length;j++){
	  			if(tagIdArr[j]==data[i].id){
	  				obj.checked=true;
	  				break;
	  			}
	  		}
  		var html="<input>";
  		$(html,obj).appendTo("#formContent");
  		var s="input[name='"+data[i].id+"']";
  		$(s).data("tag",data[i]);
  		$("<label for='"+data[i].id+"'>"+data[i].name+"</label>&nbsp;<a href='javascript:void(0)' id='"+data[i].id+"'>(NEXT)</a>").insertAfter(s);
  		if(data[i].level=="1"){
  			$("#parentId").val("0");
  		}else{
  			$("#parentId").val(data[i].parentId);
  		}
  	}
  	
  	//为生成的checkbox绑定单击事件
  	$("input:checkbox","#formContent").on("click",{id:10}, function(event){
  		if($(this).attr("checked")){
//  			insertRow($(this).data("tag"));
  			tagIdArr.push($(this).data("tag").id);
			//alert(tagIdArr);
  		}else{
//  			deletetRow($(this).data("tag"));
  			delTagId($(this).data("tag").id);
  		}
  	});
  	//为超链接绑定事件
  	$("#formContent > a").on("click", function(){
		var id=$(this).attr("id");
		$("#parentId").val(id);
		upData(id,data);
		var s="input[name='"+id+"']";
		queryTags($(s).data("tag"));
	});
}

//提交表单前调用
//function setTagIds(){
//	var tagList=$('#tagList').datagrid('getRows');
//	var ids=[];
//	for(var key in tagList){
//		ids.push(tagList[key].id);
//	}
//	$('#tags').val(ids.join(",")); 
//}

// 批量审批通过
function approvalAudit() {
	// 需要批量审批通过的记录的id串，通过“&”分隔
	var baseIdStr = $('#baseId').val();
	$.messager.confirm("提示：", "确认审批通过所有选中的记录吗？", function(v) {
		if(v) {
			$.messager.progress({
				title : '提示：' ,
				text : '批量审批中……' ,
				interval : 200
			});
			$.get('/approval/audit', { idStr: baseIdStr}, function(r) {
				if(r.success) {
					window.close();
					$('#gridcenter').datagrid('reload');	// 重新加载数据
				} else if(r.fail) {
					$.messager.show({
						title: '错误：',
						msg: r.msg
					})
				}
			}, 'json')
			.complete(function() { $.messager.progress('close'); $.messager.alert('提示：', '批量审批成功！'); });
		} else {
			
		}
	});
}


var $parent = self.parent.$

function showSegment(){
	var row = $('#gridcenter').datagrid('getSelected');
//	grid.datagrid('clearSelections');
	if (row){
		if(row.infoType == 2) {
//			alert(row.title);
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
		} else {
			$.messager.show({
	        	title: '提示',
	        	msg: '只能预览切片！'
	        });
		}
	}else {
        $.messager.show({
        	title: '提示',
        	msg: '请先选择一条切片记录查看！'
        });
    }
}

var idString = "";

function editSource() {
	var idStr = '';
	var rowArr = $('#gridcenter').datagrid('getSelections');
//	alert( rowArr.length);
//	return;
	for(var i = 0; i < rowArr.length; i++) {
		idStr += rowArr[i].id + '&';
	}
	idString = idStr;
	grid.datagrid('clearSelections');
	//alert(idStr);
	if(idStr != '') {
		$("#newSource").val("");
		$("#source_dialog").dialog("open").dialog("setTitle", "批量来源");
	} else {
		$.messager.show({
			title: '提示：',
			msg: '请选择相应的记录进行操作！'
		});
	}
}

function saveNewSource() {
	var newSource = $("#newSource").val();
	$.messager.confirm("提示：", "确定批量修改数据来源？", function(r) {
		if(r) {
			$("#source_dialog").dialog("close");
			$.messager.progress({
				title : '提示：' ,
				text : '批量更新中……' ,
				interval : 500
			});
			$.post("/approval/source", { idStr: idString, source: newSource}, function(result) {
				if(result.success) {
					query();
					$.messager.alert("提示：", "批量更新成功！", "info");
				} else if(result.fail) {
					$.messager.alert("错误：", result.msg, "error");
				}
			}, "json")
			.complete(function() { $.messager.progress('close'); });
		} 
	})
}

function seleceAll() {
	$('#gridcenter').datagrid('selectAll');
}

function unselectAll() {
	$('#gridcenter').datagrid('unselectAll');
}