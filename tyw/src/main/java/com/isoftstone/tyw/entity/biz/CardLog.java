package com.isoftstone.tyw.entity.biz;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

@Entity
@Table(name = "biz_card_log")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
/**
 * 用户充值消费台账
 * @author wml
 *
 */
public class CardLog extends ID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4378095611676878861L;

	/**
	 * 卡id
	 */
	private String bizCardId;
	
	/**
	 * 业务记账时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date dataDate;
	
	/**
	 * 消费点数
	 */
	private Integer value;
	
	/**
	 * 上期结余金额
	 */
	private Integer lastTotal;
	
	/**
	 * 备注
	 */
	private String summary;
	
	/**
	 * 状态(0:初始,1:充值,2:消费,3:结转)
	 */
	private Integer status;
	
	/**
	 * 本期结余金额
	 */
	private Integer overTotal;
	
	/**
	 * 操作人
	 */
	private String insertName;
	
	/**
	 * 账户id
	 */
	private String authsUserId;
	
	/**
	 * 业务类型(0:访问时长,单位月,1:下载, 2：视频)
	 */
	private Integer bizType;
	

	public String getBizCardId() {
		return bizCardId;
	}
	public void setBizCardId(String bizCardId) {
		this.bizCardId = bizCardId;
	}
	public Date getDataDate() {
		return dataDate;
	}
	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getInsertName() {
		return insertName;
	}
	public void setInsertName(String insertName) {
		this.insertName = insertName;
	}
	public String getAuthsUserId() {
		return authsUserId;
	}
	public void setAuthsUserId(String authsUserId) {
		this.authsUserId = authsUserId;
	}
	public Integer getBizType() {
		return bizType;
	}
	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getLastTotal() {
		return lastTotal;
	}
	public void setLastTotal(Integer lastTotal) {
		this.lastTotal = lastTotal;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getOverTotal() {
		return overTotal;
	}
	public void setOverTotal(Integer overTotal) {
		this.overTotal = overTotal;
	}

}
