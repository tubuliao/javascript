package com.isoftstone.tyw.repository.info;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Form;

public interface FormDao extends PagingAndSortingRepository<Form,String> ,JpaSpecificationExecutor<Form>{
	Page<Form> findByInsertId(String insertId, Pageable pageable);
	
	Page<Form> findByInsertIdAndDescContentIsNull(String insertId, Pageable pageable);
	
	Page<Form> findByCreateDateGreaterThanAndInsertIdAndDescContentIsNull(Date modifyDate, String insertId, Pageable pageable);

	Page<Form> findByCreateDateGreaterThanAndInsertId(Date modifyDate, String insertId, Pageable pageable);

//	@Query(value="select form from Form form where form.title = :name and form.id in ( select base.id from Base base inner join base.tags tags where tags.name = :areaTag and base.infoType = 4) ")
//	public Form findOneByName(@Param("name")String name, @Param("areaTag")String areaTag) ;
	
	@Query(value="select form from Form form where form.title = :name and form.id in ( select base.id from Base base inner join base.tags tags where tags.name = :areaTag and base.infoType = 4 and base.source like :source) ")
	public Form findOneByName(@Param("name")String name, @Param("areaTag")String areaTag, @Param("source")String source) ;
	
//	@Query(nativeQuery=true, value=" SELECT * FROM info_form a, info_base b, info_base_tag c, info_tag d WHERE a.id = b.id AND b.id = c.base_id AND c.tag_id = d.id AND b.title = ?1 AND d.name = ?2 AND b.source LIKE ?3 ")
//	public Form findOneByName(String name, String areaTag, String source) ;
//	
	/**
	 * 源文件地址存数据库
	 * @param title
	 * @param path
	 * @param areaTag
	 * @return
	 */
	@Modifying 
	@Query("update Form form set form.empUrl = :path where form.title = :title and form.id in ( select base.id from Base base inner join base.tags tags where tags.name = :areaTag and base.infoType = 4 and base.source like :source) ") 
	public int updateEmpSourceUrl(@Param("title")String title, @Param("path")String path, @Param("areaTag")String areaTag, @Param("source")String source);
	

	/*
	@Modifying
	@Query("update Form form set form.sampUrl = :path where form.title = :title")
	public int updateSampSourceUrl(@Param("title")String title, @Param("path")String path) ;
	*/
	@Modifying
	@Query("update Form form set form.descUrl = :path where form.title = :title and form.id in ( select base.id from Base base inner join base.tags tags where tags.name = :areaTag and base.infoType = 4 and base.source like :source) ") 
	public int updateDescSourceUrl(@Param("title")String title, @Param("path")String path, @Param("areaTag")String areaTag, @Param("source")String source) ;
	
	
	
//	@Modifying
//	@Query("update Form form set form.empHiPicUrl = :path where form.title = :title and form.id in ( select base.id from Base base inner join base.tags tags where tags.name = :areaTag and base.infoType = 4 )")
//	public int updateEmpHiFirstPicUrl(@Param("title")String title, @Param("path")String path, @Param("areaTag")String areaTag) ;
	
	
	/**
	 * 空表或样表WEB端图片（一张）地址存数据库
	 * @param title
	 * @param path
	 * @return
	 */
//	@Modifying
//	@Query("update Form form set form.empHiPicUrl = :path where form.title = :title")
//	public int updateEmpHiFirstPicUrl(@Param("title")String title, @Param("path")String path/*, @Param("areaTag")String areaTag*/) ;
	
	/**
	 * 空表或样表WEB端图片（多张，‘;’分隔）地址存数据库
	 * @param title
	 * @param path
	 * @param areaTag
	 * @return
	 */
	@Modifying
	@Query("update Form form set form.empHiPicUrl = CONCAT(form.empHiPicUrl, ';', :path) where form.title = :title and form.id in ( select base.id from Base base inner join base.tags tags where tags.name = :areaTag and base.infoType = 4 and base.source like :source)")
	public int updateEmpHiNoFirstPicUrl(@Param("title")String title, @Param("path")String path, @Param("areaTag")String areaTag, @Param("source")String source) ;
	
	/**
	 * 空表或样表Mobile端图片（一张）地址存数据库
	 * @param title
	 * @param path
	 * @return
	 */
//	@Modifying
//	@Query("update Form form set form.empLowPicUrl = :path where form.title = :title")
//	public int updateEmpLowFirstPicUrl(@Param("title")String title, @Param("path")String path) ;
	
	/**
	 * 空表或样表Mobile端图片（多张， ‘;’分隔）地址存数据库
	 * @param title
	 * @param path
	 * @param areaTag
	 * @return
	 */
	@Modifying
	@Query("update Form form set form.empLowPicUrl = CONCAT(form.empLowPicUrl, ';', :path) where form.title = :title and form.id in ( select base.id from Base base inner join base.tags tags where tags.name = :areaTag and base.infoType = 4)")
	public int updateEmpLowNoFirstPicUrl(@Param("title")String title, @Param("path")String path, @Param("areaTag")String areaTag) ;
	
	/*
	@Modifying
	@Query("update Form form set form.sampHiPicUrl = :path where form.title = :title")
	public int updateSampHiFirstPicUrl(@Param("title")String title, @Param("path")String path) ;
	
	@Modifying
	@Query("update Form form set form.sampHiPicUrl = CONCAT(form.sampHiPicUrl, ';', :path, ';') where form.title = :title")
	public int updateSampHiNoFirstPicUrl(@Param("title")String title, @Param("path")String path) ;
	
	@Modifying
	@Query("update Form form set form.sampLowPicUrl = :path where form.title = :title")
	public int updateSampLowFirstPicUrl(@Param("title")String title, @Param("path")String path) ;
	
	@Modifying
	@Query("update Form form set form.sampLowPicUrl = CONCAT(form.sampLowPicUrl, ';', :path, ';') where form.title = :title")
	public int updateSampLowNoFirstPicUrl(@Param("title")String title, @Param("path")String path) ;
	*/
	@Modifying
	@Query("update Form form set form.empHiPicUrl = :url where form.id = :id")
	public void deleteHiPic(@Param("id")String id, @Param("url")String url) ;
	
	@Modifying
	@Query("update Form form set form.empLowPicUrl = :url where form.id = :id")
	public void deleteLowPic(@Param("id")String id, @Param("url")String url) ;
	
	@Query(nativeQuery=true, value="SELECT c.name FROM info_form a, info_base_tag b, info_tag c WHERE a.id = b.base_id AND b.tag_id = c.id AND c.code LIKE '2%' AND a.id = :id")
	public String findAreaTagById(@Param("id")String id);
	
	@Query(nativeQuery=true, value=" SELECT a.*, b.* FROM info_form a, info_base b, info_base_tag c, info_tag d WHERE a.id = b.id AND b.id = c.base_id AND c.tag_id = d.id AND b.title LIKE '%样表%' AND b.source = :formSource AND d.code LIKE '2%' AND d.name =:formAreaTag AND b.title LIKE :formTitle AND a.emp_hi_pic_url != '' LIMIT :pageIndex, :itemsPerPage ")
	public List<Form> findExampleFormList(@Param("formTitle")String formTitle, @Param("formSource")String formSource, @Param("formAreaTag")String formAreaTag, @Param("pageIndex")Integer pageIndex, @Param("itemsPerPage")Integer itemsPerPage);
	
	@Query(nativeQuery=true, value=" SELECT COUNT(*) FROM info_form a, info_base b, info_base_tag c, info_tag d WHERE a.id = b.id AND b.id = c.base_id AND c.tag_id = d.id AND b.title LIKE '%样表%' AND b.source = :formSource AND d.code LIKE '2%' AND d.name =:formAreaTag AND b.title LIKE :formTitle AND a.emp_hi_pic_url != '' ")
	public BigInteger findExampleFormListCount(@Param("formTitle")String formTitle, @Param("formSource")String formSource, @Param("formAreaTag")String formAreaTag);
	
	@Query(nativeQuery=true, value=" SELECT a.*, b.* FROM info_form a, info_base b, info_base_tag c, info_tag d WHERE a.id = b.id AND b.id = c.base_id AND c.tag_id = d.id AND b.title LIKE '%样表%' AND d.code LIKE '2%' AND b.title LIKE :formTitle AND a.emp_hi_pic_url != '' LIMIT :pageIndex, :itemsPerPage ")
	public List<Form> findOtherExampleFormList(@Param("formTitle")String formTitle, @Param("pageIndex")Integer pageIndex, @Param("itemsPerPage")Integer itemsPerPage);
	
	@Query(nativeQuery=true, value=" SELECT COUNT(*) FROM info_form a, info_base b, info_base_tag c, info_tag d WHERE a.id = b.id AND b.id = c.base_id AND c.tag_id = d.id AND b.title LIKE '%样表%' AND d.code LIKE '2%' AND b.title LIKE :formTitle AND a.emp_hi_pic_url != '' ")
	public BigInteger findOtherExampleFormListCount(@Param("formTitle")String formTitle);
	
	
	@Query(nativeQuery=true,value="select * from info_base base ,info_form form where form.id=base.id and form.id=:id")
	Form findForm(@Param("id")String id);
}
