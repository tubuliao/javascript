package com.isoftstone.tyw.entity.biz;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

@Entity
@Table(name = "biz_payment_item")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
/**
 * 购物车
 * @author wml
 *
 */
public class PaymentItem extends ID implements Serializable{
	/**
	 * 所属人id
	 */
	private String userId;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 单价
	 */
	private Double price;
	
//	/**
//	 * 卡类型id
//	 */
//	private String cardTypeId;

	/**
	 * 缴费ID
	 */
	private String paymentId;
	/**
	 * 数量
	 */
	private Integer amount;
	
	/**
	 * 订单ID
	 */
	private String orderId;
	
	/**
	 * 订单状态（是否已经生产订单）
	 */
	private String orderStatus;
	
	@OneToOne(fetch = FetchType.LAZY,cascade=CascadeType.REFRESH,optional=true)
	@JoinColumn(name="cardTypeId")
    private CardType cardType ;

	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
//	public String getCardTypeId() {
//		return cardTypeId;
//	}
//	public void setCardTypeId(String cardTypeId) {
//		this.cardTypeId = cardTypeId;
//	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
	
}
