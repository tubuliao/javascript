package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

public class PdTag implements Serializable{
	
	/**
	 * 标签名称
	 */
	private String name;
	
	/**
	 * 此标签下的知识数量
	 */
	private Integer count;
	
	
	/**
	 * 是否为叶子节点
	 */
	private Integer leaf;  

	/**
	 * 为z-tree添加字段 判断是否是父节点
	 * @return
	 */
	public boolean getIsParent(){
		if(leaf == 1){
			return false;
		}else{
			return true;
		}
	}
	
	public String getText() {
		return name;
	}

	/**
	 * 标签代码层级
	 * 
	 */
	private Long code;
	
	
	/**
	 * 层级
	 */
	private Integer level;
	
	
	

	public PdTag() {
		super();
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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



	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}


}
