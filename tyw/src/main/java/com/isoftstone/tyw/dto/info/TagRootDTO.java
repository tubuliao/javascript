package com.isoftstone.tyw.dto.info;
public class TagRootDTO{
		private String id;
		private String name;
		private int leaf;
		
		public TagRootDTO() {
            super();
            // TODO Auto-generated constructor stub
        }
        

        public TagRootDTO(String id, String name, int leaf) {
            super();
            this.id = id;
            this.name = name;
            this.leaf = leaf;
        }


        public TagRootDTO(String id) {
			super();
			this.id = id;
		}

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

        public int getLeaf() {
            return leaf;
        }

        public void setLeaf(int leaf) {
            this.leaf = leaf;
        }
		
	}