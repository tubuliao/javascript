package com.isoftstone.tyw.service;



import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.Image;
import com.isoftstone.tyw.entity.info.ImageModule;
import com.isoftstone.tyw.repository.info.BaseBaseDao;
import com.isoftstone.tyw.repository.info.ImageDao;

@Component
@Transactional(readOnly = true)
public class ImageService {
	
	@Autowired
	private ImageDao imageDao;
	@Autowired
	private BaseBaseDao baseBaseDao;
	
	@PersistenceContext
	public EntityManager entityManager;
	
	private static final String xmlConfig = "/META-INF/resource/image_mapping.xml" ;
	
	/**
	 * web----通过id获取视频
	 * 
	 * @param id
	 * @return
	 */
	public Image getImageById(String id){
		return imageDao.findOne(id);
	}
	
	/**
	 * 新增图片
	 * @param image
	 */
	@Transactional(readOnly=false)
	public Image saveOne(Image image) {
		return imageDao.save(image) ;
	}
	
	@Transactional(readOnly=false) 
	public void deleteOne(String id) {
		imageDao.delete(id) ;
	}
	
	
	public Page<Image> listImage(Pageable pageable,String insertId,String syncDate){
		try{
			if(StringUtils.isNotBlank(syncDate)){
				Date date = DateUtils.parseDate(syncDate, "yyyy-MM-dd hh:mm:ss");
				return imageDao.findByCreateDateGreaterThanAndInsertId(date, insertId, pageable);
			}else{
				return imageDao.findByInsertId(insertId, pageable);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	public Map<String, Object> listImageBaseRows(String tagId, String title, String source, String insertName, String state, Integer infoType, String begincreateDate, String level, Pageable pageable) {
		
		Map<String, Object> page = new HashMap<String, Object>();
		
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT DISTINCT b.id, b.title, b.source, b.insert_name, b.create_date, b.modify_date, b.state, b.weighing FROM info_image s LEFT OUTER JOIN info_base b ON b.id = s.id ");
		StringBuffer sbc = new StringBuffer("SELECT COUNT(DISTINCT s.id) FROM info_image s LEFT OUTER JOIN info_base b ON b.id = s.id");
		
		if(tagId == null || "".equals(tagId)) {
			sb.append(" WHERE 1 = 1");
			
			sbc.append(" WHERE 1 = 1");
		}
		// 标签
		if(tagId != null && !"".equals(tagId)) {
			sb.append(" LEFT OUTER JOIN info_base_tag bt ON bt.base_id = b.id WHERE 1 = 1 AND bt.tag_id = '" + tagId + "'");
			
			sbc.append(" LEFT OUTER JOIN info_base_tag bt ON bt.base_id = b.id WHERE 1 = 1 AND bt.tag_id = '" + tagId + "'");
		}
				
		// 标题
		if(title != null && !"".equals(title)) {
			sb.append(" AND b.title LIKE '%" + title + "%' ");
			
			sbc.append(" AND b.title LIKE '%" + title + "%' ");
		}
		// 来源
		if(source != null && !"".equals(source)) {
			sb.append(" AND b.source LIKE '%" + source + "%' ");
			
			sbc.append(" AND b.source LIKE '%" + source + "%' ");
		}
		// 录入人
		if(insertName != null && !"".equals(insertName)) {
			sb.append(" AND b.insert_name LIKE '%" + insertName + "%' ");
			
			sbc.append(" AND b.insert_name LIKE '%" + insertName + "%' ");
		}
		// 审批状态
		if(state != null && !"".equals(state) && !"10000".equals(state)) {
			sb.append(" AND b.state = " + state);
			
			sbc.append(" AND b.state = " + state);
		}
		// 数据类型
		if(infoType != null && !"".equals(infoType)) {
			sb.append(" AND b.info_type =" + infoType);
			
			sbc.append(" AND b.info_type =" + infoType);
		}
		// 知识等级
		if(level != null && !"".equals(level) && !"10000".equals(level)) {
			sb.append(" AND b.weighing = " + level);
			
			sbc.append(" AND b.weighing = " + level);
		}
		// 发布日期
		if(begincreateDate != null && !"".equals(begincreateDate)) {
			sb.append(" AND b.begincreate_date = '" + begincreateDate + "'");
			
			sbc.append(" AND b.begincreate_date = '" + begincreateDate + "'");
		}
		
		// 分页
		sb.append(" limit " + pageable.getPageNumber()*pageable.getPageSize() + "," + pageable.getPageSize());
		
		Query query = entityManager.createNativeQuery(sb.toString(), ImageModule.class);
		List<ImageModule> rows = query.getResultList(); 
		page.put("rows", rows);
		
		Query queryc = entityManager.createNativeQuery(sbc.toString());
		BigInteger total = (BigInteger)queryc.getResultList().get(0); 
		page.put("total", total);
		
		entityManager.close();
		
        return page;
	}
	
	public BigInteger listImageBaseTotal(String tagId, String title, String source, String insertName, String state, Integer infoType) {
		// sql语句
		StringBuffer sb = new StringBuffer();
		// 标签
		StringBuffer tagIdCondition = new StringBuffer();
		// 标题
		StringBuffer titleCondition = new StringBuffer();
		// 来源
		StringBuffer sourceCondition = new StringBuffer();
		// 录入人
		StringBuffer insertNameCondition = new StringBuffer();
		// 审批状态
		StringBuffer stateCondition = new StringBuffer();
		// 数据类型
		StringBuffer infoTypeCondition = new StringBuffer();
		
		if(infoType != null && !"".equals(infoType)) {
			infoTypeCondition.append(" AND b.info_type =");
			infoTypeCondition.append(infoType);
		}
		if(tagId != null && !"".equals(tagId)) {
			tagIdCondition.append(" AND bt.tag_id = '" + tagId + "'");
		}
		if(title != null && !"".equals(title)) {
			titleCondition.append(" AND b.title LIKE '%" + title + "%' ");
		}
		if(source != null && !"".equals(source)) {
			sourceCondition.append(" AND b.source LIKE '%" + source + "%' ");
		}
		if(insertName != null && !"".equals(insertName)) {
			insertNameCondition.append(" AND b.insert_name LIKE '%" + insertName + "%' ");
		}
		if(state != null && !"".equals(state) && !"10000".equals(state)) {
			stateCondition.append(" AND b.state =");
			stateCondition.append(state);
		}
		
		sb.append("SELECT COUNT(DISTINCT s.id) FROM info_image s LEFT OUTER JOIN info_base b ON b.id = s.id LEFT OUTER JOIN info_base_tag bt ON bt.base_id = b.id WHERE 1 = 1");
		sb.append(infoTypeCondition);
		sb.append(tagIdCondition);
		sb.append(titleCondition);
		sb.append(sourceCondition);
		sb.append(insertNameCondition);
		sb.append(stateCondition);
		
		Query query = entityManager.createNativeQuery(sb.toString());
		
		BigInteger total = (BigInteger)query.getResultList().get(0);   
		
		entityManager.close();
        return total;
	}
}
