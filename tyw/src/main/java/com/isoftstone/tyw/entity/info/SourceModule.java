package com.isoftstone.tyw.entity.info;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

@Entity
public class SourceModule extends ID {

	/**
	 * 标准类型
	 */
	private String standardType;  
	
	/**
	 * 标准名称
	 */
	private String standardName;
	
	/**
	 * 标准编号
	 */
	private String standardNo;
	
	 /**
     * 被替标准编号
     */
    private String changeNo ;
    
    /**
     * 英文名称
     */
    private String englishName ;

	/**
	 * 主编单位
	 */
	private String editDepartment;
	
	/**
	 * 批准部门
	 */
	private String approveDepartment;
	
	 /**
	 * 实施日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date executeDate = new Date();
	
	/**
	 * 切片数量
	 */
	private Integer segmentCount;
	
	
	public SourceModule() {}


	public String getStandardType() {
		return standardType;
	}


	public void setStandardType(String standardType) {
		this.standardType = standardType;
	}


	public String getStandardName() {
		return standardName;
	}


	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}


	public String getStandardNo() {
		return standardNo;
	}


	public void setStandardNo(String standardNo) {
		this.standardNo = standardNo;
	}


	public String getChangeNo() {
		return changeNo;
	}


	public void setChangeNo(String changeNo) {
		this.changeNo = changeNo;
	}


	public String getEnglishName() {
		return englishName;
	}


	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}


	public String getEditDepartment() {
		return editDepartment;
	}


	public void setEditDepartment(String editDepartment) {
		this.editDepartment = editDepartment;
	}


	public String getApproveDepartment() {
		return approveDepartment;
	}


	public void setApproveDepartment(String approveDepartment) {
		this.approveDepartment = approveDepartment;
	}


	public Date getExecuteDate() {
		return executeDate;
	}


	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}


	public Integer getSegmentCount() {
		return segmentCount;
	}


	public void setSegmentCount(Integer segmentCount) {
		this.segmentCount = segmentCount;
	}
	
}
