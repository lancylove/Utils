package com.lancy.utils.root;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import com.lancy.utils.R;
import com.lancy.utils.util.FileUtil;

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
				
				
					
//					RootUtils.kill("com.justsy.justchat");
					
					File file = new File(FileUtil.path+"/Download/SingleTing2.0.apk");
					RootUtils.slientInstall(file);
					Toast.makeText(RootActivity.this, file.getAbsolutePath(), 0).show();

//				if(RootUtils.slientUninstall("com.up.control")){
//					Toast.makeText(RootActivity.this, "卸载成功", 0).show();
//				}else{
//					Toast.makeText(RootActivity.this, "卸载失败", 0).show();
//				}
				
				
			}
		});
		
		
		
	}

}
