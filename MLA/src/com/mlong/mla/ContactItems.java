package com.mlong.mla;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ContactItems{

	public static class ContactItem {
		private String name;
		private String phonenumber;
		private int listkey;
		private int phoneid;
		int  position;        
	    boolean ischeckedflag;
	    	
		public ContactItem(){

		}
		
		public ContactItem(String n, String d, int i, int name, boolean flag, int pid){
			this.name = n;
			this.phonenumber = d;
			this.listkey = i;
			position = name;           
            ischeckedflag = flag;
            phoneid = pid;
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPhonenumber() {
			return phonenumber;
		}

		public void setPhoneNumber(String phonenumber) {
			this.phonenumber = phonenumber;
		}
		
		public int getListkey() {
			return listkey;
		}

		public void setListkey(int listkey) {
			this.listkey = listkey;
		}
		
		public int getPhoneId() {
			return phoneid;
		}

		public void setPhoneId(int phoneid) {
			this.phoneid = phoneid;
		}
	}
}
