package com.example.tetrixtest;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

public class BlockControl extends View{

	
	Block block;
	private int[][] currentMap = new int[10][25];
	private long dropTimer = 1000;
	
	private Timer timer;
	
	public BlockControl(Context context) {
		super(context);
		
		block = new Block(3, 0, (int)(Math.random()*6) +1);
		
		timer = new Timer(true);
		timer.schedule(new TimerTask() {

			@Override
			public void run() {			
				if(isCollide() == false){
					block.setCurrentY(block.getCurrentY() + 1);
					block.setCurrentX(block.getCurrentX() + 1);
					mHandler.sendEmptyMessage(0);
				}
			}
			
		}, 1000, 100);
	}
	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what == 0) {
				invalidate();
			}
		}
	};

	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setARGB(255, 200, 200, 200);
		canvas.drawRect(0,0,500,1250, paint);	//테트리스가 진행되는 곳		
		block.onDraw(canvas);
	}
	
	public boolean isCollide() {
		int[][] currentBlock = block.getCurrentBlock();
		
		for(int i = 0; i < currentBlock.length; i++) {
			for(int j = 0;j < currentBlock[i].length; j++) {
				if(currentBlock[i][j] == 1) {
					
					/*왼쪽*/
					if(j+block.getCurrentX()-1 < 0) return true;
					/*오른쪽*/
					if(j+block.getCurrentX()+1 > 9) return true;
					/*바닥*/
					if(i+block.getCurrentY()+1 > 24) return true;
					/*아래블럭*/
					if(currentMap[j+block.getCurrentX()][i+block.getCurrentY()+1] == 1) return true;
					/*오른쪽블럭*/
					if(currentMap[j+block.getCurrentX()+1][i+block.getCurrentY()] == 1) return true;
					/*왼쪽블럭*/
					if(currentMap[j+block.getCurrentX()-1][i+block.getCurrentY()] == 1) return true;
				}
			}
		}
		return false;
	}
	
}
