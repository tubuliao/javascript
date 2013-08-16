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

import com.isoftstone.tyw.entity.auths.Agent;
import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.KnowledgeSort;
import com.isoftstone.tyw.entity.info.PdTag;
import com.isoftstone.tyw.entity.info.Tag;
import com.isoftstone.tyw.entity.info.TagPath;

public interface TagDao  extends PagingAndSortingRepository<Tag, String>, JpaSpecificationExecutor<Tag>{
	
	
	
	
	
	/**
	 * 2013年7月10日优化
	 * 张一青
	 * 获取某个Tag所有得子Tag的分页列表
	 * 
	 * @param parentId
	 * @param pageable
	 * @return
	 */
	//@Query("select tag from Tag tag where tag.status=true and tag.parentId=:parentId order by tag.tagNo asc")
	@Query(nativeQuery=true,value="select tag.* from info_tag tag where tag.status=1 and tag.parent_id = :parentId order by tag.tag_no asc")
	Tag findTagByParentId8(@Param("parentId") String parentId);
	
	
	/**
	 * 2013年7月10日优化
	 * 张一青
	 * 获取某个Tag所有得子Tag的分页列表
	 * 
	 * @param parentId
	 * @param pageable
	 * @return
	 */
	//@Query("select tag from Tag tag where  tag.parentId=:parentId and tag.status=true order by tag.tagNo asc")
	@Query(nativeQuery=true,value="select tag.* from info_tag tag where tag.status=true and tag.parent_id = :parentId order by tag.tag_no asc")
	Page<Tag> findAllTagByParentId(@Param("parentId") String parentId, Pageable pageable);

	
	/**
	 * 根据内容类型查询所有标签
	 * 
	 * @param infoType
	 * @param pageable
	 * @return
	@Query("select tag from Tag tag, TagMap tagMap where tag.status=true And tag.code Between tagMap.tagCodeFrom and tagMap.tagCodeTo And tagMap.infoType=:infoType order by tag.sortNo asc")
	Page<Tag> findByInfoType(@Param("infoType") Integer infoType, Pageable pageable);
	 */
	

	@Query("select tag from Tag tag where tag.status=true order by tag.sortNo asc")
	Page<Tag> findByInfoType(Pageable pageable);
	/**
	 * 根据内容类型及时间戳字段分页查询所有标签
	 *
	 * @param infoType
	 * @param syncDate
	 * @param pageable
	 * @return

	@Query("select tag from Tag tag, TagMap tagMap where tag.status=true And tag.code Between tagMap.tagCodeFrom and tagMap.tagCodeTo And tagMap.infoType=:infoType And tag.dataDate > :syncDate Order by tag.sortNo asc")
	Page<Tag> findByDataDateGreaterThanAndInfoType(@Param("infoType") Integer infoType, @Param("syncDate") Date syncDate, Pageable pageable);
	 *	
	 */
	@Query("select tag from Tag tag where tag.status=true And tag.dataDate > :syncDate Order by tag.sortNo asc")
	Page<Tag> findByDataDateGreaterThanAndInfoType( @Param("syncDate")Date syncDate, Pageable pageable);

	
	
	/**
	 * 查询正在被使用着的标签
	 * @param id
	 * @return
	 */
	@Query(nativeQuery=true,value="select tag.* from info_tag tag ,info_base_tag baseTag where tag.id=baseTag.tag_id and baseTag.tag_id=:id and tag.status=1")
	List<Tag> findUseingTagById(@Param("id")String id);

	/**
	 * 标签的统计查询
	 * @return
	 */
	@Query(nativeQuery=true,value="SELECT child.id,child.code,'' as recommend,now() as data_date,child.leaf,child.level,'' as parent_id,'' as parent_code,'' as tag_no,child.status,''as summary,child.sort_no, concat( child.name, ' (', COUNT(child.code)-1, ')' ) AS NAME FROM info_tag AS tag, ( SELECT tag.id, tag.code,tag.status,tag.sort_no, tag.name,tag.leaf, tag.level, tag.data_date FROM info_tag tag WHERE  tag.parent_id IS NULL or tag.parent_id='' AND tag.status = 1) AS child WHERE tag.code BETWEEN child.code AND ( child.code + ( 99 * POW(100, 9 -(child.level + 1)))) and tag.status=1 GROUP BY child.code ORDER BY child.sort_no, child.data_date")
	List<Tag> findTagRootForCount();
	
	@Query(nativeQuery=true,value="SELECT child.id,child.code,'' as recommend,now() as data_date,child.leaf,child.level,'' as parent_id,'' as parent_code,'' as tag_no,child.status,''as summary,child.sort_no, concat( child.name, ' (', COUNT(child.code)-1, ')' ) AS NAME FROM info_tag AS tag, ( SELECT tag.id, tag.code,tag.status, tag.name,tag.leaf,tag.sort_no, tag.level, tag.data_date FROM info_tag tag WHERE  tag.parent_id = :parentId AND tag.status = 1) AS child WHERE tag.code BETWEEN child.code AND ( child.code + ( 99 * POW(100, 9 -(child.level + 1)))) and tag.status=1 GROUP BY child.code ORDER BY child.sort_no, child.data_date")
	List<Tag> findTagForCount(@Param("parentId") String parentId);
	
	@Query("select tag from Tag tag where tag.status=true and (tag.parentId=null or tag.parentId='') order by tag.sortNo asc")
	List<Tag> findRootTags();
	
	@Query("select tag from Tag tag where tag.status=true and tag.parentId=:parentId order by tag.sortNo asc")
	List<Tag> findTagByParentId(@Param("parentId") String parentId);
	
	/**
	 * 2013年7月10日优化
	 * 张一青
	 * @param name
	 * @return
	 */
	//@Query("select tag from Tag tag where tag.status=true and tag.name=:name  ")
	@Query(nativeQuery=true,value="select tag.* from info_tag tag where tag.status=true and tag.name = :name")
    Tag findTagByTagName(@Param("name") String name);
	
	
	
	/**
	 * 根据知识id查询分部分项标签
	 * @param id
	 * @return
	 */
	@Query(nativeQuery=true,value="SELECT it.* FROM info_tag it LEFT JOIN ( SELECT t.* FROM info_base_tag t  WHERE t.base_id = :id AND t.tag_code > 10000000000000000 AND t.tag_code < 20000000000000000 ) tt ON it.code = tt.tag_code WHERE tt.tag_code>0")
    List<Tag> findTagListByBaseId(@Param("id") String id);
	
	/**
	 * 2013年7月10日优化
	 * 张一青
	 * @param parentCode
	 * @return
	 */
	//@Query("select tag from Tag tag where  tag.parentCode=:parentCode order by tag.sortNo asc")
	@Query(nativeQuery=true,value="select tag.* from info_tag tag where tag.parent_code = :parentCode order by tag.sort_no asc")
    List<Tag> findTagByparentCode(@Param("parentCode") Long parentCode);
	
	/**
	 * 2013年7月10日优化
	 * 张一青
	 * @return
	 */
	//@Query("select tag from Tag tag where  searchhotword=1   order by tag.sortNo asc")
	@Query(nativeQuery=true,value="select tag.* from info_tag tag where tag.searchhotword=1 order by tag.sort_no asc")
	List<Tag> findsearchhotword();
	   
	@Modifying 
	@Query("update Tag tag set tag.status = false where tag.id =:id") 
	public int modifyStatus(@Param("id")String id); 
	
	@Modifying 
	@Query("update Tag tag set tag.leaf = :leaf where tag.id =:id") 
	public int modifyLeaf(@Param("id")String id,@Param("leaf")Integer leaf); 
	
	/**
	 * 2013年7月9日优化
	 * 张一青
	 * 切片标签的统计查询
	 * @return
	 */
	@Query(nativeQuery=true,value="select r.id,'' as recommend,r.status,r.parent_id,r.code,r.parent_code,r.level,r.leaf,r.tag_no,r.sort_no,r.data_date,r.summary,CONCAT(r.name,' (',y.info_count,')') as name from info_tag as r inner join (select p.id,COUNT(t.id) as info_count  from  info_base_tag as bt inner join info_tag as t on bt.`tag_id` = t.id and t.status=1 right join info_tag as p on t.code BETWEEN p.CODE AND (p.CODE + (99 * POW(100, 9 -(p.LEVEL + 1))))  and p.status=1 group by p.code) as y on r.id=y.id where r.status=1 and (r.parent_Id is null or r.parent_Id='')")
	List<Tag> findSegmentTagRootForCount();
	
	
	/**
	 * 2013年7月9日优化
	 * 张一青
	 * 切片标签的统计查询
	 * @param parentId
	 * @return
	 */
	@Query(nativeQuery=true,value="select r.id,'' as recommend,r.status,r.parent_id,r.code,r.parent_code,r.level,r.leaf,r.tag_no,r.sort_no,r.data_date,r.summary,CONCAT(r.name,' (',y.info_count,')') as name from info_tag as r inner join (select p.id,COUNT(t.id) as info_count  from  info_base_tag as bt inner join info_tag as t on bt.`tag_id` = t.id and t.status=1 right join info_tag as p on t.code BETWEEN p.CODE AND (p.CODE + (99 * POW(100, 9 -(p.LEVEL + 1))))  and p.status=1 group by p.code) as y on r.id=y.id where r.status=1 and r.parent_Id=:parentId")
	List<Tag> findSegmentTagForCount(@Param("parentId") String parentId);

	
//	@Query(nativeQuery=true,value="select r.id,r.status,r.parent_id,r.code,r.parent_code,r.level,r.leaf,r.tag_no,r.sort_no,r.data_date,r.summary,CONCAT(r.name,' (',y.info_count,'/',y.info_count_distinct,')') as name from info_tag as r " +
//			"inner join (select p.id,COUNT(t.id) as info_count,COUNT(distinct bt.base_id) as info_count_distinct  from  info_base_tag as bt inner join info_tag as t on bt.`tag_id` = t.id and t.status=1 right join info_tag as p on t.code BETWEEN " +
//			"p.CODE AND (p.CODE + (99 * POW(100, 9 -(p.LEVEL + 1))))  and p.status=1 group by p.code) as y on r.id=y.id where r.status=1 and r.parent_Id=:parentId")
//	List<Tag> findSegmentTagForCount(@Param("parentId") String parentId);
	
	
	
	@Query(nativeQuery=true,value="select tag.* from info_tag tag where tag.status=1 and tag.code = (select max(code) from info_tag where  parent_Id=:parentId)")
	Tag findTagByMaxCode(@Param("parentId")String parentId);
	
	@Query(nativeQuery=true,value="select tag.* from info_tag tag where tag.status=1 and (tag.parent_Id is null or tag.parent_Id='') order by tag.sort_no desc limit 1")
	Tag findMaxSortNoByRoot();
	
	@Query(nativeQuery=true,value="select tag.* from info_tag tag where tag.status=1 and tag.parent_Id=:parentId order by sort_no desc limit 1")
	Tag findMaxSortNoByParentId(@Param("parentId")String parentId);
	
	@Query(nativeQuery=true, value="select tag.* from info_tag tag where tag.name = :name and tag.status = 1")
	Tag findTagByName(@Param("name")String name);
	
	@Query(nativeQuery=true, value="select tag.* from info_tag tag where code between :codestart and :codeend and tag.level = 3")
    List<Tag> findTagByCodeLevel(@Param("codestart")Long codestart,@Param("codeend")Long codeend);
	
	/**
	 * 标签的统计查询（优化后）
	 * @param parentId
	 * @return
	 */
	@Query(nativeQuery=true, value="SELECT tag.id, '' as recommend,tag.code, now() AS data_date, tag.leaf, tag.level, '' AS parent_id, '' as parent_code, '' as tag_no, tag.status, '' AS summary, tag.sort_no, tag.name AS name, tag.searchhotword AS searchhotword FROM info_tag tag WHERE tag.status = '1' AND tag.parent_id = :parentId ORDER BY tag.sort_no, tag.data_date ")
	List<Tag> findTagListByParentId(@Param("parentId") String parentId) ;
	
	/**
	 * 标签的统计查询（根标签）
	 * @return
	 */
	@Query(nativeQuery=true, value="SELECT tag.id, '' as recommend,tag.code, now() AS data_date, tag.leaf, tag.level, '' AS parent_id, '' as parent_code, '' as tag_no, tag.status, '' AS summary, tag.sort_no, tag.name AS name, tag.searchhotword AS searchhotword FROM info_tag tag WHERE tag.status = '1' AND (tag.parent_id = '' OR tag.parent_id IS NULL) ORDER BY tag.sort_no, tag.data_date")
	List<Tag> findTagList() ;
	
	/**
	 * 2013年7月9日优化
	 * 张一青
	 * @param code
	 * @return
	 */
	@Query(nativeQuery=true,value="select tag.* from info_tag tag where tag.code = :code")
	Tag findByCode(@Param("code") Long code);
	
	Tag findByName(String name);
	
	@Query(nativeQuery=true,value="select tag.* from info_tag tag where tag.parent_id = :parentId")
	List<Tag> findTagForCount1(@Param("parentId") String parentId);
	
	@Query(nativeQuery=true,value="select * from info_tag  where code like '1__00000000000000'  and code > 10000000000000000 order by code")
	List<Tag> findTagForCode();
 	
	@Query(nativeQuery=true,value="select * from info_tag  where code like :scode  and code > :ecode order by code")
	List<Tag> findTagForCode(@Param("scode") String scode,@Param("ecode") String ecode);
 	
    @Query(nativeQuery=true,value="select ib.id,ib.title from info_tag tag,info_base_tag ibt,info_base ib where tag.id = ibt.tag_id and tag.code like :code  and ib.id =ibt.base_id")
//  @Query(nativeQuery=true,value="select * from info_tag tag where tag.code like :nextCode")
    List<Tag> searchTagByCode(@Param("code") String code);
 	
    /**
     * 2013年7月9日优化
     * 张一青
     * @param parentCode
     * @return
     */
    @Query(nativeQuery=true,value="select tag.* from info_tag tag where tag.parent_code = :parentCode and tag.status=true order by tag.sort_no , tag.data_date asc")
	//@Query("select tag from Tag tag where  tag.parentCode=:parentCode and tag.status=true order by tag.sortNo asc,tag.dataDate asc")
	List<Tag> findAllTagByParentCode(@Param("parentCode") Long parentCode);
	
	/**
	 * 根据知识ID取出此知识所在的知识性质标签的路径
	 * 在各个知识详情中显示
	 * 例如：首页-》知识性质-》....
	 * @param Id
	 * @return
	 */
	@Query(nativeQuery=true,value="select fun_get_full_road( :Id)")
	String findTagPathById(@Param("Id") String Id);
	
	/**
	 * 2013年7月9日优化
	 * 张一青
	 * 优化分部分项标签显示
	 * @param code
	 * @return
	 */
	
	@Query(nativeQuery=true,value=" select t1.leaf as leaf ,t1.level as level ,t1.code as code,t1.name as name,ifnull(info_count,0) as count from info_tag t1 left join ( SELECT t.code, COUNT(DISTINCT bt1.base_id) as 'info_count' FROM info_base_tag bt1  ,info_base_tag bt2 ,info_tag t WHERE  bt1.tag_code BETWEEN :pdCode AND :pdCode + CAST((POW(100,6) -1 ) AS UNSIGNED) AND bt2.tag_code BETWEEN :parentCode AND (:parentCode + CAST((POW(100,9 -(:pLevel)) -1 ) AS UNSIGNED)) AND bt1.`base_id` = bt2.`base_id` AND t.`parent_code` =:parentCode  AND bt2.tag_code BETWEEN t.code AND (t.code + CAST((POW(100,9 -(t.level)) -1 ) AS UNSIGNED)) GROUP BY t.code) t2 on t1.code = t2.code where  t1.parent_code=:parentCode")
	List<PdTag> findAllTagByParentCodeAndPdCodeAndPLevel(@Param("parentCode") Long parentCode,@Param("pdCode") Long pdCode,@Param("pLevel") Integer pLevel);
	
	 @Query(nativeQuery=true,value="select count(tag.id) from info_tag tag  where  tag.code like :code")
	 public BigInteger searchBaseCount(@Param("code")String code);
	 
	 
     @Query(nativeQuery=true,value="select * from info_tag tag  where  tag.level=1")
     List<Tag> tagRoot();
      
     @Query(nativeQuery=true, value="select tag.* from info_tag tag where tag.recommend = 1")
     List<Tag> searchTagByrecommend();

     /**
      * 2013年7月9日优化
      * 张一青
      */
     @Query(nativeQuery=true,value="select * from (select *  from info_base_tag  where tag_code >= :start  and  tag_code <:end group by tag_id) as t, info_base_tag bt where bt.base_id=t.base_id and bt.tag_code >= '40202000000000000'  and  bt.tag_code <'40212000000000000' group by bt.source   limit :pageSize,:count")
 	List<Base> findBaseForTags(@Param("start")String start,@Param("end")String end, @Param("count")Integer count ,@Param("pageSize")Integer pageSize);
 	
     
     @Query(nativeQuery=true, value="SELECT c.id AS id, c.code, c.name AS name, COUNT(a.base_id) AS count FROM info_base_tag AS a RIGHT JOIN info_tag AS b ON b.id = a.tag_id AND b.code BETWEEN :codeBetween AND :codeAnd INNER JOIN info_tag AS c ON c.code LIKE :codeLike AND c.code > :codeBetween AND b.code BETWEEN c.code AND c.code + :codeLast GROUP BY c.name ORDER BY c.code")
     public List<Tag> knowledgeList(@Param("codeBetween")Long codeBetween, @Param("codeLike")String codeLike, @Param("codeLast")Long codeLast, @Param("codeAnd")Long codeAnd) ;

     
     @Modifying
     @Query(nativeQuery=true, value="DELETE FROM info_base_tag WHERE base_id = :baseId AND tag_id = :tagId")
     public void deleteSingleTag(@Param("baseId")String baseId, @Param("tagId")String tagId);

     
     
     
     @Query(nativeQuery=true, value="select name from info_tag where code = :code")
     public String getTagNameByCode(@Param("code")String code) ;
 
  }
