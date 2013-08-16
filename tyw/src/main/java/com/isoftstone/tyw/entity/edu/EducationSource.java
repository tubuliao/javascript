package com.isoftstone.tyw.entity.edu;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

/**
 * 课件类
 * @author zhaowenli
 *
 */
@Entity
@Table(name = "edu_source")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EducationSource extends ID implements Serializable{
 
	private String sourceName;//课程名称
	private String workUnit;//工作单位
	private String sourceType;//课程类型
	private String teacher;//授课教师
	private String status;//状态
	private String description;//课程简介
	private String parentSourceId;//上级课程ID
	private String sortid;//排序索引
	private String period;//学时（分钟）
	private Integer sourceWareCount;//课件数量
	private String sourceWareurlFirst;//课程地址_1
	private String sourceWareurlSecond;//课程地址_2
	private String sourceWareurlThird;//课程地址_3
	private String postName;//专业
	private Integer sourceYear;//年度
	/**
	 * @return the sourceName
	 */
	public String getSourceName() {
		return sourceName;
	}
	/**
	 * @param sourceName the sourceName to set
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	/**
	 * @return the workUnit
	 */
	public String getWorkUnit() {
		return workUnit;
	}
	/**
	 * @param workUnit the workUnit to set
	 */
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	/**
	 * @return the sourceType
	 */
	public String getSourceType() {
		return sourceType;
	}
	/**
	 * @param sourceType the sourceType to set
	 */
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	/**
	 * @return the teacher
	 */
	public String getTeacher() {
		return teacher;
	}
	/**
	 * @param teacher the teacher to set
	 */
	public void setTeacher(String teacher) {
		this.teacher = teacher;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the parentSourceId
	 */
	public String getParentSourceId() {
		return parentSourceId;
	}
	/**
	 * @param parentSourceId the parentSourceId to set
	 */
	public void setParentSourceId(String parentSourceId) {
		this.parentSourceId = parentSourceId;
	}
	/**
	 * @return the sortid
	 */
	public String getSortid() {
		return sortid;
	}
	/**
	 * @param sortid the sortid to set
	 */
	public void setSortid(String sortid) {
		this.sortid = sortid;
	}
	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}
	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}
	/**
	 * @return the sourceWareCount
	 */
	public Integer getSourceWareCount() {
		return sourceWareCount;
	}
	/**
	 * @param sourceWareCount the sourceWareCount to set
	 */
	public void setSourceWareCount(Integer sourceWareCount) {
		this.sourceWareCount = sourceWareCount;
	}
	/**
	 * @return the sourceWareurlFirst
	 */
	public String getSourceWareurlFirst() {
		return sourceWareurlFirst;
	}
	/**
	 * @param sourceWareurlFirst the sourceWareurlFirst to set
	 */
	public void setSourceWareurlFirst(String sourceWareurlFirst) {
		this.sourceWareurlFirst = sourceWareurlFirst;
	}
	/**
	 * @return the sourceWareurlSecond
	 */
	public String getSourceWareurlSecond() {
		return sourceWareurlSecond;
	}
	/**
	 * @param sourceWareurlSecond the sourceWareurlSecond to set
	 */
	public void setSourceWareurlSecond(String sourceWareurlSecond) {
		this.sourceWareurlSecond = sourceWareurlSecond;
	}
	/**
	 * @return the sourceWareurlThird
	 */
	public String getSourceWareurlThird() {
		return sourceWareurlThird;
	}
	/**
	 * @param sourceWareurlThird the sourceWareurlThird to set
	 */
	public void setSourceWareurlThird(String sourceWareurlThird) {
		this.sourceWareurlThird = sourceWareurlThird;
	}
	/**
	 * @return the postName
	 */
	public String getPostName() {
		return postName;
	}
	/**
	 * @param postName the postName to set
	 */
	public void setPostName(String postName) {
		this.postName = postName;
	}
	/**
	 * @return the sourceYear
	 */
	public Integer getSourceYear() {
		return sourceYear;
	}
	/**
	 * @param sourceYear the sourceYear to set
	 */
	public void setSourceYear(Integer sourceYear) {
		this.sourceYear = sourceYear;
	}
	
}
