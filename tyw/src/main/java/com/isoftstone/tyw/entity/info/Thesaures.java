package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

/**
 * 词库类
 * @author liuwei
 *
 */
@Entity
@Table(name="info_thesaures")
@Inheritance(strategy=InheritanceType.JOINED)
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Thesaures extends ID implements Serializable{

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 关键词长度
	 */
	private Integer len;
	
	public Thesaures() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLen() {
		return len;
	}

	public void setLen(Integer len) {
		this.len = len;
	}
	
	
}
