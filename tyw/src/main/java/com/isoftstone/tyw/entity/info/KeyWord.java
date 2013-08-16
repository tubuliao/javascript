package com.isoftstone.tyw.entity.info;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.google.common.collect.Sets;
import com.isoftstone.tyw.entity.ID;
import com.isoftstone.tyw.util.JsonDateTimeSerializer;

/**
 * 用户常用关键词
 * 
 * @author liuyulong
 */
@Entity
@Table(name="info_keyword")
@Inheritance(strategy=InheritanceType.JOINED)
 public class KeyWord extends ID implements Serializable{
    /**
     * 关键词
     */
    private String keyword ;
    
    /**
     * 是否为tag
     * 0 热词 ,1 tag
     */
    private int istag ;
  
    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using=JsonDateTimeSerializer.class)
    protected Date createDate;

    
    public KeyWord() {
        super();
        // TODO Auto-generated constructor stub
    }

    public KeyWord(String id) {
        super(id);
        // TODO Auto-generated constructor stub
    }

    public KeyWord(String keyword, int istag, Date createDate) {
        super();
        this.keyword = keyword;
        this.istag = istag;
        this.createDate = createDate;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getIstag() {
        return istag;
    }

    public void setIstag(int istag) {
        this.istag = istag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
}
