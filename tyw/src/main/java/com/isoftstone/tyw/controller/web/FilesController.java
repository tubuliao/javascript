package com.isoftstone.tyw.controller.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

import com.isoftstone.tyw.dto.info.FilesDTO;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.FileModule;
import com.isoftstone.tyw.entity.info.Files;
import com.isoftstone.tyw.entity.info.FilesPageCount;
import com.isoftstone.tyw.entity.info.Form;
import com.isoftstone.tyw.entity.info.Source;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.BaseService;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.FdfsService;
import com.isoftstone.tyw.service.FilesPageService;
import com.isoftstone.tyw.service.FilesService;
import com.isoftstone.tyw.service.InfoService;
import com.isoftstone.tyw.service.SourceService;
import com.isoftstone.tyw.service.TagService;
import com.isoftstone.tyw.util.IOUtil;
import com.isoftstone.tyw.util.PropertiesReader;

@Controller
public class FilesController {
	@Autowired
	private FilesService filesService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private IOUtil ioUtil;
	@Autowired
	private AccountService accountService;
	@Autowired
	private FdfsService fdfsService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private TagService tagService ;
	@Autowired
	private FilesPageService filesPageService;
	@Autowired
	private BaseService baseService;
	@Autowired
	private SourceService sourceService;
	/**
	 * 添加页面跳转
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/files", method = RequestMethod.GET)
	public String files(Model model){
		model.addAttribute(new Files());
		model.addAttribute("kLevel", baseService.getKnowledgeLevelSort("1"));
		return "page/files/files";
	}
	 
	@RequestMapping(value = "/files/add", method = RequestMethod.GET)
	public String toAddfilesPage(Model model){
		return "page/files/addFiles";
	}
	
	/**
	 * 跳转到修改页面
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
   @RequestMapping(value="/file/edit/{id}", method=RequestMethod.GET)
    public String toEditFilesPage(@PathVariable("id") String id, Model model) throws Exception {
	    Files f = filesService.getFileById(id);
	    String urlStr = "";
	    String urlArr[] = null;
	    List<String> urlList = new ArrayList<String>();
	    // 将附件url字符串根据“;”拆分，存入List中传递到页面
	    if(f != null) {
	    	urlStr = f.getUrl();
	    	if(urlStr != null && !"".equals(urlStr)) {
	    		urlArr = urlStr.split(";");
	    		for(int i = 0; i < urlArr.length; i++) {
	    			if(urlArr[i] != null && !"".equals(urlArr[i])) {
	    				urlList.add(urlArr[i]);
	    			}
	    		}
	    	}
	    }
	    
	    model.addAttribute("urlList", urlList);
    	model.addAttribute("file", f) ;
    	return "page/files/editFiles" ;
    }
	/**
	 * 添加
	 * @param files
	 * @return
	 */
   /*
	@RequestMapping(value = "/files/save", method = RequestMethod.POST)
	@ResponseBody
	public void doSave(Files files, 
			 HttpServletRequest request,HttpServletResponse response){
		List<Attachment> aList = new ArrayList<Attachment>() ;
		List<Attachment> attList = new ArrayList<Attachment>() ;
		
		// 修改部分
		String id = request.getParameter("id") ;
		////System.out.println("id : " + id) ;
		if(id != null) {
			attList = attachmentService.findByFileId(id) ;	// 获取原来的附件List		
		}
		if(id != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				User user = accountService.loadUserByUsername(userDetails.getUsername());
				files.setModifyId(user.getId());
				files.setModifyName(user.getAliasname());
				files.setModifyDate(new Date());
    		}
		} else {
			files.setCreateDate(new Date()) ;		// 只在新建是创建createDate,不能修改
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				User user = accountService.loadUserByUsername(userDetails.getUsername());
				files.setInsertId(user.getId());
				files.setInsertName(user.getAliasname());
			}
		}
		String resultEdit = "" ;
		if(attList != null && !attList.isEmpty()) {
			Iterator<Attachment> ite = attList.iterator();
			StringBuffer sb = new StringBuffer() ;
			while(ite.hasNext()) {
				Attachment a = (Attachment)ite.next() ;
				sb.append(a.getTitle()) ;
				sb.append(",") ;
				sb.append(a.getUrl()) ;
				sb.append(",") ;
				sb.append(a.getSuffix()) ;
				sb.append(",") ;
				sb.append(a.getSize()) ;
				sb.append("|") ;
			}
			resultEdit = sb.toString();
		}
		////System.out.println("resultEdit : " + resultEdit) ;
		// 新增部分
		String results = request.getParameter("results") ;
		////System.out.println("results : " + results) ;
		if((results != null && !"".equals(results)) || (resultEdit != null && !"".equals(resultEdit))) {    // 新增或修改时上传了附件
			// 新增 + 修改
			results += resultEdit ;
			results = results.substring(0, results.lastIndexOf("|")) ;  // 附件信息 格式：“title,url,suffix,size|title,url,suffix,size|……”
			////System.out.println("results : " + results) ;
			String attachments[] = results.split("\\|") ;
			for(int i = 0 ; i < attachments.length ; i++) {
				String attachm[] = attachments[i].split(",") ;
				Attachment am = new Attachment() ;
				am.setTitle(attachm[0]) ;
				am.setUrl(attachm[1]) ;
				am.setSuffix(attachm[2]) ;
				am.setSize(attachm[3]) ;
				am.setCreateDate(new Date()) ;
				aList.add(am) ;
			}
			files.setAttachments(aList) ;
			// 删除info_attachment中的垃圾数据
			if(attList != null && attList.size() != 0) {
				attachmentService.deleteAttachmentList(attList) ;
			}
		}
		String resultJson="{'success':true}";
		 try{
			 Files f=filesService.saveFile(files);
			 resultJson = "{'success':true}";
		 }catch(Exception e){
			 resultJson = "{'msg':'保存出错!'}";
			 e.printStackTrace();
		 }
		 commonService.responseJsonBody(response, resultJson);
	}
	*/
   @RequestMapping(value = "/downloadfiles", method = RequestMethod.GET)
   @ResponseBody
   public void downloadfiles(HttpServletRequest request,HttpServletResponse response, Model model, @RequestParam(value = "downLoadPath", required = false) String downLoadPath) {
      try {
          if(StringUtils.isNotBlank(downLoadPath)){
              String pathSuffix=downLoadPath.substring(downLoadPath.lastIndexOf("."), downLoadPath.length());
              ioUtil.httpDownloadfile(request, response,downLoadPath);
          }
           } catch (Exception e) {
               e.printStackTrace();
           }
       
   }
   /**
    * 新增/修改 保存文件
    * @param files
    * @param request
    * @param response
    */
	@RequestMapping(value = "/files/save", method = RequestMethod.POST)
	@ResponseBody
	public void doSave(Files files, 
			 HttpServletRequest request,HttpServletResponse response){
		
		String id = request.getParameter("id") ;
		////System.out.println("id : " + id) ;
		String originalUrl = "";
		String newUrl = "";
		// 发布时间
    	String begincreateDate = "";
    	SimpleDateFormat sdf02 = new SimpleDateFormat("yyyy-MM-dd");
    	//　填写的来源
    	String source = "";
    	// 根据source获取的Source对象
    	Source s = null;
		if(id != null) { // 修改
			Files f = filesService.getFileById(id);
			if(f != null) {
				originalUrl = f.getUrl();
			}
			newUrl = originalUrl + files.getUrl();
			files.setUrl(newUrl);
			// 审批时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				if(!"".equals(request.getParameter("checkDate02"))) {
					files.setCheckDate(sdf.parse(request.getParameter("checkDate02")));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				User user = accountService.loadUserByUsername(userDetails.getUsername());
				files.setModifyId(user.getId());
				files.setModifyName(user.getAliasname());
				files.setModifyDate(new Date());
			}
			// 发布日期
    		begincreateDate = request.getParameter("begincreateDate02");
    		if(!"".equals(begincreateDate)) {	// 选择了发布日期
    			try {
					files.setBegincreateDate(sdf02.parse(begincreateDate));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		} else { // 没有选择发布日期，根据来源查询对应的实施日期
    			source = files.getSource();
    			if(!"".equals(source)) {
    				s = sourceService.getSourceByStandardName(source);
    				if(s != null && s.getExecuteDate() != null) { // 来源存在且有实施日期
    					files.setBegincreateDate(s.getExecuteDate());
    				}
    			}
    		}
    		// 若发布时间为空，默认为"1970-01-01"
    		if(files.getBegincreateDate() == null) {
    			try {
					files.setBegincreateDate(sdf02.parse("1970-01-01"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
    		}
		} else {	// 新增
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				User user = accountService.loadUserByUsername(userDetails.getUsername());
				files.setInsertId(user.getId());
				files.setInsertName(user.getAliasname());
			}
			// 发布日期
    		begincreateDate = request.getParameter("begincreateDate02");
    		if(!"".equals(begincreateDate)) {	// 选择了发布日期
    			try {
					files.setBegincreateDate(sdf02.parse(begincreateDate));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		} else { // 没有选择发布日期，根据来源查询对应的实施日期
    			source = files.getSource();
    			if(!"".equals(source)) {
    				s = sourceService.getSourceByStandardName(source);
    				if(s != null && s.getExecuteDate() != null) { // 来源存在且有实施日期
    					files.setBegincreateDate(s.getExecuteDate());
    				}
    			}
    		}
    		// 若发布时间为空，默认为"1970-01-01"
    		if(files.getBegincreateDate() == null) {
    			try {
					files.setBegincreateDate(sdf02.parse("1970-01-01"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
    		}
		}
		String resultJson="{'success':true}";
		 try{
			 Files f=filesService.saveFile(files);
			 resultJson = "{'success':true}";
		 }catch(Exception e){
			 resultJson = "{'msg':'保存出错!'}";
			 e.printStackTrace();
		 }
		 commonService.responseJsonBody(response, resultJson);
	}
   
	/**
	 * 审批/驳回
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/files/approve/{id}")   
	@ResponseBody
    public Map<String,Object> approve(@PathVariable("id") String id,HttpServletRequest request){   
		Map<String,Object> result=new HashMap<String,Object>();
		// 审批状态值
		Integer status=Integer.parseInt(request.getParameter("status"));
		// 驳回信息
		String auditInfo=request.getParameter("auditInfo");
		// 权重类型（标题/来源）
		String weightType = request.getParameter("weightType");
		// 权重值（1~10）
		String weightVal = request.getParameter("weightVal");
		// 来源信息
		String baseSource = request.getParameter("baseSource");
		User user=null; 
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				user = accountService.loadUserByUsername(userDetails.getUsername());
		 }
		 String checkId=(user!=null)?user.getId():"";
		 String checkName=(user!=null)?user.getAliasname():"";
		try {   
			if(auditInfo!=null&&!"".equals(auditInfo)){
				infoService.modifyStatus(status,checkId,checkName,new Date(),auditInfo,id);
			}else{
				infoService.modifyStatus(status,checkId,checkName,new Date(), id);
			}
			// 二次审批通过 权重值设置
			if(weightType != null && !"".equals(weightType) && weightVal != null && !"".equals(weightVal)) {
				 if("title".equals(weightType)) {	// 标题
					 baseService.setTitleWeighing(id, Integer.valueOf(weightVal));
				 } else if("source".equals(weightType) && baseSource != null && !"".equals(baseSource))	{	// 来源
					 baseService.setSourceWeighing(baseSource, Integer.valueOf(weightVal));
				 }
			}
        	result.put("success",true);
        } catch (Exception e) {   
        	result.put("msg", "审批失败！"); 
        	e.printStackTrace();   
        }   
        return result;
    }  
	
	
	@RequestMapping(value="/files/upload",method=RequestMethod.POST)
	public @ResponseBody String fileUplad(HttpServletRequest request,HttpServletResponse response){
		String path = request.getSession().getServletContext().getRealPath("upload");
		//String basePath = request.getScheme()+"://"+request.getServerName()+":"+ request.getServerPort() +  request.getContextPath()+"upload/";

		String abspath="http://localhost:8080/upload/";
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();   
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
			MultipartFile mf = entity.getValue();    
			String fileName=mf.getOriginalFilename();
			File file= new File(path,fileName);
			abspath=abspath+fileName;
			InputStream is = null;
            try {
				ioUtil.streamToFile(mf.getInputStream(), file, true);
				is = mf.getInputStream();
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
		return abspath;
	}
	
	/*
	@RequestMapping(value="/files/upload/fdfs",method=RequestMethod.POST)
	@ResponseBody
	public String uplad(HttpServletRequest request,HttpServletResponse response){
		String result = "" ;
		StringBuffer sb = new StringBuffer() ;
		PropertiesReader pu=PropertiesReader.getInstance();
		String path = pu.getProperties("fdfs.HttpAddress");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();   
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
			MultipartFile mf = entity.getValue();  
			String fileName = mf.getOriginalFilename();
			////System.out.println("fileName: "+fileName);
			long fileLength=mf.getSize();
			String fileExtName = "";
//			String fName = "" ;
			if (fileName.contains(".")) {
				fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
//				fName = fileName.substring(0, fileName.lastIndexOf(".")) ;
			}
            try {
            	path += fdfsService.upload(fileName, fileExtName, mf.getInputStream(), fileLength);
			} catch (Exception e) {
				e.printStackTrace();
			} 
            // sb格式："title,url,suffix,size|title,url,suffix,size|……"
//            sb.append(fName) ;
            sb.append(",") ;
            sb.append(path) ;
            sb.append(",") ;
            sb.append(fileExtName) ;
            sb.append(",") ;
            sb.append(fileLength) ;
		}
		result = sb.toString() ;
//		commonService.responseJsonBody(response, result);
		return result ;
	}
	*/
	
	/**
	 * 附件上传
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/files/upload/fdfs",method=RequestMethod.POST)
	@ResponseBody
	public String uplad(HttpServletRequest request,HttpServletResponse response){
		PropertiesReader pu=PropertiesReader.getInstance();
		String path = pu.getProperties("fdfs.HttpAddress");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap(); 
		InputStream is = null;
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
            	is = mf.getInputStream();
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
		return path ;
	}
	
	/**
	 *  获取所有文件
	 * @param pageable
	 * @param model
	 * @return
	 */
	
	
	@RequestMapping(value = "/fileslist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> fileListPager(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String,Object> result= new HashMap<String,Object>();
		// 标签
		String tagId=request.getParameter("tagId");
		// 标题
		String title=request.getParameter("title");
		// 审批状态
		String state = request.getParameter("state");
		// 来源
		String source = request.getParameter("source");
		// 录入人
		String insertName = request.getParameter("insertName");
		// 同步状态
		String synStatus = request.getParameter("synStatus") ;
		// 知识等级
		String level = request.getParameter("level");	
		// 发布日期
		String begincreateDate = request.getParameter("begincreateDate");	
//		Page<Files> pageAll=infoService.listFiles(this.getWhereClause(tagId, title,source,insertName,state,synStatus, 1), pageable);
//		Page<FilesDTO> page = filesListMajorization(pageAll) ;
		List<FileModule> fileList = filesService.listFileBaseRows(tagId, title, source, insertName, state, 1, synStatus, level, begincreateDate,pageable);
		BigInteger fileTotal = filesService.listFileBaseTotal(tagId, title, source, insertName, state, synStatus, level, begincreateDate,1);
		result.put("total",fileTotal);
		result.put("rows",fileList);
		return result;
	}
	
	public Specification<Files> getWhereClause(final String tagId,final String title,final String source,final String insertName, final String state, final String synStatus, final Integer infoType){
		return new Specification<Files>(){
			@Override
			public Predicate toPredicate(Root<Files> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate =cb.conjunction();
				//标签类型
				predicate.getExpressions().add(cb.equal(root.<String>get("infoType"), infoType));
				//创建时间倒叙
				query.orderBy(cb.desc(root.get("createDate")));	
				// 标签
				if(tagId!=null&&!"".equals(tagId)){
					predicate.getExpressions().add(cb.equal(root.joinSet("tags").get("id"), tagId));
				}
				// 标题
				if(title!=null&&!"".equals(title)){
					predicate.getExpressions().add(cb.like(root.<String>get("title"), "%"+title.trim()+"%"));
				}
				
				//录入人
				if(StringUtils.isNotBlank(insertName)){
					predicate.getExpressions().add(cb.like(root.<String>get("insertName"), "%"+insertName.trim()+"%"));
				}
				// 来源
				if(StringUtils.isNotBlank(source)){
					predicate.getExpressions().add(cb.like(root.<String>get("source"), "%"+source.trim()+"%"));
				}
				// 审批状态
				if(StringUtils.isNotBlank(state)){
					int status = Integer.parseInt(state);
					if(status != 10000){
						predicate.getExpressions().add(cb.equal(root.get("state"), status));
					}
				}
				// 同步状态
				if(StringUtils.isNotBlank(synStatus)) {
					if("1".equals(synStatus)) {	// 已同步
						predicate.getExpressions().add( cb.and(cb.notEqual(root.get("url"), ""), cb.isNotNull(root.get("url")))) ;
					} else if("2".equals(synStatus)) {	// 未同步
						predicate.getExpressions().add( cb.or(cb.equal( root.get("url"), ""), cb.isNull( root.get("url")))) ;
					} else { //全部
						
					}
				}
				return predicate;
			}
		};
	}
	
	/**
	 * 优化列表查询
	 * @param base
	 * @return
	 */
	public Page<FilesDTO> filesListMajorization(Page<Files> file) {
		List<Files> content = file.getContent() ;
		List<FilesDTO> nContent = Lists.newArrayList();
		for(Files f: content) {
			FilesDTO fdto = new FilesDTO(f.getId(), f.getTitle(), f.getSource(), f.getInsertName(), f.getCreateDate(), f.getState(), f.getUrl()) ;
			nContent.add(fdto) ;
		}
		Page<FilesDTO> result = new PageImpl<FilesDTO>(nContent, new PageRequest(file.getNumber(), file.getSize()), file.getTotalElements()) ;
		return result ;
	}
	
	/***
	 * 修改文件
	 * @param id
	 * @param base
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/files/update/{id}", method = RequestMethod.GET)
	public String toUpdate(@PathVariable("id")String id,Base base,Model model){
		model.addAttribute("files", filesService.getFileById(id));
		return "page/files/updateFile";
	}
	
	@RequestMapping(value="/files/update/{id}", method = RequestMethod.POST)
	public String doUpdate(@PathVariable("id")String id,Files files,Model model){
		try{
			filesService.saveFile(files);
			model.addAttribute("resMess", "更新成功！");
		}catch(Exception e){
			e.printStackTrace();
			model.addAttribute("resMess", "更新失败！");
		}
		return "redirect:/fileslist";
	}
	
	
	
	@RequestMapping(value="/files/details/{id}", method = RequestMethod.GET)
	public String doDetails(@PathVariable("id")String id,Files files,Model model){
		model.addAttribute("file", filesService.getFileById(id));
		return "page/files/seeFile";
	}
	/***
	 * 删除文件
	 * @param id
	 * @param base
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/files/delete/{id}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> doDelete(@PathVariable("id")String id,Model model){
		Map<String,Object> result=new HashMap<String,Object>();
		try{
			filesService.deleteFile(id);
			filesPageService.deleteOne(id);
			result.put("success",true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("success","删除失败！");
		}
		return result;
	}
 
  
    @RequestMapping(value="/files/findContent", method=RequestMethod.GET)
    @ResponseBody
    public String findContent(Model model,HttpServletRequest request,HttpServletResponse response){
        //System.out.println(new Date()); 
        String id=request.getParameter("id");
         String pageStr=request.getParameter("pageStr");
         int pageNo=Integer.valueOf(request.getParameter("pageNo"));
         int pageCount=Integer.valueOf(request.getParameter("pageCount"));
         int pageSize=1; 
         int lastPage=pageSize*(pageNo)+4;
         String[] pageStrs=null;
         FilesPageCount content=null;
        if(StringUtils.isEmpty(pageStr)){
//            pageStr=filesService.findContentPage(id);
            pageStrs=pageStr.split(",");
        }
         if(pageNo==1){
            content=  filesService.findContent(Integer.valueOf(pageStrs[(pageNo*pageSize-pageSize)]), Integer.valueOf(pageStrs[(pageNo*pageSize)])+3, id);
        }if(pageNo!=1){
            int st=Integer.valueOf(pageStrs[pageSize*pageNo-pageSize])+4;
            int ed=Integer.valueOf(pageStrs[(pageNo*pageSize)])-Integer.valueOf(pageStrs[pageSize*pageNo-pageSize]);
            content= filesService.findContent(st, ed, id);
        }
        //System.out.println(pageStrs);
        //System.out.println(pageStrs.length);
        //System.out.println(new Date()); 
        return new String("{id:"+ content.getContent() +"}");
    }
	/***
	 * 删除文件下的附件
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/attachment/delete/{url}/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> doAttachmentDelete(@PathVariable("id")String id, @PathVariable("url")String url, Model model,HttpServletRequest request){
		Map<String,Object> result = new HashMap<String,Object>();
		Files f = null;
		String dUrl = url.replaceAll("&", "/");
		String originalUrlStr = "";
		String urlArr[] = null;
		String newUrlStr = "";
		StringBuffer sb = new StringBuffer();
		try{
			// 删除文件系统上的附件
			url = url.substring(21);
			fdfsService.delete(url);
			// 更新数据库
			f = filesService.getFileById(id);
			if(f != null) {
				originalUrlStr = f.getUrl();
				if(!"".equals(originalUrlStr)) {
					urlArr = originalUrlStr.split(";");
					for(int i = 0; i < urlArr.length; i++) {
						if(urlArr[i] != null && !"".equals(urlArr[i])) {
							if(!urlArr[i].equals(dUrl)) {	// 将非删除的附件url重新组成新的url字符串
								sb.append(";");
								sb.append(urlArr[i]);
							}
						}
					}
				}
				newUrlStr = sb.toString();
			}
			f.setUrl(newUrlStr);
			filesService.saveFile(f);
			result.put("success", true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("fail", "删除失败！");
		}
		return result;
	}
	
	

	/**
	 * excel上传
	 * @param request
	 * @param response
	 * @return
	 */
	 @RequestMapping(value="/form/excelFileUpload",method=RequestMethod.POST)
	   	public @ResponseBody String doUploadExcel(HttpServletRequest request,HttpServletResponse response){
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
	   				List<Files> files = filesService.readXls(file.getAbsolutePath()) ;
	   				//System.out.println("files.size:" + files.size());

	   				// 建立表格与标间 的多对多关系
	   				List<Files> fileList = new ArrayList<Files>() ;
	   				if(files.size() != 0 && files != null) {
	   					Iterator<Files> ite = files.iterator();
	   					while(ite.hasNext()) {
	   						Files f = (Files)ite.next() ;
	   						Set<Tag> tagSet = new HashSet<Tag>() ;
	   						// 获取暂存的标签名称，通过“&”分隔
	   						String tags = f.getTagArray() ;
	   						String tagArr[] = tags.split("&") ;
	   						for(int i = 0 ; i < tagArr.length ; i++) {
	   							Tag tag = new Tag() ;
	   							if(tagArr[i] != null) {
	   								// 根据标签名称获取对应的标签对象
	   								tag = tagService.findTagByName(tagArr[i]) ;
	   							}
	   							if(tag != null) {
	   								tagSet.add(tag) ;
	   							}
	   						}
	   						f.setTags(tagSet) ;
	   						if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
	   							UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	   							User user = accountService.loadUserByUsername(userDetails.getUsername());
	   							f.setInsertId(user.getId());
	   							f.setInsertName(user.getAliasname());
	   						}
	   						f.setCreateDate(new Date()) ;	// 创建时间
	   						f.setUrl("");	// 设置url字段为空字符串，为了在同步附件时能通过函数CONCAT()拼接上传到fdfs后返回的地址字符串
	   						fileList.add(f) ;
	   					}
	   					filesService.saveFileList(fileList) ;
	   				}
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
	  * 同步文件附件
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value="/files/synFileAttachment", method=RequestMethod.GET)
	 @ResponseBody
	 public Map<String, Object> synFileAttachment() /*throws Exception*/ {
		 Map<String, Object> result = new HashMap<String, Object>() ;
		 PropertiesReader pr = PropertiesReader.getInstance() ;
		 String inFolder = pr.getProperties("FileDirectoryPath") ;
		 File inFile = new File(inFolder) ;
		 // 从磁盘目录遍历所有的附件
//    	 List<File> allFiles = new ArrayList<File>() ;
    	 TreeSet<File> allFiles = new TreeSet<File>();
    	 try {
    		ioUtil.setAllFiles(allFiles) ;
    		//List<File> af = ioUtil.getAllFiles();
    	 	ioUtil.traversalFile(inFile) ;
    	 	allFiles = ioUtil.getAllFiles();
    	 } catch(Exception e) {
    		result.put("fail", true);
    		result.put("msg", "遍历磁盘目录出错！") ;
    		e.printStackTrace() ;
       	 }
    	 
    	 // 附件处理
    	 Files files = new Files() ;	// 根据附件名称获取的Files对象
    	 if(allFiles != null) {
    		 Iterator<File> ite = allFiles.iterator() ;
    		 while(ite.hasNext()) {
    			 File f = ite.next() ;
	    		 // 大小
	    		 Long fileLength = f.length() ;
	    		 // 绝对路径
	    		 String fileAbsultePath = f.getAbsolutePath() ;
	    		 // 文件名
//	    		 String fileName = fileAbsultePath.substring(fileAbsultePath.lastIndexOf("\\")+1, fileAbsultePath.lastIndexOf(".")) ;	// Windows 环境
	    		 String fileName = fileAbsultePath.substring(fileAbsultePath.lastIndexOf("/")+1, fileAbsultePath.lastIndexOf(".")) ;	// Linux 环境
	    		 // 文件后缀
	    		 String fileExtName =  fileAbsultePath.substring(fileAbsultePath.lastIndexOf(".")+1) ;
	    		
	    		 // 存数据库
	    		 String path = "" ;
	    		 if(fileName.contains("_")) {
	    			 // 多附件，附件名称：标题_数字
	    			 files = filesService.getFileByName(fileName.substring(0, fileName.indexOf("_"))) ;
	    			 if(files != null) {
	         			// 上传到文件系统 fdfs
	     	    		 PropertiesReader pu=PropertiesReader.getInstance();
	     	    		 path = pu.getProperties("fdfs.HttpAddress");
	     	    		 if("xls".equals(fileExtName) || "jpg".equals(fileExtName) || "doc".equals(fileExtName) ||
	     	    				 "xlsx".equals(fileExtName) || "docx".equals(fileExtName) || "pdf".equals(fileExtName) || "rar".equals(fileExtName)){
	     	    		 	 InputStream inputStream = null;
							try {
								inputStream = new FileInputStream(f);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
	     	        		  try {
	     	                 	 path += fdfsService.upload(fileName, fileExtName, inputStream, fileLength);
							 	 if(path.equals(pu.getProperties("fdfs.HttpAddress"))) {	// 文件服务器返回地址为空
							 		 result.put("fail", true);
									 result.put("msg", "文件服务器fdfs未正确的返回附件地址！") ;
									 return result;
								 }
	     	     			  } catch (Exception e) {
	     	     				 result.put("fail", true);
	     	     				 result.put("msg", "上传文件系统出错！") ;
	     	     				 e.printStackTrace();
	     	     			  } 
	     	    		  }	else {
     	    				  result.put("fail", true);
     	    				  result.put("msg", "同步失败，暂不支持该类型《" + fileName + "." + fileExtName + "》的附件同步！");
     	    				  return result;
	     	    		  }
	     	    		 try {
	     	    			// 更新url字段，继续拼接url,通过“;”分隔
							filesService.updateUrl(files.getId(), path);
						} catch (Exception e) {
							result.put("fail", true);
							result.put("msg", "更新数据库时出错！") ;
							e.printStackTrace();
						}
	         		 }
	    		 }
    		 }
    	 }
    	 result.put("success", true) ;
    	 return result ;
    }
	 
	 /**
	  * 同步文件附件
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value="/files/synFileAttachment02", method=RequestMethod.GET)
	 @ResponseBody
	 public Map<String, Object> synFileAttachment02() /*throws Exception*/ {
		 Map<String, Object> result = new HashMap<String, Object>() ;
		 PropertiesReader pr = PropertiesReader.getInstance() ;
		 String inFolder = pr.getProperties("FileDirectoryPath") ;
		 File inFile = new File(inFolder) ;
		 // 从磁盘目录遍历所有的附件
//    	 List<File> allFiles = new ArrayList<File>() ;
    	 TreeSet<File> allFiles = new TreeSet<File>();
    	 try {
    		ioUtil.setAllFiles(allFiles) ;
    		//List<File> af = ioUtil.getAllFiles();
    	 	ioUtil.traversalFile(inFile) ;
    	 	allFiles = ioUtil.getAllFiles();
    	 } catch(Exception e) {
    		result.put("fail", true);
    		result.put("msg", "遍历磁盘目录出错！") ;
    		e.printStackTrace() ;
       	 }
    	 
    	 // 附件处理
    	 result = filesService.modifyUrl(allFiles);
    	 result.put("success", true) ;
    	 return result ;
    }
	 
	 /**
	  * 同步文件附件(Original)
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value="/files/synFileAttachmentOriginal", method=RequestMethod.GET)
	 @ResponseBody
	 public Map<String, Object> synFileAttachmentOriginal() /*throws Exception*/ {
		 Map<String, Object> result = new HashMap<String, Object>() ;
		 PropertiesReader pr = PropertiesReader.getInstance() ;
		 String inFolder = pr.getProperties("FileDirectoryPath") ;
		 File inFile = new File(inFolder) ;
		 // 从磁盘目录遍历所有的附件
//    	 List<File> allFiles = new ArrayList<File>() ;
    	 TreeSet<File> allFiles = new TreeSet<File>();
    	 try {
    		ioUtil.setAllFiles(allFiles) ;
    		//List<File> af = ioUtil.getAllFiles();
    	 	ioUtil.traversalFile(inFile) ;
    	 	allFiles = ioUtil.getAllFiles();
    	 } catch(Exception e) {
    		result.put("fail", true);
    		result.put("msg", "遍历磁盘目录出错！") ;
    		e.printStackTrace() ;
       	 }
    	 
    	 // 附件处理
    	 Files files = new Files() ;	// 根据附件名称获取的Files对象
    	 if(allFiles != null) {
    		 Iterator<File> ite = allFiles.iterator() ;
    		 while(ite.hasNext()) {
    			 File f = ite.next() ;
	    		 // 大小
	    		 Long fileLength = f.length() ;
	    		 // 绝对路径
	    		 String fileAbsultePath = f.getAbsolutePath() ;
	    		 // 文件名
//	    		 String fileName = fileAbsultePath.substring(fileAbsultePath.lastIndexOf("\\")+1, fileAbsultePath.lastIndexOf(".")) ;	// Windows 环境
	    		 String fileName = fileAbsultePath.substring(fileAbsultePath.lastIndexOf("/")+1, fileAbsultePath.lastIndexOf(".")) ;	// Linux 环境
	    		 // 文件后缀
	    		 String fileExtName =  fileAbsultePath.substring(fileAbsultePath.lastIndexOf(".")+1) ;
	    		
	    		 // 存数据库
	    		 String path = "" ;
	    		 if(fileName.contains("_")) {
	    			 // 多附件，附件名称：标题_数字
	    			 files = filesService.getFileByName(fileName.substring(0, fileName.indexOf("_"))) ;
	    			 if(files != null) {
	         			// 上传到文件系统 fdfs
	     	    		 PropertiesReader pu=PropertiesReader.getInstance();
	     	    		 path = pu.getProperties("fdfs.HttpAddress");
	     	    		 if("xls".equals(fileExtName) || "jpg".equals(fileExtName) || "doc".equals(fileExtName) ||
	     	    				 "xlsx".equals(fileExtName) || "docx".equals(fileExtName) || "pdf".equals(fileExtName) || "rar".equals(fileExtName)){
	     	    		 	 InputStream inputStream = null;
							try {
								inputStream = new FileInputStream(f);
							} catch (FileNotFoundException e1) {
								e1.printStackTrace();
							}
	     	        		  try {
	     	                 	 path += fdfsService.upload(fileName, fileExtName, inputStream, fileLength);
							 	 if(path.equals(pu.getProperties("fdfs.HttpAddress"))) {	// 文件服务器返回地址为空
							 		 result.put("fail", true);
									 result.put("msg", "文件服务器fdfs未正确的返回附件地址！") ;
									 return result;
								 }
	     	     			  } catch (Exception e) {
	     	     				 result.put("fail", true);
	     	     				 result.put("msg", "上传文件系统出错！") ;
	     	     				 e.printStackTrace();
	     	     			  } 
	     	    		  }	else {
     	    				  result.put("fail", true);
     	    				  result.put("msg", "同步失败，暂不支持该类型《" + fileName + "." + fileExtName + "》的附件同步！");
     	    				  return result;
	     	    		  }
	     	    		 try {
	     	    			// 更新url字段，继续拼接url,通过“;”分隔
							filesService.updateUrlOriginal(files.getId(), path);
						} catch (Exception e) {
							result.put("fail", true);
							result.put("msg", "更新数据库时出错！") ;
							e.printStackTrace();
						}
	         		 }
	    		 }
    		 }
    	 }
    	 result.put("success", true) ;
    	 return result ;
    }
	 
	 /**
	  * 反选标签
	  */
	 @RequestMapping(value="/file/tags/{id}", method = RequestMethod.POST)
		@ResponseBody
		public Set<Tag> doFileTags(@PathVariable("id")String id,Form form,Model model){
			return filesService.getFileById(id).getTags() ;
		}
	 
	 
	 /**
	  * 文件预览
	  * @param request
	  * @return
	  */
	 @RequestMapping(value="/files/previewFiles", method=RequestMethod.GET)
	 @ResponseBody
	 public Map<String, Object> previewFiles(HttpServletRequest request) {
		 Map<String, Object> result = new HashMap<String, Object>() ;
		 String id = "" ;
		 Files f = new Files() ;
		 try {
			id = request.getParameter("id") ;
			 if(id != null && !"".equals(id)) {
				 f = filesService.getFileById(id) ;
				 if(f != null) {
					 result.put("files", f) ;
					 result.put("success", true) ;
				 }
			 }
		} catch (Exception e) {
			result.put("success", false) ;
			result.put("msg", "预览失败！") ;
			e.printStackTrace();
		}
		 return result ;
	 }
	 
	 /**
	  * 跳转到审批页面
	  * @param id
	  * @param model
	  * @return
	  */
	 @RequestMapping(value="/file/audit/{id}", method=RequestMethod.GET)
	 public String toAuditFilesPage(@PathVariable("id")String id, Model model) {
		 Files f = filesService.getFileById(id);
		    String urlStr = "";
		    String urlArr[] = null;
		    List<String> urlList = new ArrayList<String>();
		    if(f != null) {
		    	urlStr = f.getUrl();
		    	if(urlStr != null && !"".equals(urlStr)) {
		    		urlArr = urlStr.split(";");
		    		for(int i = 0; i < urlArr.length; i++) {
		    			if(urlArr[i] != null && !"".equals(urlArr[i])) {
		    				urlList.add(urlArr[i]);
		    			}
		    		}
		    	}
		    }
		 // 附件url的List   
		 model.addAttribute("urlList", urlList);
		 model.addAttribute("file", f);
		 return "page/files/auditFiles" ; 
	 }
}

