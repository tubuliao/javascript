package com.isoftstone.tyw.controller.web;


import java.io.File;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.TagService;
import com.isoftstone.tyw.util.IOUtil;
import com.isoftstone.tyw.util.PingYinUtil;


@Controller
public class TagController {
	@Autowired
	private TagService tagService;
	
	@Autowired
	private IOUtil ioUtil;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 *  获取所有标签
	 * @param pageable
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/tag", method = RequestMethod.GET)
	public String tag() {
		return "page/tag/tag";
	}
	
	@RequestMapping(value = "/tag/save", method = RequestMethod.POST)
	@ResponseBody
	public void doAdd(Tag tag, Model model,HttpServletResponse response) {
		String resultJson="{'success':true}";
		response.setContentType("text/html;charset=utf-8");
		//解决IE内核返回数据下载问题
		try{
			tag.setDataDate(new Date());
			tag.setStatus(true);
			if(tag.getCode()==null||"".equals(tag.getCode())){
				tag.setCode(0L);
			}
			//新增数据同时要更新父节点是否为叶子(leaf)属性
			if(tag.getId()==null||"".equals(tag.getId())){
				tag.setLeaf(1);
				tagService.modifyLeaf(tag.getParentId(),0);
			}
			//取拼音首字母
			tag.setTagNo(tag.getName()==null?"":PingYinUtil.getFirstSpell(tag.getName()));
			//根节点添加直接保存
			if(tag.getParentId()==null||"".equals(tag.getParentId())){
				tagService.saveTag(tag);
				resultJson="{'success':true}";
			}else{
				//父下最大节点实体
				Tag maxCodeTag=tagService.findTagByMaxCode(tag.getParentId());
				//该节点还没有子节点
				if(maxCodeTag==null){
					tagService.saveTag(tag);
					resultJson="{'success':true}";
				}else{
					String codeStr=maxCodeTag.getCode().toString(),maxStr="";
					switch(codeStr.length()){
						case 17:
							//maxStr=codeStr.substring(tag.getLevel()*2-1==1?0:tag.getLevel()*2-1,tag.getLevel()*2-1);
							maxStr=codeStr.substring(tag.getLevel()*2-3,tag.getLevel()*2-1);
							break;
						case 18:
							//maxStr=codeStr.substring(tag.getLevel()*2==2?0:tag.getLevel()*2,tag.getLevel()*2);
							maxStr=codeStr.substring(tag.getLevel()*2-2,tag.getLevel()*2);
							break;
					}
					//if(maxStr.equalsIgnoreCase("03")&&("".equals(tag.getId())||null==tag.getId())){
					if(maxStr.equalsIgnoreCase("99")){
						resultJson="{'msg':'非法操作,同一层级最大支持99个标签！'}";
					}else{
						tagService.saveTag(tag);
						resultJson="{'success':true}";
					}
				}
			}
		}catch(Exception ex){
			resultJson="{'msg':'失败！'}";
			ex.printStackTrace();
		}
		commonService.responseJsonBody(response, resultJson);
	}
	
	
	
	
	/**
	 * 标签树/标签列表
	 * @param pageable
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/tagtree/tag/{parentId}", method = RequestMethod.POST)
	@ResponseBody
	public List<Tag> listTag(@PathVariable("parentId")String parentId) {
		List<Tag> tags=null;
		if("0".equals(parentId)){
			tags=tagService.getTagRoot();
		}else{
			tags=tagService.getTagByParentId(parentId);
		}
		return tags;
	}
	
	
	@RequestMapping(value = "/combotree/tag/{parentId}", method = RequestMethod.POST)
	@ResponseBody
	public List<Tag> listCombotreeTag(@PathVariable("parentId")String parentId,HttpServletRequest request,HttpServletResponse response,  @RequestParam(value = "pageStr", required = false) String pageStr,
            @RequestParam(value = "name",required = false )String name, @RequestParam(value = "code",required = false )String code,@RequestParam(value = "isAll",required = false )String isAll) {
		List<Tag> tags=null;
//		  Map<String,String> formContent_Copy=new HashMap<String, String>();
//          HttpSession session = request.getSession();
//          String formContent=request.getParameter("formContent");
//          formContent_Copy.put(parentId, formContent);
//          //System.out.println("---------"+formContent);
//          session.setAttribute("formContent_Copy", formContent_Copy);
		if("0".equals(parentId)){
			tags=tagService.getAllTagRoot();
		}else{
		    if("knowledge".equals(name)&&"0".equals(isAll)){
 	            Long codestart=Long.valueOf(code);
 	            Long codeend=Long.valueOf(code.replace("0000000000000000", "9999999999999999"));
	            tags=tagService.findTagByCodeLevel(codestart,codeend);
	        }else{
	            tags=tagService.getAllTagByParentId(parentId);
	        }
		}
		return tags;
	}
	
	@RequestMapping(value = "/combotree/findTagByTagName", method = RequestMethod.POST)
    @ResponseBody
    public  Tag findTagByTagName(HttpServletRequest request,HttpServletResponse response) {
	    response.setContentType("text/html;charset=utf-8"); 
	    String names=request.getParameter("name");
	    Tag tag=null;
        tag=tagService.findTagByTagName(names);
       
         return tag;
    }
	
	   @RequestMapping(value = "/combotree/getHis/{parentId}", method = RequestMethod.POST)
	    @ResponseBody
	  public  List<Tag> listGetHis(@PathVariable("parentId")String parentId,HttpServletRequest request,HttpServletResponse response) {
	       response.setContentType("text/html;charset=utf-8");
	       HttpSession session = request.getSession();
           Map<String,String> formContent_Copy=new HashMap<String, String>();
//            PrintWriter out = response.getWriter();
//            formContent_Copy=(Map<String, String>) session.getAttribute("formContent_Copy");
//            //System.out.println("========="+formContent_Copy.get(parentId));
//            out.print(formContent_Copy.get(parentId));
           List<Tag> tags=null;
           if("0".equals(parentId)){
               tags=tagService.getAllTagRoot();
           }else{
               tags=tagService.getAllTagByParentId(parentId);
               if((tags.get(0).getLevel()-1)==1){
                       tags=tagService.getAllTagRoot();
                }else{
                       tags=tagService.getAllTagByParentId(parentId);
                       tags=tagService.getAllTagsByParentCode(String.valueOf(tags.get(0).getParentCode()),tags.get(0).getLevel()-1);
                }
           }
           return tags;
	    }
	
	@RequestMapping(value = "/tagtree/segment/{parentId}", method = RequestMethod.POST)
	@ResponseBody
	public List<Tag> listSegmentTag(@PathVariable("parentId")String parentId) {
		List<Tag> tags=null;
		if("0".equals(parentId)){
//			tags=tagService.getSegmentTagRoot();
			tags = tagService.getTagList();
		}else{
//			tags=tagService.getSegmentTagByParentId(parentId);
			tags =  tagService.getTagListByParentId(parentId) ;
		}
		return tags;
	}
	
	@RequestMapping(value = "/tagtree/form/{parentId}", method = RequestMethod.POST)
	@ResponseBody
	public List<Tag> listFormTag(@PathVariable("parentId")String parentId) {
		List<Tag> tags=null;
		if("0".equals(parentId)){
//			tags=tagService.getTagRoot();
			tags = tagService.getTagList() ;
		}else{
//			tags=tagService.getTagByParentId(parentId);
			tags = tagService.getTagListByParentId(parentId) ;
		}
		return tags;
	}
	
	
	
	@RequestMapping(value = "/taglist", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> listTagJson(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		String name=request.getParameter("name");
		String parentId=request.getParameter("parentId");
		String tagNo = request.getParameter("tagNo");
		//Page<Tag> page=tagService.listTag(this.getWhereClause(name, tagNo,parentId), pageable);
		List<Tag> tagList = tagService.listTagBaseRows(name, tagNo, parentId, pageable);
		BigInteger tagTotal = tagService.listTagBaseTotal(name, tagNo, parentId);
		result.put("total",tagTotal);  
		result.put("rows", tagList);  
		return result;
	}
	
	
	/**
	 * 上传标签文件
	 * @param file
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/tag/ajaxUpload",method=RequestMethod.POST)
	public @ResponseBody String ajaxUpload(HttpServletRequest request,HttpServletResponse response){
		String path = request.getSession().getServletContext().getRealPath("upload");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();   
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
			MultipartFile mf = entity.getValue();    
			File file= new File(path,mf.getOriginalFilename());
            //System.out.println("FileName: "+mf.getOriginalFilename());
            //System.out.println("FileSize: "+mf.getSize());
            //System.out.println("ContentType: "+mf.getContentType());
            try {
				ioUtil.streamToFile(mf.getInputStream(), file, true);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return path;
	}
	
	
	/**
	 * 上传标签文件
	 * @param file
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/tag/import",method=RequestMethod.POST)
	public String upload(@RequestParam(value = "file", required = false) MultipartFile file, 
			 HttpServletRequest request, ModelMap model){
		String path = request.getSession().getServletContext().getRealPath("upload");
		String fileName = file.getOriginalFilename();
		File targetFile = new File(path, fileName);
	    
		//System.out.println(request.getContextPath());
			
		if(!targetFile.exists()){
	    	targetFile.mkdirs();
	    }
	    try {
	    	file.transferTo(targetFile);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }   
	    try {
			List<Tag> listTag=tagService.readExcel(targetFile.getAbsolutePath());
			tagService.saveTag(listTag);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "redirect:/taglist/2";
	}
	
	
	@RequestMapping(value="/tag/export",method=RequestMethod.GET)  
	@ResponseBody
	public void export(Pageable pageable, HttpServletRequest request, HttpServletResponse response) {    
		String name=request.getParameter("name");
		String parentId=request.getParameter("parentId");
		String tagNo = request.getParameter("tagNo");
		String resultJson = "{'success':true}";
		Page<Tag> page=tagService.listTag(this.getWhereClause(name,tagNo, parentId), pageable);
		List<Tag> listTag=page.getContent();
		
		String templatePath = request.getSession().getServletContext().getRealPath("template") + File.separator+"tag_template.xls";   
		String downLoadPath = request.getSession().getServletContext().getRealPath("download") + File.separator+"export.xls";
		try {
			tagService.writeExcel(listTag,templatePath, downLoadPath);
			response.setContentType("text/html;charset=utf-8"); 
			resultJson = "{'success':true}";
		} catch (Exception e) {
			e.printStackTrace();
			resultJson = "{'msg':'导出失败!'}";
		}
		commonService.responseJsonBody(response, resultJson);
	} 
	
	@RequestMapping(value="/tag/download",method=RequestMethod.GET)  
	public ModelAndView download(Pageable pageable, HttpServletRequest request, HttpServletResponse response)    throws Exception {    
		String downLoadPath = request.getSession().getServletContext().getRealPath("download") + File.separator+"export.xls";
		ioUtil.httpDownload(request, response, "export.xls", downLoadPath);
		return null;  
	}

	
	/***
	 * 删除标签
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/tag/delete/{id}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doDelete(@PageableDefaults(value=1)Pageable pageable, @PathVariable("id")String id,Model model){
		Map<String,Object> result=new HashMap<String,Object>();
		try{
			//删除标签前先判断该标签是否有下级标签，同时判断该标签是否被应用
			
			//判断该标签是否有下级标签
			if(tagService.getTagByParentId8(id) != null){
				result.put("msg", "该标签不是最末级禁止删除,删除该标签会造成数据不完整！");
			//判断该标签是否被应用
			}else if(tagService.findUseingTagById(id)){
				result.put("msg", "该标签已被使用不能删除！");
			}else{
				tagService.deleteTag(id,false);
				Tag tag=tagService.getTagById(id);
				//最有一个叶子节点也被删除,修改父节点为叶子
				if(tagService.getTagByParentId(tag.getParentId()).size()==0){
					tagService.modifyLeaf(tag.getParentId(),1);
				}
				result.put("success",true);
			}
		}catch(Exception e){
			e.printStackTrace();
			result.put("msg", "删除失败！");
		}
		return result;
	}
	
	
	public Specification<Tag> getWhereClause(final String name,final String tagNo,final String parentId){
		return new Specification<Tag>(){
			@Override
			public Predicate toPredicate(Root<Tag> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				query.orderBy(cb.asc(root.get("sortNo")));
				predicate.getExpressions().add(cb.equal(root.<String>get("status"), true));
				if(name!=null&&!"".equals(name)){
					predicate.getExpressions().add(cb.like(root.<String>get("name"), "%"+name.trim()+"%"));
				}
				if(parentId!=null&&!"".equals(parentId)){
					predicate.getExpressions().add(cb.equal(root.<String>get("parentId"), parentId.trim()));
				}
				if(StringUtils.isNotBlank(tagNo)){
					predicate.getExpressions().add(cb.like(root.<String>get("tagNo"), "%"+tagNo.trim()+"%"));
				}
				
				return predicate;
			}
		};
	}
	
	@RequestMapping(value="/tag/details/{id}", method=RequestMethod.POST)
	@ResponseBody
	public Tag doDetails(@PathVariable("id")String id,Model model){
		Tag tag = null;
		try{
			tag=tagService.getTagById(id);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return tag;
	}
	
	
	@RequestMapping(value="/tag/sortNo/{parentId}", method=RequestMethod.POST)
	@ResponseBody
	public Tag doSortNo(@PathVariable("parentId")String parentId,Model model){
		Tag tag = null;
		try{
			if("0".equals(parentId)){
				tag=tagService.findMaxSortNoByRoot();
			}else{
				tag=tagService.findMaxSortNoByParentId(parentId);
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return tag;
	}
	
	/**
	 * 删地区标签时提示
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/tag/delAreaTag", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteAreaTagAlert(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		try {
			String id = "" ;
			id = request.getParameter("tagId") ;	// 根据id
			Tag t = new Tag() ;
			if(id != null && !"".equals(id)) {
				t = tagService.getTagById(id) ;
			}
			String tagParentCode = "" ;
			if(t != null) {
				tagParentCode = String.valueOf(t.getParentCode()) ;
			}
			if(!"".equals(tagParentCode) && "20000000000000000".equals(tagParentCode)) {
				result.put("success", true) ;
			} else {
				result.put("success", false) ;
			}
		} catch (Exception e) {
			result.put("success", false) ;
			e.printStackTrace();
		}
		return result ;
	}
	

}
