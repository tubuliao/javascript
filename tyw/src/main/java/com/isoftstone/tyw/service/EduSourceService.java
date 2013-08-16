package com.isoftstone.tyw.service;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.tyw.entity.edu.EducationSource;
import com.isoftstone.tyw.repository.edu.EduSourceDao;
import com.isoftstone.tyw.util.Pager;

@Component
@Transactional(readOnly = true)
public class EduSourceService {
	@Autowired
	public EduSourceDao eduSourceDao;
	
	/**
	 * 根据packageId来查询一个某些条件的课程列表
	 * @param packageId
	 */
	public List<EducationSource> findEducationSource(int packageId,Pager pager) {
	    return  eduSourceDao.findEducationSource(packageId,pager.getPageSize()*(pager.getPageNo()-1),pager.getPageSize());
	}
	
	/**
	 * 根据包Id查询所有课程
	 * @param packageId
	 * @return
	 */
	public List<EducationSource> findAllEducationSourceByPackageId(int packageId) {
	    return  eduSourceDao.findAllEducationSourceByPackageId(packageId);
	}
	
	/**
	 * 根据包ID来查询课程总数
	 * @param packageId
	 * @return
	 */
	public BigInteger findAllEducationSourceCountByPackageId(int packageId) {
	    return  eduSourceDao.findAllEducationSourceCountByPackageId(packageId);
	}
	
	/**
	 * 根据课程Id来查询课节列表
	 * @param sourceId
	 * @param pageable
	 * @return
	 */
	public Page<EducationSource> findAllEducationSourceByParentSourceId(String sourceId,Pageable pageable) {
		return  eduSourceDao.findEducationSourceByParentSourceId(sourceId, pageable);
	}
	
	/**
	 * 根据sourceId来获取本身的信息
	 * @param sourceId
	 * @return
	 */
	public EducationSource findEducationSourceBySourceId(String sourceId){
		return eduSourceDao.findOne(sourceId);
	}
	
	
	 /**
     * 条件查询
     * 
     * @param sourceName 课程名称
     * @param status 课程状态
     * @param sourceType 课程类型
     * @param postName 专业
     * @param sourceYear 年份
     * @param pageable 分页
     * @return
     */
    public Page<EducationSource> findAllEducationSourceBy(String sourceName, String status, String sourceType,
            String postName, Integer sourceYear, Pageable pageable) {
        return eduSourceDao.findAll(getWhereClause(sourceName, status, sourceType, postName, sourceYear), pageable);
    }

    public Specification<EducationSource> getWhereClause(final String sourceName, final String status,
            final String sourceType, final String postName, final Integer sourceYear) {
        return new Specification<EducationSource>() {
            @Override
            public Predicate toPredicate(Root<EducationSource> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (null != sourceName) {
                    predicate.getExpressions().add(cb.equal(root.<Integer> get("sourceName"), sourceName));
                }
                if (null != status) {
                    predicate.getExpressions().add(cb.equal(root.<Integer> get("status"), status));
                }
                if (null != sourceType) {
                    predicate.getExpressions().add(cb.equal(root.<Integer> get("sourceType"), sourceType));
                }
                if (null != postName) {
                    predicate.getExpressions().add(cb.equal(root.<Integer> get("postName"), postName));
                }
                if (null != sourceYear) {
                    predicate.getExpressions().add(cb.equal(root.<Integer> get("sourceYear"), sourceYear));
                }
                return predicate;
            }
        };
    }
	
}
