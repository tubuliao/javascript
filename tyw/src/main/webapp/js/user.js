var listUrl='/userlist/data';
var url='/user/save';
var grid;

function getRadio(id){
	var d1=$("#div1"),d2=$("#div2"),d3=$("#div3");
	switch(id){
		case '1':
		d1.show();d2.hide();d3.hide();
		break;
		case '2':
		d2.show();d1.hide();d3.hide();
		break;
		case '3':
		d3.show();d1.hide();d2.hide();
		break;
		case '4':
		d1.hide();d2.hide();d3.hide();
		break;
	}
}

function query(){
	var username=$("#username").val();
	var aliasname=$("#aliasname").val();
	var quserType=$("#quserType").val();
	var qenable=$("#qenable").val();
	var qnonLocked=$("#qnonLocked").val();
	grid.datagrid('clearSelections');
	grid.datagrid({ url: listUrl, queryParams: { username: username,aliasname:aliasname,userType:quserType,enable:qenable,nonLocked:qnonLocked} });
}

function remove(){
	var row = $('#grid').datagrid('getSelected');
	var dunames = $('#duname').val();
	if(row&&row.username==dunames){
		$.messager.alert('您不能删除当前正在使用的用户！','当前用户名为 '+dunames+'。');
		return '';
	}
	if (row){
		$.messager.confirm('提示','您确定要删除该用户?',function(r){
			if (r){
				$.post('/user/delete/'+row.id,{id:row.id},function(result){
					if (result.success){
						$('#grid').datagrid('reload');	// 重新加载数据
					} else {
						$.messager.show({	// 显示错误信息
							title: '错误',
							msg: result.msg
						});
					}
				},'json');
			}
		});
	}else {
        Msgshow('请先选择要删除的记录。');
    }
}
//显示新增标签对话框
function add(){
	url='/user/save';
	$('#fm').form('clear');
	$("input[name='enable']:first").attr('checked', 'checked'); 
	$("input[name='nonLocked']:last").attr('checked', 'checked');
	$("input[name='userType']:last").attr('checked', 'checked');
	getRadio('4');//设置默选中
	$('#dlg').dialog('open').dialog('setTitle','添加用户');
	$("#roles").combobox({editable:false });
	$('#showDiv').hide();
	$('#modifyPassword').show();

	$('#password').attr('required', 'true');
	$('input[name="userType"]').attr('disabled', false);
	$('input[name="username"]').attr('readonly', false);
	$("#flag").val("add");
}
function save(){
	$('#fm').form('submit',{
		url: url,
		onSubmit: function(){
//		   var data0 ; 
		   
		  /* $.ajax({  
                type: "post",  
                url:"${pageContext.request.contextPath}/user/validateUser",  
                data:"userName="+$("#username").val(),
                async:false,  
                cache: false,
                dataType:"json",
                success: function(data){ 
                	data0 = eval('('+data+')');
                	alert(data0);
                }
            });
            if (!data0.success) {   
               $.fn.validatebox.defaults.rules.username.message = '用户名已存在！';   
               return false;   
            } else {   
               return true;   
            }*/
			return $(this).form('validate');
		},
		success: function(result){
			var result = eval('('+result+')');
			if (result.success){
				$('#dlg').dialog('close');		
				$('#grid').datagrid('reload');	
			} else {
				$.messager.show({
					title: '错误',
					msg: result.msg
				});
			}
		}
	});
	$('input[name="userType"]').attr('disabled', false);
	
}


//分页方法
function pageUrl(pageSize,pageNo){
	return listUrl+"?page.page="+pageNo+"&page.size="+pageSize;
}


$(function () {
	
	backGround = $('#backGround_read').val();
	bar={};
	bar1=[{
        text: '新增',
        iconCls: 'icon-add',
        handler: add
    }, '-', {
        text: '修改',
        iconCls: 'icon-edit',
        handler: edit
    },'-', {
        text: '删除',
        iconCls: 'icon-remove',
        handler: remove
    }/*, '-',{
        text: '执行首字母转换',
        iconCls: 'icon-excel',
        handler: toSpell
    }*/];
	var bar2=[];
       
	if(backGround=='1'){bar=bar2;}else{bar=bar1;};
    $.extend($.fn.validatebox.defaults.rules, {   
	    /*必须和某个字段相等*/
	    equalTo: { 
	        validator:function(value,param){ 
	            return $(param[0]).val() == value; 
	        }, 
	        message:'字段不匹配'
	    },
	    validateUser : {     
            validator: function(value,param){     
                
                if(value.length >= param[0] && param[1] >= value.length) {  
                    if (!/^\w+$/.test(value) && !/^[a-zA-Z0-9_-]+@\w+(\.\w+)+$/.test(value)) {   
	                    param[2]= '用户名只能英文字母、数字及下划线的组合或有效的电子邮件地址！';   
	                    return false;   
	                }
                }else{  
                    param[2] = "请输入"+param[0]+"-"+param[1]+"位字符.";  
                    return false;  
                }
                return true;
            },     
            message: "{2}"   
        },
        wokao:{
        	validator:function(value,param){	// _38f: value; _390: param
        		var flag = $("#flag").val();
				var data={};
				data[param[1]]=value;
				var msg=$.ajax({url:param[0],dataType:"json",data:data,async:false,cache:false,type:"post"}).responseText;
				// 修改不验证用户名唯一性
				if("edit" == flag) {
					msg = "true";
				}
				if(value.length >= 5 && value.length <= 18) {  
				    if (!/^\w+$/.test(value) && !/^[a-zA-Z0-9_-]+@\w+(\.\w+)+$/.test(value)) {  
				    	param[2] = "用户名只能英文字母、数字及下划线的组合或有效的电子邮件地址！";
				        return false;  
				    } else if( msg == "false") {
				    	param[2] = "已存在相同的用户名，请重新输入！"; 
						return false;
					}
				}else{  
					param[2] = "请输入5-18位字符！";
				    return false;  
				}
				return true;
			},
			message:"{2}"
		}
    });  

	grid=$('#grid').datagrid({
				url: listUrl,
				loadMsg:'正在处理，请稍待。。。',
				title: '用户列表',
		        iconCls: 'icon-user',
		        methord: 'post',
		        sortName: 'ID',
		        sortOrder: 'desc',
		        idField: 'ID',
		        pageSize: 10 ,
		        columns: [[
							 {field:'id',title:'id',hidden:true},  
			        		 {field:'username',title:'用户名',width:25},  
			        		 {field:'aliasname',title:'用户显示名',width:25}, 
			        		 {field:'userType',title:'会员类型',width:20,
			        			formatter: function(value,row,index){
								if (value==1){
									return '个人会员';
								} else if(value==2) {
									return '企业会员';
								}else if(value==3) {
									return '渠道商';
								}else if(value==4) {
									return '管理员';
								}
							}},
			        		{field:'enable',title:'是否可用',width:15,
					        	formatter: function(value,row,index){
									if (value){
										return '是';
									} else  {
										return '否';
									}
								}},
			        		{field:'nonLocked',title:'是否锁定',width:15,
					        	formatter: function(value,row,index){
									if (value){
										return '否';
									} else  {
										return '是';
									}
								}}
						]],
		        fit:true,
		        pagination: true,
		        rownumbers: true,
		        fitColumns: true,
		        singleSelect: true,
		        toolbar: bar,
		        onHeaderContextMenu: function (e, field) {
		            e.preventDefault();
		            if (!$('#tmenu').length) {
		                createColumnMenu();
		            }
		            $('#tmenu').menu('show', {
		                left: e.pageX,
		                top: e.pageY
		            });
		        },
				onBeforeLoad: function(param){
					var pageNo=$(this).datagrid('getPager').pagination('options').pageNumber;
					var pageSize=$(this).datagrid('getPager').pagination('options').pageSize;
					//使用查询参数有问题
					//var queryParams={'page.page':pageNo,'page.size':pageSize};
					//$(this).datagrid('options').queryParams=queryParams;
					$(this).datagrid('options').url=pageUrl(pageSize,pageNo);
				}
			});
			
			$('#grid').datagrid('getPager').pagination({
	    		displayMsg:'当前显示从{from}到{to}共{total}记录',
	    		beforePageText:'当前第',
	    		afterPageText:'页,共{pages}页',
	    		onBeforeRefresh:function(pageNumber, pageSize){
	     			$(this).pagination('loading');
	     			$(this).pagination('loaded');
	    		}});
	    		
	$('#modifyPassword').hide();
	
	$('#showDiv').click(function() {
		$('#modifyPassword').slideToggle(1000);
	}); 
	  
    $('body').layout();
    
    
});

function createColumnMenu() {
    var tmenu = $('<div id="tmenu" style="width:100px;"></div>').appendTo('body');
    var fields = grid.datagrid('getColumnFields');
    for (var i = 0; i < fields.length; i++) {
        $('<div iconCls="icon-ok"/>').html(fields[i]).appendTo(tmenu);
    }
    tmenu.menu({
        onClick: function (item) {
            if (item.iconCls == 'icon-ok') {
                grid.datagrid('hideColumn', item.text);
                tmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-empty'
                });
            } else {
                grid.datagrid('showColumn', item.text);
                tmenu.menu('setIcon', {
                    target: item.target,
                    iconCls: 'icon-ok'
                });
            }
        }
    });
}

//首字母转换
function toSpell(){ 
			location.href='tag/tospell';
		
}


function edit() {
    var row = grid.datagrid('getSelected');
    if(row){
    	url='/user/update/'+row.id;
    	$('#dlg').dialog('open').dialog('setTitle','编辑用户');
		$('#fm').form('load',row);
		if(row.nonLocked){$("input[name='nonLocked'][value='1']").attr('checked', 'checked');}else{$("input[name='nonLocked'][value='0']").attr('checked', 'checked');}
		if(row.enable){$("input[name='enable'][value='1']").attr('checked', 'checked');}else{$("input[name='enable'][value='0']").attr('checked', 'checked');}
		userTypeFormLoad(row);
		$("#roles").combobox({editable:false });
		$('#roles').combobox('setValues', roleLoad(row));
		getRadio($("input[name='userType']:checked").val());
		$("input[name='check_password']").val('');
		$("input[name='password']").val('');
		$('#showDiv').show();
		$('#modifyPassword').hide();
		for(var i = 0; i < 4; i++) {
			var str = 'input[name="userType"]:eq(' + i + ')';
			//var str = 'input[name="userType"][value="' + i + '"]';
			var r = $(str);
			//alert(r.val());
			if(!r.attr('checked')) {
				r.attr('disabled', true);
			}
		}
		$("input[name='username']").attr("readonly", true);
		$("#flag").val("edit");
    }else{
    	 Msgslide('请选择一条记录进行操作!'); //$.messager.alert('提示', '请选择一条记录进行操作!', 'info');
        return;
    }
}

function roleLoad(row){
	var roleIds=[];
	for(var i=0;i<row.roles.length;i++){
		roleIds.push(row.roles[i].id);
	}
	return roleIds;
}

// 清除缓存
function userTypeFormLoad(row){
			
			/*个人用户*/
			if(row.additional != null && row.additional != '') {
				if(''!==row.additional.email && null!==row.additional.email){
					$("input[name='additional.email']").val(row.additional.email);
				} else {
					$("input[name='additional.email']").val('');
				}	
				if(''!==row.additional.mobile && null!==row.additional.mobile){
					$("input[name='additional.mobile']").val(row.additional.mobile);
				} else {
					$("input[name='additional.mobile']").val('');
				}
				if(''!==row.additional.location && null!==row.additional.location){
					$("input[name='additional.location']").val(row.additional.location);
				} else {
					$("input[name='additional.location']").val('');
				}
				if(row.additional.sex==1){
					$("input[name='additional.sex'][value='1']").attr('checked', 'checked');
				}else{
					$("input[name='additional.sex'][value='0']").attr('checked', 'checked');
				}
				if(''!==row.additional.birthday && null!==row.additional.birthday){
					$("input[name='str_birthday']").val(row.additional.birthday);
				} else {
					$("input[name='str_birthday']").val('');
				}
			}
			
			/*企业用户*/
			if(row.firm != null && row.firm != '') {
				if(row.firm.name != '' && row.firm.name != null) {
					$("input[name='firm.name']").val(row.firm.name);
				} else {
					$("input[name='firm.name']").val('');
				}
				if(row.firm.addr != '' && row.firm.addr != null) {
					$("input[name='firm.addr']").val(row.firm.addr);
				} else {
					$("input[name='firm.addr']").val('');
				}
				if(row.firm.linkman != '' && row.firm.linkman != null) {
					$("input[name='firm.linkman']").val(row.firm.linkman);
				} else {
					$("input[name='firm.linkman']").val('');
				}
				if(row.firm.phone != '' && row.firm.phone != null) {
					$("input[name='firm.phone']").val(row.firm.phone);
				} else {
					$("input[name='firm.phone']").val('');
				}
				if(row.firm.fax != '' && row.firm.fax != null) {
					$("input[name='firm.fax']").val(row.firm.fax);
				} else {
					$("input[name='firm.fax']").val('');
				}
				if(row.firm.zip != '' && row.firm.zip != null) {
					$("input[name='firm.zip']").val(row.firm.zip);
				} else {
					$("input[name='firm.zip']").val('');
				}
				if(row.firm.email != '' && row.firm.email != null) {
					$("input[name='firm.email']").val(row.firm.email);
				} else {
					$("input[name='firm.email']").val('');
				}
			}
			
			/*渠道商*/
			if(row.agent != null && row.agent != '') {
				if(row.agent.name != '' && row.agent.name != null) {
					$("input[name='agent.name']").val(row.agent.name);
				} else {
					$("input[name='agent.name']").val('');
				}
				if(row.agent.addr != '' && row.agent.addr != null) {
					$("input[name='agent.addr']").val(row.agent.addr);
				} else {
					$("input[name='agent.addr']").val('');
				}
				if(row.agent.linkman != '' && row.agent.linkman != null) {
					$("input[name='agent.linkman']").val(row.agent.linkman);
				} else {
					$("input[name='agent.linkman']").val('');
				}
				if(row.agent.phone != '' && row.agent.phone != null) {
					$("input[name='agent.phone']").val(row.agent.phone);
				} else {
					$("input[name='agent.phone']").val('');
				}
				if(row.agent.fax != '' && row.agent.fax != null) {
					$("input[name='agent.fax']").val(row.agent.fax);
				} else {
					$("input[name='agent.fax']").val('');
				}
				if(row.agent.zip != '' && row.agent.zip != null) {
					$("input[name='agent.zip']").val(row.agent.zip);
				} else {
					$("input[name='agent.zip']").val('');
				}
				if(row.agent.email != '' && row.agent.email != null) {
					$("input[name='agent.email']").val(row.agent.email);
				} else {
					$("input[name='agent.email']").val('');
				}
				if(row.agent.discountValue != '' && row.agent.discountValue != null) {
					$("input[name='agent.discountValue']").val(row.agent.discountValue);
				} else {
					$("input[name='agent.discountValue']").val('');
				}
			}
		
}


function userTypeFormLoad02(row){
	switch(row.userType){
		case 1:
			if(''===row.additional||null===row.additional){break;}
			if(''!==row.additional.email && null!==row.additional.email){
				$("input[name='additional.email']").val(row.additional.email);
			}
			if(''!==row.additional.mobile && null!==row.additional.mobile){
				$("input[name='additional.mobile']").val(row.additional.mobile);
			}
			if(''!==row.additional.location && null!==row.additional.location){
				$("input[name='additional.location']").val(row.additional.location);
			}
			if(row.additional.sex==1){
				$("input[name='additional.sex'][value='1']").attr('checked', 'checked');
			}else{
				$("input[name='additional.sex'][value='0']").attr('checked', 'checked');}
			if(''!==row.additional.birthday && null!==row.additional.birthday){
				$("input[name='str_birthday']").val(row.additional.birthday);
			}

			break;
		case 2:
			$("input[name='firm.name']").val(row.firm.name);
			$("input[name='firm.addr']").val(row.firm.addr);
			$("input[name='firm.linkman']").val(row.firm.linkman);
			$("input[name='firm.phone']").val(row.firm.phone);
			$("input[name='firm.fax']").val(row.firm.fax);
			$("input[name='firm.zip']").val(row.firm.zip);
			$("input[name='firm.email']").val(row.firm.email);
			break;
		case 3:
			$("input[name='agent.name']").val(row.agent.name);
			$("input[name='agent.addr']").val(row.agent.addr);
			$("input[name='agent.linkman']").val(row.agent.linkman);
			$("input[name='agent.phone']").val(row.agent.phone);
			$("input[name='agent.fax']").val(row.agent.fax);
			$("input[name='agent.zip']").val(row.agent.zip);
			$("input[name='agent.email']").val(row.agent.email);
			$("input[name='agent.discountValue']").val(row.agent.discountValue);
			break;
	}
}

function cancel() {
	$('input[name="userType"]').attr('disabled', false);
	$('#dlg').dialog('close');
}

function Msgshow(msg) {
    $.messager.show({
        title: '提示',
        msg: msg,
        showType: 'show'
    });
}
function Msgslide(msg) {
    $.messager.show({
        title: '提示',
        msg: msg,
        timeout: 3000,
        showType: 'slide'
    });
}
function Msgfade(msg) {
    $.messager.show({
        title: '提示',
        msg: msg,
        timeout: 3000,
        showType: 'fade'
    });
}
