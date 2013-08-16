var returnurl = '';
var messagebox_timer; 
$.fn.messagebox = function (message, url, type, delay) {
	clearTimeout(messagebox_timer);
	$("#msgprint").remove(); 
	var m_body = $(this), timer = false; delay = (typeof delay == "undefined" ? 5000 : delay);
	returnurl = url;
	var box_style = 'position:absolute;display:none;z-index:1000;padding:10px 30px 10px 40px;'; 
	switch (type) { 
		case 1: box_style += 'border:1px solid Green;color:#090;background:url(icons/ok.png) 10px 10px no-repeat #F1FEF2; font-size:14px; line-height:30px; width:400px; text-align:center'; break; 
		case 0: box_style += 'border:1px solid Red;color:#EE1010;background:url(icons/error.png) 10px 10px no-repeat #FDF8E8;'; break; 
		default: box_style += 'border:1px solid Orange;color:Orange;background:url(icons/warning.png) 10px 10px no-repeat #FEFDE9;'; break 
		} 
	var a = "font-size:14px; line-height:30px; width:400px; height:300px";
		var str = "<div id=\"msgprint\" style=\"" + box_style + "\">" + message + "</div>";
		m_body.append(str); 
		var dom_obj = document.getElementById("msgprint"); 
		//var ext_width = $("#msgprint").width("400px"); 
		dom_obj.style.top = (document.documentElement.scrollTop + (document.documentElement.clientHeight - dom_obj.offsetHeight - $("#msgprint").height()) / 2) + "px"; 
		dom_obj.style.left = (document.documentElement.scrollLeft + (document.documentElement.clientWidth - dom_obj.offsetWidth - $("#msgprint").width()) / 2) + "px"; 
		$("#msgprint").fadeIn(1000, function () { messagebox_timer = setTimeout(messagebox_out, delay) }) }; 
		function messagebox_out() { 
			if (returnurl == undefined || returnurl == '') { $("#msgprint").fadeOut(1000); }
			if (returnurl == "back") { this.history.back(-1) }
			else if (returnurl != "" && returnurl != undefined) { 
				//this.location.href = returnurl; 
				
				
				//var comment = document.getElementsByTagName('a')[0];
				var comment = document.getElementById('userLogin');
				if (document.all) {// For IE 
					comment.click();
				} else if (document.createEvent) {//FOR DOM2
					var ev = document.createEvent('MouseEvents');
					ev.initEvent('click', false, true);
					comment.dispatchEvent(ev);
				}//上面的代码在IE6, IE7 和 FireFox 3 下通过测试。
			} 
}