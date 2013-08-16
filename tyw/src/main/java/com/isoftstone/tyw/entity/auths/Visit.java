package com.isoftstone.tyw.entity.auths;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.isoftstone.tyw.util.JsonDateTimeSerializer;
/**
 * 用户最近访问表
 * 
 * @author 王锐
 */
@Entity
@Table(name = "auths_user_visit")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Visit{
	/**
	 * 主键id
	 */
	@Id
	private int id;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 浏览过的标题
	 */
	private String title;
	
	/**
	 * 最后一次浏览时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date visitDate = new Date();
	
	/**
	 * 浏览过的url
	 */
	private String url;
	
	/**
	 * 浏览过的内容（知识、表格、图片等）的id
	 */
	private String visitId;
	
	
	public Visit() {
		super() ;
	}
	
	public Visit(String userId, String title, Date visitDate, String url) {
		this.userId = userId ;
		this.title = title ;
		this.visitDate = visitDate ;
		this.url = url ;
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getVisitId() {
		return visitId;
	}

	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
