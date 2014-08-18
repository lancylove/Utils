package com.lancy.utils.homecode;

import com.lancy.utils.CommString;
import com.lancy.utils.R;
import com.lancy.utils.applock.Applock;
import com.lancy.utils.applock.PlanReceiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

public class controlHomeServer extends Service {
	homelockReceiver mhomelockReceiver;
	private String LOGTAG = "controlHomeServer";
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		
		
		mhomelockReceiver = new homelockReceiver();				// 应用锁
		IntentFilter appLockFilter = new IntentFilter();
		appLockFilter.addAction(CommString.homeReceiver);
		registerReceiver(mhomelockReceiver, appLockFilter);
		Log.i(LOGTAG, "home   开启");
	
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		unregisterReceiver(mhomelockReceiver);
		super.onDestroy();
	}
	
	/**
	 * 
	 *home建
	 * @author user
	 * 
	 */
	private class homelockReceiver extends BroadcastReceiver { 	// 应用锁
		@Override
		public void onReceive(Context context, Intent intent) {
			Boolean lock =true;
			lock = intent.getBooleanExtra("lock", true);
			
			if(homelock.lockLayer!=null)
				homelock.control(lock);
			else{
				 Intent startTaobao = new Intent(context, homelock.class).putExtra("lock", lock);  
			    startTaobao.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
			    context.startActivity(startTaobao); 
			}
			
		}
	}
	
	private void updateforbiddenpklist(Boolean enable) {
		
		
			startActivity(new Intent(this, homelock.class).putExtra("lock", enable));
			
		
		
//		AlarmManager am = getAm();
//		long now = System.currentTimeMillis();
//		Intent intent = new Intent(this, ControlhomeReceiver.class);
//		PendingIntent tpr = PendingIntent.getBroadcast(this, CommString.ALARM_HOMECODE, intent,
//				Intent.FLAG_ACTIVITY_NEW_TASK);
//		if(enable){
//			am.setInexactRepeating(AlarmManager.RTC_WAKEUP, now,1000, tpr);   //设置闹钟
//		}
//		else{
//			am.cancel(tpr);  //取消闹钟
//		}
		
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
