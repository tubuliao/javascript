package com.isoftstone.tyw.controller.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.info.Answer;
import com.isoftstone.tyw.entity.info.Question;
import com.isoftstone.tyw.service.AnswerService;
import com.isoftstone.tyw.service.CommonService;

@Controller
public class AnswerController {
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private CommonService commonService;
	
	/**
	 * 保存答案
	 * 
	 */
	@RequestMapping(value = "/saveAnswer", method = RequestMethod.POST)
	@ResponseBody
	public void saveAnswer(HttpServletRequest request,HttpServletResponse response,Model model){
		String resultJson="{'success':true}";
		//获取回答的内容
		String content = request.getParameter("content");
		//获取问题id
		String quesId = request.getParameter("quesId");
		//实例化答案实体
		Answer ans = new Answer();
		try {
			//给相应的字段赋值
			ans.setAid("");
			ans.setContent(content);
			ans.setQid(quesId);
			ans.setCreateDate(new Date());
			//执行保存方法
			answerService.saveAnswer(ans);
			resultJson = "{'success':true}";
		 }catch (Exception e) {   
			 resultJson = "{'msg':'保存出错!'}";
	         e.printStackTrace();  
	    } 
		commonService.responseJsonBody(response, resultJson);
	}
	
	/**
	 *  获取所有指定问题的答案
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/answerlist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> answerListPager(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String,Object> result= new HashMap<String,Object>();
		//获取当前问题的id
		String ansId = request.getParameter("ansId");
		//根据当前问题的id查询此问题有多少个答案
		Page<Answer> pageAll=answerService.getAnswerByQid(ansId, pageable);
		//答案总数
		result.put("total",pageAll.getTotalElements());
		//返回数据
		result.put("rows",pageAll.getContent());
		return result;
	}
}
