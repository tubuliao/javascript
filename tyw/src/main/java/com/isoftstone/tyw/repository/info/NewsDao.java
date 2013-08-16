
package com.isoftstone.tyw.repository.info;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.BaseModule;
import com.isoftstone.tyw.entity.info.News;

public interface NewsDao extends PagingAndSortingRepository<News, String>, JpaSpecificationExecutor<News>{
	
    /**
     * 新闻列表
     * @param pageable
     * @return
     */
   // Page<News>  findByTypeOrderByReleaseDateDesc (Integer type,Pageable pageable);
    
    @Query(nativeQuery=true,value="SELECT DISTINCT base.id,base.title,base.source,base.create_date,base.info_type from info_base  base,info_base_tag bt where bt.tag_id=:id and base.info_type=2 and base.id=bt.base_id  and base.check_date!='' ORDER BY base.check_date desc limit :pageNum,:pageSize ")
    List<News> searchByid(@Param("id")String id,@Param("pageNum")Integer pageNum,@Param("pageSize")Integer pageSize);
    
    /**
     * 最新更新
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Query(nativeQuery=true,value="SELECT DISTINCT base.id,base.title,base.source,base.create_date,base.info_type from info_base  base ORDER BY base.check_date desc limit :pageNum,:pageSize ")
    List<News> LatestChange(@Param("pageNum")Integer pageNum,@Param("pageSize")Integer pageSize);
    
    @Query(nativeQuery=true,value="SELECT DISTINCT count(base.id)  from info_base  base,info_base_tag bt where bt.tag_id=:id and base.id=bt.base_id")
    BigInteger searchCountByid(@Param("id")String id);
    /**
     * 新闻详情
     * @param pageable
     * @return
     */
    News  findById (String Id);
    
}
