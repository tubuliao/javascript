<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <link href="easyui/themes/default/easyui.css" rel="stylesheet" type="text/css" />
    <link href="easyui/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="easyui/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
    <script src="js/news.js" type="text/javascript"></script>
    <script type="text/javascript" src="ueditor/editor_config.js"></script>
    <script type=text/javascript src="ueditor/editor_all.js"></script>
    <style type="text/css">
        #content{
            width: 300px;
            height: 300px;
        }
    </style>
       <script type="tex javascript">
        var editor_a = new baidu.editor.ui.Editor();
        //渲染编辑器
        editor_a.render('content');
    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
    <div region="west" split="true" title="标签树" style="width: 180px;" id="west">
        <ul id="tree">
        </ul>
    </div>
    <div region="center" style="width: 500px; height: 300px; padding: 1px;overflow-y: hidden">
        <div id="grid" >
        </div>
    </div>
    <div id="Dlg-Edit" title="编辑窗口" style="width: 500px; height: 300px;">
        <div style="padding: 20px 20px 40px 80px;">
            <form method="post">
            <table>
                <tr>
                    <td>
                                                            标签：
                    </td>
                    <td>
                    	<select id ="tags" name="tags" Multiple style = "width:204px ;" ></ select >
                    </td>
                </tr>
                <tr>
                    <td>
                                                           名称：
                    </td>
                    <td>
                        <input name="title" class="easyui-validatebox" required="true" style="width: 200px;" />
                    </td>
                </tr>
                <tr>
                    <td>
                                                            内容 ：
                    </td>
                    <td>
                         <textarea id="content" name="content"></textarea>
                    </td>
                </tr>
            </table>
            </form>
        </div>
    </div>
    <div id="search-window" title="查询窗口" style="width: 350px; height: 200px;">
        <div style="padding: 20px 20px 40px 80px;">
            <form method="post">
            <table>
                <tr>
                    <td>
                        名称：
                    </td>
                    <td>
                        <input name="s_title" id="s_title" style="width: 150px;" />
                    </td>
                </tr>
            </table>
            </form>
        </div>
        <div style="text-align: center; padding: 5px;">
            <a href="javascript:void(0)" onclick="SearchOK()" id="btn-search" icon="icon-ok">确定</a>
            <a href="javascript:void(0)" onclick="closeSearchWindow()" id="btn-search-cancel" icon="icon-cancel">
                取消</a>
        </div>
    </div>
</body>
</html>
