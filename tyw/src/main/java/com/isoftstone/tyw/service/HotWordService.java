package com.isoftstone.tyw.service;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.HotWords;
import com.isoftstone.tyw.repository.info.HotWordsDao;
@Component
@Transactional(readOnly = true)
public class HotWordService {
    @Autowired
    private HotWordsDao hotWordDao;
    
    public Page<HotWords> searchHotWords(Date startDate,Date endDate,Pageable pageable){
       return hotWordDao.findByCreateDateBetweenOrderByNumDesc(startDate,endDate,pageable);
     }
}
