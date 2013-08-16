package com.isoftstone.tyw.controller.web;

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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

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

import com.isoftstone.tyw.dto.info.BaseDTO;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.ApprovalModule;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.BaseService;
import com.isoftstone.tyw.service.TagService;

@Controller
public class ApprovalController {

	@Autowired
	private BaseService baseService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 跳转到列表页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="batchApproval", method=RequestMethod.GET)
	public String toApprovalListPage(Model model) {
		return "page/approval/approvalList";
	}
	
	/**
	 * 查询列表
	 * @param pageable
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/allList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> approvalListPager(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String,Object> result= new HashMap<String,Object>();
		// 标签ID（来自标签树）
		String tagId=request.getParameter("tagId");
		// 知识类型
		String infoType = request.getParameter("infoType");
		// 录入人
		String insertName = request.getParameter("insertName");
		// 录入时间From
		String dateFrom = "".equals(request.getParameter("dateFrom")) || request.getParameter("dateFrom") == null ? "1999-01-01" : request.getParameter("dateFrom");
		// 录入时间To
		String dateTo = "".equals(request.getParameter("dateTo")) || request.getParameter("dateTo") == null ? "2999-12-12" : request.getParameter("dateTo");
		// 标题
		String title = request.getParameter("title");
		// 审批状态
		String state = request.getParameter("state");
		//申请删除
		String applyDeteles = request.getParameter("applyDetele");
		Integer applyDetele = null;
		if(applyDeteles !=null&& !"".equals(applyDeteles)){
			applyDetele = Integer.parseInt(applyDeteles);
		}
		// 标题重复
		String titleRepeat = request.getParameter("titleRepeat");
		// 来源
		String source = request.getParameter("source");
		
		Map<String, Object> page = baseService.listBaseBySql(tagId, infoType, insertName, dateFrom, dateTo, title, state, applyDetele, titleRepeat, source, pageable) ;
		result.put("total", page.get("total"));
		result.put("rows", page.get("rows"));
		return result;
	}
	
	/**
	 * 查询条件
	 * @param infoType
	 * @param insertName
	 * @param dateFrom
	 * @param dateTo
	 * @param title
	 * @return
	 *//*
	public Specification<Base> getWhereClause(final String tagId, final String infoType, final String insertName, final String dateFrom, final String dateTo,final String title, final String state){
		return new Specification<Base>(){
			public Predicate toPredicate(Root<Base> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				// 修改时间倒叙
//				query.orderBy(cb.desc(root.get("createDate")));
				query.orderBy(cb.desc(root.get("modifyDate")));
				// 标签
				if (tagId != null && !"".equals(tagId)) { 
					predicate.getExpressions().add(cb.equal(root.joinSet("tags").get("id"), tagId));
				}
				// 知识类型
				if(infoType != null && !"".equals(infoType) && !"0".equals(infoType)) {
					predicate.getExpressions().add(cb.equal(root.<String> get("infoType"), infoType));
				}
				// 录入人
				if(insertName != null && !"".equals(insertName)) {
					predicate.getExpressions().add(cb.like(root.<String>get("insertName"), "%" + insertName + "%"));
				}
				// 标题
				if(title != null && !"".equals(title)) {
					predicate.getExpressions().add(cb.like(root.<String>get("title"), "%" + title + "%"));
				}
				// 录入时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					predicate.getExpressions().add(cb.between(root.<Date>get("createDate"), sdf.parse(dateFrom), sdf.parse(dateTo)));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				// 审批状态
				if(state != null && !"".equals(state)) {
					predicate.getExpressions().add(cb.equal(root.<String>get("state"), state));
				}
				
				return predicate;
			}
		};
	}

	*//**
	 * 优化列表查询
	 * @param base
	 * @return
	 *//*
	public Page<BaseDTO> listMajorization(Page<Base> base) {
		List<Base> content = base.getContent() ;
		List<BaseDTO> ncontent = new ArrayList<BaseDTO>() ;
		for(Base b : content) {
			// id, 知识类型， 标题， 来源， 录入时间， 录入人， 修改时间， 修改人， 审批状态
			BaseDTO bdto = new BaseDTO(b.getId(), b.getInfoType(), b.getTitle(), b.getSource(), b.getCreateDate(), b.getInsertName(), b.getModifyDate(), b.getModifyName(), b.getState()) ;
			ncontent.add(bdto) ;
		}
		Page<BaseDTO> result = new PageImpl<BaseDTO>(ncontent, new PageRequest(base.getNumber(), base.getSize()), base.getTotalElements()) ;
		return result ;
	}
	*/
	/**
	 * open批量标签页面
	 * @param idStr
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/showTags/{idStr}", method=RequestMethod.GET)
	public String toTagsPage(@PathVariable("idStr")String idStr, Model model, HttpServletRequest request) {
		
		// 全部记录列表
		List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
		// 单条Base
		Base b =  null;
		// 批量ID数组
		String idArr[] = null;
		try {
			if(idStr != null && !"".equals(idStr)) {
				idArr = idStr.split("&");
			}
			if(idArr != null) {
				// 根据拆分的取得的ID,逐条获取记录进行 处理
				for(int i = 0; i < idArr.length; i++) {
					// 分部分项(10000000000000000)
					List<Tag> subitemTagList = new ArrayList<Tag>();
					// 地区(20000000000000000)
					List<Tag> areaTagList = new ArrayList<Tag>();
					// 人员分类(30000000000000000)
					List<Tag> personnelTagList = new ArrayList<Tag>();
					// 知识性质(40000000000000000)
					List<Tag> architectonicTagList = new ArrayList<Tag>();
					// 单条记录详细
					Map<String, Object> oneDetail = new HashMap<String, Object>();
					// 单条记录的Tags
					Set<Tag> tags = new HashSet<Tag>();
					if(idArr[i] != null && !"".equals(idArr[i])) {
						// 获取其中一条记录
						b = baseService.getOne(idArr[i]);
						// 该记录的所有标签
						tags = b.getTags();
						Iterator<Tag> ite = tags.iterator();
						// 循环处理所有的标签
						while(ite.hasNext()) {
							Tag t = ite.next();
							// 获取标签code的首位数字（1：分部分项  2：地区  3：人员分类  4：知识性质）
							String codeFirst = t.getCode().toString().substring(0,1);
							// 将相应类型的标签存入对应的list中
							switch (Integer.parseInt(codeFirst)) {
							case 1:	// 分部分项标签
								subitemTagList.add(t);
								break;
								
							case 2:	// 地区标签
								areaTagList.add(t);
								break;
								
							case 3:	// 人员分类标签
								personnelTagList.add(t);
								break;
								
							case 4:	// 知识性质标签
								architectonicTagList.add(t);
								break;
							}	
						}
						// 知识基本信息 
						oneDetail.put("baseData", b);
						// 标签信息
						oneDetail.put("subitemTagList", subitemTagList);
						oneDetail.put("areaTagList", areaTagList);
						oneDetail.put("personnelTagList", personnelTagList);
						oneDetail.put("architectonicTagList", architectonicTagList);
					}
					// 单条知识的所有信息
					allList.add(oneDetail);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}		
		// 选择的所有知识的id字符串（通过“&”分隔）
		model.addAttribute("idStr", idStr);
		model.addAttribute("allList", allList);
		
		return "page/approval/approvalShowTags"; 
	}
	
	/**
	 * 申请批量删除
	 * ajax
	 * @param idStr
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/applyDetele/{idStr}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> toApplyDetele(@PathVariable("idStr")String idStr, Model model) throws Exception{
		Map<String,Object> result=new HashMap<String,Object>();
		// 批量ID数组
		String idArr[] = null;
		try {
			if(idStr != null && !"".equals(idStr)) {
				idArr = idStr.split("&");
			}
			if(idArr != null) {
				// 根据拆分的取得的ID,逐条进行更新apply_detele为1 处理，申请删除
				for(int i = 0; i < idArr.length; i++) {
					baseService.updateBaseById(idArr[i],1);
				}
				result.put("success",true);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.put("msg", "申请删除失败！");
		}		
		
		return result; 
	}
	
	/**
	 * 申请批量取消删除
	 * ajax
	 * @param idStr
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/applyCancelDetele/{idStr}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> toApplyCancelDetele(@PathVariable("idStr")String idStr, Model model) throws Exception{
		Map<String,Object> result=new HashMap<String,Object>();
		// 批量ID数组
		String idArr[] = null;
		try {
			if(idStr != null && !"".equals(idStr)) {
				idArr = idStr.split("&");
			}
			if(idArr != null) {
				// 根据拆分的取得的ID,逐条进行更新apply_detele为0 处理,取消申请删除
				for(int i = 0; i < idArr.length; i++) {
					baseService.updateBaseById(idArr[i],0);
				}
				result.put("success",true);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.put("msg", "取消申请删除失败！");
		}		
		
		return result; 
	}
	
	/**
	 * 批量同意删除
	 * ajax
	 * @param idStr
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/agreeDetele/{idStr}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> toAgreeDetele(@PathVariable("idStr")String idStr, Model model) throws Exception{
		Map<String,Object> result=new HashMap<String,Object>();
		// 批量ID数组
		String idArr[] = null;
		try {
			if(idStr != null && !"".equals(idStr)) {
				idArr = idStr.split("&");
			}
			if(idArr != null) {
				// 根据拆分的取得的ID,逐条进行删除
				for(int i = 0; i < idArr.length; i++) {
					baseService.deteleBaseById(idArr[i]);
				}
				result.put("success",true);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result.put("msg", "删除失败！");
		}		
		
		return result; 
	}
	
	/**
	 * 删除单个标签
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/approval/delTag", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteSingleTag(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 知识id
		String baseId = request.getParameter("baseId");
		// 标签id
		String tagId = request.getParameter("tagId");
		try {
			if(baseId != null && !"".equals(baseId) && tagId != null && !"".equals(tagId)) {
				// 根据知识id和标签id 接触标签关系即删除中间关联表数据
				tagService.removeBaseAndTag(baseId, tagId);
			}
			// 记录修改者的信息
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				User user = accountService.loadUserByUsername(userDetails.getUsername());
				baseService.modifyModifyInfo(baseId, new Date(), user.getId(), user.getAliasname());
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("fail", true);
			result.put("msg", "删除标签失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 批量标签
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/approval/doSave", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBaseAndTag(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 知识ID数组
		String baseIdArr[] = null;
		// 标签ID数组
		String tagIdArr[] = null;
		// 操作人
		User user = null;
		
		try {
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				user = accountService.loadUserByUsername(userDetails.getUsername());
			}
			String baseIdStr = request.getParameter("baseIdStr");
			String tagIdStr = request.getParameter("tagIdStr");
			if(baseIdStr != null && !"".equals(baseIdStr) && tagIdStr != null && !"".equals(tagIdStr)) {
				// 根据"&"来拆分ID
				baseIdArr = baseIdStr.split("&");
				tagIdArr = tagIdStr.split("&");
				if(baseIdArr != null && tagIdArr != null) {
					// 循环获取标签信息
					for(int i = 0; i < tagIdArr.length; i++) {
						if(tagIdArr[i] != null && !"".equals(tagIdArr[i])) {
							Tag t = tagService.getTagById(tagIdArr[i]);
							if(t != null) {
								for(int j = 0; j < baseIdArr.length; j++) {
									// 循环获取每一条知识
									if(baseIdArr[j] != null && !"".equals(baseIdArr[j])) {
										Base b = baseService.getOne(baseIdArr[j]);
										if(b != null) {
											// 记录修改人信息
											baseService.modifyModifyInfo(b.getId(), new Date(), user.getId(), user.getAliasname());
											// 绑定知识与标签间的关系
											baseService.saveAll(b.getId(), t.getId(), t.getCode(), t.getName(), b.getTitle(), b.getSource());
										}
									}
								}
							}
						}
					}
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "批量标签失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 批量审批通过
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/approval/audit", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> approvalAuditPass(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 需要批量审批通过的ID串
		String idStr = request.getParameter("idStr");
		String idArr[] = null;
		// 将通过“&”分隔的ID串拆分成ID数组
		if(idStr != null && !"".equals(idStr)) {
			idArr = idStr.split("&");
		}
		// 审批人
		User user = null;
		try {
			// 审批人信息
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				user = accountService.loadUserByUsername(userDetails.getUsername());
			}
			// 循环处理ID数组
			if(idArr !=  null) {
				for(int i = 0; i < idArr.length; i++) {
					if(!"".equals(idArr[i]) && idArr[i] != null) {
						// 执行审批通过，并记录审批人员信息
						baseService.modifyApprovalState(idArr[i], 1, new Date(), user.getId(), user.getAliasname());
					}
				}
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("fail", true);
			result.put("msg", "批量审批失败！");
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value="/approval/source", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approvalSource(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// id字符串（&分隔）
			String idStr = request.getParameter("idStr");
			// id数组
			String idArr[] = null;
			// 来源
			String source = request.getParameter("source");
			// 拆分
			if(idStr != null && !"".equals(idStr)) {
				idArr = idStr.split("&");
			}
			// 修改人
			User user = null;
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				user = accountService.loadUserByUsername(userDetails.getUsername());
			}
			if(idArr != null && source != null && !"".equals(source) && user != null) {
				baseService.modifyApprovalSource(idArr, source, new Date(), user.getId(), user.getAliasname());
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("fail", true);
			result.put("msg", "批量跟新来源失败！");
			e.printStackTrace();
		}
		return result;
	}
}


