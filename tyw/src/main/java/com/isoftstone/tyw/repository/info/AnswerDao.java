package com.isoftstone.tyw.repository.info;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Answer;

public interface AnswerDao  extends PagingAndSortingRepository<Answer, String>{

	@Query(nativeQuery=true,value="select * from info_answer where qid=:qid")
    List<Answer> getAnswerPageList(@Param("qid")String qid);
	
	@Query(nativeQuery=true,value="select * from info_answer where qid=:qid")
	Page<Answer> getAnswerByQid(@Param("qid")String qid,Pageable pageable);
}
