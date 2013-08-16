package com.isoftstone.tyw.repository.info;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.KnowledgeSort;

public interface KnowledgeSortDao  extends PagingAndSortingRepository<KnowledgeSort, String>, JpaSpecificationExecutor<KnowledgeSort>{

	/**
	 * 知识分类数据统计
	 * @return
	 */
	@Query(nativeQuery=true, value="SELECT c.id AS id,  c.name AS name, COUNT(a.base_id) AS count FROM info_base_tag AS a RIGHT JOIN info_tag AS b ON b.id = a.tag_id AND b.code BETWEEN :codeBetween AND :codeAnd INNER JOIN info_tag AS c ON c.code LIKE :codeLike AND c.code > :codeBetween AND b.code BETWEEN c.code AND c.code + :codeLast GROUP BY c.name ORDER BY c.code")
	public List<KnowledgeSort> knowledgeList(@Param("codeBetween")Long codeBetween, @Param("codeLike")String codeLike, @Param("codeLast")Long codeLast, @Param("codeAnd")Long codeAnd) ;
}
