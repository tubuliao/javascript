function click_a(divDisplay)
{
  		  if(document.getElementById(divDisplay).style.display != "block")
		    {
  			 	$("#"+divDisplay).toggle(function() {
  					$("#"+divDisplay).show('fast');
  				});
  				document.getElementById(divDisplay).style.display = "block";
		    	$("#advance_search").html("高级搜索 "+"<img src=\"images/search/xia.gif\" width=\"8\" height=\"6\" />");
 		    	var HaveselVal=$("#Havesel").html();
 		    	if($.trim(HaveselVal)==""){
			    	$("#Havesel").html("<img class=\"zuo10\" src=\"images/search/ins_03.gif\" />");
 		    	}
		    }else{
 		    	document.getElementById(divDisplay).style.display = "none";
 		    	var HaveselVal=$("#Havesel").html();
 		    	if(HaveselVal=="<img class=\"zuo10\" src=\"images/search/ins_03.gif\">"){
 			    	$("#Havesel").html("");
 		    	}
		    	$("#advance_search").html("高级搜索 "+"<img src=\"images/search/shang.gif\" width=\"8\" height=\"6\" />");
		    }
}

function hidendiv(divDisplay)
{
 		if(document.getElementById(divDisplay).style.display == "block"){
     		document.getElementById(divDisplay).style.display == "block";
     		return ;
	    }
		 document.getElementById(divDisplay).style.display = "none";
		       
}
function btn_hideDiv(divDisplay)
{
 		 document.getElementById(divDisplay).style.display = "none";
}




//-->



 
 