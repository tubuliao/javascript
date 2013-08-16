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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.pub.PersonalTheme;
import com.isoftstone.tyw.entity.pub.ThemeDetail;
import com.isoftstone.tyw.repository.info.BaseDao;
import com.isoftstone.tyw.repository.pub.PersonalThemeDao;
import com.isoftstone.tyw.repository.pub.ThemeDetailDao;

/**
 * @author zhaowenli
 *
 */
@Component
@Transactional(readOnly = true)
public class PersonalThemeService {

	@Autowired
	private PersonalThemeDao personThemeDao ;
	@Autowired
	private ThemeDetailDao themeDetailDao;
	@Autowired
	private BaseDao baseDao;
	
	@PersistenceContext
	 public EntityManager entityManager;
	
	/**
	 * 保存专题内容
	 * @param ep
	 * @return
	 */
	@Transactional(readOnly = false)
    public PersonalTheme saveTheme(PersonalTheme ep) {
        return personThemeDao.save(ep);
    }
	
	/**
	 * 删除专题内容
	 * @param ep
	 * @return
	 */
	@Transactional(readOnly = false)
    public void deleteTheme(PersonalTheme ep) {
         personThemeDao.delete(ep);
    }
	
	
	/**
	 * 根据id获取专题内容
	 * @param id
	 * @return
	 */
	public PersonalTheme findPersonalThemeSingle(String id){
		return personThemeDao.findOne(id);
	}
	
	public List<PersonalTheme> findlastlyThemelist(){
		String sql = "SELECT * FROM pub_personal_theme t WHERE t.status = 1 ORDER BY create_date DESC ";
		Query query = entityManager.createNativeQuery(sql,PersonalTheme.class);
	 	List<PersonalTheme> list = query.getResultList();
		entityManager.flush();
		entityManager.close();
		return list;
	}
	
	/**
	 * 保存专题详细
	 * @param ep
	 * @return
	 */
	@Transactional(readOnly = false)
    public void saveThemeDetail(List<ThemeDetail> ep) {
		for(int i = 0;i<ep.size();i++){
			themeDetailDao.save(ep.get(i));
		}
    }
	
	@Transactional(readOnly = false)
    public void deleteThemeDetail(String themeId) {
		String sql = "delete from pub_theme_detail where theme_id = '"+themeId+"'";
		Query query = entityManager.createNativeQuery(sql);
		query.executeUpdate();
		entityManager.flush();
		entityManager.close();
    }
	
	/**
	 * 发布自定义专题
	 * @param themes
	 */
	@Transactional(readOnly = false)
    public void publishThemes(String [] themes) {
		String id = "";
		 for(int i = 0 ; i<themes.length;i++){
			 if(i == themes.length-1){
				 id +="'"+themes[i].trim()+"'";
			 }else{
				 id +="'"+themes[i].trim()+"',";
			 }
		} 
		String sql = "UPDATE pub_personal_theme SET STATUS = 1 WHERE id in ("+id.trim()+")";
		Query query = entityManager.createNativeQuery(sql);
		query.executeUpdate();
		entityManager.flush();
		entityManager.close();
    }
	
	/**
	 * 删除自定义专题及其内容
	 * @param themes
	 */
	@Transactional(readOnly = false)
    public void deleteThemes(String [] themes) {
		String id = "";
		 for(int i = 0 ; i<themes.length;i++){
			 if(i == themes.length-1){
				 id +="'"+themes[i].trim()+"'";
			 }else{
				 id +="'"+themes[i].trim()+"',";
			 }
		} 
		 
		String forsql = "delete from pub_theme_detail where theme_id in ("+id.trim()+")";
		String prisql = "delete from pub_personal_theme where id in ("+id.trim()+")";
		
		Query forquery = entityManager.createNativeQuery(forsql);
		Query priquery = entityManager.createNativeQuery(prisql);
		
		forquery.executeUpdate();
		priquery.executeUpdate();
		entityManager.flush();
		entityManager.close();
    }
	
	/**
	 * 根据主题id查找主题详细实体
	 * @param themeId
	 * @return
	 */
	public List<ThemeDetail> findThemeDetailList(String themeId){
		return themeDetailDao.findAll(getThemeDetailWhereClause(themeId));
	}
	
	public List<Base> findDetialListById(String detialId){
		ThemeDetail detail = themeDetailDao.findOne(detialId);
		List<Base> baselist = new ArrayList<Base>();
		if(StringUtils.isNotBlank(detail.getValue())){
			String value = detail.getValue();
			String values [] = value.split(";");
			int end = values.length;
			for(int i = 0;i<end;i++){
				Base base = baseDao.findOne(values[i]);
				baselist.add(base);
			}
		}
		return baselist;
	}
	
	public Base findBaseSingle(String id){
		Base base = baseDao.findOne(id);
		return base;
	}
	
	/**
	 * 根据用户ID查询用户的自定义专题
	 * @param userId
	 * @param pageable
	 * @return
	 */
	public Page<PersonalTheme> findPersonalTheme(String userId,Pageable pageable) {
		return personThemeDao.findAll(getWhereClause(userId),pageable);
    }
	
	/**
	 * 查询分页  专题
	 * @param userId
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	public List<PersonalTheme> findPersonalThemeList(String userId,int pageSize,int pageIndex){
		int startIndex = pageIndex*pageSize;
		List<PersonalTheme>  list = personThemeDao.findPersonalThemeList(startIndex, pageSize, userId);
		return list;
	}
	
	/**
	 * 分页个数
	 * @param userId
	 * @return
	 */
	public BigInteger findPersonalThemeListCount(String userId){
		BigInteger count = personThemeDao.findPersonalThemeListCount(userId);
		return count;
	}
	
	 public Specification<PersonalTheme> getWhereClause(final String userId) {
	        return new Specification<PersonalTheme>() {
	            @Override
	            public Predicate toPredicate(Root<PersonalTheme> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                Predicate predicate = cb.conjunction();
	              
	                if (StringUtils.isNotBlank(userId)) {
	                    predicate.getExpressions().add(cb.like(root.<String> get("userId"), "%" + userId.trim() + "%"));
	                }
	                return predicate;
	            }
	        };
	    }
	 
	 public Specification<ThemeDetail> getThemeDetailWhereClause(final String themeId) {
	        return new Specification<ThemeDetail>() {
	            @Override
	            public Predicate toPredicate(Root<ThemeDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
	                Predicate predicate = cb.conjunction();
	              
	                if (StringUtils.isNotBlank(themeId)) {
	                    predicate.getExpressions().add(cb.equal(root.<String> get("themeId"),  themeId.trim() ));
	                }
	                return predicate;
	            }
	        };
	    }
}
