package com.lancy.utils.homecode;

import com.lancy.utils.CommString;
import com.lancy.utils.R;
import com.lancy.utils.applock.appLockServer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class ComtrolHomeActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homecontrol);
		startService(new Intent(this, controlHomeServer.class));
		startService(new Intent(this, appLockServer.class));
		
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopService(new Intent(this, controlHomeServer.class));
		stopService(new Intent(this, appLockServer.class));
		
	}
	

	
	public void close(View v){
		Log.i("ComtrolHomeActivity", "锁屏");
		Intent intent = new Intent();  	//获取策略
	      intent.setAction(CommString.homeReceiver);  
	      intent.putExtra("lock",false);
	      sendBroadcast(intent); 
	      
		
	     
	      intent.setAction(CommString.AppLockReceiver);  
	      intent.putExtra("lock",true);
	      intent.putExtra("type",1 );
	      sendBroadcast(intent); 
	}

}
