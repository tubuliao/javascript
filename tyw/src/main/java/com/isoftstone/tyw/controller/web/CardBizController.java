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

import com.isoftstone.tyw.entity.biz.CardBiz;
import com.isoftstone.tyw.service.BizService;

@Controller
public class CardBizController {
	@Autowired
	private BizService bizService;

	
	/**
	 *  返回所有产品功能列表
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="/cardBizlist", method = RequestMethod.GET)
	public String cardBizList(@PageableDefaults(value=20) Pageable pageable, Model model) {
		
		Page<CardBiz> page = bizService.listAllCardBiz(pageable);
		model.addAttribute("page",page);
		model.addAttribute("cardBizlist", page.getContent());
		model.addAttribute("pagesize", page.getSize());
		return "page/card/cardBizList";
	}
	@RequestMapping(value ="/cardBizlist", method = RequestMethod.POST)
	public String cardBizListPage(@PageableDefaults(value=20) Pageable pageable, Model model) {
		
		Page<CardBiz> page = bizService.listAllCardBiz(pageable);
		model.addAttribute("page",page);
		model.addAttribute("cardBizlist", page.getContent());
		model.addAttribute("pagesize", page.getSize());
		return "page/card/cardBizList";
	}
	/**
	 * 添加新卡
	 * @return
	 */

    @RequestMapping(value="/addCardBiz",method=RequestMethod.GET)   
    public String toAdd(Model model) throws Exception{   
        return "page/card/addCardBiz";   
    }   
    @RequestMapping(value="/addCardBiz",method=RequestMethod.POST)   
    public String doAdd(CardBiz cardBiz,Model model) throws Exception{   
        try {   
        	bizService.saveCardBiz(cardBiz);   
            model.addAttribute("resMess", "新增卡成功！");   
        } catch (Exception e) {   
            e.printStackTrace();   
            model.addAttribute("resMess", "新增卡失败！");   
            throw e;   
        }   
        return "redirect:/cardBizlist";    
    } 
	/**
	 * 根据ID删除某卡
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cardBiz/delete/{id}")   
    public String delete(@PathVariable("id") String id,Model model)throws Exception{   
        try {   
        	bizService.deleteCardBizById(id);   
            model.addAttribute("resMess", "删除成功");   
        } catch (Exception e) {   
            e.printStackTrace();   
            model.addAttribute("resMess", "删除失败");   
            throw e;   
        }   
        return "redirect:/cardBizlist";//重定向   
    }  
	
	/**
	 * 修改卡信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value="/cardBiz/update/{id}",method=RequestMethod.GET)   
	    public String toUpdate(@PathVariable("id") String id, Model model) throws Exception{   
	        model.addAttribute("cardBiz",bizService.getCardBizById(id));   
	        return "page/card/updateCardBiz";   
	    }   
	    @RequestMapping(value="/cardBiz/update/{id}",method=RequestMethod.POST)   
	    public String doUpdate(@PathVariable("id") String id, CardBiz cardBiz,Model model) throws Exception{   
	        try {   
	        	bizService.saveCardBiz(cardBiz);   
	            model.addAttribute("resMess", "更新成功！");   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	            model.addAttribute("resMess", "更新失败！");   
	            throw e;   
	        }   
	        return "redirect:/cardBizlist";//重定向    
	    }   
}
