//将checkbox替换成想到的样式，Range：范围，CheckedClass，选中的样式，UnCheckedClass，没选中的样式
//by:allen
function ChangeCheckbox (opions) {
		 var oriCheId=opions+"_css";
     	 var defaluts = { Range: "#tabId", CheckedClass: "checked", UnCheckedClass: "unchecked" };
    	 var Opions = $.extend(defaluts, opions);
         var state = $("#opions").attr("checked") ? Opions.CheckedClass : Opions.UnCheckedClass;
         var check = $("<span id='"+oriCheId+"' class=" + state + " ></span>").click(function () {
         if ($(this).hasClass(Opions.CheckedClass)) {
                  $(this).attr("class", Opions.UnCheckedClass);
                  $(this).prev().attr("checked", false);
          }else{
                  $(this).attr("class", Opions.CheckedClass);
                  $(this).prev().attr("checked", true);
          }
          });
           $("#"+opions).hide();
           $("#"+opions).after(check);
           
}
 