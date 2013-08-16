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
			var zTree;
			var setting = {
				async : {
					enable : true,
					url : "/getTagList",
					autoParam : [ "id", "code", "level" ],
					otherParam : {
						"rootCode" : "${rootCode}"
					},
					dataFilter : filter
				},
				callback : {
					beforeClick : beforeClick,
					beforeExpand : beforeExpand,
					onAsyncSuccess : zTreeOnAsyncSuccess
				}
			};
			function beforeExpand(treeId, treeNode) {
				$("#zsxzpath").val(treeNode.code);
				return true;
			};
			function beforeClick(treeId, treeNode) {
				$("#zsxzpath").val(treeNode.code);
				if (!treeNode.isParent) {
					return false;
				} else {
					return true;
				}
			}

			function filter(treeId, parentNode, childNodes) {
				if (!childNodes)
					return null;
				for ( var i = 0, l = childNodes.length; i < l; i++) {
					childNodes[i].name = childNodes[i].name
							.replace(/\.n/g, '.');
				}
				return childNodes;
			}

			var firstAsyncSuccessFlag = 0;
			function zTreeOnAsyncSuccess(event, treeId, msg) {
				if (firstAsyncSuccessFlag == 0) {
					try {
						var nodes = zTree.getNodes();
						zTree.expandNode(nodes[0], true);
						var childNodes = zTree.transformToArray(nodes[0]);
						zTree.expandNode(childNodes[1], true);
						 zTree.selectNode(childNodes[0]);  
						var childNodes1 = zTree.transformToArray(childNodes[1]);
						zTree.checkNode(childNodes1[1], true, true);
						firstAsyncSuccessFlag = 1;
					} catch (err) {

					}

				}
			}
			var province ="";
			

			$(document).ready(function() {
				
				zTree = $.fn.zTree.init($("#treeDemo"), setting);
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
				//给目录添加事件
				var dAccess = $('#dAccess').val();//访问权限
				if(dAccess=='0'){
					$('.knowledge').live("click",function(){tishi('');});
				}
				if(dAccess=='2'){
					$('.knowledge').live("click",function(){alert('您的服务已到期，请去购买服务！');});
				}
				if(dAccess=='3'){
					$('.knowledge').live("click",function(){alert('您需要购买服务！');});
				}
				
				
				
				var fpaths=['/TywViewer/TestSwf/1.swf','/TywViewer/TestSwf/12.swf','/TywViewer/TestSwf/13.swf','/TywViewer/TestSwf/14.swf','/TywViewer/TestSwf/15.swf'];
				
				var index=0;
				
				var swfconfig={
				 config : {
                     SwfFile : fpaths[index],
					 Scale : 0.8, 
					 ZoomTransition : 'easeOut',
					 ZoomTime : 0.5, 
					 ZoomInterval : 0.1,
					 FitPageOnLoad : true,
					 FitWidthOnLoad : true, 
					 FullScreenAsMaxWindow : false,
					 ProgressiveLoading : false,
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
					 WMode : 'window',
					 localeChain: 'zh_CN'
					 }
				};
				
				$("#ftitle").html(fpaths[index]);
				
				
				
				$("#fnext").click(function(){
					 index=index+1;
					 swfconfig={
					 config : {
	                     SwfFile : fpaths[index],
						 Scale : 0.8, 
						 ZoomTransition : 'easeOut',
						 ZoomTime : 0.5, 
						 ZoomInterval : 0.1,
						 FitPageOnLoad : true,
						 FitWidthOnLoad : true, 
						 FullScreenAsMaxWindow : false,
						 ProgressiveLoading : false,
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
						 WMode : 'window',
						 localeChain: 'zh_CN'
						 }
					};
					$('#documentViewer').FlexPaperViewer(swfconfig);
					$("#ftitle").html(fpaths[index]);
					if(index==4){
						index=-1;
					}
				});
				
				
				
				$('#documentViewer').FlexPaperViewer(swfconfig);
				
			});
			
	 
			function fenbufenxiang(){
				if($("#bak_ul ul").length>0){
				    $("ul.now").first().clone(true).attr('ajax_status','0').removeClass('now').css("left","214px").css('position','relative').appendTo("#bak_ul");
				    $("ul.now").first().remove();
				    $("#bak_ul ul").eq(0).clone(true).attr('ajax_status',"0").appendTo("#left_banner").addClass('now');
				    $('ul.now').eq(0).animate({left:'0'}, 1000,function(){});
				    $(".path_info").text('');
				    $("#lastpath").val("10000000000000000");
			    } 
			}
			
		
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

		  <div id="documentViewer" class="flexpaper_viewer" style="left:10px;top:10px;width:750px;height:550px; z-index:2"></div>

						</div>
			  </div>
				
              <div class="top10 right" style="width:215px;" >
                <div class="top10_title">  最新知识TOP10 </div>
                <ul>
              <li style="width:190px"> 
               2013年最新液态气压防水条款（一）  
              </li>
                  <c:forEach items="${pageTopNum}" var="pageTopNum">
                <li style="width:190px">     
                
                			<c:if test="${empty DetailAccess}">
	                    		<a href="javascript:void()" onclick="tishi('');">	
			                  		<c:choose>
					                   		<c:when test="${fn:length(pageTopNum.title) > 12}">
					                   		 	${fn:substring(pageTopNum.title, 0, 12)}... 
				                    		</c:when>
				                     		<c:otherwise> 
				                      			${pageTopNum.title}  
				                        	</c:otherwise>
				                	</c:choose>
			                  	 </a>
                    		</c:if>
                    		<c:if test="${!empty DetailAccess}">
                    			<sec:authorize ifAllGranted="ADMINISTRATOR">
                    				<a  title = "${pageTopNum.title}" href="${pageContext.request.contextPath}/detail/${pageTopNum.infoType}/${pageTopNum.id}" target="_blank">
				                  		<c:choose>
						                   		<c:when test="${fn:length(pageTopNum.title) > 12}">
						                   		 	${fn:substring(pageTopNum.title, 0, 12)}... 
					                    		</c:when>
					                     		<c:otherwise> 
					                      			${pageTopNum.title} 
					                        	</c:otherwise>
					                	</c:choose>
				                  	 </a>
                    			</sec:authorize>
                    			<sec:authorize ifNotGranted="ADMINISTRATOR">
                    				<c:if test="${DetailAccess==1 }">
			                    		<a  title = "${pageTopNum.title}" href="${pageContext.request.contextPath}/detail/${pageTopNum.infoType}/${pageTopNum.id}" target="_blank">
					                  		<c:choose>
							                   		<c:when test="${fn:length(pageTopNum.title) > 12}">
							                   		 	${fn:substring(pageTopNum.title, 0, 12)}... 
						                    		</c:when>
						                     		<c:otherwise> 
						                      			${pageTopNum.title} 
						                        	</c:otherwise>
						                	</c:choose>
					                  	 </a>
	                    			</c:if>
	                    			<c:if test="${DetailAccess==0 }">
		                  	 			<a href="javascript:alert('您的服务已到期，请购买服务！')" onclick="">	
					                  		<c:choose>
							                   		<c:when test="${fn:length(pageTopNum.title) > 12}">
							                   		 	${fn:substring(pageTopNum.title, 0, 12)}... 
						                    		</c:when>
						                     		<c:otherwise> 
						                      			${pageTopNum.title}  
						                        	</c:otherwise>
						                	</c:choose>
					                  	 </a>
	                    			</c:if>
	                    			<c:if test="${DetailAccess==2 }">
		                  	 			<a href="javascript:alert('您需要购买服务！')" onclick="">	
					                  		<c:choose>
							                   		<c:when test="${fn:length(pageTopNum.title) > 12}">
							                   		 	${fn:substring(pageTopNum.title, 0, 12)}... 
						                    		</c:when>
						                     		<c:otherwise> 
						                      			${pageTopNum.title}  
						                        	</c:otherwise>
						                	</c:choose>
					                  	 </a>
	                    			</c:if>
                    			</sec:authorize>
                    		</c:if>
                  	     
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
