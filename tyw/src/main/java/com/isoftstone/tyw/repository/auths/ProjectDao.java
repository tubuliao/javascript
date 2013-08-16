package com.isoftstone.tyw.repository.auths;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.auths.Project;

public interface ProjectDao extends PagingAndSortingRepository<Project, String>{

	List<Project> findByAdditionalId(String additionalId);
	
	@Query(nativeQuery=true, value="SELECT * FROM auths_project WHERE additional_id = :userId ORDER BY id DESC limit 0,1")
    Project findByAdditionalId1(@Param("userId")String userId);
	
}
