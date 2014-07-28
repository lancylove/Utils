package com.lancy.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.lancy.utils.applock.ApplockActivity;
import com.lancy.utils.homecode.ComtrolHomeActivity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		startActivity(new Intent(this, ImageActivity.class));//获取图片，裁剪
//		startActivity(new Intent(this, Listviewfilter.class));//listview filter过滤搜索
//		startActivity(new Intent(this, ApplockActivity.class));//应用锁
		startActivity(new Intent(this, ComtrolHomeActivity.class));//home建
		
		
		//ApplockActivity
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
