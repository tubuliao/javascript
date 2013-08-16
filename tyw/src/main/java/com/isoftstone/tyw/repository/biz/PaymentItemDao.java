package com.isoftstone.tyw.repository.biz;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.isoftstone.tyw.entity.biz.CardType;
import com.isoftstone.tyw.entity.biz.PaymentItem;


public interface PaymentItemDao extends PagingAndSortingRepository<PaymentItem, String>{
	
	
	List<PaymentItem> findPaymentItemByUserIdAndOrderStatus(String UserId,String OrderStatus);
	
	PaymentItem findPaymentItemByUserIdAndCardTypeAndOrderStatus(String UserId,CardType cardType,String orderStatus);
	
	List<PaymentItem> findPaymentItemByOrderIdAndOrderStatus(String orderId,String orderStatus);
	
}
