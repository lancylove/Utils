package com.lancy.utils.differentlistview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.lancy.utils.R;

public class ScrollTest extends Activity {
    ImageView img;
    Context context;
    Scroller scroller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll);
        img = (ImageView) findViewById(R.id.scroll_img);
        context = this;
        
//        TextView
        
        scroller = new Scroller(context);
        img.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                scroller.forceFinished(true);
                scroller.startScroll(0, 0, 100, 100, 3000);
                img.invalidate();
            }
        });
        
        
    }
    
}
