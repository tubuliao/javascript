package com.isoftstone.tyw.service;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.FileModule;
import com.isoftstone.tyw.entity.info.Powerpoint;
import com.isoftstone.tyw.entity.info.PowerpointPage;
import com.isoftstone.tyw.entity.info.PptModule;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.repository.info.BaseBaseDao;
import com.isoftstone.tyw.repository.info.BaseDao;
import com.isoftstone.tyw.repository.info.PowerpointDao;
import com.isoftstone.tyw.repository.info.PowerpointPageDao;

@Component
@Transactional(readOnly = true)
public class PowerpointService {

	@Autowired
	private PowerpointDao powerpointDao;
	@Autowired
	private BaseBaseDao baseBaseDao;
	@Autowired
	private BaseDao baseDao;
	@Autowired
	private PowerpointPageDao powerpointPageDao;
	@PersistenceContext
	public EntityManager entityManager;

	@Transactional(readOnly = false)
	public Powerpoint savePowerpoint(Powerpoint powerpoint) {
		return powerpointDao.save(powerpoint);
	}
	
	/**
	 * 接口调用保存PPT
	 * @param powerpoint
	 * @return
	 */
	@Transactional(readOnly = false)
	public int save(Powerpoint powerpoint) {
		if (powerpoint.getBegincreateDate() != null) {
			baseDao.insertBase(powerpoint.getId(), powerpoint.getTitle(),
					powerpoint.getInfoType(), powerpoint.getInsertId(),
					powerpoint.getInsertName(), powerpoint.getSource(),
					powerpoint.getShortTitle(), powerpoint.getBegincreateDate());
		} else {
			baseDao.insertBase(powerpoint.getId(), powerpoint.getTitle(),
					powerpoint.getInfoType(), powerpoint.getInsertId(),
					powerpoint.getInsertName(), powerpoint.getSource(),
					powerpoint.getShortTitle());
		}
		for (Tag tag : powerpoint.getTags()) {
			baseDao.insertBaseTag(powerpoint.getId(), tag.getId());
		}
		return powerpointDao.insertPowerpoint(powerpoint.getId(),
				powerpoint.getUrls(), powerpoint.getContent());
	}

	//删除PPT
	@Transactional(readOnly = false)
	public void deletePowerpoint(String id) {
//		baseDao.deleteBaseTag(id);
//		powerpointDao.deletePowerpoint(id);
//		baseDao.deleteBase(id);
//		powerpointPageDao.deletePowerpointPage(id);
		powerpointDao.delete(id);
	}

	public Powerpoint getPptById(String id) {
		return powerpointDao.findOne(id);
	}

	@Transactional(readOnly = true)
	public void deletePpt(String id) {
		powerpointDao.deletePowerpoint(id);
	}

	public PowerpointPage getPptPage(String id, int pageNum) {
		return powerpointPageDao.getPptPage(id, pageNum);
	}

	public Page<PowerpointPage> getPptPageById(String id, Pageable pageable) {
		return powerpointPageDao.getPptPageById(id, pageable);
	}
	
	/**
	 * ppt列表查询
	 * @param tagId
	 * @param title
	 * @param source
	 * @param insertName
	 * @param state
	 * @param infoType
	 * @param synStatus
	 * @param pageable
	 * @return
	 */
	public List<PptModule> listFileBaseRows(String tagId, String title, String source, String insertName, String state, Integer infoType, String synStatus, String level, String begincreateDate,Pageable pageable) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT DISTINCT b.id, b.title, b.create_date, s.urls, b.source, b.insert_name, b.modify_date, b.state,b.weighing FROM info_powerpoint s LEFT OUTER JOIN info_base b ON b.id = s.id");
		
		if(tagId == null || "".equals(tagId)) {
			sb.append(" WHERE 1 = 1");
		}
		// 标签
		if(tagId != null && !"".equals(tagId)) {
			sb.append(" LEFT OUTER JOIN info_base_tag bt ON bt.base_id = b.id WHERE 1 = 1 AND bt.tag_id = '" + tagId + "'");
		}
		// 数据类型
		if(infoType != null && !"".equals(infoType)) {
			sb.append(" AND b.info_type =" + infoType);
		}
		// 标题
		if(title != null && !"".equals(title)) {
			sb.append(" AND b.title LIKE '%" + title + "%' ");
		}
		// 来源
		if(source != null && !"".equals(source)) {
			sb.append(" AND b.source LIKE '%" + source + "%' ");
		}
		// 录入人
		if(insertName != null && !"".equals(insertName)) {
			sb.append(" AND b.insert_name LIKE '%" + insertName + "%' ");
		}
		// 审批状态
		if(state != null && !"".equals(state) && !"10000".equals(state)) {
			sb.append(" AND b.state =" + state);
		}
		//同步状态（已同步）
		if(synStatus != null && !"".equals(synStatus) && synStatus.equals("1")){
			sb.append(" AND s.urls != '' ");
		}
		//同步状态（未同步）
		if(synStatus != null && !"".equals(synStatus) && synStatus.equals("2")){
			sb.append(" AND s.urls = '' ");
		}
		// 发布日期
		if(StringUtils.isNotBlank(begincreateDate)) {
			sb.append(" AND b.begincreate_date = '" + begincreateDate + "'");
			
		}
		// 知识等级
		if(level != null && !"10000".equals(level)) {
			sb.append(" AND b.weighing = " + level);
			
		}
		// 分页
		sb.append(" limit " + pageable.getPageNumber()*pageable.getPageSize() + "," + pageable.getPageSize());
		
		Query query = entityManager.createNativeQuery(sb.toString(), PptModule.class);
		
		List<PptModule> result = query.getResultList();   
		
		entityManager.close();
        return result;
	}
	
	/**
	 * ppt列表查询（count）
	 * @param tagId
	 * @param title
	 * @param source
	 * @param insertName
	 * @param state
	 * @param synStatus
	 * @param infoType
	 * @return
	 */
	public BigInteger listFileBaseTotal(String tagId, String title, String source, String insertName, String state, String synStatus, String level, String begincreateDate,Integer infoType) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT DISTINCT COUNT(s.id) FROM info_powerpoint s LEFT OUTER JOIN info_base b ON b.id = s.id");
		
		if(tagId == null || "".equals(tagId)) {
			sb.append(" WHERE 1 = 1");
		}
		// 标签
		if(tagId != null && !"".equals(tagId)) {
			sb.append(" LEFT OUTER JOIN info_base_tag bt ON bt.base_id = b.id WHERE 1 = 1 AND bt.tag_id = '" + tagId + "'");
		}
		// 数据类型
		if(infoType != null && !"".equals(infoType)) {
			sb.append(" AND b.info_type =" + infoType);
		}
		// 标题
		if(title != null && !"".equals(title)) {
			sb.append(" AND b.title LIKE '%" + title + "%' ");
		}
		// 来源
		if(source != null && !"".equals(source)) {
			sb.append(" AND b.source LIKE '%" + source + "%' ");
		}
		// 录入人
		if(insertName != null && !"".equals(insertName)) {
			sb.append(" AND b.insert_name LIKE '%" + insertName + "%' ");
		}
		// 审批状态
		if(state != null && !"".equals(state) && !"10000".equals(state)) {
			sb.append(" AND b.state =" + state);
		}
		//同步状态（已同步）
		if(synStatus != null && !"".equals(synStatus) && synStatus.equals("1")){
			sb.append(" AND s.urls != '' ");
		}
		//同步状态（未同步）
		if(synStatus != null && !"".equals(synStatus) && synStatus.equals("2")){
			sb.append(" AND s.urls = '' ");
		}
		// 发布日期
		if(StringUtils.isNotBlank(begincreateDate)) {
			sb.append(" AND b.begincreate_date = '" + begincreateDate + "'");
			
		}
		// 知识等级
		if(level != null && !"10000".equals(level)) {
			sb.append(" AND b.weighing = " + level);
			
		}
		Query query = entityManager.createNativeQuery(sb.toString());
		
		BigInteger total = (BigInteger)query.getResultList().get(0);   
		
		entityManager.close();
        return total;
	}
}
