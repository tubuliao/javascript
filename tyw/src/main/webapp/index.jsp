<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<jsp:include page="${pageContext.request.contextPath}/top.jsp" />


<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>天佑网首页</title>
<link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
<LINK rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/search.css">
<LINK rel=stylesheet type=text/css href="${pageContext.request.contextPath}/css/home.css">
 <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js"></script>
 <script src="${pageContext.request.contextPath}/js/rotate3di.js"></script>
<script src="${pageContext.request.contextPath}/js/rotateCss.js"></script>
<script src="${pageContext.request.contextPath}/js/Jquery.L.Message.js"	type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/js/search_ajax.js" type="text/javascript"></script>
 <style type="text/css">
   
	.back{
		width:100%;
	    height:100%;
	    background:#484848;
 		padding:2px;
 		display:none;
	}  
	.back ul{ margin-left:20px; margin-top:20px; line-height:25px; font-size:14px}
	.front{
		 
	}  
    </style>
<script type="text/javascript" >

	$(window).load(function () {
		//3D 旋转
   	    $('.back').hide().css('left', 0);    
	    function mySideChange(front) {
	        if (front) {
	            $(this).parent().find('.front').show();
	            $(this).parent().find('.back').hide();
	        } else {
	            $(this).parent().find('.front').hide();
	            $(this).parent().find('.back').show();
	        }
	    }    
	    $('#info_show li').hover(
	        function () {
	            $(this).find('div').stop().rotate3Di('flip', 250, {direction: 'clockwise', sideChange: mySideChange});
 	 	   },
	        function () {
	            $(this).find('div').stop().rotate3Di('unflip', 500, {sideChange: mySideChange});
	        }
	    );
	  
	 	 //统计首页数量
  	 	$.post("/BaseController/getCount",function(data){
 	  	  	 $.each(data,function(i){
    		  	  	 for(var j=0;j<11;j++){
 	 		  	  		if(data[i].name==$("#info_show .left:eq("+j+")").text()){
	 		  	  	 		$("#info_show  .left:eq("+j+")").parent().nextAll("#info_show  .num").html(data[i].count)
			  	  	 		return ;
			  	  	  	 }
				 }
		  	 });
 	  	});
	 	$.post("/BaseController/getCountfbfx",function(data){
			$("#fbfx").html(data.count);
	  	});
	 	
 	  	
  	});
//最新动态和最新新闻
$(window).load(function(){
 	var content="";
 	var html="";
 	var html1="";
 	var html2="";
 	$.post("/index/information",{type:1},function(data){
  		 $.each(data,function(i){
  	  		 var kv=$("#keyword").val();
 			    content= content+"<li><a title='"+data[i].title+"' href='${pageContext.request.contextPath}/detail/"+data[i].infoType+"/"+data[i].id+"?keyword=''' target=\"_blank\" >"+data[i].title+"</a> </li>"; 
   				$("#con_new_1").html(content);
  		 });
    },"json");
 	
 	$.post("/announcelist/data",function(result){
 		var total = result.total;
 		 $.each(result.rows,function(i,t){
 			 if(i == 0){
 				html1= "<a href='/announcetotallist?annId="+t.id+"' target='_blank'><img src='/images/home/new.gif' />"+(i+1) + "." + t.title + "</a>&nbsp;&nbsp;&nbsp;&nbsp;";
 			 }else if(i == 1){
 				html2= "<a href='/announcetotallist?annId="+t.id+"' target='_blank'><img src='/images/home/new.gif' />"+(i+1) + "." + t.title + "</a>&nbsp;&nbsp;&nbsp;&nbsp;"; 
 			 }else{
 				html+= "<a href='/announcetotallist?annId="+t.id+"' target='_blank'>"+(i+1) + "." + t.title + "</a>&nbsp;&nbsp;&nbsp;&nbsp;"; 
 			 }
 		 });
 		 html = html1 + html2 + html;
 		 $("#announce").html(html);
   },"json");
})
function news(type,connews){
	var content="";
 	$.post("/index/information",{type:type},function(data){
  		 $.each(data,function(i){
  	  		 var kv=$("#keyword").val();
 			    content= content+"<li><a title='"+data[i].title+"' href='${pageContext.request.contextPath}/detail/"+data[i].infoType+"/"+data[i].id+"?keyword=''' target=\"_blank\" >"+data[i].title+"</a> </li>"; 
    				$("#"+connews).html(content);
 		 });
    },"json");
}

function newsKnowledge(type,connews){
	var dAccess = $('#dAccess').val();//访问权限
	var content="";
 	$.post("/index/information",{type:type},function(data){
  		 $.each(data,function(i){
  	  		 var kv=$("#keyword").val();
  	  		if(dAccess=='0'){
  	  			content= content+"<li class='knowledge'><a title='"+data[i].title+"' href='javascript:void();'  >"+data[i].title+"</a> </li>"; 
			}
			if(dAccess=='1'){
				content= content+"<li class='knowledge'><a title='"+data[i].title+"' href='${pageContext.request.contextPath}/detail/"+data[i].infoType+"/"+data[i].id+"?keyword=''' target=\"_blank\" >"+data[i].title+"</a> </li>"; 
			}
			if(dAccess=='2'){
				content= content+"<li class='knowledge'><a title='"+data[i].title+"' href='javascript:void();'  >"+data[i].title+"</a> </li>";
			}
			if(dAccess=='3'){
				content= content+"<li class='knowledge'><a title='"+data[i].title+"' href='javascript:void();'  >"+data[i].title+"</a> </li>";
			}
 			   // content= content+"<li><a title='"+data[i].title+"' href='${pageContext.request.contextPath}/detail/"+data[i].infoType+"/"+data[i].id+"?keyword=''' target=\"_blank\" >"+data[i].title+"</a> </li>"; 
    		$("#"+connews).html(content);

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
    },"json");
}


function getlastlytheme(connews){
	var dAccess = $('#dAccess').val();//访问权限
	var content="";
 	$.post("/theme/getlastlytheme",function(data){
  		 $.each(data,function(i){
  	  		 var kv=$("#keyword").val();
  	  		if(dAccess=='0'){
  	  			content= content+"<li class='knowledge'><a title='"+data[i].title+"' href='javascript:void();'  >"+data[i].title+"</a> </li>"; 
			}
			if(dAccess=='1'){
				content= content+"<li class='knowledge'><a title='"+data[i].title+"' href='${pageContext.request.contextPath}/theme/goThemeDetail?themeId="+data[i].id+"''' target=\"_blank\" >"+data[i].title+"</a> </li>"; 
			}
			if(dAccess=='2'){
				content= content+"<li class='knowledge'><a title='"+data[i].title+"' href='javascript:void();'  >"+data[i].title+"</a> </li>";
			}
			if(dAccess=='3'){
				content= content+"<li class='knowledge'><a title='"+data[i].title+"' href='javascript:void();'  >"+data[i].title+"</a> </li>";
			}
    		$("#"+connews).html(content);
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
    },"json");
}

	function setTabnews(name,cursel,n){
 	 		for(i=1;i<=n;i++){
		  		var menu=document.getElementById(name+i);
		 		var con=document.getElementById("con_"+name+"_"+i);
		 		menu.className=i==cursel?"hover":"";
		 		con.style.display=i==cursel?"block":"none";
	 		}
	 		 if(cursel==1){
	 			news(cursel,"con_new_1");
		 	  }else if(cursel == 2){
		 		 newsKnowledge(cursel,"con_new_2");
			 }else if(cursel == 3){
				 getlastlytheme("con_new_3");
			 }else{
				 
			 }
  		}

 
	</script>	
</head>
<body>
<div id='hello'/>
							<c:if test="${empty DetailAccess}">
								<input type="hidden" id='dAccess' name='dAccess' value='0'/>
                    		</c:if>
                    		<c:if test="${!empty DetailAccess}">
                    			<sec:authorize ifAllGranted="ADMINISTRATOR">
                    				<input type="hidden" id='dAccess' name='dAccess' value='1'/>
                    			</sec:authorize>
                    			<sec:authorize ifNotGranted="ADMINISTRATOR">
                    				<c:if test="${DetailAccess==1 }">
                    					<input type="hidden" id='dAccess' name='dAccess' value='1'/>
	                    			</c:if>
	                    			<c:if test="${DetailAccess==0 }">
	                    				<input type="hidden" id='dAccess' name='dAccess' value='2'/>
	                    			</c:if>
	                    			<c:if test="${DetailAccess==2 }">
	                    				<input type="hidden" id='dAccess' name='dAccess' value='3'/>
	                    			</c:if>
                    			</sec:authorize>
							</c:if>
                    		
<input type="hidden" id="isindex" value="isindex"></input>
<div class="homebg">
	<div class="wordmap"></div>
    <div class="leaf"></div>
</div>

<div class="clear"></div>

<div class="body"  style="margin-top: 30px">
  <jsp:include page="${pageContext.request.contextPath}/top_search.jsp" /> 
 
  
 <!--滚动开始--> 
 <div class="block_box">
    	<div class="block_nav" id="info_show">
    	<ul>
        	<li class="block b_1">
            	<h3 class="front"><div class="left">资讯信息</div><div class="right"><img src="/images/home/homejt_08.png"></div></h3>
                <div class="back">
                <ul>
                	<li><a target='_blank'  href="/knowledge/web/newTable?rootCode=40101000000000000">土木资讯</a></li>
				      <li><a target='_blank' href="/knowledge/web/displayKnowledgeByDate?rootCode=40102000000000000">法律法规</a></li>
				      <li><a target='_blank'  href="/knowledge/web/bookList?rootCode=40103000000000000&sourceType=1">标准规范</a></li>
				      <li><a target='_blank'  href="/knowledge/web/bookList?rootCode=40105000000000000&sourceType=2">施工图集</a></li>
                </ul>
                </div>
                <div class="num">0</div>
            </li>
            <li class="block b_2">
              <h3 class="front">
                <div class="left">核心技术</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="back">
                <ul>
                 <li><a target='_blank'  href="/knowledge/web/anotherTable?rootCode=40201000000000000">强制性条文</a></li>
      <li><a target='_blank'   href="/specialKnowledge/web/ClauseNew/1">规范条文</a></li>
      <li><a target='_blank'  href="/knowledge/web/newTable?rootCode=40203000000000000">安全条文</a></li>
      <li><a target='_blank'  href="/knowledge/web/otherTable?rootCode=40205000000000000">工法</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40206000000000000">工艺</a></li>
                </ul>
              </div>
              <div class="num">0</div>
            </li>
            <li class="block">
            	<img src="/images/home/home_05.gif" />
            </li>
            <li class="block b_3">
            	<h3 class="front"><div class="left">技术文件</div><div class="right"><img src="/images/home/homejt_08.png"></div></h3>
                <div class="back">
                <ul>
                	 <li><a  target='_blank' href="/knowledge/web/newTable?rootCode=40306000000000000">投标施组</a></li>
      <li><a target='_blank' href="/knowledge/web/newTable?rootCode=40301000000000000">实施性施组</a></li>
      <li><a target='_blank'    href="/knowledge/web/sgfaform?rootCode=40302000000000000">施工方案</a></li>
      <li><a  target='_blank' href="/knowledge/web/form?rootCode=40303000000000000">技术交底</a></li>
                </ul>
                </div>
                <div class="num">0</div>
            </li>
            <li class="big">
            	<img src="/images/home/home_09.gif" />            </li>
            <li class="block b_4">
              <h3 class="front">
                <div class="left">工程施工</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="back">
                <ul>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40401000000000000">施工做法</a></li>
      <li><a target='_blank'   href="/knowledge/web/form?rootCode=40402000000000000">经验技巧</a></li>
      <li><a target='_blank' href="/knowledge/web/form?rootCode=40403000000000000">数据</a>·<a target='_blank' href="/knowledge/web/otherTable?rootCode=40404000000000000">案例</a></li>
      <li><a target='_blank' href="/knowledge/web/form?rootCode=40405000000000000">计算</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40406000000000000">详图</a></li>
                </ul>
              </div>
              <div class="num">0</div>
            </li>
            <li class="block b_5">
              <h3 class="front">
                <div class="left">工程质量</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="back">
                <ul>
                    <li><a target='_blank'  href="/knowledge/web/form?rootCode=40501000000000000">质量检验</a></li>
      <li><a target='_blank'    href="/knowledge/web/form?rootCode=40502000000000000">质量控制</a></li>
      <li><a target='_blank'   href="/knowledge/web/form?rootCode=40503000000000000">质量通病</a></li>
      <li><a  target='_blank'  href="/knowledge/web/newTable?rootCode=40504000000000000">工程创优</a></li>
                </ul>
              </div>
              <div class="num">0</div>
            </li>
            <li class="block b_6">
              <h3 class="front">
                <div class="left">资料表格</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="back">
                <ul>
                  <li><a  target='_blank'  href="/knowledge/web/form?rootCode=40601000000000000">工程资料</a></li>
      <li><a  target='_blank'  href="/knowledge/web/form?rootCode=40603000000000000">新规范表格</a></li>
      <li><a  target='_blank' href="/knowledge/web/form?rootCode=40602000000000000">检测机构用表</a></li>
      <li><a  target='_blank' href="/knowledge/web/newTable?rootCode=40604000000000000">管理常用表</a></li>
                </ul>
              </div>
              <div class="num">0</div>
            </li>
            <li class="block b_7">
              <h3 class="front">
                <div class="left">安全环保</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="back">
                <ul>
                    <li><a  target='_blank'  href="/knowledge/web/newTable?rootCode=40701000000000000">安全管理</a></li>
      <li><a  target='_blank'  href="/knowledge/web/form?rootCode=40702000000000000">安全技术</a></li>
      <li><a  target='_blank'  href="/knowledge/web/form?rootCode=40703000000000000">安全资料</a></li>
      <li><a  target='_blank'  href="/knowledge/web/newTable?rootCode=40704000000000000">环境保护</a></li>
                </ul>
              </div>
              <div class="num">0</div>
            </li>
            
            <li class="block b_8">
              <h3 class="front">
                <div class="left">施工管理</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="back">
                <ul>
                  <li><a target='_blank'  href="/knowledge/web/newTable?rootCode=40804000000000000">工程管理</a></li>
			      <li><a target='_blank'  href="/knowledge/web/newTable?rootCode=40803000000000000">招投标</a>·<a target='_blank' href="/knowledge/web/newTable?rootCode=40805000000000000">造价</a></li>
			      <li><a target='_blank'  href="/knowledge/web/newTable?rootCode=40806000000000000">物资</a>·<a target='_blank' href="/knowledge/web/newTable?rootCode=40801000000000000">机械</a></li>
			      <li><a target='_blank'  href="/knowledge/web/newTable?rootCode=40802000000000000">试验</a>·<a target='_blank' href="/knowledge/web/newTable?rootCode=40807000000000000">测量</a></li>
                </ul>
              </div>
              <div class="num">0</div>
            </li>
            
             <li class="block b_9">
              <h3 class="front">
                <div class="left">教育执考</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="back">
                <ul>
                 <li><a target='_blank'  href="/knowledge/web/newTable?rootCode=40903000000000000">培训课程</a></li>
     			 <li><a target='_blank'  href="/knowledge/web/newTable?rootCode=40902000000000000">执业考试</a></li>
     			 <li><a target='_blank'  href="/toPackageList">视频</a>·<a target='_blank' href="/knowledge/web/newTable?rootCode=40905000000000000">图片</a></li>
     			 <li><a target='_blank'  href="/knowledge/web/newTable?rootCode=40904000000000000">论文</a>·<a target='_blank' href="/knowledge/web/newTable?rootCode=40906000000000000">学生</a></li>
                </ul>
              </div>
              <div class="num">0</div>
            </li>
           
            
            <li class="bigblock b_10">
            	<h3 class="front"><div class="left">四新技术</div><div class="right"><img src="/images/home/homejt_08.png"></div></h3>
                <div class="back">
                <ul>
                	      <li><a target='_blank' href="/knowledge/web/newTable?rootCode=41001000000000000">新技术信息</a></li>
      <li><a target='_blank' href="/knowledge/web/newTable?rootCode=41002000000000000">新技术应用</a></li>
      <li><a target='_blank' href="/knowledge/web/newTable?rootCode=41003000000000000">工程示范</a></li>
      <li><a target='_blank' href="/knowledge/web/displayKnowledgeByDate?rootCode=41004000000000000">专利技术</a></li>
                </ul>
                </div>
                <div class="num">0</div>
            </li>
            <li class="bigblock2 b_11">
            	<h3>
           	    <div class="left">
           	    <a target='_blank' href="/knowledge/web/fbfxChannel?fbfxCode=10000000000000000">分部分项</a></div><div class="right"><img src="images/home/homejt_08.png"></div></h3>
                <div class="num" id="fbfx">0</div>
            </li>
            
        </ul>
    </div>
    <div class="news">
    	<h3 class="front"><a id="new1" onclick="setTabnews('new',1,3)"  class="hover" href="#">最新动态</a> <a id="new2" onclick="setTabnews('new',2,3)" href="#">最新更新</a><a id="new3" onclick="setTabnews('new',3,3)" href="#">专题列表</a>  </h3>
        <ul id="con_new_1">
        	 
        </ul>
         <ul id="con_new_2" style="display:none">
        	 
        </ul>
         <ul id="con_new_3" style="display:none">
        	<li> <a target="_blank" href="/theme/goThemeDetail?themeId=1#">钢筋焊接工程质量员范本</a></li>
        </ul>  
    </div>
  </div>
     <!--滚动--> 
	<div class="clear"></div>
 <div class="shang10"></div>
 <div class="bottom">
    <div class="home_foot">
<div class="left">
联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客| <a target="_blank" href="/map.jsp">网站地图</a>| <a target="_blank" href="/toSuggest">您的意见</a>|&nbsp;<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=2945694238&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:2945694238:51" alt="欢迎来到天佑网，可以点击图像咨询！" title="欢迎来到天佑网，可以点击图像咨询！"/></a>
    </div><div class="right">天佑网版权所有　©2013　京ICP备13028869-1号  版本号 V1.0.8</div> 
<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/js/google.js"></script> -->
</div>
</div>

</div>
<!-- <div class="update_notice" id="update_notice">
	<div class="update_notice_box">
    <img src="images/xtgxts_75.png" />
    <img class="update_notice_close" onclick="update_notice.style.display='none'" src="images/close.png" />
	</div>
</div>
 -->
</body>
</html>
