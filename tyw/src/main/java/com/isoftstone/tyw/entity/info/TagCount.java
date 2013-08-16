package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

@Entity
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TagCount  implements Serializable{
	
    @Id
    private String id;
    
	 private String name ;
	 
	 private Integer count ;
	 
	 
     private Integer level ;
	 
	 private Long code;
	 
	 public TagCount() {
		 super() ;
	 }

	 public TagCount(String name, Integer count) {
		 this.name =  name ;
		 this.count = count ;
	 }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
	 
	 
}
