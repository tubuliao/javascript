package com.isoftstone.tyw.repository.biz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.biz.Discount;


public interface DiscountDao extends PagingAndSortingRepository<Discount, String>, JpaSpecificationExecutor<Discount>{
	
	List<Discount> findDiscountByAgentId(String agentId);
}
