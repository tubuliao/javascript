package com.isoftstone.tyw.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 主键为UUID
 * 
 * @author ray
 */
@MappedSuperclass
public class ID implements Serializable{

	@Id
	@Column(updatable=false,unique=true,length=36)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	protected String id;
	
	public ID() {
		super();
	}
	
	public ID(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
