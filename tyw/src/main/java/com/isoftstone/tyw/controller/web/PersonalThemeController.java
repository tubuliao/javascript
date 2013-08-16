package com.isoftstone.tyw.controller.web;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.Classification;
import com.isoftstone.tyw.entity.pub.PersonalTheme;
import com.isoftstone.tyw.entity.pub.ThemeDetail;
import com.isoftstone.tyw.service.ClassificationService;
import com.isoftstone.tyw.service.PersonalThemeService;

@Controller
public class PersonalThemeController {

    @Autowired
    private PersonalThemeService personalThemeService;
    
    @Autowired
	private ClassificationService classificationService ;

    @RequestMapping(value = "/theme/viewThemeDetail", method = RequestMethod.POST)
    @ResponseBody
    public List<ThemeDetail> viewThemeDetail(HttpServletRequest request,String themeId) {
    	List<ThemeDetail> detialist = personalThemeService.findThemeDetailList(themeId);
    	return detialist;
    }
    
    
    @RequestMapping(value = "/theme/getlastlytheme", method = RequestMethod.POST)
    @ResponseBody
    public List<PersonalTheme> getlastlytheme(HttpServletRequest request,String themeId) {
    	List<PersonalTheme> themelist = personalThemeService.findlastlyThemelist();
    	return themelist;
    }
    
    @RequestMapping(value = "/theme/deleteThemelist", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteThemeDetaillist(HttpServletRequest request) {
    	String ids = request.getParameter("ids");
    	String [] themeIds = ids.split(",");
    	boolean flag = false;
    	try{
    		personalThemeService.deleteThemes(themeIds);
    		flag = true;
    	}catch(Exception ex){
    		flag = false;
    	} 
    	return flag;
    }
    
    @RequestMapping(value = "/theme/publishTheme", method = RequestMethod.POST)
    @ResponseBody
    public boolean publishTheme(HttpServletRequest request) {
    	String ids = request.getParameter("ids");
    	String [] themeIds = ids.split(",");
    	boolean flag = false;
    	try{
    		personalThemeService.publishThemes(themeIds);
    		flag = true;
    	}catch(Exception ex){
    		flag = false;
    	} 
    	return flag;
    }
    
    @RequestMapping(value = "/theme/saveThemeDetail", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveThemeDetail(HttpServletRequest request) {
    	boolean flag = false;
    	String titles = request.getParameter("titles");
    	String types = request.getParameter("types");
    	String values = request.getParameter("values");
    	String themeId = request.getParameter("themeId");
    	
    	String [] type = types.split("@#@");
    	String [] title = titles.split("@#@");
    	String [] value =values.split("@#@");
    	List<ThemeDetail> detaillist = new ArrayList<ThemeDetail>();
    	for(int i = 0 ;i<type.length;i++){
    		if(StringUtils.equals("undefined",type[i])){
    			break;
    		}
    		ThemeDetail detail = new ThemeDetail();
    		detail.setCreateDate(new Date());
    		detail.setThemeId(themeId.trim());
    		detail.setTitle(title[i].trim());
    		detail.setValue(value[i].trim());
    		detail.setType(Integer.parseInt(type[i]));
    		detail.setSortNum(i);
    		detaillist.add(detail);
    	}
    	
    	try{
    		if(detaillist.size()!=(type.length-1)){
    			personalThemeService.deleteThemeDetail(themeId);
    			personalThemeService.saveThemeDetail(detaillist);
    			flag = true;
    		}else{
    			flag = false;
    		}
    		
    	}catch(Exception ex){
    		flag = false;
    	}finally{
    		return flag;
    	}
    }
    
    
    
    @RequestMapping(value = "/theme/findThemeDetailContentlist", method = RequestMethod.POST)
    @ResponseBody
    public Map findThemeDetailContentlist(HttpServletRequest request) {
    	String content = request.getParameter("content");
    	List<Base> list = new ArrayList<Base>();
    	if(StringUtils.isNotBlank(content)){
    		String ids [] = content.split(";");
    		int size = ids.length;
    		for(int i =0 ;i<size;i++){
    			if(StringUtils.isNotBlank(ids[i])){
    				Base base = personalThemeService.findBaseSingle(ids[i].trim());
    				list.add(base);
    			}
    		}
    	}
    	Map map = new HashMap();
    	map.put("result", list);
    	map.put("index",request.getParameter("sort"));
    	return map;
    }
    
    @RequestMapping(value = "/theme/getDetailList", method = RequestMethod.POST)
    @ResponseBody
    public List<Base> getDetailList(HttpServletRequest request,String detailId) {
    	List<Base> baselist = personalThemeService.findDetialListById(detailId);
    	return baselist;
    }
    
    
    @RequestMapping(value = "/person/goThemeDetail")
    public String goThemeDetail(HttpServletRequest request,String themeId,Model model) {
    	PersonalTheme personalTheme = personalThemeService.findPersonalThemeSingle(themeId);
    	model.addAttribute("personalTheme", personalTheme);
    	
    	HttpSession session = request.getSession() ;
		User user = (User)session.getAttribute("user") ;
		String userId = user.getId();
		// 用户自定义的收藏类型
		List<Classification> cList = classificationService.getClassificationListByUserId(userId) ;
		model.addAttribute("cList", cList);
		//获取当前自定义主题下有多少个detail
		List<ThemeDetail> detaillist = personalThemeService.findThemeDetailList(themeId);
		model.addAttribute("detaillist", detaillist);
		model.addAttribute("size", detaillist.size());
    	return "/page/front/personalThemeDetail";
    }
    
    @RequestMapping(value = "/theme/goThemeDetail")
    public String goThemeDetail(String themeId,Model model) {
    	PersonalTheme personalTheme = personalThemeService.findPersonalThemeSingle(themeId);
    	model.addAttribute("personalTheme", personalTheme);
    	return "/page/knowledge/personalTheme";
    }
    
    @RequestMapping(value = "/theme/saveMyTheme")
	@ResponseBody
	public boolean saveMyTheme(PersonalTheme personalTheme,HttpServletRequest request,Model model) {
	    HttpSession session = request.getSession();
	    User user = (User)session.getAttribute("user");
    	personalTheme.setStatus(0);
    	personalTheme.setCreateDate(new Date());
    	personalTheme.setUserId(user.getId());
    	PersonalTheme theme = personalThemeService.saveTheme(personalTheme);
    	if(StringUtils.isNotBlank(theme.getId())){
    		return true;
    	}else{
    		return false;
    	}
	}
    
    @RequestMapping(value = "/theme/deleteTheme")
	@ResponseBody
	public boolean deleteTheme(PersonalTheme personalTheme,HttpServletRequest request) {
    	String id = personalTheme.getId();
	    HttpSession session = request.getSession();
	    User user = (User)session.getAttribute("user");
    	 personalThemeService.deleteTheme(personalTheme);
    	 PersonalTheme theme = personalThemeService.findPersonalThemeSingle(id);
    	if(theme == null){
    		return true;
    	}else{
    		return false;
    	}
	}
    
    @RequestMapping(value = "/theme/findMyThemes")
	@ResponseBody
	public Map<String,Object> findMyThemes(HttpServletRequest request,Pageable pageable) {
	    HttpSession session = request.getSession();
	    User user = (User)session.getAttribute("user");
	    int pageSize = 10;
	    if(StringUtils.isNotBlank(request.getParameter("items_per_page"))){
	    	pageSize = Integer.parseInt(request.getParameter("items_per_page"));
	    }
	    int pageIndex = 0;
	    if(StringUtils.isNotBlank(request.getParameter("pageIndex"))){
	    	pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
	    }
	   List<PersonalTheme> list = personalThemeService.findPersonalThemeList(user.getId(), pageSize, pageIndex);
	   BigInteger total = personalThemeService.findPersonalThemeListCount(user.getId());
	    
	    Map<String,Object> map = new HashMap<String,Object>();
	    map.put("total",total);
	    map.put("result", list); 
        return map;
	}
    
}
