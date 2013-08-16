/**
≥ *  Simcore.org Copyright (C) 2010 ray

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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.isoftstone.tyw.util.JsonDateSerializer;

/**
 * 用户附加信息
 * 
 * @author ray
 *
 */
@Entity
@Table(name="auths_additional")
public class Additional implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2395690447603455585L;
	
	@Id
    @GenericGenerator(name = "generator", 
                      strategy = "foreign", 
                      parameters = { 
                        @Parameter(name = "property", value = "user") 
                      })
    @GeneratedValue(generator = "generator")
	private String id;
	
	@OneToOne(optional=true, mappedBy="additional")
	@JsonIgnore
	private User user;
	
	/**
	 * 用户移动电话号码，用于进行短信通知
	 */
	private String mobile;

	/**
	 * 用户电子邮箱地址，用于email通知
	 */
	private String email;
	
	/**
	 * 用户性别
	 * 1:男
	 * 0:女
	 */
	private int sex;
	
	/**
	 * 原籍
	 */
	private String originalFamilyHome;
	/**
	 * 专业
	 */
	private String profession;
	/**
	 * 工作性质
	 */
	private String jobNature;
	/**
	 * 公司性质
	 */
	private String companyNature;
	/**
	 * qq
	 */
	private String qq;
	/**
	 * 简介
	 */
	private String brief; 

	/**
	 * 
	 * @return
	 */
	private String location;
	
	private String personId;
	
	@Temporal(TemporalType.DATE)
	@JsonSerialize(using=JsonDateSerializer.class)
	private Date birthday;
	
	private String name;
	
	private String firmId;
	
	/**
	 * 用户经验
	 */
	private String experience ;
	
	/**
	 * 用户等级
	 */
	private String grade ;
	
	/**
	 * @return the originalFamilyHome
	 */
	public String getOriginalFamilyHome() {
		return originalFamilyHome;
	}


	/**
	 * @param originalFamilyHome the originalFamilyHome to set
	 */
	public void setOriginalFamilyHome(String originalFamilyHome) {
		this.originalFamilyHome = originalFamilyHome;
	}


	/**
	 * @return the profession
	 */
	public String getProfession() {
		return profession;
	}


	/**
	 * @param profession the profession to set
	 */
	public void setProfession(String profession) {
		this.profession = profession;
	}


	/**
	 * @return the jobNature
	 */
	public String getJobNature() {
		return jobNature;
	}


	/**
	 * @param jobNature the jobNature to set
	 */
	public void setJobNature(String jobNature) {
		this.jobNature = jobNature;
	}


	/**
	 * @return the companyNature
	 */
	public String getCompanyNature() {
		return companyNature;
	}


	/**
	 * @param companyNature the companyNature to set
	 */
	public void setCompanyNature(String companyNature) {
		this.companyNature = companyNature;
	}


	/**
	 * @return the qq
	 */
	public String getQq() {
		return qq;
	}


	/**
	 * @param qq the qq to set
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}


	/**
	 * @return the brief
	 */
	public String getBrief() {
		return brief;
	}


	/**
	 * @param brief the brief to set
	 */
	public void setBrief(String brief) {
		this.brief = brief;
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public int getSex() {
		return sex;
	}


	public void setSex(int sex) {
		this.sex = sex;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getPersonId() {
		return personId;
	}


	public void setPersonId(String personId) {
		this.personId = personId;
	}


	public Date getBirthday() {
		return birthday;
	}


	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFirmId() {
		return firmId;
	}


	public void setFirmId(String firmId) {
		this.firmId = firmId;
	}


	public String getExperience() {
		return experience;
	}


	public void setExperience(String experience) {
		this.experience = experience;
	}


	public String getGrade() {
		return grade;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
}
