package com.isoftstone.tyw.entity.info;

import javax.persistence.Entity;

import com.isoftstone.tyw.entity.ID;

@Entity
public class ThesauresModule extends ID {

	/**
	 * 名称
	 */
	private String name;
	
	public ThesauresModule() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
