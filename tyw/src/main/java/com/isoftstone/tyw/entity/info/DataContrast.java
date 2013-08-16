package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

@Entity
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DataContrast extends ID implements Serializable{
	
	/**
	 * 数据类型
	 */
	private String dataType;
	
	/**
	 * 统计数量
	 */
	private Integer counts;
	
	public DataContrast() {
		super();
	}
	
	public DataContrast(String dataType, Integer counts) {
		this.dataType = dataType;
		this.counts = counts;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

}
