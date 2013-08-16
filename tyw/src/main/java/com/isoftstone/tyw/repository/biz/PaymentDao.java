package com.isoftstone.tyw.repository.biz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.biz.Payment;


public interface PaymentDao extends PagingAndSortingRepository<Payment, String>, JpaSpecificationExecutor<Payment>{
	
	List<Payment> findPaymentByUserId(String userId);
	
}
