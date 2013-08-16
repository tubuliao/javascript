<%@ page contentType="text/html;charset=utf-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>发票信息</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link href="/css/project.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/common/jquery.validate.js"></script>
<script type="text/javascript">
	$(function(){
		jQuery.validator.addMethod("isMobile", function(value, element) {       
		     var length = value.length;   
		    var mobile = /^(\d{1,12})$/;   
		   return this.optional(element) || (mobile.test(value));       
		}, "<font color='red'>请正确填写电话号码(区号+电话号)或者手机号</font>");    
		jQuery.validator.addMethod("isZipCode", function(value, element) {       
		     var tel = /^[0-9]{6}$/;       
		    return this.optional(element) || (tel.test(value));       
		 }, "<font color='red'>请正确填写邮政编码</font>");    
		 
		
		var validator = $("#subForm").validate({
			onfocusout: false,
			onsubmit: false ,
	        rules: {
	        		receiptTitle: {
					    required: true,
					    maxlength:50
				    },
				    receiptPerson: {
					    required: true,
					    maxlength:50
				    },
				    postCode: {
					    required: true ,
					    isZipCode:true
				    },
				    receiptPhone: {
					    required: true ,
					    isMobile: true
				    },
				    receiptAddress: {
					    required: true ,
					    maxlength:255
				    }  
	 			 },
	        messages: { 
	        	receiptTitle: {
				    required: "<font color='red'>必填字段</font>" ,
				    maxlength:jQuery.format("<font color='red'>发票抬头不能大于{0}个字 符</font>")
			    },
			    receiptPerson: {
			    	required: "<font color='red'>必填字段</font>" ,
				    maxlength:jQuery.format("<font color='red'>联系人姓名不能大于{0}个字 符</font>")
			    },
			    postCode: {
			    	required: "<font color='red'>必填字段</font>"
			    },
			    receiptPhone: {
			    	required: "<font color='red'>必填字段</font>" 
			    },
			    receiptAddress: {
			    	required: "<font color='red'>必填字段</font>" ,
				    maxlength:jQuery.format("<font color='red'>邮寄地址不能大于{0}个字 符</font>")
			    }  
	  			}
	    });
		 
		$("#button").click(function(){
			var status = $("#rstatus").val();
			if(status == 1){
				alert("此发票已经开具");
				return;
			}
			if(validator.form()){
				var receiptTitle = $("#receiptTitle").val();
				var receiptPerson = $("#receiptPerson").val();
				var postCode = $("#postCode").val();
				var receiptPhone = $("#receiptPhone").val();
				var receiptAddress = $("#receiptAddress").val();
				var licenseNumber = $("#licenseNumber").val();
				var receiptId = $("#receiptId").val();
				var vOid = $("#v_oid").val();
				$.post('license/saveReceipt',{vOid:vOid,id:receiptId,licenseNumber:licenseNumber,receiptTitle:receiptTitle,receiptPerson:receiptPerson,postCode:postCode,receiptPhone:receiptPhone,receiptAddress:receiptAddress},function(result){
					if(result == '1'){
						 $("#button").attr("disabled","true");
						 $("#subForm").submit();
					 }else{
						 alert("添加失败");
					 }
				},'json');
			}  
		});
	});
</script>
<body>
<!-- form action="http://pay.beijing.com.cn/customer/gb/pay_bank.jsp" method="POST" name="E_FORM" -->
<div class="content">
<div class="pro_head">
<div class="logo_box left">
        	<img class="left" src="../images/search_03.png" />
        <div class="left">
            	<h3>项目部计划</h3>
                <p>专业的建筑信息服务平台</p>
        </div>
            <div class="clear"></div>
    </div>
      <div class="right"><img src="../images/project/lou_03.gif" width="303" height="59" /></div>
      <div class="clear"></div>
      </div>
<div class="neirong">
<div class="pay_lishi"></div>

<form id="subForm" action="http://pay.beijing.com.cn/prs/user_payment.checkit" method="POST" name="E_FORM">
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
	<input type="hidden" id="licenseNumber" name="licenseNumber" value="${licenseNumber }">
	<input type="hidden" id="receiptId" name="receiptId" value="${receipt.id }"/>
	<input type="hidden" id="rstatus" name="rstatus" value="${receipt.status }"/>
  <table class="fapiao_biao" width="90%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#EAEAEA">
    <tr>
      <td height="50" colspan="2" background="../images/bg/bg2.gif" bgcolor="#FFFFFF"><span class="pay_lishi">填写发票信息</span></td>
    </tr>
    <tr>
      <td width="14%" height="50" align="right" bgcolor="#FFFFFF"><strong>发票抬头</strong></td>
      <td width="86%" bgcolor="#FFFFFF"><input id="receiptTitle" name="receiptTitle" type="text" class="input_shouji fapiao_input" value="${receipt.receiptTitle }"/></td>
    </tr>
    <tr>
      <td height="50" align="right" bgcolor="#FFFFFF"><strong>收件人姓名</strong></td>
      <td bgcolor="#FFFFFF"><input id="receiptPerson" name="receiptPerson"  type="text" class="input_shouji fapiao_input" value="${receipt.receiptPerson }"/></td>
    </tr>
    <tr>
      <td height="50" align="right" bgcolor="#FFFFFF"><strong>收件人电话</strong></td>
      <td bgcolor="#FFFFFF"><input  id="receiptPhone" name="receiptPhone" type="text" class="input_shouji fapiao_input" value="${receipt.receiptPhone }"/></td>
    </tr>
    <tr>
      <td height="50" align="right" bgcolor="#FFFFFF"><strong>收件人邮编</strong></td>
      <td bgcolor="#FFFFFF"><input id="postCode" name="postCode" type="text" class="input_shouji fapiao_input" value="${receipt.postCode }" /></td>
    </tr>
    <tr>
      <td height="50" align="right" bgcolor="#FFFFFF"><strong>收件人地址</strong></td>
      <td bgcolor="#FFFFFF">
      	<input id="receiptAddress" name="receiptAddress" type="text" class="input_shouji fapiao_input" value="${receipt.receiptAddress }" />
      </td>
    </tr>
    <tr>
      <td height="50" colspan="2" align="center" bgcolor="#FFFFFF">
      	<input class="baocun" type="button" name="button" id="button" value="马上支付" />
      </td>
    </tr>
<tr></tr>
</table>
  <div class="pay_lishi"></div>
  </form>
  <div class="clear"></div>
</div>    
<!-- Footer -->
	<div id="footer">
	  联系我们 | 招贤纳士 | 移动客户端 | 风格模板 | 官方博客  天佑网版权所有　©1997-2013　粤ICP备20</div>
	<!-- END Footer -->  
</div>


<!-- form action="http://pay.beijing.com.cn/prs/user_payment.checkit" method="POST" name="E_FORM">
  <input type="hidden" name="v_md5info" size="100"  value="${digestString }">
	<input type="hidden" name="v_mid" value="${v_mid }">
	<input type="hidden" name="v_oid" value="${v_oid }">
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
                    <input class="baocun" type="submit" name="button" id="button" value="进入首信易支付" />
                </span>
            </p>
        </div>
    </div>
</form-->
</body>
</html>
