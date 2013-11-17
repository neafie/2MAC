package com.mlong.mla;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.widget.DatePicker;

public class Date_pick extends DialogFragment{

@Override
public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
final Calendar c = Calendar.getInstance();
int year = c.get(Calendar.YEAR);
int month = c.get(Calendar.MONTH);
int day = c.get(Calendar.DAY_OF_MONTH);

// Create a new instance of DatePickerDialog and return it
return new DatePickerDialog(getActivity(), (OnDateSetListener)getActivity(), year, month, day);
}



/*public void onDateSet(DatePicker view, int year, int month, int day) {
// Do something with the date chosen by the user
	String mydate = month+"-"+day+"-"+"-"+year;	
	
}*/

}
