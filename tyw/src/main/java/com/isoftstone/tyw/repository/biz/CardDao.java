package com.isoftstone.tyw.repository.biz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.biz.Card;


public interface CardDao extends PagingAndSortingRepository<Card, String>, JpaSpecificationExecutor<Card>{
	
	 //@Query( " select c from biz_card c where c.auths_user_id = ? " )
	 List<Card> findByCreateId(String createId);
	 
	 Card findByCardNo(String cardNo);
	 
	 Card findById(String id);
	
	 @Modifying 
	 @Query(nativeQuery=true,value="update biz_card set price=:price where id=:bizCardId") 
	 public int modifyPrice(@Param("bizCardId")String bizCardId,@Param("price")double price); 
}
