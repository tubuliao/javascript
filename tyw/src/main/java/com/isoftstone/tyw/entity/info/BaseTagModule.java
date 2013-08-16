package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Entity;

import com.isoftstone.tyw.entity.ID;

@Entity
public class BaseTagModule extends ID implements Serializable{
	
 private long code;
 private int totalcount;
 
/**
 * @return the code
 */
public long getCode() {
	return code;
}
/**
 * @param code the code to set
 */
public void setCode(long code) {
	this.code = code;
}
 
/**
 * @return the totalcount
 */
public int getTotalcount() {
	return totalcount;
}
/**
 * @param totalcount the totalcount to set
 */
public void setTotalcount(int totalcount) {
	this.totalcount = totalcount;
}
	
}