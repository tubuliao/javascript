package com.isoftstone.tyw.service;



import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.SegmentModule;
import com.isoftstone.tyw.entity.info.Video;
import com.isoftstone.tyw.entity.info.VideoModule;
import com.isoftstone.tyw.repository.info.BaseBaseDao;
import com.isoftstone.tyw.repository.info.VideoDao;

@Component
@Transactional(readOnly = true)
public class VideoService {
	
	@Autowired
	private VideoDao videoDao;
	@Autowired
	private BaseBaseDao baseBaseDao;
	@PersistenceContext
	public EntityManager entityManager;
	
	private static final String xmlConfig = "/META-INF/resource/video_mapping.xml" ;
	
	/**
	 * web----通过id获取视频
	 * 
	 * @param id
	 * @return
	 */
	public Video getVideoById(String id){
		return videoDao.findOne(id);
	}
	
	@Transactional(readOnly=false)
	public void saveOne(Video video) {
		videoDao.save(video) ;
	}
	
	@Transactional(readOnly=false)
	public void deleteOne(String id) {
		videoDao.delete(id) ;
	}
	
	@Transactional(readOnly=false)
	public void deleteVideoUrl(String id) {
		videoDao.updateUrl(id) ;
	}
	/**
	 * 视频列表查询
	 * @param tagId
	 * @param title
	 * @param source
	 * @param insertName
	 * @param state
	 * @param infoType
	 * @param pageable
	 * @return
	 */
	public List<VideoModule> listVideoBaseRows(String tagId, String title, String source, String insertName, String state, Integer infoType, String level, String begincreateDate,Pageable pageable) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT DISTINCT b.id, b.title, b.create_date, b.source, b.insert_name, b.modify_date, b.state, b.weighing FROM info_video s LEFT OUTER JOIN info_base b ON b.id = s.id");
		
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
		
		Query query = entityManager.createNativeQuery(sb.toString(), VideoModule.class);
		
		List<VideoModule> result = query.getResultList();   
		entityManager.close();
        return result;
	}
	
	/**
	 * 视频列表查询（count）
	 * @param tagId
	 * @param title
	 * @param source
	 * @param insertName
	 * @param state
	 * @param infoType
	 * @return
	 */
	public BigInteger listVideoBaseTotal(String tagId, String title, String source, String insertName, String state, String level, String begincreateDate,Integer infoType) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT DISTINCT COUNT(s.id) FROM info_video s LEFT OUTER JOIN info_base b ON b.id = s.id");
		
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
