package com.isoftstone.tyw.controller.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook; 
import org.apache.poi.hssf.usermodel.HSSFSheet; 
import org.apache.poi.hssf.usermodel.HSSFRow; 
import org.apache.poi.hssf.usermodel.HSSFCell; 
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.isoftstone.tyw.entity.auths.Firm;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Files;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.FdfsService;
import com.isoftstone.tyw.service.FirmService;
import com.isoftstone.tyw.service.TagService;
import com.isoftstone.tyw.util.IOUtil;
import com.isoftstone.tyw.util.PropertiesReader;

@Controller
public class FirmController {
	@Autowired
	private FirmService firmService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private IOUtil ioUtil;
	@Autowired
	private TagService tagService ;
	@Autowired
	private FdfsService fdfsService;

	/**
	 *  返回所有企业列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/firmlist", method = RequestMethod.GET)
	public String firmList(HttpServletRequest request,Model model) {
		HttpSession session = request.getSession();
		User user =(User)session.getAttribute("user");
		List<Firm> list = firmService.getFirmByAgentId(user.getId());
		model.addAttribute("list", list);
		return "page/channel/id";
	}
	
	/**
	 * 前台保存企业信息
	 * 
	 */
	@RequestMapping(value = "/saveFirmInfo", method = RequestMethod.POST)
	public String saveFirmInfo(@ModelAttribute("User") User user,
			HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			String username = request.getParameter("username");
			user.setUserType(2);
			user.setPassword("123456");
			user.setNonLocked(true);
			user.setEnable(true);
			user.setUsername(username);
			user.getFirm().setUser(user);
			accountService.saveUser(user);
		 }catch (Exception e) {   
	         e.printStackTrace();  
	    } 
		return "redirect:/firmlist";
	}
	
	/**
	 * 根据ID删除企业信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/firm/delete/{id}")  
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
	 * 批量导入企业信息
	 * 
	 */
	@RequestMapping(value="/firm/excelFirmUpload",method=RequestMethod.POST)
	@ResponseBody
   	public String doUploadExcel(HttpServletRequest request,HttpServletResponse response){
   		String path = request.getSession().getServletContext().getRealPath("upload");
   		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
   		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();   
   		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {    
   			MultipartFile mf = entity.getValue();    
   			String fileName=mf.getOriginalFilename();
   			File file= new File(path,fileName);
               try {
   				ioUtil.streamToFile(mf.getInputStream(), file, true);
   				List<User> users = accountService.readXls(file.getAbsolutePath()) ;
   				//System.out.println("users.size:" + users.size());

   				// 建立表格与标间 的多对多关系
   				List<User> userList = new ArrayList<User>() ;
   				if(users.size() != 0 && users != null) {
   					Iterator<User> ite = users.iterator();
   					while(ite.hasNext()) {
   						
   						HttpSession session = request.getSession();
   						User sessionUser =(User)session.getAttribute("user");
   						
   						User u = (User)ite.next() ;
   						Firm firm = new Firm();
   						firm.setName(u.getName());
   						firm.setAddr(u.getAddr());
   						firm.setLinkman(u.getLinkman());
   						firm.setPhone(u.getFphone());
   						firm.setFax(u.getFax());
   						firm.setZip(u.getZip());
   						firm.setEmail(u.getEmail());
   						firm.setAgentId(sessionUser.getId());
   						User user = new User();
   						user.setUsername(u.getUsername());
   						user.setPassword(u.getPassword());
   						user.setUserType(2);
   						user.setNonLocked(true);
   						user.setEnable(true);
   						user.setFirm(firm);
   						user.getFirm().setUser(user);
   						userList.add(user) ;
   					}
   					//保存用户信息
   					accountService.saveUser(userList) ;
   				}
   				return "success";
   			} catch (Exception e) {
   				e.printStackTrace();
   			} 
   		}
   		return "false";
   	}
	
	
}
