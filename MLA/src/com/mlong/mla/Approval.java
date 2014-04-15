package com.mlong.mla;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import com.actionbarsherlock.view.MenuItem;
import com.mlong.mla.AchItems.Item;
import com.mlong.mla.PersonalListItems.PersonalListItem;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Approval extends Activity {

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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_approval);
		
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
        AList.setOnItemClickListener(listener);
        
              
        String aname;
    	String dname;
    	long time;
    	String mydate = null;
    	String mytime = null;
        boolean trophy = false;
        int achkey;
        String iconpath;
        
        Item myitem1 = new Item();
        myitem1.setName("Poop");
		myitem1.setDescription("Poop");
		myitem1.setDate("");
		myitem1.setTime("");
		myitem1.setAchkey(1337);
		myitem1.setIconPath("rocket_");
		
		Item myitem2 = new Item();
	    myitem2.setName("Poopie Pants");
		myitem2.setDescription("Poopie Pants");
		myitem2.setDate("");
		myitem2.setTime("");
		myitem2.setAchkey(4);
		myitem2.setIconPath("turtle_");
        
		m_parts.add(myitem1);
		m_parts.add(myitem2);
    	m_adapter.notifyDataSetChanged();
		
	}

	//When you click on an achievement
    private OnItemClickListener listener = new AdapterView.OnItemClickListener() {
    	
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            // TODO Auto-generated method stub
        	
        	Item myitem = new Item();
        	
        	String desc = "";
        	int comp = 0;
        	int points = 0;
        	long datetime = 0;
        	String date;
        	String time;
        	int Achkey;
        	String name;
        	myitem = m_parts.get(arg2);
        	
        	/*name = myitem.getName();
        	Achkey = myitem.getAchkey();
        	
        	
        	//REMOVE = myS;
        	REMOVE2 = arg2;
        	
        	//getting all the things to pass to the dialog
        	PersonalDB myDB = new PersonalDB(getBaseContext());
        	Cursor cursor;
        	myDB.open();
        	cursor = myDB.getmypoints(Achkey);
        	cursor.moveToFirst();
        	
        	if(cursor != null) {
                if(cursor.getCount() > 0) {
                		points = cursor.getInt(cursor.getColumnIndex(PersonalDB.COLUMN_POINTS));
                }
        	}
        	
        	cursor = myDB.getnumb(Achkey);
        	cursor.moveToFirst();
        	
        	if(cursor != null) {
                if(cursor.getCount() > 0) {
                	comp = cursor.getInt(cursor.getColumnIndex(PersonalDB.COLUMN_NUMBERTOCOMP));
                }
        	}
        	
        	cursor = myDB.getdesc(Achkey);
        	cursor.moveToFirst();
        	
        	if(cursor != null) {
                if(cursor.getCount() > 0) {
                	desc = cursor.getString(cursor.getColumnIndex(PersonalDB.COLUMN_DESCRIPTION));
                }
        	}
        	
        	cursor = myDB.gettime(Achkey);
        	cursor.moveToFirst();
        	
        	if(cursor != null) {
                if(cursor.getCount() > 0) {
                	datetime =	cursor.getLong(cursor.getColumnIndex(PersonalDB.COLUMN_TIMEFRAME));
                }
        	}
        	
        	cursor.close();
        	myDB.close();
        	
        	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm"); 
    		String dateString = formatter.format(new Date(datetime));
    		
    		//parse dateString
    		StringTokenizer tokens = new StringTokenizer(dateString, " ");
    		date = tokens.nextToken();
    		time = tokens.nextToken();
        	

        	boolean completed;
        	int ofcomplete;
        	
        	
        	//send data to details page
        	Bundle mybundle = new Bundle();
        	mybundle.putString("namekey", name);
        	mybundle.putInt("points", points);
        	mybundle.putInt("comp", comp);
        	mybundle.putString("desc", desc);
        	//mybundle.putBoolean("iscomp", completed);
        	//mybundle.putInt("ofcomp", ofcomplete);
        	mybundle.putInt("place", arg2);
        	mybundle.putString("date", date);
    		mybundle.putString("time", time);
        	mybundle.putInt("achkey", Achkey);
        	
    		if(dateString.compareTo("12/31/1969 19:00") == 0)
    		{
        		mybundle.putString("date", "");
        		mybundle.putString("time", "");
    		}

    		Fragment newFragment = new Personal_Achievement_Details_Fragment();
            newFragment.setArguments(mybundle);
            
            //mActivity.pushFragments(AppConstants.TAB_P, newFragment,true,true);*/
        	
        	
        	Intent i = new Intent(getBaseContext(),  Approval2.class);
            startActivity(i);
        	
        	
        	
        }
    };
    

}	


