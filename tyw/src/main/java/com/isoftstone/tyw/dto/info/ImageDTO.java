package com.isoftstone.tyw.dto.info;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.isoftstone.tyw.util.JsonDateTimeSerializer;

public class ImageDTO {

	/**
	 * ID
	 */
	private String id ;
	
	/**
	 * 标题
	 */
	private String title ;
	
	/**
	 * 来源
	 */
	private String source ;
	
	/**
	 * 录入人
	 */
	private String insertName ;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date createDate;
	
	/**
	 * 状态
	 */
	private Integer state ;
	
	public ImageDTO(String id, String title, String source, String insertName, Date createDate, Integer state) {
		this.id = id ;
		this.title = title ;
		this.source = source ;
		this.insertName = insertName ;
		this.createDate = createDate ;
		this.state = state ;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getInsertName() {
		return insertName;
	}

	public void setInsertName(String insertName) {
		this.insertName = insertName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
