
package com.isoftstone.tyw.repository.info;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.info.Bookmark;
import com.isoftstone.tyw.entity.info.LikeCount;
import com.isoftstone.tyw.entity.info.SiteRecord;

public interface SiteRecordDao extends PagingAndSortingRepository<SiteRecord, String>, JpaSpecificationExecutor<SiteRecord>{
  
    @Modifying
    @Query("delete from SiteRecord sre where sre.id =?1")
    public void delSiteRecord(String id) ;
    
    Page<SiteRecord> findByUid(String uid,Pageable pageable);
    
    SiteRecord findById(String id);
 }
