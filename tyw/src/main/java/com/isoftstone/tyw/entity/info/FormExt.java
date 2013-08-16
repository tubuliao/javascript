package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "info_form_ext")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FormExt extends Base implements Serializable{

	/**
	 * 样表源文件地址
	 */
	private String sampUrl ;
	
	/**
	 * 样表WEB页面图片展示地址
	 */
	private String sampHiPicUrl ;
	
	/**
	 * 样表移动端图片展示地址
	 */
	private String sampLowPicUrl ;
	
	/**
	 * 关联空表外键
	 */
	private String formId ;
	
	
	public FormExt() {
		super(5) ;
	}
	
	public FormExt(String sampUrl, String sampHiPicUrl, String sampLowPicUrl, String formId) {
		this.sampUrl = sampUrl ;
		this.sampHiPicUrl = sampHiPicUrl ;
		this.sampLowPicUrl = sampLowPicUrl ;
		this.formId = formId ;
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

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	
}
