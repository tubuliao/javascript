package com.isoftstone.tyw.entity.info;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

/**
 * 内容基类
 * 
 * @author zhanglei
 */
@Entity
@Table(name="info_base_base")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

public class BaseBase extends ID implements Serializable{
	
	/**
	 * 主表id
	 */
	private String infoPrimaryId;

	/**
	 * 从表id
	 */
	private String infoSlaveId;

	public String getInfoPrimaryId() {
		return infoPrimaryId;
	}

	public void setInfoPrimaryId(String infoPrimaryId) {
		this.infoPrimaryId = infoPrimaryId;
	}

	public String getInfoSlaveId() {
		return infoSlaveId;
	}

	public void setInfoSlaveId(String infoSlaveId) {
		this.infoSlaveId = infoSlaveId;
	}
	
	
	
	
	
}
