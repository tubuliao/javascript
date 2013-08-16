package com.isoftstone.tyw.dto.info;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.isoftstone.tyw.util.JsonDateTimeSerializer;

public class BaseAPIDTO{
		private String id;
		
		private String auditInfo;
		
		@Temporal(TemporalType.TIMESTAMP)
		@JsonSerialize(using=JsonDateTimeSerializer.class)
		protected Date checkDate;
		
		public Date getCheckDate() {
			return checkDate;
		}

		public void setCheckDate(Date checkDate) {
			this.checkDate = checkDate;
		}

		public BaseAPIDTO() {
			super();
		}

		public BaseAPIDTO(String id, String auditInfo,Date checkDate) {
			super();
			this.id = id;
			this.auditInfo = auditInfo;
			this.checkDate=checkDate;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getAuditInfo() {
			return auditInfo;
		}

		public void setAuditInfo(String auditInfo) {
			this.auditInfo = auditInfo;
		}
		
	}