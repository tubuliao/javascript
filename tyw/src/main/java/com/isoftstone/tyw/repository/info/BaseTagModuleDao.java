package com.isoftstone.tyw.repository.info;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.BaseTagModule;

public interface BaseTagModuleDao extends PagingAndSortingRepository<BaseTagModule,String> ,JpaSpecificationExecutor<BaseTagModule>{

	@Query(nativeQuery=true,value=" SELECT UUID() AS id,t.code,(SELECT it.name FROM info_tag it WHERE it.code = t.code) AS tagname,COUNT(DISTINCT bt1.base_id) AS totalcount FROM info_base_tag bt1 ,info_base_tag bt2 ,info_tag t WHERE  bt1.tag_code BETWEEN :code AND :code + CAST((POW(100,9 -(:level)) -1 ) AS UNSIGNED) AND bt2.tag_code BETWEEN 40000000000000000 AND 49999999999999999 AND bt1.`base_id` = bt2.`base_id` AND bt1.title NOT LIKE '%样表%' AND t.`code` BETWEEN 40000000000000000 AND 49999999999999999 AND t.level =3 AND bt2.tag_code BETWEEN t.code AND (t.code + CAST((POW(100,9 -(t.level)) -1 ) AS UNSIGNED)) GROUP BY t.code") 
   List<BaseTagModule> findAllBaseTagModule( @Param("code")Long code,@Param("level")Integer level);
			
}
