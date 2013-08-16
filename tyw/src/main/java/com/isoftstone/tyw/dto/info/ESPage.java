package com.isoftstone.tyw.dto.info;

import java.util.List;
import java.util.Map;

public class ESPage {
	 
	//高亮显示字段
	private List<Map<String,Object>> highlighterResult;
	//全部元素数量
	private long totalElements;
	//查询耗时(单位 秒)
	private String totalTook;
	public ESPage(List<Map<String, Object>> searchResult,
			List<Map<String, Object>> highlighterResult, long totalElements,
			String totalTook) {
		super();
 		this.highlighterResult = highlighterResult;
		this.totalElements = totalElements;
		this.totalTook = totalTook;
	}
	 
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	public List<Map<String, Object>> getHighlighterResult() {
		return highlighterResult;
	}
	public void setHighlighterResult(List<Map<String, Object>> highlighterResult) {
		this.highlighterResult = highlighterResult;
	}
	public String getTotalTook() {
		return totalTook;
	}
	public void setTotalTook(String totalTook) {
		this.totalTook = totalTook;
	}
	
	
}
