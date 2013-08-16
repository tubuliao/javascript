
package com.isoftstone.tyw.repository.info;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Video;

public interface VideoDao extends PagingAndSortingRepository<Video, String>{
	
	Page<Video> findByModifyDateGreaterThan(Date modifyDate, Pageable pageable);
	
	@Query(value="select video from Video video where video.title = :name")
	public Video findByName(@Param("name")String name);
	
	@Modifying
	@Query(nativeQuery=true, value="UPDATE info_video v SET v.url = '' WHERE v.id = :id ")
	public void updateUrl(@Param("id")String id) ;
	
	@Query(nativeQuery=true,value="select * from info_base base ,info_video video where video.id=base.id and video.id=:id")
	Video findVideo(@Param("id")String id);
}
