function setinitkey(s) {	
	var cur_key=document.getElementById("k").value;
	
	if( (isInitKey(cur_key)==true)  ) {
		if(s==0) { //��������
			document.getElementById("k").value="����Ҫ���������Źؼ��ʣ����硰2010���Ϻ������ᡱ"
			return ;
		}
		if(s==1) { //��������
			document.getElementById("k").value="����Ҫ�ҵ��������ݣ����硰�������ʩ��ͼ������Ҫ�����롰������"
			return ;
		}
		if(s==2) { //����ͼƬ��
			document.getElementById("k").value="����Ҫ�ҵĹ�����Ŀ���ƻ����ʦ���������硰��������ݡ�"
			return ;
		}
		if(s==3) { //������̳
			document.getElementById("k").value="����Ҫ�ҵ��������ݣ����硰���ּ��¹ʷ�����"
			return ;
		}
		if(s==4) { //�����ٿ�
			document.getElementById("k").value="����Ҫ�ҵ��������ƣ����硰���ӡ�����Ҫ�������⣬���硰ʲô�л��ӡ�"
			return ;
		}
		if(s==5) { //��������
			document.getElementById("k").value="����Ҫ�ҵĲ����������ݻ��߲������û���"
			return ;
		}
		if(s==6) { //����ְλ
			document.getElementById("k").value="����ְλ���ƣ����硰��Ŀ��������Ҫ��ְλ�����мӵ�����Ϣ"
			return ;
		}
		if(s==7) { //������Ʒ
			document.getElementById("k").value="����Ҫ�ҵĲ�Ʒ���ơ�Ʒ�����ƻ��߲�Ʒ����ͺţ����硰�ھ���ש������������ 5L��װ����"
			return ;
		}
		if(s==8) { //��������
			document.getElementById("k").value="����Ҫ�ҵ�Ʒ�����ƻ��߲�Ʒϵ�����ƣ����硰����ʿ��ζϵ�С���"
			return ;
		}
	}
}

function isInitKey(s) {
	
	if(s=="����Ҫ���������Źؼ��ʣ����硰2010���Ϻ������ᡱ")
		return true;
	
	
	if(s=="����Ҫ�ҵ��������ݣ����硰�������ʩ��ͼ������Ҫ�����롰������")
		return true;

	
	if(s=="����Ҫ�ҵĹ�����Ŀ���ƻ����ʦ���������硰��������ݡ�")
		return true;
	
	if(s=="����Ҫ�ҵ��������ݣ����硰���ּ��¹ʷ�����")
		return true;
	
	
	if(s=="����Ҫ�ҵ��������ƣ����硰���ӡ�����Ҫ�������⣬���硰ʲô�л��ӡ�")
		return true;
	
	if(s=="����Ҫ�ҵĲ����������ݻ��߲������û���")
		return true;
	
	if(s=="����ְλ���ƣ����硰��Ŀ��������Ҫ��ְλ�����мӵ�����Ϣ")
		return true;
	
	if(s=="����Ҫ�ҵĲ�Ʒ���ơ�Ʒ�����ƻ��߲�Ʒ����ͺţ����硰�ھ���ש������������ 5L��װ����")
		return true;
	
	if(s=="����Ҫ�ҵ�Ʒ�����ƻ��߲�Ʒϵ�����ƣ����硰����ʿ��ζϵ�С���")
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
		alert('�������ѯ�ؼ���');
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
	if(i_style==1) { //��������
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