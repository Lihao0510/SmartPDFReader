package com.dzyd.pdfreader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	private SurfaceHolder mHolder;
	private Canvas mCanvas;
	private boolean isDrawing;

	public MySurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public MySurfaceView(Context context) {
		super(context);
		initView();
	}

	public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}
	
	private void initView() {
		mHolder = getHolder();
		mHolder.addCallback(this);
		setFocusable(true);
		setFocusableInTouchMode(true);
		setKeepScreenOn(true);
		mHolder.setFormat(PixelFormat.OPAQUE);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int x,y;
		Path mPath;
		while(isDrawing){
			draw();
			
		}
	}

	private void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		isDrawing =true;
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		isDrawing =false;
	}

}
