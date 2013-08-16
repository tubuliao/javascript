package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "info_powerpoint")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Powerpoint extends Base implements Serializable{

	/**
	 * 文本内容
	 */
	private String content;	
	
	   
    /*文件后缀 */
    @Transient
    private String suffix ;
	/**
	 * 源文件地址
	 */
	private String urls;
	
	
	
	public Powerpoint() {
		super(6);
	}

	
	public Powerpoint(String title, String insertId, 
			String insertName, Integer version, String summary, 
			String content,String urls) {
		super(6, title, insertId, insertName, version, summary);
		this.content = content ;
		this.urls=urls;
	}
	
	
	public String getUrls() {
		return urls;
	}

	public void setUrls(String urls) {
		this.urls = urls;
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
