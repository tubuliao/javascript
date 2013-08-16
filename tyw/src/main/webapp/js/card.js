var grid;
var listUrl='cardlist/data';

		
		//分页方法
		function pageUrl(pageSize,pageNo){
			return listUrl+"?page.page="+pageNo+"&page.size="+pageSize;
		}
		

		
		$(function(){
			grid=$('#grid').datagrid({
				url: listUrl,
				loadMsg:'天佑卡列表',
				 loadMsg:'正在处理，请稍待。。。',
				 columns:[[  
			        {field:'id',title:'id',hidden:true},  
			        {field:'cardNo',title:'卡号',width:25},  
			        {field:'authsUserId',title:'持有者',width:25}, 
			        {field:'createDate',title:'开卡时间',width:20},
			        {field:'createId',title:'开卡人',width:20},
			        {field:'price',title:'金额',width:20},
			        {field:'discountCode',title:'优惠编号',width:20},
			        {field:'batchCode',title:'批次号',width:20}
			    ]],  
			    fit:true,
		        pagination: true,
		        rownumbers: true,
		        fitColumns: true,
		        singleSelect: true,
		        toolbar: [{
		            
		            text: '查看',
		            iconCls: 'icon-edit',
		            handler: viewCard
		        }],
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
		

		function viewCard(){ 
		     var row = grid.datagrid('getSelected');
			if(row){
				$('#dlg_view').dialog('open').dialog('setTitle','查看卡信息');
				$('#fm1').form('load',row);
				
				
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
	var cardNo=$("#cardNo").val();
	var discountCode=$("#discountCode").val();
	grid.datagrid('clearSelections');
	grid.datagrid({ url: listUrl, queryParams: { cardNo: cardNo,discountCode:discountCode} });
   }