package com.mlong.mla;

import com.actionbarsherlock.app.SherlockFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class BaseFragment extends SherlockFragment {

	VieTabActivity mActivity;

	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      mActivity     =   (VieTabActivity) this.getActivity();
	    }

	  public boolean onBackPressed(){
		  return false;
	  }

	  public void onActivityResult(int requestCode, int resultCode, Intent data){
	  }
}