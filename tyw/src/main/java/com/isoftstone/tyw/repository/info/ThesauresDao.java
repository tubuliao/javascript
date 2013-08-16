package com.isoftstone.tyw.repository.info;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Thesaures;

/**
 * 
 * @author liuwei
 *
 */
public interface ThesauresDao extends
		PagingAndSortingRepository<Thesaures, String>,
		JpaSpecificationExecutor<Thesaures> {

	@Query(nativeQuery=true, value="SELECT * FROM info_thesaures WHERE name = :name")
	public Thesaures findOneByName(@Param("name")String name);
}
