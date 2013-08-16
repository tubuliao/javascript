package com.isoftstone.tyw.entity.auths;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.isoftstone.tyw.entity.ID;

@Entity
@Table(name="auths_project")
public class Project extends ID implements Serializable{
	
	
	/**
	 * 项目名称
	 */
	private String name;
	
	/**
	 * 人员ID
	 */
	private String additionalId;
	/**
	 * 所在地区
	 */
	private String region;
	/**
	 * 建设地点
	 */
	private String addr;
	/**
	 * 建设单位
	 */
	private String buildUnit;
	/**
	 * 施工单位
	 */
	private String constructUnit;
	/**
	 * 施工许可证号
	 */
	private String licenseNo;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdditionalId() {
		return additionalId;
	}

	public void setAdditionalId(String additionalId) {
		this.additionalId = additionalId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getBuildUnit() {
		return buildUnit;
	}

	public void setBuildUnit(String buildUnit) {
		this.buildUnit = buildUnit;
	}

	public String getConstructUnit() {
		return constructUnit;
	}

	public void setConstructUnit(String constructUnit) {
		this.constructUnit = constructUnit;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	
	
}
