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


import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.auths.Role;
import com.isoftstone.tyw.repository.auths.RoleDao;
import com.isoftstone.tyw.util.Pager;

/**
 * 角色管理类
 * 
 * @author jqz
 */
@Component
@Transactional(readOnly=true)
public class RoleService {
	
	@Autowired
	private RoleDao roleDao;
	
	 @PersistenceContext
	 public EntityManager entityManager;
	/**
	 * 保存角色
	 * 
	 * @param role
	 * @return
	 */
	 
	@Transactional(readOnly = false)
	public Role saveRole(Role role) {
		return roleDao.save(role);
	}
	
	/**
	 * 删除角色
	 * 
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void deleteRole(String id){
		roleDao.delete(id);
	}
	
	/**
	 * 分页查询角色
	 * 
	 * @param pageable
	 * @return
	 */
	public Pager listRole(String name,String permissions,Pageable pageable){
		
		StringBuffer sb = new StringBuffer("select * from auths_role u where 1=1 ");
		StringBuffer sbc = new StringBuffer("select count(1) from auths_role u where 1=1 ");
		if(StringUtils.isNotBlank(name)){
			sb.append(" and u.name like '%");
			sb.append(name);
			sb.append("%' ");
			
			sbc.append(" and u.name like '%");
			sbc.append(name);
			sbc.append("%' ");
		}
		if(StringUtils.isNotBlank(permissions)){
			sb.append(" and u.authority like '%");
			sb.append(permissions.trim());
			sb.append("%' ");
			
			sbc.append(" and u.authority like '%");
			sbc.append(permissions.trim());
			sbc.append("%' ");
		}
		 
		sb.append(" limit ");
		sb.append(pageable.getPageNumber()*pageable.getPageSize());
		sb.append(",");
		sb.append(pageable.getPageSize());
		
		Query query = entityManager.createNativeQuery(sb.toString(), Role.class);
		List<Role> rolelist = query.getResultList();
		Query queryCount = entityManager.createNativeQuery(sbc.toString());
		BigInteger totalcount =	(BigInteger)queryCount.getResultList().get(0);
		Pager pager =  new Pager(pageable.getPageSize(),pageable.getPageNumber(),totalcount.longValue(),rolelist);
		
		entityManager.close();
		return pager;
	}
	
	public List<Role> listRole(){
		StringBuffer sb = new StringBuffer("select * from auths_role u where 1=1 ");
		Query query = entityManager.createNativeQuery(sb.toString(), Role.class);
		List<Role> rolelist = query.getResultList();
		entityManager.close();
		return rolelist;
	}
	
	/**
	 * 通过id获取角色
	 * 
	 * @param id
	 * @return
	 */
	public Role getRoleById(String id){
		return roleDao.findOne(id);
		
	}
	
}
