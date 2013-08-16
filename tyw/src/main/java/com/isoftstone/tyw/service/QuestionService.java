package com.isoftstone.tyw.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.Question;
import com.isoftstone.tyw.entity.info.QuestionModule;
import com.isoftstone.tyw.repository.info.QuestionDao;

@Component
@Transactional(readOnly=true)
public class QuestionService {
	
    @Autowired
    private QuestionDao questionDao;
    @PersistenceContext
	public EntityManager entityManager;
	  
	/**
	 * 根据问题id查询一条问题信息
	 * @param id
	 * @return
	 */
    public Question getQuestionById(String id) {
  		return questionDao.findOne(id);
  	}
    
    /**
     * 保存问题
     * @param question
     * @return
     */
    @Transactional(readOnly = false)
  	public Question saveQuestion(Question question) {
  		return questionDao.save(question);
  	}
    
    /**
     * 删除问题，对应的答案也全部删掉
     * @param id
     */
    @Transactional(readOnly = false)
	public void deleteQuestion(String id) {
    	questionDao.delete(id);
    	questionDao.deleteAnswer(id);
	}
    
    /**
     * 审核时用的更新方法
     * @param status
     * @param checkId
     * @param checkName
     * @param checkDate
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public boolean modifyState(int status,String checkId,String checkName,Date checkDate,String id){
    	return questionDao.modifyState(status, checkId, checkName, checkDate, id)>0;
	} 
    /**
	 * 问答列表查询
	 * @param tagId
	 * @param title
	 * @param source
	 * @param insertName
	 * @param state
	 * @param infoType
	 * @param pageable
	 * @return
	 */
	public List<QuestionModule> listQuestionBaseRows(String tagId, String title, String source, String insertName, String state, Integer infoType, Pageable pageable) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT DISTINCT b.id, b.title, b.create_date, b.source, b.insert_name, b.modify_date, b.state FROM info_question s LEFT OUTER JOIN info_base b ON b.id = s.id");
		
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
		// 分页
		sb.append(" limit " + pageable.getPageNumber()*pageable.getPageSize() + "," + pageable.getPageSize());
		
		Query query = entityManager.createNativeQuery(sb.toString(), QuestionModule.class);
		
		List<QuestionModule> result = query.getResultList();   
		
		entityManager.close();
        return result;
	}
	
	/**
	 * 问答列表查询（count）
	 * @param tagId
	 * @param title
	 * @param source
	 * @param insertName
	 * @param state
	 * @param infoType
	 * @return
	 */
	public BigInteger listQuestionBaseTotal(String tagId, String title, String source, String insertName, String state, Integer infoType) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT DISTINCT COUNT(s.id) FROM info_question s LEFT OUTER JOIN info_base b ON b.id = s.id");
		
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
		Query query = entityManager.createNativeQuery(sb.toString());
		
		BigInteger total = (BigInteger)query.getResultList().get(0);   
		
		entityManager.close();
        return total;
	}
}
