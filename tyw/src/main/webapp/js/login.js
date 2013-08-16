	/////////////////////下面是用formValidator框架进行验证
	
	//初始化框架
$(document).ready(function(){
	$.formValidator.initConfig({
		formID:"form2",
		theme:'ArrowSolidBox',
		mode:'AutoTip',
		onError:function(msg){
			alert(msg)
		},
		inIframe:true,
		ajax:true
	});
	
	
	$("#j_username").formValidator({
		onShow:"请输入用户名",
		onFocus:"用户名至少6个字符,最多100个字符",
		onCorrect:"该用户名可以登陆"
	}).inputValidator({
		min:6,
		max:100,
		onError:"你输入的用户名非法,请确认"
	}).ajaxValidator({dataType : "text", 
		async : true, 
		url : "/validateClientUserName",
		success :function(data){
			if( data == "1" ) return true;
			//return "该用户名不存在，请更换用户名";
		},           
		buttons: $("#button"),       
		//error: function(jqXHR, textStatus, errorThrown){alert("服务器没有返回数据，可能服务器忙，请重试"+errorThrown);},        
		onError : "该用户名不存在，请更换用户名",        
		//onWait : "正在对用户名进行合法性校验，请稍候..."         
	});
	
	
	



	$("#j_password").formValidator({
		onShow:"请输入密码",
		onFocus:"至少1个长度",
		onCorrect:"密码合法"
	}).inputValidator({
		min:1,
		empty:{
			leftEmpty:false,
			rightEmpty:false,
			emptyError:"密码两边不能有空符号"
		},
		onError:"密码不能为空,请确认"
	}).passwordValidator({       
		continueChar:true,       
		sameChar:false,    
		compareID:"username"      });
	
	
	$("#validateCode").formValidator({
		onShow:"请输入验证码",
		onFocus:"4个长度",
		onCorrect:"验证码正确"
	}).inputValidator({
		min:4,
		max:4,
		onError:"你输入的验证码长度非法,请确认"
	}).ajaxValidator({dataType : "text", 
		async : true, 
		url : "/validateCodeResponse",
		success :function(data){
			if( data == "1" ) return true;
			return "验证码输入错误，请重新输入";
		},           
		buttons: $("#button"),       
		//error: function(jqXHR, textStatus, errorThrown){alert("服务器没有返回数据，可能服务器忙，请重试"+errorThrown);},        
		onError : "验证码输入错误，请重新输入",        
		//onWait : "正在对验证码进行合法性校验，请稍候..."         
	});

})


function dosubmit(){  
    $('#form2').submit();  
}

function yanzhengma(){
	$('#yzm').attr("src","/validateCode?"+(new Date()).getTime());
}
document.onkeydown=function(){}