package com.example.tetrixtest;


import android.content.Context;
import android.graphics.Paint;

import android.graphics.Canvas;
import android.view.View;

public class DrawSquare extends View {

	Block block;
	int blockType = (int)(Math.random()*6)+1;
	
	public DrawSquare(Context context) {
		super(context);		
		block = new Block(0, 0, blockType);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		block.onDraw(canvas);
	}

}