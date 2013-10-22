package com.mlong.mla;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LookDialog extends DialogFragment {
	
	public interface NoticelookDialogListener {
        public void onlookDialogPositiveClick(DialogFragment dialog);
        public void onlookDialogNegativeClick(DialogFragment dialog);
    }
	
	Cursor cursor;
	
	
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
		String myName = mybundle.getString("namekey");
		int points = mybundle.getInt("points");
		int comp = mybundle.getInt("comp");
		String desc = mybundle.getString("desc");
		int ofcomp = mybundle.getInt("ofcomp");
		boolean iscomp = mybundle.getBoolean("iscomp");
		
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        
        final View layout = inflater.inflate(R.layout.activity_look_dialog,null); 

        builder.setView(layout)
        	   .setMessage(myName)
               .setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   
                	   
                	   mListener.onlookDialogPositiveClick(LookDialog.this);
                   }
               })
               .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                	   mListener.onlookDialogNegativeClick(LookDialog.this);
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
        	
        	final Button b_add = (Button) layout.findViewById(R.id.b_addcomp);
        	
        	if(iscomp == true)
        	{
        		b_add.setEnabled(false);
        	}
        	
        	//if is competed = 1 disablebutton  
        	b_add.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {

        			ACHDatabase myDB = new ACHDatabase(getActivity());
        			int comp2 = 0;
        			Bundle mybundle = getArguments();
        			String myS = mybundle.getString("namekey");
        			comp2 = mybundle.getInt("ofcomp");
        			int comp = mybundle.getInt("comp");
        			
        			myDB.open();
        			
        			cursor = myDB.getofcomp(myS);
        	        cursor.moveToFirst();
        	        
        	        if(cursor != null) {
        	            if(cursor.getCount() > 0) {
        	             	
        	                // Loop through all Results
        	                do {
        	                	//can take out if later when add not null to database
        	                	if(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_NUMBEROFCOMP)) != null)
        	                	{
        	                		comp2 =	Integer.parseInt(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_NUMBEROFCOMP)));
        	                	}
        	                } while (cursor.moveToNext());
        	          
        	               
        	            }
        	        }
        			
        			comp2 = comp2 +1;
        			
        			Button b_add = (Button) layout.findViewById(R.id.b_addcomp);
        			if(comp2 == comp)
        			{
        				b_add.setEnabled(false);
        				myDB.setiscomp(myS, 1);
        			}
        			
        			TextView tv_comp2 = (TextView) layout.findViewById(R.id.tv_com2_i);
                	tv_comp2.setText(Integer.toString(comp2));
                	myDB.setofcomp(myS, comp2);	
        		} 
        	});
        	
        	
        // Create the AlertDialog object and return it
        return builder.create();
    }
	
}
