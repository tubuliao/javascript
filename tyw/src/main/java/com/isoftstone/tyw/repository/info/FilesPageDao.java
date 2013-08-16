package com.isoftstone.tyw.repository.info;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.FilesPageCount;
public interface FilesPageDao extends PagingAndSortingRepository<FilesPageCount, String>, JpaSpecificationExecutor<FilesPageCount>{
    @Query(nativeQuery=true,value="call getContentPage(?1,@pageCount)")
    public String findContentPage(String fileId);
    
     @Query(nativeQuery=true,value="select id as id,url as url,substr(content,:pageStart,:pageEnd) as content from info_files where  id = :id")
     public FilesPageCount findContent(@Param("pageStart")int pageStart,@Param("pageEnd")int pageEnd,@Param("id")String id);
     
}
