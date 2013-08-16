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
 * 用户下载记录
 * 
 * @author 王锐
 */
@Entity
@Table(name = "auths_user_download")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Download{
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
	 * 下载的附件标题
	 */
	private String title;
	
	/**
	 * 下载时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date downloadDate = new Date();
	
	/**
	 * 下载的附件的url
	 */
	private String url;
	
	
	
	public Download() {
		super() ;
	}
	
	public Download(String userId, String title, Date downloadDate, String url) {
		this.userId = userId ;
		this.title = title ;
		this.downloadDate = downloadDate ;
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

	public Date getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(Date downloadDate) {
		this.downloadDate = downloadDate;
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
