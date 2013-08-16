package com.isoftstone.tyw.repository.auths;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.auths.Firm;

public interface FirmDao extends PagingAndSortingRepository<Firm, String>{
	List<Firm> findFirmByAgentId(String agentId);
}
