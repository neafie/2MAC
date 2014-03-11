package com.mlong.mla;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class CommunityPage1 extends BaseFragment {

	View view;
	private TextView mGotoButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		//setContentView(R.layout.activity_personal_page1);
		view = inflater.inflate(R.layout.activity_community_page1, container, false);
		
		 mGotoButton =   (TextView) view.findViewById(R.id.gotopage2);
	     mGotoButton.setOnClickListener(listener);
		
		return view;
	}
	
	private OnClickListener listener       =   new View.OnClickListener(){
        @Override
        public void onClick(View v){
            /* Go to next fragment in navigation stack*/
            mActivity.pushFragments(AppConstants.TAB_C, new CommunityPage2(),true,true);
        }
    };
	
}
