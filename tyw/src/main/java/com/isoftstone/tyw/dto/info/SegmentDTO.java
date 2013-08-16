package com.isoftstone.tyw.dto.info;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

@Entity
public class SegmentDTO extends ID implements Serializable {

	/**
	 * ID
	 */
//	private String id ;
	
	/**
	 * 标题
	 */
	private String title ;
	
	/**
	 * 条目
	 */
	private String segItem ;
	
	/**
	 * 来源
	 */
	private String source ;
	
	/**
	 * 录入人
	 */
	private String insertName ;
	
	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date modifyDate ;
	
	/**
	 * 状态
	 */
	private Integer state ;
	
	
	public SegmentDTO(String id, String title, String segItem, String source, String insertName, Date modifyDate, Integer state) {
		this.id = id ;
		this.title = title ;
		this.segItem = segItem ;
		this.source = source ;
		this.insertName = insertName ;
		this.modifyDate = modifyDate ;
		this.state = state ;
	}

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSegItem() {
		return segItem;
	}

	public void setSegItem(String segItem) {
		this.segItem = segItem;
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

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
