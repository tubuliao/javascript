package com.isoftstone.tyw.repository.auths;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.auths.Occupational;

public interface OccupationalDao extends PagingAndSortingRepository<Occupational, String>{

	List<Occupational> findByAdditionalId(String additionalId);
	
	@Query(nativeQuery=true, value="SELECT * FROM auths_occupational WHERE additional_id = :userId ORDER BY id DESC limit 0,1")
	Occupational findByAdditionalId1(@Param("userId")String userId);
	
}
