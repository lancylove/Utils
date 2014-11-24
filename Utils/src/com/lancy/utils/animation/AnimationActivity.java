package com.lancy.utils.animation;

import com.lancy.utils.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class AnimationActivity extends Activity {
    private static final int MIN_FRAME_WIDTH = 240;
    private static final int MIN_FRAME_HEIGHT = 240;
    private static final int MAX_FRAME_WIDTH = 640;
    private static final int MAX_FRAME_HEIGHT = 640;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrtest);
       
        //
        

    }
    
    
    
    /**
     * 模拟二维码中间那条线
     */
    private void qr_line(){
        ImageView image = (ImageView) findViewById(R.id.imageView1);
        image.setBackgroundResource(R.drawable.qrcode_scan_line);
       
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        int width = displayWidth * 3 / 5;
        if (width < MIN_FRAME_WIDTH) {
            width = MIN_FRAME_WIDTH;
        } else if (width > MAX_FRAME_WIDTH) {
            width = MAX_FRAME_WIDTH;
        }
        float fromXDelta = (displayWidth - width) / 2;
        float fromYDelta = (displayHeight - width) / 2;
        float toYDelta = (displayHeight + width) / 2 - 40;

        TranslateAnimation tra = new TranslateAnimation(fromXDelta, fromXDelta, fromYDelta, toYDelta);
        tra.setInterpolator(this, android.R.anim.anticipate_overshoot_interpolator);
        tra.setDuration(3000);
        tra.setRepeatCount(Animation.INFINITE);
        tra.setRepeatMode(Animation.RESTART);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(tra);

        LayoutParams lp = image.getLayoutParams();
        lp.width = width;
        image.setLayoutParams(lp);
        image.startAnimation(set);
    }

}
