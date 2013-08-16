package com.isoftstone.tyw.repository.auths;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.auths.Agent;

public interface ChannelDao extends PagingAndSortingRepository<Agent, String>{
	Agent findById(String id);
	
	@Query(nativeQuery=true,value="select round(SUM(pay_amount)) from licenseview where agent_id= :agentId") 
	public Double getTotalMoney(@Param("agentId")String agentId);
}
