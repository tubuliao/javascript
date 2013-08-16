package com.isoftstone.tyw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.isoftstone.tyw.entity.info.Classification;
import com.isoftstone.tyw.repository.info.ClassificationDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Component
@Transactional(readOnly = true)
public class ClassificationService {

	@Autowired
	private ClassificationDao classificationDao;
	
	@Transactional(readOnly=false) 
	public List<Classification> getClassificationListByUserId(String userId) {
		return classificationDao.findAllByUserId(userId) ;
	}
	
	public Page<Classification> findByUserId(String userId,Pageable pageable) {
        return classificationDao.findByUserId(userId,pageable) ;
    }
	
	@Transactional(readOnly=false) 
	public void addNewClassification(Classification c) {
		classificationDao.save(c) ;
	}
	
	@Transactional(readOnly=false)
	public Classification getClassificationById(String id) {
		return classificationDao.findOne(id) ;
	}
	
	@Transactional(readOnly=false) 
	public void deleteClassificationById(String id) {
		classificationDao.delete(id) ;
	}
	
	@Transactional(readOnly=false)
	public Classification getOneByUserIdAndTitle(String userId, String title) {
		return classificationDao.findOneByUserIdAndTitle(userId, title);
	}
}
