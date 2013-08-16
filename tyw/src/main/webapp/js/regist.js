$(document).ready(function(){

	var nowid;
	var totalid;
	var can1press = false;
	var emailafter;
	var emailbefor;

	    $("#mail").focus(function(){ //文本框获得焦点，插入Email提示层
	        $("#myemail").remove();
	 $(this).after("<div id='myemail' style='width:170px; height:auto; background:#fff; color:#6B6B6B; position:absolute; border:1px solid #ccc;z-index:5px; '></div>");
	        if($("#myemail").html()){
	             $("#myemail").css("display","block");
	 $(".newemail").css("width",$("#myemail").width());
	  can1press = true;
	        } else {
	             $("#myemail").css("display","none");
	  can1press = false;
	        }  
	    }).keyup(function(){ //文本框输入文字时，显示Email提示层和常用Email
	  var press = $("#mail").val();
	  if (press!="" || press!=null){
	  var emailtxt = "";   
	  var emailvar = new Array("@163.com","@126.com","@yahoo.com","@qq.com","@sina.com","@gmail.com","@hotmail.com","@foxmail.com");
	  totalid = emailvar.length;
	   var emailmy = "<div class='newemail' style='width:170px; color:#6B6B6B; overflow:hidden;'><font color='#D33022'>" + press + "</font></div>";
	   if(!(isEmail(press))){
	       for(var i=0; i<emailvar.length; i++) {
	        emailtxt = emailtxt + "<div class='newemail' style='width:170px; color:#6B6B6B; overflow:hidden;'><font color='#D33022'>" + press + "</font>" + emailvar[i] + "</div>"
	       }
	   } else {
	       emailbefor = press.split("@")[0];
	       emailafter = "@" + press.split("@")[1];
	       for(var i=0; i<emailvar.length; i++) {
	            var theemail = emailvar[i];
	            if(theemail.indexOf(emailafter) == 0)
	            {
	             emailtxt = emailtxt + "<div class='newemail' style='width:170px; color:#6B6B6B; overflow:hidden;'><font color='#D33022'>" + emailbefor + "</font>" + emailvar[i] + "</div>"
	         }
	       }
	   }
	   $("#myemail").html(emailmy+emailtxt);
	   if($("#myemail").html()){
	     $("#myemail").css("display","block");
	     $(".newemail").css("width",$("#myemail").width());
	     can1press = true;
	   } else {
	     $("#myemail").css("display","none");
	     can1press = false;
	   }
	   beforepress = press;
	  }
	  if (press=="" || press==null){
	      $("#myemail").html("");  
	       $("#myemail").css("display","none");    
	  }    
	    });
	 $(document).click(function(){ //文本框失焦时删除层
	        if(can1press){
	   $("#myemail").remove();
	   can1press = false; 
	   if($("#mail").focus()){
	       can1press = false;
	   }
	  }
	    });
	    $(".newemail").live("mouseover",function(){ //鼠标经过提示Email时，高亮该条Email
	        $(".newemail").css("background","#FFF");
	        $(this).css("background","#CACACA");  
	  $(this).focus();
	  nowid = $(this).index();
	    }).live("click",function(){ //鼠标点击Email时，文本框内容替换成该条Email，并删除提示层
	        var newhtml = $(this).html();
	        newhtml = newhtml.replace(/<.*?>/g,"");
	        $("#mail").val(newhtml); 
	        $("#myemail").remove();
	    });
	 $(document).bind("keydown",function(e)  
	 {     
	  if(can1press){
	   switch(e.which)     
	   {            
	    case 38:
	    if (nowid > 0){  
	     $(".newemail").css("background","#FFF");
	     $(".newemail").eq(nowid).prev().css("background","#CACACA").focus();
	     nowid = nowid-1;  
	    }
	    if(!nowid){
	     nowid = 0;
	     $(".newemail").css("background","#FFF");
	     $(".newemail").eq(nowid).css("background","#CACACA");  
	     $(".newemail").eq(nowid).focus();    
	    }
	    break;       
	  
	    case 40:
	    if (nowid < totalid){    
	     $(".newemail").css("background","#FFF");
	     $(".newemail").eq(nowid).next().css("background","#CACACA").focus(); 
	     nowid = nowid+1;   
	    }
	    if(!nowid){
	     nowid = 0;
	     $(".newemail").css("background","#FFF");
	     $(".newemail").eq(nowid).css("background","#CACACA");  
	     $(".newemail").eq(nowid).focus();    
	    }
	    break;  
	  
	    case 13:
	    var newhtml = $(".newemail").eq(nowid).html();
	    newhtml = newhtml.replace(/<.*?>/g,"");
	    $("#mail").val(newhtml); 
	    $("#myemail").remove();
	   }
	  }   
	 });

	 
	 //检查email邮箱
	function isEmail(str){
	    if(str.indexOf("@") > 0)     
	    {     
	        return true;
	    } else {
	        return false;
	    }
	}
	
	/////////////////////下面是用formValidator框架进行验证
});	
	


function dosubmit(){
	//初始化框架
	/*$.formValidator.initConfig({
		formID:"form1",
		theme:'ArrowSolidBox',
		mode:'AutoTip',
		onError:function(msg){
			//alert(msg)
		},
		inIframe:true,
		ajax:true
	});
	
	$("#mail").formValidator({
		onShow:"请输入邮箱",
		onFocus:"邮箱至少6个字符,最多100个字符",
		onCorrect:"该邮箱可以使用"
	}).regexValidator({
		regExp:"^([\\w-.]+)@(([[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.)|(([\\w-]+.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$",
		onError:"你输入的邮箱格式不正确"
	}).inputValidator({
		min:6,
		max:100,
		onError:"你输入的邮箱非法,请确认"
	}).ajaxValidator({dataType : "text", 
		async : true, 
		url : "/validateClientMail",
		success :function(data){
			if( data == "1" ) return true;
			return "该邮箱已存在，请更换邮箱";
		},           
		buttons: $("#button"),       
		error: function(jqXHR, textStatus, errorThrown){alert("服务器没有返回数据，可能服务器忙，请重试"+errorThrown);},        
		onError : "该邮箱已存在，请更换邮箱",        
		onWait : "正在对邮箱进行合法性校验，请稍候..."         
	});

	$("#password1").formValidator({
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
		sameChar:false });

	$("#confirm_password1").formValidator({
		onShow:"输再次输入密码",
		onFocus:"至少1个长度",
		onCorrect:"密码一致"
	}).inputValidator({
		min:1,
		empty:{
			leftEmpty:false,
			rightEmpty:false,
			emptyError:"重复密码两边不能有空符号"
		},
		onError:"重复密码不能为空,请确认"
	}).compareValidator({
		desID:"password1",
		operateor:"=",
		onError:"2次密码不一致,请确认"
	});*/
	
	
	var mail = $('#mail').val();
	var password1 = $('#password1').val();
	var confirm_password1 = $('#confirm_password1').val();
	if(mail==''||password1==''||confirm_password1==''){
		return false;
	}
	
	$('#tijiao').disabled=true;
    $('#form1').submit();
   
}
function dosubmit2(){
	//初始化框架
	/*$.formValidator.initConfig({
		formID:"form2",
		theme:'ArrowSolidBox',
		mode:'AutoTip',
		onError:function(msg){
			//alert(msg)
		},
		inIframe:true,
		ajax:true
	});
	
	$("#phone").formValidator({
		onShow:"请输入手机号",
		onFocus:"请输入手机号",
		onCorrect:"手机号合法合法"
	}).inputValidator({
		min:1,
		empty:{
			leftEmpty:false,
			rightEmpty:false,
			emptyError:"手机号两边不能有空符号"
		},
		onError:"手机号不能为空,请确认"
	});

	$("#password2").formValidator({
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
		sameChar:false });

	$("#confirm_password2").formValidator({
		onShow:"输再次输入密码",
		onFocus:"至少1个长度",
		onCorrect:"密码一致"
	}).inputValidator({
		min:1,
		empty:{
			leftEmpty:false,
			rightEmpty:false,
			emptyError:"重复密码两边不能有空符号"
		},
		onError:"重复密码不能为空,请确认"
	}).compareValidator({
		desID:"password2",
		operateor:"=",
		onError:"2次密码不一致,请确认"
	});*/
		
	var phone = $('#phone').val();
	var password2 = $('#password2').val();
	var confirm_password2 = $('#confirm_password2').val();
	if(phone==''||password2==''||confirm_password2==''){
		return false;
	}
	
	$('#tijiao2').disabled=true;
    $('#form2').submit();  
}