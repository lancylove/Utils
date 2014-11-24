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
        // ��ȡWindowManager   
        wm = (WindowManager) getApplicationContext().getSystemService("window");  
        // ����LayoutParams(ȫ�ֱ�������ز���   
        wmParams = new WindowManager.LayoutParams();  
        wmParams.type = 2002;  
        wmParams.flags |= 8;  
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; // �����������������Ͻ�   
        // ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ   
        wmParams.x = 0;  
        wmParams.y = 0;  
        // �����������ڳ�������   
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;  
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;  
        wmParams.format = 1;  
          
        wm.addView(view, wmParams);  
  
        view.setOnTouchListener(new OnTouchListener() {  
            public boolean onTouch(View v, MotionEvent event) {  
                // ��ȡ�����Ļ�����꣬������Ļ���Ͻ�Ϊԭ��   
                x = event.getRawX();  
                y = event.getRawY() - 25; // 25��ϵͳ״̬���ĸ߶�   
                Log.i("currP", "currX" + x + "====currY" + y);// ������Ϣ   
                switch (event.getAction()) {  
                case MotionEvent.ACTION_DOWN:  
                    state = MotionEvent.ACTION_DOWN;  
                    StartX = x;  
                    StartY = y;  
                    // ��ȡ���View�����꣬���Դ�View���Ͻ�Ϊԭ��   
                    mTouchStartX = event.getX();  
                    mTouchStartY = event.getY();  
                    Log.i("startP", "startX" + mTouchStartX + "====startY"  
                            + mTouchStartY);// ������Ϣ   
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
        // ���¸�������λ�ò���   
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
