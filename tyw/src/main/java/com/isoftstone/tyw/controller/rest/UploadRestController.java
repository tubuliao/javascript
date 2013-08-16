package com.isoftstone.tyw.controller.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.isoftstone.tyw.service.FdfsService;

@Controller
public class UploadRestController {
	
	@Autowired
	private FdfsService fdfsService;
	
	@RequestMapping(value = "/api/upload", method = RequestMethod.POST)
	public void upload(@RequestParam(value = "file", required = false) MultipartFile file, 
			 HttpServletRequest request, ModelMap model, HttpServletResponse response) {

		long fileLength = file.getSize();

		String fileName = file.getOriginalFilename();
		String fileExtName = "";
		if (fileName.contains(".")) {
			fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		
		InputStream inputStream = null;
		String result = "{url:";
		try {
			inputStream = file.getInputStream();
			result += fdfsService.upload(fileName, fileExtName, inputStream, fileLength);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result +="}";
		
		PrintWriter writer=null;
		try {
			response.setHeader("Cache-Control", "no-store");  
	        response.setHeader("Pragma", "no-cache");  
	        response.setDateHeader("Expires", 0);  
	        response.setContentType("text/html");  
	        
			writer = response.getWriter();
			writer.print(result);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				inputStream.close();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
		}
	}
	
}
