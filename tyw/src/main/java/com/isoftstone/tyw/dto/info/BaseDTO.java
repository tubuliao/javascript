package com.isoftstone.tyw.dto.info;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.isoftstone.tyw.util.JsonDateTimeSerializer;

public class BaseDTO{
	
	/**
	 * ID
	 */
	private String id;
	
	/**
	 * 知识类型
	 */
	private Integer infoType;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 来源
	 */
	private String source;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date createDate;
	
	/**
	 * 录入人
	 */
	private String insertName;
	
	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date modifyDate;
	
	/**
	 * 修改人
	 */
	private String modifyName;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 审批时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date checkDate;
	
	/**
	 * 审批人
	 */
	private String checkName;

	
	public BaseDTO(String id, Integer infoType, String title, String source, Date createDate, String insertName, Date modifyDate, String modifyName, Integer state ) {
		this.id = id;
		this.infoType = infoType;
		this.title =title;
		this.source = source;
		this.createDate = createDate;
		this.insertName = insertName;
		this.modifyDate = modifyDate;
		this.modifyName = modifyName;
		this.state = state;
	}
	
	public BaseDTO(String id, Integer infoType, String title, String source, Date createDate, String insertName, Date modifyDate, String modifyName, Integer state, Date checkDate, String checkName) {
		this.id = id;
		this.infoType = infoType;
		this.title =title;
		this.source = source;
		this.createDate = createDate;
		this.insertName = insertName;
		this.modifyDate = modifyDate;
		this.modifyName = modifyName;
		this.state = state;
		this.checkDate = checkDate;
		this.checkName = checkName;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getInfoType() {
		return infoType;
	}

	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


	public Date getCheckDate() {
		return checkDate;
	}


	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}


	public String getCheckName() {
		return checkName;
	}


	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	
	
	
}