package com.isoftstone.tyw.repository.info;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Source;

public interface SourceDao  extends PagingAndSortingRepository<Source, String>, JpaSpecificationExecutor<Source>{
	
    /**
     * 根据内容类型查询所有标签
     * 
     * @param infoType
     * @param pageable
     * @return
     */
//    @Modifying 
//    @Query("update Source  set  segmentCount=(select count(source) from Base  where source=:name_no) where name_no=:name_no")
//    public void modifySegMentCount(@Param("name_no") String name_no); 
    @Modifying 
    @Query(nativeQuery=true,value="call batchModSegMent(?1)")
    public void modifySegMentCount(String name_no); 
    
    @Query(nativeQuery=true, value="SELECT s.* FROM info_source s WHERE s.standard_name = :standardName AND s.standard_no = :standardNo")
    public List<Source> findOneByNameAndNo(@Param("standardName")String standardName, @Param("standardNo")String standardNo);
    
    @Query(nativeQuery=true, value="SELECT s.* FROM info_source s WHERE s.standard_name = :standardName")
    public List<Source> findOneByName(@Param("standardName")String standardName);
    
    @Query(nativeQuery=true, value="SELECT s.* FROM info_source s WHERE s.standard_type = :standardType AND s.standard_name != :standardName LIMIT 0, 5")
    public List<Source> findSameType(@Param("standardType")String standardType, @Param("standardName")String standardName);
    
    @Query(nativeQuery=true, value="SELECT DISTINCT standard_type FROM info_source")
    public List<String> findAllStandardType();
    
    @Query(nativeQuery=true, value="SELECT s.* FROM info_source s WHERE s.standard_name LIKE :standardTitle AND (s.execute_date BETWEEN :dateFrom AND :dateTo) AND s.source_type = :sourceType ORDER BY s.standard_type, s.sort_no LIMIT :pageIndex, :itemsPerPage ")
    public List<Source> findSourceListByExecuteDate(@Param("standardTitle")String standardTitle, @Param("dateFrom")String dateFrom, @Param("dateTo")String dateTo, @Param("pageIndex")Integer pageIndex, @Param("itemsPerPage")Integer itemsPerPage, @Param("sourceType")String sourceType);
    
    @Query(nativeQuery=true, value="SELECT COUNT(*) FROM info_source s WHERE s.standard_name LIKE :standardTitle AND (s.execute_date BETWEEN :dateFrom AND :dateTo) AND s.source_type = :sourceType ")
    public BigInteger findSourceTotalByExecuteDate(@Param("standardTitle")String standardTitle, @Param("dateFrom")String dateFrom, @Param("dateTo")String dateTo, @Param("sourceType")String sourceType);
    
    @Query(nativeQuery=true, value="SELECT COUNT(*) FROM info_source s WHERE s.standard_name LIKE :standardTitle AND (s.execute_date >= :dateFrom) AND s.source_type = :sourceType ")
    public BigInteger findSourceTotalByExecuteDateOnlyDateFrom(@Param("standardTitle")String standardTitle, @Param("dateFrom")String dateFrom, @Param("sourceType")String sourceType);
    
    @Query(nativeQuery=true, value="SELECT s.* FROM info_source s WHERE s.standard_name LIKE :standardTitle AND (s.execute_date >= :dateFrom) AND s.source_type = :sourceType ORDER BY s.standard_type, s.sort_no LIMIT :pageIndex, :itemsPerPage ")
    public List<Source> findSourceListByExecuteDateOnlyDateFrom(@Param("standardTitle")String standardTitle, @Param("dateFrom")String dateFrom, @Param("pageIndex")Integer pageIndex, @Param("itemsPerPage")Integer itemsPerPage, @Param("sourceType")String sourceType);
    
    @Query(nativeQuery=true, value="SELECT s.* FROM info_source s WHERE s.standard_name LIKE :standardTitle AND (s.execute_date <= :dateTo) AND s.source_type = :sourceType ORDER BY s.standard_type, s.sort_no LIMIT :pageIndex, :itemsPerPage ")
    public List<Source> findSourceListByExecuteDateOnlyDateTo(@Param("standardTitle")String standardTitle, @Param("dateTo")String dateTo, @Param("pageIndex")Integer pageIndex, @Param("itemsPerPage")Integer itemsPerPage, @Param("sourceType")String sourceType);
    
    @Query(nativeQuery=true, value="SELECT COUNT(*) FROM info_source s WHERE s.standard_name LIKE :standardTitle AND (s.execute_date <= :dateTo) AND s.source_type = :sourceType ")
    public BigInteger findSourceTotalByExecuteDateOnlyDateTo(@Param("standardTitle")String standardTitle, @Param("dateTo")String dateTo, @Param("sourceType")String sourceType);
    
    
    @Query(nativeQuery=true, value="SELECT s.* FROM info_source s WHERE s.standard_name LIKE :standardTitle AND s.execute_date IS NULL AND s.source_type = :sourceType ORDER BY s.standard_type, s.sort_no LIMIT :pageIndex, :itemsPerPage ")
    public List<Source> findSourceListNoExecuteDate(@Param("standardTitle")String standardTitle, @Param("pageIndex")Integer pageIndex, @Param("itemsPerPage")Integer itemsPerPage, @Param("sourceType")String sourceType);
    
    @Query(nativeQuery=true, value="SELECT COUNT(*) FROM info_source s WHERE s.standard_name LIKE :standardTitle AND s.execute_date IS NULL AND s.source_type = :sourceType ")
    public BigInteger findSourceTotalNoExecuteDate(@Param("standardTitle")String standardTitle, @Param("sourceType")String sourceType);
    
    @Query(nativeQuery=true, value="SELECT s.* FROM info_source s WHERE s.source_type = :sourceType ORDER BY s.standard_type, s.sort_no LIMIT :pageIndex, :itemsPerPage ")
    public List<Source> findSourceListAll(@Param("pageIndex")Integer pageIndex, @Param("itemsPerPage")Integer itemsPerPage, @Param("sourceType")String sourceType);
    
    @Query(nativeQuery=true, value="SELECT COUNT(*) FROM info_source s WHERE s.source_type = :sourceType ")
    public BigInteger findSourceTotalAll(@Param("sourceType")String sourceType);
    
    @Query(value="FROM Source s WHERE s.standardName = :standardName")
    public Source findSourceByStandardName(@Param("standardName")String standardName);
    
    @Query(value="FROM Source s WHERE CONCAT('《', s.standardName, '》' , s.standardNo) = :source")
    public Source findSourceByStandardNameAndStandardNo(@Param("source")String source);
    
    @Query(nativeQuery=true, value="SELECT s.* FROM info_source s WHERE s.standard_type = :standardType AND s.source_type = :sourceType ORDER BY s.standard_type, s.sort_no LIMIT :pageIndex, :itemsPerPage ")
    public List<Source> findSourceListByStandardType(@Param("pageIndex")Integer pageIndex, @Param("itemsPerPage")Integer itemsPerPage, @Param("standardType")String standardType, @Param("sourceType")String sourceType);
    
    @Query(nativeQuery=true, value="SELECT COUNT(*) FROM info_source s WHERE s.standard_type = :standardType AND s.source_type = :sourceType ")
    public BigInteger findSourceTotalByStandardType(@Param("standardType")String standardType, @Param("sourceType")String sourceType);
}
