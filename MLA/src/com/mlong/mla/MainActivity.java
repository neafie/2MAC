package com.mlong.mla;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import android.os.Bundle;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity 
	implements AddDialog.NoticeDialogListener{

	public final static String MESSAGE = "com.mlong.mla";
	public final static int requestcode = 1;
	//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;  
    AddDialog myaddDialog = new AddDialog();
    
    ListView myList;
    
    
    int REMOVE;
    
    Cursor cursor;
    List<Integer> myint = new ArrayList<Integer>();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        
		ACHDatabase myDB = new ACHDatabase(this);
		
		myDB.open();
		cursor = myDB.listquery();
        cursor.moveToFirst();
        
        // Check if our result was valid.
		if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	String lname = cursor.getString(cursor.getColumnIndex(myDB.COLUMN_LISTNAME));
                	listItems.add(lname);
                } while (cursor.moveToNext());
            }
        }
		
        cursor.close();
        myDB.close();
        
       
        myDB.open();
		cursor = myDB.keyquery();	
        cursor.moveToFirst();
        
        // Check if our result was valid.
		if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	myint.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_FOR))));
                } while (cursor.moveToNext());
            }
        }
		
        cursor.close();
        myDB.close();
		
		myList=(ListView)findViewById(android.R.id.list);
		adapter=new ArrayAdapter<String>(this,
	            android.R.layout.simple_list_item_1,
	            listItems);
		
		myList.setChoiceMode(1);
		myList.setItemsCanFocus(false);
		myList.setAdapter(adapter);
		myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                // TODO Auto-generated method stub
                
            	String myS =(myList.getItemAtPosition(arg2).toString());
                REMOVE = arg2;
            	int mykey = myint.get(arg2);
            	
            	Bundle mybundle = new Bundle();
                Intent intent = new Intent(MainActivity.this, AddAchActivity.class);
                mybundle.putInt("intkey", mykey);
                mybundle.putString("stringkey",myS);
                intent.putExtras(mybundle);
                startActivityForResult(intent,requestcode);
            }
        });
	
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void sendMessage(View myV)
	{
		myaddDialog.show(getSupportFragmentManager(), "Add");
	}
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		 Dialog dialogView = dialog.getDialog();
		 
		 final EditText my_name = (EditText) dialogView.findViewById(R.id.et_aname);
		 if(my_name.getText().toString().equals(""))
			{
				Toast.makeText(this, "You must enter a valid name", Toast.LENGTH_SHORT).show(); 
			}
	     else{
		 
		 final int MAX_VALUE = 1000000;
		 Random rand = new Random();
		 int randkey = rand.nextInt(MAX_VALUE);
		 
		 ACHDatabase myDB = new ACHDatabase(this);
		 myDB.open();			
		 myDB.createListInsert(randkey,my_name.getText().toString().trim());
		 myDB.close();
		 		 
		myint.add(randkey);
	    listItems.add(my_name.getText().toString()); 
		adapter.notifyDataSetChanged();
	    }
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
	}
	
	public void onActivityResult(int ReqC, int ResC, Intent data)
	{
		if(ReqC == requestcode && ResC == RESULT_OK)
		{
    		listItems.remove(REMOVE);
    		adapter.notifyDataSetChanged();	                  
		} 
	}
	
}