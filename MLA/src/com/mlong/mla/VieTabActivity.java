package com.mlong.mla;



import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.mlong.mla.AddDialog.NoticeDialogListener;

import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TabHost;
import android.widget.TimePicker;

/**
 * This demonstrates how you can implement switching between the tabs of a
 * TabHost through fragments, using FragmentTabHost.
 */
public class VieTabActivity extends SherlockFragmentActivity implements NoticeDialogListener,
OnDateSetListener,TimePickerDialog.OnTimeSetListener{
	
	TabHost mTabHost;
	
    Friend_Lists_Fragment listpageFragment;
    Friend_Add_Achievement_Fragment addachpage;
    Friend_Achievement_Details_Fragment detailspage;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tab);
        
        
        setupTabs(savedInstanceState);
       
    }

    private void setupTabs(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        Tab tab = actionBar.newTab().setText("Personal").setTabListener(new TabListener(this, "personal", PersonalPage1.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Community").setTabListener(new TabListener(this, "community", CommunityPage1.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab().setText("Friends").setTabListener(new TabListener(this, "friends", Friend_Lists_Fragment.class));
        actionBar.addTab(tab);

        actionBar.setSelectedNavigationItem(1);
        
        
        if (savedInstanceState != null) {
        	
        	int index = savedInstanceState.getInt("index");
            getActionBar().setSelectedNavigationItem(index);
        	
        }
         
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int i = getActionBar().getSelectedNavigationIndex();
        outState.putInt("index", i);
    }
    
    @Override
    public void onBackPressed()
    {
		FragmentManager fm = getSupportFragmentManager();
		
		int selectedIndex = getActionBar().getSelectedNavigationIndex();
		
		boolean isFinish = false;
	
		if (selectedIndex == 2)
		{
			Fragment f = fm.findFragmentByTag("friends");
			
			if (Friend_Lists_Fragment.class.isInstance(f))
			{
				isFinish = true;
			}
		}
		
		if (selectedIndex == 1)
		{
			Fragment c = fm.findFragmentByTag("community");
			
			if(CommunityPage1.class.isInstance(c))
			{
				isFinish = true;
			}
		}
		
		if (selectedIndex == 0)
		{
			Fragment p = fm.findFragmentByTag("personal");
			
			if(PersonalPage1.class.isInstance(p))
			{
				isFinish = true;
			}
		}
		
		if (isFinish)
		{
			finish();
		}
		else
		{
			super.onBackPressed();
		}
    }
   
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		listpageFragment = (Friend_Lists_Fragment)getSupportFragmentManager().findFragmentByTag("friends");
		listpageFragment.positiveClick(dialog);
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		//do nothing
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		addachpage = (Friend_Add_Achievement_Fragment)getSupportFragmentManager().findFragmentByTag("friends");
		addachpage.myonTimeSet(view, hourOfDay, minute);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		addachpage = (Friend_Add_Achievement_Fragment)getSupportFragmentManager().findFragmentByTag("friends");
		addachpage.myonDateSet(view, year, monthOfYear, dayOfMonth);
	}
	
	public void myPickDate(View v)
	{
		addachpage = (Friend_Add_Achievement_Fragment)getSupportFragmentManager().findFragmentByTag("friends");
		addachpage.myPickDate(v);
	}
	
	public void myPickTime(View v)
	{
		addachpage = (Friend_Add_Achievement_Fragment)getSupportFragmentManager().findFragmentByTag("friends");
		addachpage.myPickTime(v);
	}
    
	public void RadioClick(View v)
	{
		addachpage = (Friend_Add_Achievement_Fragment)getSupportFragmentManager().findFragmentByTag("friends");
		addachpage.RadioClick(v);
	}
	
	public void onePerson(View v)
	{
		addachpage = (Friend_Add_Achievement_Fragment)getSupportFragmentManager().findFragmentByTag("friends");
		addachpage.onePerson(v);
	}
	
	public void AddAch(View v)
	{
		addachpage = (Friend_Add_Achievement_Fragment)getSupportFragmentManager().findFragmentByTag("friends");
		addachpage.AddAch(v);
	}
	
	public void delete(View v)
	{
		detailspage = (Friend_Achievement_Details_Fragment)getSupportFragmentManager().findFragmentByTag("friends");
		detailspage.delete(v);
	}
	
	public void takePhoto(View v)
	{
		detailspage = (Friend_Achievement_Details_Fragment)getSupportFragmentManager().findFragmentByTag("friends");
		detailspage.takePhoto(v);
	}
	
}
