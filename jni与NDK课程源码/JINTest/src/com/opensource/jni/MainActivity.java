package com.opensource.jni;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	private static String TAG = "MainActivity";
	private static int saticIntField = 300;

	//加载本地库
	static{
		System.loadLibrary("jin-test");
	}
	
	//本地方法
	//静态方法通过类访问，而不是对象
	public static native JNITest createJNIObject();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.i(TAG, "[java]调用createJNIObject()");
		JNITest jniObject = createJNIObject();
		//call the JNITest对象的方法
		jniObject.cllTest();
	}
}
