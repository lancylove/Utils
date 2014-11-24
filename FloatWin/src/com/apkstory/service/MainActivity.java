package com.apkstory.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startService(View v){
    	startService(new Intent(this, Floating.class));
    }
    
    public void stopService(View v){
    	stopService(new Intent(this, Floating.class));
    }
    
    public void startService2(View v){
    	startService(new Intent(this, FloatService.class));
    }
    
    public void stopService2(View v){
    	stopService(new Intent(this, FloatService.class));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
