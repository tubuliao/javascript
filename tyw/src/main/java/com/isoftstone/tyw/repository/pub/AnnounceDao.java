package com.isoftstone.tyw.repository.pub;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.pub.Announce;

public interface AnnounceDao extends PagingAndSortingRepository<Announce, String>{
	
	@Query(nativeQuery=true,value="select * from pub_announce order by create_date desc limit 0,2") 
	List<Announce> announceTotalListFirst();
	
	@Query(nativeQuery=true,value="select * from pub_announce order by create_date desc limit 2,8") 
	List<Announce> announceTotalListOther();
	
	@Query(nativeQuery=true,value="select * from pub_announce where id = :anId") 
	Announce getContentById(@Param("anId")String anId);
}
