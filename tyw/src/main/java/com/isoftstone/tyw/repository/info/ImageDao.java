
package com.isoftstone.tyw.repository.info;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Image;

public interface ImageDao extends PagingAndSortingRepository<Image, String>, JpaSpecificationExecutor<Image>{
	
	Page<Image> findByModifyDateGreaterThan(Date modifyDate, Pageable pageable);
	
	@Query(value="select image from Image image where image.title = :name")
	public Image findByName(@Param("name")String name);
	
	Page<Image> findByInsertId(String insertId, Pageable pageable);
	
	Page<Image> findByCreateDateGreaterThanAndInsertId(Date modifyDate, String insertId, Pageable pageable);
	
	@Query(nativeQuery=true,value="select * from info_base base ,info_image image where image.id=base.id and image.id=:id")
	Image findImage(@Param("id")String id);

	
}
