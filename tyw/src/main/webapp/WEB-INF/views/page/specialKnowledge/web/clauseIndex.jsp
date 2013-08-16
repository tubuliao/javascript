<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>条文类</title>
<link href="${pageContext.request.contextPath}/css/pagination.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<LINK rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/search.css">
<LINK rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/new.css">
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxbase.js"  type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/clauseIndexNew.js"></script>
<script src="${pageContext.request.contextPath}/js/Jquery.L.Message.js"	type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/search_ajax.js" type="text/javascript"></script>
<style>
 	.highlight{
	background:#FF0;
 	color:#E00;
	}  
    </style>
</head>

<body>

<div class="body">
 
<div class="clear"></div>
  <div>
  <jsp:include page="${pageContext.request.contextPath}/top.jsp" />
  <jsp:include page="/menu.jsp"></jsp:include>

  <jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />
  </div>

 <div class=" new_box">
 	<div class="new_body">
	<jsp:include page="/summary.jsp"></jsp:include>
 	<div class="new_position"><img src="${pageContext.request.contextPath}/images/new/new_08.gif" /> 
        	<span class="path_info">
        		
        	</span>
        </div>

    	
        <div class="new_left" id="left_banner" style="overflow:hidden">
        <div class="new_left_top">
        
        	<a href="javascript:void(0)" class="back_link"><img class="left" src="${pageContext.request.contextPath}/images/new/new_03.gif" /></a>
        	<span><a href="javascript:void(0)" onclick="fenbufenxiang()" >分部分项细部检索</a></span><a href="javascript:void(0)" onclick="fenbufenxiang()"><img class="right fenbufenxiang" src="${pageContext.request.contextPath}/images/new/new_05.gif" /></a>
        </div>
        <%-- <ul class="now" data="${Source[0].parentCode }"> --%>
        <ul class="now" >
          <c:forEach items="${Source}" var="source" varStatus="status">
                    	<!-- <li onclick="getSourceList('核心条文')"> -->
                    	<li>
                    		<c:if test="${source.count!=0}">
                    			<a  title="${source.name}" href="javascript:void(0)" class="a_clicks" data="${source.name}"> ${source.name}&nbsp;&nbsp;<span style="color:green">(${source.count})</span></a>
	                    		<c:if test="${source.leaf==0}">
	                    			<a href="javascript:void(0)"  style="display:inline-block;float:right" class="a_click" level="${source.level}" data="${source.code}" text="${source.name}"><img class="right " title='进入下一级' src="${pageContext.request.contextPath}/images/new/new_17.gif" /></a>
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
      <div style="display:none" id="html_bak">
      	<div id="data_bak">
	      	<ul class="bak_next" data="" style="position: relative;left:214px;">
			</ul>
			<li onclick="getSourceList()" class="bak_li"><a href="javascript:void(0)" class="a_click" data=''>标签1</a></li>
		</div>
		<div id="bak_ul">
		</div>
		<a index="" data="" class="path" href="#"></a>
      </div>
        <div class="new_right">
        <div id="searchCombination" class="fcontrol" title="高级查询"
											style="font:15px宋体;width: 250px; height:350px; display: none;cursor:help;line-height:30px;"></div>
<div class="new_search" style="display:none">
        	<form >
        	  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table2">
                <tr>
                  <td width="10%" height="30" align="right"><div>目录标题</div></td>
                  <td width="24%" height="30"><input name="bookTitle" type="text" class="xmm_input_s" id="title" /></td>
                </tr>
                <tr>
                  <td width="10%" height="30" align="right"><div>书籍来源</div></td>
                  <td width="24%" height="30"><input name="bookSource" type="text" class="xmm_input_s" id="bookSource" /></td>
                </tr>
                <tr>
	                  <td width="10%" height="30" align="right"><div>标准</div></td>
	                  <td width="24%" height="30">
	                  <select id="bookStandard">
	                  	<option value="">全部</option>
	                  	<option value="GB">GB国家标准</option>
	                  	<option value="DB">DB地方标准</option>
	                  	<option value="JGJ">JGJ建筑行业标准</option>
	                  	<option value="CJJ">CJJ城镇建设工程行业标准</option>
	                  	<option value="CECS">CECS工程部推荐标准</option>
	                  </select></td>
                 </tr>
                  <!-- <td width="9%" height="30" align="right"><div>分部分项</div></td>
                  <td width="24%" height="30"><input name="BQItem" type="text" class="xmm_input" id="BQItem" /></td> -->
                </tr>
              </table>
              <!-- <button class="new_chaxun" onclick="queryForm();">查询</button> -->
              <input class="new_chaxun" type="button" id="new_chaxun"  value="查询"/>
              <div class="clear"></div>
       	  </form>
</div>
        
          <div class="new_right_book" id="new_right_book">
           
          <div class="new_right_book_title"><div class="left">书籍来源</div><!-- div class="right"><img src="${pageContext.request.contextPath}/images/new/new_24.gif" /><img src="${pageContext.request.contextPath}/images/new/new_26.gif" /></div-->
          <div class="clear"></div>
          </div>
          		 <div id="bookcontent"></div>
          
            
             <div id="bookPag" class="pagination"></div>
          </div>
          <div class="new_right_mulu" id="ss">
         
          
          <div class="new_right_book_title">
            <div class="left">目录结构</div>
            <!-- div class="right"><a href="#"><span>更多></span></a></div-->
            <div class="clear"></div>
          </div>
          <div class="mulu_select">
          	<a  id="filter1" onclick="setabFilter('filter',1,2)" class="hover" href="javascript:void(0)">书籍全目录</a>
          	<a  id="filter2" onclick="setabFilter('filter',2,2)" href="javascript:void(0)">过滤结果</a>
          </div>
          <div class="mulu_list"  id="con_filter_1">
          		<div id="new_right_mulu"></div>
          </div>
          
          <div class="mulu_list"  id="con_filter_2" style="display:none">
	           <div id="new_right_mulu_filters"></div>
          </div>
          
          
          
          
          
          
          
           <div id="paginations" class="pagination"></div>
        </div>
           </div>
            
        <div class="clear"></div>
 </div> 	
 </div>  
 <input type="hidden" id="hiderow_Count" />
 <input type="hidden" id="hiderowContent_Count" />
  <div class="clear"></div>
  <div class="foot">
<!-- 联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号  -->
<jsp:include page="${pageContext.request.contextPath}/bottom.jsp" />
</div></div>

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

<div id="hello"/>
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
