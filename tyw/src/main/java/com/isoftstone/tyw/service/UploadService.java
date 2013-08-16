package com.isoftstone.tyw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.pub.Upload;
import com.isoftstone.tyw.repository.pub.UploadDao;

@Component
@Transactional(readOnly=true)
public class UploadService {
	@Autowired
	private UploadDao uploadDao;
	
	/**
	 * 保存投稿信息
	 * 
	 * @param upload
	 * @return
	 */
	 
	@Transactional(readOnly = false)
	public Upload saveUpload(Upload upload) {
		return uploadDao.save(upload);
	}
	
	public List<Upload> getByUploadName(String name){
		return uploadDao.findByUploadName(name);
	}
	
	/**
	 * 删除投稿信息
	 * 
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void deleteUpload(String id){
		uploadDao.delete(id);
	}
}
