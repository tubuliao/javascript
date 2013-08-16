package com.isoftstone.tyw.repository.info;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.info.FormView;

public interface FormViewDao extends PagingAndSortingRepository<FormView,String> ,JpaSpecificationExecutor<FormView>{
	 
}
