package com.isoftstone.tyw.service;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.Powerpoint;
import com.isoftstone.tyw.entity.info.VideoModule;
import com.isoftstone.tyw.entity.pub.Suggest;
import com.isoftstone.tyw.repository.pub.SuggestDao;

/**
 * 留言管理类
 * 
 * @author wangrui
 */
@Component
@Transactional(readOnly=true)
public class SuggestService {
	
	@Autowired
	private SuggestDao suggestDao;
	@PersistenceContext
	public EntityManager entityManager;
	
	/**
	 * 保存意见
	 * @param suggest
	 * @return
	 */
	@Transactional(readOnly = false)
	public Suggest saveSuggest(Suggest suggest) {
		return suggestDao.save(suggest);
	}
	
	public Page<Suggest> listSuggest(Specification<Suggest> specification, Pageable pageable) {
		return suggestDao.findAll(pageable) ;
	}
	
	public Suggest getSuggestById(String id){
		return suggestDao.findOne(id);
	}
	
	/**
	 * 用户意见列表查询
	 * @param pageable
	 * @return
	 */
	public List<Suggest> listSuggestBaseRows(Pageable pageable) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT * FROM pub_suggest ");
		// 分页
		sb.append(" limit " + pageable.getPageNumber()*pageable.getPageSize() + "," + pageable.getPageSize());
		
		Query query = entityManager.createNativeQuery(sb.toString(), Suggest.class);
		
		List<Suggest> result = query.getResultList();   
		entityManager.close();
        return result;
	}
	
	/**
	 * 用户意见列表查询（总数）
	 * @return
	 */
	public BigInteger listSuggestBaseTotal() {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT COUNT(id) FROM pub_suggest");
		
		
		Query query = entityManager.createNativeQuery(sb.toString());
		
		BigInteger total = (BigInteger)query.getResultList().get(0);   
		entityManager.close();
        return total;
	}
	
}
