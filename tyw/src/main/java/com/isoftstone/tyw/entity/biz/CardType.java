package com.isoftstone.tyw.entity.biz;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;


import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.common.collect.Lists;
import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

@Entity
@Table(name = "biz_card_type")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CardType extends ID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3744554782386841606L;
	
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	@Lob
	private String content;
	/**
	 * 备注
	 */
	private String summary;
	/**
	 * 单价
	 */
	private Double price;
	
	/**
	 * 状态
	 */
	private Integer status;
	
	/**
	 * 包年卡 12 半年卡 6 月数
	 */
	private Integer months;
	
	/**
	 * 创建人
	 */
	private String createName;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date createDate = new Date();
	
	/**
	 * 修改人
	 */
	private String modifyName;
	
	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date modifyDate = new Date();
	
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})//级联保存、更新、删除、刷新;延迟加载
	@JoinColumn(name="cardTypeId")//在biz_card_biz表增加一个外键列来实现一对多的单向关联
	private List<CardBiz> cardBizs = Lists.newArrayList();
	
	
	/**
	 * @return the months
	 */
	public Integer getMonths() {
		return months;
	}

	/**
	 * @param months the months to set
	 */
	public void setMonths(Integer months) {
		this.months = months;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

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

	public Integer getStatues() {
		return status;
	}

	public void setStatues(Integer statues) {
		this.status = statues;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<CardBiz> getCardBizs() {
		return cardBizs;
	}

	public void setCardBizs(List<CardBiz> cardBizs) {
		this.cardBizs = cardBizs;
	}







}
