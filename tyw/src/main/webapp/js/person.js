function formEduSubmit(){
	$('#formEdu').submit(); 
}
function formOccupSubmit(){
	$('#formOccup').submit(); 
}
function formProjectSubmit(){
	$('#formPro').submit(); 
}
function formUserSubmit(){
	$('#userInfo').submit(); 
}

$(document).ready(function(){
	//获取信息类型
	var hover = $("#personTag").val();
	if(hover=='2'){//教育背景
		$("#one2").click();
	}else if(hover=='3'){//工作经历
		$('#one3').click();
	}else if(hover=='4'){//项目经验
		$('#one4').click();
	}
	
	
	
	
	
	
	
})