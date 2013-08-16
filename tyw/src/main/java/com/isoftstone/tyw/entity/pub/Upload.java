package com.isoftstone.tyw.entity.pub;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateSerializer;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

@Entity
@Table(name = "pub_upload")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Upload extends ID implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1864259982703936718L;
	/**
	 * 上传知识内容
	 */
	@Lob
	private String content;
	/**
	 * 审核人
	 */
	private String checkName;
	
	/**
	 * 最后修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date modifyDate;
	/**
	 * 状态(0:未审核,1:审核通过,2:拒绝)
	 */
	private Integer status;
	/**
	 * 审核时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date checkDate;
	
	/**
	 * 上传者姓名
	 */
	private String uploadName;
	
	/**
	 * 上传日期
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date createDate = new Date();
	
	public String getFormatCreateDate(){
		String result = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		if(null != createDate){
			result = df.format(createDate);
		}
		return result;
	}
	
	/**
	 * 连接地址S
	 */
	private String url;
	/**
	 * 
	 * 稿件标题
	 */
	private String title;
	/**
	 * 
	 * 上传文件的类型
	 */
	private String uploadType;
	
	/**
	 * 
	 * 上传文件的知识类型
	 */
	private String tagType;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}


	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
}
