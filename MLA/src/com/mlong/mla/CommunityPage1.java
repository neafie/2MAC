package com.mlong.mla;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import library.ListFunctions;

public class CommunityPage1 extends BaseFragment {

	View view;
	private TextView mGotoButton, tvDescription, tvName, tvPoints,
			tvCompletionsNeeded, tvCompletions;
	private Button completeButton;

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_LISTS = "lists";
	private static final String TAG_LID = "lid";
	private static final String TAG_UID_CREATOR = "uid_ceator";
	private static final String TAG_LIST_NAME = "list_name";
	private static final String TAG_LIST_DESCRIPTION = "list_description";
	private static final String TAG_CREATED_AT = "created_at";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// setContentView(R.layout.activity_personal_page1);
		view = inflater.inflate(R.layout.activity_community_page1, container,
				false);

		mGotoButton = (TextView) view.findViewById(R.id.gotopage2);
		mGotoButton.setOnClickListener(listener);
		
		completeButton = (Button) view.findViewById(R.id.b_addcomp);
		completeButton.setOnClickListener(listener);
		
		// new MyAsyncTask().execute(2);

		return view;
	}

	private OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			/* Go to next fragment in navigation stack */
			switch(v.getId()) {
			case R.id.gotopage2:
				mActivity.pushFragments(AppConstants.TAB_C, new CommunityPage2(),
										true, true);
				break;
			case R.id.b_addcomp:
				mActivity.pushFragments(AppConstants.TAB_C, new Community_Achievement_Details_Fragment(),
										true, true);
				break;
				
			
			}
			
		}
	};

	private class MyAsyncTask extends AsyncTask<Integer, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(Integer... integers) {
			ListFunctions listFunction = new ListFunctions();
			if (integers.length != 1)
				return null;
			JSONObject json = listFunction.getAchievements(integers[0]);
			Log.d("All achievements: ", json.toString());
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {

		}
	}
}
