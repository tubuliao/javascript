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
<!--[if lt IE 7]>
        <script type="text/javascript" src="unitpngfix.js"></script>
<![endif]-->
<link rel="stylesheet" href="${pageContext.request.contextPath}/jquery-ui-1.10.2.custom/development-bundle/themes/base/jquery.ui.all.css">
<script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js"></script>
<%-- <script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/js/prototype.js"></script>
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/js/effects.js"></script>
<script language="JavaScript" type="text/javascript" src="${pageContext.request.contextPath}/js/glider.js"></script> --%>
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2.custom/development-bundle/ui/jquery.ui.core.js"></script>
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2.custom/development-bundle/ui/jquery.ui.widget.js"></script>
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2.custom/development-bundle/ui/jquery.ui.position.js"></script>
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2.custom/development-bundle/ui/jquery.ui.menu.js"></script>
<script src="${pageContext.request.contextPath}/jquery-ui-1.10.2.custom/development-bundle/ui/jquery.ui.autocomplete.js"></script>
<script type="text/javascript" >
	$(function(){ 
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
	
	});
 
	function goLuxurySearch(){
 		location.href='${pageContext.request.contextPath}/goLuxurySearch?typeId='+typeId+'';
	}
	function onkeyWordVal(valkeyword){
 		if(valkeyword=="输入要查找的内容"){
			$("#keyword").val("");
		}
	}
	var isclickform=true;
	var iskdform=true;
 function isclick(){
	if($("#keyword").val()=="输入要查找的内容"){
		isclickform=false;
	}
 }
function onkeyword(e){
	if($("#keyword").val()==""){
		var keynum = window.event ? e.keyCode : e.which;
		if(keynum==13)
			iskdform=false;
		}
 	}
 
function issub(){
	if(isclickform==false||iskdform==false){
		 isclickform=true;
		 iskdform=true;
		 return false;
	}
}
function setTab(name,cursel,n){
		for(var i=0;i<=n;i++){
		var menu=document.getElementById(name+i);
		con=document.getElementById("con_"+name+"_"+i);
		menu.className=i==cursel?"hover":"";
		if(menu.className=="hover"){
			if(i==0){//全部
				$("#typeId").val(0);
			}
			if(i==1){//切片
					$("#typeId").val(2);
			}
			if(i==2){//表格
				$("#typeId").val(4);
			}
			if(i==3){//图片
				$("#typeId").val(5);
			}
			if(i==4){//视频
				$("#typeId").val(3);
			}
			if(i==5){//文档
				$("#typeId").val(1);
			}	
			if(i==6){//课件
				$("#typeId").val(6);
			}
			$("#onex").val(name+i);
		}
  	}
}

$(document).ready(function(){
	alert();
	var content="";
 

	
	$.post("/index/information",{type:1},function(data){
		 $.each(data,function(i){
			   // content= content+"<li> <input   id="+data[j].id+"_a"+" type=\"checkbox\" ondoubleclick=\"javascript:return false\" value="+data[j].name+">"+"<a href='javascript:void(0)' id="+data[j].id+">"+data[j].name+"</a></li>"; 
			    content= content+"<li><a  id="+data[i].id+">"+data[i].title+"</a> </li>"; 
			    alert(content);
		 });
    },"json");
})
	</script>	
</head>
<body>

<div class="homebg">
	<div class="wordmap"></div>
    <div class="leaf"></div>
</div>

<div class="clear"></div>

<div class="body"  style="margin-top: 30px">
<jsp:include page="${pageContext.request.contextPath}/top_search.jsp" />

   <!--  <div class="home_logo"><img src="/images/home/logo_home_03.png" width="222" height="81" /></div>
    <div class="home_search">
		<div class="tansuo"><a  href="${pageContext.request.contextPath}/goLuxurySearch?typeId=0" >探索？</a></div>
      <form method="post" action="${pageContext.request.contextPath}/goPageList" name="searchform" id="searchform" onsubmit="return issub()"> 
<div class="whitetxt2">
<div id="konwledgeType0" onclick="setTab('konwledgeType',0,9)"  class="hover" >全部</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType1" onclick="setTab('konwledgeType',1,9)"   >知识</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType2" onclick="setTab('konwledgeType',2,9)"  >表格</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType3" onclick="setTab('konwledgeType',3,9)" >图片</div>
  <div  style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType4" onclick="setTab('konwledgeType',4,9)" >视频</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC">|</div>
  <div id="konwledgeType5" onclick="setTab('konwledgeType',5,9)" >文档</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
  <div id="konwledgeType6" onclick="setTab('konwledgeType',6,9)" >课件</div>
  <div style="TEXT-ALIGN: center; WIDTH: 5px; FLOAT: left; color:#CCCCCC;">|</div>
</div>
        <div class="clear"></div>
        <div class="search_input2">
   			<input id="onex"  type="hidden"   name="onex"/>
			<input id="typeId"   type="hidden"  name="typeId"/>
          <input id="tag_name" value="" type="hidden" name="tag_name" />
           <input id="pageNo" value="1" type="hidden" name="pageNo" />
          <input class="shurukuang2" id="keyword" value="输入要查找的内容" size="40" type="text" name="keyword" 
          	x-webkit-speech="" x-webkit-grammar="builtin:translate" 
          			onfocus="onkeyWordVal(this.value)" onkeydown="return onkeyword(event)"
					onblur="this.value=(this.value=='') ? '输入要查找的内容' : this.value;"/>
          <input class="search_btn" id="search_btn"  value="搜索"  type="submit"  onclick="isclick()" style="vertical-align:middle" />
        </div>
    </div>
    </form> 
  -->
 <!--滚动开始--> 
 <div class="block_box">
    	<div class="block_nav">
    	<ul>
        	<li class="block">
            	<h3><div class="left">资讯信息</div><div class="right"><img src="/images/home/homejt_08.png"></div></h3>
                <div class="list2">
                <ul>
                	<li><a target='_blank' href="/knowledge/web/form?rootCode=40101000000000000">土木资讯</a></li>
                    <li><a target='_blank' href="/knowledge/web/form?rootCode=40102000000000000">法律法规</a></li>
                    <li><a target='_blank' href="/knowledge/web/form?rootCode=40103000000000000">标准</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40105000000000000">图集</a></li>
                    <li><a target='_blank' href="/knowledge/web/form?rootCode=40104000000000000">四新技术</a></li>
                </ul>
                </div>
                <div class="num">8990</div>
            </li>
            <li class="block b_blue">
              <h3>
                <div class="left">核心技术</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="list2">
                <ul>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40201000000000000"><b>强制性条文</b></a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40202000000000000"><b>规范条文</b></a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40203000000000000">安全条文</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40205000000000000">工法</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40206000000000000">工艺</a></li>
                </ul>
              </div>
              <div class="num">8990</div>
            </li>
            <li class="block">
            	<img src="/images/home/home_05.gif" />            </li>
            <li class="block b_blue">
            	<h3><div class="left">技术文件</div><div class="right"><img src="/images/home/homejt_08.png"></div></h3>
                <div class="list2">
                <ul>
                	<li><a target='_blank' href="/knowledge/web/form?rootCode=40306000000000000">投标施组</a></li>
                    <li><a target='_blank' href="/knowledge/web/form?rootCode=40301000000000000">实施性施组</a></li>
                    <li><a target='_blank' href="/knowledge/web/form?rootCode=40302000000000000">施工方案</a></li>
                    <li><a target='_blank' href="/knowledge/web/form?rootCode=40303000000000000">技术交底</a></li>
                </ul>
                </div>
                <div class="num">8990</div>
            </li>
            <li class="big">
            	<img src="/images/home/home_09.gif" />            </li>
            <li class="block">
              <h3>
                <div class="left">工程施工</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="list2">
                <ul>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40401000000000000">施工做法</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40402000000000000"><b>经验技巧</b></a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40403000000000000">数据</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40404000000000000">案例</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40405000000000000">计算</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40406000000000000">详图</a></li>
                </ul>
              </div>
              <div class="num">8990</div>
            </li>
            <li class="block b_green">
              <h3>
                <div class="left">工程质量</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="list2">
                <ul>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40505000000000000">策划</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40501000000000000">验收</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40502000000000000">质量控制</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40503000000000000">质量问题</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40504000000000000">工程创优</a></li>
                </ul>
              </div>
              <div class="num">8990</div>
            </li>
            <li class="block">
              <h3>
                <div class="left">资料表格</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="list2">
                <ul>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40601000000000000">施工资料</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40603000000000000"><b>新规范表格</b></a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40602000000000000">检测用表</a></li>
                  <li><a target='_blank' href="/detail/1/64708a91-98bd-4dc3-ab5a-5ac2a5b3da66">管理用表</a></li>
                </ul>
              </div>
              <div class="num">8990</div>
            </li>
            <li class="block b_green">
              <h3>
                <div class="left">安全环保</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="list2">
                <ul>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40701000000000000">职业健康安全</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40702000000000000">安全技术</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40703000000000000">安全资料</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40705040000000000">现场</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40704000000000000">环保</a></li>
                </ul>
              </div>
              <div class="num">8990</div>
            </li>
            <li class="block b_blue">
              <h3>
                <div class="left">施工管理</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="list2">
                <ul>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40804000000000000">工程管理</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40803000000000000">招投标</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40805000000000000">造价</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40806000000000000">物资</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40801000000000000">机械</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40802000000000000">试验</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40807000000000000">测量</a></li>
                </ul>
              </div>
              <div class="num">8990</div>
            </li>
            <li class="block">
              <h3>
                <div class="left">教育执考</div>
                <div class="right"><img src="/images/home/homejt_08.png" /></div>
              </h3>
              <div class="list2">
                <ul>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40903000000000000">培训</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40902000000000000">执考</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40901000000000000">视频</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40905000000000000">图片</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40904000000000000">文化</a>·<a target='_blank' href="/knowledge/web/form?rootCode=40907000000000000">文集</a></li>
                  <li><a target='_blank' href="/knowledge/web/form?rootCode=40906000000000000">学生专区</a></li>
                </ul>
              </div>
              <div class="num">8990</div>
            </li>
            <li class="bigblock b_green">
            	<h3><div class="left">新技术应用</div><div class="right"><img src="/images/home/homejt_08.png"></div></h3>
                <div class="list2">
                <ul>
                	<li><a target='_blank' href="/knowledge/web/form?rootCode=41001000000000000">技术研发</a></li>
                    <li><a target='_blank' href="/knowledge/web/form?rootCode=41002000000000000">推广应用</a></li>
                    <li><a target='_blank' href="/knowledge/web/form?rootCode=41003000000000000">工程示范</a></li>
                    <li><a target='_blank' href="/knowledge/web/form?rootCode=41004000000000000">绿色建筑</a></li>
                </ul>
                </div>
                <div class="num">8990</div>
            </li>
        </ul>
    </div>
    <div class="news">
    	<h3><a id="new1" onclick="setTab('new',1,9)"  class="hover" href="#">最新动态</a><a id="new2" onclick="setTab('new',2,9)" href="#">最新更新</a></h3>
        <ul id="con_new_1">
        	<li><a href="#">建筑给水、排水及采暖分部工程质量控制</a></li>
        	<li><a href="#">建筑给水、排水及采暖分部工程质量控制</a></li>
        	<li><a href="#">建筑给水、排水及采暖分部工程质量控制</a></li>
        	<li><a href="#">建筑给水、排水及采暖分部工程质量控制</a></li>
        	<li><a href="#">建筑给水、排水及采暖分部工程质量控制</a></li>
        	<li><a href="#">建筑给水、排水及采暖分部工程质量控制</a></li>
        	<li><a href="#">建筑给水、排水及采暖分部工程质量控制</a></li>
        	<li><a href="#">建筑给水、排水及采暖分部工程质量控制</a></li>
        </ul>
        <ul id="con_new_2" style="display:none">
        	<li>建筑给水、排水及采暖分部工程质量控制</li>
            <li>建筑给水、排水及采暖分部工程质量控制</li>
            <li>建筑给水、排水及采暖分部工程质量控制</li>
            <li>建筑给水、排水及采暖分部工程质量控制</li>
            <li>建筑给水、排水及采暖分部工程质量控制</li>
            <li>建筑给水、排水及采暖分部工程质量控制</li>
            <li>建筑给水、排水及采暖分部工程质量控制</li>
            <li>建筑给水、排水及采暖分部工程质量控制</li>
        </ul>
    </div>
  </div>
     <!--滚动--> 
	<div class="clear"></div>
 <div class="shang10"></div>
  <div class="foot">
联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号
 	<script type="text/javascript" src="${pageContext.request.contextPath}/js/google.js"></script>
</div>
</div>

</body>
</html>
