package com.mlong.mla;



import java.util.HashMap;
import java.util.Stack;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Window;
import com.mlong.mla.AddDialog.NoticeDialogListener;

import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
	
	/* Your Tab host */
    private TabHost mTabHost;

    /* A HashMap of stacks, where we use tab identifier as keys..*/
    private HashMap<String, Stack<Fragment>> mStacks;

    /*Save current tabs identifier in this..*/
    private String mCurrentTab;
	
	
    Friend_Lists_Fragment listpageFragment;
    Friend_Add_Achievement_Fragment addachpage;
    Friend_Achievement_Details_Fragment detailspage;
    Friend_Achievement_List_Fragment achlistpage;
    CommunityPage1 communitypage;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	//removes title bar at top
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tab);
     
        mStacks             =   new HashMap<String, Stack<Fragment>>();
        mStacks.put(AppConstants.TAB_P, new Stack<Fragment>());
        mStacks.put(AppConstants.TAB_C, new Stack<Fragment>());
        mStacks.put(AppConstants.TAB_F, new Stack<Fragment>());

        mTabHost                =   (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setOnTabChangedListener(listener);
        mTabHost.setup();
   
        initializeTabs();
        setCurrentTab(1);
        
        //setupTabs(savedInstanceState);
       
    }

    /*private View createTabView(final int id) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
        ImageView imageView =   (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageDrawable(getResources().getDrawable(id));
        return view;
    }*/
    
    public void initializeTabs(){
        /* Setup your tab icons and content views.. Nothing special in this..*/
        TabHost.TabSpec spec    =   mTabHost.newTabSpec(AppConstants.TAB_P);
        mTabHost.setCurrentTab(1);
        
        spec.setContent(new TabHost.TabContentFactory() {
            @Override
			public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        //spec.setIndicator(createTabView(R.drawable.tab_home_state_btn));
        spec.setIndicator("Personal");
        mTabHost.addTab(spec);


        spec                    =   mTabHost.newTabSpec(AppConstants.TAB_C);
        spec.setContent(new TabHost.TabContentFactory() {
            @Override
			public View createTabContent(String tag) {
                return findViewById(R.id.realtabcontent);
            }
        });
        //spec.setIndicator(createTabView(R.drawable.tab_status_state_btn));
        spec.setIndicator("Community");
        mTabHost.addTab(spec);
    
    
    spec                    =   mTabHost.newTabSpec(AppConstants.TAB_F);
    spec.setContent(new TabHost.TabContentFactory() {
        @Override
		public View createTabContent(String tag) {
            return findViewById(R.id.realtabcontent);
        }
    });
    //spec.setIndicator(createTabView(R.drawable.tab_status_state_btn));
    spec.setIndicator("Friends");
    mTabHost.addTab(spec);
    }
    
    /*Comes here when user switch tab, or we do programmatically*/
    TabHost.OnTabChangeListener listener    =   new TabHost.OnTabChangeListener() {
      @Override
	public void onTabChanged(String tabId) {
        /*Set current tab..*/
        mCurrentTab                     =   tabId;
        
        if(mStacks.get(tabId).size() == 0){
          /*
           *    First time this tab is selected. So add first fragment of that tab.
           *    Dont need animation, so that argument is false.
           *    We are adding a new fragment which is not present in stack. So add to stack is true.
           */
          if(tabId.equals(AppConstants.TAB_P)){
            pushFragments(tabId, new PersonalPage1(), false,true);
          }else if(tabId.equals(AppConstants.TAB_C)){
            pushFragments(tabId, new CommunityPage1(), false,true);
          }else if(tabId.equals(AppConstants.TAB_F)){
              pushFragments(tabId, new Friend_Lists_Fragment(), false,true);
          }
        
        }else {
        	
          /*
           *    We are switching tabs, and target tab is already has atleast one fragment. 
           *    No need of animation, no need of stack pushing. Just show the target fragment
           */
          
        	
        	pushFragments(tabId, mStacks.get(tabId).lastElement(), false,false);  
        }
        
      }

    };
    
    /* Might be useful if we want to switch tab programmatically, from inside any of the fragment.*/
    public void setCurrentTab(int val){
          mTabHost.setCurrentTab(val);
    }
    
    /* 
     *      To add fragment to a tab. 
     *  tag             ->  Tab identifier
     *  fragment        ->  Fragment to show, in tab identified by tag
     *  shouldAnimate   ->  should animate transaction. false when we switch tabs, or adding first fragment to a tab
     *                      true when when we are pushing more fragment into navigation stack. 
     *  shouldAdd       ->  Should add to fragment navigation stack (mStacks.get(tag)). false when we are switching tabs (except for the first time)
     *                      true in all other cases.
     */
    public void pushFragments(String tag, Fragment fragment,boolean shouldAnimate, boolean shouldAdd){
      if(shouldAdd)
          mStacks.get(tag).push(fragment);
 
      FragmentManager   manager         =   getSupportFragmentManager();
      FragmentTransaction ft            =   manager.beginTransaction();
      if(shouldAnimate)
          ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
      ft.replace(R.id.realtabcontent, fragment);
      ft.commit();
    }
    
    public void popFragments(){
        /*    
         *    Select the second last fragment in current tab's stack.. 
         *    which will be shown after the fragment transaction given below 
         */
        Fragment fragment             =   mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() - 2);

        /*pop current fragment from stack.. */
        mStacks.get(mCurrentTab).pop();

        /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/
        FragmentManager   manager         =   getSupportFragmentManager();
        FragmentTransaction ft            =   manager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.replace(R.id.realtabcontent, fragment);
        ft.commit();
      }   

    @Override
    public void onBackPressed() {
        if(mStacks.get(mCurrentTab).size() == 1){
          // We are already showing first fragment of current tab, so when back pressed, we will finish this activity..
          finish();
          return;
        }

        /*  Each fragment represent a screen in application (at least in my requirement, just like an activity used to represent a screen). So if I want to do any particular action
         *  when back button is pressed, I can do that inside the fragment itself. For this I used AppBaseFragment, so that each fragment can override onBackPressed() or onActivityResult()
         *  kind of events, and activity can pass it to them. Make sure just do your non navigation (popping) logic in fragment, since popping of fragment is done here itself.
         */
        ((BaseFragment)mStacks.get(mCurrentTab).lastElement()).onBackPressed();

        /* Goto previous fragment in navigation stack of this tab */
            popFragments();
    }
    
    /*
     *   Imagine if you wanted to get an image selected using ImagePicker intent to the fragment. Ofcourse I could have created a public function
     *  in that fragment, and called it from the activity. But couldn't resist myself.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mStacks.get(mCurrentTab).size() == 0){
            return;
        }

        /*Now current fragment on screen gets onActivityResult callback..*/
        mStacks.get(mCurrentTab).lastElement().onActivityResult(requestCode, resultCode, data);
    }
   
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		listpageFragment = (Friend_Lists_Fragment) mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() -1);
		if(listpageFragment != null)
		{
			listpageFragment.positiveClick(dialog);
		}
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		//do nothing
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		addachpage = (Friend_Add_Achievement_Fragment)mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() -1);
		addachpage.myonTimeSet(view, hourOfDay, minute);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		addachpage = (Friend_Add_Achievement_Fragment)mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() -1);
		addachpage.myonDateSet(view, year, monthOfYear, dayOfMonth);
	}
	
	public void myPickDate(View v)
	{
		addachpage = (Friend_Add_Achievement_Fragment)mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() -1);
		addachpage.myPickDate(v);
	}
	
	public void myPickTime(View v)
	{
		addachpage = (Friend_Add_Achievement_Fragment)mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() -1);
		addachpage.myPickTime(v);
	}
    
	public void RadioClick(View v)
	{
		addachpage = (Friend_Add_Achievement_Fragment)mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() -1);
		addachpage.RadioClick(v);
	}
	
	public void onePerson(View v)
	{
		addachpage = (Friend_Add_Achievement_Fragment)mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() -1);
		addachpage.onePerson(v);
	}
	
	public void AddAch(View v)
	{
		addachpage = (Friend_Add_Achievement_Fragment)mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() -1);
		addachpage.AddAch(v);
	}
	
	public void delete(View v)
	{
		detailspage = (Friend_Achievement_Details_Fragment)mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() -1);
		detailspage.delete(v);
	}
	
	public void takePhoto(View v)
	{
		detailspage = (Friend_Achievement_Details_Fragment)mStacks.get(mCurrentTab).elementAt(mStacks.get(mCurrentTab).size() -1);
		detailspage.takePhoto(v);
	}
	
    	
}
