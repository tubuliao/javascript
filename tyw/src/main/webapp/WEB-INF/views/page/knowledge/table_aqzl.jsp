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
 <style type="text/css">
   
   
	.highlight{
	background:#FF0;
 	color:#E00;
	}  
    </style>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/search.css"/>
<LINK rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/apprise.css">

	<link rel=stylesheet type=text/css href="/css/biaoge.css"/>

		<script
			src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js"
			type="text/javascript"></script>
		<script
			src="${pageContext.request.contextPath}/js/jquery.pagination.js"
			type="text/javascript" />
		<script
			src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js"
			type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/clausetable.js"></script>
 			<script src="${pageContext.request.contextPath}/js/ajaxbase.js"
			type="text/javascript"></script>
		<link
			href="${pageContext.request.contextPath}/ztree3.5/zTreeStyle/zTreeStyle.css"
			rel="stylesheet" type="text/css" />
	 
		<link href="${pageContext.request.contextPath}/css/biaoge.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/ztree3.5/js/jquery.ztree.core-3.5.js"></script>
		<script src="${pageContext.request.contextPath}/js/Jquery.L.Message.js"	type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/search_ajax.js" type="text/javascript"></script>
		<script type="text/javascript">
			var items_per_page = 15;
			var page_index = 0;
			var flag = 1;
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
				zTree.selectNode(treeNode);
 				$("#zsxzpath").val(treeNode.code);
				$("#Pagination").html('');
				getDataList(page_index);
				return true;
			};
			function beforeClick(treeId, treeNode) {
				zTree.selectNode(treeNode);
				 var isfbfx=treeNode.name.indexOf("分部分项");
				 if(0>isfbfx){
 		 				 $(".now a").attr("class","");
		 			 	 $("a[name='a_fbfx']").html("");
  				 }else{
						$(".now a").attr("class","a_clicks");
						$("a[name='a_fbfx']").attr("class","a_click");
						 $("a[name='a_fbfx']").html("<img class=\"right\" title='进入下一级' src=\"${pageContext.request.contextPath}/images/new/new_17.gif\" />");
						//<img class="right " title='进入下一级' src="${pageContext.request.contextPath}/images/new/new_17.gif" />
				 }
 				$("#zsxzpath").val(treeNode.code);
				$("#Pagination").html('');
				getDataList(page_index);
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
			var province = "";
			function getDataList(index) {
				//alert(11);
  				var title = $("#title").val();
				var pageIndex = index;
 				var zsxzpath = $("#zsxzpath").val();
 				province=getCookie("provinceVal");
  				$.ajax({
							type : "POST",
							url : "${pageContext.request.contextPath}/dolistBaseBy",
							data : {
								"pageIndex" : (pageIndex + 1),
								"items_per_page" : items_per_page,
								"fbfx" : $("#lastpath").val(),
								"title" : title,
								"province" : province,
								"zsxzpath" : zsxzpath,
								"infoType":"${type}"
							},
							dataType : 'json',
							beforeSend:tywBeforeSend,                    
							complete: tywAjaxComplete,
							success : function(msg) {
								var total = msg.total;

								var html = '';
								$.each(msg.result,function(i, n) {
													var dAccess = $('#dAccess').val();//访问权限
													html+='<li class="knowledge">';
													html+=' <div class="search_list_pd">';
													html+='  <div class="search_list_pd_bt">';
													html+='<div class="left">';
													 if(n.infoType == 1){
															html += "<img src='/images/bg/bigfile.gif' />";
														}
														if(n.infoType == 2){
															html += "<img src='/images/bg/segment.gif' />";
														}
														if(n.infoType == 3){
															html += "<img src='/images/bg/video.gif' />";
														}
														if(n.infoType == 4){
															html += "<img src='/images/bg/table.gif' />";
														}
														if(n.infoType == 5){
															html += "<img src='/images/bg/picture.gif' />";
														}
														if(n.infoType == 6){
															html += "<img src='/images/bg/ppt.gif' />";
														}
														 
														
													if(dAccess=='0'){
														html += "&nbsp;<a href='javascript:void()' title='"+n.title+"'>"+ n.title + "</a>";
													}
													if(dAccess=='1'){
														html += "&nbsp;<a target='_blank' href='/detail/"+n.infoType+"/"+n.id+"' title='"+n.title+"'>"+ n.title + "</a>";
													}
													if(dAccess=='2'){
														html += "&nbsp;<a href='javascript:void()' title='"+n.title+"'>"+ n.title + "</a>";
													}
													if(dAccess=='3'){
														html += "&nbsp;<a href='javascript:void()' title='"+n.title+"'>"+ n.title + "</a>";
													}
													html+='</div><div class="right">日期：'+n.begincreateDate+'</div>';
													html+='</div>';
													html+=' <div class="clear"></div>';
													html+='<div class="search_leibie_pd">';
													html+='<div class="left">来源：<span id="sourceDetail7">'+n.source+'</span></div>';
													html+='<div class="right"> <img src="/images/personal/chakan.png" width="11" height="10" /> 浏览('+ (n.clickcount==null?0:n.clickcount)+')  </div>';
													html+='<div class="clear"></div>';
													html+='</div>';
													html+='<div class="clear"></div>';
													html+=' </div>';
													html+=' </li>';
												});
								if(html == ''){
									html = "&nbsp;&nbsp;&nbsp;&nbsp;<font color='red'>文件正在整理上传中，如果您工作中急需，请与我们联系</font>";
								}
								$('#searchResultList').html(html);
								//分页-只初始化一次
								if ($("#Pagination").html().length == '') {
									$('#Pagination').pagination(total, {
										'items_per_page' : items_per_page,
										'num_display_entries' : 5,
										'num_edge_entries' : 2,
										'prev_text' : "上一页",
										'next_text' : "下一页",
										'callback' : pageselectCallback
									});
								}
							}
						});
		
			}

			function pageselectCallback(page_index, jq) {
 				if (page_index == 0 && flag == 1) {
					flag = 0;
				} else {
					 
					getDataList(page_index);
					
					
				}
			
				
   				
			}

			$(document).ready(function() {
				getDataList(page_index);
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
 			 //$("#treeDemo a").live("click", function(event){
					// var isfbfx=$(this).text().indexOf("分部分项");
					// if(0>isfbfx){
	 		 			//	 $(".now a").attr("class","");
			 			 //	 $("a[name='nextsrc']").html("");
	  				// }else{
						//	$("#zsxzview a").attr("class","a_clicks");
						//	$("a[name='nextsrc']").attr("class","a_click");
						//	 $("a[name='nextsrc']").html("<img class=\"right\" title='进入下一级' src=\"${pageContext.request.contextPath}/images/new/new_17.gif\" />");
							//<img class="right " title='进入下一级' src="${pageContext.request.contextPath}/images/new/new_17.gif" />
					 //}
			//	})
			 		
				 
			});
			function sub() {
 				flag = 1;
 				$("#Pagination").html('');
  				getDataList(page_index);

  				
			}
			 
			function getProvince() {
				//这里去加载标签树
				var areaurl = '${ctx}/tag/getProvinceTag';
				$.post(areaurl, function(result) {
					var html = '';
					html += "<table>";
					html += "<tr>";
					$.each(result, function(i) {
						if (i % 5 == 0) {
							html += "</tr>";
						}
						if (i % 5 == 0) {
							html += "<tr>";
						}
						html += "<td style='width:120px;'>";
						html += "<a id='pid_" + this.id
								+ "' href='javascript:getProvinceValue(\""+this.id+"\")'>" + this.name + "</a>";
						html += "</td>";
					});
					html += "</tr>";
					html += "</table>";
					$("#chooseArea").html(html);
				}, 'json');
			}
			function getProvinceValue(op) {
				 
				$("#province").val($("#pid_" + op).text());
				$('#chooseArea').dialog('close');
			}
			function fenbufenxiang(){
				if($("#bak_ul ul").length>0){
				    $("ul.now").first().clone(true).attr('ajax_status','0').removeClass('now').css("left","214px").css('position','relative').appendTo("#bak_ul");
				    $("ul.now").first().remove();
				    $("#bak_ul ul").eq(0).clone(true).attr('ajax_status',"0").appendTo("#left_banner").addClass('now');
				    $('ul.now').eq(0).animate({left:'0'}, 1000,function(){});
				    $(".path_info").text('');
			    }
				$("#lastpath").val("10000000000000000");
				$(".a_clicks").css({ 'color':'#333333'});
				$("#Pagination").html("");
				getDataList(0);
			}
			//根据地区查找
	 	function getDataView(){
 	 		flag = 1;
	 		$("#Pagination").html('');
 			getDataList(0);
		 }
		 
		</script>
</head>

<body>


							<c:if test="${empty DetailAccess}">
								<input type="hidden" id='dAccess' name='dAccess' value='0'/>
	                    		<%-- <a href="javascript:void()" onclick="tishi('');">	
			                  		<font size="3">	${base.title} </font>
		                  	 	</a> --%>
                    		</c:if>
                    		<c:if test="${!empty DetailAccess}">
                    			<sec:authorize ifAllGranted="ADMINISTRATOR">
                    				<input type="hidden" id='dAccess' name='dAccess' value='1'/>
                    				<%-- <a id="url"   onclick="javascript:window.open('${pageContext.request.contextPath}/detail/${base.infoType}/${base.id}?keyword=${keyword}') " >
				                   		<font size="3"> ${base.title}</font>
				                    </a> --%>
                    			</sec:authorize>
                    			<sec:authorize ifNotGranted="ADMINISTRATOR">
                    				<c:if test="${DetailAccess==1 }">
                    					<input type="hidden" id='dAccess' name='dAccess' value='1'/>
			                    		<%-- <a id="url"   onclick="javascript:window.open('${pageContext.request.contextPath}/detail/${base.infoType}/${base.id}?keyword=${keyword}') " >
				                   			<font size="3"> ${base.title}</font>
				                    	</a> --%>
	                    			</c:if>
	                    			<c:if test="${DetailAccess==0 }">
	                    				<input type="hidden" id='dAccess' name='dAccess' value='2'/>
		                    			<%-- <a href="javascript:alert('您的服务已到期，请购买服务！')" onclick="">	
			                  				<font size="3">	${base.title} </font>
		                  	 			</a> --%>
	                    			</c:if>
	                    			<c:if test="${DetailAccess==2 }">
	                    				<input type="hidden" id='dAccess' name='dAccess' value='3'/>
		                    			<%-- <a href="javascript:alert('您还没交费！')" onclick="">	
			                  				<font size="3">	${base.title} </font>
		                  	 			</a> --%>
	                    			</c:if>
                    			</sec:authorize>
                    		</c:if>
											
	<div class="body">
		<div>
			<jsp:include page="${pageContext.request.contextPath}/top.jsp" />
			<jsp:include page="/menu.jsp"></jsp:include>
			<jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />
		</div>
		<div class=" new_box">
			<div class="new_body">
			<jsp:include page="/summary.jsp"></jsp:include>
            <div class="new_position">
						<a href="javascript:fenbufenxiang()"><img src="/images/new/new_08.gif" /></a> <span class="path_info">

						</span>
					</div>
				<div class="new_left" id="left_banner" style="overflow: hidden">
					<div class="new_left_top">
						<a href="javascript:void(0)" class="back_link"><img
							class="left"
							src="${pageContext.request.contextPath}/images/new/new_03.gif" /></a>
						<span ><a href="javascript:fenbufenxiang()">分部分项细部检索</a></span><a href="javascript:fenbufenxiang()"><img class="right"
							src="${pageContext.request.contextPath}/images/new/new_05.gif" /></a>
					</div>
					<ul class="now" id="zsxzview">
						<c:forEach items="${Source}" var="source" varStatus="status">
                    	<!-- <li onclick="getSourceList('核心条文')"> -->
                    	<li>
                    		<c:if test="${source.count!=0}">
                    			<span class='left' ><a  title="${source.name}" href="javascript:void(0)" class="a_clicks" data="${source.code}"> ${source.name}&nbsp;&nbsp;<span style="color:green">(${source.count})</span></a></span>
	                    		<c:if test="${source.leaf==0}">
	                    			<a href="javascript:void(0)"   style="display:inline-block;float:right" name="a_fbfx"  class="a_click" level="${source.level}" data="${source.code}" text="${source.name}"><img class="right " title='进入下一级' src="${pageContext.request.contextPath}/images/new/new_17.gif" /></a>
	                    		</c:if>
                    		</c:if>
                    		<c:if test="${source.count==0}">
                    			${source.name}&nbsp;&nbsp;<span style="color:green">(${source.count})</span>
                    		</c:if>
                    		
                    	</li>
           </c:forEach>

						<div class="clear"></div>
					</ul>
				</div>

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

				<div class="new_right">
					
					<div class="new_search" style="display:none">
                    <div class="new_search_title">查询条件</div>
						<form>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="table2">
								<tr>
									<td width="10%" height="30" align="right"><div>标题</div></td>
									<td width="24%" height="30"><input id="title" name="title"
										type="text" class="xmm_input_s width90" /></td>
									<td width="9%" height="30" align="right"></td>
									<td width="24%" height="30"><input
										class="xmm_input_s width90" type="hidden" name="lastpath"
										id="lastpath" /> <input class="xmm_input_s width90"
										type="hidden" name="zsxzpath" id="zsxzpath" value="${rootCode}" /></td>
								</tr>
							</table>

							<div class="clear"></div>
						</form>
                        <div class="new_search_bottom">
						<button   class="new_chaxun">查询</button>
                        </div>
					</div>

					<div class="p_tree">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
					<div class="new_right_mulu">
						<div class="new_right_book_title">
							<div class="left">
								标题
							</div>
							<div class="right">
								<a href="#"><span>更多></span></a>
							</div>
							<div class="clear"></div>
						</div>
						<div class="mulu_list">
							<ul id="searchResultList">

							</ul>

							<div class="scott">
								<div id="Pagination" class="pagination"></div>
							</div>




						</div>
					</div>
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
			<div class="zuofu">
				<ul>
					<li class="present"><a href="#">资讯</a></li>
					<li><a href="#">文件</a></li>
					<li><a href="#">核心</a></li>
					<li><a href="#">施工</a></li>
					<li><a href="#">验收</a></li>
					<li><a href="#">表格</a></li>
					<li><a href="#">监管</a></li>
					<li><a href="#">管理</a></li>
					<li><a href="#">职考</a></li>
					<li><a href="#">应用</a></li>
				</ul>
			</div>
			<div class="btn_fd">
				<a href="#"></a> <a
					href="#top"><img title="返回顶部" class="shang20"
					src="/images/btnfd_13.gif" /></a>
			</div>
		</div>
	</div>
	<div id="hello" ></div>
	<div class="fixed"><div class="fixed_box">
<div class="shangxiaye">
	<a class="shangyiye" href="javascript:void(0)" style="display: none;" onclick="last();" id="shangyiye"></a>
    <a class="xiayiye" href="javascript:void(0)" style="display: none;" onclick="next();" id="xiayiye"></a>
    <div class="glgjc_box">
	    <a id="hotup"><img title="关键字高亮" class="shang10" src="/images/detail/gjc_03.gif" /></a>
	    <a class="glgjc" id="glgjc" href="javascript:void(0)">关键词</a>
	    <a id="hotnext"  href="javascript:void(0)"><img title="关键字不高亮" src="/images/detail/gjc_06.gif"/></a>
    </div>
</div>
</div></div>
</body>
</html>
