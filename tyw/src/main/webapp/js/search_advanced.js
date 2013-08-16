$(window).load(function(){
		var endPage="";
 		var typeId=$("#typeId").val();
		$("input[name='type_form']").each(function(){
				if(typeId==this.value){
					$("#"+this.id).attr("checked",true);   
				}
		});
		 $("#typecontent > td >label").click(function(){
			 var radval=$('input:radio:checked').val();
			 $("#typeId").val(radval);
		});
		 
		 	var arrData_array=new Array();//存放数组的数组
			var contentForms=["address","persontype","knowledge","SubPart"];//随着业务变化而变化
			//初始化页面数据
			for(var p in contentForms){
				InitialArray();
				var contentFormTittle=$("#"+contentForms[p]+"_0").text();
				initTags(contentFormTittle,contentForms[p]);
			}
			var tagCode=0;
			var isall=1;
			function initTags(name,contentForm){
				name=name.replace("：","");
 				hidendiv("filter");
				$.post("/combotree/findTagByTagName",{name:name},function(data){
					tagCode=data.code;
					isall=0;
					getMsg(data.id,contentForm);
			    },"json");
			}
			var total_num = 0;
			var j=1;
			
			function getMsg(id,contentForm) {  
   				$.post("/combotree/tag/"+id,{name:contentForm,code:tagCode,isAll:isall},function(data){
 					if(data.length==0){
						return 0;
					}else{
 						 var content="";
						 for(var j=0;j<data.length;j++){ 
							    content= content+"<li> <input   id="+data[j].id+"_a"+" type=\"checkbox\" ondoubleclick=\"javascript:return false\" value="+data[j].name+">"+"<a href='javascript:void(0)' name="+"tag"+data[j].leaf+" id="+data[j].id+">"+data[j].name+"</a></li>"; 
					        }
						 $("#"+contentForm+"_2").html("<ul>"+content+"</ul>");
						 for(var j=0;j<data.length;j++){ 
							  ChangeCheckbox(data[j].id+"_a"); //加载插件
						 }
						 for(var kj in arrId){
							 if(arrId[kj]!=0){
								 $("#"+arrId[kj].replace("_s","_a_css")).attr("class","checked");
							 }
						 }
						
					}
 			    },"json");
 			}
		
	  
		
		//初始化数组，目前4个数组,随着业务变化而变化
		function InitialArray(){
			 var addressarrData=new Array();
			 var persontypearrData=new Array();
			 var knowledgearrData=new Array();
			 var SubPartarrData=new Array();
			 arrData_array["addressarrData"]=addressarrData;
			 arrData_array["persontypearrData"]=persontypearrData;
			 arrData_array["knowledgearrData"]=knowledgearrData;
			 arrData_array["SubPartarrData"]=SubPartarrData;
		}
		//处理导航 
		 var arrData=null;
		function upData(keyData,valData,NavigationId,isDel){ 
   				var arrDataCode=NavigationId.substring(0,NavigationId.length-2);
		 		var NavigationVal="";
 				var isHav=-1;
				var arrDatalen=null;
				for(var q in contentForms){
					if(contentForms[q]==arrDataCode){
						arrData=arrData_array[arrDataCode+"arrData"];
						arrDatalen=arrData.length;
					}
				}
				if(isDel==-1){
					arrData.splice(0,arrDatalen);//清空所有元素
					NavigationVal="";
				}if(isDel==0){
					isHav=$.inArray(valData,arrData);
					arrData.splice((isHav+1),arrDatalen);//清空后面元素
				}else{
					if(keyData!=null){
						 valData="<a href='javascript:void(0)' id="+keyData+">"+ valData +"</a>";
						 isHav=$.inArray(valData,arrData);
						 if(isHav==-1){
							arrData[arrDatalen]=valData;
						 }
					}
				}
				for(var p in arrData){
						NavigationVal=NavigationVal+"<li style='float:left'> <img src=\"images/regist/regist_07.gif\" width=\"18\" height=\"12\" />  "+ arrData[p]+"</li>";
				}
				$("#"+NavigationId).html("<ul>"+NavigationVal+"</ul>");
			}
		
		function isCheckboxed(cheId){
			$("#"+cheId).attr("checked", true);  
		}
		/**
		 * 分类操作搜索条件
		 * stauts:1 表示添加，0表示减少,2全部清除
		 * dataId:address_2,persontype_2,knowledge_2,SubPart_2
		 */
		 var addressarr=new Array();
		 var persontypearr=new Array();
		 var knowledgearr=new Array();
		 var SubPartarr=new Array();
		function StorageData(dataId,datavalue,status){
			if(status==1){
				if(dataId=="address_2"){
					var addlen=addressarr.length;
 					addressarr[addlen]=datavalue;  
				}if(dataId=="persontype_2"){
					var addlen=persontypearr.length;
 					persontypearr[addlen]=datavalue;  
				}if(dataId=="knowledge_2"){
					var addlen=knowledgearr.length;
 					knowledgearr[addlen]=datavalue;  
				}if(dataId=="SubPart_2"){
					var addlen=SubPartarr.length;
 					SubPartarr[addlen]=datavalue;  
				}
			}if(status==2){
				addressarr.splice(0,addressarr.length);
				knowledgearr.splice(0,knowledgearr.length);
				persontypearr.splice(0,persontypearr.length);
				SubPartarr.splice(0,SubPartarr.length);
			}if(status==0){
  				if(dataId==null){
					datavalue=datavalue.replace(/[ ]/g,"");
  					if($.inArray(datavalue,addressarr)>-1){
 						addressarr.splice($.inArray(datavalue,addressarr),1);//删除元素
					}if($.inArray(datavalue,knowledgearr)>-1){
						knowledgearr.splice($.inArray(datavalue,knowledgearr),1);//删除元素
					}if($.inArray(datavalue,persontypearr)>-1){
						persontypearr.splice($.inArray(datavalue,persontypearr),1);//删除元素
					}if($.inArray(datavalue,SubPartarr)>-1){
						SubPartarr.splice($.inArray(datavalue,SubPartarr),1);//删除元素
					}
				}else{
					//$.inArray($.inArray(datavalue,addressarr);,根据值获取改值在数组中的下标
					if(dataId=="address_2"){
 						addressarr.splice($.inArray(datavalue,addressarr),1);//删除元素
					}if(dataId=="persontype_2"){
						persontypearr.splice($.inArray(datavalue,persontypearr),1);//删除元素
					}if(dataId=="knowledge_2"){
						knowledgearr.splice($.inArray(datavalue,knowledgearr),1);//删除元素
					}if(dataId=="SubPart_2"){
						SubPartarr.splice($.inArray(datavalue,SubPartarr),1);//删除元素
					}
				}
			}
			var hidAddre="";
			var hidperson="";
			var hidknole="";
			var hidsupart="";
			for(var ps in addressarr){
				if(hidAddre==""){
					hidAddre="Tags:"+addressarr[ps];
				}else{
					hidAddre=hidAddre+"OR"+addressarr[ps];
				}
			}
			for(var per in persontypearr){
				if(hidperson==""){
					hidperson="Tags:"+persontypearr[per];
				}else{
					hidperson=hidperson+"OR"+persontypearr[per];
				}
			}
			for(var kno in knowledgearr){
				if(hidknole==""){
					hidknole="Tags:"+knowledgearr[kno];
				}else{
					hidknole=hidknole+"OR"+knowledgearr[kno];
				}
			}
			for(var sub in SubPartarr){
				if(hidsupart==""){
					hidsupart="Tags:"+SubPartarr[sub];
				}else{
					hidsupart=hidsupart+"OR"+SubPartarr[sub];
				}
			}
			$("#hidAddre").val(hidAddre);
			$("#hidperson").val(hidperson);
			$("#hidknole").val(hidknole);
			$("#hidsupart").val(hidsupart);
		} 
		/**
		 * 为checkbox绑定事件
		 */
 		var arr=new Array();
		var arrId=new Array();
 		var arrlen=arr.length;
		arr[0]="清除";
		arrId[0]="0"; 
		$("input:checkbox","#tabId").live("click", function(event){
			var gradeId=$(this).parent().parent().parent().attr("id");//获取超链接的父级id(Td的Id)
 			if(this.checked){
  				var showArr="";
				var id=$(this).attr("id").replace("_a","_s");
				if(arr[0]==null&&arrId[0]==null){
					arr[0]="清除";
					arrId[0]="0";
				}
  				arrlen=arr.length;
				arr[arrlen]=$(this).val();  
				arrId[arrlen]=$(this).attr("id").replace("_a","_s");
 				for(var p in arr){
					showArr=showArr+"<li name='"+gradeId+"'> <a href='javascript:void(0)' id='"+arrId[p]+"'>" +arr[p]+" "+"</a></li>";
				} 
 				StorageData(gradeId,$(this).val(),1);
    			$("#Havesel").html(showArr);
			}else{
 				var id=$(this).attr("id").replace("_a","_s");
				var showArr="";
				if(arr.length==2){
					    arr.splice(0,arr.length);//删除元素
						arrId.splice(0,arrId.length);//删除元素 
				}
 				arr.splice($.inArray($(this).val(),arr),1);//删除元素
				arrId.splice($.inArray(id,arrId),1);//删除元素
				for(var p in arr){
					showArr=showArr+"<li name='"+gradeId+"'><a href='javascript:void(0)' id='"+arrId[p]+"'>" +arr[p]+" "+"</a></li> ";
				}
				StorageData(gradeId,$(this).val(),0);
 				$("#Havesel").html(showArr);
			}
		});
		
		/**
		 * 为checkbox绑定事件
		 */
		$("span","#tabId").live("click", function(event){
			var id=$(this).attr("id").replace("_a_css","_a");
			$("#"+id).click();
		});
 
		 /**
		  * 为子内容绑定事件
		  */
		  $("#tabId  td ul>li> a").live("click mouseover mouseout", function(event){
			 	var isNext=this.name.replace("tag","");
			  if (event.type == 'mouseover' && isNext==1) {
				  var toolTip = "<div id='div1' width='100px' height='12px' style='position:absolute;border:solid  1px;background-color:#F9F9F9'>没有下一级</div>"; 
				  $(this).parent().append(toolTip);
 				  showTip(event);
 				  return;
			  }if(event.type == 'mouseout' && isNext==1){
				  closeTip();
				  return;
			  }if(event.type == 'click' && isNext==0){
 				var gradeId=$(this).parent().parent().parent().attr("id");//获取超链接的父级id(Td的Id)
				var gradeNum=gradeId.substring(gradeId.length-1,gradeId.length);
				var gradeCode=gradeId.substring(0,gradeId.length-2);
				var id=$(this).attr("id");
			 	var htmlVal=$(this).html();
 			 	isall=1;
			 	if(gradeNum==1){
 			 		var cloneVal="<a href='javascript:void(0)' id="+id+">"+ htmlVal +"</a>";
			 		upData(id,cloneVal,gradeCode+"_1",0);
			 		getMsg(id,gradeCode);
				}if(gradeNum==2){
 					upData(id,htmlVal,gradeCode+"_1",1);
					getMsg(id,gradeCode);
 				}if(gradeNum!=1&&gradeNum!=2){
   					upData(id,htmlVal,gradeCode+"_1",-1);
					initTags(htmlVal,gradeCode);
				}
			  }
		  });
			
		  
			/**
			 * 操作搜索条件
			 */
			 $("#Havesel a").live("click", function(){
 					var showArr=" ";
					var id=this.id;
 					idlen=id.replace("_s","_a");
 					if(id==0){//删除全部
    					for(var kj in arrId){
  	 						$("#"+arrId[kj].replace("_s","_a")).attr("checked", false);  
 	   	 	 				$("#"+arrId[kj].replace("_s","_a_css")).attr("class","unchecked");
 						}
 						arr.splice(0,arr.length);//删除元素
 						arrId.splice(0,arrId.length);//删除元素 
 						StorageData(null,$(this).text(),2);
 	 	 			}else{
       	 	 			$("#"+idlen.replace("_a","_a_css")).attr("class","unchecked");
 	 	 				$("#"+idlen).attr("checked", false);  
 	 	 				if(arrId.length==2){
 	 	 					arr.splice(0,arr.length);//删除元素
 	 						arrId.splice(0,arrId.length);//删除元素 
 	 	 				}
 	 	 				arr.splice($.inArray(id,arrId),1);//删除元素
						arrId.splice($.inArray(id,arrId),1);//删除元素 
 	 	 			}
					for(var p in arr){
						showArr=showArr+" <li> <a href='javascript:void(0)' id="+arrId[p]+">" +arr[p]+" "+"</a> </li>";
					}
 					StorageData(null,$(this).text(),0);
  					$("#Havesel").html(showArr);
   					if($.trim($("#Havesel").html())==""){
  						$("#Havesel").html("<img class=\"zuo10\" src=\"images/search/ins_03.gif\" />");
  					}
			});	
		
			 
 //高级搜索按钮提示
 $(".select_sort").hover( function(){
	  $(this).children("div.shang10").slideDown(290);
 },function (event) {
        $(this).children("div.shang10").slideUp(5);	
  });
				 
				 
	});
