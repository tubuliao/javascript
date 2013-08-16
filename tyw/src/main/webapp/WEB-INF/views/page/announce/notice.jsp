<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>天佑网系统公告</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link rel=stylesheet type=text/css href="/css/search.css" />

</head>

<body>
<div class="ggaopage">
<img src="/images/home/gglm_03.gif" />
<div class="ggao_list">
	<h2 align="center">${annTitle}</h2>
    <h4>发布时间：${annCreateDate}</h4>
    <p>
    ${annContent}
    </p>
	</div>
</div>
</body>
</html>
