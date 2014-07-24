package com.lancy.utils.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
	

	
	
	
}
