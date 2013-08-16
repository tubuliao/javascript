package com.isoftstone.tyw.dto.info;

import java.util.Date;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.google.common.collect.Sets;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

public class FormDTO{
	private String id;
	private String title;
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date createDate = new Date();
	protected Set<TagDTO> tags = Sets.newHashSet();
	
	private String source ;
	private String insertName ;
	private String modifyName ;
	private Integer state ;
	private String empUrl ;
	private String empHiPicUrl ;
	private String empLowPicUrl ;
	private String descUrl ;
	private String descContent ;
	
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date modifyDate ;

	private String tagStr;
	public String getTagStr() {
		return tagStr;
	}
	public void setTagStr(String tagStr) {
		this.tagStr = tagStr;
	}

	public FormDTO(String id, String title, Date createDate,
			String tagStr) {
		super();
		this.id = id;
		this.title = title;
		this.createDate = createDate;
		this.tagStr = tagStr;
	}
	
	public FormDTO(String id, String title, String source, String insertName, Date createDate, Integer state, /*Set<TagDTO> tags,*/
					String empUrl, String empHiPicUrl, String empLowPicUrl, String descUrl, String descContent, Date modifyDate, String modifyName) {
		super() ;
		this.id = id ;
		this.title = title ;
		this.source = source ;
		this.insertName = insertName ;
		this.createDate = createDate ;
		this.state = state ;
//		this.tags = tags ;
		this.empUrl = empUrl ;
		this.empHiPicUrl = empHiPicUrl ;
		this.empLowPicUrl = empLowPicUrl ;
		this.descUrl = descUrl ;
		this.descContent = descContent ;
		this.modifyDate = modifyDate ;
		this.modifyName = modifyName;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Set<TagDTO> getTags() {
		return tags;
	}
	public void setTags(Set<TagDTO> tags) {
		this.tags = tags;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getInsertName() {
		return insertName;
	}
	public void setInsertName(String insertName) {
		this.insertName = insertName;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	public String getModifyName() {
		return modifyName;
	}
	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
}