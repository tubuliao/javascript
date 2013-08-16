<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/body.css" rel="stylesheet" type="text/css" />

<!-- 日期控件 -->
<script type="text/javascript" src="common/datepicker/WdatePicker.js"></script>
<link rel="stylesheet" href="kindedit/themes/default/default.css" />
	<link rel="stylesheet" href="kindedit/plugins/code/prettify.css" />
	<script charset="utf-8" src="kindedit/kindeditor.js"></script>
	<script charset="utf-8" src="kindedit/lang/zh_CN.js"></script>
	<script charset="utf-8" src="kindedit/plugins/code/prettify.js"></script>
	<link href="css/body.css" rel="stylesheet" type="text/css" />
	<script>
		KindEditor.ready(function(K) {
			var editor1 = K.create('textarea[name="content"]', {
				cssPath : 'plugins/code/prettify.css',
				uploadJson : '/upload_json',
				fileManagerJson : '/file_manager_json',
				allowFileManager : true,
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['standard'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['standard'].submit();
					});
				}
			});
			prettyPrint();
		});
	</script>
<title>标准</title>
</head>

<body>

<form name="news" method="POST" modelAttribute="news" >

  <table width="80%" border="1" align="center" cellpadding="0" cellspacing="0" bordercolorlight="#185AB4" bordercolordark="#FFFFFF">
    <tr>
    <td height="25" colspan="4" align="center" class="title1">新增新闻资讯</td>
  	</tr>
    <tr> 
      <td  class="title2" width="24%">&nbsp;&nbsp;标题:</td>
      <td width="76%"><label> 
        <input type="text" name="title">
        </label></td>
    </tr>
    <tr> 
      <td  class="title2">&nbsp;&nbsp;内容:</td>
      <td >
      	<textarea name="content" cols="90" rows="17" style="visibility:hidden;"></textarea>
        <span class="STYLE1">*</span></td>
    </tr>
    
  </table>
  <table width="80%" align="center">
    <tr>
      <td height="17" align="center">
	    <button class="form-table-button"  style="width: 80px;" type="submit">保存&nbsp;&nbsp;</button>	   
    </tr>
</table>

</form>

</body>
</html>
