package com.isoftstone.tyw.repository.info;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.BaseBase;

public interface BaseBaseDao  extends PagingAndSortingRepository<BaseBase, String>{
	@Modifying 
	@Query("delete from BaseBase baseBase where baseBase.infoPrimaryId =:id") 
	public int deleteBaseBaseByParimaryId(@Param("id")String id); 
	
	@Modifying 
	@Query("delete from BaseBase baseBase where baseBase.infoSlaveId =:id") 
	public int deleteBaseBaseBySlaveId(@Param("id")String id); 
	
}
