
package com.isoftstone.tyw.repository.info;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.LikeCount;
import com.isoftstone.tyw.entity.pub.LicenseUser;

public interface LikeCountDao extends PagingAndSortingRepository<LikeCount, String>, JpaSpecificationExecutor<LikeCount>{
    @Query(nativeQuery=true,value="select count(*) as count from info_like_count likeCount where likeCount.resources_id=?1")
    public BigInteger searchCount(String id);
    
    @Query(nativeQuery=true,value="select * from info_like_count where resources_id = :fileId and uid = :userId") 
	 List<LikeCount> findExsitOneInfo(@Param("fileId")String fileId,@Param("userId")String userId);
}
