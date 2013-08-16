package com.isoftstone.tyw.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.biz.CardType;
import com.isoftstone.tyw.entity.biz.Discount;
import com.isoftstone.tyw.service.BizService;
import com.isoftstone.tyw.service.CommonService;

@Controller
public class DiscountController {
	@Autowired
	private BizService bizService;

	@Autowired
	private CommonService commonService;
	
	/**
	 *  返回所有优惠通道列表
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/discountlist", method = RequestMethod.GET)
	public String paymentlist(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user =(User)session.getAttribute("user");
		List<Discount> list = bizService.getDiscountByAgentId(user.getId());
		session.setAttribute("list", list);
		return "page/channel/channel";
	}
	
	@RequestMapping(value = "/discountlist/data", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> listPaymentJson(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		String discountName=request.getParameter("discountName");
		String discountCode=request.getParameter("discountCode");
		Page<Discount> page = bizService.listAllDiscount(discountName,discountCode,pageable);
		result.put("total",page.getTotalElements());  
		result.put("rows", page.getContent());  
		return result;
	}
	/**
	 * 添加新优惠通道
	 * @return
	 */

//    @RequestMapping(value="/addDiscount",method=RequestMethod.GET)   
//    public String toAdd(Model model) throws Exception{   
//        return "page/discount/addDiscount";   
//    } 
    
    @RequestMapping(value="/addDiscount",method=RequestMethod.POST)   
	public String doAdd(@ModelAttribute("discount") Discount discount,
			Model model,
			@RequestParam("strValidStartDate") String strValidStartDate,
			@RequestParam("strValidEndDate") String strValidEndDate,HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		try {
//        	String validStartDate= discount.getValidStartDate();
//        	String validEndDate = discount.getValidEndDate();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

//			UserDetails userDetails = (UserDetails) SecurityContextHolder
//					.getContext().getAuthentication().getPrincipal();
			
			Date validStartDate = DateUtils.parseDate(strValidStartDate, "yyyy-MM-dd");
			Date validEndDate=DateUtils.parseDate(strValidEndDate, "yyyy-MM-dd");
			Date createDateTime =  df.parse(df.format(new Date()));
			
			HttpSession session = request.getSession();
			User user =(User)session.getAttribute("user");
			discount.setAgentId(user.getId());

			discount.setValidStartDate(validStartDate);
			discount.setValidEndDate(validEndDate);
			discount.setCreateDate(createDateTime);
//			discount.setCreateName(userDetails.getUsername());
			discount.setCreateName("admin");
        	bizService.saveDiscount(discount);   

            model.addAttribute("resMess", "新增优惠通道成功！");   
        } catch (Exception e) {   
            e.printStackTrace();   
            model.addAttribute("resMess", "新增优惠通道失败！");   
            throw e;   
        }   
        return "redirect:/discountlist";    
    } 
    
    @RequestMapping(value="/discount/save",method=RequestMethod.POST)  
    @ResponseBody
    public void doSave(@ModelAttribute("discount") Discount discount,
			Model model,
			@RequestParam("strValidStartDate") String strValidStartDate,
			@RequestParam("strValidEndDate") String strValidEndDate,HttpServletResponse response)
			throws Exception {
    	String resultJson = "{'success':true}";
		try {
//        	String validStartDate= discount.getValidStartDate();
//        	String validEndDate = discount.getValidEndDate();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

//			UserDetails userDetails = (UserDetails) SecurityContextHolder
//					.getContext().getAuthentication().getPrincipal();
			
			Date validStartDate = DateUtils.parseDate(strValidStartDate, "yyyy-MM-dd");
			Date validEndDate=DateUtils.parseDate(strValidEndDate, "yyyy-MM-dd");
			Date createDateTime =  df.parse(df.format(new Date()));


			discount.setValidStartDate(validStartDate);
			discount.setValidEndDate(validEndDate);
			discount.setCreateDate(createDateTime);
//			discount.setCreateName(userDetails.getUsername());
			discount.setCreateName("admin");
        	bizService.saveDiscount(discount);   
        	resultJson = "{'success':true}";
        } catch (Exception e) {   
            e.printStackTrace(); 
            resultJson = "{'msg':'新增优惠通失败!'}";
        }   
		 commonService.responseJsonBody(response, resultJson);
    } 
    
	/**
	 * 根据ID删除某优惠通道
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/discount/delete/{id}")  
	@ResponseBody
	public  Map<String,Object> delete(@PathVariable("id") String id, Model model)
			throws Exception {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			
			bizService.deleteDiscountId(id);
			result.put("success",true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "删除失败！");
			throw e;
		}
		return result;// 重定向
	}
	/**
	 * 修改优惠通道信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
//	 @RequestMapping(value="/discount/update/{id}",method=RequestMethod.GET)   
//	    public String toUpdate(@PathVariable("id") String id, Model model) throws Exception{   
//	        model.addAttribute("discount",bizService.getDiscountById(id));   
//	        return "page/discount/updateDiscount";   
//	    }   
	    @RequestMapping(value="/discount/update/{id}",method=RequestMethod.POST)   
	    @ResponseBody
		public void doUpdate(@PathVariable("id") String id, Discount discount,Model model,
				@RequestParam("strValidStartDate") String strValidStartDate,
				@RequestParam("strValidEndDate") String strValidEndDate,HttpServletResponse response)
			throws Exception{   
	    	String resultJson = "{'success':true}";
	    	model.addAttribute("discount",bizService.getDiscountById(id)); 
	        try {   
//
//				UserDetails userDetails = (UserDetails) SecurityContextHolder
//						.getContext().getAuthentication().getPrincipal();
				
				Date validStartDate = DateUtils.parseDate(strValidStartDate, "yyyy-MM-dd");
				Date validEndDate=DateUtils.parseDate(strValidEndDate, "yyyy-MM-dd");

				String createName = discount.getCreateName()==null?"admin":discount.getCreateName();
				discount.setCreateName(createName);
				discount.setValidStartDate(validStartDate);
				discount.setValidEndDate(validEndDate);
	        	bizService.saveDiscount(discount);   
	        	resultJson = "{'success':true}";
	        } catch (Exception e) {   
	            e.printStackTrace();   
	            resultJson = "{'msg':'保存失败!'}";
	            throw e;   
	        }   
	        commonService.responseJsonBody(response, resultJson);
	    }   
	    
		/**
		 * 查看优惠通道信息
		 * @param id
		 * @param model
		 * @return
		 * @throws Exception
		 */
		 @RequestMapping(value="/discount/view/{id}",method=RequestMethod.POST)   
		 @ResponseBody
		    public Object toView(@PathVariable("id") String id,Model model) throws Exception{
		        return bizService.getDiscountById(id);   
		    }  
}
