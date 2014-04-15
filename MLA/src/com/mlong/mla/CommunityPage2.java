package com.mlong.mla;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class CommunityPage2 extends BaseFragment {

	View view;
	private TextView prevAch;
	private Button completeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.activity_community_page2, container,
				false);
		
		prevAch = (TextView) view.findViewById(R.id.gotopage2);
		prevAch.setOnClickListener(listener);
		
		completeButton = (Button) view.findViewById(R.id.b_addcomp);
		completeButton.setOnClickListener(listener);
		
		return view;
	}

	private OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			/* Go to next fragment in navigation stack */
			switch(v.getId()) {
			case R.id.gotopage2:
				mActivity.pushFragments(AppConstants.TAB_C, new CommunityPage1(),
										true, true);
				break;
			case R.id.b_addcomp:
				mActivity.pushFragments(AppConstants.TAB_C, new Community_Achievement_Details_Fragment2(),
										true, true);
				break;
				
			
			}
			
		}
	};
	
	
}
