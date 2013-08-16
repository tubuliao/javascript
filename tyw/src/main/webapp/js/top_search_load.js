var cityVal=""; 
 var searchCityVal="";
 $(window).load(function(){
	//初始化搜索条件
	 var tagSearchArrayval=getCookie("tagSearchArray");
		if(tagSearchArrayval!=null&&tagSearchArrayval!="undefined"){
	 		$("#searchCondition").html(tagSearchArrayval);
	 		var taglenVal="";
	 	 	for(var taglen=0;taglen<$("#searchCondition a").length;taglen++){
	 	 		if(taglenVal==""&&$("#searchCondition a:eq("+taglen+")").text()!=""){
	 	 	 		taglenVal=$("#searchCondition a:eq("+taglen+")").text();
	 	 	 		continue;
	 	 	 	 }if(taglenVal!=""&&$("#searchCondition a:eq("+taglen+")").text()!=""){
		  	 	 		taglenVal=taglenVal+"OR"+$("#searchCondition a:eq("+taglen+")").text();
	 	 	 	 }
			}
		  	$("#tag_name").val(taglenVal);
		}
		//初始化高级搜索框
		$("#tagSearch").dialog({
			autoOpen : false,
			width : 900,
			width : 800,
	 		modal : true
		});
		 $("#konwledgeType7").click(function(){
	  				$("#tagSearch").dialog('open');
		 });
	   				
	 
		//初始化地区查询
	 	var provinceVal = getCookie("provinceVal");
	     if(provinceVal!=null){
			$("#province").html(provinceVal); 
		}else{
	 		var citys=null;
			if($.trim(ILData[2])==""){
 	    			$("head").append("<script type=\"text/javascript\" src=\"${pageContext.request.contextPath}/js/geoip.js\"><\/script>");
	  			$.post("/index/getpoint",{x:geoip_latitude(),y:geoip_longitude()},function(data){
 					cityVal=$.trim(data.long_name).length==2?$.trim(data.long_name)+"市":$.trim(data.long_name);
	   	 			citys=cityVal==""?'全国':cityVal;
		 		//	getProvince(citys);
		   			searchCityVal=citys;
					setCookie("provinceVal", citys,1);
					$("#province").html(citys); 
				});
			}else{
	   			searchCityVal=ILData[2];
				setCookie("provinceVal", ILData[2],1);
				$("#province").html(ILData[2]); 
			}
		}
	 
	  	//初始化省份DIV
			$("#provinceArea").dialog({
				autoOpen : false,
				width : 600,
				center:true,
				dialogClass : "my-dialog",
				modal : true
	 		});
			$("#provinceArea").parent().css("position", "fixed");
	 		$("#provinceArea").parent().css("top",$(window).height()/2+"px");
			$("#province").click(function() {
				try{
					var provinceVal = getCookie("provinceVal");
	 				if(provinceVal!=null){
						getProvince(provinceVal);
					}else{
						getProvince(searchCityVal);
					}
	 				$("#provinceArea").dialog('open');
				}catch(error){
				}
	 		});
		
			var kw=$("#keyword").val();
			if(kw==""){
				$("#keyword").val("输入要查找的内容");
			}
			
			 $("#search_pd_shuru").blur(function(){
	 				$("#title").val($("#search_pd_shuru").val());
	 		 });
			//频道搜索
			 $("#pdsousuo").click(function(){
					 try
					{
		 				sub();
 					}catch(err){
				  		 
					};
			});
   });