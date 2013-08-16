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
@Table(name = "info_site_record")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SiteRecord extends ID implements Serializable{
    /**
     * 用户Id
     */
    private String uid;

    /**
     * 说明文字
     */
    private String content;

    /**
     * 图片/视屏/语音地址
     */
    private String url;

    /**
     * 经度
     */
    private String Longitude;

    /**
     * 纬度
     */
    private String Latitude;

    /**
     * 类型 1:图片，2：视频 ,3:语音
     */
    private Integer type;

    /**
     * 地址
     */
    private String location;

    /**
     * 记录日期
     */
    private String recordDate;

    /**
     * 上传日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    private Date uploadDate = new Date();

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

}
