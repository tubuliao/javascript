$(document).ready(function(){
	//获取信息类型
	var hover = $("#accountTag").val();
	var beActive = $("#beActive").val();//是否激活，如果有值说明此卡号已经激活不可再用了。
	var beCardNo = $("#beCardNo").val();//此卡号不存在
	if(beActive=='1'){//已经激活不能再用
		$("#isActive").show();
	}else{
		$("#isActive").hide();
	}
	if(beCardNo=='1'){//此卡号不存在
		$("#isCardNo").show();
	}else{
		$("#isCardNo").hide();
	}
	if(hover=='2'){//我的购物车
		$("#one2").click();
	}else if(hover=='3'){//我的订单
		$('#one3').click();
	}else if(hover=='4'){//我已购买
		$('#one4').click();
	}else if(hover=='5'){//充值
		$('#one5').click();
	}
	
	var chenggong = $('#sds').val();
	if(""!=chenggong){
		alert("添加成功！");
	}

});

function gwc(id){
	var sl = $('#'+id).val();
	$('#shoppingAmount').attr("value",sl);
	$('#shoppingId').attr("value",id);
	$('#shopping').submit();
}

function payMoney(id){
	$('#orderId').attr("value",id);
	$('#toPayMoney').submit();
}

function produceOrder(id){
	$('#produceOrders').disabled=true;
	$('#formProduceOrder').submit();
	
}
function chongzhi(id){
	$("#cardNo").attr("value",id);
	$('#one5').click();
}
function chongzhitijiao(){
	var length = $("#cardId").length;
	//alert(length);
	//if(length==1)return false;
	//if(length!=32){
	//	alert("卡号长度不对，请核实后再提交！");
		//return false;
	//}else{
		$('#formPay').submit();
	//}
}