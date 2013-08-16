package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Id;

public class BaseTitleModule implements Serializable{
	
	@Id
    private String id;
  
    private String title;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public BaseTitleModule() {
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    
}