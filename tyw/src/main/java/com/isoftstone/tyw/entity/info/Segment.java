package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "info_segment")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Segment extends Base implements Serializable{
	
	/** 
	 * 扩展信息
	 */
	private String extra;

	/**
	 * 条目内容
	 */
	private String content;	
	
	/**
	 * 语音文件路径
	 */
	private String urlSound ;
	
	/**
	 * 切片附件
	 */
	private String urls;
	
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
	 * 标签条目
	 */
	private String segItem ;
	
	public Segment() {
		super(2);
	}

	public Segment(String title, String insertId,
			String insertName, Integer version, String summary, String extra,
			String content) {
		super(2, title, insertId, insertName, version, summary);
		this.extra = extra;
		this.content = content;
	}
	
	public Segment(String title, String insertId, 
			String insertName, Integer version, String summary, String extra,
			String content, String urlSound, String segItem,String urls) {
		super(2, title, insertId, insertName, version, summary);
		this.extra = extra;
		this.content = content ;
		this.urlSound = urlSound ;
		this.segItem = segItem ;
		this.urls=urls;
	}
	
	
	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrlSound() {
		return urlSound;
	}

	public void setUrlSound(String urlSound) {
		this.urlSound = urlSound;
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

	public String getSegItem() {
		return segItem;
	}

	public void setSegItem(String segItem) {
		this.segItem = segItem;
	}
	
	
	
}
