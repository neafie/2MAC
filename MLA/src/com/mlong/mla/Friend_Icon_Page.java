package com.mlong.mla;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class Friend_Icon_Page extends BaseFragment {


	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_friend__icon__page,
				container, false);
		
		ImageView cup = (ImageView) view.findViewById(R.id.icon_cup);
		ImageView fish = (ImageView) view.findViewById(R.id.icon_fish);
		ImageView footprints = (ImageView) view.findViewById(R.id.icon_footprints);
		ImageView fork = (ImageView) view.findViewById(R.id.icon_fork);
		ImageView hammer = (ImageView) view.findViewById(R.id.icon_hammer);
		ImageView pretzel = (ImageView) view.findViewById(R.id.icon_pretzel);
		ImageView quill = (ImageView) view.findViewById(R.id.icon_quill);
		ImageView rocket = (ImageView) view.findViewById(R.id.icon_rocket);
		ImageView spaceship = (ImageView) view.findViewById(R.id.icon_spaceship);
		ImageView tree = (ImageView) view.findViewById(R.id.icon_tree);
		ImageView turtle = (ImageView) view.findViewById(R.id.icon_turtle);
		cup.setOnClickListener(cuplistener);
		fish.setOnClickListener(fishlistener);
		footprints.setOnClickListener(footprintslistener);
		fork.setOnClickListener(forklistener);
		hammer.setOnClickListener(hammerlistener);
		pretzel.setOnClickListener(pretzellistener);
		quill.setOnClickListener(quilllistener);
		rocket.setOnClickListener(rocketlistener);
		spaceship.setOnClickListener(spaceshiplistener);
		tree.setOnClickListener(treelistener);
		turtle.setOnClickListener(turtlelistener);
		
		return view;
	}
	
	private OnClickListener cuplistener       =   new View.OnClickListener(){
        @Override
        public void onClick(View v){
        	
            AppConstants.icon = "cup_";
            mActivity.onBackPressed(); 
        }
    };
    
    private OnClickListener fishlistener       =   new View.OnClickListener(){
        @Override
        public void onClick(View v){
        	AppConstants.icon = "fish_";
        	mActivity.onBackPressed(); 
        }
    };
    
    private OnClickListener footprintslistener       =   new View.OnClickListener(){
        @Override
        public void onClick(View v){
        	AppConstants.icon = "footprints_";
        	mActivity.onBackPressed(); 
        }
    };
    
    private OnClickListener forklistener       =   new View.OnClickListener(){
        @Override
        public void onClick(View v){
        	AppConstants.icon = "fork_";
        	mActivity.onBackPressed(); 
        }
    };
    
    private OnClickListener hammerlistener       =   new View.OnClickListener(){
        @Override
        public void onClick(View v){
        	AppConstants.icon = "hammer_";
        	mActivity.onBackPressed(); 
        }
    };
    
    private OnClickListener pretzellistener       =   new View.OnClickListener(){
        @Override
        public void onClick(View v){
        	AppConstants.icon = "pretzel_";
        	mActivity.onBackPressed(); 
        }
    };
    
    private OnClickListener quilllistener       =   new View.OnClickListener(){
        @Override
        public void onClick(View v){
        	AppConstants.icon = "quill_";
        	mActivity.onBackPressed(); 
        }
    };

    private OnClickListener rocketlistener       =   new View.OnClickListener(){
        @Override
        public void onClick(View v){
        	AppConstants.icon = "rocket_";
        	mActivity.onBackPressed(); 
        }
    };
    
    private OnClickListener spaceshiplistener       =   new View.OnClickListener(){
        @Override
        public void onClick(View v){
        	AppConstants.icon = "spaceship_";
        	mActivity.onBackPressed(); 
        }
    };
    
    private OnClickListener treelistener       =   new View.OnClickListener(){
        @Override
        public void onClick(View v){
        	AppConstants.icon = "tree_";
        	mActivity.onBackPressed(); 
        }
    };
    
    private OnClickListener turtlelistener       =   new View.OnClickListener(){
        @Override
        public void onClick(View v){
        	AppConstants.icon = "turtle_";
        	mActivity.onBackPressed(); 
        }
    };
}
