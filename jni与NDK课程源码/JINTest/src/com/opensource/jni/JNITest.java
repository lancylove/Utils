package com.opensource.jni;

import android.util.Log;

public class JNITest {
	
	private static String TAG = "JNITest";
	
	private int num;

	public JNITest(int num) {
		// TODO Auto-generated constructor stub
		this.num = num;
		Log.i(TAG, "[java]����JNITest����Ĺ��췽����num="+this.num);
	}
	
	public int callByNative(int num){
		Log.i(TAG, "[java]����JNITest�����callByNative������num="+num);
		return num;
	}

	public void cllTest(){
		Log.i(TAG, "[java]����JNITest�����cllTest������num="+num);
	}
}
