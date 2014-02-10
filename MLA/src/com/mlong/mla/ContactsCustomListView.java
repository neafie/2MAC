package com.mlong.mla;

import java.util.ArrayList;

import com.mlong.mla.ContactItems.ContactItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ContactsCustomListView extends ArrayAdapter<ContactItem> {

	// declaring our ArrayList of items
	private ArrayList<ContactItem> objects;
	static ImageView trophy;

	/* here we must override the constructor for ArrayAdapter
	* the only variable we care about now is ArrayList<Item> objects,
	* because it is the list of objects we want to display.
	*/
	public ContactsCustomListView(Context context, int textViewResourceId, ArrayList<ContactItem> objects) {
		super(context, textViewResourceId, objects);
		this.objects = objects;
	}

	/*
	 * we are overriding the getView method here - this is what defines how each
	 * list item will look.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent){

		// assign the view we are converting to a local variable
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.contacts_listview, null);
		}

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 * 
		 * Therefore, i refers to the current Item object.
		 */
		ContactItem i = objects.get(position);

		if (i != null) {

			// This is how you obtain a reference to the TextViews.
			// These TextViews are created in the XML files we defined.

			TextView tt = (TextView) v.findViewById(R.id.listtoptext);
			TextView mt = (TextView) v.findViewById(R.id.listmiddletext);

			// check to see if each individual textview is null.
			// if not, assign some text!
			if (tt != null){
				tt.setText(i.getName());
			}
			if (mt != null){
				mt.setText(i.getPhonenumber());
			}
		}
		 
		 ContactItem pos = getselectedposition(position);
		 CheckBox chkbox = (CheckBox) v.findViewById(R.id.checkBox1);
         chkbox.setOnCheckedChangeListener(myCheckChangList);
         chkbox.setTag(position);
         chkbox.setChecked(pos.ischeckedflag);

		// the view must be returned to our activity
		return v;
	}
	

	ContactItem getselectedposition(int position) {
        return (getItem(position));
}
	 
	ArrayList<ContactItem> getcheckedposition() {
         ArrayList<ContactItem> checkedposition = new ArrayList<ContactItem>();
         for (ContactItem p : objects) {
                 if (p.ischeckedflag)
                         checkedposition.add(p);
         }
         return checkedposition;
 }

 OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
         @Override
		public void onCheckedChanged(CompoundButton buttonView,
                         boolean isChecked) {
                 getselectedposition((Integer) buttonView.getTag()).ischeckedflag = isChecked;
         }
 };

}