package com.opensource.jni;

import android.util.Log;

public class JNITest {
	
	private static String TAG = "JNITest";
	
	private int num;

	public JNITest(int num) {
		// TODO Auto-generated constructor stub
		this.num = num;
		Log.i(TAG, "[java]调用JNITest对象的构造方法：num="+this.num);
	}
	
	public int callByNative(int num){
		Log.i(TAG, "[java]调用JNITest对象的callByNative方法：num="+num);
		return num;
	}

	public void cllTest(){
		Log.i(TAG, "[java]调用JNITest对象的cllTest方法：num="+num);
	}
}
