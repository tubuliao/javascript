package com.isoftstone.tyw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.PowerpointPage;
import com.isoftstone.tyw.repository.info.PowerpointPageDao;

@Component
@Transactional(readOnly=true)
public class PowerpointPageService {
	@Autowired
	private PowerpointPageDao powerpointPageDao;
	
	@Transactional(readOnly=false)
	public PowerpointPage savePowerpointPage(PowerpointPage powerPage){
		return powerpointPageDao.save(powerPage);
	}
	
	@Transactional(readOnly=false)
	public void deletePowerpointPage(String id){
		powerpointPageDao.delete(id);
	}
	

	public PowerpointPage getPowerpointPage(String id){
		return powerpointPageDao.findOne(id);
	}
	
	public List<PowerpointPage> getPptPageList(String id){
		return powerpointPageDao.getPptPageList(id);
	}
}
