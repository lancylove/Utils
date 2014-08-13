package com.lancy.utils.root;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class RootUtils {
	
	
	 private static Process process;  
	  
	    /** 
	     * 结束进程,执行操作调用即可 
	     */  
	    public static void kill(String packageName) {  
	        initProcess();  
	        killProcess(packageName);  
	        close();  
	    }  
	  
	    /** 
	     * 初始化进程 
	     */  
	    private static void initProcess() {  
	        if (process == null)  
	            try {  		
	                process = Runtime.getRuntime().exec("su");  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	    }  
	  
	    /** 
	     * 结束进程 
	     */  
	    private static void killProcess(String packageName) {  
	    	DataOutputStream out = new DataOutputStream(process.getOutputStream());
	        String cmd = "am force-stop " + packageName + " \n";  
	        try {  
	            out.write(cmd.getBytes());  
	            out.writeBytes("exit\n");
	            out.flush();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    /** 
	     * 关闭输出流 
	     */  
	    private static void close() {  
	        if (process != null)  
	            try {  
	                process.getOutputStream().close();  
	                process = null;  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	    }  
	
	
	

	// 判断是否具有ROOT权限
	public static boolean is_root() {

		boolean res = false;

		try {
			if ((!new File("/system/bin/su").exists())
					&& (!new File("/system/xbin/su").exists())) {
				res = false;
			} else {
				res = true;
			}
			;
		} catch (Exception e) {

		}
		return res;
	}
	
	
	public static void getroot(){
		Process process;
		try {
			process = Runtime.getRuntime().exec("su");
			DataOutputStream os = new DataOutputStream(process.getOutputStream());  
			//这里执行命令
			os.writeBytes("exit\n");  
			os.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  

		
	}
	

}
