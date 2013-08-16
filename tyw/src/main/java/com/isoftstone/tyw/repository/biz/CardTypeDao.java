package com.isoftstone.tyw.repository.biz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.biz.CardType;

public interface CardTypeDao extends PagingAndSortingRepository<CardType, String>, JpaSpecificationExecutor<CardType>{
	
	List<CardType> findAll();

}
