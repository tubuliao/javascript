<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/body.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
<!--


//查找
function search(){
	  document.forms[0].action="placeInfo_view.do?info.policeStation=<s:property value='info.policeStation' />";
	  document.forms[0].submit();
}

 function selectall(me)
   {
     if(me.checked == true)
     {
       if(document.forms[0].id.length == undefined)
       {
     		document.forms[0].id.checked=true;
       }
       if(document.forms[0].id.length != undefined)
       {     
	        for(i=0;i<document.forms[0].id.length;i++){
	   	      document.forms[0].id[i].checked=true;
            }
       }
     }else{
        if(document.forms[0].id.length == undefined)
        {
     		document.forms[0].id.checked=false;
        }
        if(document.forms[0].id.length != undefined)
        {     
	        for(i=0;i<document.forms[0].id.length;i++){
	   	      document.forms[0].id[i].checked=false;
  	        }
  	     }
     }
   }


   function add()
   {
      var checknum=checkboxnum();
      if(checknum==0){
         document.forms[0].action="placeInfo_add.do?info.policeStation=<s:property value='info.policeStation' />";
	     document.forms[0].submit();
	  }
	  else{
	     alert("不能选择编目");
	  }      
   }

   function modify()
   {
     if(check()==true)
     {
	   var num= checkboxnum();
	   if(num==1) 
	    {
	     
	      location.href='editUser.html';
	    }else{
	      alert("只能选择一条编目！"); 
	    }
     }
   }
 
   function check()
   {
     var chnum=checkboxnum();
     if(chnum==0)
     {
       alert("没有选择编目！");
       return false;
     }  
     return true;
   }

   function checkboxnum()
   {
     var box=document.forms[0].id;
     var chnum=0;
     if(box.length == undefined && box.checked == true)
     {
 	   chnum = 1;
     }
     if(box.length != undefined)
     {
	    for (var i=0;i<box.length;i++)
	    {
	      var bx=document.forms[0].id[i];
	      if(bx.checked==true) chnum=chnum+1;
	    }
     }
      return chnum;
    }
   
   function doDel()
   { 
     if(check()==true){
        if(confirm("确定要删除吗？")==true)
        {
          alert("删除成功！");
		  document.forms[0].submit();
        }
     } 
   }
   
     
   function find(placeid)
   {
		  	window.showModalDialog("viewUser.html","","dialogWidth:850px; dialogHeight:660px;center:yes;help:no;resizable:no;status:no");
   } 
//-->
</script>
<title>新闻资讯列表</title>
</head>

<body>

<form name="" action="" >

  <table border="1" align="center" cellpadding="0" cellspacing="0" bordercolorlight="#185AB4" bordercolordark="#FFFFFF">
    <tr>
     <td height="25" colspan="13" align="center" class="title1">新闻资讯列表</td>
    </tr>
    <tr > 
      <td width="10%" class="title2"><input type="checkbox" onClick="selectall(this)">
        全选</td>
      <td width="20%" class="title2" align="center">序号</td>
      <td width="30%" class="title2" align="center">标题</td>
      <td width="30%" class="title2" align="center">状态</td>
      <td width="10%" class="title2" align="center">操作</td>
    </tr>
	<c:forEach items="${page.content}" var="news" varStatus="xh">
    <tr class="list-table-content1"> 
      <td align="center"><input type="checkbox" name="id" id="id" value="${news.id}"></td>
      <td align="center">${xh.count}</td>
      <td align="center">&nbsp;${news.title}</td>
      <td align="center">&nbsp;${news.state?'已审核':'未审核'}</td>
      <td align="center">
           <img src="../../images/dele.gif" alt="删除" title="删除" onclick="javascript:location.href='/news/delete/${news.id}'"/>|
	       <img src="../../images/edit.gif" alt="编辑" title="编辑" onclick="javascript:location.href='/news/update/${news.id}'"/>|
	       <img src="../../images/info.gif" alt="查看" title="查看" onclick="javascript:location.href='/news/details/${news.id}'"/>
	  </td>
    </tr>
   </c:forEach>
  </table> 
</form>

<hr size="3" />
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listTableTurnPageInfo">
			<tr>

				<td align="right">
					<page:bt />
				</td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="listTableTurnPageInfo">
			<tr>

				<td align="right">
					<form name='pageable' method='POST'>
				<input id='pageNumber' type='hidden' name='page.page' value='1'>
				<input id='pageSize' type='hidden' name='page.size' value='20'>
	每页<input type='text' size='2' value='${page.size}' onChange="document.getElementById('pageNumber').value='1';document.getElementById('pageSize').value=this.value;this.form.submit();">
             记录 | 共<font color=red>[${page.totalPages}]</font>页/<font color=red>[${page.totalElements}]</font>条记录 | | 转到第
	<select onChange="javascript:document.getElementById('pageNumber').value= this.value;document.pageable.submit();return false;"> 
			<c:forEach var="i" begin="1" end="${page.totalPages}">
			   <option value='${i}'>${i}</option>
			</c:forEach>
	 </select>页


</form>

				</td>
			</tr>
		</table>		
</body>
</html>
