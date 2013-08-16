package com.isoftstone.tyw.repository.biz;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.isoftstone.tyw.entity.biz.CardBiz;

public interface CardBizDao extends PagingAndSortingRepository<CardBiz, String>{
	
	List<CardBiz> findByCardTypeId(String cardTypeId); 
	
	@Query(nativeQuery=true, value="SELECT card_type_id FROM biz_card WHERE id = :cardId")
	public String findCardTypeIdByCardId(@Param("cardId")String cardId) ;
	
	@Query(nativeQuery=true, value="SELECT title FROM biz_card_type WHERE id = :cardTypeId")
	public String findCardTitleByCardTypeId(@Param("cardTypeId")String cardTypeId) ;
}
