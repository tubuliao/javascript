package com.isoftstone.tyw.entity.info;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.util.DateManager;
@Entity
@Table(name="info_base")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BaseModule implements Serializable{
    @Id
    private String id;

    private String title;
    
    /**
     * 内容类型
     * files:1
     * Segment:2
     * video:3
     * Form:4
     * image:5
     */
    @Column(nullable=false)
    private Integer infoType;
    
    private Date createDate;
    
    private String source;
    
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sf.format(createDate);
        createDate=DateManager.stringFormat(strDate);
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BaseModule() {
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}