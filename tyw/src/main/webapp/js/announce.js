var grid;
var listUrl='announcelist/data';

	    //删除记录
		function removeAnnounce(){
			var row = $('#grid').datagrid('getSelected');
			if (row){
				$.messager.confirm('提示','您确定要删除该记录?',function(r){
					if (r){
						$.post('announce/delete/'+row.id,{id:row.id},function(result){
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
	            text: '添加公告',
	            iconCls: 'icon-add',
	            handler: addAnnounce
	        }, '-', {
	            text: '删除公告',
	            iconCls: 'icon-remove',
	            handler: removeAnnounce
	        }, '-', {
	            text: '编辑公告',
	            iconCls: 'icon-edit',
	            handler: updateAnnounce
	        }, '-', {
	            text: '查看公告',
	            iconCls: 'icon-edit',
	            handler: viewAnnounce
	        }];
			var bar2=[{
	            text: '查看公告',
	            iconCls: 'icon-edit',
	            handler: viewAnnounce
	        }];
		       
			if(backGround=='1'){bar=bar2;}else{bar=bar1;};
			grid=$('#grid').datagrid({
				url: listUrl,
				loadMsg:'正在处理，请稍待。。。',
				title: '公告列表',
				 columns:[[  
			        {field:'id',title:'id',hidden:true},  
			        {field:'title',title:'标题',width:25},  
			        {field:'issuedBy',title:'发布人',width:25}, 
			        {field:'createDate',title:'创建时间',width:20}
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
		function addAnnounce(){
			location.href='addAnnounce';
		}
		function viewAnnounce(){
			var row = $('#grid').datagrid('getSelected');
			if(row){
				location.href='announce/view/'+row.id;
			}else{
				 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
				return;
			}
		}
		function updateAnnounce(){
			var row =$('#grid').datagrid('getSelected');
			if(row){
				location.href='announce/update/'+row.id;
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
	var issuedBy=$("#issuedBy").val();
	var title=$("#title").val();
	grid.datagrid({ url: listUrl, queryParams: { title: title,issuedBy:issuedBy} });
   }