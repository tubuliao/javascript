package com.isoftstone.tyw.entity.river;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.common.collect.Sets;
import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

/**
 * 内容基类
 * 
 * @author zhangyiqing
 */
@Entity
@Table(name="river_base")
@Inheritance(strategy=InheritanceType.JOINED)
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BaseRiver extends ID {
	

	
	/**ok
	 * 消息名称
	 */
	private String title;

	
	/**ok
	 * 描述
	 */
	private String description;
	
	/**ok
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date createDate;
	
	
	/**
	 * 内容摘要
	 */
	protected String summary;
	
	
	/**
	 * 内容类型
	 * News:1
	 * Segment:2
	 * Standard:3
	 * Form:4
	 * FormExt:5
	 */
	@Column(nullable=false)
	private Integer type;
	
	/**
	 * 内容与分部分项关系,用逗号（,）隔开
	 */
	private String tags ;
	
	
	/**
	 * 出处
	 */
	private String source ;
	
	/**
	 * url
	 */
	private String url ;

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getSummary() {
		return summary;
	}

	public Integer getType() {
		return type;
	}

	public String getTags() {
		return tags;
	}

	public String getSource() {
		return source;
	}

	public String getUrl() {
		return url;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
