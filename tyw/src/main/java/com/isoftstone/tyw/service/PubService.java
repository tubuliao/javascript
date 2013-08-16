package com.isoftstone.tyw.service;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.auths.Role;
import com.isoftstone.tyw.entity.pub.Announce;
import com.isoftstone.tyw.entity.pub.Comments;
import com.isoftstone.tyw.entity.pub.LicensePayment;
import com.isoftstone.tyw.entity.pub.Province;
import com.isoftstone.tyw.repository.pub.AnnounceDao;
import com.isoftstone.tyw.repository.pub.CommentsDao;
import com.isoftstone.tyw.repository.pub.ProvinceDao;
import com.isoftstone.tyw.repository.pub.UploadDao;
import com.isoftstone.tyw.util.Pager;

@Component
@Transactional(readOnly=true)
public class PubService {
	
	@Autowired
	private AnnounceDao announceDao;
	
	@Autowired
	private CommentsDao commentsDao;
	
	@Autowired
	private UploadDao uploadDao;
	
	@Autowired
	private ProvinceDao provinceDao ;
	
	@PersistenceContext
	 public EntityManager entityManager;

	/**
	 * 查询所有的省份列表
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<Province> getProvinceList() {
		List<String> properties = new ArrayList<String>();
		properties.add("areaCode");
		return (List<Province>)provinceDao.findAll(new Sort(Sort.Order.create(Sort.Direction.ASC, (Iterable<String>)properties)));
	}
	
	/**
	 * 公告操作
	 * @param 
	 * @return
	 */
	
	public Pager listAllAnnounce(String title,String issuedBy,Pageable pageable){

		StringBuffer sb = new StringBuffer("select * from pub_announce u where 1=1 ");
		StringBuffer sbc = new StringBuffer("select count(1) from pub_announce u where 1=1 ");
		if(StringUtils.isNotBlank(title)){
			sb.append(" and u.title like '%");
			sb.append(title.trim());
			sb.append("%' ");
			
			sbc.append(" and u.title like '%");
			sbc.append(title.trim());
			sbc.append("%' ");
		}
		if(StringUtils.isNotBlank(issuedBy)){
			sb.append(" and u.issued_by like '%");
			sb.append(issuedBy.trim());
			sb.append("%' ");
			
			sbc.append(" and u.issued_by like '%");
			sbc.append(issuedBy.trim());
			sbc.append("%' ");
		}
		 
		sb.append(" order by create_date desc limit ");
		sb.append(pageable.getPageNumber()*pageable.getPageSize());
		sb.append(",");
		sb.append(pageable.getPageSize());
		
		Query query = entityManager.createNativeQuery(sb.toString(), Announce.class);
		List<Announce> announcelist = query.getResultList();
		Query queryCount = entityManager.createNativeQuery(sbc.toString());
		BigInteger totalcount =	(BigInteger)queryCount.getResultList().get(0);
		Pager pager =  new Pager(pageable.getPageSize(),pageable.getPageNumber(),totalcount.longValue(),announcelist);
		entityManager.close();
		return pager;
	}
	
	@Transactional(readOnly=false)
	public void deleteAnnounceById(String id){
		announceDao.delete(id);
		
	}
	
	@Transactional(readOnly=false)
	public void saveAnnounce(Announce announce){
		announceDao.save(announce);
		
	}
	
	public Announce getAnnounceById(String id){
		return announceDao.findOne(id);
		
	}
	
	public Page<Comments> getByInfoBaseIdAndAuthsUserId(String infoBaseId,String authsUserId,Pageable pageable){
		return commentsDao.findByInfoBaseIdAndAuthsUserId(infoBaseId, authsUserId, pageable);
	}
	
	@Transactional(readOnly=false)
	public void saveComments(Comments comments){
		commentsDao.save(comments);
		
	}
	
	@Transactional(readOnly = false)
	public List<Announce> announceTotalListFirst(){
		return announceDao.announceTotalListFirst();
	}
	
	@Transactional(readOnly = false)
	public List<Announce> announceTotalListOther(){
		return announceDao.announceTotalListOther();
	}
	
	@Transactional(readOnly = false)
	public Announce getContentById(String anId){
		return announceDao.getContentById(anId);
	}
}
