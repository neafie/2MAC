package com.mlong.mla;

public class ListItems{

	public static class ListItem {
		private String name;
		private String description;
		private int listkey;
	
		public ListItem(){

		}
		
		public ListItem(String n, String d, int i){
			this.name = n;
			this.description = d;
			this.listkey = i;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
		public int getListkey() {
			return listkey;
		}

		public void setListkey(int listkey) {
			this.listkey = listkey;
		}
	}
}