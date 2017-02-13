package com.tata.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GuaGuaKa extends View {
	/** 
     * ����������Paint,���û���ָ����Path 
     */  
    private Paint mOutterPaint = new Paint();  
    /** 
     * ��¼�û����Ƶ�Path 
     */  
    private Path mPath = new Path();  
    /** 
     * �ڴ��д�����Canvas 
     */  
    private Canvas mCanvas;  
    /** 
     * mCanvas�������������� 
     */  
    private Bitmap mBitmap;  
  
    private int mLastX;  
    private int mLastY;
    private Paint mBackPint = new Paint();  
    private Rect mTextBound = new Rect();  
    private String mText = "50000000";  
    /** 
     * ��ʼ��canvas�Ļ����õĻ��� 
     */  
    private void setUpBackPaint()  
    {  
        mBackPint.setStyle(Style.FILL);  
        mBackPint.setTextScaleX(2f);  
        mBackPint.setColor(Color.DKGRAY);  
        mBackPint.setTextSize(15);  
        mBackPint.getTextBounds(mText, 0, mText.length(), mTextBound);  
    }  
	public GuaGuaKa(Context context) {
		super(context);
	}

	public GuaGuaKa(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GuaGuaKa(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(); 
	}
	private void init()  
    {  
        mPath = new Path();  
  
    }  
	
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		// ��ʼ��bitmap
		mBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		setUpBackPaint();
		//������ĳ�
		mCanvas.drawColor(Color.parseColor("#C8C8C8"));
	}
	
	
	/** 
     * �������� 
     */  
    private void drawPath()  
    {  
    	mOutterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));  
        mCanvas.drawPath(mPath, mOutterPaint);  
  
    }  
    
	@Override
	protected void onDraw(Canvas canvas)
	{
		 //���ƽ���  
        canvas.drawText(mText, getWidth() / 2 - mTextBound.width() / 2,  
                getHeight() / 2 + mTextBound.height() / 2, mBackPint);  
          
        drawPath();  
        canvas.drawBitmap(mBitmap, 0, 0, null);  
	}
  
    @Override  
    public boolean onTouchEvent(MotionEvent event)  
    {  
        int action = event.getAction();  
        int x = (int) event.getX();  
        int y = (int) event.getY();  
        switch (action)  
        {  
        case MotionEvent.ACTION_DOWN:  
            mLastX = x;  
            mLastY = y;  
            mPath.moveTo(mLastX, mLastY);  
            break;  
        case MotionEvent.ACTION_MOVE:  
  
            int dx = Math.abs(x - mLastX);  
            int dy = Math.abs(y - mLastY);  
  
            if (dx > 3 || dy > 3)  
                mPath.lineTo(x, y);  
  
            mLastX = x;  
            mLastY = y;  
            break;  
        }  
  
        invalidate();  
        return true;  
    }  
}