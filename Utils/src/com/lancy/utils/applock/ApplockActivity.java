package com.lancy.utils.applock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.lancy.utils.CommString;
import com.lancy.utils.R;

public class ApplockActivity extends Activity {
	Button openbtn,closebtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applock);
		openbtn = (Button) findViewById(R.id.open);
		closebtn = (Button) findViewById(R.id.close);
		
		
		openbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();  	//获取策略
			      intent.setAction(CommString.AppLockReceiver);  
			      intent.putExtra("lock",true);
			      intent.putExtra("type",0 );
			      sendBroadcast(intent); 
				finish();
				
			}
		});
		
		
		closebtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();  	//获取策略
			      intent.setAction(CommString.AppLockReceiver);  
			      intent.putExtra("lock",false);
			      intent.putExtra("type",0 );
			      sendBroadcast(intent); 
				
			}
		});
		
//		appLockServer server = new appLockServer();
		
		
		startService(new Intent(this, appLockServer.class));
		
	}

}
