<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>易支付介绍页面</title>
	<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$("#button").click(function(){
		 
			var licenseNumber = $("#licenseNumber").val();
			var vOid = $("#v_oid").val();
			$.post('license/savePayment',{vOid:vOid,licenseNumber:licenseNumber},function(result){
				 if(result == '1'){
					 $("#button").attr("disabled","true");
					 $("#myform").submit();
				 }else{
					 alert("添加失败");
				 }
			},'json');
		  
	});
});
</script>
<body>
<!-- form action="http://pay.beijing.com.cn/customer/gb/pay_bank.jsp" method="POST" name="E_FORM" -->
<form id="myform" action="http://pay.beijing.com.cn/prs/user_payment.checkit" method="POST" name="E_FORM">
  <input type="hidden" name="v_md5info" size="100"  value="${digestString }">
	<input type="hidden" name="v_mid" value="${v_mid }">
	<input type="hidden" id="v_oid" name="v_oid" value="${v_oid }">
	<input type="hidden" name="v_rcvname" value="${v_rcvname }">
	<input type="hidden" name="v_rcvaddr" value="${v_rcvaddr }">
	<input type="hidden" name="v_rcvtel" value="${v_rcvtel }">
	<input type="hidden" name="v_rcvpost" value="${v_rcvpost }">
	<input type="hidden" name="v_amount" value="${v_amount }">
	<input type="hidden" name="v_ymd"  value="${v_ymd }">
	<input type="hidden" name="v_orderstatus"  value="${v_orderstatus }">
	<input type="hidden" name="v_ordername"  value="${v_ordername }">
	<input type="hidden" name="v_moneytype"  value="${v_moneytype }">
	<input type="hidden" name="v_url" value="${v_url }">
	<input type="hidden" id="licenseNumber" name="licenseNumber" value="${licenseNumber }"/>
	
	
	<div style="background-color: White; margin-top: 8px; padding: 18px 0px 18px 0px;
        border: 1px solid #dddddd; width: 97%">
        <div id="Div1" class="DivContent" style="text-align: left; padding-left: 30px;">
            <p style="padding: 10px 30px 10px 310px; font-size: 12px; line-height: 30px; font-weight: normal;
                background: url(/images/epay.gif) 20px top no-repeat;">
                <span style="line-height: 30px; font-size: 14px;"><strong style="line-height: 40px;
                    padding-left: 20px; padding-bottom: 20px; font-size: 24px; color: #DC2804;">首信易支付
                    PayEase</strong>
                    <br />
                    1998年11月12日，由北京市政府与中国人民银行、信息产业部、国家内贸局等中央部委共同发起的首都电子商务工程正式启动，确定 <b>易智付科技（北京）有限公司</b>
                    为网上交易与支付中介的示范平台。首信易支付自1999年3月开始运行，是中国首家实现跨银行跨地域提供多种银行卡在线交易的网上支付服务平台，现支持全国范围23家银行及全球范围4种国际信用卡在线支付，拥有千余家大中型企事业单位、政府机关、社会团体组成的庞大客户群。
                    首信易支付作为具有国家资质认证、政府投资背景的中立第三方网上支付平台拥有雄厚的实力和卓越的信誉。
                    <br />
                    <input class="baocun" type="button" name="button" id="button" value="进入首信易支付" />
                </span>
            </p>
        </div>
    </div>

</form>
</body>
</html>
