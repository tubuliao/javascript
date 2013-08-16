package com.isoftstone.tyw.controller.web;

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

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Answer;
import com.isoftstone.tyw.entity.info.Files;
import com.isoftstone.tyw.entity.info.Form;
import com.isoftstone.tyw.entity.info.Question;
import com.isoftstone.tyw.entity.info.QuestionModule;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.AnswerService;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.InfoService;
import com.isoftstone.tyw.service.QuestionService;

@Controller
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private InfoService infoService;

	/**
	 * 后台跳转到问题列表
	 * @param model
	 */
	@RequestMapping(value = "/question", method = RequestMethod.GET)
	public String question(Model model){
		model.addAttribute(new Files());
		return "page/question/question";
	}
	
	/**
	 * 后台跳转到添加问题页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/question/add", method = RequestMethod.GET)
	public String toAddQuestionPage(Model model){
		return "page/question/addQuestion";
	}	 
	
	/**
      * 新增/修改 保存问答
      * @param ppt
      * @param request
      * @param response
      */
	@RequestMapping(value = "/question/save", method = RequestMethod.POST)
	@ResponseBody
	public void doSave(Question question, 
			 HttpServletRequest request,HttpServletResponse response){
		String title = request.getParameter("title");
		String id = request.getParameter("id") ;
		
		if(id != null) { // 修改
			try {
				
				// 审批时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					if(request.getParameter("checkDate02") != null && !request.getParameter("checkDate02").equals("")){
						question.setCheckDate(sdf.parse(request.getParameter("checkDate02")));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
					UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					User user = accountService.loadUserByUsername(userDetails.getUsername());
					question.setModifyId(user.getId());
					question.setModifyName(user.getAliasname());
					question.setModifyDate(new Date());
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
					question.setInsertId(user.getId());
					question.setInsertName(user.getAliasname());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String resultJson="{'success':true}";
		 try{
			 question.setTitle(title);
			 questionService.saveQuestion(question);
			 resultJson = "{'success':true}";
		 }catch(Exception e){
			 resultJson = "{'msg':'保存出错!'}";
			 e.printStackTrace();
		 }
		 commonService.responseJsonBody(response, resultJson);
	}
	
	
	/**
	 *  获取所有问答
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/questionlist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> questionListPager(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String,Object> result= new HashMap<String,Object>();
		String tagId=request.getParameter("tagId");
		String title=request.getParameter("title");
		String state = request.getParameter("state");
		String source = request.getParameter("source");
		String insertName = request.getParameter("insertName"); 
		String synStatus = request.getParameter("synStatus") ;
		//查询所有的问题
		//Page<Question> pageAll=infoService.listQuestion(this.getWhereClause(tagId, title,source,insertName,state,synStatus, 7), pageable);
		List<QuestionModule> questionList = questionService.listQuestionBaseRows(tagId, title, source, insertName, state, 7, pageable);
		BigInteger questionTotal = questionService.listQuestionBaseTotal(tagId, title, source, insertName, state, 7);
		//问题总数
		result.put("total",questionTotal);
		//查询结果
		result.put("rows",questionList);
		return result;
	}
	
	public Specification<Question> getWhereClause(final String tagId,final String title,final String source,final String insertName, final String state, final String synStatus, final Integer infoType){
		return new Specification<Question>(){
			@Override
			public Predicate toPredicate(Root<Question> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate =cb.conjunction();
				//标签类型
				predicate.getExpressions().add(cb.equal(root.<String>get("infoType"), infoType));
				//创建时间倒叙
				query.orderBy(cb.desc(root.get("createDate")));	
			
				if(tagId!=null&&!"".equals(tagId)){
					predicate.getExpressions().add(cb.equal(root.joinSet("tags").get("id"), tagId));
				}
				//根据问题内容查询（title字段存的是问题的内容）
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
	  * 跳转到修改问题页面
	  */
	 @RequestMapping(value="/question/edit/{id}", method=RequestMethod.GET)
	    public String toEditQuestionPage(@PathVariable("id") String id, Model model) throws Exception {
		 	//根据id查询一条问题的信息
		    Question question = questionService.getQuestionById(id);
	    	model.addAttribute("question", question) ;
	    	return "page/question/editQuestion" ;
	    }
	 
	 /**
	   * 删除问答
	   * @param id
	   * @param base
	   * @param model
	   * @return
	   */
		@RequestMapping(value="/question/delete/{id}", method=RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> doDelete(@PathVariable("id")String id,Model model){
			Map<String,Object> result=new HashMap<String,Object>();
			try{
				//根据id删除问答
				questionService.deleteQuestion(id);
				result.put("success",true);
			}catch(Exception e){
				e.printStackTrace();
				result.put("success","删除失败！");
			}
			return result;
		}
		
		/**
		  * 问答预览
		  * @param request
		  * @return
		  */
		 @RequestMapping(value="/question/previewQuestion", method=RequestMethod.GET)
		 @ResponseBody
		 public Map<String, Object> previewQuestion(HttpServletRequest request) {
			 Map<String, Object> result = new HashMap<String, Object>() ;
			 String id = "" ;
			 Question question = new Question() ;
			 //创建一个问题答案的集合（一个问题会有多个答案）
			 List<Answer> answerPageList = new ArrayList();
			 try {
				id = request.getParameter("id");
				 if(id != null && !"".equals(id)) {
					 //根据问题的id查询基本信息
					 question = questionService.getQuestionById(id) ;
					 //根据id查询当前问题所有的答案
					 answerPageList = answerService.getAnswerPageList(id);
					 if(question != null) {
						 result.put("question", question) ;
						 result.put("answerPageList", answerPageList);
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
		 
	    /***
		 * 跳转到审核页面
		 * @param id
		 * @param model
		 * @return
		 */
		@RequestMapping(value="/question/audit/{id}", method=RequestMethod.GET)
		 public String toAuditQuestionPage(@PathVariable("id")String id, Model model) {
			 Question question = questionService.getQuestionById(id);
			 model.addAttribute("question", question);
			 return "page/question/auditQuestion" ; 
		 }
		
		/***
		 * 审核方法
		 * @param id
		 * @param model
		 * @return
		 */
		@RequestMapping(value="/question/approve/{id}")   
		@ResponseBody
	    public Map<String,Object> approve(@PathVariable("id") String id,HttpServletRequest request){   
			Map<String,Object> result=new HashMap<String,Object>();
			int status=Integer.parseInt(request.getParameter("status"));
			User user=null; 
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
					UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					user = accountService.loadUserByUsername(userDetails.getUsername());
			 }
			 String checkId=(user!=null)?user.getId():"";
			 String checkName=(user!=null)?user.getAliasname():"";
			try {   
				//执行更新方法，更新审核时应该修改的相应字段
				infoService.modifyStatus(status,checkId,checkName,new Date(), id);
	        	result.put("success",true);
	        } catch (Exception e) {   
	        	result.put("fail", true); 
	        	e.printStackTrace();   
	        }   
	        return result;
	    }
		
		/**
		  * 反选标签
		  */
		 @RequestMapping(value="/question/tags/{id}", method = RequestMethod.POST)
			@ResponseBody
			public Set<Tag> doQuestionTags(@PathVariable("id")String id,Form form,Model model){
				return questionService.getQuestionById(id).getTags() ;
			}
}
