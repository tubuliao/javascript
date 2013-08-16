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

import com.isoftstone.tyw.entity.biz.CardLog;
import com.isoftstone.tyw.service.BizService;

@Controller
public class CardLogController {
	@Autowired
	private BizService bizService;

	
	/**
	 *  返回所有卡列表
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="/cardLoglist", method = RequestMethod.GET)
	public String cardList(@PageableDefaults(value=20) Pageable pageable, Model model) {
		
		Page<CardLog> page = bizService.listAllCardLog(pageable);
		model.addAttribute("page",page);
		model.addAttribute("cardLoglist", page.getContent());
		model.addAttribute("pagesize", page.getSize());
		return "page/card/cardLogList";
	}
	@RequestMapping(value ="/cardLoglist", method = RequestMethod.POST)
	public String cardListPage(@PageableDefaults(value=20) Pageable pageable, Model model) {
		
		Page<CardLog> page = bizService.listAllCardLog(pageable);
		model.addAttribute("page",page);
		model.addAttribute("cardLoglist", page.getContent());
		model.addAttribute("pagesize", page.getSize());
		return "page/card/cardLogList";
	}
	/**
	 * 添加新卡
	 * @return
	 */

    @RequestMapping(value="/addCardLog",method=RequestMethod.GET)   
    public String toAdd(Model model) throws Exception{   
        return "page/card/addCardLog";   
    }   
    @RequestMapping(value="/addCardLog",method=RequestMethod.POST)   
    public String doAdd(CardLog cardLog,Model model) throws Exception{   
        try {   
        	bizService.saveCardLog(cardLog);   
            model.addAttribute("resMess", "新增卡成功！");   
        } catch (Exception e) {   
            e.printStackTrace();   
            model.addAttribute("resMess", "新增卡失败！");   
            throw e;   
        }   
        return "redirect:/cardLoglist";    
    } 
	/**
	 * 根据ID删除某卡
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cardLog/delete/{id}")   
    public String delete(@PathVariable("id") String id,Model model)throws Exception{   
        try {   
        	bizService.deleteCardLogById(id);   
            model.addAttribute("resMess", "删除成功");   
        } catch (Exception e) {   
            e.printStackTrace();   
            model.addAttribute("resMess", "删除失败");   
            throw e;   
        }   
        return "redirect:/cardLoglist";//重定向   
    }  
	
	/**
	 * 修改卡信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value="/cardLog/update/{id}",method=RequestMethod.GET)   
	    public String toUpdate(@PathVariable("id") String id, Model model) throws Exception{   
	        model.addAttribute("cardLog",bizService.getCardLogById(id));   
	        return "page/card/updateCardLog";   
	    }   
	    @RequestMapping(value="/cardLog/update/{id}",method=RequestMethod.POST)   
	    public String doUpdate(@PathVariable("id") String id, CardLog cardLog,Model model) throws Exception{   
	        try {   
	        	bizService.saveCardLog(cardLog);   
	            model.addAttribute("resMess", "更新成功！");   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	            model.addAttribute("resMess", "更新失败！");   
	            throw e;   
	        }   
	        return "redirect:/cardLoglist";//重定向    
	    }   
}
