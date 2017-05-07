package com.example.tetrixtest;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class BlockControl extends View{

	
	Block block;
	private int[][] currentMap = new int[20][30];
	private long dropTimer;
	
	Timer timer;
	
	public BlockControl(Context context) {
		super(context);
		
		dropTimer = (long) 1.000;
		block = new Block(500, 0, (int)(Math.random()*6) +1);
		
		timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				
				block.setCurrentY(block.getCurrentY() + 50);
			}
			
		}, dropTimer);
	}
}
