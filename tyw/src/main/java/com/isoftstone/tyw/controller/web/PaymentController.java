package com.isoftstone.tyw.controller.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.biz.Payment;
import com.isoftstone.tyw.service.BizService;

@Controller
public class PaymentController {
	@Autowired
	private BizService bizService;

	
	/**
	 *  返回所有缴费记录列表
	 * @param pageable
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/paymentlist", method = RequestMethod.GET)
	public String paymentlist() {
		return "page/card/paymentList";
	}
	
	@RequestMapping(value = "/paymentlist/data", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> listPaymentJson(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		String orderCode=request.getParameter("orderCode");
		String discountCode=request.getParameter("discountCode");
		Page<Payment> page = bizService.listAllPayment(orderCode,discountCode,pageable);
		result.put("total",page.getTotalElements());  
		result.put("rows", page.getContent());  
		return result;
	}
//	@RequestMapping(value ="/paymentlist", method = RequestMethod.GET)
//	public String cardList(@PageableDefaults(value=20) Pageable pageable, Model model) {
//		
//		Page<Payment> page = bizService.listAllPayment(pageable);
//		
//		model.addAttribute("paymentlist", page.getContent());
//		model.addAttribute("pagesize", page.getSize());
//		model.addAttribute("page",page);
//		return "page/card/paymentList";
//	}
//	@RequestMapping(value ="/paymentlist", method = RequestMethod.POST)
//	public String cardListPage(@PageableDefaults(value=20) Pageable pageable, Model model) {
//		
//		Page<Payment> page = bizService.listAllPayment(pageable);
//		
//		model.addAttribute("paymentlist", page.getContent());
//		model.addAttribute("pagesize", page.getSize());
//		model.addAttribute("page",page);
//		return "page/card/paymentList";
//	}
	/**
	 * 添加新卡
	 * @return
	 */

    @RequestMapping(value="/addPayment",method=RequestMethod.GET)   
    public String toAdd(Model model) throws Exception{   
        return "page/card/addPayment";   
    }   
    @RequestMapping(value="/addPayment",method=RequestMethod.POST)   
    public String doAdd(Payment payment,Model model) throws Exception{   
        try {   
        	bizService.savePayment(payment);   
            model.addAttribute("resMess", "新增卡成功！");   
        } catch (Exception e) {   
            e.printStackTrace();   
            model.addAttribute("resMess", "新增卡失败！");   
            throw e;   
        }   
        return "redirect:/paymentlist";    
    } 
	/**
	 * 根据ID删除某卡
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/payment/delete/{id}")  
	@ResponseBody
	public  Map<String,Object> delete(@PathVariable("id") String id, Model model)
			throws Exception {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			
			bizService.deletePaymentById(id); 
			result.put("success",true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "删除失败！");
			throw e;
		}
		return result;// 重定向
	}
	
	/**
	 * 修改卡信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value="/payment/update/{id}",method=RequestMethod.GET)   
	    public String toUpdate(@PathVariable("id") String id, Model model) throws Exception{   
	        model.addAttribute("payment",bizService.getPaymentById(id));   
	        return "page/card/updatePayment";   
	    }   
	    @RequestMapping(value="/payment/update/{id}",method=RequestMethod.POST)   
	    public String doUpdate(@PathVariable("id") String id, Payment payment,Model model) throws Exception{   
	        try {   
	        	bizService.savePayment(payment);   
	            model.addAttribute("resMess", "更新成功！");   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	            model.addAttribute("resMess", "更新失败！");   
	            throw e;   
	        }   
	        return "redirect:/paymentlist";//重定向    
	    }   
	    
		/**
		 * 查看缴费记录详细信息
		 * @param id
		 * @param model
		 * @return
		 * @throws Exception
		 */
		 @RequestMapping(value="/payment/info/{id}",method=RequestMethod.GET)   
		    public String paymentInfo(@PathVariable("id") String id, Model model) throws Exception{   
		        model.addAttribute("payment",bizService.getPaymentById(id));   
		        return "page/card/viewPayment";   
		    }   
  
}
