package com.isoftstone.tyw.repository.info;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.FilesPage;

public interface FilesPageNumDao extends PagingAndSortingRepository<FilesPage, String>{
	
	
	@Query(nativeQuery=true,value="select * from info_files_page where page_num=1 and file_id=:fileId") 
	FilesPage findFilePageByFileId(@Param("fileId")String fileId);
	
	@Query(nativeQuery=true,value="select * from info_files_page where file_id=:fileId") 
	List<FilesPage> findFilePageListByFileId(@Param("fileId")String fileId);
	
	@Query(nativeQuery=true, value="SELECT COUNT(*) FROM info_files_page WHERE file_id = :fileId order by page_num asc")
	public BigInteger findAllTotal(@Param("fileId")String fileId) ;
	
	@Query(nativeQuery=true, value="SELECT url FROM info_files_page WHERE file_id = :fileId and page_num=:pageNum")
	public String findFileUrl(@Param("fileId")String fileId,@Param("pageNum")int pageNum) ;
	
	@Query(nativeQuery=true, value="SELECT page_num FROM info_files_page WHERE url = :url")
	public int findNumByUrl(@Param("url")String url) ;
	
	@Query(nativeQuery=true, value="SELECT file_id FROM info_files_page WHERE url = :url")
	public String findIdByUrl(@Param("url")String url) ;
	
    @Modifying
    @Query(nativeQuery=true, value="DELETE FROM info_files_page WHERE file_id = :fileId")
    public void deleteFilesPageByFileId(@Param("fileId")String fileId);
}
