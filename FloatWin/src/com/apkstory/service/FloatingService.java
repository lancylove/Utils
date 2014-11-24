package com.apkstory.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * ������Service �÷�����ں�̨һֱ����һ��������͸���Ĵ��塣
 * @author
 * 
 */
public class FloatingService extends Service {

    private int statusBarHeight;// ״̬���߶�
    private View view;// ͸������
    private boolean viewAdded = false;// ͸�������Ƿ��Ѿ���ʾ
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        
        view = LayoutInflater.from(this).inflate(R.layout.floating, null);
        windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
        /*
         * LayoutParams.TYPE_SYSTEM_ERROR����֤������������View�����ϲ�
         * LayoutParams.FLAG_NOT_FOCUSABLE:�ø����������ý��㣬�����Ի���϶�
         * PixelFormat.TRANSPARENT��������͸��
         */
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, LayoutParams.TYPE_PHONE,
                LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
        // layoutParams.gravity = Gravity.RIGHT|Gravity.BOTTOM; //��������ʼ�����½���ʾ
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;

        view.setOnTouchListener(new OnTouchListener() {
            float x ;
            float y;
            public boolean onTouch(View v, MotionEvent event) {
                layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: // �����¼�����¼����ʱ��ָ����������XY����ֵ
                    x = event.getX();
                    y = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    refreshView((int)(event.getRawX()-x), (int)(event.getRawY()-y));
                    break;
                }
                return true;
            }
        });

    }

    /**
     * ˢ��������
     * @param x �϶����X������   
     * @param y �϶����Y������
     */
    public void refreshView(int x, int y) {
        //״̬���߶Ȳ�������ȡ����Ȼ�õ���ֵ��0
        if(statusBarHeight == 0){
            View rootView  = view.getRootView();
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            statusBarHeight = r.top;
        }
        
        layoutParams.x = x;
        // y���ȥ״̬���ĸ߶ȣ���Ϊ״̬�������û����Ի��Ƶ����򣬲�Ȼ�϶���ʱ���������
        layoutParams.y = y - statusBarHeight;//STATUS_HEIGHT;
        refresh();
    }

    /**
     * ������������߸��������� �����������û�������� ����Ѿ�����������λ��
     */
    private void refresh() {
        if (viewAdded) {
            windowManager.updateViewLayout(view, layoutParams);
        } else {
            windowManager.addView(view, layoutParams);
            viewAdded = true;
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        refresh();
    }

    /**
     * �ر�������
     */
    public void removeView() {
        if (viewAdded) {
            windowManager.removeView(view);
            viewAdded = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeView();
    }
    
    class StatusBarReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            //intent.get
        }
    }
}