package com.mlong.mla;

import java.util.ArrayList;
import java.util.HashMap;

import library.GetAttemptsFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.view.MenuItem;
import com.mlong.mla.AchItems.Item;
import com.mlong.mla.ListItems.ListItem;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class Approval2 extends Activity {

int REMOVE2;
	
    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW

	// declare class variables
 	private ArrayList<Item> m_parts = new ArrayList<Item>();
 	private AchCustomListview m_adapter;
 	
 	public final static String MESSAGE = "com.mlong.mla";
	public final static int requestcode = 1;
	
	int key;
	int AchPageKey = 3;
	int DELETECODE = 4;
	int RESULT_CANCELED = 0;
	View view;
	MenuItem deleteList;
	int forkey;
	
	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_ATTEMPTS = "attempt";
	private static final String TAG_ATID = "atid";
	private static final String TAG_UID_CREATOR = "uid_ceator";
	private static final String TAG_AID_BELONGSTO = "aid_belongsto";
	private static final String TAG_POS_VOTES = "pos_votes";
	private static final String TAG_NEG_VOTES = "neg_Votes";
	private static final String TAG_PICTURE_LINK = "picture_link";
	private static final String TAG_VIDEO_LINK = "video_link";
	private static final String TAG_CREATED_AT = "created_at";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_approval2);
		
AppConstants.icon = null;
		
		Button b_addach = (Button) findViewById(R.id.buttonmyach);
		PersonalDB myDB = new PersonalDB(this);
		Cursor cursor;
		ListView AList;
		
		AList=(ListView)findViewById(android.R.id.list);
		AList.setChoiceMode(1);
		
		// instantiate our ItemAdapter class
        m_adapter = new AchCustomListview(this, R.layout.activity_achcustom_listview, m_parts);
        AList.setAdapter(m_adapter);
        m_parts.clear();
        
        //b_addach.setOnClickListener(otherlistener);
        //AList.setOnItemClickListener(listener);
        
              
       /* String aname;
    	String dname;
    	long time;
    	String mydate = null;
    	String mytime = null;
        boolean trophy = false;
        int achkey;
        String iconpath;
        
        Item myitem1 = new Item();
        myitem1.setName("Poop");
		myitem1.setDescription(" ");
		myitem1.setDate("");
		myitem1.setTime("");
		myitem1.setAchkey(1338);
		myitem1.setIconPath("rocket_");
		
		Item myitem2 = new Item();
	    myitem2.setName("Poopie Pants");
		myitem2.setDescription(" ");
		myitem2.setDate("");
		myitem2.setTime("");
		myitem2.setAchkey(44);
		myitem2.setIconPath("turtle_");
        
		m_parts.add(myitem1);
		m_parts.add(myitem2);
    	m_adapter.notifyDataSetChanged();	*/
		
    	new GetAttempts().execute("2");
    	
		
	}

	
	
	 private class GetAttempts extends AsyncTask<String, Void, JSONObject> {
	        
	        @Override
			protected JSONObject doInBackground(String... params) {
	        	GetAttemptsFunctions listFunction = new GetAttemptsFunctions();
	            JSONObject json = listFunction.getAttempts(params[0]);
	            return json;
	        }
	        
	        @Override
			protected void onPostExecute(JSONObject json) {
	        	ArrayList<HashMap<String,String>> lists;
	        	JSONArray jLists = null;
	        	
	        	try{
	        		int success = json.getInt(TAG_SUCCESS);
	        		if(success == 1) {
	        			jLists = json.getJSONArray(TAG_ATTEMPTS);
	        			
	        			for(int i=0; i < jLists.length(); i++) {
	        				JSONObject c = jLists.getJSONObject(i);
	        				Item myitem = new Item();
	                    	String lname = c.getString(TAG_ATID);
	                    	//Log.d("test",c.getString(TAG_LIST_NAME));
	                    	//String ldesc = c.getString(TAG_LIST_DESCRIPTION);
	                    	//forkey = Integer.parseInt(c.getString(TAG_LID));
	                    	
	                    	myitem.setDescription(" ");
	                    	myitem.setName(lname);
	                    	myitem.setAchkey(8457439);
	                    	myitem.setIconPath("rocket_");
	                    	
	                    	//add item to list
	                		m_parts.add(myitem);
	                		m_adapter.notifyDataSetChanged();
	            
	        			}
	        		}
	        		
	        	}
	        	catch(JSONException e) {
	        		e.printStackTrace();
	        	}
	        }

	  }
	
	
	
	
	
	
	
	
	

}
