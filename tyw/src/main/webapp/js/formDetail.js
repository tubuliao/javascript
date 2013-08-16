
function addComments(){
	var authsUserId = $("#authsUserId").val();//用户ID
	var infoBaseId = $("#infoBaseId").val();//知识ID
	var content = $("#content1").attr("value");//评论内容
	//alert(content);
	//var s= escape(content).toLocaleLowerCase().replace(/%u/gi,'\\u');
	var dataSource = "authsUserId="+authsUserId+"&infoBaseId="+infoBaseId+"&content="+content;
	if(content==""){
		alert("评论内容不能为空！");
		return;
	}
	if(authsUserId==""){
		alert("没有用户信息！");
		return;
	}
	if(infoBaseId==""){
		alert("没有知识信息！");
		return;
	}
	$.ajax({
   		type: "POST",
   		url: "/addComments",
   		data: dataSource,
   		success: function(){
   			window.location.reload();
   				}
	});


}

function setTab(name,cursel,n){
	for(var i=1;i<=n;i++){
	var menu=document.getElementById(name+i);
	var con=document.getElementById("con_"+name+"_"+i);
	menu.className=i==cursel?"hover":"";
	con.style.display=i==cursel?"block":"none";
	}
	}