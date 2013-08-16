
package com.isoftstone.tyw.repository.info;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.info.HotWords;

public interface HotWordsDao extends PagingAndSortingRepository<HotWords, String>, JpaSpecificationExecutor<HotWords>{
	
    /**
     * 获取热词列表
     * 
     * @param startDate
     * @param endDate
     * @param pageable
     * @return
     */
    Page<HotWords>  findByCreateDateBetweenOrderByNumDesc (Date startDate,Date endDate,Pageable pageable);
    
}
