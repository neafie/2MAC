package com.mlong.mla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import library.CreateAttemptFunctions;
import library.CreateListFunctions;
import library.ListFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mlong.mla.ListItems.ListItem;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Dialog;
import android.database.Cursor;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
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

public class Friend_Lists_Fragment extends BaseFragment 
	{

	public final static String MESSAGE = "com.mlong.mla";
	public final static int requestcode = 1;
	
	private ArrayList<ListItem> m_parts = new ArrayList<ListItem>();
 	private ListCustomListview m_adapter;
 	
 // JSON Node names
 	private static final String TAG_SUCCESS = "success";
 	private static final String TAG_LISTS = "list";
 	private static final String TAG_LID = "lid";
 	private static final String TAG_UID_CREATOR = "uid_ceator";
 	private static final String TAG_LIST_NAME = "list_name";
 	private static final String TAG_LIST_DESCRIPTION = "list_description";
 	private static final String TAG_CREATED_AT = "created_at";

   
    int REMOVE;
    int forkey;
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		
    	View view = inflater.inflate(R.layout.activity_main, container, false);
        
		
		Button AddList = (Button)view.findViewById(R.id.button1);
		ListView myList;
        myList=(ListView)view.findViewById(android.R.id.list);
		myList.setChoiceMode(1);
        
		m_adapter = new ListCustomListview(getActivity(), R.layout.activity_list_custom_listview, m_parts);
        myList.setAdapter(m_adapter);
		
		m_parts.clear();
		myList.setOnItemClickListener(listener);
		myList.setOnItemLongClickListener(Longlistener);

		new GetLists().execute(14);
		
		/*ACHDatabase myDB = new ACHDatabase(getActivity());
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
                	String lname = cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_LISTNAME));
                	String ldesc = cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_LISTDESC));
                	forkey = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_FOR)));
                	
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
		*/
       
		
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
    
    private class GetLists extends AsyncTask<Integer, Void, JSONObject> {
        
        @Override
		protected JSONObject doInBackground(Integer... params) {
        	ListFunctions listFunction = new ListFunctions();
            JSONObject json = listFunction.getLists(params[0]);
            return json;
        }
        
        @Override
		protected void onPostExecute(JSONObject json) {
        	ArrayList<HashMap<String,String>> lists;
        	JSONArray jLists = null;
        	
        	try{
        		int success = json.getInt(TAG_SUCCESS);
        		if(success == 1) {
        			jLists = json.getJSONArray(TAG_LISTS);
        			
        			for(int i=0; i < jLists.length(); i++) {
        				JSONObject c = jLists.getJSONObject(i);
        				ListItem myitem = new ListItem();
                    	String lname = c.getString(TAG_LIST_NAME);
                    	Log.d("test",c.getString(TAG_LIST_NAME));
                    	String ldesc = c.getString(TAG_LIST_DESCRIPTION);
                    	forkey = Integer.parseInt(c.getString(TAG_LID));
                    	
                    	myitem.setDescription(ldesc);
                    	myitem.setName(lname);
                    	myitem.setListkey(forkey);
                    	
                    	//add item to list
                		m_parts.add(myitem);
                		m_adapter.notifyDataSetChanged();
            
        			}
        		}
        		
        	}
        	catch(JSONException e) {
        		e.printStackTrace();
        	}
        }

  }
        
    
    private OnItemClickListener listener =  new AdapterView.OnItemClickListener() {

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
            
            mActivity.pushFragments(AppConstants.TAB_F, newFragment,true,true);
           
        }
    };
    private OnItemLongClickListener Longlistener = new AdapterView.OnItemLongClickListener() {

    	  @Override
    	  public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
                  long arg3) {
    		  ListItem myitem = new ListItem();
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
		 
		 /*ACHDatabase myDB = new ACHDatabase(getActivity());
		 myDB.open();			
		 myDB.createListInsert(randkey,list_name.getText().toString().trim(),list_desc.getText().toString().trim());
		 myDB.close();
		*/
		 
		 ListItem myitem = new ListItem();
		 String lname = list_name.getText().toString();
		 String ldesc = list_desc.getText().toString();
		 new PushLists().execute(lname,ldesc);
		 
		 
		 /*m_parts.add(myitem); 
		 m_adapter.notifyDataSetChanged();
		 */
	    }
	}
	
	public void deleteList()
	{
		ACHDatabase myDB = new ACHDatabase(getActivity());
		myDB.open();
		myDB.delete_list(forkey);
		myDB.close();
		m_adapter.notifyDataSetChanged();
	}
	
	private class PushLists extends AsyncTask<String, Void, JSONObject> {
        
        @Override
		protected JSONObject doInBackground(String... params) {
        	CreateListFunctions listFunction = new CreateListFunctions();
            JSONObject json = listFunction.createList("14",params[0],params[1]);
            
            return json;
        }
        
        @Override
		protected void onPostExecute(JSONObject json) {
        	

            //mActivity.pushFragments(AppConstants.TAB_F, Friend_Lists_Fragment.this,true,true);
        			
        		
       
        }

	}

}

	
	
