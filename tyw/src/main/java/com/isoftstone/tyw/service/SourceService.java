/**
 * Copyright 2008 - 2012 Simcore.org.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.isoftstone.tyw.service;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReadStatus;
import net.sf.jxls.reader.XLSReader;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import com.isoftstone.tyw.entity.info.Source;
import com.isoftstone.tyw.entity.info.SourceModule;
import com.isoftstone.tyw.entity.info.SourceView;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.repository.info.BaseDao;
import com.isoftstone.tyw.repository.info.SourceDao;
import com.isoftstone.tyw.repository.info.SourceViewDao;

/**
 * 账户管理类
 * 
 * @author ray
 */
@Component
@Transactional(readOnly=true)
public class SourceService {
	private final static String xmlConfig = "/META-INF/resource/source_mapping.xml";
	
	@Autowired
	private SourceDao sourceDao;
	
	@Autowired
	private SourceViewDao sourceViewDao;
	
	@Autowired
    private BaseDao baseDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	/**
	 * 分页查询来源
	 * 
	 * @param pageable
	 * @return
	 */
	public Page<Source> listSource(Pageable pageable){
		return sourceDao.findAll(pageable);
	}
	
	/**
	 * 提供高级分页查询功能
	 * @param specification
	 * @param pageable
	 * @return
	 */
	public Page<Source> listSource(Specification<Source> specification,Pageable pageable){
		return sourceDao.findAll(specification,pageable);
	}
	
	/**
	 * 保存来源
	 * 
	 * @param source
	 * @return
	 */
	 
	@Transactional(readOnly = false)
	public Source saveSource(Source source) {
		return sourceDao.save(source);
	}
	
	/**
	 * 删除来源
	 * @param id
	 */
	@Transactional(readOnly=false)
	public void deleteSource(String id){
		sourceDao.delete(id);
	}
	 /**
     * 批量统计切片数量
     * @param sources
     */
	@Transactional(readOnly=false)
	public void modifySegMentCount(String name_no){
			sourceDao.modifySegMentCount(name_no);
	}
	  /**
     * 保存来源列表
     * @param sources
     */
    @Transactional(readOnly=false)
    public void saveSource(List<Source> sources){
    	Date date ;
        for(Source source:sources){
        	date = source.getExecuteDate() ;
        	try {	// Excel导入空日期默认为"1999-12-12 00:00:00"
				if("1999-12-12 00:00:00".equals(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date))) {
					source.setExecuteDate(null) ;
				}
				// 默认切片数量为0
				source.setSegmentCount(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            sourceDao.save(source);
        }
    }
    
	
	public List<Source> readXls(String dataXls) throws IOException, SAXException, InvalidFormatException{
		Map<String,List<Source>> beans = new HashMap<String,List<Source>>();
		InputStream inputXML = new BufferedInputStream(getClass().getResourceAsStream(xmlConfig));
		XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
		InputStream inputXLS = new BufferedInputStream(new FileInputStream(dataXls));
		List<Source> sourceList = new ArrayList<Source>();
		beans.put("sourceList", sourceList);
		//ReaderConfig.getInstance().setSkipErrors(true);
		XLSReadStatus readStatus = mainReader.read(inputXLS, beans);
		if(readStatus.isStatusOK()){
			return sourceList;
		}
		return sourceList;
	}

	
	public Source getSourceById(String id) {
		return sourceDao.findOne(id) ;
	}

	
	@Transactional(readOnly = false)
	public Page<SourceView> listSourceView(String sourceType, String knowledgeType, String sources, Pageable pageable){
		return sourceViewDao.findAll(this.getWhereClause(sourceType, knowledgeType, sources),pageable);
	}
	
	public Specification<SourceView> getWhereClause(final String sourceType, final String knowledgeType, final String sources){
		return new Specification<SourceView>(){
			@Override
			public Predicate toPredicate(Root<SourceView> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
//				query.orderBy(cb.desc(root.get("createDate")));	
				if(sourceType!=null&&!"".equals(sourceType)){
					predicate.getExpressions().add(cb.like(root.<String>get("sourceType"), "%"+sourceType+"%"));
				}
				if(knowledgeType != null && !"".equals(knowledgeType)) {
					if(!"全部".equals(knowledgeType)) {
						predicate.getExpressions().add(cb.like(root.<String>get("infoType"), "%"+knowledgeType+"%")) ;
					}
				}
				if(sources != null && !"".equals(sources)) {
					predicate.getExpressions().add(cb.like(root.<String>get("source"), "%"+sources+"%")) ;
				}
				return predicate;
			}
		};
	}

	/**
	 * 根据标准名称和标准编号查询来源信息
	 * @param standardName
	 * @param standardNo
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<Source> getOneByNameAndNo(String standardName, String standardNo) {
		return sourceDao.findOneByNameAndNo(standardName, standardNo);
	}
	
	/**
	 * 根据标准名称查询来源信息
	 * @param standardName
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<Source> getOneByName(String standardName) {
		return sourceDao.findOneByName(standardName);
	}
	
	/**
	 * 获取相同类型的来源名称集合
	 * @param standardType
	 * @param standardName
	 * @return
	 */
	public List<Source> getSameTypeSourceList(String standardType, String standardName) {
		return sourceDao.findSameType(standardType, standardName);
	}
	
	/**
	 * 获取所有的标准类型
	 * @return
	 */
	public List<String> getAllStandardType() {
		return sourceDao.findAllStandardType();
	}
	
	/**
	 * 根据查询条件获取来源列表
	 * @param standardTitle
	 * @param standardType
	 * @param dateFrom
	 * @param dateTo
	 * @param st
	 * @return
	 */
	public List<Source> getSourceListByExecuteDate(String standardTitle, String dateFrom, String dateTo, Integer pageIndex, Integer itemsPerPage, String sourceType){
		return  sourceDao.findSourceListByExecuteDate(standardTitle, dateFrom, dateTo, pageIndex, itemsPerPage, sourceType);
	}
	
	/**
	 * 根据查询条件获取来源数量
	 * @param standardTitle
	 * @param dateFrom
	 * @param dateTo
	 * @param sourceType
	 * @return
	 */
	public BigInteger getSourceTotalByExecuteDate(String standardTitle, String dateFrom, String dateTo, String sourceType){
		return sourceDao.findSourceTotalByExecuteDate(standardTitle, dateFrom, dateTo, sourceType);
	} 
	
	
	/**
	 * 根据查询条件获取来源数量(只有起始日期)
	 * @param standardTitle
	 * @param dateFrom
	 * @param sourceType
	 * @return
	 */
	public BigInteger getSourceTotalByExecuteDateOnlyDateFrom(String standardTitle, String dateFrom, String sourceType){
		return sourceDao.findSourceTotalByExecuteDateOnlyDateFrom(standardTitle, dateFrom, sourceType);
	} 
	
	/**
	 * 根据查询条件获取来源列表(只有起始日期)
	 * @param standardTitle
	 * @param standardType
	 * @param dateFrom
	 * @param st
	 * @return
	 */
	public List<Source> getSourceListByExecuteDateOnlyDateFrom(String standardTitle, String dateFrom, Integer pageIndex, Integer itemsPerPage, String sourceType){
		return  sourceDao.findSourceListByExecuteDateOnlyDateFrom(standardTitle, dateFrom, pageIndex, itemsPerPage, sourceType);
	}
	
	/**
	 * 根据查询条件获取来源列表（只有终止日期）
	 * @param standardTitle
	 * @param standardType
	 * @param dateFrom
	 * @param dateTo
	 * @param st
	 * @return
	 */
	public List<Source> getSourceListByExecuteDateOnlyDateTo(String standardTitle, String dateTo, Integer pageIndex, Integer itemsPerPage, String sourceType){
		return  sourceDao.findSourceListByExecuteDateOnlyDateTo(standardTitle, dateTo, pageIndex, itemsPerPage, sourceType);
	}
	
	/**
	 * 根据查询条件获取来源数量（只有终止日期）
	 * @param standardTitle
	 * @param dateFrom
	 * @param dateTo
	 * @param sourceType
	 * @return
	 */
	public BigInteger getSourceTotalByExecuteDateOnlyDateTo(String standardTitle, String dateTo, String sourceType){
		return sourceDao.findSourceTotalByExecuteDateOnlyDateTo(standardTitle, dateTo, sourceType);
	} 
	
	
	/**
	 * 根据查询条件获取来源列表(executeDate IS NULL)
	 * @param standardTitle
	 * @param standardType
	 * @param dateFrom
	 * @param dateTo
	 * @param st
	 * @return
	 */
	public List<Source> getSourceListNoExecuteDate(String standardTitle, Integer pageIndex, Integer itemsPerPage, String sourceType){
		return  sourceDao.findSourceListNoExecuteDate(standardTitle, pageIndex, itemsPerPage, sourceType);
	}
	
	/**
	 * 根据查询条件获取来源数量(executeDate IS NULL)
	 * @param standardTitle
	 * @param standardType
	 * @param dateFrom
	 * @param dateTo
	 * @param st
	 * @return
	 */
	public BigInteger getSourceTotalNoExecuteDate(String standardTitle, String sourceType){
		return sourceDao.findSourceTotalNoExecuteDate(standardTitle, sourceType);
	} 
	
	/**
	 * 初始化，查询所有的
	 * @param pageIndex
	 * @param itemsPerPage
	 * @return
	 */
	public List<Source> getSourceListAll(Integer pageIndex, Integer itemsPerPage, String sourceType) {
		return sourceDao.findSourceListAll(pageIndex, itemsPerPage, sourceType);
	}
	
	/**
	 * 初始化，查询所有的
	 * @return
	 */
	public BigInteger getSourceTotalAll(String sourceType) {
		return sourceDao.findSourceTotalAll(sourceType);
	}
	
	/**
	 * 根据来源名称获取对象
	 * @param standardName
	 * @return
	 */
	public Source getSourceByStandardName(String standardName) {
		return sourceDao.findSourceByStandardName(standardName);
	}
	
	/**
	 * 根据来源名称和编号获取对象
	 * @param standardName
	 * @return
	 */
	public Source getSourceByStandardNameAndStandardNo(String source) {
		return sourceDao.findSourceByStandardNameAndStandardNo(source);
	}
	
	/**
	 * 根据标准类型查询
	 * @param pageIndex
	 * @param itemsPerPage
	 * @return
	 */
	public List<Source> getSourceListByStandardType(Integer pageIndex, Integer itemsPerPage, String standardType, String sourceType) {
		return sourceDao.findSourceListByStandardType(pageIndex, itemsPerPage, standardType, sourceType);
	}
	
	/**
	 * 根据标准类型查询
	 * @return
	 */
	public BigInteger getSourceTotalByStandardType(String standardType, String sourceType) {
		return sourceDao.findSourceTotalByStandardType(standardType, sourceType);
	}
	
	/**
	 * 来源列表查询
	 * @param standardName
	 * @param standardNo
	 * @param standardType
	 * @param approveDepartment
	 * @param editDepartment
	 * @param pageable
	 * @return
	 */
	public List<SourceModule> listSourceBySql(String standardName, String standardNo, String standardType, String approveDepartment, String editDepartment, Pageable pageable) {
		// sql
		StringBuffer sb = new StringBuffer(" SELECT DISTINCT s.id, s.standard_type, s.standard_name, s.standard_no, s.change_no, s.english_name, s.edit_department, s.approve_department, s.execute_date, s.segment_count FROM info_source s WHERE 1 = 1 ");
		// 标准名称
		if(standardName != null && !"".equals(standardName)) {
			sb.append(" AND s.standard_name LIKE '%" + standardName + "%'");
		}
		// 标准编号
		if(standardNo != null && !"".equals(standardNo)) {
			sb.append(" AND s.standard_no LIKE '%" + standardNo + "%'");
		}
		// 标准类型
		if(standardType != null && !"".equals(standardType)) {
			sb.append(" AND s.standard_type LIKE '%" + standardType + "%'");
		}
		// 主编单位
		if(editDepartment != null && !"".equals(editDepartment)) {
			sb.append(" AND s.edit_department LIKE '%" + editDepartment + "%'");
		}
		// 批准部门
		if(approveDepartment != null && !"".equals(approveDepartment)) {
			sb.append(" AND s.approve_department LIKE '%" + approveDepartment + "%'");
		}
		// 排序
//		sb.append(" ORDER BY s.standard_type, s.standard_name");太慢
		// 分页
		sb.append(" LIMIT " + pageable.getPageNumber()*pageable.getPageSize() + ", " + pageable.getPageSize());
		// 查询
		Query q = entityManager.createNativeQuery(sb.toString(), SourceModule.class);
		List<SourceModule> rows = q.getResultList();

		entityManager.close();
		return rows;
	}
	
	
	public BigInteger listSourceBySqlCount(String standardName, String standardNo, String standardType, String approveDepartment, String editDepartment) {
		// sql
		StringBuffer sb = new StringBuffer(" SELECT COUNT(DISTINCT s.id) FROM info_source s WHERE 1 = 1 ");
		// 标准名称
		if(standardName != null && !"".equals(standardName)) {
			sb.append(" AND s.standard_name LIKE '%" + standardName + "%'");
		}
		// 标准编号
		if(standardNo != null && !"".equals(standardNo)) {
			sb.append(" AND s.standard_no LIKE '%" + standardNo + "%'");
		}
		// 标准类型
		if(standardType != null && !"".equals(standardType)) {
			sb.append(" AND s.standard_type LIKE '%" + standardType + "%'");
		}
		// 主编单位
		if(editDepartment != null && !"".equals(editDepartment)) {
			sb.append(" AND s.edit_department LIKE '%" + editDepartment + "%'");
		}
		// 批准部门
		if(approveDepartment != null && !"".equals(approveDepartment)) {
			sb.append(" AND s.approve_department LIKE '%" + approveDepartment + "%'");
		}
		// 查询
		Query q = entityManager.createNativeQuery(sb.toString());
		BigInteger total = (BigInteger)q.getResultList().get(0);
		entityManager.close();
		return total;
	}
	
	
	/**
	 * 知识来源列表查询
	 * @param sourceType
	 * @param knowledgeType
	 * @param sources
	 * @param pageable
	 * @return
	 */
	public List<SourceView> listSourceViewBaseRows(String sourceType, String knowledgeType, String sources, Pageable pageable) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT * FROM view_info_source t WHERE 1 = 1 ");
		// 来源类型
		if(sourceType != null && !"".equals(sourceType)) {
			sb.append(" AND t.source_type like '%" + sourceType + "%' ");
		}
		// 知识类型
		if(knowledgeType != null && !"".equals(knowledgeType) && !"全部".equals(knowledgeType)) {
				sb.append(" AND t.info_type LIKE '%" + knowledgeType + "%' ");
		}
		// 来源
		if(sources != null && !"".equals(sources)) {
			sb.append(" AND t.source = '"+sources+"' ");
		}
		// 分页
		sb.append(" limit " + pageable.getPageNumber()*pageable.getPageSize() + "," + pageable.getPageSize());
		
		Query query = entityManager.createNativeQuery(sb.toString(), SourceView.class);
		
		List<SourceView> result = query.getResultList();   
        return result;
	}
	
	/**
	 * 知识来源列表查询(count)
	 * @param sourceType
	 * @param knowledgeType
	 * @param sources
	 * @return
	 */
	public BigInteger listSourceViewBaseTotal(String sourceType, String knowledgeType, String sources) {
		// sql语句
		StringBuffer sb = new StringBuffer("SELECT count(*) FROM view_info_source t WHERE 1 = 1 ");
		// 来源类型
		if(sourceType != null && !"".equals(sourceType)) {
			sb.append(" AND t.source_type like '%" + sourceType + "%' ");
		}
		// 知识类型
		if(knowledgeType != null && !"".equals(knowledgeType) && !"全部".equals(knowledgeType)) {
			sb.append(" AND t.info_type LIKE '%" + knowledgeType + "%' ");
		}
		// 来源
		if(sources != null && !"".equals(sources)) {
			sb.append(" AND t.source = '"+sources+"' ");
		}
		Query query = entityManager.createNativeQuery(sb.toString());
		
		BigInteger total = (BigInteger)query.getResultList().get(0);   
		
		entityManager.close();
        return total;
	}
	
	
}
