package com.isoftstone.tyw.repository.pub;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.pub.LicenseBatch;

public interface LicenseBatchDao extends PagingAndSortingRepository<LicenseBatch, String>, JpaSpecificationExecutor<LicenseBatch>{

}
