//控制父窗体iframe高度自适应js脚本
var iframeids=["main"]
var iframehide="yes"
function dyniframesize() 
{
 var dyniframe=new Array()
 for (i=0; i<iframeids.length; i++)
 {
  if (document.getElementById)
  {
   dyniframe[dyniframe.length] = window.parent.document.getElementById(iframeids[i]);
   if (dyniframe[i] && !window.opera)
   {
    dyniframe[i].style.display="block"
    if (dyniframe[i].contentDocument && dyniframe[i].contentDocument.body.offsetHeight){
     dyniframe[i].height = dyniframe[i].contentDocument.body.offsetHeight; }
    else if (dyniframe[i].Document && dyniframe[i].Document.body.scrollHeight){
     dyniframe[i].height = dyniframe[i].Document.body.scrollHeight;}
   }
  }
  if ((document.all || document.getElementById) && iframehide=="no")
  {
   var tempobj=window.parent.document.all? window.parent.document.all[iframeids[i]] : window.parent.document.getElementById(iframeids[i])
   tempobj.style.display="block"
  }
 }
}
if (window.addEventListener)
window.addEventListener("load", dyniframesize, false)
else if (window.attachEvent)
window.attachEvent("onload", dyniframesize)
else
window.onload=dyniframesize