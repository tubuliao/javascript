var grid;
var listUrl='/receipt/data';

		//分页方法
		function pageUrl(pageSize,pageNo){
			return listUrl+"?page.page="+pageNo+"&page.size="+pageSize;
		}
		
		$(function(){
			backGround = $('#backGround_read').val();
			bar={};
			bar1=[{
	            text: '导出Excel',
	            iconCls: 'icon-redo',
	            handler: exportExcel
	        }, '-', {
	        	text: '批量状态',
	        	iconCls: 'icon-edit',
	        	handler: updateStatus
	        }];
			var bar2=[/*{
	            text: '查看订单',
	            iconCls: 'icon-view',
	            handler: viewPayment
	        }*/];
		       
			if(backGround=='1'){bar=bar2;}else{bar=bar1;};
			grid=$('#grid').datagrid({
				url: listUrl,
				loadMsg:'发票列表',
				 loadMsg:'正在处理，请稍待。。。',
				 columns:[[  
			        {field:'id',title:'id',hidden:true},  
			        {field:'agentName',title:'渠道商',width:15},
			        {field:'batchCode',title:'批次号',width:10},
			        {field:'receiptTitle',title:'发票抬头',width:35},
			        {field:'receiptAddress',title:'发票地址',width:35},
			        {field:'receiptAmount',title:'发票金额',width:10},
			        {field:'vMoneytype',title:'币种',width:10, formatter: function(value, rowData, rowIndex) {if(value == "0") { return "人民币";} else if(value == "1") { return "美元";}}},
			        {field:'receiptPerson',title:'发票人',width:15},
			        {field:'postCode',title:'邮编',width:15},
			        {field:'receiptPhone',title:'联系电话',width:15},
			        {field:'licenseNumber',title:'关联序列号',width:25},
			        {field:'status',title:'发票状态',width:15, align: 'center', formatter:function(value, rowData, rowIndex) {if(value == 0) { return "<font color='red'>未开票</font>";} else if(value == 1) { return "<font color='green'>已开票</font>";}}},
			    ]], 
			    fit:true,
		        pagination: true,
		        rownumbers: true,
		        fitColumns: true,
		        singleSelect: false,
		        toolbar: bar,
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
	    		
	    	$('#mm').menu({  
		    	onClick:function(item){  
       	 			newTag();
    			}  
			});	
		});
		

		function viewPayment(){ 
		    var row = $('#grid').datagrid('getSelected');
			if (row){
			location.href='payment/info/'+row.id;
			}else{
				 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
				return;
			}
		}

		
		function Msgslide(msg) {
			$.messager.show({
				title: '提示',
				msg: msg,
				timeout: 3000,
				showType: 'slide'
		});
}
		
function query(){
	// 序列号
	var searchLicenseNumber = $("#searchLicenseNumber").val();
	// 发票状态
	var searchStatus = $("#searchStatus").val();
	// 渠道商
	var searchAgentId = $("#searchAgentName").val();
	// 批次号
	var searchBatchCode = $("#searchBatchCode").val();
	grid.datagrid({ 
		url: listUrl, 
		queryParams: { 
				licenseNumber: searchLicenseNumber,
				status: searchStatus,
				sAgentId: searchAgentId,
				sBatchCode: searchBatchCode
			} 
	});
}

function exportExcel() {
	// 序列号
	var searchLicenseNumber = $("#searchLicenseNumber").val();
	// 发票状态
	var searchStatus = $("#searchStatus").val();
	// 渠道商
	var searchAgentId = $("#searchAgentName").val();
	// 批次号
	var searchBatchCode = $("#searchBatchCode").val();
	
	window.location.href = "/receipt/export?licenseNumber=" + searchLicenseNumber + 
							"&status=" + searchStatus + 
							"&sAgentId=" + searchAgentId + 
							"&sBatchCode=" + searchBatchCode;
}

var idString = "";
function updateStatus() {
	var idStr = '';
	var rowArr = $('#grid').datagrid('getSelections');
	//alert(rowArr.length);
	for(var i = 0; i < rowArr.length; i++) {
		idStr += rowArr[i].id + '&';
	}
	idString = idStr;
	grid.datagrid('clearSelections');
	//alert(idStr);
	if(idStr != '') {
		$("#status_dialog").dialog("open").dialog("setTitle", "批量状态");
	} else {
		$.messager.show({
			title: '提示：',
			msg: '请选择相应的记录进行操作！'
		});
	}
}

function saveNewStatus() {
	var newStatus = $("#newStatus").val();
	$.messager.confirm("提示：","确认批量更新发票状态？",function(r) {
		if(r) {
			$("#status_dialog").dialog("close");
			$.messager.progress({
				title : '提示：' ,
				text : '批量更新中……' ,
				interval : 500
			});
			$.post("/regeict/updateStatus", { status: newStatus, idString: idString}, function(result) {
				if(result.success) {
					$.messager.alert("提示：", "批量更新状态成功！", "info", function() {
						this.query();
					});
				} else if(result.fail) {
					$.messager.alert("错误：", result.msg, "error");
				}
			}, "json")
			.complete(function() { $.messager.progress('close'); });
		}
	});
}