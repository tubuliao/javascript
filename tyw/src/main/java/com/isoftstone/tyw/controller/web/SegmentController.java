package com.isoftstone.tyw.controller.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.google.common.collect.Lists;
import com.isoftstone.tyw.dto.info.SegmentDTO;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.Segment;
import com.isoftstone.tyw.entity.info.SegmentModule;
import com.isoftstone.tyw.entity.info.Source;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.BaseService;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.InfoService;
import com.isoftstone.tyw.service.SourceService;
import com.isoftstone.tyw.service.TagService;
import com.isoftstone.tyw.util.IOUtil;

/**
 * @author qzjiang
 *
 */
@Controller
public class SegmentController {
	@Autowired
	private InfoService infoService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private IOUtil ioUtil ;
	@Autowired
	private TagService tagService ;
	@Autowired
	private BaseService baseService;
	@Autowired
	private SourceService sourceService;
	
	/**
	 *  返回所有知识切片列表
	 * @param pageable
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/segmentlist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> segmentListPager(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String,Object> result = new HashMap<String,Object>();
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
		Map<String, Object> page = infoService.listBaseRows(tagId, title,source,insertName,state, 2, level, begincreateDate, pageable);
	    result.put("total",page.get("total"));  
		result.put("rows", page.get("rows")); 
		return result;
	}
	
	public Specification<Base> getWhereClause(final String tagId,final String title,final String source,final String insertName, final String state,final Integer infoType){
		return new Specification<Base>(){
			@Override
			public Predicate toPredicate(Root<Base> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate =cb.conjunction();
				//标签类型
				predicate.getExpressions().add(cb.equal(root.<String>get("infoType"), infoType));
				//创建时间倒叙
				query.orderBy(cb.desc(root.get("modifyDate")));	
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
	 * 优化查询列表
	 * @param base
	 * @return
	 */
	public Page<SegmentDTO> segmentListMajorization(Page<Base> base) {
		List<Base> content = base.getContent();
		List<SegmentDTO> nContent = Lists.newArrayList() ;
		for(Base b : content) {
			Segment s = infoService.getSegmentById(b.getId()) ;
			SegmentDTO sDTO = new SegmentDTO(b.getId(), b.getTitle(), s.getSegItem(), b.getSource(), b.getInsertName(), b.getModifyDate(), b.getState()) ;
			nContent.add(sDTO) ;
		}
		Page<SegmentDTO> result = new PageImpl<SegmentDTO>(nContent, new PageRequest(base.getNumber(), base.getSize()), base.getTotalElements()) ;
		return result ;
	}
	
	/**
	 * 知识切片列表页
	 * @return
	 */
    @RequestMapping(value="/segment",method=RequestMethod.GET)   
    public String toAdd(Model model) throws Exception{   
    	model.addAttribute("kLevel", baseService.getKnowledgeLevelSort("2"));
        return "page/segment/segment";   
    }   
    
    
    @RequestMapping(value="/segment/add",method=RequestMethod.GET)   
    public String toAddPage(Model model) throws Exception{   
        return "page/segment/addSegment";   
    }
    
    /**
     *  保存
     * @param segment
     * @param model
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping(value="/segment/save",method=RequestMethod.POST)   
    @ResponseBody
    public void doSave(Segment segment,Model model,HttpServletResponse response, HttpServletRequest request) throws Exception{ 
    	String resultJson = "{'success':true}";
    	String id = request.getParameter("id") ;
    	// 发布时间
    	String begincreateDate = "";
    	SimpleDateFormat sdf02 = new SimpleDateFormat("yyyy-MM-dd");
    	//　填写的来源
    	String source = "";
    	// 根据source获取的Source对象
    	Source s = null;
    	try {  
    		if(id == null) {	// 新建
        		segment.setCreateDate(new Date()) ;	// 只在新建是创建createDate,不再修改
        		// 发布日期
        		begincreateDate = request.getParameter("begincreateDate02");
        		if(!"".equals(begincreateDate)) {	// 选择了发布日期
        			segment.setBegincreateDate(sdf02.parse(begincreateDate));
        		} else { // 没有选择发布日期，根据来源查询对应的实施日期
        			source = segment.getSource();
        			if(!"".equals(source)) {
        				s = sourceService.getSourceByStandardNameAndStandardNo(source);
        				if(s != null && s.getExecuteDate() != null) { // 来源存在且有实施日期
        					segment.setBegincreateDate(s.getExecuteDate());
        				}
        			}
        		}
        		// 若发布时间为空，默认为"1970-01-01"
        		if(segment.getBegincreateDate() == null) {
        			segment.setBegincreateDate(sdf02.parse("1970-01-01"));
        		}
        		// 录入人
        		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
    				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    				User user = accountService.loadUserByUsername(userDetails.getUsername());
    				segment.setInsertId(user.getId());
    				segment.setInsertName(user.getAliasname());
    			}
        	} else {	// 修改
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        		if(request.getParameter("createDate02") != null && !"".equals(request.getParameter("createDate02"))) {
//        			segment.setCreateDate(sdf.parse(request.getParameter("createDate02")));
//        		}
        		if(request.getParameter("checkDate02") != null && !"".equals(request.getParameter("checkDate02"))) {
        			segment.setCheckDate(sdf.parse(request.getParameter("checkDate02")));
        		}
        		// 发布日期
        		begincreateDate = request.getParameter("begincreateDate02");
        		if(!"".equals(begincreateDate)) {	// 选择了发布日期
        			segment.setBegincreateDate(sdf02.parse(begincreateDate));
        		} else { // 没有选择发布日期，根据来源查询对应的实施日期
        			source = segment.getSource();
        			if(!"".equals(source)) {
        				s = sourceService.getSourceByStandardNameAndStandardNo(source);
        				if(s != null && s.getExecuteDate() != null) { // 来源存在且有实施日期
        					segment.setBegincreateDate(s.getExecuteDate());
        				}
        			}
        		}
        		// 若发布时间为空，默认为"1970-01-01"
        		if(segment.getBegincreateDate() == null) {
        			segment.setBegincreateDate(sdf02.parse("1970-01-01"));
        		}
        		// 修改人
        		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
    				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    				User user = accountService.loadUserByUsername(userDetails.getUsername());
    				segment.setModifyId(user.getId());
    				segment.setModifyName(user.getAliasname());
    				segment.setModifyDate(new Date());
    			}
        	}
			
        	infoService.saveSegment(segment); 
        	resultJson = "{'success':true}";
        } catch (Exception e) {
        	resultJson = "{'msg':'保存失败！'}";
            e.printStackTrace();   
            throw e;   
        }   
		commonService.responseJsonBody(response, resultJson);
    } 
    
	/**
	 * 根据ID删除切片
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/segment/delete/{id}")   
	@ResponseBody
    public Map<String,Object> delete(@PathVariable("id") String id,Model model){   
		Map<String,Object> result=new HashMap<String,Object>();
        try {   
        	infoService.deleteSegmentById(id);
        	result.put("success",true);
        } catch (Exception e) {   
        	result.put("msg", "删除失败！"); 
        	e.printStackTrace();   
        }   
        return result;
    }
	
	
	/**
	 * 根据ID审批切片
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/segment/approve/{id}")   
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
		// 审批人信息
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
	
	/***
	 * 查看切片
	 * @param id
	 * @param segment
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/segment/details/{id}", method = RequestMethod.GET)
	public String doDetails(@PathVariable("id")String id,Segment segment,Model model){
		model.addAttribute("segment", infoService.getSegmentById(id));
		return "page/segment/seeSegment";
	}
	
	@RequestMapping(value="/segment/tags/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Set<Tag> doTags(@PathVariable("id")String id,Segment segment,Model model){
		return infoService.getSegmentById(id).getTags();
	}

	
	
	/**
	 * 修改切片信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value="/segment/update/{id}/{index}",method=RequestMethod.GET)   
	 public String toUpdate(@PathVariable("id") String id, @PathVariable("index")String index, Model model) throws Exception{ 
        model.addAttribute("segment",infoService.getSegmentById(id));   
        model.addAttribute("index", index);
        return "page/segment/updateSegment";   
	}   
	 
    @RequestMapping(value="/segment/update/{id}",method=RequestMethod.POST)  
    @ResponseBody
    public void doUpdate(@PathVariable("id") String id, Segment segment,Model model,HttpServletResponse response){   
    	String resultJson = "{'success':true}";
    	try {   
        	infoService.saveSegment(segment);   
        	resultJson = "{'success':true}";
        } catch (Exception e) { 
        	resultJson = "{'msg':'更新失败！'}";
            e.printStackTrace();   
        }   
    	commonService.responseJsonBody(response, resultJson);
    }   
    
    /**
     * excel上传知识切片基本信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/segment/upload",method=RequestMethod.POST)
	public @ResponseBody String doUpload(HttpServletRequest request,HttpServletResponse response){
		String path = request.getSession().getServletContext().getRealPath("upload");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();   
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
			MultipartFile mf = entity.getValue();    
			String fileName=mf.getOriginalFilename();
			File file= new File(path,fileName);
			InputStream is =  null;
            try {
				ioUtil.streamToFile(mf.getInputStream(), file, true);
				List<Segment> segments = infoService.readXls(file.getAbsolutePath()) ;
				//System.out.println("segments.size:" + segments.size());

				// 建立切片与标签之间 的多对多关系
				List<Segment> segmentList = new ArrayList<Segment>() ;
				if(segments.size() != 0 && segments != null) {
					Iterator<Segment> ite = segments.iterator();
					while(ite.hasNext()) {
						Segment seg = (Segment)ite.next();
						Set<Tag> tagSet = new HashSet<Tag>() ;
						// 获取暂存的所有标签
						String tags = seg.getTagArray() ;
						String tagArr[] = tags.split("&") ;
						for(int i = 0 ; i < tagArr.length ; i++) {
							Tag tag = new Tag() ;
							if(tagArr[i] != null) {
								tag = tagService.findTagByName(tagArr[i]) ;
							}
							if(tag != null) {
								tagSet.add(tag) ;
							}
						}
						seg.setTags(tagSet) ;
						// 读取Excle时extra字段用来暂时存放标签数组
						//seg.setExtra(null) ;
						if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
							UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
							User user = accountService.loadUserByUsername(userDetails.getUsername());
							seg.setInsertId(user.getId());
							seg.setInsertName(user.getAliasname());
						}
						seg.setCreateDate(new Date()) ;		// 创建时间
						segmentList.add(seg) ;
					}
					infoService.saveSegmentList(segmentList) ;
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
     * 切片预览
     * @param id
     * @return
     */
    @RequestMapping(value="/segment/previewSegment/{id}", method=RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> previewSegmentDetail(@PathVariable("id")String id) {
    	Map<String, Object> result = new HashMap<String, Object>() ;
    	try {
			Segment s = infoService.getSegmentById(id) ;
			result.put("segment", s) ;
			result.put("success", true) ;
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
    @RequestMapping(value="/segment/auditSegment/{id}", method=RequestMethod.GET)
    public String toAuditSegmentPage(@PathVariable("id")String id, Model model) {
    	Segment s = infoService.getSegmentById(id);
    	model.addAttribute("segment", s);
    	return "/page/segment/auditSegment";
    }
   
}
