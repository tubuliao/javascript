package com.isoftstone.tyw.controller.web;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.LicensePaymentModule;
import com.isoftstone.tyw.entity.pub.License;
import com.isoftstone.tyw.entity.pub.LicensePayment;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.LicensePaymentService;

@Controller
public class LicensePaymentController {

	@Autowired
	private LicensePaymentService licensePaymentService;
	
	@Autowired
	private AccountService accountService;
	
	/**
	 * 跳转到订单列表页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/licensePaymentlist", method=RequestMethod.GET)
	public String toLicensePaymentListPage(Model model) {
		
		model.addAttribute(new LicensePayment());
		List<User> agentList = licensePaymentService.getAgentNameList();
		model.addAttribute("agentList", agentList);
		return "page/license/payment";
	}
	
	/**
	 * 查询订单列表
	 * @param pageable
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/licensePaymentList/data", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> listPaymentJson(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		// 订单编号
		String vOid = request.getParameter("vOid");
		// 交费状态
		String payStatus = request.getParameter("payStatus");
		// 渠道商
		String sAgentId = request.getParameter("sAgentId");
		// 批次号
		String sBatchCode = request.getParameter("sBatchCode");
		
		Map<String, Object> licensePaymentList = licensePaymentService.listLicensePaymentBySql(vOid, payStatus, sAgentId, sBatchCode, pageable);
		
		result.put("total", licensePaymentList.get("total"));  
		result.put("rows", licensePaymentList.get("rows"));  
		return result;
	}
	
	/**
	 * 导出Excel
	 * @param pageable
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/license/export", method=RequestMethod.GET)
	public String toLicenseExportPage(@PageableDefaults(value=1000000) Pageable pageable, Model model, HttpServletRequest request, HttpServletResponse response) {
		// 订单编号
		String vOid = request.getParameter("vOid");
		// 交费状态
		String payStatus = request.getParameter("payStatus");
		// 渠道商
		String sAgentId = request.getParameter("sAgentId");
		// 批次号
		String sBatchCode = request.getParameter("sBatchCode");
		// 导出列表
		Map<String, Object> page = licensePaymentService.listLicensePaymentBySql(vOid, payStatus, sAgentId, sBatchCode, pageable);
		// Excel中文列
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("heading", "订单列表");
		result.put("num", "编号");
		result.put("agent", "渠道商");
		result.put("batch", "批次号");
		result.put("user", "用户");
		result.put("payStatus", "交费状态");
		result.put("payAmount", "交费金额");
		result.put("payDate", "交费时间");
		result.put("vOid", "订单编号");
		result.put("vPmode", "支付方式");
		result.put("vPstring", "支付结果信息");
		result.put("vMoneytype", "币种");
		result.put("payStatus01", "已提交");
		result.put("payStatus02", "支付成功");
		result.put("payStatus03", "支付失败");
		result.put("payStatus04", "未支付");
		result.put("vMoneytype01", "人民币");
		result.put("vMoneytype02", "美元");
		result.put("vMoneytype03", "");
		
		
		model.addAttribute("result", result);
		model.addAttribute("paymentList", page.get("rows"));
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-msdownload;");   
			response.setHeader("Content-disposition", "attachment; filename=export.xls");
			response.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}   
		return "/page/license/exportExcelLicense";
	}
}
