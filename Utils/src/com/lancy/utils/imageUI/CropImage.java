package com.lancy.utils.imageUI;

import java.io.FileNotFoundException;

import com.lancy.utils.R;
import com.lancy.utils.R.id;
import com.lancy.utils.R.layout;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * 固定大小裁剪
 * @author Lancy
 *
 */
public class CropImage extends Activity{
	
//	private static final int MAXIMUM_WIDTH = 2500;
//	private static final int MAXIMUM_HEIGHT = 2500;
	private CropImageView image;
	private Bitmap bitmap;
	private Uri uri;
	
//	private CropImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crop_image);
		image = (CropImageView) findViewById(R.id.image);
		uri = getIntent().getParcelableExtra("uri");
		try {
			DisplayMetrics dm = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			int screenW = dm.widthPixels;
			int screenH = dm.heightPixels;
			
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
			int imageWidth = options.outWidth;
			int imageHeight = options.outHeight;
			int tmp = (imageWidth/screenW)>(imageHeight/screenH)?(imageHeight/screenH):(imageWidth/screenW);
			
//			while (imageWidth > MAXIMUM_WIDTH || imageHeight > MAXIMUM_HEIGHT) {
//				imageWidth /= 2;
//				imageHeight /= 2;
//				tmp *= 2;
//			}
			Log.d("ease", "Sample Size: "+tmp);
			options.inSampleSize = tmp;
			options.inJustDecodeBounds = false;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
			image.setImageBitmap(bitmap);				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void back(View view){
		Toast.makeText(this, "back", 0).show();
		finish();
	}

	
	private  Bitmap rotaingImageView(int angle2) {  
        //旋转图片 动作  
		Matrix matrix = new Matrix();
        matrix.postRotate(angle2);  
        
        System.out.println("angle2=" + angle2);  
        // 创建新的图片  
       
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,  
        		bitmap.getWidth(), bitmap.getHeight(), matrix, false);  
        return resizedBitmap;
          
    }
	
	
	public void toleft(View v){
		bitmap = rotaingImageView(-90);
		image.setImageBitmap(bitmap);      
	}
	
	public void toright(View v){
		bitmap = rotaingImageView(+90);
		image.setImageBitmap(bitmap);      
	}
	
	public void save(View view){
		Toast.makeText(this, "save", 0).show();
		Matrix inverse = new Matrix();
		image.getImageMatrix().invert(inverse);
		float[] pts = new float[]{image.getBoxLeft(), image.getBoxTop(), image.getBoxRight(), image.getBoxBottom()};
		inverse.mapPoints(pts);
		if(pts[0] < 0){
			pts[0] = 0;
		}
		if(pts[1] < 0){
			pts[1] = 0;
		}
		if(pts[2] > bitmap.getWidth()){
			pts[2] = bitmap.getWidth();
		}
		if(pts[3] > bitmap.getHeight()){
			pts[3] = bitmap.getHeight();
		}
		Bitmap b = Bitmap.createBitmap(bitmap, (int)pts[0], (int)pts[1], (int)(pts[2] - pts[0]), (int)(pts[3] - pts[1]));
//		Gl applicaiton = (Gl) getApplication();
//		applicaiton.myHeadBm = b;
		ImageActivity.cropBitmap = b;
		setResult(RESULT_OK);
		//setResult时或者说intent里的extra不能太大，否则在某些手机上会出现crash；
//		setResult(RESULT_OK, new Intent().putExtra("data", b));
		//setResult(RESULT_OK, new Intent().putExtra("data", Bitmap.createScaledBitmap(b, 60, 60, false)));
		finish();
	}
}
