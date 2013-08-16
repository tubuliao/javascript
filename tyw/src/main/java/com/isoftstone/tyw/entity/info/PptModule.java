package com.isoftstone.tyw.entity.info;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

@Entity
public class PptModule extends ID{
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 来源
	 */
	private String source;
	/**
	 * 录入人
	 */
	private String insertName;
	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	protected Date createDate;
	/**
	 * 审核状态
	 */
	private Integer state ;
	/**
	 * ppt文件路径
	 */
	private String urls;
	/**
	 * 权重值
	 */
	private int weighing;
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	/**
	 * @return the insertName
	 */
	public String getInsertName() {
		return insertName;
	}
	/**
	 * @param insertName the insertName to set
	 */
	public void setInsertName(String insertName) {
		this.insertName = insertName;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * @return the urls
	 */
	public String getUrls() {
		return urls;
	}
	/**
	 * @param urls the urls to set
	 */
	public void setUrls(String urls) {
		this.urls = urls;
	}
	/**
	 * @return the weighing
	 */
	public int getWeighing() {
		return weighing;
	}
	/**
	 * @param weighing the weighing to set
	 */
	public void setWeighing(int weighing) {
		this.weighing = weighing;
	}
}
