package com.isoftstone.tyw.controller.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.auths.Visit;
import com.isoftstone.tyw.service.VisitService;

@Controller
public class VisitController {
	@Autowired
	private VisitService visitService;
	
	
	/**
	 * 用户最近访问分页
	 * 
	 */
	@RequestMapping(value="/visitPagination", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> visitPagination(HttpServletRequest request) {
		HttpSession session = request.getSession() ;
		User user = (User)session.getAttribute("user") ;
		String userId = user.getId();
		Integer pageIndex = Integer.parseInt(request.getParameter("pageIndex")) ;
		Integer items_per_page = Integer.parseInt(request.getParameter("items_per_page")) ;
		//定义从当前时间起向前推15天的日期,最近访问只查询最近15天的访问记录
		Date d = new Date(System.currentTimeMillis()-1000*60*60*24*15);
		//分页起始记录
		pageIndex = pageIndex * items_per_page ;
		
		Map<String, Object> msg = new HashMap<String, Object>() ;
		//查询结果总数
		BigInteger total ; ;	
		//查询返回的数据列表
		List<Visit> result = new ArrayList<Visit>() ;	
		
		total = visitService.getVisitTotal(userId, d) ;
		result = visitService.getVisitSet(userId, d, pageIndex, items_per_page) ;
		
		msg.put("total", total) ;
		msg.put("result", result) ;
		return msg ;
	}
	
	
	/**
	 * 保存最近访问信息
	 * 
	 */
	@RequestMapping(value = "/addVisit", method = RequestMethod.POST)
	@ResponseBody
	public void addUpload(HttpServletRequest request,HttpServletResponse response,Model model){
		//获取url
		String url = request.getParameter("url");
		//获取标题
		String title = request.getParameter("title");
		//获取浏览内容（知识、表格、图片等）id
		String visitId = request.getParameter("visitId");
		//数据库中存在的url,用户可能重复点击同一个链接
		//String oldUrl = "";
		
		//获取session
		HttpSession session = request.getSession();
		//从session中获取用户信息
		User user =(User)session.getAttribute("user");
		try {
			if(user != null && !user.equals("")){
				//根据用户id查找最近访问信息
				String exsitVisitId = visitService.getOneUrl(user.getId(), visitId);
				
					//判断如果当前点击的链接以前点击过,那么只执行更新时间的操作,否则执行添加操作
					if (visitId.equals(exsitVisitId)){
						//更新时间
						visitService.modifyVisit(new Date(), user.getId(), visitId);
					}else{
						Visit visit = new Visit();
						visit.setTitle(title);
						visit.setUserId(user.getId());
						visit.setVisitDate(new Date());
						visit.setUrl(url);
						visit.setVisitId(visitId);
						//保存新的访问记录
						visitService.saveVisit(visit);
					}
			}
		 }catch (Exception e) {   
	         e.printStackTrace();  
	    } 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args){  
		 
        //boolean w = false;  
 
        String path = "d:\\";  
 
        try {  
 
            if (!"".equals(path)) {  
 
                // 检查目录是否存在  
 
                File fileDir = new File(path);  
 
                if (fileDir.exists()) {  
 
                    // 生成临时文件名称  
 
                    String fileName = "a.doc";  
 
                    String content = "<html><div style=\"text-align: center\"><span style=\"font-size: 28px\"><span style=\"font-family: 黑体\">" +  
 
                        "制度发布通知<br /> <br /> </span></span></div></html>";  
 
                    byte b[] = content.getBytes("GBK");  
 
                    ByteArrayInputStream bais = new ByteArrayInputStream(b);  
 
                    POIFSFileSystem poifs = new POIFSFileSystem();  
 
                    DirectoryEntry directory = poifs.getRoot();  
 
                    DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);  
 
                    FileOutputStream ostream = new FileOutputStream(path+ fileName);  
 
                    poifs.writeFilesystem(ostream);  
 
                    bais.close();  
 
                    ostream.close();  
 
                }  
 
            }  
 
        } catch (IOException e) {  
 
            e.printStackTrace();  
 
      }  
 
      //return w;  
 
    }  
	
	
	
	
}
