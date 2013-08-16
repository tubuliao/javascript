var grid;
var listUrl='/licensePaymentList/data';

		 //删除记录
		function removePayment(){
			var row = $('#grid').datagrid('getSelected');
			if (row){
				$.messager.confirm('提示','您确定要删除该记录?',function(r){
					if (r){
						$.post('payment/delete/'+row.id,{id:row.id},function(result){
						   // alert(row.id);
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
			}
		}
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
	        }];
			var bar2=[/*{
	            text: '查看订单',
	            iconCls: 'icon-view',
	            handler: viewPayment
	        }*/];
		       
			if(backGround=='1'){bar=bar2;}else{bar=bar1;};
			grid=$('#grid').datagrid({
				url: listUrl,
				loadMsg:'订单列表',
				 loadMsg:'正在处理，请稍待。。。',
				 columns:[[  
			        {field:'id',title:'id',hidden:true},  
			        {field:'name',title:'渠道商',width:15},
			        {field:'batchCode',title:'批次号',width:10},
			        {field:'aliasname',title:'用户',width:30/*, formatter: function(value, rowData, rowIndex) { if(value != "" && value != null) { return $.ajax({url: '/license/userAliasname', dataType: "json", data: {userId: value}, async: false, cache: false, type:"get"}).responseText; } else { return ""; }}*/},  
			        {field:'payStatus',title:'交费状态',width:10,formatter: function(value, rowData, rowIndex) { if(value == "1") { return "已提交";} else if(value == "20") { return "支付成功";} else if(value == "30") { return "支付失败";} else if(value == null) {return "未支付";}}}, 
			        {field:'payAmount',title:'交费金额',width:10},
			        {field:'payDate',title:'交费时间',width:20},
			        {field:'vOid',title:'订单编号',width:50},
			        {field:'vPmode',title:'支付方式',width:20},
			        {field:'vPstring',title:'支付结果信息',width:15},
			        {field:'vMoneytype',title:'币种',width:10, formatter: function(value, rowData, rowIndex) {if(value == "0") { return "人民币";} else if(value == "1") { return "美元";}}}
			    ]], 
			    fit:true,
		        pagination: true,
		        rownumbers: true,
		        fitColumns: true,
		        singleSelect: true,
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
	// 订单编号
	var searchVOid = $("#searchVOid").val();
	// 交费状态
	var searchPayStatus = $("#searchPayStatus").val();
	// 渠道商
//	var searchAgentName = $("#searchAgentName").val();
	var searchAgentId = $("#searchAgentName").val();
	// 批次号
	var searchBatchCode = $("#searchBatchCode").val();
	grid.datagrid({ 
		url: listUrl, 
		queryParams: { 
				vOid: searchVOid,
				payStatus: searchPayStatus,
				sAgentId: searchAgentId,
				sBatchCode: searchBatchCode
			} 
	});
}

function exportExcel() {
	// 订单编号
	var searchVOid = $("#searchVOid").val();
	// 交费状态
	var searchPayStatus = $("#searchPayStatus").val();
	// 渠道商
	var searchAgentId = $("#searchAgentName").val();
	// 批次号
	var searchBatchCode = $("#searchBatchCode").val();
	
	window.location.href = "/license/export?vOid=" + searchVOid + 
							"&payStatus=" + searchPayStatus + 
							"&sAgentId=" + searchAgentId + 
							"&sBatchCode=" + searchBatchCode;
}