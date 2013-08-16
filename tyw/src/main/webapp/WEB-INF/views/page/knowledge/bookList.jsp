<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>书籍列表</title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<link rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/book.css"/>
<link href="${pageContext.request.contextPath}/jquery-ui-1.10.2/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
<link  href="${pageContext.request.contextPath}/css/pagination.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/ztree3.5/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
   <style type="text/css">
   
   
	.highlight{
	background:#FF0;
 	color:#E00;
	}  
    </style>
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.pagination.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/js/clausetable.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxbase.js"	type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/ztree3.5/js/jquery.ztree.core-3.5.js"></script>
<!--[if lt IE 7]>
        <script type="text/javascript" src="unitpngfix.js"></script>
<![endif]-->
<script type="text/javascript">
	var items_per_page = 5;
	var page_index = 0;
	var sTitle = "";
	var sType =  "";
	var dFrom = "";
	var dTo = "";
	var st = "";
	var sourceType = "";
	var zTree;
	var firstVisit = "";

	function getDataList(index){
		//alert("??");
		var pageIndex = index;
		sTitle = encodeURIComponent(encodeURIComponent($('#title').val()));
		//sType =  encodeURIComponent(encodeURIComponent($('#standardType').val()));
		dFrom = $('#dateFrom').val();
		dTo = $('#dateTo').val();
		st = encodeURIComponent(encodeURIComponent($("#st").val()));
		// 来源类型
		sourceType = $("#sourceType").val();
		// 是否首次
		firstVisit = $("#firstVisit").val();
		$.ajax({
			type: "get",
			url: encodeURI("${pageContext.request.contextPath}/pagination/bookList"),
			data: "pageIndex=" + pageIndex + '&itemsPerPage=' + items_per_page + '&standardTitle=' + sTitle + '&dateFrom=' + dFrom + '&dateTo=' + dTo + '&standardType=' + st + "&sourceType=" + sourceType + '&firstVisit=' + firstVisit + "&" + Math.random(),
			dataType: 'json',
			contentType: "text/html; charset=utf-8",
			//contentType: "application/x-www-form-urlencoded",
			success: function(r){
				var total = r.total;
				var html = '' ;
				$.each(r.list,function(i,s){	
						html += '<li>' +
						'<div class="book_list_book">' +
							'<p><a href="javascript:void(0);" onclick="toBookDetail(\'' + s.standardName + '\', \'' + s.standardNo + '\');">' + (s.standardName.substring(0, 15)) + '</a></p>' +
						'</div>' +
						'<div class="book_info">' +
							'<div class="book_info_title">' +
								'<div class="left"><a href="javascript:void(0);" onclick="toBookDetail(\'' + s.standardName + '\', \'' + s.standardNo + '\');">' + s.standardName + '</a></div>' +
								'<div style="padding-left:400px;font-size:12px;">标准编号：' + (s.standardNo == null ? '暂无' : s.standardNo) + '</div>' + 
								'<div class="right"><a href="javascript:void(0);" onclick="toBookDetail(\'' + s.standardName + '\', \'' + s.standardNo + '\');"><img src="${pageContext.request.contextPath}/images/new/book_17.gif"/></a></div>' +
							'</div>' +			
							'<div class="clear"></div>' +
							'<ul>' +
								'<li><span>标准类型：</span>' + (s.standardType == null ? '暂无' : s.standardType) + '</li>' +
								'<li><span>实施日期：</span>' + (s.executeDate == null ? '暂无' : s.executeDate) + '</li>' +
								/* '<li><span>标准编号：</span>' + (s.standardNo == null ? '暂无' : s.standardNo) + '</li>' + */
								'<li><span>主编单位：</span>' + (s.editDepartment == null ? '暂无' : s.editDepartment) + '</li>' +
								'<li><span>批准部门：</span>' + (s.approveDepartment == null ? '暂无' : s.approveDepartment) + '</li>' +
							'</ul>' +
							'<div class="clear"></div>' +
							'<div class="book_shotinfo">书籍简介</div>' +
							'<p>' + (s.description == null ? '暂无简介！' : s.description.substring(0, 100)+'……') + '</p>' +
						'</div>' +
						'<div class="clear"></div>' +
					'</li>';
					
				});
				if(html == '') {
					html = "<font color='red'>文件正在整理上传中，如果您工作中急需，请与我们联系</font>";
				}
				
				$('#searchresult').html(html);
				
				//分页
				if($("#pagination").html().length == ''){
					//alert('xx');
					$('#pagination').pagination(total, {
						'items_per_page'      : items_per_page,
						'num_display_entries' : 6,
						'num_edge_entries'    : 2,
						'prev_text'           : "上一页",
						'next_text'           : "下一页",
						'callback'            : pageselectCallback
					});
				}
			}
			
		});
	}

	function pageselectCallback(page_index, jq){
		getDataList(page_index);
	}

	$(document).ready(function(){
		//alert("pagination") ;
		$('#dateFrom').val('1900-01-01');
		$('#dateTo').val('2999-12-12');
		getDataList(page_index);
		//知识性质树
		zTree = $.fn.zTree.init($("#treeDemo"), setting);
	});

	var setting = {
		async : {
			enable : true,
			url : "/getTagList",
			autoParam : [ "id", "code", "level", "name" ],
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
		// 非首次访问
		$("#firstVisit").val("false");
		$("#st").val(treeNode.name);
		$("#pagination").html('');
		//$('#standardType').val('');
		getDataList(page_index);
		//$('#standardType').val('全部');
		return true;
	};
	function beforeClick(treeId, treeNode) {
		zTree.selectNode(treeNode);
		// 非首次访问
		$("#firstVisit").val("false");
		$("#st").val(treeNode.name);
		$("#pagination").html('');
		//$('#standardType').val('');
		getDataList(page_index);
		//$('#standardType').val('全部');
		if (!treeNode.isParent) {
			return true;
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
	
	
	
	function sub() {
		// 非首次访问
		$("#firstVisit").val("false");
		sTitle = encodeURIComponent(encodeURIComponent($('#title').val()));
		//sType =  encodeURIComponent(encodeURIComponent($('#standardType').val()));
		dFrom = $('#dateFrom').val();
		dTo = $('#dateTo').val();
		//点击查询，暂不带st
		$("#st").val("");
		//alert(sTitle + "  **  " + sType);
		$("#pagination").html('');
		getDataList(page_index);
	}

	function checkDate() {
		//alert(new Date($('#dateFrom').val()));
		if( new Date($('#dateFrom').val()) > new Date($('#dateTo').val())) {
			$.messager.alert('提示：', '起始时间不能大于终止时间！');
			return false;
		}
	}

	function toBookDetail(sName, sNo) {
		//alert(typeof sName);
		var sourceCompose = '《' + sName + '》' + sNo ;
		//alert(sourceCompose);
	//	location.href="${pageContext.request.contextPath}/knowledge/web/bookdetail/" + encodeURIComponent(encodeURIComponent(sourceCompose));	// 两次编码 java在后台会自动的进行一次解码 但结果不对
		window.open("${pageContext.request.contextPath}/knowledge/web/bookdetail/" + encodeURIComponent(encodeURIComponent(sourceCompose)), '_blank');
	}
</script>

</head>

<body>
<div class="body">
 
<div class="clear"></div>
  <div>
  <jsp:include page="${pageContext.request.contextPath}/top.jsp" />
  <jsp:include page="/menu.jsp"></jsp:include>
  <jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />
  </div>
 <div id="searchCombination" class="fcontrol" title="高级查询"
											style="font:15px宋体;width: 250px; height:500px; display: none;cursor:help;line-height:30px;"></div>
 <div class=" new_box">
 	<div class="new_body">
    	<jsp:include page="/summary.jsp"></jsp:include>
        <div class="">
          <div class="new_search"  style="display: none">
         	<form>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="table2">
  <tr>
    <td width="10%" height="30" align="right"><div>标题：</div></td>
     <td width="24%" height="30"><input name="standardTitle"   id="title" type="text" class="xmm_input_s"/></td>
    </tr>
     <tr>
    <%-- <td width="9%" height="30" align="right"><div>类型：</div></td>
    <td width="16%" height="30">
		<select name="select" size="1" id="standardType">
			  <option value="%" selected>-全部-</option>
			  <c:forEach items="${standardTypeList}" var="st" varStatus="idx">
				<option value="${st}">${st}</option>
			  </c:forEach>
		</select>
		<input type="hidden" id="st" />
	</td>
 
    <td width="24%" height="30"><input name="title" id="title" type="text" class="xmm_input_s width90"/> --%>
    <input type="hidden" id="st" /><input type="hidden" id="sourceType" value="${sourceType }" />
    	<input type="hidden" id="firstVisit" value="true"></td>
 	  </tr>
	  <tr>
    <td width="8%" height="30" align="right"><div>日期：</div></td>
    <td width="33%" height="30"><input name="dateFrom"   type="text" class="Wdate" id="dateFrom" size="15" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'dateTo\')||\'%y-%M-%d\'}'})" onblur="checkDate();" readonly/>
    至
      <input name="dateTo" type="text" class="Wdate" id="dateTo" size="15" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',minDate:'#F{$dp.$D(\'dateFrom\')}'})" onblur="checkDate();"  readonly/></td>
  </tr>
</table>
<div class="new_search_bottom">
<input type="button"   id="new_chaxun"  class="new_chaxun right you10" value="点击查询" />
</div>
              <div class="clear"></div>
       	  </form>
          
        </div>
        
        <!-- 知识性质树 -->
        <div class="p_tree">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		
		<!-- 查询结果列表 -->			
        <div class="new_right_mulu mulu_list">
          <div class="new_right_book_title">
            <div class="left">书籍列表</div>
            <div class="clear"></div>
          </div>
          <div class="book_list">
          <ul>
          	<!--结果列表start-->
			<div id="searchresult">
			</div>
			<!--结果列表end-->
          </ul>
		<!--分页按钮start-->
			<div class="scott">
				<div id="pagination" class="pagination" style="margin-left:30%;left:-width/2;padding:0 0 30px 0"></div>
			</div>
        <!---分页按钮end--> 
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
	<a href="#"></a>
	<a href="#top"><img title="返回顶部" class="shang20" src="${pageContext.request.contextPath}/images/btnfd_13.gif" /></a>
</div>
</div></div>
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
<script>
	//根据地区查找
	function getDataView(){
		getDataList(page_index);
 	}
</script>
</html>
