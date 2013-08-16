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


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Powerpoint;
import com.isoftstone.tyw.service.AccountService;
import com.isoftstone.tyw.service.PowerpointService;

/**
 * 
 * 
 * @author jqz
 *
 */
@Controller
public class PowerpointRestController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private  PowerpointService powerpointService;
	
	
	
	@RequestMapping(value = "/api/powerpoint", method = RequestMethod.POST)
	@ResponseBody
	public String save(@ModelAttribute("Powerpoint")Powerpoint powerpoint){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = accountService.loadUserByUsername(userDetails.getUsername());
		powerpoint.setInsertId(user.getId());
		powerpoint.setInsertName(user.getAliasname());
		powerpointService.save(powerpoint);
		return "{id:"+powerpoint.getId()+"}";
	}
}