package com.mlong.mla;

public class AchItems{

	public static class Item {
		private String name;
		private String description;
		private boolean trophy; 
		private String date;
		private String time;
		private int achkey;
		private String iconpath;
		
		public Item(){

		}

		public Item(String n, String d, boolean t, String da, String ti, int a, String i){
			this.name = n;
			this.description = d;
			this.trophy = t;
			this.date = da;
			this.time = ti;
			this.achkey = a;
			this.iconpath = i;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public String getIconPath() {
			return iconpath;
		}

		public void setIconPath(String iconpath) {
			this.iconpath = iconpath;
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
		
		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}
		
		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}
		
		public int getAchkey() {
			return achkey;
		}

		public void setAchkey(int achkey) {
			this.achkey = achkey;
		}
	}
}