package com.isoftstone.tyw.entity.info;

import java.io.Serializable;


public class BookDirModule implements Serializable{
	
    private String id;
  
    private String title;
    
    private String segItem;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public BookDirModule() {
    }

	public String getSegItem() {
		return segItem;
	}

	public void setSegItem(String segItem) {
		this.segItem = segItem;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}