package com.mlong.mla;

import java.util.ArrayList;

import com.mlong.mla.Items.Item;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;




public class AddAchActivity extends FragmentActivity 
			implements LookDialog.NoticelookDialogListener{
	
	String REMOVE = "";
	int REMOVE2;
	//LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

 // declare class variables
 	private ArrayList<Item> m_parts = new ArrayList<Item>();
 	private CustomListview m_adapter;
 	
 	public final static String MESSAGE = "com.mlong.mla";
	public final static int requestcode = 1;
    
	String myS;
    ListView AList;
	LookDialog mylookDialog = new LookDialog();
	Cursor cursor;
	
	int key;
	EditText name;;
	ACHDatabase myDB = new ACHDatabase(this);
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_ach);
		
		AList=(ListView)findViewById(android.R.id.list);
		AList.setChoiceMode(1);
		
		// instantiate our ItemAdapter class
        m_adapter = new CustomListview(this, R.layout.activity_custom_listview, m_parts);
        AList.setAdapter(m_adapter);

		Intent intent = getIntent();
		myS = intent.getStringExtra("stringkey");
		key = intent.getIntExtra("intkey", 0); 
		
		myDB.open();
		cursor = myDB.achievementQuery(myS, key);
        cursor.moveToFirst();
        String aname;
    	String dname;
        boolean trophy = false;
        // Check if our result was valid.
		if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_ACHNAME)) != null)
                	{
                		Item myitem = new Item();
                		aname = cursor.getString(cursor.getColumnIndex(myDB.COLUMN_ACHNAME));
                		dname = cursor.getString(cursor.getColumnIndex(myDB.COLUMN_DESCRIPTION));
                		trophy = Integer.parseInt(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_ISCOMPLETED)))>0;
                		if(trophy == true)
                		{
                			myitem.setcomplete(true);
                		}
                		myitem.setName(aname);
                		myitem.setDescription(dname);
                		m_parts.add(myitem);
                		m_adapter.notifyDataSetChanged();

                	}
                } while (cursor.moveToNext());
            }
        }
		
		setPointTotal();
		setAchTotal();
		
		cursor.close();
        myDB.close();		
        Log.i("vie","Made It1");
        
        AList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                // TODO Auto-generated method stub
            	
            	Item myitem = new Item();
            	
            	Log.i("vie","Made It2");
            	String desc;
            	int comp;
            	int points;
            	//old list
            	//String myS =(AList.getItemAtPosition(arg2).toString());
            	
            	myitem = m_parts.get(arg2);
            	
            	myS = myitem.getName();
            	
            	REMOVE = myS;
            	REMOVE2 = arg2;
            	
            	
            	ACHDatabase myDB = new ACHDatabase(AddAchActivity.this);
        		myDB.open();
            	cursor = myDB.getpoints(myS);
            	cursor.moveToFirst();
            	
            	Log.i("vie",myS);
            	points = Integer.parseInt(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_POINTS)));
            	
            	cursor = myDB.getnumb(myS);
            	cursor.moveToFirst();
            	
            	comp = Integer.parseInt(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_NUMBERTOCOMP)));
            	
            	
            	cursor = myDB.getdesc(myS);
            	cursor.moveToFirst();
            	
            	desc = cursor.getString(cursor.getColumnIndex(myDB.COLUMN_DESCRIPTION));
            	
            	myDB.close();
            	cursor.close();

            	boolean completed;
            	int ofcomplete;
            	
            	completed = getiscomplete(myS);
            	ofcomplete = getofcomplete(myS);
            	
            	Bundle mybundle = new Bundle();
            	mybundle.putString("namekey", myS);
            	mybundle.putInt("points", points);
            	mybundle.putInt("comp", comp);
            	mybundle.putString("desc", desc);
            	mybundle.putBoolean("iscomp", completed);
            	mybundle.putInt("ofcomp", ofcomplete);
            	
            	mylookDialog.setCancelable(false);
            	mylookDialog.setArguments(mybundle);
            	mylookDialog.show(getSupportFragmentManager(), "Add");
            	
            	
            }
        });
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_ach, menu);
		return true;
	}
	
	public void Delete(View myV)
	{
		ACHDatabase myDB = new ACHDatabase(this);
		myDB.open();
		myDB.delete_list(key);
		myDB.close();
		Intent databackIntent = new Intent();
		
		setResult(MainActivity.RESULT_OK,databackIntent);
		finish();
	}
	

	public void AddAch(View myV)
	{
		Intent otherintent = getIntent();
		key = otherintent.getIntExtra("intkey", 0); 
		Intent intent = new Intent(this, AddListActivity.class);
		intent.putExtra(MESSAGE, key);
		startActivityForResult(intent,requestcode);
	}

	public void onActivityResult(int ReqC, int ResC, Intent data)
	{
		String Name = "";
		String Desc = "";
		Item myitem = new Item();
		
		if(ReqC == requestcode && ResC == RESULT_OK)
		{
			
			Name = data.getStringExtra("namekey");
			Desc = data.getStringExtra("desckey");
			
			//listItems.add(Str);
		    
			myitem.setName(Name);
    		myitem.setDescription(Desc);
    		m_parts.add(myitem);
			m_adapter.notifyDataSetChanged();
			setPointTotal();
		    setAchTotal();
		} 
	}

	@Override
	public void onlookDialogPositiveClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
		ACHDatabase myDB = new ACHDatabase(this);
		myDB.open();
		myDB.delete_achievement(REMOVE);
		myDB.close();
		//listItems.remove(REMOVE2);
		
		m_parts.remove(REMOVE2);
		m_adapter.notifyDataSetChanged();
		
		setPointTotal();
		setAchTotal();
	}
	
	@Override
	public void onlookDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
		//need the myS to be the name from the dialog
		setPointTotal();
		setAchTotal();
		setTrophy(myS);
		
	}
	
	public void setPointTotal()
	{
		int point_total = 0;
		int point = 0;
		int getpoint1;
		int getpoint2;
		String fancy;
		Intent intent = getIntent();
		key = intent.getIntExtra("intkey", 0); 
		
		myDB.open();
		cursor = myDB.getallpoints(key);
        cursor.moveToFirst();
		
        if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_POINTS)) != null)
                	{
                		getpoint1 = Integer.parseInt(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_POINTS)));
                		point_total = point_total + getpoint1;
                	}
                } while (cursor.moveToNext());
            }
        }
        
        myDB.open();
		cursor = myDB.getpoints(key);
        cursor.moveToFirst();
        if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_POINTS)) != null)
                	{
                		getpoint2 = Integer.parseInt(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_POINTS)));
                		point = point + getpoint2;
                	}
                } while (cursor.moveToNext());
            }
        }
        
        fancy = point +"/"+point_total;
        
        TextView tv_point = (TextView) findViewById(R.id.tv_pt_i);
        tv_point.setText(fancy);
		cursor.close();
        myDB.close();
	
	}
	
	public void setAchTotal()
	{
		int ach_total = 0;
		int ach = 0;
		String fancy;
		Intent intent = getIntent();
		key = intent.getIntExtra("intkey", 0);
		
		myDB.open();
		cursor = myDB.getallach(key);
        cursor.moveToFirst();
		
        if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_ACHNAME)) != null)
                	{
                		ach_total++;
                	}
                } while (cursor.moveToNext());
            }
        }
        
        cursor = myDB.getach(key);
        cursor.moveToFirst();
        
        if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_ACHNAME)) != null)
                	{
                		ach++;
                	}
                } while (cursor.moveToNext());
            }
        }
        
        fancy = ach+"/"+ach_total;
        TextView tv_ach = (TextView) findViewById(R.id.tv_at_i);
        tv_ach.setText(fancy);
		cursor.close();
        myDB.close();
	
	}
	
	//update dialog complete
	public boolean getiscomplete(String myS)
	{
		myDB.open();
		cursor = myDB.getiscomp(myS);
        cursor.moveToFirst();
		boolean completed = false;
        
        if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_ISCOMPLETED)) != null)
                	{
                		completed = Integer.parseInt(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_ISCOMPLETED)))>0;
                	}
                } while (cursor.moveToNext()); 
            }
        }
        return completed;
	}
	
	public int getofcomplete(String myS)
	{
		myDB.open();
		cursor = myDB.getofcomp(myS);
        cursor.moveToFirst();
		int ofcompleted = 0;
        
        if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_NUMBEROFCOMP)) != null)
                	{
                		ofcompleted = Integer.parseInt(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_NUMBEROFCOMP)));
                	}
                } while (cursor.moveToNext());
            }
        }
        myDB.close();
        return ofcompleted;
	}
	
	public void setTrophy(String myS)
	{
		myDB.open();
		cursor = myDB.getiscomp(myS);
        cursor.moveToFirst();
		boolean completed = false;
        
        if(cursor != null) {
            if(cursor.getCount() > 0) {
             	
                // Loop through all Results
                do {
                	//can take out if later when add not null to database
                	if(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_ISCOMPLETED)) != null)
                	{
                		completed = Integer.parseInt(cursor.getString(cursor.getColumnIndex(myDB.COLUMN_ISCOMPLETED)))>0;
                	}
                } while (cursor.moveToNext()); 
            }
        }
        if(completed == true)
        {
        	CustomListview.trophy.setImageResource(R.drawable.gold_cup_trophy);
        	//m_adapter.notifyDataSetChanged();
        }
	}
	
	
}