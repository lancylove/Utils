package com.lancy.utils.root;

import java.io.DataOutputStream;
import java.io.IOException;

import com.lancy.utils.R;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class RootActivity extends Activity {
	Process p;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);

		setContentView(tv);

		if (RootUtils.is_root()) {
			tv.setText("有root权限");
		} else {
			tv.setText("没有root权限");
		}

		

		tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(RootActivity.this, "---", 0).show();
				
					
					RootUtils.kill("com.justsy.justchat");
//					p = Runtime.getRuntime().exec("su");
//					
//					String packageName="com.justsy.justchat";
//					String cmd = "am force-stop " + packageName + " \n"; 
//					DataOutputStream os = new DataOutputStream(p.getOutputStream());
//					os.writeBytes(cmd);
//					os.writeBytes("exit\n");
//					os.flush();
//					os.close();
				
				
				
			}
		});
		
		
		
	}

}
