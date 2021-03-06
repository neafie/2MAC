package com.mlong.mla;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

public class AddDialog extends DialogFragment {
   	
	public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
	
	// Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;
    
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
	
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_add_dialog, null))
        	   .setMessage(R.string.app_name)
               .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	 
                	   mListener.onDialogPositiveClick(AddDialog.this);
                   }
               })
               .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       // User cancelled the dialog
                	   mListener.onDialogNegativeClick(AddDialog.this);
                   }
               });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}