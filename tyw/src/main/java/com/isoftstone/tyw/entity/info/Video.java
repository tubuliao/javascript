package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "info_video")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Video extends Base implements Serializable{

	/**
	 * 视频地址
	 */
	private String url;
    /*文件后缀 */
  @Transient
  private String suffix ;
	/**
	 * 视频说明
	 */
	private String content ;

	public Video() {
		super(3);
	}
	
	/**
	 * @param url url地址
	 */
	public Video(String title, String insertId,
			String insertName, Integer version, String summary,String url, String content) {
		super(3, title, insertId, insertName, version, summary);
		this.url=url;
		this.content = content ;
	}


	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
