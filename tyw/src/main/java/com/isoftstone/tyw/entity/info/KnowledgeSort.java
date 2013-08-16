package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

@Entity
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class KnowledgeSort extends ID implements Serializable{
	
	 private String name ;
	 
	 private Integer count ;
	 
	 public KnowledgeSort() {
		 super() ;
	 }

	 public KnowledgeSort(String name, Integer count) {
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
	 
	 
}
