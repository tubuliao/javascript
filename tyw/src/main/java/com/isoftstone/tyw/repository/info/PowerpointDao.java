
package com.isoftstone.tyw.repository.info;


import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Powerpoint;

public interface PowerpointDao extends PagingAndSortingRepository<Powerpoint, String>, JpaSpecificationExecutor<Powerpoint>{
	@Modifying
	@Query(nativeQuery=true,value="insert into info_powerpoint(id,urls,content)value(:id,:urls,:content)")
	public int insertPowerpoint(@Param("id")String id,@Param("urls")String url,@Param("content")String content);
	@Modifying
	@Query(nativeQuery=true,value="delete from info_powerpoint where id=:id")
	public void deletePowerpoint(@Param("id")String id);
	
	@Query(nativeQuery=true,value="select * from info_base base ,info_powerpoint powerpoint where powerpoint.id=base.id and powerpoint.id=:id")
	Powerpoint findPowerpoint(@Param("id")String id);
}
