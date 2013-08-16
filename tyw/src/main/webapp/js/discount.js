var grid;
var listUrl='discountlist/data';
var url;

	    //删除用户
		function removeDiscount(){
			var row = $('#grid').datagrid('getSelected');
			if (row){
				$.messager.confirm('提示','您确定要删除该记录?',function(r){
					if (r){
						$.post('discount/delete/'+row.id,{id:row.id},function(result){
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
			grid=$('#grid').datagrid({
				url: listUrl,
				loadMsg:'正在处理，请稍待。。。',
				 columns:[[  
			        {field:'id',title:'id',hidden:true},  
			        {field:'discountName',title:'通道名称',width:25},  
			        {field:'discountCode',title:'优惠编号',width:25}, 
			        {field:'discountValue',title:'优惠折扣',width:20},
			        {field:'remark',title:'优惠说明',width:15},
			        {field:'createName',title:'创建人',width:15},
					{field:'createDate',title:'创建时间',width:20},
					{field:'validStartDate',title:'有效期始',width:20}
			    ]], 
				fit:true,
		        pagination: true,
		        rownumbers: true,
		        fitColumns: true,
		        singleSelect: true,
		        toolbar: [{
		            text: '添加',
		            iconCls: 'icon-add',
		            handler: addDiscount
		        }, '-', {
		            text: '删除',
		            iconCls: 'icon-remove',
		            handler: removeDiscount
		        }, '-', {
		            text: '编辑',
		            iconCls: 'icon-edit',
		            handler: edit
		        }, '-', {
		            text: '查看',
		            iconCls: 'icon-edit',
		            handler: viewDiscount
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
		
		function addDiscount(){
			//location.href='addDiscount';
			url='discount/save';
			$('#fm').form('clear');
			$('#dlg').dialog('open').dialog('setTitle','新增优惠通道');
		}
	function save(){
	$('#fm').form('submit',{
		url: url,
		onSubmit: function(){
			var begin=$('#strValidStartDate').datebox('getValue');//开始日期
			var over=$('#strValidEndDate').datebox('getValue');//结束日期
			var ass,aD,aS;
			var bss,bD,bS;
			ass=begin.split("-");//以"-"分割字符串，返回数组；
			aD=new Date(ass[0],ass[1]-1,ass[2]);//格式化为Date对像;
			aS=aD.getTime(); 
			bss=over.split("-");
			bD=new Date(bss[0],bss[1]-1,bss[2]);
			bS=bD.getTime();
			if(aS>bS){
				alert("有效期结束日期不能小于有效期起始日期");
				return false;
			}
			return $(this).form('validate');
		},
		success: function(result){
			var result = eval('('+result+')');
			if (result.success){
				$('#dlg').dialog('close');		
				$('#grid').datagrid('reload');	
			} else {
				$.messager.show({
					title: '错误',
					msg: result.msg
				});
			}
		}
	});
}
        function updateDiscount(){ 
		    var row = $('#grid').datagrid('getSelected');
			if (row){
			location.href='discount/update/'+row.id;
			}else{
				 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
				return;
			}
		}
		
		function edit() {
			var row = grid.datagrid('getSelected');
			url='discount/update/'+row.id;
			if(row){
				$('#dlg').dialog('open').dialog('setTitle','编辑优惠通道');
				$('#fm').form('load',row);
				//alert(row.validStartDate);
				//$("input[name='strValidStartDate']").val(row.validStartDate);
				//$("input[name='strValidEndDate']").val(row.validEndDate);
				$('#strValidStartDate').datebox('setValue',row.validStartDate);
				$('#strValidEndDate').datebox('setValue',row.validEndDate);
				
			}else{
				 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
				return;
			}
		}

		function viewDiscount(){ 
		    var row = grid.datagrid('getSelected');
			if(row){
				$('#dlg_view').dialog('open').dialog('setTitle','查看优惠通道');
				$('#fm1').form('load',row);
				//alert(row.validStartDate);
				//$("input[name='strValidStartDate']").val(row.validStartDate);
				//$("input[name='strValidEndDate']").val(row.validEndDate);
				$('#strValidStartDate1').datebox('setValue',row.validStartDate);
				$('#strValidEndDate1').datebox('setValue',row.validEndDate);
				
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
	var discountCode=$("#discountCode").val();
	var discountName=$("#discountName").val();
	grid.datagrid({ url: listUrl, queryParams: { discountName: discountName,discountCode:discountCode} });
   }