package com.isoftstone.tyw.entity.pub;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

@Entity
@Table(name="pub_license_project")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LicenseProject extends ID implements Serializable{
	
	/**
	 * 项目组名称
	 */
	private String proName;
	
	/**
	 * 项目组地址
	 */
	private String proAddress;
	
	/**
	 * 序列号id
	 */
	private String licenseId;

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProAddress() {
		return proAddress;
	}

	public void setProAddress(String proAddress) {
		this.proAddress = proAddress;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}
}
