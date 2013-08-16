package com.isoftstone.tyw.util;

import java.util.List;

/**
 * @author liuyulong
 * 
 */
public class Pager {
int pageSize=0;
int pageNo=0;
long rowCount=0;
int showNext=0;
List result=null;
int showIndex=0;
public Pager(int pageSize, int pageNo, long rowCount, List result) {
	this.pageSize = pageSize;
	this.pageNo = pageNo;
	this.rowCount = rowCount;
	this.result = result;
	if(Math.ceil(((rowCount+0.0)/pageSize))>pageNo){
			showNext=1;
			showIndex=(int) Math.ceil(((rowCount+0.0)/pageSize));
	}
	if(pageNo > 1) {
		    showIndex=(int) Math.ceil(((rowCount+0.0)/pageSize));
	}

}
public Pager() {
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
public long getRowCount() {
	return rowCount;
}
public void setRowCount(long rowCount) {
	this.rowCount = rowCount;
}
public List getResult() {
	return result;
}
public void setResult(List result) {
	this.result = result;
}
public void setShowNext(int showNext) {
	this.showNext = showNext;
}

public int getShowNext() {
	return showNext;
}
public int getShowIndex() {
	return showIndex;
}
public void setShowIndex(int showIndex) {
	this.showIndex = showIndex;
}
 

}
