package com.lancy.utils.differentlistview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lancy.utils.differentlistview.SlidView.OnSlideListener;

public class SlidActivity extends Activity implements OnSlideListener{
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LinearLayout li = new LinearLayout(this);
        
        final SlidView slidview = new SlidView(this);
        
//        slidview.setButtonText("aaaa");
        TextView tv = new TextView(this);
        tv.setBackgroundColor(Color.BLACK);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 200);
       
        tv.setLayoutParams(lp);
      
        tv.setText("aaaa");
        slidview.setLayoutParams(lp);
        slidview.setContentView(tv);
        slidview.setOnSlideListener(this);
        li.addView(slidview);
        setContentView(li);
        
        slidview.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                slidview.onRequireTouchEvent(event);
                
                
                return true;
            }
        });
        
    
    }

    @Override
    public void onSlide(View view, int status) {
        // TODO Auto-generated method stub
        Log.i("SlidActivity", "status:"+status);
    }

}
