package com.lancy.utils.devicemanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lancy.utils.R;
import com.lancy.utils.applock.appLockServer;
import com.lancy.utils.homecode.controlHomeServer;

public class DeviceMainActivity extends Activity {

	private Button openDeviceAdminBtn,closeDeviceAdminBtn,lockBtn;
	
	private DevicePolicyManager dpm;
	private ComponentName componentName ;
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deviceadmin);
		mContext = this;
		initView();
		startService(new Intent(this, controlHomeServer.class));
		
	}

	private void initView() {
		openDeviceAdminBtn = (Button) findViewById(R.id.button1);
		closeDeviceAdminBtn = (Button) findViewById(R.id.button2);
		lockBtn = (Button) findViewById(R.id.button3);
		
		//取得系统服务
    	dpm  = (DevicePolicyManager)getSystemService(Context.DEVICE_POLICY_SERVICE);
    	componentName = new ComponentName(this, MyDeviceAdminReceiver.class);
	}
	
	public void open(View v){
		startDeviceManager();
	}
	
	public void close(View v){
		stopDeviceManager();
	}
	public void lock(View v){
		boolean active = dpm.isAdminActive(componentName);
        if (active) {
        	dpm.lockNow();
        }else{
        	Toast.makeText(this, "请先激活设备管理器", 0).show();
        }
		
	}
	
	public void setPasswordLimit(View v){
//		boolean active = dpm.isAdminActive(componentName);
//        if (active) {
////        	dpm.
//        }else{
//        	Toast.makeText(this, "请先激活设备管理器", 0).show();
//        }
	}
	
	public void PasswordTimeOut(View v){
		boolean active = dpm.isAdminActive(componentName);
        if (active) {
        	dpm.setPasswordExpirationTimeout(componentName, 1000*5);
//        	dpm.setMaximumTimeToLock(admin, timeMs)
        	
        }else{
        	Toast.makeText(this, "请先激活设备管理器", 0).show();
        }
	}
	
	public void resetPassword(View v){
		resetPassword("1234");
		
	}
	
	public void clearPassword(View v){
		resetPassword("");
		
	}
	
	public void stopCamera(View v){
		DisabledCamera(true);
	}
	
	public void startCamera(View v){
		DisabledCamera(false);
		
	}
	

	
	
	@SuppressLint("NewApi")
	private boolean DisabledCamera(boolean b){
		boolean active = dpm.isAdminActive(componentName);
        if (active) {
        	dpm.setCameraDisabled(componentName, b);
//        	dpm.set
        	
        }else{
        	Toast.makeText(this, "请先激活设备管理器", 0).show();
        }
        return false;
		
	}
	
	private boolean resetPassword(String password){
		boolean active = dpm.isAdminActive(componentName);
        if (active) {
        	
        	if(dpm.resetPassword(password, dpm.RESET_PASSWORD_REQUIRE_ENTRY)){
        		return true;
        	}
        	
        }else{
        	Toast.makeText(this, "请先激活设备管理器", 0).show();
        }
        return false;
	}
	
	
	
	/**
	 * 启动设备管理权限
	 */
	private void startDeviceManager(){
		//添加一个隐式意图，完成设备权限的添加 
		//这个Intent (DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)跳转到 权限提醒页面
		//并传递了两个参数EXTRA_DEVICE_ADMIN 、 EXTRA_ADD_EXPLANATION
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		
		//权限列表
		//EXTRA_DEVICE_ADMIN参数中说明了用到哪些权限， 
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        
        //描述(additional explanation)
        //EXTRA_ADD_EXPLANATION参数为附加的说明
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "设备管理器测试");
        
        startActivityForResult(intent, 0);
        
		}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case 0:
			Log.i("resultcode", resultCode+"----");
			Toast.makeText(mContext, "resultcode="+resultCode, Toast.LENGTH_LONG).show();
			break;

		default:
			break;
		}
		
	}
	
	
	
	/**
	 * 禁用设备管理权限方法实现
	 */
	private void stopDeviceManager(){
		
		boolean active = dpm.isAdminActive(componentName);
        if (active) {
        	dpm.removeActiveAdmin(componentName);
        }
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		stopService(new Intent(this, appLockServer.class));
	}
	
	
	
	

}
