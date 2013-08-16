
package com.isoftstone.tyw.repository.edu;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.edu.EducationSource;

public interface EduSourceDao extends PagingAndSortingRepository<EducationSource, String>, JpaSpecificationExecutor<EducationSource>{
	 
	/**
	 * 根据packageId来查询一个某些条件的课程列表
	 * @param packageId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	@Query(nativeQuery=true,value="select es.* from edu_source es , edu_package_source eps where es.id = eps.sourceId  and eps.packageId = :packageId limit :startIndex,:pageSize ") 
	public List<EducationSource> findEducationSource(@Param("packageId")Integer packageId,@Param("startIndex")Integer startIndex,@Param("pageSize")Integer pageSize);
	
	
	/**
	 * 根据packageId来查询一个某些条件的课程列表
	 * @param packageId
	 * @return
	 */
	@Query(nativeQuery=true,value="select es.* from edu_source es , edu_package_source eps where es.id = eps.sourceId  and eps.packageId = :packageId") 
	public List<EducationSource> findAllEducationSourceByPackageId(@Param("packageId")Integer packageId);
	
	@Query(nativeQuery=true,value="select count(es.id) from edu_source es , edu_package_source eps where es.id = eps.sourceId  and eps.packageId = :packageId") 
	public BigInteger findAllEducationSourceCountByPackageId(@Param("packageId")Integer packageId);
	
	/**
	 * 根据课程Id来查询课节列表
	 * @param Id
	 * @param pageable
	 * @return
	 */
	public Page<EducationSource> findEducationSourceByParentSourceId(String Id,Pageable pageable);
	
}
