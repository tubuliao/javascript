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

import com.isoftstone.tyw.entity.pub.Type;
import com.isoftstone.tyw.repository.pub.TypeDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 枚举
 * 
 * @author ray
 */
@Component
@Transactional(readOnly=true)
public class TypeEnumService {
	
	@Autowired
	private TypeDao typeDao;
		
	/**
	 * 分页查询枚举
	 * @param pageable
	 * @return
	 */
	public Page<Type> listTypeEnum(Pageable pageable){
		return typeDao.findAll(pageable);
	}
	
	public List<Type> listTypeEnum(String typeValue){
		return typeDao.getTypeByTypeId(typeValue);
	}
}
