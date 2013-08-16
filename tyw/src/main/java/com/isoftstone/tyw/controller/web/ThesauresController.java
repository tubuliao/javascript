package com.isoftstone.tyw.controller.web;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.jexl2.UnifiedJEXL.Exception;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.isoftstone.tyw.entity.info.Thesaures;
import com.isoftstone.tyw.entity.info.ThesauresModule;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.InfoService;
import com.isoftstone.tyw.service.ThesauresService;
import com.isoftstone.tyw.util.IOUtil;

/**
 * 
 * @author liuwei
 *
 */
@Controller
public class ThesauresController {

	@Autowired
	private ThesauresService thesauresService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private IOUtil ioUtil;
	
	@Autowired
	private InfoService infoService;
	
	/**
	 * 跳转到词库列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/thesaures",  method=RequestMethod.GET)
	public String toThesauresList(Model model) {
		model.addAttribute(new Thesaures());
		return "page/thesaures/thesaures";
	}
	
	
	/**
	 * 词库列表
	 * @param pageable
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/thesauresList", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> thesauresListPager(@PageableDefaults(value=10)Pageable pageable, Model model, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		// 名称
		String name=request.getParameter("name");
		Map<String, Object> page = thesauresService.listThesauresBySql(name, pageable);
		result.put("rows", page.get("rows"));
		result.put("total", page.get("total"));
		return result ;
	}
	
	/**
	 * 跳转到添加页
	 * @return
	 */
	@RequestMapping(value="/addThesaures", method=RequestMethod.GET)
	public String toAddThesauresPage() {
		
		return "page/thesaures/addThesaures";
	}
	
	/**
	 * 保存
	 * @param thesaures
	 * @param response
	 */
	@RequestMapping(value="/saveThesaures", method=RequestMethod.POST)
	@ResponseBody
	public void doSaveThesaures(Thesaures thesaures, HttpServletResponse response) {
		String resultJson = "{'success':true}";
		try {
			thesauresService.saveOne(thesaures);
			resultJson = "{'success':true}";
		} catch(Exception e) {
			resultJson = "{'fail':true}";
			resultJson = "{'msg':'保存失败!'}";
			e.printStackTrace();
		}
		
		commonService.responseJsonBody(response, resultJson);
	}
	
	/**
	 * 跳转到修改页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/editThesaures/{id}", method=RequestMethod.GET)
	public String toEditThesauresPage(@PathVariable("id")String id, Model model) {
		Thesaures t = thesauresService.getOne(id);
		model.addAttribute("thesaures", t);
		return "page/thesaures/editThesaures";
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deleteThesaures/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteThesaures(@PathVariable("id")String id) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			thesauresService.delOne(id);
			result.put("success", true);
		} catch (java.lang.Exception e) {
			result.put("fail", true);
			result.put("msg", "删除失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	   /**
     * excel上传词库
     * @param request
     * @param response
     * @return
	 * @throws java.lang.Exception 
	 * @throws IOException 
     */
    @RequestMapping(value="/uploadThesaures",method=RequestMethod.POST)
	public @ResponseBody String doUploadThesaures(HttpServletRequest request,HttpServletResponse response) throws IOException, java.lang.Exception{
		String path = request.getSession().getServletContext().getRealPath("upload");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();   
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
			MultipartFile mf = entity.getValue();    
			String fileName=mf.getOriginalFilename();
			File file= new File(path,fileName);
            try {
            	// 将输入流写入文件
				ioUtil.streamToFile(mf.getInputStream(), file, true);
				// 读取Excel文件
				List<Thesaures> thesauresList = thesauresService.readXls(file.getAbsolutePath());
				//System.out.println("thesauresList.size:" + thesauresList.size());
				// 存数据库
				/*
				List<Thesaures> tList = new ArrayList<Thesaures>();
				Iterator<Thesaures> ite = thesauresList.iterator();
				while(ite.hasNext()) {
					Thesaures t = ite.next();
					// 判断名称的唯一性
					if(thesauresService.getOneByName(t.getName()) == null) {
						tList.add(t);
						
					}
				}
				thesauresService.saveOne(t);
				*/
				
				// List中判断名称的唯一性
				
				
				// 保存
				thesauresService.saveAll(thesauresList);
				
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return "false";
	}
    
    /**
     * 检查关键词的唯一性
     * @param request
     * @param response
     */
    @RequestMapping(value="/validThesauresName", method=RequestMethod.POST)
    @ResponseBody
    public void checkName(HttpServletRequest request, HttpServletResponse response) {
    	try {
			// 输入的关键词
			String name = request.getParameter("name");
			if(thesauresService.getOneByName(name) == null) {
				response.getWriter().print("true");
			} else {
				response.getWriter().print("false");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
 }
