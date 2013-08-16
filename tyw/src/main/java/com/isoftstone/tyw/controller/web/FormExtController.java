package com.isoftstone.tyw.controller.web;




import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.FormExt;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.FdfsService;
import com.isoftstone.tyw.service.FormExtService;
import com.isoftstone.tyw.service.InfoService;
import com.isoftstone.tyw.util.IOUtil;
import com.isoftstone.tyw.util.PropertiesReader;

@Controller
public class FormExtController {
	@Autowired
	private FormExtService formExtService;
	@Autowired
	private IOUtil ioUtil;
	@Autowired
	private AccountService accountService;
	@Autowired
	private FdfsService fdfsService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private InfoService infoService;
	/**
	 * 添加页面跳转
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/formext/add/{primary}", method = RequestMethod.GET)
	public String addForm(Model model,@PathVariable("primary")String primary){
		model.addAttribute("primary",primary);
		return "page/form/addFormExt";
	}
	
	/**
	 * 添加新表格
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/formext/save", method = RequestMethod.POST)
	@ResponseBody
	public void doSave(FormExt formExt, @RequestParam("primary")String primary,
			 HttpServletRequest request,HttpServletResponse response){
		String resultJson = "{'success':true}";
		 try{
			 if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
					UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					User user = accountService.loadUserByUsername(userDetails.getUsername());
					formExt.setInsertId(user.getId());
					formExt.setInsertName(user.getAliasname());
			 }
			 formExtService.saveFormExt(formExt,primary);
			 resultJson = "{'success':true}";
		 }catch(Exception e){
			 resultJson = "{'msg':'保存出错!'}";
			 e.printStackTrace();
		 }
		 commonService.responseJsonBody(response, resultJson);
	}
	
	
	@RequestMapping(value="/formext/upload",method=RequestMethod.POST)
	public @ResponseBody String formUplad(HttpServletRequest request,HttpServletResponse response){
		String path = request.getSession().getServletContext().getRealPath("upload");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();   
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
			MultipartFile mf = entity.getValue();    
			File file= new File(path,mf.getOriginalFilename());
            try {
				ioUtil.streamToFile(mf.getInputStream(), file, true);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return path;
	}
	
	@RequestMapping(value="/formext/uploads/fdfs",method=RequestMethod.POST)
	public @ResponseBody String upladSimple(HttpServletRequest request,HttpServletResponse response){
		PropertiesReader pu=PropertiesReader.getInstance();
		String path = pu.getProperties("fdfs.HttpAddress");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();   
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
			MultipartFile mf = entity.getValue();  
			String fileName = mf.getOriginalFilename();
			long fileLength=mf.getSize();
			String fileExtName = "";
			if (fileName.contains(".")) {
				fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
			}
            try {
            	path += fdfsService.upload(fileName, fileExtName, mf.getInputStream(), fileLength);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return path;
	}
	
	@RequestMapping(value="/formext/uploadc/fdfs",method=RequestMethod.POST)
	public @ResponseBody String upladContext(HttpServletRequest request,HttpServletResponse response){
		//String path = "http://211.141.29.47/";
		PropertiesReader pu=PropertiesReader.getInstance();
		String path = pu.getProperties("fdfs.HttpAddress");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();   
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
			MultipartFile mf = entity.getValue();  
			String fileName = mf.getOriginalFilename();
			long fileLength=mf.getSize();
			String fileExtName = "";
			if (fileName.contains(".")) {
				fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
			}
            try {
            	path += fdfsService.upload(fileName, fileExtName, mf.getInputStream(), fileLength);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return path;
	}
	
	
	/**
	 *  获取所有表格
	 * @param pageable
	 * @param model
	 * @return
	 */
	
	
	/**
	@RequestMapping(value = "/formextlist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> formListPager(@PageableDefaults(value=10) Pageable pageable, Model model) {
		Map<String,Object> result= new HashMap<String,Object>();
		Page<FormExt> page=formExtService.listFormExt(pageable);
		result.put("total",page.getTotalElements());
		result.put("rows",page.getContent());
		return result;
	}
	**/
	
	@RequestMapping(value = "/formextlist/{primary}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> formListPagerPrimary(@PathVariable("primary")String primary,@PageableDefaults(value=10) Pageable pageable, Model model) {
		Map<String,Object> result= new HashMap<String,Object>(); 
		//System.out.println("primary:"+primary);
		if(!"0".equals(primary)){
			Page<FormExt> page=formExtService.listFormExt(primary,pageable);
			result.put("total",page.getTotalElements());
			result.put("rows",page.getContent());
		}else{
			result.put("total",0);
			result.put("rows",new ArrayList<FormExt>());
		}
		return result;
	}
	
	/***
	 * 修改表格
	 * @param id
	 * @param base
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/formext/update/{id}", method = RequestMethod.GET)
	public String toUpdate(@PathVariable("id")String id,Base base,Model model){
		model.addAttribute("formext", formExtService.getFormExtById(id));
		return "page/form/updateFormExt";
	}
	
	@RequestMapping(value="/formext/update/{id}", method = RequestMethod.POST)
	public void doUpdate(@PathVariable("id")String id, @RequestParam("primary")String primary,FormExt formExt,Model model,
			HttpServletRequest request,HttpServletResponse response){
		String resultJson = "";
		try{
			formExtService.saveFormExt(formExt);
			resultJson = "{'success':true}";
		}catch(Exception e){
			e.printStackTrace();
			resultJson = "{'resMess':'更新失败！'}";
		}
		commonService.responseJsonBody(response, resultJson);
	}
	
	
	
	@RequestMapping(value="/formext/details/{id}", method = RequestMethod.GET)
	public String doDetails(@PathVariable("id")String id,FormExt form,Model model){
		model.addAttribute("form", formExtService.getFormExtById(id));
		return "page/form/seeFormExt";
	}
	
	
	
	/***
	 * 删除表格
	 * @param id
	 * @param base
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/formext/delete/{id}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doDelete(@PathVariable("id")String id,Model model){
		Map<String,Object> result=new HashMap<String,Object>();
		try{
			formExtService.deleteFormExt(id);
			result.put("success",true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("success","删除失败！");
		}
		return result;
	}
	
	/*************************************************************************************************************/
	
	/**
	 * 跳转到样表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/formext", method=RequestMethod.GET)
	public String formExt(Model model) {
		model.addAttribute(new FormExt()) ;
		return "page/formext/formExt" ;
	}
	
	/**
	 * 查询列表
	 * @param pageable
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/formExtList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> formListPager(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String,Object> result= new HashMap<String,Object>();
		String tagId=request.getParameter("tagId");
		String title=request.getParameter("title");
		//System.out.println("tagId: "+tagId);
		//System.out.println("title: "+title);
		Page<Base> page=infoService.listBase(this.getWhereClause(tagId, title, 5), pageable);
		result.put("total",page.getTotalElements());
		result.put("rows",page.getContent());
		return result;
	}
	
	public Specification<Base> getWhereClause(final String tagId,final String title,final Integer infoType){
		return new Specification<Base>(){
			@Override
			public Predicate toPredicate(Root<Base> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate =cb.conjunction();
				//标签类型
				predicate.getExpressions().add(cb.equal(root.<String>get("infoType"), infoType));
				//创建时间倒叙
				query.orderBy(cb.desc(root.get("createDate")));	
			
				if(tagId!=null&&!"".equals(tagId)){
					predicate.getExpressions().add(cb.equal(root.joinSet("tags").get("id"), tagId));
				}
				
				if(title!=null&&!"".equals(title)){
					predicate.getExpressions().add(cb.like(root.<String>get("title"), "%"+title.trim()+"%"));
				}
				return predicate;
			}
		};
	}
	
	/**
	 * 跳转到新增样表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/formExt/add", method=RequestMethod.GET)
	public String toAddFormExtPage(Model model) {
		return "page/formext/addFormExt" ;
	}
	
}
