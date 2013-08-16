package com.isoftstone.tyw.service;

import java.io.IOException;

import javax.security.sasl.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.AuthenticationEntryPoint;

public class LoginUrlEntryPoint implements AuthenticationEntryPoint{

	 private static final Log log = LogFactory.getLog(LoginUrlEntryPoint.class);

	 @Override
	 public void commence(HttpServletRequest request,HttpServletResponse response, 
			 org.springframework.security.core.AuthenticationException authException)
	 throws IOException, ServletException {
	  String targetUrl = null;
	  String url = request.getRequestURI();

	  // 取得登陆前的url
	  String refererUrl = request.getHeader("Referer");  
	  System.out.println("url:"+url);
	  System.out.println("refererUrl:"+refererUrl);

	  // TODO 增加处理逻辑
	  targetUrl = refererUrl;
	         response.sendRedirect(targetUrl);
	 }
}