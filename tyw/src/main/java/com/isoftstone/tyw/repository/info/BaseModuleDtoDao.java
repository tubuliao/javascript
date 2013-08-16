package com.isoftstone.tyw.repository.info;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.BaseModule;

public interface BaseModuleDtoDao  extends PagingAndSortingRepository<BaseModule, String>{
    @Query(nativeQuery=true,value="select DISTINCT ib.id,ib.source,ib.title, ib.create_date,ib.info_type from  info_base_tag ibt,info_base ib where   ibt.tag_code >= :codestart and ibt.tag_code <=:codeend   and ib.id =ibt.base_id and ibt.tag_id=:tagid  limit :pageNo,:pageSize")
    public List<BaseModule> searchBase(@Param("codestart")Long codestart,@Param("codeend")Long codeend,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);


    @Query(nativeQuery=true,value="select * from  (select   ib.id,ib.source,ib.title, ib.create_date,ib.info_type , ibt.tag_code from  info_base_tag ibt,info_base ib where  ib.id =ibt.base_id    GROUP BY ib.id ) AS Q  where    Q.tag_code >= :codestart and  Q.tag_code <=:codeend  LIMIT :pageNo,:pageSize")
    public List<BaseModule> searchBaseModule(@Param("codestart")Long codestart,@Param("codeend")Long codeend,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
    
    @Query(nativeQuery=true,value=" select  b.id, b.source, b.title,  b.create_date, b.info_type   from ( select distinct base_id from  info_base_tag ibt where ibt.tag_code between  (select  code     from info_tag where id=:id ) and  (  select code + CAST((POW(100,9 -(level )) -1 ) as UNSIGNED)   from info_tag where id=:ids  ) ) t,info_base b where t.base_id = b.id   LIMIT :pageNo,:pageSize")
    public List<BaseModule> searchBaseModule(@Param("id")String id,@Param("ids")String ids,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
    
    @Query(nativeQuery=true,value="select   COUNT(distinct b.id)  from ( select distinct base_id from  info_base_tag ibt where ibt.tag_code between  (select  code     from info_tag where id=:id ) and  (  select code + CAST((POW(100,9 -(level )) -1 ) as UNSIGNED)   from info_tag where id=:id1  ) ) t,info_base b where t.base_id = b.id  ")
    public BigInteger searchBaseModuleCount(@Param("id")String id,@Param("id1")String id1);
    
    
    @Query(nativeQuery=true,value="select COUNT(Q.id) from (select   ib.id,ibt.tag_code from  info_base_tag ibt,info_base ib where  ib.id =ibt.base_id   GROUP BY ib.id) AS Q where   Q.tag_code >= :codestart and  Q.tag_code <=:codeend")
    public BigInteger searchBaseModuleCount(@Param("codestart")Long codestart,@Param("codeend")Long codeend);
    
    @Query(nativeQuery=true,value="select count( DISTINCT ibt.base_id)  from info_base_tag ibt where   ibt.tag_code >= :codestart and ibt.tag_code <=:codeend and ibt.tag_id =:tagid")
    public BigInteger searchTagBaseCount(@Param("codestart")Long codestart,@Param("codeend")Long codeend,@Param("tagid")String tagid);
    
    @Query(nativeQuery=true,value="SELECT base.* FROM info_base base LEFT JOIN  info_base_tag tag ON base.id = tag.base_id where  tag.tag_code=:code order by base.weighing desc limit 0,10 ")
	List<BaseModule> findBaseListByTag(@Param("code")Long code);
}
