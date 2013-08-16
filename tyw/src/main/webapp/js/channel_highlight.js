$(document).ready(function(){
	 var kw=$("#keyword").val();
	 var i=0;
	 var highlightlen=0;
	 var isnextlen=0;
	 var scval=$("#search_pd_shuru").val();
	 var isnext=true;
		var ishigh=0;
		 
			$("#glgjc").on("click",function(){
			 if(ishigh==0){
			 		$(".highlight").css("background","#FFFFFF");
				 		ishigh=1;
			 }else{
	 		 		$(".highlight").css("background","#FF0");
			 		ishigh=0;
			 }
	 	});
	 if($.trim(scval)!=""){
		 $(".mulu_list").highlight(scval);
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
	$("#search_pd_shuru").blur(function(){
		if($("#isindex").val()!="detail"){
	 		scval=$("#search_pd_shuru").val();
			$("#issearch").val(0);
	  		if($.trim(scval)!=""){
	 			// $(".highlight").removeClass("highlight").removeAttr("style");
				
	 			 var scvals=$.trim(scval).split(" ");
	 			 for(var q=0;q<scvals.length;q++){
						if($.trim(scvals[q])!=""){
							$(".mulu_list").highlight($.trim(scvals[q]));
							$(".highlight").css({ color: "#000000"});
	 					}
				 }
	 			 
	 			var scvalsum=$.trim(scval).split("+");
			    for(var jq=0;jq<scvalsum.length;jq++){
			    	if($.trim(scvalsum[jq])!=""){
						$(".mulu_list").highlight($.trim(scvalsum[jq]));
						$(".highlight").css({ color: "#000000"});
			    	}
			    }
				 $(".glgjc_box").show();
			}else{
				$(".highlight").removeClass("highlight").removeAttr("style");
			}
			i=0;
			highlightlen=$(".highlight").length;
		}
	});
	
 	$(document).ajaxStop(helightSerach);
  	function helightSerach(){
   	  	try{
   	  		var isindex=$("#isindex").val();
	  	    var	scval=$("#search_pd_shuru").val();
	  		$("#issearch").val(0);
	  			if($.trim(scval)!=""&&isindex!="detail"){
	  			// $(".highlight").removeClass("highlight").removeAttr("style");
	  			
	  				 var scvals=$.trim(scval).split(" ");
	  				 for(var q=0;q<scvals.length;q++){
	  					if($.trim(scvals[q])!=""){
	  						$(".mulu_list").highlight($.trim(scvals[q]));
	  						$(".highlight").css({ color: "#000000"});
	  						}
	  			 }
	  				 
	  			var scvalsum=$.trim(scval).split("+");
	  		    for(var jq=0;jq<scvalsum.length;jq++){
	  		    	if($.trim(scvalsum[jq])!=""){
	  					$(".mulu_list").highlight($.trim(scvalsum[jq]));
	  					$(".highlight").css({ color: "#000000"});
	  		    	}
	  		    }
	  			 $(".glgjc_box").show();
	  		}if($.trim(scval)==""&&isindex!="detail"){
   	  			$(".highlight").removeClass("highlight").removeAttr("style");
	  		}
  	  	}catch(error){
  	  	  	}
  	}
	/*$("#hotup").click(function(event){
		//alert($('#shangyiye').attr('class'));
		
		 var highLight=$(".highlight")[i-1];
		 $(".mulu_list").find(highLight).css("background","#FFCCCC");
  		 var vh=$(window).height();//可视窗口高度
  		 var st=$(document).scrollTop()==0?0:$(document).scrollTop();//滚动条的高度
  		 if(i>0){
   			 var highLightu=$(".highlight")[i];
     		 $(".mulu_list").find(highLightu).css("background","#FF0");
  		 }if(i==-1){
  			$(document).scrollTop(10);
  		 }
  		$(".mulu_list").find(highLight).each(function(){
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
         			   $("#schidden").val("last");
         			   $('#hotup').unbind("click"); 
         			   $('#shangyiye').unbind("click");
         			   $("#isbtn").val(1);
         			   $("#issearch").val(0);
    				   $( this ).dialog( "close" );
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
	*/
	/*$("#hotnext").click(function(event){
		
  		highlightlen=$(".highlight").length;
     	 var highLight=$(".highlight")[i];
		 $(".mulu_list").find(highLight).css("background","#FFCCCC");
  		 var vh=$(window).height();//可视窗口高度
  		 var st=$(document).scrollTop()==0?0:$(document).scrollTop();//滚动条的高度
   		 if(i>0){
   			 var highLightu=$(".highlight")[i-1];
     		 $(".mulu_list").find(highLightu).css("background","#FF0");
  		 }if(i==-1){
  			 $(document).scrollTop(10);
  		 }
  	
  		$(".mulu_list").find(highLight).each(function(){
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
    	       			   $("#schidden").val("next");
    	       			   $("#isbtn").val(1);
    	       			   $("#issearch").val(0);
    	      			   $('#hotnext').unbind("click"); 
    	       			   $('#xiayiye').unbind("click");
    					$( this ).dialog( "close" );
    				},
    				"否": function() {
    					$( this ).dialog( "close" );
    				}
    			}
    		});
      		$("#dialog-confirm").parent().css("position", "fixed");
      		$("#dialog-confirm").parent().css("top",$(window).height()/2+"px");
     	}
   	});*/
 	 
 
});