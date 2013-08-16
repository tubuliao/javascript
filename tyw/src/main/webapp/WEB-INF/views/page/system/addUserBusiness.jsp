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

<title>会员</title>
</head>

<body>

<form name="agent" method="POST" modelAttribute="agent" >

  <table  class="form-table"  style="width: 96%;" align="center">

    <tr> 
      <td class="form-table-label">渠道商名称:</td>
           <td colspan="3"><label> 
        <input type="text" name="name">
        </label></td>
    </tr>
    <tr> 
      <td class="form-table-label">地址:</td>
      <td colspan="3"><label> 
        <input type="text" name="addr">
        </label></td>
    </tr>
    <tr> 
      <td class="form-table-label">法人代表:</td>
      <td colspan="3"><label> </label>
        <input type="text" name="linkman"></td>
    </tr>
   
    <tr> 
      <td class="form-table-label">联系电话:</td>
      <td colspan="3"><label>
      <input type="text" name="phone" />
      </label></td>
    </tr>
	<tr> 
      <td class="form-table-label">传真:</td>
      <td colspan="3"><label>
      <input type="text" name="fax" />
      </label></td>
    </tr>
	<tr> 
      <td class="form-table-label">邮编:</td>
      <td colspan="3"><label>
      <input type="text" name="zip" />
      </label></td>
    </tr>
	<tr> 
      <td class="form-table-label">电子邮箱:</td>
      <td colspan="3"><label>
      <input type="text" name="email" />
      </label></td>
    </tr>
  </table>

</form>

</body>
</html>
