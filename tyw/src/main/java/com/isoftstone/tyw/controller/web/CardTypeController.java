package com.isoftstone.tyw.controller.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.biz.CardBiz;
import com.isoftstone.tyw.entity.biz.CardType;
import com.isoftstone.tyw.entity.biz.Payment;
import com.isoftstone.tyw.service.BizService;

@Controller
public class CardTypeController {
	@Autowired
	private BizService bizService;

	
	/**
	 *  返回所有卡类型列表
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/cardTypelist", method = RequestMethod.GET)
	public String paymentlist() {
		return "page/card/cardTypeList";
	}
	
	@RequestMapping(value = "/cardTypelist/data", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> listPaymentJson(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		String title=request.getParameter("title");
		String createName=request.getParameter("createName");
		Page<CardType> page = bizService.listAllCardType(title,createName,pageable);
		result.put("total",page.getTotalElements());  
		result.put("rows", page.getContent());  
		return result;
	}
	/**
	 * 添加新卡类型
	 * @return
	 */

    @RequestMapping(value="/addCardType",method=RequestMethod.GET)   
    public String toAdd(Model model) throws Exception{   
        return "page/card/addCardType";   
    }   
    
    @RequestMapping(value="/addCardType",method=RequestMethod.POST)   
    public String doAdd(CardType cardType,Model model) throws Exception{   
        try { 
//        	UserDetails userDetails = (UserDetails) SecurityContextHolder
//					.getContext().getAuthentication().getPrincipal();
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        	Date createDateTime =  df.parse(df.format(new Date())); //创建时间
        	cardType.setCreateName("admin");
        	cardType.setCreateDate(createDateTime);
            List lst=	cardType.getCardBizs();
        	bizService.saveCardType(cardType);   
//        	CardBiz cardBiz = (CardBiz)cardType.getCardBizs().toArray()[0];
            model.addAttribute("resMess", "新增卡类型成功！");   
        } catch (Exception e) {   
            e.printStackTrace();   
            model.addAttribute("resMess", "新增卡类型失败！");   
            throw e;   
        }   
        return "redirect:/cardTypelist";    
    } 
	/**
	 * 根据ID删除某卡类型
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/cardType/delete/{id}")  
	@ResponseBody
	public  Map<String,Object> delete(@PathVariable("id") String id, Model model)
			throws Exception {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			
			bizService.deleteCardTypeById(id); 
			result.put("success",true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "删除失败！");
			throw e;
		}
		return result;// 重定向
	}
	/**
	 * 查看卡类型信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value="/cardType/view/{id}",method=RequestMethod.GET)   
	    public String toView(@PathVariable("id") String id, Model model) throws Exception{   
	        model.addAttribute("cardType",bizService.getCardTypeById(id));   
	        return "page/card/viewCardType";   
	    }   
	    
	    /**
		 * 修改卡类型信息
		 * @param id
		 * @param model
		 * @return
		 * @throws Exception
		 */
		 @RequestMapping(value="/cardType/update/{id}",method=RequestMethod.GET)   
		    public String toUpdate(@PathVariable("id") String id, Model model) throws Exception{   
		        model.addAttribute("cardType",bizService.getCardTypeById(id));   
		        return "page/card/updateCardType";   
		    }   
		  @RequestMapping(value="/cardType/update/{id}",method=RequestMethod.POST)   
		    public String doUpdate(@PathVariable("id") String id, CardType cardType,Model model) throws Exception{   
		        try {   
		        	bizService.saveCardType(cardType);   
		            model.addAttribute("resMess", "更新成功！");   
		        } catch (Exception e) {   
		            e.printStackTrace();   
		            model.addAttribute("resMess", "更新失败！");   
		            throw e;   
		        }   
		        return "redirect:/cardTypelist";//重定向    
		    }   
}
