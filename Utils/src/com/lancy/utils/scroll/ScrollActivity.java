package com.lancy.utils.scroll;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ScrollActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        View myview = new MyView(this);
        setContentView(myview);
    
    }
    
    
}
