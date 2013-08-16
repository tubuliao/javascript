var grid;
var listUrl='paymentlist/data';

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
	            
	            text: '删除订单',
	            iconCls: 'icon-remove',
	            handler: removePayment
	        }, '-', {
	            text: '查看订单',
	            iconCls: 'icon-view',
	            handler: viewPayment
	        }];
			var bar2=[{
	            text: '查看订单',
	            iconCls: 'icon-view',
	            handler: viewPayment
	        }];
		       
			if(backGround=='1'){bar=bar2;}else{bar=bar1;};
			grid=$('#grid').datagrid({
				url: listUrl,
				loadMsg:'订单列表',
				 loadMsg:'正在处理，请稍待。。。',
				 columns:[[  
			        {field:'id',title:'id',hidden:true},  
			        {field:'name',title:'用户名',width:25},  
			        {field:'orderCode',title:'订单编号',width:25}, 
			        {field:'discountCode',title:'优惠编号',width:20},
			        {field:'createdAt',title:'下单时间',width:20},
			        {field:'payTime',title:'缴费时间',width:20},
			        {field:'payMoney',title:'缴费金额',width:20},
			        {field:'billHead',title:'发票抬头',width:20},
			        {field:'payWay',title:'支付方式',width:15,
			        	formatter: function(value,row,index){
							if (value){
								return '是';
							} else  {
								'否';
							}
						}
			        }
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
	var orderCode=$("#orderCode").val();
	var discountCode=$("#discountCode").val();

	grid.datagrid({ url: listUrl, queryParams: { orderCode: orderCode,discountCode:discountCode} });
   }