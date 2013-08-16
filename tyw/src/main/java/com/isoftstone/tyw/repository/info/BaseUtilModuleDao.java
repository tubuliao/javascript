package com.isoftstone.tyw.repository.info;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.BaseUtilModule;

public interface BaseUtilModuleDao extends PagingAndSortingRepository<BaseUtilModule,String>{

	@Query(nativeQuery=true,value="SELECT  DISTINCT bt.id,bt.title,bt.info_type FROM info_base AS bt LEFT JOIN info_base_tag AS z ON z.tag_code BETWEEN :zsxzstart AND :zsxzend AND bt.id = z.base_id LEFT JOIN info_base_tag AS f ON f.tag_code BETWEEN :fbfxstart AND :fbfxend AND bt.id = f.base_id LEFT JOIN info_base_tag AS d ON d.tag_code = :areaCode AND bt.id = d.base_id LEFT JOIN info_base_tag AS r ON r.tag_code BETWEEN 30000000000000000 AND 39999999999999999 AND bt.id = r.base_id WHERE z.tag_code>0 AND d.tag_code>0 AND f.tag_code>0 AND  bt.title NOT LIKE '%样表%' ORDER BY f.tag_code limit :count,:pageSize")
	List<BaseUtilModule> findBaseForfbfxAndzsxzAndarea( @Param("fbfxstart")String fbfxstart,@Param("fbfxend")String fbfxend,@Param("zsxzstart")String zsxzstart,@Param("zsxzend")String zsxzend,@Param("areaCode")String areaCode, @Param("count")Integer count ,@Param("pageSize")Integer pageSize);
	
	@Query(nativeQuery=true,value="SELECT  DISTINCT bt.id,bt.title,bt.info_type FROM info_base AS bt LEFT JOIN info_base_tag AS z ON z.tag_code BETWEEN :zsxzstart AND :zsxzend AND bt.id = z.base_id LEFT JOIN info_base_tag AS f ON f.tag_code BETWEEN :fbfxstart AND :fbfxend AND bt.id = f.base_id LEFT JOIN info_base_tag AS d ON d.tag_code = :areaCode AND bt.id = d.base_id LEFT JOIN info_base_tag AS r ON r.tag_code BETWEEN :peoplestart AND :peopleend AND bt.id = r.base_id WHERE z.tag_code>0 AND d.tag_code>0 AND f.tag_code>0 AND r.tag_code>0 AND  bt.title NOT LIKE '%样表%' ORDER BY f.tag_code limit :count,:pageSize")
	List<BaseUtilModule> findBaseForfbfxAndzsxzAndareaAndPeople( @Param("fbfxstart")String fbfxstart,@Param("fbfxend")String fbfxend,@Param("zsxzstart")String zsxzstart,@Param("zsxzend")String zsxzend,@Param("peoplestart")String peoplestart,@Param("peopleend")String peopleend,@Param("areaCode")String areaCode, @Param("count")Integer count ,@Param("pageSize")Integer pageSize);
			
	
	@Query(nativeQuery=true,value="SELECT  COUNT(DISTINCT bt.id) FROM info_base AS bt LEFT JOIN info_base_tag AS z ON z.tag_code BETWEEN :zsxzstart AND :zsxzend AND bt.id = z.base_id LEFT JOIN info_base_tag AS f ON f.tag_code BETWEEN :fbfxstart AND :fbfxend AND bt.id = f.base_id LEFT JOIN info_base_tag AS d ON d.tag_code = :areaCode AND bt.id = d.base_id LEFT JOIN info_base_tag AS r ON r.tag_code BETWEEN 30000000000000000 AND 39999999999999999 AND bt.id = r.base_id WHERE z.tag_code>0 AND d.tag_code>0 AND f.tag_code>0 AND  bt.title NOT LIKE '%样表%' ")
	BigInteger findBaseForfbfxAndzsxzAndareaCount( @Param("fbfxstart")String fbfxstart,@Param("fbfxend")String fbfxend,@Param("zsxzstart")String zsxzstart,@Param("zsxzend")String zsxzend,@Param("areaCode")String areaCode);
	
	@Query(nativeQuery=true,value="SELECT  COUNT(DISTINCT bt.id) FROM info_base AS bt LEFT JOIN info_base_tag AS z ON z.tag_code BETWEEN :zsxzstart AND :zsxzend AND bt.id = z.base_id LEFT JOIN info_base_tag AS f ON f.tag_code BETWEEN :fbfxstart AND :fbfxend AND bt.id = f.base_id LEFT JOIN info_base_tag AS d ON d.tag_code = :areaCode AND bt.id = d.base_id LEFT JOIN info_base_tag AS r ON r.tag_code BETWEEN :peoplestart AND :peopleend AND bt.id = r.base_id WHERE z.tag_code>0 AND d.tag_code>0 AND f.tag_code>0 AND r.tag_code>0 AND  bt.title NOT LIKE '%样表%' ")
	BigInteger findBaseForfbfxAndzsxzAndareaAndPeopleCount( @Param("fbfxstart")String fbfxstart,@Param("fbfxend")String fbfxend,@Param("zsxzstart")String zsxzstart,@Param("zsxzend")String zsxzend,@Param("peoplestart")String peoplestart,@Param("peopleend")String peopleend,@Param("areaCode")String areaCode);
			
	
	@Query(nativeQuery=true,value="SELECT DISTINCT bt.id,bt.title,bt.info_type FROM info_base AS bt LEFT JOIN info_base_tag AS z ON z.tag_code BETWEEN :zsxzstart AND :zsxzend AND bt.id = z.base_id LEFT JOIN info_base_tag AS f ON f.tag_code BETWEEN :fbfxstart AND :fbfxend AND bt.id = f.base_id LEFT JOIN info_base_tag AS d ON d.tag_code = :areaCode AND bt.id = d.base_id LEFT JOIN info_base_tag AS r ON r.tag_code BETWEEN 30000000000000000 AND 39999999999999999 AND bt.id = r.base_id WHERE z.tag_code>0 AND d.tag_code>0 AND f.tag_code>0 AND  bt.title NOT LIKE '%样表%' AND bt.title like :title ORDER BY f.tag_code limit :count,:pageSize")
	List<BaseUtilModule> findBaseForfbfxAndzsxzAndareaAndTitle( @Param("fbfxstart")String fbfxstart,@Param("fbfxend")String fbfxend,@Param("zsxzstart")String zsxzstart,@Param("zsxzend")String zsxzend,@Param("areaCode")String areaCode,@Param("title")String title,@Param("count")Integer count,@Param("pageSize")Integer pageSize);
	
	@Query(nativeQuery=true,value="SELECT DISTINCT bt.id,bt.title,bt.info_type FROM info_base AS bt LEFT JOIN info_base_tag AS z ON z.tag_code BETWEEN :zsxzstart AND :zsxzend AND bt.id = z.base_id LEFT JOIN info_base_tag AS f ON f.tag_code BETWEEN :fbfxstart AND :fbfxend AND bt.id = f.base_id LEFT JOIN info_base_tag AS d ON d.tag_code = :areaCode AND bt.id = d.base_id LEFT JOIN info_base_tag AS r ON r.tag_code BETWEEN :peoplestart AND :peopleend AND bt.id = r.base_id WHERE z.tag_code>0 AND d.tag_code>0 AND f.tag_code>0 AND r.tag_code>0 AND  bt.title NOT LIKE '%样表%' AND bt.title like :title ORDER BY f.tag_code limit :count,:pageSize")
	List<BaseUtilModule> findBaseForfbfxAndzsxzAndareaAndPeopleAndTitle( @Param("fbfxstart")String fbfxstart,@Param("fbfxend")String fbfxend,@Param("zsxzstart")String zsxzstart,@Param("zsxzend")String zsxzend,@Param("peoplestart")String peoplestart,@Param("peopleend")String peopleend,@Param("areaCode")String areaCode,@Param("title")String title,@Param("count")Integer count,@Param("pageSize")Integer pageSize);
	
	
	@Query(nativeQuery=true,value="SELECT  COUNT(DISTINCT bt.id) FROM info_base AS bt LEFT JOIN info_base_tag AS z ON z.tag_code BETWEEN :zsxzstart AND :zsxzend AND bt.id = z.base_id LEFT JOIN info_base_tag AS f ON f.tag_code BETWEEN :fbfxstart AND :fbfxend AND bt.id = f.base_id LEFT JOIN info_base_tag AS d ON d.tag_code = :areaCode AND bt.id = d.base_id LEFT JOIN info_base_tag AS r ON r.tag_code BETWEEN 30000000000000000 AND 39999999999999999 AND bt.id = r.base_id WHERE z.tag_code>0 AND d.tag_code>0 AND f.tag_code>0 AND  bt.title NOT LIKE '%样表%' AND bt.title like :title ")
	BigInteger findBaseForfbfxAndzsxzAndareaAndTitleCount( @Param("fbfxstart")String fbfxstart,@Param("fbfxend")String fbfxend,@Param("zsxzstart")String zsxzstart,@Param("zsxzend")String zsxzend,@Param("areaCode")String areaCode,@Param("title")String title);
	
	@Query(nativeQuery=true,value="SELECT  COUNT(DISTINCT bt.id) FROM info_base AS bt LEFT JOIN info_base_tag AS z ON z.tag_code BETWEEN :zsxzstart AND :zsxzend AND bt.id = z.base_id LEFT JOIN info_base_tag AS f ON f.tag_code BETWEEN :fbfxstart AND :fbfxend AND bt.id = f.base_id LEFT JOIN info_base_tag AS d ON d.tag_code = :areaCode AND bt.id = d.base_id LEFT JOIN info_base_tag AS r ON r.tag_code BETWEEN :peoplestart AND :peopleend AND bt.id = r.base_id WHERE z.tag_code>0 AND d.tag_code>0 AND f.tag_code>0 AND r.tag_code>0 AND  bt.title NOT LIKE '%样表%' AND bt.title like :title ")
	BigInteger findBaseForfbfxAndzsxzAndareaAndPeopleAndTitleCount( @Param("fbfxstart")String fbfxstart,@Param("fbfxend")String fbfxend,@Param("zsxzstart")String zsxzstart,@Param("zsxzend")String zsxzend,@Param("peoplestart")String peoplestart,@Param("peopleend")String peopleend,@Param("areaCode")String areaCode,@Param("title")String title);
	
}
