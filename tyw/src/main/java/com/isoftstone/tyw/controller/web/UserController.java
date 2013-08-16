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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.isoftstone.tyw.entity.auths.Additional;
import com.isoftstone.tyw.entity.auths.Educational;
import com.isoftstone.tyw.entity.auths.Occupational;
import com.isoftstone.tyw.entity.auths.Project;
import com.isoftstone.tyw.entity.auths.Role;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.biz.CardLog;
import com.isoftstone.tyw.entity.info.Bookmark;
import com.isoftstone.tyw.entity.info.Classification;
import com.isoftstone.tyw.entity.pub.City;
import com.isoftstone.tyw.entity.pub.Province;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.BizService;
import com.isoftstone.tyw.service.BookmarkService;
import com.isoftstone.tyw.service.CardLogService;
import com.isoftstone.tyw.service.ClassificationService;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.FdfsService;
import com.isoftstone.tyw.service.PubService;
import com.isoftstone.tyw.util.Pager;
import com.isoftstone.tyw.util.PropertiesReader;

/**
 * @author zhanglei
 * 
 */
@Controller
public class UserController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private FdfsService fdfsService;
	@Autowired
	private BookmarkService bookmarkService ;

	@Autowired
	private BizService bizService ;
	@Autowired
	private CardLogService cardLogService ;
	@Autowired
	private ClassificationService classificationService ;

	@Autowired
	private PubService pubService ;

	private static final Logger log = Logger
			.getLogger(UserController.class);
	@RequestMapping(value = "/tywadmin", method = RequestMethod.GET)
	public String index(HttpServletRequest request,Model model) {
		
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = accountService.loadUserByUsername(userDetails.getUsername());
			model.addAttribute("user",user);
			HttpSession session = request.getSession();
		    session.setAttribute("user", user);
		    this.ValidationDataAccess(user, session);
		}
		return "redirect:/index.jsp";
	}
	
	//Validation data access
	/**
	 * 初始化（验证）本用户的数据权限
	 * @param user
	 * @param session
	 */
	public void ValidationDataAccess(User user,HttpSession session){
		CardLog cardLog = null;
		List<CardLog> listCardLog = bizService.getCardLogByAuthsUserIdAndBizTypeASC(user.getId(), 0);//类型是0的数据，本期结余字段为时间
		//System.out.println("listCardLog.size():"+listCardLog.size());
		if(listCardLog==null||"".equals(listCardLog)||listCardLog.size()<1){
			session.setAttribute("DetailAccess", "2");//数据权限为0，不能访问详细.从来没交过费
		}else{
			cardLog = listCardLog.get(listCardLog.size()-1);//如果此用户台账有信息，就取出最后一条。
			Integer overTotal = cardLog.getOverTotal();//本期结余
			Date Datedate = cardLog.getDataDate();//充值日期
			Calendar calendar = Calendar.getInstance();//日历对象
			calendar.setTime(Datedate);
			calendar.add(Calendar.DATE, overTotal);
			Date date = calendar.getTime();//服务到期时间
			Date currentDate = new Date();//当前时间
			if(currentDate.before(date)){//当前时间在服务到期之前
				session.setAttribute("DetailAccess", "1");//数据权限为1，能访问详细。
			}else{
				session.setAttribute("DetailAccess", "0");//数据权限为0，不能访问详细
			}
			
		}
	}
	
	@RequestMapping(value = "/toBackGround", method = RequestMethod.GET)
	public String toBackGround(HttpServletRequest request,Model model) {
		
		HttpSession session = request.getSession();
	    User user = (User)session.getAttribute("user");
	    model.addAttribute("user",user);
	    log.info(user.getId()+" 进入后台");
		return "index";
	}
	
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String regist(Model model) {
		return "redirect:/regist.jsp";
	}
	
	@RequestMapping(value = "/loginView", method = RequestMethod.GET)
	public String loginView(Model model) {
		return "redirect:/login.jsp";
	}
	
	@RequestMapping(value = "/validateClientUser", method = RequestMethod.GET)
	@ResponseBody
	public String validateClientUser(HttpServletRequest request,HttpServletResponse response) {
		String username=request.getParameter("username");
		User user = null;
		try{
			user = accountService.loadUserByUsername(username);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(null==user){
			return "1";
		}else{
			return "0";
		}
	}
	
	@RequestMapping(value = "/validateClientUserName", method = RequestMethod.GET)
	@ResponseBody
	public String validateClientUserName(HttpServletRequest request,HttpServletResponse response) {
		String username=request.getParameter("j_username");
		User user = null;
		try{
			user = accountService.loadUserByUsername(username);
			if(null==user){
				user = accountService.loadUserByMail(username);
			}
			if(null==user){
				user = accountService.loadUserByPhone(username);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(null==user){
			return "0";
		}else{
			return "1";
		}
	}
	
	@RequestMapping(value = "/validateClientMail", method = RequestMethod.GET)
	@ResponseBody
	public String validateClientMail(HttpServletRequest request,HttpServletResponse response) {
		System.err.println("single");
		String mail=request.getParameter("mail");
		System.err.println("mail:"+mail);
		User user = null;
		try{
			user = accountService.loadUserByMail(mail);
			System.err.println("user:"+user);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if(null==user){
			return "1";
		}else{
			return "0";
		}
	}
	
	@RequestMapping(value = "/validateCodeResponse", method = RequestMethod.GET)
	@ResponseBody
	public String validateCodeResponse(HttpServletRequest request,HttpServletResponse response) {
		// 将四位数字的验证码从Session中取出。
        HttpSession session = request.getSession();
        String code = (String)session.getAttribute("rand");
        String validateCode=request.getParameter("validateCode");
        if(code.equalsIgnoreCase(validateCode)){//验证码比较不区分大小写
        	return "1";
        }
        return "0";
	}
	
	@RequestMapping(value = "/personCenter" ,method = RequestMethod.POST)
	public String login(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String id = user.getId();
		
		List<Project> project = accountService.getProjectByAdditionalId(id);//通过用户ID得到对应项目信息列表
	    List<Occupational> occup = accountService.getOccupByAdditionalId(id);//通过用户ID得到对应工作信息列表
	    List<Educational> edu = accountService.getEduByAdditionalId(id);//通过用户ID得到对应工作信息列表
	    
		model.addAttribute("user",user);
		model.addAttribute("project",project);
		model.addAttribute("occup",occup);
		model.addAttribute("educational",edu);
		
		
		return "page/front/person";
	}
	
	
	
	@RequestMapping(value="/validateCode",method = RequestMethod.GET)
	public void validateCode(HttpServletRequest request,HttpServletResponse response){
		printCode(request,response);    
	}
	
	/**
	 * 打印验证码
	 * @param request
	 * @param response
	 */
	public void printCode(HttpServletRequest request,HttpServletResponse response){
		 // 验证码图片的宽度。
        int width = 70;
        // 验证码图片的高度。
        int height = 30;
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        // 创建一个随机数生成器类。
        Random random = new Random();

        // 设定图像背景色(因为是做背景，所以偏淡)
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Times New Roman", Font.HANGING_BASELINE, 28);
        // 设置字体。
        g.setFont(font);

        // 画边框。
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到。
//g.setColor(Color.GRAY);
        g.setColor(getRandColor(160,200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();

        // 设置默认生成4个验证码
        int length = 4;
        // 设置备选验证码:包括"a-z"和数字"0-9"
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        int size = base.length();

        // 随机产生4位数字的验证码。
        for (int i = 0; i < length; i++) {
            // 得到随机产生的验证码数字。
            int start = random.nextInt(size);
            String strRand = base.substring(start, start + 1);

            // 用随机产生的颜色将验证码绘制到图像中。
// 生成随机颜色(因为是做前景，所以偏深)
//g.setColor(getRandColor(1, 100));
            
//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));

            g.drawString(strRand, 15 * i + 6, 24);

            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。
        HttpSession session = request.getSession();
        session.setAttribute("rand", randomCode.toString());

        //图象生效
        g.dispose();

        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        try{
	         ServletOutputStream sos = response.getOutputStream();
	         ImageIO.write(buffImg, "jpeg", sos);
	         sos.flush();
	         sos.close();
        }catch(Exception ex){
       	 ex.printStackTrace();
        }
	}
	
	Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
	
	

	/**
	 * 前台用户注册
	 * 分为：普通会员注册和企业会员注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/regists" ,method = RequestMethod.POST)
	public String signIn(@ModelAttribute("user") User user,
			HttpServletRequest request ,HttpServletResponse response ,Model model){
		User u = null;
		boolean bePhone = false;//是否手机注册
		try {
			int userType = user.getUserType();
			switch(userType){
				case 1://Ordinary members(普通会员)
					user.getAdditional().setUser(user);//附加信息
					user.setEnable(true);//是否能用
					user.setNonLocked(true);//是否锁定
					Set<Role> rs = new TreeSet<Role>();//声明权限集合
					Role r = accountService.getById("5");//取得个人权限
					rs.add(r);//把个人权限放入权限集合中
					user.setRoles(rs);//把权限集合赋给注册的个人用户
					if("".equals(user.getMail())||null==user.getMail()){//如果邮箱为空则说明用手机注册的
						user.setUsername(user.getPhone());//手机注册，把手机号放入用户名字段
						user.setStatus("1");//注册的时候用户状态为1，可用状态
						bePhone = true;
					}else{
						user.setUsername(user.getMail());//邮箱注册，把邮箱放入用户名字段
						user.setStatus("0");//注册的时候用户状态为0，不可用状态
					}
					
					user.setFirm(null);//Enterprise information set is empty(企业信息设置为空)
					user.setAgent(null);//Distributors additional information set is empty(渠道商附加信息设置为空)
					break;
				case 2://Enterprise members(企业会员)
					user.setEnable(true);
					user.setNonLocked(true);
					user.setStatus("0");//注册的时候用户状态为0，不可用状态
					user.getFirm().setUser(user);//Set up enterprise member of additional information(设置企业会员附加信息)
					break;
			}
			/*String mail = user.getMail();
			String phone = user.getPhone();
			String username = user.getUsername();
			User user1 = accountService.getByMail(mail);
			User user2 = accountService.getByPhone(phone);
			User user3 = accountService.getByUsername(username);
			if(null!=user1||null!=user2||null!=user3){
				return "page/front/regist";
			}else{*/
				u = accountService.saveUser(user);
			//}
		 }catch (Exception e) {   
	         e.printStackTrace();   
	    } 
		String id = u.getId();//通过用户得到用户ID
		model.addAttribute("uId", id);
		
		HttpSession session = request.getSession();
		session.setAttribute("user", u);
		if(bePhone){
			return "redirect:/login.jsp";
		}else{
			return "forward:/setEmail";
		}
	}
	
	
	
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String userPage() {
		return "page/system/user";
	}
	
	@RequestMapping(value = "/userlist/data", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> listTagJson(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		String username=request.getParameter("username");
		String aliasname=request.getParameter("aliasname");
		
		String ut = request.getParameter("userType");
		Integer userType= null;
		if(ut!=null&&!"".equals(ut)){
			userType=Integer.valueOf(ut);
		}
		
		String ea = request.getParameter("enable");
		Boolean enable = null;
		if(ea!=null&&!"".equals(ea)){
			enable = "1".equals(ea)?true:false;
		}
		
		String nl = request.getParameter("nonLocked");
		Boolean nonLocked = null;
		if(nl!=null&&!"".equals(nl)){
			nonLocked= "0".equals(nl)?true:false;
		}
		
		Pager page = accountService.listUser(username,aliasname,userType,enable,nonLocked,pageable);
		result.put("total",page.getRowCount());  
		result.put("rows", page.getResult());  
		return result;
	}
	
	@RequestMapping(value = "/person/myThemes")
	public String toMyThemes(HttpServletRequest request,Model model) {
		return "page/front/myThemes";
	}
	
	
	/**
	 * 保存用户
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
	@ResponseBody
	public void doSave(@ModelAttribute("user") User user,
			@RequestParam("str_birthday") String str_birthday,HttpServletResponse response){
		String resultJson = "{'success':true}";
		try {
			int userType = user.getUserType();
			Date birthday = null;
			switch(userType){
				case 1:
					
					if(str_birthday!=null &&!"".equals(str_birthday))
						birthday = DateUtils.parseDate(str_birthday,"yyyy-MM-dd");
					user.setAgent(null);
					user.setFirm(null);
					user.getAdditional().setBirthday(birthday);
					user.getAdditional().setUser(user);
					user.getAdditional().setName(user.getAliasname());
					user.setPhone(user.getAdditional().getMobile());
					user.setMail(user.getAdditional().getEmail());
					break;
				case 2:
					user.setAdditional(null);
					user.setAgent(null);
					user.getFirm().setUser(user);
					break;
				case 3:
					user.setFirm(null);
					user.setAdditional(null);
					user.getAgent().setUser(user);
					user.setPhone(user.getAgent().getPhone());
					user.setMail(user.getAgent().getEmail());
					break;
				case 4:
					//给管理员添加上个人空间的表关联
					Additional additional = new Additional();
					user.setAdditional(additional);
					user.getAdditional().setUser(user);
					//user.setAdditional(null);
					user.setAgent(null);
					user.setFirm(null);
					break;
			}	
			// 是否首次登录，默认为"0"：从未登录过, 1：已经登陆过
			user.setFirstLogin("0");
			accountService.saveUser(user);
			resultJson = "{'success':true}";
		 }catch (Exception e) {   
	         e.printStackTrace();  
	         resultJson = "{'msg':'保存失败!'}";
	    } 
		commonService.responseJsonBody(response, resultJson);
	}

	/**
	 * 修改用户密码
	 * @param user
	 * @param response
	 */
	@RequestMapping(value = "/user/changepwd", method = RequestMethod.POST)
	@ResponseBody
	public void doChangepwd(@ModelAttribute("user") User user,HttpServletResponse response){
		String resultJson = "{'success':true}";
		response.setContentType("text/html;charset=utf-8");
		try {
			String password = user.getPassword();//更新的新密码
			Md5PasswordEncoder md5 = new Md5PasswordEncoder(); 
			password = md5.encodePassword(user.getPassword(), user.getUsername());//加密之后的新密码
			String oldPassword = accountService.getUserById(user.getId()).getPassword();//通过用户ID得到旧密码
			if(!oldPassword.equalsIgnoreCase(password)){//比较新旧密码，在新旧密码不相同的情况下才更新密码，不然不错操作。
				if(accountService.modifyPassword(user)){
					resultJson = "{'success':true}";
				}else{
					resultJson = "{'msg':'密码修改失败!'}";
				}
			}else{
				resultJson = "{'msg':'新密码和旧密码相同!'}";
			}
		 }catch (Exception e) {   
	         e.printStackTrace();   
	         resultJson = "{'msg':'密码修改失败!'}";
	    } 
		commonService.responseJsonBody(response, resultJson);
	}
	 
	
	@RequestMapping(value = "/user/update/{id}", method = RequestMethod.POST)
	@ResponseBody
	public void doUpdate(@PathVariable("id") String id,@ModelAttribute("user") User user,
			@RequestParam("str_birthday") String str_birthday,HttpServletResponse response){
		String resultJson = "{'success':true}";
		try {
			// 修改 密码处理
			User u = accountService.getUserById(id);
			Boolean flag = true;	// 默认修改了密码
			if(user.getPassword() == null || "".equals(user.getPassword())) {	// 没有修改密码
				if(u != null) {
					if(u.getPassword() != null && !"".equals(u.getPassword())) {
						user.setPassword(u.getPassword());	// 保存原来的密码
						flag = false;
					}
				}
			}
			
			int userType = user.getUserType();
			Date birthday = null;
			user.setId(id);
			switch(userType){
				case 1:
					if(str_birthday!=null &&!"".equals(str_birthday))
						birthday = DateUtils.parseDate(str_birthday,"yyyy-MM-dd");
					user.setAgent(null);
					user.setFirm(null);
					user.getAdditional().setBirthday(birthday);
					user.getAdditional().setId(id);
					user.getAdditional().setUser(user);
					user.getAdditional().setName(user.getAliasname());
					user.setPhone(user.getAdditional().getMobile());
					user.setMail(user.getAdditional().getEmail());
					break;
				case 2:
					user.setAdditional(null);
					user.setAgent(null);
					user.getFirm().setId(id);
					user.getFirm().setUser(user);
					break;
				case 3:
					user.setFirm(null);
					user.setAdditional(null);
					user.getAgent().setId(id);
					user.getAgent().setUser(user);
					user.setPhone(user.getAgent().getPhone());
					user.setMail(user.getAgent().getEmail());
					break;
				case 4:
					user.setAdditional(null);
					user.setAgent(null);
					user.setFirm(null);
					break;
			}	
			if(flag) {	// 修改了密码
				accountService.saveUser(user);
			} else {	
				accountService.updateUserNoPassword(user);
			}
			resultJson = "{'success':true}";
		 }catch (Exception e) {   
	         e.printStackTrace();  
	         resultJson = "{'msg':'保存失败!'}";
	    } 
		commonService.responseJsonBody(response, resultJson);
	}
	
	/**
	 * 删除用户信息
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String,Object> delete(@PathVariable("id") String id, Model model)
			throws Exception {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			
			accountService.deleteUser(id);
			result.put("success",true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "删除失败！");
			throw e;
		}
		return result;
	}
	/**
	 * 根据用户名查找用户
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/validateUser", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String,Object> validateUser(@PathVariable("userName") String userName, Model model)
			throws Exception {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
            if(userName!=null){
               //查询用户是否存在
            	User user=accountService.loadUserByUsername(userName);
               if(user==null){ //用户不存在
            	   result.put("success",true);
               }else{
            	   result.put("success",false);
               }
            }else{
            	result.put("success",false);
            } 

		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "查找用户失败！");
			throw e;
		}
		return result;
	}
	
	/**
	 * 前台个人中心
	 * 保存用户教育信息
	 * 
	 * @param educational
	 * @return
	 */
	@RequestMapping(value = "/personCenterSaveEduInfo", method = RequestMethod.POST)
	public String doSaveEduInfo(@ModelAttribute("educational") Educational educational,
			HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			accountService.saveUserEduInfo(educational);
		 }catch (Exception e) {   
	         e.printStackTrace();  
	    } 
	    String id = educational.getAdditionalId();//得到用户ID
	    User user = accountService.getUserById(id);//通过用户ID得到用户表基础信息
	    
	    HttpSession session = request.getSession();
	    session.setAttribute("user", user);
	    int proPercent = accountService.getProgressPercent(user.getId());
		model.addAttribute("sumTotal", proPercent);
		model.addAttribute("hover","2");
		return "forward:/personCenter";
	}
	
	/**
	 * ajax保存用户教育信息
	 * @param educational
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxSaveEduInfo", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxSaveEduInfo(@ModelAttribute("educational") Educational educational,
			HttpServletRequest request,HttpServletResponse response,Model model){
		int flag = 1;
		try {
			accountService.saveUserEduInfo(educational);
			flag = 0;
		 }catch (Exception e) {   
	         e.printStackTrace(); 
	         flag = 1;
	    } 
	    
		return flag+"";
	}
	
	@RequestMapping(value = "/web/welcome",method = RequestMethod.GET)
	@ResponseBody
	public String ajaxWelcome(HttpServletRequest request,HttpServletResponse response,Model model){
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String nextAlert= request.getParameter("nextAlert");
		if(null!=user){
			String firstLogin = "1";
			if("1".equals(nextAlert)||"0".equals(nextAlert)){
				firstLogin=nextAlert;
			}
			user.setFirstLogin(firstLogin);
			accountService.updateUserNoPassword(user);
			//System.out.println("firstLogin:"+firstLogin);
		}
		
		return "success";
	}
	
	/**
	 * 保存用户项目经历
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxSaveProjectInfo", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxSaveProjectInfo(Project project,
			HttpServletRequest request,HttpServletResponse response,Model model){
		int flag = 1;
		try {
			accountService.saveUserProjectInfo(project);
			flag = 0;
		 }catch (Exception e) {   
	         e.printStackTrace(); 
	         flag = 1;
	    } 
	    
		return flag+"";
	}
	
	/**
	 * ajax保存工作经历
	 * @param occupational
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ajaxSaveOccpInfo", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxSaveOccpInfo(Occupational occupational,
			HttpServletRequest request,HttpServletResponse response,Model model){
		int flag = 1;
		try {
			accountService.saveUserOccupInfo(occupational);
			flag = 0;
		 }catch (Exception e) {   
	         e.printStackTrace(); 
	         flag = 1;
	    } 
	    
		return flag+"";
	}
	
	
	/**
	 * 删除教育信息
	 * @param educational
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteEducationInfo", method = RequestMethod.POST)
	@ResponseBody
	public String deleteEducationInfo(Educational educational,
			HttpServletRequest request,HttpServletResponse response,Model model){
		int flag = 1;
		try {
			accountService.deleteEducationInfo(educational.getId());
			flag = 0;
		 }catch (Exception e) {   
	         e.printStackTrace();
	         flag = 1;
	    } 
	     
		return flag+"";
	}
	
	/**
	 * 删除工作经历
	 * @param occupational
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteOccupInfo", method = RequestMethod.POST)
	@ResponseBody
	public String deleteOccupInfo(Occupational occupational,
			HttpServletRequest request,HttpServletResponse response,Model model){
		int flag = 1;
		try {
			accountService.deleteOccupInfo(occupational.getId());
			flag = 0;
		 }catch (Exception e) {   
	         e.printStackTrace();
	         flag = 1;
	    } 
	     
		return flag+"";
	}
	
	
	/**
	 * 删除项目经验
	 * @param project
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
	@ResponseBody
	public String deleteProject(Project project,
			HttpServletRequest request,HttpServletResponse response,Model model){
		int flag = 1;
		try {
			accountService.deleteProject(project.getId());
			flag = 0;
		 }catch (Exception e) {   
	         e.printStackTrace();
	         flag = 1;
	    } 
	     
		return flag+"";
	}
	
	
	/**
	 * 验证手机号是否存在
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/validateIsExistPhone", method = RequestMethod.POST)
	@ResponseBody
	public boolean validateIsExistPhone(
			HttpServletRequest request,HttpServletResponse response,Model model){
		boolean flag = true;
		try {
			String phone = request.getParameter("phone");
			
			 HttpSession session = request.getSession();
			 User suser = (User)session.getAttribute("user");
			 if(!StringUtils.equals(phone, suser.getPhone())){
				User user =	accountService.getByPhone(phone);
				if(user !=null && StringUtils.isNotBlank(user.getId())){
					flag = false;
				}
			 } 
			
		 }catch (Exception e) {   
	         e.printStackTrace();
	         flag = false;
	    } 
	    //System.out.println("flag="+flag);
		return flag;
	}
	
	/**
	 * 前台个人中心
	 * 保存用户工作经历信息
	 * 
	 * @param occupational
	 * @return
	 */
	@RequestMapping(value = "/personCenterFormOccupInfo", method = RequestMethod.POST)
	public String SaveOccuInfo(@ModelAttribute("occupational") Occupational occupational,
			HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			accountService.saveUserOccupInfo(occupational);
		 }catch (Exception e) {   
	         e.printStackTrace();  
	    } 
		String id = occupational.getAdditionalId();//得到用户ID
	    User user = accountService.getUserById(id);//通过用户ID得到用户表基础信息

	    HttpSession session = request.getSession();
		session.setAttribute("user", user);
		int proPercent = accountService.getProgressPercent(user.getId());
		model.addAttribute("sumTotal", proPercent);
		model.addAttribute("hover","3");
		return "forward:/personCenter";
	}
	
	/**
	 * 前台个人中心
	 * 保存用户项目信息
	 * 
	 * @param occupational
	 * @return
	 */
	@RequestMapping(value = "/personCenterFormProjectInfo", method = RequestMethod.POST)
	public String SaveProjectInfo(@ModelAttribute("project") Project project,
			HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			accountService.saveUserProjectInfo(project);
		 }catch (Exception e) {   
	         e.printStackTrace();  
	    } 
		String id = project.getAdditionalId();//得到用户ID
	    User user = accountService.getUserById(id);//通过用户ID得到用户表基础信息

	    HttpSession session = request.getSession();
		session.setAttribute("user", user);
		int proPercent = accountService.getProgressPercent(user.getId());
		model.addAttribute("sumTotal", proPercent);
		model.addAttribute("hover","4");
		return "forward:/personCenter";
	}
	
	/**
	 * 前台个人中心
	 * 保存用户项目信息
	 * 
	 * @param occupational
	 * @return
	 */
	@RequestMapping(value = "/personCenterUpdateUserInfo", method = RequestMethod.POST)
	public String UpdateUserInfo(@ModelAttribute("user") User user,@RequestParam("str_birthday") String str_birthday,
			HttpServletRequest request,HttpServletResponse response,Model model){
		//从session中获取user 是为了避免丢失 用户角色信息
		HttpSession session = request.getSession();
		User sessionuser = (User)session.getAttribute("user"); 
		try {
			if(StringUtils.isNotBlank(str_birthday)){
				Date birthday =  DateUtils.parseDate(str_birthday.trim(),"yyyy-MM-dd");
				user.getAdditional().setBirthday(birthday);
				user.getAdditional().setUser(user);
			}
			//个人中心中修改的关于用户的信息如下
			sessionuser.setHeadUrl(user.getHeadUrl());
			sessionuser.setMail(user.getMail());
			sessionuser.setAliasname(user.getAliasname());
			sessionuser.setPhone(user.getPhone());
			sessionuser.setAdditional(user.getAdditional());
			sessionuser.getAdditional().setEmail(user.getMail());
			sessionuser.getAdditional().setMobile(user.getPhone());
			sessionuser.getAdditional().setUser(sessionuser);
			
			accountService.updateUserNoPassword(sessionuser);
		 }catch (Exception e) {   
	         e.printStackTrace();  
	    } 
		int proPercent = accountService.getProgressPercent(user.getId());
		model.addAttribute("sumTotal", proPercent);
		
		//更新session
		session.setAttribute("user", sessionuser);
		model.addAttribute("hover","1");
		return "forward:/personCenter";
	}
	
	/*************前台***********************非验证框架登陆**************************************************************/
	@RequestMapping(value = "/denglu", method = RequestMethod.GET)
	public String denglu(Model model) {
		return "page/front/denglu";
	}
	
	@RequestMapping(value = "/dengluok", method = RequestMethod.GET)
	public String dengluok(@ModelAttribute("user") User user,
			HttpServletRequest request,HttpServletResponse response,Model model){
		User u = null;
		if (u == null) {
			u = accountService.getByUsername(user.getUsername());
		}
		if(u == null){
			u = accountService.getByMail(user.getUsername());
		}
		if(u == null){
			u = accountService.getByPhone(user.getUsername());
		}
		if(u == null){
			throw new UsernameNotFoundException(user.getUsername());
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("user", u);
		model.addAttribute("hover","1");
		return "forward:/personCenter1";
	}
	
	/**
	 * 跳转到个人中心首页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/personCenter1" ,method = RequestMethod.GET)
	public String login1(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		String id = user.getId();
		
		List<Project> project = accountService.getProjectByAdditionalId(id);//通过用户ID得到对应项目信息列表
	    List<Occupational> occup = accountService.getOccupByAdditionalId(id);//通过用户ID得到对应工作信息列表
	    List<Educational> edu = accountService.getEduByAdditionalId(id);//通过用户ID得到对应工作信息列表
	    int proPercent = accountService.getProgressPercent(id);
		model.addAttribute("sumTotal", proPercent);
		
		model.addAttribute("user",user);
		model.addAttribute("project",project);
		model.addAttribute("occup",occup);
		model.addAttribute("educational",edu);
		
		
		return "page/front/person";
	}
	
	@RequestMapping(value = "/setEmail" ,method = RequestMethod.POST)
	public String setEmail(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		model.addAttribute("user",user);
		return "page/front/email";
	}
	
	/**
	 * 更改用户状态
	 * @param user
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/modifyStatus" ,method = RequestMethod.GET)
	public String modifyStatus(@ModelAttribute("user") User user,
			HttpServletRequest request,HttpServletResponse response,Model model){
			accountService.modifyStatus("1", user.getId());
			return "redirect:/login.jsp";//修改成功跳转到登陆页面
	}
	
	/**
	 * 跳转到修改密码的页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/personCentertoModifyPassword" ,method = RequestMethod.GET)
	public String toModifyPassword(
			HttpServletRequest request,HttpServletResponse response,Model model){
			HttpSession session = request.getSession();
		    User user = (User)session.getAttribute("user");
		    int proPercent = accountService.getProgressPercent(user.getId());
			model.addAttribute("sumTotal", proPercent);
		    model.addAttribute("user",user);
			return "page/front/updatePassword";//修改成功跳转到登陆页面
	}
	
	/**
	 * 前台修改个人用户密码
	 * @param frontuser
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/personalCenterModifyPassword" ,method = RequestMethod.POST)
	public String modifyPassword(User frontuser,
			HttpServletRequest request,HttpServletResponse response,Model model){
			HttpSession session = request.getSession();
		    User user = (User)session.getAttribute("user");
		    //重新设置密码
		    user.setPassword(frontuser.getPassword());
		    accountService.saveUser(user);
		    //System.out.println("the lastly password:"+user.getPassword());
		    model.addAttribute("user",user);
			return "page/front/skipPage";//修改成功跳转到登陆页面
	}
	
	/**
	 * 跳转验证邮箱的页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/personCentertoValidateEmail" ,method = RequestMethod.GET)
	public String toValidateEmail(
			HttpServletRequest request,HttpServletResponse response,Model model){
			HttpSession session = request.getSession();
		    User user = (User)session.getAttribute("user");
		    model.addAttribute("user",user);
			return "page/front/validateEmail";//修改成功跳转到登陆页面
	}
	
	
	/**
	 * 跳转验证手机的页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/personCentertoValidatePhone" ,method = RequestMethod.GET)
	public String toValidatePhone(
			HttpServletRequest request,HttpServletResponse response,Model model){
			HttpSession session = request.getSession();
		    User user = (User)session.getAttribute("user");
		    model.addAttribute("user",user);
			return "page/front/validatePhone";//修改成功跳转到登陆页面
	}
	
	/**
	 * 上传头像用的
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/upload/fdfs", method = RequestMethod.POST)
	public @ResponseBody
	String uplad(HttpServletRequest request, HttpServletResponse response) {
		PropertiesReader pu = PropertiesReader.getInstance();
		String path = pu.getProperties("fdfs.HttpAddress");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();
			String fileName = mf.getOriginalFilename();
			long fileLength = mf.getSize();
			String fileExtName = "";
			if (fileName.contains(".")) {
				fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
			}
			try {
				path += fdfsService.upload(fileName, fileExtName,
						mf.getInputStream(), fileLength);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;
	}
	
	/**
	 * 跳转到我的状态页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/personCentermyStatus", method = RequestMethod.GET)
	public String paymentlists(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession() ;
		User user = (User)session.getAttribute("user") ;
		if(user != null){
			String userId = user.getId();
			
			CardLog cardLog = cardLogService.getCardLogByUserId(userId) ;
			
//			List<Bookmark> bmList = bookmarkService.getBookmarkSet(userId) ;
			model.addAttribute("cardLog", cardLog) ;
			
			int proPercent = accountService.getProgressPercent(userId);
			model.addAttribute("sumTotal", proPercent);
			getMyService(user,model);
		}
	
		return "page/front/myStatus";
	}
	
	/**
	 * 收藏泪飙 ajax 分页
	 * @param request
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/pagination", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> myStatusBookmarkPagination(HttpServletRequest request) throws ParseException {
		HttpSession session = request.getSession() ;
		User user = (User)session.getAttribute("user") ;
		String userId = user.getId();
		// 页码
		Integer pageIndex = Integer.parseInt(request.getParameter("pageIndex")) ;
		// 每页显示的记录数
		Integer items_per_page = Integer.parseInt(request.getParameter("items_per_page")) ;
		// 收藏类型
		String cId = request.getParameter("classification_id") ;
		// 分页 起始记录
		pageIndex = pageIndex * items_per_page ;
		
		Map<String, Object> msg = new HashMap<String, Object>() ;
		BigInteger total = null ;	// 收藏数量
		List<Bookmark> result = new ArrayList<Bookmark>() ;	// 收藏列表
		
		if(cId != null && !"".equals(cId)) {	// 在我的文库页面选中了收藏类型时分页
			total = bookmarkService.getBookmarkTotalByCid(userId, cId) ;
			result = bookmarkService.getBookmarkListByCid(userId, pageIndex, items_per_page, cId) ;
		} else {
			total = bookmarkService.getBookmarkTotal(userId) ;
			result = bookmarkService.getBookmarkSet(userId, pageIndex, items_per_page) ;
		}

		msg.put("total", total) ;
		msg.put("result", result) ;
		
		return msg ;
	}
	
	/**
	 * 个人空间 取消收藏
	 * @param bId
	 * @return
	 */
	@RequestMapping(value="/deleteBookmark/{bId}", method=RequestMethod.GET) 
	@ResponseBody
	public Map<String, Object> deleteMystatusBookmark(@PathVariable("bId")String bId) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		try {
			bookmarkService.removeBookmark(bId) ;
			result.put("success", true) ;
		} catch(Exception e) {
			result.put("success", false) ;
			result.put("msg", "取消收藏失败！") ;
			e.printStackTrace() ;
		}
		return result ;
	}

	
	/**
	 * 验证密码是否正确
	 * @param password
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/validatePassword", method = RequestMethod.POST)
	@ResponseBody
	public  void validatePassword(String password, HttpServletRequest request,HttpServletResponse response,Model model){
		try {    
			if(password!=null){
	            	HttpSession session = request.getSession();
	    		    User user = (User)session.getAttribute("user");
	    		    Md5PasswordEncoder md5 = new Md5PasswordEncoder(); 
	    			String newpwd = md5.encodePassword(password, user.getUsername());
	    			if(user.getPassword().equals(newpwd)){
	    				response.getWriter().print(true);
	    			}else{
	    				response.getWriter().print(false);
	               }
	            }else{
						response.getWriter().print(false);
	            }  
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	}
	
	/**
	 * 检查新密码与原密码是否相同
	 * @param originalPassword
	 * @param newPassword
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping(value="/passwordRepeat", method = RequestMethod.POST)
	@ResponseBody
	public void passwordRepeat(String originalPassword, String newPassword, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			if(originalPassword != null && !"".equals(originalPassword) && newPassword != null && !"".equals(newPassword)) {
				if(!originalPassword.equals(newPassword)) {
					response.getWriter().print(true);
				} else {
					response.getWriter().print(false);
				}
			} else {
				response.getWriter().print(false);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * 跳转到我的收藏页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/personCenterMyBookmark", method=RequestMethod.GET)
	public String toMyBookmarkPage(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession() ;
		User user = (User)session.getAttribute("user") ;
		String userId = user.getId();
		// 用户自定义的收藏类型
		List<Classification> cList = classificationService.getClassificationListByUserId(userId) ;
		int proPercent = accountService.getProgressPercent(userId);
		model.addAttribute("sumTotal", proPercent);
		model.addAttribute("cList", cList) ;
		// 向页面传送收藏类型列表的长度值，为了在页面新增和删除收藏类型时来判断初始时是否有类型，避免刷新页面
		model.addAttribute("cListSize", cList.size());
		return "page/front/myBookmark";
	}

	

	/**
	 * 提供一个jqueryUI的样例dialog
	 * @param request
	 * @param model
	 * @return
	 */

	@RequestMapping(value = "/person/sampleJqueryUI" )
	public String test(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		model.addAttribute("user",user);
		return "page/front/sampleJqueryUI";
	}
	
	
	/**
	 * 从数据库读取省份列表 并且存入session中
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/getProvince", method = RequestMethod.POST)
	@ResponseBody
	public List<Province> getProvince(HttpServletRequest request,HttpServletResponse response){
		List<Province> list = pubService.getProvinceList();
		HttpSession session = request.getSession();
		session.setAttribute("provinceList", list);
		return list;
	}
	
	/**
	 * 从session中获取城市列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/user/getCity", method = RequestMethod.POST)
	@ResponseBody
	public List<City> getCity(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		List<City> citylist = new ArrayList<City>();
		List<Province> list = (List<Province>)session.getAttribute("provinceList");
		for(Province p:list){
			if(StringUtils.equals(p.getId(), id)){
				citylist =  p.getCitys();
			}
		}
		return citylist;
	}
	
	/**
	 * 跳转到我的账户页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/person/myAccount", method = RequestMethod.GET)
	public String toMyAccount(HttpServletRequest request, Model model) {
		//获取session对象
		HttpSession session = request.getSession();
		//获取session中的用户信息
		User user = (User)session.getAttribute("user");
		//通过用户id查询卡id
		getMyService(user,model);
		
		int proPercent = accountService.getProgressPercent(user.getId());
		model.addAttribute("sumTotal", proPercent);
		return "page/front/account";
	}
	
	/**
	 * 查找该用户所购买的服务和到期时间
	 * @param user 用户
	 * @param model 返回数据载体
	 */
	public void getMyService(User user,Model model){
		String cardId = cardLogService.getCardIdByUserId(user.getId());
		//通过卡id查询卡类型id
		String cardTypeId = bizService.getCardTypeIdByCardId(cardId);
		//通过卡类型id查询卡名称
		String cardTitle = bizService.getCardTitleByCardTypeId(cardTypeId);
		//通过用户id查询用户的消费台账信息
		CardLog cl = cardLogService.getCardLogByUserId(user.getId());
		//定义到期时间
		String endDate = "";
		String img = "images/personal/card-no.gif";
		if(cl != null){
			//获取够买卡时间
			Date dataDate = cl.getDataDate();
			//获取剩余时间
			int overTotal = cl.getOverTotal();
			//格式化日期的显示格式
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cd = Calendar.getInstance();
			cd.setTime(dataDate);
			//用够买时间加上剩余天数得出到期时间
			cd.add(Calendar.DATE, overTotal);
			//将Date类型转换为字符串,用来在前台页面显示
			endDate = sdf1.format(cd.getTime());
			img = "images/personal/card_03.gif";
		}else{
			endDate="尚未购买服务";
			cardTitle = "尚未购买服务";
		}
		String gradeimg = "";
		if(user.getGrade()==1){
			gradeimg = "images/personal/vip_09.png";
		}else if(user.getGrade()==2){
			gradeimg = "images/personal/vip_11.png";
		}else if(user.getGrade()==3){ 
			gradeimg = "images/personal/vip_13.png";
		}else{
			gradeimg = "images/personal/vip_07.png";
		}
		model.addAttribute("gradeimg",gradeimg);
		model.addAttribute("img",img);
		model.addAttribute("endDate", endDate);
		model.addAttribute("title", cardTitle);
	}
	
	/**
	 * 跳转到我的账户中我的等级页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/person/Level", method = RequestMethod.GET)
	public String toMyLevel(HttpServletRequest request, Model model) {
		return "page/front/level";
	}
	
	/**
	 * 跳转到我的账户中账户信息页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/person/accountInfo", method = RequestMethod.GET)
	public String toAccountInfo(HttpServletRequest request, Model model) {
		
		//获取session对象
		HttpSession session = request.getSession();
		//获取session中的用户信息
		User user = (User)session.getAttribute("user");
		//通过用户id查询卡id
		String cardId = cardLogService.getCardIdByUserId(user.getId());
		//通过卡id查询卡类型id
		String cardTypeId = bizService.getCardTypeIdByCardId(cardId);
		//通过卡类型id查询卡名称
		String cardTitle = bizService.getCardTitleByCardTypeId(cardTypeId);
		//通过用户id查询用户的消费台账信息
		CardLog cl = cardLogService.getCardLogByUserId(user.getId());
		//定义到期时间
		String endDate = "";
		String img = "images/personal/card-no.gif";
		if(cl != null){
			//获取够买卡时间
			Date dataDate = cl.getDataDate();
			//获取剩余时间
			int overTotal = cl.getOverTotal();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
			try {
				//得到够买时间的时间戳
				long l=sdf.parse(dataDate.toString()).getTime();
				//得到到期日期的时间戳
				long l1 = l+1000*60*60*24*overTotal;
				//将时间戳转换为Date
				Date date = new Date(l1);
				//将Date类型转换为字符串,用来在前台页面显示
				endDate = sdf1.format(date);
				img = "images/personal/card_03.gif";
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			endDate="尚未购买服务";
			cardTitle = "尚未购买服务";
		}
		model.addAttribute("img",img);
		model.addAttribute("endDate", endDate);
		model.addAttribute("title", cardTitle);
		
		return "page/front/account";
	}

	
	/**
	 * 收藏页面添加新的收藏类型
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/addNewClassification", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> addNewClassification(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		HttpSession session = request.getSession() ;
		User user = (User)session.getAttribute("user") ;
		String userId = user.getId();
		// 新增收藏类型的名称
		String newTitle = request.getParameter("newTitle") ;
		// 新增收藏类型对象
		Classification classification = new Classification() ;
		classification.setTitle(newTitle) ;
		classification.setUserId(userId) ;
		try {
			classificationService.addNewClassification(classification) ;
			result.put("success", true) ;
			result.put("cf", classification) ;
		} catch (Exception e) {
			result.put("fail", true) ;
			result.put("msg", "新增失败！") ;
			e.printStackTrace();
		}
		return result ;
	}
	
	/**
	 * 添加新收藏类型时检验是否存在同名
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/testNewTitle", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> textNewTitle(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = request.getSession() ;
		User user = (User)session.getAttribute("user") ;
		try {
			String userId = user.getId();
			// 新添加的收藏类型的名称
			String newTitle = request.getParameter("newT") ;
			Classification c = new Classification();
			if(!"".equals(userId) && !"".equals(newTitle)) {
				// 根据用户ID和收藏类型的名称检查
				c = classificationService.getOneByUserIdAndTitle(userId, newTitle);
			}
			if(c != null) {
				result.put("fail", true);
				result.put("msg", "已存在同名的类型！");
			} else {
				result.put("success", true);
				result.put("msg", "该类型可以使用！");
			}
		} catch(Exception e) {
			result.put("fail", true);
			result.put("msg", "检查失败！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 删除收藏类型
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/delOldClassification", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> delOldClassification(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		HttpSession session = request.getSession() ;
		User user = (User)session.getAttribute("user") ;
		String userId = user.getId();
		// 收藏类型ID
		String oldId = request.getParameter("oldId") ;
		// 根据用户Id和收藏类型ID获取收藏的数量
		BigInteger count = bookmarkService.getBookmarkCountByCidAndUserId(oldId, userId) ;
		String num = String.valueOf(count) ;
		if("0".equals(num)) {
			try {
				Classification c = classificationService.getClassificationById(oldId) ;
				if(c != null) {
					classificationService.deleteClassificationById(oldId) ;
					result.put("success", true) ;
				}
			} catch (Exception e) {
				result.put("success", false) ;
				result.put("msg", "删除失败！") ;
				e.printStackTrace();
			}
		} else {	// 分类中已有收藏，禁止删除
			result.put("success", false) ;
			result.put("msg", "无法删除，存在相关联的收藏！") ;
		}
 		return result ;
	}
	
	/**
	 *  对收藏进行分类
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/choseClassification", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> choseClassificatioin(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		// 收藏ID
		String bookmarkId = request.getParameter("bId") ;
		// 收藏类型ID
		String classificationId = request.getParameter("cId") ;
		if("0".equals(classificationId)) {	// 取消类型,将收藏类型字段置空（为了能删除所有的收藏类型）
			try {
				bookmarkService.ModifyClassificationId(bookmarkId);
				result.put("success", true) ;
			} catch (Exception e) {
				result.put("fail", true) ;
				result.put("msg", "移动失败！") ;
				e.printStackTrace();
			}
		} else {
			try {
				Bookmark b = bookmarkService.getOneById(bookmarkId) ;
				Classification c = classificationService.getClassificationById(classificationId) ;
				// 绑定收藏和分类的关系
				b.setClassification(c) ;
				bookmarkService.saveOne(b) ;
				result.put("success", true) ;
			} catch (Exception e) {
				result.put("fail", true) ;
				result.put("msg", "移动失败！") ;
				e.printStackTrace();
			}
		}
		
		return result ;
	}
	
	/**
	 * 批量删除收藏
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/deleteBatchBookmark", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteBatchBookmark(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		String idArray[] ;
		String idString = "" ;
		try {
			// 需要批量删除的收藏的ID，通过“;”分隔
			idString = request.getParameter("bookmarkIdArr") ;
			if(idString != null && !"".equals(idString)) {
				// 拆分id
				idArray = idString.split(";") ;
				for(int i = 0 ; i < idArray.length ; i++) {
					if(idArray[i] != null && !"".equals(idArray[i].trim())) {
						bookmarkService.removeBookmark(idArray[i].trim()) ;
					}
				}
			}
			result.put("success", true) ;
		} catch (Exception e) {
			result.put("success", false) ;
			result.put("msg", "批量删除失败！") ;
 			e.printStackTrace();
		}
		return result ;
	}

	/**
	 *  批量分类收藏
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/chooseBatchBookmark", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> chooseBatchBookmark(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		// 收藏类型
		String classificationId = request.getParameter("cId") ;
		Classification c = classificationService.getClassificationById(classificationId) ;
		// 收藏
		String idString = request.getParameter("bookmarkIdArr") ; 
		String idArray[] ;
		Bookmark b = new Bookmark() ;
		try {
			if(idString != null && !"".equals(idString)) {
				idArray = idString.split(";") ;
				if(idArray!= null && !"".equals(idArray)) {
					// 循环处理每一个收藏
					for(int i = 0 ; i < idArray.length ; i++) {
						if(idArray[i] != null && !"".equals(idArray[i].trim())) {
							b = bookmarkService.getOneById(idArray[i].trim()) ;
							b.setClassification(c) ;	// 关联收藏和分类
							bookmarkService.saveOne(b) ;
						}
					}
					result.put("success", true) ;
				}
			}
		} catch (Exception e) {
			result.put("success", false) ;
			result.put("msg", "批量移动失败！") ;
			e.printStackTrace();
		}
		return result ;
	}
	
	/**
	 * 详细页面收藏
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/collectBookmark", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> collectBookmark(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession() ;
		User user = (User)session.getAttribute("user") ;
		String userId = user.getId() ;
		Map<String, Object> result = new HashMap<String, Object>() ;
		// 知识url
		String bUrl = request.getParameter("bUrl") ;
		// 知识标题
		String bTitle = request.getParameter("bTitle") ;
		// 知识id
		String kId = request.getParameter("kId");
		if(!"".equals(bUrl) && !"".equals(bTitle)) {
			if(bookmarkService.getOneByUrl(bUrl, userId, bTitle) == null) {		// 未收藏
				// 新建收藏对象
				Bookmark bm = new Bookmark() ; 
				try {
					bm.setTitle(bTitle) ;
					bm.setUrl(bUrl) ;
					bm.setCreateDate(new Date()) ;
					bm.setUserId(userId) ;
					bm.setDataType(1) ;
					bm.setKnowledgeId(kId);
					// 保存收藏对象
					bookmarkService.saveOne(bm) ;
					result.put("success", true) ;
				} catch (Exception e) {
					result.put("fail", true) ;
					result.put("msg", "添加收藏失败！") ;
					e.printStackTrace();
				}
			} else {	// 已收藏
				result.put("fail", true);
			}
		}
		
		return result ;
	}
	
	@RequestMapping(value="/bookmarkCheck", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> bookmarkCheck(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user") ;
		String userId = user.getId();
		String bookmarkUrl = request.getParameter("bUrl") ;
		Bookmark bm = null ;
		try {
			if(!"".equals(userId) && !"".equals(bookmarkUrl)) {
				bm = bookmarkService.getOneByUserIdUrl(userId, bookmarkUrl) ;
			}
			if(bm == null) {
				result.put("flag", true) ;
			} else {
				result.put("flag", false) ;
			}
		} catch (Exception e) {
			result.put("success", false) ;
			e.printStackTrace();
		}
		return result ;
	}
	
	/**
	 * 修改密码时校验当前密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/user/CheckOriginalPwd", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkOriginalPassword(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		try {
			User user = (User)session.getAttribute("user");
			Md5PasswordEncoder md5 = new Md5PasswordEncoder();
			String originalPwd = request.getParameter("originalPwd");
			// 对新密码进行加密
			String md5Pwd = md5.encodePassword(originalPwd, user.getUsername());
			if(md5Pwd.equals(user.getPassword())) {
				result.put("success", true);
			} else {
				result.put("success", false);
			}
		} catch (Exception e) {
			result.put("success", "error");
			result.put("msg", "校验出错！");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 新增用户时校验用户名的唯一性
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/validateUsername", method=RequestMethod.POST)
	@ResponseBody
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("inputUsername");
		User user = null;
		try {
			user = accountService.getUserByUsername(username);
			if(user != null) {
				response.getWriter().print(false);
			} else {
				response.getWriter().print(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
