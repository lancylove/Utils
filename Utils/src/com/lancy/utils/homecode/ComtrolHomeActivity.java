package com.lancy.utils.homecode;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lancy.utils.CommString;
import com.lancy.utils.R;
import com.lancy.utils.applock.appLockServer;

/**
 * 强制锁屏功能
 * @author Lancy
 *
 */
public class ComtrolHomeActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homecontrol);
		startService(new Intent(this, appLockServer.class));
		
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	

	
	public void close(View v){
		Log.i("ComtrolHomeActivity", "锁屏");
		startService(new Intent(this, FloatingService.class));
//		Intent intent = new Intent();  	//获取策略
//	      intent.setAction(CommString.homeReceiver);  
//	      
//	      sendBroadcast(intent); 
		finish();
	
	}
	
	
	

}
