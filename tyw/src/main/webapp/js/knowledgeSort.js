var listUrl='/knowledgeSort/data';
var grid;
 

function query(v){
	//alert(v);
	var knowledgeSort = $('select option:selected').val() ;
	var id = v; 
	//alert(knowledgeSort) ;
	grid.datagrid({ url: listUrl, queryParams: { sort: knowledgeSort, tagId: id} });
}


$(function () {
	grid=$('#grid').datagrid({
		url: listUrl,
		loadMsg:'正在处理，请稍待。。。',
		title: '知识分类统计' + ' 》<a href="javascript:void(0);" onclick="navigate01(\'分部分项\')">分部分项</a>',
		iconCls: 'icon-user',
		methord: 'post',
		columns: [[
					 {field:'id', title:'ID', width:25, hidden:true},
					 {field:'name',title:'知识分类',width:25},  
					 {field:'count',title:'数量',width:50}  
				]],
		fit:true,
		pagination: false,
		rownumbers: true,
		fitColumns: true,
		singleSelect: true,	
	    onDblClickCell: function(rowIndex, field, value) {
				showNextLevel(rowIndex, field, value) ;
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

/* 双击列表中的记录 */
function showNextLevel(rowIndex, field, value) {
	// 原来的title
	var gridTitle = $('#grid').datagrid('options').title;	
	//alert('title:' + gridTitle) ;

	var tagId = $('#grid').datagrid('getSelected').id ;
	var tagName = $('#grid').datagrid('getSelected').name;
	//alert(tagName);

	// 新的title
	$('#grid').datagrid('options').title = gridTitle + " 》" + '<a href="javascript:void(0);" onclick="navigate02(\''+tagId+'\', \''+tagName+'\');" >' + tagName + '</a>' ;
		
	// 查询
	query(tagId) ;
}

/* 修改查询选项中的知识分类选项 */
function changeTitle() {
	var t02 = $('select option:selected').text() ;
	//alert(t) ;
	$('#grid').datagrid('options').title = '知识分类统计' + ' 》<a href="javascript:void(0);" onclick="navigate01(\''+t02+'\')">'+t02+'</a>' ;

}

// 点击按钮查询
function navigate01(v) {
	var originalTitle01 = $('#grid').datagrid('options').title;
	var newTitle01 = originalTitle01.substring(0, originalTitle01.lastIndexOf(v) + v.length + 4);
	//alert('newTitle01:' + newTitle01 );
	$('#grid').datagrid('options').title = newTitle01;
	query() ;
}

// 双击查询
function navigate02(v1, v2) {
	//alert('v1:' + v1 + '  v2:' + v2) ;
	// 获取原标题
	var originalTitle02 = $('#grid').datagrid('options').title;
	// 从获取的原标题中截取所需要的部分
	var newTitle02 = originalTitle02.substring(0, originalTitle02.lastIndexOf(v2) + v2.length + 4);
	$('#grid').datagrid('options').title = newTitle02;
	query(v1);
}