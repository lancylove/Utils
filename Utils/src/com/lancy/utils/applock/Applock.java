package com.lancy.utils.applock;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

public class Applock extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ImageView img = new ImageView(this);
		 //注册广播，监听home建
		  registerReceiver(mHomeKeyEventReceiver, new IntentFilter(  
	                Intent.ACTION_CLOSE_SYSTEM_DIALOGS));  
		setContentView(img);
		img.setBackgroundColor(Color.BLUE);
	
	}
	
	 /** 
		 * 监听是否点击了home键将客户端推到后台 
		 */  
		private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {  
		    String SYSTEM_REASON = "reason";  
		    String SYSTEM_HOME_KEY = "homekey";  
		    String SYSTEM_HOME_KEY_LONG = "recentapps";  
		       
		    @Override  
		    public void onReceive(Context context, Intent intent) {  
		        String action = intent.getAction();  
//		        Toast.makeText(context, "home1111", 1).show();  
		        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
//		        	Toast.makeText(context, "home222", 1).show();  
		            String reason = intent.getStringExtra(SYSTEM_REASON);  
		            if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {  
		                 //表示按了home键,程序到了后台  
		                Toast.makeText(context, "home", 1).show();  
		               finish();
		                
		                
		            }else if(TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)){  
		                //表示长按home键,显示最近使用的程序列表  
		            }  
		        }   
		    }  
		};  
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			return true;
		}
		
		
		return super.onKeyDown(keyCode, event);
	}
	

}
