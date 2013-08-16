<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="css/mainstyle.css" />
<link type="text/css" rel="stylesheet" href="css/jsandcss/happysysLaw.css" />
<!-- 日期控件 -->
<script type="text/javascript" src="../../common/datepicker/WdatePicker.js"></script>

<title>普通会员</title>

</head>

<body>

<form name="additional" method="POST" modelAttribute="additional" >

  <table class="form-table"  style="width: 96%;" align="center">

    <tr> 
      <td class="form-table-label">邮箱:</td>
      <td colspan="3"><label> 
        <input type="text" name="email">
        </label></td>
    </tr>
    <tr> 
      <td class="form-table-label">手机号:</td>
      <td colspan="3"><label> 
        <input type="text" name="mobile">
        </label></td>
    </tr>
    <tr> 
      <td class="form-table-label">性别:</td>
      <td colspan="3"><label> </label>
        <input type="text" name="sex"></td>
    </tr>
    
    <tr> 
      <td class="form-table-label">住址:</td>
      <td colspan="3"><label>
      <input type="text" name="location" />
      </label></td>
    </tr>
	<tr> 
      <td class="form-table-label">生日:</td>
      <td colspan="3"><label>
      <input type="text" name="birthday" />
      </label></td>
    </tr>

  </table>

</form>

</body>
</html>
