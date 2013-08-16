package com.isoftstone.tyw.repository.info;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.info.SourceView;

public interface SourceViewDao  extends PagingAndSortingRepository<SourceView, String>, JpaSpecificationExecutor<SourceView>{
	
 
}
