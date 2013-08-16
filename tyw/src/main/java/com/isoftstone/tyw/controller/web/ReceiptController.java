package com.isoftstone.tyw.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.pub.Receipt;
import com.isoftstone.tyw.service.LicensePaymentService;
import com.isoftstone.tyw.service.ReceiptService;

@Controller
public class ReceiptController {

	@Autowired
	private ReceiptService receiptService;
	
	@Autowired
	private LicensePaymentService licensePaymentService;
	
	
	/**
	 * 跳转到发票列表页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/receiptList", method=RequestMethod.GET)
	public String toReceiptListPage(Model model) {
		model.addAttribute(new Receipt());
		List<User> agentList = licensePaymentService.getAgentNameList();
		model.addAttribute("agentList", agentList);
		return "/page/license/receipt";
	}
	
	/**
	 * 发票 列表查询
	 * @param pageable
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/receipt/data", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listReceipt(@PageableDefaults(value=10)Pageable pageable, Model model, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 序列号
		String licenseNumber = request.getParameter("licenseNumber");
		// 发票状态
		String status = request.getParameter("status");
		// 渠道商
		String sAgentId = request.getParameter("sAgentId");
		// 批次号
		String sBatchCode = request.getParameter("sBatchCode");
		
		result = receiptService.listReceiptBySql(licenseNumber, status, sAgentId, sBatchCode, pageable);
		
		return result;
	}
	
	/**
	 * Excel导出
	 * @param pageable
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/receipt/export", method=RequestMethod.GET)
	public String exportExcelReceipt(@PageableDefaults(value=1000000)Pageable pageable, Model model, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 序列号
		String licenseNumber = request.getParameter("licenseNumber");
		// 发票状态
		String status = request.getParameter("status");
		// 渠道商
		String sAgentId = request.getParameter("sAgentId");
		// 批次号
		String sBatchCode = request.getParameter("sBatchCode");
		
		result = receiptService.listReceiptBySql(licenseNumber, status, sAgentId, sBatchCode, pageable);
		
		// Excel中文列
		Map<String, Object> r = new HashMap<String, Object>();
		r.put("heading", "发票列表");
		r.put("num", "编号");
		r.put("agentName", "渠道商");
		r.put("batchCode", "批次号");
		r.put("receiptTitle", "发票抬头");
		r.put("receiptAddress", "发票地址");
		r.put("receiptAmount", "发票金额");
		r.put("vMoneytype", "币种");
		r.put("vMoneytype01", "人民币");
		r.put("vMoneytype02", "美元");
		r.put("vMoneytype03", "");
		r.put("receiptPerson", "发票人");
		r.put("postCode", "邮编");
		r.put("receiptPhone", "联系电话");
		r.put("licenseNumber", "关联序列号");
		r.put("status", "发票状态");
		r.put("status01", "未开票");
		r.put("status02", "已开票");
		r.put("status03", "未知");
		
		model.addAttribute("r", r);
		model.addAttribute("receiptList", result.get("rows"));
		
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-msdownload;");   
			response.setHeader("Content-disposition", "attachment; filename=export.xls");
			response.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}  
		return "/page/license/exportExcelReceipt";
	}
	
	/**
	 * 批量修改发票状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/regeict/updateStatus", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateReceiptStatus(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		// id字符串（&分隔）
		String idString = request.getParameter("idString");
		// id数组
		String idArr[] = null;
		// 状态
		String status = request.getParameter("status");
		
		try {
			if(idString != null && !"".equals(idString)) {
				idArr = idString.split("&");
			}
			if(idArr != null) {
				receiptService.modifyAllStatus(idArr, status);
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("fail", true);
			result.put("msg", "批量更新状态失败！");
			e.printStackTrace();
		}
		return result;
	}
	
}
