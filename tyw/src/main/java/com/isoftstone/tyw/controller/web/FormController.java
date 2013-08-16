package com.isoftstone.tyw.controller.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.Form;
import com.isoftstone.tyw.entity.info.FormModule;
import com.isoftstone.tyw.entity.info.FormView;
import com.isoftstone.tyw.entity.info.Source;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.BaseService;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.FdfsService;
import com.isoftstone.tyw.service.FormService;
import com.isoftstone.tyw.service.InfoService;
import com.isoftstone.tyw.service.SourceService;
import com.isoftstone.tyw.service.TagService;
import com.isoftstone.tyw.util.IOUtil;
import com.isoftstone.tyw.util.PropertiesReader;

@Controller
public class FormController {
	@Autowired
	private FormService formService;
	@Autowired
	private InfoService infoService;
	@Autowired
	private IOUtil ioUtil;
	@Autowired
	private AccountService accountService;
	@Autowired
	private FdfsService fdfsService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private TagService tagService;
	@Autowired
	private BaseService baseService;
	@Autowired
	private SourceService sourceService;
	

	/**
	 * 添加页面跳转
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String form(Model model) {
		model.addAttribute(new Form());
		model.addAttribute("kLevel", baseService.getKnowledgeLevelSort("4"));
		return "page/form/form";
	}

	// @RequestMapping(value = "/form/add", method = RequestMethod.GET)
	// public String addForm(){
	// return "page/form/addForm";
	// }

	/**
	 * 添加新表格
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/form/save", method = RequestMethod.POST)
	@ResponseBody
	public void doSave(Form form, HttpServletRequest request,
			HttpServletResponse response) {
		String resultJson = "{'success':true}";

		String id = request.getParameter("id");
		
		// 空表/样表 Web图片
		String originalEmpHiPicUrl = "";
		String newEmpHiPicUrl = "";
		// 空表/样表 Mobile图片
		String originalEmpLowPicUrl = "";
		String newEmpLowPicUrl = "";
		// 发布时间
    	String begincreateDate = "";
    	SimpleDateFormat sdf02 = new SimpleDateFormat("yyyy-MM-dd");
    	//　填写的来源
    	String source = "";
    	// 根据source获取的Source对象
    	Source s = null;
		if (id != null) { // 修改
			try {
				Form f = formService.getFormById(id);
				// WEB
				originalEmpHiPicUrl = f.getEmpHiPicUrl();
				if (originalEmpHiPicUrl != null && !"".equals(originalEmpHiPicUrl)) {	// 原来有图片
					newEmpHiPicUrl = originalEmpHiPicUrl + form.getEmpHiPicUrl();
				} else {	// 原来没有图片
					newEmpHiPicUrl = form.getEmpHiPicUrl() ;
				}
				
				// Mobile
	 			originalEmpLowPicUrl = f.getEmpLowPicUrl();
				if (originalEmpLowPicUrl != null && !"".equals(originalEmpLowPicUrl)) {
					newEmpLowPicUrl = originalEmpLowPicUrl + form.getEmpLowPicUrl();
				} else {
					newEmpLowPicUrl = form.getEmpLowPicUrl() ;
				}
				
				// 审批保存时，需要校验：源文件、WEB图片、mobile图片
				String saveType = request.getParameter("saveType");
				if("approve".equals(saveType)) {
					if("".equals(newEmpHiPicUrl) || "".equals(newEmpLowPicUrl) || "".equals(form.getEmpUrl())) {
						resultJson = "{'success':false}";
						commonService.responseJsonBody(response, resultJson);
						return;
					}
				}
				
				// 保存图片url
				form.setEmpHiPicUrl(newEmpHiPicUrl);
				form.setEmpLowPicUrl(newEmpLowPicUrl);
			
				// 记录修改者的信息
				if (SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal() instanceof UserDetails) {
					UserDetails userDetails = (UserDetails) SecurityContextHolder
							.getContext().getAuthentication().getPrincipal();
					User user = accountService.loadUserByUsername(userDetails
							.getUsername());
					form.setModifyId(user.getId());
					form.setModifyName(user.getAliasname());
					form.setModifyDate(new Date());
				}
				
				// 修改后，创建和审批时间不变
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					// 创建时间
//				if(request.getParameter("createDate02") != null && !"".equals(request.getParameter("createDate02"))) {
//					form.setCreateDate(sdf.parse(request.getParameter("createDate02"))) ;
//				}
					// 审批时间
				if(request.getParameter("checkDate02") != null && !"".equals(request.getParameter("checkDate02"))) {
					form.setCheckDate(sdf.parse(request.getParameter("checkDate02"))) ;
				}
				// 发布日期
        		begincreateDate = request.getParameter("begincreateDate02");
        		if(!"".equals(begincreateDate)) {	// 选择了发布日期
        			form.setBegincreateDate(sdf02.parse(begincreateDate));
        		} else { // 没有选择发布日期，根据来源查询对应的实施日期
        			source = form.getSource();
        			if(!"".equals(source)) {
        				s = sourceService.getSourceByStandardName(source);
        				if(s != null && s.getExecuteDate() != null) { // 来源存在且有实施日期
        					form.setBegincreateDate(s.getExecuteDate());
        				}
        			}
        		}
        		// 若发布时间为空，默认为"1970-01-01"
        		if(form.getBegincreateDate() == null) {
        			form.setBegincreateDate(sdf02.parse("1970-01-01"));
        		}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else { // 新增
			try {
				// 记录新增者的信息
				if (SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal() instanceof UserDetails) {
					UserDetails userDetails = (UserDetails) SecurityContextHolder
							.getContext().getAuthentication().getPrincipal();
					User user = accountService.loadUserByUsername(userDetails
							.getUsername());
					form.setInsertId(user.getId());
					form.setInsertName(user.getAliasname());
					// 只在新建是创建createDate,不再修改
					form.setCreateDate(new Date()) ;	
				}
				// 发布日期
        		begincreateDate = request.getParameter("begincreateDate02");
        		if(!"".equals(begincreateDate)) {	// 选择了发布日期
        			form.setBegincreateDate(sdf02.parse(begincreateDate));
        		} else { // 没有选择发布日期，根据来源查询对应的实施日期
        			source = form.getSource();
        			if(!"".equals(source)) {
        				s = sourceService.getSourceByStandardName(source);
        				if(s != null && s.getExecuteDate() != null) { // 来源存在且有实施日期
        					form.setBegincreateDate(s.getExecuteDate());
        				}
        			}
        		}
        		// 若发布时间为空，默认为"1970-01-01"
        		if(form.getBegincreateDate() == null) {
        			form.setBegincreateDate(sdf02.parse("1970-01-01"));
        		}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			// 保存
			formService.saveForm(form);
		} catch (Exception e) {
			resultJson = "{'msg':'保存出错!'}";
			e.printStackTrace();
		}
		commonService.responseJsonBody(response, resultJson);
	}

	/**
	 * 审批/驳回
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/form/approve/{id}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approve(@PathVariable("id") String id,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 审批状态
		Integer status = Integer.parseInt(request.getParameter("status"));
		// 驳回信息
		String auditInfo = request.getParameter("auditInfo");
		// 权重类型（标题/来源）
		String weightType = request.getParameter("weightType");
		// 权重值（1~10）
		String weightVal = request.getParameter("weightVal");
		// 来源信息
		String baseSource = request.getParameter("baseSource");
		// 审批人信息
		User user = null;
		if (SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			user = accountService.loadUserByUsername(userDetails.getUsername());
		}
		String checkId = (user != null) ? user.getId() : "";
		String checkName = (user != null) ? user.getAliasname() : "";
		try {	// 根据有无驳回信息来判断是审批还是驳回
			if (auditInfo != null && !"".equals(auditInfo)) {
				infoService.modifyStatus(status, checkId, checkName,
						new Date(), auditInfo, id);
			} else {
				infoService.modifyStatus(status, checkId, checkName,
						new Date(), id);
			}
			// 二次审批通过 权重值设置
			if(weightType != null && !"".equals(weightType) && weightVal != null && !"".equals(weightVal)) {
				 if("title".equals(weightType)) {	// 标题，设置单条记录的权重
					 baseService.setTitleWeighing(id, Integer.valueOf(weightVal));
				 } else if("source".equals(weightType) && baseSource != null && !"".equals(baseSource))	{	// 来源，设置所有符合来源的知识的权重
					 baseService.setSourceWeighing(baseSource, Integer.valueOf(weightVal));
				 }
			}
			result.put("success", true);
		} catch (Exception e) {
			result.put("msg", "审批失败！");
			e.printStackTrace();
		}
		return result;
	}

	
	@RequestMapping(value = "/form/upload/fdfs", method = RequestMethod.POST)
	public @ResponseBody
	String uplad(HttpServletRequest request, HttpServletResponse response) {
		PropertiesReader pu = PropertiesReader.getInstance();
		String path = pu.getProperties("fdfs.HttpAddress");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();
			String fileName = mf.getOriginalFilename();
			long fileLength = mf.getSize();
			String fileExtName = "";
			if (fileName.contains(".")) {
				fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
			}
			InputStream is = null;
			try {
				is = mf.getInputStream();
				path += fdfsService.upload(fileName, fileExtName,
						is, fileLength);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return path;
	}

	/**
	 * 获取所有表格
	 * 
	 * @param pageable
	 * @param model
	 * @return
	 */
/*
	@RequestMapping(value = "/formlist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> formListPager(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String,Object> result= new HashMap<String,Object>();
		String tagId=request.getParameter("tagId");
		String title=request.getParameter("title");
		String state = request.getParameter("state");
		String source = request.getParameter("source");
		String insertName = request.getParameter("insertName"); 
		Page<Base> page=infoService.listBase(this.getWhereClause(tagId, title,source,insertName,state, 4), pageable);
		
		result.put("total",page.getTotalElements());
		result.put("rows",page.getContent());
		return result;
	}
	*/

	@RequestMapping(value = "/formlist", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> formListPager(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String,Object> result= new HashMap<String,Object>();
		String tagId=request.getParameter("tagId");	// 标签
		String title=request.getParameter("title");	// 标题
		String state = request.getParameter("state");	// 审批状态
		String source = request.getParameter("source");	// 来源
		String insertName = request.getParameter("insertName");	// 录入人 
		
		String empUrl = request.getParameter("searchEmpUrl") ;	// 源文件
		String empHiPicUrl = request.getParameter("searchEmpHiPicUlr") ;	// WEB图片
		String empLowPicUrl = request.getParameter("searchEmpLowPicUrl") ;	// Mobile图片
		String descUrl = request.getParameter("searchDescUrl") ;	// 填表说明doc
		String descContent = request.getParameter("searchDescContent") ;	// 填表说明文本
		
		String level = request.getParameter("level");	// 知识等级
		String begincreateDate = request.getParameter("begincreateDate");	// 发布日期
		Map<String, Object> page =	infoService.listForm(tagId, title,source,insertName,state, empUrl, empHiPicUrl, empLowPicUrl, descUrl, descContent, 4, level, begincreateDate, pageable);
		
		result.put("total", page.get("total"));
		result.put("rows", page.get("rows"));
		return result;
	}
	 

	/***
	 * 跳转到修改表格页面
	 * @param id
	 * @param base
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/form/update/{id}", method = RequestMethod.GET)
	public String toUpdate(@PathVariable("id") String id, Base base, Model model) {
		model.addAttribute("form", formService.getFormById(id));
		return "page/form/updateForm";
	}

	@RequestMapping(value = "/form/update/{id}", method = RequestMethod.POST)
	public String doUpdate(@PathVariable("id") String id, Form form, Model model) {
		try {
			formService.saveForm(form);
			model.addAttribute("resMess", "更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("resMess", "更新失败！");
		}
		return "redirect:/formlist";
	}

	@RequestMapping(value = "/form/details/{id}", method = RequestMethod.GET)
	public String doDetails(@PathVariable("id") String id, Form form,
			Model model) {
		model.addAttribute("form", formService.getFormById(id));
		return "page/form/seeForm";
	}

	/***
	 * 删除表格
	 * 
	 * @param id
	 * @param base
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/form/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> doDelete(@PathVariable("id") String id,
			Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			formService.deleteForm(id);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "删除失败！");
		}
		return result;
	}

	@RequestMapping(value = "/form/add", method = RequestMethod.GET)
	public String toAddPage(Model model) throws Exception {
		return "page/form/addForm";
	}

	/**
	 * 跳转到修改页面
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/edit/{id}", method = RequestMethod.GET)
	public String toEditPage(@PathVariable("id") String id, Model model)
			throws Exception {
		// 表格对象
		Form f = formService.getFormById(id) ;
		// WEB图片url列表
		List<String> hiPicUrlSet = new ArrayList<String>();
		// Mobile图片url列表
		List<String> lowPicUrlSet = new ArrayList<String>();
		// WEB图片url字符串
		String hiPicUrl = f.getEmpHiPicUrl();
		// Mobile图片url字符串
		String lowPicUrl = f.getEmpLowPicUrl() ;
		// 将WEB图片url字符串根据“;”拆分存入WEB图片url列表中
		if(hiPicUrl != null && !"".equals(hiPicUrl)) {	// 获取WEB图片的SET
			String hiPicUrlArray[] = hiPicUrl.split(";") ;	
			for(int i = 0 ; i < hiPicUrlArray.length ; i++) {	
				if(!"".equals(hiPicUrlArray[i]) && hiPicUrlArray[i] != null) {
					hiPicUrlSet.add(hiPicUrlArray[i]) ;
				}
			}
		}
		// 将Mobile图片url字符串根据“;”拆分存入Mobile图片url列表中
		if(lowPicUrl != null && !"".equals(lowPicUrl)) {	// 获取Mobile图片的SET
			String lowPicUrlArray[] = lowPicUrl.split(";") ;
			for(int j = 0 ; j < lowPicUrlArray.length ; j++) {
				if(!"".equals(lowPicUrlArray[j]) && lowPicUrlArray[j] != null) {
					lowPicUrlSet.add(lowPicUrlArray[j]) ;
				}
			}
		}
		model.addAttribute("form", f);
		model.addAttribute("hiPicUrlSet", hiPicUrlSet) ;
		model.addAttribute("lowPicUrlSet", lowPicUrlSet) ;
		return "page/form/editForm";
	}

	/**
	 * excel导入表格基本信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/form/excelUpload", method = RequestMethod.POST)
	public @ResponseBody
	String doUpload(HttpServletRequest request, HttpServletResponse response) {
		String path = request.getSession().getServletContext()
				.getRealPath("upload");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();
			String fileName = mf.getOriginalFilename();
			File file = new File(path, fileName);
			InputStream is = null;
			try {
				// 将excel上传到项目文件夹中
				ioUtil.streamToFile(mf.getInputStream(), file, true);
				// jxls从excel中读取数据
				List<Form> forms = formService.readXls(file.getAbsolutePath());
				//System.out.println("form.size:" + forms.size());

				// 建立表格与标签间的多对多关系
				List<Form> formList = new ArrayList<Form>();
				if (forms.size() != 0 && forms != null) {
					Iterator<Form> ite = forms.iterator();
					while (ite.hasNext()) {
						Form f = (Form) ite.next();
						Set<Tag> tagSet = new HashSet<Tag>();
						// 获取所有的标签
						String tags = f.getTagArray();
						String tagArr[] = tags.split("&");
						for (int i = 0; i < tagArr.length; i++) {
							Tag tag = new Tag();
							if (tagArr[i] != null) {
								tag = tagService.findTagByName(tagArr[i]);
							}
							if (tag != null) {
								tagSet.add(tag);
							}
						}
						f.setTags(tagSet);
						// f.setSampHiPicUrl("") ;
						f.setEmpHiPicUrl(""); // 设置预览图片地址为空，默认为NULL，无法弹出预览窗口
						f.setEmpLowPicUrl(""); // 通过Excel导入，设置图片地址字段默认为空字符串
						f.setCreateDate(new Date()) ; // 创建时间
						if (SecurityContextHolder.getContext()
								.getAuthentication().getPrincipal() instanceof UserDetails) {
							UserDetails userDetails = (UserDetails) SecurityContextHolder
									.getContext().getAuthentication()
									.getPrincipal();
							User user = accountService
									.loadUserByUsername(userDetails
											.getUsername());
							f.setInsertId(user.getId());
							f.setInsertName(user.getAliasname());
						}
						formList.add(f);
					}
					// 保存所有的表格信息
					formService.saveFormList(formList);
				}
				is = mf.getInputStream();
				return "success";
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} 
		return "false";
	}

	/**
	 * 同步附件(original)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/synchronousAttachmentsOriginal", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> synchronousAttachmentsOriginal() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		// 存放错误信息
		String msg = "" ;
		PropertiesReader pr = PropertiesReader.getInstance();
		// 获取附件存放的路径
		String inFolder = pr.getProperties("FormDirectoryPath");
		File inFile = new File(inFolder);
		// //System.out.println("2222  path:" + inFile) ;
		// 从磁盘目录遍历所有的附件
		TreeSet<File> allFiles = new TreeSet<File>();
		try {
			ioUtil.setAllFiles(allFiles);
			ioUtil.traversalFile(inFile);
			allFiles = ioUtil.getAllFiles();
			// //System.out.println("3333 获取的文件size : " + allFiles.size()) ;
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "同步失败, 遍历附件出错！");
			e.printStackTrace();
		}

		// 处理文件
		try {
			Iterator<File> ite = allFiles.iterator();
			while (ite.hasNext()) {
				File f = ite.next();
				// 大小
				Long fileLength = f.length();
				// 绝对路径
				String fileAbsultePath = f.getAbsolutePath();
				//System.out.println("4444 绝对路径 ：" + fileAbsultePath);
				
				// 文件名
//				String fileName = fileAbsultePath.substring(
//						fileAbsultePath.lastIndexOf("\\") + 1,
//						fileAbsultePath.lastIndexOf(".")); // Windows 环境
				
				 String fileName =
				 fileAbsultePath.substring(fileAbsultePath.lastIndexOf("/")+1,
				 fileAbsultePath.lastIndexOf(".")) ; // linux 环境

				// //System.out.println("fileName:" + fileName) ;
				msg = fileName ;
				
				// 文件后缀
				String fileExtName = fileAbsultePath.substring(fileAbsultePath
						.lastIndexOf(".") + 1);
				// //System.out.println("fileExtName:" + fileExtName) ;
				// 标题
				String fileTitle = fileName.substring(0, fileName.indexOf("_"));
				// //System.out.println("fileTitle:" + fileTitle) ;
				
				// 地区标签
				// windows
//	    		String fileAreaTag = fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).substring(0, fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).lastIndexOf("\\")).substring(fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).substring(0, fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).lastIndexOf("\\")).lastIndexOf("\\")+1) ;
	    		// linux
	    		String fileAreaTag = fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).substring(0, fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).lastIndexOf("/")).substring(fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).substring(0, fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).lastIndexOf("/")).lastIndexOf("/")+1) ;
	    		
	    		// 来源标记
	    		// windows
//	    		String fileSource = fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).substring(fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).lastIndexOf("\\")+1) ;
	    		// linux
	    		String fileSource = fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).substring(fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).lastIndexOf("/")+1) ;
	    		// 模糊查询
	    		fileSource = "%" + fileSource + "%" ;
	    		
				if (formService.getFormByName(fileTitle, fileAreaTag, fileSource) != null) { // 已通过Excel导入数据
					// 上传到文件系统
					PropertiesReader pu = PropertiesReader.getInstance();
					String path = pu.getProperties("fdfs.HttpAddress");
					// 上传附件的格式校验
					if ("xls".equals(fileExtName) || "jpg".equals(fileExtName)
							|| "doc".equals(fileExtName)
							|| "pdf".equals(fileExtName)
							|| "xlsx".equals(fileExtName)
							|| "docx".equals(fileExtName)) {
						InputStream inputStream = new FileInputStream(f);
						try {
							path += fdfsService.upload(fileName, fileExtName,
									inputStream, fileLength);
							if(path.equals(pu.getProperties("fdfs.HttpAddress"))) {	// 文件服务器返回地址为空
						 		 result.put("fail", true);
								 result.put("msg", "文件服务器fdfs未正确的返回附件地址！") ;
								 return result;
							 }
						} catch (Exception e) {
							result.put("success", false);
							result.put("msg", "同步失败，附件 《 " + fileName + " 》 上传文件系统出错！" ) ;
							e.printStackTrace();
						}
						//System.out.println("path : " + path);
						// 存数据库
						// 标题
						String formTitle = "";
						// 类型 emp/samp
						String formSort = "";
						// 大小 WEB/Mobile
						String formSize = "";
						// 顺序（多图片）
						String formOrder = "";
						try {
							String arr[];
							arr = fileName.split("_");
							if (arr.length == 2) { // 源文件
								formTitle = arr[0];
								formSort = arr[1];
								formService.modifySourceUrlOriginal(formTitle,
										formSort, path, fileAreaTag, fileSource);
								// //System.out.println("6666 源文件更新数量：" +

							} else if (arr.length == 4) { // 图片文件
								formTitle = arr[0];
								formSort = arr[1];
								formSize = arr[2];
								formOrder = arr[3];
								formService.modifyPicUrlOriginal(formTitle, formSort,
										formSize, formOrder, path, fileAreaTag, fileSource);
								// //System.out.println("7777 图片更新数量：" +
							} else { // 命名错误

							}

						} catch (Exception e) {
							result.put("success", false);
							result.put("msg", "同步失败, 附件 《" + fileName + "》 更新数据库时出错，请检查！");
							e.printStackTrace();
						}
					} else {
	    				  result.put("fail", true);
	    				  result.put("msg", "同步失败，暂不支持该类型《" + fileName + "." + fileExtName + "》的附件同步！");
	    				  return result;
   	    		  	}
				}

			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "同步失败, 附件 《" + msg + "》 名称格式有误，请检查！");
			e.printStackTrace();
		}
		return result;
	}
	

	/**
	 * 同步附件
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/synchronousAttachments", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> synchronousAttachments() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		// 存放错误信息
		String msg = "" ;
		PropertiesReader pr = PropertiesReader.getInstance();
		// 获取附件存放的路径
		String inFolder = pr.getProperties("FormDirectoryPath");
		File inFile = new File(inFolder);
		// //System.out.println("2222  path:" + inFile) ;
		// 从磁盘目录遍历所有的附件
		TreeSet<File> allFiles = new TreeSet<File>();
		try {
			ioUtil.setAllFiles(allFiles);
			ioUtil.traversalFile(inFile);
			allFiles = ioUtil.getAllFiles();
			// //System.out.println("3333 获取的文件size : " + allFiles.size()) ;
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "同步失败, 遍历附件出错！");
			e.printStackTrace();
		}

		// 处理文件
		try {
			Iterator<File> ite = allFiles.iterator();
			while (ite.hasNext()) {
				File f = ite.next();
				// 大小
				Long fileLength = f.length();
				// 绝对路径
				String fileAbsultePath = f.getAbsolutePath();
				//System.out.println("4444 绝对路径 ：" + fileAbsultePath);
				
				// 文件名
//				String fileName = fileAbsultePath.substring(
//						fileAbsultePath.lastIndexOf("\\") + 1,
//						fileAbsultePath.lastIndexOf(".")); // Windows 环境
				
				 String fileName =
				 fileAbsultePath.substring(fileAbsultePath.lastIndexOf("/")+1,
				 fileAbsultePath.lastIndexOf(".")) ; // linux 环境

				// //System.out.println("fileName:" + fileName) ;
				msg = fileName ;
				
				// 文件后缀
				String fileExtName = fileAbsultePath.substring(fileAbsultePath
						.lastIndexOf(".") + 1);
				// //System.out.println("fileExtName:" + fileExtName) ;
				// 标题
				String fileTitle = fileName.substring(0, fileName.indexOf("_"));
				// //System.out.println("fileTitle:" + fileTitle) ;
				
				// 地区标签
				// windows
//	    		String fileAreaTag = fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).substring(0, fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).lastIndexOf("\\")).substring(fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).substring(0, fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).lastIndexOf("\\")).lastIndexOf("\\")+1) ;
	    		// linux
	    		String fileAreaTag = fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).substring(0, fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).lastIndexOf("/")).substring(fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).substring(0, fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).lastIndexOf("/")).lastIndexOf("/")+1) ;
	    		
	    		// 来源标记
	    		// windows
//	    		String fileSource = fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).substring(fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("\\")).lastIndexOf("\\")+1) ;
	    		// linux
	    		String fileSource = fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).substring(fileAbsultePath.substring(0, fileAbsultePath.lastIndexOf("/")).lastIndexOf("/")+1) ;
	    		// 模糊查询
	    		fileSource = "%" + fileSource + "%" ;
	    		
				if (formService.getFormByName(fileTitle, fileAreaTag, fileSource) != null) { // 已通过Excel导入数据
					// 上传到文件系统
					PropertiesReader pu = PropertiesReader.getInstance();
					String path = pu.getProperties("fdfs.HttpAddress");
					// 上传附件的格式校验
					if ("xls".equals(fileExtName) || "jpg".equals(fileExtName)
							|| "doc".equals(fileExtName)
							|| "pdf".equals(fileExtName)
							|| "xlsx".equals(fileExtName)
							|| "docx".equals(fileExtName)) {
						InputStream inputStream = new FileInputStream(f);
						try {
							path += fdfsService.upload(fileName, fileExtName,
									inputStream, fileLength);
							if(path.equals(pu.getProperties("fdfs.HttpAddress"))) {	// 文件服务器返回地址为空
						 		 result.put("fail", true);
								 result.put("msg", "文件服务器fdfs未正确的返回附件地址！") ;
								 return result;
							 }
						} catch (Exception e) {
							result.put("success", false);
							result.put("msg", "同步失败，附件 《 " + fileName + " 》 上传文件系统出错！" ) ;
							e.printStackTrace();
						}
						//System.out.println("path : " + path);
						// 存数据库
						// 标题
						String formTitle = "";
						// 类型 emp/samp
						String formSort = "";
						// 大小 WEB/Mobile
						String formSize = "";
						// 顺序（多图片）
						String formOrder = "";
						try {
							String arr[];
							arr = fileName.split("_");
							if (arr.length == 2) { // 源文件
								formTitle = arr[0];
								formSort = arr[1];
								formService.modifySourceUrl(formTitle,
										formSort, path, fileAreaTag, fileSource);
								// //System.out.println("6666 源文件更新数量：" +

							} else if (arr.length == 4) { // 图片文件
								formTitle = arr[0];
								formSort = arr[1];
								formSize = arr[2];
								formOrder = arr[3];
								formService.modifyPicUrl(formTitle, formSort,
										formSize, formOrder, path, fileAreaTag, fileSource);
								// //System.out.println("7777 图片更新数量：" +
							} else { // 命名错误

							}

						} catch (Exception e) {
							result.put("success", false);
							result.put("msg", "同步失败, 附件 《" + fileName + "》 更新数据库时出错，请检查！");
							e.printStackTrace();
						}
					} else {
	    				  result.put("fail", true);
	    				  result.put("msg", "同步失败，暂不支持该类型《" + fileName + "." + fileExtName + "》的附件同步！");
	    				  return result;
   	    		  	}
				}

			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "同步失败, 附件 《" + msg + "》 名称格式有误，请检查！");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 同步附件(批量)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/synchronousAttachments02", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> synchronousAttachments02() throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		PropertiesReader pr = PropertiesReader.getInstance();
		// 获取附件存放的路径
		String inFolder = pr.getProperties("FormDirectoryPath");
		File inFile = new File(inFolder);
		// 从磁盘目录遍历所有的附件
		TreeSet<File> allFiles = new TreeSet<File>();
		try {
			ioUtil.setAllFiles(allFiles);
			ioUtil.traversalFile(inFile);
			allFiles = ioUtil.getAllFiles();
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "同步失败, 遍历附件出错！");
			e.printStackTrace();
		}

		// 批处理文件
		result = formService.modifyUrl(allFiles);
		
		return result;
	}

	
	
	/**
	 * 反选标签
	 * 
	 * @param id
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/form/tags/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Set<Tag> doFormTags(@PathVariable("id") String id, Form form,
			Model model) {
		return formService.getFormById(id).getTags();
	}

	/**
	 * 表格预览
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/form/preView/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getFormForPreview(@PathVariable("id") String id) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		Form f = new Form() ;
		try {
			f = formService.getFormById(id);
			result.put("success", true) ;
		} catch (Exception e) {
			result.put("success", false) ;
			result.put("msg", "预览失败！") ;
			e.printStackTrace() ;
		}
		result.put("form", f) ;
		return result ;
	}
	
	/**
	 * 修改界面删除图片附件
	 * @param picUrl
	 * @param id
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/form/deletePic/{url}/{id}/{type}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deletePic(@PathVariable("url")String url, @PathVariable("id")String id, @PathVariable("type")String type) {
		Map<String, Object> result = new HashMap<String, Object>() ;
		String picUrl = url.replaceAll("&", "/") ;	// 将在JS中转换的url地址还原
		
		//删除文件服务器数据
		try {
			fdfsService.delete(picUrl.substring(21)) ;
		} catch(Exception e) {
			result.put("success", false) ;
			result.put("msg", "删除文件系统数据时出错！") ;
			e.printStackTrace() ;
		}
		
		
		// 删除数据库数据
		try {
			Form f = formService.getFormById(id) ;
			String hiPicUrl = "" ;
			String hiPicUrlArray[] ;
			String newHiPicUrl = "" ;
			String lowPicUrl = "" ;
			String lowPicUrlArray[] ;
			String newLowPicUrl = "" ;
			if(f != null) {
				if("hi".equals(type)) {	// 删除WEB端图片
					hiPicUrl = f.getEmpHiPicUrl();
					hiPicUrlArray = hiPicUrl.split(";") ;
					StringBuffer sb = new StringBuffer() ;
					for(int i = 0 ; i < hiPicUrlArray.length ; i++) {	// 循环判断原字符串中的URL
						if(!"".equals(hiPicUrlArray[i]) && hiPicUrlArray[i] != null) {
							if(!picUrl.equals(hiPicUrlArray[i])) {	// 将非删除的url重新组合成新的url字符串
								sb.append(";") ;
								sb.append(hiPicUrlArray[i]) ;
							}
						}
					}
					newHiPicUrl = sb.toString() ;
					//f.setEmpHiPicUrl(newHiPicUrl) ;
					// 保存新的字符串url
					formService.deletePic(id, newHiPicUrl, type) ;
					result.put("success",  true) ;
				} else if("low".equals(type)) {	// 删除Mobile图片
					lowPicUrl = f.getEmpLowPicUrl() ;
					lowPicUrlArray = lowPicUrl.split(";") ;
					StringBuffer sb = new StringBuffer() ;
					for(int i = 0 ; i < lowPicUrlArray.length ; i++) {
						if(!"".equals(lowPicUrlArray[i]) && lowPicUrlArray[i] != null) {
							if(!picUrl.equals(lowPicUrlArray[i])) {
								sb.append(";") ;
								sb.append(lowPicUrlArray[i]) ;
							}
						}
					}
					newLowPicUrl = sb.toString() ;
					//f.setEmpLowPicUrl(newLowPicUrl) ;
					formService.deletePic(id, newLowPicUrl, type) ;
					result.put("success", true) ;
				}
			}
		} catch(Exception e) {
			result.put("success", false) ;
			result.put("msg", "删除数据库数据时错误！") ;
			e.printStackTrace() ;
		}
		return result ;
	}
	
	/**
	 * 跳转到表格复制页面
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/form/copy/{id}", method = RequestMethod.GET)
	public String toCopyPage(@PathVariable("id") String id, Model model)
			throws Exception {
		Form f = formService.getFormById(id) ;
		model.addAttribute("form", f);
		return "page/form/copyForm";
	}
	
	/**
	 * 复制新表格
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/form/copySave", method = RequestMethod.POST)
	@ResponseBody
	public void doCopySave(Form form, HttpServletRequest request,
			HttpServletResponse response) {
		String resultJson = "{'success':true}";
		
		try {
			if (SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal() instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				User user = accountService.loadUserByUsername(userDetails
						.getUsername());
				form.setInsertId(user.getId());
				form.setInsertName(user.getAliasname());
				form.setCreateDate(new Date());
			}
			// 未传id，所以是新建
			formService.saveForm(form);
		} catch (Exception e) {
			resultJson = "{'msg':'保存出错!'}";
			e.printStackTrace();
		}
		commonService.responseJsonBody(response, resultJson);
	}

	/**
	 * 跳转到来源视图页面
	 * @return
	 */
	@RequestMapping(value = "/formviewlist", method = RequestMethod.GET)
	public String sourceviewlist() {
		return "page/form/formviewlist";
	}
	
	@RequestMapping(value = "/formviewlist/data", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> viewlistTagJson(@PageableDefaults(value=60) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		String name=request.getParameter("name");
		//Page<FormView> page = formService.listFormView(name, pageable);
		List<FormView> formViewList = formService.listFormViewBaseRows(name, pageable);
		BigInteger formViewTotal = formService.listFormViewBaseTotal(name);
		result.put("total",formViewTotal);  
		result.put("rows", formViewList);  
		return result;
	}
	
	/**
	 * 跳转到审批页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/form/audit/{id}", method = RequestMethod.GET)
	public String toAuditPage(@PathVariable("id")String id, Model model) {
		Form f = formService.getFormById(id);
		List<String> hiPicUrlSet = new ArrayList<String>();
		List<String> lowPicUrlSet = new ArrayList<String>();
		String hiPicUrl = f.getEmpHiPicUrl();
		String lowPicUrl = f.getEmpLowPicUrl() ;
		if(hiPicUrl != null && !"".equals(hiPicUrl)) {	// 获取WEB图片的SET
			String hiPicUrlArray[] = hiPicUrl.split(";") ;	
			for(int i = 0 ; i < hiPicUrlArray.length ; i++) {	
				if(!"".equals(hiPicUrlArray[i]) && hiPicUrlArray[i] != null) {
					hiPicUrlSet.add(hiPicUrlArray[i]) ;
				}
			}
		}
		if(lowPicUrl != null && !"".equals(lowPicUrl)) {	// 获取Mobile图片的SET
			String lowPicUrlArray[] = lowPicUrl.split(";") ;
			for(int j = 0 ; j < lowPicUrlArray.length ; j++) {
				if(!"".equals(lowPicUrlArray[j]) && lowPicUrlArray[j] != null) {
					lowPicUrlSet.add(lowPicUrlArray[j]) ;
				}
			}
		}
		model.addAttribute("form", f);
		model.addAttribute("hiPicUrlSet", hiPicUrlSet) ;
		model.addAttribute("lowPicUrlSet", lowPicUrlSet) ;
		return "/page/form/auditForm";
	}
	
	/**
	 * 源文件替换时删除fdfs上的文件
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/form/fdfsDel", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteFromFdfs(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String url = request.getParameter("url");
		if(!"".equals(url) && url != null) {
			url = url.replaceAll("&", "/");
		}
		try {
			fdfsService.delete(url.substring(21)) ;
			result.put("success", true);
		} catch(Exception e) {
			result.put("success", false) ;
			result.put("msg", "删除文件系统数据时出错！") ;
			e.printStackTrace() ;
		}
		return result;
	}
	
}
