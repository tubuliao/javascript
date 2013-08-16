package com.isoftstone.tyw.dto.info;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;
@Entity
@Table(name="info_base")
public class BaseModuleDTO {
    @Id
    private String Id;

    private String Title;

    private Integer InfoType;
    
    private Date CreateDate;
    
    private String Url;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Integer getInfoType() {
        return InfoType;
    }

    public void setInfoType(Integer infoType) {
        InfoType = infoType;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    

}