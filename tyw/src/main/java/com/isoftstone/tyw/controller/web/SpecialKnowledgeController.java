/**
 * Copyright 2008 - 2012 Simcore.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.isoftstone.tyw.controller.web;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.isoftstone.tyw.dto.info.BaseSearchDto;
import com.isoftstone.tyw.dto.info.ESPage;
import com.isoftstone.tyw.entity.auths.Role;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.BaseSourceModule;
import com.isoftstone.tyw.entity.info.BaseTag;
import com.isoftstone.tyw.entity.info.BaseTitleModule;
import com.isoftstone.tyw.entity.info.BookDirModule;
import com.isoftstone.tyw.entity.info.PdTag;
import com.isoftstone.tyw.entity.info.Segment;
import com.isoftstone.tyw.entity.info.SegmentDir;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.ElasticsearchService;
import com.isoftstone.tyw.service.SpecialService;
import com.isoftstone.tyw.util.DateManager;
import com.isoftstone.tyw.util.Pager;

/**
 * @author zhangyq
 * 前台展示类
 * 知识专题--规范条文
 * 
 * 
 */
@Controller
public class SpecialKnowledgeController {

	@Autowired
	private CommonService commonService;
	@Autowired
	private ElasticsearchService elasticsearchService;
	@Autowired 
	private SpecialService specialService;

	/**
	 * 知识专题--表格首页
	 * @return
	 */
	@RequestMapping(value = "/specialKnowledge/web/Form", method = RequestMethod.GET)
	public String specialFromPage(){
		return "/page/specialKnowledge/web/formIndex";
	}
	
	/**
	 * 知识专题--条文首页--最新版
	 * @return
	 */
	@RequestMapping(value = "/specialKnowledge/web/ClauseNew/{type}", method = RequestMethod.GET)
	public String specialClausePageNew(Model model,HttpServletRequest request,@PathVariable("type")int type){
		//List<Tag> tags = specialService.listTag();//28个分部分项
    	Long code=10000000000000000l;//分部分项标签的code
    	long pdCode = 40501000000000000L;//规范条文分部分项
    	String tagName="规范条文";
		model.addAttribute("Source",this.getPdTag(code, pdCode, 1));
		model.addAttribute("tagName",tagName);
		
		return "/page/specialKnowledge/web/clauseIndex";
	}
	
	/**
	 * 知识专题--条文首页
	 * @return
	 */
	@RequestMapping(value = "/specialKnowledge/web/Clause/{type}", method = RequestMethod.GET)
	public String specialClausePage(Model model,HttpServletRequest request,@PathVariable("type")int type){
		String qString ="";
		switch(type){
			case 1:
				qString = "Tags:核心条文";
				break;
			case 2:
				qString = "Tags:强制性条文";
				break;
		}
		List<Object> GBSource = this.listSource(qString+" AND Source:GB");//国标来源
		List<Object> GBknowledge = this.listAllKnowledge(GBSource,qString,model);//国标来源下的知识
		model.addAttribute("GBSource",GBSource);
		model.addAttribute("GBknowledge",GBknowledge);
		
		List<Object> HBSource = this.listSource(qString+" AND Source:CJJ");//行标来源
		List<Object> HBknowledge = this.listAllKnowledge(HBSource,qString,model);//行标来源下的知识
		model.addAttribute("HBSource",HBSource);
		model.addAttribute("HBknowledge",HBknowledge);
		
		List<Object> DBSource = this.listSource(qString+" AND Source:DB");//地标来源
		List<Object> DBknowledge = this.listAllKnowledge(DBSource,qString,model);//地标来源下的知识
		model.addAttribute("DBSource",DBSource);
		model.addAttribute("DBknowledge",DBknowledge);
		
		List<Object> TJSource = this.listSource(qString+" AND Source:CECS");//推荐来源
		List<Object> TJknowledge = this.listAllKnowledge(TJSource,qString,model);//推荐来源下的知识
		model.addAttribute("TJSource",TJSource);
		model.addAttribute("TJknowledge",TJknowledge);
		
		List<Object> JGJSource = this.listSource(qString+" AND Source:JGJ");//建筑行业标准来源
		List<Object> JGJknowledge = this.listAllKnowledge(JGJSource,qString,model);//推荐来源下的知识
		model.addAttribute("JGJSource",JGJSource);
		model.addAttribute("JGJknowledge",JGJknowledge);
		
		//KnowledgeController kc = new KnowledgeController();
		List<BaseSearchDto> listForm=relatedKnoledge("《北京市建筑工程资料表格填写范例与指南》","form");//前十条表格相关的
		model.addAttribute("listForm",listForm);
		return "/page/specialKnowledge/web/clauseIndex1";
	}
	
	/**
	 * 知识专题--表格首页
	 * 走搜索引擎
	 * @return
	 */
	@RequestMapping(value = "/specialKnowledge/web/Form/{type}", method = RequestMethod.GET)
	public String specialFormPage(Model model,HttpServletRequest request,@PathVariable("type")int type){
		String qString ="";
		switch(type){
			case 1:
				qString = "《吉林省建筑工程资料表格填写范例与指南》";
				break;
			case 2:
				qString = "Tags:强制性条文";
				break;
		}
		List<Tag> SGtags = specialService.listTag();//施工28个分部分项
		List<Object> SGknowledge = this.listAllKnowledge(qString,SGtags,model);//施工表格下的知识
		model.addAttribute("GBSource",SGtags);
		model.addAttribute("GBknowledge",SGknowledge);
		
		List<Tag> AQtags = specialService.listTag();//安全28个分部分项
		List<Object> AQknowledge = this.listAllKnowledge(qString,AQtags,model);//施工表格下的知识
		model.addAttribute("HBSource",AQtags);
		model.addAttribute("HBknowledge",AQknowledge);
		
		List<Tag> JLtags = specialService.listTag();//监理28个分部分项
		List<Object> JLknowledge = this.listAllKnowledge(qString,JLtags,model);//施工表格下的知识
		model.addAttribute("DBSource",JLtags);
		model.addAttribute("DBknowledge",JLknowledge);
		
		//KnowledgeController kc = new KnowledgeController();
		List<BaseSearchDto> listForm=relatedKnoledge("《北京市建筑工程资料表格填写范例与指南》","form");//前十条表格相关的
		model.addAttribute("listForm",listForm);
		return "/page/specialKnowledge/web/formIndex";
	}
	
	//相关来源top10
    public  List<BaseSearchDto> relatedKnoledge(String source,String resultType){
        //String resultType=resultType(typeId);
        List<BaseSearchDto> baseResult=new ArrayList<BaseSearchDto>();
        try {
            ESPage esPage=elasticsearchService.queryByStringBuilder("tyw", resultType,"AND",source,"","",10,1);
            List<Map<String,Object>> listMap=esPage.getHighlighterResult();
            BaseSearchDto base=null;
            for(Map<String,Object> mapObj:listMap){
                base=new BaseSearchDto();
                 for(Entry<String, Object> m:mapObj.entrySet()){
                    if(m.getKey().equals("Source")){
                        base.setSource(m.getValue().toString());
                    }if(m.getKey().equals("Type")){
                        base.setInfoType(Integer.valueOf(m.getValue().toString()));
                    }if(m.getKey().equals("Id")){
                        base.setId(m.getValue().toString());
                    }if(m.getKey().equals("Title")){
                        KnowledgeController kc=new KnowledgeController();
                        base.setTitle(kc.subStr(m.getValue().toString().replace("<em>", "").replace("</em>", ""),70));
                    } 
                }
                 baseResult.add(base);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baseResult;
    }
	
	@RequestMapping(value="/specialKnowledge/web/sourceList/{tag}",method=RequestMethod.GET)
	public String sourceList(@PathVariable("tag")String tag,Model model){
		
		model.addAttribute("source",this.listSource(tag));
		String type = tag.substring(tag.length()-2);
		
		if(type.equals("GB")){
			model.addAttribute("title","国家标准");
		}else if(type.equals("DB")){
			model.addAttribute("title","地方标准");
		}else if(type.equals("GJ")){//JGJ
			model.addAttribute("title","建筑行业标准");
		}else if(type.equals("JJ")){//CJJ
			model.addAttribute("title","行业标准");
		}else if(type.equals("CS")){//CECS
			model.addAttribute("title","推荐标准");
		}else{
			model.addAttribute("title","一般标准");
		}
		return "/page/specialKnowledge/web/clauseList";
		
	}
	
	/**
	 * 通过标签名称取出来源
	 * @param tags
	 * @return
	 */
	public  List<Object> listSource(String tags){
        
        List<Object> baseResult=new ArrayList<Object>();
        try {
            ESPage esPage=elasticsearchService.queryByStringBuilder("tyw", "","AND",tags,"","", 5000,1);
            List<Map<String,Object>> listMap=esPage.getHighlighterResult();
            ////System.out.println(listMap);
            BaseSearchDto base=null;
            Set set = new HashSet();
            for(Map<String,Object> mapObj:listMap){
                base=new BaseSearchDto();
                 for(Entry<String, Object> m:mapObj.entrySet()){
                    if(m.getKey().equals("Source")){
                    	set.add(splitAndFilterString(m.getValue().toString(),100));
                    }
                }
                if(set.size()==25)break;
            }
            baseResult.addAll(set);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseResult;
    }
	
public  List<Object> listSourcetest(String tags,int pageSize,int pageNo,Model model){
        
        List<Object> baseResult=new ArrayList<Object>();
        try {
            ESPage esPage=elasticsearchService.queryByStringBuilder("tyw", "segment","AND",tags+" AND 核心条文 ","","", pageSize,pageNo);
            model.addAttribute("rowCount",esPage.getTotalElements());
            List<Map<String,Object>> listMap=esPage.getHighlighterResult();
            ////System.out.println(listMap);
            BaseSearchDto base=null;
            Set set = new HashSet();
            for(Map<String,Object> mapObj:listMap){
                base=new BaseSearchDto();
                 for(Entry<String, Object> m:mapObj.entrySet()){
                    if(m.getKey().equals("Source")){
                    	if(!m.getValue().toString().equals("")&&m.getValue().toString()!=null){
                    		set.add(splitAndFilterString(m.getValue().toString(),100));
                    	}
                    }
                }
                if(set.size()==25)break;
            }
            baseResult.addAll(set);
 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseResult;
    }

public  List<Object> listSourceBySource(String source,String tags,int pageSize,int pageNo,Model model){
    
    List<Object> baseResult=new ArrayList<Object>();
    tags=tags + " AND "+source;
    try {
        ESPage esPage=elasticsearchService.queryByStringBuilder("tyw", "segment","AND",tags+" AND 核心条文","","", pageSize,pageNo);
        model.addAttribute("rowCount",esPage.getTotalElements());
        List<Map<String,Object>> listMap=esPage.getHighlighterResult();
        ////System.out.println(listMap);
        BaseSearchDto base=null;
        Set set = new HashSet();
        for(Map<String,Object> mapObj:listMap){
            base=new BaseSearchDto();
             for(Entry<String, Object> m:mapObj.entrySet()){
                if(m.getKey().equals("Source")){
                	if(!m.getValue().toString().equals("")&&m.getValue().toString()!=null){
                		set.add(splitAndFilterString(m.getValue().toString(),100));
                	}
                }
            }
            if(set.size()==25)break;
        }
        baseResult.addAll(set);

    } catch (Exception e) {
        e.printStackTrace();
    }
    return baseResult;
}
	
	/**
	 * 通过来源名称取得知识列表
	 * @param source
	 * @return
	 */
	public  List<Object> listKnowledge(String source,int amount,Model model){
        
        List<Object> baseResult=new ArrayList<Object>();
        try {
            ESPage esPage=elasticsearchService.queryByStringBuilder("tyw", "",source, amount,1);
            model.addAttribute("showRowContent",esPage.getTotalElements());
            ////System.out.println(esPage.getHighlighterResult());
            List<Map<String,Object>> listMap=esPage.getHighlighterResult();
            BaseSearchDto base=null;
            for(Map<String,Object> mapObj:listMap){
                base=new BaseSearchDto();
                for(Entry<String, Object> m:mapObj.entrySet()){
                    if(m.getKey().equals("Title")){
                    	////System.out.println(m.getValue().toString());
                        base.setTitle(m.getValue().toString());
                    }if(m.getKey().equals("CreateDate")){
                    	Date date=DateManager.stringFormats(m.getValue().toString());
                        base.setCreateDate(date);
                    }if(m.getKey().equals("Type")){
                        base.setInfoType(Integer.valueOf(m.getValue().toString()));
                    }if(m.getKey().equals("Id")){
                        base.setId(m.getValue().toString());
                    }if(m.getKey().equals("SegItem")){
                        base.setSegItem(m.getValue().toString());
                    }
                }
                baseResult.add(base);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseResult;
    }
	
	/**
	 * 条文类
	 * 通过来源集合取出每个来源下的知识
	 * @param source
	 * @param quSource
	 * @return
	 */
	public List<Object> listAllKnowledge(List source,String quSource,Model model){
		List<Object> list = new ArrayList<Object>();
		Iterator it = source.iterator();
		while(it.hasNext()){
			List<Object> baseResult=new ArrayList<Object>();
			String s = (String)it.next();
			String qSource = quSource+"  AND Source:"+s+" ";
			baseResult = this.listKnowledge(qSource,15,model);
			list.add(baseResult);
		}
        return list;
    }
	
	/**
	 * 表格专题
	 * 通过分部分项标签列表、来源取出知识列表
	 * @param quSource
	 * @param tags
	 * @return
	 */
	public List<Object> listAllKnowledge(String quSource ,List tags,Model model){
		List<Object> list = new ArrayList<Object>();
		Iterator it = tags.iterator();
		while(it.hasNext()){
			List<Object> baseResult=new ArrayList<Object>();
			Tag t = (Tag)it.next();
			String query = "Tags:"+t.getName()+"  AND Source:"+quSource+" ";
			baseResult = this.listKnowledge(query,35,model);
			list.add(baseResult);
		}
        return list;
    }
	
	/** 
     * 删除input字符串中的html格式 
     *  
     * @param input 
     * @param length 
     * @return 
     */  
    public static String splitAndFilterString(String input, int length) {  
        if (input == null || input.trim().equals("")) {  
            return "";  
        }  
        // 去掉所有html元素,  
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(  
                "<[^>]*>", "");  
        str = str.replaceAll("[(/>)<]", "");  
        int len = str.length();  
        if (len <= length) {  
            return str;  
        } else {  
            str = str.substring(0, length);  
            str += "......";  
        }  
        return str;  
    }
    
    //根据28个分部分项，异步加载来源列表
    @RequestMapping(value = "/doSourceListajax", method = RequestMethod.POST)
    public String doSourceListajax(@PageableDefaults(value=10) Pageable pageable, Model model,	@RequestParam("pageNum") int pageNum,HttpServletRequest request) throws Exception {
    	String tags = request.getParameter("tag_name");	
    	String source = request.getParameter("source");
    	//System.out.println("source:"+source);
    	List<Object> se=null;
    	if(!"undefined".equals(source)&&!"".equals(source)&&null!=source){
    		se=this.listSourceBySource(source,tags,30,pageNum,model);
    		//se=this.listSourceBySource(source,tags,30,pageNum,model);
    	}else{
    		se=this.listSourcetest(tags,30,pageNum,model);
    	}
    	model.addAttribute("name",se);
	    return "/page/specialKnowledge/web/clauseIndex_source"; 
    }
    
    //根据28个分部分项，异步加载来源列表queryAndFilterByString
    @RequestMapping(value = "/doSourceListQueryajax", method = RequestMethod.POST)
    public String doSourceListQueryajax(@PageableDefaults(value=10) Pageable pageable, Model model,	@RequestParam("pageNum") int pageNum,HttpServletRequest request) throws Exception {
    	String tags = request.getParameter("tag_name");	//分部分项
    	String source = request.getParameter("source"); //来源
    	String title = request.getParameter("title");//标题
    	//System.out.println("source:"+source);
    	String filterString = "";
    	if(!"undefined".equals(tags)&&!"".equals(tags)&&null!=tags){
    		filterString+="(Tags:"+tags+")";
    	}else{
    		filterString+="(Tags:分部分项)";
    	}
    	if(!"undefined".equals(source)&&!"".equals(source)&&null!=source){
    		filterString+="AND(Source:"+source+")";
    	}
    	if(!"undefined".equals(title)&&!"".equals(title)&&null!=title){
    		filterString+="AND(Title:"+title+")";
    	}
    	filterString+="AND(Tags:核心条文)";
    	List<Object> baseResult=new ArrayList<Object>();
        try {
        	//"(Tags:模板工程)AND(Title:建筑工程大模板的安装)AND(Source:《地基与基础工程现场施工处理方法与技巧》)"
        	//ESPage esPage=elasticsearchService.queryAndFilterByString("tyw", "segment", "核心条文", filterString, "", "", 20, pageNum);
            ESPage esPage = elasticsearchService.getFacets("tyw", "segment", filterString, "", "Source.unsource", 20, pageNum);
        	//ESPage esPage = elasticsearchService.queryByTerm("tyw","segment", "Source", termValue, filterString, 20, pageNum);
            ////ESPage ep = elasticsearchService.queryByTerm("twy", "test", "Source", termValue, filterString, 20, pageNum)
            ////System.out.println(elasticsearchService.getFacets("tyw", "test","Tags:施工资料", null,"Source.untouched", 10,3).getTotalElements());
        	model.addAttribute("rowCount",esPage.getTotalElements());
            //System.out.println("esPage.getTotalElements():"+esPage.getTotalElements());
            List<Map<String,Object>> listMap=esPage.getHighlighterResult();
            BaseSearchDto base=null;
            Set set = new HashSet();
            for(Map<String,Object> mapObj:listMap){
                base=new BaseSearchDto();
                 for(Entry<String, Object> m:mapObj.entrySet()){
                    if(m.getKey().equals("Term")){
                    	if(!m.getValue().toString().equals("")&&m.getValue().toString()!=null){
                    		set.add(splitAndFilterString(m.getValue().toString(),100));
                    	}
                    }
                }
                if(set.size()==25)break;
            }
            baseResult.addAll(set);

        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	model.addAttribute("name",baseResult);
	    return "/page/specialKnowledge/web/clauseIndex_source"; 
    }
    
    //根据分部分项的code作为parent_code取出此标签下一层的标签，异步加载下一层标签列表
    @RequestMapping(value = "/doTagListajax", method = RequestMethod.GET)
    @ResponseBody
    public List<PdTag> doTagListajax(Model model,HttpServletRequest request) {
    	/*Long code=Long.parseLong(request.getParameter("code"));//取出点击标签的code
    	List<Tag> tag = specialService.getListAllTagByParentCode(code);//取出下一层的所有标签
	        return tag;*/ 
    	
    	Long code=Long.parseLong(request.getParameter("code"));//取出点击标签的code
    	String level=(String)request.getParameter("level");//取出点击标签的层级level
    	Long pdCode = Long.parseLong(request.getParameter("pdCode"));
    	//long pdCode = 40501000000000000l;//规范条文分部分项
    	return getPdTag(code,pdCode,Integer.parseInt(level));
    }
    
    /**
     * 频道里通过下面三个参数得到下级存在内容的标签
     * @param code
     * @param pdCode
     * @param level
     * @return
     */
    private List<PdTag> getPdTag(Long code,Long pdCode,Integer level){
    	List<PdTag> tag = new ArrayList<PdTag>();
    	List tags = specialService.getListAllTagByParentCodeAndPdCodeAndPLevel(code, pdCode,level);//取出下一层的所有标签
    	for(int i=0;i<tags.size();i++){
    		Object[] oj = (Object[]) tags.get(i);
    		
    		//Integer leaf = (Integer) oj[0];
    		java.lang.Byte bleaf = (java.lang.Byte) oj[0];
    		Integer leaf = bleaf.intValue();
    		
    		//Integer levels = (Integer) oj[1];
    		java.lang.Byte blevels = (java.lang.Byte) oj[1];
    		Integer levels = blevels.intValue(); 
    		
    		//Long codes = (Long) oj[2];
    		BigInteger bCodes = (BigInteger) oj[2];
    		Long codes = bCodes.longValue();
    		
    		String name = (String) oj[3];
    		
    		//Integer count = (Integer) oj[4];
    		BigInteger bcount = (BigInteger) oj[4];
    		Integer count = bcount.intValue();
    		//System.out.println("count:"+count);
    		
    		PdTag pdTag = new PdTag();
    		if(!"".equals(leaf)&&null!=leaf)
    		pdTag.setLeaf(leaf);
    		if(!"".equals(levels)&&null!=levels)
        		pdTag.setLevel(levels);
    		if(!"".equals(codes)&&null!=codes)
        		pdTag.setCode(codes);
    		if(!"".equals(name)&&null!=name)
        		pdTag.setName(name);
    		if(!"".equals(count)&&null!=count)
        		pdTag.setCount(count);
    		tag.add(pdTag);
    	}
	        return tag;
    }
    
  //根据来源名称，异步加载知识列表
    /*@RequestMapping(value = "/doKnowledgeListajax", method = RequestMethod.POST)
    public String doKnowledgeListajax(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) throws Exception {
    	String source = request.getParameter("source");	
        //ESPage esPage=elasticsearchService.queryByStringBuilder("tyw", "",source, 5,1);
    	List<Object> se= this.listKnowledge(source,14,model);
    	model.addAttribute("knowle",se);
    	
	        return "/page/specialKnowledge/web/clauseIndex_Knowledge"; 
    }*/
    
    /**
     * 根据来源异步加载切片（知识）列表
     * @param pageable
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doKnowledgeListajax", method = RequestMethod.POST)
    public String doKnowledgeListajax(@PageableDefaults(value=15) Pageable pageable, Model model,HttpServletRequest request) throws Exception {
    	String source = request.getParameter("source");	
    	/*List<Segment> page = specialService.getListBaseBySource(source);*/
    	List<SegmentDir> segDir = new ArrayList<SegmentDir>();
    	List segmentDir = specialService.getListBaseBySourceN(source);
    	for(int i=0;i<segmentDir.size();i++){
    		Object[] oj = (Object[]) segmentDir.get(i);
    		String id = (String) oj[0];
    		String segItem = (String) oj[1];
    		String title = (String) oj[2];
    		SegmentDir sd = new SegmentDir();
    		if(!"".equals(id)&&null!=id)
    		sd.setId(id);
    		if(!"".equals(segItem)&&null!=segItem)
        		sd.setSegItem(segItem);
    		if(!"".equals(title)&&null!=title)
        		sd.setTitle(title);
    		segDir.add(sd);
    	}
    	model.addAttribute("page",segDir);
    	
	        return "/page/specialKnowledge/web/clauseIndex_Knowledge"; 
    }
    
    public  List<Object> listKnowledgeByTitle(String source,String tags,String title,int pageSize,int pageNo,Model model){
        
        List<Object> baseResult=new ArrayList<Object>();
        String filterString = "(Tags:核心条文)";
        if(tags!=null&&!"".equals(tags)){
        	filterString +="AND(tags:"+tags+")";
        }
        try {
            //ESPage esPage=elasticsearchService.queryByStringBuilder("tyw", "segment","AND",tags+" AND 核心条文","","", pageSize,pageNo);
            ESPage esPage=elasticsearchService.queryByTerm("tyw", "test", "Source.untouched", source, filterString, pageSize, pageNo);
            ////System.out.println(es.queryByTerm("tyw","test", "Source.untouched", "《埋地钢质管道防腐保温层技术标准》GB/T50538-2010", "(Tags:核心条文)AND(Tags:湖北省)AND(Tags:地基与基础工程)", 10,1).getHighlighterResult());
            model.addAttribute("rowCount",esPage.getTotalElements());
            List<Map<String,Object>> listMap=esPage.getHighlighterResult();
            ////System.out.println(listMap);
            BaseSearchDto base=null;
            Set set = new HashSet();
            for(Map<String,Object> mapObj:listMap){
                base=new BaseSearchDto();
                 for(Entry<String, Object> m:mapObj.entrySet()){
                    if(m.getKey().equals("Source")){
                    	if(!m.getValue().toString().equals("")&&m.getValue().toString()!=null){
                    		set.add(splitAndFilterString(m.getValue().toString(),100));
                    	}
                    }
                }
                if(set.size()==25)break;
            }
            baseResult.addAll(set);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseResult;
    }
    
    /**
     * 根据28个分部分项，异步加载来源列表queryAndFilterByString
     * @param pageable
     * @param model
     * @param pageNum
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doSourceListQueryDBajax", method = RequestMethod.POST)
    public String doSourceListQueryDBajax(@PageableDefaults(value=10) Pageable pageable, Model model,	@RequestParam("pageNum") int pageNum,HttpServletRequest request) throws Exception {
    	String tags = request.getParameter("tag_name");	//分部分项
    	String source = request.getParameter("source"); //来源
    	String title = request.getParameter("title");//标题
    	String standard = request.getParameter("standard");//标题
    	//System.out.println("source:"+source);
    	String filterString = "";
    	if("undefined".equals(tags)||"".equals(tags)||null==tags){
    		tags="分部分项";
    	}
    	if("undefined".equals(source)||"".equals(source)||null==source){
    		source = "";
    	}else{
    		source="%"+source+"%";
    	}
    	if("undefined".equals(title)||"".equals(title)||null==title){
    		title = "";
    	}else{
    		title="%"+title+"%";
    	}
    	if("undefined".equals(standard)||"".equals(standard)||null==standard){
    		standard = "";
    	}else{
    		standard="%"+standard+"%";
    	}
    	List base = null;
    	List bt = null;
    	List<BaseSourceModule> baseSource = new ArrayList<BaseSourceModule>();
    	List<Base> baseTitle = new ArrayList<Base>();
        try {
        	Tag tag = specialService.getTagByName(tags);
        	int level = tag.getLevel();//等级
        	Long startCode = tag.getCode();
        	Long add = (long)Math.pow(100, 9-level)-1;
        	Long endCode =startCode+add;
        	//System.out.println("startCode:"+startCode);
        	//System.out.println("endCode:"+endCode);
        	//System.out.println("pageNum:"+pageNum);
        	//System.out.println("source:"+source);
        	//System.out.println("title:"+title);
        	//System.out.println("standard:"+standard);
        	if("".equals(source)&&"".equals(title)&&"".equals(standard)){
        		base = specialService.getBaseListByTags(startCode.toString(), endCode.toString());
        	}
        	if(!"".equals(source)&&"".equals(title)&&"".equals(standard)){//来源
        		base = specialService.getBaseListByTagsAndSource(startCode.toString(), endCode.toString(),source);
        	}
        	if("".equals(source)&&!"".equals(title)&&"".equals(standard)){//标题
        		base = specialService.getBaseListByTagsAndTitle(startCode.toString(), endCode.toString(),title);
        		//bt   = specialService.getTitleByTagsAndTitle(startCode.toString(), endCode.toString(), title);
        	}
        	if("".equals(source)&&"".equals(title)&&!"".equals(standard)){//标准
        		base = specialService.getBaseListByTagsAndSource(startCode.toString(), endCode.toString(),standard);
        	}
        	if(!"".equals(source)&&!"".equals(title)&&"".equals(standard)){//来源和标题
        		base = specialService.getBaseListByTagsAndTitleAndSource(startCode.toString(), endCode.toString(),title,source);
        		//bt   = specialService.getTitleByTagsAndTitleAndSource(startCode.toString(), endCode.toString(), title, source);
        	}
        	if(!"".equals(source)&&"".equals(title)&&!"".equals(standard)){//来源和标准
        		base = specialService.getBaseListByTagsAndStandardAndSource(startCode.toString(), endCode.toString(),standard,source);
        	}
        	if("".equals(source)&&!"".equals(title)&&!"".equals(standard)){//标题和标准
        		base = specialService.getBaseListByTagsAndTitleAndSource(startCode.toString(), endCode.toString(),title,standard);
        		//bt   = specialService.getTitleByTagsAndTitleAndSource(startCode.toString(), endCode.toString(), title, standard);
        	}
        	if(!"".equals(source)&&!"".equals(title)&&!"".equals(standard)){//来源和标题和标准
        		base = specialService.getBaseListByTagsAndTitleAndStandardAndSource(startCode.toString(), endCode.toString(),title,standard,source);
        		//bt   = specialService.getTitleByTagsAndTitleAndStandardAndSource(startCode.toString(), endCode.toString(), title, standard, source);
        	}
        	
        	//System.out.println(base.size());
        	for(int i=0;i<base.size();i++){
        		Object[] oj = (Object[]) base.get(i);
        		String id = (String) oj[0];
        		String sources = (String) oj[1];
        		BaseSourceModule bsm = new BaseSourceModule();
        		if(!"".equals(id)&&null!=id)
        		bsm.setId(id);
        		if(!"".equals(sources)&&null!=sources)
            		bsm.setSource(sources);
        		baseSource.add(bsm);
        	}
        	/*if(null!=bt){
        		for(int i = 0;i<bt.size();i++){
        			Object[] oj = (Object[]) bt.get(i);
        			String id = (String) oj[0];
        			String titles = (String) oj[1];
        			//BaseTitleModule btm = new BaseTitleModule();
            		if(!"".equals(id)&&null!=id){
            			Base b = new Base();
            			b = specialService.getBaseById(id);
            			baseTitle.add(b);
            		}
        		}
        	}*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    	
    	model.addAttribute("name",baseSource);
    	/*if(null!=baseTitle){
    		model.addAttribute("baseTitle",baseTitle);
    	}*/
	    return "/page/specialKnowledge/web/clauseIndex_source"; 
    }
    
    /**
     * 规范条文
     * 根据查询条件---涉及到标题的字段查询
     * @param model
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/doknowledgeListQueryDBajaxs", method = RequestMethod.POST)
    public String doKnowledgeListQueryDBajaxs(Model model,HttpServletRequest request) throws Exception {
    	//System.out.println("/doknowledgeListQueryDBajaxs");
    	String tags = request.getParameter("tag_name");	//分部分项
    	String source = request.getParameter("source"); //来源
    	String title = request.getParameter("title");//标题
    	String standard = request.getParameter("standard");//标题
    	//System.out.println("source:"+source);
    	String filterString = "";
    	if("undefined".equals(tags)||"".equals(tags)||null==tags){
    		tags="分部分项";
    	}
    	if("undefined".equals(source)||"".equals(source)||null==source){
    		source = "";
    	}else{
    		source="%"+source+"%";
    	}
    	if("undefined".equals(title)||"".equals(title)||null==title){
    		title = "";
    	}else{
    		title="%"+title+"%";
    	}
    	if("undefined".equals(standard)||"".equals(standard)||null==standard){
    		standard = "";
    	}else{
    		standard="%"+standard+"%";
    	}
    	List bt = null;
    	List<BookDirModule> baseSource = new ArrayList<BookDirModule>();
    	List<Base> baseTitle = new ArrayList<Base>();
        try {
        	Tag tag = specialService.getTagByName(tags);
        	int level = tag.getLevel();//等级
        	Long startCode = tag.getCode();
        	Long add = (long)Math.pow(100, 9-level)-1;
        	Long endCode =startCode+add;
        	//System.out.println("startCode:"+startCode);
        	//System.out.println("endCode:"+endCode);
        	//System.out.println("source:"+source);
        	//System.out.println("title:"+title);
        	//System.out.println("standard:"+standard);
        	/*if("".equals(source)&&"".equals(title)&&"".equals(standard)){
        		base = specialService.getBaseListByTags(startCode.toString(), endCode.toString());
        	}
        	if(!"".equals(source)&&"".equals(title)&&"".equals(standard)){//来源
        		base = specialService.getBaseListByTagsAndSource(startCode.toString(), endCode.toString(),source);
        	}*/
        	if("".equals(source)&&!"".equals(title)&&"".equals(standard)){//标题
        		//base = specialService.getBaseListByTagsAndTitle(startCode.toString(), endCode.toString(),title);
        		bt   = specialService.getTitleByTagsAndTitle(startCode.toString(), endCode.toString(), title);
        	}
        	/*if("".equals(source)&&"".equals(title)&&!"".equals(standard)){//标准
        		base = specialService.getBaseListByTagsAndSource(startCode.toString(), endCode.toString(),standard);
        	}*/
        	if(!"".equals(source)&&!"".equals(title)&&"".equals(standard)){//来源和标题
        		//base = specialService.getBaseListByTagsAndTitleAndSource(startCode.toString(), endCode.toString(),title,source);
        		bt   = specialService.getTitleByTagsAndTitleAndSource(startCode.toString(), endCode.toString(), title, source);
        	}
        	/*if(!"".equals(source)&&"".equals(title)&&!"".equals(standard)){//来源和标准
        		base = specialService.getBaseListByTagsAndStandardAndSource(startCode.toString(), endCode.toString(),standard,source);
        	}*/
        	if("".equals(source)&&!"".equals(title)&&!"".equals(standard)){//标题和标准
        		//base = specialService.getBaseListByTagsAndTitleAndSource(startCode.toString(), endCode.toString(),title,standard);
        		bt   = specialService.getTitleByTagsAndTitleAndSource(startCode.toString(), endCode.toString(), title, standard);
        	}
        	if(!"".equals(source)&&!"".equals(title)&&!"".equals(standard)){//来源和标题和标准
        		//base = specialService.getBaseListByTagsAndTitleAndStandardAndSource(startCode.toString(), endCode.toString(),title,standard,source);
        		bt   = specialService.getTitleByTagsAndTitleAndStandardAndSource(startCode.toString(), endCode.toString(), title, standard, source);
        	}
        	
        	/*//System.out.println(base.size());
        	for(int i=0;i<base.size();i++){
        		Object[] oj = (Object[]) base.get(i);
        		String id = (String) oj[0];
        		String sources = (String) oj[1];
        		BaseSourceModule bsm = new BaseSourceModule();
        		if(!"".equals(id)&&null!=id)
        		bsm.setId(id);
        		if(!"".equals(sources)&&null!=sources)
            		bsm.setSource(sources);
        		baseSource.add(bsm);
        	}*/
        	if(null!=bt){
        		for(int i = 0;i<bt.size();i++){
        			Object[] oj = (Object[]) bt.get(i);
        			String id = (String) oj[0];
        			String titles = (String) oj[1];
        			String segItem = (String) oj[2];
        			////System.out.println(id+":"+titles+":"+segItem);
        			BookDirModule bdm = new BookDirModule();
        			bdm.setId(id);
        			bdm.setSegItem(segItem);
        			bdm.setTitle(titles);
        			baseSource.add(bdm);
        		}
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    		model.addAttribute("baseSource",baseSource);
	    return "/page/specialKnowledge/web/clauseIndex_KnowledgeFilter"; 
    }

}
