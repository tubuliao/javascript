<%@ page contentType="text/html; charset=utf-8" language="java" 
import="com.isoftstone.tyw.entity.auths.User,com.isoftstone.tyw.entity.auths.Additional,java.sql.*,com.isoftstone.tyw.util.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
PropertiesReader pu = PropertiesReader.getInstance();
String path = pu.getProperties("fdfs.HttpAddress");
//虽然使用了session但是不影响数据即时性展示问题，因为每次都是最新的放入session中
session.setAttribute("httpAddress", path);
User user = (User)session.getAttribute("user");
if(user == null){
	user = new User();
	Additional additional = new Additional();
	user.setAdditional(additional);
}
String url = "";
if(user.getHeadUrl() == "" || user.getHeadUrl() == null){
	url = request.getContextPath()+"/images/personal/person_07.gif";
}else{
	url = path+user.getHeadUrl();
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>个人信息</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/person.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/jquery-ui-1.10.2/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<SCRIPT language=javascript src="homesearch.js"></SCRIPT>

<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
<script src="${ctx}/js/persondialogs.js" type="text/javascript"></script>
<!--[if lt IE 7]>
        <script type="text/javascript" src="unitpngfix.js"></script>
<![endif]-->
    <link href="${pageContext.request.contextPath}/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/uploadify/jquery.uploadify-3.1.min.js" type="text/javascript"></script>
    <script  type="text/javascript">
/*
    函数名称: Scroll
    Scroll(obj, h, s)
    参数说明:
        obj,[object]  id值或对象.     必需
          h,[height]  展开后的高度.   可选(默认为200px)
          s,[speed]   展开速度,值越小展开速度越慢. 可选(默认为1.2){建议取值为1.1到2.0之间[例如:1.17]}.
    函数返回值:
        true    展开(对象的高度等于展开后的高度)
        false   关闭(对象的高度等于原始高度)
*/
function Scroll(obj, h, s){
    if(obj == undefined){return false;}
    var h = h || 200;
    var s = s || 1.2;
    var obj = typeof(obj)=="string"?document.getElementById(obj):obj;
    var status = obj.getAttribute("status")==null;
    var oh = parseInt(obj.offsetHeight);
    obj.style.height = oh;
    obj.style.display = "block";
	obj.style.overflow = "hidden";
    if(obj.getAttribute("oldHeight") == null){
        obj.setAttribute("oldHeight", oh);
    }else{
        var oldH = Math.ceil(obj.getAttribute("oldHeight"));
    }
    var reSet = function(){
        if(status){
            if(oh < h){
                oh = Math.ceil(h-(h-oh)/s);
                obj.style.height = oh+"px";
            }else{
                obj.setAttribute("status",false);
                window.clearInterval(IntervalId);
            }
        }else{
            obj.style.height = oldH+"px";
            obj.removeAttribute("status");
            window.clearInterval(IntervalId);
        }
    }
    var IntervalId = window.setInterval(reSet,10);
	return status;
}

<!--
/*第一种形式 第二种形式 更换显示样式*/
function setTab(name,cursel,n){
for(i=1;i<=n;i++){
var menu=document.getElementById(name+i);
var con=document.getElementById("con_"+name+"_"+i);
menu.className=i==cursel?"hover":"";
con.style.display=i==cursel?"block":"none";
}
}
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
window.onload = function(){
	 var headUrl = '${user.headUrl}';
	 if(headUrl.length>1){
		$("#person_info_head").attr("src","${httpAddress}"+headUrl);
	 }
	 
	 var sex ='${user.additional.sex}';
	 if(sex==''){
	 	sex = 1;
	 }
	 var sexs = document.getElementsByName("additional.sex");
	 for(var i = 0 ;i<sexs.length;i++){
	 	if(sex == sexs[i].value){
	 		sexs[i].checked = true;
	 	}
	 } 
	
	 var hover = $("#personTag").val();
	 if(hover == 2 || hover == 3 || hover == 4){
		 setTab('one',hover,4);	 
	 }else{
	 	setTab('one',1,4);
	 }
	 	
	 getProvince(1);
	 $('#chooseProfession').html($('#divprofession').html());
	 $("#chooseJobNature").html($("#divjobnature").html());
	 $("#chooseCompanyNature").html($("#divcompayNature").html());
}
function getProvince(flag){
//这里自动去加载地域信息等
	 var areaurl = '${ctx}/user/getProvince';
	 $.post(areaurl,function(result){
	    var html = '';
	    html += "<table>";
	    	html += "<tr>";	
  		$.each(result,function(i){
  			 if(i%5 == 0){
  				html += "</tr>";	
  			 }	
  			 if(i%5 == 0){
  					html += "<tr>";
  			 }
  			 html += "<td style='width:120px;'>";
  			 	 html += "<a href='javascript:getCity("+this.id+")'>"+this.name+"</a>";	
  			 html += "</td>"; 
  		});
  			html += "</tr>";
  		html += "</table>";	
  		$("#chooseArea").html(html);
  		if(flag == 1){
  			$('#chooseArea').dialog('close');
  		}else{
	  		$('#chooseArea').dialog('open');
  		}
  	},'json');
}
function getCity(op){
	if(op == undefined)return;
	 var cityurl = '${ctx}/user/getCity';
	 $.post(cityurl,{id:op},function(result){
	 	var html = '';
	    html += "<table style='width:400px;'>";
	    	html += "<tr>";	
  		$.each(result,function(i){
  			 if((i)%4 == 0){
  				html += "</tr>";	
  			 }	
  			 if((i)%4 == 0){
  					html += "<tr>";
  			 }
  			 html += "<td>";
  			 	 html += "<a id='acity_"+this.id+"' href='javascript:addCity("+this.id+")'>"+this.name+"</a>";	
  			 html += "</td>"; 
  		});
  			html += "</tr>";
  		html += "</table>";	
  		$("#chooseArea").html(html);
  		$('#chooseArea').dialog('close');
  		$('#chooseArea').dialog('open');
	 },'json');
}

function addCity(cityid){
	$('#chooseArea').dialog('close');
	$("input[name='additional.originalFamilyHome']").val($("#acity_"+cityid).html());
	getProvince(1);
}
 
//删除教育信息
function deleteEducation(eduId){
	var url = '${pageContext.request.contextPath}/deleteEducationInfo';
  	$.post(url,{id:eduId},function(result){
  		if(result == 0){
  			 $("#edu"+eduId).remove();
  		} 
  	},'json');
}
function deleteOccupInfo(occid){
	var url = '${pageContext.request.contextPath}/deleteOccupInfo';
  	$.post(url,{id:occid},function(result){
  		if(result == 0){
  			 $("#occ"+occid).remove();
  		} 
  	},'json'); 
}
function deleteProject(proid){
	var url = '${pageContext.request.contextPath}/deleteProject';
  	$.post(url,{id:proid},function(result){
  		if(result == 0){
  			 $("#pro"+proid).remove();
  		} 
  	},'json'); 
}
//点击修改的效果
function showEducation(eduId){
	if(Scroll('list'+eduId, 240, 1.2)){
		$("#show"+eduId).hide();
		$("#hide"+eduId).show();
	}else{
		$("#show"+eduId).show();
		$("#hide"+eduId).hide();
	} 
}
//点击展开 标签的切换情况
function expandEducation(eduId){
	if(Scroll('list'+eduId, 240, 1.2)){
		$("#show"+eduId).show();
		$("#hide"+eduId).hide();
	}else{
	    $("#show"+eduId).hide();
		$("#hide"+eduId).show();
	} 
}
//根据id获取相应元素的值，拼成ajax参数 修改edu实体内容，然后关闭标签
function eduSubForm(eduId){
	var additionalId = 1;
		additionalId = '${user.id}';	
	var area = $("#area"+eduId).val();
	var inSchool = $("#inSchool"+eduId).val();
	var schoolType = $("#schoolType"+eduId).val();
	var faculty = $("#faculty"+eduId).val();
	var schoolName = $("#schoolName"+eduId).val();
	var schoolType = $("#schoolType"+eduId).val();
	var url = '${pageContext.request.contextPath}/ajaxSaveEduInfo';
  	$.post(url,{schoolType:schoolType,schoolName:schoolName,id:eduId,area:area,inSchool:inSchool,faculty:faculty,additionalId:additionalId},function(result){
  		if(result == 0){
  			 //更新静态表格的内容
  			 var html = '';
  			 	 html += "入学年份:"+inSchool+"</br>";
  			 	 html += "教育程度:"+schoolType+"</br>";
				 html += "院系:"+faculty+"</br>";
				 html += "学校地址:"+area+"</br>";  
				 $("#show"+eduId).html(html);
  			 
  			 $("#img_"+eduId).click();
  		} 
  	},'json');  
}
function occpSubmit(occId){
	var additionalId = 1;
	additionalId = '${user.id}';	
	var unitProperties = $("#unitProperties_"+occId).val();
	var department = $("#department_"+occId).val();
	var beginTime = $("#beginTime_"+occId).val();
	var endTime = $("#endTime_"+occId).val();
	var location = $("#location_"+occId).val();
	var unitName = $("#unitName_"+occId).val();
	var url = '${pageContext.request.contextPath}/ajaxSaveOccpInfo';
	$.post(url,{unitName:unitName,unitProperties:unitProperties,department:department,id:occId,beginTime:beginTime,endTime:endTime,location:location,additionalId:additionalId},function(result){
  		if(result == 0){
  			 var html = '';
  			 	 html += "单位性质:"+unitProperties+"</br>";
  			 	 html += "担任职位:"+department+"</br>";
				 html += "工作时间:"+beginTime+"~"+endTime+"</br>";
				 html += "单位地址:"+location+"</br>";  
				 $("#show"+occId).html(html);
  			 $("#img_"+occId).click();
  		} 
  	},'json'); 
}
function projectSubmit(proId){
	var additionalId = 1;
	additionalId = '${user.id}';	
	var buildUnit = $("#buildUnit_"+proId).val();
	var addr = $("#addr_"+proId).val();
 	var name = $("#proName_"+proId).val();
 	var region = $("#region_"+proId).val();
	var url = '${pageContext.request.contextPath}/ajaxSaveProjectInfo';
	$.post(url,{region:region,addr:addr,buildUnit:buildUnit,id:proId,additionalId:additionalId,name:name},function(result){
  		if(result == 0){
  			 var html = '';
  			 	 html += "职位:"+region+"</br>";
  			 	 html += "工作地点:"+addr+"</br>";
				 $("#show"+proId).html(html);
  			 $("#img_"+proId).click();
  		} 
  	},'json'); 
	
}
 
</script>
<script type="text/javascript" >
function $$$$$(_sId){
 return document.getElementById(_sId);
 }
function hide(_sId)
 {$$$$$(_sId).style.display = $$$$$(_sId).style.display == "none" ? "" : "none";}
function pick(v) {
 document.getElementById('am').value=v;
hide('HMF-1')
}
function bgcolor(id){
 document.getElementById(id).style.background="#F7FFFA";
 document.getElementById(id).style.color="#000";
}
function nocolor(id){
 document.getElementById(id).style.background="";
 document.getElementById(id).style.color="#788F72";
}
function profession(op){
$("input[name='additional.profession']").val(op);
$('#chooseProfession').dialog('close');
}
function jobNature(op){
$("input[name='additional.jobNature']").val(op);
$('#chooseJobNature').dialog('close');
}
function companyNature(op){
$("input[name='additional.companyNature']").val(op);
$('#chooseCompanyNature').dialog('close');
}
</script>
<script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/common/jquery.validate.js"></script>
<script type="text/javascript">
$(function() {
		$("#userInfo").validate({
	        rules: {
	    			phone: {
	    				isMobile:true,
					    remote:{ //验证手机是否存在
　　						type:"POST",
　　                                                    url:"/validateIsExistPhone",
　　                                                   data:{
　                                                                      　 phone:function(){return $("#phone").val();}
　			                }  
					  }
				    } 
	 			 },
	        messages: { 
				    phone: {
					    remote: "<font color='red'>*该手机号已经存在</font>"
				   }  
	  			}
	    });
	    $("#formEdu").validate({
	        rules: {
	    			schoolName: {
					    required: true 
				    } 
	 			 },
	        messages: { 
				    schoolName: {
					    required: "<font color='red'>*必填字段</font>"
				   }  
	  			}
	    });
	    
	    $("#formOccup").validate({
	        rules: {
	    			unitName: {
					    required: true 
				    } 
	 			 },
	        messages: { 
				    unitName: {
					    required: "<font color='red'>*必填字段</font>"
				   }  
	  			}
	    });
	    $("#formPro").validate({
	        rules: {
	    			name: {
					    required: true 
				    } 
	 			 },
	        messages: { 
				    name: {
					    required: "<font color='red'>*必填字段</font>"
				   }  
	  			}
	    });
});
</script>
	
</head>

<body> 
 <div class="search_up"></div>
  <div class="search_content_box">
  <div class="search_content">
    <div class="person_nav2">
    	
        	<a class="present" href="/personCenter1">个人资料补全</a>
            <a href="/personCentertoModifyPassword">修改密码</a>
            <!-- 
            <a href="/personCentertoValidatePhone">密保手机</a>
            <a href="/personCentertoValidateEmail">密保邮箱</a>
            <a href="#">我的积分</a>
            <a href="#">我的等级</a>
            <a href="#">安全中心</a-->
        </ul>
  </div>
<div class="person_left left">
    	<div class="person_info">
        <ul>
        	<li id="one1" onclick="setTab('one',1,4)"  class="hover">基本资料</li>
            <li id="one2" onclick="setTab('one',2,4)">教育背景</li>
            <li id="one3" onclick="setTab('one',3,4)">工作经历</li>
            <li id="one4" onclick="setTab('one',4,4)">项目经验</li>
        </ul>
        </div>
      <div class="content_box">
	<form id="userInfo" name="userInfo" method="post" action="/personCenterUpdateUserInfo">
		<input type="hidden" name="id" id="id" value="${user.id}"/>
		<input type="hidden" name="additional.id" id="additional.id" value="${user.id}"/>
		<input type="hidden" name="enable" id="enable" value="${user.enable}"/>
		<input type="hidden" name="nonLocked" id="nonLocked" value="${user.nonLocked}"/>
		
        <table style="display:none" id="con_one_1" width="700" border="0" cellpadding="0" cellspacing="0" class="person_info_add hover">
          <tr>
            <td width="120" height="40" align="right" valign="top">头像：</td>
            <td  height="40">
				<a href="#"><img  id="person_info_head" class="person_info_head" 
				 
				src="<%=url%>"
				
				width="150" height="150" /></a>
				<input name='headUrl' type='hidden' id="headUrl" value="${user.headUrl}" />
				</br>
				<table>
					<tr>
						<td><input type="file"  name="uploadify" id="uploadify"/></td><td></td>
					</tr>
				</table> 
			</div>
            </td>
            <td width="396" height="40" valign="top" class="login_tishi"></td>
          </tr>
          <tr>
            <td height="40" align="right">用户名：</td>
            <td height="40"><input type="text" name="username" readonly id="username" value="${user.username}" /></td>
            <td height="40" class="login_tishi">
            	<input type="hidden" name="userType" id="userType" value="${user.userType}" />
            	<input type="hidden" name="password" id="password" value="${user.password}" />
            	<input type="hidden" name="userType" id="userType" value="${user.userType}" />
            	注册后不可更改
            </td>
          </tr>
          <tr>
            <td height="40" align="right">登录邮箱：</td>
            <td height="40"><input type="text" name="mail" id="mail" value="${user.mail}" /></td>
            
            <td height="40" class="login_tishi">请正确填写，邮件是取回密码的重要手段</td>
          </tr>
          <tr>
            <td height="40" align="right">真实姓名：</td>
            <td height="40"><input type="text" name="aliasname" id="aliasname" value="${user.aliasname}" /></td>
            <td height="40" class="login_tishi">请填写真实姓名，以便日后积分兑换</td>
          </tr>
          <tr>
            <td height="40" align="right">性&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
            <td height="40">
            	<input type="radio" name="additional.sex" checked id="male" value="1" />男 
            	<input type="radio" name="additional.sex" id="female" value="0" />女
            </td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td height="40" align="right">生&nbsp;&nbsp;&nbsp;&nbsp;日：</td>
            <td height="40">
            	<input type="text"  class="Wdate" value="<fmt:formatDate value='${user.additional.birthday}' pattern='yyyy-MM-dd'/> " name="str_birthday" id="str_birthday" onClick="WdatePicker()"  />
            </td>
            <td height="40">&nbsp;</td>
          </tr>
          <tr>
            <td height="40" align="right">原&nbsp;&nbsp;&nbsp;&nbsp;籍：</td>
            <td height="40">
            	<input type="text" value="${user.additional.originalFamilyHome}" name="additional.originalFamilyHome" id="additional.originalFamilyHome" onfocus="test()" />
            	<div id="chooseArea" class="fcontrol" title="请选择您的原籍" style="width:250px;height:100px;display:none">
				</div>
            </td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td height="40" align="right">专业属性：</td>
            <td height="40">
            	<input type="text" readonly name="additional.profession" id="additional.profession" value="${user.additional.profession}"/>
            	<div id="chooseProfession" class="fcontrol" title="请选择您的专业属性" style="width:250px;height:100px;display:none">
				</div>
            	<div id='divprofession' style='display:none'>
<table style="width:450px;">
<tr>
	<td colspan="5">建筑与环艺：</td>
</tr>
<tr>
	<td><a href="#chooseProfession" onclick="profession('建筑设计')">建筑设计</a></td>
	<td><a href="#chooseProfession" onclick="profession('园林景观 ')">园林景观 </a></td>
	<td><a href="#chooseProfession" onclick="profession('室内设计')">室内设计</a></td>
	<td><a href="#chooseProfession" onclick="profession('照明设计')">照明设计</a></td>
	<td><a href="#chooseProfession" onclick="profession('建模渲染')">建模渲染 </a></td>
</tr>
<tr>
	<td colspan="5">工程技术类：</td>
</tr>
<tr>
	<td><a href="#chooseProfession" onclick="profession('建筑施工')">建筑施工</a></td>
	<td><a href="#chooseProfession" onclick="profession('结构设计')">结构设计 </a></td>
	<td><a href="#chooseProfession" onclick="profession('路桥市政')">路桥市政 </a></td>
	<td><a href="#chooseProfession" onclick="profession('岩土工程')">岩土工程 </a></td>
	<td><a href="#chooseProfession" onclick="profession('水利水电')">水利水电  </a></td>
</tr>
<tr>
	<td colspan="5">造价与管理：</td>
</tr>
<tr>
	<td><a href="#chooseProfession" onclick="profession('工程造价')">工程造价</a></td>
	<td><a href="#chooseProfession" onclick="profession('房地产')">房地产 </a></td>
	<td><a href="#chooseProfession" onclick="profession('工程监理')">工程监理 </a></td>
	<td><a href="#chooseProfession" onclick="profession('项目管理')">项目管理</a></td>
	<td></td>
</tr>
<tr>
	<td colspan="5">建筑设备类：</td>
</tr>
<tr>
	<td><a href="#chooseProfession" onclick="profession('建筑电气')">建筑电气 </a></td>
	<td><a href="#chooseProfession" onclick="profession('给排水')">给排水  </a></td>
	<td><a href="#chooseProfession" onclick="profession('暖通空调')">暖通空调  </a></td>
	<td></td>
	<td></td>
</tr>
<tr>
	<td colspan="5">其它专业类：</td>
</tr>
<tr>
	<td><a href="#chooseProfession" onclick="profession('BIM')">BIM</a></td>
	<td><a href="#chooseProfession" onclick="profession('建筑材料')">建筑材料  </a></td>
	<td><a href="#chooseProfession" onclick="profession('其他专业')">其他专业 </a></td>
	<td></td>
	<td></td>
</tr>
</table>
            	</div>
            </td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td height="40" align="right">工作性质：</td>
            <td height="40">
            	<input type="text"  name="additional.jobNature" id="additional.jobNature" value="${user.additional.jobNature}"  />
            	<div id="chooseJobNature" class="fcontrol" title="请选择您的工作性质" style="width:250px;height:100px;display:none">
				</div>
            	<div id='divjobnature' style='display:none'>
<table style="width:450px;">
<tr>
	<td><a href="#chooseJobNature" onclick="jobNature('管理')">管理</a></td>
	<td><a href="#chooseJobNature" onclick="jobNature('设计')">设计</a></td>
	<td><a href="#chooseJobNature" onclick="jobNature('施工')">施工</a></td>
	<td><a href="#chooseJobNature" onclick="jobNature('监理')">监理</a></td>
	<td><a href="#chooseJobNature" onclick="jobNature('造价')">造价 </a></td>
</tr>
<tr>
	<td><a href="#chooseJobNature" onclick="jobNature('生产制造')">生产制造</a></td>
	<td><a href="#chooseJobNature" onclick="jobNature('科研 ')">科研 </a></td>
	<td><a href="#chooseJobNature" onclick="jobNature('采购')">采购</a></td>
	<td><a href="#chooseJobNature" onclick="jobNature('销售')">销售</a></td>
	<td><a href="#chooseJobNature" onclick="jobNature('市场')">市场 </a></td>
</tr>
<tr>
	<td><a href="#chooseJobNature" onclick="jobNature('评估咨询 ')">评估咨询</a></td>
	<td><a href="#chooseJobNature" onclick="jobNature('其他')">其他</a></td>
	<td></td>
	<td></td>
	<td></td>
</tr>
</table>
            	</div>
            </td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td height="40" align="right">单位性质：</td>
            <td height="40">
            	<input type="text" name="additional.companyNature" id="additional.companyNature" value="${user.additional.companyNature}" />
            		<div id="chooseCompanyNature" class="fcontrol" title="请选择您的单位性质" style="width:250px;height:100px;display:none">
				</div>
            	<div id='divcompayNature' style='display:none'>
<table style="width:450px;">
<tr>
	<td><a href="#chooseCompanyNature" onclick="companyNature('房地产公司')">房地产公司</a></td>
	<td><a href="#chooseCompanyNature" onclick="companyNature('设计院所')">设计院所</a></td>
	<td><a href="#chooseCompanyNature" onclick="companyNature('施工企业')">施工企业</a></td>
	<td><a href="#chooseCompanyNature" onclick="companyNature('监理公司')">监理公司 </a></td>
	<td><a href="#chooseCompanyNature" onclick="companyNature('建材/设备厂商')">建材/设备厂商 </a></td>
</tr>
<tr>
	<td><a href="#chooseCompanyNature" onclick="companyNature('生产制造')">生产制造</a></td>
	<td><a href="#chooseCompanyNature" onclick="companyNature('咨询公司')">咨询公司  </a></td>
	<td><a href="#chooseCompanyNature" onclick="companyNature('政府/社团')">政府/社团 </a></td>
	<td><a href="#chooseCompanyNature" onclick="companyNature('学校/科研')">学校/科研</a></td>
	<td><a href="#chooseCompanyNature" onclick="companyNature('媒体')">媒体 </a></td>
</tr>
<tr>
	<td><a href="#chooseCompanyNature" onclick="companyNature('其他')">其他</a></td>
	<td></td>
	<td></td>
	<td></td>
	<td></td>
</tr>
</table>
            	</div>
            </td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td height="40" align="right">手&nbsp;&nbsp;&nbsp;&nbsp;机：</td>
            <td height="40"><input type="text" name="phone" id="phone" value="${user.phone}" /></td>
            <td height="40" class="login_tishi"><label for="phone" class="error"></label>  请正确填写，手机是取回密码的重要手段</td>
          </tr>
          <tr>
            <td height="40" align="right">Q&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Q：</td>
            <td height="40"><input type="text" name="additional.qq" id="additional.qq" value="${user.additional.qq}" /></td>
            <td height="40" class="login_tishi">为了朋友可以更好的联系您</td>
          </tr>
          <tr>
            <td height="40" align="right">简&nbsp;&nbsp;&nbsp;&nbsp;介：</td>
            <td height="40" colspan="2"><textarea name="additional.brief" id="additional.brief" cols="45" rows="5">${user.additional.brief}</textarea><span class="login_tishi"> 最多200字</span></td>
            
          </tr>
          <tr>
            <td align="right">&nbsp;</td>
            <td height="80" colspan="2"><input type="image" src="images/personal/person_14.gif" name="userInfo" id="userInfo" value="提交" onclick="formUserSubmit();"/></td>
          </tr> 
        </table>
</form>
<!--添加教育信息-->
<form id="formEdu" name="formEdu" method="post" action="/personCenterSaveEduInfo">  
      <table style="display:none" id="con_one_2" width="700" border="0" cellpadding="0" cellspacing="0" class="person_info_add hover">
         <c:forEach  var="ED"  items="${educational}"> 
		    <tr id="edu${ED.id}">
	            <td  height="40" colspan="3" valign="top">
	            <div class="person_info_show"  id="list${ED.id}"> 
		            <table width="670" border="0" cellpadding="0" cellspacing="0" >
		              <tr>
		                <td height="30" class="person_info_add hover">
		                	<table width="670" border="0" cellpadding="0" cellspacing="0">
		                 		 <tr>
				                    <td width="16" height="30" align="center" background="${pageContext.request.contextPath}/images/personal/daxue_btn_15.gif" class="hover person_info_add">&nbsp;</td>
				                    <td width="248" background="${pageContext.request.contextPath}/images/personal/daxue_btn_15.gif" class="hover person_info_add"><strong><c:out value="${ED.schoolName}"/></strong></td>
				                    <td width="373" align="right" valign="middle" background="${pageContext.request.contextPath}/images/personal/daxue_btn_15.gif" class="person_info_add hover">
					                   <a href="#">
					                    	<img id="img_${ED.id}" onclick="showEducation('${ED.id}')" src="${pageContext.request.contextPath}/images/personal/college_21.png" />
					                   </a> 
					                    <a href="javascript:deleteEducation('${ED.id}')">
					                    	<img  src="${pageContext.request.contextPath}/images/personal/college_23.png" />
					                    </a>
				                   </td>
				                   <td width="33" align="center" background="${pageContext.request.contextPath}/images/personal/daxue_btn_15.gif" class="person_info_add hover">
				                   		<a  href="#"><img onclick="expandEducation('${ED.id}')"  src="${pageContext.request.contextPath}/images/personal/daxue_btn_18.gif" width="22" height="22" /></a>
				                   </td>
				                 </tr> 
		               		 </table>
	               		 </td>
	               		</tr> 
	               		<tr style="display:">
			                 <td>  
			                 	 <div id="show${ED.id}">
			                 	 	<table>
			                 			<tr>
			                 				<td>入学年份:</td>
			                 				<td>${ED.inSchool}</td>
			                 			</tr>
			                 			<tr>
			                 				<td>教育程度:</td>
			                 				<td>${ED.schoolType}</td>
			                 			</tr>
			                 			<tr>
			                 				<td>院系:</td>
			                 				<td>${ED.faculty}</td>
			                 			</tr>
			                 			<tr>
			                 				<td>学校地址:</td>
			                 				<td>${ED.area}</td>
			                 			</tr>
			                 		</table> 
			                 	 </div>
			                 	 <div id="hide${ED.id}" style="display:none">
			                 	 	<input type="hidden" name="schoolName${ED.id}" id="schoolName${ED.id}" value="${ED.schoolName}" />
			                 	 	<table>
			                 			<tr>
			                 				<td>入学年份:</td>
			                 				<td>
			                 					<input name="inSchool${ED.id}" id="inSchool${ED.id}" type="text" value="${ED.inSchool}" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy年',isShowToday:false,readOnly:true})" class="Wdate"/
			                 				</td>
			                 			</tr>
			                 			<tr>
			                 				<td>教育程度:</td>
			                 				<td>
			                 					<select  name="schoolType${ED.id}" id="schoolType${ED.id}">
								              		<option>${ED.schoolType}</option>
								              		<option>大学</option>
								              		<option>高中</option>
								              		<option>中专技校</option>
								              		<option>初中</option>
								              		<option>小学</option>
								           		 </select>
			                 				</td>
			                 			</tr>
			                 			<tr>
			                 				<td>院系:</td>
			                 				<td><input type="text" name="faculty${ED.id}" id="faculty${ED.id}" value="${ED.faculty}"/></td>
			                 			</tr>
			                 			<tr>
			                 				<td>学校地址:</td>
			                 				<td>
			                 					<textarea type="text" name="area${ED.id}" id="area${ED.id}" >${ED.area}</textarea>
			                 				</td>
			                 			</tr>
			                 			<tr>
			                 				<td colspan='2'>
			                 					<input type="button" class="order_btn" value="保 存" name="edu${ED.id}" id="edu${ED.id}" onclick="eduSubForm('${ED.id}')"/>
			                 				</td>
			                 			</tr>
			                 		</table> 
			                 	 </div>  
			                 </td>
			           </tr> 
		             </table>
	             </td>
	         </tr>  
		 </c:forEach>	 
         <tr style="display:">
                 <td align="left">  
                 		  <input type="hidden" id="additionalId" name="additionalId" value=${user.id} />
                 		  <table  width="700" border="0" cellpadding="0" cellspacing="0" class="person_info_add">
					          <tr>
					            <td height="40" align="right">教育程度：</td>
					            <td height="40">
									<select name="schoolType" id="schoolType">
					              		<option>大学</option>
					              		<option>高中</option>
					              		<option>中专技校</option>
					              		<option>初中</option>
					              		<option>小学</option>
					           		 </select>
								</td>
					            <td height="40">&nbsp;</td>
					          </tr>
					          <tr>
					            <td height="40" align="right">入学年份：</td>
					            <td height="40">
					           		 <input name="inSchool" id="inSchool" type="text" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy年',isShowToday:false,readOnly:true})" class="Wdate"/>
					            </td>
					            <td height="40" class="login_tishi"></td>
					          </tr>
					          <tr>
					            <td height="40" align="right">学校名称：</td>
					            <td height="40"><input type="text" name="schoolName" id="schoolName" />
					            <span class="login_tishi" ><label for="schoolName" class="error"></label>  </span></td>
					            <td height="40" class="login_tishi"></td>
					          </tr>
					          <tr>
					            <td height="40" align="right">院系：</td>
					            <td height="40"><input type="text" name="faculty" id="faculty" /></td>
					            <td height="40">&nbsp;</td>
					          </tr>
					          <tr>
					            <td height="40" align="right">学校地址：</td>
					            <td height="40">
					            	<textarea name="area" id="area" cols="45" rows="5"></textarea>
					            </td>
					            <td height="40">&nbsp;</td>
					          </tr>
					          <tr>
					            <td align="right">&nbsp;</td>
					            <td height="80" colspan="2"><input type="button" class="order_btn" value="保 存" name="formEdu" id="formEdu"   onclick="formEduSubmit();"/></td>
					          </tr>
					        </table> 
                 </td>
               </tr>
            </table> 
        
</form>

<form id="formOccup" name="formOccup" method="post" action="/personCenterFormOccupInfo">
		
		
		<table style="display:none" id="con_one_3" style="display:none" width="700" border="0" cellpadding="0" cellspacing="0" class="person_info_add">
			<c:forEach  var="OC"  items="${occup}">
		    <tr id="occ${OC.id}">
	            <td height="40" colspan="3" valign="top">
	            <div class="person_info_show"  id="list${OC.id}"> 
		            <table width="670" border="0" cellpadding="0" cellspacing="0" >
		              <tr>
		                <td height="30" class="person_info_add hover">
		                	<table width="670" border="0" cellpadding="0" cellspacing="0">
		                 		 <tr>
				                    <td width="16" height="30" align="center" background="${pageContext.request.contextPath}/images/personal/daxue_btn_15.gif" class="hover person_info_add">&nbsp;</td>
				                    <td width="248" background="${pageContext.request.contextPath}/images/personal/daxue_btn_15.gif" class="hover person_info_add"><strong><c:out value="${OC.unitName}"/></strong></td>
				                    <td width="373" align="right" valign="middle" background="${pageContext.request.contextPath}/images/personal/daxue_btn_15.gif" class="person_info_add hover">
					                    <a href="#">
					                    	<img id="img_${OC.id}" onclick="showEducation('${OC.id}')" src="${pageContext.request.contextPath}/images/personal/college_21.png" />
					                   </a> 
					                    <a href="javascript:deleteOccupInfo('${OC.id}')">
					                    	<img  src="${pageContext.request.contextPath}/images/personal/college_23.png" />
					                    </a>
				                   </td>
				                   <td width="33" align="center" background="${pageContext.request.contextPath}/images/personal/daxue_btn_15.gif" class="person_info_add hover">
				                   		<a  href="#"><img onclick="expandEducation('${OC.id}')"  src="${pageContext.request.contextPath}/images/personal/daxue_btn_18.gif" width="22" height="22" /></a>
				                   </td>
				                 </tr> 
		               		 </table>
	               		 </td>
	               		</tr> 
	               		<tr style="display:">
			                 <td>  
			                 		<div id="show${OC.id}">
				                 		<table>
				                 			<tr>
				                 				<td>单位性质:</td>
				                 				<td>${OC.unitProperties}</td>
				                 			</tr>
				                 			<tr>
				                 				<td>担任职位:</td>
				                 				<td>${OC.department}</td>
				                 			</tr>
				                 			<tr>
				                 				<td>工作时间:</td>
				                 				<td>${OC.beginTime}~${OC.endTime}</td>
				                 			</tr>
				                 			<tr>
				                 				<td>单位地址:</td>
				                 				<td>${OC.location}</td>
				                 			</tr>
				                 		</table>
			                 		</div>
			                 		<div id="hide${OC.id}"  style="display:none">
			                 			<table>
			                 			<tr>
			                 				<td>单位性质:</td>
			                 				<td>
			                 					<input name="unitProperties_${OC.id}" value="${OC.unitProperties}" type="text" id="unitProperties_${OC.id}"/>
			                 					<input name="unitName_${OC.id}" value="${OC.unitName}" type="hidden" id="unitName_${OC.id}"/>	
			                 				</td>
			                 			</tr>
			                 			<tr>
			                 				<td>担任职位:</td>
			                 				<td><input name="department_${OC.id}" value="${OC.department}" type="text" id="department_${OC.id}"/></td>
			                 			</tr>
			                 			<tr>
			                 				<td>工作时间:</td>
			                 				<td>
			                 					<input name="beginTime_${OC.id}" class="Wdate" value="${OC.beginTime}" type="text" id="beginTime_${OC.id}" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime_${OC.id}\')}',skin:'whyGreen',dateFmt:'yyyy.MM',isShowToday:false})"/>
			                 					~
			                 					<input name="endTime_${OC.id}" class="Wdate" value="${OC.endTime}" type="text" id="endTime_${OC.id}" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime_${OC.id}\')}',skin:'whyGreen',dateFmt:'yyyy.MM',isShowToday:false})"/>
			                 				</td>
			                 			</tr>
			                 			<tr>
			                 				<td>单位地址:</td>
			                 				<td>
			                 					<textarea id="location_${OC.id}" name="location_${OC.id}" cols="35" rows="3"   >${OC.location}</textarea>
			                 				</td>
			                 			</tr>
			                 			<tr>
								            <td align="right">&nbsp;</td>
								            <td height="80" colspan="2">
								            	<input type="button" class="order_btn" value="保 存" name="formoccp" id="formoccp" onclick="occpSubmit('${OC.id}');"/>
								            </td>
								        </tr>
			                 		</table>
			                 		</div>
			                 		 
			                 </td>
			           </tr> 
		             </table>
	             </td>
	         </tr>  
		 </c:forEach>	 
          <tr>
            <td height="40" align="right">单位名称：</td>
            <td height="40"><input type="text" name="unitName" id="unitName" /></td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td height="40" align="right">单位性质：</td>
            <td height="40"><input type="text" name="unitProperties" id="unitProperties" /></td>
            <td height="40" class="login_tishi"></td>
          </tr>
           <tr>
            <td height="40" align="right">部门/职位：</td>
            <td height="40"><input type="text" name="department" id="department" /></td>
            <td height="40">&nbsp;</td>
          </tr>
          <tr>
            <td height="40" align="right">工作时间：</td>
            <td height="40">
				<input name="beginTime" class="Wdate" value="${OC.beginTime}" type="text" id="beginTime" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',skin:'whyGreen',dateFmt:'yyyy.MM',isShowToday:false})"/>
			    ~
			    <input name="endTime" class="Wdate" value="${OC.endTime}" type="text" id="endTime" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',skin:'whyGreen',dateFmt:'yyyy.MM',isShowToday:false})"/>
			</td>
            <td height="40">&nbsp;</td>
          </tr>
          <tr>
            <td height="40" align="right">单位地址：</td>
            <td height="40">
	            <input type="hidden" id="additionalId" name="additionalId" value=${user.id} />
				
				<textarea id="location" name="location" cols="35" rows="5"></textarea>
			</td>
            <td height="40">&nbsp;</td>
          </tr>
          <tr>
            <td align="right">&nbsp;</td>
            <td height="80" colspan="2"><input type="button" class="order_btn" value="保 存" name="formOccup" id="formOccup"  onclick="formOccupSubmit();"/></td>
          </tr>
        </table>
</form>

<form id="formPro" name="formPro" method="post" action="/personCenterFormProjectInfo">
        <table style="display:none" id="con_one_4" style="display:none" width="700" border="0" cellpadding="0" cellspacing="0" class="person_info_add">
			
		<c:forEach  var="PRO"  items="${project}">
		    <tr id="pro${PRO.id}">
	            <td height="40" colspan="3" valign="top">
	            <div class="person_info_show"  id="list${PRO.id}"> 
		            <table width="670" border="0" cellpadding="0" cellspacing="0" >
		              <tr>
		                <td height="30" class="person_info_add hover">
		                	<table width="670" border="0" cellpadding="0" cellspacing="0">
		                 		 <tr>
				                    <td width="16" height="30" align="center" background="${pageContext.request.contextPath}/images/personal/daxue_btn_15.gif" class="hover person_info_add">&nbsp;</td>
				                    <td width="248" background="${pageContext.request.contextPath}/images/personal/daxue_btn_15.gif" class="hover person_info_add"><strong><c:out value="${PRO.name}"/></strong></td>
				                    <td width="373" align="right" valign="middle" background="${pageContext.request.contextPath}/images/personal/daxue_btn_15.gif" class="person_info_add hover">
					                     <a href="#">
					                    	<img id="img_${PRO.id}" onclick="showEducation('${PRO.id}')" src="${pageContext.request.contextPath}/images/personal/college_21.png" />
					                   </a> 
					                    <a href="javascript:deleteProject('${PRO.id}')">
					                    	<img  src="${pageContext.request.contextPath}/images/personal/college_23.png" />
					                    </a>
				                   </td>
				                   <td width="33" align="center" background="${pageContext.request.contextPath}/images/personal/daxue_btn_15.gif" class="person_info_add hover">
				                   		<a  href="#"><img onclick="expandEducation('${PRO.id}')"  src="${pageContext.request.contextPath}/images/personal/daxue_btn_18.gif" width="22" height="22" /></a>
				                   </td>
				                 </tr> 
		               		 </table>
	               		 </td>
	               		</tr> 
	               		<tr style="display:">
			                 <td>  
			                 		<div id="show${PRO.id}">
			                 			<table>
				                 			<tr>
				                 				<td>职位:</td>
				                 				<td>${PRO.region}</td>
				                 			</tr>
				                 			<!--tr>
				                 				<td>时间:</td>
				                 				<td></td>
				                 			</tr-->
				                 			<tr>
				                 				<td>工作地点:</td>
				                 				<td>${PRO.addr}</td>
				                 			</tr> 
			                 			</table> 
			                 		</div>
			                 		<div id="hide${PRO.id}" style="display:none">
			                 			<table>
				                 			<tr>
				                 				<td>职位:</td>
				                 				<td>
				                 					<input type="hidden" name="proName_${PRO.id}" id="proName_${PRO.id}" value="${PRO.name}"/>
				                 					<input type="text" name="region_${PRO.id}" id="region_${PRO.id}" value="${PRO.region}"/>
				                 				</td>
				                 			</tr>
				                 			<!--tr>
				                 				<td>时间:</td>
				                 				<td>
				                 					<input type="text" name="buildUnit_${PRO.id}" id="buildUnit_${PRO.id}" value="${PRO.buildUnit}"/>
				                 				</td>
				                 			</tr-->
				                 			<tr>
				                 				<td>工作地点:</td>
				                 				<td>
				                 					<textarea name="addr_${PRO.id}" id="addr_${PRO.id}">${PRO.addr}</textarea>
				                 				</td>
				                 			</tr> 
				                 			<tr>
									            <td align="right">&nbsp;</td>
									            <td height="80" colspan="2">
									            	<input type="button" class="order_btn" value="保 存" name="formoccp" id="formoccp" onclick="projectSubmit('${PRO.id}');"/>
									            </td>
									        </tr>
			                 			</table> 
			                 		</div> 
			                 </td>
			           </tr> 
		             </table>
	             </td>
	         </tr>  
		 </c:forEach>	
         
          <tr>
            <td height="40" align="right">项目名称：</td>
            <td height="40"><input type="text" name="name" id="name" /></td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td height="40" align="right">部门/职位：</td>
            <td height="40"><input type="text" name="region" id="region" /></td>
            <td height="40" class="login_tishi"></td>
          </tr>
          <tr>
            <td height="40" align="right"><input type="hidden" id="additionalId" name="additionalId" value=${user.id} />
            		工作地点：
            </td>
            <td height="40">
				 <textarea name="addr" id="addr" cols="35" rows="5"></textarea>
			</td>
            <td height="40">&nbsp;</td>
          </tr>
          <!--tr>
            <td height="40" align="right">起止时间：</td>
            <td height="40">
				<input name="beginTimepro" class="Wdate" value="${OC.beginTime}" type="text" id="beginTimepro" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTimepro\')}',skin:'whyGreen',dateFmt:'yyyy.MM',isShowToday:false})"/>
			    ~
			    <input name="endTimepro" class="Wdate" value="${OC.endTime}" type="text" id="endTimepro" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTimepro\')}',skin:'whyGreen',dateFmt:'yyyy.MM',isShowToday:false})"/>
			</td>
            <td height="40">&nbsp;</td>
          </tr-->
          <tr>
            <td align="right">&nbsp;</td>
            <td height="80" colspan="2"><input type="button" class="order_btn" value="保 存" name="fromProject" id="fromProject"  onclick="formProjectSubmit();"/></td>
          </tr>
        </table>
</form>
        </div>
    </div>
     <div class="person_right right">
    	<jsp:include page="right.jsp" ></jsp:include>
        <!--div class="info_youhui">
       	  <div class="info_youhui_title">继续完善可以获得以下优惠或特权：</div>
            <ul>
           	  <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
            </ul>
        </div>
        <div class="new_youhui">
        	<div class="new_youhui_title">最新优惠<span class="new_youhui_title_en"> / latest preferential</span> </div>
            <ul>
            	<li><a href="#"><img src="images/personal/person_11.gif" width="53" height="64" /></a></li>
                <li><a href="#"><img src="images/personal/person_11.gif" width="53" height="64" /></a></li>
                <li><a href="#"><img src="images/personal/person_11.gif" width="53" height="64" /></a></li>
            </ul>
        </div-->
    </div>
    <div class="clear"></div>
    </div>
  </div>
    <div class="search_down"></div>
    <div class="clear"></div>
    <div class="foot">
		<!-- 联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客| <a target="_blank" href="/map.jsp">网站地图</a>  天佑网版权所有　©1997-2013　粤ICP备20090191号  -->
		<jsp:include page="${pageContext.request.contextPath}/bottom.jsp" />
	</div>
</div>
<div class="btn_fd">
	<a href="#"></a>
	<a href="#top"><img title="返回顶部" class="shang20" src="images/btnfd_13.gif" /></a>
</div>
<script src="js/formValidator-4.1.1.js" type="text/javascript"></script>
<script src="js/person.js" type="text/javascript"></script>
<script  type="text/javascript">
function startUpload(){
	$('#uploadify').uploadify('upload','*');
}
$(function(){
		jQuery.validator.addMethod("isMobile", function(value, element) {       
		     var length = value.length;   
		    var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;   
		   return this.optional(element) || (length == 11 && mobile.test(value));       
		}, "<font color='red'>请正确填写手机号码</font>");    
		
 	$("#uploadify").uploadify({
 			'fileSizeLimit' : '1024KB',
			'swf':'${pageContext.request.contextPath}/uploadify/uploadify.swf',
			'uploader':'${pageContext.request.contextPath}/form/upload/fdfs',
			'queueID':'fileQueue',
			'buttonText': '选择头像',  
			'height': 20,   
			'multi':false,
    		'width': 100, 
    		'fileTypeDesc' : '图片文件',
    		'fileTypeExts':'*.jpg;*.gif;*.png;',
    		'auto': true,
			'onUploadSuccess':function(file,data,response){
	        	$('#person_info_head').attr("src",data);
				$('#headUrl').val(data.replace("${httpAddress}",""));
			},
			'onUploadError':function(file,errorCode,errorMsg,errorString){
				$.messager.alert('错误','头像上传出错 ,错误代码:'+errorCode); 
			}
		});
    	
});
</script>
</body>
</html>
 
