package com.isoftstone.tyw.controller.web;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.edu.EducationPackage;
import com.isoftstone.tyw.entity.edu.EducationSource;
import com.isoftstone.tyw.service.EduPackageService;
import com.isoftstone.tyw.service.EduSourceService;
import com.isoftstone.tyw.util.Pager;
import com.isoftstone.tyw.util.PropertiesReader;

@Controller
public class EduSourceController {
	
	@Autowired
	private EduSourceService eduSourceService; 
	@Autowired
	private EduPackageService eduPackageService;
	 //后台
    @RequestMapping(value = "/eduSourcelist", method = RequestMethod.GET)
    public String eduPagelist(HttpServletRequest request, Model model) {
        return "page/edu/eduSourcelist";
    }
    @RequestMapping(value = "/eduSource/data", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findAllEducationSourceBy(@PageableDefaults(value = 10) Pageable pageable, Model model,
            HttpServletRequest request, @RequestParam(value = "sourceName", required = false) String sourceName,
            @RequestParam(value = "sourceType", required = false) String sourceType,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "sourceYear", required = false) Integer sourceYear,
            @RequestParam(value = "postName", required = false) String postName) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Page<EducationSource> page = eduSourceService.findAllEducationSourceBy(sourceName, status, sourceType,
                    postName, sourceYear, pageable);
            result.put("total", page.getTotalElements());
            result.put("rows", page.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    
	/**
	 * 跳转到课程列表页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toEduSourceList")
	public String toPackageList(HttpServletRequest request, Model model) {
		String packageId = request.getParameter("packageId");
		EducationPackage edupack = eduPackageService.findEducationPackageByPackageId(packageId);
		model.addAttribute("pack", edupack);
    	return "page/edu/sourcelist";
	}
    
    /**
     * 根据专题Id来查询所有课程列表
     * @param pager
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getEducationSourceList")
    @ResponseBody
   	public Map<String,Object> getEducationPackageList(Pager pager,HttpServletRequest request, Model model) {
    	String packageId = request.getParameter("packageId");
    	Integer pid = 0;
    	if(StringUtils.isNotBlank(packageId)){
    		pid =Integer.parseInt(packageId);
    	}
       	Map<String,Object> map = new HashMap<String,Object>();
       	BigInteger sourceCount = eduSourceService.findAllEducationSourceCountByPackageId(pid);
       	List<EducationSource> sourcelist = eduSourceService.findEducationSource(pid,pager);
        map.put("total", sourceCount);
		map.put("result", sourcelist);
       	return map;
   	}
	
    
    /**
	 * 跳转到课程列表页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toSourceLeafList")
	public String toSourceLeafList(HttpServletRequest request, Model model) {
		String sourceId = request.getParameter("sourceId");
		EducationSource educationSource = eduSourceService.findEducationSourceBySourceId(sourceId);
		String packageId = request.getParameter("packageId");
		EducationPackage edupack = eduPackageService.findEducationPackageByPackageId(packageId);
		PropertiesReader pu = PropertiesReader.getInstance();
		String sourcewareurl = pu.getProperties("sourcewareurl");
		model.addAttribute("sourcewareurl",sourcewareurl);
		model.addAttribute("pack", edupack);
		model.addAttribute("eduSource", educationSource);
    	return "page/edu/sourceleaflist";
	}
	
    
    /**
     * 根据课程获取课节列表
     * @param pageable
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getEducationSourceListBySource")
    @ResponseBody
   	public Map<String,Object> getEducationSourceListBySource(Pageable pageable,HttpServletRequest request, Model model) {
    	String sourceId = request.getParameter("sourceId");
       	Map<String,Object> map = new HashMap<String,Object>();
        Page<EducationSource> sourcelist = eduSourceService.findAllEducationSourceByParentSourceId(sourceId, pageable);
        map.put("total", sourcelist.getTotalElements());
		map.put("result", sourcelist.getContent());
       	return map;
   	}
    
}
