package com.isoftstone.tyw.entity.pub;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.collect.Lists;
import com.isoftstone.tyw.entity.ID;

@Entity
@Table(name = "pub_province")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Province extends ID implements Serializable{

	 /**
	 * 省份名称
	 */
	private String name;
	 
	 /**
	 * 简称
	 */
	private String shortName;
	
	/**
	 * 行政区划
	 */
	private String areaCode;
	 
	
	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}
	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})//级联保存、更新、删除、刷新;延迟加载
	@JoinColumn(name="pid")
	private List<City> citys = Lists.newArrayList();
	
	 /**
	 * @return the citys
	 */
	public List<City> getCitys() {
		return citys;
	}
	/**
	 * @param citys the citys to set
	 */
	public void setCitys(List<City> citys) {
		this.citys = citys;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}
	/**
	 * @param shortName the shortName to set
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
