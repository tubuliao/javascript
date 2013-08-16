package com.isoftstone.tyw.entity.biz;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateSerializer;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

@Entity
@Table(name = "biz_payment")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
/**
 * 缴费记录
 * @author wml
 *
 */
public class Payment extends ID implements Serializable{

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 订单编号
	 */
	private String orderCode;
	/**
	 * 发票抬头
	 */
	private String billHead;
	/**
	 * 发票状态
	 */
	private String billStatus;
	/**
	 * 缴费时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date payTime;
	/**
	 * 缴费确认时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date confirmTime;
	/**
	 * 发票受理时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date acceptTime=new Date();
	
	/**
	 * 缴费金额
	 */
	private Double payMoney;
	
	/**
	 * 优惠编号
	 */
	private String discountCode;

	
	/**
	 * 支付方式
	 */
	private String payWay;
	
	/**
	 *  发票受理人
	 */
	private String acceptMan;
	
	/**
	 * 下单时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date createdAt=new Date();
	
	/**
	 * 订单备注
	 */
	private String remark;
	
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})//级联保存、更新、删除、刷新;延迟加载,是关系的维护端
	@JoinColumn(name="paymentId")//在biz_payment_item表增加一个外键列来实现一对多的单向关联
	@JsonIgnore
	private Set<PaymentItem> paymentItems = new HashSet<PaymentItem>();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getBillHead() {
		return billHead;
	}

	public void setBillHead(String billHead) {
		this.billHead = billHead;
	}

	public String getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getAcceptMan() {
		return acceptMan;
	}

	public void setAcceptMan(String acceptMan) {
		this.acceptMan = acceptMan;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<PaymentItem> getPaymentItems() {
		return paymentItems;
	}

	public void setPaymentItems(Set<PaymentItem> paymentItems) {
		this.paymentItems = paymentItems;
	}



	

	
}
