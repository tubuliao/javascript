/**
 * Copyright 2008 - 2012 Simcore.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.isoftstone.tyw.controller.rest;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.isoftstone.tyw.dto.info.FileAPIDTO;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Files;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.FilesService;

/**
 * 
 * 大文件接口
 * @author zhanglei
 *
 */
@Controller
public class FileRestController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private FilesService filesService;
	
	
	@RequestMapping(value = "/api/file", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<FileAPIDTO> list(@PageableDefaults(value=30) Pageable pageable,@RequestParam(value = "syncDate", required = false) String syncDate) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = accountService.loadUserByUsername(userDetails.getUsername());
		Page<Files> list = filesService.listFiles(pageable,user.getId(),syncDate);
		List<Files> content = list.getContent();
		List<FileAPIDTO> ncontent = Lists.newArrayList();
		for(Files file : content){
			StringBuffer sb = new StringBuffer("");
			Tag [] tags=file.getTags().toArray(new Tag[file.getTags().size()]);
			for(int i=0;i<tags.length;i++){
				sb.append(tags[i].getId());
				if(i!=tags.length-1){
					sb.append(",");
				}
			}
			FileAPIDTO fto = new FileAPIDTO(file.getId(), file.getTitle(),file.getCreateDate(),sb.toString(),file.getSource(),file.getShortTitle());
			ncontent.add(fto);
		}
		Page<FileAPIDTO> result = new PageImpl<FileAPIDTO>(ncontent,new PageRequest(list.getNumber(),list.getSize()),list.getTotalElements());
		return result;
	}
	
	@RequestMapping(value = "/api/file", method = RequestMethod.POST)
	@ResponseBody
	public String save(@ModelAttribute("files")Files files,@RequestParam(value = "urls", required = false)String urls){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = accountService.loadUserByUsername(userDetails.getUsername());
		/*if(urls!=null&&!"".equals(urls)){
			String [] url=urls.split(";");
			List<Attachment> attachments = new ArrayList<Attachment>();
			for(int i=0;i<url.length;i++){
				Attachment attachment = new Attachment();
				attachment.setUrl(url[i]);
			}
			files.setAttachments(attachments);
		}*/
		files.setInsertId(user.getId());
		files.setInsertName(user.getAliasname());
		if(files.getId()==null||"".equals(files.getId())){
			files.setCreateDate(new Date());
		}
		filesService.saveFileAndBase(files);
		return "{id:"+files.getId()+"}";
	}
	
	
	@RequestMapping(value = "/api/file/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Files getById(@PathVariable("id") String id){
		return filesService.getFileById(id);
	}
	
}
