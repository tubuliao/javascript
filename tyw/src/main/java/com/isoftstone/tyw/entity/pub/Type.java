package com.isoftstone.tyw.entity.pub;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name = "pub_type")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Type implements Serializable{

	/**
	 * 类型ID
	 */
	private String typeId;
	
	/**
	 * 排序Id
	 */
	private Integer sortId;
	
	/**
	 * 名称
	 */
	private String typeName;

	/**
	 * 值
	 */
	@Id
	@Column(updatable=false,unique=true,length=36)
	private String TypeValue;

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeValue() {
		return TypeValue;
	}

	public void setTypeValue(String typeValue) {
		TypeValue = typeValue;
	}

	
}
