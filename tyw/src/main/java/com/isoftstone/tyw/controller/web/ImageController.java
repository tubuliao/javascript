package com.isoftstone.tyw.controller.web;



import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.jexl2.UnifiedJEXL.Exception;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.isoftstone.tyw.dto.info.ImageDTO;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.Image;
import com.isoftstone.tyw.entity.info.ImageModule;
import com.isoftstone.tyw.entity.info.Source;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.BaseService;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.FdfsService;
import com.isoftstone.tyw.service.ImageService;
import com.isoftstone.tyw.service.InfoService;
import com.isoftstone.tyw.service.SourceService;
import com.isoftstone.tyw.util.PropertiesReader;

@Controller
public class ImageController {
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private InfoService infoService ;
	
	@Autowired
	private FdfsService fdfsService ;
	
	@Autowired
	private CommonService commonService ;
	
	@Autowired
	private AccountService accountService ;
	
	@Autowired
	private BaseService baseService;
	
	@Autowired
	private SourceService sourceService;
	
	
	/**
	 * 跳转到图片页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/image", method=RequestMethod.GET)
	public String image(Model model) {
		model.addAttribute(new Image()) ;
		model.addAttribute("kLevel", baseService.getKnowledgeLevelSort("5"));
		return "page/image/image" ;
	}
	
	/**
	 * 查询图片列表
	 * @param pageable
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/imageList", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> imageListPager(@PageableDefaults(value=10)Pageable pageable, Model model, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
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
		// 发布日期
		String begincreateDate = request.getParameter("begincreateDate");
		// 知识等级
		String level = request.getParameter("level");
		Map<String, Object> page = imageService.listImageBaseRows(tagId, title,source,insertName,state, 5, begincreateDate, level, pageable);
		result.put("total",page.get("total"));  
		result.put("rows", page.get("rows")); 
		return result ;
	}
	
	public Specification<Base> getWhereClause(final String tagId,final String title,final String source,final String insertName, final String state,final Integer infoType){
		return new Specification<Base>(){
			@Override
			public Predicate toPredicate(Root<Base> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				// 标签类型
				predicate.getExpressions().add(
						cb.equal(root.<String> get("infoType"), infoType));
				// 创建时间倒叙
				query.orderBy(cb.desc(root.get("createDate")));
				// 标签
				if (tagId != null && !"".equals(tagId)) {
					predicate.getExpressions().add(
							cb.equal(root.joinSet("tags").get("id"), tagId));
				}
				// 标题
				if (title != null && !"".equals(title)) {
					predicate.getExpressions().add(
							cb.like(root.<String> get("title"),
									"%" + title.trim() + "%"));
				}
				
				//录入人
				if(StringUtils.isNotBlank(insertName)){
					predicate.getExpressions().add(cb.like(root.<String>get("insertName"), "%"+insertName.trim()+"%"));
				}
				// 来源
				if(StringUtils.isNotBlank(source)){
					predicate.getExpressions().add(cb.like(root.<String>get("source"), "%"+source.trim()+"%"));
				}
				//状态
				if(StringUtils.isNotBlank(state)){
					int status = Integer.parseInt(state);
					if(status != 10000){
						predicate.getExpressions().add(cb.equal(root.get("state"), status));
					}
				}
				return predicate;
			}
		};
	}
	
	/**
	 * 列表查询优化
	 * @param base
	 * @return
	 */
	public Page<ImageDTO> imageListMajorization(Page<Base> base) {
		List<Base> content = base.getContent() ;
		List<ImageDTO> nContent = new ArrayList<ImageDTO>() ;
		for(Base b: content) {
			ImageDTO idto = new ImageDTO(b.getId(), b.getTitle(), b.getSource(), b.getInsertName(), b.getCreateDate(), b.getState()) ;
			nContent.add(idto) ;
		}
		Page<ImageDTO> result = new PageImpl<ImageDTO>(nContent, new PageRequest(base.getNumber(), base.getSize()), base.getTotalElements()) ;
		return result ;
	}

	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(value="/image/addImage", method=RequestMethod.GET)
	public String toAddImagePage() {
		return "/page/image/addImage" ;
	}
	 
	/**
	 * 图片上传fdfs服务器
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/image/upload/fdfs", method = RequestMethod.POST)
	@ResponseBody
	public String imageUpload(HttpServletRequest request, HttpServletResponse response) {
		PropertiesReader pu = PropertiesReader.getInstance();
		String path = pu.getProperties("fdfs.HttpAddress");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();
			String fileName = mf.getOriginalFilename();
			long fileLength = mf.getSize();
			String fileExtName = "";
			if (fileName.contains(".")) {
				fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
			}
			InputStream is = null;
			try {
				path += fdfsService.upload(fileName, fileExtName,
						mf.getInputStream(), fileLength);
				is = mf.getInputStream();
			} catch (Exception e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return path;
	}
	
	/**
	 * 保存图片
	 * @param image
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/image/save", method = RequestMethod.POST)
	@ResponseBody
	public void doSaveImage(Image image, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultJson = "{'success':true}";
		String id = request.getParameter("id") ;
		
		String originalUrl = "" ;
		String newUrl = "" ;
		String originalName = "" ;
		String newName = "" ;
		Image imageOriginal = new Image() ;
		// 发布时间
    	String begincreateDate = "";
    	SimpleDateFormat sdf02 = new SimpleDateFormat("yyyy-MM-dd");
    	//　填写的来源
    	String source = "";
    	// 根据source获取的Source对象
    	Source s = null;
		if(id == null) {	// 新增
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				User user = accountService.loadUserByUsername(userDetails.getUsername());
				image.setInsertId(user.getId());
				image.setInsertName(user.getAliasname());
			}
			// 发布日期
    		begincreateDate = request.getParameter("begincreateDate02");
    		if(!"".equals(begincreateDate)) {	// 选择了发布日期
    			try {
					image.setBegincreateDate(sdf02.parse(begincreateDate));
				} catch (ParseException e) {
					e.printStackTrace();
				}
    		} else { // 没有选择发布日期，根据来源查询对应的实施日期
    			source = image.getSource();
    			if(!"".equals(source)) {
    				s = sourceService.getSourceByStandardName(source);
    				if(s != null && s.getExecuteDate() != null) { // 来源存在且有实施日期
    					image.setBegincreateDate(s.getExecuteDate());
    				}
    			}
    		}
    		// 若发布时间为空，默认为"1970-01-01"
    		if(image.getBegincreateDate() == null) {
    			try {
					image.setBegincreateDate(sdf02.parse("1970-01-01"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
    		}
		} else {	// 修改
			try {	// 保持审批时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
				if(!"".equals(request.getParameter("checkDate02"))) {
					image.setCheckDate(sdf.parse(request.getParameter("checkDate02"))) ;
				}
			
				// 图片url拼接
				imageOriginal = imageService.getImageById(id) ;
				// url
				originalUrl = imageOriginal.getUrl() ;	// 原始的url
				newUrl = image.getUrl() ;	// 修改后可能新增的url 
				if(!"".equals(newUrl)) {	// 有新增的url
					newUrl = originalUrl + newUrl ;
				} else {	// 无新增的url
					newUrl = originalUrl ;
				}
				image.setUrl(newUrl) ;
				// name
				originalName = imageOriginal.getDescription() ;	// 原始的name
				newName = image.getDescription() ;	// 修改可能新增的name
				if(!"".equals(newName)) { 	// 修改中新增了图片
					newName = originalName + newName  ;
				} else {	// 无新增的图片 
					newName = originalName ;
				}
				image.setDescription(newName) ;	// info_base表中的description字段暂存图片名称
				// 修改人信息
				if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
					UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					User user = accountService.loadUserByUsername(userDetails.getUsername());
					image.setModifyId(user.getId());
					image.setModifyName(user.getAliasname());
					image.setModifyDate(new Date());
				}
				// 发布日期
        		begincreateDate = request.getParameter("begincreateDate02");
        		if(!"".equals(begincreateDate)) {	// 选择了发布日期
        			image.setBegincreateDate(sdf02.parse(begincreateDate));
        		} else { // 没有选择发布日期，根据来源查询对应的实施日期
        			source = image.getSource();
        			if(!"".equals(source)) {
        				s = sourceService.getSourceByStandardName(source);
        				if(s != null && s.getExecuteDate() != null) { // 来源存在且有实施日期
        					image.setBegincreateDate(s.getExecuteDate());
        				}
        			}
        		}
        		// 若发布时间为空，默认为"1970-01-01"
        		if(image.getBegincreateDate() == null) {
        			image.setBegincreateDate(sdf02.parse("1970-01-01"));
        		}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		try {
		    imageService.saveOne(image) ;
			resultJson = "{'success':true}";
		 }catch (Exception e) {   
	         e.printStackTrace();  
	         resultJson = "{'msg':'保存失败!'}";
	    } 
		commonService.responseJsonBody(response, resultJson);
	}
	
	/**
	 * 删除图片
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/image/delete/{id}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doDeleteImage(@PathVariable("id")String id) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		try {
			imageService.deleteOne(id) ;
			result.put("success", true) ;
		} catch(Exception e) {
			result.put("success", false) ;
			result.put("msg", "删除失败！") ;
			e.printStackTrace() ;
		}
		return result ;
	}
	
	/**
	 * 跳转到修改页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/image/edit/{id}", method=RequestMethod.GET)
	public String doEditImage(@PathVariable("id")String id, Model model) {
		Image image = imageService.getImageById(id) ;
		String urlStr = "" ;
		String urlArr[] ;
		String nameStr = "" ;	// 图片名称字符串
		String nameArr[] ;	// 图片名称数组
		List<String> urlList = new ArrayList<String>() ;
		List<String> nameList = new ArrayList<String>() ;	// 传递到修改页面的名称列表
		if(image != null) {
			// 图片附件url
			urlStr = image.getUrl() ;
			if(!"".equals(urlStr)) {
				urlArr = urlStr.split(";") ;
				for(int i = 0 ; i < urlArr.length ; i++) {
						if(urlArr[i] != null && !"".equals(urlArr[i])) {
							urlList.add(urlArr[i]) ;
						}
				}
			}
			// 图片附件的原名称，保存在info_base的description字段中，通过“;”分隔
			nameStr = image.getDescription() ;
			if(!"".equals(nameStr)) {
				nameArr = nameStr.split(";") ;
				for(int j = 0 ; j < nameArr.length ; j++) {
					if(nameArr[j] != null && !"".equals(nameArr[j])) {
						nameList.add(nameArr[j]) ;
					}
				}
			}
		}
		
		model.addAttribute("image", image) ;
		model.addAttribute("urlList", urlList) ;
		model.addAttribute("nameList", nameList) ;
		return "/page/image/editImage" ;
	}
	
	
	/**
	 * 反选标签
	 * 
	 * @param id
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/image/tags/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Set<Tag> doImageTags(@PathVariable("id") String id, Image image, Model model) {
		return imageService.getImageById(id).getTags();
	}
	
	/**
	 * 审批通过/驳回
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/image/approve/{id}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approveImage(@PathVariable("id") String id,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 审批状态值
		Integer status = Integer.parseInt(request.getParameter("status"));
		// 驳回信息
		String auditInfo = request.getParameter("auditInfo");
		// 权重类型（标题/来源）
		String weightType = request.getParameter("weightType");
		// 权重值（1~10）
		String weightVal = request.getParameter("weightVal");
		// 来源信息
		String baseSource = request.getParameter("baseSource");
		User user = null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = accountService.loadUserByUsername(userDetails.getUsername());
		}
		String checkId = (user != null) ? user.getId() : "";
		String checkName = (user != null) ? user.getAliasname() : "";
		try {
			if (auditInfo != null && !"".equals(auditInfo)) {	// 驳回
				infoService.modifyStatus(status, checkId, checkName,
						new Date(), auditInfo, id);
			} else {	// 审批
				infoService.modifyStatus(status, checkId, checkName,
						new Date(), id);
			}
			// 二次审批通过 权重值设置
			if(weightType != null && !"".equals(weightType) && weightVal != null && !"".equals(weightVal)) {
				 if("title".equals(weightType)) {	// 标题
					 baseService.setTitleWeighing(id, Integer.valueOf(weightVal));
				 } else if("source".equals(weightType) && baseSource != null && !"".equals(baseSource))	{	// 来源
					 baseService.setSourceWeighing(baseSource, Integer.valueOf(weightVal));
				 }
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false) ;
			result.put("msg", "审批失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 预览图片
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/image/preview", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> imagePreview(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		String id = request.getParameter("imageId") ;
		Image image = new Image() ;
		try {
			image = imageService.getImageById(id) ;
			if(image != null) {
				result.put("imageDetail", image) ;
			}
			result.put("success", true) ;
		} catch(Exception e) {
			result.put("success", false) ;
			result.put("msg", "预览失败！") ;
			e.printStackTrace() ;
		}
		return result ;
	}
	
	/**
	 * 图片删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/img/deleteImg", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteImageUrl(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		String url = request.getParameter("imgUrl") ;	// 需要删除的image的url
		String imgUrl = "" ;	// 格式转换后的url
		String fileNameForDel = "" ;	// 删除fdfs上的文件
		String originalUrl = "" ;	// 原image的url
		String originalName = "" ; 	// 原image的name
		String urlArr[] ;	// 通过";"拆分后的url数组
		String nameArr[] ;	// name数组
		StringBuffer sb = new StringBuffer() ;	// 新的url
		StringBuffer sbName = new StringBuffer() ;	// 新的name
		if(url != null && !"".equals(url)) {
			imgUrl = url.replaceAll("&", "/") ;
		}
		try {
			String id = request.getParameter("imgId") ;
			Image image = new Image() ;
			Integer flag = 0 ;
			if(id != null && !"".equals(id)) {
				image = imageService.getImageById(id) ;
				originalUrl = image.getUrl() ;
				originalName = image.getDescription();
				if(!"".equals(originalUrl)) {
					urlArr = originalUrl.split(";") ;
					for(int i = 0 ; i < urlArr.length ; i++) {
						if(urlArr[i] != null && !"".equals(urlArr[i])) {
							if(!urlArr[i].equals(imgUrl)) {
								sb.append(urlArr[i]) ;
								sb.append(";") ;
							} else {
								flag = i ;	// 需要删除的图片数据在拆分后的数组中的index
							}
						}
					}
				}
				// 删除description字段中对应的name
				if(!"".equals(originalName)) {
					nameArr = originalName.split(";") ;
					for(int j = 0 ; j < nameArr.length ; j++) {
						if(nameArr[j] != null && !"".equals(nameArr[j])) {
							if( j != flag) {
								sbName.append(nameArr[j]) ;
								sbName.append(";") ;
							}
						}
					}
				}
			}
			// 保存删除后的图片信息
			image.setUrl(sb.toString()) ;
			image.setDescription(sbName.toString()) ;
			imageService.saveOne(image) ;
			
			// 删除文件系统上的内容
			try {
				fileNameForDel = imgUrl.substring(imgUrl.indexOf("g")) ;
				fdfsService.delete(fileNameForDel) ;
			} catch (java.lang.Exception e) {
				result.put("success", false) ;
				result.put("msg", "删除fdfs上的内容失败！") ;
				e.printStackTrace();
			}
			
			result.put("success", true) ;
		} catch (java.lang.Exception e) {
			result.put("success", false) ;
			result.put("msg", "删除失败！") ;
			e.printStackTrace();
		}
		return result ;
	}
	
	/**
	 * 跳转到审批页面
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/image/audit/{id}", method=RequestMethod.GET)
	public String toAuditOrRejectPage(@PathVariable("id")String id, HttpServletRequest request, Model model){
		Image image = imageService.getImageById(id) ;
		String urlStr = "" ;
		String urlArr[] ;
		String nameStr = "" ;	// 图片名称字符串
		String nameArr[] ;	// 图片名称数组
		List<String> urlList = new ArrayList<String>() ;
		List<String> nameList = new ArrayList<String>() ;	// 传递到修改页面的名称列表
		if(image != null) {
			// 图片附件的url
			urlStr = image.getUrl() ;
			if(!"".equals(urlStr)) {
				urlArr = urlStr.split(";") ;
				for(int i = 0 ; i < urlArr.length ; i++) {
						if(urlArr[i] != null && !"".equals(urlArr[i])) {
							urlList.add(urlArr[i]) ;
						}
				}
			}
			// 图片附件的原名称，保存在info_base的description字段中，通过“;”分隔
			nameStr = image.getDescription() ;
			if(!"".equals(nameStr)) {
				nameArr = nameStr.split(";") ;
				for(int j = 0 ; j < nameArr.length ; j++) {
					if(nameArr[j] != null && !"".equals(nameArr[j])) {
						nameList.add(nameArr[j]) ;
					}
				}
			}
		}
		
		model.addAttribute("image", image) ;
		model.addAttribute("urlList", urlList) ;
		model.addAttribute("nameList", nameList) ;
		return "/page/image/auditImage" ;
	}
}

