package com.isoftstone.tyw.controller.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.pub.Announce;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.PubService;
import com.isoftstone.tyw.util.Pager;

@Controller
public class AnnounceController {
	@Autowired
	private PubService pubService;

	@Autowired
	private CommonService commonService;
	
	/**
	 * demo页面
	 * @return
	 */

    @RequestMapping(value="/demo",method=RequestMethod.GET)   
    public String openDemo(Model model) throws Exception{   
        return "page/announce/demo";   
    } 
	/**
	 * demo页面
	 * @return
	 */

    @RequestMapping(value="/demo",method=RequestMethod.POST)   
    public String openDemo1(Model model) throws Exception{   
        return "page/announce/demo";   
    } 
    
	/**
	 * upload_json页面
	 * @return
	 */

    @RequestMapping(value="/upload_json",method=RequestMethod.POST)   
    public String openUploadJson(Model model) throws Exception{   
        return "page/announce/upload_json";   
    } 
    
	/**
	 * demo页面
	 * @return
	 */

    @RequestMapping(value="/file_manager_json",method=RequestMethod.POST)   
    public String openFileManagerJson(Model model) throws Exception{   
        return "page/announce/file_manager_json";   
    } 
	/**
	 *  返回所有公告信息列表
	 * @param pageable
	 * @param model
	 * @return
	 */
//	@RequestMapping(value = "/announcelist", method = RequestMethod.GET)
//	public String announceList(@PageableDefaults(value=20) Pageable pageable, Model model) {
//		
//		Page<Announce> page = pubService.listAllAnnounce(pageable);
//		model.addAttribute("page",page);
//		model.addAttribute("announce", page.getContent());
//		model.addAttribute("pagesize", page.getSize());
//		return "page/announce/announceList";
//	}
//	@RequestMapping(value = "/announcelist", method = RequestMethod.POST)
//	public String announceListPage(@PageableDefaults(value=20) Pageable pageable, Model model) {
//		
//		Page<Announce> page = pubService.listAllAnnounce(pageable);
//		model.addAttribute("page",page);
//		model.addAttribute("announce", page.getContent());
//		model.addAttribute("pagesize", page.getSize());
//		return "page/announce/announceList";
//	}
	@RequestMapping(value = "/announcelist", method = RequestMethod.GET)
	public String paymentlist() {
		return "page/announce/announceList";
	}
	
	@RequestMapping(value = "/announcelist/data", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> listPaymentJson(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		String title=request.getParameter("title");
		String issuedBy=request.getParameter("issuedBy");
		Pager page = pubService.listAllAnnounce(title,issuedBy,pageable);
		result.put("total",page.getRowCount());  
		result.put("rows", page.getResult());
		return result;
	}
	/**
	 * 添加新公告
	 * @return
	 */

    @RequestMapping(value="/addAnnounce",method=RequestMethod.GET)   
    public String toAdd(Model model) throws Exception{  
//    	UserDetails userDetails = (UserDetails) SecurityContextHolder
//				.getContext().getAuthentication().getPrincipal();
//    	
    	 model.addAttribute("issuedBy", "admin");  
        return "page/announce/addAnnounce";   
    }   
    @RequestMapping(value="/addAnnounce",method=RequestMethod.POST)   
    @ResponseBody
    public void doSave(Announce announce,Model model,HttpServletResponse response,HttpServletRequest request) throws Exception{ 
		String resultJson = "{'success':true}";
        try {
        	//String content = request.getParameter("content");
        	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        	Date createDateTime =  df.parse(df.format(new Date())); //创建时间
        	announce.setCreateDate(createDateTime);
        	//announce.setContent(content);
        	pubService.saveAnnounce(announce); 
        	resultJson = "{'success':true}";
        } catch (Exception e) {
        	resultJson = "{'success':true}";
        	resultJson="{'msg':'保存失败！'}";
            e.printStackTrace();   
            throw e;   
        }   
        commonService.responseJsonBody(response, resultJson);
    }
	/**
	 * 根据ID删除某条公告
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/announce/delete/{id}")   
	@ResponseBody
	public Map<String,Object> doDelete(@PathVariable("id")String id,Model model){
		Map<String,Object> result=new HashMap<String,Object>();
		try{
			pubService.deleteAnnounceById(id);
			result.put("success",true);
		}catch(Exception e){
			e.printStackTrace();
			result.put("msg", "删除失败！");
		}
		return result;
	}
	/**
	 * 修改公告信息
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	 @RequestMapping(value="/announce/update/{id}",method=RequestMethod.GET)   
	    public String toUpdate(@PathVariable("id") String id, Model model) throws Exception{   
	        model.addAttribute("announce",pubService.getAnnounceById(id));   
	        return "page/announce/updateAnnounce";   
	    } 
	 @RequestMapping(value="/announce/update/{id}",method=RequestMethod.POST)  
	 @ResponseBody
	    public void doUpdate(@PathVariable("id") String id,  Announce announce,Model model,HttpServletResponse response) throws Exception{   
	    	String resultJson = "{'success':true}";
	    	try {   
	    		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	        	Date createDateTime =  df.parse(df.format(new Date())); //修改时间
	        	announce.setCreateDate(createDateTime);
	    		pubService.saveAnnounce(announce);  
	    		resultJson = "{'success':true}";
	        } catch (Exception e) {   
	        	resultJson = "{'msg':'更新失败！'}";
	            e.printStackTrace();   
	            throw e;   
	        }   
	        commonService.responseJsonBody(response, resultJson);
	    } 
		/**
		 * 查看公告信息
		 * @param id
		 * @param model
		 * @return
		 * @throws Exception
		 */
		 @RequestMapping(value="/announce/view/{id}",method=RequestMethod.GET)   
		    public String toView(@PathVariable("id") String id, Model model) throws Exception{   
		        model.addAttribute("announce",pubService.getAnnounceById(id));   
		        return "page/announce/viewAnnounce";   
		    } 
		 
		@RequestMapping(value = "/announcetotallist")
		public String announcetotallist(Model model,HttpServletRequest request) {
			
			String annId = request.getParameter("annId");
			Announce announce = pubService.getAnnounceById(annId);
			if(null != announce){
				String annTitle = announce.getTitle();
				String annContent = announce.getContent();
				String annCreateDate = announce.getFormatCreateDate();
				model.addAttribute("annTitle", annTitle);
				model.addAttribute("annContent", annContent);
				model.addAttribute("annCreateDate", annCreateDate);
			}
			
			return "page/announce/notice";
		}
		
//		/**
//		 * 根据ID查询某条公告
//		 * @param id
//		 * @param model
//		 * @return
//		 * @throws Exception
//		 */
//		@RequestMapping(value="/singleAnnounce")   
//		@ResponseBody
//		public Map<String,Object> singleAnnounce(Model model, HttpServletRequest request){
//			String anId = request.getParameter("anId");
//			Map<String,Object> result=new HashMap<String,Object>();
//			try{
//				String content = pubService.getContentById(anId);
//				if(null != content && !content.equals("")){
//					result.put("success",true);
//					result.put("announceContent", content);
//				}else{
//					result.put("fail",true);
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			return result;
//		}
}
