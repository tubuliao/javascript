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
package com.isoftstone.tyw.controller.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.auths.Role;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.RoleService;
import com.isoftstone.tyw.util.Pager;

/**
 * @author jqz
 * 
 * 
 */
@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private CommonService commonService;

	
	@RequestMapping(value = "/role", method = RequestMethod.GET)
	public String rolePage() {
		return "page/role/role";
	}

	/**
	 * 获取所有息角色列表
	 * 
	 * @param pageable
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/rolelist", method = RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> listRole(@PageableDefaults(value=10) Pageable pageable, Model model,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String,Object>(); 
		String name=request.getParameter("name");
		String permissions=request.getParameter("permissions");
		Pager page = roleService.listRole(name,permissions,pageable);
		result.put("total",page.getRowCount());  
		result.put("rows", page.getResult());  
		return result;
	}
	
	
	@RequestMapping(value = "/rolelist/all", method = RequestMethod.POST )
	@ResponseBody
	public Object listRoleAll() {
		return roleService.listRole();
	}
	

	/**
	 * 保存角色
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/role/save", method = RequestMethod.POST)
	@ResponseBody
	public void doSave(@ModelAttribute("role") Role role,HttpServletResponse response){
		String resultJson = "{'success':true}";
		try {
			roleService.saveRole(role);
			resultJson = "{'success':true}";
		 } catch (Exception e) { 
			 resultJson = "{'msg':'保存出错!'}";
			 e.printStackTrace();
	     } 
		commonService.responseJsonBody(response, resultJson);
	}

	/**
	 * 删除角色信息
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/role/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String,Object> doDelete(@PathVariable("id") String id, Model model){
		Map<String,Object> result=new HashMap<String,Object>();
		try {
			roleService.deleteRole(id);
			result.put("success",true);
		} catch (Exception e) {
			result.put("msg", "删除失败！");
			e.printStackTrace();
		}
		return result;
	}
}
