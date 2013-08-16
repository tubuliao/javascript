package com.isoftstone.tyw.repository.biz;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.biz.CardLog;


public interface CardLogDao extends PagingAndSortingRepository<CardLog, String>{
	
	List<CardLog> findByAuthsUserIdAndBizType(String authUserId,Integer bizType);
	
	@Query(nativeQuery=true, value="SELECT cardLog.* FROM biz_card_log cardLog WHERE auths_user_id = :userId ORDER BY data_date DESC")
	public List<CardLog> findAllByUserId(@Param("userId")String userId) ;
	
	@Query(nativeQuery=true, value="SELECT biz_card_id FROM biz_card_log WHERE auths_user_id = :userId and biz_type=0 ORDER BY data_date DESC limit 0,1")
	public String findCardIdByUserId(@Param("userId")String userId) ;
	
	@Query(nativeQuery=true, value="SELECT * FROM biz_card_log WHERE auths_user_id = :userId and biz_type=0 ORDER BY data_date DESC limit 0,1")
	CardLog findCardInfoByUserId(@Param("userId")String userId) ;
	
	@Modifying 
	@Query(nativeQuery=true,value="update biz_card_log set last_total=:lastTotal,over_total=:overTotal where auths_user_id =:userId") 
	public int modifyLastTotalAndOverTotal(@Param("lastTotal")int lastTotal,@Param("overTotal")int overTotal,@Param("userId")String userId); 

	@Query(nativeQuery=true, value="SELECT cardLog.* FROM biz_card_log cardLog WHERE auths_user_id = :userId and biz_type = :bizType ORDER BY data_date ASC")
	public List<CardLog> findAllByUserIdAndBizType(@Param("userId")String userId,@Param("bizType")Integer bizType) ;
}
