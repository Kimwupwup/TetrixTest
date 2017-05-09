package com.example.tetrixtest;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.os.Handler;
import android.os.Message;

public class BlockControl extends View{
	
	Block block;
	private final int blockSize = 50;
	
	/*블럭이 쌓이는 것을 확인 하기위해서 만든 현재의 맵*/
	private int[][] currentMap = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
		};
	private long dropTimer = 1000;
	
	private Timer timer;
	
	public BlockControl(Context context) {
		super(context);
		
		block = new Block(3, 0, (int)(Math.random()*7) +1);
		
		/*타이머를 이용해서 일정 주기마다 한칸씩 내린다.*/
		timer = new Timer(true);
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {			
				
				/*충돌시에 복구하기 위해서 만든 recover*/
				int reX = block.getCurrentX();
				int reY = block.getCurrentY();
				int[][] recovBlock = block.getCurrentBlock().clone();
				
				/*한칸 내린다*/
				block.setCurrentY(block.getCurrentY() + 1);
				block.setCurrentX(block.getCurrentX() + 1);
				
				/*충돌이 일어날시에 이전의 상태로 복구한다.*/
				if(isCollide() == true) {
					block.setCurrentX(reX);
					block.setCurrentY(reY);
					block.setCurrentBlock(recovBlock);
					
					block.setCurrentY(block.getCurrentY() + 1);
					if(isCollide() == true) {
						block.setCurrentY(reY);
						setMap();
						block = new Block(3, 0, (int)(Math.random()*7) +1);
					}
				}
				/*Handler를 이용해서 Timer마다 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
				mHandler.sendEmptyMessage(0);
			}
			
		}, 1000, 300);
	}
	
	/*Handler*/
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what == 0) {
				invalidate();
			}
		}
	};
	
	/*onDraw 실제 화면에 출력해준다.*/
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		
		paint.setARGB(255, 200, 200, 200);	//연한 회색
		
		canvas.drawRect(50,50,650,1300, paint);	//테트리스가 진행되는 곳		
		
		block.onDraw(canvas);
		onDrawMap(canvas);
	}
	
	
	/*블럭이 충돌하는지를 알아보는 함수.*/
	public boolean isCollide() {
		int[][] currentBlock = block.getCurrentBlock();
		int chkX = 0;
		int chkY = 0;

		for(int i = 0; i < currentBlock.length; i++) {
			for(int j = 0;j < currentBlock[i].length; j++) {
				if(currentBlock[i][j] == 1) {
					chkX = j + block.getCurrentX();
					chkY = i + block.getCurrentY();
										
					if(currentMap[chkY][chkX] == 1) return true;
				}
			}
		}
		return false;
	}
	
	/*현재 쌓인 블럭을 새롭게 갱신해주는 함수*/
	public void setMap() {
		int[][] currentBlock = block.getCurrentBlock();
		int chkX = 0;
		int chkY = 0;

		for(int i = 0; i < currentBlock.length; i++) {
			for(int j = 0;j < currentBlock[i].length; j++) {
				if(currentBlock[i][j] == 1) {
					chkX = j + block.getCurrentX();
					chkY = i + block.getCurrentY();

					currentMap[chkY][chkX] = 1;					
				}
			}
		}
	}
	
	public void onDrawMap(Canvas canvas) {
		
		Paint paint = new Paint();
		paint.setARGB(255, 50, 50, 50);
		for(int i = 1; i < currentMap.length-1; i++) {
			for(int j = 1;j < currentMap[i].length-1; j++) {
				if(currentMap[i][j] == 1) {
					canvas.drawRect(j*blockSize, i*blockSize, (j+1)*blockSize, (i+1)*blockSize, paint);
				}
			}
		}
	}
	
	
}
