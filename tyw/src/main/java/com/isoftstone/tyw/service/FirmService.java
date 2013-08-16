package com.isoftstone.tyw.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.auths.Firm;
import com.isoftstone.tyw.entity.auths.User;
import com.isoftstone.tyw.repository.auths.FirmDao;

@Component
@Transactional(readOnly = true)
public class FirmService {
	@Autowired
	private FirmDao firmDao;
	
	
	
	/**
	 * 保存企业信息
	 * 
	 * @param firm
	 * @return
	 */
	 
	@Transactional(readOnly = false)
	public Firm saveFirm(Firm firm) {
		return firmDao.save(firm);
	}
	
	/**
	 * 根据渠道商ID查找企业信息
	 * @param 
	 * @return
	 */
	public List<Firm> getFirmByAgentId(String id){
		return firmDao.findFirmByAgentId(id);
	}
	
	/**
	 * 根据企业ID删除企业信息
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false)
	public void deleteFirm(String id){
		firmDao.delete(id);
		
	}
	
}
