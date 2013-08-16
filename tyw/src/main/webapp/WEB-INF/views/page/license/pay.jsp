<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%response.setHeader("progma","no-cache");%>
<%response.setHeader("Cache-Control","no-cache"); %>   
<%response.setDateHeader("Expires",0); %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>支付</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/css/project.css" rel="stylesheet" type="text/css" />

<script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>
</head>

<body>
<div class="content">
<div class="pro_head">
<div class="logo_box left">
        	<img class="left" src="/images/search_03.png" />
        <div class="left">
            	<h3>项目部计划</h3>
                <p>专业的建筑信息服务平台</p>
        </div>
            <div class="clear"></div>
    </div>
      <div class="right"><img src="/images/project/lou_03.gif" width="303" height="59" /></div>
      <div class="clear"></div>
      </div>
<div class="zhuti">
	<div class="pay_left">
    <div class="fangfa">使用方法：</div>
    <p>填写需要分配账号的人的名字和手机号。<br />
		点击发送按钮，系统会把账号密码通过短信方式发送到相应手机号码上，可凭此账号登陆。<br/>
		<font color="red">第一行填写的为白金用户的信息，请填写准确信息，以免影响使用。</font></p>
    <div class="shjh">
    <form action="/createUserList" method="post" onsubmit="return check();">
    	<table width="638" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="24" align="center" class="shuzi">NO.</td>
            <td width="62" height="30" align="center" class="biaoti">类型</td>
            <td width="229" height="30" class="biaoti">名字 </td>
            <td width="221" height="30" class="biaoti">手机号</td>
            <td width="68" height="30" align="center" class="biaoti">发送</td>
            <td width="34" height="30" align="center" class="biaoti">状态</td>
            </tr>
          <c:forEach items="${transit}" var="cl">
          <tr>
            <td id="tdid" align="center" class="shuzi">${cl}</td>
            <c:choose>  
  				<c:when test="${cl==1}">
     				<td height="50" align="center"><img title="超级管理员" src="/images/project/admin.png" width="16" height="16" /> <img title="白金VIP" src="/images/project/star.png" width="16" height="16" /></td>
   				</c:when>  
   				<c:otherwise>  
   					<td height="50" align="center"><img title="黄金VIP" src="/images/project/w-star.png" width="16" height="16" /></td>
   				</c:otherwise>
			</c:choose> 
            <td height="50"><input name="name${cl}" type="text" class="input_mingzi" id="name${cl}" value="Name" maxlength="25" onfocus="if(this.value=='Name'||this.value=='请填写白金用户姓名')this.value=''" onblur="query(this.value,${cl})"/></td>
            <td><input name="phone${cl}" type="text" class="input_shouji" id="phone${cl}" value="Phone" maxlength="11" onfocus="if(this.value=='Phone'||this.value=='请填写白金用户手机号')this.value=''" onblur="query(this.value,${cl})"/></td>
            <input type="hidden" name="licenseUserId${cl}" id="licenseUserId${cl}" value=""/>
            <td align="center"><input class="fasong" type="button" onclick="addLicenseUser(${cl});" name="button${cl}" id="button${cl}" value="发送"/></td>
            <td id="imgId${cl}" align="center"></td>
            </tr>
          </c:forEach>
          <!--tr>
            <td height="50" colspan="5" align="right" class="shuzi"><input class="baocun" type="submit" name="button3" id="button11" value="全部发送" /></td>
            </tr-->
        </table>
        </form>
    </div>
    </div>
    <div class="pay_right">
    	<form action="/payAllUser" method="post" onsubmit="return checkButton();">
       	  <table width="200" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td align="center">
                	<input type="submit" class="zhifu" value="点击支付 " />
                	<input type="hidden" name="licenseNumber" id="licenseNumber" value="${license.licenseNum }"/>
                </td>
            </tr>
              <tr>
                <td height="40" valign="bottom"><input class="zuo10" type="checkbox" name="isReceipt" id="isReceipt" />
                  发票开具</td>
            </tr>
              <tr>
                <td align="center"></td>
            </tr>
            </table>
        
        </form>
    </div>
    <div class="clear"></div>
</div>    
	<!-- Footer -->
	<div id="footer">
	  联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20</div>
	<!-- END Footer -->  
</div>


</body>
</html>
<script type="text/javascript">
  function query(getphone,num){
	  var arr10 = ["1","2","3","4","5","6","7","8","9","10"];
	  var arr15 = ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"];
	  var req = ${req};
	  if(req==10){
		  for(var i=1;i<=arr10.length;i++){
				var phone = document.getElementById("phone"+i).value;
				if(phone=="Phone"){
					continue;
				}
				if(i==num){
					continue;
				}else if(getphone==phone){
					alert("手机号不能重复！");
					document.getElementById("phone"+num).value="";
					document.getElementById("phone"+num).focus();
					return;
				}
			}
	  }
	  if(req==15){
		  for(var i=1;i<=arr15.length;i++){
				var phone = document.getElementById("phone"+i).value;
				if(phone=="Phone"){
					continue;
				}
				if(i==num){
					continue;
				}else if(getphone==phone){
					alert("手机号不能重复！");
					document.getElementById("phone"+num).value="";
					document.getElementById("phone"+num).focus();
					return;
				}
		   }
	  }
	  
	  
	  var arr10 = ["1","2","3","4","5","6","7","8","9","10"];
	  var arr15 = ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"];
	  if(num=="1"&&document.getElementById("name1").value==""){
		  $("#name1").val("请填写白金用户姓名");
	  }
	  if(num=="1"&&document.getElementById("phone1").value==""){
		  $("#phone1").val("请填写白金用户手机号");
	  }
	  for(var i=2;i<=arr10.length;i++){
		  if(document.getElementById("phone"+i).value==""){
			  $("#phone"+i).val("Phone");
		  }
		  if(document.getElementById("name"+i).value==""){
			  $("#name"+i).val("Name");
		  }
	  }
	  
	  for(var i=2;i<=arr15.length;i++){
		  if(document.getElementById("phone"+i).value==""){
			  $("#phone"+i).val("Phone");
		  }
		  if(document.getElementById("name"+i).value==""){
			  $("#name"+i).val("Name");
		  }
	  }
	  
	  
  }	
 

  function check(){
	  var arr10 = ["1","2","3","4","5","6","7","8","9","10"];
	  var arr15 = ["1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"];
	  var req = ${req};
	  if(req==10){
		  for(var i=1;i<=arr10.length;i++){
				var name = document.getElementById("name"+i).value;
				var phone = document.getElementById("phone"+i).value;
				if(phone=="Phone"){
					continue;
				}
				if(phone.length!=11){
				    alert("手机号码必须为11位");
				    document.getElementById("phone"+i).focus(); 
				   	return false;
				}
				var isMobile = /^(?:13\d|15[0-9]|18[0-9])-?\d{5}(\d{3}|\*{3})$/;
				if(!isMobile.test(phone)){
					alert("您输入的手机号码不合法，请输入正确的手机号码");    
					document.getElementById("phone"+i).focus(); 
			        return false;   
				}
				
			}
	  }
	  if(req==15){
		  for(var i=1;i<=arr15.length;i++){
				var name = document.getElementById("name"+i).value;
				var phone = document.getElementById("phone"+i).value;
				if(phone=="Phone"){
					continue;
				}
				if(phone.length!=11){
				    alert("手机号码必须为11位");
				    document.getElementById("phone"+i).focus(); 
				   	return false;
				}
				var isMobile = /^(?:13\d|15[0-9]|18[0-9])-?\d{5}(\d{3}|\*{3})$/;
				if(!isMobile.test(phone)){
					alert("您输入的手机号码不合法，请输入正确的手机号码");    
					document.getElementById("phone"+i).focus(); 
			        return false;   
				}
				
			}
	  }
}
  
  function addLicenseUser(num){
	  
	  var licenseId = "${licenseId}";
	  
	  var singlename = document.getElementById("name"+num).value;
	  var singlephone = document.getElementById("phone"+num).value;
	  var licenseUserId = document.getElementById("licenseUserId"+num).value;
	  if(singlename.length==""||singlename=="Name"||singlename=="请填写白金用户姓名"){
		  alert("请填写姓名！");
		  document.getElementById("name"+num).focus();
		  return false;
	  }
	  if(singlephone.length==""||singlephone=="Phone"||singlephone=="请填写白金用户手机号"){
		  alert("请填写电话号码！");
		  document.getElementById("phone"+num).focus();
		  return false;
	  }
	  
	  if(singlephone.length!=11){
		    alert("手机号码必须为11位");
		    document.getElementById("phone"+num).focus();
		   	return false;
		}
		var isMobile = /^(?:13\d|15[0-9]|18[0-9])-?\d{5}(\d{3}|\*{3})$/;
		if(!isMobile.test(singlephone)){
			alert("您输入的手机号码不合法，请输入正确的手机号码");    
			document.getElementById("phone"+num).focus();
	        return false;   
		}
	  
		
		 
		
		if(num==1){
			$("#button1").attr('disabled',true);
			$.post('/addLicenseUser/',{singlename:singlename,singlephone:singlephone,num:num,licenseUserId:licenseUserId},function(result){
				  if (result.success){
					  	alert("发送成功！");
						location.href='/toPay';
					}else if(result.fail){
						alert("请勿重复发送！");
						location.href='/toPay';
					}else if(result.change){
						alert("修改姓名成功！");
						location.href='/toPay';
					}else if(result.changephone){
						alert("修改用户成功！");
						location.href='/toPay';
					}else if(result.tip){
						alert("该手机号不是普通会员，无法分配！");
						location.href='/toPay';
					}else if(result.messageTip){
						alert("创建用户成功，但您发送短信的次数已超过30次，无法再发送信息！");
						location.href='/toPay';
					}else if(result.changephoneTip){
						alert("修改用户成功，但您发送短信的次数已超过30次，无法再发送信息！");
						location.href='/toPay';
					}
				},'json');
		}else{
			$("#button"+num).attr('disabled',true);
			$.post('/existLicenseUser/',{licenseId:licenseId},function(result){
				  if(result.success){
					  $.post('/addLicenseUser/',{singlename:singlename,singlephone:singlephone,num:num,licenseUserId:licenseUserId},function(result){
						  if (result.success){
							  	alert("发送成功！");
								location.href='/toPay?numId='+num;
							}else if(result.fail){
								alert("请勿重复发送！");
								location.href='/toPay?numId='+num;
							}else if(result.change){
								alert("修改姓名成功！");
								location.href='/toPay?numId='+num;
							}else if(result.changephone){
								alert("修改用户成功！");
								location.href='/toPay?numId='+num;
							}else if(result.tip){
								alert("该手机号不是普通会员，无法分配！");
								location.href='/toPay?numId='+num;
							}else if(result.messageTip){
								alert("创建用户成功，但您发送短信的次数已超过30次，无法再发送信息！");
								location.href='/toPay?numId='+num;
							}else if(result.changephoneTip){
								alert("修改用户成功，但您发送短信的次数已超过30次，无法再发送信息！");
								location.href='/toPay?numId='+num;
							}
						},'json');
				  }else if(result.fail){
					  	alert("请先创建白金用户！");
						location.href='/toPay';
				  }
			},'json');
		}
  }
  
  window.onload=function(){
	  var tdid = $("#tdid").html();
	  if(tdid=="1"){
		  $("#name1").val("请填写白金用户姓名");
		  $("#phone1").val("请填写白金用户手机号");
		  $("#name1").attr("class","input_mingzi_bj");
		  $("#phone1").attr("class","input_mingzi_bj");
	  }
	     
	  
	  $.post('/toFind/',function(result){
		  $.each(result, function(i,val){      
		      $("#name"+(i+2)).val(val.userName);
		      $("#phone"+(i+2)).val(val.phone);
		      $("#licenseUserId"+(i+2)).val(val.id);
		      $("#imgId"+(i+2)).append('<img src="/images/project/pay_22.gif" width="20" height="19" />');
		  });   
		},'json');
	  
	    var user_name = "${lu.userName}";
		var phone = "${lu.phone}";
		var licenseUserId = "${lu.id}";
		
		if(user_name!=""){
			$("#name1").val(user_name);
			$("#name1").attr('readonly',true);
		}
		if(phone!=""){
			$("#phone1").val(phone);
			$("#phone1").attr('readonly',true);
			$("#button1").attr('disabled',true);
			$("#licenseUserId1").val(licenseUserId);
			$("#imgId1").append('<img src="/images/project/pay_22.gif" width="20" height="19" />');
		}
		  
		  $("#isReceipt").click(function(){
			 if($("#isReceipt").attr("checked")){
				 $("#isReceipt").val("1");
			 }else{
				 $("#isReceipt").val("0");
			 }
		  });
		  
		  var numId = "${numId}";
		  if(numId>7){
			  window.scrollTo(0,document.body.scrollHeight); 
		  }
  }
  

  
  function checkButton(){
	  
	  var licenseId = "${licenseId}";
	  
	  var uid = "${uid}";
	  if(uid==null||uid==""){
			  alert("请先创建白金用户！");
			  return false;
	  }
	  
	  $.post('/checkPay/',{licenseId:licenseId},function(result){
		  if (result.fail){
			  	alert("此许可号已交费，请勿重复付费！");
				location.href='/toPay';
			}
		},'json');
  }
</script>
