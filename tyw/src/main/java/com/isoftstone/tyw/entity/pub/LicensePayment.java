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
@Table(name="pub_license_payment")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LicensePayment extends ID implements Serializable{

	/**
	 * 序列号id
	 */
	private String licenseId;
	
	/**
	 * 用户id
	 */
	private String userId;
	
	/**
	 * 交费状态
	 */
	private String payStatus;
	
	/**
	 * 交费金额
	 */
	private String payAmount;
	
	/**
	 * 交费日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date payDate = new Date();
	
	private String vOid;
	private String vPmode;
	private String vPstring;
	private String vMd5info;
	private String vMd5money;
	private String vMoneytype;
	
	/**
	 * 0：不要  1：要
	 */
	private int  isReceipt;
	/**
	 * 发票抬头
	 */
	private String receiptTitle;
	
	/**
	 * 第三方id
	 */
	private String thirdId;

	
	/**
	 * @return the isReceipt
	 */
	public int getIsReceipt() {
		return isReceipt;
	}

	/**
	 * @param isReceipt the isReceipt to set
	 */
	public void setIsReceipt(int isReceipt) {
		this.isReceipt = isReceipt;
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
	 * @return the vOid
	 */
	public String getvOid() {
		return vOid;
	}

	/**
	 * @param vOid the vOid to set
	 */
	public void setvOid(String vOid) {
		this.vOid = vOid;
	}

	/**
	 * @return the vPmode
	 */
	public String getvPmode() {
		return vPmode;
	}

	/**
	 * @param vPmode the vPmode to set
	 */
	public void setvPmode(String vPmode) {
		this.vPmode = vPmode;
	}

	/**
	 * @return the vPString
	 */
	public String getvPstring() {
		return vPstring;
	}

	/**
	 * @param vPString the vPString to set
	 */
	public void setvPstring(String vPstring) {
		this.vPstring = vPstring;
	}

	/**
	 * @return the vMd5info
	 */
	public String getvMd5info() {
		return vMd5info;
	}

	/**
	 * @param vMd5info the vMd5info to set
	 */
	public void setvMd5info(String vMd5info) {
		this.vMd5info = vMd5info;
	}

	/**
	 * @return the vMd5money
	 */
	public String getvMd5money() {
		return vMd5money;
	}

	/**
	 * @param vMd5money the vMd5money to set
	 */
	public void setvMd5money(String vMd5money) {
		this.vMd5money = vMd5money;
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

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getThirdId() {
		return thirdId;
	}

	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}
}
