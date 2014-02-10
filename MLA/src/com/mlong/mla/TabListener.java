package com.mlong.mla;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

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

    
    //if current tag != previous tag don't attach.
    @Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

    	SherlockFragment preInitializedFragment = (SherlockFragment) mActivity.getSupportFragmentManager().findFragmentByTag(mTag);
        if (preInitializedFragment == null) {
            mFragment = (SherlockFragment) Fragment.instantiate(mActivity, mClass.getName());
            ft.add(R.id.realtabcontent, mFragment, mTag);
        } else /*if(preInitializedFragment.getTag().contentEquals(mFragment.getTag())) */{
        	
            ft.attach(preInitializedFragment);
        }
    	
    }

    @Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        SherlockFragment preInitializedFragment = (SherlockFragment) mActivity.getSupportFragmentManager().findFragmentByTag(mTag);

        if (preInitializedFragment != null) {
            ft.detach(preInitializedFragment);
        } else if (mFragment != null) {
            ft.detach(mFragment);
        }
          	
    }

    @Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
    }
}


