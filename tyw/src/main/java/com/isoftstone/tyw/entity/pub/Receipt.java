package com.isoftstone.tyw.entity.pub;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

@Entity
@Table(name="pub_license_receipt")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Receipt extends ID implements Serializable{

	/**
	 * 发票针对的序列号
	 */
	private String licenseNumber;
	/**
	 * 发票抬头
	 */
	private String receiptTitle;
	/**
	 * 联系人
	 */
	private String receiptPerson;
	/**
	 *邮编
	 */
	private String postCode;
	/**
	 * 联系电话
	 */
	private String receiptPhone;
	/**
	 * 发票邮寄地址
	 */
	private String receiptAddress;
	/**
	 * 金额
	 */
	private Double receiptAmount;
	
	/**
	 * 状态 0未开，1已开
	 */
	private int status;
	
	
	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
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
	public Double getReceiptAmount() {
		return receiptAmount;
	}
	/**
	 * @param receiptAmount the receiptAmount to set
	 */
	public void setReceiptAmount(Double receiptAmount) {
		this.receiptAmount = receiptAmount;
	}
	
	
	
}
