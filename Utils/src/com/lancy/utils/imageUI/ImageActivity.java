package com.lancy.utils.imageUI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lancy.utils.util.FileUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ImageActivity extends Activity {
	private static final int REQUEST_CODE_BEFORE_CROP = 0;//通过拍照或者照相获取图片
	private static final int REQUEST_CODE_CROP = 1;//裁剪固定大小图片
	private static final int PHOTO_REQUEST_CUT = 2;//调用系统方法裁剪图片
	private Context mContext;
	Button btn;
	public static Bitmap cropBitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		btn = new Button(this);

		setContentView(btn);
		mContext = this;
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(mContext, ActionSheet.class)
						.putExtra("return-data", true),
						REQUEST_CODE_BEFORE_CROP);

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CODE_BEFORE_CROP:

				Uri uri = null;
				if (data.getBooleanExtra("isLocal", false)) {
					uri = data.getData();
					if (uri != null) {
						Cursor cursor = getContentResolver().query(uri, null,
								null, null, null);
						cursor.moveToFirst();

						String photopathString = cursor.getString(cursor
								.getColumnIndex(MediaStore.Images.Media.DATA));

						Bitmap bitmap;
						bitmap = BitmapFactory.decodeFile(photopathString);
						if (bitmap != null) {
							btn.setBackgroundDrawable(new BitmapDrawable(
									bitmap));
						}
					}

				} else {
					Bitmap bitmap1;
					bitmap1 = BitmapFactory.decodeFile(FileUtil.otherpath
							+ "/tmp.jpg");
					if (bitmap1 != null) {
						btn.setBackgroundDrawable(new BitmapDrawable(bitmap1));
					}
					uri = Uri.fromFile(FileUtil.getFile(FileUtil.otherpath, "tmp.jpg"));
				}
				startActivityForResult(
						new Intent(this, CropImage.class).putExtra("uri", uri),
						REQUEST_CODE_CROP);
				
//				startPhotoZoom(uri, PHOTO_REQUEST_CUT);
				
				

				break;
			case REQUEST_CODE_CROP:
				if(cropBitmap != null){
					btn.setBackgroundDrawable(new BitmapDrawable(cropBitmap));
					FileUtil.saveBitmap(FileUtil.cachepath, "tmp.jpg", cropBitmap);
					
					cropBitmap =null;
					
				}else{
					Toast.makeText(mContext, "取消", 0).show();
					
				}
				break;
			case PHOTO_REQUEST_CUT:
				 Bundle bundle = data.getExtras();
				 if(bundle != null){
					 Bitmap photo = bundle.getParcelable("data");
					 if(photo != null){
						 btn.setBackgroundDrawable(new BitmapDrawable(photo));
						 FileUtil.saveBitmap(FileUtil.cachepath, "tmp1.jpg", photo);
					 }
					 
				 }
				
				
				break;
			default:
				break;
			}

		}

	}
	
	/**
	 * 调用系统方法对图片进行裁剪
	 * @param uri
	 */
	 private void startPhotoZoom(Uri uri,int RequestCode) {
	        Intent intent = new Intent("com.android.camera.action.CROP");
	        intent.setDataAndType(uri, "image/*");
	        // crop为true是设置在开启的intent中设置显示的view可以剪裁
	        intent.putExtra("crop", "true");

	        // aspectX aspectY 是宽高的比例
	        intent.putExtra("aspectX", 1);
	        intent.putExtra("aspectY", 1);

	        // outputX,outputY 是剪裁图片的宽高
	        intent.putExtra("outputX", 300);
	        intent.putExtra("outputY", 300);
	        intent.putExtra("return-data", true);
	        intent.putExtra("noFaceDetection", true);
	        startActivityForResult(intent,RequestCode );
	    }
	
	 
	    // 将进行剪裁后的图片传递到下一个界面上
	    private void sentPicToNext(Intent picdata) {
	        Bundle bundle = picdata.getExtras();
	        if (bundle != null) {
	            Bitmap photo = bundle.getParcelable("data");
	            if (photo==null) {
//	                img.setImageResource(R.drawable.get_user_photo);
	            }else {
	            	btn.setBackgroundDrawable(new BitmapDrawable(photo));

	            }
	           
	            
	            
	            ByteArrayOutputStream baos = null;
	            try {
	                baos = new ByteArrayOutputStream();
	                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	                byte[] photodata = baos.toByteArray();
	                System.out.println(photodata.toString());
	                // Intent intent = new Intent();
	                // intent.setClass(RegisterActivity.this, ShowActivity.class);
	                // intent.putExtra("photo", photodata);
	                // startActivity(intent);
	                // finish();
	            } catch (Exception e) {
	                e.getStackTrace();
	            } finally {
	                if (baos != null) {
	                    try {
	                        baos.close();
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }
	    }
	 

}
