<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" pageEncoding="UTF-8"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新频道测试</title>
  <script src="${pageContext.request.contextPath}/easyui/jquery-1.7.2.min.js" type="text/javascript"></script>
 <!--[if lt IE 7]>
        <script type="text/javascript" src="unitpngfix.js"></script>
<![endif]-->
 </head>
<style type="text/css">
*{ margin:0; padding:0;}
body{ font:12px/30px "宋体", "Arial Unicode MS"; color:#666; background-color:#f9f9f9;}
em{ font-style:normal;}
H3 {FONT-SIZE: 12px}
.box{width:188px;text-align:left; float:left; margin:4px 10px 0 0;}
.allsort {Z-INDEX:10; FLOAT: left; WIDTH: 188px; MARGIN-left: 4px; POSITION: relative; HEIGHT:32px; background-color:#FFFFFF;clear:both;}
.allsort .mt {PADDING-RIGHT: 12px; PADDING-LEFT: 16px; CURSOR: pointer; LINE-HEIGHT: 24px; HEIGHT: 24px; margin:0}
.allsort .mt { font-family:"微软雅黑", "宋体", "黑体";FONT-SIZE: 14px; FLOAT: left; COLOR: #630}
#o-search .allsort .mt A {COLOR: #630}
.allsort .mt .extra {DISPLAY: none}
.allsort .mc { left:-1px; background:#FFF;BORDER-RIGHT: #C6001D 1px solid; PADDING-RIGHT: 3px; BORDER-TOP: #C6001D 0px solid; PADDING-LEFT: 3px;PADDING-BOTTOM: 0px; OVERFLOW: visible; BORDER-LEFT: #C6001D 1px solid; WIDTH: 182px; PADDING-TOP: 0px; BORDER-BOTTOM: #C6001D 1px solid; POSITION: absolute; TOP: 32px; -moz-border-radius: 0 0 5px 5px; -webkit-border-radius: 0 0 5px 5px}
.allsort .item {BORDER-TOP: #E8E8E8 1px solid; WIDTH: 176px; HEIGHT:27px}
.allsort .fore {BORDER-TOP-STYLE: none}
.allsort SPAN {DISPLAY: block; Z-INDEX: 1; WIDTH: 176px; POSITION: relative}
.allsort H3 { PADDING-LEFT: 20px; FONT-WEIGHT: normal;   WIDTH: 165px; HEIGHT:28px;}
.allsort H3 A:link {      COLOR: #666; LINE-HEIGHT: 27px; HEIGHT:27px}
.allsort H3 A:visited {DISPLAY: block; COLOR: #666; LINE-HEIGHT: 27px; HEIGHT: 27px}
.allsort H3 A:hover {COLOR: #ff0000}
.allsort H3 A:active {COLOR: #666}
.allsort S {DISPLAY: block; BACKGROUND: url(/images/homeBg_53.gif) no-repeat top right; LEFT: 156px; WIDTH: 13px; POSITION: absolute; TOP: 10px; HEIGHT: 13px}
.allsort .item .i-mc { DISPLAY: none; LEFT: 188px; OVERFLOW: hidden;  WIDTH: 1000px; border:1px solid #FF0000; background-color:#FFFFFF; POSITION: absolute; TOP: 0px}
.allsort .item DT {    PADDING-RIGHT: 6px; PADDING-LEFT: 0px; FONT-WEIGHT: bold; PADDING-BOTTOM: 0px; PADDING-TOP: 3px}
.allsort .item DD {    PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; OVERFLOW: hidden; PADDING-TOP: 3px; ZOOM: 1}
.allsort .subitem {    PADDING-RIGHT: 4px; PADDING-LEFT: 8px; MIN-HEIGHT: px; FLOAT: left; PADDING-BOTTOM: 0px; WIDTH: ; PADDING-TOP: 0px}
.allsort .subitem .fore{ display:block; width:; clear:both; overflow:hidden; border-bottom:1px solid #eee;}
.allsort .subitem dt{ width:px; text-align:right; float:left; color:#FF6600; padding-right:20px;}
.allsort .subitem dd{ float:left; display:block; color:#999999; width:720px;}
.allsort .subitem dd span{ display: inline-table; color:#FF6600; width:800px; float:left; border-bottom:1px dotted #CCCCCC;}
.allsort .subitem dd a{ color:#3399FF;}

.allsort .fr {BACKGROUND: #fff; MARGIN-BOTTOM: -2000px; PADDING-BOTTOM: 2010px; WIDTH: 176px}
.allsort .fr DL {PADDING-BOTTOM: 0px}
#o-search .allsort .fr A {COLOR: #666}
.allsort .brands {PADDING-RIGHT: 8px; PADDING-LEFT: 8px; PADDING-BOTTOM: 6px; WIDTH: 160px; PADDING-TOP: 6px}
.allsort .brands EM {FLOAT: left; WIDTH: 80px}
.allsort .brands DD {LINE-HEIGHT: 20px; PADDING-TOP: 6px}
.allsort .promotion {PADDING-RIGHT: 8px; PADDING-LEFT: 8px; PADDING-BOTTOM: 6px; WIDTH: 160px; PADDING-TOP: 6px}
.allsort .promotion DD {LINE-HEIGHT: 20px; PADDING-TOP: 6px}
.allsort .mc .extra {PADDING-RIGHT: 8px; BORDER-TOP: #fde6d2 1px solid; PADDING-LEFT: 8px; BACKGROUND: #fdf1de; PADDING-BOTTOM: 7px; PADDING-TOP: 7px}
#o-search .allsort .mc .extra A {COLOR: #c00}
.allsorthover {BACKGROUND-POSITION: 0px -166px}
.allsorthover .mt .extra {BACKGROUND-POSITION: -274px -190px}
.allsorthover .mc {DISPLAY: inline-block}
.allsort .hover SPAN {Z-INDEX: 13; WIDTH: 160px}
.allsort .hover H3 {BORDER-RIGHT: #c30 0px solid; BORDER-TOP: #c30 1px solid; FONT-WEIGHT: bold; BACKGROUND: url(/images/Bg.jpg) #fff no-repeat -318px -172px; OVERFLOW: hidden; BORDER-LEFT: #c30 1px solid; BORDER-BOTTOM: #c30 1px solid}
.allsort .hover S {DISPLAY: none}
.allsort .hover .i-mc {DISPLAY: inline-block; Z-INDEX: 12; padding:20px;
  box-shadow: 0px 0px 5px #afafaf;    /*css3*/
  -moz-box-shadow: 0px 0px 5px #afafaf;     /*firefox*/
  -webkit-box-shadow: 0px 0px 5px #afafaf;    /*webkit*/
}
* HTML .allsort .item DD {PADDING-BOTTOM: 6px}
.allsort .blue{ border-top:solid 1px #ddd;}
.allsort .blue a{color:#3399FF;font-weight:bold;text-decoration: underline; padding-left:20px;}
</style>
<script type="text/javascript" src="http://www.miiceic.org.cn/templets/js/jquery.min.js"></script>
<script type="text/javascript">
(function ($){$.fn.hoverForIE6 = function (option) { var s = $.extend({ current: "hover", delay: 10 }, option || {});$.each(this, function () { var timer1 = null, timer2 = null, flag = false;$(this).bind("mouseover", function () { if (flag) { clearTimeout(timer2); } else { var _this = $(this); timer1 = setTimeout(function () { _this.addClass(s.current); flag = true; }, s.delay); } }).bind("mouseout", function () { if (flag) { var _this = $(this); timer2 = setTimeout(function () { _this.removeClass(s.current); flag = false; }, s.delay); } else { clearTimeout(timer1); } }) }) } })(jQuery);
 $(document).ready(function(){
  $("#menufbfx span a").mouseover(function(){
 		var menuname=$(this).attr("name");
		var contentfbfx=$("#contentfbfx div[name='"+menuname+"']").html();
		  $(this).parent().parent().parent().next().find('.subitem').html(contentfbfx);
 });
 })

</script>
<body  >
 <div style="display:none" id="contentfbfx">
  <div name="rfgc"  class='subitem'></div> 
 <div class='subitem'  name="smgc"><dl class='fore'><dt>基层与保护工程</dt><dd><p><span>屋面找坡层和找平层</span>找坡层和找平层设计 | 找坡层与找平层施工</p><p><span>屋面保护层和隔离层</span>保护层和隔离层设计 | 屋面保护层施工 | 屋面隔离层施工</p><p>屋面隔汽层</p></dd></dl><dl class='fore'><dt>屋面保温与隔热工程</dt><dd><p><span>保温层与隔热层施工</span>板状材料保温层 | 纤维材料保温层 | 喷涂硬泡聚氨酯保温层 | 现浇泡沫混凝土保温层 | 种植隔热层 | 架空隔热层 | 蓄水隔热层</p><p>保温层与隔热层设计</p></dd></dl><dl class='fore'><dt>屋面防水与密封工程</dt><dd><p><span>屋面防水与密封设计</span>屋面卷材及涂膜防水设计 | 屋面接缝密封防水设计</p><p>卷材防水屋面施工 | 涂膜防水屋面施工 | 复合防水层屋面施工 | 接缝密封防水施工</p></dd></dl><dl class='fore'><dt>瓦屋面与板屋面工程</dt><dd><p><span>瓦屋面工程</span>瓦屋面工程设计 | 瓦屋面工程施工</p><p><span>金属板屋面工程</span>金属板屋面设计 | 金属板屋面施工</p><p><span>玻璃采光顶工程</span>玻璃采光顶设计 | 玻璃采光顶施工</p></dd></dl><dl class='fore'><dt>屋面工程细部构造</dt><dd><p><span>屋面细部构造设计</span>檐口构造 | 檐沟和天沟构造 | 女儿墙和山墙构造 | 水落口构造 | 屋面变形缝构造 | 伸出屋面管道构造 | 屋面出入口构造 | 反过梁水孔构造 | 屋面设施基座构造 | 屋脊构造 | 屋顶窗构造</p><p><span>细部构造工程施工</span>檐口施工 | 檐沟和天沟施工 | 女儿墙和山墙施工 | 水落口施工 | 屋面变形缝施工 | 伸出屋面管道施工 | 屋面出入口施工 | 反梁过水孔施工 | 设施基座施工 | 屋脊施工 | 屋顶窗施工</p></dd></dl><dl class='fore'><dt>坡屋面工程</dt><dd><p>坡屋面工程材料 | 坡屋面工程防水垫层 | 沥青瓦坡屋面 | 块瓦坡屋面 | 波形瓦坡屋面 | 金属板坡屋面 | 防水卷材坡屋面 | 装配式轻型坡屋面</p></dd></dl><dl class='fore'><dt>倒置式屋面工程</dt><dd></dd></dl><dl class='fore'><dt>屋面排水系统工程</dt><dd></dd></dl></div>
 <div class='subitem'  name="mbgc"><dl class='fore'><dt>组合钢模板</dt><dd><p><span>组合钢模板设计</span>组合钢模板设计计算 | 组合钢模板配板 | 组合钢模板支承系统设计</p><p>组合钢模板制作 | 组合钢模板安装 | 组合钢模板拆除 | 组合钢模板运输、维修与保管</p></dd></dl><dl class='fore'><dt>木竹胶合板模板</dt><dd><p><span>胶合板模板</span>胶合板模板设计 | 胶合板模板构造 | 胶合板模板安装 | 胶合板模板拆除</p><p><span>钢框胶合板模板</span>钢框胶合板模板设计 | 钢框胶合板模板构造 | 钢框胶合板模板安装 | 钢框胶合板模板拆除</p></dd></dl><dl class='fore'><dt>全钢大模板</dt><dd><p><span>全钢大模板设计</span>全钢大模板设计计算 | 全钢大模板配板 | 全钢大模板支承系统设计</p><p>全钢大模板安装 | 全钢大模板拆除 | 全钢大模板运输、维修和保管</p></dd></dl><dl class='fore'><dt>滑动模板</dt><dd><p><span>滑模工程设计</span>筒体结构滑模设计 | 框架结构滑模设计 | 墙板结构滑模设计</p><p><span>滑模装置设计制作</span>滑模总体设计 | 滑模部件设计制作</p><p>滑模施工 | 特种滑模施工</p></dd></dl><dl class='fore'><dt>爬升模板</dt><dd><p><span>爬模装置设计</span>爬模设计计算 | 爬模整体设计 | 爬模部件设计</p><p>爬模装置制作 | 爬模安装 | 爬模施工 | 爬模拆除 | 爬模装置维护保养</p></dd></dl><dl class='fore'><dt>飞模</dt><dd><p>飞模设计 | 飞模施工 | 升降、行走和吊运</p></dd></dl><dl class='fore'><dt>模壳</dt><dd><p>模壳设计 | 模壳支撑系统 | 模壳施工</p></dd></dl><dl class='fore'><dt>永久性模板</dt><dd><p>压型钢板模板 | 混凝土薄板模板</p></dd></dl><dl class='fore'><dt>清水混凝土模板</dt><dd><p>清水混凝土模板设计 | 清水混凝土模板制作 | 清水混凝土模板安装 | 清水混凝土模板拆除</p></dd></dl><dl class='fore'><dt>其他模板</dt><dd></dd></dl><dl class='fore'><dt>模板体系</dt><dd></dd></dl></div>
<div class='subitem'  name="dtgc"><dl class='fore'><dt>曳引式或强制式电梯安装</dt><dd><p>设备进场验收 | 土建交接检验 | 驱动主机安装 | 导轨安装 | 门系统安装 | 轿厢安装 | 对重（平衡重）安装 | 安全部件安装 | 悬挂装置安装 | 随行电缆安装 | 补偿装置安装 | 电气装置安装 | 整机安装验收</p></dd></dl><dl class='fore'><dt>液压电梯安装</dt><dd><p>液压电梯设备进场验收 | 液压电梯安装土建交接检验 | 液压系统安装 | 液压电梯导轨安装 | 液压电梯门系统安装 | 液压电梯轿厢安装 | 液压电梯对重（平衡重）安装 | 液压电梯安全部件安装 | 液压电梯悬挂装置安装 | 液压电梯随行电缆安装 | 液压电梯电气装置安装 | 液压电梯整机安装验收</p></dd></dl><dl class='fore'><dt>自动扶梯、自动人行道安装</dt><dd><p>自动扶梯、人行道设备进场验收 | 自动扶梯、人行道土建交接检验 | 自动扶梯、人行道整机安装验收</p></dd></dl></div>
<div class='subitem'  name="gjgc"><dl class='fore'><dt>钢筋原材料</dt><dd><p>钢筋品种和规格 | 钢筋性能 | 钢筋检验 | 钢筋保管</p></dd></dl><dl class='fore'><dt>配筋构造</dt><dd><p>板配筋构造 | 抗震配筋构造 | 梁配筋构造 | 柱配筋构造 | 墙体配筋构造 | 基础配筋构造 | 预埋件及吊环</p></dd></dl><dl class='fore'><dt>钢筋配料与代换</dt><dd><p>钢筋配料 | 钢筋代换</p></dd></dl><dl class='fore'><dt>钢筋加工</dt><dd><p>钢筋除锈 | 钢筋调直 | 钢筋切断 | 钢筋弯曲成型</p></dd></dl><dl class='fore'><dt>钢筋机械连接</dt><dd><p>钢筋锥螺纹连接 | 钢筋套筒挤压连接 | 镦粗直螺纹钢筋连接 | 滚轧直螺纹钢筋连接 | 钢筋熔融金属充填连接 | 水泥灌浆充填钢筋连接</p></dd></dl><dl class='fore'><dt>钢筋焊接连接</dt><dd><p>钢筋电阻点焊接 | 钢筋闪光对焊焊接 | 箍筋闪光对焊焊接 | 钢筋电弧焊焊接 | 钢筋电渣压力焊焊接 | 钢筋气压焊焊接 | 预埋件钢筋埋弧压力焊焊接 | 预埋件钢筋埋弧螺柱焊焊接</p></dd></dl><dl class='fore'><dt>钢筋绑扎安装</dt><dd><p>基础钢筋绑扎 | 柱钢筋绑扎 | 梁、板钢筋绑扎 | 墙钢筋绑扎</p></dd></dl><dl class='fore'><dt>钢筋网</dt><dd><p><span>钢筋焊接网构造</span>板 | 墙 | 钢筋笼</p><p>绑扎钢筋网与钢筋骨架 | 钢筋焊接网安装</p></dd></dl><dl class='fore'><dt>冷轧带肋钢筋工程</dt><dd><p><span>冷轧带肋钢筋结构构造</span>冷轧带肋钢筋结构梁、柱构造 | 冷轧带肋钢筋结构墙、板构造</p><p><span>冷轧带肋钢筋结构施工</span>冷轧带肋钢筋加工 | 冷轧带肋钢筋骨架制作安装 | 预应力钢筋张拉</p><p>冷轧带肋钢筋原材料</p></dd></dl><dl class='fore'><dt>冷轧扭钢筋工程</dt><dd><p>冷轧扭钢筋原材料 | 冷轧扭钢筋结构构造 | 冷轧扭钢筋混凝土构件施工 | 预应力冷轧扭钢筋混凝土构件施工</p></dd></dl><dl class='fore'><dt>钢筋间隔件</dt><dd><p>钢筋间隔件制作 | 钢筋间隔件安放 | 钢筋间隔件运输、保管</p></dd></dl><dl class='fore'><dt>钢筋阻锈剂</dt><dd><p>钢筋阻锈剂材料及选用 | 钢筋阻锈剂施工</p></dd></dl><dl class='fore'><dt>植筋施工</dt><dd></dd></dl><dl class='fore'><dt>钢筋工程特殊季节施工</dt><dd></dd></dl></div>
<div class='subitem'  name="mjggc"><dl class='fore'><dt>方木和原木结构</dt><dd></dd></dl><dl class='fore'><dt>轻型木结构</dt><dd></dd></dl><dl class='fore'><dt>木屋架、屋盖</dt><dd></dd></dl><dl class='fore'><dt>木结构工程原材料</dt><dd></dd></dl><dl class='fore'><dt>胶合木结构</dt><dd></dd></dl><dl class='fore'><dt>木结构防护</dt><dd></dd></dl></div>
<div class='subitem'  name="gzwgc"><dl class='fore'><dt>烟囱</dt><dd></dd></dl><dl class='fore'><dt>水塔</dt><dd></dd></dl><dl class='fore'><dt>水池</dt><dd></dd></dl><dl class='fore'><dt>油罐</dt><dd></dd></dl><dl class='fore'><dt>冷却塔</dt><dd></dd></dl><dl class='fore'><dt>电视塔</dt><dd></dd></dl><dl class='fore'><dt>筒仓</dt><dd></dd></dl></div>
<div class='subitem'  name="zjcgc"><dl class='fore'><dt>混凝土灌注桩</dt><dd><p><span>泥浆护壁成孔灌注桩</span>正循环钻孔灌注桩 | 反循环钻孔灌注桩 | 冲击成孔灌注桩 | 旋挖成孔灌注桩 | 潜水钻孔灌注桩</p><p><span>套管护壁灌注桩</span>锤击沉管灌注桩 | 振动、振动冲击沉管灌注桩 | 内夯沉管灌注桩 | 贝诺托灌注桩</p><p><span>干作业成孔灌注桩</span>钻孔扩底灌注桩 | 人工挖孔灌注桩 | 长螺旋钻孔灌注桩 | 短螺旋钻孔灌注桩 | 机动洛阳铲成孔灌注桩</p><p>长螺旋钻孔压灌桩 | 钻孔挤扩多支盘桩 | 灌注桩后注浆 | 水下混凝土灌注 | 大直径混凝土灌注桩</p></dd></dl><dl class='fore'><dt>预制桩和钢桩</dt><dd><p><span>混凝土预制桩</span>混凝土预制桩制作 | 预制桩起吊、运输和堆放 | 混凝土预制桩接桩 | 混凝土预制桩沉桩</p><p><span>钢桩</span>钢桩制作 | 钢桩焊接 | 钢桩运输和堆放 | 钢桩沉桩</p><p>先张法预应力管桩 | 静力压桩</p></dd></dl><dl class='fore'><dt>复合桩基础</dt><dd><p><span>逆作复合桩基</span>逆作复合桩基设计 | 逆作复合桩基施工 | 逆作复合桩基检测</p></dd></dl></div>
<div class='subitem'  name="hntgc"><dl class='fore'><dt>普通混凝土</dt><dd><p><span>普通混凝土原材料</span>水泥 | 砂 | 石 | 混凝土用水</p><p><span>混凝土掺和料</span>粉煤灰 | 沸石粉 | 矿渣粉 | 其他掺和料</p><p><span>混凝土外加剂</span>减水剂 | 引气剂 | 缓凝剂 | 早强剂 | 防冻剂 | 膨胀剂 | 泵送剂 | 速凝剂 | 防水剂</p><p><span>普通混凝土配合比设计</span>普通混凝土配制强度确定 | 普通混凝土配合比计算 | 普通混凝土配合比试配、调整与确定 | 泵送混凝土配合比设计</p><p>普通混凝土搅拌 | 控制碱骨料反应</p></dd></dl><dl class='fore'><dt>特殊混凝土</dt><dd><p><span>高强混凝土</span>高强混凝土材料 | 高强混凝土配合比 | 高强混凝土搅拌 | 高强混凝土施工</p><p><span>轻骨料混凝土</span>轻骨料混凝土材料 | 轻骨料混凝土配合比 | 轻骨料混凝土搅拌 | 轻骨料混凝土施工</p><p><span>补偿收缩混凝土</span>补偿收缩混凝土材料 | 补偿收缩混凝土配合比 | 补偿收缩混凝土搅拌 | 补偿收缩混凝土施工</p><p><span>纤维混凝土</span>纤维混凝土材料 | 纤维混凝土配合比 | 纤维混凝土搅拌 | 纤维混凝土施工</p><p><span>再生骨料混凝土</span>再生骨料混凝土材料 | 再生骨料混凝土配合比 | 再生骨料混凝土搅拌 | 再生骨料混凝土施工</p><p><span>海砂混凝土</span>海砂混凝土材料 | 海砂混凝土配合比 | 海砂混凝土搅拌 | 海砂混凝土施工</p><p><span>人工砂混凝土</span>人工砂混凝土材料 | 人工砂混凝土配合比 | 人工砂混凝土搅拌 | 人工砂混凝土施工</p><p><span>自密实混凝土</span>自密实混凝土材料 | 自密实混凝土配合比 | 自密实混凝土搅拌 | 自密实混凝土施工</p><p><span>抗冻混凝土</span>抗冻混凝土材料 | 抗冻混凝土配合比 | 抗冻混凝土搅拌 | 抗冻混凝土施工</p><p>其他特殊混凝土</p></dd></dl><dl class='fore'><dt>预拌混凝土</dt><dd></dd></dl><dl class='fore'><dt>混凝土运输</dt><dd><p>混凝土水平运输 | 混凝土垂直运输 | 混凝土泵送输送 | 混凝土布料</p></dd></dl><dl class='fore'><dt>混凝土浇筑与振捣</dt><dd><p>混凝土浇筑 | 混凝土振捣 | 现浇混凝土结构外观 | 特殊部位浇筑与振捣</p></dd></dl><dl class='fore'><dt>混凝土泵送</dt><dd><p>混凝土泵送方案设计 | 泵送设备安装与泵送 | 泵送混凝土浇筑</p></dd></dl><dl class='fore'><dt>混凝土施工缝</dt><dd><p>施工缝 | 后浇带 | 变形缝</p></dd></dl><dl class='fore'><dt>混凝土养护</dt><dd><p>自然养护 | 加热养护 | 其他养护</p></dd></dl><dl class='fore'><dt>混凝土试件制作与养护</dt><dd></dd></dl><dl class='fore'><dt>大体积混凝土</dt><dd><p>大体积混凝土材料 | 大体积混凝土配合比 | 大体积混凝土搅拌与运输 | 大体积混凝土浇筑 | 大体积混凝土养护 | 大体积混凝土测温 | 大体积混凝土特殊季节施工 | 大体积混凝土裂缝控制</p></dd></dl><dl class='fore'><dt>清水混凝土</dt><dd><p>清水混凝土原材料 | 清水混凝土配合比设计 | 清水混凝土制备与运输 | 清水混凝土浇筑 | 清水混凝土养护 | 清水混凝土表面处理 | 清水混凝土特殊季节施工</p></dd></dl><dl class='fore'><dt>混凝土工程特殊季节施工</dt><dd><p>冬期施工 | 雨期施工 | 高温环境施工</p></dd></dl></div>
<div class='subitem'  name="jsjgc"><dl class='fore'><dt>扣件式钢管脚手架</dt><dd><p>扣件式钢管脚手架构配件 | 扣件式钢管脚手架荷载计算 | 扣件式钢管脚手架设计计算 | 扣件式钢管脚手架构造 | 扣件式钢管脚手架施工 | 扣件式钢管脚手架检查与验收</p></dd></dl><dl class='fore'><dt>碗扣式钢管脚手架</dt><dd><p>碗扣式钢管脚手架构配件 | 碗扣式钢管脚手架荷载计算 | 碗扣式钢管脚手架设计计算 | 碗扣式钢管脚手架构造 | 碗扣式钢管脚手架施工 | 碗扣式钢管脚手架检查与验收</p></dd></dl><dl class='fore'><dt>门式钢管脚手架</dt><dd><p>门式钢管脚手架构配件 | 门式钢管脚手架荷载计算 | 门式钢管脚手架设计计算 | 门式钢管脚手架构造 | 门式钢管脚手架施工 | 门式钢管脚手架检查与验收</p></dd></dl><dl class='fore'><dt>盘口式脚手架</dt><dd><p>承插型盘口式脚手架构配件 | 承插型盘口式脚手架荷载计算 | 承插型盘口式脚手架构造 | 承插型盘口式脚手架搭设与拆除 | 承插型盘口式脚手架检查与验收</p></dd></dl><dl class='fore'><dt>木、竹脚手架</dt><dd><p>木、竹脚手架构配件 | 木、竹脚手架荷载计算 | 木、竹脚手架设计计算 | 木、竹脚手架构造 | 木、竹脚手架施工 | 木、竹脚手架检查与验收</p></dd></dl><dl class='fore'><dt>升降式脚手架</dt><dd><p>升降式脚手架构配件 | 升降式脚手架荷载计算 | 升降式脚手架设计计算 | 升降式脚手架构造 | 升降式脚手架安全装置 | 升降式脚手架安装施工 | 升降式脚手架的升降及使用 | 升降式脚手架验收 | 升降式脚手架拆除</p></dd></dl><dl class='fore'><dt>液压升降脚手架</dt><dd><p><span>液压升降脚手架安全装置</span>防坠落装置 | 防倾斜装置 | 荷载控制或同步控制装置</p><p><span>液压升降脚手架安装、升降、使用及拆除</span>安装 | 升降 | 使用 | 拆除</p><p>液压升降脚手架荷载计算 | 液压升降脚手架设计及计算 | 液压升降脚手架升降装置 | 液压升降脚手架检查与验收</p></dd></dl><dl class='fore'><dt>悬挑式脚手架</dt><dd></dd></dl><dl class='fore'><dt>外挂防护架</dt><dd><p>外挂防护架荷载计算 | 外挂防护架设计计算 | 外挂防护架构造 | 外挂防护架安装施工 | 外挂防护架提升 | 外挂防护架验收 | 外挂防护架拆除</p></dd></dl><dl class='fore'><dt>吊篮脚手架</dt><dd><p>吊篮脚手架荷载计算 | 吊篮脚手架设计计算 | 吊篮脚手架构造 | 吊篮脚手架安装、使用 | 吊篮脚手架验收 | 吊篮脚手架拆除</p></dd></dl><dl class='fore'><dt>移动式脚手架</dt><dd></dd></dl><dl class='fore'><dt>卸料平台</dt><dd></dd></dl><dl class='fore'><dt>模板支架及其他</dt><dd></dd></dl></div>
<div class='subitem'  name="dxgcfs"><dl class='fore'><dt>地下工程混凝土结构主体防水</dt><dd><p>防水混凝土 | 水泥砂浆防水层 | 卷材防水层 | 涂料防水层 | 塑料板防水层 | 金属防水层 | 膨润土防水材料防水层 | 地下工程种植顶板防水</p></dd></dl><dl class='fore'><dt>地下工程混凝土结构细部构造防水</dt><dd><p>变形缝防水构造 | 后浇带防水构造 | 穿墙管（盒）防水构造 | 埋设件防水构造 | 预留通道接头防水构造 | 桩头防水构造 | 孔口防水构造 | 坑、池防水构造 | 施工缝防水构造</p></dd></dl><dl class='fore'><dt>地下工程排水</dt><dd><p>渗排水、盲沟排水 | 隧道、坑道排水 | 塑料排水板排水</p></dd></dl><dl class='fore'><dt>注浆防水</dt><dd><p>预注浆、后注浆防水 | 结构裂缝注浆防水</p></dd></dl><dl class='fore'><dt>特殊施工法结构防水</dt><dd><p>锚喷支护结构防水 | 地下连续墙结构防水 | 盾构隧道结构防水 | 沉井结构防水 | 逆筑结构防水</p></dd></dl><dl class='fore'><dt>地下工程渗漏治理</dt><dd><p>方案设计 | 治理材料 | 治理施工</p></dd></dl></div>
<div class='subitem'  name="jzdmgc"><dl class='fore'><dt>基层铺设</dt><dd><p>基土 | 灰土垫层 | 砂垫层和砂石垫层 | 碎石垫层和碎砖垫层 | 三合土垫层和四合土垫层 | 炉渣垫层 | 水泥混凝土垫层及陶粒混凝土垫层 | 地面工程找平层 | 地面工程隔离层 | 地面工程填充层 | 地面工程绝热层</p></dd></dl><dl class='fore'><dt>整体面层铺设</dt><dd><p>水泥混凝土面层 | 水泥砂浆面层 | 水磨石面层 | 硬化耐磨面层 | 防油渗面层 | 不发火（防爆）面层 | 自流平面层 | 涂料面层 | 塑胶面层 | 地面辐射供暖的整体面层</p></dd></dl><dl class='fore'><dt>板块面层铺设</dt><dd><p>砖面层 | 大理石面层和花岗石面层 | 预制板块面层 | 料石面层 | 塑料板面层 | 活动地板面层 | 金属板面层 | 地毯面层 | 地面辐射供暖的板块面层</p></dd></dl><dl class='fore'><dt>木、竹面层铺设</dt><dd><p>实木地板、实木集成地板、竹地板面层 | 实木复合地板面层 | 浸渍纸层压木质地板面层 | 软木类地板面层 | 地面辐射供暖的地板面层</p></dd></dl><dl class='fore'><dt>地面防水</dt><dd></dd></dl><dl class='fore'><dt>地面监测</dt><dd></dd></dl></div>
<div class='subitem'  name="jzmqgc"><dl class='fore'><dt>幕墙工程材料</dt><dd><p>幕墙工程铝合金材料 | 幕墙工程钢材 | 幕墙工程玻璃 | 幕墙工程石材 | 幕墙工程金属板材 | 幕墙工程人造板材 | 建筑密封材料 | 硅酮结构密封胶</p></dd></dl><dl class='fore'><dt>玻璃幕墙工程</dt><dd><p><span>玻璃幕墙工程设计</span>玻璃幕墙建筑设计 | 玻璃幕墙结构设计 | 框支承玻璃幕墙结构 | 全玻璃幕墙结构 | 点支承玻璃幕墙结构</p><p><span>玻璃幕墙加工制作</span>玻璃幕墙铝型材加工制作 | 玻璃幕墙钢构件加工制作 | 玻璃加工制作 | 明框幕墙组件加工制作 | 隐框幕墙组件加工制作 | 单元式幕墙组件加工制作</p><p><span>玻璃幕墙安装</span>构件式玻璃幕墙安装 | 单元式玻璃幕墙安装 | 点支承玻璃幕墙安装 | 全玻璃幕墙安装</p><p><span>玻璃幕墙保养和维修</span>玻璃幕墙检查与维修 | 玻璃幕墙清洗</p></dd></dl><dl class='fore'><dt>石材幕墙工程</dt><dd><p><span>石材幕墙工程设计</span>石材幕墙性能与构造 | 石材幕墙结构设计</p><p><span>石材幕墙加工制作</span>石材幕墙构件加工制作 | 石板加工制作</p><p><span>石材幕墙保护与维修</span>石材幕墙保护和清洗 | 石材幕墙保养与维修</p><p>石材幕墙安装</p></dd></dl><dl class='fore'><dt>金属幕墙工程</dt><dd><p><span>金属幕墙工程设计</span>金属幕墙性能与构造 | 金属幕墙结构设计</p><p><span>金属幕墙加工制作</span>金属幕墙构件加工制作 | 金属板加工制作</p><p><span>金属幕墙保护与维修</span>金属幕墙保护和清洗 | 金属幕墙保养与维修</p><p>金属幕墙安装</p></dd></dl><dl class='fore'><dt>人造板幕墙工程</dt><dd><p>陶瓷板幕墙 | 合成树脂幕墙 | 铝塑复合板幕墙</p></dd></dl><dl class='fore'><dt>光伏幕墙工程</dt><dd></dd></dl><dl class='fore'><dt>双层幕墙</dt><dd></dd></dl><dl class='fore'><dt>单层索网幕墙</dt><dd></dd></dl></div>
<div class='subitem'  name="jzdqgc"><dl class='fore'><dt>架空线路及杆上电气设备安装</dt><dd><p><span>架空线路安装</span>电杆组立 | 架空线路架设 | 拉线安装</p><p>杆上电气设备安装 | 户外电缆敷设 | 接户线安装 | 室外电气调试与试运行</p></dd></dl><dl class='fore'><dt>变配电设备安装</dt><dd><p><span>变压器、箱式变电所安装</span>变压器安装 | 箱式变电所安装</p><p><span>成套配电设备安装</span>成套配电柜、控制柜（屏、台） | 动力、照明配电箱（盘）</p><p>变配电设备调试和试运行</p></dd></dl><dl class='fore'><dt>备用和不间断电源安装</dt><dd><p>柴油发电机组安装 | 不间断电源安装</p></dd></dl><dl class='fore'><dt>电气动力安装</dt><dd><p>低压电动机、电加热器及电动执行机构检查接线 | 低压电气动力设备试验和试运行</p></dd></dl><dl class='fore'><dt>供电干线安装</dt><dd><p><span>母线安装</span>裸母线安装 | 封闭母线安装 | 插接式母线安装</p><p>电缆桥架安装和桥架内电缆敷设 | 电缆沟内和电缆竖井内电缆敷设 | 电缆导管安装和管内电缆敷设 | 直埋电缆敷设 | 电缆的其他方式敷设 | 电缆附件安装 | 电缆头制作、接线和线路绝缘 | 防火阻燃及封堵 | 供电干线调试和试运行 | 电缆支架安装</p></dd></dl><dl class='fore'><dt>1kV以下配电线路安装</dt><dd><p><span>电线导管、电缆导管和线槽敷设</span>金属管敷设 | 塑料管敷设 | 线槽敷设</p><p>塑料护套线敷设 | 电线、电缆穿管和线槽敷线 | 槽板配线 | 钢索配线 | 导线连接和线路电气试验</p></dd></dl><dl class='fore'><dt>低压电器设备安装</dt><dd><p>接触器安装 | 继电器安装 | 低压熔断器安装 | 漏电保护器安装 | 启动器安装 | 电容器安装 | 低压开关安装 | 低压断路器安装</p></dd></dl><dl class='fore'><dt>电气照明装置安装</dt><dd><p>普通灯具安装 | 专用灯具安装 | 景观照明、庭院路灯安装 | 开关、插座、风扇安装 | 建筑物照明通电试运行</p></dd></dl><dl class='fore'><dt>防雷及接地安装</dt><dd><p><span>接闪器安装</span>避雷针安装 | 避雷带安装</p><p>接地装置安装 | 避雷引下线和变配电室接地干线敷设 | 建筑物等电位联结</p></dd></dl></div>
<div class='subitem'  name="jzjngc"><dl class='fore'><dt>围护系统保温节能</dt><dd><p>墙体节能 | 幕墙节能 | 门窗节能 | 屋面节能 | 地面节能</p></dd></dl><dl class='fore'><dt>设备及管网节能</dt><dd><p>采暖节能 | 通风与空调设备节能 | 空调与采暖系统冷热源及管网节能</p></dd></dl><dl class='fore'><dt>电气动力节能</dt><dd><p>配电节能 | 照明节能</p></dd></dl><dl class='fore'><dt>新能源与可再生能源利用</dt><dd></dd></dl><dl class='fore'><dt>监测与控制节能</dt><dd><p>控制系统节能 | 监测系统节能</p></dd></dl><dl class='fore'><dt>建筑节能工程现场检验</dt><dd><p>围护结构现场实体检验 | 系统节能性能检验</p></dd></dl></div>
<div class='subitem'  name="znjzgc"><dl class='fore'><dt>综合管线</dt><dd></dd></dl><dl class='fore'><dt>综合布线系统</dt><dd><p>缆线敷设和终接 | 机柜、机架、配线架安装 | 信息插座和光缆芯线终端安装 | 综合布线系统测试</p></dd></dl><dl class='fore'><dt>通信网络系统</dt><dd><p>通信系统 | 卫星及有线电视系统 | 广播系统 | 视频会议系统 | 通信网络系统测试</p></dd></dl><dl class='fore'><dt>计算机网络系统</dt><dd><p>信息平台及办公自动化应用软件 | 网络安全系统 | 计算机网络系统测试</p></dd></dl><dl class='fore'><dt>建筑设备监控系统</dt><dd><p>空调与通风系统 | 变配电系统 | 照明系统 | 给水排水系统 | 热源和热交换系统 | 冷冻和冷却系统 | 电梯和自动扶梯系统 | 中央管理工作站与操作分站 | 子系统通信接口 | 建筑设备监控系统测试</p></dd></dl><dl class='fore'><dt>火灾报警及消防联动系统</dt><dd><p>火灾和可燃气体探测系统 | 火灾报警控制系统 | 消防联动系统 | 火灾报警及消防联动系统测试</p></dd></dl><dl class='fore'><dt>会议系统与信息导航系统</dt><dd><p>会议系统 | 信息导航系统 | 会议及信息导航系统测试</p></dd></dl><dl class='fore'><dt>专业应用系统</dt><dd><p>专业应用系统施工 | 专业应用系统测试</p></dd></dl><dl class='fore'><dt>安全防范系统</dt><dd><p>电视监控系统 | 入侵报警系统 | 巡更系统 | 出入口控制(门禁)系统 | 停车管理系统 | 智能卡应用系统 | 安全防范系统测试</p></dd></dl><dl class='fore'><dt>智能化集成系统</dt><dd><p>集成系统网络 | 实时数据库 | 信息安全 | 功能接口 | 智能化集成系统测试</p></dd></dl><dl class='fore'><dt>电源与接地</dt><dd><p>智能建筑电源 | 智能建筑防雷及接地 | 智能建筑电源与接地系统测试</p></dd></dl><dl class='fore'><dt>计算机机房工程</dt><dd><p>路由交换系统 | 服务器系统 | 空间环境 | 室内空调环境 | 视觉照明环境 | 电磁环境 | 计算机机房工程系统测试</p></dd></dl><dl class='fore'><dt>住宅（小区）智能化系统</dt><dd><p>住宅（小区）火灾自动报警及消防联动系统 | 住宅（小区）安全防范系统 | 住宅（小区）物业管理系统 | 智能家庭信息平台 | 住宅（小区）智能化系统测试</p></dd></dl></div>
<div class='subitem'  name="ctjggc"><dl class='fore'><dt>砌体结构工程材料</dt><dd></dd></dl><dl class='fore'><dt>砌体结构构造</dt><dd><p>砌体结构构造要求 | 圈梁、过梁、墙梁及挑梁 | 配筋砖砌体构件 | 配筋砌块砌体构件 | 砌体结构抗震构造</p></dd></dl><dl class='fore'><dt>砌筑砂浆</dt><dd><p><span>预拌砂浆</span>干混砂浆 | 湿拌砂浆</p><p>砌筑砂浆材料 | 砌筑砂浆配合比设计 | 砂浆搅拌</p></dd></dl><dl class='fore'><dt>砖砌体工程施工</dt><dd><p><span>普通砖砌体工程</span>普通砖砌体构造 | 普通砖砌体抗震构造 | 普通砖砌体施工</p><p><span>多孔砖砌体工程</span>多孔砖砌体构造 | 多孔砖砌体抗震构造 | 多孔砖砌体施工</p><p>砖砌体工程材料 | 空心砖砌体工程</p></dd></dl><dl class='fore'><dt>砌块砌体工程施工</dt><dd><p><span>混凝土小型空心砌块砌体工程</span>混凝土小型空心砌块砌体构造 | 混凝土小型空心砌块砌体抗震构造 | 混凝土小型空心砌块砌体施工</p><p><span>中型混凝土空心砌块砌体工程</span>中型砌块砌体构造 | 中型砌块砌体抗震构造 | 中型砌块砌体施工</p><p><span>蒸压加气混凝土砌块砌体工程</span>加气混凝土砌块砌体构造 | 加气混凝土砌块砌体施工</p><p><span>石膏砌块砌体工程</span>石膏砌块材料 | 石膏砌块砌体构造 | 石膏砌块砌体施工</p><p><span>植物纤维砌块砌体工程</span>植物纤维砌块材料 | 植物纤维砌块砌体构造 | 植物纤维砌块砌体施工</p><p>砌块砌体工程材料 | 粉煤灰砌块砌体工程</p></dd></dl><dl class='fore'><dt>石砌体工程施工</dt><dd><p>石砌体工程原材料 | 毛石砌体工程 | 料石砌体工程</p></dd></dl><dl class='fore'><dt>配筋砖砌体工程施工</dt><dd><p>构造柱和砖组合 | 网状配筋砌体 | 配筋砌块砌体</p></dd></dl><dl class='fore'><dt>填充墙砌体工程施工</dt><dd></dd></dl><dl class='fore'><dt>夹心复合墙工程施工</dt><dd></dd></dl><dl class='fore'><dt>其他砌体工程施工</dt><dd></dd></dl><dl class='fore'><dt>砌体工程特殊季节施工</dt><dd></dd></dl></div>
<div class='subitem'  name="jsjggc"><dl class='fore'><dt>钢结构工程</dt><dd><p><span>钢结构施工阶段设计</span>钢结构工程施工阶段结构分析 | 钢结构预变形 | 钢结构工程施工详图设计</p><p><span>钢结构工程材料</span>钢材 | 焊接材料 | 紧固件 | 钢铸件、锚具和销轴 | 涂装材料 | 材料存储</p><p><span>钢结构焊接</span>焊接连接构造设计 | 焊接从业人员 | 焊接工艺评定 | 焊接工艺 | 焊接接头 | 焊接补强、加固与返修 | 焊钉（栓钉）焊接</p><p><span>紧固件连</span>连接件加工及摩擦面处理 | 钢结构普通紧固件连接 | 钢结构高强度螺栓连接</p><p><span>钢零件及部件加工</span>放样、号料 | 切割 | 矫正和成型 | 边缘加工 | 制孔 | 管、球加工 | 铸钢节点加工 | 索节点加工</p><p><span>钢构件组装及加工</span>钢构件部件拼接 | 钢构件组装 | 钢构件端部加工 | 钢构件矫正</p><p><span>钢构件预拼装</span>实体预拼装 | 模拟预拼装</p><p><span>钢结构安装</span>起重和吊装 | 基础和支承面 | 构件安装和校正 | 单层钢结构安装 | 多层、高层钢结构安装 | 大跨度空间钢结构安装 | 高耸钢结构安装</p><p><span>压型金属板</span>压型钢板构造 | 压型金属板制作 | 压型金属板安装</p><p><span>钢结构涂装</span>钢结构表面处理 | 钢结构防腐涂料涂装 | 钢结构防火涂料涂装</p><p>钢结构施工监测</p></dd></dl><dl class='fore'><dt>冷弯薄壁型钢结构工程</dt><dd><p><span>冷弯薄壁型钢结构屋架</span>屋架计算 | 屋架构造</p><p><span>冷弯薄壁型钢结构刚架</span>刚架计算 | 刚架构造</p><p><span>冷弯薄壁型钢结构施工</span>冷弯薄壁型钢结构制作、安装 | 冷弯薄壁型钢结构防腐</p><p><span>低层冷弯薄壁型钢建筑</span>低层冷弯薄壁型钢建筑材料 | 低层冷弯薄壁型钢构件和连接 | 低层冷弯薄壁型钢楼盖 | 低层冷弯薄壁型钢墙体 | 低层冷弯薄壁型钢屋盖 | 低层冷弯薄壁型钢结构施工 | 保温、隔热和防潮、防火</p><p>冷弯薄壁型钢结构材料 | 冷弯薄壁型钢结构连接构造 | 冷弯薄壁型钢结构檩条构造 | 冷弯薄壁型钢结构墙梁构造</p></dd></dl><dl class='fore'><dt>轻型钢结构工程</dt><dd><p>轻型钢结构材料 | 轻型钢结构节点构造 | 轻型钢结构防护 | 轻型钢结构施工</p></dd></dl><dl class='fore'><dt>高层建筑钢结构工程</dt><dd><p>高层建筑钢结构材料 | 高层建筑钢结构节点 | 高层钢结构制作 | 高层钢结构安装 | 高层建筑钢结构防火</p></dd></dl><dl class='fore'><dt>预应力钢结构工程</dt><dd><p><span>预应力钢结构材料和锚具</span>预应力钢结构索材 | 预应力钢结构锚具</p><p><span>预应力钢结构构造</span>预应力拉杆 | 预应力压杆 | 预应力实腹梁 | 预应力桁架 | 预应力拱架 | 预应力吊挂结构 | 预应力立体桁架 | 预应力网架 | 预应力网壳</p><p><span>预应力钢结构节点</span>张拉节点 | 锚固节点 | 转折节点 | 索杆连接节点 | 拉索交叉节点</p><p><span>预应力钢结构施工</span>预应力钢结构安装 | 预应力张拉</p><p><span>预应力钢结构防护</span>预应力钢结构防腐 | 预应力钢结构防火</p><p>预应力钢结构施工监测</p></dd></dl><dl class='fore'><dt>网架结构工程</dt><dd><p><span>网格结构杆件和节点</span>杆件 | 焊接空心球节点 | 螺栓球节点 | 嵌入式毂节点 | 铸钢节点 | 销轴式节点 | 组合结构节点 | 支座节点</p><p><span>空间网格结构施工</span>制作与拼装 | 高空散装法施工 | 分条或分块安装法施工 | 滑移法施工 | 整体吊装法施工 | 整体提升法施工 | 整体顶升法施工 | 折叠展开式整体提升法施工 | 组合空间网格结构施工</p></dd></dl><dl class='fore'><dt>铝合金结构工程</dt><dd><p><span>铝合金结构原材料</span>铝合金结构材料 | 铝合金焊接材料 | 铝合金结构标准紧固件 | 铝合金结构螺栓球 | 铝合金结构其他材料</p><p><span>铝合金紧固件连接工程</span>铝合金普通紧固件连接 | 铝合金高强度螺栓连接</p><p><span>铝合金零部件加工工程</span>铝合金零部件切割 | 铝合金零部件边缘加工 | 铝合金球、毂加工 | 铝合金制孔 | 铝合金零部件槽、豁、榫加工</p><p><span>铝合金构件组装工程</span>铝合金构件组装 | 铝合金构件端部铣平及安装焊缝坡口</p><p><span>铝合金框架结构安装工程</span>铝合金框架结构安装支承面 | 铝合金框架结构总拼和安装</p><p><span>铝合金防腐处理</span>铝合金阳极氧化 | 铝合金涂装 | 铝合金隔离防腐</p><p>铝合金焊接工程 | 铝合金构件预拼装工程 | 铝合金空间网格结构安装工程</p></dd></dl><dl class='fore'><dt>索膜结构工程</dt><dd><p><span>膜结构工程材料</span>膜材 | 拉索和锚具</p><p><span>膜结构连接节点</span>膜材连接 | 膜材与构件连接 | 钢索及端部连接 | 拉索锚锭 | 空气支承膜结构构造</p><p><span>膜结构安装</span>钢构件、拉索安装 | 膜单元安装 | 预张力施加</p><p>膜结构工程制作 | 维护和保养</p></dd></dl><dl class='fore'><dt>特殊季节施工</dt><dd></dd></dl></div>
<div class='subitem'  name="fsssgc"></div>
<div class='subitem'  name="djyjcgc"><dl class='fore'><dt>土方工程</dt><dd><p><span>场地平整</span>场地 | 场内道路</p><p><span>土方开挖</span>人工挖土 | 机械挖土</p><p><span>土方回填</span>人工回填 | 机械回填</p><p><span>特殊土施工</span>软土施工 | 湿陷性黄土施工 | 膨胀土施工 | 红黏土施工 | 盐渍土施工</p><p><span>土石方爆破</span>起爆方法 | 露天爆破 | 控制爆破 | 水下爆破 | 冻土爆破 | 沟槽爆破 | 爆破工程监测</p></dd></dl><dl class='fore'><dt>基坑工程</dt><dd><p><span>板桩围护墙</span>混凝土板桩围护墙 | 钢板桩围护墙</p><p><span>地下连续墙</span>地下连续墙导墙施工 | 泥浆制备 | 地下连续墙成槽施工 | 地下连续墙钢筋笼制作安装 | 地下连续墙水下混凝土浇筑</p><p><span>预制连续墙</span>预制构件 | 预制连续墙成槽施工 | 预制墙吊装</p><p><span>咬合桩围护墙</span>咬合桩钢筋笼制作安装 | 咬合切割 | 咬合桩导墙施工</p><p><span>内支撑系统</span>钢支撑 | 钢筋混凝土支撑</p><p><span>复合土钉墙</span>复合土钉墙施工勘察 | 复合土钉墙设计 | 复合土钉墙施工 | 复合土钉墙施工监测</p><p><span>逆作法基坑支护</span>逆作法基坑支护岩土勘察 | 逆作法基坑支护设计 | 逆作法基坑支护施工 | 逆作法基坑支护现场监测</p><p><span>沉井</span>沉井制作 | 沉井施工</p><p><span>沉箱</span>沉箱制作 | 沉箱施工</p><p><span>特殊性土基坑工程</span>膨胀土基坑 | 冻胀土基坑 | 软土基坑 | 盐渍土基坑 | 其他基坑</p><p>灌注桩排桩围护墙 | 水泥土重力式挡墙 | 型钢水泥土搅拌墙 | 土钉墙 | 土层锚杆 | 基槽 | 基坑工程监测</p></dd></dl><dl class='fore'><dt>地基处理</dt><dd><p><span>换填地基</span>素土地基 | 灰土地基 | 砂和砂石地基 | 土工合成材料 | 粉煤灰地基 | 其他材料地基</p><p><span>预压地基</span>真空预压 | 堆载预压 | 堆载－真空联合预压</p><p><span>压实、夯实地基</span>压实地基 | 夯实地基 | 强夯地基 | 强夯置换地基</p><p><span>挤密地基</span>沉管挤密地基 | 冲击挤密地基 | 夯扩挤密地基 | 振冲地基 | 振动沉管挤密</p><p><span>复合地基</span>砂石桩复合地基 | 水泥土搅拌桩复合地基 | 旋喷桩复合地基 | 土桩和灰土桩复合地基 | 夯实水泥土桩复合地基 | 水泥粉煤灰碎石桩（CFG）复合地基 | 柱锤冲扩桩复合地基 | 多桩型复合地基 | 混凝土大直径管桩复合地基 | 劲性复合桩地基</p><p><span>注浆地基</span>水泥注浆 | 硅化注浆 | 碱液注浆 | 高压喷射注浆地基</p><p><span>微型桩地基</span>树根桩 | 静压桩 | 注浆钢管桩</p><p><span>特殊地基处理</span>膨胀土地基处理 | 冻胀土地基处理 | 湿陷性地基处理 | 山区地基处理</p></dd></dl><dl class='fore'><dt>基础工程</dt><dd><p><span>无筋扩展基础</span>混凝土基础 | 砖基础 | 毛石基础 | 灰土基础 | 三合土基础</p><p><span>钢筋混凝土扩展基础</span>墙下条形基础 | 柱下独立基础</p><p><span>高层建筑基础</span>平板式筏形基础 | 梁板式筏形基础 | 箱形基础</p><p>柱下条形基础 | 岩石锚杆基础</p></dd></dl><dl class='fore'><dt>边坡工程</dt><dd><p><span>锚杆（索）</span>锚杆（索）工程原材料 | 锚杆（索）工程设计计算 | 锚杆（索）工程构造 | 锚杆（索）工程施工</p><p><span>锚杆（索）挡墙</span>锚杆（索）挡墙工程设计计算 | 锚杆（索）挡墙工程构造 | 锚杆（索）挡墙工程施工</p><p><span>岩石锚喷支护</span>岩石锚喷支护工程设计计算 | 岩石锚喷支护工程构造 | 岩石锚喷支护工程施工</p><p><span>重力式挡墙</span>重力式挡墙工程设计计算 | 重力式挡墙工程构造 | 重力式挡墙工程施工</p><p><span>悬臂式和扶壁式挡墙</span>悬臂式和扶壁式挡墙工程设计计算 | 悬臂式和扶壁式挡墙工程构造 | 悬臂式和扶壁式挡墙工程施工</p><p><span>桩板式挡墙</span>桩板式挡墙设计计算 | 桩板式挡墙工程构造 | 桩板式挡墙工程施工</p><p><span>坡率法放坡</span>坡率法放坡设计计算 | 坡率法放坡构造设计 | 坡率法放坡施工</p><p><span>坡面防护与绿化</span>坡面工程防护 | 植物防护与绿化 | 坡面防护与绿化施工</p><p><span>边坡工程排水系统</span>坡面排水 | 地下排水 | 边坡排水施工</p><p><span>坡顶有重要建筑物的边坡工程</span>坡顶有重要建筑物边坡设计计算 | 坡顶有重要建筑物边坡构造 | 坡顶有重要建筑物边坡施工</p><p>工程滑坡防治 | 边坡工程监测</p></dd></dl><dl class='fore'><dt>地下水控制</dt><dd><p><span>降水</span>轻型井点降水 | 喷射井点降水 | 电渗井点降水 | 真空管井降水 | 辐射井降水</p><p>排水 | 截水、隔水 | 地下水回灌</p></dd></dl><dl class='fore'><dt>地基基础季节性施工</dt><dd><p>地基与基础工程雨期施工 | 地基与基础工程冬期施工</p></dd></dl></div>
<div class='subitem'  name="zpsjggc"><dl class='fore'><dt>装配式结构施工验算</dt><dd></dd></dl><dl class='fore'><dt>混凝土构件制作</dt><dd></dd></dl><dl class='fore'><dt>构件运输、堆放与拼装</dt><dd></dd></dl><dl class='fore'><dt>混凝土构件安装与连接</dt><dd></dd></dl><dl class='fore'><dt>预应力混凝土装配式结构</dt><dd><p>构件制作 | 装配施工</p></dd></dl></div>
<div class='subitem'  name="tfyktgc"><dl class='fore'><dt>风管制作</dt><dd><p>金属风管制作 | 非金属风管制作 | 复合材料风管制作 | 防火风管制作 | 净化空调风管制作</p></dd></dl><dl class='fore'><dt>风管部件与消声器制作</dt><dd></dd></dl><dl class='fore'><dt>风管系统安装</dt><dd><p>金属风管安装 | 非金属风管安装 | 风管部件安装</p></dd></dl><dl class='fore'><dt>通风与空调设备安装</dt><dd><p>通风机安装 | 空调机组安装 | 除尘器与排污设备安装 | 过滤器安装 | 净化空调设备安装 | 消声设备安装</p></dd></dl><dl class='fore'><dt>空调制冷系统安装</dt><dd><p>制冷机组安装 | 制冷剂管道及配件安装 | 制冷附属设备安装</p></dd></dl><dl class='fore'><dt>空调水系统管道和设备安装</dt><dd><p>管道冷热（媒）水系统安装 | 空调系统阀门及部件安装 | 冷却塔安装 | 空调系统水泵及附属设备安装 | 空调系统水箱及其他设备安装</p></dd></dl><dl class='fore'><dt>通风与空调系统防腐与绝热</dt><dd><p>通风与空调管道和设备防腐 | 通风与空调管道和设备绝热</p></dd></dl><dl class='fore'><dt>通风与空调系统调试</dt><dd></dd></dl><dl class='fore'><dt>综合效能测定与调整</dt><dd></dd></dl></div>
<div class='subitem'  name="jzzszxgc"><dl class='fore'><dt>抹灰工程</dt><dd><p>一般抹灰工程 | 装饰抹灰工程 | 清水砌体勾缝工程 | 特种抹灰工程 | 保温层抹灰工程</p></dd></dl><dl class='fore'><dt>门窗工程</dt><dd><p>木门窗制作与安装 | 金属门窗安装 | 塑料门窗安装 | 特种门窗安装 | 门窗玻璃安装</p></dd></dl><dl class='fore'><dt>吊顶工程</dt><dd><p><span>吊顶工程材料</span>龙骨 | 饰面板</p><p><span>龙骨安装工程</span>明龙骨安装 | 暗龙骨安装</p><p>石膏板类吊顶工程 | 矿棉板类吊顶工程 | 玻璃吊顶工程 | 金属吊顶工程 | 花栅类吊顶工程</p></dd></dl><dl class='fore'><dt>轻质隔墙工程</dt><dd><p><span>板材隔墙工程</span>复合轻质墙板 | 石膏板 | 钢丝网水泥板 | 轻型钢丝网架聚苯板 | 木质墙板</p><p><span>骨架隔墙工程</span>隔墙骨架安装 | 墙面板安装</p><p>活动隔墙工程 | 玻璃隔墙工程 | 石膏砌块隔墙工程</p></dd></dl><dl class='fore'><dt>饰面板（砖）工程</dt><dd><p><span>饰面板安装工程</span>玻璃饰面 | 金属板饰面 | 石材饰面 | 塑料板饰面 | 陶瓷板饰面 | 木板饰面</p><p><span>饰面砖粘贴工程</span>陶瓷面砖饰面 | 玻璃砖饰面</p></dd></dl><dl class='fore'><dt>涂饰工程</dt><dd><p>水性涂料涂饰工程 | 溶剂型涂料涂饰工程 | 美术涂料涂饰工程 | 彩色喷涂涂饰 | 细木制品涂饰</p></dd></dl><dl class='fore'><dt>裱糊与软包</dt><dd><p>裱糊工程 | 软包工程</p></dd></dl><dl class='fore'><dt>外墙防水工程</dt><dd><p>外墙防水工程材料 | 外墙防水工程设计 | 外墙防水工程施工</p></dd></dl><dl class='fore'><dt>装饰工程细部</dt><dd><p>橱柜制作与安装 | 窗帘盒、窗台板和散热器罩制作安装 | 门窗套制作与安装 | 护栏和扶手制作与安装 | 花饰制作与安装 | 装饰线、踢脚线制作与安装 | 五金件安装</p></dd></dl><dl class='fore'><dt>室内环境污染控制</dt><dd></dd></dl><dl class='fore'><dt>装饰工程特殊季节施工</dt><dd></dd></dl></div>
<div class='subitem'  name="yylhntgc"><dl class='fore'><dt>预应力工程材料</dt><dd><p>预应力筋 | 预应力锚具、夹具和连接器 | 制孔管材 | 灌浆水泥</p></dd></dl><dl class='fore'><dt>预应力施工计算</dt><dd><p>预应力筋下料长度 | 预应力筋张拉力 | 预应力损失 | 锚固区受压承载力 | 预应力筋张拉伸长值</p></dd></dl><dl class='fore'><dt>预应力筋制作安装</dt><dd><p>预应力筋制作 | 预应力筋孔道留设 | 预应力筋安装</p></dd></dl><dl class='fore'><dt>预应力筋张拉和放张</dt><dd><p>预应力筋张拉 | 预应力筋放张</p></dd></dl><dl class='fore'><dt>灌浆及封锚</dt><dd></dd></dl><dl class='fore'><dt>无粘结预应力</dt><dd><p>无粘结预应力材料 | 无粘结预应力制作及运输 | 无粘结预应力筋铺设 | 无粘结预应力筋张拉</p></dd></dl><dl class='fore'><dt>体外预应力</dt><dd></dd></dl></div>
<div class='subitem'  name="jzfhxfgc"><dl class='fore'><dt>建筑防火</dt><dd></dd></dl><dl class='fore'><dt>水灭火系统</dt><dd><p><span>水灭火系统供水设施安装</span>消防水泵安装 | 消防水箱安装和消防水池施工 | 消防气压给水设备和稳压泵安装 | 消防水泵接合器及室外消火栓安装 | 室内消火栓系统安装</p><p><span>水灭火系统管网及系统组件安装</span>水灭火系统管网安装 | 水灭火系统喷头安装 | 水灭火系统报警阀组安装 | 水灭火系统其他组件安装</p><p><span>自动喷水灭火系统试压及冲洗</span>自动喷水灭火系统水压试验 | 自动喷水灭火系统气压试验 | 自动喷水灭火系统冲洗</p><p><span>水喷雾灭火系统安装</span>水喷雾灭火系统设计 | 水喷雾灭火系统组件 | 水喷雾灭火系统给水 | 水喷雾系统操作与控制 | 水喷雾系统水力计算</p><p>自动喷水灭火系统调试 | 自动喷水灭火系统验收</p></dd></dl><dl class='fore'><dt>泡沫灭火系统</dt><dd><p>泡沫灭火系统材料进场检验 | 泡沫灭火系统施工 | 泡沫灭火系统调试 | 泡沫灭火系统验收 | 泡沫灭火系统维护管理</p></dd></dl><dl class='fore'><dt>固定消防炮灭火系统</dt><dd><p>固定消防炮灭火系统材料进场检验 | 固定消防炮灭火系统组件安装与施工 | 固定消防炮灭火系统电气安装与施工 | 固定消防炮灭火系统试压与冲洗 | 固定消防炮灭火系统调试 | 固定消防炮灭火系统验收 | 固定消防炮灭火系统维护管理</p></dd></dl><dl class='fore'><dt>气体灭火系统</dt><dd><p>气体灭火系统材料进场检验 | 气体灭火系统安装 | 气体灭火系统调试 | 气体灭火系统验收 | 气体灭火系统维护管理</p></dd></dl><dl class='fore'><dt>干粉灭火系统</dt><dd></dd></dl><dl class='fore'><dt>建筑灭火器配置</dt><dd><p>灭火器安装设置 | 灭火器配置验收 | 灭火器检查与维护</p></dd></dl></div>
<div class='subitem'  name="jspsjcrgc"><dl class='fore'><dt>室内给水系统</dt><dd><p><span>室内金属给水管道安装</span>室内给水铸铁管安装 | 室内给水钢管安装 | 室内给水不锈钢管安装 | 室内给水铜管安装</p><p><span>室内复合管给水管道安装</span>室内给水铝塑复合管安装 | 室内给水钢塑复合管安装 | 室内给水不锈钢塑料复合管安装</p><p><span>室内塑料给水管道安装</span>室内给水聚乙烯管道安装 | 室内给水聚丙烯管道安装</p><p><span>室内给水设备安装</span>水泵机组安装 | 水箱安装</p><p>室内给水管道防腐</p></dd></dl><dl class='fore'><dt>室内排水系统</dt><dd><p><span>室内金属排水管道安装</span>室内排水铸铁管安装 | 室内排水钢管安装</p><p>室内塑料排水管道安装 | 室内复合排水管道安装 | 室内雨水管道及配件安装 | 室内排水管道防腐</p></dd></dl><dl class='fore'><dt>室内热水供应系统</dt><dd><p>室内热水管道及配件安装 | 室内热水系统辅助设备安装 | 室内热水系统防腐与绝热</p></dd></dl><dl class='fore'><dt>卫生器具及配件安装</dt><dd><p>卫生器具安装 | 卫生器具给水配件安装 | 卫生器具排水管道安装</p></dd></dl><dl class='fore'><dt>室内采暖系统</dt><dd><p>室内采暖管道及配件安装 | 室内采暖辅助设备及散热器安装 | 金属辐射板安装 | 低温热水地板辐射采暖系统安装 | 热计量装置安装 | 系统水压试验及调试 | 室内采暖系统防腐与绝热</p></dd></dl><dl class='fore'><dt>室外给水管网</dt><dd><p><span>室外给水金属管道安装</span>室外给水铸铁管安装 | 室外给水钢管安装</p><p><span>室外给水管沟及井室</span>室外给水管网沟槽开挖 | 室外给水管网沟槽回填 | 室外给水附属构筑物施工</p><p>室外给水复合管道安装 | 室外给水塑料管道安装 | 室外给水管道附件安装 | 室外给水系统试验与冲洗 | 室外给水管网防腐</p></dd></dl><dl class='fore'><dt>室外排水管网</dt><dd><p><span>室外排水管道安装</span>铸铁管 | 钢筋混凝土管 | 塑料管</p><p><span>室外排水地下管道开槽法施工</span>下管、稳管 | 管道接口 | 管道基础 | 管道铺设</p><p><span>室外排水地下管道不开槽法施工</span>直接顶进 | 挖土与顶进 | 无沟敷设</p><p>室外排水管沟及井池 | 管道交叉处理 | 室外排水管网沟槽回填</p></dd></dl><dl class='fore'><dt>室外供热管网</dt><dd><p>室外供热管道及配件安装 | 室外供热系统水压试验及调试 | 室外供热系统绝热、防腐</p></dd></dl><dl class='fore'><dt>建筑中水系统及游泳池系统</dt><dd><p>建筑中水系统管道及辅助设备安装 | 游泳池水系统安装</p></dd></dl><dl class='fore'><dt>供热锅炉及辅助设备安装</dt><dd><p><span>锅炉安装</span>散装锅炉安装 | 整装、组装锅炉 | 锅炉筑炉 | 燃油、燃气锅炉</p><p>锅炉辅助设备及管道安装 | 锅炉安全附件安装 | 烘炉、煮炉和试运行 | 换热站安装 | 供热锅炉防腐与绝热</p></dd></dl><dl class='fore'><dt>太阳能热水系统</dt><dd><p>基座、支架 | 集热器 | 贮水箱 | 太阳能热水系统管道安装 | 辅助能源加热设备 | 电气与自动控制系统 | 太阳能热水系统水压试验与冲洗 | 太阳能热水系统调试 | 太阳能热水系统防腐与绝热</p></dd></dl><dl class='fore'><dt>地源热泵系统</dt><dd><p>地埋管换热系统 | 地下水换热系统 | 地表水换热系统 | 建筑物内系统 | 地源热泵系统整体运转、调试</p></dd></dl></div>
<div class='subitem'  name="kzjgjffgc"><dl class='fore'><dt>建筑防腐蚀工程</dt><dd></dd></dl><dl class='fore'><dt>修缮与改造</dt><dd></dd></dl><dl class='fore'><dt>建筑抗震</dt><dd></dd></dl><dl class='fore'><dt>结构加固</dt><dd><p>地基基础工程加固 | 砌体结构工程加固 | 混凝土结构工程加固 | 钢结构工程加固 | 其他结构工程加固</p></dd></dl><dl class='fore'><dt>结构检测</dt><dd></dd></dl></div>
<div class='subitem'  name="gyjzjsbazgc"></div> 
<div class='subitem'  name="gyhntzhjggc"><dl class='fore'><dt>型钢混凝土结构工程</dt><dd><p>型钢混凝土结构材料 | 型钢混凝土结构构造及深化设计 | 型钢混凝土柱 | 型钢混凝土组合梁 | 型钢混凝土剪力墙 | 型钢混凝土结构节点 | 型钢混凝土结构施工 | 钢与混凝土组合墙 | 钢与混凝土组合板</p></dd></dl><dl class='fore'><dt>钢管混凝土结构工程</dt><dd><p>钢管混凝土工程材料 | 钢管混凝土结构构造及深化设计 | 钢管混凝土构件现场拼装 | 钢管混凝土柱施工 | 钢管混凝土构件安装 | 柱梁连接</p></dd></dl></div>
</div>
      <div class="allsort">
        <div class="mt"><a href="#">其他</a></div>
        <div class="mc" style="height:1000;">
		
<div id="menufbfx">
<div class='item'><span><h3><b><a name="djyjcgc">地基基础</a> <a name="gzwgc" href="">桩基础</a> <a name="dxgcfs" href="">地下防水</a></b></h3></span><div class='i-mc'><div class='subitem'></div></div></div>
<div class='item'><span><h3><b><a name="gjgc" href="">钢筋</a> <a name="hntgc" href="">混凝土</a> <a name="mbgc" href="">模板</a> <a name="jsjgc" href="">脚手架</a></b></h3></span><div class='i-mc'><div class='subitem'></div></div></div>
<div class='item'><span><h3><b><a name="zpsjggc" href="">装配结构</a> <a name="yylhntgc" href="">预应力</a> <a name="jsjggc" href="">钢结构</a></b></h3></span><div class='i-mc'><div class='subitem'></div></div></div>
<div class='item'><span><h3><b><a name="gyhntzhjggc" href="">组合结构</a> <a name="ctjggc" href="">砌体</a> <a name="smgc" href="">屋面</a><a name="jzdmgc" href="">地面</a></b></h3></span><div class='i-mc'><div class='subitem'></div></div></div>
<div class='item'><span><h3><b><a name="jzzszxgc" href="">装饰</a> <a name="jzmqgc" href="">幕墙</a> <a name="jspsjcrgc" href="">给排水采暖</a> <a name="jzjngc" href="">节能</a></b></h3></span><div class='i-mc'><div class='subitem'></div></div></div>
<div class='item'><span><h3><b><a name="tfyktgc" href="">通风空调</a> <a name="jzdqgc" href="">电气</a> <a name="znjzgc" href="">智能</a> <a name="dtgc" href="">电梯</a></b></h3></span><div class='i-mc'><div class='subitem'></div></div></div>
<div class='item'><span><h3><b><a name="yylhntgc" href="">消防</a> <a name="kzjgjffgc" href="">抗震加固</a> <a name="rfgc" href="">人防</a> <a name="gzwgc" href="">构筑物</a></b></h3></span><div class='i-mc'><div class='subitem'></div></div></div>
<div class='item'><span><h3><b><a name="fsssgc" href="">附属设施</a> <a name="gyjzjsbazgc" href="">工业建筑</a> <a name="mjggc" href="">木结构</a></b></h3></span><div class='i-mc'><div class='subitem'></div></div></div>
 </div>
        </div>
      </div>
    <script type="text/javascript">
      $(".allsort .item").hoverForIE6({ delay: 150 });
    </script>
    <div style="float:left;width:600px;font-size:14px;line-height:36px;padding-top:50px;padding-left:20px;">
     安博中程企业培训多年来致力于android培训,软考培训,嵌入式,UI设计,重构,架构,软件测试,需求分析,软件开发,数据库,国防军工等IT高端技术的企业培训、企业内训,一直在行业内处于领先地位,所开企业内训或公开课如软考培训,android应用开发培训,软件工程培训,敏捷开发,嵌入式培训,项目管理及国防军工的电子产品设计和信息化培训等授课讲师均是知名专家,行业内领军人物,已成为中国高端培训第一品牌.
    </div>
</body>
</html>
