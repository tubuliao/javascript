<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 

 <title>天佑网</title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/search.css"/>

<link rel="Bookmark"  type="image/x-icon"  href="${pageContext.request.contextPath}/favicon.ico"/>
<link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico" />
<link rel="icon" href="${pageContext.request.contextPath}/favicon.ico" type="image/gif"/>
 <body>
<!-- 只读权限start -->
	<sec:authorize ifAllGranted="BACKGROUND_ONLYREAD">
		<input type="hidden" id="backGround_read" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_ONLYREAD">
		<input type="hidden" id="backGround_read" value=""/>
	</sec:authorize>
<!-- 只读权限end -->

<!-- segment权限start -->
<!-- 新增权限start -->
	<sec:authorize ifAllGranted="BACKGROUND_ADD">
		<input type="hidden" id="backGround_add" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_ADD">
		<input type="hidden" id="backGround_add" value=""/>
	</sec:authorize>
<!-- 新增权限end -->

<!-- 修改权限start -->
	<sec:authorize ifAllGranted="BACKGROUND_MODIFY">
		<input type="hidden" id="backGround_modify" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_MODIFY">
		<input type="hidden" id="backGround_modify" value=""/>
	</sec:authorize>
<!-- 修改权限end -->

<!-- 删除权限start -->
	<sec:authorize ifAllGranted="BACKGROUND_REMOVE">
		<input type="hidden" id="backGround_remove" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_REMOVE">
		<input type="hidden" id="backGround_remove" value=""/>
	</sec:authorize>
<!-- 删除权限end -->

<!-- 摘要权限start -->
	<sec:authorize ifAllGranted="BACKGROUND_SUMMARY">
		<input type="hidden" id="backGround_summary" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_SUMMARY">
		<input type="hidden" id="backGround_summary" value=""/>
	</sec:authorize>
<!-- 摘要权限end -->

<!-- 预览切片权限start -->
	<sec:authorize ifAllGranted="BACKGROUND_PREVIEW">
		<input type="hidden" id="backGround_preview" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_PREVIEW">
		<input type="hidden" id="backGround_preview" value=""/>
	</sec:authorize>
<!-- 预览切片权限end -->

<!-- Excel导入权限start -->
	<sec:authorize ifAllGranted="BACKGROUND_IMPORTING">
		<input type="hidden" id="backGround_importing" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_IMPORTING">
		<input type="hidden" id="backGround_importing" value=""/>
	</sec:authorize>
<!-- Excel导入权限end -->

<!-- 审批驳回权限start -->
	<sec:authorize ifAllGranted="BACKGROUND_APPROVAL_REJECT">
		<input type="hidden" id="backGround_approval_reject" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_APPROVAL_REJECT">
		<input type="hidden" id="backGround_approval_reject" value=""/>
	</sec:authorize>
<!-- 审批驳回权限end -->

<!-- 导出Excel权限start -->
	<sec:authorize ifAllGranted="BACKGROUND_EXPROT">
		<input type="hidden" id="backGround_exprot" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_EXPROT">
		<input type="hidden" id="backGround_exprot" value=""/>
	</sec:authorize>
<!-- 导出Excel权限end -->
<!-- segment权限end -->
<!-- ------------------------------------------------------------------------------------------------------------------------------------------------- -->
<!-- form权限start -->
<!-- 同步附件权限start -->
	<sec:authorize ifAllGranted="BACKGROUND_SYNCHRO">
		<input type="hidden" id="backGround_synchro" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_SYNCHRO">
		<input type="hidden" id="backGround_synchro" value=""/>
	</sec:authorize>
<!-- 同步附件权限end -->

<!-- 单表复制权限start -->
	<sec:authorize ifAllGranted="BACKGROUND_FORM_COPY">
		<input type="hidden" id="backGround_form_copy" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_FORM_COPY">
		<input type="hidden" id="backGround_form_copy" value=""/>
	</sec:authorize>
<!-- 单表复制权限end -->
<!-- form权限end -->
	
	
</body>
 