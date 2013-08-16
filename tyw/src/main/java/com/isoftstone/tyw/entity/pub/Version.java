package com.isoftstone.tyw.entity.pub;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.isoftstone.tyw.entity.ID;

/**
 * 版本管理
 * @author JQZ
 *
 */
@Entity
@Table(name="tool_version")
public class Version extends ID implements Serializable{
	
	//主版本
	private Integer versionFirst;
	//次版本
	private Integer versionSecond;
	//工具类型  默认情况下  1：WEB  。2：切片工具。3：移动IPAD。4：移动Android
	private Integer toolsType;
	public Integer getVersionFirst() {
		return versionFirst;
	}
	public void setVersionFirst(Integer versionFirst) {
		this.versionFirst = versionFirst;
	}
	public Integer getVersionSecond() {
		return versionSecond;
	}
	public void setVersionSecond(Integer versionSecond) {
		this.versionSecond = versionSecond;
	}
	public Integer getToolsType() {
		return toolsType;
	}
	public void setToolsType(Integer toolsType) {
		this.toolsType = toolsType;
	}
	
}
