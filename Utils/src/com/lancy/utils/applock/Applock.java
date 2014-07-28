package com.lancy.utils.applock;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

public class Applock extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ImageView img = new ImageView(this);
		
		setContentView(img);
		img.setBackgroundColor(Color.BLUE);
	
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			return true;
		}
		if(keyCode==KeyEvent.KEYCODE_HOME){
			Toast.makeText(this, "homr", 0).show();
			
		}
		
		return super.onKeyDown(keyCode, event);
	}
	

}
