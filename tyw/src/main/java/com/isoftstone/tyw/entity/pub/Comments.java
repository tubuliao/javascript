package com.isoftstone.tyw.entity.pub;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
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
@Table(name = "pub_comments")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comments extends ID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6988903320175235149L;
	
	private String infoBaseId;
	/**
	 * 评论内容
	 */
	@Lob
	private String content;
	/**
	 * 评论频道
	 */
	private int channelType;
	/**
	 * 评论人
	 */
	private String authsUserId;
	/**
	 * 审核人
	 */
	private String checkName;
	
	/**
	 * 审核日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date checkDate;
	/**
	 * 审核状态(0:未审核,1:审核通过)
	 */
	private Integer checkStatus;
	/**
	 * 打分
	 */
	private int rate;
	/**
	 * 发布人
	 */
	private String issuedBy;
	/**
	 * 发布人头像URL
	 */
	private String issuedByUrl;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date createDate;

	
	
	public String getInfoBaseId() {
		return infoBaseId;
	}

	public void setInfoBaseId(String infoBaseId) {
		this.infoBaseId = infoBaseId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getChannelType() {
		return channelType;
	}

	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}

	public String getAuthsUserId() {
		return authsUserId;
	}

	public void setAuthsUserId(String authsUserId) {
		this.authsUserId = authsUserId;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}


	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getIssuedByUrl() {
		return issuedByUrl;
	}

	public void setIssuedByUrl(String issuedByUrl) {
		this.issuedByUrl = issuedByUrl;
	}

}
