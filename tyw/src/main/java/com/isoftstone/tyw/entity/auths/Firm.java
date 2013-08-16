package com.isoftstone.tyw.entity.auths;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.isoftstone.tyw.entity.ID;

@Entity
@Table(name = "auths_firm")
//@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Firm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5417331350115061922L;
	@Id
    @GenericGenerator(name = "generator", 
                      strategy = "foreign", 
                      parameters = { 
                        @Parameter(name = "property", value = "user") 
                      })
    @GeneratedValue(generator = "generator")
	private String id;
	
	/**
	 * 企业名称
	 */
	private String name;
	
	@OneToOne(optional=true, mappedBy="firm")
	@JsonIgnore
	private User user;
	
	/**
	 * 地址
	 */
	private String addr;
	
	/**
	 * 法人代表
	 */
	private String linkman;
	/**
	 * 电话
	 */
	private String phone;
	/**
	 * 传真
	 */
	private String fax;
	
	/**
	 * 邮编
	 */
	private String zip;
	
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 渠道商ID
	 */
	private String agentId;
	
	
	
	@OneToMany(fetch=FetchType.LAZY, cascade={CascadeType.REFRESH})//级联刷新;延迟加载,是关系的维护端
	@JoinColumn(name="firmId")//在auth_additional表增加一个外键列来实现一对多的单向关联
	@JsonIgnore
	private Set<Additional> additionals = new HashSet<Additional>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<Additional> getAdditionals() {
		return additionals;
	}
	public void setAdditionals(Set<Additional> additionals) {
		this.additionals = additionals;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
}
