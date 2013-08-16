package com.isoftstone.tyw.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.edu.EducationPackage;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.EduPackageService;
import com.isoftstone.tyw.util.DateManager;

@Controller
public class EduPackageController {

    @Autowired
    private EduPackageService eduPackageService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private EduPackageService packageService;

    /**
     * 进入eduPage管理页面
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/eduPagelist", method = RequestMethod.GET)
    public String eduPagelist(HttpServletRequest request, Model model) {
        return "page/edu/eduPagelist";
    }

    @RequestMapping(value = "/eduPagelist/save", method = RequestMethod.POST)
    @ResponseBody
    public void saveEp(Model model, EducationPackage ep, HttpServletResponse response, HttpServletRequest request) {
        String resultJson = "{'success':true}";
        response.setContentType("text/html;charset=utf-8");
        HttpSession session = request.getSession();
        try {
            // 记录修改者的信息
            User user = null;
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
                user = accountService.loadUserByUsername(userDetails.getUsername());
            }
            String validDateStarts = request.getParameter("validDateStarts");
            String validDateEnds = request.getParameter("validDateEnds");
            ep.setValidDateEnd(DateManager.stringFormat(validDateEnds));
            ep.setValidDateStart(DateManager.stringFormat(validDateStarts));
            ep.setUname(user.getUsername());
            List<EducationPackage> list = eduPackageService.findByTitle(ep.getTitle());
            if (list.size() <= 0 && StringUtils.isEmpty(ep.getId())) {
                eduPackageService.save(ep);
            }
            if (StringUtils.isNotEmpty(ep.getId())) {
                eduPackageService.save(ep);
            }
            if (list.size() > 0 && StringUtils.isEmpty(ep.getId())) {
                resultJson = "{'msg':'包名已存在!'}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultJson = "{'msg':'保存失败!'}";
        }
        commonService.responseJsonBody(response, resultJson);
    }
    
    @RequestMapping(value = "/eduPackage/data", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> findAllEducationPackageBy(@PageableDefaults(value = 10) Pageable pageable, Model model,
            HttpServletRequest request, @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "period", required = false) Integer period,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "postTypeName", required = false) String postTypeName,
            @RequestParam(value = "postName", required = false) String postName) {
        Map<String, Object> result = new HashMap<String, Object>();
        Page<EducationPackage> page = eduPackageService.findAllEducationPackageBy(year, period, title, status,
                postTypeName, postName, pageable);
        result.put("total", page.getTotalElements());
        result.put("rows", page.getContent());
        return result;
    }
    

 
    /**
     * 进入专题页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/toPackageList")
	public String toPackageList(HttpServletRequest request, Model model) {
    	List<EducationPackage> plist = packageService.findAllEducationPackageNoPageable(null, null, null, null, null, null);
		String tagName = "视频资源";
    	model.addAttribute("plist", plist);
    	model.addAttribute("tagName", tagName);
    	return "page/edu/packagelist";
	}
    
    /**
     * 获取专题列表
     * @param pageable
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/getEducationPackageList")
    @ResponseBody
   	public Map<String,Object> getEducationPackageList(Pageable pageable,HttpServletRequest request, Model model) {
       	Map<String,Object> map = new HashMap<String,Object>();
    	Page<EducationPackage> pagelist = packageService.findAllEducationPackageBy(null, null, null, null, null, null, pageable);
        map.put("total", pagelist.getTotalElements());
		map.put("result", pagelist.getContent());
       	return map;
   	}
}
