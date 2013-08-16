package com.isoftstone.tyw.repository.info;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Classification;

public interface ClassificationDao extends
		PagingAndSortingRepository<Classification, String>{

	@Query("from Classification classification where classification.userId = :userId")
	public List<Classification> findAllByUserId(@Param("userId")String userId) ;
	
    public Page<Classification> findByUserId(@Param("userId")String userId,Pageable pageable) ;
     
    @Query(nativeQuery=true, value="SELECT a.* FROM info_classification a WHERE a.user_id = :userId AND a.title = :title")
    public Classification findOneByUserIdAndTitle(@Param("userId")String userId, @Param("title")String title);
}
