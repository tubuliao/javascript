package com.isoftstone.tyw.repository.info;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.BaseSourceModule;
import com.isoftstone.tyw.entity.info.BaseTitleModule;
import com.isoftstone.tyw.entity.info.BookDirModule;

public interface BaseDao  extends PagingAndSortingRepository<Base, String>, JpaSpecificationExecutor<Base>{
	
	Page<Base> findByModifyDateGreaterThanAndInfoType(Date modifyDate, int infoType, Pageable pageable);

	Page<Base> findByInfoType(int infoType, Pageable pageable);
	
	@Modifying 
	@Query(nativeQuery=true,value="update info_base base set base.apply_detele =:appleDetele where base.id=:id")
	int updateById(@Param("id")String id,@Param("appleDetele")Integer appleDetele);
	
	@Query("select base from Base base where base.source=:source order by segItem ASC")
	Page<Base> findBySource(@Param("source")String source, Pageable pageable);
	
	@Query("select base from Base base where base.title=:title")
	Page<Base> findByTitle(@Param("title")String title,Pageable pageable);
	
	//Page<Base> findByTitleLike(String title,Pageable pageable);
	
	@Query("select base from Base base left join fetch base.tags tag where tag.id=:tagId")
	Page<Base> findByTag(@Param("tagId")String tagId,Pageable pageable);
	
	@Query("select base from Base base left join fetch base.tags tag where tag.code=:code and base.infoType=:infoType")
	List<Base> findBaseListByTag(@Param("code")Long code,@Param("infoType")Integer infoType);
	
	
	@Query("select base from Base base left join fetch base.tags tag where tag.id=:tagId and base.title=:title")
	Page<Base> findByTagAndTitle(@Param("tagId")String tagId,@Param("title")String title,Pageable pageable);
	
	Page<Base> findByStateAndInfoTypeAndInsertId(Integer state,int infoType,String insertId, Pageable pageable);
	

    @Query(nativeQuery=true,value="select ib.id,ib.create_date,ib.title,ib.source from info_base ib where  ib.id = :id")
    public Base findBaseById(@Param("id")String id);
    
    @Query(nativeQuery=true,value="select ib.id,ib.info_type,ib.create_date,ib.title,ib.source from info_base ib where  ib.id = :id")
    public Base findBaseModuleById(@Param("id")String id);
	
    
    @Query(nativeQuery=true,value="select ib.id,ib.title from info_tag tag,info_base_tag ibt,info_base ib where tag.id = ibt.tag_id and tag.code like :code  and ib.id =ibt.base_id")
    public List<Base> searchBase(@Param("code")String code);
    
	@Modifying 
	@Query("update Base base set base.state = :status,base.checkId= :checkId,base.checkName=:checkName,base.checkDate=:checkDate where base.id =:id") 
	public int modifyStatus(@Param("status")Integer status,@Param("checkId")String checkId,@Param("checkName")String checkName,@Param("checkDate")Date checkDate,@Param("id")String id); 
	
	@Modifying 
	@Query("update Base base set base.state = :status,base.auditInfo=:auditInfo,base.checkId= :checkId,base.checkName=:checkName,base.checkDate=:checkDate where base.id =:id") 
	public int modifyStatus(@Param("status")Integer status,@Param("checkId")String checkId,@Param("checkName")String checkName,@Param("checkDate")Date checkDate,@Param("auditInfo")String auditInfo,@Param("id")String id);
	
	@Modifying  
    @Query(nativeQuery=true,value="update info_base base set base.click_count=(1+click_count) where base.id=:id")
    public int modifyclickCount(@Param("id")String id); 
	
    Page<Base> findByRecommendOrderByModifyDateDesc(Integer recommend,Pageable pageable);
    
    /*@Query(nativeQuery=true,value=" select  distinct uuid() as id, bt.source"
    		+" from info_base as bt"
    		+" left join info_base_tag as z "
    		+" on z.tag_code BETWEEN 40201000000000000 and 40201999999999999"
    		+" and bt.id = z.base_id"
    		+" left join info_base_tag as f "
    		+" on f.tag_code BETWEEN :start and :end"
    		+" and bt.id = f.`base_id`"
    		+" left join info_base_tag as d "
    		+" on d.tag_code BETWEEN 20000000000000000 and 29999999999999999"
    		+" and bt.id = d.base_id"
    		+" left join info_base_tag as r "
    		+" on r.tag_code BETWEEN 30000000000000000 and 39999999999999999"
    		+" and bt.id = r.base_id"
    		+" where z.tag_code>0 and f.tag_code>0"
    		+"  limit :count,:pageSize"
    		)
	List<Base> findBaseForTags(@Param("start")String start,@Param("end")String end, @Param("count")Integer count ,@Param("pageSize")Integer pageSize);
	*/
    
    /**
	 * 根据 基本两项
	 * 核心条文、分部分项 
	 * 查询来源
	 * @param start
	 * @param end
	 * @return
	 */
	/*@Query(nativeQuery=true,value=" select  distinct uuid() as id, bt.source as source"
    		+" from info_base as bt"
    		+" left join info_base_tag as z "
    		+" on z.tag_code BETWEEN 40202000000000000 and 40202999999999999"
    		+" and bt.id = z.base_id"
    		+" left join info_base_tag as f "
    		+" on f.tag_code BETWEEN :start and :end"
    		+" and bt.id = f.`base_id`"
    		+" left join info_base_tag as d "
    		+" on d.tag_code BETWEEN 20000000000000000 and 29999999999999999"
    		+" and bt.id = d.base_id"
    		+" left join info_base_tag as r "
    		+" on r.tag_code BETWEEN 30000000000000000 and 39999999999999999"
    		+" and bt.id = r.base_id"
    		+" where z.tag_code>0 and f.tag_code>0"
    		)*/
	@Query(nativeQuery=true,value=" select  distinct uuid() as id, bt.source as source from info_base as bt left join info_base_tag as z  on z.tag_code BETWEEN 40202000000000000 and 40202999999999999 and bt.id = z.base_id left join info_base_tag as f  on f.tag_code BETWEEN :start and :end and bt.id = f.`base_id` left join info_base_tag as d  on d.tag_code BETWEEN 20000000000000000 and 29999999999999999 and bt.id = d.base_id left join info_base_tag as r  on r.tag_code BETWEEN 30000000000000000 and 39999999999999999 and bt.id = r.base_id where z.tag_code>0 and f.tag_code>0"
    		)
	List<BaseSourceModule> findBaseForTags(@Param("start")String start,@Param("end")String end);	
	
	/**
	 * 根据后1项
	 * 核心条文、分部分项 和 来源(标准)
	 * 查询来源
	 * @param start
	 * @param end
	 * @param source
	 * @return
	 */
	/*@Query(nativeQuery=true,value=" select  distinct uuid() as id, bt.source as source"
    		+" from info_base as bt"
    		+" left join info_base_tag as z "
    		+" on z.tag_code BETWEEN 40202000000000000 and 40202999999999999"
    		+" and bt.id = z.base_id"
    		+" left join info_base_tag as f "
    		+" on f.tag_code BETWEEN :start and :end"
    		+" and bt.id = f.`base_id`"
    		+" left join info_base_tag as d "
    		+" on d.tag_code BETWEEN 20000000000000000 and 29999999999999999"
    		+" and bt.id = d.base_id"
    		+" left join info_base_tag as r "
    		+" on r.tag_code BETWEEN 30000000000000000 and 39999999999999999"
    		+" and bt.id = r.base_id"
    		+" where z.tag_code>0 and f.tag_code>0 and bt.source like :source"
    		)*/
	@Query(nativeQuery=true,value=" select  distinct uuid() as id, bt.source as source from info_base as bt left join info_base_tag as z  on z.tag_code BETWEEN 40202000000000000 and 40202999999999999 and bt.id = z.base_id left join info_base_tag as f  on f.tag_code BETWEEN :start and :end and bt.id = f.`base_id` left join info_base_tag as d  on d.tag_code BETWEEN 20000000000000000 and 29999999999999999 and bt.id = d.base_id left join info_base_tag as r  on r.tag_code BETWEEN 30000000000000000 and 39999999999999999 where z.tag_code>0 and f.tag_code>0 and bt.source like :source")
	List<BaseSourceModule> findBaseForTagsAndSource(@Param("start")String start,@Param("end")String end,@Param("source")String source);
	
	
	/**
	 * 根据后1项
	 * 核心条文、分部分项 和 标题
	 * 查询来源
	 * @param start
	 * @param end
	 * @param title
	 * @return
	 */
	/*@Query(nativeQuery=true,value=" select  distinct uuid() as id, bt.source as source"
    		+" from info_base as bt"
    		+" left join info_base_tag as z "
    		+" on z.tag_code BETWEEN 40202000000000000 and 40202999999999999"
    		+" and bt.id = z.base_id"
    		+" left join info_base_tag as f "
    		+" on f.tag_code BETWEEN :start and :end"
    		+" and bt.id = f.`base_id`"
    		+" left join info_base_tag as d "
    		+" on d.tag_code BETWEEN 20000000000000000 and 29999999999999999"
    		+" and bt.id = d.base_id"
    		+" left join info_base_tag as r "
    		+" on r.tag_code BETWEEN 30000000000000000 and 39999999999999999"
    		+" and bt.id = r.base_id"
    		+" where z.tag_code>0 and f.tag_code>0 and bt.title like :title"
    		)*/
	@Query(nativeQuery=true,value=" select  distinct uuid() as id, bt.source as source from info_base as bt left join info_base_tag as z  on z.tag_code BETWEEN 40202000000000000 and 40202999999999999 and bt.id = z.base_id left join info_base_tag as f  on f.tag_code BETWEEN :start and :end and bt.id = f.`base_id` left join info_base_tag as d  on d.tag_code BETWEEN 20000000000000000 and 29999999999999999 and bt.id = d.base_id left join info_base_tag as r  on r.tag_code BETWEEN 30000000000000000 and 39999999999999999 and bt.id = r.base_id where z.tag_code>0 and f.tag_code>0 and bt.title like :title")
	List<BaseSourceModule> findBaseForTagsAndTitle(@Param("start")String start,@Param("end")String end,@Param("title")String title);
	
	@Query(nativeQuery=true,value=" select  distinct bt.id as id, bt.title as title,seg.seg_item as segItem from info_base as bt left join info_base_tag as z on z.tag_code BETWEEN 40202000000000000 and 40202999999999999 and bt.id = z.base_id left join info_base_tag as f on f.tag_code BETWEEN :start and :end and bt.id = f.`base_id` left join info_base_tag as d on d.tag_code BETWEEN 20000000000000000 and 29999999999999999 and bt.id = d.base_id left join info_base_tag as r on r.tag_code BETWEEN 30000000000000000 and 39999999999999999 and bt.id = r.base_id left join info_segment as seg on bt.id = seg.id where z.tag_code>0 and f.tag_code>0 and bt.title like :title order by segItem * 1 ASC")
	List<BookDirModule> findTitleForTagsAndTitle(@Param("start")String start,@Param("end")String end,@Param("title")String title);

	/**
	 * 根据后2项
	 * 核心条文、分部分项 和 标题、来源（标准）
	 * @param start
	 * @param end
	 * @param title
	 * @param source
	 * @return
	 */
	@Query(nativeQuery=true,value=" select  distinct uuid() as id, bt.source as source from info_base as bt left join info_base_tag as z on z.tag_code BETWEEN 40202000000000000 and 40202999999999999 and bt.id = z.base_id left join info_base_tag as f on f.tag_code BETWEEN :start and :end and bt.id = f.`base_id` left join info_base_tag as d on d.tag_code BETWEEN 20000000000000000 and 29999999999999999 and bt.id = d.base_id left join info_base_tag as r on r.tag_code BETWEEN 30000000000000000 and 39999999999999999 and bt.id = r.base_id where z.tag_code>0 and f.tag_code>0 and bt.title like :title and bt.source like :source")
	List<BaseSourceModule> findBaseForTagsAndTitleAndSource(@Param("start")String start,@Param("end")String end,@Param("title")String title,@Param("source")String source);

	@Query(nativeQuery=true,value=" select  distinct bt.id as id, bt.title as title,seg.seg_item as segItem from info_base as bt left join info_base_tag as z on z.tag_code BETWEEN 40202000000000000 and 40202999999999999 and bt.id = z.base_id left join info_base_tag as f on f.tag_code BETWEEN :start and :end and bt.id = f.`base_id` left join info_base_tag as d on d.tag_code BETWEEN 20000000000000000 and 29999999999999999 and bt.id = d.base_id left join info_base_tag as r on r.tag_code BETWEEN 30000000000000000 and 39999999999999999 and bt.id = r.base_id left join info_segment as seg on bt.id = seg.id where z.tag_code>0 and f.tag_code>0 and bt.title like :title and bt.source like :source order by segItem * 1 ASC")
	List<BaseTitleModule> findBaseForTitleAndTitleAndSource(@Param("start")String start,@Param("end")String end,@Param("title")String title,@Param("source")String source);
	
	/**
	 * 根据后2项
	 * 核心条文、分部分项 和 标准、来源
	 * @param start
	 * @param end
	 * @param title
	 * @param standard
	 * @param source
	 * @return
	 */
	@Query(nativeQuery=true,value=" select  distinct uuid() as id, bt.source as source from info_base as bt left join info_base_tag as z on z.tag_code BETWEEN 40202000000000000 and 40202999999999999 and bt.id = z.base_id left join info_base_tag as f on f.tag_code BETWEEN :start and :end and bt.id = f.`base_id` left join info_base_tag as d on d.tag_code BETWEEN 20000000000000000 and 29999999999999999 and bt.id = d.base_id left join info_base_tag as r on r.tag_code BETWEEN 30000000000000000 and 39999999999999999 and bt.id = r.base_id where z.tag_code>0 and f.tag_code>0 and bt.source like :standard and bt.source like :source")
	List<BaseSourceModule> findBaseForTagsAndStandardAndSource(@Param("start")String start,@Param("end")String end,@Param("standard")String standard,@Param("source")String source);

	/**
	 * 根据后3项
	 * 核心条文、分部分项 和 标题、来源、标准
	 * @param start
	 * @param end
	 * @param title
	 * @param standard
	 * @param source
	 * @return
	 */
	@Query(nativeQuery=true,value=" select  distinct uuid() as id, bt.source as source from info_base as bt left join info_base_tag as z on z.tag_code BETWEEN 40202000000000000 and 40202999999999999 and bt.id = z.base_id left join info_base_tag as f on f.tag_code BETWEEN :start and :end and bt.id = f.`base_id` left join info_base_tag as d on d.tag_code BETWEEN 20000000000000000 and 29999999999999999 and bt.id = d.base_id left join info_base_tag as r on r.tag_code BETWEEN 30000000000000000 and 39999999999999999 and bt.id = r.base_id where z.tag_code>0 and f.tag_code>0 and bt.title like :title and bt.source like :standard and bt.source like :source")
	List<BaseSourceModule> findBaseForTagsAndTitleAndStandardAndSource(@Param("start")String start,@Param("end")String end,@Param("title")String title,@Param("standard")String standard,@Param("source")String source);

	@Query(nativeQuery=true,value=" select  distinct bt.id as id, bt.title as title,seg.seg_item as segItem from info_base as bt left join info_base_tag as z on z.tag_code BETWEEN 40202000000000000 and 40202999999999999 and bt.id = z.base_id left join info_base_tag as f on f.tag_code BETWEEN :start and :end and bt.id = f.`base_id` left join info_base_tag as d on d.tag_code BETWEEN 20000000000000000 and 29999999999999999 and bt.id = d.base_id left join info_base_tag as r on r.tag_code BETWEEN 30000000000000000 and 39999999999999999 and bt.id = r.base_id left join info_segment as seg on bt.id = seg.id where z.tag_code>0 and f.tag_code>0 and bt.title like :title and bt.source like :standard and bt.source like :source order by segItem * 1 ASC")
	List<BaseTitleModule> findBaseForTitleAndTitleAndStandardAndSource(@Param("start")String start,@Param("end")String end,@Param("title")String title,@Param("standard")String standard,@Param("source")String source);
	@Query(value="from Base base where base.id = :id")
	public Base findOneById(@Param("id")String id);
	
	@Modifying
	@Query(nativeQuery=true, value="INSERT INTO info_base_tag VALUES(:baseId, :tagId, :tagCode, :tagName, :title, :source)")
	public void saveBaseAndTag(@Param("baseId")String baseId, @Param("tagId")String tagId, @Param("tagCode")Long tagCode, @Param("tagName")String tagName, @Param("title")String title, @Param("source")String source);

	
	/**
	 * 保存基础(base)单表信息
	 * @param id
	 * @param title
	 * @param infoType
	 * @param insertId
	 * @param insertName
	 * @param source
	 * @return
	 */
	@Modifying
	@Query(nativeQuery=true,value="insert into info_base(id,title,create_date,info_type,insert_id,insert_name,state,source,click_count,recommend)value(:id,:title,now(),:infoType,:insertId,:insertName,0,:source,0,0)")
    public int insertBase(@Param("id")String id,@Param("title")String title,@Param("infoType")int infoType,@Param("insertId")String insertId,@Param("insertName")String insertName,@Param("source")String source);

	/**
	 * 保存基础(base)单表信息
	 * @param id
	 * @param title
	 * @param infoType
	 * @param insertId
	 * @param insertName
	 * @param source
	 * @return
	 */
	@Modifying
	@Query(nativeQuery=true,value="insert into info_base(id,title,create_date,modify_date,info_type,insert_id,insert_name,state,source,click_count,recommend,short_title)value(:id,:title,now(),now(),:infoType,:insertId,:insertName,0,:source,0,0,:shortName)")
    public int insertBase(@Param("id")String id,@Param("title")String title,@Param("infoType")int infoType,@Param("insertId")String insertId,@Param("insertName")String insertName,@Param("source")String source,@Param("shortName")String shortName);

	/**
	 * 保存基础(base)单表信息
	 * @param id
	 * @param title
	 * @param infoType
	 * @param insertId
	 * @param insertName
	 * @param source
	 * @param shortName
	 * @param begincreateDate
	 * @return
	 */
	@Modifying
	@Query(nativeQuery=true,value="insert into info_base(id,title,create_date,modify_date,info_type,insert_id,insert_name,state,source,click_count,recommend,short_title,begincreate_date)value(:id,:title,now(),now(),:infoType,:insertId,:insertName,0,:source,0,0,:shortName,:begincreateDate)")
    public int insertBase(@Param("id")String id,@Param("title")String title,@Param("infoType")int infoType,@Param("insertId")String insertId,@Param("insertName")String insertName,@Param("source")String source,@Param("shortName")String shortName,@Param("begincreateDate")Date begincreateDate);


	/**
	 * 保存中间表(info_base_tag)信息
	 * @param baseId
	 * @param tagId
	 * @return
	 */
	@Modifying
	@Query(nativeQuery=true,value="insert into info_base_tag(base_id,tag_id)value(:baseId,:tagId)")
	public int insertBaseTag(@Param("baseId")String baseId,@Param("tagId")String tagId);
	
	/**
	 * 根据BaseId删除中间表和Base关联的Tag
	 * @param baseId
	 * @return
	 */
	@Modifying
	@Query(nativeQuery=true,value="delete from info_base_tag where base_id=:baseId")
	public int deleteBaseTag(@Param("baseId")String baseId);
	
	/**
	 * 根据BaseId删除Base
	 * @param baseId
	 * @return
	 */
	@Modifying
	@Query(nativeQuery=true,value="delete from info_base where id=:baseId")
	public int deleteBase(@Param("baseId")String baseId);
	
	@Modifying
	@Query(nativeQuery=true, value="UPDATE info_base b SET b.weighing = :wVal WHERE b.id = :id")
	public void updateTitleWeighing(@Param("id")String id, @Param("wVal")int wVal);
	
	@Modifying
	@Query(nativeQuery=true, value="UPDATE info_base b SET b.weighing = :wVal WHERE b.source = :source")
	public void updateSourceWeighing(@Param("source")String source, @Param("wVal")int wVal);
	    
	@Modifying
	@Query(nativeQuery=true, value="UPDATE info_base b SET b.modify_date = :modifyDate, b.modify_id = :modifyId, b.modify_name = :modifyName WHERE b.id = :id")
	public void updateModifyInfo(@Param("id")String id, @Param("modifyDate")Date modifyDate, @Param("modifyId")String modifyId, @Param("modifyName")String modifyName);
	
	@Modifying
	@Query(nativeQuery=true, value="UPDATE info_base b SET b.state = :state, b.check_date = :checkDate, b.check_id = :checkId, b.check_name = :checkName WHERE b.id = :id")
	public void updateApprovalState(@Param("id")String id, @Param("state")Integer state, @Param("checkDate")Date checkDate, @Param("checkId")String checkId, @Param("checkName")String checkName);
	
	@Modifying
	@Query(nativeQuery=true, value="UPDATE info_base b SET b.source = :source, b.modify_date = :modifyDate, b.modify_id = :modifyId, b.modify_name = :modifyName WHERE b.id = :id")
	public void updateApprovalSource(@Param("id")String id, @Param("source")String source, @Param("modifyDate")Date modifyDate, @Param("modifyId")String modifyId, @Param("modifyName")String modifyName);
	
	@Query(nativeQuery=true, value="SELECT DISTINCT weighing FROM info_base WHERE info_type = :infoType ORDER BY weighing")
	public List<String> findKnowledgeLevelSort(@Param("infoType")String infoType);

}
