package com.isoftstone.tyw.repository.pub;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.pub.Type;

public interface TypeDao extends PagingAndSortingRepository<Type, String>{
	
	@Query(nativeQuery=true,value="select * from pub_type where type_id=:typeId")
	List<Type>  getTypeByTypeId(@Param("typeId")String typeId); 
}
