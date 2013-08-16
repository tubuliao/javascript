package com.isoftstone.tyw.repository.info;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.FormExt;

public interface FormExtDao extends PagingAndSortingRepository<FormExt,String> ,JpaSpecificationExecutor<FormExt>{
	Page<FormExt> findByModifyDateGreaterThan(Date modifyDate, Pageable pageable);
	
	
	@Query("select formExt from FormExt formExt, BaseBase baseBase where formExt.id=baseBase.infoSlaveId and baseBase.infoPrimaryId=:primary")
	Page<FormExt> findByPrimary(@Param("primary")String primary,Pageable pageable);
	
	@Query("select formExt from FormExt formExt, BaseBase baseBase where formExt.id=baseBase.infoSlaveId and baseBase.infoPrimaryId=:primary")
	List<FormExt> findByPrimary(@Param("primary")String primary);
	
}
