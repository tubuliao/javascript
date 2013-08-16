package com.isoftstone.tyw.repository.auths;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.auths.Educational;

public interface EducationalDao extends PagingAndSortingRepository<Educational, String>{

	List<Educational> findByAdditionalId(String additionalId);
	
	@Query(nativeQuery=true, value="SELECT * FROM auths_educational WHERE additional_id = :userId ORDER BY id DESC limit 0,1")
	Educational findByAdditionalId1(@Param("userId")String userId);
	
}
