package com.lancy.utils.applock;

import com.lancy.utils.CommString;
import com.lancy.utils.homecode.FloatingService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;


public class appLockServer  extends Service{
	private String LOGTAG = "appLockServer";
	private AppLockReceiver appLockReceiver;
	@Override
		public void onCreate() {
			// TODO Auto-generated method stub
			super.onCreate();
			appLockReceiver = new AppLockReceiver();				// 应用锁
			IntentFilter appLockFilter = new IntentFilter();
			appLockFilter.addAction(CommString.AppLockReceiver);
			appLockFilter.addAction(CommString.homeReceiver);
			registerReceiver(appLockReceiver, appLockFilter);
			
			
			
	}

	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		unregisterReceiver(appLockReceiver);
		super.onDestroy();
	}
	
	
	/**
	 * 
	 *应用锁开关
	 * @author user
	 * 
	 */
	private class AppLockReceiver extends BroadcastReceiver { 	// 应用锁
		@Override
		public void onReceive(Context context, Intent intent) {
			
			Boolean lock =true;
			if(intent.getAction().equals(CommString.AppLockReceiver)){
				lock = intent.getBooleanExtra("lock", true);
				int type = intent.getIntExtra("type", 0);
				Log.i(LOGTAG, "应用锁   lock： "+lock);
				updateforbiddenpklist(lock,type);						//打开或关闭应用锁
			}else if(intent.getAction().equals(CommString.homeReceiver)){
				
				Log.i(LOGTAG, "屏幕锁   lock： ");
			}
			
		}
	}
	
	private void updateforbiddenpklist(Boolean enable,int type) {
		AlarmManager am = getAm();
		long now = System.currentTimeMillis();
		Intent intent = new Intent(this, PlanReceiver.class);
		intent.putExtra("type",type );

		PendingIntent tpr = PendingIntent.getBroadcast(this, CommString.ALARM_FORBIDTIMEPLANAPP_RQC, intent,
				Intent.FLAG_ACTIVITY_NEW_TASK);
		if(enable){
			am.setInexactRepeating(AlarmManager.RTC_WAKEUP, now,1000, tpr);   //设置闹钟
		}
		else{
			am.cancel(tpr);  //取消闹钟
			stopSelf();
			Log.i("ComtrolHomeActivity", "应用锁 关闭服务！");
		}
		
	}
	private AlarmManager getAm() {
		return (AlarmManager) getSystemService(ALARM_SERVICE);
	}
	

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
