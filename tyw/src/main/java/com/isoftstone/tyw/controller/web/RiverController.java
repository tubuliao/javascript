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
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.entity.river.BaseRiver;
import com.isoftstone.tyw.service.CommonService;
import com.isoftstone.tyw.service.RiverService;

/**
 * @author zhangyq
 * 
 * 
 */
@Controller
public class RiverController {

	@Autowired
	private RiverService riverService;

	
	@RequestMapping(value = "/addTag", method = RequestMethod.GET)
	public String addTag() {
		
		int amount = 0;//每一页的个数
		int amountPage = 0;//元素为amount个的页数
		boolean havaLast = false;//是否有最后不满amount个的页面
		int lastPageAmount = 0;//最后一页元素的个数
		long total = 0;//总记录数
		String tag = "";//标签用逗号‘,’隔开的字符串
		int w =0;
		
		Page<Base> base = null;//分页取出切片知识列表
		List<Base> rows = null;//每个分页里的元素列表
		Base os = null;//一个切片实例
		Pageable pageable=null;//用来初始化分页元素的个数
		Set<Tag> tags = null;
		BaseRiver baseRiver = null;
		
		amount = 100;//初始化每页的个数
		pageable=new PageRequest(0,amount);//按amount分页
		base = riverService.listBase(pageable);
		total=base.getTotalElements();
		havaLast = (total/amount)>0;
		lastPageAmount = (int) (total%amount);
		amountPage=(int) (total/amount);
		
		if(havaLast){
			amountPage+=1;//如果存在最后不满amount个的页面，就让元素为amount个的页数加上一个为当前需要循环的页面数
		}

		for(int i=0;i<amountPage;i++){//循环页内元素个数为amount的每一页
			base = riverService.listBase(new PageRequest(i,amount));
			rows = base.getContent();//当前页的元素
			if(havaLast&&i==(amountPage-1)){//如果循环到最后一个元素，并且此元素为不满amount个的页面，此循环数就变为当前（最后一页）页的记录数
				amount = lastPageAmount;
			}
			System.out.println("第一个for循环：+"+rows.size());
			for(int j=0;j<amount;j++){//每页循环amount次，每次取出一条Base（切片）的记录.并通过此条记录查询评论数，然后保存到ASegment记录里。
				os = rows.get(j);//当前记录
				tag ="";//初始化标签
				tags = os.getTags();//取得标签SET
				
				for(Tag o :tags){
					tag+=(o.getName()+',');
				}
				tag = tag.substring(0, tag.length()-1);//组装之后的tag.用逗号组装
				//System.out.println("第二个for循环：2"+os.getId()+tag);
				baseRiver = riverService.getBaseRiverById(os.getId());//
				if(null!=baseRiver&&!"".equals(baseRiver)){
					baseRiver.setTags(tag);
					System.out.println("成功"+(w++));
					riverService.saveBaseRiver(baseRiver);
				}
			}
		}
		
		
		return "redirect:/index.jsp";
	}

}
