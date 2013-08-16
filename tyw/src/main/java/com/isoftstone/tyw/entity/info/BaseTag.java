package com.isoftstone.tyw.entity.info;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

@Entity
// @Table(name = "info_base_tag")
// @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BaseTag extends ID implements Serializable {
    /**
     * baseId
     */
    private String baseId;

    /**
     * 标签id
     */
    private String tagId;

    /**
     * 标签名称
     */
    private String tagName;

    /**
     * 标签code
     */
    private long tagCode;

    /**
     * 标题
     */
    private String title;

    /**
     * 来源
     */
    private String source;

    @Column(nullable = false)
    protected Integer infoType;

    public String getBaseId() {
        return baseId;
    }

    public String getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getTagCode() {
        return tagCode;
    }

    public void setTagCode(long tagCode) {
        this.tagCode = tagCode;
    }

    public Integer getInfoType() {
        return infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }
}
