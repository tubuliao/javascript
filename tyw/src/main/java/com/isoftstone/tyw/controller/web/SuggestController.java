package com.isoftstone.tyw.controller.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.pub.Suggest;
import com.isoftstone.tyw.service.SuggestService;
import com.isoftstone.tyw.util.Html2Text;

/**
 * @author wangrui
 * 
 */
@Controller
public class SuggestController {
	@Autowired
	private SuggestService suggestService;
	
	/**
	 * 前台跳转到用户添加意见页面
	 * 
	 */
	@RequestMapping(value = "/toSuggest", method = RequestMethod.GET)
	public String toSuggest(HttpServletRequest request,HttpServletResponse response,Model model) {
		//跳转到指定页面
		return "page/front/suggest";
	}
	
	/**
	 * 前台保存用户意见
	 * 
	 */
	@RequestMapping(value = "/addSuggest", method = RequestMethod.POST)
	public String addSuggest(@ModelAttribute("suggest") Suggest suggest,
			HttpServletRequest request,HttpServletResponse response,Model model){
		try {	
				//获取文本域中的意见内容
				String content = Html2Text.HtmlFilter(request.getParameter("content"));
				//意见添加的时间
				suggest.setCreateDate(new Date());
				//获得内容
				suggest.setContent(content);
			    //保存意见
				suggestService.saveSuggest(suggest);
				
				PrintWriter out = null;
				try{
					//设置除出编码格式
					response.setCharacterEncoding("utf-8");
					response.setContentType("text/html; charset=utf-8");
					//获取out对象
					out = response.getWriter();
					//输出提示信息
					out.println("<script>alert('感谢您的留言，我们会及时给您反馈！'); window.location.href='/toSuggest';</script>");
				}catch(IOException e){
					e.printStackTrace();
				}finally{
					if(out != null){
						out.close();
						out = null;
					}
				}
			
		 }catch (Exception e) {   
	         e.printStackTrace();  
	    } 
		return null;
	}
	
	/**
	 * 后台进入用户意见管理页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/suggestList", method = RequestMethod.GET)
	public String suggestList(HttpServletRequest request, Model model) {
		return "page/suggest/suggestList";
	}
	
	/**
	 *  获取所有用户意见
	 * @param pageable
	 * @param model
	 * @return
	 */
	
	
	@RequestMapping(value = "/suggestlist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> suggestListPager(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String,Object> result= new HashMap<String,Object>();
		//Page<Suggest> pageAll=suggestService.listSuggest(null, pageable);
		List<Suggest> suggestList = suggestService.listSuggestBaseRows(pageable);
		BigInteger suggestTotal = suggestService.listSuggestBaseTotal();
		result.put("total",suggestTotal);
		result.put("rows",suggestList);
		return result;
	}
	
	 /**
	  * 跳转到查看意见页面
	  */
	 @RequestMapping(value="/suggest/edit/{id}", method=RequestMethod.GET)
	    public String toViewSuggestPage(@PathVariable("id") String id, Model model) throws Exception {
		    Suggest suggest = suggestService.getSuggestById(id);
	    	model.addAttribute("suggest", suggest) ;
	    	return "page/suggest/viewSuggest" ;
	    }
}
