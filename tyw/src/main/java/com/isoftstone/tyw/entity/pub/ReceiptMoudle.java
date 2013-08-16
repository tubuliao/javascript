package com.isoftstone.tyw.entity.pub;

import java.io.Serializable;

import javax.persistence.Entity;

import com.isoftstone.tyw.entity.ID;

@Entity
public class ReceiptMoudle extends ID implements Serializable {

	/**
	 * 渠道商
	 */
	private String agentName;
	
	/**
	 * 批次号
	 */
	private String batchCode;
	
	/**
	 * 发票抬头
	 */
	private String receiptTitle;
	
	/**
	 * 发票人
	 */
	private String receiptPerson;
	
	/**
	 * 邮编
	 */
	private String postCode;
	
	/**
	 * 联系电话
	 */
	private String receiptPhone;
	
	/**
	 * 发票地址
	 */
	private String receiptAddress;
	
	/**
	 * 发票金额
	 */
	private String receiptAmount;
	
	/**
	 * 币种
	 */
	private String vMoneytype;
	
	/**
	 * 关联序列号
	 */
	private String licenseNumber;
	
	/**
	 * 发票状态
	 */
	private String status;
	
	public ReceiptMoudle() {}

	/**
	 * @return the agentName
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * @param agentName the agentName to set
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
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
	 * @return the receiptTitle
	 */
	public String getReceiptTitle() {
		return receiptTitle;
	}

	/**
	 * @param receiptTitle the receiptTitle to set
	 */
	public void setReceiptTitle(String receiptTitle) {
		this.receiptTitle = receiptTitle;
	}

	/**
	 * @return the receiptPerson
	 */
	public String getReceiptPerson() {
		return receiptPerson;
	}

	/**
	 * @param receiptPerson the receiptPerson to set
	 */
	public void setReceiptPerson(String receiptPerson) {
		this.receiptPerson = receiptPerson;
	}

	/**
	 * @return the postCode
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * @param postCode the postCode to set
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * @return the receiptPhone
	 */
	public String getReceiptPhone() {
		return receiptPhone;
	}

	/**
	 * @param receiptPhone the receiptPhone to set
	 */
	public void setReceiptPhone(String receiptPhone) {
		this.receiptPhone = receiptPhone;
	}

	/**
	 * @return the receiptAddress
	 */
	public String getReceiptAddress() {
		return receiptAddress;
	}

	/**
	 * @param receiptAddress the receiptAddress to set
	 */
	public void setReceiptAddress(String receiptAddress) {
		this.receiptAddress = receiptAddress;
	}

	/**
	 * @return the receiptAmount
	 */
	public String getReceiptAmount() {
		return receiptAmount;
	}

	/**
	 * @param receiptAmount the receiptAmount to set
	 */
	public void setReceiptAmount(String receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	/**
	 * @return the licenseNumber
	 */
	public String getLicenseNumber() {
		return licenseNumber;
	}

	/**
	 * @param licenseNumber the licenseNumber to set
	 */
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the vMoneytype
	 */
	public String getvMoneytype() {
		return vMoneytype;
	}

	/**
	 * @param vMoneytype the vMoneytype to set
	 */
	public void setvMoneytype(String vMoneytype) {
		this.vMoneytype = vMoneytype;
	}
	
	
}

