function tywAjaxComplete(XMLHttpRequest, textStatus){
	$('#baseAjax').dialog("close");
	$("#baseAjax").parent().find(".ui-dialog-titlebar").show();
	$("#baseAjax").parent().addClass("ui-widget");
	$("#baseAjax").parent().addClass("ui-widget-content"); 
}
function tywBeforeSend(XMLHttpRequest, textStatus){
	$("#baseAjax").parent().find(".ui-dialog-titlebar").hide();
	$("#baseAjax").parent().removeClass("ui-widget");
	$("#baseAjax").removeClass();
	$("#baseAjax").parent().removeClass("ui-widget-content"); 
	$('#baseAjax').dialog("open");
	$("#baseAjax").parent().css("position", "fixed");
	$("#baseAjax").parent().css("top",$(window).height()/2+"px");
}
$(document).ready(function() {   
	var boardDiv ='<div id="baseAjax" ><img src="/images/wait.gif"/></div>';
	$(document.body).append(boardDiv); 
	$(document).ajaxStart(tywBeforeSend);
	$(document).ajaxStop(tywAjaxComplete); 
	$('#baseAjax').dialog({
		autoOpen : false,
		width : 50,
		height: 50,
		center:true,
		title: false,
		modal : true
	});
});