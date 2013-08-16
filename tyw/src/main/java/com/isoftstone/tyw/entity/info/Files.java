package com.isoftstone.tyw.entity.info;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "info_files")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Files extends Base implements Serializable{

	/**
	 * 文件地址
	 */
	private String url;
	
	/**
	 * 文件与附件的关系
	 */
//	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL})//级联保存、更新、删除、刷新;延迟加载
//	@JoinColumn(name="fileId")
//	private List<Attachment> attachments = Lists.newArrayList();
	
	/*人员标签*/
	@Transient
	private String personnelTag ;
	
	/*地区标签 */
	@Transient
	private String areaTag ;
	
	/*文件后缀 */
    @Transient
    private String suffix ;
	
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
	 * 文件说明
	 */
	private String content ;
	
	/**
	 * 文件总页数
	 */
	private Integer totalPages=0;
	
	/**
	 * 文件目录
	 */
	@Column(updatable=false)
	private String catalog ;
	
	
	/**
	 * @return the totalPages
	 */
	public Integer getTotalPages() {
		return totalPages;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the catalog
	 */
	public String getCatalog() {
		return catalog;
	}

	/**
	 * @param catalog the catalog to set
	 */
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public Files() {
		super(1);
	}
	
	/**
	 * @param url url地址
	 */
	public Files(String title, String insertId,
			String insertName, Integer version, String summary,String url, String content) {
		super(1, title, insertId, insertName, version, summary);
		this.url=url;
		this.content = content ;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/*
	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	*/
	
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
	
	
}
