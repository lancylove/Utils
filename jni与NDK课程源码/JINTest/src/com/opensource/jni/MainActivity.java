package com.opensource.jni;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	private static String TAG = "MainActivity";
	private static int saticIntField = 300;

	//���ر��ؿ�
	static{
		System.loadLibrary("jin-test");
	}
	
	//���ط���
	//��̬����ͨ������ʣ������Ƕ���
	public static native JNITest createJNIObject();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.i(TAG, "[java]����createJNIObject()");
		JNITest jniObject = createJNIObject();
		//call the JNITest����ķ���
		jniObject.cllTest();
	}
}
