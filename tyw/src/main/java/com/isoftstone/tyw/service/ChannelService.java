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



import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.auths.Agent;
import com.isoftstone.tyw.entity.pub.License;
import com.isoftstone.tyw.entity.pub.LicenseView;
import com.isoftstone.tyw.repository.auths.ChannelDao;
import com.isoftstone.tyw.repository.pub.LicenseViewDao;

/**
 * 账户管理类
 * 
 * @author zhangyq
 */
@Component
@Transactional(readOnly=true)
public class ChannelService {
	
	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private LicenseViewDao licenseViewDao;
	
	public Agent getById(String id){
		return channelDao.findById(id);
	}
	
	/**
	 * 保存渠道商信息
	 * 
	 * @param agent
	 * @return
	 */
	 
	@Transactional(readOnly = false)
	public Agent saveAgent(Agent agent) {
		return channelDao.save(agent);
	}
	
	/**
	 * 查询渠道商对应的项目组的交费情况
	 */
	@Transactional(readOnly = false)
	public Page<LicenseView> listLicenseView(String agentId,Date createDate,Date activateDate,String batchCode,String licenseStatus,String proName,Pageable pageable){
		return licenseViewDao.findAll(this.getWhereClause(agentId,createDate,activateDate,batchCode,licenseStatus,proName),pageable);
	}
	
	/**
	 * 条件查询
	 */
	public Specification<LicenseView> getWhereClause
	(final String agentId,final Date createDate,final Date activateDate,final String batchCode,final String licenseStatus,final String proName){
		return new Specification<LicenseView>(){
			@Override
			public Predicate toPredicate(Root<LicenseView> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				 
//				query.orderBy(cb.desc(root.get("createDate")));	
				if(agentId!=null&&!"".equals(agentId)){
					predicate.getExpressions().add(cb.equal(root.<String>get("agentId"), agentId));
				}
				if(createDate!=null&&!"".equals(createDate)){
					Date createStart = createDate;
					Date createEnd = new Date(createStart.getTime()+86400000);
					predicate.getExpressions().add(cb.between(root.<Date>get("createDate"), createStart, createEnd));
				}
				if(activateDate!=null&&!"".equals(activateDate)){
					Date activateStart = activateDate;
					Date activateEnd = new Date(activateStart.getTime()+86400000);
					predicate.getExpressions().add(cb.between(root.<Date>get("activateDate"), activateStart, activateEnd));
				}
				if(batchCode!=null&&!"".equals(batchCode)){
					predicate.getExpressions().add(cb.equal(root.<String>get("batchCode"), batchCode));
				}
				if(licenseStatus!=null && !licenseStatus.equals("")){
					if(licenseStatus.equals("2")){
						predicate.getExpressions().add(cb.equal(root.<Integer>get("licenseStatus"), licenseStatus));
					}else{
						predicate.getExpressions().add(cb.notEqual(root.<Integer>get("licenseStatus"), 2));
					}
				}
				if(proName!=null&&!"".equals(proName)){
					predicate.getExpressions().add(cb.like(root.<String>get("proName"), "%"+proName+"%"));
				}
				return predicate;
			}
		};
	}
	
	/**
	 * 查询渠道商对应的项目组的交费总金额
	 */
	public Double getAgentTotalMoney(String agentId){
		return channelDao.getTotalMoney(agentId);
	}
}
