package com.isoftstone.tyw.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.info.KnowledgeSort;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.KnowledgeSortService;
import com.isoftstone.tyw.service.TagService;

@Controller
public class KnowledgeSortViewController {

	@Autowired
	private KnowledgeSortService knowledgeSortService ;
	@Autowired
	private TagService tagService ;
	
	
	
	/**
	 * 跳转到知识分类统计页面
	 * @return
	 */
	@RequestMapping(value="/knowledgeSortList", method=RequestMethod.GET)
	public String toKnowledgeSortListPage() {
		return "/page/tag/knowledgeSortList" ;
	}
	
	/**
	 * 知识分类统计查询列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/knowledgeSort/data", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> knowledgeSortList(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		String sort = "" ;	// 1：分部分项    2：地区    3：人员分类    4：知识性质
		String tagId = "";	// 标签ID
		Integer tagLevel = 0;	// 标签等级
		String tagCode = "";	// 标签code
		sort = request.getParameter("sort") ;
		tagId = request.getParameter("tagId");
		if((sort == null || "".equals(sort)) && (tagId == null || "".equals(tagId))) {	// 通过菜单链接进入
			sort = "1";	// 默认 分部分项
			tagCode = "10000000000000000" ;
			tagLevel = 1;
		} else if((sort != null && !"".equals(sort)) && (tagId == null || "".equals(tagId))) {	// 点击查询按钮
			 tagCode = sort + "0000000000000000";
			 tagLevel = 1;
		} else if((sort != null && !"".equals(sort)) && (tagId != null && !"".equals(tagId))) {	// 双击查询下级
			Tag t = tagService.getTagById(tagId) ;
			if(t != null) {
				tagCode = t.getCode().toString();
				tagLevel = t.getLevel();
			}
		}
		List<KnowledgeSort> list = new ArrayList<KnowledgeSort>() ;
		long count = 0 ;
		list = knowledgeSortService.getKnowledgeSortList(tagCode, tagLevel) ;
		if(list != null) {
			count = list.size();	
		}
		result.put("rows", list) ;
		result.put("total", count) ;
		return result ;
	}
	
	
}
