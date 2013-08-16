package com.isoftstone.tyw.util;

import java.util.List;

/**
 * @author liuyulong
 * 
 */
public class ApiPager {
int pageSize=0;
int pageNo=0;
long totalElements=0;
 List content=null;
//int showIndex=0;
public ApiPager(int pageSize, int pageNo, long totalElements, List content) {
	this.pageSize = pageSize;
	this.pageNo = pageNo;
	this.totalElements = totalElements;
	this.content = content;
//	if(Math.ceil(((totalElements+0.0)/pageSize))>pageNo){
// 			showIndex=(int) Math.ceil(((totalElements+0.0)/pageSize));
//	}
//	if(pageNo > 1) {
//		    showIndex=(int) Math.ceil(((totalElements+0.0)/pageSize));
//	}

}
public ApiPager() {
	super();
}

public int getPageSize() {
	//java.lang.Math.
	//java.lang.Math.ceil(5.5);
	return pageSize;
}
public void setPageSize(int pageSize) {
	this.pageSize = pageSize;
}
public int getPageNo() {
	return pageNo;
}
public void setPageNo(int pageNo) {
	this.pageNo = pageNo;
}
 
 
public long getTotalElements() {
    return totalElements;
}
public void setTotalElements(long totalElements) {
    this.totalElements = totalElements;
}
//public int getShowIndex() {
//	return showIndex;
//}
//public void setShowIndex(int showIndex) {
//	this.showIndex = showIndex;
//}
public List getContent() {
    return content;
}
public void setContent(List content) {
    this.content = content;
}
 

}
