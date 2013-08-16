function initTags(){
	 $.post("/combotree/tag/0",{},function(data){
    	var rows=$('#tagList').datagrid('getRows');  //获取已选标签列表
    	$("#formContent").empty(); //清空之前内容
    	$("#parentId").val("0");
     	for(var i=0;i<data.length;i++){
    		var obj={type:"checkbox",val:data[i].id,id:data[i].id,name:data[i].id,checked:false}
    		for(var j=0;j<rows.length;j++){
    			if(rows[j].id==data[i].id){
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
    		if($(this).attr("checked")){
    			insertRow($(this).data("tag"));
    		}else{
    			deletetRow($(this).data("tag"));
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

//插入行
function insertRow(obj){
	var rows=$('#tagList').datagrid('getRows');
	var isHas=false;
	for(var i=0;i<rows.length;i++){
		if(rows[i].id==obj.id){
			isHas=true;
		}
	}
	if(!isHas){
		$('#tagList').datagrid('appendRow',{id:obj.id,name:obj.name,manager:"<a href='javascript:void(0)' id='"+obj.id+"' onclick='deletetRowById(this.id)'>删除</a>"});
	}
}

//删除行根据对象
function deletetRow(obj){
	var rows=$('#tagList').datagrid('getRows');
	var index=0;
	for(var i=0;i<rows.length;i++){
		if(rows[i].id==obj.id){
			index=i;
		}
	}
	$('#tagList').datagrid('deleteRow',index);
}

//删除行根据ID
function deletetRowById(id){
	$.get('/tag/delAreaTag', { tagId: id}, function(r) {
		if(r.success) {	// 删除地区标签时弹出提示
			$.messager.confirm('提示：', '确定删除此地区标签？', function(v) {
				if(v) {
					var rows=$('#tagList').datagrid('getRows');
					var index=0;
					for(var i=0;i<rows.length;i++){
						if(rows[i].id==id){
							index=i;
						}
					}
					$('#tagList').datagrid('deleteRow',index);
				} else {
					return ;
				}
			}) ;
		} else {
			var rows=$('#tagList').datagrid('getRows');
			var index=0;
			for(var i=0;i<rows.length;i++){
				if(rows[i].id==id){
					index=i;
				}
			}
			$('#tagList').datagrid('deleteRow',index);
		}
	}, 'json') ;
}
function queryTags(obj){
	var prientId=obj.id;
	appendPath(obj);
 	$.post("/combotree/tag/"+obj.id,{},function(data){
		if(data.length==0){
			alert("无下级标签");
		}else{
			$("#parentId").val(prientId);
			$("#formContent").empty(); //清空之前内容
			var rows=$('#tagList').datagrid('getRows');  //获取已选标签列表
			for(var i=0;i<data.length;i++){
        		var obj={type:"checkbox",val:data[i].id,id:data[i].id,name:data[i].id,checked:false}
        		for(var j=0;j<rows.length;j++){
        			if(rows[j].id==data[i].id){
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
								insertRow(myObj.data("tag"));
							}else{
							   myObj.attr("checked",false) ;
							}
						});
        			} else {
        				insertRow(myObj.data("tag"));
        			}
        		}else{
        			if($(this).data("tag").code.toString().indexOf("2")==0){	// 修改地区标签
        			 	$.messager.confirm('提示：', '确定修改此地区标签？', function(v) {
							if(v) {	
								deletetRow(myObj.data("tag"));
							}else{
							   myObj.attr("checked",true) ;
							}
						});
        			} else {
        				deletetRow(myObj.data("tag"));
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
 		var rows=$('#tagList').datagrid('getRows');  //获取已选标签列表
    	$("#formContent").empty(); //清空之前内容
     	for(var i=0;i<data.length;i++){
    		var obj={type:"checkbox",val:data[i].id,id:data[i].id,name:data[i].id,checked:false}
    		for(var j=0;j<rows.length;j++){
    			if(rows[j].id==data[i].id){
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
    			insertRow($(this).data("tag"));
    		}else{
    			deletetRow($(this).data("tag"));
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
function setTagIds(){
	var tagList=$('#tagList').datagrid('getRows');
	var ids=[];
	for(var key in tagList){
		ids.push(tagList[key].id);
	}
	$('#tags').val(ids.join(",")); 
}