package com.isoftstone.tyw.repository.info;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.KnowledgeSort;
import com.isoftstone.tyw.entity.info.TagCount;

public interface TagCodeDao  extends PagingAndSortingRepository<TagCount, String>, JpaSpecificationExecutor<TagCount>{

	/**
	 * 知识分类数据统计
	 * @return
	 */
	@Query(nativeQuery=true, value="SELECT c.id AS id, c.code AS code,c.level AS level, c.name AS name, COUNT(a.base_id) AS count FROM info_base_tag AS a RIGHT JOIN info_tag AS b ON b.id = a.tag_id AND b.code BETWEEN :codeBetween AND :codeAnd INNER JOIN info_tag AS c ON c.code LIKE :codeLike AND c.code > :codeBetween AND b.code BETWEEN c.code AND c.code + :codeLast GROUP BY c.name ORDER BY c.code")
	public List<TagCount> knowledgeList(@Param("codeBetween")Long codeBetween, @Param("codeLike")String codeLike, @Param("codeLast")Long codeLast, @Param("codeAnd")Long codeAnd) ;
	
//	@Query(nativeQuery=true, value="SELECT tag.id AS id  , tag.code AS code  ,tag.level AS level , tag.name AS name, 0 AS count from info_tag tag where tag.parent_id=:id")
//    public List<TagCount>  knowledgeList(@Param("id")String id) ;
	
	@Query(nativeQuery=true, value="select t1.id AS id,t1.name AS name ,t1.code AS code,t1.level AS level,t2.info_count AS count from info_tag t1,(select t.`id`,COUNT(distinct bt.base_id) as  'info_count' from info_base_tag bt ,info_tag t where bt.`tag_code` between t.code and (t.code + CAST((POW(100,9 -(t.level)) -1 ) AS UNSIGNED)) and t.`parent_id`=:id GROUP BY t.id) t2 where t1.id = t2.id")
    public List<TagCount>  knowledgeList(@Param("id")String id) ;
	
	@Query(nativeQuery=true, value="select c.code AS code,c.level AS level FROM info_tag c where id=:id")
    public TagCount  tagCount(@Param("id")String id) ;
	
	@Query(nativeQuery=true, value="select  t.id AS id ,t.name AS name ,COUNT(distinct bt.base_id) AS count,t.code AS code,t.level AS level   from  `info_base_tag` bt , info_tag t where bt.tag_code between 40000000000000000 and 49999999999999999 and t.code like '4__00000000000000' and t.code > 40000000000000000 and bt.tag_code between t.code and t.code + CAST((POW(100,9 -(t.LEVEL )) -1 ) as UNSIGNED) group by t.name")
    public List<TagCount>  tagCountIndex() ;
	
	@Query(nativeQuery=true, value="select bt.base_id AS id, '分部分项' AS name,COUNT(distinct bt.base_id) AS count,0 AS code,0 AS level from  `info_base_tag` bt where  bt.tag_code >= 10000000000000000 and bt.tag_code <=19999999999999999")
	public TagCount  tagCountfbfx() ;
}
