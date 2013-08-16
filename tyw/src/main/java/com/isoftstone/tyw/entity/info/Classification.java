package com.isoftstone.tyw.entity.info;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;


import com.isoftstone.tyw.entity.ID;

@Entity
@Table(name="info_classification")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Classification extends ID implements Serializable{

	/**
	 * 分类标题
	 */
	private String title ;
	
	/**
	 * 关联用户
	 */
	private String userId ;
	
	/**
	 * 收藏信息
	 */
//	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.ALL}, mappedBy = "classification")
//	private Set<Bookmark> bookmarkSet = Sets.newHashSet() ;
	
	public Classification() {
		super() ;
	}
	
	public Classification(String title, String userId, Set<Bookmark> bookmarkSet) {
		this.title = title ;
		this.userId = userId ;
//		this.bookmarkSet = bookmarkSet ;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

//	public Set<Bookmark> getBookmarkSet() {
//		return bookmarkSet;
//	}
//
//	public void setBookmarkSet(Set<Bookmark> bookmarkSet) {
//		this.bookmarkSet = bookmarkSet;
//	}
	
	
}
