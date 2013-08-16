package com.isoftstone.tyw.entity.pub;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.entity.auths.User;

/**
 * license批次号
 * @author zhaowenli
 *
 */
@Entity
@Table(name="pub_license_batch")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LicenseBatch extends ID implements Serializable{
  
	private String batchCode;
	private Date createDate = new Date();
	private int licenseTotal;
	private int licenseFifTotal;
	private int licenseTenTotal;
	
	@ManyToOne(cascade = {CascadeType.REFRESH}, optional=true) 
	@JoinColumn(name = "agent_id")
	private User agent ;	
	
	/**
	 * @return the agent
	 */
	public User getAgent() {
		return agent;
	}
	/**
	 * @param agent the agent to set
	 */
	public void setAgent(User agent) {
		this.agent = agent;
	}
	/**
	 * @return the batchCode
	 */
	public String getBatchCode() {
		return batchCode;
	}
	/**
	 * @param batchCode the batchCode to set
	 */
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the licenseTotal
	 */
	public int getLicenseTotal() {
		return licenseTotal;
	}
	/**
	 * @param licenseTotal the licenseTotal to set
	 */
	public void setLicenseTotal(int licenseTotal) {
		this.licenseTotal = licenseTotal;
	}
	/**
	 * @return the licenseFifTotal
	 */
	public int getLicenseFifTotal() {
		return licenseFifTotal;
	}
	/**
	 * @param licenseFifTotal the licenseFifTotal to set
	 */
	public void setLicenseFifTotal(int licenseFifTotal) {
		this.licenseFifTotal = licenseFifTotal;
	}
	/**
	 * @return the licenseTenTotal
	 */
	public int getLicenseTenTotal() {
		return licenseTenTotal;
	}
	/**
	 * @param licenseTenTotal the licenseTenTotal to set
	 */
	public void setLicenseTenTotal(int licenseTenTotal) {
		this.licenseTenTotal = licenseTenTotal;
	}
	
} 
