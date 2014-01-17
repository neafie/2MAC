package com.mlong.mla;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LookDialog extends DialogFragment {
	
	public interface NoticelookDialogListener {
        public void onlookDialogPositiveClick(DialogFragment dialog, int achkeys);
        public void onlookDialogNegativeClick(DialogFragment dialog, String Name, String Description, boolean iscomplete, int place, String date, String time, int achkey);
    }
	
	//Cursor cursor;
	boolean iscomplete = false;

	
	// Use this instance of the interface to deliver action events
    NoticelookDialogListener mListener;
    
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticelookDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

		Bundle mybundle = getArguments();
		final String myName = mybundle.getString("namekey");
		int points = mybundle.getInt("points");
		int comp = mybundle.getInt("comp");
		final String desc = mybundle.getString("desc");
		int ofcomp = mybundle.getInt("ofcomp");
		boolean iscomp = mybundle.getBoolean("iscomp");
		final String date = mybundle.getString("date");
		final String time = mybundle.getString("time");
		final int achkey = mybundle.getInt("achkey");
		String photopath = null;
		
		iscomplete = iscomp;
		final int myplace = mybundle.getInt("place");
		
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View layout = inflater.inflate(R.layout.activity_look_dialog,null); 
		
		
        builder.setView(layout)
        	   .setMessage(myName)
               .setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   
                	   
                	   mListener.onlookDialogPositiveClick(LookDialog.this, achkey);
                   }
               })
               .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                	   boolean iscomp = getcomp();
                	   
                	   mListener.onlookDialogNegativeClick(LookDialog.this, myName, desc, iscomp, myplace, date, time, achkey);
                   }

				
               });
        	
        	TextView tv_points = (TextView) layout.findViewById(R.id.tv_points_i);
        	tv_points.setText(Integer.toString(points));
        	TextView tv_comp = (TextView) layout.findViewById(R.id.tv_numb_i);
        	tv_comp.setText(Integer.toString(comp));
        	TextView tv_desc = (TextView) layout.findViewById(R.id.tv_desc_i);
        	tv_desc.setText(desc);
        	TextView tv_ofcomp = (TextView) layout.findViewById(R.id.tv_com2_i);
        	tv_ofcomp.setText(Integer.toString(ofcomp));
        	ImageView iv_photo = (ImageView) layout.findViewById(R.id.iv_photo);
        	
        	
        	final TextView tv_day = (TextView) layout.findViewById(R.id.tv_time);
        	final TextView tv_time_remain = (TextView) layout.findViewById(R.id.tv_time_remain);
        	final TextView tv_hour = (TextView) layout.findViewById(R.id.tv_hour);
        	final TextView tv_min = (TextView) layout.findViewById(R.id.tv_min);
        	final TextView tv_sec = (TextView) layout.findViewById(R.id.tv_sec);
        	
        	final Button b_add = (Button) layout.findViewById(R.id.b_addcomp);
        	final Button b_addphoto = (Button) layout.findViewById(R.id.b_addphoto);
        	
        	//need to do
        	//check current time, if greater than current disable button
        	//got currenttime milliseconds
        	//othercalendar.clear();
        	long usertime = 0;
        	long currtime;
        	long timeleft;
        	
        	Calendar currcalendar = Calendar.getInstance();
        	currtime = currcalendar.getTimeInMillis();
        	
        	ACHDatabase myDB = new ACHDatabase(getActivity());
        	myDB.open();
			
        	Cursor cursor;
        	
			cursor = myDB.gettime(achkey);
	        cursor.moveToFirst();
	        
	        if(cursor != null) {
	            if(cursor.getCount() > 0) {
	             	
	                // Loop through all Results
	                do {
	                	//can take out if later when add not null to database
	                	if(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_TIMEFRAME)) != null)
	                	{
	                		usertime =	cursor.getLong(cursor.getColumnIndex(myDB.COLUMN_TIMEFRAME));
	                	}
	                } while (cursor.moveToNext());
	          
	               
	            }
	        }
        	
	        cursor = myDB.getphotopath(achkey);
	        cursor.moveToFirst();
	        
	        if(cursor != null) {
	            if(cursor.getCount() > 0) {
	             	
	                // Loop through all Results
	                do {
	                	//can take out if later when add not null to database
	                	if(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_PHOTOPATH)) != null)
	                	{
	                		photopath =	cursor.getString(cursor.getColumnIndex(myDB.COLUMN_PHOTOPATH));
	                	}
	                } while (cursor.moveToNext());
	          
	               
	            }
	        }       
	        
	        cursor.close();
	        myDB.close();
        	
	        if(photopath != null)
        	{
	        	try {
	        	FileInputStream imgFile = new FileInputStream(photopath);
        		if(imgFile != null){

        			//resize image for memory purposes
        			BitmapFactory.Options options=new BitmapFactory.Options();
        			options.inSampleSize = 8;
        			Bitmap myBitmap=BitmapFactory.decodeStream(imgFile,null,options);
        			
        		    iv_photo.setImageBitmap(myBitmap);
        		}
	        	}catch (FileNotFoundException e) {}
        	}
	        
	        
	        //need better time formatting
	        //getting time remaining
	        timeleft = usertime - currtime;
	        
	        //setting up timer, not complete needs some formatting
	        if(timeleft > 0)
	        {
	        new CountDownTimer(timeleft, 1000) {

	            public void onTick(long millisUntilFinished) {
	                tv_day.setText("Days: " + millisUntilFinished / (60 * 60 * 24 * 1000) + " Hours: " + (millisUntilFinished / (60 * 60 * 1000)) % 24
	                		+ " Min: " + (millisUntilFinished / (60 * 1000)) % 60 + " Sec: " + (millisUntilFinished / 1000) % 60);
	                //tv_hour.setText("Hours: " + (millisUntilFinished / (60 * 60 * 1000)) % 24);
	                //tv_min.setText("Min: " + (millisUntilFinished / (60 * 1000)) % 60);
	                //tv_sec.setText("Sec: " + (millisUntilFinished / 1000) % 60);
	                tv_hour.setText("");
		        	tv_min.setText("");
		        	tv_sec.setText("");
	                
	            }

	            public void onFinish() {
	            	b_add.setEnabled(false);
	            	b_addphoto.setEnabled(false);
	            	tv_day.setText("");
		        	tv_hour.setText("");
		        	tv_min.setText("");
		        	tv_sec.setText("");
		        	tv_time_remain.setText("");
	                //tv_day.setText("done!");
	            }
	         }.start();
	        }
	        
	        int days = (int)((timeleft) / (60 * 60 * 24 * 1000)) % 365;
	        int hours = (int)(timeleft / (60 * 60 *1000)) % 24;
	        int minutes = (int)(timeleft / (60 * 1000)) % 60;
	        int seconds = (int)(timeleft / 1000) % 60;
	        
	        if(timeleft > 0)
	        {
	        	String dateString = String.format("%03d:%02d:%02d:%02d",days,hours, minutes, seconds);
	        	//tv_day.setText(dateString);
	        	/*tv_hour.setText(hours+" Hours ");
	        	tv_min.setText(minutes+" Minutes ");
	        	tv_sec.setText(seconds+" Seconds ");
	        	tv_time_remain.setText("Time Remaining: ");*/
	        }
	        else
	        {
	        	tv_day.setText("");
	        	tv_hour.setText("");
	        	tv_min.setText("");
	        	tv_sec.setText("");
	        	tv_time_remain.setText("");
	        }
	        
	        if(iscomp == true || (usertime < currtime && usertime != 0))
        	{
        		b_add.setEnabled(false);
        		b_addphoto.setEnabled(false);
        	}
        	
        	//if is competed = 1 disablebutton  
        	b_add.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {

        			ACHDatabase myDB = new ACHDatabase(getActivity());
        			int comp2 = 0;
        			Bundle mybundle = getArguments();
        			comp2 = mybundle.getInt("ofcomp");
        			int comp = mybundle.getInt("comp");
        			int achkey = mybundle.getInt("achkey");
        			
        			long usertime = 0;
                	long currtime;
                	Calendar currcalendar = Calendar.getInstance();
                	currtime = currcalendar.getTimeInMillis();
        			
        			myDB.open();
        			
        			Cursor cursor;
        			cursor = myDB.gettime(achkey);
        	        cursor.moveToFirst();
        	        
        	        if(cursor != null) {
        	            if(cursor.getCount() > 0) {
        	             	
        	                // Loop through all Results
        	                do {
        	                	//can take out if later when add not null to database
        	                	if(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_TIMEFRAME)) != null)
        	                	{
        	                		usertime =	cursor.getLong(cursor.getColumnIndex(myDB.COLUMN_TIMEFRAME));
        	         
        	                	}
        	                } while (cursor.moveToNext());
        	          
        	               
        	            }
        	        }
                	
        	        cursor.close();
        	        myDB.close();
        			
        			
        	        if(usertime != 0 && usertime < currtime)
                	{
                		b_add.setEnabled(false);
                		b_addphoto.setEnabled(false);
                		return;
                	}
			
        			myDB.open();
        			
        			cursor = myDB.getofcomp(achkey);
        	        cursor.moveToFirst();
        	        
        	        if(cursor != null) {
        	            if(cursor.getCount() > 0) {
        	             	
        	                // Loop through all Results
        	                do {
        	                	//can take out if later when add not null to database
        	                	if(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_NUMBEROFCOMP)) != null)
        	                	{
        	                		comp2 =	cursor.getInt(cursor.getColumnIndex(myDB.COLUMN_NUMBEROFCOMP));
        	                	}
        	                } while (cursor.moveToNext());
        	          
        	               
        	            }
        	        }
        	        cursor.close();
                	myDB.close();
                	
        	        Button b_add = (Button) layout.findViewById(R.id.b_addcomp);
        	        
        	        comp2 = comp2 +1;
        			
        			
        			if(comp2 == comp)
        			{
        				myDB.open();
        				b_add.setEnabled(false);
        				b_addphoto.setEnabled(false);
        				myDB.setiscomp(achkey, 1);
        				myDB.close();
        				iscomplete = true;
        			}
        			
        			TextView tv_comp2 = (TextView) layout.findViewById(R.id.tv_com2_i);
                	tv_comp2.setText(Integer.toString(comp2));
                	
                	myDB.open();
                	myDB.setofcomp(achkey, comp2);
                	myDB.close();
        		} 
        	});
        
        	
        	
        // Create the AlertDialog object and return it
        return builder.create();
    }


	protected boolean getcomp() {
		// TODO Auto-generated method stub
		
		return iscomplete;
	}
	
}