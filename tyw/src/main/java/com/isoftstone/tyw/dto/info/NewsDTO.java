package com.isoftstone.tyw.dto.info;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.isoftstone.tyw.util.JsonDateTimeSerializer;

public class NewsDTO {
    
    public NewsDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

  

    public NewsDTO(String id, String title, Integer type, Date createDate) {
        super();
        this.id = id;
        this.title = title;
        this.type = type;
        this.createDate = createDate;
    }



    private String id;
    
   private String title;
  
  
   
   
   /**
    * 搜索类型
    */
   private Integer type ;
   
   /**
    * 热词创建时间
    */
   @Temporal(TemporalType.TIMESTAMP)
   @JsonSerialize(using=JsonDateTimeSerializer.class)
   private Date createDate = new Date() ;

   public String getTitle() {
       return title;
   }

   public void setTitle(String title) {
       this.title = title;
   }
 


   public Integer getType() {
    return type;
}

public void setType(Integer type) {
    this.type = type;
}

public Date getCreateDate() {
       return createDate;
   }

   public void setCreateDate(Date createDate) {
       this.createDate = createDate;
   }

  
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
   
}
