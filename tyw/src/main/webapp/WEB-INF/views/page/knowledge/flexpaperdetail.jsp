<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>知识查询</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link rel=stylesheet type=text/css href="/css/search.css"/>
	<link rel=stylesheet type=text/css href="/css/common.css"/>
		<script
			src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js"
			type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/clausetable.js"></script>

			<script src="${pageContext.request.contextPath}/js/ajaxbase.js"
			type="text/javascript"></script>
		<link
			href="${pageContext.request.contextPath}/ztree3.5/zTreeStyle/zTreeStyle.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/ztree3.5/js/jquery.ztree.core-3.5.js"></script>
		<script src="${pageContext.request.contextPath}/js/search_ajax.js" type="text/javascript"></script>
		<script type="text/javascript" src="/TywViewer/aserts/jquery.extensions.min.js"></script>
		<script type="text/javascript" src="/TywViewer/aserts/flexpaper.js"></script>
		<script type="text/javascript" src="/TywViewer/aserts/flexpaper_handlers.js"></script>
		<script type="text/javascript">
			var province ="";
			$(document).ready(function() {
				$('#chooseArea').dialog({
					autoOpen : false,
					width : 600,
					dialogClass : "my-dialog",
					modal : false
				});
				getProvince("0");
				$("#province").click(function() {
					$('#chooseArea').dialog('open');
				});
				
				 
				var fpaths=[${pageurlString}];
		 	     
				var swfconfig={
				 config : {
                     SwfFile : fpaths[0],
					 Scale : 0.8, 
					 ZoomTransition : 'easeOut',
					 ZoomTime : 0.5, 
					 ZoomInterval : 0.1,
					 FitPageOnLoad : true,
					 FitWidthOnLoad : true, 
					 FullScreenAsMaxWindow : false,
					 ProgressiveLoading : true,
					 MinZoomSize : 0.5,
					 MaxZoomSize : 3,
					 SearchMatchAll : false,
					 ViewModeToolsVisible : true,
					 ZoomToolsVisible : true,
					 NavToolsVisible : true,
					 CursorToolsVisible : true,
					 SearchToolsVisible : true,
					 jsDirectory : '/TywViewer/aserts/',
					 JSONDataType : 'jsonp',
					 WMode : 'opaque',
					 localeChain: 'zh_CN'
					 }
				};
				 
				$('#documentViewer').FlexPaperViewer(swfconfig);
				
			}); 
		
		</script>
</head>

<body>					
	<div class="body">
		<div>
			<jsp:include page="${pageContext.request.contextPath}/top.jsp" />
			<jsp:include page="/menu.jsp"></jsp:include>
			<jsp:include page="${pageContext.request.contextPath}/top_search_flex.jsp" />
		</div>
		<div class=" new_box">
			<div class="new_body">
  <div style="display: none" id="html_bak">
					<div id="data_bak">
						<ul class="bak_next" data=""
							style="position: relative; left: 214px;">
						</ul>
						<li onclick="getSourceList()" class="bak_li"><a href="#"
							class="a_click" data=''>标签1</a></li>
					</div>
					<div id="bak_ul"></div>
					<a index="" data="" class="path" href="#"></a>
				</div>
<div class="new_right_mulu left" style="width:750px; margin:10px 0 0 10px">
  <div class="mulu_list">

		  <div id="documentViewer" class="flexpaper_viewer" style="left:10px;top:10px;width:760px;height:700px;"></div>

						</div>
			  </div>
				<a id="url" href="${files.url }"><img style="margin:10px; float:right" src="/images/detail/wdxz_03.gif" /></a>
              <div class="top10 right" style="width:215px;" >
                <div class="top10_title">  相关推荐 </div>
                <ul>
                  <c:forEach items="${baselist}" var="base">
	                <li style="width:190px">     
                  		 <a target='_blank' href='/detail/${base.infoType }/${base.id }' title='${base.title }'>" ${base.title }  </a>
	                 </li>
                 </c:forEach>
                </ul>
              </div>
              
				<div class="clear"></div>
			</div>
		</div>
		<div class="clear"></div>
		<div class="foot">
<!-- 联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号  -->
<jsp:include page="${pageContext.request.contextPath}/bottom.jsp" />
</div>
	</div>

	<div class="guding">
		<div class="guding2">
			<div class="btn_fd">
				<a href="/toSuggest"><img title="留言" src="/images/btnfd_10.gif" /></a> <a
					href="#top"><img title="返回顶部" class="shang20"
					src="/images/btnfd_13.gif" /></a>
			</div>
		</div>
	</div>
	<div id="hello" ></div>
	<div class="fixed"><div class="fixed_box">

</div></div>
</body>
</html>
