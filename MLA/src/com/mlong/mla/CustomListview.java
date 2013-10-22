package com.mlong.mla;

import java.util.ArrayList;

import com.mlong.mla.Items.Item;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListview extends ArrayAdapter<Item> {

	// declaring our ArrayList of items
	private ArrayList<Item> objects;
	static ImageView trophy;

	/* here we must override the constructor for ArrayAdapter
	* the only variable we care about now is ArrayList<Item> objects,
	* because it is the list of objects we want to display.
	*/
	public CustomListview(Context context, int textViewResourceId, ArrayList<Item> objects) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
	}

	/*
	 * we are overriding the getView method here - this is what defines how each
	 * list item will look.
	 */
	public View getView(int position, View convertView, ViewGroup parent){

		// assign the view we are converting to a local variable
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.activity_custom_listview, null);
		}

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 * 
		 * Therefore, i refers to the current Item object.
		 */
		Item i = objects.get(position);

		if (i != null) {

			// This is how you obtain a reference to the TextViews.
			// These TextViews are created in the XML files we defined.

			TextView tt = (TextView) v.findViewById(R.id.toptext);
			TextView mt = (TextView) v.findViewById(R.id.middletext);
			trophy = (ImageView) v.findViewById(R.id.imageView1);
			

			// check to see if each individual textview is null.
			// if not, assign some text!
			if (tt != null){
				tt.setText(i.getName());
			}
			if (mt != null){
				mt.setText(i.getDescription());
			}
			if (i.getcomplete() == true)
			{
				trophy.setImageResource(R.drawable.gold_cup_trophy);
			}
			else
				trophy.setImageResource(R.drawable.trophy_cup_or_goblet_silhouette);	
			
		}

		// the view must be returned to our activity
		return v;
	}

}