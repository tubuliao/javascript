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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.isoftstone.tyw.dto.info.FormAPIDTO;
import com.isoftstone.tyw.dto.info.FormDTO;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Form;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.FormService;

/**
 * 
 * 
 * @author zhanglei
 *
 */
@Controller
public class FormRestController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private FormService formService;
	

	@RequestMapping(value = "/api/form", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<FormAPIDTO> list(@PageableDefaults(value=30) Pageable pageable,@RequestParam(value = "syncDate", required = false) String syncDate) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = accountService.loadUserByUsername(userDetails.getUsername());
		Page<Form> list = formService.listForm(pageable,user.getId(),syncDate);
		List<Form> content = list.getContent();
		List<FormAPIDTO> ncontent = Lists.newArrayList();
		for(Form form : content){
			StringBuffer sb = new StringBuffer("");
			Tag [] tags=form.getTags().toArray(new Tag[form.getTags().size()]);
			for(int i=0;i<tags.length;i++){
				sb.append(tags[i].getId());
				if(i!=tags.length-1){
					sb.append(",");
				}
			}
			FormAPIDTO fto = new FormAPIDTO(form.getId(), form.getTitle(),form.getCreateDate(),sb.toString(),form.getSource(),form.getShortTitle());
			ncontent.add(fto);
		}
		Page<FormAPIDTO> result = new PageImpl<FormAPIDTO>(ncontent,new PageRequest(list.getNumber(),list.getSize()),list.getTotalElements());
		return result;
	}
	
	@RequestMapping(value = "/api/form", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String save(Form form){
	    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    User user = accountService.loadUserByUsername(userDetails.getUsername());
	    form.setInsertId(user.getId());
	    form.setInsertName(user.getAliasname());
	    String id=form.getId();
	    Form old = formService.getFormById(id);
	    form.setCreateDate(old.getCreateDate());
	    form.setEmpHiPicUrl(old.getEmpHiPicUrl());
	    form.setEmpLowPicUrl(old.getEmpLowPicUrl());
	    form.setEmpUrl(old.getEmpUrl());
	    form.setDescUrl(old.getDescUrl());
	    form.setSampHiPicUrl(old.getSampHiPicUrl());
	    form.setSampLowPicUrl(old.getSampLowPicUrl());
	    form.setSampUrl(old.getSampUrl());
    	formService.saveForm(form);
	    return new String("{id:"+ form.getId() +"}");
	}
	
	@RequestMapping(value = "/api/form/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Form getById(@PathVariable("id") String id){
		return formService.getFormById(id);
	}
	
}
