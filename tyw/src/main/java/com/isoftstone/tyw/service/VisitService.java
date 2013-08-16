package com.isoftstone.tyw.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.auths.Visit;
import com.isoftstone.tyw.repository.auths.VisitDao;

/**
 * 用户最近浏览类
 * 
 * @author 王锐
 */
@Component
@Transactional(readOnly=true)
public class VisitService {
	@Autowired
	VisitDao visitDao;
	/**
	 * 分页查询当前页
	 * 
	 */
	@Transactional(readOnly = false)
	public List<Visit> getVisitSet(String userId, Date visitDate, Integer pageIndex, Integer items_per_page) {
		return visitDao.findVisitSet(userId, visitDate, pageIndex, items_per_page) ;
	}
	/**
	 * 分页获得总数
	 *
	 */
	@Transactional(readOnly = false)
	public BigInteger getVisitTotal(String userId, Date visitDate) {
		return visitDao.findAllTotal(userId,visitDate) ;
	}
	
	/**
	 * 保存最近访问信息
	 * 
	 */
	 
	@Transactional(readOnly = false)
	public Visit saveVisit(Visit visit) {
		return visitDao.save(visit);
	}
	
	/**
	 * 根据用户id查找最近访问信息
	 * 
	 */
	public List<Visit> getByUserId(String userId){
		return visitDao.findByUserId(userId);
	}
	
	public String getOneUrl(String userId, String visitId){
		return visitDao.findOneUrl(userId, visitId);
	}
	
	/**
	 * 更改最近访问时间
	 * 
	 */
	@Transactional(readOnly = false)
	public boolean modifyVisit(Date visitDate, String userId, String visitId){
		return visitDao.modifyVisit(visitDate, userId, visitId)>0;
	}
	
}
