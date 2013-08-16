package com.isoftstone.tyw.repository.info;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.PowerpointPage;

public interface PowerpointPageDao extends PagingAndSortingRepository<PowerpointPage,String>,JpaSpecificationExecutor<PowerpointPage>{
     @Query(nativeQuery=true,value="select * from info_powerpoint_page ipp where ipp.id=:id and ipp.page_no=:pno")
    public PowerpointPage getPptPage(@Param("id")String id,@Param("pno")int pno);
     
     @Query("select ipp from PowerpointPage ipp where ipp.pid=:id order by pageNo")
     Page<PowerpointPage> getPptPageById(@Param("id")String id,Pageable pageable);
     
     @Query(nativeQuery=true,value="select * from info_powerpoint_page where pid=:pid")
     List<PowerpointPage> getPptPageList(@Param("pid")String pid);
     
     @Modifying
 	 @Query(nativeQuery=true,value="delete from info_powerpoint_page where pid=:pid")
 	 public void deletePowerpointPage(@Param("pid")String pid);
     
}
