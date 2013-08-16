package com.isoftstone.tyw.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.FilesPage;
import com.isoftstone.tyw.repository.info.BaseDao;
import com.isoftstone.tyw.repository.info.FilesPageNumDao;
import com.isoftstone.tyw.repository.info.TagDao;

@Component
@Transactional(readOnly = true)
public class FilesPageService {
	@Autowired
	private FilesPageNumDao filesPageNumDao;
	
	
	 /**
	 * 根据文件id查询第一页
	 *
	 */
	public FilesPage getFilePageByFileId(String fileId){
		return filesPageNumDao.findFilePageByFileId(fileId);
	}
	
	public List<FilesPage> findFilePageListByFileId(String fileId){
		return filesPageNumDao.findFilePageListByFileId(fileId);
	}
	
	/**
	 * 分页获得总数
	 *
	 */
	@Transactional(readOnly = false)
	public BigInteger getBigFileTotal(String fileId) {
		return filesPageNumDao.findAllTotal(fileId) ;
	}
	
	/**
	 * 根据文章id和页码查询文件在服务器上的路径
	 *
	 */
	public String getFileUrl(String fileId,int pageNum){
		return filesPageNumDao.findFileUrl(fileId, pageNum);
	}
	
	@Transactional(readOnly=false)
	public FilesPage saveFilePage(FilesPage page){
		return filesPageNumDao.save(page);
	}
	
	/**
	 * 根据文件路径查询该文件的页码
	 *
	 */
	public int getPageNumByUrl(String url){
		return filesPageNumDao.findNumByUrl(url);
	}
	
	/**
	 * 根据文件路径查询文件id
	 *
	 */
	public String getIdByUrl(String url){
		return filesPageNumDao.findIdByUrl(url);
	}
	
	/**
	 * 删除指定文件id的所有文件
	 *
	 */
	public void deleteOne(String fileId) {
		filesPageNumDao.deleteFilesPageByFileId(fileId);
	}
}
