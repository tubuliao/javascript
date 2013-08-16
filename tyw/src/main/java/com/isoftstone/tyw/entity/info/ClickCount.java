package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

@Entity
@Table(name = "info_clickcount")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ClickCount implements Serializable{
	
	/** 
	 * 点击id
	 */
    @Id
	private String id;
	/**
	 * 点击数量
	 */
	private int clickcount;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getClickcount() {
        return clickcount;
    }
    public void setClickcount(int clickcount) {
        this.clickcount = clickcount;
    }
     
}
