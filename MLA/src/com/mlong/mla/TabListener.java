package com.mlong.mla;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.mlong.mla.AchItems.Item;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class TabListener extends SherlockFragment implements com.actionbarsherlock.app.ActionBar.TabListener {
    private final SherlockFragmentActivity mActivity;
    private final String mTag;
    private final Class mClass;

    private ArrayList<SherlockFragment> fraglist = null;
    
    private SherlockFragment mFragment;

    public TabListener(SherlockFragmentActivity activity, String tag, Class clz) {
        mActivity = activity;
        mTag = tag;
        mClass = clz;
    }

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        
    	Log.i("vie"," Select MAde It!!");
    	/*SherlockFragment preInitializedFragment = (SherlockFragment) mActivity.getSupportFragmentManager().findFragmentByTag(mTag);
        if (preInitializedFragment == null) {
            mFragment = (SherlockFragment) SherlockFragment.instantiate(mActivity, mClass.getName());
            ft.add(R.id.realtabcontent, mFragment, mTag);
        } else {
            ft.attach(preInitializedFragment);
        }*/
    	
    	if (fraglist != null)
    	{
            FragmentManager fm = mActivity.getSupportFragmentManager();
            if(fm != null)
            {
            	Log.i("vie"," fm not null");
            	SherlockFragment showfrag = null;
		    	while(!fraglist.isEmpty())
		    	{
		    		Log.i("vie"," fraglist not empty");
		    		int index = fraglist.size() - 1;
		    		//int index = 0;
		    		
		    		SherlockFragment myfrag = fraglist.get(index);
		    		
		    		
		    		if (showfrag == null)
		    		{
		    			showfrag = myfrag;
		    		}
		    		// push on
	            
	            	//FragmentTransaction tempft = fm.beginTransaction();
        		
	        		//ft.add(myfrag, mTag);
	        		ft.attach(myfrag);
	        		//ft.show(myfrag);
	        		//ft.addToBackStack(arg0)
	        		//ft.addToBackStack(mTag);
	        		

		    		fraglist.remove(index);
		            
		    	}
		    	//ft.commit();
		    	
		    	if (showfrag != null)
		    	{
		    		ft.show(showfrag);
		    	}
            }
    	}
    	else
    	{
    		SherlockFragment preInitializedFragment = (SherlockFragment) mActivity.getSupportFragmentManager().findFragmentByTag(mTag);
            if (preInitializedFragment == null) {
                mFragment = (SherlockFragment) SherlockFragment.instantiate(mActivity, mClass.getName());
                ft.add(R.id.realtabcontent, mFragment, mTag);
            }
    	}
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        /*SherlockFragment preInitializedFragment = (SherlockFragment) mActivity.getSupportFragmentManager().findFragmentByTag(mTag);

        if (preInitializedFragment != null) {
            ft.detach(preInitializedFragment);
        } else if (mFragment != null) {
            ft.detach(mFragment);
        }*/
       
    	if (fraglist == null)	
    	{
    		fraglist = new ArrayList<SherlockFragment>();
    	}
    	
        fraglist.clear();
        FragmentManager fm = mActivity.getSupportFragmentManager();
        
        if(fm != null)
        {
        	Log.i("vie"," Unselect MAde It!!");
        	
        	int counter = 10;
        	
        	SherlockFragment myfrag = (SherlockFragment) fm.findFragmentByTag(mTag+counter);
        	
        	
        	
        	while(counter >= 0)
        	{	
        		

        		
        		if(myfrag != null)
        		{
        			Log.i("vie","Inside If Block");
        			fraglist.add(myfrag);
        		
        		
        		//FragmentTransaction tempft = fm.beginTransaction();
        			ft.detach(myfrag);
        			fm.popBackStackImmediate();
        		//tempft.remove(myfrag);
        		}
        		
        		//tempft.commit();
        		
        		//fm.popBackStackImmediate();
                
        		
        		counter--;
        		
        		if (counter == 0)
        		{
        			myfrag = (SherlockFragment) fm.findFragmentByTag(mTag);   
        		}
        		else
        		{
        			myfrag = (SherlockFragment) fm.findFragmentByTag(mTag+counter);   
        		}
        		Log.i("vie",mTag+counter);
        		
        		
        	}
        	
        }	
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
    }
}


