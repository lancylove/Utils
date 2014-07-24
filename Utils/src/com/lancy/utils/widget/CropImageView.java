 package com.lancy.utils.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

public class CropImageView extends ImageView {

    public CropImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.STROKE);
		matrixUninitialized = true;
		matrix = new Matrix();
		
		mScaleDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener(){
			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				Log.d("sss", "on scale: " + detector.getScaleFactor());
				matrix.set(getImageMatrix());
				float scaleFactor = detector.getScaleFactor();
				matrix.getValues(values);
				float scaleFactorToBe = values[Matrix.MSCALE_X] * scaleFactor;
				Log.d("sss", "scale to be: " + scaleFactorToBe);
				if(!(scaleFactorToBe > maximumScale) && !(scaleFactorToBe < baseScale)){
					matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
					setImageMatrix(matrix);
					invalidate();
					return true;
				}
				return super.onScale(detector);
			}
		});
	}

    private Matrix matrix;
	private int width;
    private int height;
    private float baseScale;
    private float maximumScale;
    private float lastX;
    private float lastY;
    private int bitmapWidth;
    private int bitmapHeight;
    private Paint paint;
    private float left;
    private float top;
    private int boxSize;
    private float right;
    private float bottom;
    private boolean matrixUninitialized;
    private float[] values;
    
    
    private ScaleGestureDetector mScaleDetector;
    
    public float getBoxLeft(){
    	return left;
    }
    public float getBoxRight(){
    	return right;
    }
    public float getBoxTop(){
    	return top;
    }
    public float getBoxBottom(){
    	return bottom;
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getWidth();
        height = getHeight();
        if(width < height){
        	boxSize = width - 60;
        	left = 30;
        	right = width - 30;
        	top = (height - width) / 2 + 30;
        	bottom = (height + width) / 2 - 30;
        } else {
        	boxSize = height - 60;
        	top = 30;
        	bottom = height - 30;
        	left = (width - height) / 2 + 30;
        	right = (width + height) / 2 - 30;
        }
    }
    
    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        matrixUninitialized = true;
        bitmapWidth = bm.getWidth();
        bitmapHeight = bm.getHeight();
        Log.d("sss", "w:" + bitmapWidth);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
    	mScaleDetector.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = event.getX();
			lastY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			matrix.set(getImageMatrix());
			float[] pts = new float[] { 0.0f, 0.0f, bitmapWidth, bitmapHeight };
			matrix.mapPoints(pts);
			float deltaX = event.getX() - lastX;
			float deltaY = event.getY() - lastY;
			if (pts[0] + deltaX > left) {
				deltaX = left - pts[0];
			} else if (pts[2] + deltaX < right) {
				deltaX = right - pts[2];
			}
			if (pts[1] + deltaY > top) {
				deltaY = top - pts[1];
			} else if (pts[3] + deltaY < bottom) {
				deltaY = bottom - pts[3];
			}
			matrix.postTranslate(deltaX, deltaY);
			setImageMatrix(matrix);
			invalidate();
			lastX = event.getX();
			lastY = event.getY();
			break;
		default:
			break;
		}
        return true;        
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
    	if(matrixUninitialized){
    		matrix.reset();
    		if(bitmapWidth < boxSize || bitmapHeight < boxSize){
    			if(bitmapWidth < bitmapHeight){
    				float ratio = (float) boxSize / (float) bitmapWidth;
    				matrix.postScale(ratio, ratio);
    				matrix.postTranslate(left, (height - (bitmapHeight * ratio)) / 2 );
    			} else {
    				float ratio = (float) boxSize / (float) bitmapHeight;
    				matrix.postScale(ratio, ratio);
    				matrix.postTranslate((width - (bitmapWidth * ratio)) / 2, top);
    			}
    		} else {
    			matrix.postTranslate((width - bitmapWidth) / 2, (height - bitmapHeight) / 2);
     		}
			setImageMatrix(matrix);
			values = new float[9];
			matrix.getValues(values);
			baseScale = values[Matrix.MSCALE_X];
			maximumScale = baseScale * 3.0f;
    		matrixUninitialized = false;
    	}
        super.onDraw(canvas);
        canvas.drawRect(left, top, right, bottom, paint);
    }
}