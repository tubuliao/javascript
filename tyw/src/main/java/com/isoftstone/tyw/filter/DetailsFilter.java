package com.isoftstone.tyw.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailsFilter implements Filter {

		 public void init(FilterConfig filterConfig) throws ServletException {


		  }

		 public void doFilter(ServletRequest req, ServletResponse res,
		   FilterChain chain) throws IOException, ServletException {
		 
		       HttpServletRequest  request=(HttpServletRequest)req;
		       HttpServletResponse response = (HttpServletResponse) res;
		  
		  /*response.setDateHeader("expires",-1);
		  response.setHeader("Cache-Control","no-cache");
		  response.setHeader("Pragma","no-cache");*/
		  System.out.println("sssssssssssssssssssssss");
		  chain.doFilter(request, response); 

		    }

		   public void destroy() {}

}