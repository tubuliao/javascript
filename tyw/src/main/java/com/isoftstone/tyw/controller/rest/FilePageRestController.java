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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.isoftstone.tyw.entity.info.FilesPage;
import com.isoftstone.tyw.service.FilesPageService;

/**
 * 
 * 大文件单页信息接口
 * @author jqz
 *
 */
@Controller
public class FilePageRestController {
	

	@Autowired
	private FilesPageService pageService;
	
	
	/**
	 * 保存单页信息
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/api/filePage", method = RequestMethod.POST)
	@ResponseBody
	public String save(@ModelAttribute("page")FilesPage page){
		FilesPage filesPage=pageService.saveFilePage(page);
		return new String("{id:"+ filesPage.getId() +"}");
	}
	
}
