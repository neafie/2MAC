package com.mlong.mla;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Color;


public class AddListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_list);
		TableLayout rl = (TableLayout)findViewById(R.id.table);
		rl.setBackgroundColor(Color.parseColor("#C5E3BF"));		
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_list, menu);
		return true;
	}

	public void AddList(View myV)
	{
		
		 final EditText my_name = (EditText) findViewById(R.id.et_aname);
		 final EditText my_desc = (EditText) findViewById(R.id.et_desc);
	     final EditText my_point = (EditText) findViewById(R.id.et_points);
	     final EditText my_numb = (EditText) findViewById(R.id.et_numb);
		
	    
	     if(my_name.getText().toString().equals("") 
					|| my_desc.getText().toString().equals("") || my_point.getText().toString().equals("")
					|| my_numb.getText().toString().equals(""))
			{
				Toast.makeText(this, "You must complete all fields", Toast.LENGTH_SHORT).show(); 
			}
	     else{
	     
	    Intent otherintent = getIntent();
		int key = otherintent.getIntExtra("com.mlong.mla", 0); 
		
    	Bundle mybundle = new Bundle();
        mybundle.putString("namekey",my_name.getText().toString());
        mybundle.putString("desckey",my_desc.getText().toString());
		Intent databackIntent = new Intent();
		databackIntent.putExtras(mybundle);
		setResult(MainActivity.RESULT_OK,databackIntent);
		
		ACHDatabase myDB = new ACHDatabase(this);
		
		myDB.open();
		
		
		myDB.createInsert(key, my_name.getText().toString(), Integer.parseInt(my_point.getText().toString()), null, null, Integer.parseInt(my_numb.getText().toString()), my_desc.getText().toString(), null);
		
		myDB.close();
		
		finish();
	     }
	}
	
}