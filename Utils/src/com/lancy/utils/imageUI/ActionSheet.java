package com.lancy.utils.imageUI;

import java.io.File;

import com.lancy.utils.R;
import com.lancy.utils.R.layout;
import com.lancy.utils.util.FileUtil;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class ActionSheet extends Activity {
	
	private static final int REQUEST_CODE_LOCAL = 0;
	private static final int REQUEST_CODE_CAMERA = 1;
    private File cameraFile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actionsheet);
	}

	/**
	 * 拍照
	 * @param view
	 */
	public void shoot(View view) {
		cameraFile = FileUtil.getFile(FileUtil.otherpath, "tmp.jpg");
		
		    startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)), REQUEST_CODE_CAMERA);
	    
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			if(data == null){
				data = new Intent();
			}
			if(cameraFile != null){
			    setResult(RESULT_OK, data.putExtra("filePath", cameraFile.getAbsolutePath()));
			}else{
			    setResult(RESULT_OK, data.putExtra("isLocal", requestCode == REQUEST_CODE_LOCAL));
			    
			}
		}
		finish();
	}

	/**
	 * 本地相册
	 * @param view
	 */
	public void local(View view) {
	    Intent intent;
	    if (Build.VERSION.SDK_INT < 19) {
		    intent = new Intent(Intent.ACTION_GET_CONTENT);
		    intent.setType("image/*");
	        
	    } else {
	       
		    intent = new Intent(
		        Intent.ACTION_PICK,
		        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	    }
		startActivityForResult(intent, REQUEST_CODE_LOCAL);
	}

	public void cancel(View view) {
		finish();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

}
