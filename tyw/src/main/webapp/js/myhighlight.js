$(window).load(function(){
	 var kw=$("#keyword").val();
	 var i=0;
 	 var highlightlen=0;
	 var isnextlen=0;
	 var scval=$("#search_pd_shuru").val();
	 var isnext=true;
 		if($.trim(scval)==""&&$("#issearch").val()!=1&&$('#shangyiye').attr('class')!="shangyiye2"){
			kw=$.trim(kw)=="输入要查找的内容"?"":$.trim(kw);
			var ishigh=0;
			$.post("/analyzer/keywords",{keyword:kw},function(data){
				 $.each(data,function(i){
					 try
						{
					   $(".wenzi").highlight(data[i]);
						$(".highlight").css({ color: "#000000"});
						}catch(err){
							
						}
					 });
			});
			$("#glgjc").live("click",function(){
				 if(ishigh==0){
 					 $.each($(".highlight"),function(i,n){
						 $(this).replaceWith($(this).html());
					 })
			 			//$(".highlight").removeClass("highlight").removeAttr("style");
				 	//	$(".highlight").css("background","#FFFFFF");
				 		ishigh=1;
				 }else{
					 $.each($(".highlight"),function(i,n){
						 $(this).replaceWith($(this).html());
					 })
 					 showheight();
					// $("#search_pd_shuru").focus().blur();
 		 		 		//$(".highlight").css("background","#FF0");
				 		ishigh=0;
				 }
		 	});
		}
	 if($.trim(scval)!=""){
		 $(".wenzi").highlight(scval);
	 }
	 isnextlen=$(".highlight").length;
 
if($.trim(scval)!=""&&isnextlen==0&&$("#isbtn").val()==1&&$("#issearch").val()!=1&&$('#shangyiye').attr('class')!="shangyiye2"&&$('#xiayiye').attr('class')!="xiayiye2"){
	kw=$.trim(kw)=="输入要查找的内容"?"":$.trim(kw);
	$("#dialog-confirm").html("本页未找到，是否继续?");
	$("#dialog-confirm" ).dialog({
		resizable: false,
		height:140,
		modal: true,
		dialogClass : "my-dialog",
		show:400,
  		buttons: {
			"是": function() {
			     var isxiashang=$("#schidden").val();
 				 if(isxiashang=="last"){
 				   $('#shangyiye').click();
 				 }else{
 				    $('#xiayiye').click();
 			 	 }
 				  $("#isbtn").val(1);
				  $('#hotup').unbind("click"); 
				  $('#hotnext').unbind("click"); 
				  $( this ).dialog( "close" );
			},
			"否": function() {
				$( this ).dialog( "close" );
			}
		}
	});
   }
$("#pdsousuo").live("click",function(){
 	showheight();
});
$("#search_pd_shuru").live("keydown",function(e){
	if($("#search_pd_shuru").val()!=""){
		var keynum = window.event ? e.keyCode : e.which;
		if(keynum==13)
			showheight();
	}
});
 
function showheight(){
	scval=$("#search_pd_shuru").val();
		$("#issearch").val(0);
		if($.trim(scval)!=""){
			 $(".highlight").removeClass("highlight").removeAttr("style");
			 if(scval.indexOf(" ")>=0){
  			 var scvals=$.trim(scval).split(" ");
 			 for(var q=0;q<scvals.length;q++){
					if($.trim(scvals[q])!=""){
 						$(".wenzi").highlight($.trim(scvals[q]));
						}
			 }
			 }
			if(scval.indexOf("+")>=0){
 			var scvalsum=$.trim(scval).split("+");
		    for(var jq=0;jq<scvalsum.length;jq++){
		    	if($.trim(scvalsum[jq])!=""){
 					$(".wenzi").highlight($.trim(scvalsum[jq]));
		    	}
		    }
			}else{
 				  $(".wenzi").highlight(scval);
			}
		 	 $(".glgjc_box").show();
	}else{
			$(".highlight").removeClass("highlight").removeAttr("style");
	}
	i=0;
	highlightlen=$(".highlight").length;
}
	
	
	$("#hotup").live("click",function(event){
		//alert($('#shangyiye').attr('class'));
		
		 var highLight=$(".highlight")[i-1];
		 $(".wenzi").find(highLight).css("background","#FFCCCC");
  		 var vh=$(window).height();//可视窗口高度
  		 var st=$(document).scrollTop()==0?0:$(document).scrollTop();//滚动条的高度
  		 if(i>0){
   			 var highLightu=$(".highlight")[i];
     		 $(".wenzi").find(highLightu).css("background","#FF0");
  		 }if(i==-1){
  			$(document).scrollTop(10);
  		 }
  		$(".wenzi").find(highLight).each(function(){
 			 var eh=$(this).position().top;//元素距离顶部的高度
    		if(eh<st){
   				$(document).scrollTop(eh);
   			}if(eh>(st+vh)){  
   				$(document).scrollTop(eh-vh);
 			}
  		});
  		i>=0?i--:0;
   		 if(i<=0){
   			if($('#shangyiye').attr('class')=="shangyiye2")return;
   			$("#dialog-confirm" ).dialog({
     			height:140,
    		    dialogClass: "my-dialog",
    			modal: true,
    			buttons: {
    				"是": function() {
    					hotup();
    				   $(".knowledge_context").ajaxStop(function(){
     					   $("#schidden").val("last");
             			   $('#hotup').unbind("click"); 
             			   $('#shangyiye').unbind("click");
             			   $("#isbtn").val(1);
             			   $("#issearch").val(0);
             			  showheight();
						 //  $("#search_pd_shuru").focus().blur();
					 	});
 						$(this).dialog( "close" );
     				},
    				"否": function() {
    					$( this ).dialog( "close" );
    				}
    			}
    		});
   			$("#dialog-confirm").parent().css("position", "fixed");
   			$("#dialog-confirm").parent().css("top",$(window).height()/2+"px");
      			//alert("本页搜索完毕");
     	}
   	});
	
	$("#hotnext").live("click",function(event){
   		highlightlen=$(".highlight").length;
     	 var highLight=$(".highlight")[i];
		 $(".wenzi").find(highLight).css("background","#FFCCCC");
  		 var vh=$(window).height();//可视窗口高度
  		 var st=$(document).scrollTop()==0?0:$(document).scrollTop();//滚动条的高度
   		 if(i>0){
   			 var highLightu=$(".highlight")[i-1];
     		 $(".wenzi").find(highLightu).css("background","#FF0");
  		 }if(i==-1){
  			 $(document).scrollTop(10);
  		 }
  
  		$(".wenzi").find(highLight).each(function(){
 			 var eh=$(this).position().top+400;//元素距离顶部的高度
    		if(eh<st){
  				$(document).scrollTop(-eh);
   			}if(eh>(st+vh)){  
  				$(document).scrollTop(eh-vh);
 			}
   		});
   		i<=highlightlen-1?i++:highlightlen;
   	 
      	 if(i==highlightlen){
     		if($('#xiayiye').attr('class')=="xiayiye2")return;
      		$("#dialog-confirm" ).dialog({
    			resizable: false,
     			height:140,
    			modal: true,
    			buttons: {
    				"是": function() {
    					   hotnext();
    						 $(".knowledge_context").ajaxStop(function(){
    							  $("#schidden").val("next");
    							  $("#isbtn").val(1);
    							  $("#issearch").val(0);
    							  $('#hotnext').unbind("click"); 
    							  $('#xiayiye').unbind("click");
    							  showheight();
    							//  $("#search_pd_shuru").focus().blur();
    						});
  								  $(this).dialog( "close" );
      				},
    				"否": function() {
    					$( this ).dialog( "close" );
    				}
    			}
    		});
      		$("#dialog-confirm").parent().css("position", "fixed");
      		$("#dialog-confirm").parent().css("top",$(window).height()/2+"px");
     	}
   	});
 	 
 
});