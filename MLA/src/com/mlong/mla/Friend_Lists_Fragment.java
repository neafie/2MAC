package com.mlong.mla;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.actionbarsherlock.app.SherlockFragment;
import com.mlong.mla.ListItems.ListItem;


import android.os.Bundle;
import android.app.Dialog;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Friend_Lists_Fragment extends SherlockFragment 
	{

	public final static String MESSAGE = "com.mlong.mla";
	public final static int requestcode = 1;
	
	private ArrayList<ListItem> m_parts = new ArrayList<ListItem>();
 	private ListCustomListview m_adapter;
   
    int REMOVE;
    List<Integer> myint = new ArrayList<Integer>();
    
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		
    	View view = inflater.inflate(R.layout.activity_main, container, false);
        
		int forkey;
		Button AddList = (Button)view.findViewById(R.id.button1);
		ListView myList;
        myList=(ListView)view.findViewById(android.R.id.list);
		myList.setChoiceMode(1);
        
		m_adapter = new ListCustomListview(getActivity(), R.layout.activity_list_custom_listview, m_parts);
        myList.setAdapter(m_adapter);
		
		m_parts.clear();
        
		ACHDatabase myDB = new ACHDatabase(getActivity());
		Cursor cursor;
		myDB.open();
		
		
		cursor = myDB.listquery();
        cursor.moveToFirst();
        
        // Check if our result was valid.
		if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	ListItem myitem = new ListItem();
                	String lname = cursor.getString(cursor.getColumnIndex(myDB.COLUMN_LISTNAME));
                	String ldesc = cursor.getString(cursor.getColumnIndex(myDB.COLUMN_LISTDESC));
                	forkey = Integer.parseInt(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_FOR)));
                	
                	myitem.setDescription(ldesc);
                	myitem.setName(lname);
                	myitem.setListkey(forkey);
                	
                	//add item to list
            		m_parts.add(myitem);
            		m_adapter.notifyDataSetChanged();
                	
                } while (cursor.moveToNext());
            }
        }
		
        cursor.close();
        myDB.close();
		
		myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                // TODO Auto-generated method stub
                
                ListItem myitem = new ListItem();
            	myitem = m_parts.get(arg2);
                
                REMOVE = arg2;
            	
                //gets list key from list
 
            	int listkey = myitem.getListkey();
            	
            	Bundle mybundle = new Bundle();   
                mybundle.putInt("listkey", listkey);
                
                
                Fragment newFragment = new Friend_Achievement_List_Fragment();
                newFragment.setArguments(mybundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                
                transaction.replace(R.id.realtabcontent, newFragment, "friends1");
                transaction.addToBackStack(null);
 
                // Commit the transaction
                transaction.commit();
            }
        });
		
		AddList.setOnClickListener(
		        new View.OnClickListener() {
		            @Override
		            public void onClick(View v) {
		            	AddDialog myaddDialog = new AddDialog();
		            	
		        		myaddDialog.show(getFragmentManager(), "Add"); 
		            }
		        }
		    );
		
		return view;
	}

    @Override 
	public void onCreate(Bundle savedInstanceState) { 
	        super.onCreate(savedInstanceState);
	
    }
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    
	    
	}
	
	public void sendMessage(View myV)
	{
		AddDialog myaddDialog = new AddDialog();
		myaddDialog.show(getFragmentManager(), "Add");
	}
	
	//add list of achievements
	
	public void positiveClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		 Dialog dialogView = dialog.getDialog();
		 
		 final EditText list_name = (EditText) dialogView.findViewById(R.id.et_aname);
		 final EditText list_desc = (EditText) dialogView.findViewById(R.id.et_listdesc);
		 if(list_name.getText().toString().equals("") || list_desc.getText().toString().equals(""))
			{
				Toast.makeText(getActivity(), "You must enter a valid name", Toast.LENGTH_SHORT).show(); 
			}
	     else{
		 
		 final int MAX_VALUE = 1000000;
		 Random rand = new Random();
		 int randkey = rand.nextInt(MAX_VALUE);
		 
		 ACHDatabase myDB = new ACHDatabase(getActivity());
		 myDB.open();			
		 myDB.createListInsert(randkey,list_name.getText().toString().trim(),list_desc.getText().toString().trim());
		 myDB.close();
		
		 ListItem myitem = new ListItem();
		 myitem.setListkey(randkey);
		 myitem.setName(list_name.getText().toString());
		 myitem.setDescription(list_desc.getText().toString());
		 m_parts.add(myitem); 
		 m_adapter.notifyDataSetChanged();
	    }
	}
	
}