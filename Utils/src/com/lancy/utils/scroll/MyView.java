package com.lancy.utils.scroll;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

    Paint mPaint;
    
    
    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyView(Context context) {
        super(context);
        initView();
    }
    
    private void initView(){
        setFocusable(true);
        setFocusableInTouchMode(true);
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        
    }
    
    @Override
    protected Parcelable onSaveInstanceState() {
        
        Parcelable pSaved = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        //dosomething
        return bundle;
    }
    
    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        // TODO Auto-generated method stub
        Bundle bundle = (Bundle) state;
        
        super.onRestoreInstanceState(state);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        Log.i("onSizeChanged", "w="+w+" h="+h+" oldw="+oldw+" oldh="+oldh);
        super.onSizeChanged(w, h, oldw, oldh);
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // TODO Auto-generated method stub
        Log.i("onLayout", "changed?"+changed+"  left="+left+" top="+top+" right="+right+" bottom="+bottom);
        
        super.onLayout(changed, left, top, right, bottom);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        float x = event.getX();
        float y = event.getY();
        float rowX = event.getRawX();
        float rowY = event.getRawY();
        
//        Log.i("onTouchEvent", "x="+x+" y="+y+" rowx="+rowX+" rowy="+rowY);
        
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
           
            break;
        case MotionEvent.ACTION_MOVE:
            
            break;
        case MotionEvent.ACTION_UP:
            
            break;
        default:
            break;
        }
        
        return super.onTouchEvent(event);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        
        Log.i("onDraw", " onDraw");
        canvas.drawRect(0, 0, getWidth()/2, getHeight()/2, mPaint);
        
        super.onDraw(canvas);
    }
    
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
