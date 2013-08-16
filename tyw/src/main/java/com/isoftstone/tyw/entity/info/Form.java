package com.isoftstone.tyw.entity.info;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.google.common.collect.Lists;

@Entity
@Table(name = "info_form")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Form extends Base implements Serializable{

	
	/**
	 * 空表源文件地址
	 */
	private String empUrl;
	   /*文件后缀 */
    @Transient
    private String suffix ;
	/**
	 * 空表WEB页面展示图片地址
	 */
	private String empHiPicUrl ;
	
	/**
	 * 空表移动端展示图片地址
	 */
	private String empLowPicUrl ;
	
	/**
	 * 样表源文件地址
	 */
	private String sampUrl ;
	
	/**
	 * 样表WEB页面展示图片地址
	 */
	private String sampHiPicUrl ;
	
	/**
	 * 样表移动端展示图片地址
	 */
	private String sampLowPicUrl ;
	
	/**
	 * 填表说明源文件地址
	 */
	private String descUrl ;
	
	/**
	 * 填表说明具体内容
	 */
	private String descContent ;

	
	/*人员标签*/
	@Transient
	private String personnelTag ;
	
	/*地区标签 */
	@Transient
	private String areaTag ;
	
	/*知识性质标签*/
	@Transient
	private String knowledgeTag ;
	
	/*分部分项标签*/
	@Transient
	private String subitemTag ;
	
	/*标签汇总*/
	@Transient
	private String tagArray ;
	
	/**
	 *  空表与样表的一对多关系
	 */
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})//级联保存、更新、删除、刷新;延迟加载
	@JoinColumn(name="formId")
	private List<FormExt> formExtList = Lists.newArrayList() ;
	
	public Form() {
		super(4) ;
	}
	
	public Form(String empUrl, String empHiPicUrl, String empLowPicUrl, String sampUrl, String sampHiPicUrl,
					String sampLowPicUrl, String descUrl, String descContent) {
		this.empUrl = empUrl ;
		this.empHiPicUrl = empHiPicUrl ;
		this.empLowPicUrl = empLowPicUrl ;
		this.sampUrl = sampUrl ;
		this.sampHiPicUrl = sampHiPicUrl ;
		this.sampLowPicUrl = sampLowPicUrl ;
		this.descUrl = descUrl ;
		this.descContent = descContent ;
	}
	
	public String getEmpUrl() {
		return empUrl;
	}

	public void setEmpUrl(String empUrl) {
		this.empUrl = empUrl;
	}

	public String getEmpHiPicUrl() {
		return empHiPicUrl;
	}

	public void setEmpHiPicUrl(String empHiPicUrl) {
		this.empHiPicUrl = empHiPicUrl;
	}

	public String getEmpLowPicUrl() {
		return empLowPicUrl;
	}

	public void setEmpLowPicUrl(String empLowPicUrl) {
		this.empLowPicUrl = empLowPicUrl;
	}

	public String getSampUrl() {
		return sampUrl;
	}

	public void setSampUrl(String sampUrl) {
		this.sampUrl = sampUrl;
	}

	public String getSampHiPicUrl() {
		return sampHiPicUrl;
	}

	public void setSampHiPicUrl(String sampHiPicUrl) {
		this.sampHiPicUrl = sampHiPicUrl;
	}

	public String getSampLowPicUrl() {
		return sampLowPicUrl;
	}

	public void setSampLowPicUrl(String sampLowPicUrl) {
		this.sampLowPicUrl = sampLowPicUrl;
	}

	public String getDescUrl() {
		return descUrl;
	}

	public void setDescUrl(String descUrl) {
		this.descUrl = descUrl;
	}

	public String getDescContent() {
		return descContent;
	}

	public void setDescContent(String descContent) {
		this.descContent = descContent;
	}

	public String getPersonnelTag() {
		return personnelTag ;
	}
	
	public void setPersonnelTag(String personnelTag) {
		this.personnelTag = personnelTag ;
	}
	
	public String getAreaTag() {
		return areaTag ;
	}
	
	public void setAreaTag(String areaTag) {
		this.areaTag = areaTag ;
	}
	
	public String getKnowledgeTag() {
		return knowledgeTag ;
	}
	
	public void setKnowledgeTag(String knowledgeTag) {
		this.knowledgeTag = knowledgeTag ;
	}
	
	public String getSubitemTag() {
		return subitemTag ;
	}
	
	public void setSubitemTag(String subitemTag) {
		this.subitemTag = subitemTag ;
	}
	
	public String getTagArray() { 
		return this.personnelTag + "&" +  this.areaTag + "&" + this.knowledgeTag + "&" + this.subitemTag ;
	}
	
//	public void setTagArray() {
//	    this.tagArray = this.personnelTag + "、" +  this.areaTag + "、" + this.knowledgeTag + "、" + this.subitemTag ;	
//	}

	public void setTagArray(String tagArray) {
		this.tagArray = tagArray;
	}

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
	
	
}
