package com.lancy.utils;

import com.lancy.utils.filter.Listviewfilter;
import com.lancy.utils.imageUI.ImageActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		startActivity(new Intent(this, ImageActivity.class));//获取图片，裁剪
		startActivity(new Intent(this, Listviewfilter.class));//listview filter过滤搜索
		
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
