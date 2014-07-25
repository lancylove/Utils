package com.lancy.utils.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;

public class FileUtil {

	public static final String path = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String cachepath = path+"/Android/data/com.lancy.util";
	
	public  static final String otherpath = path+"/util";
	
	
	public static  File getFile(String path,String filename){
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdir();
		}
		  File file = new File(dir, filename); 
		  return file;
		
	}
	
	
	/**
	 * Bitmap保存到为指定文件
	 * @param filePath
	 * @param filename
	 * @param bitmap
	 * @return
	 */
	public static boolean saveBitmap(String filePath,String filename,Bitmap bitmap){
		FileOutputStream out = null;
		
			try {
				out = new FileOutputStream(getFile(filePath, filename));
				return bitmap.compress(CompressFormat.JPEG, 100, out);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
				
			}finally{
				if(out != null){
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
	}
	
	
	public void downloadApktoappDir(Context m,String path,String apkname) throws IOException{ 
	     URL url = null;
	     FileOutputStream fos = null; 
	     BufferedInputStream bis = null; 
	     InputStream is = null; 
	   try { 
	       url = new URL(path); 
	       HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
	       conn.setConnectTimeout(5000); 
	       // 获取到文件的大小 
	       int size = conn.getContentLength(); 
	       is = conn.getInputStream(); 
	 
	       fos = m.openFileOutput(apkname, 
	       Context.MODE_WORLD_READABLE); 
	       bis = new BufferedInputStream(is); 
	       byte[] buffer = new byte[1024]; 
	       int len; 
	       int total = 0; 
	         while ((len = bis.read(buffer)) != -1) { 
	             fos.write(buffer, 0, len); 
	             // 获取当前下载量 
	             total += len; 
	         } 
	   } catch (MalformedURLException e) { 
	       // TODO Auto-generated catch block 
	       e.printStackTrace(); 
	   } catch (IOException e) { 
	       // TODO Auto-generated catch block 
	       e.printStackTrace(); 
	   }finally{ 
	       fos.close(); 
	       bis.close(); 
	       is.close(); 
	   } 
	 } 


	

	
	
	
}
