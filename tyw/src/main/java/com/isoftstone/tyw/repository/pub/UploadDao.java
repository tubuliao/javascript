package com.isoftstone.tyw.repository.pub;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.pub.Upload;

public interface UploadDao extends PagingAndSortingRepository<Upload,String>{

	Page<Upload> findByModifyDateGreaterThan(Date modifyDate, Pageable pageable);
	
	List<Upload> findByUploadName(String name);
}
