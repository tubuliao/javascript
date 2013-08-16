
package com.isoftstone.tyw.repository.info;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Files;

public interface FilesDao extends PagingAndSortingRepository<Files, String>{
	
	Page<Files> findByModifyDateGreaterThan(Date modifyDate, Pageable pageable);
	
	Page<Files> findByInsertId(String insertId, Pageable pageable);
	
	Page<Files> findByCreateDateGreaterThanAndInsertId(Date modifyDate, String insertId, Pageable pageable);

	
	@Query(value="select files from Files files where files.title = :name")
	//@Query(value="from Files files where :name like '%files.title%' ")
	public Files findByName(@Param("name")String name);
	

	@Modifying
	@Query(nativeQuery=true,value="insert into info_files(id,url,catalog)value(:id,:url,:catalog)")
	public int insertFile(@Param("id")String id,@Param("url")String url,@Param("catalog")String catalog);
	
	

	@Modifying
	@Query(nativeQuery=true,value="insert into info_files(id,url,catalog,total_pages)value(:id,:url,:catalog,:totalPages)")
	public int insertFile(@Param("id")String id,@Param("url")String url,@Param("catalog")String catalog,@Param("totalPages")Integer totalPages);

	
	@Modifying
	@Query(nativeQuery=true,value="delete from info_files where id=:id")
	public int deleteFile(@Param("id")String id);


	@Modifying
	@Query(value="update Files f set f.url = CONCAT(f.url, ';', :url) WHERE f.id = :id")
	public void modifyUrl(@Param("id")String id, @Param("url")String url);
	
	@Query(nativeQuery=true,value="select * from info_base base ,info_files files where files.id=base.id and files.id=:id")
	public Files findFile(@Param("id")String id);

}
