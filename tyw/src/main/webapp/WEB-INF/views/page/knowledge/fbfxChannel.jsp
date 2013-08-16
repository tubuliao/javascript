<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分部分项知识查询</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<LINK rel=stylesheet type=text/css href="/css/search.css">
	<LINK rel=stylesheet type=text/css href="/css/biaoge.css">
	<LINK rel=stylesheet type=text/css href="/css/step.css">
	
		<script
			src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js"
			type="text/javascript"></script>
		<script
			src="${pageContext.request.contextPath}/js/jquery.pagination.js"
			type="text/javascript" />
		<script
			src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js"
			type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/clausefbfx.js"></script>
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
			<script src="${pageContext.request.contextPath}/js/ajaxbase.js"
			type="text/javascript"></script>
		<link
			href="${pageContext.request.contextPath}/ztree3.5/zTreeStyle/zTreeStyle.css"
			rel="stylesheet" type="text/css" />
		<link href="${pageContext.request.contextPath}/jquery-ui-1.10.2/themes/base/jquery-ui.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/ztree3.5/js/jquery.ztree.core-3.5.js"></script>
			<script src="${pageContext.request.contextPath}/js/Jquery.L.Message.js"	type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/search_ajax.js" type="text/javascript"></script>
		
		<script type="text/javascript">
			var items_per_page = 10;
			var page_index = 0;
			var flag = 1;
			var zTree;
			//点分部分项初始化各个知识性质的数量
			function getDataList(index) {
				var fbfxCode = $("#lastpath").val();
				var peoplecode = $("#peoplecode").val();
				var province = getCookie("provinceVal");
				$.ajax({
					type : "POST",
					url : "${pageContext.request.contextPath}/knowledge/web/fbfxChannelajax?"+Math.random(),
					data : {
						"fbfxCode" : fbfxCode,
						"peoplecode":peoplecode,
						"areacode":province
					},
					dataType : 'json',
					success : function(msg) {
						 
						$.each(msg,function(x,y){
							if(y.totalcount==0){
								$("#akey_"+y.code).css('display',"none");	
							}else if(y.totalcount>0){
								$("#akey_"+y.code).find("span").html("("+y.totalcount+")");	
								$("#akey_"+y.code).css('display',"block");
							}else{
								$("#akey_"+y.code).css('display',"none");
							} 
						});
						 
					}
				}); 
			}

			function getFbfxDataList(index) {
				var title = $("#title").val();
				var pageIndex = index;
				var zsxzpath = $("#zsxzpath").val();
				var  province=getCookie("provinceVal");
				 
				var title = $("#title").val();
				$.ajax({
							type : "POST",
							url : "${pageContext.request.contextPath}/dolistBaseBy?"+Math.random(),
							data : {
								"pageIndex" : (pageIndex + 1),
								"items_per_page" : items_per_page,
								"fbfx" : $("#lastpath").val(),
								"title" : title,
								"province" : province,
								"zsxzpath" : zsxzpath,
								"peopleCode":$("#peoplecode").val() 
							},
							dataType : 'json',
							success : function(msg) {
								var total = msg.total;
								var html = '';
								$.each(msg.result,function(i, n) {
													var dAccess = $('#dAccess').val();//访问权限
													html += "<li class='knowledge'>";
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
													//html += "<a href='/detail/"+n.infoType+"/"+n.id+"'>"+ n.title + "</a>";
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
													html += "</li>";
												});
								if(html == ''){
									html = "&nbsp;&nbsp;&nbsp;&nbsp;<font color='red'>文件正在整理上传中，如果您工作中急需，请与我们联系</font>";
								}
								$('#mulu_contents').html(html);
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
					getFbfxDataList(page_index);
				}
			}

			$(document).ready(function() {  
				$('#fbfbDialog').dialog({
					autoOpen : false,
					width : 600,
					height: 550,
					center:true,
					title: '查询结果列表',
					modal : true
				});  
				zTree = $.fn.zTree.init($("#treeDemo"), setting);
				fenbufenxiang();
				
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
				
			});
			function sub() {
				flag = 1;
				$("#Pagination").html('');
				getFbfxDataList(page_index);
			}
			
			function fenbufenxiang(){
				if($("#bak_ul ul").length>0){
				    $("ul.now").first().clone(true).attr('ajax_status','0').removeClass('now').css("left","214px").css('position','relative').appendTo("#bak_ul");
				    $("ul.now").first().remove();
				    $("#bak_ul ul").eq(0).clone(true).attr('ajax_status',"0").appendTo("#left_banner").addClass('now');
				    $('ul.now').eq(0).animate({left:'0'}, 1000,function(){});
				    $(".path_info").text('');
			    } 
				$("#fbfxtreetitle").text("分布分项");
				$("#lastpath").val("10000000000000000");
				$(".a_clicks").css({ 'color':'#333333'});
				$("#Pagination").html("");
				getDataList(0);
			}
			
			function tcdialog(fcode){
				$("#zsxzpath").val(fcode);
				$("#Pagination").html('');
				$("#title").val("");
				$("#fbfbDialog").parent().css("position", "fixed");
				$("#fbfbDialog").parent().css("top",$(window).height()/2+"px");
				$("#fbfbDialog").dialog("open"); 
				getFbfxDataList(0); 
			}
			
		
			var setting = {
				async : {
					enable : true,
					url : "/getTagList",
					autoParam : [ "id", "code", "level" ],
					otherParam : {
						"rootCode" : "30100000000000000"
					} 
				},
				callback : {
					beforeClick : beforeClick
				}
			};
			
		 
			
			function beforeClick(treeId, treeNode) {
				zTree.selectNode(treeNode);
				$("#peoplecode").val(treeNode.code);
				var cityObj = $("#citySel");
				cityObj.attr("value", treeNode.name);
				$("#Pagination").html("");
				getDataList(0);
				hideMenu();
				if (!treeNode.isParent) {
					return false;
				} else {
					return true;
				}
			}
			
			 
			
			function showMenu() {
				var cityObj = $("#citySel");
				var cityOffset = $("#citySel").offset();
				$("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
				$("body").bind("mousedown", onBodyDown);
			}
			function hideMenu() {
				$("#menuContent").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDown);
			}
			function onBodyDown(event) {
				if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
					hideMenu();
				}
			}
			function qingkong(){
				$("#peoplecode").val("");
				$("#citySel").val("");
				getDataList(0);
			}
			//根据地区查找
		 	function getDataView(){
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
                    		
<div id="fbfbDialog" ><br/>
				<div id = "searchTitle" >
						<form>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="table2">
								<tr>
									<td width="10%" height="30" align="right"><div>标题</div></td>
									<td width="24%" height="30"><input id="title" name="title"
										type="text" class="xmm_input_s width90" /></td>
									<td width="24%" height="30"><input
										class="xmm_input_s width90" type="hidden" name="lastpath"
										id="lastpath" value="${fbfxCode }" /> <input class="xmm_input_s width90"
										type="hidden" name="zsxzpath" id="zsxzpath" value="${rootCode}" />
										<input type="button" onclick="sub()"  class="mulu_list_btn" value="点击查询"/>
										</td>
								</tr>
							</table>
							
						</form> 
					</div>
			    <div id='mulu_contents' class='mulu_list'></div>
				<div id="paginationDisplayNone" class="scott" style="display:block">
							<div id="Pagination" class="pagination"></div>
				</div>
</div>
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
			<jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />
		</div>
		<div class=" new_box">
			<div class="new_body">
			 <div class="new_position">
						<a href="javascript:fenbufenxiang()"><img src="/images/new/new_08.gif" /></a> <span class="path_info">

						</span>
					</div>
					
				  <form>
	  <div id="menuContent" style="z-index:100000000;display:none; position: absolute;background:#ffffff">
			<ul id="treeDemo" class="ztree" style="margin-top:0; width:310px;"></ul>
	  </div>
      <input id="citySel" type="text" readonly value="" style="width:290px;"/>
  	  <a id="menuBtn" href="#" onclick="showMenu(); return false;">人员选择</a>&nbsp;&nbsp;<a href="javascript:qingkong()">清空</a>
  	  <input type="hidden" id="peoplecode" name='peoplecode' value=''/>
    </form>
					
				<div class="new_left" id="left_banner" style="overflow: hidden">
					<div class="new_left_top">
						<a href="javascript:void(0)" class="back_link"><img
							class="left"
							src="${pageContext.request.contextPath}/images/new/new_03.gif" /></a>
						<span ><a id="fbfxtreetitle" href="#">分部分项细部检索</a></span><a href="javascript:fenbufenxiang()"><img class="right"
							src="${pageContext.request.contextPath}/images/new/new_05.gif" /></a>
					</div>
					<ul class="now" data="${Source[0].parentCode }">
						<c:forEach items="${Source}" var="source" varStatus="status">
							<li onclick="getSourceList()"><script>
								var s = ${
									status.count
								};
								if (s == 1) {
									getSourceList();
								}
							</script>
								<a title="${source.name}" href="javascript:void(0)"
								data="${source.code}" class="a_clicks"> ${source.name}</a> <a
								href='javascript:void(0)'
								style='display: inline-block; float: right' class='a_click'
								data="${source.code}" text='${source.name}'><img
									class='right ' src='/images/new/new_17.gif' title='点击进入下一级'  /></a></li>
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
			  <div class="fbzs">
		       	  <div class="fbzs-1">
		            	<ul>
		        			<li id="akey_40101000000000000"><a  href="#" onclick="tcdialog('40101000000000000')">
		           				  土木资讯</a><span>(0)</span>            		
		           		    </li>
		           		    <li id="akey_40102000000000000"><a href="#" onclick="tcdialog('40102000000000000')">
		           				 法律法规</a><span>(0)</span>            		
		           		    </li>
		           		    <li id="akey_40103000000000000"><a href="#" onclick="tcdialog('40103000000000000')">
		           				  标准规范</a><span>(0)</span>            		
		           		    </li>
		           		    <li id="akey_40105000000000000"><a href="#" onclick="tcdialog('40105000000000000')">
		           				  施工图集</a><span>(0)</span>            		
		           		    </li> 
		        		</ul>
		          </div> 
		          <div class="fbzs-2">
		            	<ul>
		        			<li id="akey_40201000000000000"><a href="#" onclick="tcdialog('40201000000000000')">
		           				  强制性条文</a><span>(0)</span>            		
		           		    </li>
		           		    <li id="akey_40202000000000000"><a href="#" onclick="tcdialog('40202000000000000')">
		           				 规范条文</a><span>(0)</span>            		
		           		    </li>
		           		    <li id="akey_40203000000000000"><a href="#" onclick="tcdialog('40203000000000000')">
		           				  安全条文</a><span>(0)</span>            		
		           		    </li>
		           		    <li id="akey_40205000000000000"><a href="#" onclick="tcdialog('40205000000000000')">
		           				  施工工法</a><span>(0)</span>            		
		           		    </li>
		           		    <li id="akey_40206000000000000"><a href="#" onclick="tcdialog('40206000000000000')">
		           				 工艺标准</a><span>(0)</span>            		
		           		    </li> 
		        		</ul>
		          </div> 
		          <div class="fbzs-3">
		            	<ul>
		        			<li id="akey_40306000000000000"><a href="#" onclick="tcdialog('40306000000000000')">
		           				  投标施组</a><span>(0)</span>            		
		           		    </li>
		           		    <li id="akey_40301000000000000"><a href="#" onclick="tcdialog('40301000000000000')">
		           				 实施性施组</a><span>(0)</span>     		
		           		    </li>
		           		    <li id="akey_40302000000000000"><a href="#" onclick="tcdialog('40302000000000000')">
		           				  施工方案</a><span>(0)</span>            		
		           		    </li>
		           		    <li id="akey_40303000000000000"><a href="#" onclick="tcdialog('40303000000000000')">
		           				  技术交底</a><span>(0)</span>            		
		           		    </li>
		        		</ul>
		          </div> 
		          <div class="fbzs-4">
		            	<ul>
		        			<li id="akey_40401000000000000"><a href="#" onclick="tcdialog('40401000000000000')">施工做法</a><span>(0)</span></li>
							<li id="akey_40402000000000000"><a href="#" onclick="tcdialog('40402000000000000')">经验技巧</a><span>(0)</span></li>
							<li id="akey_40403000000000000"><a href="#" onclick="tcdialog('40403000000000000')">基础数据</a><span>(0)</span></li>
							<li id="akey_40404000000000000"><a href="#" onclick="tcdialog('40404000000000000')">工程案例</a><span>(0)</span></li>
							<li id="akey_40405000000000000"><a href="#" onclick="tcdialog('40405000000000000')">施工计算</a><span>(0)</span></li>
							<li id="akey_40406000000000000"><a href="#" onclick="tcdialog('40406000000000000')">构造详图</a><span>(0)</span></li>
		        		</ul>
		          </div> 
		          <div class="fbzs-5">
		            	<ul>
		        			<li id="akey_40501000000000000"><a href="#" onclick="tcdialog('40501000000000000')">质量检验</a><span>(0)</span></li>
							<li id="akey_40502000000000000"><a href="#" onclick="tcdialog('40502000000000000')">质量控制</a><span>(0)</span></li>
							<li id="akey_40503000000000000"><a href="#" onclick="tcdialog('40503000000000000')">质量通病</a><span>(0)</span></li>
							<li id="akey_40504000000000000"><a href="#" onclick="tcdialog('40504000000000000')">工程创优</a><span>(0)</span></li>
		        		</ul>
		          </div> 
		          <div class="fbzs-6">
		            	<ul>
		        			<li id="akey_40601000000000000"><a href="#" onclick="tcdialog('40601000000000000')">工程资料</a><span>(0)</span></li>
							<li id="akey_40602000000000000"><a href="#" onclick="tcdialog('40602000000000000')">检测机构用表</a><span>(0)</span></li>
							<li id="akey_40603000000000000"><a href="#" onclick="tcdialog('40603000000000000')">新规范表格</a><span>(0)</span></li>
							<li id="akey_40604000000000000"><a href="#" onclick="tcdialog('40604000000000000')">管理常用表</a><span>(0)</span></li>
		        		</ul>
		          </div> 
		          <div class="fbzs-7">
		            	<ul>
							<li id="akey_40702000000000000"><a href="#" onclick="tcdialog('40702000000000000')">安全技术</a><span>(0)</span></li>
							<li id="akey_40703000000000000"><a href="#" onclick="tcdialog('40703000000000000')">安全资料</a><span>(0)</span></li>
							<li id="akey_40704000000000000"><a href="#" onclick="tcdialog('40704000000000000')">环境保护</a><span>(0)</span></li>
							<li id="akey_40705040000000000"><a href="#" onclick="tcdialog('40705040000000000')">现场管理</a><span>(0)</span></li>
		        		</ul>
		          </div> 
		          <div class="fbzs-8">
		            	<ul>
							<li id="akey_40801000000000000"><a href="#" onclick="tcdialog('40801000000000000')">机械机具</a><span>(0)</span></li>
							<li id="akey_40802000000000000"><a href="#" onclick="tcdialog('40802000000000000')">检测试验</a><span>(0)</span></li>
							<li id="akey_40803000000000000"><a href="#" onclick="tcdialog('40803000000000000')">招标投标</a><span>(0)</span></li>
							<li id="akey_40804000000000000"><a href="#" onclick="tcdialog('40804000000000000')">工程管理</a><span>(0)</span></li>
							<li id="akey_40805000000000000"><a href="#" onclick="tcdialog('40805000000000000')">工程造价</a><span>(0)</span></li>
							<li id="akey_40806000000000000"><a href="#" onclick="tcdialog('40806000000000000')">工程物资</a><span>(0)</span></li>
							<li id="akey_40807000000000000"><a href="#" onclick="tcdialog('40807000000000000')">工程测量</a><span>(0)</span></li>
		        		</ul>
		          </div> 
		          <div class="fbzs-9">
		            	<ul>
		        			<li id="akey_40901000000000000"><a href="#" onclick="tcdialog('40901000000000000')">视频资源</a><span>(0)</span></li>
							<li id="akey_40902000000000000"><a href="#" onclick="tcdialog('40902000000000000')">执业考试</a><span>(0)</span></li>
							<li id="akey_40903000000000000"><a href="#" onclick="tcdialog('40903000000000000')">培训课程</a><span>(0)</span></li>
							<li id="akey_40904000000000000"><a href="#" onclick="tcdialog('40904000000000000')">文化/论文</a><span>(0)</span></li>
							<li id="akey_40905000000000000"><a href="#" onclick="tcdialog('40905000000000000')">照片图片</a><span>(0)</span></li>
							<li id="akey_40906000000000000"><a href="#" onclick="tcdialog('40906000000000000')">学生专区</a><span>(0)</span></li>
							<li id="akey_40907000000000000"><a href="#" onclick="tcdialog('40907000000000000')">论文、文集</a><span>(0)</span></li>
		        		</ul>
		          </div>
		           <div class="fbzs-10">
		            	<ul>
		        			<li id="akey_41001000000000000"><a href="#" onclick="tcdialog('41001000000000000')">新技术信息</a><span>(0)</span></li>
							<li id="akey_41002000000000000"><a href="#" onclick="tcdialog('41002000000000000')">新技术应用</a><span>(0)</span></li>
							<li id="akey_41003000000000000"><a href="#" onclick="tcdialog('41003000000000000')">工程示范</a><span>(0)</span></li>
							<li id="akey_41004000000000000"><a href="#" onclick="tcdialog('41004000000000000')">绿色建筑</a><span>(0)</span></li>
							<!--
							<li id="akey_40505000000000000"><a href="#" onclick="tcdialog('40505000000000000')">质量策划</a><span>(0)</span></li>
							<li id="akey_40104000000000000"><a href="#" onclick="tcdialog('40104000000000000')">四新技术</a><span>(0)</span></li>
							<li id="akey_40701000000000000"><a href="#" onclick="tcdialog('40701000000000000')">职业健康安全</a><span>(0)</span></li>
		        			-->
		        		</ul>
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
	<div id="hello"></div>
</body>
</html>
