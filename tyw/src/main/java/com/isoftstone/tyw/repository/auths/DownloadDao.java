package com.isoftstone.tyw.repository.auths;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.auths.Download;

public interface DownloadDao extends PagingAndSortingRepository<Download, Integer>{
	
}
