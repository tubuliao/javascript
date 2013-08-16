package com.isoftstone.tyw.entity.info;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

@Entity
@Table(name = "info_hot_words")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HotWords extends ID implements Serializable{
	/**
	 * 热词
	 */
	private String word ;
	
	/**
	 * 出现的次数
	 */
	private Integer num ;
	
	/**
     * 搜索人Id
     */
    private String searchUid ;
    
	
	/**
	 * 热词创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using=JsonDateTimeSerializer.class)
	private Date createDate = new Date() ;
	
	
	

 
	

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }



    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    public String getSearchUid() {
        return searchUid;
    }

    public void setSearchUid(String searchUid) {
        this.searchUid = searchUid;
    }
	
}
