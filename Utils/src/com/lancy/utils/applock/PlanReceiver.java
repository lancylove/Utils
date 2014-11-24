package com.lancy.utils.applock;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/***应用锁，当打开 的不是我们允许的应用时，做相应的操作***/
public class PlanReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("currentrunningpk", getCurrentPk(context));
		
		int type = intent.getIntExtra("type", 0);
		
		 if(type == 0){
			
			//包名为com.up.control的应用不可用
			if(getCurrentPk(context).equals("com.up.control")){
			
			
			 Intent startTaobao = new Intent(context, Applock.class);  
			    startTaobao.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
			    context.startActivity(startTaobao);  
			
		}
			
		}
		
		
	
		
	

		
		
		
	}

	public static String getCurrentPk(Context context){
		// 当前正在运行的应用的包名
		ActivityManager am = (ActivityManager) context.getSystemService("activity");
		String currentrunningpk = am.getRunningTasks(1).get(0).topActivity.getPackageName();
		

		
//		List<ActivityManager.RunningTaskInfo>  list = am.getRunningTasks(10);
		
		
		return currentrunningpk;
	}

}
