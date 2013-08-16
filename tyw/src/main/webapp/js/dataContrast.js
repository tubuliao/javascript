var listUrl='/dataContrast/list';
var grid;
 

function query(){
	var type = $("#searchType").val() == "" ? "1" : $("#searchType").val() ;
//	alert(type) ;
	grid.datagrid({ 
		url: listUrl, 
		queryParams: { searchType: type} 
	});
}


$(function () {
	grid=$('#grid').datagrid({
		url: listUrl,
		loadMsg:'正在处理，请稍待。。。',
		title: '数据比对统计',
		iconCls: 'icon-user',
		methord: 'post',
		columns: [[
					 {field:'id', title:'ID', width:25, hidden:true},
					 {field:'dataType',title:'数据类型',width:25, formatter: function(value, rowData, rowIndex) {
																	if(value == "msg01") {
																		return "切片_生产库未同步到应用库" ;
																	} else if(value == "msg02") {
																		return "切片_生产库已同步到应用库但已删除";
																	} else if(value == "msg03") {
																		return "表格_生产库未同步到应用库";
																	} else if(value == "msg04") {
																		return "表格_生产库已同步到应用库但已删除";
																	} else if(value == "msg05") {
																		return "文件_生产库未同步到应用库";
																	} else if(value == "msg06") {
																		return "文件_生产库已同步到应用库但已删除";
																	} else if(value == "msg07") {
																		return "图片_生产库未同步到应用库";
																	} else if(value == "msg08") {
																		return "图片_生产库已同步到应用库但已删除";
																	} else if(value == "msg09") {
																		return "视频_生产库未同步到应用库";
																	} else if(value == "msg10") {
																		return "视频_生产库已同步到应用库但已删除";
																	} else if(value == "msg11") {
																		return "PPT_生产库未同步到应用库";
																	} else if(value == "msg12") {
																		return "PPT_生产库已同步到应用库但已删除";
																	} else if(value == "msg13") {
																		return "切片_tywadb";
																	} else if(value == "msg14") {
																		return "表格_tywadb";
																	} else if(value == "msg15") {
																		return "文件_tywadb";
																	} else if(value == "msg16") {
																		return "图片_tywadb";
																	} else if(value == "msg17") {
																		return "视频_tywadb";
																	} else if(value == "msg18") {
																		return "PPT_tywadb";
																	} else if(value == "msg19") {
																		return "切片_tywdb";
																	} else if(value == "msg20") {
																		return "表格_tywdb";
																	} else if(value == "msg21") {
																		return "文件_tywdb";
																	} else if(value == "msg22") {
																		return "图片_tywdb";
																	} else if(value == "msg23") {
																		return "视频_tywdb";
																	} else if(value == "msg24") {
																		return "PPT_tywdb";
																	} else {
																		return value;
																	}
																}
					 },  
					 {field:'counts',title:'数量',width:50}  
				]],
		fit:true,
		pagination: false,
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,	
	    onDblClickCell: function(rowIndex, field, value) {
				
		}
	});
    		
    $('body').layout();
	
});

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


