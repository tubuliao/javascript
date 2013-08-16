<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>天佑网,专业建筑网站</title>
     <style>
       	   #tagSearch h4{ margin:0;}
           #tagSearch h4 a{ margin:0; color:#999999;font-size:14px; font-family:"microsoft yahei";margin-right:10px; }
	       #tagSearch .hover{
	       		color:green; background:#; border-bottom:1px solid green; 
	       }
      </style>
 	     <link rel=stylesheet type=text/css href="${pageContext.request.contextPath}/jquery-ui-1.10.2/themes/base/jquery.ui.all.css" />
 	
 	
 	<script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js"></script>
 	<script src="${pageContext.request.contextPath}/js/jquery.pagination.js" type="text/javascript" />
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/homesearch.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/search.js"></script>
	 <!--  <script type="text/javascript" src="${pageContext.request.contextPath}/js/nav_search.js"></script>  -->
	
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/search_advanced.js"></script> -->
	    <!-- <script src="${pageContext.request.contextPath}/js/search_changecheckbox.js" type="text/javascript"></script> -->
	 
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/search_ajax.js"></script>
 <script src="${pageContext.request.contextPath}/jquery-ui-1.10.2/ui/jquery-ui.js"></script>
	
	<script type="text/javascript" >
	$(document).ready(function(){
 			$("#keyword").autocomplete({
				source: function(request,response){
					$.ajax({
			            type: "POST",
			            contentType: "application/json; charset=utf-8",
						url: encodeURI("${pageContext.request.contextPath}/list/suggest/"+$("#keyword").val()),
	                    dataType: "json",
	                    data: request,
	                    success: function(data) {
	                        response(data);
	                    }
		           })
				}
			});
			 //统计首页数量
  			goLuxurySearch('filter',$("#goLuxurySearch").val());
 			
});
	function onkeyWordVal(valkeyword){
		if(valkeyword=="输入要找的内容"){
			$("#keyword").val("");
		}
	}
	function showTip(e) 
	{ 
	 	$("#div1").css("display","block");
	 	$("#div1").css("position","absolute"); 
	 	$("#div1").css("left", e.pageX-170 + "px"); 
		$("#div1").css("top", e.pageY-150 + "px"); 
	}
	
	function closeTip() 
	{ 
	var div1 = document.getElementById('div1'); 
	div1.style.display="none"; 
	} 
        </script>
	<!--[if lt IE 7]>
	        <script type="text/javascript" src="${pageContext.request.contextPath}/js/unitpngfix.js"></script>
	<![endif]-->
 
<style>
.stuff{border:1px #ccc solid;}

#div1{
display:none;
}
</style>
	
    
  
	
</head>
 

<body> 
  

<div class="homebg">
	<div class="wordmap"></div>
    <div class="leaf"></div>
</div>
<div class="clear"></div>
<div class="body">
  
<div class="clear"></div>
<jsp:include page="${pageContext.request.contextPath}/top.jsp" />
<div class="head_top"><a href="/index.jsp"><img src="images/home/logo_home_03.png" class="left" width="183" height="66"  /></a>
 <div class="now_good left">
      <h3>
      <a id="province"><span></span></a><img src="${pageContext.request.contextPath}/images/home/xl_03.gif"/>
      </h3> 
         <h4>${tagName}</h4>
           
             
         <div id="tagSearch" class="fcontrol" title="选词助手：最多选择5个词" style="font:12px;width: 600px; height:800px; display: none;cursor:help;line-height:30px;">
			    	<h4 class="front"><a id="hotTagword1" onclick="hotTag('tagSearch',1,2)"  class="hover" href="#">热标签</a> <a id="hotTagword2"  onclick="hotTag('tagSearch',2,2)" href="#">热词</a> </h4>
			        <ul id="tagSearch_1">
			        	 
			        </ul>
			         <ul id="tagSearch_2" >
			        	 
			        </ul>
		 </div>
         <div id="provinceArea" class="fcontrol" title="省份"
											style="font:15px宋体;width: 250px; height:350px; display: none;cursor:help;line-height:30px;"></div>
         
      
 </div>
  <form class="left"   name="searchform" id="searchform"   >
 
<div class="whitetxt2">
<div id="konwledgeType0" onclick="setTab('konwledgeType',0,5)"  class="hover" >全部</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType1" onclick="setTab('konwledgeType',1,5)"   >文库</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType2" onclick="setTab('konwledgeType',2,5)"  >表格</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType3" onclick="setTab('konwledgeType',3,5)" >图片</div>
  <div  style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType4" onclick="setTab('konwledgeType',4,5)" >视频</div>
   <!--<div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC">|</div>
  <div id="konwledgeType5" onclick="setTab('konwledgeType',5,6)" >文档</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div> -->
  <!--<div id="konwledgeType5" onclick="setTab('konwledgeType',5,5)" >PPT</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>-->
</div>
          <div class="clear"></div>
          <div class="search_input2">
           			 <input id="s" value="1" type="hidden" name="s" />
                    <input id="typeId"   type="hidden" name="typeId" value="${typeId}"/>
                     <input id="onex"  type="hidden"  name="onex" value="${onex}"/>
                     <input id="tag_name"  type="hidden"  name="tag_name" value="${tag_name}"/>
                     
                      <input id="pageNo"   type="hidden" name="pageNo" value="1"/>
                        <input type="hidden" value="0" id="hiderow_Count">
                        <input  value="${goLuxurySearch}" type="hidden" id="goLuxurySearch">
                       
                        <input id="hidAddre"   type="hidden"  />
                        <input id="hidperson"   type="hidden"   />
                        <input  id="hidknole" type="hidden"  />
                        <input id="hidsupart"    type="hidden" />
                       
                    <input class="shurukuang2" id="keyword"  size="40" type="text" name="keyword" 
                    onfocus="onkeyWordVal(this.value)"
					onblur="this.value=(this.value=='') ? '输入要找的内容' : this.value;" value="${keyword}"></input>
					           <input class="search_btn" id="search_btn"  value="全站搜索"  type="button"    style="vertical-align:middle;" />
					 
                     <input class="search_gaoji" type="button" name="button" id="konwledgeType7" value="" />
          
           
       </div>
       <div class="type_choose" style="display: none">	
          <label>
            <input type="radio" name="RadioGroup1" checked="checked" value="100" id="RadioGroup1_0" />
            全部</label>
          <label>
            <input type="radio" name="RadioGroup1" value="2" id="RadioGroup1_1" />
        TXT</label>
          <label>
            <input type="radio" name="RadioGroup1" value="1" id="RadioGroup1_2" />
         WORD</label>
  		<label>
            <input type="radio" name="RadioGroup1" value="6" id="RadioGroup1_3" />
          PPT</label>
      </div>
    </form>
    
    <div class="sousuo_xia" id="searchCondition">
           </div>
    <div class="clear"></div>
</div>
<div class="search_up2"></div>
<table width="1007" border="0" align="center" id="showParemter"  cellpadding="0" cellspacing="0">
      <tr>
        <td width="803" valign="top" background="${pageContext.request.contextPath}/images/bgtm.png">
        <div class="search_result_box left">
            
         <!--   <div class="search_result" > 
            <div class="Filter">
                  <div class="filter_btn " ><a id="advance_search" onclick="click_a('filter')" href="#">高级搜索  <img src="images/search/xia.gif" width="8" height="6" /></a></div>  
        
        	<form>
        	 <div class="selected"  id="selecteddiv">
                           <ul id="Havesel"></ul>
                           <div class="clear"></div>
               </div>
        	<div id="filter" class="Filter_table" style="display:none">
        	
          <table width="750" id="selectedId" border="0" cellpadding="0" cellspacing="1" bgcolor="#EFEFEF" class="shang10 zuo10">
          
              <tr id="tabId">
                      <td height="50" colspan="7" bgcolor="#FFFFFF">
                      <div id="select" class="m" clstag="thirdtype|keycount|thirdtype|select">
                         <div class="select_sort"  >
                          <div class="select_title" id="SubPart_0"><ul><li><a href="javascript:void(0)">分部分项：</a></li></ul></div>
                          <div class="select">
                          <div class="select_bread" id="SubPart_1"></div>
                          <div class="select_list" id="SubPart_2">
                           
                            </div>
                        </div>
                        <div class="clear"></div>
                        <div align="center" style="display:none"  class="shang10"><input value="点击进行筛选" id="search_next1" class="shaixuan" type="button" /></div>
                        </div>
                       
                         <div class="select_sort"  >
                          <div class="select_title" id="knowledge_0"><ul><li><a href="javascript:void(0)">知识性质：</a></li></ul></div>
                          <div class="select">
                          <div class="select_bread" id="knowledge_1"></div>
                          <div class="select_list" id="knowledge_2">
                           
                            </div>
                        </div>
                        <div class="clear"></div>
                                                <div align="center" style="display:none"  class="shang10"><input value="点击进行筛选" id="search_next2" class="shaixuan" type="button" /></div>
                        
                        </div>
                       
                        <div class="select_sort"  >
                          <div class="select_title" id="address_0"><ul><li><a href="javascript:void(0)">地区：</a></li></ul></div>
                          <div class="select">
                          <div class="select_bread" id="address_1"></div>
                          <div class="select_list" id="address_2">
                            
                            </div>
                        </div>
                        <div class="clear"></div>
                                                <div align="center" style="display:none" class="shang10"><input value="点击进行筛选" id="search_next3" class="shaixuan" type="button" /></div>
                        
                        </div>
                        
                         <div class="select_sort"  >
                          <div class="select_title" id="persontype_0"><ul><li><a href="javascript:void(0)">人员分类：</a></li></ul></div>
                          <div class="select">
                          <div class="select_bread" id="persontype_1"> </div>
                          <div class="select_list" id="persontype_2">
                           
                            </div>
                        </div>
                        <div class="clear"></div>
                                                <div align="center" style="display:none"  class="shang10"><input value="点击进行筛选" id="search_next4" class="shaixuan" type="button" /></div>
                        
                        </div>
                          <div class="clear"></div>
                        </div>
                        
                        </div>
                   </td>
                   
              </tr>
                    
          </table>
          </div>
          </form>
        </div>
          		
             </div>-->
            <div id="search_sort" class="search_result search_sort zuo10 xia10"> 
              <div class="left"><img src="images/search/paixu_03.gif" width="19" height="16" /> 排序</div>
              <div class="right">
               
                <select name="sortField" id="sortField">
                  <option value="null">默认权重</option> 
              	  <option value="ClickCount">按浏览量</option> 
                  <option value="CreateDate">按时间</option>
                  <!--    <option value="">按权重排序</option>--> 
                </select>
                
                <select name="sortOrder" id=sortOrder>
                 <option value="null">默认顺序</option>
                  <option value="DESC">降序</option>
                  <option value="ASC">升序</option>
                  
                </select>
                </div>
               </div>
             <div  id="search_result" >
      
          
            </div>
              
            <!--
              <div class="scott" id="scott_page" >
              <a id="firstPage" style="cursor:pointer"> 首页</a>
             	  <a id="lastPage" style="cursor:pointer">上一页 </a>
               	  <a id="nextPage" style="cursor:pointer">下一页 </a>
              <a id="endPage" style="cursor:pointer" >尾页 </a>
           <span   id="pageNum">  [当前第:${pager.pageNo}/${pager.showIndex}页,共${pager.rowCount}记录]</span></div> -->
         
       
		<div class="scott"><div id="Pagination" class="pagination"></div></div>
        </div>
        </td>
      <td width="204" valign="top" background="images/bgtm_05.png"><div class="search_right_box left" >
          <div class="search_right">
              <div class="top10 left">
                <div class="top10_title">  最新知识TOP10 </div>
                <ul>
              
                  <c:forEach items="${pageTopNum}" var="pageTopNum">
                <li>     
                
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
              <!-- a href="#"><img class="tiwen" src="${pageContext.request.contextPath}/images/cs_25.gif" /></a--> </div>
        </div></td>
      </tr>
    </table>
  <div class="search_down"></div>
  <div class="foot">
<!-- 联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号  -->
<jsp:include page="${pageContext.request.contextPath}/bottom.jsp" />
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/google.js"></script>
</div></div>
<div id="fixed" style="display: none" class="fixed"><div class="fixed_box"><div class="filter_btn2"><img src="${pageContext.request.contextPath}/images/search/gaojss_03.gif" /></a></div></div></div>

<div class="guding">
<div class="guding2">

<div class="btn_fd">

	<a href="#"></a>
	<a href="#top"><img title="返回顶部" class="shang20" src="${pageContext.request.contextPath}/images/btnfd_13.gif" /></a>
</div>
</div></div>
</body>
</html>
