package com.mlong.mla;

public class Items{

	public static class Item {
		private String name;
		private String description;
		private boolean trophy; 

		public Item(){

		}

		public Item(String n, String d, boolean t){
			this.name = n;
			this.description = d;
			this.trophy = t;
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
		
		public boolean getcomplete() {
			return trophy;
		}

		public void setcomplete(boolean trophy) {
			this.trophy = trophy;
		}

	}
}