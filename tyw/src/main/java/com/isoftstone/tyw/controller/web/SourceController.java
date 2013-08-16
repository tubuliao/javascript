package com.isoftstone.tyw.controller.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.isoftstone.tyw.entity.info.Source;
import com.isoftstone.tyw.entity.info.SourceModule;
import com.isoftstone.tyw.entity.info.SourceView;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.SourceService;
import com.isoftstone.tyw.util.IOUtil;

@Controller
public class SourceController {
	@Autowired
	private SourceService sourceService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private IOUtil ioUtil;

	@RequestMapping(value = "/source", method = RequestMethod.GET)
	public String userPage() {
		return "page/source/source";
	}
	@RequestMapping(value = "/modifySegMentCount", method = RequestMethod.POST)
    @ResponseBody
	public Map<String,Object>  modifySegMentCount(HttpServletRequest request,HttpServletResponse response){
	    Map<String,Object> result=new HashMap<String,Object>();
	    String name_no=request.getParameter("name_no");
	    name_no=name_no.equals("")?null:name_no;
	    sourceService.modifySegMentCount(name_no);
	    result.put("success",true);
	    return result;
	}
	
	
	@RequestMapping(value = "/sourcelist/data", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> listTagJson(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		// 标准名称
		String standardName=request.getParameter("standardName");
		// 标准编号
		String standardNo=request.getParameter("standardNo");
		// 标准类型
		String standardType = request.getParameter("standardType");
		// 批准部门
		String approveDepartment = request.getParameter("approveDepartment");
		// 主编单位
		String editDepartment = request.getParameter("editDepartment");
//		Page<Source> page = sourceService.listSource(getWhereClause(standardName,standardNo,standardType,approveDepartment,editDepartment),pageable);
//		result.put("total",page.getTotalElements());  
//		result.put("rows", page.getContent());  
		
		List<SourceModule> sourceRows = sourceService.listSourceBySql(standardName,standardNo,standardType,approveDepartment,editDepartment, pageable);
		BigInteger sourceTotal = sourceService.listSourceBySqlCount(standardName,standardNo,standardType,approveDepartment,editDepartment);
		result.put("total", sourceTotal);  
		result.put("rows", sourceRows);  
		return result;
	}
	
	
	/**
	 * 跳转到来源视图页面
	 * @return
	 */
	@RequestMapping(value = "/sourceviewlist", method = RequestMethod.GET)
	public String sourceviewlist() {
		return "page/source/sourceviewlist";
	}
	
	@RequestMapping(value = "/sourceviewlist/data", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> viewlistTagJson(@PageableDefaults(value=60) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		String sourceType=request.getParameter("sourceType");
		String knowledgeType = request.getParameter("knowledgeType") ;	// 知识类型
		String sources = request.getParameter("sources") ;	// 来源
		//Page<SourceView> page = sourceService.listSourceView(sourceType, knowledgeType, sources, pageable);
		List<SourceView> sourceViewList = sourceService.listSourceViewBaseRows(sourceType, knowledgeType, sources, pageable);
		BigInteger sourceViewTotal = sourceService.listSourceViewBaseTotal(sourceType, knowledgeType, sources);
		result.put("total",sourceViewTotal);  
		result.put("rows", sourceViewList);  
		return result;
	}
	
	/**
	 * 条件查询
	 * @param standardName 标准名称
	 * @param standardNo 标准编号
	 * @param standardType 标准类型
	 * @return
	 */
	public Specification<Source> getWhereClause(final String standardName,final String standardNo,final String standardType,final String approveDepartment,final String editDepartment){
		return new Specification<Source>(){
			@Override
			public Predicate toPredicate(Root<Source> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				// 创建时间倒叙
				query.orderBy(cb.desc(root.get("standardType")));
				
				//标准名称
				if(StringUtils.isNotBlank(standardName)){
					predicate.getExpressions().add(cb.like(root.<String>get("standardName"), "%"+standardName.trim()+"%"));
				}
				// 编号
				if(StringUtils.isNotBlank(standardNo)){
					predicate.getExpressions().add(cb.like(root.<String>get("standardNo"), "%"+standardNo.trim()+"%"));
				}
				// 审批部门
				if(StringUtils.isNotBlank(approveDepartment)){
					predicate.getExpressions().add(cb.like(root.<String>get("approveDepartment"), "%"+approveDepartment.trim()+"%"));
				}
				// 主编部门
				if(StringUtils.isNotBlank(editDepartment)){
					predicate.getExpressions().add(cb.like(root.<String>get("editDepartment"), "%"+editDepartment.trim()+"%"));
				}
				//类型
				if(StringUtils.isNotBlank(standardType)){
					predicate.getExpressions().add(cb.like(root.<String>get("standardType"), standardType));
				}
				return predicate;
			}
		};
	}
	
	/**
	 * 新增来源信息
	 * 
	 * @param source
	 * @return
	 */
	@RequestMapping(value = "/source/saveSource", method = RequestMethod.POST)
	@ResponseBody
	public void doSave(Source source,HttpServletResponse response, HttpServletRequest request){
		String resultJson = "" ;
		try {
			String executeDate = request.getParameter("executeDate02") ;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
			if(executeDate != null && !"".equals(executeDate)) {
				source.setExecuteDate(sdf.parse(executeDate)) ;
			} else {
//				source.setExecuteDate(null) ;
			}
		    source.setNameNo(source.getStandardName()+source.getStandardNo());
		    // 来源类型  1：标准规范	2：图集	3：其他
		    String st = source.getStandardType();
		    if(st != null && !"".equals(st)) {
		    	if(st.contains("图集")) {		// 图集
		    		source.setSourceType("2");
		    	} else if(st.contains("标准")) {	// 标准规范
		    		source.setSourceType("1");
		    	} else {	// 其他
		    		source.setSourceType("3");
		    	}
		    }
			sourceService.saveSource(source);
			resultJson = "{'success':true}";
		 }catch (Exception e) {   
	         e.printStackTrace();  
	         resultJson = "{'msg':'保存失败!'}";
	    } 
		commonService.responseJsonBody(response, resultJson);
	}
	
	@RequestMapping(value = "/source/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String,Object> doDelete(@PathVariable("id") String id, Model model)
			throws Exception {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			sourceService.deleteSource(id);
			result.put("success",true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "删除失败！");
			throw e;
		}
		return result;
	}
	
	
	@RequestMapping(value="/source/upload",method=RequestMethod.POST)
	public @ResponseBody String doUpload(HttpServletRequest request,HttpServletResponse response){
		String path = request.getSession().getServletContext().getRealPath("upload");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();   
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
			MultipartFile mf = entity.getValue();    
			String fileName=mf.getOriginalFilename();
			File file= new File(path,fileName);
			InputStream is = null;
            try {
				ioUtil.streamToFile(mf.getInputStream(), file, true);
				List<Source> sources=sourceService.readXls(file.getAbsolutePath());
				//System.out.println("sources.size:" + sources.size());
				sourceService.saveSource(sources);
				is = mf.getInputStream();
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "false";
	}
	
	/**
	 * 跳转到新增页面
	 * @return
	 */
	@RequestMapping(value="/source/addSource", method=RequestMethod.GET) 
	public String toAddSourcePage() {
		return "/page/source/addSource" ;
	}
	
	/**
	 * 跳转到到修改页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/source/editSource/{id}", method=RequestMethod.GET)
	public String toEditSourcePage(@PathVariable("id")String id, Model model) {
		model.addAttribute("source", sourceService.getSourceById(id)) ;
		return "/page/source/editSource" ;
	} 
	
	/**
	 * 预览
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/source/preview", method=RequestMethod.GET) 
	@ResponseBody
	public Map<String, Object> doPreviewSource(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		try {
			String id = request.getParameter("sourceId") ;
			Source sourceDetail = sourceService.getSourceById(id) ;
			result.put("sourceDetail", sourceDetail) ;
			result.put("success", true) ;
		} catch (Exception e) {
			result.put("success", false) ;
			result.put("msg", "预览失败！") ;
			e.printStackTrace();
		}
		
		return result ;
	}
	
	
}
