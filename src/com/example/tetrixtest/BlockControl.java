package com.example.tetrixtest;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;




public class BlockControl extends View{
	
	private int score = 0;
	private int nextStage = 100;
	private int stage = 1;
	private TextView mTextScore;
	private TextView mTextStage;
	Block block;
	Block nextBlock;
	
	
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
	
	public BlockControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		block = new Block(3, 0, (int)(Math.random()*7) +1);
		nextBlock = new Block(15, 1, (int)(Math.random()*7) +1);
		
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
				
				
				/*충돌이 일어날시에 이전의 상태로 복구한다.*/
				if(isCollide() == true) {
					block.setCurrentX(reX);
					block.setCurrentY(reY);
					block.setCurrentBlock(recovBlock);
					
					block.setCurrentY(block.getCurrentY() + 1);
					if(isCollide() == true) {
						block.setCurrentY(reY);
						setMap();
						deleteLine();
						
						block = new Block(3, 0, nextBlock.getBlockType());
						nextBlock = new Block(15, 1, (int)(Math.random()*7) +1);
						
					}
				}
				/*Handler를 이용해서 Timer마다 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
				mHandler.sendEmptyMessage(0);
			}
			
		}, 1000, dropTimer);
	}
	
	/*Handler*/
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if(msg.what == 0) {
				mTextScore.setText("Score : " + score);
				mTextStage.setText("Stage : " + stage);
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
		canvas.drawRect(730, 50, 1030, 350, paint); //다음 블럭이 나오는 곳
		
		
		nextBlock.onDraw(canvas);
		block.onDraw(canvas);
		onDrawMap(canvas);
	}
	
	/*줄 완성시 */
	public void deleteLine() {
		int countBlock = 0;
		int countScore = 0;
		
		for(int i = 1; i < currentMap.length-1; i++) {
			for(int j = 1;j < currentMap[i].length-1; j++) {
				if(currentMap[i][j] == 1) {
					countBlock++;
					if(countBlock == 12) {
						for(int k = 1; k < currentMap[i].length-1; k++) {
							currentMap[i][k] = 0;
						}
						countScore++;
						pullLine(i);
					}
				}
			}
			countBlock = 0;
		}
		calScore(countScore);
	}
	
	public void pullLine(int i) {
		for(;i > 1; i--) {
			for(int j = 1; j < currentMap[i].length-1; j++) {
				currentMap[i][j] = currentMap[i-1][j];
			}
		}
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
	
	/*현재 맵을 그려준다*/
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
	
	/*버튼에 따른 이동*/
	public void moveLeft() {
		block.setCurrentX(block.getCurrentX() - 1);
		/*충돌이 일어날시에 이전의 상태로 복구한다.*/
		if(isCollide() == true) {
			block.setCurrentX(block.getCurrentX() + 1);
		}
		/*Handler를 이용해서 Timer마다 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
		mHandler.sendEmptyMessage(0);
		
	}
	public void moveRight() {
		block.setCurrentX(block.getCurrentX() + 1);
		
		/*충돌이 일어날시에 이전의 상태로 복구한다.*/
		if(isCollide() == true) {
			block.setCurrentX(block.getCurrentX() - 1);
			
		}
		/*Handler를 이용해서 Timer마다 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
		mHandler.sendEmptyMessage(0);
	}
	
	public void moveDown() {
		block.setCurrentY(block.getCurrentY() + 1);
		
		/*충돌이 일어날시에 이전의 상태로 복구한다.*/
		if(isCollide() == true) {
			
			block.setCurrentY(block.getCurrentY() - 1);
			
			block.setCurrentY(block.getCurrentY() + 1);
			if(isCollide() == true) {
				block.setCurrentY(block.getCurrentY() - 1);
				setMap();
				deleteLine();
				
				block = new Block(3, 0, nextBlock.getBlockType());
				nextBlock = new Block(15, 1, (int)(Math.random()*7) +1);
			}
		}
		/*Handler를 이용해서 Timer마다 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
		mHandler.sendEmptyMessage(0);
	}
	
	/*Down키를 길게 누를경우 한번에 내려간다.*/
	public void moveQuickDown() {
		block.setCurrentY((currentMap.length - 3));
		
		/*충돌이 일어날시에 이전의 상태로 복구한다.*/
		while(isCollide()) {
			block.setCurrentY(block.getCurrentY() - 1);
		}

		block.setCurrentY(block.getCurrentY() + 1);
		if(isCollide() == true) {
			block.setCurrentY(block.getCurrentY() - 1);
			setMap();
			deleteLine();
			
			block = new Block(3, 0, nextBlock.getBlockType());
			nextBlock = new Block(15, 1, (int)(Math.random()*7) +1);
		}
		/*Handler를 이용해서 Timer마다 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
		mHandler.sendEmptyMessage(0);
	}
	
	public void rotate() {
		int[][] recovBlock = block.getCurrentBlock();
		block.rotate();
		/*충돌이 일어날시에 이전의 상태로 복구한다.*/
		if(isCollide() == true) {
			block.setCurrentBlock(recovBlock);
		}
		/*Handler를 이용해서 Timer마다 화면을 초기화하여 블럭을 움직이는 것처럼 보여준다.*/
		mHandler.sendEmptyMessage(0);
	}
	
	/*TextView를 가져오기 위한 함수*/
	public void setTextView(TextView score, TextView stage) {
		mTextScore = score;
		mTextStage = stage;
	}

	/*점수를 계산 하기 위한 함수*/
	public void calScore(int num) {
		/*줄을 없앤 수많큼 점수를 달리한다.*/
		switch(num) {
		case 0:
			break;
		case 1:
			score += 10; break;
		case 2:
			score += 30; break;
		case 3:
			score += 50; break;
		case 4: 
			score += 70; break;			
		}
		calStage();
	}
	
	/*스테이지를 올리기위한 함수*/
	public void calStage() {
		if(nextStage < score) {
			stage++;
			dropTimer *= 0.9;
			nextStage += 100;
		}
	}
	
}
