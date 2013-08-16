<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登陆页面</title>
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#333333">
  <tr>
    <td bgcolor="#FFFFFF"><table width="1000" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
      <tr>
        <td width="826" bgcolor="#FFFFFF">&nbsp;</td>
        <td width="74" bgcolor="#FFFFFF">登陆</td>
        <td width="100" bgcolor="#FFFFFF">注册</td>
      </tr>
    </table></td>
  </tr>
</table>
<form id="form1" name="form1" method="post" action="">
  <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="132" height="70"><strong>天佑网</strong></td>
      <td width="168"><input type="text" name="textfield" id="textfield" /></td>
      <td width="700"><input type="submit" name="button" id="button" value="提交" /></td>
    </tr>
  </table>
</form>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#000000">
  <tr>
    <td align="center" bgcolor="#FFFFFF"><em><strong>分部分项</strong></em></td>
    <td align="center" bgcolor="#FFFFFF"><em><strong>强制性条文</strong></em></td>
    <td align="center" bgcolor="#FFFFFF"><em><strong>核心条文</strong></em></td>
    <td align="center" bgcolor="#FFFFFF"><em><strong>安全条文</strong></em></td>
    <td align="center" bgcolor="#FFFFFF"><em><strong>法律法规</strong></em></td>
    <td height="30" align="center" bgcolor="#FFFFFF"><em><strong>标准规范</strong></em></td>
  </tr>
</table>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="121">你的位置：</td>
    <td width="545" height="40">首页》注册</td>
    <td width="334">&nbsp;</td>
  </tr>
</table>
<form id="form2" name="form2" method="post" action="">
  <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="100" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
        <tr>
          <td height="30" align="center" bgcolor="#FFFFFF"><strong><em>功能列表</em></strong></td>
          </tr>
        <tr>
          <td height="30" align="center" bgcolor="#FFFFFF"><p>个人空间</p></td>
          </tr>
        <tr>
          <td height="60" align="center" bgcolor="#FFFFFF"><strong>个人信息</strong></td>
          </tr>
        <tr>
          <td height="30" align="center" bgcolor="#FFFFFF">点卡选购</td>
          </tr>
        <tr>
          <td height="30" align="center" bgcolor="#FFFFFF">已购点卡</td>
          </tr>
        <tr>
          <td height="30" align="center" bgcolor="#FFFFFF">我的订单</td>
          </tr>
        <tr>
          <td height="30" align="center" bgcolor="#FFFFFF">充值</td>
        </tr>
        <tr>
          <td height="30" align="center" bgcolor="#FFFFFF">项目信息</td>
        </tr>
        <tr>
          <td height="30" align="center" bgcolor="#FFFFFF">安全中心</td>
        </tr>
      </table></td>
      <td width="661" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">

        <tr>
          <td width="86%" height="50"><em><strong>个人信息</strong></em></td>
          <td width="14%"><input type="submit" name="button2" id="button2" value="提交" /></td>
        </tr>
        <tr>
          <td height="50" colspan="2"><table width="100%" cellpadding="0" cellspacing="0">
            <tr>
              <td height="30">Email</td>
              <td height="30">${user.username}</td>
            </tr>
            <tr>
              <td height="30">Mobile</td>
              <td height="30">${user.additional.mobile}</td>
            </tr>
            <tr>
              <td height="30">Sex</td>
              <td height="30">${user.additional.sex}</td>
            </tr>
            <tr>
              <td height="30">Location</td>
              <td height="30">${user.additional.location}</td>
            </tr>
            <tr>
              <td height="30">专业</td>
              <td height="30">${user.additional.location}</td>
            </tr>
          </table></td>
        </tr>

        
      </table></td>
    </tr>
  </table>
</form>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#000000">
  <tr>
    <td align="center" bgcolor="#FFFFFF">版权所有 v1.1</td>
  </tr>
</table>
</body>
</html>
