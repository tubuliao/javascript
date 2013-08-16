package com.isoftstone.tyw.entity.info;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;


@Entity
@Table(name="info_bookmark")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bookmark extends ID implements Serializable{

	/**
	 * 用户ID
	 */
	private String userId ;
	
	/**
	 * 
	 */
	private String pid ;
	
	/**
	 * 标题
	 */
	private String title ;
	
	/**
	 * 数据类型
	 */
	private Integer dataType ;
	
	/**
	 * 地址
	 */
	private String url ;
	
	/**
	 * 收藏时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date createDate = new Date() ;
	
	/**
	 * 分类ID
	 */
	@ManyToOne(/*fetch=FetchType.LAZY, */cascade = {CascadeType.REFRESH}, optional=true) // optional=true 可选属性， 表示classification可以为空
	@JoinColumn(name = "classification_id")
	private Classification classification ;	
	
	/**
	 * 知识ID
	 */
	private String knowledgeId;
	
	public Bookmark() {
		super() ;
	}
	
	public Bookmark(String userId, String pid, String title, Integer dataType, String url, Date createDate, Classification classification, String knowledgeId) {
		this.userId = userId ;
		this.pid = pid ;
		this.title = title ;
		this.dataType = dataType ;
		this.url = url ;
		this.createDate = createDate ;
		this.classification = classification ;
		this.knowledgeId = knowledgeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateDate() {
		return createDate ;
	}

	public void setCreateDate(Date createDate) throws ParseException {
		this.createDate = createDate ;
	}

	public Classification getClassification() {
		return classification;
	}

	public void setClassification(Classification classification) {
		this.classification = classification;
	}

	public String getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}
	
	
}
