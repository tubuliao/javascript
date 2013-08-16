package com.isoftstone.tyw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.auths.Download;
import com.isoftstone.tyw.repository.auths.DownloadDao;

/**
 * 用户下载记录类
 * 
 * @author 王锐
 */
@Component
@Transactional(readOnly=true)
public class DownloadService {
	@Autowired
	DownloadDao downloadDao;
	
	/**
	 * 保存下载记录信息
	 * 
	 */
	@Transactional(readOnly = false)
	public Download saveDownload(Download download) {
		return downloadDao.save(download);
	}
}
