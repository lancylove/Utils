package com.apkstory.service;

import java.text.DecimalFormat;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class Floating extends Service {

    private int statusBarHeight;
    private View view;
    private WindowManager windowManager;
    private LayoutParams layoutParams;
    
    private TextView tv;  
   // private ImageView iv;
    
    int delayTime = 3000;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        
        view = LayoutInflater.from(this).inflate(R.layout.float1, null);  
        tv = (TextView) view.findViewById(R.id.tv);  
      //  iv = (ImageView) view.findViewById(R.id.iv);  
        tv.setText("0KB/s");  
        
        createView();
        handler.postDelayed(task, delayTime);
        startForeground(1111, new Notification());
    }
    
    private Handler handler = new Handler();  
    private Runnable task = new Runnable() {  
        public void run() {
            dataRefresh();  
            handler.postDelayed(this, delayTime);  
            windowManager.updateViewLayout(view, layoutParams);  
        }  
    }; 
    
    private void createView() {
    	
        windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        /*
         * LayoutParams.TYPE_SYSTEM_ERROR：保证该悬浮窗所有View的最上层
         * LayoutParams.FLAG_NOT_FOCUSABLE:该浮动窗不会获得焦点，但可以获得拖动
         * PixelFormat.TRANSPARENT：悬浮窗透明
         */
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
        // layoutParams.gravity = Gravity.RIGHT|Gravity.BOTTOM; //悬浮窗开始在右下角显示
        layoutParams.gravity = Gravity.RIGHT | Gravity.TOP;
        layoutParams.y = 100;
        
        windowManager.addView(view, layoutParams);

        view.setOnTouchListener(new OnTouchListener() {
            float x ;
            float y;
            public boolean onTouch(View v, MotionEvent event) {
                layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: // 按下事件，记录按下时手指在悬浮窗的XY坐标值
                    x = event.getX();
                    y = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    refreshView((int)(event.getRawX()-x), (int)(event.getRawY()-y));
                    break;
                case MotionEvent.ACTION_UP:
                	
                	break;
                }
                return true;
            }
        });
    } 

    public void refreshView(int x, int y) {
        //状态栏高度不能立即取，不然得到的值是0
        if(statusBarHeight == 0){
            View rootView  = view.getRootView();
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            statusBarHeight = r.top;
        }
        layoutParams.x = x;
        layoutParams.y = y - statusBarHeight;
        windowManager.updateViewLayout(view, layoutParams);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        windowManager.removeView(view);
        handler.removeCallbacks(task);
    }
    
    
    public void dataRefresh() {  
        tv.setText(netSpeedCount.getSpeed() + "/s"); 
    }  
    
    NetSpeedCount netSpeedCount = new NetSpeedCount();
    
    class NetSpeedCount {
    	
    	long lastTime;
    	long lastByte;
    	
    	public NetSpeedCount(){
    		lastTime = System.currentTimeMillis();
    		lastByte = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();
    	}
    	
    	public String getSpeed(){
    		long nowByte = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();
    		long nowTime = System.currentTimeMillis();
    		long speed = (nowByte - lastByte)/((nowTime - lastTime)/1000);
    		lastTime = nowTime;
    		lastByte = nowByte;
    		return formatTraffic(speed);
    	}
    	
    	public String formatTraffic(long traffic){
    		final DecimalFormat df = new DecimalFormat("0.0");  
    		String info = "";
    		if(traffic >= 1024*1024){
    			double format = traffic/(double)(1024*1024);
    			info += df.format(format) + " M";
    		}else if(traffic>1024){
    			double format = traffic/(double)(1024);
    			info += df.format(format) + " K";
    		}else{
    			info += traffic + " B";
    		}
    		return info;
    	}
    }
    
}