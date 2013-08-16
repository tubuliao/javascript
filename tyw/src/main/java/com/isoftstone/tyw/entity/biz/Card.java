package com.isoftstone.tyw.entity.biz;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateSerializer;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

@Entity
@Table(name = "biz_card")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Card extends ID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7515388304408399249L;


	/**
	 * 会员卡编码
	 */
	private String cardNo;
	/**
	 * 会员卡类型
	 */
	private String cardTypeId;
	
	/**
	 * 卡有效期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date expireDate=new Date();;
	/**
	 * 开卡时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date createDate=new Date();;
	/**
	 * 卡激活时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date activeTime;
	
	/**
	 * 金额
	 */
	private Double price;
	
	/**
	 * 优惠编号
	 */
	private String discountCode;
	/**
	 * 批次号
	 */
	private String batchCode;
	/**
	 * 卡状态(0:禁用,1:注销,2:可用)
	 */
	private Integer status;
	
	/**
	 * 持有者
	 */
	private String authsUserId;
	
	/**
	 *  企业id
	 */
	private String bizFirmId;
	
	/**
	 * 创建人--开卡人
	 */
	private String createId;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardTypeId() {
		return cardTypeId;
	}

	public void setCardTypeId(String cardType) {
		this.cardTypeId = cardType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAuthsUserId() {
		return authsUserId;
	}

	public void setAuthsUserId(String authsUserId) {
		this.authsUserId = authsUserId;
	}

	public String getBizFirmId() {
		return bizFirmId;
	}

	public void setBizFirmId(String bizFirmId) {
		this.bizFirmId = bizFirmId;
	}

	public String getCreateId() {
		return createId;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}



	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	
}
