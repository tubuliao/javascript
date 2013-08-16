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
import com.isoftstone.tyw.dto.info.PptDTO;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.Files;
import com.isoftstone.tyw.entity.info.Form;
import com.isoftstone.tyw.entity.info.Powerpoint;
import com.isoftstone.tyw.entity.info.PowerpointPage;
import com.isoftstone.tyw.entity.info.PptModule;
import com.isoftstone.tyw.entity.info.Source;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.BaseService;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.FdfsService;
import com.isoftstone.tyw.service.InfoService;
import com.isoftstone.tyw.service.PowerpointPageService;
import com.isoftstone.tyw.service.PowerpointService;
import com.isoftstone.tyw.service.SourceService;
import com.isoftstone.tyw.util.PropertiesReader;

@Controller
public class PowerpointController {
	@Autowired
	PowerpointService pptService;
	@Autowired
	PowerpointPageService pptPageService;
	@Autowired
	AccountService accountService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private FdfsService fdfsService;
	@Autowired
	private BaseService baseService;
	@Autowired
	private SourceService sourceService;
	
	@RequestMapping(value = "/ppt", method = RequestMethod.GET)
	public String ppt(Model model){
		model.addAttribute(new Files());
		model.addAttribute("kLevel", baseService.getKnowledgeLevelSort("6"));
		return "page/ppt/ppt";
	}
	 
	@RequestMapping(value = "/ppt/add", method = RequestMethod.GET)
	public String toAddPptPage(Model model){
		return "page/ppt/addPpt";
	}
	
 /**
    * 新增/修改 保存ppt
    * @param ppt
    * @param request
    * @param response
    */
	@RequestMapping(value = "/ppt/save", method = RequestMethod.POST)
	@ResponseBody
	public void doSave(Powerpoint ppt, 
			 HttpServletRequest request,HttpServletResponse response){
		
		String id = request.getParameter("id") ;
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
			try {
				Powerpoint p = pptService.getPptById(id);
				if(p != null) {
					originalUrl = p.getUrls();
				}
				newUrl = originalUrl + ppt.getUrls();
				ppt.setUrls(newUrl);
				// 审批时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					if(request.getParameter("checkDate02") != null && !request.getParameter("checkDate02").equals("")){
						ppt.setCheckDate(sdf.parse(request.getParameter("checkDate02")));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
					UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					User user = accountService.loadUserByUsername(userDetails.getUsername());
					ppt.setModifyId(user.getId());
					ppt.setModifyName(user.getAliasname());
					ppt.setModifyDate(new Date());
				}
				// 发布日期
				begincreateDate = request.getParameter("begincreateDate02");
				if(!"".equals(begincreateDate)) {	// 选择了发布日期
					ppt.setBegincreateDate(sdf02.parse(begincreateDate));
				} else { // 没有选择发布日期，根据来源查询对应的实施日期
					source = ppt.getSource();
					if(!"".equals(source)) {
						s = sourceService.getSourceByStandardName(source);
						if(s != null && s.getExecuteDate() != null) { // 来源存在且有实施日期
							ppt.setBegincreateDate(s.getExecuteDate());
						}
					}
				}
				// 若发布时间为空，默认为"1970-01-01"
        		if(ppt.getBegincreateDate() == null) {
        			ppt.setBegincreateDate(sdf02.parse("1970-01-01"));
        		}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {	// 新增
			try {
				if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
					UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					User user = accountService.loadUserByUsername(userDetails.getUsername());
					ppt.setInsertId(user.getId());
					ppt.setInsertName(user.getAliasname());
				}
				// 发布日期
				begincreateDate = request.getParameter("begincreateDate02");
				if(!"".equals(begincreateDate)) {	// 选择了发布日期
					ppt.setBegincreateDate(sdf02.parse(begincreateDate));
				} else { // 没有选择发布日期，根据来源查询对应的实施日期
					source = ppt.getSource();
					if(!"".equals(source)) {
						s = sourceService.getSourceByStandardName(source);
						if(s != null && s.getExecuteDate() != null) { // 来源存在且有实施日期
							ppt.setBegincreateDate(s.getExecuteDate());
						}
					}
				}
				// 若发布时间为空，默认为"1970-01-01"
        		if(ppt.getBegincreateDate() == null) {
        			ppt.setBegincreateDate(sdf02.parse("1970-01-01"));
        		}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String resultJson="{'success':true}";
		 try{
			 pptService.savePowerpoint(ppt);
			 resultJson = "{'success':true}";
		 }catch(Exception e){
			 resultJson = "{'msg':'保存出错!'}";
			 e.printStackTrace();
		 }
		 commonService.responseJsonBody(response, resultJson);
	}
	
	   /**
     * ppt分页展示
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/ppt/pptview", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> pptview(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        String id = "";
        Powerpoint ppt = new Powerpoint();
        try {
            id = request.getParameter("id");
            if (id != null && !"".equals(id)) {
                ppt = pptService.getPptById(id);
                if (ppt != null) {
                    result.put("ppt", ppt);
                    result.put("success", true);
                }
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", "预览失败！");
            e.printStackTrace();
        }
        return result;
    }
	
	
	/**
	 *  获取所有文件
	 * @param pageable
	 * @param model
	 * @return
	 */
	
	
	@RequestMapping(value = "/pptlist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> pptListPager(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String,Object> result= new HashMap<String,Object>();
		String tagId=request.getParameter("tagId");
		String title=request.getParameter("title");
		String state = request.getParameter("state");
		String source = request.getParameter("source");
		String insertName = request.getParameter("insertName"); 
		String synStatus = request.getParameter("synStatus") ;
		// 知识等级
		String level = request.getParameter("level");	
		// 发布日期
		String begincreateDate = request.getParameter("begincreateDate");	
//		Page<Powerpoint> pageAll=infoService.listPpt(this.getWhereClause(tagId, title,source,insertName,state,synStatus, 6), pageable);
//		Page<PptDTO> page = pptListMajorization(pageAll) ;
		List<PptModule> pptList = pptService.listFileBaseRows(tagId, title, source, insertName, state, 6, synStatus, level, begincreateDate,pageable);
		BigInteger pptTotal = pptService.listFileBaseTotal(tagId, title, source, insertName, state, synStatus, level, begincreateDate,6);
		result.put("total",pptTotal);
		result.put("rows",pptList);
		return result;
	}
	
	/**
	 * 优化列表查询
	 * @param base
	 * @return
	 */
	public Page<PptDTO> pptListMajorization(Page<Powerpoint> base) {
		List<Powerpoint> content = base.getContent() ;
		List<PptDTO> ncontent = Lists.newArrayList() ;
		for(Base b : content) {
			Powerpoint ppt = pptService.getPptById(b.getId()) ;
			PptDTO pptDto = new PptDTO(b.getId(), b.getTitle(), b.getSource(), b.getInsertName(), b.getCreateDate(), b.getState(),ppt.getUrls()) ;
			ncontent.add(pptDto) ;
		}
		Page<PptDTO> result = new PageImpl<PptDTO>(ncontent, new PageRequest(base.getNumber(), base.getSize()), base.getTotalElements()) ;
		return result ;
	}
	
	public Specification<Powerpoint> getWhereClause(final String tagId,final String title,final String source,final String insertName, final String state, final String synStatus, final Integer infoType){
		return new Specification<Powerpoint>(){
			@Override
			public Predicate toPredicate(Root<Powerpoint> root,
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
				// 同步状态
				if(StringUtils.isNotBlank(synStatus)) {
					if("1".equals(synStatus)) {	// 已同步
						predicate.getExpressions().add( cb.and(cb.notEqual(root.get("urls"), ""), cb.isNotNull(root.get("urls")))) ;
					} else if("2".equals(synStatus)) {	// 未同步
						predicate.getExpressions().add( cb.or(cb.equal( root.get("urls"), ""), cb.isNull( root.get("urls")))) ;
					} else { //全部
						
					}
				}
				return predicate;
			}
		};
	}

	
	/**
	  * ppt预览
	  * @param request
	  * @return
	  */
	 @RequestMapping(value="/ppt/previewPpt", method=RequestMethod.GET)
	 @ResponseBody
	 public Map<String, Object> previewPpt(HttpServletRequest request) {
		 Map<String, Object> result = new HashMap<String, Object>() ;
		 String id = "" ;
		 Powerpoint ppt = new Powerpoint() ;
		 //创建一个ppt图片的集合（一个ppt中有多个图片）
		 List<PowerpointPage> pptPageList = new ArrayList();
		 try {
			id = request.getParameter("id");
			 if(id != null && !"".equals(id)) {
				 //根据ppt的id查询基本信息
				 ppt = pptService.getPptById(id) ;
				 //根据id查询当前ppt所有图片的集合
				 pptPageList = pptPageService.getPptPageList(id);
				 if(ppt != null) {
					 result.put("ppt", ppt) ;
					 result.put("pptPageList", pptPageList);
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
	  * 跳转到修改ppt页面
	  */
	 @RequestMapping(value="/ppt/edit/{id}", method=RequestMethod.GET)
	    public String toEditPptPage(@PathVariable("id") String id, Model model) throws Exception {
		    Powerpoint ppt = pptService.getPptById(id);
		    String urlStr = "";
		    String urlArr[] = null;
		    List<String> urlList = new ArrayList<String>();
		    if(ppt != null) {
		    	urlStr = ppt.getUrls();
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
	    	model.addAttribute("ppt", ppt) ;
	    	return "page/ppt/editPpt" ;
	    }
		
		 
	 /***
		 * 删除文件
		 * @param id
		 * @param base
		 * @param model
		 * @return
		 */
		@RequestMapping(value="/ppt/delete/{id}", method=RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> doDelete(@PathVariable("id")String id,Model model){
			Map<String,Object> result=new HashMap<String,Object>();
			try{
				//根据id删除ppt
				pptService.deletePowerpoint(id);
				result.put("success",true);
			}catch(Exception e){
				e.printStackTrace();
				result.put("success","删除失败！");
			}
			return result;
		}
	
		/***
		 * 上传ppt附件
		 */
		@RequestMapping(value="/ppt/upload/fdfs",method=RequestMethod.POST)
		@ResponseBody
		public String uplad(HttpServletRequest request,HttpServletResponse response){
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
				InputStream is = null;
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
		/***
		 * 删除ppt附件
		 * @param id
		 * @param model
		 * @return
		 */
		@RequestMapping(value="/ppt/delete/{url}/{id}",method=RequestMethod.GET)
		@ResponseBody
		public Map<String,Object> doAttachmentDelete(@PathVariable("id")String id, @PathVariable("url")String url, Model model,HttpServletRequest request){
			Map<String,Object> result = new HashMap<String,Object>();
			Powerpoint ppt = null;
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
				ppt = pptService.getPptById(id);
				if(ppt != null) {
					originalUrlStr = ppt.getUrls();
					if(!"".equals(originalUrlStr)) {
						urlArr = originalUrlStr.split(";");
						for(int i = 0; i < urlArr.length; i++) {
							if(urlArr[i] != null && !"".equals(urlArr[i])) {
								if(!urlArr[i].equals(dUrl)) {
									sb.append(";");
									sb.append(urlArr[i]);
								}
							}
						}
					}
					newUrlStr = sb.toString();
				}
				ppt.setUrls(newUrlStr);
				pptService.savePowerpoint(ppt);
				result.put("success", true);
			}catch(Exception e){
				e.printStackTrace();
				result.put("fail", "删除失败！");
			}
			return result;
		}
		
		/***
		 * 跳转到审核页面
		 * @param id
		 * @param model
		 * @return
		 */
		@RequestMapping(value="/ppt/audit/{id}", method=RequestMethod.GET)
		 public String toAuditPptPage(@PathVariable("id")String id, Model model) {
			 Powerpoint ppt = pptService.getPptById(id);
			    String urlStr = "";
			    String urlArr[] = null;
			    List<String> urlList = new ArrayList<String>();
			    if(ppt != null) {
			    	urlStr = ppt.getUrls();
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
			 model.addAttribute("ppt", ppt);
			 return "page/ppt/auditPpt" ; 
		 }
		
		/***
		 * 审核方法
		 * @param id
		 * @param model
		 * @return
		 */
		@RequestMapping(value="/ppt/approve/{id}")   
		@ResponseBody
	    public Map<String,Object> approve(@PathVariable("id") String id,HttpServletRequest request){   
			Map<String,Object> result=new HashMap<String,Object>();
			Integer status=Integer.parseInt(request.getParameter("status"));
			String auditInfo=request.getParameter("auditInfo");
			String weightType = request.getParameter("weightType");
			String weightVal = request.getParameter("weightVal");
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
		
		/**
		  * 反选标签
		  */
		 @RequestMapping(value="/ppt/tags/{id}", method = RequestMethod.POST)
			@ResponseBody
			public Set<Tag> doPptTags(@PathVariable("id")String id,Form form,Model model){
				return pptService.getPptById(id).getTags() ;
			}
}
