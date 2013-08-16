package com.isoftstone.tyw.entity.pub;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

@Entity
@Table(name = "pub_city")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class City extends ID implements Serializable{

	 /**
	 * 省份名称
	 */
	private String name;
	 
	/**
	 * 排序
	 */
	private int sort;
	
	
	private int pid;


//	@OneToOne(fetch = FetchType.LAZY,cascade=CascadeType.REFRESH,optional=true)
//	@JoinColumn(name="cardTypeId")
//    private Province province ;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the sort
	 */
	public int getSort() {
		return sort;
	}


	/**
	 * @param sort the sort to set
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}


	/**
	 * @return the pid
	 */
	public int getPid() {
		return pid;
	}


	/**
	 * @param pid the pid to set
	 */
	public void setPid(int pid) {
		this.pid = pid;
	}
	

}
