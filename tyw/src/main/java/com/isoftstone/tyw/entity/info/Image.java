package com.isoftstone.tyw.entity.info;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "info_image")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Image extends Base implements Serializable{

	/**
	 * 图片源文件地址
	 */
	private String imgUrl;
	   /*文件后缀 */
    @Transient
    private String suffix ;
	/**
	 * 图片地址
	 */
	private String url;
	
	/**
	 * 图片说明
	 */
	private String content ;

	public Image() {
		super(5);
	}
	
	/**
	 * @param url url地址
	 */
	public Image(String title, String insertId,
			String insertName, Integer version, String summary,String url, String content, String imgUrl) {
		super(5, title, insertId, insertName, version, summary);
		this.url=url;
		this.content = content;
		this.imgUrl = imgUrl;
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

	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
	
	
	
}
