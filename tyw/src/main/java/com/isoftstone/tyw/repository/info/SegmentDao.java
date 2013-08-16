
package com.isoftstone.tyw.repository.info;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Base;
import com.isoftstone.tyw.entity.info.Form;
import com.isoftstone.tyw.entity.info.Segment;
import com.isoftstone.tyw.entity.info.SegmentDir;

public interface SegmentDao extends PagingAndSortingRepository<Segment, String>, JpaSpecificationExecutor<Segment>{
	
	Page<Segment> findByModifyDateGreaterThan(Date modifyDate, Pageable pageable);
	
	@Query("select segment from Segment segment where segment.source=:source and segment.segItem !='' order by segItem * 1 ASC")
	List<Segment> findBySource(@Param("source")String source);
	
	@Query(nativeQuery=true,value="select s.id ,s.seg_item ,b.title from info_segment s ,info_base b where s.id=b.id and  b.source=:source and s.seg_item !='' order by seg_item * 1 ASC")
	List<SegmentDir> findBySourceN(@Param("source")String source);
	
	/*@Query(nativeQuery=true,value="select b.* from info_base_tag bt,info_base b,info_tag t  where bt.base_id=b.id and bt.tag_id=t.id and t.code>=：start and t.code<:end and t.code>'40202000000000000' and t.code<'40212000000000000'  group by b.source   limit :count,:pageSize")
	List<Base> findBaseForTags(@Param("start")String start,@Param("end")String end, @Param("count")Integer count ,@Param("pageSize")Integer pageSize);
	*///select b.* from info_base_tag bt,info_base b,info_tag t  where bt.base_id=b.id and bt.tag_id=t.id and t.id>'10000000000000000'   and t.code>'40202000000000000' and t.code<'40212000000000000'       group by b.source  
	//@Query(nativeQuery=true,value="SELECT info_base.*,  info_segment.* FROM ( SELECT tag_id,base_id FROM info_base_tag WHERE tag_code >= :zsxzstart AND tag_code < :zsxzend GROUP BY base_id) AS a  CROSS JOIN (	SELECT tag_id,base_id FROM info_base_tag  WHERE tag_code >= :fbfxstart AND tag_code < :fbfxend GROUP BY base_id) AS b  CROSS JOIN (	SELECT tag_id,base_id FROM info_base_tag  WHERE tag_code = :areaCode   GROUP BY base_id  ) AS c ON a.base_id = b.base_id AND a.base_id = c.`base_id` AND b.base_id = c.base_id LEFT JOIN info_tag  ON info_tag.id = a.tag_id  LEFT JOIN info_base ON info_base.id = a.base_id AND info_tag.status=1  LEFT JOIN info_form ON info_base.id = info_form.id limit :count,:pageSize")
	
	@Query(nativeQuery=true,value="select * from info_base base ,info_segment segment where segment.id=base.id and segment.id=:id")
	Segment findSegment(@Param("id")String id);
}
