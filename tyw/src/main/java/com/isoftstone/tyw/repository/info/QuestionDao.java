package com.isoftstone.tyw.repository.info;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.info.Question;

public interface QuestionDao  extends PagingAndSortingRepository<Question, String>, JpaSpecificationExecutor<Question>{
	@Modifying
	@Query(nativeQuery=true,value="delete from info_answer where qid=:qid")
	public void deleteAnswer(@Param("qid")String qid);
	
	@Modifying 
	@Query(nativeQuery=true,value="update info_base set state = :status,check_id= :checkId,check_name=:checkName,check_date=:checkDate where id =:id") 
	public int modifyState(@Param("status")int status,@Param("checkId")String checkId,@Param("checkName")String checkName,@Param("checkDate")Date checkDate,@Param("id")String id); 
}
