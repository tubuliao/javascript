package com.isoftstone.tyw.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.News;
import com.isoftstone.tyw.entity.info.SiteRecord;
import com.isoftstone.tyw.repository.info.NewsDao;
import com.isoftstone.tyw.repository.info.SiteRecordDao;
@Component
@Transactional(readOnly = true)
public class SiteRecordService {
    @Autowired
    private SiteRecordDao siteRecordDao;
  
    
    @Transactional(readOnly = false)
    public SiteRecord save(SiteRecord siteRecord){
        return siteRecordDao.save(siteRecord);
    }
    
    @Transactional(readOnly = false)
    public void delSiteRecord(String id){
         siteRecordDao.delSiteRecord(id);
    }
    
     public Page<SiteRecord> findSiteRecord(String id,Pageable pageAble){
        return siteRecordDao.findByUid(id,pageAble);
    }
     public  SiteRecord findById(String id){
         return siteRecordDao.findById(id);
     }
}
