package com.lancy.utils.differentlistview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.lancy.utils.R;

public class ListviewActivity extends Activity {
	
	private ListView listview;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.differentlistview);
		listview = (ListView) findViewById(R.id.differentlistview);
		
		
	}
	
	
	
	

}
