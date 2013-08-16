package com.isoftstone.tyw.entity.biz;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

@Entity
@Table(name = "biz_card_biz")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CardBiz extends ID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3526696013367106231L;

	/**
	 * 业务类型(0:访问时长,单位月,1:下载, 2：视频)
	 */
	private Integer bizType;
	
	/**
	 * 卡id
	 */
	private String cardTypeId;

	/**
	 * 业务点数
	 */
	private String value;
	
	private Integer status;
	
	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getCardTypeId() {
		return cardTypeId;
	}

	public void setCardTypeId(String cardTypeId) {
		this.cardTypeId = cardTypeId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
