package com.lancy.utils.devicemanager;

import com.lancy.utils.CommString;
import com.lancy.utils.applock.appLockServer;
import com.lancy.utils.homecode.FloatingService;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver {
	private String TAG = "MyDeviceAdminReceiver";
	int i=0;
	private DevicePolicyManager dpm;
	private ComponentName componentName ;
	
	@Override
	public IBinder peekService(Context myContext, Intent service) {
		Log.i(TAG, "peekService");
		return super.peekService(myContext, service);
	}
	
	@Override
	public DevicePolicyManager getManager(Context context) {
		Log.i(TAG, "getManager");
		return super.getManager(context);
	}

	@Override
	public ComponentName getWho(Context context) {
		Log.i(TAG, "getWho");
		return super.getWho(context);
	}

	@Override
	public void onEnabled(Context context, Intent intent) {
		Log.i(TAG, "onEnabled");
		super.onEnabled(context, intent);
	}

	@Override
	public CharSequence onDisableRequested(Context context, Intent intent) {
		

		
//		Intent intent1 = new Intent();  	//获取策略
//	      intent1.setAction(CommString.homeReceiver);  
//	      intent1.putExtra("lock",false);
//	      context.sendBroadcast(intent1);
	      
	      Intent startTaobao = new Intent(context, FloatingService.class); 
			context.startService(startTaobao);
			
	      
		return "确定取消？";
	}

	@Override
	public void onDisabled(Context context, Intent intent) {
		Log.i(TAG, "onDisabled");
		super.onDisabled(context, intent);
	}

	@Override
	public void onPasswordChanged(Context context, Intent intent) {
		Log.i(TAG, "onPasswordChanged");
		super.onPasswordChanged(context, intent);
	}

	@Override
	public void onPasswordFailed(Context context, Intent intent) {
		Log.i(TAG, "onPasswordFailed"+(i++));
		super.onPasswordFailed(context, intent);
		
	}

	@Override
	public void onPasswordSucceeded(Context context, Intent intent) {
		Log.i(TAG, "onPasswordSucceeded");
		
		
		super.onPasswordSucceeded(context, intent);
	}

	@Override
	public void onPasswordExpiring(Context context, Intent intent) {
		Log.i(TAG, "onPasswordExpiring");
		super.onPasswordExpiring(context, intent);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "onReceive");
		//取得系统服务
    	dpm  = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
    	componentName = new ComponentName(context, MyDeviceAdminReceiver.class);
		
    	
		
		super.onReceive(context, intent);
	}

	

}
