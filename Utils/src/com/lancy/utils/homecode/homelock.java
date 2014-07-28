package com.lancy.utils.homecode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lancy.utils.CommString;
import com.lancy.utils.R;

public class homelock extends Activity {
	View lock;
	public static LockLayer lockLayer;
	public static Activity mContext;
	Boolean enable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		enable = getIntent().getBooleanExtra("lock", true);
		
		lock = View.inflate(this, R.layout.homecontrol, null); 
		mContext = this;
		lockLayer = LockLayer.getInstance(this);
		lockLayer.setLockView(lock);  
		if(enable){
			lockLayer.unlock(); 
			finish();
		}else{
			lockLayer.lock();
		}
		
	}

	
	public static void control(boolean enale){
		if(enale){
			lockLayer.unlock(); 
			 mContext.finish();
		}else{
			lockLayer.lock();
		}
		
	}
	
	
public void open(View v){
		
		Intent intent = new Intent();  	//获取策略
	      intent.setAction(CommString.homeReceiver);  
	      intent.putExtra("lock",true);
	      sendBroadcast(intent); 
	      
	      intent.setAction(CommString.AppLockReceiver);  
	      intent.putExtra("lock",false);
	      intent.putExtra("type",1 );
	      sendBroadcast(intent); 
		
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
