package com.lancy.utils.deleteSelf;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DeleteSelfActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Button btn = new Button(this);
		
		setContentView(btn);
		btn.setText("delete myself");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//通过程序的包名创建URL  
				Uri packageURI=Uri.parse("package:com.lancy.utils");  
				//创建Intent意图  
				Intent intent=new Intent(Intent.ACTION_DELETE);  
				//设置Uri  
				intent.setData(packageURI);  
				//卸载程序  
				startActivity(intent); 
				finish();
				
			}
		});
		
		
	}
	
	
}
