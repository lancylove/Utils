package com.lancy.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.lancy.utils.scroll.ScrollActivity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		startActivity(new Intent(this, ImageActivity.class));//获取图片，裁剪
//		startActivity(new Intent(this, Listviewfilter.class));//listview filter过滤搜索
//		startActivity(new Intent(this, ApplockActivity.class));//应用锁
//		startActivity(new Intent(this, ComtrolHomeActivity.class));//home建自定义锁屏
//		startActivity(new Intent(this, RootActivity.class));//root相关
//		startActivity(new Intent(this, DeviceMainActivity.class));//设备管理器相关
//		startActivity(new Intent(this, DeleteSelfActivity.class));//卸载本身
//		startActivity(new Intent(this, GetmacipinfoActivity.class));//wifi
//		startActivity(new Intent(this, AnimationActivity.class));//二维码动画
//		startActivity(new Intent(this, SlidActivity.class));//滑动删除
		startActivity(new Intent(this, ScrollActivity.class));
		
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
