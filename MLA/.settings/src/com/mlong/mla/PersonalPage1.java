package com.mlong.mla;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class PersonalPage1 extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		//setContentView(R.layout.activity_personal_page1);
		return inflater.inflate(R.layout.activity_personal_page1, container, false);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.personal_page1, menu);
		return true;
	}*/

}
