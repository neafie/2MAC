package com.mlong.mla;

import java.util.ArrayList;
import java.util.Random;

import com.mlong.mla.PersonalListItems.PersonalListItem;


import android.os.Bundle;
import android.app.Dialog;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Personal_Lists_Fragment extends BaseFragment 
	{

	public final static String MESSAGE = "com.mlong.mla";
	public final static int requestcode = 1;
	
	private ArrayList<PersonalListItem> m_parts = new ArrayList<PersonalListItem>();
 	private PersonalListCustomListview m_adapter;
   
    int REMOVE;
    int forkey;
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		
    	View view = inflater.inflate(R.layout.personal_lists_page, container, false);
        
		
		Button AddList = (Button)view.findViewById(R.id.button1);
		ListView myList;
        myList=(ListView)view.findViewById(android.R.id.list);
		myList.setChoiceMode(1);
        
		m_adapter = new PersonalListCustomListview(getActivity(), R.layout.personal_list_custom_listview, m_parts);
        myList.setAdapter(m_adapter);
		
		m_parts.clear();
		myList.setOnItemClickListener(listener);
		myList.setOnItemLongClickListener(Longlistener);

		PersonalDB myDB = new PersonalDB(getActivity());
		Cursor cursor;
		myDB.open();
		
		
		cursor = myDB.listquery();
        cursor.moveToFirst();
        
        // Check if our result was valid.
		if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	PersonalListItem myitem = new PersonalListItem();
                	String lname = cursor.getString(cursor.getColumnIndex(PersonalDB.COLUMN_LISTNAME));
                	String ldesc = cursor.getString(cursor.getColumnIndex(PersonalDB.COLUMN_LISTDESC));
                	forkey = Integer.parseInt(cursor.getString(cursor.getColumnIndex(PersonalDB.COLUMN_FOR)));
                	Cursor achCursor = myDB.getpoints(forkey);
                	int totalPoints = 0;
                	if( achCursor.moveToFirst() ) {
                		
                		do {
                			totalPoints += achCursor.getInt(achCursor.getColumnIndex(PersonalDB.COLUMN_POINTS));
                		} while(achCursor.moveToNext());
                	}
                	
                	
                	myitem.setDescription(ldesc);
                	myitem.setName(lname);
                	myitem.setListkey(forkey);
                	myitem.setPoints(totalPoints);
                	
                	//add item to list
            		m_parts.add(myitem);
            		m_adapter.notifyDataSetChanged();
                	
                } while (cursor.moveToNext());
            }
        }
		
        cursor.close();
        myDB.close();
		
       
		
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
    
    private OnItemClickListener listener =  new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            // TODO Auto-generated method stub
            
            PersonalListItem myitem = new PersonalListItem();
        	myitem = m_parts.get(arg2);
            
            REMOVE = arg2;
        	
            //gets list key from list

        	int listkey = myitem.getListkey();
        	
        	Bundle mybundle = new Bundle();
            mybundle.putInt("listkey", listkey);

            Fragment newFragment = new Personal_Achievement_List_Fragment();
            newFragment.setArguments(mybundle);
            
            mActivity.pushFragments(AppConstants.TAB_P, newFragment,true,true);
           
        }
    };
    private OnItemLongClickListener Longlistener = new AdapterView.OnItemLongClickListener() {

    	  @Override
    	  public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                  long arg3) {
    		  PersonalListItem myitem = new PersonalListItem();
          	myitem = m_parts.get(arg2);
              
              REMOVE = arg2;
          	
              //gets list key from list

          	forkey = myitem.getListkey();
          
          	//remove item to list
    		m_parts.remove(myitem);
          	deleteList();
    	    return true;
    	  }
    	};
	
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
		 
		 PersonalDB myDB = new PersonalDB(getActivity());
		 myDB.open();			
		 myDB.createListInsert(randkey,list_name.getText().toString().trim(),list_desc.getText().toString().trim());
		 myDB.close();
		
		 PersonalListItem myitem = new PersonalListItem();
		 myitem.setListkey(randkey);
		 myitem.setName(list_name.getText().toString());
		 myitem.setDescription(list_desc.getText().toString());
		 myitem.setPoints(0);
		 m_parts.add(myitem); 
		 m_adapter.notifyDataSetChanged();
	    }
	}
	
	public void deleteList()
	{
		PersonalDB myDB = new PersonalDB(getActivity());
		myDB.open();
		myDB.delete_list(forkey);
		myDB.close();
		m_adapter.notifyDataSetChanged();
	}
	
	
}