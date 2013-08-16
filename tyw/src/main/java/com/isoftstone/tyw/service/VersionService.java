package com.isoftstone.tyw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isoftstone.tyw.entity.pub.Version;
import com.isoftstone.tyw.repository.pub.VersionDao;

@Component
public class VersionService {
	@Autowired
	VersionDao versionDao;

	/**
	 * 查询版本号
	 * @param toolsType
	 * @return
	 */
	public Version getVersionForTools(int toolsType){
		return versionDao.getVersionByType(toolsType);
	}
}
