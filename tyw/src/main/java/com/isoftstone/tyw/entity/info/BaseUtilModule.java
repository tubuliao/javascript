package com.isoftstone.tyw.entity.info;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateSerializer;

@Entity
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BaseUtilModule extends ID implements Serializable{
	
	/**
	 * 消息名称
	 */
	protected String title;
	
	@JsonSerialize(using=JsonDateSerializer.class)
	protected Date begincreateDate = new Date();
	
	@JsonSerialize(using=JsonDateSerializer.class)
	protected Date modifyDate ;
	
	private Integer clickcount = 0;
	
	private String source;
	
	/**
	 * 内容类型
	 * files:1
	 * Segment:2
	 * video:3
	 * Form:4
	 * image:5
	 */
	@Column(nullable=false)
	protected Integer infoType;

	
	/**
	 * @return the modifyDate
	 */
	public Date getModifyDate() {
		return modifyDate;
	}

	/**
	 * @param modifyDate the modifyDate to set
	 */
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * @return the clickcount
	 */
	public Integer getClickcount() {
		return clickcount;
	}

	/**
	 * @param clickcount the clickcount to set
	 */
	public void setClickcount(Integer clickcount) {
		this.clickcount = clickcount;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		if(StringUtils.isBlank(this.source)){
			return "";
		}
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the begincreateDate
	 */
	public Date getBegincreateDate() {
		return begincreateDate;
	}

	/**
	 * @param begincreateDate the begincreateDate to set
	 */
	public void setBegincreateDate(Date begincreateDate) {
		this.begincreateDate = begincreateDate;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the infoType
	 */
	public Integer getInfoType() {
		return infoType;
	}

	/**
	 * @param infoType the infoType to set
	 */
	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}
	
}