package com.isoftstone.tyw.entity.info;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

@Entity
@Table(name = "info_tag")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tag extends ID  implements Serializable{
	
	/**
	 * 标签名称
	 */
	private String name;
	
	/**
	 * 父节点id
	 */
	@Column(length=36)
	private String parentId;
	
	/**
	 * 是否为叶子节点
	 */
	private Integer leaf;  
	/**
     * 搜索热词
     */
	@Transient
	private Integer  searchhotword;
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
	/**
	 * 标签代码(业务)
	 */
	private String tagNo;
	
	/**
     * 推荐表示 1推荐 0不推荐
     */
	private Integer recommend = 0;
    
	/*业务代码*/
	@Transient
	private String text;
	
	public String getText() {
		return name;
	}
	
	@Transient
	private String state;
	
	public String getState(){
		return "closed";
	}
	
	@Transient
	private String iconCls;
	public String getIconCls(){
		if(this.getLeaf()==1){
			return "icon-tag";
		}else{
			return "";
		}
		
	}
	@Transient
	private String manager;
	public String getManager(){
		return "<a href='javascript:void(0)' id='"+this.id+"' onclick='deletetRowById(this.id)'>删除</a>";
	}
	


	/**
	 * 标签代码层级
	 * 
	 */
	private Long code;
	
	/**
	 * 父级标签代码层级
	 */
	private Long parentCode;
	
	/**
	 * 层级
	 */
	private Integer level;
	
	/**
	 * 排序
	 */
	private Integer sortNo;
	
	
	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	/**
	 * 数据时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date dataDate = new Date();
	
	/**
	 * 有效(0:无效,1:有效)
	 */
	private Boolean status;
	
	/**
	 * 说明
	 */
	private String summary;

	public Tag() {
		super();
	}

	public Tag(String id) {
		super(id);
	}

	public Tag(String name, String parentId, String tagNo, Long code,
			Long parentCode, Integer level, Date dataDate, Boolean status,
			String summary) {
		super();
		this.name = name;
		this.parentId = parentId;
		this.tagNo = tagNo;
		this.code = code;
		this.parentCode = parentCode;
		this.level = level;
		this.dataDate = dataDate;
		this.status = status;
		this.summary = summary;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTagNo() {
		return tagNo;
	}

	public void setTagNo(String tagNo) {
		this.tagNo = tagNo;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public Long getParentCode() {
		return parentCode;
	}

	public void setParentCode(Long parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getSearchhotword() {
        return searchhotword;
    }

    public void setSearchhotword(Integer searchhotword) {
        this.searchhotword = searchhotword;
    }

}
