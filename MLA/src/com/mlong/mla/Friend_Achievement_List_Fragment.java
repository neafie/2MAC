package com.mlong.mla;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.mlong.mla.AchItems.Item;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Friend_Achievement_List_Fragment extends BaseFragment {
	
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.activity_ach_list, container, false);
		setHasOptionsMenu(true);
		
		AppConstants.icon = null;
		
		Button b_addach = (Button) view.findViewById(R.id.buttonmyach);
		ACHDatabase myDB = new ACHDatabase(getActivity());
		Cursor cursor;
		ListView AList;
		
		AList=(ListView)view.findViewById(android.R.id.list);
		AList.setChoiceMode(1);
		
		// instantiate our ItemAdapter class
        m_adapter = new AchCustomListview(getActivity(), R.layout.activity_achcustom_listview, m_parts);
        AList.setAdapter(m_adapter);
        m_parts.clear();
        
        b_addach.setOnClickListener(otherlistener);
        AList.setOnItemClickListener(listener);
        
        
        Friend_Achievement_List_Fragment myfrag = this;
        Bundle mybundle;
        mybundle = myfrag.getArguments();
        key= mybundle.getInt("listkey");
        
        myDB.open();
		cursor = myDB.achievementQuery(key);
        cursor.moveToFirst();
        
        
        String aname;
    	String dname;
    	long time;
    	String mydate = null;
    	String mytime = null;
        boolean trophy = false;
        int achkey;
        String iconpath;
        
        // Check if our result was valid.
		if(cursor != null) {
            if(cursor.getCount() > 0) {
            	
            	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_ACHKEY)) != null)
                	{
                		Item myitem = new Item();
                		aname = cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_ACHNAME));
                		dname = cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_DESCRIPTION));
                		trophy = cursor.getInt(cursor.getColumnIndex(ACHDatabase.COLUMN_ISCOMPLETED))>0;
                		time = cursor.getLong(cursor.getColumnIndex(ACHDatabase.COLUMN_TIMEFRAME));
                		achkey = cursor.getInt(cursor.getColumnIndex(ACHDatabase.COLUMN_ACHKEY));
                		iconpath = cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_ICON));
                		
                		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm"); 
                		String dateString = formatter.format(new Date(time));
                		
                		
                		
                		//parse dateString
                		StringTokenizer tokens = new StringTokenizer(dateString, " ");
                		mydate = tokens.nextToken();
                		mytime = tokens.nextToken();
                		
                		//set values of item
                		if(trophy == true)
                		{
                			myitem.setcomplete(true);
                		}
                		myitem.setName(aname);
                		myitem.setDescription(dname);
                		myitem.setDate(mydate);
                		myitem.setTime(mytime);
                		myitem.setAchkey(achkey);
                		myitem.setIconPath(iconpath);
                		if(dateString.compareTo("12/31/1969 19:00") == 0)
                		{
                			myitem.setDate("");
                    		myitem.setTime("");
                		}
                		
                		//add item to list
                		m_parts.add(myitem);
                		m_adapter.notifyDataSetChanged();

                	}
                } while (cursor.moveToNext());
            }
        }
		
		setPointTotal();
		setAchTotal();
		
		cursor.close();
        myDB.close();		
        
        Button b_findContacts = (Button) view.findViewById(R.id.button2);
        b_findContacts.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	Fragment newFragment = new Friend_Contact_List();
            	Bundle mybundle = new Bundle();
            	mybundle.putInt("listkey", key);
            	newFragment.setArguments(mybundle);
                mActivity.pushFragments(AppConstants.TAB_F, newFragment,true,true);
            	
            }
           
        });
        
        
        return view;
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
        	
        	name = myitem.getName();
        	Achkey = myitem.getAchkey();
        	
        	
        	//REMOVE = myS;
        	REMOVE2 = arg2;
        	
        	//getting all the things to pass to the dialog
        	ACHDatabase myDB = new ACHDatabase(getActivity());
        	Cursor cursor;
        	myDB.open();
        	cursor = myDB.getmypoints(Achkey);
        	cursor.moveToFirst();
        	
        	if(cursor != null) {
                if(cursor.getCount() > 0) {
                		points = cursor.getInt(cursor.getColumnIndex(ACHDatabase.COLUMN_POINTS));
                }
        	}
        	
        	cursor = myDB.getnumb(Achkey);
        	cursor.moveToFirst();
        	
        	if(cursor != null) {
                if(cursor.getCount() > 0) {
                	comp = cursor.getInt(cursor.getColumnIndex(ACHDatabase.COLUMN_NUMBERTOCOMP));
                }
        	}
        	
        	cursor = myDB.getdesc(Achkey);
        	cursor.moveToFirst();
        	
        	if(cursor != null) {
                if(cursor.getCount() > 0) {
                	desc = cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_DESCRIPTION));
                }
        	}
        	
        	cursor = myDB.gettime(Achkey);
        	cursor.moveToFirst();
        	
        	if(cursor != null) {
                if(cursor.getCount() > 0) {
                	datetime =	cursor.getLong(cursor.getColumnIndex(ACHDatabase.COLUMN_TIMEFRAME));
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
        	
        	completed = getiscomplete(Achkey);
        	ofcomplete = getofcomplete(Achkey);
        	
        	//send data to details page
        	Bundle mybundle = new Bundle();
        	mybundle.putString("namekey", name);
        	mybundle.putInt("points", points);
        	mybundle.putInt("comp", comp);
        	mybundle.putString("desc", desc);
        	mybundle.putBoolean("iscomp", completed);
        	mybundle.putInt("ofcomp", ofcomplete);
        	mybundle.putInt("place", arg2);
        	mybundle.putString("date", date);
    		mybundle.putString("time", time);
        	mybundle.putInt("achkey", Achkey);
        	
    		if(dateString.compareTo("12/31/1969 19:00") == 0)
    		{
        		mybundle.putString("date", "");
        		mybundle.putString("time", "");
    		}

    		Fragment newFragment = new Friend_Achievement_Details_Fragment();
            newFragment.setArguments(mybundle);
            
            mActivity.pushFragments(AppConstants.TAB_F, newFragment,true,true);
        }
    };
    
    private OnClickListener otherlistener = new View.OnClickListener() {
        @Override
    	public void onClick(View v) {   
        	
    		Bundle mybundle = new Bundle();         
            mybundle.putInt("key", key);
            
            Fragment newFragment = new Friend_Add_Achievement_Fragment();
            newFragment.setArguments(mybundle);
  
            mActivity.pushFragments(AppConstants.TAB_F, newFragment,true,true);
    		
        }
      
    };
	
	//sets total points
	public void setPointTotal()
	{
		int point_total = 0;
		int point = 0;
		int getpoint1;
		int getpoint2;
		String fancy;

		ACHDatabase myDB = new ACHDatabase(getActivity());
		myDB.open();
		Cursor cursor;
		cursor = myDB.getallpoints(key);
        cursor.moveToFirst();
		
        if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_POINTS)) != null)
                	{
                		getpoint1 = cursor.getInt(cursor.getColumnIndex(ACHDatabase.COLUMN_POINTS));
                		if(getpoint1 >=0)
                		{
                			point_total = point_total + getpoint1;
                		}
                	}
                } while (cursor.moveToNext());
            }
        }
        
		cursor = myDB.getpoints(key);
        cursor.moveToFirst();
        if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_POINTS)) != null)
                	{
                		getpoint2 = cursor.getInt(cursor.getColumnIndex(ACHDatabase.COLUMN_POINTS));
                		point = point + getpoint2;
                	}
                } while (cursor.moveToNext());
            }
        }
        
        fancy = point +"/"+point_total;
        
        TextView tv_point = (TextView) view.findViewById(R.id.tv_pt_i);
        tv_point.setText(fancy);
		
        cursor.close();
        myDB.close();
	
	}
	
	//sets the achievement total
	public void setAchTotal()
	{
		int ach_total = 0;
		int ach = 0;
		String fancy;
		
		ACHDatabase myDB = new ACHDatabase(getActivity());
		Cursor cursor;
		
		myDB.open();
		cursor = myDB.getallach(key);
        cursor.moveToFirst();
		
        if(cursor != null) {
            if(cursor.getCount() > 0) {
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_ACHNAME)) != null)
                	{
                		ach_total++;
                	}
                } while (cursor.moveToNext());
            }
        }
        
        cursor = myDB.getach(key);
        cursor.moveToFirst();
        
        if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_ACHNAME)) != null)
                	{
                		ach++;
                	}
                } while (cursor.moveToNext());
            }
        }
        
        fancy = ach+"/"+ach_total;
        TextView tv_ach = (TextView) view.findViewById(R.id.tv_at_i);
        tv_ach.setText(fancy);
		cursor.close();
        myDB.close();
	
	}
	
	//update dialog complete
	public boolean getiscomplete(int achkey)
	{
		ACHDatabase myDB = new ACHDatabase(getActivity());
		Cursor cursor;
		myDB.open();
		cursor = myDB.getiscomp(achkey);
        cursor.moveToFirst();
		boolean completed = false;
        
        if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_ISCOMPLETED)) != null)
                	{
                		completed = cursor.getInt(cursor.getColumnIndex(ACHDatabase.COLUMN_ISCOMPLETED))>0;
                	}
                } while (cursor.moveToNext()); 
            }
        }
        cursor.close();
        myDB.close();
        return completed;
	}
	
	//gets how many completions there are for an achievement
	public int getofcomplete(int achkey)
	{
		ACHDatabase myDB = new ACHDatabase(getActivity());
		Cursor cursor;
		myDB.open();
		cursor = myDB.getofcomp(achkey);
        cursor.moveToFirst();
		int ofcompleted = 0;
        
        if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_NUMBEROFCOMP)) != null)
                	{
                		ofcompleted = cursor.getInt(cursor.getColumnIndex(ACHDatabase.COLUMN_NUMBEROFCOMP));
                	}
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        myDB.close();
        return ofcompleted;
	}
}