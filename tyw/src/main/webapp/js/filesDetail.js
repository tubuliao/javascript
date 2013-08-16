
function addComments(){
	var authsUserId = $("#authsUserId").val();//用户ID
	var infoBaseId = $("#infoBaseId").val();//知识ID
	var content = $("#content1").val();//评论内容
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