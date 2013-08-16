package com.isoftstone.tyw.entity.info;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.common.collect.Sets;
import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

/**
 * 内容基类
 * 
 * @author zhanglei
 */
@Entity
@Table(name="info_base")
@Inheritance(strategy=InheritanceType.JOINED)
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Base extends ID implements Serializable{
	
	/**
	 * 消息名称
	 */
	protected String title;

	/**
	 * 插入记录用户ID
	 */
	@Column(updatable=false)
	protected String insertId;
	
	/** 
	 * 录入人别名
	 */
	@Column(updatable=false)
	protected String insertName;
	
	/**
	 * 描述
	 */
	protected String description;
	
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	@Column(updatable=false)
	protected Date createDate=new Date();
	
	/**
	 * 审核记录用户ID
	 */
	protected String checkId;
	
	/**
	 * 审核人别名
	 */
	protected String checkName;
	
	/**
	 * 审核日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date checkDate;
	
	/**
	 * 审核状态(0:未审批 1:审批通过 2:驳回 3:已二次审批)
	 */
	protected Integer state = 0;
	
	/**
	 * 申请删除(0:未申请 1:已申请)
	 */
	protected Integer applyDetele = 0;
	
	/**
	 * 点击率
	 */
	protected Integer clickCount = 0;
	
	/**
	 * 推荐表示 1推荐 0不推荐
	 */
	protected Integer recommend = 0;
	/**
	 * 审核意见,审核驳回时填写
	 */
	protected String auditInfo;
	
	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date modifyDate = new Date();
	
	/**
	 * 修改记录用户ID
	 */
	protected String modifyId;
	
	/**
	 * 修改人别名
	 */
	protected String modifyName;
	
	/**
	 * 内容摘要
	 */
	protected String summary;
	
	/**
	 * 内容版本号
	 */
	protected Integer version;
	
	/**
	 * 内容类型
	 * files:1
	 * Segment:2
	 * video:3
	 * Form:4
	 * image:5
	 * ppt:6
	 * question:7
	 */
	@Column(nullable=false)
	protected Integer infoType;
	
	/**
	 * 内容与分部分项关系
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "info_base_tag", joinColumns = { @JoinColumn(name = "base_id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id ASC")
	protected Set<Tag> tags = Sets.newHashSet();
	
	/**
	 * 简标题 
	 */
	protected String shortTitle ;
	
	/**
	 * 出处
	 */
	protected String source ;
	
	/**
	 * 发布时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date begincreateDate ;
	
	/**
	 * 发布时间
	 */
	@Transient
	protected String publishDate;
	
	/**
	 * 最后修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date lastmodifyTime ;
	
	/**
	 * 权重值 默认值：7 （1~10）
	 */
	protected int weighing = 7;
	
	public Base() {
		super();
	}
	
	public Base(String id, Integer infoType) {
		super(id);
		this.infoType = infoType;
	}
	
	public Base(Integer infoType) {
		super();
		this.infoType = infoType;
	}

	public Base(int infoType,String title, String insertId, String insertName, Integer version, String summary) {
		super();
		this.title = title;
		this.infoType = infoType;
		this.insertId = insertId;
		this.insertName = insertName;
		this.version = version;
		this.summary = summary;
	}
	
	public Base(int infoType, String title, String insertId, String insertName, Integer version, String summary,
			String shortTitle, String source, Date begincreateDate, Date lastmodifyTime, String modifyId, String modifyName) {
		super() ;
		this.title = title ;
		this.infoType = infoType ;
		this.insertId = insertId ;
		this.insertName = insertName ;
		this.version = version ;
		this.summary = summary ;
		this.shortTitle = shortTitle ;
		this.source = source ;
		this.begincreateDate = begincreateDate ;
		this.lastmodifyTime = lastmodifyTime ;
		this.modifyId = modifyId;
		this.modifyName = modifyName;
	}

	
	

	public void setPublishDate(String publishDate)throws Exception{
		if(publishDate!=null&&!"".equals(publishDate)){
			java.text.SimpleDateFormat smdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
			this.setBegincreateDate(smdf.parse(publishDate));
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getInsertId() {
		return insertId;
	}

	public void setInsertId(String insertId) {
		this.insertId = insertId;
	}

	public String getInsertName() {
		return insertName;
	}

	public void setInsertName(String insertName) {
		this.insertName = insertName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCheckId() {
		return checkId;
	}

	public void setCheckId(String checkId) {
		this.checkId = checkId;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getInfoType() {
		return infoType;
	}

	public void setInfoType(Integer infoType) {
		this.infoType = infoType;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getSource() {
		if(StringUtils.isBlank(this.source)){
			return "";
		}
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getBegincreateDate() {
		return begincreateDate;
	}

	public void setBegincreateDate(Date begincreateDate) {
		this.begincreateDate = begincreateDate;
	}

	public Date getLastmodifyTime() {
		return lastmodifyTime;
	}

	public void setLastmodifyTime(Date lastmodifyTime) {
		this.lastmodifyTime = lastmodifyTime;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Integer getClickCount() {
		return clickCount;
	}

	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public int getWeighing() {
		return weighing;
	}

	public void setWeighing(int weighing) {
		this.weighing = weighing;
	}
	
	
	
}
