package com.isoftstone.tyw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.BaseBase;
import com.isoftstone.tyw.entity.info.FormExt;
import com.isoftstone.tyw.repository.info.BaseBaseDao;
import com.isoftstone.tyw.repository.info.FormExtDao;

@Component
@Transactional(readOnly = true)
public class FormExtService {
	
	@Autowired
	private FormExtDao formExtDao;
	@Autowired
	private BaseBaseDao baseBaseDao;
	
	@Transactional(readOnly = false)
	public FormExt saveFormExt(FormExt formext,String primary){
		FormExt formExt= formExtDao.save(formext);
		BaseBase bb= new BaseBase();
		bb.setInfoPrimaryId(primary);
		bb.setInfoSlaveId(formExt.getId());
		baseBaseDao.save(bb);
		return formExt;
	}
	
	@Transactional(readOnly = false)
	public FormExt saveFormExt(FormExt formext){
		FormExt formExt= formExtDao.save(formext);
		return formExt;
	}
	
	
	
	@Transactional(readOnly = false)
	public void deleteFormExt(String id){
		//先删除关系
		baseBaseDao.deleteBaseBaseBySlaveId(id);
		formExtDao.delete(id);
	}
	
	public Page<FormExt> listFormExt(Pageable pageable){
		return formExtDao.findAll(pageable);
	}
	
	public Page<FormExt> listFormExt(String primary,Pageable pageable){
		return formExtDao.findByPrimary(primary,pageable);
	}
	
	public FormExt getFormExtById(String id){
		return formExtDao.findOne(id);
	}
}
