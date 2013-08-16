/**
 *  Simcore.org Copyright (C) 2010 ray

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.isoftstone.tyw.entity.auths;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.common.collect.Sets;
import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.entity.info.Bookmark;
import com.isoftstone.tyw.entity.info.Classification;
import com.isoftstone.tyw.util.JsonDateSerializer;



/**
 * 用户.
 * 
 * @author ray
 */
@Entity
@Table(name = "auths_user")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

public class User extends ID implements Serializable{

	/**
	 * 帐户名称
	 */
	@Column(nullable=false,unique=true)
	private String username;
	
	/**
	 * 帐户密码
	 */
	@Column(nullable=false)
	private String password;
	
	/**
	 * 账户别名
	 */
	private String aliasname;
	
	/**
	 * 是否管理员
	 */
	@Column(nullable=false)
	private Integer userType;

	/**
	 * 帐户到期日
	 */
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using=JsonDateSerializer.class)
	private Date accountExpireDate;

	/**
	 * 证书到期日
	 */
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using=JsonDateSerializer.class)
	private Date credentialsExpireDate;

	/**
	 * 帐户可用
	 */
	@Column(nullable=false)
	private Boolean enable;

	/**
	 * 帐户未锁定
	 */
	@Column(nullable=false)
	private Boolean nonLocked;
	
	/**
	 * 用户附加信息
	 */
	@OneToOne(optional=true, cascade={CascadeType.ALL},fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Additional additional;
	
	/**
	 * 渠道商附加信息
	 */
	@OneToOne(optional=true, cascade={CascadeType.ALL},fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Agent agent;
	
	/**
	 * 企业会员附加信息
	 */
	@OneToOne(optional=true, cascade={CascadeType.ALL},fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Firm firm;
	
	/**
	 * 用户状态
	 */
	private String status;
	
	/**
	 * 邮箱
	 */
	private String mail;
	
	/**
	 * 手机
	 */
	private String phone;
	
	 
	/**
	 * 头像地址
	 */
	private String headUrl; 
	
	/**
	 * 用户等级  普通：0 ， 黄金vip ：1， 白金vip：2， 钻石vip ：3
	 */
	private int grade;
	
	/**
	 * 收藏信息
	 */
//	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})//级联保存、更新、删除、刷新;延迟加载
//	@JoinColumn(name="userId")
//	private Set<Bookmark> bookmarkSet = Sets.newHashSet() ;
	

	/**
	 * 收藏分类
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})//级联保存、更新、删除、刷新;延迟加载
	@JoinColumn(name="userId")
	private Set<Classification> classificationSet = Sets.newHashSet() ;
	
	/**
	 * 是否第一次登陆：值为0时从未登陆过，值为1时已经登陆过
	 */
	private String firstLogin;
	
	/**
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * @return the headUrl
	 */
	public String getHeadUrl() {
		return headUrl;
	}

	/**
	 * @param headUrl the headUrl to set
	 */
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	/** 
	 * 账户角色列表
	 */
	@ManyToMany
	@JoinTable(name = "auths_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id ASC")
	private Set<Role> roles = Sets.newHashSet();

	public User() {
		super();
	}

	public User(String username, String password, Integer userType, Boolean nonLocked) {
		super();
		this.username = username;
		this.password = password;
		this.userType = userType;
		this.enable = false;
		this.nonLocked = nonLocked;
	}

	public User(String username, String password, String aliasname,
			Integer userType, Boolean nonLocked, String status, Set<Bookmark> bookmarkSet, Set<Classification> classificationSet) {
		super();
		this.username = username;
		this.password = password;
		this.aliasname = aliasname;
		this.userType = userType;
		this.nonLocked = nonLocked;
		this.status = status;
//		this.bookmarkSet = bookmarkSet ;
		this.classificationSet = classificationSet ;
	}
	
	
	 /*企业表中的企业名称字段*/
	@Transient
	private String name;
	
	/*企业表中的联系地址字段*/
	@Transient
	private String addr;
	
	/*企业表中的联系人字段*/
	@Transient
	private String linkman;
	
	/*企业表中的联系电话字段*/
	@Transient
	private String fphone;
	
	/*企业表中的传真字段*/
	@Transient
	private String fax;
	
	/*企业表中的邮编字段*/
	@Transient
	private String zip;
	
	/*企业表中的邮箱字段*/
	@Transient
	private String email;
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAliasname() {
		return aliasname;
	}

	public void setAliasname(String aliasname) {
		this.aliasname = aliasname;
	}


	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Date getAccountExpireDate() {
		return accountExpireDate;
	}

	public void setAccountExpireDate(Date accountExpireDate) {
		this.accountExpireDate = accountExpireDate;
	}

	public Date getCredentialsExpireDate() {
		return credentialsExpireDate;
	}

	public void setCredentialsExpireDate(Date credentialsExpireDate) {
		this.credentialsExpireDate = credentialsExpireDate;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Boolean getNonLocked() {
		return nonLocked;
	}

	public void setNonLocked(Boolean nonLocked) {
		this.nonLocked = nonLocked;
	}

	public Additional getAdditional() {
		return additional;
	}

	public void setAdditional(Additional additional) {
		this.additional = additional;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public boolean isAccountNonExpired(){
		return (accountExpireDate==null)?true:!accountExpireDate.before(new Date());
	}
	
	public boolean isCredentialsNonExpired(){
		return (credentialsExpireDate==null)?true:!credentialsExpireDate.before(new Date());
	}
	
	public boolean isAccountNonLocked() {
		return this.nonLocked;
	}
	
	public boolean isEnabled() {
		return this.enable;
	}
	
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Firm getFirm() {
		return firm;
	}

	public void setFirm(Firm firm) {
		this.firm = firm;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getFphone() {
		return fphone;
	}

	public void setFphone(String fphone) {
		this.fphone = fphone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	public Set<Bookmark> getBookmarkSet() {
//		return bookmarkSet;
//	}
//
//	public void setBookmarkSet(Set<Bookmark> bookmarkSet) {
//		this.bookmarkSet = bookmarkSet;
//	}

	public Set<Classification> getClassificationSet() {
		return classificationSet;
	}

	public void setClassificationSet(Set<Classification> classificationSet) {
		this.classificationSet = classificationSet;
	}

	public String getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(String firstLogin) {
		this.firstLogin = firstLogin;
	}
	
}