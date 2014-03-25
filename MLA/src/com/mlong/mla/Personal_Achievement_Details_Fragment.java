package com.mlong.mla;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Personal_Achievement_Details_Fragment extends BaseFragment {

	boolean iscomplete = false;
	String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/ViePics/"; 
	int TAKE_PHOTO_CODE = 1337;
	int DELETECODE = 4;
	Bundle mybundle = new Bundle();
	View view;
	Personal_Achievement_Details_Fragment myfrag = this;
	int RESULT_CANCELED = 0;
	int RESULT_OK = -1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.activity_achievment_page, container, false);
		
        Bundle mybundle;
        mybundle = myfrag.getArguments();
		
		
		final String myName = mybundle.getString("namekey");
		int points = mybundle.getInt("points", 0);
		final int comp = mybundle.getInt("comp", 0);
		final String desc = mybundle.getString("desc");
		int ofcomp = mybundle.getInt("ofcomp",0);
		boolean iscomp = mybundle.getBoolean("iscomp", false);
		final String date = mybundle.getString("date");
		final String time = mybundle.getString("time");
		final int achkey = mybundle.getInt("achkey",0);
		String photopath = null;
		
		
		iscomplete = iscomp;
		//int myplace = intent.getIntExtra("place",0);
		
		TextView tv_points = (TextView) view.findViewById(R.id.tv_points_i);
    	tv_points.setText(Integer.toString(points));
    	TextView tv_comp = (TextView) view.findViewById(R.id.tv_numb_i);
    	tv_comp.setText(Integer.toString(comp));
    	TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc_i);
    	tv_desc.setText(desc);
    	TextView tv_ofcomp = (TextView) view.findViewById(R.id.tv_com2_i);
    	tv_ofcomp.setText(Integer.toString(ofcomp));
    	ImageView iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
    	
    	
    	final TextView tv_day = (TextView) view.findViewById(R.id.tv_time);
    	final TextView tv_time_remain = (TextView) view.findViewById(R.id.tv_time_remain);
    	final TextView tv_hour = (TextView) view.findViewById(R.id.tv_hour);
    	final TextView tv_min = (TextView) view.findViewById(R.id.tv_min);
    	final TextView tv_sec = (TextView) view.findViewById(R.id.tv_sec);
    	
    	final Button b_add = (Button) view.findViewById(R.id.b_addcomp);
    	final Button b_addphoto = (Button) view.findViewById(R.id.b_addphoto);
    	
    	//need to do
    	//check current time, if greater than current disable button
    	//got currenttime milliseconds
    	//othercalendar.clear();
    	long usertime = 0;
    	long currtime;
    	long timeleft;
    	
    	Calendar currcalendar = Calendar.getInstance();
    	currtime = currcalendar.getTimeInMillis();
    	
    	PersonalDB myDB = new PersonalDB(getActivity());
    	
    	myDB.open();
		
    	Cursor cursor;
    	
		cursor = myDB.gettime(achkey);
        cursor.moveToFirst();
        
        if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(PersonalDB.COLUMN_TIMEFRAME)) != null)
                	{
                		usertime =	cursor.getLong(cursor.getColumnIndex(PersonalDB.COLUMN_TIMEFRAME));
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
                	if(cursor.getString(cursor.getColumnIndex(PersonalDB.COLUMN_PHOTOPATH)) != null)
                	{
                		photopath =	cursor.getString(cursor.getColumnIndex(PersonalDB.COLUMN_PHOTOPATH));
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

            @Override
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

            @Override
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
    		@Override
			public void onClick(View v) {

    			PersonalDB myDB = new PersonalDB(getActivity());
    			//Intent intent = getIntent();
    			
    	        Bundle mybundle;
    	        mybundle = myfrag.getArguments();
		
    			int ofcomp = mybundle.getInt("ofcomp", 0);
    			int achkey = mybundle.getInt("achkey",0);
    			
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
    	                	if(cursor.getString(cursor.getColumnIndex(PersonalDB.COLUMN_TIMEFRAME)) != null)
    	                	{
    	                		usertime =	cursor.getLong(cursor.getColumnIndex(PersonalDB.COLUMN_TIMEFRAME));
    	         
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
    	                	if(cursor.getString(cursor.getColumnIndex(PersonalDB.COLUMN_NUMBEROFCOMP)) != null)
    	                	{
    	                		ofcomp =	cursor.getInt(cursor.getColumnIndex(PersonalDB.COLUMN_NUMBEROFCOMP));
    	                	}
    	                } while (cursor.moveToNext());
    	          
    	               
    	            }
    	        }
    	        cursor.close();
            	myDB.close();
            	
    	        Button b_add = (Button) view.findViewById(R.id.b_addcomp);
    	        
    	        ofcomp = ofcomp +1;
    			
    			
    			if(ofcomp == comp)
    			{
    				myDB.open();
    				b_add.setEnabled(false);
    				b_addphoto.setEnabled(false);
    				myDB.setiscomp(achkey, 1);
    				myDB.close();
    				iscomplete = true;
    			}
    			
    			TextView tv_comp2 = (TextView) view.findViewById(R.id.tv_com2_i);
            	tv_comp2.setText(Integer.toString(ofcomp));
            	
            	myDB.open();
            	myDB.setofcomp(achkey, ofcomp);
            	myDB.close();
    		} 
    	});
		
		return view;
		
	}

	
	@Override
	public void onActivityResult(int ReqC, int ResC, Intent data)
	{
		Log.i("vie","Made It to Activity Result, ReqC =  " + ReqC + " and ResC = " + ResC);
		
		Bundle mybundle;
        mybundle = myfrag.getArguments();
		
		
		int achkey = mybundle.getInt("achkey",0);
		String achname = mybundle.getString("namekey");
		ImageView iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
		
		if(ReqC == TAKE_PHOTO_CODE && ResC == RESULT_OK)
		{
			//add photopath to database
			int photocount = 0;
			String photopath = null;
			PersonalDB myDB = new PersonalDB(getActivity());
			Cursor cursor;
			
			myDB.open();
			
			photocount = getachphotocount();
			myDB.setphotopath(achkey, dir+achname+photocount+".jpg");
        	
        	myDB.setphotocount(achkey, photocount+1);	
			cursor = myDB.getphotopath(achkey);
	        cursor.moveToFirst();
	        
	        if(cursor != null) {
	            if(cursor.getCount() > 0) {
	             	
	                // Loop through all Results
	                do {
	                	//can take out if later when add not null to database
	                	if(cursor.getString(cursor.getColumnIndex(PersonalDB.COLUMN_PHOTOPATH)) != null)
	                	{
	                		photopath =	cursor.getString(cursor.getColumnIndex(PersonalDB.COLUMN_PHOTOPATH));
	                	}
	                } while (cursor.moveToNext());
	          
	               
	            }
	        }       
	        
	        cursor.close();
	        myDB.close();
			
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
		
		else if(ReqC == TAKE_PHOTO_CODE && ResC == RESULT_CANCELED)
		{
			int photocount;
			photocount = getachphotocount();
			
			//delete photofile
			File file = new File(dir+achname+photocount+".jpg");
			file.delete();
		}
	
	}
	
	
	protected boolean getcomp() {
		// TODO Auto-generated method stub
		
		return iscomplete;
	}
	
	public void takePhoto(View v) {
    	
	// here,counter will be incremented each time,and the picture taken by camera will be stored as 1.jpg,2.jpg and likewise.
	
		Bundle mybundle;
        mybundle = myfrag.getArguments();
		
	  String achname = mybundle.getString("namekey");
		
      int photocount = getachphotocount();
      File newdir = new File(dir); 
      newdir.mkdirs();
      String file = dir+achname+photocount+".jpg";
      File newfile = new File(file);
      try {
          newfile.createNewFile();
      } catch (IOException e) {}       

      Uri outputFileUri = Uri.fromFile(newfile);

      Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
      cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
      getActivity().startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
	}
	
	public int getachphotocount()
	{
		int myphotocount = 0;
		PersonalDB myDB = new PersonalDB(getActivity());
		Cursor cursor;
		myDB.open();
		
		Bundle mybundle;
        mybundle = myfrag.getArguments();
		int achkey = mybundle.getInt("achkey",0);
		
		cursor = myDB.getphotocount(achkey);
		cursor.moveToFirst();
		 if(cursor != null) {
	            if(cursor.getCount() > 0) {
	             	
	                // Loop through all Results
	                do {
	                	//can take out if later when add not null to database
	                	if(cursor.getString(cursor.getColumnIndex(PersonalDB.COLUMN_PHOTOCOUNT)) != null)
	                	{
	                		myphotocount =	cursor.getInt(cursor.getColumnIndex(PersonalDB.COLUMN_PHOTOCOUNT));
	                	}
	                } while (cursor.moveToNext());
	            }
	        }
		cursor.close();
		myDB.close();
		
		return myphotocount;
	}

	
	public void delete(View view)
	{	
		Bundle mybundle;
        mybundle = myfrag.getArguments();
		int achkey = mybundle.getInt("achkey",0);
		PersonalDB myDB = new PersonalDB(getActivity());
		myDB.open();
		myDB.delete_achievement(achkey);
		myDB.close();    
		
		mActivity.onBackPressed();

	}
	
}
