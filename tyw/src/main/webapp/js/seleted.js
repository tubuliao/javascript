$(window).load(function(){
 	try{
 		var	isindex=$("#isindex").val();
		if(isindex=="detail"){
 			 $("#pdssval").text('页内搜索');
		}
	}catch(error){
	}
	var newSelect = $("#selectpq");
 	newSelect.click(function(e){
  		if(this.className == "open"){
			closeSelect(this);
		}else{
			this.className = "open";
			$(this.nextSibling).slideDown("fast");
		}
		e.stopPropagation();
	});
	
	function closeSelect(obj){
		try{
			$(obj.nextSibling).slideUp("fast",function(){
				obj.className = "";
			});
		}catch(error){
		}
	}

	$(document).bind("click", function() {
	  	closeSelect(newSelect[0]);
	});

	newSelect.next().click(function(e){
		var src = e.target;
		if(src.tagName == "A"){
			var PObj = src.parentNode;
			PObj.previousSibling.innerHTML = src.innerHTML;
			/*var aList = PObj.getElementsByTagName("a");
			for(var i=0;i<aList.length;i++){
				aList[i].className = "";
			}*/
			$(src).siblings().removeClass();
			src.className = "current";
			PObj.nextSibling.value = src.getAttribute("value");
			changesearch($("#category").val());//触发select事件
		}
	});
});