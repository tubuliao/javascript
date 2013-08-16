package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

 
/**
 * @author zhaowenli
 *
 */
@Entity
@Table(name = "view_report_form")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FormView extends ID implements Serializable{


	private String name;
	
	private int allCount;
	
	private int empCount;
	
	private int picCount;
	
	private int docCount;
	
	private int htmlCount;
	
	private String formType;

	
	/**
	 * @return the formType
	 */
	public String getFormType() {
		return formType;
	}

	/**
	 * @param formType the formType to set
	 */
	public void setFormType(String formType) {
		this.formType = formType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the allCount
	 */
	public int getAllCount() {
		return allCount;
	}

	/**
	 * @param allCount the allCount to set
	 */
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}

	/**
	 * @return the empCount
	 */
	public int getEmpCount() {
		return empCount;
	}

	/**
	 * @param empCount the empCount to set
	 */
	public void setEmpCount(int empCount) {
		this.empCount = empCount;
	}

	/**
	 * @return the picCount
	 */
	public int getPicCount() {
		return picCount;
	}

	/**
	 * @param picCount the picCount to set
	 */
	public void setPicCount(int picCount) {
		this.picCount = picCount;
	}

	/**
	 * @return the docCount
	 */
	public int getDocCount() {
		return docCount;
	}

	/**
	 * @param docCount the docCount to set
	 */
	public void setDocCount(int docCount) {
		this.docCount = docCount;
	}

	/**
	 * @return the htmlCount
	 */
	public int getHtmlCount() {
		return htmlCount;
	}

	/**
	 * @param htmlCount the htmlCount to set
	 */
	public void setHtmlCount(int htmlCount) {
		this.htmlCount = htmlCount;
	}

	
 }
