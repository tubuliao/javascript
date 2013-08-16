package com.isoftstone.tyw.entity.info;

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
@Table(name = "info_source")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Source extends ID implements Serializable{
	
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
	 * 标准目次
	 */
    private String catalog ;
    
    /**
     * 标准目的
     */
    private String goal ;
    
    /**
     * 适用范围
     */
    private String scope ;
    
    /**
     * 标准介绍
     */
    private String description ;
	
	/**
	 * 切片数量
	 */
	private Integer segmentCount;
	
	/**
     * 切片名称
     */
    private String nameNo;
    
    /**
     * 来源类型（1：标准规范	2：图集	3：其他）
     */
    private String sourceType;
    
    /**
     * 排序
     */
    private Double sortNo;
    
    
    public Source() {
    	super() ;
    }
    
    public Source(String standardType, String standardName, String standardNo, String changeNo, String englishName,
    		String editDepartment, String approveDepartment, Date executeDate, String catalog, String goal, 
    		String scope, String description, Integer segmentCount, String nameNo, String sourceType, Double sortNo) {
    	this.standardType = standardType ;
    	this.standardName = standardName ;
    	this.standardNo = standardNo ;
    	this.changeNo = changeNo ;
    	this.englishName = englishName ;
    	this.editDepartment = editDepartment ;
    	this.approveDepartment = approveDepartment ;
    	this.executeDate = executeDate ;
    	this.catalog = catalog ;
    	this.goal = goal ;
    	this.scope = scope ;
    	this.description = description ;
    	this.segmentCount = segmentCount ;
    	this.nameNo = nameNo ;
    	this.sourceType = sourceType;
    	this.sortNo = sortNo;
    }

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

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSegmentCount() {
		return segmentCount;
	}

	public void setSegmentCount(Integer segmentCount) {
		this.segmentCount = segmentCount;
	}

	public String getNameNo() {
		return nameNo;
	}

	public void setNameNo(String nameNo) {
		this.nameNo = nameNo;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * @return the sortNo
	 */
	public Double getSortNo() {
		return sortNo;
	}

	/**
	 * @param sortNo the sortNo to set
	 */
	public void setSortNo(Double sortNo) {
		this.sortNo = sortNo;
	}

	
}
