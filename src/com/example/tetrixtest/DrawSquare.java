package com.example.tetrixtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class DrawSquare extends View {

	public DrawSquare(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint pnt = new Paint();
		pnt.setARGB(255, 0, 0, 0); /* ��� */		
		canvas.drawRect(0,0,50,50,pnt);

	}

}