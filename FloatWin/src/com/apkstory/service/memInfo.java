package com.apkstory.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.ActivityManager;
import android.content.Context;

public class memInfo {  
          
    public static long getmem_UNUSED(Context mContext) {  
        long MEM_UNUSED;  
        ActivityManager am = (ActivityManager) mContext  
                .getSystemService(Context.ACTIVITY_SERVICE);  
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();  
        am.getMemoryInfo(mi);  
        MEM_UNUSED = mi.availMem / 1024;  
        return MEM_UNUSED;  
    }  
      
    public static long getmem_TOLAL() {  
        long mTotal;  
        // ϵͳ�ڴ�   
        String path = "/proc/meminfo";  
        // �洢������   
        String content = null;  
        BufferedReader br = null;  
        try {  
            br = new BufferedReader(new FileReader(path), 8);  
            String line;  
            if ((line = br.readLine()) != null) {  
                // �ɼ��ڴ���Ϣ   
                content = line;  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (br != null) {  
                try {  
                    br.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        // beginIndex   
        int begin = content.indexOf(':');  
        // endIndex   
        int end = content.indexOf('k');  
        // �ɼ��������ڴ�   
        content = content.substring(begin + 1, end).trim();  
        // ת��ΪInt��   
        mTotal = Integer.parseInt(content);   
        return mTotal;  
    }     
}  
