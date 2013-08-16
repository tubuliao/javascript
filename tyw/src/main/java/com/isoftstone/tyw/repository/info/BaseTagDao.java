package com.isoftstone.tyw.repository.info;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.BaseTag;

public interface BaseTagDao  extends PagingAndSortingRepository<BaseTag, String>{
     
    @Query(nativeQuery=true,value="select uuid() as id,bt.* from (select *  from info_base_tag  where tag_code >= :start  and  tag_code <:end group by tag_id) as t, info_base_tag bt where bt.base_id=t.base_id and bt.tag_code >= '40202000000000000'  and  bt.tag_code <'40203000000000000' group by bt.source limit :pageSize,:count")
 	List<BaseTag> findBaseTagForTagCode(@Param("start")String start,@Param("end")String end, @Param("count")Integer count ,@Param("pageSize")Integer pageSize);
 	
    //@Query(nativeQuery=true,value="select base.id,base_id,tag_id, tag_name,base.info_type,basetag.source,basetag.title,tag_code from info_base_tag basetag,info_base base where  basetag.base_id=:id and base.id=basetag.base_id and basetag.tag_code>=40000000000000000 and basetag.tag_code<=49999999999999999 " +
    //		"UNION" +
    //		" select base.id,base_id,tag_id, tag_name,base.info_type,basetag.source,basetag.title,tag_code from info_base_tag basetag,info_base base where  basetag.base_id=:id1 and base.id=basetag.base_id and basetag.tag_code>=10000000000000000 and basetag.tag_code<=19999999999999999  limit 0,15")
    @Query(nativeQuery=true,value="select DISTINCT(basetag.tag_name),basetag.base_id,basetag.tag_id as id,basetag.tag_id,base.info_type,basetag.source,basetag.title,basetag.tag_code from info_base_tag basetag,info_base base where  basetag.base_id=:id and base.id=basetag.base_id and basetag.tag_code>=10000000000000000 and basetag.tag_code<=19999999999999999 limit 0,15")
    public List<BaseTag> findRelatedWords(@Param("id")String id);

 }
