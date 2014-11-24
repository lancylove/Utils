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
import android.os.Handler;
import android.os.Message;
import android.util.Log;

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


	/**
     * 将assets文件夹下的apk fileName，拷贝到路径path
     * 
     * @param context
     *            上下文环境
     * @param fileName
     *            apk名称
     * @param path
     *            存储APK路径
     * @return
     */
    public boolean copyMDMApkFromAssets(Context context, String fileName,
                    String path,Handler handler) {
            boolean bRet = false;
            try {
                    InputStream is = context.getAssets().open(fileName);

                    File file1 = new File(path);
                    if(!file1.exists()){
                    	file1.mkdir();
                    }
                    File file = new File(path+"/"+fileName);
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);

                    byte[] temp = new byte[1024];
                    int i = 0;
                    while ((i = is.read(temp)) > 0) {
                            fos.write(temp, 0, i);
                    }

                    fos.close();
                    is.close();

                    bRet = true;

            } catch (IOException e) {
                    e.printStackTrace();
            }
            if(bRet){
            	Message mes = new Message();
            	mes.what=0;
            	handler.sendMessage(mes);
            }else{
            	Message mes = new Message();
            	mes.what=-1;
            	handler.sendMessage(mes);
            	
            }
            return bRet;
    }

	
	
	
}
