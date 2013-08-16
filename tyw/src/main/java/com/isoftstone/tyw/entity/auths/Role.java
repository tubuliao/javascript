package com.isoftstone.tyw.entity.auths;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

/**
 * 角色.
 * 
 * @author ray
 */
@Entity
@Table(name = "auths_role")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends ID implements Serializable{

	private String name;

	private String authority;

	public Role() {
	}
	
	public Role(String id) {
		super(id);
	}

	public Role(String name, String authority) {
		super();
		this.name = name;
		this.authority = authority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermissions() {
		return authority;
	}

	public void setPermissions(String authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
