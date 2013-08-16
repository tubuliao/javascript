/**
 * Copyright 2008 - 2012 Simcore.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this image except in compliance with the License.
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
import com.isoftstone.tyw.dto.info.ImageAPIDTO;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Image;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.ImageService;

/**
 * 
 * 
 * @author zhanglei
 *
 */
@Controller
public class ImageRestController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private ImageService imageService;
	
	
	@RequestMapping(value = "/api/image", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<ImageAPIDTO> list(@PageableDefaults(value=30) Pageable pageable,@RequestParam(value = "syncDate", required = false) String syncDate) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = accountService.loadUserByUsername(userDetails.getUsername());
		Page<Image> list = imageService.listImage(pageable,user.getId(),syncDate);
		List<Image> content = list.getContent();
		List<ImageAPIDTO> ncontent = Lists.newArrayList();
		for(Image image : content){
			StringBuffer sb = new StringBuffer("");
			Tag [] tags=image.getTags().toArray(new Tag[image.getTags().size()]);
			for(int i=0;i<tags.length;i++){
				sb.append(tags[i].getId());
				if(i!=tags.length-1){
					sb.append(",");
				}
			}
			ImageAPIDTO fto = new ImageAPIDTO(image.getId(), image.getTitle(),image.getCreateDate(),sb.toString(),image.getSource(),image.getShortTitle());
			ncontent.add(fto);
		}
		Page<ImageAPIDTO> result = new PageImpl<ImageAPIDTO>(ncontent,new PageRequest(list.getNumber(),list.getSize()),list.getTotalElements());
		return result;
	}
	
	@RequestMapping(value = "/api/image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String save(@ModelAttribute("image")Image image){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = accountService.loadUserByUsername(userDetails.getUsername());
		image.setInsertId(user.getId());
		image.setInsertName(user.getAliasname());
		if(image.getId()==null||"".equals(image.getId())){
			image.setCreateDate(new Date());
		}
		Image pimage=imageService.saveOne(image);
		return "{id:"+pimage.getId()+"}";
	}
	
	@RequestMapping(value = "/api/image/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Image getById(@PathVariable("id") String id){
		return imageService.getImageById(id);
	}
	
}
