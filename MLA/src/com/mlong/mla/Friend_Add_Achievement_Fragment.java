package com.mlong.mla;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.TimeZone;

import com.actionbarsherlock.app.SherlockFragment;


import android.os.Bundle;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Color;


public class Friend_Add_Achievement_Fragment extends SherlockFragment {

	int myyear,mymonth,myday,myhour,mymin;
	View view;
	int RESULT_OK = 1;
	boolean ischecked = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.add_ach, container, false);	
		
		
		Button date = (Button) view.findViewById(R.id.b_dpick);
		Button time = (Button) view.findViewById(R.id.b_tpick);
		
		date.setEnabled(false);
		time.setEnabled(false);

		return view;
	}


	

	public void AddAch(View myV)
	{
		Log.i("view", "Made It!");
		 final EditText my_name = (EditText) view.findViewById(R.id.et_aname);
		 final EditText my_desc = (EditText) view.findViewById(R.id.et_desc);
	     final EditText my_point = (EditText) view.findViewById(R.id.et_points);
	     final EditText my_numb = (EditText) view.findViewById(R.id.et_numb);
	     TextView mydate = (TextView) view.findViewById(R.id.tv_myDate);
		 TextView mytime = (TextView) view.findViewById(R.id.tv_myTime);
	     Button date = (Button) view.findViewById(R.id.b_dpick);
	     RadioButton myRPerson = (RadioButton) view.findViewById(R.id.radioButton2);
	     myRPerson.setChecked(false);
	    
	     
	     if((my_name.getText().toString().equals("") 
					|| my_desc.getText().toString().equals("") || my_point.getText().toString().equals("")
					|| Integer.parseInt(my_numb.getText().toString()) < 1
					|| my_numb.getText().toString().equals("")) || ((mydate.getText().toString().equals("")
					|| mytime.getText().toString().equals("")) && date.isEnabled() == true))
			{
				Toast.makeText(getActivity(), "You must complete all fields", Toast.LENGTH_SHORT).show(); 
			}
	     else if ((my_name.getText().toString().equals("") 
					|| my_desc.getText().toString().equals("") || my_point.getText().toString().equals("")
					|| Integer.parseInt(my_numb.getText().toString()) < 1
					|| my_numb.getText().toString().equals("")) && date.isEnabled() == false)
	     {
	    	 Toast.makeText(getActivity(), "You must complete all fields", Toast.LENGTH_SHORT).show(); 
	     }
	     
	     else{
	     
		//if date selected do this, if buttons are not disabled, if all globals are not nulls
	    Calendar usercalendar = Calendar.getInstance();
	    
	    //set time to utc, store as utc, update their set with timezone change
	    //clear is important because if its not there the timeframe is from "now" not from current time
	    usercalendar.clear();
	    usercalendar.set(myyear, mymonth, myday, myhour, mymin);

	    long user_date;
	    
	    //time in millisecs from the epoch to store, current is for testing purposes
		user_date = usercalendar.getTimeInMillis();
		
		
		String Sdate;
		String Stime;
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm"); 
		String dateString = formatter.format(new Date(user_date));
		
		//parse dateString
		StringTokenizer tokens = new StringTokenizer(dateString, " ");
		Sdate = tokens.nextToken();
		Stime = tokens.nextToken();
		
		//make key for achievement
		final int MAX_VALUE = 1000000;
		Random rand = new Random();
		int randkey = rand.nextInt(MAX_VALUE);
		
		//bundle up all data to go back to achievment list
		//Intent otherintent = getIntent();
		//int key = otherintent.getIntExtra("com.mlong.mla", 0); 
		
		Friend_Add_Achievement_Fragment myfrag = this;
        Bundle myotherbundle;
        myotherbundle = myfrag.getArguments();
        int key;
        key= myotherbundle.getInt("key");
;
		
    	Bundle mybundle = new Bundle();
        mybundle.putString("namekey",my_name.getText().toString());
        mybundle.putString("desckey",my_desc.getText().toString());
        mybundle.putInt("achkey", randkey);
        
        if(date.isEnabled() == true)
        {
        	mybundle.putString("datekey",Sdate);
        	mybundle.putString("timekey", Stime);
        }
        else
        {
        	mybundle.putString("datekey","");
        	mybundle.putString("timekey", "");
        }
        
		//Intent databackIntent = new Intent();
		//databackIntent.putExtras(mybundle);
		//setResult(RESULT_OK,databackIntent);
	    
	    //store in database
		ACHDatabase myDB = new ACHDatabase(getActivity());
		
	    if(date.isEnabled() == true && myRPerson.isChecked() == true)
	    {
		
			myDB.open();
		
			myDB.createInsert(key, my_name.getText().toString(), randkey, Integer.parseInt(my_point.getText().toString()), null, user_date, Integer.parseInt(my_numb.getText().toString()), my_desc.getText().toString(), 1,null);
		
			myDB.close();
		
			getFragmentManager().popBackStackImmediate();
			
	    }
	    else if(date.isEnabled() == false && myRPerson.isChecked() == true)
	    {
			
			myDB.open();
		
			myDB.createInsert(key, my_name.getText().toString(), randkey, Integer.parseInt(my_point.getText().toString()), null, 0, Integer.parseInt(my_numb.getText().toString()), my_desc.getText().toString(), 1,null);
		
			myDB.close();
		
			getFragmentManager().popBackStackImmediate();
			
	    }
	    else if(date.isEnabled() == true && myRPerson.isChecked() == false)
	    {
			
			myDB.open();
		
			myDB.createInsert(key, my_name.getText().toString(), randkey, Integer.parseInt(my_point.getText().toString()), null, user_date, Integer.parseInt(my_numb.getText().toString()), my_desc.getText().toString(), 0,null);
		
			myDB.close();
		
			getFragmentManager().popBackStackImmediate();
			
	    }
	    else
	    {
	    	myDB.open();
			
			myDB.createInsert(key, my_name.getText().toString(), randkey, Integer.parseInt(my_point.getText().toString()), null, 0, Integer.parseInt(my_numb.getText().toString()), my_desc.getText().toString(), 0,null);
		
			myDB.close();
		
			getFragmentManager().popBackStackImmediate();
			
	    }
	    
	    
	    
	    }
	}
	
	public void myPickDate(View v)
	{
		DialogFragment newFragment = new Date_pick();
	    newFragment.show(getFragmentManager(), "datePicker");
	    
	}
	
	public void myPickTime(View v)
	{
		DialogFragment newFragment = new Time_pick();
	    newFragment.show(getFragmentManager(), "timePicker");
	}


	//get date

	public void myonDateSet(DatePicker Dview, int year, int month,
			int day) {
		// TODO Auto-generated method stub
		Log.i("vie",month+"-"+day+"-"+year);
		TextView mydate = (TextView) view.findViewById(R.id.tv_myDate);
		mydate.setText(1+month+"-"+day+"-"+year);
		
		myyear = year;
		mymonth = month;
		myday= day;
	}


	//get time

	public void myonTimeSet(TimePicker Tview, int hour, int minute) {
		// TODO Auto-generated method stub
		Log.i("vie",hour+"-"+minute);
		TextView mytime = (TextView) view.findViewById(R.id.tv_myTime);
		mytime.setText(hour+"-"+minute);

		myhour = hour;
		mymin = minute;
	}
	
	//radio button stuff
	public void RadioClick(View v)
	{
		RadioButton myR = (RadioButton) view.findViewById(R.id.radioButton1);
		Button date = (Button) view.findViewById(R.id.b_dpick);
		Button time = (Button) view.findViewById(R.id.b_tpick);
		TextView mydate = (TextView) view.findViewById(R.id.tv_myDate);
		TextView mytime = (TextView) view.findViewById(R.id.tv_myTime);
		if(myR.isChecked() == true && date.isEnabled() == false)
		{
			
			date.setEnabled(true);
			time.setEnabled(true);
			
		}
		else if(myR.isChecked() == true && date.isEnabled() == true)
		{
			myR.setChecked(false); 
			date.setEnabled(false);
			time.setEnabled(false);
			mydate.setText("");
			mytime.setText("");
		}	
	}
	
	
	
	public void onePerson(View v)
	{
		RadioButton myRPerson = (RadioButton) v.findViewById(R.id.radioButton2);
		if(myRPerson.isChecked() == true && ischecked == false)
		{
			ischecked = true;
		}
		
		else if(myRPerson.isChecked() == true && ischecked == true)
		{
			myRPerson.setChecked(false);
			ischecked = false;
		}
	}
	
	public void addIcon(View v)
	{
		
	}
	
}