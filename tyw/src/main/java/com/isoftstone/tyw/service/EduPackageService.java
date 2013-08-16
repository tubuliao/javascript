package com.isoftstone.tyw.service;

import java.util.List;

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

import com.isoftstone.tyw.entity.edu.EducationPackage;
import com.isoftstone.tyw.repository.edu.EduPackageDao;

@Component
@Transactional(readOnly = true)
public class EduPackageService {
    @Autowired
    private EduPackageDao eduPackageDao;

    @Transactional(readOnly = false)
    public EducationPackage save(EducationPackage ep) {
        return eduPackageDao.save(ep);
    }

    public List<EducationPackage> findByTitle(String title) {
        return eduPackageDao.findByTitle(title);
    }

    /**
     * 根据 条件来查询package的分页列表
     * 
     * @param year 年份
     * @param period 周期
     * @param title 标题
     * @param status 状态
     * @param postTypeName 类别名称 如：二级建造师 等
     * @param postName 大的类别 如：公路 市政 建筑 等
     * @param pageable 分页对象
     * @return 返回指定条件的 分页列表
     */
    public Page<EducationPackage> findAllEducationPackageBy(Integer year, Integer period, String title, String status,
            String postTypeName, String postName, Pageable pageable) {
        return eduPackageDao.findAll(getWhereClause(year, period, title, status, postTypeName, postName), pageable);
    }

    /**
     * 根据 条件来查询package的列表
     * 
     * @param year 年份
     * @param period 周期
     * @param title 标题
     * @param status 状态
     * @param postTypeName 类别名称 如：二级建造师 等
     * @param postName 大的类别 如：公路 市政 建筑 等
     * @return 返回指定条件的 列表
     */
    public List<EducationPackage> findAllEducationPackageNoPageable(Integer year, Integer period, String title,
            String status, String postTypeName, String postName) {
        return eduPackageDao.findAll(getWhereClause(year, period, title, status, postTypeName, postName));
    }

    public Specification<EducationPackage> getWhereClause(final Integer year, final Integer period, final String title,
            final String status, final String postTypeName, final String postName) {
        return new Specification<EducationPackage>() {
            @Override
            public Predicate toPredicate(Root<EducationPackage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                if (null != year) {
                    predicate.getExpressions().add(cb.equal(root.<Integer> get("pagyear"), year));
                }
                if (null != period) {
                    predicate.getExpressions().add(cb.equal(root.<Integer> get("period"), period));
                }
                if (StringUtils.isNotBlank(title)) {
                    predicate.getExpressions().add(cb.like(root.<String> get("title"), "%" + title.trim() + "%"));
                }
                if (StringUtils.isNotBlank(status)) {
                    predicate.getExpressions().add(cb.equal(root.<String> get("status"), status.trim()));
                }
                if (StringUtils.isNotBlank(postTypeName)) {
                    predicate.getExpressions().add(cb.equal(root.<String> get("postTypeName"), postTypeName.trim()));
                }
                if (StringUtils.isNotBlank(postName)) {
                    predicate.getExpressions().add(cb.equal(root.<String> get("postName"), postName.trim()));
                }
                return predicate;
            }
        };
    }

	public EducationPackage findEducationPackageByPackageId(String packageId){
		return eduPackageDao.findOne(packageId);
	}
	
}
