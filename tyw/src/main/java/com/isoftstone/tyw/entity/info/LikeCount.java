package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

@Entity
@Table(name = "info_like_count")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LikeCount extends ID implements Serializable{
	/**
	 *文章Id
	 */
	private String resourcesId ;
	/**
     *用户Id
     */
    private String uid ;
    /*标签汇总*/
    @Transient
    private int count ;
    
    public String getResourcesId() {
        return resourcesId;
    }
    public void setResourcesId(String resourcesId) {
        this.resourcesId = resourcesId;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    
}
