package com.mlong.mla;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class CommunityPage2 extends BaseFragment {

	View view;
	private TextView prevAch;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_community_page2, container,
				false);
		
		prevAch = (TextView) view.findViewById(R.id.gotopage2);
		prevAch.setOnClickListener(listener);
		
		return view;
	}

	private OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			 mActivity.pushFragments(AppConstants.TAB_C, new CommunityPage1(),true,true);
		}
	};
	
	
}
