package com.lancy.utils.util;

import java.io.File;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class IntentUtil {
	
	public static void DeleteApp(Context mc,String packageName){
		//通过程序的包名创建URL  
		Uri packageURI=Uri.parse("package:"+packageName);  
		//创建Intent意图  
		Intent intent=new Intent(Intent.ACTION_DELETE);  
		//设置Uri  
		intent.setData(packageURI);  
		//卸载程序  
		mc.startActivity(intent); 
		
	}
	
	public static void AddApp(Context mc,String apkPath){
		//安装文件apk路径  
//        String fileName=Environment.getExternalStorageDirectory()+"/"+apkName;  
        //创建URI  
        Uri uri=Uri.fromFile(new File(apkPath));  
        //创建Intent意图  
        Intent intent=new Intent(Intent.ACTION_VIEW);  
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//启动新的activity  
        //设置Uri和类型  
        intent.setDataAndType(uri, "application/vnd.android.package-archive");  
        //执行安装  
        mc.startActivity(intent); 
		
	}
	
	public static void StartApp(Context mc){
		 ComponentName componentName=new   ComponentName("xx.yy.zz","xx.yy.zz.xxActivity");   
         Intent intent=new Intent();   
         intent.setComponent(componentName);   
         intent.setAction(Intent.ACTION_VIEW);   
         mc.startActivity(intent); 
	}

}
