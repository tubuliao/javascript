<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="${pageContext.request.contextPath}/easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/user.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/common/datepicker/WdatePicker.js" type="text/javascript" ></script>
    <script src="${pageContext.request.contextPath }/common/jquery.validate.js" type="text/javascript"></script>
    
    	 <style type="text/css">
        #fm{
			margin:0;
			padding:10px 30px;
		}
		.ftitle{
			font-size:14px;
			font-weight:bold;
			color:#666;
			padding:5px 0;
			margin-bottom:10px;
			border-bottom:1px solid #ccc;
		}
		.fitem{
			margin-bottom:5px;
		}
		.fitem label{
			display:inline-block;
			width:80px;
		}
		.qitem label{
			display:inline-block;
			width:60px;
			margin-left:90px;
		}
		.hidden{
			display:none;
		}
    </style>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<sec:authorize ifAllGranted="BACKGROUND_ONLYREAD">
		<input type="hidden" id="backGround_read" value="1"/>
	</sec:authorize>
	<sec:authorize ifNotGranted="BACKGROUND_ONLYREAD">
		<input type="hidden" id="backGround_read" value=""/>
	</sec:authorize>
	
    <div region="north" title="查 询" style="height: 140px;background:#fafafa;" id="west">
    	<div id="p" style="padding:10px;">  
			<table>
				<tr>
					<td>
						<span class="qitem" >
							<label>用户名:</label>
							<input id="username" name="username" style="width:100px"/>
							<input type='hidden' value='${user.username}' id = 'duname'/>
						</span>
					</td>
					<td>
						<span class="qitem">
							<label>显示名称:</label>
							<input id="aliasname" name="aliasname" style="width:100px"/>
						</span>
					</td>
					<td></td>
				</tr>
				<tr>
					<td>
						<span class="qitem">
							<label>会员类型:</label>
							<select id="quserType" name="quserType">
								<option  value="">全部</option>
								<option value="1">个人会员</option>
								<option value="2">企业会员</option>
								<option value="3">渠道商</option>
								<option value="4">管理员</option>
							</select>
						</span>
					</td>
					<td>
						<span class="qitem">
							<label>是否可用:</label>
							<select id="qenable" name="qenable">
								<option  value="">全部</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</span>
					</td>
					<td>
						<span class="qitem">
							<label>是否锁定:</label>
							<select id="qnonLocked" name="qnonLocked">
								<option  value="">全部</option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</span>
					</td>
				</tr>
			</table> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query()" style="margin-top:5px;margin-left:700px;">查询</a> 
		</div> 
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden">
        <div id="grid" >
        </div>
    </div>
    <div id="dlg" class="easyui-dialog" style="width:500px;height:400px;padding:10px 20px"
			closed="true" buttons="#dlg-buttons" data-options="modal:true">
		<div class="ftitle">用户信息</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				<label>用户名:</label>
				  <!-- <input name="username" id="inputUsername" class="easyui-validatebox" required="true" validType="validateUser[5,18]" /> -->
				  <input name="username" id="inputUsername" class="easyui-validatebox" required="true" validType="wokao['/validateUsername', 'inputUsername']"  />
				  <!-- <input name="username" id="inputUsername" class="easyui-validatebox" required="true" validType="validateUsername"  /> -->
				  <input type="hidden" id="flag" />
				 <span></span>
			</div>
			<div class="fitem">
				<label>显示名:</label>
				<input name="aliasname" class="easyui-validatebox" required="true"/>
			</div>
			<div class="fitem">
				<a href="javascript:void(0);" id="showDiv">修改密码</a>
			</div>
			<div id="modifyPassword">
				<div class="fitem">
					<label>登录口令:</label>
						<input name="password"  id="password" class="easyui-validatebox"  type="password" validType="length[6,30]" />
				</div>
				<div class="fitem" id="ck_pass">
					<label>确认口令:</label>
					<input name="check_password" id="check_password" class="easyui-validatebox"  type="password" validType="equalTo['#password']" invalidMessage="两次输入密码不匹配"/>
				</div>
			</div>	
			<div class="fitem">
				<label>角色:</label>
				<select id="roles" class="easyui-combobox" name="roles" required="true"  data-options="valueField:'id',multiple:true,mode:'remote',textField:'name',url:'/rolelist/all'" style="width:200px;">  
				</select>
			</div>
			<div class="fitem">
				<label>账户是否可用:</label>
				<input type="radio" name="enable" value="1"/>是 
        		<input type="radio" name="enable" value="0"/>否 			
        	</div>
			<div class="fitem">
				<label>账户是否锁定:</label>
        		<input type="radio" name="nonLocked" value="0"/>是
        	    <input type="radio" name="nonLocked" value="1"/>否 			
        	</div>
        	<div class="fitem" >
				<label>账户类型:</label>
        		<input type="radio" name="userType" value="1"  onclick="getRadio('1')"/>个人会员 
        		<input type="radio" name="userType" value="2"  onclick="getRadio('2')"/>企业会员
		 		<input type="radio" name="userType" value="3"  onclick="getRadio('3')"/>渠道商 
        		<input type="radio" name="userType" value="4"  onclick="getRadio('4')"/>管理员			
        	</div>
        	<div id="div1" class="hidden">
        		<div class="fitem">
					<label>邮箱:</label>
	        		<input type="text" name="additional.email"/>
        		</div>
        		<div class="fitem">
					<label>手机号:</label>
	        		<input type="text" name="additional.mobile"/>
        		</div>
        		<div class="fitem">
					<label>性别:</label>
	        		<input type="radio" name="additional.sex" value="1" />男 
					<input type="radio" name="additional.sex" value="0"/>女
        		</div>
        			<div class="fitem">
					<label>住址:</label>
	        		<input type="text" name="additional.location"/>
        		</div>
        			<div class="fitem">
					<label>生日:</label>
	        		<input id="str_birthday" name="str_birthday" type="text" class="Wdate" onfocus="WdatePicker({skin:'blue'})"/>
        		</div>
        	</div>
        	<div id="div2" class="hidden">
        		<div class="fitem">
					<label>企业名称:</label>
	        		<input type="text" name="firm.name"/>
        		</div>
        		<div class="fitem">
					<label>地址:</label>
	        		<input type="text" name="firm.addr"/>
        		</div>
        		<div class="fitem">
					<label>法人代表:</label>
	        		<input type="text" name="firm.linkman"/>
        		</div>
        		<div class="fitem">
					<label>联系电话:</label>
	        		<input type="text" name="firm.phone"/>
        		</div>
        		<div class="fitem">
					<label>传真:</label>
	        		<input type="text" name="firm.fax"/>
        		</div>
        		<div class="fitem">
					<label>邮编:</label>
	        		<input type="text" name="firm.zip"/>
        		</div>
        		<div class="fitem">
					<label>电子邮箱:</label>
	        		<input type="text" name="firm.email"/>
        		</div>
        	</div>
        	<div id="div3" class="hidden">
        		<div class="fitem">
					<label>渠道商名称:</label>
	        		<input type="text" name="agent.name"/>
        		</div>
        		<div class="fitem">
					<label>地址:</label>
	        		<input type="text" name="agent.addr"/>
        		</div>
        		<div class="fitem">
					<label>法人代表:</label>
	        		<input type="text" name="agent.linkman"/>
        		</div>
        		<div class="fitem">
					<label>联系电话:</label>
	        		<input type="text" name="agent.phone"/>
        		</div>
        		<div class="fitem">
					<label>传真:</label>
	        		<input type="text" name="agent.fax"/>
        		</div>
        		<div class="fitem">
					<label>邮编:</label>
	        		<input type="text" name="agent.zip"/>
        		</div>
        		<div class="fitem">
					<label>电子邮箱:</label>
	        		<input type="text" name="agent.email"/>
        		</div>
        		<div class="fitem">
					<label>默认折扣:</label>
	        		<input name="agent.discountValue" value="0.9"/>
        		</div>
        	</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel();">取消</a>
	</div>
</body>
</html>
