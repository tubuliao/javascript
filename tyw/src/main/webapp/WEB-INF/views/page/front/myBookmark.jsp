<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>收藏页面</title>
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<link href="../css/person.css" rel="stylesheet" type="text/css" />
<link href="../jquery-ui-1.10.2/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />

<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.pagination.js" type="text/javascript" />


<SCRIPT language=javascript src="homesearch.js"></SCRIPT>
<!--[if lt IE 7]>
        <script type="text/javascript" src="../unitpngfix.js"></script>
<![endif]-->
<script type="text/javascript" >

 $(function() { 

   $('#checkAll').click(function(){
		if(this.checked) {
			$("input[name='checkname']").each(function() {this.checked=true;}) ;
		} else {
			$("input[name='checkname']").each(function() {this.checked=false;}) ;
		}
   });
	 
	$('#addNewClassification').dialog({
		autoOpen: false, 
		width: 400,
		dialogClass: "my-dialog",
		modal: true
	}); 
	
	$('#deleteClassification').dialog({
		autoOpen: false, 
		width: 400,
		dialogClass: "my-dialog",
		modal: true
	}); 

	$('#choseClassificationDialog').dialog({
		autoOpen: false,
		width: 400,
		dialogClass: 'my-dialog',
		modal: true
	}) ;
	
	$('#deleteClassificationBtn').click(function () {
		$('#deleteClassification').dialog('open');
	});
	
	$('#addClassification').click(function () {
		$('#addTitle').val('');
		$('#addTest').html('');	// 默认清空
		$('#addbtn').attr('disabled', false);
		$('#addNewClassification').dialog('open');
	}); 
	 
	$("#delbtn").click(function(){
		$('#deleteClassification').dialog('close');
	});

	$('#addbtn').click(function() {
		
		// 校验名称的唯一
		var cSize = $('#cSize').val();
		//alert(cSize);
		
		var p = /\s*/gm;
		var newTitle = $('#addTitle').val().replace(p, '');

		//var newTitle = $('#addTitle').val().replace(new RegExp('\\s*', 'gm'), '');		//	去除首位的空字符串 
		//alert(newTitle.length);

		if(newTitle == '') {
			$('#addTest').html('<font color="red">新增类型名称不能为空！</font>');
			$('#addbtn').attr('disabled', true);
			//$('#addTitle').focus();
			return;
		} else {
			$.get('${pageContext.request.contextPath}/testNewTitle', { newT: newTitle}, function(r) {
				if(r.success) {
					//$('#addTest').html('<font color="green">' + r.msg + '</font>');
					$.get('${pageContext.request.contextPath}/addNewClassification', { newTitle: newTitle }, function(result){
						if(result.success) {
							$('#addNewClassification').dialog('close');
							alert('新增成功！') ;
							if(cSize == 0) {
								location.href="${pageContext.request.contextPath}/personCenterMyBookmark" ;	// 首次刷新页面
							} else {
								var cId = result.cf.id ;
								var cTitle = result.cf.title ;
								//alert(cId + '***' + cTitle) ; 
								$("#classification").append("<option value='" + cId + "'>" + cTitle + "</option>");   
								$("#delClassification").append("<option value='" + cId + "'>" + cTitle + "</option>");   
								$("#choseClassificationId").append("<option value='" + cId + "'>" + cTitle + "</option>");
							}
						} else if(r.fail) {
							alert(result.msg) ;
						}
					}, 'json') ;
				} else if(r.fail) {
					$('#addTest').html('<font color="red">' + r.msg + '</font>');
					$('#addbtn').attr('disabled', true);
					//$('#addTitle').focus();
				}
			}, 'json');
		}
	})

	$('#delbtn').click(function() {
		var delVal = $("select[name='delClassification'] option:selected").val() ;
		//alert(delVal) ;
		$.get('${pageContext.request.contextPath}/delOldClassification', { oldId: delVal}, function(result) {
			if(result.success) {
				alert('删除成功！') ;
				location.href="${pageContext.request.contextPath}/personCenterMyBookmark" ;
			} else {
				alert(result.msg) ;
			} 		
		}, 'json')
	}) ;
	
});
 
function cancel(){
	$('#addNewClassification').dialog('close');
	$('#deleteClassification').dialog('close');
	$('#choseClassificationDialog').dialog('close') ;
}

function $$$$$(_sId){
 return document.getElementById(_sId);
 }
function hide(_sId)
 {$$$$$(_sId).style.display = $$$$$(_sId).style.display == "none" ? "" : "none";}
function pick(v) {
 document.getElementById('am').value=v;
hide('HMF-1')
}
function bgcolor(id){
 document.getElementById(id).style.background="#F7FFFA";
 document.getElementById(id).style.color="#000";
}
function nocolor(id){
 document.getElementById(id).style.background="";
 document.getElementById(id).style.color="#788F72";
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}


	var items_per_page = 5;
	var page_index = 0;
	var classification_id = "" ;

	function getDataList(index, cId){
		var pageIndex = index;
		$.ajax({
			type: "get",
			url: "${pageContext.request.contextPath}/pagination",
			data: "pageIndex="+pageIndex+'&items_per_page='+items_per_page+'&classification_id='+cId+'&'+Math.random(),
			dataType: 'json',
			contentType: "text/html; charset=utf-8",
			//contentType: "application/x-www-form-urlencoded",
			success: function(msg){
				var total = msg.total;
				var html = '' ;
				$.each(msg.result,function(i,n){	
					html +=  '<li> ' + 
							  '<div class="data_xuan left"> <input type="checkbox" name="checkname" id="checkbox" value=" ' + n.id + ' "/> </div> ' +
							  ' <div class="data_list_word left"> ' +
							  '	<div class="data_list_title"><a href=" ' + n.url+ ' " target="_blank"> ' + n.title + ' </a></div> ' +
							  '	<div class="data_list_info">收藏时间：' + n.createDate + ' &nbsp;&nbsp;&nbsp;&nbsp;类型：' + (n.classification != null ? n.classification.title : '暂未分类')  + ' </div> ' +
							  ' </div> ' +
							  ' <div class="data_btn_position"> ' +
							  ' <div class="data_btn_box right"> ' +
								' <div class="data_btn"> ' + 
									' <img src="../images/personal/data_btn_18.png" width="10" height="12" /> <a href="#" onClick="choseClassification(\'' + n.id + '\');">移动</a> ' + // id为字符串，会默认为一个变量
									' <img src="../images/personal/data_btn_20.png" width="13" height="12" /> <a href="#" onClick="deleteBookmark(\'' + n.id + '\');">删除</a> ' +
								' </div> ' +
								' </div> ' +
							  ' </div>' +
						     '</li>'
																						         
				});
				$('#Searchresult').html(html);
				
				//分页-只初始化一次
				
				if($("#Pagination").html().length == ''){
					$('#Pagination').pagination(total, {
						'items_per_page'      : items_per_page,
						'num_display_entries' : 5,
						'num_edge_entries'    : 2,
						'prev_text'           : "上一页",
						'next_text'           : "下一页",
						'callback'            : pageselectCallback
					});
				}
				
/*
				$('#Pagination').pagination(total, {
						'items_per_page'      : items_per_page,
						'num_display_entries' : 5,
						'num_edge_entries'    : 2,
						'prev_text'           : "上一页",
						'next_text'           : "下一页",
						'callback'            : pageselectCallback
					});
					*/
			}
			
		});
	}

	function pageselectCallback(page_index, jq){
		getDataList(page_index, classification_id);
	}

	$(document).ready(function(){
		//alert("pagination") ;
		getDataList(page_index, classification_id);
	});
	
	function deleteBookmark(bId) {
		//alert('xx') ;
		
		var r = window.confirm('确定取消该收藏？') ;
		if(r) {
			$.get('/deleteBookmark/' + bId, function(result) {
				if(result.success) {
					getDataList(page_index, classification_id);
					window.alert('取消收藏成功！') ;
				} else {
					alert(result.msg) ;
				}
			}) ;
		}
		
		
	}

	function choseClassification(v) {
		//alert('sdsd') ;
	
		$('#choseClassificationDialog').dialog('open') ;	
		$('#choseBtn').click(function() {
			var classificationId = $("select[name='choseClassificationId'] option:selected").val() ;
			//alert(classificationId) ;
			$.get('/choseClassification', {bId: v, cId: classificationId}, function(result) {
				if(result.success) {
					$('#choseClassificationDialog').dialog('close') ;
					alert('移动成功！') ;
					location.href="${pageContext.request.contextPath}/personCenterMyBookmark" ;
				} else if(result.fail) {
					alert(result.msg) ;
				}
			}, 'json') ;
		}) ;
		
	}


	function listForCid(cId) {
		//alert(cId) ;
		classification_id = cId ;
		$("#Pagination").html('');
		getDataList(page_index, classification_id) ;
	} 

	function delAll() {

		var idArr = '' ;
		// 遍历复选框，获取选中的id
		$("input[name='checkname']").each(function() {
			if(this.checked == true) {
				idArr += $(this).val() + ';' ;
			}
		}) ;

		if(idArr == '') {
			alert('请选择需要取消的收藏！') ;
			return false ;
		} else {
			if(window.confirm('确定取消所选收藏吗？') == true) {
				// ajax批量删除			
				$.get('${pageContext.request.contextPath}/deleteBatchBookmark', { bookmarkIdArr: idArr }, function(result) {
					if(result.success) {
						alert('批量删除成功') ;
						location.href="${pageContext.request.contextPath}/personCenterMyBookmark" ;
					} else {
						alert(result.msg) ;
					}
				}, 'json') ;
				
			} else {
				return false ;
			}
		}
		
	}

	function chooseAll() {
			// 遍历复选框，获取选中的id
			var idArr = '' ;
			$("input[name='checkname']").each(function() {
				if(this.checked == true) {
					idArr += $(this).val() + ';' ;
				}
			}) ;
			if(idArr == '') {
				alert('请选择需要分类的收藏！') ;
				return false ;
			}

			$('#choseClassificationDialog').dialog('open') ;
			// ajax批量移动
			$('#choseBtn').click(function() {
				var classificationId = $("select[name='choseClassificationId'] option:selected").val() ;
				//alert(classificationId) ;
				$.get('${pageContext.request.contextPath}/chooseBatchBookmark', { bookmarkIdArr: idArr, cId: classificationId}, function(result) {
					if(result.success) {
						$('#choseClassificationDialog').dialog('close') ;
						alert('批量移动成功！') ;
						location.href="${pageContext.request.contextPath}/personCenterMyBookmark" ;
					} else {
						alert(result.msg) ;
					}
				}, 'json') ;
		}) ;
			
	}

	function testAddTitle() {
		var newTitle02 = $('#addTitle').val() ;
		//alert(newTitle02);
		if(newTitle02 == '') {
			$('#addTest').html('<font color="red">新增类型名称不能为空！</font>');
			$('#addbtn').attr('disabled', true);
			$('#addTitle').focus();
		} else {
			$.get('${pageContext.request.contextPath}/testNewTitle', { newT: newTitle02}, function(r) {
				if(r.success) {
					$('#addTest').html('<font color="green">' + r.msg + '</font>');
					$('#addbtn').attr('disabled', false);
				} else {
					$('#addTest').html('<font color="red">' + r.msg + '</font>');
					$('#addbtn').attr('disabled', true);
					$('#addTitle').focus();
				}
			}, 'json');
		}
		
	}

	function reTitle() {
		//alert('ss');
		$('#addbtn').attr('disabled', false);
		$('#addTest').html('');
	}

</script>
</head>

<body>

    <div class="search_up"></div>

    <div class="search_content_box">
  <div class="search_content">
<div class="person_nav2">
 <a class="present" href="/personCenterMyBookmark">我的收藏</a> <!-- <a href="/personCentersubmit">我的投稿</a> -->
    </ul>
</div>
<div class="person_left left">
    	<div class="order_title">我的收藏<span class="order_title_en"> / my bookmark</span> </div>
    	<form id="form2" name="form2" method="post" action="">
        <div class="data_title">
          <div class="data_title_left left">
			<input type="checkbox" id="checkAll" />全选
		  </div>
            <div class="data_title_right right">
			收藏类型：<select name="classification" id="classification" onChange="listForCid(this.value);">
						<option value="">-全部-</option>
						<c:forEach items="${cList}" var="cl">
							<option value="${cl.id}">${cl.title}</option>
						</c:forEach>
					  </select>
			<input type="button" value="＋类型" id="addClassification" />
			<input type="hidden" value="${cListSize}" id="cSize" />
			<div id="addNewClassification" class="fcontrol" title="增加收藏类型" style="width:200px;height:100px;display:none" />
				<table style="width:100%">
					<tr>
						<td>类型名称：</td>
						<td><input type="text" name="title" id="addTitle" onMouseDown="reTitle();"/></td>
					</tr>
					<tr>
						<td></td>
						<td><span id="addTest"></span></td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<br />
							<input type="button" value="添加" id="addbtn" />&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" value="取消"  onclick="cancel()"/>
						</td>
					</tr>
				</table>
			</div>	
			
			<div id="deleteClassification" class="fcontrol" title="删除收藏类型" style="width:200px;height:100px;display:none" />
				<table style="width:100%">
					<c:choose>
						<c:when test="${not empty cList}">
							<tr>
								<td align="center">选择需要删除的类型名称：
									<select name="delClassification" id="delClassification" >
										<c:forEach items="${cList}" var="cl">
											<option value="${cl.id}">${cl.title}</option>
										</c:forEach>
								    </select>
								</td>
							</tr>
							<tr>
								<td colspan="1" align="center">
									<br />
									<input type="button" value="删除"  id="delbtn"  />&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" value="取消"  onclick="cancel()"/>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td align="center">
									<br />您没有分类可以删除！
								</td>
							</tr>
							<tr>
								<td colspan="1" align="center">
									<br />
									<input type="button" value="取消"  onclick="cancel()"/>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
			<input type="button" value="－类型" id="deleteClassificationBtn" />
			<img src="../images/personal/data_btn_05.png" width="16" height="16" /> <a href="#" onclick="chooseAll();">移动</a>
			<img src="../images/personal/data_btn_07.png" width="16" height="16" /> <a href="#" onclick="delAll();">删除</a>
			</div>
			<div id="choseClassificationDialog" class="fcontrol" title="选择收藏类型" style="width:200px;height:100px;display:none">
				<table style="width:80%">
					<c:choose>
						<c:when test="${not empty cList}">
							<tr>
								<td align="center">选择类型：
									<select name="choseClassificationId" id="choseClassificationId" >
										<c:forEach items="${cList}" var="cl">
											<option value="${cl.id}">${cl.title}</option>
										</c:forEach>
										<option value="0">全部</option>
									</select>
								</td>
							</tr>
							<tr>
								<td align="center">
									<br />
									<input type="button" value="移动" id="choseBtn" />&nbsp;&nbsp;&nbsp;&nbsp;
									<input type="button" value="取消" onclick="cancel()" />
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td align="center">
									<br />请先增加收藏类型！
								</td>	
							</tr>
							<tr>
								<td align="center">
									<br /><input type="button" value="取消" onclick="cancel()" />
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</div>
        </div>
        <ul id='Searchresult' class="data_list">
        </ul>
		<div class="scott">
		<div id="Pagination" class="pagination"></div>
		</div>

    	</form>
    </div>
    <div class="person_right right">
    	<jsp:include page="right.jsp" ></jsp:include>
        <!-- div class="info_youhui">
       	  <div class="info_youhui_title">继续完善可以获得以下优惠或特权：</div>
          
            <ul>
           	  <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
                <li>1.个性化的获取自己需要的信息</li>
            </ul>
        </div>
        <div class="new_youhui">
        	<div class="new_youhui_title">最新优惠<span class="new_youhui_title_en"> / latest preferential</span> </div>
            <ul>
            	<li><a href="#"><img src="../images/personal/person_11.gif" width="53" height="64" /></a></li>
              <li><a href="#"><img src="../images/personal/person_11.gif" width="53" height="64" /></a></li>
                <li><a href="#"><img src="../images/personal/person_11.gif" width="53" height="64" /></a></li>
            </ul>
        </div -->
    </div>
    <div class="clear"></div>
    </div>
  </div>
  <div class="search_down"></div>
    <div class="clear"></div>
    <div class="foot">
联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客| <a target="_blank" href="/map.jsp">网站地图</a>  天佑网版权所有　©1997-2013　粤ICP备20090191号</div>
</div>
<div class="guding">
<div class="guding2">

<div class="btn_fd">
	<a href="#"></a>
	<a href="#top"><img title="返回顶部" class="shang20" src="../images/btnfd_13.gif" /></a>
</div>
</div></div>

</body>
</html>

