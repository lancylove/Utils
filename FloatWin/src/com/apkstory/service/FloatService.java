package com.apkstory.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class FloatService extends Service {  
  
    WindowManager wm = null;  
    WindowManager.LayoutParams wmParams = null;  
    View view;  
    private float mTouchStartX;  
    private float mTouchStartY;  
    private float x; 
    private float y;  
    int state;  
    TextView tx1;  
    TextView tx;  
    ImageView iv;  
    private float StartX;  
    private float StartY;  
    int delaytime=1000;  
    
    @Override  
    public void onCreate() {  
        Log.d("FloatService", "onCreate");  
        super.onCreate();  
        
        view = LayoutInflater.from(this).inflate(R.layout.floating2, null);  
        tx = (TextView) view.findViewById(R.id.memunused);  
        tx1 = (TextView) view.findViewById(R.id.memtotal);  
        tx.setText("" + memInfo.getmem_UNUSED(this) + "KB");  
        tx1.setText("" + memInfo.getmem_TOLAL() + "KB");  
        iv = (ImageView) view.findViewById(R.id.iv);  
        iv.setVisibility(View.GONE);  
        createView();  
        handler.postDelayed(task, delaytime);  
    }  
  
    private void createView() {  
        // 获取WindowManager   
        wm = (WindowManager) getApplicationContext().getSystemService("window");  
        // 设置LayoutParams(全局变量）相关参数   
        wmParams = new WindowManager.LayoutParams();  
        wmParams.type = 2002;  
        wmParams.flags |= 8;  
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角   
        // 以屏幕左上角为原点，设置x、y初始值   
        wmParams.x = 0;  
        wmParams.y = 0;  
        // 设置悬浮窗口长宽数据   
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;  
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;  
        wmParams.format = 1;  
          
        wm.addView(view, wmParams);  
  
        view.setOnTouchListener(new OnTouchListener() {  
            public boolean onTouch(View v, MotionEvent event) {  
                // 获取相对屏幕的坐标，即以屏幕左上角为原点   
                x = event.getRawX();  
                y = event.getRawY() - 25; // 25是系统状态栏的高度   
                Log.i("currP", "currX" + x + "====currY" + y);// 调试信息   
                switch (event.getAction()) {  
                case MotionEvent.ACTION_DOWN:  
                    state = MotionEvent.ACTION_DOWN;  
                    StartX = x;  
                    StartY = y;  
                    // 获取相对View的坐标，即以此View左上角为原点   
                    mTouchStartX = event.getX();  
                    mTouchStartY = event.getY();  
                    Log.i("startP", "startX" + mTouchStartX + "====startY"  
                            + mTouchStartY);// 调试信息   
                    break;  
                case MotionEvent.ACTION_MOVE:  
                    state = MotionEvent.ACTION_MOVE;  
                    updateViewPosition();  
                    break;  
  
                case MotionEvent.ACTION_UP:  
                    state = MotionEvent.ACTION_UP;  
  
                    updateViewPosition();  
                    showImg();  
                    mTouchStartX = mTouchStartY = 0;  
                    break;  
                }  
                return true;  
            }  
        });  
  
        iv.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) { 
                Intent serviceStop = new Intent();  
                serviceStop.setClass(FloatService.this, FloatService.class);  
                stopService(serviceStop);  
            }  
        });  
  
    }  
  
    public void showImg() {  
        if (Math.abs(x - StartX) < 1.5 && Math.abs(y - StartY) < 1.5  
                && !iv.isShown()) {  
            iv.setVisibility(View.VISIBLE);  
        } else if (iv.isShown()) {  
            iv.setVisibility(View.GONE);  
        }  
    }  
  
    private Handler handler = new Handler();  
    private Runnable task = new Runnable() {  
        public void run() {
            dataRefresh();  
            handler.postDelayed(this, delaytime);  
            wm.updateViewLayout(view, wmParams);  
        }  
    };  
  
    public void dataRefresh() {  
        tx.setText("" + memInfo.getmem_UNUSED(this) + "KB");  
        tx1.setText("" + memInfo.getmem_TOLAL() + "KB");  
    }  
  
    private void updateViewPosition() {  
        // 更新浮动窗口位置参数   
        wmParams.x = (int) (x - mTouchStartX);  
        wmParams.y = (int) (y - mTouchStartY);  
        wm.updateViewLayout(view, wmParams);  
    }  
  
    @Override  
    public void onStart(Intent intent, int startId) {  
        Log.d("FloatService", "onStart");
        //setForeground(true);  
        startForeground(11, new Notification());
    }  
  
    @Override  
    public void onDestroy() {  
        handler.removeCallbacks(task);  
        Log.d("FloatService", "onDestroy");  
        wm.removeView(view);  
        super.onDestroy();  
    }  
  
    @Override  
    public IBinder onBind(Intent intent) {  
        return null;  
    }     
}  
