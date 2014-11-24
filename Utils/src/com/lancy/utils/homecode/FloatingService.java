package com.lancy.utils.homecode;

import com.lancy.utils.R;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

public class FloatingService extends Service {

	private int statusBarHeight;// 状态栏高度  
    private View view;// 窗体  
    private Button btn;
    
    private boolean viewAdded = false;// 透明窗体是否已经显示  
    private WindowManager windowManager;  
    private WindowManager.LayoutParams layoutParams;
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override  
    public void onCreate() {  
        super.onCreate();  
        Toast.makeText(getApplicationContext(), "start...", 0).show();
        view = LayoutInflater.from(this).inflate(R.layout.homecontrol, null);  
      //注册广播，监听home建
		  registerReceiver(mHomeKeyEventReceiver, new IntentFilter(  
	                Intent.ACTION_CLOSE_SYSTEM_DIALOGS));  
        windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);  
        /* 
         * LayoutParams.TYPE_SYSTEM_ERROR：保证该悬浮窗所有View的最上层 
         * LayoutParams.FLAG_NOT_FOCUSABLE:该浮动窗不会获得焦点，但可以获得拖动 
         * PixelFormat.TRANSPARENT：悬浮窗透明 
         */  
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,  
        		LayoutParams.MATCH_PARENT, LayoutParams.TYPE_SYSTEM_ERROR,  
                LayoutParams.FLAG_FULLSCREEN, PixelFormat.TRANSPARENT);  
        // layoutParams.gravity = Gravity.RIGHT|Gravity.BOTTOM; //悬浮窗开始在右下角显示  
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP; 
       layoutParams.systemUiVisibility = View.VISIBLE;
        btn = (Button) view.findViewById(R.id.close);
        btn.setText("点此解锁");
        btn .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "onkeydown", 0).show();
				
				
				
				removeView();
				unregisterReceiver(mHomeKeyEventReceiver);
				stopSelf();
			}
		});
        
        
        
        
       
  
    }  

    /** 
     * 添加悬浮窗或者更新悬浮窗 如果悬浮窗还没添加则添加 如果已经添加则更新其位置 
     */  
    private void refresh() { 
    	Toast.makeText(getApplicationContext(), "启动锁屏界面", 0).show();
        if (viewAdded) {  
            windowManager.updateViewLayout(view, layoutParams);  
        } else {  
            windowManager.addView(view, layoutParams);  
            viewAdded = true;  
        }  
    }  
    
    
  
    @Override  
    public void onStart(Intent intent, int startId) {  
        super.onStart(intent, startId);  
       refresh();
    }  
  
    /** 
     * 关闭悬浮窗 
     */  
    private void removeView() { 
    	Toast.makeText(getApplicationContext(), "关闭锁屏界面", 0).show();
        if (viewAdded) {  
            windowManager.removeView(view);  
            viewAdded = false;  
        }  
        
    }  
  
    @Override  
    public void onDestroy() {  
        super.onDestroy();  
      
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
//	        Toast.makeText(context, "home1111", 1).show();  
	        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
//	        	Toast.makeText(context, "home222", 1).show();  
	            String reason = intent.getStringExtra(SYSTEM_REASON);  
	            if (TextUtils.equals(reason, SYSTEM_HOME_KEY)) {  
	                 //表示按了home键,程序到了后台  
	                Toast.makeText(context, "home", 1).show();  
	                if(!getCurrentPk(context).equals("com.lancy.util.singleapp")){
	    				Log.i("currentrunningpk", getCurrentPk(context));
//	    				Intent startTaobao = new Intent(context, HomeLockActivity.class);  
//	    				 startTaobao.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
//	    				 context.startActivity(startTaobao);
	    				removeView();
	    				startService(new Intent(context, FloatingService.class));
	    				
	    			}
	                
	                
	            }else if(TextUtils.equals(reason, SYSTEM_HOME_KEY_LONG)){  
	                //表示长按home键,显示最近使用的程序列表  
	            }  
	        }   
	    }  
	};  
	
	
	public static String getCurrentPk(Context context){
		// 当前正在运行的应用的包名
		ActivityManager am = (ActivityManager) context.getSystemService("activity");
		String currentrunningpk = am.getRunningTasks(1).get(0).topActivity.getPackageName();
		
		return currentrunningpk;
	}

}
