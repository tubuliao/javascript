package com.isoftstone.tyw.entity.info;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

@Entity
public class LicensePaymentModule extends ID {

	
	/**
	 * 用户
	 */
	private String aliasname;
	
	/**
	 * 交费状态
	 */
	private String payStatus;
	
	/**
	 * 交费金额
	 */
	private String payAmount;
	
	/**
	 * 交费时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date payDate = new Date();
	
	/**
	 * 订单编号
	 */
	private String vOid;
	
	/**
	 * 支付方式
	 */
	private String vPmode;
	
	/**
	 * 支付结果信息
	 */
	private String vPstring;
	
	/**
	 * 币种
	 */
	private String vMoneytype;
	
	/**
	 * 渠道商
	 */
	private String name;
	
	/**
	 * 批次号
	 */
	private String batchCode;
	
	/**
	 * default constructor
	 */
	public LicensePaymentModule() {}

	public String getAliasname() {
		return aliasname;
	}

	public void setAliasname(String aliasname) {
		this.aliasname = aliasname;
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

	public String getvOid() {
		return vOid;
	}

	public void setvOid(String vOid) {
		this.vOid = vOid;
	}

	public String getvPmode() {
		return vPmode;
	}

	public void setvPmode(String vPmode) {
		this.vPmode = vPmode;
	}

	public String getvPstring() {
		return vPstring;
	}

	public void setvPstring(String vPstring) {
		this.vPstring = vPstring;
	}

	public String getvMoneytype() {
		return vMoneytype;
	}

	public void setvMoneytype(String vMoneytype) {
		this.vMoneytype = vMoneytype;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
