package com.isoftstone.tyw.entity.pub;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;


@Entity
@Table(name = "licenseview")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LicenseView extends ID implements Serializable{
	/**
	 * 批次号
	 */
	private String batchCode;
	/**
	 * 批次号创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date createDate = new Date();
	/**
	 * license号
	 */
	private String licenseNum;
	/**
	 * 项目组名称
	 */
	private String proName;
	/**
	 * license类型（10人，15人）
	 */
	private int licenseType;
	/**
	 * 交费时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date activateDate = new Date();
	/**
	 * 交费金额
	 */
	private String payAmount;
	/**
	 * license状态（是否付费）
	 */
	private int licenseStatus;
	/**
	 * agentId
	 */
	private String agentId;
	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
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
	 * @return the licenseNum
	 */
	public String getLicenseNum() {
		return licenseNum;
	}

	/**
	 * @param licenseNum the licenseNum to set
	 */
	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}

	/**
	 * @return the proName
	 */
	public String getProName() {
		return proName;
	}

	/**
	 * @param proName the proName to set
	 */
	public void setProName(String proName) {
		this.proName = proName;
	}

	/**
	 * @return the licenseType
	 */
	public int getLicenseType() {
		return licenseType;
	}

	/**
	 * @param licenseType the licenseType to set
	 */
	public void setLicenseType(int licenseType) {
		this.licenseType = licenseType;
	}

	/**
	 * @return the activateDate
	 */
	public Date getActivateDate() {
		return activateDate;
	}

	/**
	 * @param activateDate the activateDate to set
	 */
	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}

	/**
	 * @return the payAmount
	 */
	public String getPayAmount() {
		return payAmount;
	}

	/**
	 * @param payAmount the payAmount to set
	 */
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	/**
	 * @return the licenseStatus
	 */
	public int getLicenseStatus() {
		return licenseStatus;
	}

	/**
	 * @param licenseStatus the licenseStatus to set
	 */
	public void setLicenseStatus(int licenseStatus) {
		this.licenseStatus = licenseStatus;
	}
}
