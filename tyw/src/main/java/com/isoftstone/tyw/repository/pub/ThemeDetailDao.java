package com.isoftstone.tyw.repository.pub;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.pub.ThemeDetail;

/**
 * 专题详细dao
 * @author zhaowenli
 */
public interface ThemeDetailDao extends PagingAndSortingRepository<ThemeDetail, String>, JpaSpecificationExecutor<ThemeDetail>{

}
