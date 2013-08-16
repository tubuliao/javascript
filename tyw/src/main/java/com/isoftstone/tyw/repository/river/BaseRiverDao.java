package com.isoftstone.tyw.repository.river;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.river.BaseRiver;

public interface BaseRiverDao  extends PagingAndSortingRepository<BaseRiver, String>, JpaSpecificationExecutor<Base>{
	
}
