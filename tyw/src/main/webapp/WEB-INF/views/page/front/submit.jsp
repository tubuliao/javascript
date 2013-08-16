<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>投稿页面</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/css/person.css" rel="stylesheet" type="text/css" />

<link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/easyui-1.3.2/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery-1.8.0.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui-1.3.2/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>

<SCRIPT language=javascript src="homesearch.js"></SCRIPT>
<!--[if lt IE 7]>
        <script type="text/javascript" src="/unitpngfix.js"></script>
<![endif]-->
<script type="text/javascript" >
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


function deleteUpload(uploadId){
	$.messager.confirm('提示','确定要删除?',function(r){
			if (r){
				$.post('/deleteUpload/'+uploadId,{uploadId:uploadId},function(result){
					if (result.success){
						location.href='/personCentersubmit';
					} else {
						$.messager.show({	// 显示错误信息
							title: '错误',
							msg: result.msg
						});
					}
				},'json');
			}
		});
}


</script>
</head>

<body>


  <div class="search_up"></div>

    <div class="search_content_box">
  <div class="search_content">
<div class="person_nav2"> <a href="data.html">我的下载</a> <a href="upload.html">我的上传</a><a href="/personCentersubmit">我的投稿</a>
    </ul>
</div>
<div class="person_left left">
    	<div class="order_title">我的投稿<span class="order_title_en"> / my submission</span> </div>
    	<form id="form2" name="form2" method="post" action="">
        <div class="data_title">
          <div class="data_title_left left"><img src="/images/personal/data_btn_03.png" width="16" height="15" /> <a href="#">全选</a></div>
            <div class="data_title_right right"><a href="#">未审核</a><a href="#"> 已审核 </a><a href="#">审核未通过</a></div>
        </div>
        


        	  
			<c:forEach items="${list}" var="l">
			<ul class="data_list">
        	<li>
			<div class="data_xuan left"><input type="checkbox" name="checkbox" id="checkbox" /></div>
              <div class="data_list_word left">
           		<div class="data_list_title"><a href="#">${l.title}</a></div>
                <div class="data_list_info">投稿时间：${l.formatCreateDate}     文件类型：${l.uploadType}</div>
              </div>
              <div class="data_btn_position">
              <div class="data_btn_box right">
              <div class="data_btn">
              <img src="/images/personal/data_btn_15.png" width="10" height="13" /><a href="${l.url}">下载</a> <img src="/images/personal/data_btn_18.png" width="10" height="12" />     <a href="#">移动</a> <img src="/images/personal/data_btn_20.png" width="13" height="12" />     <a href="javascript:void(0);" onclick="deleteUpload('${l.id}');">删除</a> 
             <img src="/images/personal/data_btn_23.png" width="11" height="10" /> <a href="#">评论(10)</a> <img src="/images/personal/data_btn_25.png" width="13" height="10" />      <a href="#">喜欢(10)</a> <img src="/images/personal/data_btn_13.png" width="12" height="12" />     <a href="#">收藏(10)</a>    </div>
             </div>
             </div>
			  </li>
            </ul>
			</c:forEach>

         <!--
        <div class="scott"><a href="#?page=2"> 首页</a><a href="#?page=2">上一页</a><span class="disabled">&lt; </span><span class="current">1</span><a href="#?page=2">2</a><a href="#?page=3">3</a><a href="#?page=4">4</a><a href="#?page=5">5</a><a href="#?page=6">6</a><a href="#?page=7">7</a>.<a href="#?page=199">199</a><a href="#?page=200">200</a><a href="#?page=2">下一页 </a></div>-->  
    	</form>
    </div>
    <div class="person_right right">
    	<div class="person_info_s">
        
  <div class="person_info_jifen">
            	
                	<ul>
                    	<li>
                        	<div class="person_info_jifen_shuzi">
                            	200                            </div>
                            <div class="person_info_jifen_wenzi">
                              <a href="#">优惠券</a> </div>
                        </li>
                        <li class="xuxian">
                        	<div class="person_info_jifen_shuzi">
                            	200                            </div>
                            <div class="person_info_jifen_wenzi">
                              <a href="#">优惠券</a> </div>
                        </li>
                    </ul>
          </div>
        	<div class="person_info_caozuo">
            	<span><a href="#">修改密码</a>  |  <a href="#">充值</a>  | <a href="#">个人资料</a></span><span class="zuo10 hui">完整度</span> <span class="anhong zihao14">40%</span></div>
                <div class="person_info_touxiang">
          <a href="#"><img src="/images/personal/person_03.gif" width="89" height="89" /></a> </div>
        </div>
        <div class="info_youhui">
       	  <div class="info_youhui_title">投稿请阅读并遵守下面的规则</div>
            <ul>
              <li>1.图纸、施组等所有工程资料均可投稿<br />
              </li>
              <li>2.大于20M的资料,直接QQ联系栏目编辑<br />
              </li>
                <li>3.不接受有版权或保密工程资料<br />
                </li>
                <li>4.投稿可获得筑龙币、信誉分及纪念品<br />
                </li>
                <li>5.个性化的获取自己需要的信息</li>
          </ul>
            
            <a href="/personCentersubmitType"><img class="shang10" src="/images/personal/tougao_07.gif" width="225" height="46" /></a></div>
        <div class="new_youhui">
        	<div class="new_youhui_title">最新优惠<span class="new_youhui_title_en"> / latest preferential</span> </div>
            <ul>
            	<li><a href="#"><img src="/images/personal/person_11.gif" width="53" height="64" /></a></li>
              <li><a href="#"><img src="/images/personal/person_11.gif" width="53" height="64" /></a></li>
                <li><a href="#"><img src="/images/personal/person_11.gif" width="53" height="64" /></a></li>
            </ul>
        </div>
    </div>
    <div class="clear"></div>
    </div>
  </div>
  <div class="search_down"></div>
    <div class="clear"></div>
    <div class="foot">
联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20090191号</div>
</div>
<div class="guding">
<div class="guding2">

<div class="btn_fd">
	<a href="#"></a>
	<a href="#top"><img title="返回顶部" class="shang20" src="/images/btnfd_13.gif" /></a>
</div>
</div></div>

</body>
</html>
