package com.isoftstone.tyw.controller.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.auths.Download;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.service.DownloadService;

@Controller
public class DownloadController {
	@Autowired
	private DownloadService downloadService;
	
	/**
	 * 保存下载记录信息
	 * 
	 */
	@RequestMapping(value = "/addDownload", method = RequestMethod.POST)
	@ResponseBody
	public void addUpload(HttpServletRequest request,HttpServletResponse response,Model model){
		//获取url
		String url = request.getParameter("url");
		//获取标题
		String title = request.getParameter("title");
		
		//获取session
		HttpSession session = request.getSession();
		//从session中获取用户信息
		User user =(User)session.getAttribute("user");
		try {
			if(user != null && !user.equals("")){
				
				Download download = new Download();
				download.setTitle(title);
				download.setUserId(user.getId());
				download.setDownloadDate(new Date());
				download.setUrl(url);
				//保存下载记录
				downloadService.saveDownload(download);
			}
		 }catch (Exception e) {   
	         e.printStackTrace();  
	    } 
	}
}
