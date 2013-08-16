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
<title>专题</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<LINK rel=stylesheet type=text/css href="/css/search.css">
<LINK rel=stylesheet type=text/css href="/css/zhuanti.css">
<link href="${pageContext.request.contextPath}/jquery-ui-1.10.2/themes/base/jquery-ui.css"
			rel="stylesheet" type="text/css" />
<script
			src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js"
			type="text/javascript"></script>
			<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
			<script src="${pageContext.request.contextPath}/js/ajaxbase.js"
			type="text/javascript"></script>
<script type="text/javascript">

$(function(){
	
	$.ajax({
		type : "POST",
		url : "${pageContext.request.contextPath}/theme/viewThemeDetail?"+Math.random(),
		data : {
			"themeId" : $("#themeId").val()
		},
		dataType : 'json',
		success : function(msg) {
	        var html = "";
			$.each(msg,function(x,y){
				 
				if(y.type==0){
					html = '<div class="zt_daoyu">';
					html += '<div class="hongdian">'+(x+1)+'.</div>';
					html += '<div class="zt_daoyu">';
					html += '<div class="zt_kuang">';
					html += '<div class="yinhao1"><img src="../images/zhuanti/zht_18.gif" /></div>';
					html += '<div class="zt_kuang_n">';
					html += '<p class="">';
					html += y.value;
					html += '</p>';
					html += '<div class="yinhao2"><img src="../images/zhuanti/zht_27.gif" /></div>';
					html += ' </div>';
					html += ' <img class="zt_kuang_img" src="../images/zhuanti/zhuanti_15.gif" />            </div>';
					html += ' <div class="clear"></div>';
					html += '  </div>';
					$(".zt_content").html($(".zt_content").html()+html);
				}else
				if(y.type==1){
					var xml = '<div class="zt_liebiao">';
					xml+='<div class="lvdian">'+(x+1)+'.</div>';
					xml+='<div class="zt_kuang">';
					xml+='<div class="zt_liebiao_xtu"><img src="../images/zhuanti/zt_22.gif" /></div>';
					xml+='<h3 class="zt_zt_lv"><img src="../images/zhuanti/zt_25.gif" width="18" height="24" />'+y.title+'：</h3>';
					xml+=' <div class="zt_kuang_n">';
					xml+='<ul id="url_'+(x+1)+'">'; 
					xml+=' <div class="clear"></div>';
					xml+='</ul>';
					xml+='<div class="clear"/></div>';
					
					xml+=' <img class="zt_kuang_img" src="../images/zhuanti/zhuanti_15.gif" />          </div>';
					xml+=' <div class="clear"></div>';
					xml+=' </div>';
					$(".zt_content").html($(".zt_content").html()+xml);
					
					$.ajax({
						type : "POST",
						url : "${pageContext.request.contextPath}/theme/getDetailList?"+Math.random(),
						data : {
							"detailId":y.id
						},
						dataType : 'json',		
						success : function(submsg) {
							 
							var xx='';
							$.each(submsg,function(m,n){
								xx += '<li><a href="/detail/'+n.infoType+'/'+n.id+'" title="'+n.title+'">'+n.title+'</a></li>';
							});
							$("#url_"+(x+1)).html(xx);
						}
					});
				}else
				if(y.type==2){
					
				} 
			});
			
		}
	});
	
			 
});


</script>

</head>

<body>
 <div class="zt_body">
  <jsp:include page="${pageContext.request.contextPath}/top.jsp" />
<div class="clear"></div>
    <div class="zt_banner">
    	<div class="zt_title">
        	<h3>${personalTheme.title}</h3>
            <p>${personalTheme.content}</p>
        </div>
    </div>
    <input type="hidden" id="themeId" value="${personalTheme.id }"/>
    <div class="zt_content">
        
        
        
      
        
  </div>
    <div class="foot">
<!-- 联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号  -->
<jsp:include page="${pageContext.request.contextPath}/bottom.jsp" />
</div>
</div>
</body>
</html>
