package com.isoftstone.tyw.entity.info;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.isoftstone.tyw.entity.ID;

@Entity
@Table(name="info_tag_map")
public class TagMap extends ID implements Serializable{
	
	/**
	 * 标签起始
	 */
	private Long tagCodeFrom;
	
	/**
	 * 标签结束
	 */
	private Long tagCodeTo;
	
	/**
	 * 
	 */
	private Integer infoType;
	

	public TagMap() {
		super();
	}


	public TagMap(String id) {
		super(id);
	}


	public TagMap(Long tagCodeFrom, Long tagCodeTo, Integer infoType) {
		super();
		this.tagCodeFrom = tagCodeFrom;
		this.tagCodeTo = tagCodeTo;
		this.infoType = infoType;
	}


	public Long getTagCodeFrom() {
		return tagCodeFrom;
	}


	public void setTagCodeFrom(Long tagCodeFrom) {
		this.tagCodeFrom = tagCodeFrom;
	}


	public Long getTagCodeTo() {
		return tagCodeTo;
	}


	public void setTagCodeTo(Long tagCodeTo) {
		this.tagCodeTo = tagCodeTo;
	}


	public Integer getInfoType() {
		return infoType;
	}


	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}
	
}
