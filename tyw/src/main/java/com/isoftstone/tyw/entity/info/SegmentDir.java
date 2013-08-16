package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

public class SegmentDir implements Serializable{
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 标签条目
	 */
	private String segItem ;

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getSegItem() {
		return segItem;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSegItem(String segItem) {
		this.segItem = segItem;
	}
	
	
	
	
}
