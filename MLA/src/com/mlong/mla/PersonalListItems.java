package com.mlong.mla;

public class PersonalListItems{

	public static class PersonalListItem {
		private String name;
		private String description;
		private int listkey;
		private int points = 0;
	
		int  position;        
	    boolean ischeckedflag;
	    	
		public PersonalListItem(){

		}
		
		public PersonalListItem(String n, String d, int i, int name, boolean flag, int points){
			this.name = n;
			this.description = d;
			this.listkey = i;
			position = name;           
            ischeckedflag = flag;
            this.points = points;
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
		
		public int getPoints() {
			return points;
		}
		
		public void setPoints(int points) {
			this.points = points;
		}
	}
}