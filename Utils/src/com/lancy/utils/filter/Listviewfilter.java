package com.lancy.utils.filter;

import java.util.ArrayList;

import com.lancy.utils.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Listviewfilter extends Activity {

	ListView listview;
	EditText edittext;
	filterAdapter adapter;
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewfilter);
		listview = (ListView) findViewById(R.id.listView1);
		edittext = (EditText) findViewById(R.id.editText1);
//		ArrayList<String> list = new ArrayList<String>();
//		list.add("lancy");
//		list.add("admin");
//		list.add("wolo");
//		list.add("haly");
//		list.add("lucy");
		ArrayList<User> list = new ArrayList<User>();
		list.add(new User("lancy", 1, "male", 29));
		list.add(new User("kelo", 2, "female", 28));
		list.add(new User("admin", 3, "male", 28));
		list.add(new User("lucy", 4, "female", 23));
		
		
		
		
		adapter = new filterAdapter(this,R.layout.listviewitem, list);
		listview.setAdapter(adapter);
		edittext.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
					adapter.getFilter().filter(s);
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
//		 registerForContextMenu(listview);
	}

}
