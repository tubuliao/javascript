package com.isoftstone.tyw.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.biz.CardLog;

/**
 * 登录验证成功处理器
 * @author zhangyq
 */
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
    
    private static Logger log = LoggerFactory.getLogger(LoginAuthenticationSuccessHandler.class);
    
    //登录验证成功后需要跳转的url
    private String url ;
    
    @Autowired
	private AccountService accountService;
    @Autowired
	private BizService bizService ;
    
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response, Authentication authentication) throws IOException,
            ServletException {
        
		// 取得登陆前的url
		String refererUrl = request.getParameter("currUrl");  
		if(refererUrl != null&&refererUrl.trim().length()!=0&&!"null".equals(refererUrl)&&!"".equals(refererUrl)){
			url = refererUrl;
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
				UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				User user = accountService.loadUserByUsername(userDetails.getUsername());
				//model.addAttribute("user",user);
				HttpSession session = request.getSession();
			    session.setAttribute("user", user);
			    this.ValidationDataAccess(user, session);
			    String isFirst = user.getFirstLogin();
			    String login = "1";//默认不是第一次登陆
			    if("0".equals(isFirst)){
			    	login = "0";
			    	//user.setFirstLogin("1");//设置为已经登陆过
			    	//accountService.updateUserNoPassword(user);//更新用户登陆状态为已经登陆过
			    }
			    //session.removeAttribute("firstLogin");
			    
			    request.setAttribute("firstLogin", login);//是否第一次登陆
			    session.setAttribute("firstLogin", login);
			}
			//request.getRequestDispatcher(url).forward(request, response);
			int urlLength = url.length();
			String urlSub = url.substring(urlLength-9, urlLength);//截取上次URL的后九位
			if("login.jsp".equals(urlSub)){//如果上个url是指向登陆页面的就让它登陆成功之后指向首页
				response.sendRedirect("/tywadmin");
			}else{
				response.sendRedirect(url);
			}
			
		}else{
			response.sendRedirect("/tywadmin");
	        //request.getRequestDispatcher(url).forward(request, response);
		}
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
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

}
