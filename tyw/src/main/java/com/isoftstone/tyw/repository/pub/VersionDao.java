package com.isoftstone.tyw.repository.pub;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.pub.Version;

public interface VersionDao extends PagingAndSortingRepository<Version,String>{
	
	@Query(nativeQuery=true,value="select * from tool_version where tools_type=:type")
	Version getVersionByType(@Param("type")int type);
}
