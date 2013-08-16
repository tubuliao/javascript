<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新频道测试</title>
 
 </head>
<style type="text/css">
*{ margin:0; padding:0;}

em{ font-style:normal;}
H3 {FONT-SIZE: 12px}
.box{width:188px;text-align:left; float:left; margin:4px 10px 0 0;}
.allsort2 {Z-INDEX:1; FLOAT: left; WIDTH: 188px; MARGIN-left: px; POSITION: relative; HEIGHT:32px; background-color:#FFFFFF;clear:both;}
.allsort2 .mt {PADDING-RIGHT: 12px; PADDING-LEFT: 16px; CURSOR: pointer; LINE-HEIGHT: 24px; HEIGHT: 24px; margin:0}
.allsort2 .mt { font-family:"微软雅黑", "宋体", "黑体";FONT-SIZE: 14px; FLOAT: left; COLOR: #630}
#o-search .allsort2 .mt A {COLOR: #630}
.allsort2 .mt .extra {DISPLAY: none}
.allsort2 .mc { left:-1px; background:url(/newstart/images/fbbg_93.gif); PADDING-RIGHT: 3px; PADDING-LEFT: 3px;PADDING-BOTTOM: 0px; OVERFLOW: visible; WIDTH: 254px; -moz-border-radius: 0 0 5px 5px; -webkit-border-radius: 0 0 5px 5px}
.allsort2 .item { WIDTH: 176px; HEIGHT:34px; line-height:34px;}
.allsort2 .fore {BORDER-TOP-STYLE: none}
.allsort2 SPAN {DISPLAY: block; Z-INDEX: 1; WIDTH: 176px; POSITION: relative}
.allsort2 H3 { PADDING-LEFT: 20px; FONT-WEIGHT: normal;   WIDTH: 165px; HEIGHT:px;}
.allsort2 H3 A:link {      COLOR: #666; LINE-HEIGHT: 27px; HEIGHT:27px}
.allsort2 H3 A:visited {DISPLAY: block; COLOR: #666; LINE-HEIGHT: 27px; HEIGHT: 27px}
.allsort2 H3 A:hover {COLOR: #ff0000}
.allsort2 H3 A:active {COLOR: #666}
.allsort2 S {DISPLAY: block; BACKGROUND: url(/images/homeBg_53.gif) no-repeat top right; LEFT: 156px; WIDTH: 13px; POSITION: absolute; TOP: 10px; HEIGHT: 13px}
.allsort2 .item .i-mc { DISPLAY: none; LEFT: 188px; OVERFLOW: hidden;  WIDTH: 950px; min-height:300px; border:1px solid #FF0000; background-color:#FFFFFF; POSITION: absolute; TOP: 0px}
.allsort2 .item DT {    PADDING-RIGHT: 6px; PADDING-LEFT: 0px; FONT-WEIGHT: bold; PADDING-BOTTOM: 0px; PADDING-TOP: 3px}
.allsort2 .item DD {    PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; OVERFLOW: hidden; PADDING-TOP: 3px; ZOOM: 1}
.allsort2 .subitem {    PADDING-RIGHT: 4px; PADDING-LEFT: 8px; MIN-HEIGHT: px; FLOAT: left; PADDING-BOTTOM: 0px; WIDTH: ; PADDING-TOP: 0px}
.allsort2 .subitem .fore{ display:block; width:; clear:both; overflow:hidden; border-bottom:1px solid #eee;}
.allsort2 .subitem dt{ width:px; text-align:right; float:left; color:#FF6600; padding-right:20px;}
.allsort2 .subitem dd{ float:left; display:block; color:#999999; width:720px;}
.allsort2 .subitem dd span{ display: inline-table; color:#FF6600; width:800px; float:left; border-bottom:1px dotted #CCCCCC;}
.allsort2 .subitem dd a{ color:#3399FF;}

.allsort2 .fr {BACKGROUND: #fff; MARGIN-BOTTOM: -2000px; PADDING-BOTTOM: 2010px; WIDTH: 176px}
.allsort2 .fr DL {PADDING-BOTTOM: 0px}
#o-search .allsort2 .fr A {COLOR: #666}
.allsort2 .brands {PADDING-RIGHT: 8px; PADDING-LEFT: 8px; PADDING-BOTTOM: 6px; WIDTH: 160px; PADDING-TOP: 6px}
.allsort2 .brands EM {FLOAT: left; WIDTH: 80px}
.allsort2 .brands DD {LINE-HEIGHT: 20px; PADDING-TOP: 6px}
.allsort2 .promotion {PADDING-RIGHT: 8px; PADDING-LEFT: 8px; PADDING-BOTTOM: 6px; WIDTH: 160px; PADDING-TOP: 6px}
.allsort2 .promotion DD {LINE-HEIGHT: 20px; PADDING-TOP: 6px}
.allsort2 .mc .extra {PADDING-RIGHT: 8px; BORDER-TOP: #fde6d2 1px solid; PADDING-LEFT: 8px; BACKGROUND: #fdf1de; PADDING-BOTTOM: 7px; PADDING-TOP: 7px}
#o-search .allsort2 .mc .extra A {COLOR: #c00}
.allsort2hover {BACKGROUND-POSITION: 0px -166px}
.allsort2hover .mt .extra {BACKGROUND-POSITION: -274px -190px}
.allsort2hover .mc {DISPLAY: inline-block}
.allsort2 .hover SPAN {Z-INDEX: 13; WIDTH: 160px}
.allsort2 .hover H3 {BORDER-RIGHT: #c30 0px solid; BORDER-TOP: #c30 1px solid; FONT-WEIGHT: bold; BACKGROUND: url(/images/Bg.jpg) #fff no-repeat -318px -172px; OVERFLOW: hidden; BORDER-LEFT: #c30 1px solid; BORDER-BOTTOM: #c30 1px solid}
.allsort2 .hover S {DISPLAY: none}
.allsort2 .hover .i-mc {DISPLAY: inline-block; Z-INDEX: 12; padding:20px; line-height:30px;
  box-shadow: 0px 0px 5px #afafaf;    /*css3*/
  -moz-box-shadow: 0px 0px 5px #afafaf;     /*firefox*/
  -webkit-box-shadow: 0px 0px 5px #afafaf;    /*webkit*/
}
* HTML .allsort2 .item DD {PADDING-BOTTOM: 6px}
.allsort2 .blue{ border-top:solid 1px #ddd;}
.allsort2 .blue a{color:#3399FF;font-weight:bold;text-decoration: underline; padding-left:20px;}
</style>
 <script type="text/javascript">
(function ($){$.fn.hoverForIE6 = function (option) { var s = $.extend({ current: "hover", delay: 10 }, option || {});$.each(this, function () { var timer1 = null, timer2 = null, flag = false;$(this).bind("mouseover", function () { if (flag) { clearTimeout(timer2); } else { var _this = $(this); timer1 = setTimeout(function () { _this.addClass(s.current); flag = true; }, s.delay); } }).bind("mouseout", function () { if (flag) { var _this = $(this); timer2 = setTimeout(function () { _this.removeClass(s.current); flag = false; }, s.delay); } else { clearTimeout(timer1); } }) }) } })(jQuery);


</script>
<body>
 
      <div class="allsort2">
        
        <div class="mc">
		 
  <div class='item fore'>
	<span>
		<h3><b><a  class="present"  href="/goChannel?rootCode=40201000000000000">工程管理</a></b></h3>
	</span>
	<div class='i-mc'>
		<div class='subitem'>
			<dl class='fore'>
				<dt>项目管理规划</dt>
				<dd>
				</dd>
			</dl>
			<dl class='fore'>
				<dt>合同管理</dt>
				<dd>
				<p>工程合同管理 | 合同范本</p>
				</dd>
			</dl>
			<dl class='fore'>
				<dt>进度管理</dt>
				<dd>
				</dd>
			</dl>
			<dl class='fore'>
				<dt>质量管理</dt>
				<dd>
				<p>工程质量管理 | 质量事故及处理</p>
				</dd>
			</dl>
			<dl class='fore'>
				<dt>现场管理</dt>
				<dd>
				</dd>
			</dl>
			<dl class='fore'>
				<dt>成本管理</dt>
				<dd>
				</dd>
			</dl>
			<dl class='fore'>
				<dt>资源管理</dt>
				<dd>
				<p><span>工程物资及管理</span>
				物资管理
				</p>
				<p><span>工程物资及管理</span>
				物资管理
				</p>
				<p><span>机械设备管理</span>
				机械机具管理要求
				</p>
				<p><span>工程物资及管理</span>
				物资管理
				</p>
				<p><span>机械设备管理</span>
				机械机具管理要求
				</p>
				<p><span>技术管理</span>
				施工组织设计编制与管理 | 施工方案的编制与管理 | 技术交底的编制与管理 | 检测试验管理
				</p>
				<p>人力资源管理 | 资金管理</p>
				</dd>
			</dl>
			<dl class='fore'>
				<dt>其他管理</dt>
				<dd>
				</dd>
			</dl>
			<dl class='fore'>
				<dt>管理制度实例</dt>
				<dd>
				</dd>
			</dl>
			<dl class='fore'>
				<dt>管理常用表</dt>
				<dd>
				<p>工程部管理用表 | 技术部管理用表 | 质量部管理用表 | 安全部管理用表 | 物资部管理用表 | 设备部管理用表 | 商务部管理用表 | 项目经理办管理用表</p>
				</dd>
			</dl>
		</div>
	</div>
</div>
		 
 <div class='item fore'><span><h3><b><a class="hover"  href="/goChannel?rootCode=40503000000000000">案例·图纸</a></b></h3></span><div class='i-mc'><div class='subitem'><dl class='fore'><dt>工程施工案例</dt><dd></dd></dl><dl class='fore'><dt>质量事故及处理</dt><dd></dd></dl><dl class='fore'><dt>成套施工图纸</dt><dd></dd></dl></div></div></div>

 <div class='item fore'><span><h3><b>四新技术</b></h3></span><div class='i-mc'><div class='subitem'><dl class='fore'><dt>新技术信息</dt><dd><p><span>建筑新材料</span>新型混凝土材料 | 新型金属材料 | 新型墙体材料 | 新型防水、密封材料 | 绿色节能材料 | 新型防火阻燃材料 | 抗震、加固工程材料 | 建筑防腐材料</p><p><span>装饰装修新材料</span>新型涂料 | 新型装饰板材 | 新型玻璃材料 | 新型门窗 | 新型幕墙 | 隔声材料 | 新型地面材料 | 其他新型装饰材料</p><p><span>机电设备新产品</span>新型给水排水材料设备 | 新型供热、采暖设备 | 新型通风与空调设备 | 新型建筑电气及照明设备 | 新型能源利用设备</p><p><span>新工艺新技术</span>地基基础与地下工程技术 | 混凝土技术 | 钢筋及预应力技术 | 新型建筑结构技术 | 新型模板与脚手架技术 | 建筑防水技术 | 工程施工安全新技术 | 抗震与加固改造技术 | 室内环境技术 | 信息化应用技术</p><p>新型机械机具</p></dd></dl><dl class='fore'><dt>新技术应用</dt><dd><p>十项新技术应用要点 | 新材料新产品应用 | 新技术新工艺应用 | 绿色节能技术应用</p></dd></dl><dl class='fore'><dt>工程示范</dt><dd><p>示范工程管理 | 示范工程实例 | 示范工程申报评审</p></dd></dl></div></div></div>

<div class='item fore'><span><h3><b>安全环保</b></h3></span><div class='i-mc'><div class='subitem'><dl class='fore'><dt>安全条文</dt><dd><p>施工安全强制性条文 | 施工安全规范条文</p></dd></dl><dl class='fore'><dt>安全管理</dt><dd><p><span>安全管理制度</span>安全生产管理制度 | 日常安全管理制度</p><p><span>安全生产责任制</span>岗位安全生产责任制 | 部门安全生产责任制</p><p><span>安全教育培训</span>安全教育管理 | 三级安全教育 | 日常安全教育 | 特种人员安全培训 | 安全考试试题</p><p><span>安全事故处理</span>安全事故分析与处理 | 安全事故案例</p><p><span>安全检查与验收</span>安全管理工作检查与验收 | 基坑工程安全检查与验收 | 模板工程安全检查与验收 | 脚手架工程安全检查与验收 | 施工用电安全检查与验收 | 现场安全防护检查与验收 | 起重吊装机械安全检查验收 | 机械机具安全检查与验收 | 施工现场安全检查与验收</p><p><span>文明施工</span>文明施工管理 | 文明施工措施 | 文明施工方案</p><p>危险源识别 | 安全知识</p></dd></dl><dl class='fore'><dt>安全技术</dt><dd><p>模板工程安全技术 | 脚手架工程安全技术 | 建筑施工用电安全技术 | 建筑施工高处作业安全技术 | 建筑施工防火安全技术 | 建筑施工机械安全技术 | 建筑施工安全防护技术 | 分部分项工程安全技术</p></dd></dl><dl class='fore'><dt>安全资料</dt><dd><p><span>安全专项施工方案</span>基坑支护与降水工程安全专项施工方案 | 土方开挖工程安全专项施工方案 | 模板工程安全专项施工方案 | 脚手架工程安全专项施工方案 | 起重吊装工程安全专项施工方案 | 拆除、爆破工程安全专项施工方案 | 其他危险性较大工程安全专项施工方案</p><p><span>施工安全技术交底</span>施工作业人员安全技术交底 | 施工机械操作安全技术交底</p><p><span>施工单位安全管理资料表格</span>安全控制管理资料表格 | 消防保卫安全管理资料表格 | 脚手架工程安全管理资料表格 | 模板工程安全管理资料表格 | 基坑支护安全管理资料表格 | 安全防护安全管理资料表格 | 临时用电安全管理资料表格 | 施工升降机安全管理资料表格 | 塔式起重机安全管理资料表格 | 施工机具安全管理资料表格 | 施工现场文明生产安全管理资料表格 | 施工企业安全生产管理用表</p><p>安全策划及预案 | 建设单位安全管理资料表格 | 监理单位安全管理资料表格 | 安全管理其他资料表格</p></dd></dl><dl class='fore'><dt>环境保护</dt><dd><p>环境控制 | 节能减排</p></dd></dl></div></div></div>
	
<div class='item fore'><span><h3><b>工程创优</b></h3></span><div class='i-mc'><div class='subitem'><dl class='fore'><dt>国家级优质工程</dt><dd><p><span>创国家级优质工程相关规定</span>詹天佑奖 | 鲁班奖 | 国家优质工程奖</p><p>国家级优质工程创优方案 | 国家级优质工程申报材料实例 | 国家级优质工程图展</p></dd></dl><dl class='fore'><dt>省（市）级优质工程</dt><dd><p>创省（市）级优质工程相关规定 | 省（市）级优质工程创优方案 | 省（市）级优质工程申报材料实例 | 省（市）级优质工程图展 | 优质精品工程策划与实施</p></dd></dl></div></div></div>
<div class='item fore'><span><h3><b>工程测量</b></h3></span><div class='i-mc'><div class='subitem'><dl class='fore'><dt>平面控制测量</dt><dd></dd></dl><dl class='fore'><dt>高程控制测量</dt><dd></dd></dl><dl class='fore'><dt>地形、场地测量</dt><dd></dd></dl><dl class='fore'><dt>基础施工测量</dt><dd></dd></dl><dl class='fore'><dt>主体结构施工测量</dt><dd></dd></dl><dl class='fore'><dt>装饰施工测量</dt><dd></dd></dl><dl class='fore'><dt>设备安装施工测量</dt><dd></dd></dl><dl class='fore'><dt>道路测量</dt><dd></dd></dl><dl class='fore'><dt>管线测量</dt><dd></dd></dl><dl class='fore'><dt>变形测量</dt><dd></dd></dl><dl class='fore'><dt>工业建筑施工测量</dt><dd></dd></dl><dl class='fore'><dt>竣工测量及竣工图</dt><dd></dd></dl><dl class='fore'><dt>测量仪器设备</dt><dd></dd></dl><dl class='fore'><dt>工程测量管理</dt><dd></dd></dl></div></div></div>

<div class='item fore'><span><h3><b>施工监测</b></h3></span><div class='i-mc'><div class='subitem'><dl class='fore'><dt>工程施工监测</dt><dd><p>基坑及支护结构工程监测 | 建（构）筑物工程监测 | 地下工程施工监测 | 高支模工程监测</p></dd></dl><dl class='fore'><dt>建筑环境检测</dt><dd><p>建筑热环境检测 | 建筑光环境检测 | 建筑声环境检测 | 空气质量检测</p></dd></dl></div></div></div>
	
<div class='item fore'><span><h3><b>施组与方案</b></h3></span><div class='i-mc'><div class='subitem'><dl class='fore'><dt>投标施组</dt><dd><p>住宅建筑工程投标施组 | 办公楼酒店工程投标施组 | 公用商业工程投标施组 | 文教卫生工程投标施组 | 交通体育工程投标施组 | 装修及加固改造工程投标施组 | 工业建筑工程投标施组 | 其他工程投标施组 | 投标施组的编制与管理</p></dd></dl><dl class='fore'><dt>实施性施组</dt><dd><p>住宅建筑工程施组 | 办公楼酒店工程施组 | 公用商业工程施组 | 文教卫生工程施组 | 交通体育工程施组 | 装修及加固改造工程施组 | 工业建筑工程施组 | 其他工程施组</p></dd></dl><dl class='fore'><dt>专项施工方案</dt><dd><p>临时设施施工方案 | 临时用水施工方案 | 临时用电施工方案 | 施工测量方案 | 施工试验方案 | 工程资料管理方案 | 工程创优方案 | 绿色施工方案 | 建筑节能施工方案 | 成品保护方案 | 冬期施工方案 | 雨期施工方案</p></dd></dl></div></div></div>
<div class='item fore'><span><h3><b>造价招投标</b></h3></span><div class='i-mc'><div class='subitem'><dl class='fore'><dt>招标投标</dt><dd><p>招标投标基础知识 | 工程招标文件 | 工程投标文件</p></dd></dl><dl class='fore'><dt>工程造价</dt><dd><p>工程造价基础知识 | 工程量计算规则 | 工程概算及实例 | 工程预算及实例 | 工程量清单计价及实例 | 工程竣工结算</p></dd></dl></div></div></div>

<div class='item fore'><span><h3><b>检测机构用表</b></h3></span><div class='i-mc'><div class='subitem'><dl class='fore'><dt>检测机构用表</dt><dd><p><span>建筑及装饰材料检测试验用表</span>混凝土结构材料检测试验用表 | 墙体材料检测试验用表 | 金属结构材料检测试验用表 | 木结构材料检测试验用表 | 膜结构材料检测试验用表 | 预制混凝土构配件检测试验用表 | 砂浆材料检测试验用表 | 装饰装修材料检测试验用表 | 门窗幕墙检测试验用表 | 防水材料检测试验用表 | 嵌缝密封材料检测试验用表 | 胶粘剂检测试验用表 | 保温吸声材料检测试验用表</p><p><span>安装工程材料检测试验用表</span>管网材料检测试验用表 | 电气材料检测试验用表 | 防腐绝缘材料检测试验用表</p></dd></dl></div></div></div>



       
       
      
        </div>
      </div>
    <script type="text/javascript">
      $(".allsort2 .item").hoverForIE6({ delay: 150 });
    </script>
 
</body>
</html>
