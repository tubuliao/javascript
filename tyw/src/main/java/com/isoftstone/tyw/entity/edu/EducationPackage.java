package com.isoftstone.tyw.entity.edu;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.isoftstone.tyw.entity.ID;

/**
 * 课件包类
 * @author zhaowenli
 *
 */
@Entity
@Table(name = "edu_package")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EducationPackage extends ID implements Serializable{

	private String title;//课程包名称
	private Double money;//金额
	private Date validDateStart;//有效期自
	private Date validDateEnd;//有效期至
	private Integer period;//要求完成学时（分钟）
	private String description;//描述
	private String postTypeName;//专业类别
	private String postName;//专业
	private Integer pagyear;//年份
	private String status;//状态
	private String uname;//创建者
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the money
	 */
	public Double getMoney() {
		return money;
	}
	/**
	 * @param money the money to set
	 */
	public void setMoney(Double money) {
		this.money = money;
	}
	/**
	 * @return the validDateStart
	 */
	public Date getValidDateStart() {
		return validDateStart;
	}
	/**
	 * @param validDateStart the validDateStart to set
	 */
	public void setValidDateStart(Date validDateStart) {
		this.validDateStart = validDateStart;
	}
	/**
	 * @return the validDateEnd
	 */
	public Date getValidDateEnd() {
		return validDateEnd;
	}
	/**
	 * @param validDateEnd the validDateEnd to set
	 */
	public void setValidDateEnd(Date validDateEnd) {
		this.validDateEnd = validDateEnd;
	}
	/**
	 * @return the period
	 */
	public Integer getPeriod() {
		return period;
	}
	/**
	 * @param period the period to set
	 */
	public void setPeriod(Integer period) {
		this.period = period;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the postTypeName
	 */
	public String getPostTypeName() {
		return postTypeName;
	}
	/**
	 * @param postTypeName the postTypeName to set
	 */
	public void setPostTypeName(String postTypeName) {
		this.postTypeName = postTypeName;
	}
	/**
	 * @return the postName
	 */
	public String getPostName() {
		return postName;
	}
	/**
	 * @param postName the postName to set
	 */
	public void setPostName(String postName) {
		this.postName = postName;
	}
	/**
	 * @return the pagyear
	 */
	public Integer getPagyear() {
		return pagyear;
	}
	/**
	 * @param pagyear the pagyear to set
	 */
	public void setPagyear(Integer pagyear) {
		this.pagyear = pagyear;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }
	
}
