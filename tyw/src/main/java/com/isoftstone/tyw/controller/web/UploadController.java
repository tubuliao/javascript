package com.isoftstone.tyw.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.entity.pub.Upload;
import com.isoftstone.tyw.service.TagService;
import com.isoftstone.tyw.service.UploadService;


@Controller
public class UploadController {
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private TagService tagService;
	
	@RequestMapping(value = "/personCenterdata", method = RequestMethod.GET)
	public String toData(HttpServletRequest request,Model model) {
		return "page/front/data";
	}
	
	@RequestMapping(value = "/personCentersubmit")
	public String toSubmit(HttpServletRequest request,Model model) {
		try {
			HttpSession session = request.getSession();
			User user =(User)session.getAttribute("user");
			List<Upload> list = uploadService.getByUploadName(user.getUsername());
			model.addAttribute("list", list);
		 }catch (Exception e) {   
	         e.printStackTrace();  
	    } 
		
		return "page/front/submit";
	}
	
	@RequestMapping(value = "/personCentersubmitType", method = RequestMethod.GET)
	public String toSubmitType(HttpServletRequest request,Model model) {
		Long code = 40000000000000000L;
		Tag tag = tagService.getTagByCode(code);
		List<Tag> list = tagService.getTagByParentId1(tag.getId());
		List list1 = new ArrayList();
		for(int i = 0; i < list.size(); i++){
			Tag tag1 = list.get(i);
			list1.addAll(tagService.getTagByParentId1(tag1.getId()));
			
		}
		model.addAttribute("list1", list1);
		model.addAttribute("list", list);
		return "page/front/submit-type";
	}
	
	@RequestMapping(value = "/personCentersubmitInput/{id}", method = RequestMethod.GET)
	public String toSubmitInput(@PathVariable("id") String id,HttpServletRequest request,Model model) {
		
		Tag tag = tagService.getTagById(id);
		String name = tag.getName();
		Tag tag1 = tagService.getTagById(tag.getParentId());
		String parentName = tag1.getName();
		model.addAttribute("name",name);
		model.addAttribute("parentName",parentName);
		return "page/front/submit-input";
	}
	
	/**
	 * 前台保存投稿信息
	 * 
	 */
	@RequestMapping(value = "/personCenteraddUpload", method = RequestMethod.POST)
	public String addUpload(@ModelAttribute("upload") Upload upload,
			HttpServletRequest request,HttpServletResponse response,Model model){
		try {
			String name = request.getParameter("name");
			HttpSession session = request.getSession();
			User user =(User)session.getAttribute("user");
			upload.setUrl(upload.getUrl());
			upload.setUploadName(user.getUsername());
        	upload.setCreateDate(new Date());
        	upload.setTagType(name);
        	upload.setStatus(0);
			uploadService.saveUpload(upload);
		 }catch (Exception e) {   
	         e.printStackTrace();  
	    } 
		
		HttpSession session = request.getSession();
		session.setAttribute("upload", upload);
		
		return "forward:/personCentersubmit";
	}
	
	
	/**
	 * 根据ID删除投稿信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteUpload/{uploadId}")  
	@ResponseBody
	public  Map<String,Object> deleteUpload(@PathVariable("uploadId") String uploadId, Model model)
			throws Exception {
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			uploadService.deleteUpload(uploadId);
			result.put("success",true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg", "删除失败！");
			throw e;
		}
		return result;
	}
	
}
