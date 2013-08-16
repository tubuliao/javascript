package com.isoftstone.tyw.controller.web;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.dto.info.BaseDTO;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.DataContrast;
import com.isoftstone.tyw.service.BaseService;
import com.isoftstone.tyw.service.DataContrastService;

@Controller
public class DataContrastController {
	
	@Autowired
	private DataContrastService dataContrastService;
	
	@Autowired
	private BaseService baseService;
	
	/**
	 * 跳转到数据对比统计页面
	 * @return
	 */
	@RequestMapping(value="/dataContrastList", method=RequestMethod.GET)
	public String toKnowledgeSortListPage() {
		return "/page/statistical/dataContrastList" ;
	}
	
	
	/**
	 * 数据对比查询列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/dataContrast/list", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDataContrastList(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 查询类型，默认为"1"
		String type = (request.getParameter("searchType") == "" || request.getParameter("searchType") == null) ? "1" : request.getParameter("searchType");
		List<DataContrast> dataList = null;
		Integer dataCount = null;
		switch(Integer.parseInt(type)) {
		case 1: // 搜索引擎聚合数据量
			dataList= dataContrastService.getDataListFromTywadb();
			break;
		case 2:	// 生产库数据量
			dataList = dataContrastService.getDataListFromTywdb();
			break;
		case 3:	// 生产库和应用库未同步数据量
			dataList = dataContrastService.getDataListByDifference();
			break;
		}
		
		
		if(dataList != null) {
			dataCount = dataList.size();
		}
		
		result.put("rows", dataList);
		result.put("total", dataCount);
		return result;
	}
	
	@RequestMapping(value="/export", method=RequestMethod.GET)
	public String toExcelExport(@PageableDefaults(value=1000000) Pageable pageable,HttpServletRequest request, Model model, HttpServletResponse response) {
		// 知识类型
		String infoType = request.getParameter("infoType");
		// 标签ID（来自标签树）
		String tagId=request.getParameter("tagId");
		// 录入人
		String insertName = request.getParameter("insertName");
		// 标题
		String title = request.getParameter("title");
		// 审批状态
		String state = request.getParameter("state");
		// 来源
		String source = request.getParameter("source");
		// 发布日期
		String begincreateDate = request.getParameter("begincreateDate");
		// 知识等级
		String level = request.getParameter("level");
 		
 		Map<String, Object> page = baseService.listBaseForExcel(infoType, tagId, insertName, title, state, source, begincreateDate, level);
		
 		// 表格的中文列名，防止乱码
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("heading", "知识导出");
		result.put("num", "编号");
		result.put("title", "标题");
		result.put("source", "来源");
		result.put("createDate", "录入时间");
		result.put("insertName", "录入人");
		result.put("modifyDate", "修改时间");
		result.put("modifyName", "修改人");
		result.put("checkDate", "审批时间");
		result.put("checkName", "审批人");
		result.put("state", "审批状态");
		result.put("state0", "未审批");
		result.put("state1", "已审批");
		result.put("state2", "已驳回");
		result.put("state3", "已二次审批");
		
		model.addAttribute("dataList", page.get("rows"));
		model.addAttribute("result", result);
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-msdownload;");   
			response.setHeader("Content-disposition", "attachment; filename=export.xls");
			response.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}   
		
		return "/page/statistical/exportExcel"; 
	}
	
}
