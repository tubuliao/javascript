package com.isoftstone.tyw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.DataContrast;
import com.isoftstone.tyw.repository.info.DataContrastDao;

@Component
@Transactional(readOnly=true)
public class DataContrastService {

	@Autowired
	private DataContrastDao dataContrastDao;
	
	public List<DataContrast> getDataListFromTywadb() {
		return dataContrastDao.findDataListFromTywadb();
	}
	
	public List<DataContrast> getDataListFromTywdb() {
		return dataContrastDao.findDataListFromTywdb();
	}
	
	public List<DataContrast> getDataListByDifference() {
		return dataContrastDao.findDataListByDifference();
	}
}
