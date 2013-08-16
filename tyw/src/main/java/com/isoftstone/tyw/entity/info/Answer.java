package com.isoftstone.tyw.entity.info;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
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
@Table(name = "info_answer")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Answer extends ID implements Serializable{
    private String content;

    private String qid;// 问题Id

    private String aid;// 答案id

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = JsonDateTimeSerializer.class)
    @Column(updatable = false)
    protected Date createDate = new Date();
    
    public String getFormatCreateDate(){
		String result = "";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		if(null != createDate){
			result = df.format(createDate);
		}
		return result;
	}

    private int state;// 状态

    public Answer() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Answer(String id) {
        super(id);
        // TODO Auto-generated constructor stub
    }

    public Answer(String content, String qid, String aid, Date createDate, int state) {
        super();
        this.content = content;
        this.qid = qid;
        this.aid = aid;
        this.createDate = createDate;
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
