/**
 * Copyright 2008 - 2012 Simcore.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.isoftstone.tyw.controller.web;



import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.auths.Agent;
import com.isoftstone.tyw.entity.auths.Firm;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.auths.Visit;
import com.isoftstone.tyw.entity.biz.Discount;
import com.isoftstone.tyw.entity.pub.LicenseView;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.BizService;
import com.isoftstone.tyw.service.ChannelService;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.FirmService;
import com.isoftstone.tyw.util.PropertiesReader;

/**
 * @author wangrui
 * 
 */
@Controller
public class ChannelController {

	@Autowired
	private ChannelService channelService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private BizService bizService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private FirmService firmService;

	/**
	 * 前台跳转到渠道商信息页面
	 * 
	 */
	@RequestMapping(value = "/channel/web/info", method = RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response,Model model) {
		//获取session对象
		HttpSession session = request.getSession();
		//从session中获取user信息
		User user =(User)session.getAttribute("user");
		Agent agent = new Agent();
		//根据用户id查询渠道商信息
		agent = channelService.getById(user.getId());
		session.setAttribute("agent", agent);
		PropertiesReader pu = PropertiesReader.getInstance();
		String path = pu.getProperties("fdfs.HttpAddress");
		session.setAttribute("httpAddress", path);
		return "page/channel/info";
	}
	
	/**
	 * 前台跳转到渠道商主页
	 * 
	 */
	@RequestMapping(value = "/channel/web/home", method = RequestMethod.GET)
	public String toHome(HttpServletRequest request,Model model) {
		//获取session对象
		HttpSession session = request.getSession();
		//从session中获取user信息
		User user =(User)session.getAttribute("user");
		//根据用户id查询当前渠道商下项目组交费的总金额
		Double totalMoney =channelService.getAgentTotalMoney(user.getId());
		Agent agent = new Agent();
		//根据用户id查询当前渠道商信息
		agent = channelService.getById(user.getId());
		session.setAttribute("agent", agent);
		PropertiesReader pu = PropertiesReader.getInstance();
		String path = pu.getProperties("fdfs.HttpAddress");
		session.setAttribute("httpAddress", path);
		//如果项目组缴费的总金额不是空
		if(totalMoney!=null && !totalMoney.equals("")){
			model.addAttribute("totalMoney", totalMoney);
		}else{
			//如果金额是空，页面上显示0
			model.addAttribute("totalMoney", 0);
		}
		
		return "page/channel/home";
	}
	
	
//	@RequestMapping(value = "/channel/web/channel", method = RequestMethod.GET)
//	public String toChannel(HttpServletRequest request,Model model) {
//		HttpSession session = request.getSession();
//		User user =(User)session.getAttribute("user");
//		List<Discount> list = bizService.getDiscountByAgentId(user.getId());
//		model.addAttribute("list",list);
//		Agent agent = new Agent();
//		agent = channelService.getById(user.getId());
//		session.setAttribute("agent", agent);
//		return "page/channel/channel";
//	}
//	
//	@RequestMapping(value = "/channel/web/news", method = RequestMethod.GET)
//	public String toNews(HttpServletRequest request,Model model) {
//		HttpSession session = request.getSession();
//		User user =(User)session.getAttribute("user");
//		Agent agent = new Agent();
//		agent = channelService.getById(user.getId());
//		session.setAttribute("agent", agent);
//		return "page/channel/news";
//	}
//	
//	@RequestMapping(value = "/channel/web/id", method = RequestMethod.GET)
//	public String toId(HttpServletRequest request,Model model) {
//		HttpSession session = request.getSession();
//		User user =(User)session.getAttribute("user");
//		List<Firm> list = firmService.getFirmByAgentId(user.getId());
//		model.addAttribute("list", list);
//		Agent agent = new Agent();
//		agent = channelService.getById(user.getId());
//		session.setAttribute("agent", agent);
//		return "page/channel/id";
//	}
	/**
	 * 前台跳转到渠道商查询项目组交费信息页
	 * 
	 */
	@RequestMapping(value = "/channel/web/account", method = RequestMethod.GET)
	public String toAccount(HttpServletRequest request,Model model) {
		//获取session对象
		HttpSession session = request.getSession();
		//从session中获取user信息
		User user =(User)session.getAttribute("user");
		Agent agent = new Agent();
		//根据用户id查询当前渠道商信息
		agent = channelService.getById(user.getId());
		session.setAttribute("agent", agent);
		return "page/channel/account";
	}
	/**
	 * 前台保存渠道商信息
	 * 
	 */
	@RequestMapping(value = "/updateAgentInfo", method = RequestMethod.POST)
	public String UpdateAgentInfo(@ModelAttribute("agent") Agent agent,
			HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			//获取session对象
			HttpSession session = request.getSession();
			//从session中获取user信息
			User sessionUser =(User)session.getAttribute("user");
			//将页面中的头像路径set到用户头像字段中
			sessionUser.setHeadUrl(request.getParameter("headUrl"));
			//将电话号码set到用户手机字段中
			sessionUser.setPhone(agent.getPhone());
			//将用户邮箱set到用户邮箱字段中
			sessionUser.setMail(agent.getEmail());
			//根据当前用户填写的手机号和用户id判断手机号是否存在，保证手机号码的唯一性
			List<User> list = accountService.checkPhoneExist(agent.getPhone(), sessionUser.getId());
			if(list.size()>0){
				PrintWriter out = null;
				try{
					//设置除出编码格式
					response.setCharacterEncoding("utf-8");
					response.setContentType("text/html; charset=utf-8");
					//获取out对象
					out = response.getWriter();
					//输出提示信息
					out.println("<script>alert('您输入的手机号已存在！'); window.history.go(-1);</script>");
				}catch(IOException e){
					e.printStackTrace();
				}finally{
					if(out != null){
						out.close();
						out = null;
					}
				}
			}else{
				//保存信息到用户表
				accountService.updateUserNoPassword(sessionUser);
				//保存信息到渠道商表
				channelService.saveAgent(agent);
				
				PrintWriter out = null;
				try{
					//设置除出编码格式
					response.setCharacterEncoding("utf-8");
					response.setContentType("text/html; charset=utf-8");
					//获取out对象
					out = response.getWriter();
					//输出提示信息
					out.println("<script>alert('保存成功！'); window.location.href='/channel/web/info';</script>");
				}catch(IOException e){
					e.printStackTrace();
				}finally{
					if(out != null){
						out.close();
						out = null;
					}
				}
			}
		 }catch (Exception e) {   
	         e.printStackTrace();  
	    } 
		
		HttpSession session = request.getSession();
		session.setAttribute("agent", agent);
		
		return null;
	}
	
//	@RequestMapping(value = "/channel/web/addNew", method = RequestMethod.GET)
//	public String toAddNew(HttpServletRequest request,Model model) {
//		
//		return "page/channel/add-news";
//	}
	
	/**
	 * 渠道商的项目组交费信息查询
	 * 
	 */
	@RequestMapping(value="/licensePagination", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> licensePagination(Pageable pageable,HttpServletRequest request) {
		//Integer pageIndex = Integer.parseInt(request.getParameter("pageIndex")) ;
		//Integer items_per_page = Integer.parseInt(request.getParameter("items_per_page")) ;
		// 分页 起始记录
		//pageIndex = pageIndex * items_per_page ;
		
		Map<String, Object> msg = new HashMap<String, Object>() ;
		//交费信息数据的总条数
		BigInteger total ; ;	
		//返回的查询结果
		List<LicenseView> result = new ArrayList<LicenseView>() ;	
		//获取session对象
		HttpSession session = request.getSession() ;
		//从session中获取user信息
		User user = (User)session.getAttribute("user") ;
		String agentId = user.getId();
		//用户格式化时间显示的格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//license的创建日期
		Date createDate = null;
		try {
			if(request.getParameter("createDate")!=null && !request.getParameter("createDate").equals("")){
				createDate = sdf.parse(request.getParameter("createDate"));
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//license的交费日期
		Date activateDate = null;
		try {
			if(request.getParameter("activateDate")!=null && !request.getParameter("activateDate").equals("")){
				activateDate = sdf.parse(request.getParameter("activateDate"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//批次号
		String batchCode = request.getParameter("batchCode");
		//当前license的状态（是否交费）
		String licenseStatus = request.getParameter("licenseStatus");
		//当前license对应的项目组名称
		String proName = request.getParameter("proName");
		//分页
		Page<LicenseView> page = channelService.listLicenseView(agentId,createDate, activateDate, batchCode, licenseStatus, proName, pageable);
		
		total = BigInteger.valueOf(page.getTotalElements());
		result = page.getContent();
		msg.put("total", total) ;
		msg.put("result", result) ;
		return msg ;
	}
}
