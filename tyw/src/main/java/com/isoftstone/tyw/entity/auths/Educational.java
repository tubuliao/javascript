package com.isoftstone.tyw.entity.auths;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.isoftstone.tyw.entity.ID;

/**
 * 用户教育信息表（Information user education table）
 * 记录个人受教育信息的实体（Record of personal education information entity）
 * @date 2013-3-7
 * @author 张一青（Rommel）
 * 
 */

@Entity
@Table(name="auths_educational")
public class Educational extends ID implements Serializable{
	
	/**
	 * 学校类型The type of school
	 */
	private String schoolType;
	
	/**
	 * 入学年份School year
	 */
	private String inSchool;
	
	/**
	 * 学校名称The name of the school
	 */
	private String schoolName;
	
	/**
	 * 所在地区Area
	 */
	private String area;
	
	/**
	 * 院系faculty
	 */
	private String faculty;
	
	/**
	 * 人员ID
	 */
	private String additionalId;

	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	public String getInSchool() {
		return inSchool;
	}

	public void setInSchool(String inSchool) {
		this.inSchool = inSchool;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getFaculty() {
		return faculty;
	}

	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}

	public String getAdditionalId() {
		return additionalId;
	}

	public void setAdditionalId(String additionalId) {
		this.additionalId = additionalId;
	}
	
}
