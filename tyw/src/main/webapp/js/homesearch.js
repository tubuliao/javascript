function setinitkey(s) {	
	var cur_key=document.getElementById("k").value;
	
	if( (isInitKey(cur_key)==true)  ) {
		if(s==0) { //搜索新闻
			document.getElementById("k").value="输入要搜索的新闻关键词，例如“2010年上海世博会”"
			return ;
		}
		if(s==1) { //搜索资料
			document.getElementById("k").value="输入要找的资料内容，例如“三层别墅施工图”，不要简单输入“别墅”"
			return ;
		}
		if(s==2) { //搜索图片库
			document.getElementById("k").value="输入要找的工程项目名称或设计师姓名，例如“艺术博物馆”"
			return ;
		}
		if(s==3) { //搜索论坛
			document.getElementById("k").value="输入要找的贴子内容，例如“脚手架事故分析”"
			return ;
		}
		if(s==4) { //搜索百科
			document.getElementById("k").value="输入要找的资料名称，例如“基坑”，不要输入问题，例如“什么叫基坑”"
			return ;
		}
		if(s==5) { //搜索博客
			document.getElementById("k").value="输入要找的博客文章内容或者博主的用户名"
			return ;
		}
		if(s==6) { //搜索职位
			document.getElementById("k").value="输入职位名称，例如“项目经理”，不要在职位名称中加地区信息"
			return ;
		}
		if(s==7) { //搜索产品
			document.getElementById("k").value="输入要找的产品名称、品牌名称或者产品规格型号，例如“冠军瓷砖”、“立邦漆 5L套装”等"
			return ;
		}
		if(s==8) { //搜索样本
			document.getElementById("k").value="输入要找的品牌名称或者产品系列名称，例如“多乐士净味系列”等"
			return ;
		}
	}
}

function isInitKey(s) {
	
	if(s=="输入要搜索的新闻关键词，例如“2010年上海世博会”")
		return true;
	
	
	if(s=="输入要找的资料内容，例如“三层别墅施工图”，不要简单输入“别墅”")
		return true;

	
	if(s=="输入要找的工程项目名称或设计师姓名，例如“艺术博物馆”")
		return true;
	
	if(s=="输入要找的贴子内容，例如“脚手架事故分析”")
		return true;
	
	
	if(s=="输入要找的资料名称，例如“基坑”，不要输入问题，例如“什么叫基坑”")
		return true;
	
	if(s=="输入要找的博客文章内容或者博主的用户名")
		return true;
	
	if(s=="输入职位名称，例如“项目经理”，不要在职位名称中加地区信息")
		return true;
	
	if(s=="输入要找的产品名称、品牌名称或者产品规格型号，例如“冠军瓷砖”、“立邦漆 5L套装”等")
		return true;
	
	if(s=="输入要找的品牌名称或者产品系列名称，例如“多乐士净味系列”等")
		return true;
	return false;
}

function chgsearchstyle(strdiv,intdiv,str_disply_class,str_hide_class,int_total) {
	
	for(ix=1;ix<=int_total;ix++) {
		if(ix==intdiv) {
			
			document.getElementById(strdiv+"title"+ix).className=str_disply_class;
			document.getElementById("s").value=intdiv-1;
			
			if((document.getElementById("k").value=='') || (isInitKey(document.getElementById("k").value))) {
				setinitkey(intdiv-1);
			} else {
				
				i_style=intdiv-1;
				///document.location.href=geturl(i_style,document.getElementById("k").value);
			}
			
		} else {
			if(document.getElementById(strdiv+"title"+ix).className!=str_hide_class) {
				document.getElementById(strdiv+"title"+ix).className=str_hide_class;
				
			}
		}
		
	}
}

function homesearch() {
	var cur_key,str_key,i_len,i_ix,i_style;
	i_len=0;
	cur_key= document.getElementById("k").value;
	if(isInitKey(cur_key))
		cur_key='';
		
	
	for(i_ix=0;i_ix<cur_key.length;i_ix++) 
		if(cur_key.charAt(i_ix)!=' ') 
			i_len++;
			
	if(i_len==0) {
		alert('请输入查询关键词');
		return false;
	}
	else {
		i_style=document.getElementById("s").value;
		
		document.location.href=getnewsearchurl(i_style,cur_key);
	}
	return false;		
}

function clearinit() {
	var cur_key;

	cur_key= document.getElementById("k").value;
	if(isInitKey(cur_key)==true)
		document.getElementById("k").value='';
}
function getnewsearchurl(i_style,str_k) {	
	str_qry_key= chgstrnew(str_k);
	if(i_style==0) {
		return "http://s.tianyou.com/search.php?key="+str_qry_key+"&topic=news"
	}
	if(i_style==1) { //搜索资料
		return "http://s.tianyou.com/search.php?key="+str_qry_key+"&topic=info"
	}
	if(i_style==2) {
		return "http://s.tianyou.com/search.php?key="+str_qry_key+"&topic=project";
	}
	if(i_style==3) {
		return "http://s.tianyou.com/search.php?key="+str_qry_key+"&topic=bbs"
	}
	if(i_style==4) {
		return "http://s.tianyou.com/search.php?key="+str_qry_key+"&topic=wiki"
	}
	if(i_style==5) {
		return "http://s.tianyou.com/search.php?key="+str_qry_key+"&topic=blog";
	}
	
	if(i_style==6) {
		return "http://job.tianyou.com/users/searchresult/"+ str_k +"/jobs/0/0/0/0/0/0.html"
	}
	if(i_style==7) {
		return "http://s.tianyou.com/search.php?key="+str_qry_key+"&topic=cpk"
	}
	if(i_style==8) {
		return "http://s.tianyou.com/search.php?key="+str_qry_key+"&topic=cpbook"
	}


}

function chgstrnew(src_str) {
	var search_str="";
	search_str= encodeURIComponent(src_str);
	return search_str;
}

function searchtimer(in_ix) {
	timer_ix=in_ix;
	if(searchtimer_id) clearTimeout(searchtimer_id);
	searchtimer_id=window.setTimeout("if(timerflag==true) dochg()",1000);
}
function dochg() {
	chgsearchstyle('homesearch',timer_ix,'searchtitledivsel','searchtitlediv',9);
}