function mainmenu(){
$(" #nav ul ").css({display: "none"}); // Opera Fix
$(" #nav li").hover(function(){
		$(this).find('ul:first').css({visibility: "visible",display: "none"}).show(400);
		},function(){
		$(this).find('ul:first').css({visibility: "hidden"});
		});
}

 
 
 $(document).ready(function(){					
	mainmenu();
});


/*
    �������: Scroll
    Scroll(obj, h, s)
    ����˵��:
        obj,[object]  idֵ�����.     ����
          h,[height]  չ����ĸ߶�.   ��ѡ(Ĭ��Ϊ200px)
          s,[speed]   չ���ٶ�,ֵԽСչ���ٶ�Խ��. ��ѡ(Ĭ��Ϊ1.2){����ȡֵΪ1.1��2.0֮��[����:1.17]}.
    �����ֵ:
        true    չ��(����ĸ߶ȵ���չ����ĸ߶�)
        false   �ر�(����ĸ߶ȵ���ԭʼ�߶�)
*/
function Scroll(obj, h, s){
    if(obj == undefined){return false;}
    var h = h || 200;
    var s = s || 1.2;
    var obj = typeof(obj)=="string"?document.getElementById(obj):obj;
    var status = obj.getAttribute("status")==null;
    var oh = parseInt(obj.offsetHeight);
    obj.style.height = oh;
    obj.style.display = "block";
	obj.style.overflow = "hidden";
	  
    if(obj.getAttribute("oldHeight") == null){
        obj.setAttribute("oldHeight", oh);
    }else{
        var oldH = Math.ceil(obj.getAttribute("oldHeight"));
    }
    
    var reSet = function(){
        if(status){
            if(oh < h){
                oh = Math.ceil(h-(h-oh)/s);
                obj.style.height = oh+"px";
            }else{
                obj.setAttribute("status",false);
                window.clearInterval(IntervalId);
            }
        }else{
            obj.style.height = oldH+"px";
            obj.removeAttribute("status");
            window.clearInterval(IntervalId);
        }
    }
    var IntervalId = window.setInterval(reSet,10);
    if(status){
    	$("#advance_search").html("高级搜索 "+"<img src=\"images/search/shang.gif\" width=\"8\" height=\"6\" />");
    }else{
    	$("#advance_search").html("高级搜索 "+"<img src=\"images/search/xia.gif\" width=\"8\" height=\"6\" />");
    }
  
	return status;
}

function paremterhide(obj, h, s){
    if(obj == undefined){return false;}
    var h = h || 200;
    var s = s || 1.2;
    var obj = typeof(obj)=="string"?document.getElementById(obj):obj;
    var status = obj.getAttribute("status")==null;
    var oh = parseInt(obj.offsetHeight);
    obj.style.height = oh;
    obj.style.display = "block";
	 obj.style.overflow = "hidden";
	  
    if(obj.getAttribute("oldHeight") == null){
        obj.setAttribute("oldHeight", oh);
    }else{
        var oldH = Math.ceil(obj.getAttribute("oldHeight"));
    }
    
    var reSet = function(){
            obj.style.height = oldH+"px";
            obj.removeAttribute("status");
            window.clearInterval(IntervalId);
    }
    var IntervalId = window.setInterval(reSet,10);
   
    	$("#advance_search").html("高级搜索 "+"<img src=\"images/search/xia.gif\" width=\"8\" height=\"6\" />");
	return status;
}
window.onload= function(){
    //document.getElementById('detail').onclick = function(){
    //    Scroll('detail', 300, 1.3);
    //}
    //document.getElementById('text').onclick = function(){
    //    Scroll('text');
    //}
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


/*��һ����ʽ �ڶ�����ʽ ����ʾ��ʽ*/
function setTab(name,cursel,n){
for(i=1;i<=n;i++){
var menu=document.getElementById(name+i);
var con=document.getElementById("con_"+name+"_"+i);
menu.className=i==cursel?"hover":"";
con.style.display=i==cursel?"block":"none";
}
}
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->



 
 