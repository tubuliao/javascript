package com.isoftstone.tyw.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.info.LikeCount;
import com.isoftstone.tyw.service.LikeCountService;
@Controller
public class LikeCountController {
	@Autowired
	private LikeCountService likeCountService;
	/**
	 * 新增喜欢
	 * 
	 */
	@RequestMapping(value = "/addLikeCount", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addLikeCount(HttpServletRequest request,HttpServletResponse response,Model model){
		
		Map<String,Object> result=new HashMap<String,Object>();
		//获取用户id
		String userId = request.getParameter("userId");
		//获取文件id
		String fileId = request.getParameter("fileId");
		
		LikeCount lc = new LikeCount();
		//根据用户id和文章id查询当前用户是否喜欢过当前文章
		List<LikeCount> list = likeCountService.findExsitOneInfo(fileId, userId);
		
		try {
			//如果喜欢过
			if(list.size()>0){
				result.put("fail", true);
			}else{
				//保存文章id
				lc.setResourcesId(fileId);
				//保存用户id
				lc.setUid(userId);
				//新增一条喜欢记录
				likeCountService.saveLc(lc);
				result.put("success", true);
			}
		 }catch (Exception e) {   
	         e.printStackTrace();  
	    } 
		return result;
	}
}
