var grid;
var listUrl='cardTypelist/data';

	    //删除记录
		function removeCardType(){
			var row = $('#grid').datagrid('getSelected');
			if (row){
				$.messager.confirm('提示','您确定要删除该记录?',function(r){
					if (r){
						$.post('cardType/delete/'+row.id,{id:row.id},function(result){
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
			}else{
				 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
				return;
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
	            text: '新增',
	            iconCls: 'icon-add',
	            handler: addCardType
	        }, '-', {
	            text: '删除',
	            iconCls: 'icon-remove',
	            handler: removeCardType
	        }, '-', {
	            text: '编辑',
	            iconCls: 'icon-edit',
	            handler: updateCardType
	        }, '-', {
	            text: '查看',
	            iconCls: 'icon-edit',
	            handler: viewCardType
	        }];
			var bar2=[{
	            text: '查看',
	            iconCls: 'icon-edit',
	            handler: viewCardType
	        }];
		       
			if(backGround=='1'){bar=bar2;}else{bar=bar1;};
			grid=$('#grid').datagrid({
				url: listUrl,
				loadMsg:'产品类型列表',
				 columns:[[  
			        {field:'id',title:'id',hidden:true},  
			        {field:'title',title:'标题',width:25},  
			        {field:'createName',title:'创建人',width:25}, 
			        {field:'createDate',title:'创建时间',width:20},
			        {field:'price',title:'单价',width:15},
			        {field:'status',title:'状态',width:15,
			        	formatter: function(value,row,index){
							if (value){
								return '可用';
							} else  {
								'禁用';
							}
						}
			        
			        },
					{field:'summary',title:'备注',width:20}
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
		
		function addCardType(){
			location.href='addCardType';
		}
        function updateCardType(){ 
		    var row = $('#grid').datagrid('getSelected');
			if (row){
			location.href='cardType/update/'+row.id;
			}else{
				 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
				return;
			}
		}
		function viewCardType(){ 
		    var row = $('#grid').datagrid('getSelected');
			//alert(row.id);
			if (row){
			location.href='cardType/view/'+row.id;
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
	var createName=$("#createName").val();
	var title=$("#title").val();
	grid.datagrid('clearSelections');
	grid.datagrid({ url: listUrl, queryParams: { title: title,createName:createName} });
   }