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
package com.isoftstone.tyw.service;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.isoftstone.tyw.entity.auths.Role;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.river.BaseRiver;
import com.isoftstone.tyw.repository.auths.RoleDao;
import com.isoftstone.tyw.repository.info.BaseDao;
import com.isoftstone.tyw.repository.river.BaseRiverDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色管理类
 * 
 * @author jqz
 */
@Component
@Transactional(readOnly=true)
public class RiverService {
	
	@Autowired
	private BaseRiverDao baseRiverDao;
	
	@Autowired
	private BaseDao baseDao;
	
	/**
	 * 保存BaseRiver
	 * 
	 * @param baseRiver
	 * @return BaseRiver
	 */
	 
	@Transactional(readOnly = false)
	public BaseRiver saveBaseRiver(BaseRiver baseRiver) {
		return baseRiverDao.save(baseRiver);
	}
	
	/**
	 * 查出所有的Base信息
	 * @param pageable
	 * @return
	 */
	public Page<Base> listBase(Pageable pageable){
		return baseDao.findAll(pageable);
	}
	
	/**
	 * 根据ID查出BaseRiver单条记录
	 * @param id
	 * @return
	 */
	public BaseRiver getBaseRiverById(String id){
		return baseRiverDao.findOne(id);
		
	}
}
