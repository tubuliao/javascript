package com.isoftstone.tyw.entity.auths;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.isoftstone.tyw.entity.ID;

/**
 * 人员职业信息表（Personnel occupation information table）
 * 记录个人工作经历的实体（Record of personal experience entity）
 * @date 2013-3-7
 * @author zhangyiqing
 *
 */

@Entity
@Table(name="auths_occupational")
public class Occupational extends ID implements Serializable{

	/**
	 * 所在地location
	 */
	private String location;
	
	/**
	 * 单位名称Unit name
	 */
	private String unitName;
	
	/**
	 * 单位性质Unit properties
	 */
	private String unitProperties;
	
	/**
	 * 工作开始时间operating begin time
	 */
	private String beginTime;
	
	/**
	 * 工作结束时间operating end time
	 */
	private String endTime;
	
	/**
	 * 部门/职位Department / Office
	 */
	private String department;
	
	/**
	 * 人员ID
	 */
	private String additionalId;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitProperties() {
		return unitProperties;
	}

	public void setUnitProperties(String unitProperties) {
		this.unitProperties = unitProperties;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getAdditionalId() {
		return additionalId;
	}

	public void setAdditionalId(String additionalId) {
		this.additionalId = additionalId;
	}
	
}
