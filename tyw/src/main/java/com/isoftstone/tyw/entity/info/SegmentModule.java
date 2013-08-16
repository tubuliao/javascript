package com.isoftstone.tyw.entity.info;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

@Entity
public class SegmentModule extends ID {

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
	
	/**
	 * 知识等级
	 */
	private Integer weighing;

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

	/**
	 * @return the weighing
	 */
	public Integer getWeighing() {
		return weighing;
	}

	/**
	 * @param weighing the weighing to set
	 */
	public void setWeighing(Integer weighing) {
		this.weighing = weighing;
	}
	

}
