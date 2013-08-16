package com.isoftstone.tyw.entity.info;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;
@Entity
public class FormModule extends ID implements Serializable{
	private String title;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date createDate = new Date();
	
	private String source ;
	private String insertName ;
	private String modifyName ;
	private Integer state ;
	private String empUrl ;
	private String empHiPicUrl ;
	private String empLowPicUrl ;
	private String descUrl ;
	private String descContent;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date modifyDate ;
	
	// 知识等级
	private Integer weighing;

	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	public String getEmpUrl() {
		return empUrl;
	}

	public void setEmpUrl(String empUrl) {
		this.empUrl = empUrl;
	}

	public String getEmpHiPicUrl() {
		return empHiPicUrl;
	}

	public void setEmpHiPicUrl(String empHiPicUrl) {
		this.empHiPicUrl = empHiPicUrl;
	}

	public String getEmpLowPicUrl() {
		return empLowPicUrl;
	}

	public void setEmpLowPicUrl(String empLowPicUrl) {
		this.empLowPicUrl = empLowPicUrl;
	}

	public String getDescUrl() {
		return descUrl;
	}

	public void setDescUrl(String descUrl) {
		this.descUrl = descUrl;
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
	/**
	 * @return the descContent
	 */
	public String getDescContent() {
		return descContent;
	}
	/**
	 * @param descContent the descContent to set
	 */
	public void setDescContent(String descContent) {
		this.descContent = descContent;
	}
	
	
}