package com.isoftstone.tyw.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.isoftstone.tyw.entity.biz.PaymentItem;
import com.isoftstone.tyw.service.BizService;

@Controller
public class PaymentItemController {
	@Autowired
	private BizService bizService;

	
	/**
	 *  返回所有缴费记录列表
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="/paymentItemlist", method = RequestMethod.GET)
	public String cardList(@PageableDefaults(value=20) Pageable pageable, Model model) {
		
		Page<PaymentItem> page = bizService.listAllPaymentItem(pageable);
		model.addAttribute("page",page);
		model.addAttribute("paymentItemlist", page.getContent());
		model.addAttribute("pagesize", page.getSize());
		return "page/card/paymentItemList";
	}
	@RequestMapping(value ="/paymentItemlist", method = RequestMethod.POST)
	public String cardListPage(@PageableDefaults(value=20) Pageable pageable, Model model) {
		
		Page<PaymentItem> page = bizService.listAllPaymentItem(pageable);
		model.addAttribute("page",page);
		model.addAttribute("paymentItemlist", page.getContent());
		model.addAttribute("pagesize", page.getSize());
		return "page/card/paymentItemList";
	}
	/**
	 * 添加新卡
	 * @return
	 */

    @RequestMapping(value="/addPaymentItem",method=RequestMethod.GET)   
    public String toAdd(Model model) throws Exception{   
        return "page/card/addPaymentItem";   
    }   
    @RequestMapping(value="/addPaymentItem",method=RequestMethod.POST)   
    public String doAdd(PaymentItem paymentItem,Model model) throws Exception{   
        try {   
        	bizService.savePaymentItem(paymentItem);   
            model.addAttribute("resMess", "新增卡成功！");   
        } catch (Exception e) {   
            e.printStackTrace();   
            model.addAttribute("resMess", "新增卡失败！");   
            throw e;   
        }   
        return "redirect:/paymentItemlist";    
    } 
	/**
	 * 根据ID删除某卡
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/paymentItem/delete/{id}")   
    public String delete(@PathVariable("id") String id,Model model)throws Exception{   
        try {   
        	bizService.deletePaymentItemById(id);   
            model.addAttribute("resMess", "删除成功");   
        } catch (Exception e) {   
            e.printStackTrace();   
            model.addAttribute("resMess", "删除失败");   
            throw e;   
        }   
        return "redirect:/paymentItemlist";//重定向   
    }  
	
	/**
	 * 修改卡信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value="/paymentItem/update/{id}",method=RequestMethod.GET)   
	    public String toUpdate(@PathVariable("id") String id, Model model) throws Exception{   
	        model.addAttribute("paymentItem",bizService.getPaymentItemById(id));   
	        return "page/card/updatePaymentItem";   
	    }   
	    @RequestMapping(value="/paymentItem/update/{id}",method=RequestMethod.POST)   
	    public String doUpdate(@PathVariable("id") String id, PaymentItem paymentItem,Model model) throws Exception{   
	        try {   
	        	bizService.savePaymentItem(paymentItem);   
	            model.addAttribute("resMess", "更新成功！");   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	            model.addAttribute("resMess", "更新失败！");   
	            throw e;   
	        }   
	        return "redirect:/paymentItemlist";//重定向    
	    }   
}
