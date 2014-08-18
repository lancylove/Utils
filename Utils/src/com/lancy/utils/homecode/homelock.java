package com.lancy.utils.homecode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lancy.utils.CommString;
import com.lancy.utils.R;

public class homelock extends Activity {
	View lock;
	public static LockLayer lockLayer;
	public static Activity mContext;
	Boolean enable;
	Button btn;
	
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
		btn = (Button) lock.findViewById(R.id.close);
		btn.setText("解锁");
		
		
	}

	
	public static void control(boolean enale){
		if(enale){
			lockLayer.unlock(); 
			 mContext.finish();
			 //返回主界面
//			 Intent i = new Intent(Intent.ACTION_MAIN);  
//			  i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//			  i.addCategory(Intent.CATEGORY_HOME);  
//			  startActivity(i);
			 
		}else{
			lockLayer.lock();
		}
		
	}
	
	
public void close(View v){
		
		Intent intent = new Intent();  	//获取策略
	      intent.setAction(CommString.homeReceiver);  
	      intent.putExtra("lock",true);
	      sendBroadcast(intent); 
	      
	      intent.setAction(CommString.AppLockReceiver);  
	      intent.putExtra("lock",false);
	      intent.putExtra("type",1 );
	      sendBroadcast(intent); 
		
	}
	

	
	
}
