

function collectBookmark() {
	var userId = $("#userId").val();
	var kId = $('#kId').val();
	//alert(kId);
	//return ;
	// 收藏地址
	var url = window.location.href ;
	// pagination分页插件跳转页面会在url上加"#"号
	url = url.replace(new RegExp('#', 'gm'), '');
	//alert(url);
	//return;
	var title = $('#title02').text() ;
	//alert(url + '**********' + title) ;
	if(userId == null || userId == ''){
		$("#loginDiv").fadeIn(1000);
   		$("#loginDiv").fadeOut(1000);
   	    setTimeout("location.href='/login.jsp'",2000);
	}else{
		$.post('/collectBookmark', { bUrl: url, bTitle: title, kId: kId}, function(result) {
			if(result.success) {
				$("#collectImg").fadeIn(800).slideUp();
      		    $("#collectImg").fadeOut(800);
      		    var addCollect = parseInt($("#collectCountId").text())+1;
       		    $("#collectCountId").html(addCollect);
			} else if(result.fail) {
				$("#collectDiv").fadeIn(1000);
	       		$("#collectDiv").fadeOut(1000);
			}
		}, 'json') ;
	}
	
}



function bookDetail() {

	var sourceCompose = $('#sourceDetail').html();
	//var sourceCompose = $('#sourceDetail').html().replace(new RegExp('/', 'g'), '&');
	//alert(sourceCompose);
	//location.href="${pageContext.request.contextPath}/knowledge/web/bookdetail/" + encodeURIComponent(encodeURIComponent(sourceCompose));	// 两次编码 java在后台会自动的进行一次解码 但结果不对
	window.open("/knowledge/web/bookdetail/" + encodeURIComponent(encodeURIComponent(sourceCompose)), '_blank');

}

