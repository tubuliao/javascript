package com.isoftstone.tyw.entity.pub;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.collect.Lists;
import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

@Entity
@Table(name="pub_license")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class License extends ID implements Serializable{

	/**
	 * 批次号
	 */
	private String batchCode;
	
	/**
	 * 用户id
	 */
	private String userId;
	
	/**
	 * 序列号类型（10人，15人）
	 */
	private int licenseType;
	
	/**
	 * 序列号状态（0：未登录，1：已登录但未交费，2：已交费）
	 */
	private int licenseStatus;
	
	
	/**
	 * 发送短信数量
	 */
	private int overplusCount;
	
	/**
	 * @return the overplusCount
	 */
	public int getOverplusCount() {
		return overplusCount;
	}

	/**
	 * @param overplusCount the overplusCount to set
	 */
	public void setOverplusCount(int overplusCount) {
		this.overplusCount = overplusCount;
	}

	/**
	 * 序列号创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date createDate = new Date();
	
	/**
	 * 序列号激活时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date activateDate ;
	
	/**
	 * 序列号
	 */
	private String licenseNum;
	
	@OneToMany(cascade={CascadeType.ALL})//级联保存、更新、删除、刷新;延迟加载
	@JoinColumn(name="licenseId")
	private List<LicenseUser> liUser = Lists.newArrayList();

	 

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(int licenseType) {
		this.licenseType = licenseType;
	}

	public int getLicenseStatus() {
		return licenseStatus;
	}

	public void setLicenseStatus(int licenseStatus) {
		this.licenseStatus = licenseStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getActivateDate() {
		return activateDate;
	}

	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}

	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}

	public List<LicenseUser> getLiUser() {
		return liUser;
	}

	public void setLiUser(List<LicenseUser> liUser) {
		this.liUser = liUser;
	}
}
