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

import com.isoftstone.tyw.dto.info.VideoDTO;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.Image;
import com.isoftstone.tyw.entity.info.Source;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.entity.info.Video;
import com.isoftstone.tyw.entity.info.VideoModule;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.BaseService;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.FdfsService;
import com.isoftstone.tyw.service.InfoService;
import com.isoftstone.tyw.service.SourceService;
import com.isoftstone.tyw.service.VideoService;
import com.isoftstone.tyw.util.PropertiesReader;

@Controller
public class VideoController {
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private InfoService infoService ;
	
	@Autowired
	private FdfsService fdfsService ;
	
	@Autowired
	private AccountService accountService ;
	
	@Autowired
	private CommonService commonService ;
	 
	@Autowired
	private BaseService baseService;
	
	@Autowired
	private SourceService sourceService;
	
	/**
	 * 跳转到视频页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/video", method=RequestMethod.GET)
	public String video(Model model) {
		model.addAttribute(new Video()) ;
		model.addAttribute("kLevel", baseService.getKnowledgeLevelSort("3"));
		return "page/video/video" ;
	}
	
	/**
	 * 查询视频列表
	 * @param pageable
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/videoList", method=RequestMethod.POST)
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
		// 知识等级
		String level = request.getParameter("level");	
		// 发布日期
		String begincreateDate = request.getParameter("begincreateDate");	
//		Page<Base> pageAll=infoService.listBase(this.getWhereClause(tagId, title,source,insertName,state, 3), pageable);
//		Page<VideoDTO> page = videoListMajorization(pageAll) ;
		List<VideoModule> videoList = videoService.listVideoBaseRows(tagId, title, source, insertName, state, 3, level, begincreateDate,pageable);
		BigInteger videoTotal = videoService.listVideoBaseTotal(tagId, title, source, insertName, state, level, begincreateDate,3);
		result.put("total",videoTotal);
		result.put("rows",videoList);
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
	
	public Page<VideoDTO> videoListMajorization(Page<Base> base) {
		List<Base> content = base.getContent();
		List<VideoDTO> nContent = new ArrayList<VideoDTO>() ;
		for(Base b: content) {
			VideoDTO vdto = new VideoDTO(b.getId(), b.getTitle(), b.getSource(), b.getInsertName(), b.getCreateDate(), b.getState()) ;
			nContent.add(vdto) ;
		}
		Page<VideoDTO> result = new PageImpl<VideoDTO>(nContent, new PageRequest(base.getNumber(), base.getSize()), base.getTotalElements()) ;
		return result ;
	}
	
	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping(value="/video/addVideo", method=RequestMethod.GET)
	public String toAddImagePage() {
		return "/page/video/addVideo" ;
	}
	
	/**
	 * 视频上传fdfs服务器
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/video/upload/fdfs", method = RequestMethod.POST)
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
	 * 保存视频
	 * @param video
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/video/save", method = RequestMethod.POST)
	@ResponseBody
	public void doSaveVideo(Video video, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String resultJson = "{'success':true}";
		String id = request.getParameter("id") ;
		// 发布时间
    	String begincreateDate = "";
    	SimpleDateFormat sdf02 = new SimpleDateFormat("yyyy-MM-dd");
    	//　填写的来源
    	String source = "";
    	// 根据source获取的Source对象
    	Source s = null;
		
		if(id == null) {	// 新增
			try {
				if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
					UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					User user = accountService.loadUserByUsername(userDetails.getUsername());
					video.setInsertId(user.getId());
					video.setInsertName(user.getAliasname());
				}
				// 发布日期
				begincreateDate = request.getParameter("begincreateDate02");
				if(!"".equals(begincreateDate)) {	// 选择了发布日期
					video.setBegincreateDate(sdf02.parse(begincreateDate));
				} else { // 没有选择发布日期，根据来源查询对应的实施日期
					source = video.getSource();
					if(!"".equals(source)) {
						s = sourceService.getSourceByStandardName(source);
						if(s != null && s.getExecuteDate() != null) { // 来源存在且有实施日期
							video.setBegincreateDate(s.getExecuteDate());
						}
					}
				}
				// 若发布时间为空，默认为"1970-01-01"
        		if(video.getBegincreateDate() == null) {
        			video.setBegincreateDate(sdf02.parse("1970-01-01"));
        		}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {	// 修改
			try {
				if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
					UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					User user = accountService.loadUserByUsername(userDetails.getUsername());
					video.setModifyId(user.getId());
					video.setModifyName(user.getAliasname());
					video.setModifyDate(new Date());
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
				if(!"".equals(request.getParameter("checkDate02"))) {
					video.setCheckDate(sdf.parse(request.getParameter("checkDate02"))) ;
				}
				// 发布日期
        		begincreateDate = request.getParameter("begincreateDate02");
        		if(!"".equals(begincreateDate)) {	// 选择了发布日期
        			video.setBegincreateDate(sdf02.parse(begincreateDate));
        		} else { // 没有选择发布日期，根据来源查询对应的实施日期
        			source = video.getSource();
        			if(!"".equals(source)) {
        				s = sourceService.getSourceByStandardName(source);
        				if(s != null && s.getExecuteDate() != null) { // 来源存在且有实施日期
        					video.setBegincreateDate(s.getExecuteDate());
        				}
        			}
        		}
        		// 若发布时间为空，默认为"1970-01-01"
        		if(video.getBegincreateDate() == null) {
        			video.setBegincreateDate(sdf02.parse("1970-01-01"));
        		}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		try {
		    videoService.saveOne(video) ;
			resultJson = "{'success':true}";
		 }catch (Exception e) {   
	         e.printStackTrace();  
	         resultJson = "{'msg':'保存失败!'}";
	    } 
		commonService.responseJsonBody(response, resultJson);
	}
	
	
	/**
	 * 删除视频
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/video/delete/{id}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doDeleteImage(@PathVariable("id")String id) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		try {
			videoService.deleteOne(id) ;
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
	@RequestMapping(value="/video/edit/{id}", method=RequestMethod.GET)
	public String doEditImage(@PathVariable("id")String id, Model model) {
		model.addAttribute("video", videoService.getVideoById(id)) ;
		return "/page/video/editVideo" ;
	}
	
	/**
	 * 反选标签
	 * 
	 * @param id
	 * @param video
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/video/tags/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Set<Tag> doImageTags(@PathVariable("id") String id, Image image, Model model) {
		return videoService.getVideoById(id).getTags();
	}
	
	/**
	 * 审批通过/驳回
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/video/approve/{id}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approveVideo(@PathVariable("id") String id,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer status = Integer.parseInt(request.getParameter("status"));
		String auditInfo = request.getParameter("auditInfo");
		String weightType = request.getParameter("weightType");
		String weightVal = request.getParameter("weightVal");
		String baseSource = request.getParameter("baseSource");
		User user = null;
		if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			user = accountService.loadUserByUsername(userDetails.getUsername());
		}
		String checkId = (user != null) ? user.getId() : "";
		String checkName = (user != null) ? user.getAliasname() : "";
		try {
			if (auditInfo != null && !"".equals(auditInfo)) {
				infoService.modifyStatus(status, checkId, checkName,
						new Date(), auditInfo, id);
			} else {
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
	 * 预览视频
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/video/preview", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> imagePreview(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		String id = request.getParameter("videoId") ;
		Video video = new Video() ;
		try {
			video = videoService.getVideoById(id) ;
			if(video != null) {
				result.put("videoDetail", video) ;
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
	 * 修改界面删除视频附件
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/video/delVideo", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> delVideoUrl(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		String id = request.getParameter("id") ;
		Video v = new Video() ;
		if(id != null && !"".equals(id)) {
			v = videoService.getVideoById(id) ;
		}
		String url = "" ;
		if(v != null) {
			url = v.getUrl() ;
		}
		String fileName = "" ;
		if(url != "") {
			fileName = url.substring(url.indexOf("g")) ;
		}
		try {
			if(fileName != null && !"".equals(fileName)) {
				fdfsService.delete(fileName) ;	// 删除fdfs文件
			}
			if(id != null && !"".equals(id)) {
				videoService.deleteVideoUrl(id) ;	// 置空数据库url字段
			}
			result.put("success", true) ;
		} catch (java.lang.Exception e) {
			result.put("success", false) ;
			result.put("msg", "更新数据库url出错！") ;
			e.printStackTrace();
		}
		return result ;
	}
	
	@RequestMapping(value="/video/audit/{id}", method=RequestMethod.GET)
	public String toAuditVideoPage(@PathVariable("id")String id, Model model) {
		model.addAttribute("video", videoService.getVideoById(id)) ;
		return "/page/video/auditVideo" ;
	}
	
}

