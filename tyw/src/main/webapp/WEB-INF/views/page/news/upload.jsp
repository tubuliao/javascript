<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <title></title>
    <script src="easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="uploadify/jquery.uploadify-3.1.js" type="text/javascript"></script>
    <link href="uploadify/uploadify.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
    	$(function(){
    		$("#uploadify").uploadify({
    			'swf':'uploadify/uploadify.swf',
    			'uploader':'tag/ajaxUpload',
    			'queueID':'fileQueue',
    			'buttonText': '添加附件',  
    			'height': 20,   
    			'multi':true,
        		'width': 80, 
    			'onUploadSuccess':function(file,data,response){
    				alert(data);
    				alert(file.name);
    				alert(response);
    			},
    			'onUploadError':function(file,errorCode,errorMsg,errorString){
    				alert(errorMsg);
    				alert(errorCode);
    				alert(errorString);
    			}
    		});
    	});
    </script>

</head>
<body>
	<div id="fileQueue"></div>
	<input type="file" name="uploadify" id="uploadify"/>
</body>
</html>
