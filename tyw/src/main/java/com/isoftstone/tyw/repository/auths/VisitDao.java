package com.isoftstone.tyw.repository.auths;

import java.math.BigInteger;
import java.util.List;
import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.auths.Visit;

public interface VisitDao extends PagingAndSortingRepository<Visit, Integer>{
	@Query(nativeQuery=true, value="SELECT v.* FROM auths_user_visit v where v.user_id = :userId and v.visit_date > :visitDate order by visit_date desc LIMIT :pageIndex, :items_per_page ")
	public List<Visit> findVisitSet(@Param("userId")String userId, @Param("visitDate")Date visitDate, @Param("pageIndex")Integer pageIndex, @Param("items_per_page")Integer items_per_page) ;
	
	@Query(nativeQuery=true, value="SELECT COUNT(*) FROM auths_user_visit WHERE user_id = :userId and visit_date > :visitDate order by visit_date desc")
	public BigInteger findAllTotal(@Param("userId")String userId, @Param("visitDate") Date visitDate) ;
	
	List<Visit> findByUserId(String userId);
	
	@Query(nativeQuery=true, value="select visit_id from auths_user_visit where user_id = :userId and visit_id = :visitId")
	public String findOneUrl(@Param("userId") String userId, @Param("visitId") String visitId);
	
	 @Modifying 
	 @Query("update Visit visit set visit.visitDate = :visitDate where visit.userId =:userId and visit.visitId=:visitId") 
	 public int modifyVisit(@Param("visitDate")Date visitDate,@Param("userId")String userId,@Param("visitId")String visitId); 
	
}
