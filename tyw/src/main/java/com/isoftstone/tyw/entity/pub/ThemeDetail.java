package com.isoftstone.tyw.entity.pub;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.isoftstone.tyw.entity.ID;


/**
 * 专题实体内容
 * @author zhaowenli
 *
 */
@Entity
@Table(name = "pub_theme_detail")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ThemeDetail extends ID implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1420191903791508486L;
	/**
	 * 引用主题ID
	 */
	private String themeId;
	/**
	 * 类型：0 自定义文本，1列表2，单个详情
	 */
	private int type;
	/**
	 * 文字/知识Id集合/知识详情
	 */
	private String value;
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 某块的标题
	 */
	private String title;
	
	private int sortNum;
	
	/**
	 * @return the sortNum
	 */
	public int getSortNum() {
		return sortNum;
	}
	/**
	 * @param sortNum the sortNum to set
	 */
	public void setSortNum(int sortNum) {
		this.sortNum = sortNum;
	}
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
	 * @return the themeId
	 */
	public String getThemeId() {
		return themeId;
	}
	/**
	 * @param themeId the themeId to set
	 */
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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
	
}
