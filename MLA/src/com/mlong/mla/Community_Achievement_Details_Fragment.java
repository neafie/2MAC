package com.mlong.mla;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import library.CreateAttemptFunctions;
import library.DatabaseHandler;
import library.UserFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Toast;

public class Community_Achievement_Details_Fragment extends BaseFragment {

	boolean iscomplete = false;
	String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/ViePics/"; 
	int TAKE_PHOTO_CODE = 1337;
	int DELETECODE = 4;
	Bundle mybundle = new Bundle();
	View view;
	Community_Achievement_Details_Fragment myfrag = this;
	int RESULT_CANCELED = 0;
	int RESULT_OK = -1;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.community_achievment_page1, container, false);
		
     	String photopath = null;
		
		
		//int myplace = intent.getIntExtra("place",0);
		
    	ImageView iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
    	
    	
    	final Button b_add = (Button) view.findViewById(R.id.b_addcomp);
    	final Button b_addphoto = (Button) view.findViewById(R.id.b_addphoto);
    	
    	//need to do
    	//check current time, if greater than current disable button
    	//got currenttime milliseconds
    	//othercalendar.clear();
    	    	
    	//if is competed = 1 disablebutton  
    	b_add.setOnClickListener(new View.OnClickListener() {
    		@Override
			public void onClick(View v) {
    			new MyAsyncTask().execute();
    		} 
    	});
		
		return view;
		
	}

	  private class MyAsyncTask extends AsyncTask<Void, Void, JSONObject> {
	        
	        @Override
			protected JSONObject doInBackground(Void... params) {
	        	CreateAttemptFunctions attemptFunction = new CreateAttemptFunctions();
	            JSONObject json = attemptFunction.createList("14","1","0","0","","");
	            return json;
	        }
	        
	        @Override
			protected void onPostExecute(JSONObject json) {
	        	//Toast.makeText(getApplicationContext(),"Attempt sent to be approved.",Toast.LENGTH_LONG).show();
	        	mActivity.pushFragments(AppConstants.TAB_C, new CommunityPage1(),
										true, true);
	        }

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
			ACHDatabase myDB = new ACHDatabase(getActivity());
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
	                	if(cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_PHOTOPATH)) != null)
	                	{
	                		photopath =	cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_PHOTOPATH));
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
		ACHDatabase myDB = new ACHDatabase(getActivity());
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
	                	if(cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_PHOTOCOUNT)) != null)
	                	{
	                		myphotocount =	cursor.getInt(cursor.getColumnIndex(ACHDatabase.COLUMN_PHOTOCOUNT));
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
		ACHDatabase myDB = new ACHDatabase(getActivity());
		myDB.open();
		myDB.delete_achievement(achkey);
		myDB.close();    
		
		mActivity.onBackPressed();

	}
	
}
