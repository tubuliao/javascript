package com.isoftstone.tyw.dto.info;

 
public class SearchDetailDTO{
        private String id;
		private String title;
		private String description;//描述
		private String createDate;
		private String source;
		private String content;
 		private String url;
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
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        
        public String getCreateDate() {
            return createDate;
        }
        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
        public String getSource() {
            return source;
        }
        public void setSource(String source) {
            this.source = source;
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
		
 	}