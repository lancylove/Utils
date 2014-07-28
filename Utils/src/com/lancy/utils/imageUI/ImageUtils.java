package com.lancy.utils.imageUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.LruCache;

public class ImageUtils {

	public static Bitmap defaultBitmap = null;
	
	
	//图片缓存
	public static LruCache<String, Bitmap> bitmapLrucache = null ;
	
	public static LruCache<String, Bitmap> getLruCache(){
		if(bitmapLrucache == null){
			int maxSize = (int) (Runtime.getRuntime().maxMemory()/16);
			bitmapLrucache = new LruCache<String, Bitmap>(maxSize){
				@Override
				protected int sizeOf(String key, Bitmap value) {
					// TODO Auto-generated method stub
					return value.getRowBytes() * value.getHeight();
				}
				
			};
			
			
		}
		
		return bitmapLrucache;
	}
	
	
	
	

}
