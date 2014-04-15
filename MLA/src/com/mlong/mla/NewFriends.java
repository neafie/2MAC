package com.mlong.mla;

import java.util.ArrayList;

import com.mlong.mla.ContactItems.ContactItem;

import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class NewFriends extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_friend__contact__list);
		
		View view;
		ListView myList;
		ArrayList<ContactItem> m_parts = new ArrayList<ContactItem>();
	 	ContactsCustomListView m_adapter;
	 	ArrayList<Integer> usedPhoneId = new ArrayList<Integer>();
	 	ArrayList<ContactItem> firstContact = new ArrayList<ContactItem>();
	 	ArrayList<ContactItem> filteredContact = new ArrayList<ContactItem>();
		


			myList=(ListView)findViewById(android.R.id.list);
	        
			m_adapter = new ContactsCustomListView(this, R.layout.contacts_listview, m_parts);
	        myList.setAdapter(m_adapter);
			
			ACHDatabase myDB = new ACHDatabase(this);
			Cursor cursor;
			myDB.open();
			//cursor = myDB.getPhoneId(listkey);
	       // cursor.moveToFirst();        
	        
	    /* // Check if our result was valid.
	     		if(cursor != null) {
	                 if(cursor.getCount() > 0) {
	 	
	                     // Loop through all Results
	                     do {
	                     	//can take out if later when add not null to database
	                     	if(cursor.getString(cursor.getColumnIndex(ACHDatabase.COLUMN_PHONEID)) != null)
	                     	{
	                     		usedPhoneId.add(cursor.getInt(cursor.getColumnIndex(ACHDatabase.COLUMN_PHONEID)));
	                     	}
	                     	
	                 } while (cursor.moveToNext());
	             }
	         }*/
	        
	        Cursor cursor2;
			cursor2 = this.getContentResolver().query(
				       ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				       null,
				          null,
				          null,
				          null);
	        cursor2.moveToFirst();
	        
	        
	        // Check if our result was valid.
			if(cursor2 != null) {
	            if(cursor2.getCount() > 0) {
	             	
	                // Loop through all Results
	                do {
	                	
	                	ContactItem myitem = new ContactItem();
	                	String Name = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	                	String Number = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	                	int iD = cursor2.getInt(cursor2.getColumnIndex(BaseColumns._ID));
		
	                	myitem.setPhoneNumber(Number);
	                	myitem.setName(Name);
	                	myitem.setPhoneId(iD);

	                	firstContact.add(myitem);
	                	
	                } while (cursor2.moveToNext());
	            }
	        }
			
			cursor2.close();
			//cursor.close();
			myDB.close();
			
			filteredContact.clear();
			
			int idsize = usedPhoneId.size();
			
			if(usedPhoneId.size() == 0)
			{
				for(int j = 0; j < firstContact.size(); j++)
				{
					ContactItem myitem = new ContactItem();
					myitem = firstContact.get(j);
					filteredContact.add(myitem);
				}
			}
			else
			{
				for(int i = 0; i < usedPhoneId.size(); i++)
				{
					for(int j = 0; j < firstContact.size(); j++)
					{			
						ContactItem myitem = new ContactItem();
						myitem = firstContact.get(j);
						if(myitem.getPhoneId() == usedPhoneId.get(i))
						{
							firstContact.remove(myitem);
							break;
						}	
					}	
				}
			}
			
			filteredContact = firstContact;
			
			for(int i = 0; i < filteredContact.size(); i++)
			{
				ContactItem myitem = new ContactItem();
				myitem = filteredContact.get(i);
				Log.i("vie","new list entry Id: "+myitem.getPhoneId());
				m_parts.add(myitem);
	        	m_adapter.notifyDataSetChanged();
			}
			
			
			/*Button b_addContacts = (Button) view.findViewById(R.id.b_addContacts);
	        b_addContacts.setOnClickListener(new View.OnClickListener() {
	            @Override
				public void onClick(View v) {
	            	ACHDatabase mydb = new ACHDatabase(getActivity());
	            	mydb.open();      	
	            	String name;
	            	String number;
	            	int pid;
	     	
	            	for (int i = 0; i < m_adapter.getcheckedposition().size(); i++){
	            		ContactItem myitem = new ContactItem();
	            		myitem = m_adapter.getcheckedposition().get(i);
	            	    name = myitem.getName();    
	            		number = myitem.getPhonenumber();
	            	    pid = myitem.getPhoneId();	
	            	    mydb.createPhoneInsert(listkey, number, name, pid);     	    
	            	}
	            	mydb.close();
	            }
	           
	        });*/
			
			

		}
		
		
		
		
		

}
